/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.request.service.async.spi.impl;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.request.api.IRequestBL;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;

import java.util.Properties;

public class R_Request_CreateFromOrder_Async extends WorkpackageProcessorAdapter
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IRequestBL requestBL = Services.get(IRequestBL.class);

	/**
	 * Schedule the request creation based on the given order id
	 * <p>
	 * The request will contain information taken from the order
	 */
	public static void createWorkpackage(final I_C_Order order)
	{
		SCHEDULER.schedule(order);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<I_C_Order> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<I_C_Order>(R_Request_CreateFromOrder_Async.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final I_C_Order model)
		{
			return model != null && model.getC_Order_ID() > 0;
		}

		@Override
		protected Properties extractCtxFromItem(final I_C_Order item)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final I_C_Order item)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final I_C_Order item)
		{
			return TableRecordReference.of(I_C_Order.Table_Name, item.getC_Order_ID());
		}
	};

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// retrieve the order and generate requests
		queueDAO.retrieveItems(workPackage, I_C_Order.class, localTrxName)
				.forEach(requestBL::createRequestFromOrder);

		return Result.SUCCESS;
	}

}
