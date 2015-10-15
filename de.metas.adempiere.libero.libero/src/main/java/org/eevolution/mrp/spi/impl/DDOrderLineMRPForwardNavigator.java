package org.eevolution.mrp.spi.impl;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.impl.MRPTracer;

/**
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07961_Handelsware_DD_Order_automatisieren_%28101259925191%29
 */
public class DDOrderLineMRPForwardNavigator
{
	// services
	private final transient CLogger logger = CLogger.getCLogger(getClass());
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IMRPBL mrpBL = Services.get(IMRPBL.class);

	// parameters
	private IContextAware context;
	private int ppPlantId;

	// status
	private final Set<Integer> seenPP_MRP_IDs = new HashSet<>();
	private final Set<Integer> collectedDD_Order_IDs = new HashSet<>();

	public DDOrderLineMRPForwardNavigator setContext(final IContextAware context)
	{
		this.context = context;
		return this;
	}

	private IContextAware getContext()
	{
		Check.assumeNotNull(context, LiberoException.class, "context not null");
		return context;
	}

	public Logger getLogger()
	{
		return logger;
	}

	public DDOrderLineMRPForwardNavigator setPP_Plant_ID(final int ppPlantId)
	{
		this.ppPlantId = ppPlantId;
		return this;
	}

	private int getPP_Plant_ID()
	{
		Check.assume(ppPlantId > 0, LiberoException.class, "PP_Plant_ID configured");
		return ppPlantId;
	}

	public void navigateForward(final I_PP_MRP mrpDemand)
	{
		Check.assumeNotNull(mrpDemand, "mrpDemand not null");

		final List<I_PP_MRP> path = Collections.emptyList();
		navigateForward(mrpDemand, path);
	}

	private void navigateForward(final I_PP_MRP mrpDemand, final List<I_PP_MRP> path)
	{
		if (logger.isLoggable(Level.FINE))
		{
			logger.log(Level.FINE, "---------");
			logger.log(Level.FINE, "MRP Demand: {0}", mrpDemand);
			logger.log(Level.FINE, "MRP Demand info: {0}", MRPTracer.toString(mrpDemand));
			logger.log(Level.FINE, "Path: {0}", path);
		}

		// Make sure we did not already seen this MRP demand (i.e. avoid endless recursions)
		if (!seenPP_MRP_IDs.add(mrpDemand.getPP_MRP_ID()))
		{
			logger.log(Level.FINE, "Skip demand because already visited");
			return;
		}

		// Check if this MRP Demand is actually navigable
		if (!isMRPDemandNavigable(mrpDemand))
		{
			logger.log(Level.FINE, "Skip demand because is not navigable");
			return;
		}

		//
		// Retrieve forward MRP demands (i.e. children)
		final List<I_PP_MRP> forwardMRPDemands = retrieveForwardMRPDemands(mrpDemand);

		//
		//
		if (forwardMRPDemands.isEmpty())
		{
			// No demands found
			// => do nothing, about this path
			logger.log(Level.FINE, "Skip demand because it has no other forward demands");
			return;
		}

		//
		//
		for (final I_PP_MRP forwardMRPDemand : forwardMRPDemands)
		{
			logger.log(Level.FINE, "Evaluating forward MRP Demand: {0}", forwardMRPDemand);

			if (!isMRPDemandInScope(forwardMRPDemand))
			{
				logger.log(Level.FINE, "Skip forward demand because not in scope");
				continue;
			}

			//
			// Create current path:
			// * copy the previous path
			// * add the MRP demand that we got as parameter
			// => our path will contain all MRP records along the road, excluding "forwardMRPDemand"
			final List<I_PP_MRP> forwardPath = new ArrayList<>(path);
			forwardPath.add(mrpDemand); // add the MRP demand that we got as paraters

			final boolean forwardMRPDemand_IsFirm = mrpBL.isReleased(forwardMRPDemand);
			if (forwardMRPDemand_IsFirm)
			{
				// The forward demand is Firm
				// => Collect the Path
				collectPath(forwardPath);
				continue;
			}

			//
			// Navigate to forward MRP Demand
			logger.fine("Navigating forward MRP demand...");
			navigateForward(forwardMRPDemand, Collections.unmodifiableList(forwardPath));
		}
	}

