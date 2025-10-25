package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.mrlang.rotp_shadowworld.init.InitSounds;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class SHADOWWORLDEvolution extends StandEntityAction {
    private static final String HEAVEN_PAGE_TAG = "heaven_page";
    private static final Map<UUID, Integer> EVOLUTION_TIMERS = new HashMap<>();
    private static final Map<UUID, Boolean> HAS_PLAYED_BGM = new HashMap<>();
    private static final Map<UUID, Integer> FIRE_PROTECTION_TIMERS = new HashMap<>();
    private boolean isEvolutionStage = false;

    public SHADOWWORLDEvolution(Builder builder) {
        super(builder.holdToFire(0,  false));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase  == TickEvent.Phase.END) {
            UUID playerId = event.player.getUUID();

            if (!event.player.level.isClientSide()  && EVOLUTION_TIMERS.containsKey(playerId))  {
                int ticksLeft = EVOLUTION_TIMERS.get(playerId)  - 1;

                if (ticksLeft > 0 && ticksLeft % 15 == 0 && event.player.level.random.nextFloat()  < 0.15f) {
                    spawnRandomLightning((ServerWorld) event.player.level,  event.player);
                }

                if (ticksLeft > 0 && ticksLeft % 2 == 0) {
                    for (int i = 0; i < 4; i++) {
                        ((ServerWorld)event.player.level).sendParticles(
                                ParticleTypes.ENCHANT,
                                event.player.getX()  + (event.player.level.random.nextDouble()  - 0.5) * 5.0,
                                event.player.getY()  + (event.player.level.random.nextDouble()  - 0.5) * 4.0,
                                event.player.getZ()  + (event.player.level.random.nextDouble()  - 0.5) * 5.0,
                                1,
                                0, 0, 0,
                                0.2
                        );
                    }
                }

                if (ticksLeft <= 200 && !HAS_PLAYED_BGM.getOrDefault(playerId,  false)) {
                    event.player.level.playSound(null,
                            event.player.getX(),
                            event.player.getY(),
                            event.player.getZ(),
                            InitSounds.EVOLUTIONBGM.get(),
                            event.player.getSoundSource(),
                            1.0F,
                            1.0F);
                    HAS_PLAYED_BGM.put(playerId,  true);
                }

                if (ticksLeft <= 0) {
                    MCUtil.runCommand(event.player,  "stand clear @s");
                    MCUtil.runCommand(event.player,  "stand give @s jojo:the_world true");
                    MCUtil.runCommand(event.player,  "standlevel set @s 4");
                    event.player.addEffect(new  EffectInstance(Effects.CONFUSION, 200, 2));
                    event.player.addEffect(new  EffectInstance(Effects.BLINDNESS, 100, 0));
                    spawnFinalLightningCircle((ServerWorld) event.player.level,  event.player);

                    EVOLUTION_TIMERS.remove(playerId);
                    HAS_PLAYED_BGM.remove(playerId);
                } else {
                    EVOLUTION_TIMERS.put(playerId,  ticksLeft);
                }
            }

            if (FIRE_PROTECTION_TIMERS.containsKey(playerId))  {
                int ticksLeft = FIRE_PROTECTION_TIMERS.get(playerId)  - 1;

                if (!event.player.level.isClientSide())  {
                    event.player.clearFire();
                    event.player.setRemainingFireTicks(0);
                } else {
                    event.player.setRemainingFireTicks(-20); 
                }

                if (ticksLeft <= 0) {
                    FIRE_PROTECTION_TIMERS.remove(playerId);
                } else {
                    FIRE_PROTECTION_TIMERS.put(playerId,  ticksLeft);
                }
            }
        }
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (EVOLUTION_TIMERS.containsKey(user.getUUID()))  {
            return ActionConditionResult.createNegative(new  TranslationTextComponent("action.shadowworld.evolution.in_progress"));
        }

        if (!isNightTime(user.level))  {
            return negative("evolution.nnight_required");
        }

        ItemStack offhand = user.getOffhandItem();
        if (offhand.isEmpty())  {
            return negative("evolution.need_item");
        }

        isEvolutionStage = isHeavenPage(offhand);
        return (isEvolutionStage || isNetherStar(offhand))
                ? ActionConditionResult.POSITIVE
                : negative("evolution.need_item");
    }

    @Override
    public int getHoldDurationToFire(IStandPower power) {
        return 60;
    }

    @Override
    public void holdTick(World world, LivingEntity user, IStandPower power,
                         int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if (EVOLUTION_TIMERS.containsKey(user.getUUID()))  {
            return;
        }

        boolean isHolding = power.getHeldAction()  == this && requirementsFulfilled;
        ItemStack offhand = user.getOffhandItem();
        boolean currentStageIsEvolution = isHeavenPage(offhand);

        if (!world.isClientSide()  && ticksHeld % 10 == 0 && isHolding) {
            if (!currentStageIsEvolution) {
                world.playSound(null,  user.getX(),  user.getY(),  user.getZ(),
                        SoundEvents.GLASS_BREAK,
                        user.getSoundSource(),
                        1.0F,
                        0.5F + world.random.nextFloat()  * 0.5F);
            } else {
                world.playSound(null,  user.getX(),  user.getY(),  user.getZ(),
                        SoundEvents.BAT_TAKEOFF,
                        user.getSoundSource(),
                        2.0F,
                        0.8F + world.random.nextFloat()  * 0.4F);
            }
        }
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide())  return;

        LivingEntity user = userPower.getUser();
        if (EVOLUTION_TIMERS.containsKey(user.getUUID()))  {
            return;
        }

        ItemStack offhand = user.getOffhandItem();
        if (offhand.isEmpty())  return;

        if (isEvolutionStage) {
            if (!isHeavenPage(offhand)) return;

            world.playSound(null,  user.getX(),  user.getY(),  user.getZ(),
                    InitSounds.SDDIO_EVOLUTION.get(),
                    user.getSoundSource(),
                    1.0F, 1.0F);

            user.addEffect(new  EffectInstance(Effects.MOVEMENT_SLOWDOWN, 58*20, 2, false, false, false));

            user.addEffect(new  EffectInstance(
                    Effects.FIRE_RESISTANCE,
                    90 * 20,
                    0,
                    false,
                    false,
                    false
            ) {
                @Override
                public boolean shouldRender() {
                    return false;
                }

                @Override
                public boolean shouldRenderHUD() {
                    return false;
                }

                @Override
                public boolean shouldRenderInvText() {
                    return false;
                }
            });

            FIRE_PROTECTION_TIMERS.put(user.getUUID(),  90 * 20);
            user.clearFire();
            user.setRemainingFireTicks(0);

            EVOLUTION_TIMERS.put(user.getUUID(),  58*20);
            HAS_PLAYED_BGM.put(user.getUUID(),  false);
            spawnFinalLightningCircle((ServerWorld) world, user);
        } else {
            if (!isNetherStar(offhand)) return;

            offhand.shrink(1);
            ItemStack heavenPage = createHeavenPage();
            user.setItemInHand(user.getUsedItemHand(),  heavenPage);
            user.addEffect(new  EffectInstance(Effects.GLOWING, 200, 0));
        }
    }

    private static void spawnRandomLightning(ServerWorld world, LivingEntity user) {
        double angle = world.random.nextDouble()  * Math.PI * 2;
        double radius = 3.0 + world.random.nextDouble()  * 4.0;
        double x = user.getX()  + Math.cos(angle)  * radius;
        double z = user.getZ()  + Math.sin(angle)  * radius;

        LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        lightning.moveTo(x,  user.getY(),  z);
        lightning.setVisualOnly(true);
        world.addFreshEntity(lightning);
    }

    private static void spawnFinalLightningCircle(ServerWorld world, LivingEntity user) {
        for (int i = 0; i < 8; i++) {
            double angle = i * (Math.PI * 2 / 8);
            double radius = 3.0;
            double x = user.getX()  + Math.cos(angle)  * radius;
            double z = user.getZ()  + Math.sin(angle)  * radius;

            LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.moveTo(x,  user.getY(),  z);
            lightning.setVisualOnly(true);
            world.addFreshEntity(lightning);
        }

        LightningBoltEntity centerLightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
        centerLightning.moveTo(user.getX(),  user.getY(),  user.getZ());
        centerLightning.setVisualOnly(true);
        world.addFreshEntity(centerLightning);

        world.playSound(null,  user.getX(),  user.getY(),  user.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER,
                SoundCategory.WEATHER,
                5.0F,
                0.8F + world.random.nextFloat()  * 0.2F);
        world.playSound(null,  user.getX(),  user.getY(),  user.getZ(),
                SoundEvents.LIGHTNING_BOLT_IMPACT,
                SoundCategory.WEATHER,
                3.0F,
                0.6F + world.random.nextFloat()  * 0.2F);
    }

    private ItemStack createHeavenPage() {
        ItemStack heavenPage = new ItemStack(Items.PAPER);
        CompoundNBT tag = heavenPage.getOrCreateTag();
        tag.putBoolean(HEAVEN_PAGE_TAG,  true);

        if (Enchantments.BLOCK_EFFICIENCY.getRegistryName()  != null) {
            CompoundNBT enchTag = new CompoundNBT();
            enchTag.putString("id",  Enchantments.BLOCK_EFFICIENCY.getRegistryName().toString());
            enchTag.putInt("lvl",  0);
            heavenPage.addTagElement("Enchantments",  new net.minecraft.nbt.ListNBT());
            heavenPage.getOrCreateTag().getList("Enchantments",  10).add(enchTag);
        }

        heavenPage.setHoverName(new  TranslationTextComponent("item.shadowworld.heaven_page"));
        return heavenPage;
    }

    private boolean isNetherStar(ItemStack stack) {
        return !stack.isEmpty()  && stack.getItem()  == Items.NETHER_STAR;
    }

    private boolean isHeavenPage(ItemStack stack) {
        return !stack.isEmpty()  &&
                stack.getItem()  == Items.PAPER &&
                stack.hasTag()  &&
                stack.getTag().contains(HEAVEN_PAGE_TAG)  &&
                stack.getTag().getBoolean(HEAVEN_PAGE_TAG)  &&
                stack.getHoverName().getString().equals(
                        new TranslationTextComponent("item.shadowworld.heaven_page").getString());
    }

    private boolean isNightTime(World world) {
        if (world == null) return false;
        long time = world.getDayTime()  % 24000;
        return time >= 13000 && time <= 23000;
    }

    private ActionConditionResult negative(String key) {
        return ActionConditionResult.createNegative(new  TranslationTextComponent("action.shadowworld."  + key));
    }
}