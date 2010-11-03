package org.openengsb.openticket.ui.web;

import java.util.UUID;

import org.openengsb.core.taskbox.model.Ticket;

public class TicketServiceImpl implements TicketService {
	
	@Override
	public Ticket createEmptyTicket() {
		UUID uuid = UUID.randomUUID();
    	Ticket ticket = new Ticket("ID-" + uuid.toString());
    	
    	return ticket;
	}
}
