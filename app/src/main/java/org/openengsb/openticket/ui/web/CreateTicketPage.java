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

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.common.taskbox.TaskboxException;
import org.openengsb.ui.common.wicket.taskbox.WebTaskboxService;
import org.openengsb.core.common.taskbox.model.Task;
import org.openengsb.openticket.ui.web.panel.CustomTaskPanel;
import org.openengsb.openticket.ui.web.panel.TicketPanel;
import org.openengsb.openticket.model.Ticket;

@AuthorizeInstantiation("CASEWORKER")
public class CreateTicketPage extends BasePage {

    public CreateTicketPage() {
        Task t = new Task();
        Panel p;
        p = new TicketPanel("panel",t);
        this.add(p);
    }
}
