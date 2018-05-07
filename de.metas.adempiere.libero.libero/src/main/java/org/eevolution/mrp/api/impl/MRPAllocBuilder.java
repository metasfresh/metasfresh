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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.mrp.api.IMRPAllocBuilder;
import org.eevolution.mrp.api.IMRPBL;

/*package */class MRPAllocBuilder implements IMRPAllocBuilder
{
	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IMRPBL mrpBL = Services.get(IMRPBL.class);
	

	private I_PP_MRP mrpSupply;
	private List<I_PP_MRP> mrpDemands;

	@Override
	public IMRPAllocBuilder setMRPSupply(final I_PP_MRP mrpSupply)
	{
		this.mrpSupply = mrpSupply;
		return this;
	}

	@Override
	public IMRPAllocBuilder setMRPDemand(final I_PP_MRP mrpDemand)
	{
		this.mrpDemands = mrpDemand == null ? null : Collections.singletonList(mrpDemand);
		return this;
	}

	@Override
	public IMRPAllocBuilder setMRPDemands(final Collection<I_PP_MRP> mrpDemands)
	{
		if (mrpDemands == null || mrpDemands.isEmpty())
		{
			this.mrpDemands = null;
		}
		else
		{
			this.mrpDemands = new ArrayList<>(mrpDemands);
		}

		return this;
	}

	@Override
	public void build()
	{
		// Validate MRP Supply record
		Check.assumeNotNull(mrpSupply, "mrpSupply not null");
		Check.assume(mrpBL.isSupply(mrpSupply), "MRP record shall be a supply: {}", mrpSupply);

		// Validate MRP Demand records
		Check.assumeNotEmpty(mrpDemands, "mrpDemands not empty");
		for (final I_PP_MRP mrpDemand : mrpDemands)
		{
			Check.assume(mrpBL.isDemand(mrpDemand), "MRP record shall be a demand: {}", mrpDemand);
		}

		//
		final Properties ctx = InterfaceWrapperHelper.getCtx(mrpSupply);
		final IContextAware context = trxManager.createThreadContextAware(ctx);
		final int adOrgId = mrpSupply.getAD_Org_ID();

		//
		// Create PP_MRP_Alloc records
		for (final I_PP_MRP mrpDemand : mrpDemands)
		{
			final BigDecimal qtyAllocated = mrpDemand.getQty();
			
			final I_PP_MRP_Alloc alloc = InterfaceWrapperHelper.newInstance(I_PP_MRP_Alloc.class, context);
			alloc.setAD_Org_ID(adOrgId);
			alloc.setPP_MRP_Demand(mrpDemand);
			alloc.setPP_MRP_Supply(mrpSupply);
			alloc.setQtyAllocated(qtyAllocated);
			InterfaceWrapperHelper.save(alloc);
		}
	}
}
