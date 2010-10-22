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

import java.util.Locale;

import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

@AuthorizeInstantiation("ROLE_USER")
public class BasePage extends WebPage {
    public BasePage() {
        initWebPage();
    }

    private void initWebPage() {
        add(new Link<Object>("lang.en") {
            @Override
            public void onClick() {
                this.getSession().setLocale(Locale.ENGLISH);
            }
        });
        add(new Link<Object>("lang.de") {
            @Override
            public void onClick() {
                this.getSession().setLocale(Locale.GERMAN);
            }
        });
        add(new Link<Object>("logout") {
            @Override
            public void onClick() {
                ((AuthenticatedWebSession) this.getSession()).signOut();
                setResponsePage(LoginPage.class);
            }
        });
        
        add(new Label("helloWorld", "Hello World"));
        
        add(new HeaderTemplate("header", getHeaderMenuItem()));
        add(new FooterTemplate("footer"));
    }
    
    /**
     * @return the class name, which should be the index in navigation bar
     *
     */
    public String getHeaderMenuItem() {
        return this.getClass().getSimpleName();
    }
}
