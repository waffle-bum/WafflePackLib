package net.wafflebum.wafflepackcore.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.wafflebum.wafflepackcore.WafflePackCore;
import net.wafflebum.wafflepackcore.util.helpers.WPC_Tooltips;

import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = WafflePackCore.MOD_ID, value = Dist.CLIENT)
public class WPC_ClientEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();

        // One loop to rule them all
        for (Map.Entry<Supplier<? extends Item>, WPC_Tooltips.TooltipData> entry : WPC_Tooltips.GLOBAL_TOOLTIP_MAP.entrySet()) {
            if (entry.getKey().get() == item) {
                WPC_Tooltips.TooltipData data = entry.getValue();

                // Applying your specific style: Dark Gray + Italic
                event.getToolTip().add(Component.translatable(data.langKey())
                        .withStyle(ChatFormatting.DARK_GRAY)
                        .withStyle(ChatFormatting.ITALIC));
                break;
            }
        }
    }
}
