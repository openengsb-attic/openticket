package org.openengsb.openticket.ui.web.gateway;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.openengsb.core.persistence.PersistenceException;
import org.openengsb.core.persistence.PersistenceManager;
import org.openengsb.core.persistence.PersistenceService;
import org.openengsb.openticket.ui.web.model.TestObject;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

public class PersistenceGateway implements BundleContextAware {
    @SpringBean
    private PersistenceManager manager;
    private PersistenceService persistence;
    private PersistenceService service;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        persistence = manager.getPersistenceForBundle(bundleContext.getBundle());
    }

    public PersistenceService getService() {
        return persistence;
    }

    public void saveTestObject(TestObject object) throws PersistenceException {
        service.create(object);
    }

    public TestObject readTestObject(TestObject object) {
        return service.query(object).get(0);
    }
}
