package org.openengsb.openticket.ui.web;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;

public abstract class NonAuthenticatedPageTest extends PageTest {
    @Before
    public void authSetup() {
        tester = new WicketTester(new WebApplication() {
            @Override
            protected void init() {
                super.init();
                addComponentInstantiationListener(new SpringComponentInjector(this, appContext, false));
            }

            @Override
            public Class<? extends Page> getHomePage() {
                return TaskboxDemo.class;
            }

            @Override
            public Session newSession(Request request, Response response) {
                return new WicketSession(request);
            }
        });
    }
}
