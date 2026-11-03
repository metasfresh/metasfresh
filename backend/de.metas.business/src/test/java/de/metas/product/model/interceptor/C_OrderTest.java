package de.metas.product.model.interceptor;

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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests {@link C_Order#assertProductsAllowedOnComplete(I_C_Order)} — the re-check that runs when an order is
 * COMPLETED, catching products whose life-cycle status was flipped to a blocking value <i>after</i> the line
 * was created (the line-creation guard in {@code de.metas.order.model.interceptor.C_OrderLine} cannot see that
 * later flip).
 * <p>
 * A sales order checks {@code SELL}, a purchase order checks {@code PURCHASE}; lines with no product are
 * skipped, and {@code O}/null products are inert.
 */
public class C_OrderTest
{
	private C_Order interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
		interceptor = new C_Order();
	}

	private I_M_Product createProduct(final String productLifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("product-" + productLifeCycleStatus);
		product.setProductLifeCycleStatus(productLifeCycleStatus);
		save(product);
		return product;
	}

	private I_C_Order createOrder(final boolean isSOTrx)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(isSOTrx);
		save(order);
		return order;
	}

	private void addLine(final I_C_Order order, @Nullable final I_M_Product product)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		if (product != null)
		{
			line.setM_Product_ID(product.getM_Product_ID());
		}
		save(line);
	}

	@Test
	public void salesOrder_gesperrtProduct_throws()
	{
		final I_C_Order order = createOrder(true);
		addLine(order, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK));
		addLine(order, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked)); // "G" blocks SELL

		assertThatThrownBy(() -> interceptor.assertProductsAllowedOnComplete(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	public void purchaseOrder_auslaufProduct_throws()
	{
		final I_C_Order order = createOrder(false);
		addLine(order, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut)); // "A" blocks PURCHASE

		assertThatThrownBy(() -> interceptor.assertProductsAllowedOnComplete(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	public void salesOrder_auslaufProduct_doesNotThrow()
	{
		// "A" blocks PURCHASE, not SELL — a sales order must still complete.
		final I_C_Order order = createOrder(true);
		addLine(order, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut));

		assertDoesNotThrow(() -> interceptor.assertProductsAllowedOnComplete(order));
	}

	@Test
	public void salesOrder_okProduct_doesNotThrow()
	{
		final I_C_Order order = createOrder(true);
		addLine(order, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK));

		assertDoesNotThrow(() -> interceptor.assertProductsAllowedOnComplete(order));
	}

	@Test
	public void order_lineWithoutProduct_doesNotThrow()
	{
		final I_C_Order order = createOrder(true);
		addLine(order, null);

		assertDoesNotThrow(() -> interceptor.assertProductsAllowedOnComplete(order));
	}
}
