package org.adempiere.mm.attributes.api.impl;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.compiere.model.I_M_Attribute;

import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
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

public class LotNumberDateAttributeDAO implements ILotNumberDateAttributeDAO
{
	public static final AttributeCode ATTR_LotNumberDate = AttributeCode.ofString("HU_LotNumberDate");

	public static final String ATTR_LotNumber_String = "Lot-Nummer";
	public static final AttributeCode ATTR_LotNumber = AttributeCode.ofString(ATTR_LotNumber_String);

	@Override
	public AttributeId getLotNumberDateAttributeId()
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		return attributesRepo.retrieveAttributeIdByValueOrNull(ATTR_LotNumberDate);
	}

	@Override
	public AttributeId getLotNumberAttributeId()
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		return attributesRepo.retrieveAttributeIdByValueOrNull(ATTR_LotNumber);
	}

	@Override
	public I_M_Attribute getLotNumberAttribute()
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final AttributeId attributeId = getLotNumberAttributeId();
		return attributesRepo.getAttributeById(attributeId);
	}
}
