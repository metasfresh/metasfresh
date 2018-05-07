package org.adempiere.mm.attributes.callout;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

public class M_Attribute extends CalloutEngine
{
	public String onSetADJavaClass(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{

		final I_M_Attribute attribute = InterfaceWrapperHelper.create(mTab, I_M_Attribute.class);
		final IAttributeValueGenerator handler = Services.get(IAttributesBL.class).getAttributeValueGeneratorOrNull(attribute);
		
		//
		// Set M_Attribute.AttributeValueType from handler
		if (handler != null)
		{
			final String attributeValueType = attribute.getAttributeValueType();
			if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
			{
				// NOTE: handler it's allowed to have only String or Number value types (because it's items can be only of those values)
				// but we are dealing with a list, so don't change it
			}
			else
			{
				// Update Attribute's value type from generator
				final String valueType = handler.getAttributeValueType();
				if(valueType != null)
				{
					attribute.setAttributeValueType(valueType);
				}
			}
		}

		return NO_ERROR;
	}
}
