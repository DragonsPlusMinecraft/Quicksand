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

package plus.dragons.quicksand.client.particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public class QuicksandParticle extends SnowflakeParticle {
    protected QuicksandParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
    }

    public static class Type extends ParticleType<ColorParticleOption> {
        private final MapCodec<ColorParticleOption> codec = ColorParticleOption.codec(this);
        private final StreamCodec<? super RegistryFriendlyByteBuf, ColorParticleOption> streamCodec = ColorParticleOption.streamCodec(this);

        public Type() {
            super(false);
        }

        @Override
        public MapCodec<ColorParticleOption> codec() {
            return codec;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ColorParticleOption> streamCodec() {
            return streamCodec;
        }
    }

    public static class Provider implements ParticleProvider<ColorParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(ColorParticleOption option, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            QuicksandParticle particle = new QuicksandParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
            particle.setColor(option.getRed(), option.getGreen(), option.getBlue());
            return particle;
        }
    }
}
