package net.tslat.liveworldgen.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.tslat.liveworldgen.LiveWorldgenAccess;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
	@Mutable
	@Shadow
	@Final
	private Holder<NoiseGeneratorSettings> settings;

	@Inject(method = "applyCarvers", at = @At("HEAD"))
	public void captureLevel(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carvingStep, CallbackInfo callback) {
		LiveWorldgenAccess.currentGenLevel = level;
	}

	@Redirect(method = "applyCarvers", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Holder;value()Ljava/lang/Object;", ordinal = 1))
	public Object wrapLiveWorldCarver(Holder<ConfiguredWorldCarver<?>> instance) {
		ConfiguredWorldCarver<?> carver = instance.value();

		if (LiveWorldgenAccess.INSTANCE == null)
			return carver;

		ResourceKey<ConfiguredWorldCarver<?>> carverKey = LiveWorldgenAccess.currentGenLevel.registryAccess().registry(Registries.CONFIGURED_CARVER).get().getResourceKey(carver).orElse(null);

		if (carverKey == null)
			return carver;

		ConfiguredWorldCarver<?> liveCarver = LiveWorldgenAccess.INSTANCE.getLiveWorldCarver(carverKey, LiveWorldgenAccess.currentGenLevel.registryAccess());

		return liveCarver != null ? liveCarver : instance.value();
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void wrapLiveNoiseSettings(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> original, CallbackInfo callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			NoiseGeneratorSettings settings = LiveWorldgenAccess.INSTANCE.getLiveNoiseGeneratorSettings(original);

			if (settings != null) {
				this.settings = new Holder<NoiseGeneratorSettings>() {
					final NoiseGeneratorSettings value = settings;
					final Holder<NoiseGeneratorSettings> originalSettings = original;

					@Override
					public NoiseGeneratorSettings value() {
						return settings;
					}

					@Override
					public boolean isBound() {
						return originalSettings.isBound();
					}

					@Override
					public boolean is(ResourceLocation resourceLocation) {
						return originalSettings.is(resourceLocation);
					}

					@Override
					public boolean is(ResourceKey<NoiseGeneratorSettings> resourceKey) {
						return originalSettings.is(resourceKey);
					}

					@Override
					public boolean is(Predicate<ResourceKey<NoiseGeneratorSettings>> predicate) {
						return originalSettings.is(predicate);
					}

					@Override
					public boolean is(TagKey<NoiseGeneratorSettings> tagKey) {
						return originalSettings.is(tagKey);
					}

					@Override
					public Stream<TagKey<NoiseGeneratorSettings>> tags() {
						return originalSettings.tags();
					}

					@Override
					public Either<ResourceKey<NoiseGeneratorSettings>, NoiseGeneratorSettings> unwrap() {
						return originalSettings.unwrap();
					}

					@Override
					public Optional<ResourceKey<NoiseGeneratorSettings>> unwrapKey() {
						return originalSettings.unwrapKey();
					}

					@Override
					public Kind kind() {
						return originalSettings.kind();
					}

					@Override
					public boolean canSerializeIn(HolderOwner<NoiseGeneratorSettings> holderOwner) {
						return originalSettings.canSerializeIn(holderOwner);
					}
				};
			}
		}
	}
}
