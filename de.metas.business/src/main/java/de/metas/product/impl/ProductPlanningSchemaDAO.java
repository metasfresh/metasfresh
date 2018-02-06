package de.metas.product.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.product.IProductPlanningSchemaDAO;
import de.metas.product.model.I_M_Product_PlanningSchema;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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


public class ProductPlanningSchemaDAO implements IProductPlanningSchemaDAO
{
	@Override
	public List<I_M_Product_PlanningSchema> retrieveSchemasForSelector(final String productPlanningSchemaSelector)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product_PlanningSchema.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product_PlanningSchema.COLUMNNAME_M_ProductPlanningSchema_Selector, productPlanningSchemaSelector)
				.create()
				.list();
	}
	
	@Override
	public List<I_PP_Product_Planning> retrieveProductPlanningsForSchemaID(final int productPlanningSchemaID)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_PlanningSchema_ID, productPlanningSchemaID)
				.create()
				.list();
	}
	
	@Override
	public List<I_M_Product> retrieveProductsForSchemaSelector(final String productPlanningSchemaSelector)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_ProductPlanningSchema_Selector, productPlanningSchemaSelector)
				.create()
				.list();
	}

	@Override
	public I_PP_Product_Planning retrievePlanningForProductAndSchema(final I_M_Product product, final I_M_Product_PlanningSchema schema)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_PlanningSchema_ID, schema.getM_Product_PlanningSchema_ID())
				.create()
				.first();
	}

}
