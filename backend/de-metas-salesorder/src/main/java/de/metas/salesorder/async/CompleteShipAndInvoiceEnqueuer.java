/*
 * #%L
 * de-metas-salesorder
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.salesorder.async;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import static org.compiere.util.Env.getCtx;

@Service
public class CompleteShipAndInvoiceEnqueuer
{
	public static final String WP_PARAM_C_Order_ID = I_C_Order.COLUMNNAME_C_Order_ID;

	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public void enqueue(@NonNull final OrderId orderId, @Nullable final String trxName)
	{
		workPackageQueueFactory.getQueueForEnqueuing(getCtx(), CompleteShipAndInvoiceWorkpackageProcessor.class)
				.newWorkPackage()
				.bindToTrxName(trxName)
				.parameter(WP_PARAM_C_Order_ID, orderId)
				.buildAndEnqueue();
	}
}