	/**
	 * DAO method to retrieve forward MRP demands (which actually triggered given MRP demand)
	 * 
	 * @param mrpDemand
	 * @return forward MRP demands
	 */
	private final List<I_PP_MRP> retrieveForwardMRPDemands(final I_PP_MRP mrpDemand)
	{
		final int ddOrderLineId = mrpDemand.getDD_OrderLine_ID();
		if (ddOrderLineId <= 0)
		{
			return Collections.emptyList();
		}

		// Build up a query which will give us the forward DD_Orders of the DD Order which we are currently checking
		final IQueryBuilder<I_PP_MRP> forwardMRPDemandsQuery = queryBL.createQueryBuilder(I_PP_MRP.class)
				.setContext(getContext())
				//
				// Select the MRP supplies of this DD Order Line => i.e. where these products needs to be delivered
				.addEqualsFilter(I_PP_MRP.COLUMN_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply)
				//
				// And collect the forward MRP demands which triggered those supplies
				.andCollectChildren(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID, I_PP_MRP_Alloc.class)
				.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Demand) // just to be sure
				// For our plant
				.addEqualsFilter(I_PP_MRP.COLUMN_S_Resource_ID, getPP_Plant_ID());

		final List<I_PP_MRP> forwardMRPDemands = forwardMRPDemandsQuery.create().list();
		return forwardMRPDemands;
	}

	private boolean isMRPDemandInScope(final I_PP_MRP mrpDemand)
	{
		// MRP Demand's Plant is in our plant
		if (mrpDemand.getS_Resource_ID() != getPP_Plant_ID())
		{
			return false;
		}

		// Consider it "In Scope"
		return true;
	}

	private boolean isMRPDemandNavigable(final I_PP_MRP mrpDemand)
	{
		// Consider only DD Order Line's MRP demands
		if (mrpDemand.getDD_OrderLine_ID() <= 0)
		{
			logger.log(Level.FINEST, "Demand not navigable because it's not about DD_OrderLines: {0}", mrpDemand);
			return false;
		}
		if (mrpDemand.getDD_Order_ID() <= 0)
		{
			// shall not never ever happen
			logger.log(Level.WARNING, "Skipping an MRP demand which has DD_OrderLine_ID but not DD_Order_ID: {0}", mrpDemand);
			return false;
		}

		// Consider only those MRP demands which are in our scope
		if (!isMRPDemandInScope(mrpDemand))
		{
			logger.log(Level.FINEST, "Demand not navigable because it's not in scope: {0}", mrpDemand);
			return false;
		}

		return true;
	}

	private final void collectPath(final List<I_PP_MRP> mrpRecordsPath)
	{
		logger.log(Level.FINE, "Collecting DD_Orders from path: {0}", mrpRecordsPath);

		//
		// Extract DD_Order_IDs along the path
		final Set<Integer> ddOrderIds = new HashSet<>();
		for (final I_PP_MRP mrpRecord : mrpRecordsPath)
		{
			final int ddOrderId = mrpRecord.getDD_Order_ID();
			if (ddOrderId <= 0)
			{
				// shall not happen
				// => skip the whole path
				return;
			}

			ddOrderIds.add(ddOrderId);
		}

		//
		// Collect those DD_Order_IDs
		collectedDD_Order_IDs.addAll(ddOrderIds);
		logger.log(Level.FINE, "Collected DD_Order_IDs:: {0}", ddOrderIds);
	}

	public Set<Integer> getCollectedDD_Order_IDs()
	{
		return new HashSet<>(collectedDD_Order_IDs);
	}
}
