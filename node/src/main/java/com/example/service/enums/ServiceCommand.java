package com.example.service.enums;

public enum ServiceCommand {
    HELP("/help"), 
    REGISTRATION("/registration"), 
    CANCEL("/cancel"), 
    START("/start");

    private final String value;
    ServiceCommand(String value) {this.value = value;}
    @Override
    public String toString() {
        return value;
    }
    
    // private final String cmd;

    // ServiceCommand(String cmd) {
    //     this.cmd = cmd;
    // }

    // @Override
    // public String toString() {
    //     return cmd;
    // }

    // public boolean equals(String cmd) {
    //     return this.toString().equals(cmd);
    // }

    public static ServiceCommand fromValue(String v) {
        for (ServiceCommand c: ServiceCommand.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }
   
}
