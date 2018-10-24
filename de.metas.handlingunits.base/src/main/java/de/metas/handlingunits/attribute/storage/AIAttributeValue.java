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
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.attribute.impl.AbstractAttributeValue;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

/**
 * Wraps an {@link I_M_AttributeInstance}
 *
 * @author tsa
 *
 */
/* package */class AIAttributeValue extends AbstractAttributeValue
{
	private final I_M_AttributeInstance attributeInstance;

	public AIAttributeValue(
			final IAttributeStorage attributeStorage,
			final I_M_AttributeInstance attributeInstance)
	{
		super(
				attributeStorage,
				attributeInstance.getM_Attribute());

		this.attributeInstance = attributeInstance;
	}

	@Override
	protected void setInternalValueString(final String value)
	{
		attributeInstance.setValue(value);
	}

	@Override
	protected void setInternalValueNumber(final BigDecimal value)
	{
		attributeInstance.setValueNumber(value);
	}

	@Override
	protected String getInternalValueString()
	{
		return attributeInstance.getValue();
	}

	@Override
	protected BigDecimal getInternalValueNumber()
	{
		return attributeInstance.getValueNumber();
	}

	@Override
	protected String getInternalValueStringInitial()
	{
		return null;
	}

	/**
	 * @return <code>null</code>.
	 */
	@Override
	protected BigDecimal getInternalValueNumberInitial()
	{
		return null;
	}

	@Override
	protected void setInternalValueStringInitial(final String value)
	{
		throw new UnsupportedOperationException("Setting initial value not supported");
	}

	@Override
	protected void setInternalValueNumberInitial(final BigDecimal value)
	{
		throw new UnsupportedOperationException("Setting initial value not supported");
	}

	@Override
	public boolean isNew()
	{
		return InterfaceWrapperHelper.isNew(attributeInstance);
	}

	@Override
	protected void setInternalValueDate(Date value)
	{
		attributeInstance.setValueDate(TimeUtil.asTimestamp(value));
	}

	@Override
	protected Date getInternalValueDate()
	{
		return attributeInstance.getValueDate();
	}

	@Override
	protected void setInternalValueDateInitial(Date value)
	{
		throw new UnsupportedOperationException("Setting initial value not supported");
	}

	@Override
	protected Date getInternalValueDateInitial()
	{
		return null;
	}

	/**
	 * @return {@code PROPAGATIONTYPE_NoPropagation}.
	 */
	@Override
	public String getPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation;
	}

	/**
	 * @return {@link NullAggregationStrategy#instance}.
	 */
	@Override
	public IAttributeAggregationStrategy retrieveAggregationStrategy()
	{
		return NullAggregationStrategy.instance;
	}

	/**
	 * @return {@link NullSplitterStrategy#instance}.
	 */
	@Override
	public IAttributeSplitterStrategy retrieveSplitterStrategy()
	{
		return NullSplitterStrategy.instance;
	}

	/**
	 * @return {@link CopyHUAttributeTransferStrategy#instance}.
	 */
	@Override
	public IHUAttributeTransferStrategy retrieveTransferStrategy()
	{
		return CopyHUAttributeTransferStrategy.instance;
	}

	/**
	 * @return {@code true}.
	 */
	@Override
	public boolean isReadonlyUI()
	{
		return true;
	}

	/**
	 * @return {@code true}.
	 */
	@Override
	public boolean isDisplayedUI()
	{
		return true;
	}
	
	@Override
	public boolean isMandatory()
	{
		return false;
	}

	@Override
	public boolean isOnlyIfInProductAttributeSet()
	{
		return false;
	}

	/**
	 * @return our attribute instance's {@code M_Attribute_ID}.
	 */
	@Override
	public int getDisplaySeqNo()
	{
		return attributeInstance.getM_Attribute_ID();
	}

	/**
	 * @return {@code true}
	 */
	@Override
	public boolean isUseInASI()
	{
		return true;
	}

	/**
	 * @return {@code false}, since no HU-PI attribute is involved.
	 */
	@Override
	public boolean isDefinedByTemplate()
	{
		return false;
	}
}
