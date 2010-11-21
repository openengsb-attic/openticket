/**

   Copyright 2010 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package org.openengsb.openticket.ui.web;

import java.util.UUID;

import org.openengsb.openticket.model.CompleteTicketInformationStep;
import org.openengsb.openticket.model.DeveloperTaskStep;
import org.openengsb.openticket.model.InformationTaskStep;
import org.openengsb.openticket.model.ReviewerTaskStep;
import org.openengsb.openticket.model.TaskStepType;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.ui.taskbox.model.WebTaskStep;

public class TicketServiceImpl implements TicketService {
	
	@Override
	public Ticket createEmptyTicket() {
		UUID uuid = UUID.randomUUID();
    	Ticket ticket = new Ticket("ID-" + uuid.toString());
    	
    	return ticket;
	}

	@Override
	public WebTaskStep createNewTaskStep(TaskStepType taskStepType, String taskStepName, String taskStepDescription) {
	    WebTaskStep newTaskStep = null;
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
