package com.mrlang.rotp_shadowworld.entity.stand.stands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SHADOWWORLDEntity extends StandEntity {

    private StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(-0.45,  0.2, -0.75);
    private StandRelativeOffset offsetDefaultArmsOnly = StandRelativeOffset.withYOffset(0,  0, 0.15);

    public SHADOWWORLDEntity(StandEntityType<SHADOWWORLDEntity> type, World world) {
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }

    @Override
    public StandRelativeOffset getDefaultOffsetFromUser() {
        return isArmsOnlyMode() ? offsetDefaultArmsOnly : offsetDefault;
    }
}