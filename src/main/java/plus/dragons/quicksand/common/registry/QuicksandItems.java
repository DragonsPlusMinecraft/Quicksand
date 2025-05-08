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

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.quicksand.common.QuicksandCommon;

public class QuicksandItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(QuicksandCommon.ID);
    public static final DeferredItem<SolidBucketItem> QUICKSAND_BUCKET = ITEMS
            .register("quicksand_bucket", () -> new SolidBucketItem(
                    QuicksandBlocks.QUICKSAND.get(),
                    SoundEvents.SAND_PLACE,
                    new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final DeferredItem<SolidBucketItem> RED_QUICKSAND_BUCKET = ITEMS
            .register("red_quicksand_bucket", () -> new SolidBucketItem(
                    QuicksandBlocks.RED_QUICKSAND.get(),
                    SoundEvents.SAND_PLACE,
                    new Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(QuicksandItems::onBuildCreativeModeTabContents);
    }

    public static void onBuildCreativeModeTabContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertBefore(Items.POWDER_SNOW_BUCKET.getDefaultInstance(),
                    QUICKSAND_BUCKET.toStack(),
                    TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertBefore(Items.POWDER_SNOW_BUCKET.getDefaultInstance(),
                    RED_QUICKSAND_BUCKET.toStack(),
                    TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
