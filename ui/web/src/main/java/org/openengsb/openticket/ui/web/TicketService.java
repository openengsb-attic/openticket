package org.openengsb.openticket.ui.web;

import org.openengsb.core.taskbox.model.Ticket;

public interface TicketService {
	
	/**
     * Creates a new empty Ticketobject and returns it.
     * */
	Ticket createEmptyTicket();
}
