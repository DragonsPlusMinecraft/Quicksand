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

package plus.dragons.quicksand.mixin.biomeswevegone;

import java.util.function.Supplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import plus.dragons.quicksand.common.registry.QuicksandBlocks;

@Pseudo
@Mixin(targets = "net.potionstudios.biomeswevegone.world.level.block.BWGBlocks")
public class BWGBlocksMixin {
    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=quicksand"), to = @At(value = "FIELD", target = "Lnet/potionstudios/biomeswevegone/world/level/block/BWGBlocks;QUICKSAND:Ljava/util/function/Supplier;")), at = @At(value = "INVOKE", target = "Lnet/potionstudios/biomeswevegone/world/level/block/BWGBlocks;registerCubeAllBlockItem(Ljava/lang/String;Ljava/util/function/Supplier;)Ljava/util/function/Supplier;"))
    private static Supplier<?> clinit$replaceQuicksand(String key, Supplier<?> blockSupplier) {
        return QuicksandBlocks.QUICKSAND;
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=red_quicksand"), to = @At(value = "FIELD", target = "Lnet/potionstudios/biomeswevegone/world/level/block/BWGBlocks;RED_QUICKSAND:Ljava/util/function/Supplier;")), at = @At(value = "INVOKE", target = "Lnet/potionstudios/biomeswevegone/world/level/block/BWGBlocks;registerCubeAllBlockItem(Ljava/lang/String;Ljava/util/function/Supplier;)Ljava/util/function/Supplier;"))
    private static Supplier<?> clinit$replaceRedQuicksand(String key, Supplier<?> blockSupplier) {
        return QuicksandBlocks.RED_QUICKSAND;
    }
}
