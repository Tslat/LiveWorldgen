package net.tslat.liveworldgen.mixin;

import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.tslat.liveworldgen.LiveWorldgenAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Optional;

@Mixin(PlacedFeature.class)
public class PlacedFeatureMixin {
	@ModifyArg(method = "placeWithBiomeCheck", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/placement/PlacementContext;<init>(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Ljava/util/Optional;)V"), index = 2)
	public Optional<PlacedFeature> insertWrappedFeature(Optional<PlacedFeature> original) {
		return LiveWorldgenAccess.lastPlacedFeature == null ? original : Optional.of(LiveWorldgenAccess.lastPlacedFeature);
	}
}
