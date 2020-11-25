package org.adempiere.process;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.impl.CreateSalesOrderAndBOMsFromQuotationCommand;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;

import java.time.LocalDate;
import java.util.Optional;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class C_Order_CreateFromQuotation_Construction extends JavaProcess implements IProcessPrecondition
{
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final Optional<AdWindowId> orderWindowId = RecordZoomWindowFinder.findAdWindowId(I_C_Order.Table_Name);

	@Param(parameterName = "C_DocType_ID")
	private int salesOrderDocTypeRepoId;

	@Param(parameterName = "DateOrdered")
	private LocalDate salesOrderDateOrdered;

	@Param(parameterName = "POReference")
	private String poReference;

	@Param(parameterName = "IsDocComplete")
	private boolean completeIt;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final OrderId quotationId = OrderId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (quotationId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no document selected");
		}

		return checkEligibleSalesQuotation(quotationId);
	}

	private ProcessPreconditionsResolution checkEligibleSalesQuotation(final OrderId quotationId)
	{
		final I_C_Order quotation = ordersRepo.getById(quotationId);

		final DocStatus quotationDocStatus = DocStatus.ofNullableCodeOrUnknown(quotation.getDocStatus());
		if (!quotationDocStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a completed quotation");
		}

		if (!quotation.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not an sales quotation");
		}

		if (quotation.getRef_Order_ID() > 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("a sales order was already generated");
		}

		final DocTypeId quotationDocTypeId = DocTypeId.ofRepoId(quotation.getC_DocType_ID());
		if (!docTypeBL.isSalesProposalOrQuotation(quotationDocTypeId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a sales quotation doc type");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final OrderId quotationId = OrderId.ofRepoId(getRecord_ID());

		checkEligibleSalesQuotation(quotationId)
				.throwExceptionIfRejected();

		final I_C_Order salesOrder = CreateSalesOrderAndBOMsFromQuotationCommand.builder()
				.fromQuotationId(quotationId)
				.docTypeId(DocTypeId.ofRepoIdOrNull(salesOrderDocTypeRepoId))
				.dateOrdered(salesOrderDateOrdered)
				.poReference(poReference)
				.completeIt(completeIt)
				.build()
				.execute();

		getResult().setRecordToOpen(
				TableRecordReference.of(salesOrder),
				orderWindowId.get().getRepoId(), // adWindowId
				OpenTarget.SingleDocument,
				ProcessExecutionResult.RecordsToOpen.TargetTab.NEW_TAB);

		return MSG_OK;
	}
}
