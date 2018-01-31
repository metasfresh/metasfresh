package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.product.IProductPlanningSchemaBL;
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
	@Override
	public I_PP_Product_Planning createProductPlanning(final I_M_Product product, final I_M_Product_PlanningSchema schema)
	{
		final I_PP_Product_Planning productPlanning = newInstance(I_PP_Product_Planning.class);
		productPlanning.setM_Product(product);
		productPlanning.setM_Product_PlanningSchema_ID(schema.getM_Product_PlanningSchema_ID());

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

		return productPlanning;
	}
}
