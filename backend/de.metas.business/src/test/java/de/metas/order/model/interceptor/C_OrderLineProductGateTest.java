package de.metas.order.model.interceptor;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class C_OrderLineProductGateTest
{
	private IOrderBL orderBL;
	private IProductBL productBL;
	private C_OrderLine interceptor;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		orderBL = mock(IOrderBL.class);
		productBL = mock(IProductBL.class);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IProductBL.class, productBL);

		Services.registerService(IProgramaticCalloutProvider.class, mock(IProgramaticCalloutProvider.class));

		// Gate is ON by default so existing throw-tests keep their behavior
		when(productBL.isPurchaseSalesEnforcementEnabled(any(ClientId.class), any(OrgId.class))).thenReturn(true);

		interceptor = new C_OrderLine(
				mock(de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler.class),
				mock(de.metas.order.impl.OrderLineDetailRepository.class),
				mock(de.metas.bpartner.BPartnerSupplierApprovalService.class));
	}

	private I_C_OrderLine orderLine(final boolean soTrx, final boolean purchased, final boolean sold)
	{
		final I_M_Product p = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		p.setValue("P");
		p.setName("P");
		p.setIsPurchased(purchased);
		p.setIsSold(sold);
		InterfaceWrapperHelper.saveRecord(p);

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsSOTrx(soTrx);
		InterfaceWrapperHelper.saveRecord(order);

		final I_C_OrderLine ol = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		ol.setC_Order_ID(order.getC_Order_ID());
		ol.setM_Product_ID(p.getM_Product_ID());
		InterfaceWrapperHelper.saveRecord(ol);

		// Stub orderBL to return the real in-memory order for any OrderId
		when(orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()))).thenReturn(order);

		// Stub productBL: assertSellable throws if !sold; assertPurchasable throws if !purchased
		final ProductId productId = ProductId.ofRepoId(p.getM_Product_ID());
		if (!sold)
		{
			doThrow(new AdempiereException("Product not sold"))
					.when(productBL).assertSellable(productId);
		}
		if (!purchased)
		{
			doThrow(new AdempiereException("Product not purchased"))
					.when(productBL).assertPurchasable(productId);
		}

		return ol;
	}

	@Nested
	class SalesOrder
	{
		@Test
		void notSold_throws()
		{
			assertThatThrownBy(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(true, true, false)))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void notPurchasedButSold_doesNotThrow()
		{
			assertThatCode(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(true, false, true)))
					.doesNotThrowAnyException();
		}
	}

	@Test
	void purchaseOrder_notPurchased_throws()
	{
		assertThatThrownBy(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(false, false, true)))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void allowed_doesNotThrow()
	{
		assertThatCode(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(true, true, true)))
				.doesNotThrowAnyException();
		assertThatCode(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(false, true, true)))
				.doesNotThrowAnyException();
	}

	@Test
	void gateDisabled_doesNotThrow()
	{
		// Gate OFF — enforcement must be inert regardless of IsSold/IsPurchased flags
		when(productBL.isPurchaseSalesEnforcementEnabled(any(ClientId.class), any(OrgId.class))).thenReturn(false);

		assertThatCode(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(true, true, false)))
				.doesNotThrowAnyException();
		assertThatCode(() -> interceptor.validateProductIsPurchasedOrSold(orderLine(false, false, true)))
				.doesNotThrowAnyException();
	}
}
