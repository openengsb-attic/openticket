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

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.openticket.ui.web.panel.OverviewTicketPanel;
import org.openengsb.ui.common.wicket.taskbox.WebTaskboxService;

public class OverviewTicketPage extends BasePage {
    @SpringBean(name = "webtaskboxService")
    private WebTaskboxService taskboxService;

    public OverviewTicketPage() {
        Panel p = new OverviewTicketPanel("OverviewPanel");
        this.add(p);
    }
}
