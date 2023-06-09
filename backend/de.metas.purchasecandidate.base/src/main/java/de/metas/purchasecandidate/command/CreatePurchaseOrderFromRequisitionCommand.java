/*
 * #%L
 * de.metas.purchasecandidate.command
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

package de.metas.purchasecandidate.command;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.sql.Timestamp;

public class CreatePurchaseOrderFromRequisitionCommand
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final OrderId fromRequisitionId;
	private final DocTypeId newOrderDocTypeId;
	private final Timestamp newOrderDateOrdered;
	private final String poReference;
	private final boolean completeIt;

	@Builder
	private CreatePurchaseOrderFromRequisitionCommand(
			@NonNull final OrderId fromRequisitionId,
			@NonNull final DocTypeId newOrderDocTypeId,
			@Nullable final Timestamp newOrderDateOrdered,
			@Nullable final String poReference,
			final boolean completeIt)
	{
		this.fromRequisitionId = fromRequisitionId;
		this.newOrderDocTypeId = newOrderDocTypeId;
		this.newOrderDateOrdered = newOrderDateOrdered;
		this.poReference = poReference;
		this.completeIt = completeIt;
	}

	public I_C_Order execute()
	{
		final I_C_Order fromProposal = orderBL.getById(fromRequisitionId);
		if (!orderBL.isRequisition(fromProposal))
		{
			throw new AdempiereException("Not a Requisition: " + fromProposal);
		}

		final I_C_Order newPurchaseOrder = copyProposalHeader(fromProposal);
		copyProposalLines(fromProposal, newPurchaseOrder);
		completePurchaseOrderIfNeeded(newPurchaseOrder);
		return newPurchaseOrder;
	}

	private I_C_Order copyProposalHeader(@NonNull final I_C_Order fromProposal)
	{
		final I_C_Order newOrder = InterfaceWrapperHelper.copy()
				.setFrom(fromProposal)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_Order.class);

		orderBL.setDocTypeTargetIdAndUpdateDescription(newOrder, newOrderDocTypeId);
		newOrder.setC_DocType_ID(newOrderDocTypeId.getRepoId());

		if (newOrderDateOrdered != null)
		{
			newOrder.setDateOrdered(newOrderDateOrdered);
		}
		if (!Check.isBlank(poReference))
		{
			newOrder.setPOReference(poReference);
		}

		newOrder.setDatePromised(fromProposal.getDatePromised());
		newOrder.setPreparationDate(fromProposal.getPreparationDate());
		newOrder.setDocStatus(DocStatus.Drafted.getCode());
		newOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		newOrder.setRef_Proposal_ID(fromProposal.getC_Order_ID());
		orderDAO.save(newOrder);

		fromProposal.setRef_Order_ID(newOrder.getC_Order_ID());
		orderDAO.save(fromProposal);

		return newOrder;
	}

	private void copyProposalLines(
			@NonNull final I_C_Order fromProposal,
			@NonNull final I_C_Order newOrder)
	{
		CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name)
				.onChildRecordCopied(this::onRecordCopied)
				.copyChildren(
						InterfaceWrapperHelper.getPO(newOrder),
						InterfaceWrapperHelper.getPO(fromProposal));
	}

	private void onRecordCopied(@NonNull final PO to, @NonNull final PO from)
	{
		if (InterfaceWrapperHelper.isInstanceOf(to, I_C_OrderLine.class)
				&& InterfaceWrapperHelper.isInstanceOf(from, I_C_OrderLine.class))
		{
			final I_C_OrderLine newOrderLine = InterfaceWrapperHelper.create(to, I_C_OrderLine.class);
			final I_C_OrderLine fromProposalLine = InterfaceWrapperHelper.create(from, I_C_OrderLine.class);

			newOrderLine.setRef_ProposalLine_ID(fromProposalLine.getC_OrderLine_ID());
			orderDAO.save(newOrderLine);
			createPurchaseOrderAllocRecord(newOrderLine, fromProposalLine);
		}
	}

	private void createPurchaseOrderAllocRecord(final I_C_OrderLine newOrderLine, final I_C_OrderLine fromOrderLine)
	{

		final I_C_PurchaseCandidate_Alloc alloc = queryBL.createQueryBuilder(I_C_PurchaseCandidate_Alloc.class)
				.addEqualsFilter(I_C_PurchaseCandidate_Alloc.COLUMN_C_OrderLinePO_ID, fromOrderLine.getC_OrderLine_ID())
				.create().first();
		Check.assumeNotNull(alloc, "Could not find an allocation for line C_OrderLinePO_ID {}", fromOrderLine.getC_OrderLine_ID());
		final I_C_PurchaseCandidate_Alloc purchaseCandidateAlloc = InterfaceWrapperHelper.copy()
				.setFrom(alloc)
				.copyToNew(I_C_PurchaseCandidate_Alloc.class);
		purchaseCandidateAlloc.setC_OrderPO_ID(newOrderLine.getC_Order_ID());
		purchaseCandidateAlloc.setC_OrderLinePO_ID(newOrderLine.getC_OrderLine_ID());
		InterfaceWrapperHelper.save(purchaseCandidateAlloc);
		markProcessed(PurchaseCandidateId.ofRepoId(purchaseCandidateAlloc.getC_PurchaseCandidate_ID()));
	}

	private void markProcessed(final PurchaseCandidateId cPurchaseCandidateId)
	{
		final I_C_PurchaseCandidate candidate = queryBL.createQueryBuilder(I_C_PurchaseCandidate.class)
				.addEqualsFilter(I_C_PurchaseCandidate.COLUMN_C_PurchaseCandidate_ID, cPurchaseCandidateId)
				.create()
				.first();
		Check.assumeNotNull(candidate, "Could not find a Purchase candidate for line C_PurchaseCandidate_ID {}", cPurchaseCandidateId);
		candidate.setProcessed(true);
		InterfaceWrapperHelper.save(candidate);
	}

	private void completePurchaseOrderIfNeeded(final I_C_Order newOrder)
	{
		if (completeIt)
		{
			documentBL.processEx(newOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			newOrder.setDocAction(IDocument.ACTION_Prepare);
			orderDAO.save(newOrder);
		}
	}
}
