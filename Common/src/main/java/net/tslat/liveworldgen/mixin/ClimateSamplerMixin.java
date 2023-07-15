package net.tslat.liveworldgen.mixin;

import net.minecraft.world.level.biome.Climate;
import net.tslat.liveworldgen.LiveWorldgenAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(Climate.Sampler.class)
public class ClimateSamplerMixin {
	@Shadow @Final private List<Climate.ParameterPoint> spawnTarget;

	@Redirect(method = "findSpawnPosition", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/biome/Climate$Sampler;spawnTarget:Ljava/util/List;"))
	public List<Climate.ParameterPoint> wrapDynamicSpawnTarget(Climate.Sampler instance) {
		if (LiveWorldgenAccess.INSTANCE == null)
			return this.spawnTarget;

		List<Climate.ParameterPoint> liveSpawnTarget = LiveWorldgenAccess.INSTANCE.getLiveSpawnTarget();

		return liveSpawnTarget != null ? liveSpawnTarget : this.spawnTarget;
	}
}
