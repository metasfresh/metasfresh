package de.metas.manufacturing.rest_api;

import java.util.List;

import org.compiere.model.I_C_UOM;
import org.slf4j.MDC;

import de.metas.common.rest_api.JsonQuantity;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.manufacturing.order.exportaudit.ExportTransactionId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.manufacturing.rest-api
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

class ManufacturingOrderReportProcessCommand
{
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final JsonRequestManufacturingOrdersReport request;

	@Builder
	private ManufacturingOrderReportProcessCommand(
			@NonNull final JsonRequestManufacturingOrdersReport request)
	{
		this.request = request;
	}

	public void execute()
	{
		final ExportTransactionId transactionKey = ExportTransactionId.random();
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey.toJson()))
		{
			for (final JsonRequestIssueToManufacturingOrder issue : request.getIssues())
			{
				processIssue(issue);
			}

			for (final JsonRequestReceiveFromManufacturingOrder receipt : request.getReceipts())
			{
				processReceipt(receipt);
			}
		}

	}

	private void processIssue(final JsonRequestIssueToManufacturingOrder issue)
	{
		final PPOrderId orderId = issue.getOrderId();
		final Quantity qtyToIssue = toQuantity(issue.getQtyToIssue());
		final List<I_M_HU> hus = handlingUnitsBL.getByIds(issue.getHuIds());

		huPPOrderBL.createIssueProducer(orderId)
				.fixedQtyToIssue(qtyToIssue)
				.createIssues(hus);
	}

	private void processReceipt(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		final PPOrderId orderId = receipt.getOrderId();
		final Quantity qtyToReceive = toQuantity(receipt.getQtyToReceive());

		huPPOrderBL.receivingMainProduct(orderId)
				.movementDate(receipt.getDate())
				.receiveVHU(qtyToReceive);
	}

	private Quantity toQuantity(final JsonQuantity json)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(json.getUomCode());
		final I_C_UOM uom = uomDAO.getByX12DE355(x12de355);

		return Quantity.of(json.getQty(), uom);
	}
}
