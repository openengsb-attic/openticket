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

package org.openengsb.openticket.config;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openengsb.core.api.workflow.RuleBaseException;
import org.openengsb.core.api.workflow.RuleManager;
import org.openengsb.core.api.workflow.TaskboxException;
import org.openengsb.core.api.workflow.model.RuleBaseElementId;
import org.openengsb.core.api.workflow.model.RuleBaseElementType;
import org.openengsb.core.security.BundleAuthenticationToken;
import org.openengsb.openticket.model.TicketType;
import org.openengsb.openticket.ui.web.panel.DeveloperTicketPanel;
import org.openengsb.openticket.ui.web.panel.ReviewerTicketPanel;
import org.openengsb.openticket.ui.web.panel.TaskFinalViewPanel;
import org.openengsb.ui.common.taskbox.WebTaskboxService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class OpenTicketConfigurator {
    private Log log = LogFactory.getLog(getClass());
    private AuthenticationManager authenticationManager;
    private RuleManager ruleManager;
    private WebTaskboxService webtaskboxService;

    public void init() {
        Authentication authentication =
            authenticationManager.authenticate(new BundleAuthenticationToken("openticket-app", ""));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (ruleManager.get(new RuleBaseElementId(RuleBaseElementType.Process, "TaskDemoWorkflow")) == null) {
            addWorkflows();
            registerPanels();
        }
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private void addWorkflows() {
        InputStream is = null;
        String testWorkflow;
        RuleBaseElementId id;
        try {
            log.info("about to load workflow 'TaskDemoWorkflow'");
            is = getClass().getClassLoader().getResourceAsStream("TaskDemoWorkflow.rf");
            testWorkflow = IOUtils.toString(is);
            id = new RuleBaseElementId(RuleBaseElementType.Process, "TaskDemoWorkflow");
            ruleManager.add(id, testWorkflow);
            log.info("loaded workflow 'TaskDemoWorkflow'");
        } catch (RuleBaseException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private void registerPanels() {
        try {
            webtaskboxService.registerTaskPanel(TicketType.DeveloperTicket.toString(), DeveloperTicketPanel.class);
            webtaskboxService.registerTaskPanel(TicketType.ReviewerTicket.toString(), ReviewerTicketPanel.class);
            webtaskboxService.registerTaskPanel("TaskFinalView", TaskFinalViewPanel.class);
        } catch (TaskboxException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setRuleManager(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    public void setWebtaskboxService(WebTaskboxService webtaskboxService) {
        this.webtaskboxService = webtaskboxService;
    }
}
