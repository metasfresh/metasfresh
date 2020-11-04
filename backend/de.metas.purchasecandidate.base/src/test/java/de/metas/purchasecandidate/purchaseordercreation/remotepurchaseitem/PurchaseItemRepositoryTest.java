package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.adempiere.model.I_M_Product;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.quantity.Quantity;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, ProfitPriceActualFactory.class })
public class PurchaseItemRepositoryTest
{
	private I_C_UOM EACH;
	private Quantity ZERO;
	private Quantity ONE;
	private Quantity TEN;

	private I_M_Product product;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.EACH = createUOM("Ea");
		this.ZERO = Quantity.of(BigDecimal.ZERO, EACH);
		this.ONE = Quantity.of(BigDecimal.ONE, EACH);
		this.TEN = Quantity.of(BigDecimal.TEN, EACH);

		product = newInstanceOutOfTrx(I_M_Product.class);
		product.setC_UOM_ID(EACH.getC_UOM_ID());
		save(product);
	}

	private I_C_UOM createUOM(final String name)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		save(uom);
		return uom;
	}

	@Test
	public void storeRecords_purchaseOrderItems()
	{
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(10, ONE);
		final PurchaseOrderItem orderItem = createAndAddPurchaseOrderItem(purchaseCandidate);

		final I_C_OrderLine purchaseOrderLine = createPurchaseOrderLine(TEN);
		orderItem.setPurchaseOrderLineIdAndMarkProcessed(OrderAndLineId.ofRepoIds(purchaseOrderLine.getC_Order_ID(), purchaseOrderLine.getC_OrderLine_ID()));

		// invoke the method under test
		new PurchaseItemRepository().saveAll(purchaseCandidate.getPurchaseOrderItems());

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
		final PurchaseCandidate purchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(10, ONE);
		final PurchaseOrderItem originalPurchaseOrderItem = createAndAddPurchaseOrderItem(purchaseCandidate);

		final I_C_OrderLine purchaseOrderLine = createPurchaseOrderLine(TEN);
		originalPurchaseOrderItem.setPurchaseOrderLineIdAndMarkProcessed(OrderAndLineId.ofRepoIds(purchaseOrderLine.getC_Order_ID(), purchaseOrderLine.getC_OrderLine_ID()));

		// invoke the method under test
		final PurchaseItemRepository purchaseItemRepository = new PurchaseItemRepository();
		purchaseItemRepository.saveAll(purchaseCandidate.getPurchaseOrderItems());

		//
		final PurchaseCandidate newPurchaseCandidate = PurchaseCandidateTestTool.createPurchaseCandidate(10, ONE);
		assertThat(newPurchaseCandidate.getPurchaseOrderItems()).isEmpty(); // guard, loading the purchaseOrderItems is what *we* want to do now
		purchaseItemRepository.loadPurchaseItems(newPurchaseCandidate);

		assertThat(newPurchaseCandidate.getPurchaseOrderItems()).hasSize(1);
		final PurchaseOrderItem retrievedPurchaseOrderItem = newPurchaseCandidate.getPurchaseOrderItems().get(0);
		assertThat(retrievedPurchaseOrderItem.getPurchaseItemId()).isNotNull();
		assertThat(retrievedPurchaseOrderItem.getDatePromised()).isEqualTo(originalPurchaseOrderItem.getDatePromised());
		assertThat(retrievedPurchaseOrderItem.getPurchasedQty()).isEqualByComparingTo(TEN); // that's the quantity from the purchase order line
		assertThat(retrievedPurchaseOrderItem.getPurchaseOrderAndLineId().getOrderRepoId()).isEqualTo(purchaseOrderLine.getC_Order_ID());
		assertThat(retrievedPurchaseOrderItem.getPurchaseOrderAndLineId().getOrderLineRepoId()).isEqualTo(purchaseOrderLine.getC_OrderLine_ID());
	}

	private I_C_OrderLine createPurchaseOrderLine(final Quantity qtyOrdered)
	{
		final I_C_OrderLine purchaseOrderLine = newInstance(I_C_OrderLine.class);
		purchaseOrderLine.setC_Order_ID(50);
		purchaseOrderLine.setM_Product_ID(product.getM_Product_ID());
		purchaseOrderLine.setQtyOrdered(qtyOrdered.toBigDecimal());
		save(purchaseOrderLine);
		return purchaseOrderLine;
	}

	private PurchaseOrderItem createAndAddPurchaseOrderItem(final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseOrderItem orderItem = purchaseCandidate.createOrderItem()
				.datePromised(SystemTime.asZonedDateTime())
				.purchasedQty(ZERO) // doesn't matter
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.transactionReference(TableRecordReference.of("someTable", 30))
				.buildAndAddToParent();
		assertThat(purchaseCandidate.getPurchaseOrderItems()).hasSize(1); // guard
		return orderItem;
	}
}
