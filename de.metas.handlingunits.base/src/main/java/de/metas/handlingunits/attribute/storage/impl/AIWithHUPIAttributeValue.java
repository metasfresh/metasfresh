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

import java.math.BigDecimal;
import java.util.Date;

import org.compiere.model.I_M_AttributeInstance;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.attribute.impl.AbstractHUAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import lombok.NonNull;

/**
 * Wraps an {@link I_M_AttributeInstance} and uses definition from {@link I_M_HU_PI_Attribute}
 *
 * @author tsa
 *
 */
/* package */class AIWithHUPIAttributeValue extends AbstractHUAttributeValue
{
	private final I_M_AttributeInstance attributeInstance;
	private final boolean isGeneratedAttribute;

	public AIWithHUPIAttributeValue(
			@NonNull final IAttributeStorage attributeStorage,
			@NonNull final I_M_AttributeInstance attributeInstance,
			@NonNull final I_M_HU_PI_Attribute piAttribute,
			final boolean isGeneratedAttribute)
	{
		super(attributeStorage,
				piAttribute,
				Boolean.TRUE // ASI attributes are ALWAYS created from template attributes
		);

		this.attributeInstance = attributeInstance;
		this.isGeneratedAttribute = isGeneratedAttribute;
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
		return isGeneratedAttribute;
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

	@Override
	public boolean isOnlyIfInProductAttributeSet()
	{
		// FIXME tsa: figure out why this returns false instead of using the flag from M_HU_PI_Attribute?!
		return false;
	}
}
