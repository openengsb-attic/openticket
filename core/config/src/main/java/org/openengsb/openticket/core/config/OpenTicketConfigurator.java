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

package org.openengsb.openticket.core.config;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.openengsb.core.taskbox.TaskboxService;
import org.openengsb.core.workflow.RuleBaseException;
import org.openengsb.core.workflow.RuleManager;
import org.openengsb.core.workflow.model.RuleBaseElementId;
import org.openengsb.core.workflow.model.RuleBaseElementType;

public class OpenTicketConfigurator {
    private RuleManager ruleManager;

    public void init() {
        addGlobalsAndImports();
        addWorkflow();
    }

    private void addGlobalsAndImports() {
        try {
            ruleManager.addGlobal(TaskboxService.class.getCanonicalName(), "taskbox");
        } catch (RuleBaseException e) {
            throw new RuntimeException(e);
        }
    }

    private void addWorkflow() {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream("tasktest.rf");
            String testWorkflow = IOUtils.toString(is);
            RuleBaseElementId id = new RuleBaseElementId(RuleBaseElementType.Process, "tasktest");
            ruleManager.add(id, testWorkflow);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public void setRuleManager(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }
}
