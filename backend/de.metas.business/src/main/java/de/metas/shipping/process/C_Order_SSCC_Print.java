/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipping.process;

import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_Order_SSCC_Print extends JavaProcess implements IProcessPrecondition
{
	private final PurchaseOrderToShipperTransportationService orderToShipperTransportationService = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationService.class);

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final org.compiere.model.I_C_Order order = orderBL.getById(orderId);
		if (order.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("is sales order");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ReportResultData report = orderToShipperTransportationService.printSSCC18_Labels(getCtx(), OrderId.ofRepoId(getRecord_ID()));

		getResult().setReportData(report);

		return MSG_OK;
	}
}
