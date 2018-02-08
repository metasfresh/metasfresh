package de.metas.purchasecandidate;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Table;
import org.compiere.util.TimeUtil;
import org.junit.Test;

import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PurchaseCandidateTest
{
	@Test
	public void markProcessedAndCheckChanges()
	{
		final PurchaseCandidate candidate = PurchaseCandidateTestTool.createPurchaseCandidate(1);
		assertThat(candidate.getSalesOrderLineId()).isEqualTo(PurchaseCandidateTestTool.SALES_ORDER_LINE_ID); // guard
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
		assertThat(candidate.isProcessed()).isFalse();
		assertThat(candidate.copy().isProcessed()).isFalse();

		candidate.markProcessed();
		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.isProcessed()).isTrue();
		assertThat(candidate.copy().isProcessed()).isTrue();
		assertThat(candidate.copy().getSalesOrderLineId()).isEqualTo(PurchaseCandidateTestTool.SALES_ORDER_LINE_ID);

		candidate.markSaved(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void changeQtyRequiredAndCheckChanges()
	{
		final PurchaseCandidate candidate = PurchaseCandidateTestTool.createPurchaseCandidate(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();

		final BigDecimal newQtyRequired = candidate.getQtyToPurchase().add(BigDecimal.ONE);
		candidate.setQtyToPurchase(newQtyRequired);

		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.getQtyToPurchase()).isEqualByComparingTo(newQtyRequired);
		assertThat(candidate.copy().getQtyToPurchase()).isEqualByComparingTo(newQtyRequired);

		candidate.markSaved(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void changeDatePromisedAndCheckChanges()
	{
		final PurchaseCandidate candidate = PurchaseCandidateTestTool.createPurchaseCandidate(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();

		final Date newDatePromised = TimeUtil.addDays(candidate.getDateRequired(), +1);
		candidate.setDateRequired(newDatePromised);

		assertThat(candidate.hasChanges()).isTrue();
		assertThat(candidate.copy().hasChanges()).isTrue();
		assertThat(candidate.getDateRequired()).isEqualTo(newDatePromised);
		assertThat(candidate.copy().getDateRequired()).isEqualTo(newDatePromised);

		candidate.markSaved(1);
		assertThat(candidate.hasChanges()).isFalse();
		assertThat(candidate.copy().hasChanges()).isFalse();
	}

	@Test
	public void newErrorItem()
	{
		final PurchaseCandidate candidate1 = PurchaseCandidateTestTool
				.createPurchaseCandidate(20);

		final RuntimeException throwable = new RuntimeException();
		candidate1.newErrorItem()
				.throwable(throwable)
				.buildAndAdd();

		assertThat(candidate1.getPurchaseOrderItems()).isEmpty();
		final List<PurchaseErrorItem> purchaseErrorItems = candidate1.getPurchaseErrorItems();
		assertThat(purchaseErrorItems).hasSize(1);

		final PurchaseErrorItem purchaseErrorItem = purchaseErrorItems.get(0);
		assertThat(purchaseErrorItem.getOrgId()).isEqualTo(candidate1.getOrgId());
		assertThat(purchaseErrorItem.getPurchaseCandidateId()).isEqualTo(candidate1.getPurchaseCandidateId());
		assertThat(purchaseErrorItem.getThrowable()).isSameAs(throwable);
	}

	@Test
	public void newOrderItem()
	{
		final PurchaseCandidate candidate1 = PurchaseCandidateTestTool
				.createPurchaseCandidate(20);

		candidate1.newOrderItem()
				.purchasedQty(BigDecimal.TEN)
				.datePromised(SystemTime.asTimestamp())
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.transactionReference(TableRecordReference.of(I_AD_Table.Table_Name, 30))
				.buildAndAddToParent();

		assertThat(candidate1.getPurchaseErrorItems()).isEmpty();
		final List<PurchaseOrderItem> purchaseOrderItems = candidate1.getPurchaseOrderItems();
		assertThat(purchaseOrderItems).hasSize(1);

		final PurchaseOrderItem purchaseOrderItem = purchaseOrderItems.get(0);
		assertThat(purchaseOrderItem.getOrgId()).isEqualTo(candidate1.getOrgId());
		assertThat(purchaseOrderItem.getPurchaseCandidateId()).isEqualTo(candidate1.getPurchaseCandidateId());
		assertThat(purchaseOrderItem.getProductId()).isEqualTo(candidate1.getProductId());
	}
}
