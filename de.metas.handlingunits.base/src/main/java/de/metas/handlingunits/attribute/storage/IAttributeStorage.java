package de.metas.handlingunits.attribute.storage;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.exceptions.AttributeNotFoundException;
import de.metas.handlingunits.attribute.propagation.impl.NullHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.impl.AbstractAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.CompositeAttributeStorageListener;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.product.ProductId;
import lombok.NonNull;

/**
 * Defines a Attribute Storage pool. Use e.g. {@link IAttributeStorageFactory#getAttributeStorage(Object)} do get an instance.
 */
public interface IAttributeStorage extends IAttributeSet
{
	static IAttributeStorage cast(final IAttributeSet attributeSet)
	{
		return (IAttributeStorage)attributeSet;
	}

	/**
	 * Get's storage unique identifier.
	 *
	 * This identifier is used to uniquely identify an {@link IAttributeStorage} in children {@link IAttributeStorage} collection (internally).
	 */
	String getId();

	/**
	 * Register a storage listener which will be called when an attribute value is added, changed or deleted.
	 *
	 * NOTEs:
	 * <ul>
	 * <li>listener is registered using {@link CompositeAttributeStorageListener}.
	 * This might mean that it's stored as a weak reference. so make sure you keep a reference to this listener in our object.</li>
	 * <li>a listener won't be registered twice</li>
	 * </ul>
	 *
	 * @param listener
	 */
	void addListener(IAttributeStorageListener listener);

	/**
	 * Unregisters given storage listener.
	 *
	 * If listener was not already registered, it will silently ignore it.
	 *
	 * @param listener
	 */
	void removeListener(IAttributeStorageListener listener);

	/**
	 * @return {@link IAttributeValue}s contained in this storage
	 */
	List<IAttributeValue> getAttributeValues();

	/**
	 * @return {@link IAttributeValue} for the current attribute set
	 * @throws AttributeNotFoundException if attribute was not found
	 */
	IAttributeValue getAttributeValue(String attributeKey);

	default IAttributeValue getAttributeValue(@NonNull final I_M_Attribute attribute)
	{
		return getAttributeValue(attribute.getValue());
	}

	/**
	 * @param attribute
	 * @return true if the given attribute is available for getting/setting
	 */
	@Override
	boolean hasAttribute(String attributeKey);

	/**
	 *
	 * @return all {@link I_M_Attribute}s supported by this storage
	 */
	@Override
	Collection<I_M_Attribute> getAttributes();

	/**
	 *
	 * @param attributeValueKey
	 * @return attribute for given M_Attribute.Value or <code>null</code>
	 */
	I_M_Attribute getAttributeByValueKeyOrNull(String attributeValueKey);

	/**
	 * @return {@link IAttributeStorage} parent attribute storage; never return null
	 */
	IAttributeStorage getParentAttributeStorage();

	/**
	 * @param loadIfNeeded if <code>false</code> and the children were not loaded yet, then return an empty collection.
	 * @return collection of child attribute storages
	 */
	Collection<IAttributeStorage> getChildAttributeStorages(boolean loadIfNeeded);

	/**
	 * Generate initial storage attributes
	 *
	 * @param defaultAttributesValue
	 * @throws AdempiereException if attributes were already generated
	 */
	void generateInitialAttributes(final Map<AttributeId, Object> defaultAttributesValue);

	/**
	 * Updates given <code>huTrxAttribute</code> with storage settings and with underlying infos from <code>fromAttributeValue</code>.
	 *
	 * @param huTrxAttribute
	 * @param fromAttributeValue
	 */
	void updateHUTrxAttribute(IMutableHUTransactionAttribute huTrxAttribute, IAttributeValue fromAttributeValue);

