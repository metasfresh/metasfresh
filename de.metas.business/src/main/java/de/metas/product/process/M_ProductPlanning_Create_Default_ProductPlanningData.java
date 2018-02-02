package de.metas.product.process;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.process.JavaProcess;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.IProductPlanningSchemaDAO;
import de.metas.product.model.I_M_Product_PlanningSchema;

/*
 * #%L
 * metasfresh-webui-api
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

public class M_ProductPlanning_Create_Default_ProductPlanningData extends JavaProcess
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductPlanningSchemaDAO productPlanningSchemaDAO = Services.get(IProductPlanningSchemaDAO.class);

	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);

	@Override
	protected String doIt() throws Exception
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
				productPlanningSchemaBL.createUpdateProductPlanning(product, productPlanningSchema);
			}
		}
		return MSG_OK;
	}

}
