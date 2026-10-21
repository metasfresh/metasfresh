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
import de.metas.business.BusinessTestHelper;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link C_OrderLine#validateProductIsPurchasedOrSold(I_C_OrderLine)} — the product life-cycle
 * status must block selling a Gesperrt product on a sales-order line and purchasing an Auslauf/Gesperrt
 * product on a purchase-order line. The guard is self-gating (O/null => no-op) and fires independently
 * of the {@code M_Product_EnforcePurchaseSalesFlags} SysConfig (which is off by default in this test).
 * Also tests {@link C_OrderLine#clearCalibrationOnProductChange(I_C_OrderLine)} through a save with the interceptor registered.
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

	@Nested
	class ClearCalibrationOnProductChange
	{
		private I_C_UOM uom;
		private I_C_OrderLine orderLine;

		@BeforeEach
		void beforeEach()
		{
			uom = BusinessTestHelper.createUomEach();
			orderLine = newOrderLine(true, createProductWithUOM());
			orderLine.setC_UOM_ID(uom.getC_UOM_ID());

			final I_C_BPartner customer = newInstance(I_C_BPartner.class);
			save(customer);
			final I_C_Order order = orderLine.getC_Order();
			order.setC_BPartner_ID(customer.getC_BPartner_ID());
			order.setDocStatus(X_C_Order.DOCSTATUS_Drafted);
			save(order);
			orderLine.setC_CompensationGroup_CalibrationRule_ID(10);
			orderLine.setGroupCompensationQtyEnteredUncalibrated(new BigDecimal("375"));
			orderLine.setGroupCompensationCalibrationFactor(new BigDecimal("0.8"));
			orderLine.setQtyEntered(new BigDecimal("300"));
			save(orderLine);

			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(interceptor);
		}

		private I_M_Product createProductWithUOM()
		{
			final I_M_Product product = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK);
			product.setC_UOM_ID(uom.getC_UOM_ID());
			save(product);
			return product;
		}

		@Test
		void productChanged_clearsCalibration_keepsQty()
		{
			orderLine.setM_Product_ID(createProductWithUOM().getM_Product_ID());
			save(orderLine);

			assertThat(orderLine.getC_CompensationGroup_CalibrationRule_ID()).isLessThanOrEqualTo(0);
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_GroupCompensationQtyEnteredUncalibrated)).isNull();
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(orderLine, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor)).isNull();
			assertThat(orderLine.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void otherColumnChanged_keepsCalibration()
		{
			orderLine.setDescription("changed");
			save(orderLine);

			assertThat(orderLine.getC_CompensationGroup_CalibrationRule_ID()).isEqualTo(10);
			assertThat(orderLine.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
			assertThat(orderLine.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("0.8");
		}
	}
}
