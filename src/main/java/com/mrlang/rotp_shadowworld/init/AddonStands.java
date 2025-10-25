package com.mrlang.rotp_shadowworld.init;

import com.mrlang.rotp_shadowworld.entity.stand.stands.SHADOWWORLDEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;

public class AddonStands {

    public static final EntityStandSupplier<EntityStandType<TimeStopperStandStats>, StandEntityType<SHADOWWORLDEntity>>
    SHADOW_WORLD = new EntityStandSupplier<>(InitStands.STAND_SHADOW_WORLD);
}
