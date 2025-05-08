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

package plus.dragons.quicksand.integration.biomesoplenty;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.quicksand.common.QuicksandCommon;
import plus.dragons.quicksand.common.block.QuicksandBlock;
import plus.dragons.quicksand.common.block.QuicksandCauldronBlock;
import plus.dragons.quicksand.common.registry.QuicksandBlocks;

public class BOPIntegrationBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(QuicksandCommon.ID);
    public static final DeferredBlock<Block> WHITE_SAND = DeferredBlock
            .createBlock(BOPIntegration.asResource("white_sand"));
    public static final DeferredBlock<QuicksandBlock> WHITE_QUICKSAND = BLOCKS
            .register(BOPIntegration.asPath("white_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xF3F1E4),
                    WHITE_SAND,
                    BOPIntegrationItems.WHITE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> WHITE_QUICKSAND_CAULDRON = BLOCKS
            .register(BOPIntegration.asPath("white_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BOPIntegrationItems.WHITE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> ORANGE_SAND = DeferredBlock
            .createBlock(BOPIntegration.asResource("orange_sand"));
    public static final DeferredBlock<QuicksandBlock> ORANGE_QUICKSAND = BLOCKS
            .register(BOPIntegration.asPath("orange_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xCC9A61),
                    ORANGE_SAND,
                    BOPIntegrationItems.ORANGE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> ORANGE_QUICKSAND_CAULDRON = BLOCKS
            .register(BOPIntegration.asPath("orange_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BOPIntegrationItems.ORANGE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> BLACK_SAND = DeferredBlock
            .createBlock(BOPIntegration.asResource("black_sand"));
    public static final DeferredBlock<QuicksandBlock> BLACK_QUICKSAND = BLOCKS
            .register(BOPIntegration.asPath("black_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0x2D2C2F),
                    BLACK_SAND,
                    BOPIntegrationItems.BLACK_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> BLACK_QUICKSAND_CAULDRON = BLOCKS
            .register(BOPIntegration.asPath("black_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BOPIntegrationItems.BLACK_QUICKSAND_BUCKET::value));

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static void registerCauldronInteractions() {
        WHITE_QUICKSAND_CAULDRON.get().registerDefaultInteractions(WHITE_SAND);
        ORANGE_QUICKSAND_CAULDRON.get().registerDefaultInteractions(ORANGE_SAND);
        BLACK_QUICKSAND_CAULDRON.get().registerDefaultInteractions(BLACK_SAND);
    }
}
