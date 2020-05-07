package org.eevolution.mrp.api.impl;

import java.util.Collections;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.ILiberoMRPContextFactory;
import org.eevolution.mrp.api.IMRPBL;
import org.springframework.stereotype.Service;

import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.impl.MRPContextFactory;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Service
public class LiberoMRPContextFactory implements ILiberoMRPContextFactory
{
	private final MRPContextFactory mrpContexFactory;

	public LiberoMRPContextFactory(@NonNull final MRPContextFactory mrpContexFactory)
	{
		this.mrpContexFactory = mrpContexFactory;
	}

	@Override
	public IMutableMRPContext createMRPContextFromDemand(@NonNull final I_PP_MRP mrpDemand)
	{
		Check.assumeNotNull(mrpDemand, MrpException.class, "mrpDemand not null");

		//
		// Make sure we are dealing with a Demand MRP record
		final IMRPBL mrpBL = Services.get(IMRPBL.class);
		Check.assume(mrpBL.isDemand(mrpDemand), MrpException.class, "MRP record shall be a Demand: {}", mrpDemand);

		final IMutableMRPContext mrpContext = mrpContexFactory.createInitialMRPContext();

		//
		// Context and settings
		mrpContext.setCtx(InterfaceWrapperHelper.getCtx(mrpDemand));
		mrpContext.setTrxName(InterfaceWrapperHelper.getTrxName(mrpDemand));
		mrpContext.setDate(SystemTime.asTimestamp());
		// mrpContext.setPlanner_User_ID(plannerUserId);

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
}
