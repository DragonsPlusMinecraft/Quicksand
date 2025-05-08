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

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.quicksand.common.QuicksandCommon;

public class BOPIntegrationItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(QuicksandCommon.ID);
    private static final ResourceKey<CreativeModeTab> CREATIVE_MODE_TAB = ResourceKey
            .create(Registries.CREATIVE_MODE_TAB, BOPIntegration.asResource("main"));
    public static final DeferredItem<SolidBucketItem> WHITE_QUICKSAND_BUCKET = ITEMS
            .register(BOPIntegration.asPath("white_quicksand_bucket"), () -> new SolidBucketItem(
                    BOPIntegrationBlocks.WHITE_QUICKSAND.get(),
                    SoundEvents.SAND_PLACE,
                    new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final DeferredItem<SolidBucketItem> ORANGE_QUICKSAND_BUCKET = ITEMS
            .register(BOPIntegration.asPath("orange_quicksand_bucket"), () -> new SolidBucketItem(
                    BOPIntegrationBlocks.ORANGE_QUICKSAND.get(),
                    SoundEvents.SAND_PLACE,
                    new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final DeferredItem<SolidBucketItem> BLACK_QUICKSAND_BUCKET = ITEMS
            .register(BOPIntegration.asPath("black_quicksand_bucket"), () -> new SolidBucketItem(
                    BOPIntegrationBlocks.BLACK_QUICKSAND.get(),
                    SoundEvents.SAND_PLACE,
                    new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(BOPIntegrationItems::onBuildCreativeModeTabContents);
    }

    public static void onBuildCreativeModeTabContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CREATIVE_MODE_TAB) {
            event.insertBefore(
                    BOPIntegrationBlocks.WHITE_SAND.toStack(),
                    WHITE_QUICKSAND_BUCKET.toStack(),
                    TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(
                    BOPIntegrationBlocks.ORANGE_SAND.toStack(),
                    ORANGE_QUICKSAND_BUCKET.toStack(),
                    TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(
                    BOPIntegrationBlocks.BLACK_SAND.toStack(),
                    BLACK_QUICKSAND_BUCKET.toStack(),
                    TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
