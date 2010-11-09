package org.openengsb.openticket.ui.web;

import java.util.UUID;

import org.openengsb.core.taskbox.model.CompleteTicketInformationStep;
import org.openengsb.core.taskbox.model.DeveloperTaskStep;
import org.openengsb.core.taskbox.model.InformationTaskStep;
import org.openengsb.core.taskbox.model.ReviewerTaskStep;
import org.openengsb.core.taskbox.model.TaskStep;
import org.openengsb.core.taskbox.model.TaskStepType;
import org.openengsb.core.taskbox.model.Ticket;

public class TicketServiceImpl implements TicketService {
	
	@Override
	public Ticket createEmptyTicket() {
		UUID uuid = UUID.randomUUID();
    	Ticket ticket = new Ticket("ID-" + uuid.toString());
    	
    	return ticket;
	}

	@Override
	public TaskStep createNewTaskStep(TaskStepType taskStepType, String taskStepName, String taskStepDescription) {
		TaskStep newTaskStep = null;
		switch(taskStepType) {
		case CompleteTicketInformationStep:
			newTaskStep = new CompleteTicketInformationStep(taskStepName, taskStepDescription);
			break;
		case DeveloperTaskStep:
			newTaskStep = new DeveloperTaskStep(taskStepName, taskStepDescription);
			break;
		case InformationTaskStep:
			newTaskStep = new InformationTaskStep(taskStepName, taskStepDescription);
			break;
		case ReviewerTaskStep:
			newTaskStep = new ReviewerTaskStep(taskStepName, taskStepDescription);
			break;
		}
		return newTaskStep;
	}
}
