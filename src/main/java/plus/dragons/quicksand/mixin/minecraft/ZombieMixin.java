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

package plus.dragons.quicksand.mixin.minecraft;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.quicksand.common.extension.QuicksandConvertible;
import plus.dragons.quicksand.common.registry.QuicksandSoundEvents;
import plus.dragons.quicksand.config.QuicksandConfig;

@SuppressWarnings("WrongEntityDataParameterClass")
@Mixin(value = Zombie.class, priority = 1301686257)
public abstract class ZombieMixin extends Monster implements QuicksandConvertible {
    @Unique
    private static final EntityDataAccessor<Boolean> QUICKSAND_CONVERSION_ID = SynchedEntityData
            .defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private int ticksInQuicksand;
    @Unique
    private int quicksandConversionTime;

    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected abstract void convertToZombieType(EntityType<? extends Zombie> entityType);

    @Shadow
    protected abstract boolean convertsInWater();

    @Override
    public boolean canConvertInQuicksand() {
        return this.convertsInWater() && QuicksandConfig.SERVER.quicksandConvertsZombie.get();
    }

    @Override
    public boolean isInQuicksandConversion() {
        return this.getEntityData().get(QUICKSAND_CONVERSION_ID);
    }

    @Override
    public int getTicksInQuicksand() {
        return ticksInQuicksand;
    }

    @Override
    public void setTicksInQuicksand(int ticksInQuicksand) {
        this.ticksInQuicksand = ticksInQuicksand;
    }

    @Override
    public int getQuicksandConversionTime() {
        return quicksandConversionTime;
    }

    @Override
    public void setQuicksandConversionTime(int time) {
        this.quicksandConversionTime = time;
        this.getEntityData().set(QUICKSAND_CONVERSION_ID, time > -1);
    }

    @Override
    public void doQuicksandConversion() {
        if (EventHooks.canLivingConvert(this, EntityType.HUSK, this::setQuicksandConversionTime)) {
            this.convertToZombieType(EntityType.HUSK);
            this.playSound(QuicksandSoundEvents.ZOMBIE_CONVERTED_TO_HUSK.get(), 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSynchedData$addQuicksandConversion(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(QUICKSAND_CONVERSION_ID, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveData$addQuicksandSaveData(CompoundTag nbt, CallbackInfo ci) {
        nbt.putInt("InQuicksandTime", this.isSubmergedInQuicksand() ? this.ticksInQuicksand : -1);
        nbt.putInt("QuicksandConversionTime", this.isInQuicksandConversion() ? this.quicksandConversionTime : -1);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData$readQuicksandSaveData(CompoundTag nbt, CallbackInfo ci) {
        this.ticksInQuicksand = nbt.getInt("InQuicksandTime");
        if (nbt.contains("QuicksandConversionTime", Tag.TAG_ANY_NUMERIC))
            this.setQuicksandConversionTime(nbt.getInt("QuicksandConversionTime"));
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Zombie;isUnderWaterConverting()Z"))
    private void tick$updateQuicksandConversion(CallbackInfo ci) {
        if (this.canConvertInQuicksand()) {
            if (this.isInQuicksandConversion()) {
                --this.quicksandConversionTime;
                if (this.quicksandConversionTime < 0)
                    this.doQuicksandConversion();
            } else if (this.isSubmergedInQuicksand()) {
                if (this.ticksInQuicksand < 600)
                    this.ticksInQuicksand++;
                else
                    this.setQuicksandConversionTime(300);
            } else if (this.ticksInQuicksand > 0) {
                this.ticksInQuicksand = this.ticksInQuicksand - 2;
            } else this.ticksInQuicksand = -1;
        }
    }
}
