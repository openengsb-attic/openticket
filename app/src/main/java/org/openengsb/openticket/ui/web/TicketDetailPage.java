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

import java.util.Date;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.openticket.model.Ticket;
import org.openengsb.openticket.ui.web.gateway.PersistenceGateway;

@AuthorizeInstantiation("CASEWORKER")
public class TicketDetailPage extends BasePage {
    private Panel panel= new EmptyPanel("panel");
    
    @SpringBean
    private PersistenceGateway gateway;

    public TicketDetailPage(PageParameters parameters) {
        Ticket t=null;
        t =new Ticket(parameters.getString("ticket"));
        t.setContactEmailAddress(null);
        t.setCreationTimestamp(null);
        t.setCurrentTaskStep(null);
        t.setCustomer(null);
        t.setDescription(null);
        t.setPriority(null);
        t.setType(null);
        t.setHistory(null);
        t.setHistoryTaskSteps(null);
        t.setNotes(null);
        try{
            
            t = (Ticket) gateway.readObject(t);
            panel = t.getPanel("panel");
            panel.setOutputMarkupId(true);
        }catch(IllegalStateException e){
            e.printStackTrace();
        }
        add(panel);
    }
}
