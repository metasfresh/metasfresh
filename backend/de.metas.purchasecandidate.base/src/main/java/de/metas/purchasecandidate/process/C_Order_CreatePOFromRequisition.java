/*
 * #%L
 * de.metas.purchasecandidate.process
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

package de.metas.purchasecandidate.process;

import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.process.C_Order_CreationProcess;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.command.CreatePurchaseOrderFromRequisitionCommand;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_C_Order;

import java.sql.Timestamp;

public final class C_Order_CreatePOFromRequisition extends C_Order_CreationProcess implements IProcessPrecondition
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Param(parameterName = "C_DocType_ID", mandatory = true)
	private DocTypeId newOrderDocTypeId;

	@Param(parameterName = "DateOrdered")
	private Timestamp newOrderDateOrdered;

	@Param(parameterName = "POReference")
	private String poReference;

	@Param(parameterName = "CompleteIt")
	private boolean completeIt;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull I_C_Order order)
	{
		final DocStatus quotationDocStatus = DocStatus.ofNullableCodeOrUnknown(order.getDocStatus());
		if (!quotationDocStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a completed quotation");
		}

		if (!orderBL.isRequisition(order))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("is not purchase requisition");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_C_Order purchaseOrder = CreatePurchaseOrderFromRequisitionCommand.builder()
				.fromRequisitionId(OrderId.ofRepoId(getRecord_ID()))
				.newOrderDocTypeId(newOrderDocTypeId)
				.newOrderDateOrdered(newOrderDateOrdered)
				.poReference(poReference)
				.completeIt(completeIt)
				.build()
				.execute();

		openOrder(purchaseOrder, AdWindowId.ofRepoId(getProcessInfo().getAD_Window_ID()));

		return purchaseOrder.getDocumentNo();
	}

}
