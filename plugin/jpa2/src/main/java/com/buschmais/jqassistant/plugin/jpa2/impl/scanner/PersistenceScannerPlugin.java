package com.buschmais.jqassistant.plugin.jpa2.impl.scanner;

import static com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import static com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit.Properties.Property;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.buschmais.jqassistant.core.scanner.api.FileScannerPlugin;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.java.impl.store.descriptor.PropertyDescriptor;
import com.buschmais.jqassistant.plugin.java.impl.store.descriptor.TypeDescriptor;
import com.buschmais.jqassistant.plugin.java.impl.store.resolver.DescriptorResolverFactory;
import com.buschmais.jqassistant.plugin.jpa2.impl.store.descriptor.PersistenceDescriptor;
import com.buschmais.jqassistant.plugin.jpa2.impl.store.descriptor.PersistenceUnitDescriptor;
import com.sun.java.xml.ns.persistence.ObjectFactory;
import com.sun.java.xml.ns.persistence.Persistence;

/**
 * A scanner for JPA model units.
 */
public class PersistenceScannerPlugin implements FileScannerPlugin<PersistenceDescriptor> {

	private JAXBContext jaxbContext;

	/**
	 * Constructor.
	 */
	public PersistenceScannerPlugin() {
		try {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		} catch (JAXBException e) {
			throw new IllegalStateException("Cannot create JAXB context.", e);
		}
	}

	@Override
	public boolean matches(String file, boolean isDirectory) {
		return "META-INF/persistence.xml".equals(file) || "WEB-INF/model.xml".equals(file);
	}

	@Override
	public PersistenceDescriptor scanFile(Store store, StreamSource streamSource) throws IOException {
		DescriptorResolverFactory descriptorResolverFactory = new DescriptorResolverFactory(store);
		Persistence persistence;
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			persistence = unmarshaller.unmarshal(streamSource, Persistence.class).getValue();
		} catch (JAXBException e) {
			throw new IOException("Cannot read model descriptor.", e);
		}
		PersistenceDescriptor persistenceDescriptor = store.create(PersistenceDescriptor.class);
		persistenceDescriptor.setName(streamSource.getSystemId());
		persistenceDescriptor.setVersion(persistence.getVersion());
		// Create model units
		for (PersistenceUnit persistenceUnit : persistence.getPersistenceUnit()) {
			PersistenceUnitDescriptor persistenceUnitDescriptor = store.create(PersistenceUnitDescriptor.class);
			persistenceUnitDescriptor.setName(persistenceUnit.getName());
			persistenceUnitDescriptor.setDescription(persistenceUnit.getDescription());
			persistenceUnitDescriptor.setJtaDataSource(persistenceUnit.getJtaDataSource());
			persistenceUnitDescriptor.setNonJtaDataSource(persistenceUnit.getNonJtaDataSource());
			persistenceUnitDescriptor.setProvider(persistenceUnit.getProvider());
			persistenceUnitDescriptor.setValidationMode(persistenceUnit.getValidationMode().name());
			persistenceUnitDescriptor.setSharedCacheMode(persistenceUnit.getSharedCacheMode().name());
			for (String clazz : persistenceUnit.getClazz()) {
				TypeDescriptor typeDescriptor = descriptorResolverFactory.getTypeDescriptorResolver().resolve(clazz);
				persistenceUnitDescriptor.getContains().add(typeDescriptor);
			}
			// Create model unit properties
			for (Property property : persistenceUnit.getProperties().getProperty()) {
				PropertyDescriptor propertyDescriptor = store.create(PropertyDescriptor.class);
				propertyDescriptor.setName(property.getName());
				propertyDescriptor.setValue(property.getValue());
				persistenceUnitDescriptor.getProperties().add(propertyDescriptor);
			}
			// Add model unit to model descriptor
			persistenceDescriptor.getContains().add(persistenceUnitDescriptor);
		}
		return persistenceDescriptor;
	}

	@Override
	public PersistenceDescriptor scanDirectory(Store store, String name) throws IOException {
		return null;
	}
}
