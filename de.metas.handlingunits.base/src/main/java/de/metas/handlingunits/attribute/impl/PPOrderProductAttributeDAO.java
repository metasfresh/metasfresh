package de.metas.handlingunits.attribute.impl;

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


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;

public class PPOrderProductAttributeDAO implements IPPOrderProductAttributeDAO
{

	@Override
	public List<I_PP_Order_ProductAttribute> retrieveProductAttributesForPPOrder(final I_PP_Order ppOrder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class)
				.setContext(ppOrder)
				.filter(new EqualsQueryFilter<I_PP_Order_ProductAttribute>(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID()))
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
			final I_PP_Order_ProductAttribute attribute = InterfaceWrapperHelper.newInstance(I_PP_Order_ProductAttribute.class, costCollector);

			attribute.setPP_Cost_Collector(costCollector);
			attribute.setPP_Order(costCollector.getPP_Order());

			attribute.setM_Attribute(huAttribute.getM_Attribute());
			attribute.setValue(huAttribute.getValue());
			attribute.setValueNumber(huAttribute.getValueNumber());

			attribute.setM_HU(huAttribute.getM_HU()); // provenance HU

			InterfaceWrapperHelper.save(attribute);
		}
	}

	@Override
	public void deactivateForCostCollector(final I_PP_Cost_Collector costCollector)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_PP_Order_ProductAttribute.class)
				.setContext(costCollector)
				.addEqualsFilter(I_PP_Order_ProductAttribute.COLUMNNAME_PP_Cost_Collector_ID, costCollector.getPP_Cost_Collector_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.updateDirectly()
				.addSetColumnValue(I_PP_Order_ProductAttribute.COLUMNNAME_IsActive, false) // deactivate all for cost collector
				.execute();

	}
}
