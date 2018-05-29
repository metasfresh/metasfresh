package de.metas.purchasecandidate.purchaseordercreation.localorder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.BPartnerId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.money.grossprofit.GrossProfitPriceFactory;
import de.metas.order.OrderLineId;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.event.OrderUserNotifications.ADMessageAndParams;
import de.metas.order.event.OrderUserNotifications.NotificationRequest;
import de.metas.order.model.I_C_Order;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import mockit.Mocked;
import mockit.Verifications;

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
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, GrossProfitPriceFactory.class })
public class PurchaseOrderFromItemFactoryTest
{
	private static final LocalDateTime PURCHASE_CANDIDATE_DATE_REQUIRED = SystemTime.asLocalDateTime();

	private static final BigDecimal PURCHASE_CANDIDATE_QTY_TO_PURCHASE = BigDecimal.TEN;

	@Mocked
	OrderUserNotifications orderUserNotifications;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void deviatingDatePromised()
	{
		final LocalDateTime deviatingDatePromised = PURCHASE_CANDIDATE_DATE_REQUIRED.plusDays(1);

		setAndRunMethodUnderTest(deviatingDatePromised, PURCHASE_CANDIDATE_QTY_TO_PURCHASE);

		// @formatter:off
		new Verifications()
		{{
			NotificationRequest request;
			orderUserNotifications.notifyOrderCompleted(request = withCapture()); times = 1;

			final ADMessageAndParams adMessageAndParams = request.getAdMessageAndParams();
			assertThat(adMessageAndParams).isNotNull();
			assertThat(adMessageAndParams.getAdMessage()).isEqualTo(PurchaseOrderFromItemFactory.MSG_Different_DatePromised);
			assertThat(adMessageAndParams.getParams()).hasSize(3);
		}};	// @formatter:on
	}

	@Test
	public void deviatingPurchasedQty()
	{
		final BigDecimal deviatingPurchasedQty = PURCHASE_CANDIDATE_QTY_TO_PURCHASE.subtract(BigDecimal.ONE);

		setAndRunMethodUnderTest(PURCHASE_CANDIDATE_DATE_REQUIRED, deviatingPurchasedQty);

		// @formatter:off
		new Verifications()
		{{
			NotificationRequest request;
			orderUserNotifications.notifyOrderCompleted(request = withCapture()); times = 1;

			final ADMessageAndParams adMessageAndParams = request.getAdMessageAndParams();
			assertThat(adMessageAndParams).isNotNull();
			assertThat(adMessageAndParams.getAdMessage()).isEqualTo(PurchaseOrderFromItemFactory.MSG_Different_Quantity);
			assertThat(adMessageAndParams.getParams()).hasSize(3);
		}};	// @formatter:on
	}

	@Test
	public void deviatingPurchasedQtyAndDatePrmised()
	{
		final BigDecimal deviatingPurchasedQty = PURCHASE_CANDIDATE_QTY_TO_PURCHASE.subtract(BigDecimal.ONE);
		final LocalDateTime deviatingDatePromised = PURCHASE_CANDIDATE_DATE_REQUIRED.plusDays(1);

		setAndRunMethodUnderTest(deviatingDatePromised, deviatingPurchasedQty);

		// @formatter:off
		new Verifications()
		{{
			NotificationRequest request;
			orderUserNotifications.notifyOrderCompleted(request = withCapture()); times = 1;

			final ADMessageAndParams adMessageAndParams = request.getAdMessageAndParams();
			assertThat(adMessageAndParams).isNotNull();
			assertThat(adMessageAndParams.getAdMessage()).isEqualTo(PurchaseOrderFromItemFactory.MSG_Different_Quantity_AND_DatePromised);
			assertThat(adMessageAndParams.getParams()).hasSize(3);
		}};	// @formatter:on
	}

	private void setAndRunMethodUnderTest(final LocalDateTime deviatingDatePromised, final BigDecimal deviatingPurchasedQty)
	{
		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate();

		final PurchaseOrderItem pruchaseOrderItem = purchaseCandidate.createOrderItem()
				.datePromised(deviatingDatePromised)
				.purchasedQty(deviatingPurchasedQty)
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.transactionReference(TableRecordReference.of("tableName", 20))
				.buildAndAddToParent();

		Services.get(ITrxManager.class).run(() -> {

			final PurchaseOrderFromItemFactory purchaseOrderFromItemFactory = PurchaseOrderFromItemFactory.builder()
					.orderAggregationKey(PurchaseOrderAggregationKey.formPurchaseOrderItem(pruchaseOrderItem))
					.userNotifications(orderUserNotifications)
					.build();

			purchaseOrderFromItemFactory.addCandidate(pruchaseOrderItem);
			purchaseOrderFromItemFactory.createAndComplete();
		});
	}

	public static PurchaseCandidate createPurchaseCandidate()
	{
		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setName("vendor.name");
		vendor.setValue("vendor.value");
		save(vendor);

		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		save(salesOrder);

		final I_C_OrderLine salesOrderLine = newInstance(I_C_OrderLine.class);
		salesOrderLine.setC_Order(salesOrder);
		save(salesOrderLine);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.bpartnerProductId(10)
				.vendorBPartnerId(BPartnerId.ofRepoId(vendor.getC_BPartner_ID()))
				.productId(ProductId.ofRepoId(20))
				.productName("productName")
				.productNo("productNo")
				.build();
		return PurchaseCandidate.builder()
				.salesOrderId(salesOrder.getC_Order_ID())
				.salesOrderLineId(OrderLineId.ofRepoId(salesOrderLine.getC_OrderLine_ID()))
				.orgId(3)
				.warehouseId(4)
				.productId(ProductId.ofRepoId(5))
				.uomId(6)
				.vendorProductInfo(vendorProductInfo)
				.profitInfo(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.qtyToPurchase(PURCHASE_CANDIDATE_QTY_TO_PURCHASE)
				.dateRequired(PURCHASE_CANDIDATE_DATE_REQUIRED)
				.processed(false)
				.locked(false)
				.build();
	}
}
