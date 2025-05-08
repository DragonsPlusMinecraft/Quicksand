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

package plus.dragons.quicksand.coremod;

import com.google.auto.service.AutoService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformer.Target;
import java.util.ArrayList;
import java.util.List;
import net.neoforged.neoforgespi.coremod.ICoreMod;

@AutoService(ICoreMod.class)
public class QuicksandCoreMod implements ICoreMod {
    @Override
    public Iterable<? extends ITransformer<?>> getTransformers() {
        List<ITransformer<?>> transformers = new ArrayList<>();
        transformers.add(new ReplaceCheckCast(
                "net/potionstudios/biomeswevegone/world/level/block/sand/BWGQuickSand",
                "plus/dragons/quicksand/common/block/QuicksandBlock",
                Target.targetMethod(
                        "net.potionstudios.biomeswevegone.world.level.levelgen.biome.BWGOverworldSurfaceRules",
                        "<clinit>", "()V")));
        return transformers;
    }
}
