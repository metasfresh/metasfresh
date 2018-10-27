package de.metas.handlingunits.attribute.impl;

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
import java.util.Date;
import java.util.List;

import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.NullAttributeValueCallout;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IAttributeValueListener;
import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.propagation.impl.NullHUAttributePropagator;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.SkipHUAttributeTransferStrategy;

public final class NullAttributeValue implements IAttributeValue
{
	public static final NullAttributeValue instance = new NullAttributeValue();

	public static final boolean isNull(final IAttributeValue attributeValue)
	{
		return attributeValue == null || attributeValue == instance;
	}

	private NullAttributeValue()
	{
		super();
	}

	@Override
	public I_M_Attribute getM_Attribute()
	{
		return null;
	}

	@Override
	public String getAttributeValueType()
	{
		// NOTE: because by method's contract we are not allowed to return null, we are returning String
		return X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
	}

	@Override
	public void setValue(final IAttributeValueContext attributeValueContext, final Object value)
	{
		throw new UnsupportedOperationException("Setting value for " + this + " is not allowed");
	}

	@Override
	public void setValueInitial(final Object valueInitial)
	{
		throw new UnsupportedOperationException("Setting initial value for " + this + " is not allowed");
	}

	@Override
	public Object getValue()
	{
		return null;
	}

	/**
	 * @return <code>null</code>.
	 */
	@Override
	public Object getValueInitial()
	{
		return null;
	}

	@Override
	public BigDecimal getValueAsBigDecimal()
	{
		return null;
	}

	@Override
	public int getValueAsInt()
	{
		return 0;
	}

	@Override
	public String getValueAsString()
	{
		return null;
	}

	@Override
	public BigDecimal getValueInitialAsBigDecimal()
	{
		return null;
	}

	@Override
	public String getValueInitialAsString()
	{
		return null;
	}

	@Override
	public Date getValueAsDate()
	{
		return null;
	}

	@Override
	public Date getValueInitialAsDate()
	{
		return null;
	}

	@Override
	public boolean isNumericValue()
	{
		return false;
	}

	@Override
	public boolean isStringValue()
	{
		return false;
	}

	@Override
	public boolean isList()
	{
		return false;
	}

	@Override
	public boolean isDateValue()
	{
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}

	@Override
	public Object getEmptyValue()
	{
		return null;
	}

	@Override
	public String getPropagationType()
	{
		return NullHUAttributePropagator.instance.getPropagationType();
	}

	@Override
	public IAttributeAggregationStrategy retrieveAggregationStrategy()
	{
		return NullAggregationStrategy.instance;
	}

	@Override
	public IAttributeSplitterStrategy retrieveSplitterStrategy()
	{
		return NullSplitterStrategy.instance;
	}

	@Override
	public IHUAttributeTransferStrategy retrieveTransferStrategy()
	{
		return SkipHUAttributeTransferStrategy.instance;
	}

	@Override
	public boolean isUseInASI()
	{
		return false;
	}

	@Override
	public boolean isDefinedByTemplate()
	{
		return false;
	}

	@Override
	public void addAttributeValueListener(final IAttributeValueListener listener)
	{
		// nothing
	}

	@Override
	public List<ValueNamePair> getAvailableValues()
	{
		throw new InvalidAttributeValueException("method not supported for " + this);
	}

	@Override
	public IAttributeValuesProvider getAttributeValuesProvider()
	{
		throw new InvalidAttributeValueException("method not supported for " + this);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return null;
	}

	@Override
	public IAttributeValueCallout getAttributeValueCallout()
	{
		return NullAttributeValueCallout.instance;
	}

	@Override
	public IAttributeValueGenerator getAttributeValueGeneratorOrNull()
	{
		return null;
	}

	@Override
	public void removeAttributeValueListener(final IAttributeValueListener listener)
	{
		// nothing

	}

	@Override
	public boolean isReadonlyUI()
	{
		return true;
	}

	@Override
	public boolean isDisplayedUI()
	{
		return false;
	}

	@Override
	public boolean isMandatory()
	{
		return false;
	}

	@Override
	public int getDisplaySeqNo()
	{
		return 0;
	}

	@Override
	public NamePair getNullAttributeValue()
	{
		return null;
	}

	/**
	 * @return true; we consider Null attributes as always generated
	 */
	@Override
	public boolean isNew()
	{
		return true;
	}

	@Override
	public boolean isOnlyIfInProductAttributeSet()
	{
		return false;
	}
}
