package de.metas.purchasecandidate.purchaseordercreation.localorder;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_Order;
import org.junit.Before;
import org.junit.Test;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{

		// will be needed for user notification (CreatedBy)
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		save(salesOrder);

		// neede to construct the user notification message
		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setValue("Vendor");
		vendor.setName("Vendor");
		save(vendor);

		final int productId = 20;

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.bPartnerProductId(10)
				.productId(productId)
				.vendorBPartnerId(vendor.getC_BPartner_ID())
				.productName("productName")
				.productNo("productNo").build();

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.orgId(10)
				.dateRequired(SystemTime.asTimestamp())
				.vendorProductInfo(vendorProductInfo)
				.vendorBPartnerId(vendor.getC_BPartner_ID())
				.productId(productId)
				.qtyToPurchase(TEN)
				.salesOrderId(salesOrder.getC_Order_ID())
				.salesOrderLineId(50)
				.warehouseId(60)
				.uomId(70)
				.build();

		final PurchaseOrderFromItemsAggregator aggregator = PurchaseOrderFromItemsAggregator.newInstance();

		Services.get(ITrxManager.class).run(() -> {
			aggregator.add(PurchaseOrderItem.builder()
					.purchaseCandidate(purchaseCandidate)
					.datePromised(SystemTime.asTimestamp())
					.purchasedQty(TEN)
					.remotePurchaseOrderId(NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID)
					.build());

			aggregator.closeAllGroups();
		});

		final List<I_C_Order> createdPurchaseOrders = aggregator.getCreatedPurchaseOrders();
		assertThat(createdPurchaseOrders).hasSize(1);

		final I_C_Order purchaseOrder = createdPurchaseOrders.get(0);
		assertThat(purchaseOrder.getDocStatus()).isEqualTo(X_C_Order.DOCSTATUS_Completed);

		// these properties are currently set by MOrder.beforeSafe, which is not called in our test
		// assertThat(purchaseOrder.getM_PricingSystem_ID()).isGreaterThan(0);
		// assertThat(purchaseOrder.getM_PriceList_ID()).isGreaterThan(0);
		// assertThat(purchaseOrder.getC_Currency_ID()).isGreaterThan(0);
	}
}
