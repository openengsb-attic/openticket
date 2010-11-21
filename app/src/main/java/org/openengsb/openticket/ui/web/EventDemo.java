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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.common.Event;
import org.openengsb.core.common.context.ContextCurrentService;
import org.openengsb.core.common.taskbox.TaskboxException;
import org.openengsb.core.common.taskbox.TaskboxService;
import org.openengsb.core.common.workflow.WorkflowException;
import org.openengsb.openticket.model.Ticket;

@AuthorizeInstantiation("CASEWORKER")
public class EventDemo extends BasePage {
    @SpringBean
    private TaskboxService service;

    @SpringBean
    private ContextCurrentService ccservice;

    private Ticket ticket = new Ticket("");

    public EventDemo() {

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        final String context = ccservice.getThreadLocalContext();
        Form<Object> form = new Form<Object>("form");
        form.setOutputMarkupId(true);
        add(form);

        try {
            service.startWorkflow("eventtest", "task", ticket);

            form.add(new AjaxButton("firstClick", form)
            {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    try {
                        ccservice.setThreadLocalContext(context);
                        service.processEvent(new Event() {
                            @Override
                            public String getType() {
                                return "FirstClick";
                            }
                        });
                        info("FirstClick send!");
                        target.addComponent(feedback);
                    } catch (WorkflowException e) {
                        info(e.getMessage());
                    }
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
                    try {
                        ccservice.setThreadLocalContext(context);
                        service.processEvent(new Event() {
                            @Override
                            public String getType() {
                                return "SecondClick";
                            }
                        });

                        info("SecondClick send!");
                        target.addComponent(feedback);
                    } catch (WorkflowException e) {
                        info(e.getMessage());
                    }
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
                    try {
                        ccservice.setThreadLocalContext(context);
                        service.processEvent(new Event() {
                            @Override
                            public String getType() {
                                return "ThirdClick";
                            }
                        });

                        info("ThirdClick send.");
                        target.addComponent(feedback);
                    } catch (WorkflowException e) {
                        info(e.getMessage());
                    }
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    target.addComponent(feedback);
                }
            });
        } catch (TaskboxException e) {

        }
    }

}
