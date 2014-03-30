/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import eu.scrayos.proxytablist.api.Variable;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

/**
 *
 * @author Dan
 */
public class CurrentServer implements Variable {

    private static final Pattern pattern = Pattern.compile("\\{currentServer\\}");

    private int lastRefreshId = -1;
    private ProxiedPlayer lastPlayer;

    public CurrentServer() {
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int refreshId) {
        lastRefreshId = refreshId;
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

        Server server = lastPlayer.getServer();

        if (server == null) {
            return "";
        }

        ServerInfo serverInfo = server.getInfo();
        if (serverInfo == null) {
            return "";
        }

        if (serverInfo.getName() == null) {
            return "";
        }

        System.out.println(serverInfo.getName());
        
        return serverInfo.getName();
    }

}
