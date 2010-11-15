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

package org.openengsb.openticket.ui.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.openengsb.ui.taskbox.model.WebTaskStep;

public class TicketPanel extends Panel {

    private Panel taskPanel;
    private Panel currentTaskPanel;
    private List<Link> list = new ArrayList<Link>();
    private Map<String, Panel> panelMap = new HashMap<String, Panel>();
    private List<WebTaskStep> stepList = new ArrayList<WebTaskStep>();
    
    public TicketPanel(String id, Ticket ticket) {
        super(id);
        add(new Label("testoutput", "TicketId: "+ticket.getId()));
        WebTaskStep currentStep = ticket.getCurrentTaskStep();
        taskPanel = currentTaskPanel = currentStep.getPanel("taskPanel");
        taskPanel.setOutputMarkupId(true);
        currentTaskPanel.setOutputMarkupId(true);
        stepList = ticket.getHistoryTaskSteps();
        add(new Link<Object>("currentTask") {
            @Override
            public void onClick() {
                taskPanel.replaceWith(currentTaskPanel);
                taskPanel = currentTaskPanel;
            }
        });
        IModel<? extends List<? extends WebTaskStep>> stepModel = new LoadableDetachableModel<List<WebTaskStep>>() {
            @Override
            protected List<WebTaskStep> load() {
                return stepList;
            }
        };
        add(new ListView<WebTaskStep>("taskItems", stepModel) {
            @Override
            protected void populateItem(ListItem<WebTaskStep> item) {
                item.add(new Link<WebTaskStep>("taskStep", item.getModel()) {
                    @Override
                    public void onClick() {
                        Panel newPanel = getModelObject().getPanel("taskPanel");
                        newPanel.setOutputMarkupId(true);
                        taskPanel.replaceWith(newPanel);
                        taskPanel = newPanel;
                    }
                });
                item.add(new Label("taskLabel", item.getModelObject().getName()));
            }
        });
        add(currentTaskPanel);
    }
}
