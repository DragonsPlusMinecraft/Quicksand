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

package plus.dragons.quicksand.integration.biomeswevegone;

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

public class BWGIntegrationBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(QuicksandCommon.ID);
    public static final DeferredBlock<Block> BLACK_SAND = DeferredBlock
            .createBlock(BWGIntegration.asResource("black_sand"));
    public static final DeferredBlock<QuicksandBlock> BLACK_QUICKSAND = BLOCKS
            .register(BWGIntegration.asPath("black_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0x4F4F4F),
                    BLACK_SAND,
                    BWGIntegrationItems.BLACK_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> BLACK_QUICKSAND_CAULDRON = BLOCKS
            .register(BWGIntegration.asPath("black_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BWGIntegrationItems.BLACK_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> WHITE_SAND = DeferredBlock
            .createBlock(BWGIntegration.asResource("white_sand"));
    public static final DeferredBlock<QuicksandBlock> WHITE_QUICKSAND = BLOCKS
            .register(BWGIntegration.asPath("white_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xEAEAEA),
                    WHITE_SAND,
                    BWGIntegrationItems.WHITE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> WHITE_QUICKSAND_CAULDRON = BLOCKS
            .register(BWGIntegration.asPath("white_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BWGIntegrationItems.WHITE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> BLUE_SAND = DeferredBlock
            .createBlock(BWGIntegration.asResource("blue_sand"));
    public static final DeferredBlock<QuicksandBlock> BLUE_QUICKSAND = BLOCKS
            .register(BWGIntegration.asPath("blue_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xCEE4ED),
                    BLACK_SAND,
                    BWGIntegrationItems.BLUE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> BLUE_QUICKSAND_CAULDRON = BLOCKS
            .register(BWGIntegration.asPath("blue_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BWGIntegrationItems.BLUE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> PURPLE_SAND = DeferredBlock
            .createBlock(BWGIntegration.asResource("purple_sand"));
    public static final DeferredBlock<QuicksandBlock> PURPLE_QUICKSAND = BLOCKS
            .register(BWGIntegration.asPath("purple_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xC4A3DA),
                    PURPLE_SAND,
                    BWGIntegrationItems.PURPLE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> PURPLE_QUICKSAND_CAULDRON = BLOCKS
            .register(BWGIntegration.asPath("purple_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BWGIntegrationItems.PURPLE_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> PINK_SAND = DeferredBlock
            .createBlock(BWGIntegration.asResource("pink_sand"));
    public static final DeferredBlock<QuicksandBlock> PINK_QUICKSAND = BLOCKS
            .register(BWGIntegration.asPath("pink_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xEDCEEC),
                    PINK_SAND,
                    BWGIntegrationItems.PINK_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> PINK_QUICKSAND_CAULDRON = BLOCKS
            .register(BWGIntegration.asPath("pink_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BWGIntegrationItems.PINK_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<Block> WINDSWEPT_SAND = DeferredBlock
            .createBlock(BWGIntegration.asResource("windswept_sand"));
    public static final DeferredBlock<QuicksandBlock> WINDSWEPT_QUICKSAND = BLOCKS
            .register(BWGIntegration.asPath("windswept_quicksand"), () -> new QuicksandBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                            .forceSolidOn()
                            .dynamicShape()
                            .isRedstoneConductor(QuicksandBlocks::never),
                    new ColorRGBA(0xD5A496),
                    WINDSWEPT_SAND,
                    BWGIntegrationItems.WINDSWEPT_QUICKSAND_BUCKET::value));
    public static final DeferredBlock<QuicksandCauldronBlock> WINDSWEPT_QUICKSAND_CAULDRON = BLOCKS
            .register(BWGIntegration.asPath("windswept_quicksand_cauldron"), location -> new QuicksandCauldronBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON),
                    location,
                    BWGIntegrationItems.WINDSWEPT_QUICKSAND_BUCKET::value));

    public static void register(IEventBus modBus) {
        BLOCKS.addAlias(BWGIntegration.asResource("quicksand"), QuicksandCommon.asResource("quicksand"));
        BLOCKS.addAlias(BWGIntegration.asResource("red_quicksand"), QuicksandCommon.asResource("red_quicksand"));
        BLOCKS.register(modBus);
    }

    public static void registerCauldronInteractions() {
        BLACK_QUICKSAND_CAULDRON.get().registerDefaultInteractions(BLACK_SAND);
        WHITE_QUICKSAND_CAULDRON.get().registerDefaultInteractions(WHITE_SAND);
        BLUE_QUICKSAND_CAULDRON.get().registerDefaultInteractions(BLUE_SAND);
        PURPLE_QUICKSAND_CAULDRON.get().registerDefaultInteractions(PURPLE_SAND);
        PINK_QUICKSAND_CAULDRON.get().registerDefaultInteractions(PINK_SAND);
        WINDSWEPT_QUICKSAND_CAULDRON.get().registerDefaultInteractions(WINDSWEPT_SAND);
    }
}
