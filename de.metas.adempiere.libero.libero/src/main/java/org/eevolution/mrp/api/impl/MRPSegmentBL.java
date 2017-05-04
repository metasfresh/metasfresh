package org.eevolution.mrp.api.impl;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.api.IResourceDAO;
import org.eevolution.drp.api.IDistributionNetworkDAO;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPSegment;
import org.eevolution.mrp.api.IMRPSegmentBL;

public class MRPSegmentBL implements IMRPSegmentBL
{
	@Override
	public IMRPSegment createMRPSegment(final int adClientId)
	{
		final I_AD_Org adOrg = null;
		final I_M_Warehouse warehouse = null;
		final I_S_Resource plant = null;
		final I_M_Product product = null;
		return new MRPSegment(adClientId, adOrg, warehouse, plant, product);
	}

	@Override
	public IMRPSegment createMRPSegment(final IMRPContext mrpContext)
	{
		Check.assumeNotNull(mrpContext, "mrpContext not null");

		final int adClientId = mrpContext.getAD_Client_ID();
		final I_AD_Org adOrg = mrpContext.getAD_Org();
		final I_M_Warehouse warehouse = mrpContext.getM_Warehouse();
		final I_S_Resource plant = mrpContext.getPlant();
		final I_M_Product product = mrpContext.getM_Product();
		final IMRPSegment mrpSegment = new MRPSegment(adClientId, adOrg, warehouse, plant, product);
		return mrpSegment;
	}

	@Override
	public List<IMRPSegment> createFullyDefinedMRPSegments(final Properties ctx, final IMRPSegment mrpSegment0)
	{
		Check.assumeNotNull(ctx, LiberoException.class, "ctx not null");
		Check.assumeNotNull(mrpSegment0, LiberoException.class, "mrpContext not null");

		// services
		final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		// Constant values
		final int adClientId = mrpSegment0.getAD_Client_ID();
		final I_M_Product product = mrpSegment0.getM_Product();

		//
		// Get Available organizations to pick
		final List<I_AD_Org> orgsAvailable;
		if (mrpSegment0.getAD_Org() != null)
		{
			orgsAvailable = Collections.singletonList(mrpSegment0.getAD_Org());
		}
		else
		{
			orgsAvailable = orgDAO.retrieveClientOrgs(ctx, adClientId);
		}
		if (orgsAvailable.isEmpty())
		{
			throw new LiberoException("@NotFound@ @AD_Org_ID@ (@AD_Client_ID@: " + adClientId + ")");
		}

		// Get Available Plants
		final List<I_S_Resource> plantsAvailable;
		if (mrpSegment0.getPlant() != null)
		{
			plantsAvailable = Collections.singletonList(mrpSegment0.getPlant());
		}
		else
		{
			plantsAvailable = resourceDAO.retrievePlants(ctx);
		}
		if (plantsAvailable.isEmpty())
		{
			throw new LiberoException("@NotFound@ @S_Resource_ID@ (@" + I_S_Resource.COLUMNNAME_IsManufacturingResource + "@=@Y@)");
		}

		// Segments
		final List<IMRPSegment> mrpSegments = new ArrayList<>();

		//
		// Iterate Plants, Organizations, Warehouses and create fully defined segments
		for (final I_S_Resource plant : plantsAvailable)
		{
			//
			// Iterate organizations
			final List<I_AD_Org> orgs = new ArrayList<>(orgsAvailable);
			for (final I_AD_Org org : orgs)
			{
				final IMRPSegment mrpSegment_Org_Plant = new MRPSegment(adClientId,
						org,
						mrpSegment0.getM_Warehouse(),
						plant,
						product);

				final Set<IMRPSegment> mrpSegment_Org_Plant_Warehouses = explodeByWarehouse(ctx, mrpSegment_Org_Plant);
				mrpSegments.addAll(mrpSegment_Org_Plant_Warehouses);
			}
		}

		if (mrpSegments.isEmpty())
		{
			throw new LiberoException("No MRP segments could be identified for " + mrpSegment0);
		}

		return mrpSegments;
	}

	private final Set<IMRPSegment> explodeByWarehouse(final Properties ctx, final IMRPSegment mrpSegment)
	{
		final Map<Integer, I_M_Warehouse> warehouses = new HashMap<>();

		//
		// Retrieve Warehouses which are directly assigned to Org or Plant
		{
			final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
			final List<I_M_Warehouse> warehousesForOrgAndPlant = productPlanningDAO.retrieveWarehousesForPlant(ctx, mrpSegment.getAD_Org(), mrpSegment.getPlant());
			for (final I_M_Warehouse warehouse : warehousesForOrgAndPlant)
			{
				warehouses.put(warehouse.getM_Warehouse_ID(), warehouse);
			}
		}

		// If we have a warehouse constraint in our MRP segment, we need to make sure only that warehouse is in our map
		if (mrpSegment.getM_Warehouse_ID() > 0)
		{
			// Check if the warehouse is valid for current Org and Plant
			if (!warehouses.containsKey(mrpSegment.getM_Warehouse_ID()))
			{
				warehouses.clear();
				// Not valid => nothing to do
				return Collections.emptySet();
			}

			warehouses.clear();
			warehouses.put(mrpSegment.getM_Warehouse_ID(), mrpSegment.getM_Warehouse());
		}

		// If we got no warehouses so far, return empty
		if (warehouses.isEmpty())
		{
			return Collections.emptySet();
		}

		//
		// Check the Distribution Networks and add the Source Warehouses which are in the same plant as our warehouses are
		{
			final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);
			for (final int warehouseId : new ArrayList<>(warehouses.keySet()))
			{
				final List<I_M_Warehouse> wareousesInSamePlant = distributionNetworkDAO.retrieveWarehousesInSamePlantAs(ctx, warehouseId);
				for (final I_M_Warehouse warehouse : wareousesInSamePlant)
				{
					warehouses.put(warehouse.getM_Warehouse_ID(), warehouse);
				}
			}
		}

