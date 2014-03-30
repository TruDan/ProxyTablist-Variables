/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import eu.scrayos.proxytablist.ProxyTablist;
import eu.scrayos.proxytablist.api.Variable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

/**
 *
 * @author Dan
 */
public class ServerPlayer implements Variable {

    private HashMap<ProxiedPlayer, ServerPlayerHelper> helpers = new HashMap<ProxiedPlayer, ServerPlayerHelper>();
    private static final Pattern pattern = Pattern.compile("\\{serverPlayer\\}");

    private int lastRefreshId = -1;
    private ProxiedPlayer lastPlayer;

    public ServerPlayer() {
//        (new Thread() {
//            public void run() {
//                while(true) {
//                    try {
//                        for (ServerInfo serverInfo : BungeeCord.getInstance().getServers().values()) {
//                            pingServer(serverInfo);
//                        }
//
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        System.out.println("Interrupted ping Thread");
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }
//    
//    private void pingServer(final ServerInfo serverInfo) {
//        serverInfo.ping(new Callback<ServerPing>() {
//            @Override
//            public void done(ServerPing serverPing, Throwable throwable) {
//            }
//        });
//    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int refreshId) {
        if (lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            helpers = new HashMap<ProxiedPlayer, ServerPlayerHelper>();
            
            for(ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
                helpers.put(p, new ServerPlayerHelper(p,0));
            }
            
            for (ServerPlayerHelper helper : helpers.values()) {
                helper.refresh();
            }
        }
    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        lastPlayer = proxiedPlayer;

        return true;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {
        // Do nothing
    }

    @Override
    public boolean isForGlobalTablist() {
        return false;
    }

    @Override
    public String getText(Short ping) {
        if (lastPlayer == null) {
            return "";
        }

        if (!helpers.containsKey(lastPlayer)) {
            return "";
        }

        ServerPlayerHelper helper = helpers.get(lastPlayer);
        return helper.getText(ping);
    }

}
