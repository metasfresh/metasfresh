package org.eevolution.util;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import de.metas.material.planning.IProductPlanningDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;

import java.math.BigDecimal;

public class PPProductPlanningBuilder
{
	private IContextAware _context;
	private I_PP_Product_Planning _productPlanning;

	// Planning segment
	private I_M_Warehouse _warehouse;
	private I_S_Resource _plant;
	private I_M_Product _product;
	// Common settings
	private BigDecimal _deliveryTime = BigDecimal.ZERO;
	// Manufacturing
	private String _manufactured = X_PP_Product_Planning.ISMANUFACTURED_No;
	private I_PP_Product_BOMVersions _productBOMVersions;
	private I_AD_Workflow _workflow;
	// DRP
	private I_DD_NetworkDistribution _ddNetwork;

	public PPProductPlanningBuilder setContext(IContextAware context)
	{
		this._context = context;
		return this;
	}

	private IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	public I_PP_Product_Planning build()
	{
		return getCreateProductPlanning();
	}

	private I_PP_Product_Planning getCreateProductPlanning()
	{
		if (_productPlanning != null)
		{
			return _productPlanning;
		}

		final I_M_Warehouse warehouse = getM_Warehouse();
		final I_S_Resource plant = getPlant();
		final I_M_Product product = getM_Product();

		final I_PP_Product_Planning productPlanning = InterfaceWrapperHelper.newInstance(I_PP_Product_Planning.class, getContext());

		//
		// Planning segment
		productPlanning.setAD_Org_ID(warehouse == null ? 0 : warehouse.getAD_Org_ID());
		productPlanning.setM_Warehouse_ID(warehouse != null ? warehouse.getM_Warehouse_ID() : -1);
		productPlanning.setS_Resource_ID(plant != null ? plant.getS_Resource_ID() : -1);
		productPlanning.setM_Product_ID(product != null ? product.getM_Product_ID() : -1);

		//
		// Common settings
		productPlanning.setIsActive(true);
		productPlanning.setIsCreatePlan(true);

		productPlanning.setYield(100);

		productPlanning.setDeliveryTime_Promised(_deliveryTime);
		productPlanning.setPlanner_ID(-1);

		//
		// Purchasing
		productPlanning.setIsPurchased(X_PP_Product_Planning.ISPURCHASED_No);
		//
		// Manufacturing
		productPlanning.setIsManufactured(_manufactured);

		if (_productBOMVersions != null)
		{
			productPlanning.setPP_Product_BOMVersions_ID(_productBOMVersions.getPP_Product_BOMVersions_ID());
		}

		if (_workflow != null)
		{
			productPlanning.setAD_Workflow_ID(_workflow.getAD_Workflow_ID());
		}
		//
		// DRP
		productPlanning.setDD_NetworkDistribution(_ddNetwork);

		//
		// Save & return
		Services.get(IProductPlanningDAO.class).save(productPlanning);
		this._productPlanning = productPlanning;
		return _productPlanning;
	}

	public PPProductPlanningBuilder warehouse(final I_M_Warehouse warehouse)
	{
		this._warehouse = warehouse;
		return this;
	}

	private I_M_Warehouse getM_Warehouse()
	{
		return _warehouse;
	}

	public PPProductPlanningBuilder plant(final I_S_Resource plant)
	{
		this._plant = plant;
		return this;
	}

	private I_S_Resource getPlant()
	{
		return _plant;
	}

	public PPProductPlanningBuilder product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	private I_M_Product getM_Product()
	{
		return _product;
	}

	public PPProductPlanningBuilder setIsManufactured(final String manufactured)
	{
		this._manufactured = manufactured;
		return this;
	}

	public PPProductPlanningBuilder setPP_Product_BOMVersions(final I_PP_Product_BOMVersions productBomVersions)
	{
		this._productBOMVersions = productBomVersions;
		return this;
	}

	public PPProductPlanningBuilder setAD_Workflow(final I_AD_Workflow workflow)
	{
		this._workflow = workflow;
		return this;
	}

	public PPProductPlanningBuilder setDeliveryTime_Promised(final int deliveryTime)
	{
		this._deliveryTime = new BigDecimal(deliveryTime);
		return this;
	}

	public PPProductPlanningBuilder setDD_NetworkDistribution(final I_DD_NetworkDistribution ddNetwork)
	{
		this._ddNetwork = ddNetwork;
		return this;
	}
}
