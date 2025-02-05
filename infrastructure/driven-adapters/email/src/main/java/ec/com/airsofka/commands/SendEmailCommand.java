package ec.com.airsofka.commands;

import ec.com.airsofka.generics.utils.Command;

import java.util.Map;

public class SendEmailCommand extends Command {
    private String to;
    private Map<String, Object> variables;

    public SendEmailCommand(String s, Map<String, Object> variables) {
        super(null);
        to = s;
        this.variables = variables;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
