package de.metas.order.model.interceptor;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.impl.OrderLineDetailRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link C_OrderLine#validateProductIsPurchasedOrSold(I_C_OrderLine)} — the product life-cycle
 * status must block selling a Gesperrt product on a sales-order line and purchasing an Auslauf/Gesperrt
 * product on a purchase-order line. The guard is self-gating (O/null => no-op) and fires independently
 * of the {@code M_Product_EnforcePurchaseSalesFlags} SysConfig (which is off by default in this test).
 */
public class C_OrderLineTest
{
	private C_OrderLine interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());

		interceptor = new C_OrderLine(
				mock(OrderGroupCompensationChangesHandler.class),
				mock(OrderLineDetailRepository.class),
				mock(BPartnerSupplierApprovalService.class));
	}

	private I_M_Product createProduct(final String productLifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("product-" + productLifeCycleStatus);
		product.setProductLifeCycleStatus(productLifeCycleStatus);
		save(product);
		return product;
	}

	private I_C_OrderLine newOrderLine(final boolean isSOTrx, final I_M_Product product)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(isSOTrx);
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		return orderLine;
	}

	@Test
	public void salesLine_gesperrtProduct_throws()
	{
		// G (Gesperrt) blocks SELL
		final I_C_OrderLine orderLine = newOrderLine(true, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));
		assertThatThrownBy(() -> interceptor.validateProductIsPurchasedOrSold(orderLine))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void purchaseLine_auslaufProduct_throws()
	{
		// A (Auslauf) blocks PURCHASE; purchase order line => isSOTrx=false
		final I_C_OrderLine orderLine = newOrderLine(false, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut));
		assertThatThrownBy(() -> interceptor.validateProductIsPurchasedOrSold(orderLine))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void salesLine_auslaufProduct_doesNotThrow()
	{
		// A allows SELL — must NOT over-block on a sales-order line
		final I_C_OrderLine orderLine = newOrderLine(true, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut));
		assertDoesNotThrow(() -> interceptor.validateProductIsPurchasedOrSold(orderLine));
	}

	@Test
	public void salesLine_okProduct_doesNotThrow()
	{
		// O (OK) allows everything
		final I_C_OrderLine orderLine = newOrderLine(true, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK));
		assertDoesNotThrow(() -> interceptor.validateProductIsPurchasedOrSold(orderLine));
	}
}
