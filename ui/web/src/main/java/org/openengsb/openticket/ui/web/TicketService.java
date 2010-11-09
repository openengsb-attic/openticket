package org.openengsb.openticket.ui.web;

import org.openengsb.core.taskbox.model.TaskStepType;
import org.openengsb.core.taskbox.model.Ticket;
import org.openengsb.core.taskbox.model.TaskStep;

public interface TicketService {
	
	/**
     * Creates a new empty Ticket object and returns it.
     * */
	Ticket createEmptyTicket();
	
	/**
	 * Creates a new TaskStep object of a certain shape/type
	 * */
	TaskStep createNewTaskStep(TaskStepType taskStepType, String taskStepName, String taskStepDescription);
}
