package de.metas.fresh.ordercheckup.process;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.process.SvrProcess;

/*
 * #%L
 * de.metas.fresh.base
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


import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import de.metas.fresh.ordercheckup.IOrderCheckupBL;
import de.metas.logging.LogManager;

public class C_Order_MFGWarehouse_Report_Generate extends SvrProcess implements ISvrProcessPrecondition
{
	// services
	private static final Logger logger = LogManager.getLogger(C_Order_MFGWarehouse_Report_Generate.class);
	private final transient IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);

	private static final String SYSCONFIG_EnableProcessGear = "de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.EnableProcessGear";

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Order order = getRecord(I_C_Order.class);

		orderCheckupBL.generateReportsIfEligible(order);

		return "@Success@";
	}

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_C_Order order = context.getModel(I_C_Order.class);

		// Make sure this feature is enabled (sysconfig)
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_EnableProcessGear, false, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			return false;
		}

		// Only completed/closed orders
		if (!docActionBL.isStatusCompletedOrClosed(order))
		{
			logger.debug("{} has DocStatus={}; nothing to do", new Object[] { order, order.getDocStatus() });
			return false; // nothing to do
		}

		// Only eligible orders
		if (!orderCheckupBL.isEligibleForReporting(order))
		{
			return false;
		}

		return true;
	}
}
