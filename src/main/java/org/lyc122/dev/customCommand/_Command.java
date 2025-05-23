package org.lyc122.dev.customCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class _Command extends Command {
    File config;
    boolean isNew = false;
    List<String> perm;
    /**
     *
     * @param name Command's name
     * @param description The description of the command
     * @param usageMessage The message displayed on the usage screen
     * @param aliases Aliases
     * @param config_file The configuration file of this command.
     *                    If it is == null, then it will be replaced with plugin.getDataFolder()+"/command/"+name+".cfg".

     */

    protected _Command(@NotNull JavaPlugin plugin, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases, @Nullable File config_file, @NotNull List<String> permissions) throws IOException {
        super(name, description, usageMessage, aliases);
        config = Objects.requireNonNullElseGet(config_file, () -> new File(plugin.getDataFolder() + "/command/" + name + ".cfg"));
        if(! config.exists()){
            isNew = true;
            try{
                config.createNewFile();
            }
            catch(IOException e){
                throw new IOException("""
                        at _Command -> init
                        Affected command: %cmd%
                        Error: We can't find the configuration file of the command, and we can't create it,too!
                        File: %file%
                        Path: %path%
                        Line: *
                        Due to this: This command will NOT be loaded.
                        Solution: Check if you have the permission to create a new file. Or find your Administrator for help.
                        """);
            }
        }
        perm = permissions;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        boolean PermissionCheckFailed = false;
        for(String permission : perm){
            if(!commandSender.hasPermission(permission)){
                commandSender.sendMessage(
                        """
                        Error when executing command:
                        you don't have permission needed: %perm%
                        """
                        .replaceAll("%perm%",permission));
                PermissionCheckFailed = true;
            }
        }
        return true;
    }
}