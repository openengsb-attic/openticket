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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebSession;

public class HeaderTemplate extends Panel {
    private final ArrayList<HeaderMenuItem> menuItems = new ArrayList<HeaderMenuItem>();
    private final ArrayList<String> avialableItems = new ArrayList<String>();

    private static String menuIndex;

    public HeaderTemplate(String id, String menuIndex) {
        super(id);

        baseInitialization(menuIndex);
        initializeMenu();
    }

    private void baseInitialization(String menuIndex) {
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

        Link<Object> link = new Link<Object>("logout") {
            @Override
            public void onClick() {
                boolean signedIn = ((WicketSession) WebSession.get()).isSignedIn();
                if (signedIn) {
                    ((AuthenticatedWebSession) this.getSession()).signOut();
                }
                setResponsePage(signedIn ? Welcome.class : LoginPage.class);
            }
        };
        add(link);

        WebMarkupContainer container = new WebMarkupContainer("logintext");
        link.add(container);
        try {
            container.setVisible(!((WicketSession) WebSession.get()).isSignedIn());
        } catch (ClassCastException e) {
        }
        container = new WebMarkupContainer("logouttext");
        link.add(container);
        try {
            container.setVisible(((WicketSession) WebSession.get()).isSignedIn());
        } catch (ClassCastException e) {
        }

        HeaderTemplate.menuIndex = menuIndex;
        add(new Label("version", System.getProperty("openticket.version.number")));
    }

    private void initMainMenuItems() {
        addHeaderMenuItem("Home", Welcome.class, "index.title");

        try {
            Roles roles = ((WicketSession) WebSession.get()).getRoles();
            for (String role : roles) {
                if (role.equals("CASEWORKER")) {
                    addHeaderMenuItem("TaskboxDemo", TaskboxDemo.class, "taskboxdemo.title");
                    addHeaderMenuItem("PanelDemo", PanelDemo.class, "paneldemo.title");
                    addHeaderMenuItem("OverviewDemo", OverviewDemo.class, "overviewdemo.title");
                }
            }
        } catch (ClassCastException e) {
            addHeaderMenuItem("TasboxDemo", TaskboxDemo.class, "taskboxdemo.title");
            addHeaderMenuItem("PanelDemo", PanelDemo.class, "paneldemo.title");
        }
    }

    private void initializeMenu() {
        initMainMenuItems();

        if (HeaderTemplate.getActiveIndex() == null || !avialableItems.contains(HeaderTemplate.getActiveIndex())) {
            // update menu item to index, because page index is not found!
            HeaderTemplate.menuIndex = "Home";
        }

        // generate main navigation
        ListView<HeaderMenuItem> headerMenuItems = new ListView<HeaderMenuItem>("headerMenuItems", menuItems) {
            @Override
            protected void populateItem(ListItem<HeaderMenuItem> item) {
                HeaderMenuItem menuItem = item.getModelObject();
                item.add(menuItem.getLink());

                // set menu item to active
                if (menuItem.getItemName().equals(HeaderTemplate.getActiveIndex())) {
                    item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel<String>() {
                        @Override
                        public String getObject() {
                            return "active";
                        }
                    }));
                }
            }
        };

        add(headerMenuItems);
    }

    /**
     * @returns the name of the current active menu item
     */
    public static String getActiveIndex() {
        return HeaderTemplate.menuIndex;
    }

    /**
     * adds new item to main header navigation
     * 
     * @param index - the name of the index @see HeaderMenuItem.index
     * @param linkClass - class name to be linked to
     * @param langKey - language key, the text which should be displayed
     */
    @SuppressWarnings("unchecked")
    public void addHeaderMenuItem(String index, Class<? extends WebPage> linkClass, String langKey) {
        StringResourceModel label = new StringResourceModel(langKey, this, null);
        menuItems.add(new HeaderMenuItem(index, new BookmarkablePageLabelLink("link", linkClass, label)));
        avialableItems.add(index);
    }

    /**
     * single header menu item
     */
    private static class HeaderMenuItem implements Serializable {
        private final String index;
        private final BookmarkablePageLabelLink<? extends WebPage> link;

        public HeaderMenuItem(String index, BookmarkablePageLabelLink<? extends WebPage> link) {
            this.index = index;
            this.link = link;
        }

        public String getItemName() {
            return index;
        }

        public BookmarkablePageLabelLink<? extends WebPage> getLink() {
            return link;
        }
    }
}
