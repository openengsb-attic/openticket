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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openengsb.core.common.workflow.RuleBaseException;
import org.openengsb.core.common.workflow.RuleManager;
import org.openengsb.core.common.workflow.model.RuleBaseElementId;
import org.openengsb.core.common.workflow.model.RuleBaseElementType;
import org.springframework.security.authentication.AuthenticationManager;

public class OpenTicketConfiguratorTest {
    private OpenTicketConfigurator configurator;
    private RuleManager ruleManager;
    private AuthenticationManager authenticationManager;
    
    @Before
    public void setUp() {
        ruleManager = Mockito.mock(RuleManager.class);
        authenticationManager=Mockito.mock(AuthenticationManager.class);
        configurator = new OpenTicketConfigurator();
        configurator.setRuleManager(ruleManager);
        configurator.setAuthenticationManager(authenticationManager);
    }

    @Test
    public void testInit() throws RuleBaseException {
        configurator.init();
        RuleBaseElementId workflowId1 = new RuleBaseElementId(RuleBaseElementType.Process, "TaskDemoWorkflow");
        Mockito.verify(ruleManager).add(Mockito.eq(workflowId1), Mockito.anyString());
    }
}
