package de.metas.purchasecandidate.purchaseordercreation.localorder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.event.OrderUserNotifications.ADMessageAndParams;
import de.metas.order.event.OrderUserNotifications.NotificationRequest;
import de.metas.order.impl.OrderLineBL;
import de.metas.order.model.I_C_Order;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.VendorProductInfo;
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

public class PurchaseOrderFromItemFactoryTest
{
	private static final ZonedDateTime PURCHASE_DATE_PROMISED = SystemTime.asZonedDateTime();

	private I_C_UOM EACH;
	private Quantity PURCHASE_CANDIDATE_QTY_TO_PURCHASE;

	private OrderUserNotifications orderUserNotifications;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.EACH = createUOM("Ea");
		this.PURCHASE_CANDIDATE_QTY_TO_PURCHASE = Quantity.of(BigDecimal.TEN, EACH);

		orderUserNotifications = Mockito.mock(OrderUserNotifications.class);

		// mock IOrderLineBL.updatePrices() because setting up the required masterdata and testing the pricing engine is out of scope.
		final OrderLineBL orderLineBL = new OrderLineBL()
		{
			@Override
			public void updatePrices(I_C_OrderLine orderLine)
			{
				// do nothing
			}
		};
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
	public void deviatingDatePromised()
	{
		final ZonedDateTime deviatingDatePromised = PURCHASE_DATE_PROMISED.plusDays(1);

		setAndRunMethodUnderTest(deviatingDatePromised, PURCHASE_CANDIDATE_QTY_TO_PURCHASE);

		final ArgumentCaptor<NotificationRequest> requestCaptor = ArgumentCaptor.forClass(NotificationRequest.class);
		Mockito.verify(orderUserNotifications)
				.notifyOrderCompleted(requestCaptor.capture());

		final NotificationRequest request = requestCaptor.getValue();
		final ADMessageAndParams adMessageAndParams = request.getAdMessageAndParams();
		assertThat(adMessageAndParams).isNotNull();
		assertThat(adMessageAndParams.getAdMessage()).isEqualTo(PurchaseOrderFromItemFactory.MSG_Different_DatePromised);
		assertThat(adMessageAndParams.getParams()).hasSize(3);
	}

	@Test
	public void deviatingPurchasedQty()
	{
		final Quantity deviatingPurchasedQty = PURCHASE_CANDIDATE_QTY_TO_PURCHASE.subtract(BigDecimal.ONE);

		setAndRunMethodUnderTest(PURCHASE_DATE_PROMISED, deviatingPurchasedQty);

		final ArgumentCaptor<NotificationRequest> requestCaptor = ArgumentCaptor.forClass(NotificationRequest.class);
		Mockito.verify(orderUserNotifications)
				.notifyOrderCompleted(requestCaptor.capture());

		final NotificationRequest request = requestCaptor.getValue();
		final ADMessageAndParams adMessageAndParams = request.getAdMessageAndParams();
		assertThat(adMessageAndParams).isNotNull();
		assertThat(adMessageAndParams.getAdMessage()).isEqualTo(PurchaseOrderFromItemFactory.MSG_Different_Quantity);
		assertThat(adMessageAndParams.getParams()).hasSize(3);
	}

	@Test
	public void deviatingPurchasedQtyAndDatePrmised()
	{
		final Quantity deviatingPurchasedQty = PURCHASE_CANDIDATE_QTY_TO_PURCHASE.subtract(BigDecimal.ONE);
		final ZonedDateTime deviatingDatePromised = PURCHASE_DATE_PROMISED.plusDays(1);

		setAndRunMethodUnderTest(deviatingDatePromised, deviatingPurchasedQty);

		final ArgumentCaptor<NotificationRequest> requestCaptor = ArgumentCaptor.forClass(NotificationRequest.class);
		Mockito.verify(orderUserNotifications)
				.notifyOrderCompleted(requestCaptor.capture());

		final NotificationRequest request = requestCaptor.getValue();
		final ADMessageAndParams adMessageAndParams = request.getAdMessageAndParams();
		assertThat(adMessageAndParams).isNotNull();
		assertThat(adMessageAndParams.getAdMessage()).isEqualTo(PurchaseOrderFromItemFactory.MSG_Different_Quantity_AND_DatePromised);
		assertThat(adMessageAndParams.getParams()).hasSize(3);
	}

	private void setAndRunMethodUnderTest(final ZonedDateTime deviatingDatePromised, final Quantity deviatingPurchasedQty)
	{
		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate();

		final PurchaseOrderItem pruchaseOrderItem = purchaseCandidate.createOrderItem()
				.datePromised(deviatingDatePromised)
				.purchasedQty(deviatingPurchasedQty)
				.remotePurchaseOrderId("remotePurchaseOrderId")
				.transactionReference(TableRecordReference.of("tableName", 20))
				.buildAndAddToParent();

		Services.get(ITrxManager.class).runInNewTrx(() -> {

			final PurchaseOrderFromItemFactory purchaseOrderFromItemFactory = PurchaseOrderFromItemFactory.builder()
					.orderAggregationKey(PurchaseOrderAggregationKey.fromPurchaseOrderItem(pruchaseOrderItem))
					.userNotifications(orderUserNotifications)
					.build();

			purchaseOrderFromItemFactory.addCandidate(pruchaseOrderItem);
			purchaseOrderFromItemFactory.createAndComplete();
		});
	}

	private PurchaseCandidate createPurchaseCandidate()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("product.name");
		product.setValue("product.value");
		product.setC_UOM_ID(EACH.getC_UOM_ID());
		save(product);

		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setName("vendor.name");
		vendor.setValue("vendor.value");
		save(vendor);

		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		save(salesOrder);

		final I_C_OrderLine salesOrderLine = newInstance(I_C_OrderLine.class);
		salesOrderLine.setM_Product_ID(product.getM_Product_ID());
		salesOrderLine.setC_Order(salesOrder);
		save(salesOrderLine);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.vendorId(BPartnerId.ofRepoId(vendor.getC_BPartner_ID()))
				.defaultVendor(true)
				.product(ProductAndCategoryAndManufacturerId.of(product.getM_Product_ID(), 30, 40))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(50))
				.vendorProductNo("productNo")
				.vendorProductName("productName")
				.pricingConditions(createDummyPricingConditions())
				.build();

		return PurchaseCandidate.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(salesOrder.getC_Order_ID(), salesOrderLine.getC_OrderLine_ID()))
				.orgId(OrgId.ofRepoId(3))
				.warehouseId(WarehouseId.ofRepoId(4))
				.vendorId(vendorProductInfo.getVendorId())
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(6))
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				.profitInfoOrNull(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.qtyToPurchase(PURCHASE_CANDIDATE_QTY_TO_PURCHASE)
				.purchaseDatePromised(PURCHASE_DATE_PROMISED)
				.processed(false)
				.locked(false)
				.build();
	}

	private PricingConditions createDummyPricingConditions()
	{
		return PricingConditions.builder()
				.build();
	}
}
