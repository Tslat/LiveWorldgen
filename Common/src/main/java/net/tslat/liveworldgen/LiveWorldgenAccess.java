package net.tslat.liveworldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class LiveWorldgenAccess {
	public static final Logger LOGGER = LogManager.getLogger("liveworldgen");
	public static LiveWorldgenAccess INSTANCE = null;
	public static PlacedFeature lastPlacedFeature = null;
	public static ResourceKey<PlacedFeature> lastPlacedFeatureKey = null;
	public static WorldGenLevel currentGenLevel = null;

	protected LiveWorldgenAccess() {
		INSTANCE = this;

		LOGGER.log(Level.INFO, "Applied LiveWorldgenAccess: " + getClass().getCanonicalName());
	}

	@Nullable
	public abstract PlacedFeature getLiveFeature(final ResourceKey<PlacedFeature> currentFeature, RegistryAccess registryAccess);

	@Nullable
	public abstract ConfiguredWorldCarver<?> getLiveWorldCarver(final ResourceKey<ConfiguredWorldCarver<?>> currentCarver, final RegistryAccess registryAccess);

	@Nullable
	public abstract NoiseGeneratorSettings getLiveNoiseGeneratorSettings(final Holder<NoiseGeneratorSettings> currentSettings);

	@Nullable
	public abstract NoiseSettings getLiveNoiseSettings();

	@Nullable
	public abstract BlockState getLiveDefaultBlockState();

	@Nullable
	public abstract BlockState getLiveDefaultFluid();

	@Nullable
	public abstract NoiseRouter getLiveNoiseRouter();

	@Nullable
	public abstract SurfaceRules.RuleSource getLiveSurfaceRules();

	@Nullable
	public abstract List<Climate.ParameterPoint> getLiveSpawnTarget();

	@Nullable
	public abstract Integer getLiveSeaLevel();

	@Nullable
	public abstract Boolean getLiveDisableMobGeneration();

	@Nullable
	public abstract Boolean getLiveAquifiersEnabled();

	@Nullable
	public abstract Boolean getLiveOreVeinsEnabled();

	@Nullable
	public abstract Boolean getLiveUseLegacyRandomSource();
}
