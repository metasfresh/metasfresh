package de.metas.handlingunits.attribute.storage.impl;

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

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.exceptions.AttributeNotFoundException;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.MutableHUTransactionAttribute;
import de.metas.product.ProductId;
import de.metas.uom.UOMType;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeSourceDocument;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.NamePair;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Null implementation. Has no children, not attributes, is never disposed nor virtual.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class NullAttributeStorage implements IAttributeStorage
{
	public static final IAttributeStorage instance = new NullAttributeStorage();

	public static boolean isNull(final IAttributeStorage attributeStorage)
	{
		return attributeStorage == null || attributeStorage == instance;
	}

	private NullAttributeStorage()
	{
	}

	@Override
	public String getId()
	{
		return "NULL";
	}

	@Override
	public List<IAttributeValue> getAttributeValues()
	{
		return Collections.emptyList();
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		return NullAttributeStorage.instance;
	}

	/**
	 * Always returns the empty list.
	 */
	@Override
	public Collection<IAttributeStorage> getChildAttributeStorages(final boolean loadIfNeeded_IGNORED)
	{
		return Collections.emptyList();
	}

	@Override
	public void addListener(final IAttributeStorageListener listener)
	{
		// do nothing instead of throwing exception because no attributes will change here anyway,
		// and also because if we throw an exception i think we will affect some BLs.
	}

	@Override
	public void removeListener(final IAttributeStorageListener listener)
	{
		// do nothing
		// by method contract, if the listener was not found, it shall silently ignore it.
	}

	@Override
	public void generateInitialAttributes(final Map<AttributeId, Object> defaultAttributesValue)
	{
		// do nothing
	}

	@Override
	public void updateHUTrxAttribute(final MutableHUTransactionAttribute huTrxAttribute, final IAttributeValue fromAttributeValue)
	{
		// we shall not reach this point because this storage is not firing any event so there is no point to come back and ask it to update your transaction
		throw new UnsupportedOperationException();
	}

	@Override
	public IAttributeValue getAttributeValue(final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public String getAttributeValueType(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public boolean hasAttribute(final AttributeCode attributeCode)
	{
		return false;
	}

	@Override
	public boolean hasAttribute(final AttributeId attributeId)
	{
		return false;
	}

	@Override
	public boolean hasAttribute(final @NonNull I_M_Attribute attribute)
	{
		return false;
	}

	@Nullable
	@Override
	public I_M_Attribute getAttributeByIdIfExists(final int attributeId)
	{
		// always null because there are no attributes
		return null;
	}

	@Override
	public Collection<I_M_Attribute> getAttributes()
	{
		return Collections.emptyList();
	}

	@Nullable
	@Override
	public I_M_Attribute getAttributeByValueKeyOrNull(final AttributeCode attributeCode)
	{
		// throw new AttributeNotFoundException(attributeCode, this);
		return null;
	}

	@Override
	public boolean isPropagatedValue(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public String getPropagationType(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public IAttributeAggregationStrategy retrieveAggregationStrategy(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public IAttributeSplitterStrategy retrieveSplitterStrategy(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public IHUAttributeTransferStrategy retrieveTransferStrategy(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public void setValueNoPropagate(final AttributeCode attributeCode, final Object value)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public void setValue(final AttributeCode attributeCode, final Object value)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public void setValue(final AttributeId attributeId, final Object value)
	{
		throw new AttributeNotFoundException(attributeId, this);
	}

	@Override
	public NamePair setValueToNull(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public Object getValue(final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public String getValueAsString(@NonNull final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public String getValueAsStringOrNull(@NonNull final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public BigDecimal getValueAsBigDecimal(final @NonNull AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public int getValueAsInt(final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public Date getValueAsDate(final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public String getValueName(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public Object getValueInitial(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public BigDecimal getValueInitialAsBigDecimal(final AttributeCode attributeCode)
	{
		throw new AttributeNotFoundException(attributeCode, this);
	}

	@Override
	public IAttributeValueCallout getAttributeValueCallout(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public void onChildAttributeStorageAdded(final IAttributeStorage childAttributeStorage)
	{
		throw new HUException("Null storage cannot have children");
	}

	@Override
	public void onChildAttributeStorageRemoved(final IAttributeStorage childAttributeStorageRemoved)
	{
		throw new HUException("Null storage cannot have children");
	}

	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	@Override
	public boolean isDisplayedUI(final I_M_Attribute attribute, final Set<ProductId> productIds)
	{
		return false;
	}

	@Override
	public boolean isMandatory(final @NonNull I_M_Attribute attribute, final @NonNull Set<ProductId> productIds, final @Nullable AttributeSourceDocument attributeSourceDocument)
	{
		return false;
	}

	@Override
	public void pushUp()
	{
		// nothing
	}

	@Override
	public void pushUpRollback()
	{
		// nothing
	}

	@Override
	public void pushDown()
	{
		// nothing
	}

	@Override
	public void saveChangesIfNeeded()
	{
		// nothing
		// NOTE: not throwing UnsupportedOperationException because this storage contains no attributes so it will never have something to change
	}

	@Override
	public void setSaveOnChange(final boolean saveOnChange)
	{
		// nothing
		// NOTE: not throwing UnsupportedOperationException because this storage contains no attributes so it will never have something to change
	}

	@Override
	public IAttributeStorageFactory getAttributeStorageFactory()
	{
		throw new UnsupportedOperationException();
	}

	@Nullable
	@Override
	public UOMType getQtyUOMTypeOrNull()
	{
		// no UOMType available
		return null;
	}

	@Override
	public String toString()
	{
		return "NullAttributeStorage []";
	}

	@Override
	public BigDecimal getStorageQtyOrZERO()
	{
		// no storage quantity available; assume ZERO
		return BigDecimal.ZERO;
	}

	/**
	 * @return <code>false</code>.
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}

	@Override
	public boolean isNew(final I_M_Attribute attribute)
	{
		throw new AttributeNotFoundException(attribute, this);
	}

	/**
	 * @return true, i.e. never disposed
	 */
	@Override
	public boolean assertNotDisposed()
	{
		return true; // not disposed
	}

	@Override
	public boolean assertNotDisposedTree()
	{
		return true; // not disposed
	}
}
