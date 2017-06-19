package de.metas.dimension.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.KeyNamePair;

import de.metas.dimension.DimensionConstants;
import de.metas.dimension.IDimensionSpecAttributeBL;
import de.metas.dimension.IDimensionSpecAttributeDAO;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.i18n.IMsgBL;

/*
 * #%L
 * de.metas.dimension
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DimensionSpecAttributeBL implements IDimensionSpecAttributeBL
{

	/**
	 * message for non or empty attribute value
	 */
	public static final String MSG_NoneOrEmpty = "NoneOrEmpty";

	@Override
	public String getAttrValueFromASI(final I_M_Attribute attribute, final I_M_AttributeSetInstance asi)
	{
		final IAttributeDAO attrDAO = Services.get(IAttributeDAO.class);

		if (asi == null)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(asi);

		final I_M_AttributeInstance attributeInstance = attrDAO.retrieveAttributeInstance(asi, attribute.getM_Attribute_ID(), trxName);

		if (attributeInstance == null)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final I_M_AttributeValue attrValue = attributeInstance.getM_AttributeValue();

		if (attrValue != null)
		{
			final String value = attrValue.getName() == null ? attrValue.getValue() : attrValue.getName();

			if (value != null)
			{
				return value;
			}

		}

		final String value = attributeInstance.getValue();

		if (value != null)
		{
			return value;
		}

		final BigDecimal valueNumber = attributeInstance.getValueNumber();

		if (valueNumber != null)
		{
			return valueNumber.toString();
		}

		return DimensionConstants.DIM_EMPTY;
	}

	@Override
	public List<KeyNamePair> createAttrToValue(final I_M_AttributeSetInstance asi, final I_DIM_Dimension_Spec dimensionSpec)
	{
		final IDimensionSpecAttributeDAO dimSpecAttrDAO = Services.get(IDimensionSpecAttributeDAO.class);
		final IDimensionspecDAO dimSpecDAO = Services.get(IDimensionspecDAO.class);

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(dimensionSpec);

		final List<I_M_Attribute> dimAttrs = dimSpecAttrDAO.retrieveAttributesForDimensionSpec(dimensionSpec);

		final List<KeyNamePair> attrToValues = new ArrayList<KeyNamePair>();

		for (final I_M_Attribute attribute : dimAttrs)
		{
			String attrValue = getAttrValueFromASI(attribute, asi);

			if (DimensionConstants.DIM_EMPTY.equals(attrValue))
			{
				// replace DIM_EMPTY with the text from MSG_NoneOrEmpty
				attrValue = Services.get(IMsgBL.class).getMsg(ctxAware.getCtx(), MSG_NoneOrEmpty, true);
			}
			List<String> valueForGroup = dimSpecDAO.retrieveAttributeValueForGroup(dimensionSpec.getInternalName(), attrValue, ctxAware);

			if (!valueForGroup.isEmpty())
			{
				attrToValues.add(new KeyNamePair(attribute.getM_Attribute_ID(), valueForGroup.get(0)));
			}

			// fallback, in case the groupname was not found
			else if (dimensionSpec.isIncludeEmpty())
			{
				attrToValues.add(new KeyNamePair(attribute.getM_Attribute_ID(), DimensionConstants.DIM_EMPTY));
			}
		}

		return attrToValues;
	}

	@Override
	public I_M_AttributeSetInstance createASIForDimensionSpec(final I_M_AttributeSetInstance asi, final I_DIM_Dimension_Spec dimensionSpec)
	{
		final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(dimensionSpec);

		final List<KeyNamePair> attrToValues = createAttrToValue(asi, dimensionSpec);

		if (attrToValues.isEmpty())
		{
			// no relevant attribute was found. null asi

			return null;
		}

		final I_M_AttributeSetInstance newASI = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, dimensionSpec);

		InterfaceWrapperHelper.save(newASI);
		for (final KeyNamePair attrToValue : attrToValues)
		{
			final I_M_AttributeInstance ai = asiBL.getCreateAttributeInstance(newASI, attrToValue.getKey());

			String attrValue = attrToValue.getName();

			if (DimensionConstants.DIM_EMPTY.equals(attrValue))
			{
				attrValue = Services.get(IMsgBL.class).getMsg(ctx, MSG_NoneOrEmpty, true);
			}
			ai.setValue(attrValue);

			InterfaceWrapperHelper.save(ai);
		}

		return newASI;
	}
}
