package de.metas.handlingunits.attributes.sscc18.impl;

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


import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Services;

import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeDAO;

public class SSCC18CodeDAO implements ISSCC18CodeDAO
{
	@Override
	public org.compiere.model.I_M_Attribute retrieveSSCC18Attribute(final Properties ctx)
	{
		return Services.get(IAttributeDAO.class).retrieveAttributeByValue(ctx, Constants.ATTR_SSCC18_Value, org.compiere.model.I_M_Attribute.class);
	}
}
