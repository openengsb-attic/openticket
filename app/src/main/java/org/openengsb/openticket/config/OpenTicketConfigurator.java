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
import org.openengsb.core.common.workflow.RuleBaseException;
import org.openengsb.core.common.workflow.RuleManager;
import org.openengsb.core.common.workflow.model.RuleBaseElementId;
import org.openengsb.core.common.workflow.model.RuleBaseElementType;

public class OpenTicketConfigurator {
    private Log log = LogFactory.getLog(getClass());

    private RuleManager ruleManager;

    public void init() {
        addWorkflows();
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

    public void setRuleManager(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }
}
