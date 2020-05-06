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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.impl.AbstractHUAttributeValue;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Wraps a {@link I_M_HU_Attribute} to {@link IAttributeValue}
 *
 * @author tsa
 *
 */
final class HUAttributeValue extends AbstractHUAttributeValue
{
	private final I_M_HU_Attribute huAttribute;
	private boolean saveOnChange;
	private String valueString;
	private BigDecimal valueNumber;
	private Date valueDate;

	public HUAttributeValue(
			@NonNull final AbstractHUAttributeStorage attributeStorage,
			@NonNull final I_M_HU_Attribute huAttribute,
			@NonNull final I_M_HU_PI_Attribute piAttribute,
			final boolean saveOnChange)
	{
		super(
				attributeStorage,
				piAttribute,
				(Boolean)null // isTemplateAttribute = don't know; it will be evaluated when needed
		);

		this.huAttribute = huAttribute;
		this.valueString = huAttribute.getValue();
		this.valueNumber = huAttribute.getValueNumber();
		this.valueDate = huAttribute.getValueDate();
		this.saveOnChange = saveOnChange;
	}

	/**
	 *
	 * @param saveOnChange if true then attribute's value is saved to database when it's changed; also if previous value was false, a database save will be performed now
	 */
	public final void setSaveOnChange(final boolean saveOnChange)
	{
		if (this.saveOnChange == saveOnChange)
		{
			return;
		}

		// If we activated save on change make sure our underlying huAttribute is ok
		if (saveOnChange)
		{
			final String trxName = InterfaceWrapperHelper.getTrxName(huAttribute);
			Check.assume(Services.get(ITrxManager.class).isSameTrxName(ITrx.TRXNAME_ThreadInherited, trxName) || Services.get(ITrxManager.class).isNull(trxName),
					"TrxName shall be null or thread-inherited in case we want to activate 'saveOnChange' option, but it was {}", trxName);
		}

		this.saveOnChange = saveOnChange;

		//
		// If we just activated this feature, make sure everything that we changed until now is saved in database
		saveIfNeeded();
	}

	public final boolean isSaveOnChange()
	{
		return saveOnChange;
	}

	/**
	 *
	 * @return HU's attribute; never return null
	 */
	public I_M_HU_Attribute getM_HU_Attribute()
	{
		return huAttribute;
	}

	@Override
	protected void setInternalValueString(final String value)
	{
		huAttribute.setValue(value);
		valueString = value;
	}

	@Override
	protected void setInternalValueNumber(final BigDecimal value)
	{
		huAttribute.setValueNumber(value);
		valueNumber = value;
	}

	@Override
	protected String getInternalValueString()
	{
		return valueString;
	}

	@Override
	protected BigDecimal getInternalValueNumber()
	{
		return valueNumber;
	}

	@Override
	protected String getInternalValueStringInitial()
	{
		return huAttribute.getValueInitial();
	}

	@Override
	protected BigDecimal getInternalValueNumberInitial()
	{
		return huAttribute.getValueNumberInitial();
	}

	@Override
	protected void setInternalValueStringInitial(final String value)
	{
		huAttribute.setValueInitial(value);
	}

	@Override
	protected void setInternalValueNumberInitial(final BigDecimal value)
	{
		huAttribute.setValueNumberInitial(value);
	}

	@Override
	protected final void onValueChanged(final Object valueOld, final Object valueNew)
	{
		saveIfNeeded();
	}

	/**
	 * Save to database if {@link #isSaveOnChange()}.
	 */
	private final void saveIfNeeded()
	{
		if (!saveOnChange)
		{
			return;
		}

		save();
	}

	/**
	 * Save to database. This method is saving no matter what {@link #isSaveOnChange()} says.
	 */
	final void save()
	{
		// Make sure the storage was not disposed
		assertNotDisposed();

		// Make sure HU Attribute contains the right/fresh data
		huAttribute.setValue(valueString);
		huAttribute.setValueNumber(valueNumber);
		huAttribute.setValueDate(TimeUtil.asTimestamp(valueDate));

		getHUAttributesDAO().save(huAttribute);
	}

	@Override
	public boolean isNew()
	{
		return huAttribute.getM_HU_Attribute_ID() <= 0;
	}

	@Override
	protected void setInternalValueDate(Date value)
	{
		huAttribute.setValueDate(TimeUtil.asTimestamp(value));
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
		huAttribute.setValueDateInitial(TimeUtil.asTimestamp(value));
	}

	@Override
	protected Date getInternalValueDateInitial()
	{
		return huAttribute.getValueDateInitial();
	}
}
