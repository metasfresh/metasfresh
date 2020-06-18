package de.metas.purchasecandidate.purchaseordercreation.localorder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.impl.OrderLineBL;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

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

public class PurchaseOrderFromItemsAggregatorTest
{
	private I_C_UOM EACH;
	private Quantity TEN;

	private static class MockedOrderLineBL extends OrderLineBL
	{
		private int updatePricesCallCount;

		public void updatePrices(org.compiere.model.I_C_OrderLine orderLine)
		{
			// mock IOrderLineBL.updatePrices() because
			// setting up the required masterdata and testing the pricing engine is out of scope.

			updatePricesCallCount++;

		}
	}

	private MockedOrderLineBL orderLineBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.EACH = createUOM("Ea");
		this.TEN = Quantity.of(BigDecimal.TEN, EACH);

		orderLineBL = new MockedOrderLineBL();
		Services.registerService(IOrderLineBL.class, orderLineBL);
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
	public void test()
	{
		// will be needed for user notification (CreatedBy)
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		save(salesOrder);

		// needed to construct the user notification message
		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setValue("Vendor");
		vendor.setName("Vendor");
		save(vendor);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(EACH.getC_UOM_ID());
		saveRecord(productRecord);

		final ProductAndCategoryAndManufacturerId product = ProductAndCategoryAndManufacturerId.of(productRecord.getM_Product_ID(), 30, 35);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.product(product)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(40))
				.vendorId(BPartnerId.ofRepoId(vendor.getC_BPartner_ID()))
				.defaultVendor(false)
				.vendorProductNo("productNo")
				.vendorProductName("productName")
				.pricingConditions(PricingConditions.builder().build())
				.build();

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.orgId(OrgId.ofRepoId(10))
				.purchaseDatePromised(SystemTime.asZonedDateTime())
				.vendorId(vendorProductInfo.getVendorId())
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				.productId(vendorProductInfo.getProductId())
				.attributeSetInstanceId(vendorProductInfo.getAttributeSetInstanceId())
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				.qtyToPurchase(TEN)
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(salesOrder.getC_Order_ID(), 50))
				.warehouseId(WarehouseId.ofRepoId(60))
				.profitInfoOrNull(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.build();

		final PurchaseOrderFromItemsAggregator aggregator = PurchaseOrderFromItemsAggregator.newInstance();

		Services.get(ITrxManager.class).runInNewTrx(() -> {
			aggregator.add(PurchaseOrderItem.builder()
					.purchaseCandidate(purchaseCandidate)
					.datePromised(SystemTime.asZonedDateTime())
					.purchasedQty(TEN)
					.remotePurchaseOrderId(NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID)
					.build());

			aggregator.closeAllGroups();
		});

		final List<I_C_Order> createdPurchaseOrders = aggregator.getCreatedPurchaseOrders();
		assertThat(createdPurchaseOrders).hasSize(1);

		final I_C_Order purchaseOrder = createdPurchaseOrders.get(0);
		assertThat(purchaseOrder.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());

		// these properties are currently set by MOrder.beforeSafe, which is not called in our test
		// assertThat(purchaseOrder.getM_PricingSystem_ID()).isGreaterThan(0);
		// assertThat(purchaseOrder.getM_PriceList_ID()).isGreaterThan(0);
		// assertThat(purchaseOrder.getC_Currency_ID()).isGreaterThan(0);

		assertThat(orderLineBL.updatePricesCallCount).isEqualTo(1);
	}
}
