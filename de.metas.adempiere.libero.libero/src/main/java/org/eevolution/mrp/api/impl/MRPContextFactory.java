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


import java.util.Collections;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductPlanningBL;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPContextFactory;
import org.eevolution.mrp.api.IMRPSegment;
import org.eevolution.mrp.api.IMutableMRPContext;

public class MRPContextFactory implements IMRPContextFactory
{
	@Override
	public IMutableMRPContext createInitialMRPContext()
	{
		return new MRPContext();
	}

	@Override
	public IMutableMRPContext createMRPContext(final IMRPContext context)
	{
		final IMutableMRPContext mrpContextNew = new MRPContext();

		Check.assumeNotNull(context, LiberoException.class, "context not null");

		mrpContextNew.setCtx(context.getCtx());
		mrpContextNew.setTrxName(context.getTrxName());
		mrpContextNew.setSubsequentMRPExecutorCall(context.isSubsequentMRPExecutorCall());
		mrpContextNew.setLogger(context.getLogger());
		mrpContextNew.setDate(context.getDate());
		mrpContextNew.setAllowCleanup(context.isAllowCleanup());
		
		mrpContextNew.setAD_Client_ID(context.getAD_Client_ID());
		mrpContextNew.setAD_Org(context.getAD_Org());
		mrpContextNew.setRequireDRP(context.isRequireDRP());
		mrpContextNew.setM_Product(context.getM_Product());
		// mrpContextNew.setProductPlanning(context.getProductPlanning()); // needs to be copied, see below

		mrpContextNew.setMRPDemands(context.getMRPDemands());
		mrpContextNew.setPP_MRP(context.getPP_MRP());
		mrpContextNew.setEnforced_PP_MRP_Demand_ID(context.getEnforced_PP_MRP_Demand_ID());

		//mrpContextNew.setQtyToSupply(context.getQtyToSupply());
		mrpContextNew.setQtyProjectOnHand(context.getQtyProjectOnHand());

		mrpContextNew.setPlant(context.getPlant());
		mrpContextNew.setM_Warehouse(context.getM_Warehouse());
		mrpContextNew.setPlanningHorizon(context.getPlanningHorizon());
		mrpContextNew.setTimeFence(context.getTimeFence());
		mrpContextNew.setPlanner_User_ID(context.getPlanner_User_ID());

		//
		// Make sure we have a clone of product planning in new context
		// because we will change it
		final I_PP_Product_Planning productPlanningOld = context.getProductPlanning();
		final I_PP_Product_Planning productPlanningNew;
		if (productPlanningOld != null)
		{
			final IProductPlanningBL productPlanningBL = Services.get(IProductPlanningBL.class);
			productPlanningNew = productPlanningBL.createPlainProductPlanning(productPlanningOld);
		}
		else
		{
			productPlanningNew = null;
		}
		mrpContextNew.setProductPlanning(productPlanningNew);

		return mrpContextNew;
	}

	@Override
	public IMutableMRPContext createMRPContextFromDemand(final I_PP_MRP mrpDemand)
	{
		Check.assumeNotNull(mrpDemand, LiberoException.class, "mrpDemand not null");

		//
		// Make sure we are dealing with a Demand MRP record
		final IMRPBL mrpBL = Services.get(IMRPBL.class);
		Check.assume(mrpBL.isDemand(mrpDemand), LiberoException.class, "MRP record shall be a Demand: {0}", mrpDemand);

		final IMutableMRPContext mrpContext = createInitialMRPContext();

		//
		// Context and settings
		mrpContext.setCtx(InterfaceWrapperHelper.getCtx(mrpDemand));
		mrpContext.setTrxName(InterfaceWrapperHelper.getTrxName(mrpDemand));
		mrpContext.setDate(SystemTime.asTimestamp());
		// mrpContext.setPlanner_User_ID(plannerUserId);
		mrpContext.setRequireDRP(true);

		//
		// Planning Dimension
		mrpContext.setAD_Client_ID(mrpDemand.getAD_Client_ID());
		mrpContext.setAD_Org(mrpDemand.getAD_Org());
		mrpContext.setM_Warehouse(mrpDemand.getM_Warehouse());
		mrpContext.setPlant(mrpDemand.getS_Resource());
		// mrpContext.setPlanningHorizon(planningHorizon); // will be set when plant/resource will be set/configured

		//
		// Product
		mrpContext.setM_Product(mrpDemand.getM_Product());

		//
		// Configure context for our given PP_MRP record
		mrpContext.setMRPDemands(Collections.singletonList(mrpDemand));
		mrpContext.setEnforced_PP_MRP_Demand_ID(mrpDemand.getPP_MRP_ID());

		return mrpContext;
	}

	@Override
	public IMRPContext createMRPContext(final IMRPContext mrpContext0, final IMRPSegment mrpSegment)
	{
		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createMRPContext(mrpContext0);

		//
		// AD_Client_ID
		final int adClientId = mrpSegment.getAD_Client_ID();
		mrpContext.setAD_Client_ID(adClientId);

		//
		// Plant
		final I_S_Resource plant = mrpSegment.getPlant();
		Check.assumeNotNull(plant, LiberoException.class, "plant not null");
		mrpContext.setPlant(plant);
		mrpContext.setPlanningHorizon(TimeUtil.addDays(mrpContext.getDate(), plant.getPlanningHorizon()));

		//
		// Org
		final I_AD_Org org = mrpSegment.getAD_Org();
		Check.assumeNotNull(org, LiberoException.class, "organization not null");
		mrpContext.setAD_Org(org);

		//
		// Warehouse
		final I_M_Warehouse warehouse = mrpSegment.getM_Warehouse();
		Check.assumeNotNull(warehouse, LiberoException.class, "warehouse not null");
		mrpContext.setM_Warehouse(warehouse);

		//
		// Product
		final I_M_Product product = mrpSegment.getM_Product();
		// null is also OK
		mrpContext.setM_Product(product);

		return mrpContext;
	}

}
