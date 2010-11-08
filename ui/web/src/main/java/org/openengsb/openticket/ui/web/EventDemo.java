/**
 * Copyright 2010 OpenEngSB Division, Vienna University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.openticket.ui.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.persistence.PersistenceException;
import org.openengsb.core.taskbox.TaskboxException;
import org.openengsb.core.taskbox.TaskboxService;
import org.openengsb.core.taskbox.model.Ticket;
import org.openengsb.openticket.ui.web.model.TestObject;

@AuthorizeInstantiation("CASEWORKER")
public class EventDemo extends BasePage {
    @SpringBean
    private TaskboxService service;
    private Ticket ticket = (new TicketServiceImpl()).createEmptyTicket();
    
    public EventDemo() {

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);        
        
        Form form = new Form<TestObject>("form");
        form.setOutputMarkupId(true);
        add(form);     
     
        try {
            service.startWorkflow("eventtest", "task", ticket);
            add(new Label("testoutput", "gut"));
        } catch (TaskboxException e) {
            add(new Label("testoutput", e.getMessage()));
        }
        
        form.add(new AjaxButton("firstClick", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                /* send mist*/
                info("Value successfully written.");
                target.addComponent(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        });
        
        
        form.add(new AjaxButton("secondClick", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                /* send mist*/
                info("Value successfully written.");
                target.addComponent(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        });
        
        form.add(new AjaxButton("thirdClick", form)
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                
                info("Value successfully written.");
                target.addComponent(feedback);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        });
    }
    
    private void sendEvent(String event){
        
    }
}
