package net.tslat.liveworldgen.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.tslat.liveworldgen.LiveWorldgenAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Supplier;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
	@Inject(method = "applyBiomeDecoration", at = @At("HEAD"))
	public void captureLevel(WorldGenLevel level, ChunkAccess chunkAccess, StructureManager structureManager, CallbackInfo callback) {
		LiveWorldgenAccess.currentGenLevel = level;
	}

	@Redirect(method = "applyBiomeDecoration", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 3))
	public Object replacePlacedFeature(List<PlacedFeature> features, int i) {
		PlacedFeature feature = LiveWorldgenAccess.lastPlacedFeature = features.get(i);

		if (LiveWorldgenAccess.INSTANCE == null)
			return feature;

		LiveWorldgenAccess.lastPlacedFeatureKey = LiveWorldgenAccess.currentGenLevel.registryAccess().registry(Registries.PLACED_FEATURE).get().getResourceKey(feature).orElse(null);

		if (LiveWorldgenAccess.lastPlacedFeatureKey != null) {
			feature = LiveWorldgenAccess.INSTANCE.getLiveFeature(LiveWorldgenAccess.lastPlacedFeatureKey, LiveWorldgenAccess.currentGenLevel.registryAccess());
		}


		if (feature == null) {
			feature = LiveWorldgenAccess.lastPlacedFeature;
			LiveWorldgenAccess.lastPlacedFeature = null;
		}

		return feature;
	}

	@ModifyVariable(method = "applyBiomeDecoration", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setCurrentlyGenerating(Ljava/util/function/Supplier;)V", ordinal = 1), index = 26)
	public Supplier<String> wrapCurrentFeature(Supplier value) {
		return LiveWorldgenAccess.lastPlacedFeatureKey == null ? value : LiveWorldgenAccess.lastPlacedFeatureKey::toString;
	}
}
