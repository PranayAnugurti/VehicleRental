package model;

import exception.InvalidCommandException;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String commandName;
    private List<String> params = new ArrayList<>();
    private static final String SPACE = " ";

    public Command(String inputString) {
        String tokenList[] = inputString.trim().split(SPACE);

        if (tokenList.length == 0) {
            throw new InvalidCommandException();
        }

        commandName = tokenList[0];

        for (int i = 1; i < tokenList.length; i++) {
            params.add(tokenList[i]);
        }
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
