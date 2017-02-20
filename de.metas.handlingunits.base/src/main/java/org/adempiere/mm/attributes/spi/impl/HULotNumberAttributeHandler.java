package org.adempiere.mm.attributes.spi.impl;

import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class HULotNumberAttributeHandler
		extends AbstractAttributeValueGenerator
		implements IAttributeValueCallout
{

	public HULotNumberAttributeHandler()
	{
		super();
	}

	@Override
	public void onValueChanged(IAttributeValueContext attributeValueContext, IAttributeSet attributeSet, I_M_Attribute attribute, Object valueOld, Object valueNew)
	{
		updateLotNumber(attributeSet, attribute, valueNew);
	}

	/**
	 * Update the lotNumber based on the LotNumberDate
	 * 
	 * @param attributeSet
	 * @param attribute
	 * @param valueNew
	 */
	private void updateLotNumber(final IAttributeSet attributeSet, final I_M_Attribute attribute, final Object valueNew)
	{
		final Date newDate = (Date)valueNew;

		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);

		final I_M_Attribute lotNumber = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttribute(ctx);

		if (lotNumber == null)
		{
			return;
		}

		final IAttributeStorage attributeStorage = (IAttributeStorage)attributeSet;

		final String lotNumberValue;

		if (newDate == null)
		{
			lotNumberValue = null;
		}

		else
		{
			lotNumberValue = Services.get(ILotNumberBL.class).calculateLotNumber(newDate);
		}
		
		attributeStorage.setValue(lotNumber, lotNumberValue);

	}

	@Override
	public Object generateSeedValue(IAttributeSet attributeSet, I_M_Attribute attribute, Object valueInitialDefault)
	{
		return null;
	}

	@Override
	public boolean isReadonlyUI(IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public boolean isAlwaysEditableUI(IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return true;
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_Date;
	}

	/**
	 * @return {@code false} because none of the {@code generate*Value()} methods is implemented.
	 */
	@Override
	public boolean canGenerateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		return false;
	}

}
