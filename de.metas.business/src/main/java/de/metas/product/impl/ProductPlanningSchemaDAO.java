package de.metas.product.impl;

import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.product.model.I_M_Product_PlanningSchema;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ProductPlanningSchemaDAO
{
	private ProductPlanningSchemaDAO() {}

	/**
	 * @return All the active Product Planning Schema entries with the given Product Planning Schema Selector
	 */
	public static List<I_M_Product_PlanningSchema> retrieveSchemasForSelector(
			@NonNull final String productPlanningSchemaSelector)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product_PlanningSchema.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product_PlanningSchema.COLUMNNAME_M_ProductPlanningSchema_Selector, productPlanningSchemaSelector)
				.create()
				.list();
	}

	/**
	 * @return All the active Product Planning entries that were created for the given planning schema
	 */
	public static List<I_PP_Product_Planning> retrieveProductPlanningsForSchemaID(
			final int productPlanningSchemaID)
	{
		Check.assume(productPlanningSchemaID > 0, "Given productPlanningSchemaID is > 0");

		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_PlanningSchema_ID, productPlanningSchemaID)
				.create()
				.list();
	}

	/**
	 * @return All the active products with the given product planning schema selector
	 */
	public static List<I_M_Product> retrieveProductsForSchemaSelector(
			@NonNull final String productPlanningSchemaSelector)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_ProductPlanningSchema_Selector, productPlanningSchemaSelector)
				.create()
				.list();
	}

	/**
	 * @return the product planning for the given product and schema if found, null otherwise.
	 */
	public static I_PP_Product_Planning retrievePlanningForProductAndSchema(
			@NonNull final I_M_Product product,
			@NonNull final I_M_Product_PlanningSchema schema)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_PlanningSchema_ID, schema.getM_Product_PlanningSchema_ID())
				.orderBy()
				.addColumn(I_PP_Product_Planning.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.first();
	}

	/**
	 * @return Products that don't have PP_ProductPlanning entries
	 */
	public static Iterator<I_M_Product> retrieveProductsWithNoProductPlanning()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_PP_Product_Planning> existentProductPlanning = queryBL.createQueryBuilder(I_PP_Product_Planning.class, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create();

		return queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addNotInSubQueryFilter(I_M_Product.COLUMNNAME_M_Product_ID, I_PP_Product_Planning.COLUMNNAME_M_Product_ID, existentProductPlanning)
				.create()
				.iterate(I_M_Product.class);
	}
}
