package de.metas.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Attribute;

@Validator(I_M_AttributeInstance.class)
public class M_AttributeInstance
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void updateASIDescription(final I_M_AttributeInstance instance)
	{
		final I_M_AttributeSetInstance asi = instance.getM_AttributeSetInstance();
		if (null != asi)
		{
			Services.get(IAttributeSetInstanceBL.class).setDescription(asi);
			InterfaceWrapperHelper.save(asi);
		}
	}

	/**
	 * In case {@link I_M_AttributeInstance#COLUMNNAME_Value} column changed and we deal with an List attribute, search and set corresponding {@link I_M_AttributeValue}
	 *
	 * @param ai
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = I_M_AttributeInstance.COLUMNNAME_Value)
	public void updateAttributeValueIfList(final I_M_AttributeInstance ai)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ai);
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeById(ctx, ai.getM_Attribute_ID());

		//
		// Skip it if attribute value type is not of type List
		final String valueType = attribute.getAttributeValueType();
		if (!X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(valueType))
		{
			return;
		}

		//
		// Search for M_AttributeValue and set M_Attribute.M_AttributeValue_ID
		final String value = ai.getValue();
		final I_M_AttributeValue attributeValue = attributeDAO.retrieveAttributeValueOrNull(attribute, value);
		ai.setM_AttributeValue(attributeValue);
	}
}
