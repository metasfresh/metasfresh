package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Issue;
import org.springframework.stereotype.Repository;

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
public class RemotePurchaseItemRepository
{
	public void storeNewRecords(@NonNull final List<? extends RemotePurchaseItem> purchaseItems)
	{
		purchaseItems.forEach(RemotePurchaseItemRepository::validateAndStore);
	}

	private static void validateAndStore(@NonNull final RemotePurchaseItem purchaseItem)
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
		final I_C_PurchaseCandidate_Alloc record = newInstance(I_C_PurchaseCandidate_Alloc.class);
		record.setAD_Org_ID(purchaseOrderItem.getOrgId());

		record.setC_PurchaseCandidate_ID(purchaseOrderItem.getPurchaseCandidate().getPurchaseCandidateId());
		record.setC_OrderLinePO_ID(purchaseOrderItem.getPurchaseOrderLineId());

		record.setAD_Table_ID(purchaseOrderItem.getTransactionReference().getAD_Table_ID());
		record.setRecord_ID(purchaseOrderItem.getTransactionReference().getRecord_ID());
		save(record);
	}

	private static void store(@NonNull final PurchaseErrorItem purchaseErrorItem)
	{
		final I_C_PurchaseCandidate_Alloc record = newInstance(I_C_PurchaseCandidate_Alloc.class);
		record.setAD_Org_ID(purchaseErrorItem.getOrgId());

		record.setC_PurchaseCandidate_ID(purchaseErrorItem.getPurchaseCandidate().getPurchaseCandidateId());

		if (purchaseErrorItem.getThrowable() != null)
		{
			final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(purchaseErrorItem.getThrowable());
			record.setAD_Issue(issue);
		}

		record.setAD_Table_ID(purchaseErrorItem.getTransactionReference().getAD_Table_ID());
		record.setRecord_ID(purchaseErrorItem.getTransactionReference().getRecord_ID());
		save(record);
	}
}
