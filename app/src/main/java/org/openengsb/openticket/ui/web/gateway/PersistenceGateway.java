package org.openengsb.openticket.ui.web.gateway;

import java.util.List;

import org.openengsb.core.common.persistence.PersistenceException;
import org.openengsb.core.common.persistence.PersistenceManager;
import org.openengsb.core.common.persistence.PersistenceService;
import org.openengsb.openticket.model.DeveloperTaskStep;
import org.openengsb.openticket.model.TestObject;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

public class PersistenceGateway implements BundleContextAware {
    private PersistenceManager persistenceManager;
    private PersistenceService service;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        service = persistenceManager.getPersistenceForBundle(bundleContext.getBundle());
    }

    public void saveObject(Object object) throws PersistenceException {
        service.create(object);
    }

    public Object readObject(Object object) throws IllegalStateException {
        List<Object> objects = service.query(object);

        if (objects.size() == 0) {
            throw new IllegalStateException();
        }
        return objects.get(objects.size() - 1);
    }
    
    public void saveDeveloperTaskStep(DeveloperTaskStep object) throws PersistenceException {
        service.create(object);
    }

    public DeveloperTaskStep readDeveloperTaskStep(DeveloperTaskStep object) throws IllegalStateException {
        List<DeveloperTaskStep> objects = service.query(object);

        if (objects.size() == 0) {
            throw new IllegalStateException();
        }
        return objects.get(objects.size() - 1);
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
