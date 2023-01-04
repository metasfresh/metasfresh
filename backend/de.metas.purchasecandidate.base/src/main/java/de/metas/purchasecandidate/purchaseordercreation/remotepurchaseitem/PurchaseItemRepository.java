package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.organization.IOrgDAO;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public void saveAll(@NonNull final List<? extends PurchaseItem> purchaseItems)
	{
		purchaseItems.forEach(PurchaseItemRepository::save);
	}

	private static void save(@NonNull final PurchaseItem purchaseItem)
	{
		if (purchaseItem instanceof PurchaseOrderItem)
		{
			final PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem)purchaseItem;
			savePurchaseOrderItem(purchaseOrderItem);
		}
		else if (purchaseItem instanceof PurchaseErrorItem)
		{
			final PurchaseErrorItem purchaseErrorItem = (PurchaseErrorItem)purchaseItem;
			savePurchaseErrorItem(purchaseErrorItem);
		}
		else
		{
			throw new AdempiereException("Unknown PurchaseItem type: " + purchaseItem);
		}
	}

	private static void validate(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		final PurchaseCandidateId purchaseCandidateId = purchaseOrderItem.getPurchaseCandidateId();
		Check.assumeNotNull(purchaseCandidateId,
				"The given purchaseOrderItem needs to have a purchaseCandidateId set; purchaseOrderItem={}",
				purchaseOrderItem);

		final boolean hasTransactionReference = purchaseOrderItem.getTransactionReference() != null;
		final boolean hasPurchaseOrderLine = purchaseOrderItem.getPurchaseOrderAndLineId() != null;
		Check.errorUnless(hasTransactionReference || hasPurchaseOrderLine,
				"The given purchaseOrderItem needs to have at least a transactionReference *or* a purchaseOrderLineId; purchaseOrderItem={}",
				purchaseOrderItem);
	}

	private static void savePurchaseOrderItem(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		validate(purchaseOrderItem);

		final I_C_PurchaseCandidate_Alloc record = createOrLoadRecord(purchaseOrderItem);

		record.setAD_Org_ID(purchaseOrderItem.getOrgId().getRepoId());

		final OrderAndLineId purchaseOrderAndLineId = purchaseOrderItem.getPurchaseOrderAndLineId();
		record.setC_OrderPO_ID(OrderAndLineId.getOrderRepoIdOr(purchaseOrderAndLineId, -1));
		record.setC_OrderLinePO_ID(OrderAndLineId.getOrderLineRepoIdOr(purchaseOrderAndLineId, -1));

		record.setC_PurchaseCandidate_ID(PurchaseCandidateId.getRepoIdOr(purchaseOrderItem.getPurchaseCandidateId(), -1));
		record.setDatePromised(TimeUtil.asTimestamp(purchaseOrderItem.getDatePromised()));
		record.setRemotePurchaseOrderId(purchaseOrderItem.getRemotePurchaseOrderId());

		final ITableRecordReference transactionReference = purchaseOrderItem.getTransactionReference();
		if (transactionReference != null)
		{
			record.setAD_Table_ID(transactionReference.getAD_Table_ID());
			record.setRecord_ID(transactionReference.getRecord_ID());
		}

		saveRecord(record);
	}

	private static void savePurchaseErrorItem(@NonNull final PurchaseErrorItem purchaseErrorItem)
	{
		final I_C_PurchaseCandidate_Alloc record = createOrLoadRecord(purchaseErrorItem);

		record.setAD_Org_ID(purchaseErrorItem.getOrgId().getRepoId());

		record.setC_PurchaseCandidate_ID(PurchaseCandidateId.getRepoIdOr(purchaseErrorItem.getPurchaseCandidateId(), -1));

		if (purchaseErrorItem.getThrowable() != null)
		{
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(purchaseErrorItem.getThrowable());
			record.setAD_Issue_ID(issueId.getRepoId());
		}

		if (purchaseErrorItem.getTransactionReference() != null)
		{
			record.setAD_Table_ID(purchaseErrorItem.getTransactionReference().getAD_Table_ID());
			record.setRecord_ID(purchaseErrorItem.getTransactionReference().getRecord_ID());
		}
		saveRecord(record);
	}

	private static I_C_PurchaseCandidate_Alloc createOrLoadRecord(@NonNull final PurchaseItem purchaseOrderItem)
	{
		final I_C_PurchaseCandidate_Alloc record;
		if (purchaseOrderItem.getPurchaseItemId() != null)
		{
			record = load(purchaseOrderItem.getPurchaseItemId().getRepoId(), I_C_PurchaseCandidate_Alloc.class);
		}
		else
		{
			record = newInstance(I_C_PurchaseCandidate_Alloc.class);
		}
		return record;
	}

	public void loadPurchaseItems(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseCandidateId purchaseCandidateId = purchaseCandidate.getId();
		if (purchaseCandidateId == null)
		{
			return;
		}

		final List<I_C_PurchaseCandidate_Alloc> purchaseCandidateAllocRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PurchaseCandidate_Alloc.COLUMN_C_PurchaseCandidate_ID, purchaseCandidateId.getRepoId())
				.create()
				.list();

		for (final I_C_PurchaseCandidate_Alloc purchaseCandidateAllocRecord : purchaseCandidateAllocRecords)
		{
			loadPurchaseItem(purchaseCandidate, purchaseCandidateAllocRecord);
		}
	}

	private void loadPurchaseItem(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final I_C_PurchaseCandidate_Alloc record)
	{
		final ITableRecordReference transactionReference = TableRecordReference.ofReferencedOrNull(record);

		if (record.getAD_Issue_ID() <= 0)
		{
			final OrderAndLineId purchaseOrderAndLineId = OrderAndLineId.ofRepoIds(record.getC_OrderPO_ID(), record.getC_OrderLinePO_ID());
			final Quantity purchasedQty = orderLineBL.getQtyOrdered(purchaseOrderAndLineId);
			final ZoneId timeZone = orgDAO.getTimeZone(purchaseCandidate.getOrgId());
			
			final PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
					.purchaseCandidate(purchaseCandidate)
					.dimension(purchaseCandidate.getDimension())
					.purchaseItemId(PurchaseItemId.ofRepoId(record.getC_PurchaseCandidate_Alloc_ID()))
					.datePromised(TimeUtil.asZonedDateTime(record.getDatePromised(), timeZone))
					.purchasedQty(purchasedQty)
					.remotePurchaseOrderId(record.getRemotePurchaseOrderId())
					.transactionReference(transactionReference)
					.purchaseOrderAndLineId(purchaseOrderAndLineId)
					.build();

			purchaseCandidate.addLoadedPurchaseOrderItem(purchaseOrderItem);
		}
		else
		{
			purchaseCandidate.createErrorItem()
					.purchaseItemId(PurchaseItemId.ofRepoId(record.getC_PurchaseCandidate_Alloc_ID()))
					.adIssueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
					.transactionReference(transactionReference)
					.buildAndAdd();
		}
	}
}
