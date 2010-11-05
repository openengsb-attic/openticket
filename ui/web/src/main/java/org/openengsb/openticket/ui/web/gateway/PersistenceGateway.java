package org.openengsb.openticket.ui.web.gateway;

import java.util.List;

import org.openengsb.core.persistence.PersistenceException;
import org.openengsb.core.persistence.PersistenceManager;
import org.openengsb.core.persistence.PersistenceService;
import org.openengsb.openticket.ui.web.model.TestObject;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

public class PersistenceGateway implements BundleContextAware {
    private PersistenceManager persistenceManager;
    private PersistenceService service;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        service = persistenceManager.getPersistenceForBundle(bundleContext.getBundle());
    }

    public void saveTestObject(TestObject object) throws PersistenceException {
        service.create(object);
    }

    public TestObject readTestObject(TestObject object) throws IllegalStateException {
        List<TestObject> objects = service.query(object);

        if (objects.size() == 0) {
            throw new IllegalStateException();
        }
        return objects.get(objects.size() - 1);
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
