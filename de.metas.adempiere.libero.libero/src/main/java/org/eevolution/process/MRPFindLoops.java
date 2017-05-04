package org.eevolution.process;

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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.eevolution.drp.api.IDistributionNetworkDAO;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * 
 * Quick & Dirty process that is supposed to detect loops in the production planning (PP_Product_Planning and DD_NetworkDistributionLine). The process can be called with specific product, resource and
 * warehouse. If one of the three is not specified, all products that are sold and purchased, all resources that are plants and all warehouses are iterated in all combination.
 *
 */
public class MRPFindLoops extends JavaProcess
{
	// Services
	private final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);

	//
	// Parameters
	private int p_AD_Org_ID = -1;
	private int p_S_Resource_ID = -1;
	private int p_M_Warehouse_ID = -1;
	protected int p_M_Product_ID = -1;

	@Override
	protected final void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = para.getParameterAsInt();
			}
			else if (name.equals("S_Resource_ID"))
			{
				p_S_Resource_ID = para.getParameterAsInt();
			}
			else if (name.equals("M_Warehouse_ID"))
			{
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
			else if (name.equals("M_Product_ID"))
			{
				p_M_Product_ID = para.getParameterAsInt();
			}
		}
	}	// prepare

	@Override
	protected final String doIt() throws Exception
	{
		//
		// retrieve the products, resources and warehouses over this we will iterate

		// products
		final List<I_M_Product> products = new ArrayList<I_M_Product>();
		if (p_M_Product_ID > 0)
		{
			products.add(InterfaceWrapperHelper.create(getCtx(), p_M_Product_ID, I_M_Product.class, getTrxName()));
		}
		else
		{
			final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class, getCtx(), getTrxName())
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_Product.COLUMNNAME_IsSold, true)
					.addEqualsFilter(I_M_Product.COLUMNNAME_IsPurchased, true);
			queryBuilder.orderBy().addColumn(I_M_Product.COLUMNNAME_M_Product_ID);

			products.addAll(queryBuilder.create().list(I_M_Product.class));
		}

		// resources
		final List<I_S_Resource> resources = new ArrayList<I_S_Resource>();
		if (p_S_Resource_ID > 0)
		{
			resources.add(InterfaceWrapperHelper.create(getCtx(), p_S_Resource_ID, I_S_Resource.class, getTrxName()));
		}
		else
		{
			final IQueryBuilder<I_S_Resource> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_S_Resource.class, getCtx(), getTrxName())
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);
			queryBuilder.orderBy().addColumn(I_S_Resource.COLUMNNAME_S_Resource_ID);

			resources.addAll(queryBuilder.create().list(I_S_Resource.class));
		}

		// warehouses
		final List<I_M_Warehouse> warehouses = new ArrayList<I_M_Warehouse>();
		if (p_M_Warehouse_ID > 0)
		{
			warehouses.add(InterfaceWrapperHelper.create(getCtx(), p_M_Warehouse_ID, I_M_Warehouse.class, getTrxName()));
		}
		else
		{
			final IQueryBuilder<I_M_Warehouse> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse.class, getCtx(), getTrxName())
					.addOnlyActiveRecordsFilter();
			queryBuilder.orderBy().addColumn(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID);

			warehouses.addAll(queryBuilder.create().list(I_M_Warehouse.class));
		}

		//
		// now iterate them all and look for loops
		for (final I_M_Product product : products)
		{
			for (final I_S_Resource resource : resources)
			{
				for (final I_M_Warehouse warehouse : warehouses)
				{
					final Tracer tracer = new Tracer(product, resource, warehouse);
					final boolean noLoop = iterate(product.getM_Product_ID(), resource.getS_Resource_ID(), warehouse.getM_Warehouse_ID(), tracer);
					final int dept = tracer.getDept();
					if (noLoop /*&& dept > 0*/)
					{
						continue; // everying is OK
					}

					final String msg = String.format("No loop: %s (depth=%s) for product=%s, resource=%s, warehouse=%s",
							noLoop, dept, product.getValue(), resource.getName(), warehouse.getName());
					addLog(msg);
				}
			}
		}
		return "Success";
	}

	/**
	 * 
	 * @param productID
	 * @param resourceID
	 * @param warehouseId
	 * @param tracer
	 * @return true if *no* loop was found
	 */
	private boolean iterate(final int productID, final int resourceID, final int warehouseId, final Tracer tracer)
	{
		final I_PP_Product_Planning productPlanning = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Product_Planning.class, getCtx(), getTrxName())
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, p_AD_Org_ID, 0, null)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_IsCreatePlan, true)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productID)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, resourceID)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, warehouseId).create().firstOnly(I_PP_Product_Planning.class);

		if (productPlanning == null)
		{
			return true; // no planning, no loop
		}
		tracer.productPlanningSeen(productPlanning);

		final I_DD_NetworkDistribution networkDist = productPlanning.getDD_NetworkDistribution();
		if (networkDist == null)
		{
			return true;
		}
		final List<I_DD_NetworkDistributionLine> retrieveNetworkLines = distributionNetworkDAO.retrieveNetworkLinesByTargetWarehouse(networkDist, warehouseId);
		for (final I_DD_NetworkDistributionLine netWorkLine : retrieveNetworkLines)
		{
			if (tracer.networkLineSeen(netWorkLine))
			{
				// no loop (yet)
			}
			else
			{
				addLog("Loop detected: " + tracer);
				return false;
			}

			final boolean keepTargetPlant = netWorkLine.isKeepTargetPlant();
			final int plantID;
			if (keepTargetPlant)
			{
				plantID = resourceID;
			}
			else
			{
				final I_M_Warehouse sourceWarehouse = netWorkLine.getM_WarehouseSource();
				if (sourceWarehouse.getPP_Plant_ID() > 0)
				{
					plantID = sourceWarehouse.getPP_Plant_ID();
				}
				else
				{
					plantID = resourceID;
				}
			}
			return iterate(productID, plantID, netWorkLine.getM_WarehouseSource_ID(), tracer);
		}
		return true;
	}

	/**
	 * Internal class used to collect hold the information while the process iterates the data.
	 * 
	 *
	 */
	private static class Tracer
	{
		private final Set<Integer> seenNetWorkLineIDs = new HashSet<Integer>();

		private final List<String> networkLines = new ArrayList<String>();

		private final List<String> productPlannings = new ArrayList<String>();

		private final List<String> hops = new ArrayList<String>();

		private final I_M_Product product;

		private final I_S_Resource resource;

		private final I_M_Warehouse warehouse;

		public Tracer(final I_M_Product product, final I_S_Resource resource, final I_M_Warehouse warehouse)
		{
			this.product = product;
			this.resource = resource;
			this.warehouse = warehouse;
		}

		public void productPlanningSeen(final I_PP_Product_Planning planning)
		{
			final String planningDescription = planning.getPP_Product_Planning_ID() + "";
			productPlannings.add(planningDescription);
			hops.add(planningDescription);
		}

		public boolean networkLineSeen(I_DD_NetworkDistributionLine line)
		{
			final boolean notALoop = !seenNetWorkLineIDs.contains(line.getDD_NetworkDistributionLine_ID());

			final String ddNetWorkLineDescription = line.getDD_NetworkDistribution_ID() + " (" + line.getDD_NetworkDistribution().getName() + ") : "
					+ line.getDD_NetworkDistributionLine_ID() + " (" + line.getM_WarehouseSource().getName() + " => " + line.getM_Warehouse().getName() + ")";
			networkLines.add(ddNetWorkLineDescription);
			hops.add(ddNetWorkLineDescription);

			seenNetWorkLineIDs.add(line.getDD_NetworkDistributionLine_ID());

			return notALoop;
		}

		public int getDept()
		{
			return networkLines.size();
		}

		@Override
		public String toString()
		{
			return String.format("Tracer [product=%s, resource=%s, warehouse=%s, networkLines=%s]", product.getValue(), resource.getName(), warehouse.getName(), networkLines);
		}
	}
}
