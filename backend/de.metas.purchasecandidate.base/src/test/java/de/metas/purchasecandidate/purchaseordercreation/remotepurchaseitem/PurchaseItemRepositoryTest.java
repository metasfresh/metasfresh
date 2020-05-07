package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_OrderLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;

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

public class PurchaseItemRepositoryTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void storeRecords_purchaseOrderItems()
	{
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(10);
		final PurchaseOrderItem orderItem = createAndAddPurchaseOrderItem(purchaseCandidate);

		final I_C_OrderLine purchaseOrderLine = createPurchaseOrderLine();
		orderItem.setPurchaseOrderLineIdAndMarkProcessed(purchaseOrderLine.getC_OrderLine_ID());

		// invoke the method under test
		new PurchaseItemRepository().storeRecords(purchaseCandidate.getPurchaseOrderItems());

		final List<I_C_PurchaseCandidate_Alloc> records = POJOLookupMap.get().getRecords(I_C_PurchaseCandidate_Alloc.class);
		assertThat(records).hasSize(1);

		final I_C_PurchaseCandidate_Alloc record = records.get(0);
		assertThat(record.getC_PurchaseCandidate_ID()).isEqualTo(10);
		assertThat(record.getAD_Issue_ID()).isLessThanOrEqualTo(0);
		assertThat(record.getRemotePurchaseOrderId()).isEqualTo("remotePurchaseOrderId");
		assertThat(record.getC_OrderLinePO_ID()).isEqualTo(purchaseOrderLine.getC_OrderLine_ID());
		assertThat(record.getC_OrderPO_ID()).isEqualTo(50);
		assertThat(record.getRecord_ID()).isEqualTo(30);
	}

	@Test
	public void storeAndLoad_purchaseOrderItems()
	{
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(10);
		final PurchaseOrderItem originalPurchaseOrderItem = createAndAddPurchaseOrderItem(purchaseCandidate);

		final I_C_OrderLine purchaseOrderLine = createPurchaseOrderLine();
		originalPurchaseOrderItem.setPurchaseOrderLineIdAndMarkProcessed(purchaseOrderLine.getC_OrderLine_ID());

		// invoke the method under test
		final PurchaseItemRepository purchaseItemRepository = new PurchaseItemRepository();
		purchaseItemRepository.storeRecords(purchaseCandidate.getPurchaseOrderItems());

		//
		final PurchaseCandidate newPurchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(10);
		assertThat(newPurchaseCandidate.getPurchaseOrderItems()).isEmpty(); // guard, loading the purchaseOrderItems is what *we* want to do now
		purchaseItemRepository.retrieveForPurchaseCandidate(newPurchaseCandidate);

		assertThat(newPurchaseCandidate.getPurchaseOrderItems()).hasSize(1);
		final PurchaseOrderItem retrievedPurchaseOrderItem = newPurchaseCandidate.getPurchaseOrderItems().get(0);
		assertThat(retrievedPurchaseOrderItem.getPurchaseItemId()).isGreaterThan(0);
		assertThat(retrievedPurchaseOrderItem.getDatePromised()).isEqualTo(originalPurchaseOrderItem.getDatePromised());
		assertThat(retrievedPurchaseOrderItem.getPurchasedQty()).isEqualByComparingTo(TEN); // that's the quantity from the purchase order line
		assertThat(retrievedPurchaseOrderItem.getPurchaseOrderId()).isEqualTo(purchaseOrderLine.getC_Order_ID());
		assertThat(retrievedPurchaseOrderItem.getPurchaseOrderLineId()).isEqualTo(purchaseOrderLine.getC_OrderLine_ID());
	}

	private I_C_OrderLine createPurchaseOrderLine()
	{
		final I_C_OrderLine purchaseOrderLine = newInstance(I_C_OrderLine.class);
		purchaseOrderLine.setC_Order_ID(50);
		purchaseOrderLine.setQtyOrdered(TEN);
		save(purchaseOrderLine);
		return purchaseOrderLine;
	}

	private PurchaseOrderItem createAndAddPurchaseOrderItem(final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseOrderItem orderItem = purchaseCandidate.createOrderItem()
				.datePromised(SystemTime.asTimestamp())
				.purchasedQty(ZERO) // doesn't matter
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.transactionReference(TableRecordReference.of("someTable", 30))
				.buildAndAddToParent();
		assertThat(purchaseCandidate.getPurchaseOrderItems()).hasSize(1); // guard
		return orderItem;
	}
}
