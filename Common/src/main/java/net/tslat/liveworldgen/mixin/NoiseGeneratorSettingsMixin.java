package net.tslat.liveworldgen.mixin;

import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.tslat.liveworldgen.LiveWorldgenAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {
	@Inject(method = "noiseSettings", at = @At("HEAD"), cancellable = true)
	public void wrapLiveNoiseSettings(CallbackInfoReturnable<NoiseSettings> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			NoiseSettings newNoiseSettings = LiveWorldgenAccess.INSTANCE.getLiveNoiseSettings();

			if (newNoiseSettings != null)
				callback.setReturnValue(newNoiseSettings);
		}
	}

	@Inject(method = "defaultBlock", at = @At("HEAD"), cancellable = true)
	public void wrapLiveDefaultBlock(CallbackInfoReturnable<BlockState> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			BlockState newDefaultBlock = LiveWorldgenAccess.INSTANCE.getLiveDefaultBlockState();

			if (newDefaultBlock != null)
				callback.setReturnValue(newDefaultBlock);
		}
	}

	@Inject(method = "defaultFluid", at = @At("HEAD"), cancellable = true)
	public void wrapLiveDefaultFluid(CallbackInfoReturnable<BlockState> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			BlockState newDefaultFluid = LiveWorldgenAccess.INSTANCE.getLiveDefaultFluid();

			if (newDefaultFluid != null)
				callback.setReturnValue(newDefaultFluid);
		}
	}

	@Inject(method = "noiseRouter", at = @At("HEAD"), cancellable = true)
	public void wrapLiveNoiseRouter(CallbackInfoReturnable<NoiseRouter> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			NoiseRouter newNoiseRouter = LiveWorldgenAccess.INSTANCE.getLiveNoiseRouter();

			if (newNoiseRouter != null)
				callback.setReturnValue(newNoiseRouter);
		}
	}

	@Inject(method = "surfaceRule", at = @At("HEAD"), cancellable = true)
	public void wrapLiveSurfaceRules(CallbackInfoReturnable<SurfaceRules.RuleSource> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			SurfaceRules.RuleSource newSurfaceRules = LiveWorldgenAccess.INSTANCE.getLiveSurfaceRules();

			if (newSurfaceRules != null)
				callback.setReturnValue(newSurfaceRules);
		}
	}

	@Inject(method = "spawnTarget", at = @At("HEAD"), cancellable = true)
	public void wrapLiveSpawnTarget(CallbackInfoReturnable<List<Climate.ParameterPoint>> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			List<Climate.ParameterPoint> newSpawnTarget = LiveWorldgenAccess.INSTANCE.getLiveSpawnTarget();

			if (newSpawnTarget != null)
				callback.setReturnValue(newSpawnTarget);
		}
	}

	@Inject(method = "seaLevel", at = @At("HEAD"), cancellable = true)
	public void wrapLiveSeaLevel(CallbackInfoReturnable<Integer> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			Integer newSeaLevel = LiveWorldgenAccess.INSTANCE.getLiveSeaLevel();

			if (newSeaLevel != null)
				callback.setReturnValue(newSeaLevel);
		}
	}

	@Inject(method = "disableMobGeneration", at = @At("HEAD"), cancellable = true)
	public void wrapLiveDisableMobGeneration(CallbackInfoReturnable<Boolean> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			Boolean newDisableMobGeneration = LiveWorldgenAccess.INSTANCE.getLiveDisableMobGeneration();

			if (newDisableMobGeneration != null)
				callback.setReturnValue(newDisableMobGeneration);
		}
	}

	@Inject(method = "aquifersEnabled", at = @At("HEAD"), cancellable = true)
	public void wrapLiveAquifersEnabled(CallbackInfoReturnable<Boolean> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			Boolean newAquifiersEnabled = LiveWorldgenAccess.INSTANCE.getLiveAquifiersEnabled();

			if (newAquifiersEnabled != null)
				callback.setReturnValue(newAquifiersEnabled);
		}
	}

	@Inject(method = "oreVeinsEnabled", at = @At("HEAD"), cancellable = true)
	public void wrapLiveOreVeinsEnabled(CallbackInfoReturnable<Boolean> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			Boolean newOreVeinsEnabled = LiveWorldgenAccess.INSTANCE.getLiveOreVeinsEnabled();

			if (newOreVeinsEnabled != null)
				callback.setReturnValue(newOreVeinsEnabled);
		}
	}

	@Inject(method = "useLegacyRandomSource", at = @At("HEAD"), cancellable = true)
	public void wrapLiveUseLegacyRandomSource(CallbackInfoReturnable<Boolean> callback) {
		if (LiveWorldgenAccess.INSTANCE != null) {
			Boolean newUseLegacyRandomSource = LiveWorldgenAccess.INSTANCE.getLiveUseLegacyRandomSource();

			if (newUseLegacyRandomSource != null)
				callback.setReturnValue(newUseLegacyRandomSource);
		}
	}
}