	/**
	 * Check if this is a propagated value and not a value we can set it directly.
	 *
	 * This method checks if this action is possible in current context (i.e. it is also checking current parent and current child storages).
	 *
	 * @param attribute
	 * @return true if is propagated value
	 */
	boolean isPropagatedValue(I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return propagation type of given attribute or {@link NullHUAttributePropagator#getPropagationType()} if attribute was not found
	 * @see X_M_HU_PI_Attribute.PROPAGATIONTYPE_
	 */
	String getPropagationType(I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return {@link IAttributeAggregationStrategy} the linked aggregation strategy
	 */
	IAttributeAggregationStrategy retrieveAggregationStrategy(I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return {@link IAttributeSplitterStrategy} the linked splitter strategy
	 */
	IAttributeSplitterStrategy retrieveSplitterStrategy(I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return {@link IHUAttributeTransferStrategy} the linked transfer strategy which will be used to transfer an attribute from another {@link IAttributeStorage}.
	 */
	IHUAttributeTransferStrategy retrieveTransferStrategy(I_M_Attribute attribute);

	/**
	 *
	 * @param ctx evaluation context
	 * @param attribute
	 * @return true if given attribute is readonly for user
	 */
	boolean isReadonlyUI(final IAttributeValueContext ctx, I_M_Attribute attribute);

	boolean isDisplayedUI(final ImmutableSet<ProductId> productIDs, final I_M_Attribute attribute);

	default boolean isMandatory(@NonNull final I_M_Attribute attribute)
	{
		return getAttributeValue(attribute.getValue()).isMandatory();
	}

	/**
	 * Set attribute's value with NO propagation.
	 *
	 * @param attribute
	 * @param value
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	void setValueNoPropagate(I_M_Attribute attribute, Object value);

	/**
	 * Set attribute's value and propagate to its parent/child attribute storages using it's <b>default attribute propagator</b>. When the value is propagated and <code>this</code> instance already
	 * has an internal propagation context, then that context is cloned (with the clone having the original context as <code>parent</code>).
	 * <p>
	 * Note: not only the actual propagation, but also the set-invocation to <code>this</code> storage is the propagator's job.
	 *
	 *
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 *
	 */
	@Override
	void setValue(String attributeKey, Object value);

	/**
	 * Sets attribute's value to "null".
	 *
	 * Value to be used for null is determined from {@link IAttributeValue#getNullAttributeValue()}.
	 *
	 * @param attribute
	 * @return null value that was set
	 */
	NamePair setValueToNull(I_M_Attribute attribute);

	/**
	 * @return value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	@Override
	Object getValue(String attributeKey);

	@Override
	BigDecimal getValueAsBigDecimal(String attributeKey);

	@Override
	Date getValueAsDate(String attributeKey);

	@Override
	String getValueAsString(String attributeKey);

	/**
	 * @param attribute
	 * @return name of given attribute current value (aka. valueName)
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported (inherited from {@link #getValue(I_M_Attribute)})
	 */
	String getValueName(I_M_Attribute attribute);

	/**
	 * Returns the initial attribute value. Currently only used for the aggregation that takes place during bottom-up propagation.
	 *
	 * @param attribute
	 * @return initial value (seed value) of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	Object getValueInitial(I_M_Attribute attribute);

	/**
	 * Same as {@link #getValueInitial(I_M_Attribute)} but returns a {@link BigDecimal}.
	 *
	 * @see #getValueInitial(I_M_Attribute)
	 */
	BigDecimal getValueInitialAsBigDecimal(I_M_Attribute attribute);

	@Override
	IAttributeValueCallout getAttributeValueCallout(I_M_Attribute attribute);

	/**
	 * Method called by API when a child storage was added (e.g. a child HU was included to the HU that owns this storage).
	 *
	 * NOTE: don't call it directly, the API will make the call, e.g. if a child HU was added to aparent HU.
	 *
	 * @param childAttributeStorage
	 */
	void onChildAttributeStorageAdded(IAttributeStorage childAttributeStorage);

	/**
	 * Method called by API when a child storage was removed (e.g. an HU was taken out from HU that owns this storage)
	 *
	 * NOTE: don't call it directly, the API will.
	 *
	 * @param childAttributeStorageRemoved
	 */
	void onChildAttributeStorageRemoved(IAttributeStorage childAttributeStorageRemoved);

	/**
	 * Push up attributes: force propagating attributes from this storage to its parent's storages. Propagates all <code>this</code> storage's attribute values using
	 * {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_BottomUp}.
	 *
	 * NOTE: don't call it directly. It's called by API ({@link #onChildAttributeStorageAdded(IAttributeStorage)}) only
	 */
	void pushUp();

	/**
	 * Push up attributes. Compared with {@link #pushUp()} this method will push <code>null</code> values, but will not modify this storage itself.<br>
	 * Mainly this method is called before a storage is removed from it's parent.<br>
	 *
	 * NOTE: don't call it directly. It's called by API only
	 */
	void pushUpRollback();

	/**
	 * Push down attributes: force propagating attributes from this storage to its child-storages. Propagates all <code>this</code> storage's attribute values using
	 * {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_TopDown}.
	 *
	 * NOTE: don't call it directly. It's called by API only
	 */
	void pushDown();

	/**
	 * Save attributes value changes.
	 *
	 * If changes will be saved or not depends on implementation.
	 */
	void saveChangesIfNeeded();

	/**
	 * Enables/Disables automatic saving when an attribute value is changed
	 *
	 * @param saveOnChange
	 * @throws UnsupportedOperationException in case the operation is not supported
	 */
	void setSaveOnChange(final boolean saveOnChange);

	IAttributeStorageFactory getAttributeStorageFactory();

	/**
	 * In case this attribute storage is attached to an {@link IHUStorageDAO} which supports product Qtys, it will return the UOM Type of that Qty Storage.
	 *
	 * @return Qty Storage's UOM Type or null
	 */
	String getQtyUOMTypeOrNull();

	/**
	 * @return storage quantity that this attribute storage is bound to
	 */
	BigDecimal getStorageQtyOrZERO();

	/**
	 * @return true if attribute storage is considered virtual or attached to a virtual object (i.e a VHU)
	 */
	boolean isVirtual();

	/**
	 * Assert this storage was not disposed.
	 *
	 * NOTEs to implementors:
	 * <ul>
	 * <li>Method called before any method which is accessing or changing the data from this attribute storage.
	 * To be implemented on higher level if you want to implement destroy/dispose functionality and you want to make sure the storage is not used after that.
	 * If you don't care, just return <code>true</code>.</li>
	 * <li>in your implementation, don't check if possible child storages are disposed, because for that we have {@link #assertNotDisposedTree()}</li>
	 * <li>please have a look at {@link AbstractAttributeStorage#throwOrLogDisposedException(Long)} and use it if your particular instance is disposed.</li>
	 * </ul>
	 *
	 * @return <code>true</code> if NOT disposed (i.e. still alive); <code>false</code> if IT IS disposed
	 * @throws HUException if it's disposed (but this depends on implementation, see {@link HUConstants#isAttributeStorageFailOnDisposed()}).
	 */
	boolean assertNotDisposed();

	/**
	 * Assert that neither this storage nor any of its children are disposed. This method does <b>not</b> load any children, but just validates existing ones.
	 *
	 * @return <code>true</code> if no storage is disposed.
	 * @throws HUException if any storage is disposed
	 */
	boolean assertNotDisposedTree();

	/**
	 * @return warehouse on which this attribute storage sits (e.g. in case of a HU, it's the HU's warehouse).
	 */
	default int getM_Warehouse_ID()
	{
		return -1;
	}
}
