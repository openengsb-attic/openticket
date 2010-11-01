package org.openengsb.openticket.ui.web;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;

public abstract class AuthenticatedPageTest extends PageTest {
    @Before
    public void authSetup() {
        tester = new WicketTester(new WicketApplication() {
            @Override
            protected void init() {
                super.init();
                addComponentInstantiationListener(new SpringComponentInjector(this, appContext, false));
            }

            @Override
            public Class<? extends Page> getHomePage() {
                return WorkflowDemo.class;
            }

            @Override
            public Session newSession(Request request, Response response) {
                return new WicketSession(request);
            }

            @Override
            protected void addInjector() {
                addComponentInstantiationListener(new SpringComponentInjector(this, appContext, true));
            }
        });
    }
}
