/*
 * Copyright (C) 2025 Shnupbups, LambdAurora and DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.quicksand.common.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.quicksand.common.QuicksandCommon;
import plus.dragons.quicksand.common.block.QuicksandBlock;
import plus.dragons.quicksand.common.block.QuicksandCauldronBlock;

public class QuicksandBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(QuicksandCommon.ID);
    public static final DeferredBlock<QuicksandBlock> QUICKSAND = BLOCKS
            .register("quicksand", () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xDBD3A0),
                    () -> Blocks.SAND,
                    QuicksandItems.QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> QUICKSAND_CAULDRON = BLOCKS
            .register("quicksand_cauldron", location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    QuicksandItems.QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandBlock> RED_QUICKSAND = BLOCKS
            .register("red_quicksand", () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xA95821),
                    () -> Blocks.RED_SAND,
                    QuicksandItems.RED_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> RED_QUICKSAND_CAULDRON = BLOCKS
            .register("red_quicksand_cauldron", location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    QuicksandItems.RED_QUICKSAND_BUCKET::value));

    public static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static void registerCauldronInteractions() {
        QUICKSAND_CAULDRON.get().registerDefaultInteractions(Blocks.SAND);
        RED_QUICKSAND_CAULDRON.get().registerDefaultInteractions(Blocks.RED_SAND);
    }
}
