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

import org.openengsb.core.api.context.ContextHolder;
import org.openengsb.ui.common.OpenEngSBPage;
import org.openengsb.ui.common.OpenEngSBWebSession;

public class BasePage extends OpenEngSBPage {

    public BasePage() {
        add(new HeaderTemplate("header", getHeaderMenuItem()));
        add(new FooterTemplate("footer"));
        initContextForCurrentThread();
    }

    public String getSessionContextId() {
        OpenEngSBWebSession session = OpenEngSBWebSession.get();
        if (session == null) {
            return "foo";
        }
        String contextId = ContextHolder.get().getCurrentContextId();
        if (contextId == null) {
            ContextHolder.get().setCurrentContextId("foo");
            return contextId;
        }
        return contextId;
    }

}
