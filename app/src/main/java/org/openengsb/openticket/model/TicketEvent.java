package org.openengsb.openticket.model;

import org.openengsb.core.common.Event;

public class TicketEvent extends Event {
    private String type;
    private String ticketId;
    
    public TicketEvent() {
        super();
    }

    public TicketEvent(String name, String type, String ticketId) {
        super(name);
        this.type = type;
        this.ticketId = ticketId;
    }
    
    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
