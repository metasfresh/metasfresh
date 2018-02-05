package de.metas.product;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IProductPlanningSchemaDAO extends ISingletonService
{

	/**
	 * @param productPlanningSchemaSelector
	 * @return All the active Product Planning Schema entries with the given Product Planning Schema Selector
	 */
	List<I_M_Product_PlanningSchema> retrieveSchemasForSelector(String productPlanningSchemaSelector);

	/**
	 * @param productPlanningSchemaID
	 * @return All the active Product Planning entries that were created for the given planning schema
	 */
	List<I_PP_Product_Planning> retrieveProductPlanningsForSchemaID(int productPlanningSchemaID);

	/**
	 * @param productPlanningSchemaSelector
	 * @return All the active products with the given product planning schema selector
	 */
	List<I_M_Product> retrieveProductsForSchemaSelector(String productPlanningSchemaSelector);

	/**
	 * @param product
	 * @param schema
	 * @return the product planning for the given product and schema if found, null otherwise.
	 */
	I_PP_Product_Planning retrievePlanningForProductAndSchema(I_M_Product product, I_M_Product_PlanningSchema schema);

}
