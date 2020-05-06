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

import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

/**
 * Plain (database decoupled) {@link IAttributeValue} implementation
 *
 * @author tsa
 *
 */
public class PlainAttributeValue extends AbstractAttributeValue
{
	private String valueStr;
	private BigDecimal valueBD;
	private Date valueDate;
	private String valueInitialStr;
	private BigDecimal valueInitialBD;
	private Date valueInitialDate;

	private final String propagationType;
	private final boolean isGeneratedAttribute = false;

	private final IAttributeAggregationStrategy aggregationStrategy;
	private final IAttributeSplitterStrategy splitterStrategy;
	private final CopyHUAttributeTransferStrategy transferStrategy;

	public PlainAttributeValue(final IAttributeStorage attributeStorage, final I_M_Attribute attribute)
	{
		super(attributeStorage, attribute);

		propagationType = X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation;

		aggregationStrategy = NullAggregationStrategy.instance;
		splitterStrategy = NullSplitterStrategy.instance;
		transferStrategy = CopyHUAttributeTransferStrategy.instance;
	}

	public PlainAttributeValue(
			final IAttributeStorage attributeStorage,
			final I_M_Attribute attribute,
			// Propagation-specific attributes
			final String propagationType,
			final IAttributeAggregationStrategy aggregationStrategy,
			final IAttributeSplitterStrategy splitterStrategy)
	{
		super(attributeStorage, attribute);

		this.propagationType = propagationType;

		this.aggregationStrategy = aggregationStrategy;
		this.splitterStrategy = splitterStrategy;
		transferStrategy = CopyHUAttributeTransferStrategy.instance;
	}

	public PlainAttributeValue copy() throws InstantiationException, IllegalAccessException
	{
		final PlainAttributeValue attributeValueNew = new PlainAttributeValue(getAttributeStorage(),
				getM_Attribute(),
				propagationType,
				aggregationStrategy.getClass(),
				splitterStrategy.getClass());
		attributeValueNew.valueBD = valueBD;
		attributeValueNew.valueStr = valueStr;
		attributeValueNew.valueDate = valueDate;
		attributeValueNew.valueInitialBD = valueInitialBD;
		attributeValueNew.valueInitialStr = valueInitialStr;
		attributeValueNew.valueInitialDate = valueInitialDate;
		return attributeValueNew;
	}

	/* package */ PlainAttributeValue(final IAttributeStorage attributeStorage,
			final I_M_Attribute attribute,
			// Propagation-specific attributes
			final String propagationType, final Class<? extends IAttributeAggregationStrategy> aggregationStrategy, final Class<? extends IAttributeSplitterStrategy> splitterStrategy)
			throws InstantiationException, IllegalAccessException
	{
		this(attributeStorage, attribute, propagationType, aggregationStrategy.newInstance(), splitterStrategy.newInstance());
	}

	@Override
	protected void setInternalValueString(final String value)
	{
		valueStr = value;
	}

	@Override
	protected void setInternalValueNumber(final BigDecimal value)
	{
		valueBD = value;
	}

	@Override
	protected String getInternalValueString()
	{
		return valueStr;
	}

	@Override
	protected BigDecimal getInternalValueNumber()
	{
		return valueBD;
	}

	@Override
	protected String getInternalValueStringInitial()
	{
		return valueInitialStr;
	}

	@Override
	protected BigDecimal getInternalValueNumberInitial()
	{
		return valueInitialBD;
	}

	@Override
	protected void setInternalValueStringInitial(final String value)
	{
		valueInitialStr = value;
	}

	@Override
	protected void setInternalValueNumberInitial(final BigDecimal value)
	{
		valueInitialBD = value;
	}

	@Override
	public String getPropagationType()
	{
		return propagationType;
	}

	@Override
	public IAttributeAggregationStrategy retrieveAggregationStrategy()
	{
		return aggregationStrategy;
	}

	@Override
	public IAttributeSplitterStrategy retrieveSplitterStrategy()
	{
		return splitterStrategy;
	}

	@Override
	public IHUAttributeTransferStrategy retrieveTransferStrategy()
	{
		return transferStrategy;
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
	public int getDisplaySeqNo()
	{
		return 0;
	}

	@Override
	public boolean isReadonlyUI()
	{
		return false;
	}

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
	public boolean isNew()
	{
		return isGeneratedAttribute;
	}

	@Override
	protected void setInternalValueDate(Date value)
	{
		this.valueDate = value;
	}

	@Override
	protected Date getInternalValueDate()
	{
		return valueDate;
	}

	@Override
	protected void setInternalValueDateInitial(Date value)
	{
		this.valueInitialDate = value;
	}

	@Override
	protected Date getInternalValueDateInitial()
	{
		return valueInitialDate;
	}

	@Override
	public boolean isOnlyIfInProductAttributeSet()
	{
		return false;
	}
}
