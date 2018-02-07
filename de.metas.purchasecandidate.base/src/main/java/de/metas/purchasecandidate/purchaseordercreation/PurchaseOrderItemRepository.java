package de.metas.purchasecandidate.purchaseordercreation;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;

import org.adempiere.util.Check;
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
public class PurchaseOrderItemRepository
{
	public void storeNewRecords(@NonNull final Collection<PurchaseOrderItem> purchaseOrderItems)
	{
		purchaseOrderItems.forEach(PurchaseOrderItemRepository::validateAndStore);
	}

	private static void validateAndStore(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		validate(purchaseOrderItem);
		store(purchaseOrderItem);
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
}
