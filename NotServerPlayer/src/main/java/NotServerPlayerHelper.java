
import eu.scrayos.proxytablist.ProxyTablist;
import java.util.Collection;
import java.util.Iterator;
import net.md_5.bungee.BungeeCord;
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
public class NotServerPlayerHelper {

    private int lastSlot;
    private ProxiedPlayer player;
    private Iterator<ProxiedPlayer> playerList;

    public NotServerPlayerHelper(ProxiedPlayer player, int slot) {
        this.player = player;
        this.lastSlot = slot;
    }

    public void refresh() {
        this.lastSlot = 0;
        try {
            Collection<ProxiedPlayer> serverPlayerList = player.getServer().getInfo().getPlayers();
            Collection<ProxiedPlayer> globalPlayerList = BungeeCord.getInstance().getPlayers();
            
            for(ProxiedPlayer p : serverPlayerList) {
                if(globalPlayerList.contains(p)) {
                    globalPlayerList.remove(p);
                }
            }
            
            playerList = globalPlayerList.iterator();
        } catch (Exception e) {
            playerList = null;
        }
    }

    public String getText(Short ping) {
        if (player == null) {
            return "";
        }
        if (playerList == null) {
            return "";
        }

        if (!playerList.hasNext()) {
            return "";
        }

        ProxiedPlayer pp = playerList.next();
        ping = (new Integer(pp.getPing())).shortValue();

        return formatName(pp);
    }

    public String formatName(ProxiedPlayer p) {
        StringBuilder name = new StringBuilder();

        //Check for Prefix
        if (ProxyTablist.getInstance().getConfig().contains("variable.player.prefix." + p.getName())) {
            name.append(ProxyTablist.getInstance().getConfig().getString("variable.player.prefix." + p.getName(), ""));
        }

        for (String c : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "r"}) {
            if (p.hasPermission("proxy.tablist." + c)) {
                name.append("§");
                name.append(c);
            }
        }

        name.append(p.getName());
        String nameStr = name.toString();
        if (nameStr == null) {
            return "";
        }

        return nameStr;
    }
}
