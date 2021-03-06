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

package org.openengsb.openticket.ui.web.panel;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.api.workflow.TaskboxException;
import org.openengsb.core.api.workflow.TaskboxService;
import org.openengsb.core.api.workflow.model.Task;
import org.openengsb.ui.common.taskbox.WebTaskboxService;

@SuppressWarnings("serial")
public class OverviewTicketPanel extends Panel {

    private Panel panel = new EmptyPanel("taskPanel");

    @SpringBean(name="taskboxService")
    private TaskboxService taskboxService;
    @SpringBean(name="webtaskboxService")
    private WebTaskboxService webtaskboxService;

    public OverviewTicketPanel(String id) {
        super(id);
        //this.ticket = new Ticket(t);
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        add(panel);

        IModel<? extends List<Task>> taskModel = new LoadableDetachableModel<List<Task>>() {
            @Override
            protected List<Task> load() {
                List<Task> list = new ArrayList<Task>();
                list = taskboxService.getOpenTasks();
                if(list==null){
                    list = new ArrayList<Task>();
                }
                return list;
            }
        };
        add(new ListView<Task>("ticketList", taskModel) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                Link<Task> link = new Link<Task>("taskLink", item.getModel()) {
                    @Override
                    public void onClick() {
                        try {
                            Panel newPanel = webtaskboxService.getTaskPanel(getModelObject(), "taskPanel");
                            newPanel.setOutputMarkupId(true);
                            panel.replaceWith(newPanel);
                            panel = newPanel;
                        } catch (TaskboxException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                link.add(new Label("linkLabel", (item.getModelObject().getName()+"("+item.getModelObject().getTaskType()+")")));
                item.add(link);
                
            }
        });
        
    }
}
