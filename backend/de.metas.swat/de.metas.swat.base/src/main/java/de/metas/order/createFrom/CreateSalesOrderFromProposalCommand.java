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

package de.metas.order.createFrom;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.sql.Timestamp;

public class CreateSalesOrderFromProposalCommand
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	private final OrderId fromProposalId;
	private final DocTypeId newOrderDocTypeId;
	private final Timestamp newOrderDateOrdered;
	private final String poReference;
	private final boolean completeIt;
	private final boolean isKeepProposalPrices;

	@Builder
	private CreateSalesOrderFromProposalCommand(
			@NonNull final OrderId fromProposalId,
			@NonNull final DocTypeId newOrderDocTypeId,
			@Nullable final Timestamp newOrderDateOrdered,
			@Nullable final String poReference,
			final boolean completeIt,
			final boolean isKeepProposalPrices
	)
	{
		this.fromProposalId = fromProposalId;
		this.newOrderDocTypeId = newOrderDocTypeId;
		this.newOrderDateOrdered = newOrderDateOrdered;
		this.poReference = poReference;
		this.completeIt = completeIt;
		this.isKeepProposalPrices = isKeepProposalPrices;
	}

	public I_C_Order execute()
	{
		final I_C_Order fromProposal = orderBL.getById(fromProposalId);
		if (!orderBL.isSalesProposalOrQuotation(fromProposal))
		{
			throw new AdempiereException("Not a quotation/proposal: " + fromProposal);
		}

		final I_C_Order newSalesOrder = copyProposalHeader(fromProposal);
		copyProposalLines(fromProposal, newSalesOrder);
		completeSalesOrderIfNeeded(newSalesOrder);
		return newSalesOrder;
	}

	private I_C_Order copyProposalHeader(@NonNull final I_C_Order fromProposal)
	{
		final I_C_Order newSalesOrder = InterfaceWrapperHelper.copy()
				.setFrom(fromProposal)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_Order.class);

		orderBL.setDocTypeTargetIdAndUpdateDescription(newSalesOrder, newOrderDocTypeId);
		newSalesOrder.setC_DocType_ID(newOrderDocTypeId.getRepoId());

		if (newOrderDateOrdered != null)
		{
			newSalesOrder.setDateOrdered(newOrderDateOrdered);
		}
		if (!Check.isBlank(poReference))
		{
			newSalesOrder.setPOReference(poReference);
		}

		newSalesOrder.setDatePromised(fromProposal.getDatePromised());
		newSalesOrder.setPreparationDate(fromProposal.getPreparationDate());
		newSalesOrder.setDocStatus(DocStatus.Drafted.getCode());
		newSalesOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		newSalesOrder.setRef_Proposal_ID(fromProposal.getC_Order_ID());
		orderDAO.save(newSalesOrder);

		fromProposal.setRef_Order_ID(newSalesOrder.getC_Order_ID());
		orderDAO.save(fromProposal);

		return newSalesOrder;
	}

	private void copyProposalLines(
			@NonNull final I_C_Order fromProposal,
			@NonNull final I_C_Order newSalesOrder)
	{
		CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name)
				.onChildRecordCopied(this::onRecordCopied)
				.copyChildren(
						InterfaceWrapperHelper.getPO(newSalesOrder),
						InterfaceWrapperHelper.getPO(fromProposal));
	}

	private void onRecordCopied(@NonNull final PO to, @NonNull final PO from, @NonNull final CopyTemplate template)
	{
		if (InterfaceWrapperHelper.isInstanceOf(to, I_C_OrderLine.class)
				&& InterfaceWrapperHelper.isInstanceOf(from, I_C_OrderLine.class))
		{
			final I_C_OrderLine newSalesOrderLine = InterfaceWrapperHelper.create(to, I_C_OrderLine.class);
			final I_C_OrderLine fromProposalLine = InterfaceWrapperHelper.create(from, I_C_OrderLine.class);

			newSalesOrderLine.setRef_ProposalLine_ID(fromProposalLine.getC_OrderLine_ID());

			final I_C_Order fromProposal = fromProposalLine.getC_Order();
			final DocTypeId proposalDocType = DocTypeId.ofRepoId(fromProposal.getC_DocType_ID());
			if (isKeepProposalPrices
					&& docTypeBL.isSalesQuotation(proposalDocType))
			{
				newSalesOrderLine.setIsManualPrice(true);
				newSalesOrderLine.setPriceActual(fromProposalLine.getPriceActual());
			}
		}
	}

	private void completeSalesOrderIfNeeded(final I_C_Order newSalesOrder)
	{
		if (completeIt)
		{
			documentBL.processEx(newSalesOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			newSalesOrder.setDocAction(IDocument.ACTION_Prepare);
			orderDAO.save(newSalesOrder);
		}
	}
}
