package com.arthou.tpa.client;

import com.arthou.tpa.TpaNeoForgeMod;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.ModListScreen;
import net.neoforged.neoforge.client.gui.widget.ModListWidget;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** Gives the Mods screen the same author link that launchers render from HTML. */
@EventBusSubscriber(modid = TpaNeoForgeMod.MOD_ID, value = Dist.CLIENT)
public final class TpaModListDescription {
    private static final String AUTHOR_URL = "https://discord.com/users/990003604725321808";
    private static final int PANEL_TEXT_INSET = 12;
    private static Field selectedField;
    private static Field modInfoField;
    private static Field linesField;
    private static Field widthField;
    private static List<FormattedCharSequence> injected;
    private static boolean disabled;

    private TpaModListDescription() {
    }

    @SubscribeEvent
    public static void onRenderPre(ScreenEvent.Render.Pre event) {
        if (disabled || !(event.getScreen() instanceof ModListScreen screen)) {
            return;
        }

        try {
            resolveFields();
            if (!(selectedField.get(screen) instanceof ModListWidget.ModEntry entry)
                || !TpaNeoForgeMod.MOD_ID.equals(entry.getInfo().getModId())) {
                return;
            }

            Object panel = modInfoField.get(screen);
            if (panel == null) {
                return;
            }

            @SuppressWarnings("unchecked")
            List<FormattedCharSequence> lines = (List<FormattedCharSequence>) linesField.get(panel);
            if (lines == injected || lines == null) {
                return;
            }

            // NeoForge places the description after the first blank separator.
            int separator = lines.indexOf(null);
            int start = separator + 1;
            if (separator < 0 || start >= lines.size() || lines.get(start) == null) {
                return;
            }
            int end = start;
            while (end < lines.size() && lines.get(end) != null) {
                end++;
            }

            Component credit = Component.literal("Made by ").append(Component.literal("Arthou")
                .withStyle(Style.EMPTY.withColor(0x5BC8FF).withUnderlined(true)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, AUTHOR_URL))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(AUTHOR_URL)))));
            List<FormattedCharSequence> rewritten = new ArrayList<>(lines.subList(0, start));
            int wrapWidth = widthField.getInt(panel) - PANEL_TEXT_INSET;
            if (wrapWidth > 0) {
                rewritten.addAll(Minecraft.getInstance().font.split(credit, wrapWidth));
            } else {
                rewritten.add(credit.getVisualOrderText());
            }
            rewritten.addAll(lines.subList(end, lines.size()));
            linesField.set(panel, rewritten);
            injected = rewritten;
        } catch (ReflectiveOperationException | RuntimeException e) {
            disabled = true;
            LogUtils.getLogger().warn("TPA could not restyle its Mods screen author link", e);
        }
    }

    private static void resolveFields() throws ReflectiveOperationException {
        if (selectedField != null) {
            return;
        }
        Field selected = ModListScreen.class.getDeclaredField("selected");
        Field modInfo = ModListScreen.class.getDeclaredField("modInfo");
        Field lines = modInfo.getType().getDeclaredField("lines");
        Field width = ScrollPanel.class.getDeclaredField("width");
        selected.setAccessible(true);
        modInfo.setAccessible(true);
        lines.setAccessible(true);
        width.setAccessible(true);
        modInfoField = modInfo;
        linesField = lines;
        widthField = width;
        selectedField = selected;
    }
}
