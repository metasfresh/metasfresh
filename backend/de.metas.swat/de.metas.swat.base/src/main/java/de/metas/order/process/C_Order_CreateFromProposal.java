/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.process;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.createFrom.CreateSalesOrderFromProposalCommand;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.sql.Timestamp;

public final class C_Order_CreateFromProposal extends C_Order_CreationProcess implements IProcessDefaultParametersProvider
{
	private static final String PARAM_IsKeepProposalPrices = "IsKeepProposalPrices";
	private static final String PARAM_DEFAULT_VALUE_YES = "Y";

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	@Param(parameterName = "C_DocType_ID", mandatory = true)
	private DocTypeId newOrderDocTypeId;

	@Param(parameterName = "DateOrdered")
	private Timestamp newOrderDateOrdered;

	@Param(parameterName = "POReference")
	private String poReference;

	@Param(parameterName = "CompleteIt")
	private boolean completeIt;

	@Param(parameterName = PARAM_IsKeepProposalPrices)
	private boolean keepProposalPrices;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull I_C_Order order)
	{
		final DocStatus quotationDocStatus = DocStatus.ofNullableCodeOrUnknown(order.getDocStatus());
		if (!quotationDocStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a completed quotation");
		}

		if (!orderBL.isSalesProposalOrQuotation(order))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("is not sales proposal or quotation");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(@NonNull  final IProcessDefaultParameter parameter)
	{
		if (PARAM_IsKeepProposalPrices.contentEquals(parameter.getColumnName()))
		{
			final I_C_Order proposal = orderBL.getById(OrderId.ofRepoId(getRecord_ID()));
			if (docTypeBL.isSalesQuotation(DocTypeId.ofRepoId(proposal.getC_DocType_ID())))
			{
				return PARAM_DEFAULT_VALUE_YES;
			}
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final I_C_Order newSalesOrder = CreateSalesOrderFromProposalCommand.builder()
				.fromProposalId(OrderId.ofRepoId(getRecord_ID()))
				.newOrderDocTypeId(newOrderDocTypeId)
				.newOrderDateOrdered(newOrderDateOrdered)
				.poReference(poReference)
				.completeIt(completeIt)
				.isKeepProposalPrices(keepProposalPrices)
				.build()
				.execute();

		openOrder(newSalesOrder);

		return newSalesOrder.getDocumentNo();
	}
}