		//
		// Extract other warehouses that we currently have in our PP_MRP record and which we could not found out.
		// We shall also consider those warehouses when exploding the segment.
		// NOTE: if the MRP segment has already the warehouse defined, there is no point to search in PP_MRP.
		if (mrpSegment.getM_Warehouse_ID() <= 0)
		{
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final List<I_M_Warehouse> warehousesForMRPRecords = queryBL
					.createQueryBuilder(I_PP_MRP.class, ctx, ITrx.TRXNAME_None)
					// Only PP_MRP records for our segment
					.addEqualsFilter(I_PP_MRP.COLUMN_AD_Client_ID, mrpSegment.getAD_Client_ID())
					.addEqualsFilter(I_PP_MRP.COLUMN_AD_Org_ID, mrpSegment.getAD_Org_ID())
					.addInArrayOrAllFilter(I_PP_MRP.COLUMN_S_Resource_ID, null, mrpSegment.getPlant_ID())
					// Collect warehouses
					.andCollect(I_PP_MRP.COLUMN_M_Warehouse_ID)
					// Exclude warehouses which we already fetched
					.addNotInArrayFilter(I_M_Warehouse.COLUMN_M_Warehouse_ID, warehouses.keySet())
					.create()
					.list();

			// Add newly fetched warehouses to our list
			for (final I_M_Warehouse warehouse : warehousesForMRPRecords)
			{
				warehouses.put(warehouse.getM_Warehouse_ID(), warehouse);
			}
		}

		//
		// Create resulting MRP segments
		final Set<IMRPSegment> mrpSegmentsExploded = new HashSet<>(warehouses.size());
		for (final I_M_Warehouse warehouse : warehouses.values())
		{
			final IMRPSegment mrpSegmentWithWarehouse = mrpSegment.setM_Warehouse(warehouse);
			mrpSegmentsExploded.add(mrpSegmentWithWarehouse);
		}

		return mrpSegmentsExploded;
	}

	@Override
	public boolean isFullyDefined(final IMRPSegment mrpSegment)
	{
		Check.assumeNotNull(mrpSegment, "mrpSegment not null");
		if (mrpSegment.getAD_Client_ID() <= 0)
		{
			return false;
		}
		if (mrpSegment.getAD_Org_ID() <= 0)
		{
			return false;
		}
		if (mrpSegment.getPlant_ID() <= 0)
		{
			return false;
		}
		if (mrpSegment.getM_Warehouse_ID() <= 0)
		{
			return false;
		}

		// NOTE: even if product is not set, we consider the segment as fully defined (by definition)

		return true;
	}

	@Override
	public final boolean isSegmentIncludes(final IMRPSegment parentSegment, final IMRPSegment childSegment)
	{
		if (parentSegment.getAD_Client_ID() != childSegment.getAD_Client_ID())
		{
			return false;
		}
		if (!isSegmentDimensionIncludes(parentSegment.getAD_Org_ID(), childSegment.getAD_Org_ID()))
		{
			return false;
		}
		if (!isSegmentDimensionIncludes(parentSegment.getPlant_ID(), childSegment.getPlant_ID()))
		{
			return false;
		}
		if (!isSegmentDimensionIncludes(parentSegment.getM_Warehouse_ID(), childSegment.getM_Warehouse_ID()))
		{
			return false;
		}
		if (!isSegmentDimensionIncludes(parentSegment.getM_Product_ID(), childSegment.getM_Product_ID()))
		{
			return false;
		}

		return true;
	}

	private final boolean isSegmentDimensionIncludes(final int dimensionIdOnParent, final int dimensionIdOnChild)
	{
		//
		// Case: parent segment does not have dimensionId defined
		// => child segment is included (even if it has or not a dimensionId defined)
		if (dimensionIdOnParent <= 0)
		{
			return true;
		}
		//
		// Case: parent segment has a dimensionId defined but child segment does not have
		// => child segment is not included
		else if (dimensionIdOnChild <= 0)
		{
			return false;
		}
		//
		// Case: parent segment has a dimensionId defined and child segment has a dimensionId defined
		// => child segment is included if dimensionIds are equal
		else
		{
			return dimensionIdOnParent == dimensionIdOnChild;
		}
	}

}
