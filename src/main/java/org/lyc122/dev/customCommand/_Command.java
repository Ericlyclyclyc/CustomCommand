package org.lyc122.dev.customCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class _Command extends Command {
    Vector<String> sCommands;
    public _Command(Plugin plugin,String CommandName, String usage, String description) throws FileNotFoundException {
        super(CommandName, description, usage, new ArrayList<>());
        try {
            Scanner config = new Scanner(
                    new File(
                            plugin.getDataFolder() + "/commands/",
                            CommandName + ".cmd_cfg"
                    )
            );
            int line_number = 0;
            while(config.hasNextLine()){
                line_number++;
                String line = config.nextLine();
                char[] linea = line.toCharArray();
                if(linea[0] == '#');//do nothing to the text if it's just some comments
                else{
                    String[] args = line.split(" ");
//                    if(Objects.equals(args[0], "Description")){
//                        int pos=0;
//                        do{
//                            ++pos;
//                        }while(linea[pos] != ':');
//                        do{
//                            ++pos;
//                        }while(linea[pos] != ' ');
//                        StringBuilder desc = new StringBuilder();
//                        for(int i = pos; i <= linea.length; i++){
//                            desc.append(linea[i]);
//                        }
//                        HasDescription = true;
//                        description = "";
//                        description = desc.toString();
//                    }
//                    else if(Objects.equals(args[0], "Usage")){
//                        int pos=0;
//                        do{
//                            ++pos;
//                        }while(linea[pos] != ':');
//                        do{
//                            ++pos;
//                        }while(linea[pos] != ' ');
//                        StringBuilder usages = new StringBuilder();
//                        for(int i = pos; i <= linea.length; i++){
//                            usages.append(linea[i]);
//                        }
//                        HasUsage = true;
//                        usage = usages.toString();
//                    }
                    if(!Objects.equals(args[0], "SERVER_COMMAND")&&!Objects.equals(args[0], "CUSTOM_COMMAND")){
                        throw new InvalidConfigurationException("""
                                There is an unexpected identifier in your configuration file.
                                File : %FILE%
                                Line : %LINE%
                                Unexpected identifier : %UID%
                                This must be one in these two:
                                SERVER_COMMAND : For server command
                                CUSTOM_COMMAND : For the command you registered.
                                Then, it's just some boring stack tracks...
                                
                                """
                                .replaceAll("%FILE%", CommandName+".cmd_cfg")
                                .replaceAll("%LINE%",String.valueOf(line_number))
                        );
                    }
                    sCommands.add(line);
                }
            }
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("""
                    We got an exception when loading the configuration of one of your commands.
                    Exception : FileNotFoundException
                    Description : Please check if your command hadn't get a configuration file.
                    File : %FILE%
                    Line : *
                    Please check if there is a config file named with your command's name in the folder:
                    /plugins/CustomCommand/commands/
                    or this command will NOT be loaded!
                    Raw error message:
                    %RAW_MESSAGE%
                    """
                    .replaceAll("%FILE",plugin.getDataFolder()+"/command/"+ CommandName +".cmd_cfg")
                    .replaceAll("%RAW_MESSAGE%",e.getLocalizedMessage())
            );
        }
    }
    protected _Command(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String cmd, @NotNull String @NotNull [] args) {
        return false;
    }
}
