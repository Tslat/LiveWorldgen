package net.tslat.liveworldgen.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.tslat.liveworldgen.LiveWorldgenAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SurfaceSystem.class)
public class SurfaceSystemMixin {
	@Shadow @Final private BlockState defaultBlock;

	@Shadow @Final private int seaLevel;

	@Redirect(method = "erodedBadlandsExtension", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0))
	public boolean erodedBadlandsExtensionDefaultBlock(BlockState instance, Block block) {
		if (LiveWorldgenAccess.INSTANCE == null)
			return instance.is(block);

		BlockState liveBlock = LiveWorldgenAccess.INSTANCE.getLiveDefaultBlockState();

		return instance.is(liveBlock == null ? block : liveBlock.getBlock());
	}

	@Redirect(method = "erodedBadlandsExtension", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/BlockColumn;setBlock(ILnet/minecraft/world/level/block/state/BlockState;)V"))
	public void erodedBadlandsExtensionSetDefaultBlock(BlockColumn instance, int yPos, BlockState state) {
		if (LiveWorldgenAccess.INSTANCE == null) {
			instance.setBlock(yPos, state);

			return;
		}

		BlockState liveBlock = LiveWorldgenAccess.INSTANCE.getLiveDefaultBlockState();

		instance.setBlock(yPos, liveBlock == null ? state : liveBlock);
	}

	@Redirect(method = "buildSurface", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/SurfaceSystem;defaultBlock:Lnet/minecraft/world/level/block/state/BlockState;"))
	public BlockState captureBlockColumn(SurfaceSystem instance) {
		if (LiveWorldgenAccess.INSTANCE == null)
			return this.defaultBlock;

		BlockState liveBlock = LiveWorldgenAccess.INSTANCE.getLiveDefaultBlockState();

		return liveBlock != null ? liveBlock : this.defaultBlock;
	}

	@Redirect(method = "frozenOceanExtension", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/SurfaceSystem;seaLevel:I"))
	public int wrapDynamicSeaLevel(SurfaceSystem instance) {
		if (LiveWorldgenAccess.INSTANCE == null)
			return this.seaLevel;

		Integer liveSeaLevel = LiveWorldgenAccess.INSTANCE.getLiveSeaLevel();

		return liveSeaLevel != null ? liveSeaLevel : this.seaLevel;
	}
}
