package walksy.shieldstatus.main;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import walksy.shieldstatus.manager.ConfigManager;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class ShieldStatus implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigManager.load();

        // Command registration may have changed in newer APIs.
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> 
            dispatcher.register(ClientCommandManager.literal("walksyshieldstatusopacity")
                .then(ClientCommandManager.argument("num", IntegerArgumentType.integer())
                    .executes(context -> {
                        int value = IntegerArgumentType.getInteger(context, "num");
                        ConfigManager.enabledOpacity = value;
                        ConfigManager.disabledOpacity = value;
                        // Text.of is still valid in 1.21.x, but always check for API updates
                        context.getSource().sendFeedback(Text.literal("Opacity set to: [Enabled] " + value + " [Disabled] " + value));
                        ConfigManager.save();
                        return 1;
                    })
                )
            )
        );
    }
}
