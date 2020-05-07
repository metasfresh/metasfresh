package de.metas.handlingunits.attribute.storage;

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.util.ISingletonService;

public interface IAttributeStorageFactoryService extends ISingletonService
{
	/**
	 * Add {@link IAttributeStorageListener} to be added to
	 * all {@link IAttributeStorageFactory}s and {@link IAttributeStorage}s that are created by this instance.
	 */
	void addAttributeStorageListener(IAttributeStorageListener listener);

	/** Create a "standard" attribute storage factory, ready to use. */
	IAttributeStorageFactory createHUAttributeStorageFactory();

	/**
	 * Calls {@link #createHUAttributeStorageFactory(IHUAttributesDAO)} with the default {@link IHUAttributesDAO} implementation (no decoupled or on-commit saves).
	 * <p>
	 */
	IAttributeStorageFactory createHUAttributeStorageFactory(IHUStorageFactory huStorageFactory);

	IAttributeStorageFactory createHUAttributeStorageFactory(
			IHUStorageFactory huStorageFactory,
			IHUAttributesDAO huAttributesDAO);

	/**
	 * Creates a {@link IAttributeStorageFactory} which still lacks a {@link IHUStorageFactory}.<br>
	 * The returned factory is not usable unless that huStorage factory was also set via {@link IAttributeStorageFactory#setHUStorageFactory(IHUStorageFactory)}.
	 */
	IAttributeStorageFactory prepareHUAttributeStorageFactory(IHUAttributesDAO huAttributesDAO);

	void addAttributeStorageFactory(Class<? extends IAttributeStorageFactory> attributeStorageFactoryClass);
}
