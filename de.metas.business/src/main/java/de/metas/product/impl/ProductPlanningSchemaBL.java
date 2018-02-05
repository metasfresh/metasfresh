package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductPlanningSchemaBL implements IProductPlanningSchemaBL
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductPlanningSchemaDAO productPlanningSchemaDAO = Services.get(IProductPlanningSchemaDAO.class);

	@Override
	public void createDefaultProductPlanningsForAllProducts()
	{
		final List<I_M_Product> productsWithNoProductPlanning = productDAO.retrieveProductsWithNoProductPlanning();

		for (final I_M_Product product : productsWithNoProductPlanning)
		{
			final String productPlanningSchemaSelector = product.getM_ProductPlanningSchema_Selector();
			if (Check.isEmpty(productPlanningSchemaSelector))
			{
				// nothing to do
				continue;
			}

			final List<I_M_Product_PlanningSchema> productPlanningSchemas = productPlanningSchemaDAO.retrieveSchemasForSelector(productPlanningSchemaSelector);

			for (final I_M_Product_PlanningSchema productPlanningSchema : productPlanningSchemas)
			{
				createUpdateProductPlanning(product, productPlanningSchema);
			}
		}
	}

	@Override
	public void createDefaultProductPlanningsForSchema(final I_M_Product_PlanningSchema productPlanningSchema)
	{

		final String schemaSelector = productPlanningSchema.getM_ProductPlanningSchema_Selector();

		final List<I_PP_Product_Planning> productPlanningsForSchema = productPlanningSchemaDAO.retrieveProductPlanningsForSchemaID(productPlanningSchema.getM_Product_PlanningSchema_ID());

		for (final I_PP_Product_Planning planning : productPlanningsForSchema)
		{
			final I_M_Product product = planning.getM_Product();
			if (!schemaSelector.equals(product.getM_ProductPlanningSchema_Selector()))
			{
				delete(planning);
			}
			else
			{
				updateProductPlanningFromSchema(planning, productPlanningSchema);
			}
		}

		final List<I_M_Product> productsForSelector = productPlanningSchemaDAO.retrieveProductsForSchemaSelector(schemaSelector);
		for (final I_M_Product product : productsForSelector)
		{
			createUpdateProductPlanning(product, productPlanningSchema);
		}
	}

	private I_PP_Product_Planning createUpdateProductPlanning(final I_M_Product product, final I_M_Product_PlanningSchema schema)
	{
		final I_PP_Product_Planning productPlanning = getCreateProductPlanningForProductAndSchema(product, schema);

		productPlanning.setM_Product(product);
		productPlanning.setM_Product_PlanningSchema_ID(schema.getM_Product_PlanningSchema_ID());

		updateProductPlanningFromSchema(productPlanning, schema);

		return productPlanning;
	}

	private I_PP_Product_Planning getCreateProductPlanningForProductAndSchema(final I_M_Product product, final I_M_Product_PlanningSchema schema)
	{
		I_PP_Product_Planning productPlanning = productPlanningSchemaDAO.retrievePlanningForProductAndSchema(product, schema);

		if (productPlanning == null)
		{
			productPlanning = newInstance(I_PP_Product_Planning.class);
		}

		return productPlanning;
	}

	private void updateProductPlanningFromSchema(final I_PP_Product_Planning productPlanning, final I_M_Product_PlanningSchema schema)
	{
		productPlanning.setAD_Org(schema.getAD_Org());
		productPlanning.setIsAttributeDependant(schema.isAttributeDependant());
		productPlanning.setS_Resource_ID(schema.getS_Resource_ID());
		productPlanning.setM_Warehouse_ID(schema.getM_Warehouse_ID());
		productPlanning.setPlanner_ID(schema.getPlanner_ID());
		productPlanning.setIsManufactured(schema.getIsManufactured());
		productPlanning.setIsCreatePlan(schema.isCreatePlan());
		productPlanning.setIsDocComplete(schema.isDocComplete());
		productPlanning.setAD_Workflow_ID(schema.getAD_Workflow_ID());
		productPlanning.setDD_NetworkDistribution_ID(schema.getDD_NetworkDistribution_ID());
		productPlanning.setIsPickDirectlyIfFeasible(schema.isPickDirectlyIfFeasible());

		save(productPlanning);
	}

}
