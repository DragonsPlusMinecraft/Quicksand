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

package plus.dragons.quicksand.common.registry.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import plus.dragons.quicksand.common.QuicksandCommon;

public class QuicksandTags {
    public static final TagKey<EntityType<?>> QUICKSAND_WALKABLE_MOBS = TagKey
            .create(Registries.ENTITY_TYPE, QuicksandCommon.asResource("quicksand_walkable_mobs"));
    public static final TagKey<EntityType<?>> QUICKSAND_IMMUNE_ENTITY_TYPES = TagKey
            .create(Registries.ENTITY_TYPE, QuicksandCommon.asResource("quicksand_immune_entity_types"));
}
