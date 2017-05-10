package de.metas.handlingunits.attribute.impl;

import java.math.BigDecimal;

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

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.slf4j.Logger;

import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;
import de.metas.logging.LogManager;

public class PPOrderProductAttributeDAO implements IPPOrderProductAttributeDAO
{
	private static final transient Logger logger = LogManager.getLogger(PPOrderProductAttributeDAO.class);

	@Override
	public List<I_PP_Order_ProductAttribute> retrieveProductAttributesForPPOrder(final int ppOrderId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.filter(new EqualsQueryFilter<I_PP_Order_ProductAttribute>(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Order_ID, ppOrderId))
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_PP_Order_ProductAttribute.class);
	}

	@Override
	public void addPPOrderProductAttributes(final I_PP_Cost_Collector costCollector, final List<I_M_HU_Attribute> huAttributes)
	{
		// create one PP_Order_ProductAttribute for each of the attributes
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			final I_PP_Order_ProductAttribute ppOrderAttribute = InterfaceWrapperHelper.newInstance(I_PP_Order_ProductAttribute.class);
			ppOrderAttribute.setAD_Org_ID(costCollector.getAD_Org_ID());

			ppOrderAttribute.setPP_Cost_Collector(costCollector);
			ppOrderAttribute.setPP_Order(costCollector.getPP_Order());

			final I_M_Attribute attribute = huAttribute.getM_Attribute();
			ppOrderAttribute.setM_Attribute(attribute);

			final String attributeValueType = attribute.getAttributeValueType();
			if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
			{
				ppOrderAttribute.setValue(null);

				final BigDecimal valueNumber = InterfaceWrapperHelper.getValueOrNull(huAttribute, I_M_HU_Attribute.COLUMNNAME_ValueNumber);
				ppOrderAttribute.setValueNumber(valueNumber);
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
			{
				// TODO: introduce PP_Order_ProductAttribute.ValueDate
				continue;
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType)
					|| X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
			{
				ppOrderAttribute.setValue(huAttribute.getValue());
				ppOrderAttribute.setValueNumber(null);
			}
			else
			{
				logger.warn("AttributeValueType {} not supported for {} / {}", attributeValueType, attribute, huAttribute);
				continue;
			}

			ppOrderAttribute.setM_HU(huAttribute.getM_HU()); // provenance HU

			InterfaceWrapperHelper.save(ppOrderAttribute);
		}
	}

	@Override
	public void deactivateForCostCollector(final int costCollectorId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class)
				.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Cost_Collector_ID, costCollectorId)
				.addOnlyActiveRecordsFilter()
				.create()
				.updateDirectly()
				.addSetColumnValue(I_PP_Order_ProductAttribute.COLUMNNAME_IsActive, false) // deactivate all for cost collector
				.execute();

	}
}
