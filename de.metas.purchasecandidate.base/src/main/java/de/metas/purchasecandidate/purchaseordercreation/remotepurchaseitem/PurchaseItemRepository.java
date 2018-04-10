package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IErrorManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class PurchaseItemRepository
{
	public void storeRecords(@NonNull final List<? extends PurchaseItem> purchaseItems)
	{
		purchaseItems.forEach(PurchaseItemRepository::validateAndStore);
	}

	private static void validateAndStore(@NonNull final PurchaseItem purchaseItem)
	{
		if (purchaseItem instanceof PurchaseOrderItem)
		{
			final PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem)purchaseItem;
			validate(purchaseOrderItem);
			store(purchaseOrderItem);
		}
		if (purchaseItem instanceof PurchaseErrorItem)
		{
			final PurchaseErrorItem purchaseErrorItem = (PurchaseErrorItem)purchaseItem;
			store(purchaseErrorItem);
		}
	}

	private static void validate(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		final int purchaseCandidateId = purchaseOrderItem.getPurchaseCandidate().getPurchaseCandidateId();
		Check.errorUnless(purchaseCandidateId > 0,
				"The given purchaseOrderItem needs to have a purchaseCandidate with ID > 0; purchaseOrderItem={}",
				purchaseOrderItem);

		final boolean hasTransactionReference = purchaseOrderItem.getTransactionReference() != null;
		final boolean hasPurchaseOrderLine = purchaseOrderItem.getPurchaseOrderLineId() > 0;
		Check.errorUnless(hasTransactionReference || hasPurchaseOrderLine,
				"The given purchaseOrderItem needs to have at least a transactionReference *or* a purchaseOrderLineId; purchaseOrderItem={}",
				purchaseOrderItem);
	}

	private static void store(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		final I_C_PurchaseCandidate_Alloc record = createOrLoadRecord(purchaseOrderItem);

		record.setAD_Org_ID(purchaseOrderItem.getOrgId());

		record.setC_PurchaseCandidate_ID(purchaseOrderItem.getPurchaseCandidate().getPurchaseCandidateId());
		record.setC_OrderPO_ID(purchaseOrderItem.getPurchaseOrderId());
		record.setC_OrderLinePO_ID(purchaseOrderItem.getPurchaseOrderLineId());
		record.setDatePromised(TimeUtil.asTimestamp(purchaseOrderItem.getDatePromised()));
		record.setRemotePurchaseOrderId(purchaseOrderItem.getRemotePurchaseOrderId());

		final ITableRecordReference transactionReference = purchaseOrderItem.getTransactionReference();
		if (transactionReference != null)
		{
			record.setAD_Table_ID(transactionReference.getAD_Table_ID());
			record.setRecord_ID(transactionReference.getRecord_ID());
		}
		save(record);
	}

	private static void store(@NonNull final PurchaseErrorItem purchaseErrorItem)
	{
		final I_C_PurchaseCandidate_Alloc record = createOrLoadRecord(purchaseErrorItem);

		record.setAD_Org_ID(purchaseErrorItem.getOrgId());

		record.setC_PurchaseCandidate_ID(purchaseErrorItem.getPurchaseCandidateId());

		if (purchaseErrorItem.getThrowable() != null)
		{
			final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(purchaseErrorItem.getThrowable());
			record.setAD_Issue(issue);
		}

		record.setAD_Table_ID(purchaseErrorItem.getTransactionReference().getAD_Table_ID());
		record.setRecord_ID(purchaseErrorItem.getTransactionReference().getRecord_ID());
		save(record);
	}

	private static I_C_PurchaseCandidate_Alloc createOrLoadRecord(
			@NonNull final PurchaseItem purchaseOrderItem)
	{
		final I_C_PurchaseCandidate_Alloc record;
		if (purchaseOrderItem.getPurchaseItemId() > 0)
		{
			record = load(purchaseOrderItem.getPurchaseItemId(), I_C_PurchaseCandidate_Alloc.class);
		}
		else
		{
			record = newInstance(I_C_PurchaseCandidate_Alloc.class);
		}
		return record;
	}

	public void retrieveForPurchaseCandidate(
			@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final List<I_C_PurchaseCandidate_Alloc> purchaseItemRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PurchaseCandidate_Alloc.COLUMN_C_PurchaseCandidate_ID, purchaseCandidate.getPurchaseCandidateId())
				.create()
				.list();

		for (final I_C_PurchaseCandidate_Alloc purchaseItemRecord : purchaseItemRecords)
		{
			createForRecord(purchaseCandidate, purchaseItemRecord);
		}
	}

	private static void createForRecord(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final I_C_PurchaseCandidate_Alloc record)
	{
		final ITableRecordReference transactionReference = TableRecordReference.ofReferencedOrNull(record);

		if (record.getAD_Issue_ID() <= 0)
		{
			final I_C_OrderLine purchaseOrderLine = load(record.getC_OrderLinePO_ID(), I_C_OrderLine.class);

			final PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
					.purchaseCandidate(purchaseCandidate)
					.purchaseItemId(record.getC_PurchaseCandidate_Alloc_ID())
					.datePromised(record.getDatePromised())
					.purchasedQty(purchaseOrderLine.getQtyOrdered())
					.remotePurchaseOrderId(record.getRemotePurchaseOrderId())
					.transactionReference(transactionReference)
					.purchaseOrderId(purchaseOrderLine.getC_Order_ID())
					.purchaseOrderLineId(purchaseOrderLine.getC_OrderLine_ID())
					.build();

			purchaseCandidate.addLoadedPurchaseOrderItem(purchaseOrderItem);
			return;

		}
		purchaseCandidate.createErrorItem()
				.purchaseItemId(record.getC_PurchaseCandidate_Alloc_ID())
				.issue(record.getAD_Issue())
				.transactionReference(transactionReference)
				.buildAndAdd();
	}
}
