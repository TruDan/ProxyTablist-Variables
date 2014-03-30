
import eu.scrayos.proxytablist.ProxyTablist;
import java.util.Iterator;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dan
 */
public class ServerPlayerHelper {

    private int lastSlot;
    private ProxiedPlayer player;
    private Iterator<ProxiedPlayer> serverPlayerList;

    public ServerPlayerHelper(ProxiedPlayer player, int slot) {
        this.player = player;
        this.lastSlot = slot;
    }

    public void refresh() {
        this.lastSlot = 0;
        try {
            serverPlayerList = player.getServer().getInfo().getPlayers().iterator();
        }
        catch(Exception e) {
            serverPlayerList = null;
        }
    }

    public String getText(Short ping) {
        if (player == null) {
            return "";
        }
        if (serverPlayerList == null) {
            return "";
        }
        
        if(!serverPlayerList.hasNext()) {
            return "";
        }
        
        ProxiedPlayer pp = serverPlayerList.next();
        ping = (new Integer(pp.getPing())).shortValue();
        
        System.out.println(formatName(pp));
        
        return formatName(pp);
    }
    
    public String formatName(ProxiedPlayer p) {
        StringBuilder name = new StringBuilder();

        //Check for Prefix
        if(ProxyTablist.getInstance().getConfig().contains("variable.player.prefix." + p.getName())) {
            name.append(ProxyTablist.getInstance().getConfig().getString("variable.player.prefix." + p.getName(), ""));
        }

        for (String c : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "r"}) {
            if (p.hasPermission("proxy.tablist." + c)) {
                name.append("§");
                name.append(c);
            }
        }

        name.append(p.getName());
        return name.toString();
    }
}
