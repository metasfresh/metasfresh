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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.mm.attributes.api.IHUBestBeforeBL;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.impl.AbstractHUAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;

/**
 * Wraps the {@link I_M_AttributeSetInstance#COLUMN_GuaranteeDate} and behaves like an {@link IAttributeValue}.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/09363_Best-before_management_%28Mindesthaltbarkeit%29_%28108375354495%29
 */
class BestBeforeASIAttributeValue extends AbstractHUAttributeValue
{
	/**
	 * Creates the {@link IAttributeValue} instance of given ASI, if applies.
	 * 
	 * @param attributeStorage
	 * @param asi
	 * @param piAttribute
	 * @return {@link IAttributeValue} instance or null if it does not apply.
	 */
	public static BestBeforeASIAttributeValue createIfApplies(final IAttributeStorage attributeStorage,
			final I_M_AttributeSetInstance asi,
			final I_M_HU_PI_Attribute piAttribute)
	{

		//
		// Make sure the attribute from PI Attribute is the Best-Before attribute.
		// If not, return null because it does not apply.
		final IHUBestBeforeBL bestBeforeBL = Services.get(IHUBestBeforeBL.class);
		final I_M_Attribute attr_BestBeforeDate = bestBeforeBL.getBestBeforeDateAttribute();
		if (attr_BestBeforeDate.getM_Attribute_ID() != piAttribute.getM_Attribute_ID())
		{
			return null;
		}

		return new BestBeforeASIAttributeValue(attributeStorage, asi, piAttribute);
	}

	private final I_M_AttributeSetInstance asi;

	private BestBeforeASIAttributeValue(final IAttributeStorage attributeStorage,
			final I_M_AttributeSetInstance asi,
			final I_M_HU_PI_Attribute piAttribute)
	{
		super(attributeStorage, piAttribute);

		Check.assumeNotNull(asi, "asi not null");
		this.asi = asi;
	}

	@Override
	protected void setInternalValueString(final String value)
	{
		// throw new UnsupportedOperationException();
	}

	@Override
	protected void setInternalValueNumber(final BigDecimal value)
	{
		// throw new UnsupportedOperationException();
	}

	@Override
	protected String getInternalValueString()
	{
		return null;
	}

	@Override
	protected BigDecimal getInternalValueNumber()
	{
		return null;
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
		// FIXME: i think this method shall be renamed to something like isVirtualAttribute()
		return true;
	}

	@Override
	protected void setInternalValueDate(Date value)
	{
		asi.setGuaranteeDate(TimeUtil.asTimestamp(value));
	}

	@Override
	protected Date getInternalValueDate()
	{
		return asi.getGuaranteeDate();
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
}
