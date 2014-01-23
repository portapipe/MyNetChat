package netchat.nhadobas;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.List;

public class NetChat extends Command implements Listener
{
    public NetMain pasta;
    public List<String> input = new ArrayList<String>();
    public NetChat(NetMain Nethad)
    {
        super("netchat", "netchat.use");
        this.pasta = Nethad;
        this.pasta.getProxy().getPluginManager().registerCommand(pasta, this);
        this.pasta.getProxy().getPluginManager().registerListener(pasta, this);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!input.contains(s.getName())) {
            this.input.add(s.getName());
            s.sendMessage(new TextComponent(ChatColor.DARK_RED + "Cross Server Global Chat disabled."));
        } else {
            input.remove(s.getName());
            s.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "Cross Server Global Chat enabled."));
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(ChatEvent e){
        if(e.getSender() instanceof ProxiedPlayer){
            if (!e.isCommand()) {
                ProxiedPlayer s = (ProxiedPlayer)e.getSender();
                String senderServerName = s.getServer().getInfo().getName();
                boolean senderGlobalChat = s.hasPermission("netchat.broadcast") && !input.contains(s.getName());
                for(ProxiedPlayer pl : this.pasta.getProxy().getPlayers()) {
                    if (!senderServerName.equals(pl.getServer().getInfo().getName()) && (senderGlobalChat || !input.contains(pl.getName()))) {
                        sendMessage(s, pl, e.getMessage());
                    }
                }
            }
        }
    }

    private void sendMessage(ProxiedPlayer s, ProxiedPlayer pl, String message) {
        pl.sendMessage(new TextComponent((ChatColor.DARK_RED + "[" + ChatColor.BLUE + s.getServer().getInfo().getName() + ChatColor.DARK_RED + "] " + ChatColor.GOLD + s.getName() + ChatColor.RED + ": " + message)));
    }
}
