package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.impl.OrderLineBL;
import de.metas.quantity.Quantity;
import de.metas.util.Services;

/**
 * Tests for the C_OrderLine model interceptor regarding TU quantity editing.
 *
 * <p>Editing {@code QtyEnteredTU} recomputes {@code QtyEntered} (CU qty) when a finite-capacity
 * packing instruction is set, and is a no-op otherwise.
 *
 * <p>On received purchase lines ({@code QtyDelivered > 0}) the recomputed {@code QtyEntered} must
 * stay ≥ {@code QtyDelivered}; a reduction below what was received is rejected.
 */
public class C_OrderLineTest
{
	/** Finite capacity: 8 CU per TU */
	private static final BigDecimal CAPACITY_8 = new BigDecimal("8");

	private HUTestHelper helper;
	private I_M_Product product;
	private I_C_UOM uom;
	private I_M_HU_PI_Item_Product pip;

	@BeforeEach
	public void beforeEach()
	{
		// HUTestHelper() calls AdempiereTestHelper.get().init() + registers all HU Spring beans
		helper = HUTestHelper.newInstanceOutOfTrx();

		// Register a mock IOrderLineBL that skips the pricing logic,
		// mirroring OrderPackingMaterialDocumentLinesBuilderTest
		final OrderLineBL orderLineBL = Mockito.spy(new OrderLineBL());
		Mockito.doNothing().when(orderLineBL).updatePrices(any(OrderLinePriceUpdateRequest.class));
		Mockito.doNothing().when(orderLineBL).updateLineNetAmtFromQtyEntered(any());
		Services.registerService(IOrderLineBL.class, orderLineBL);

		// UOM: piece (helper.uomEach is the standard "Each" in HUTestHelper)
		uom = helper.uomEach;

		// Product with the piece UOM
		final I_M_Product prod = newInstance(I_M_Product.class);
		prod.setC_UOM_ID(uom.getC_UOM_ID());
		prod.setValue("TEST_PRODUCT");
		prod.setName("Test Product");
		saveRecord(prod);
		product = prod;

		// Build a TU packing instruction with finite capacity 8 CU/TU
		final de.metas.handlingunits.model.I_M_HU_PI piTU =
				helper.createHUDefinition("TestTU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piItem = helper.createHU_PI_Item_Material(piTU);
		pip = helper.assignProduct(piItem, product, CAPACITY_8, uom);
	}

	/**
	 * Editing QtyEnteredTU (to 6) on a purchase order line that has a finite-capacity PIP
	 * (8 CU/TU) must recompute QtyEntered to 6 × 8 = 48.
	 */
	@Test
	public void qtyEnteredTU_edit_recomputes_qtyEntered()
	{
		// Arrange: purchase order (IsSOTrx = false)
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		saveRecord(order);

		// Create the order line BEFORE registering the validator so the initial save avoids
		// the pre-existing add_M_HU_PI_Item_Product BEFORE_NEW path (IHUOrderBL / doc-handler /
		// pricing services unrelated to the TU fix). The new updateQtyCUFromQtyTU fires only on
		// BEFORE_CHANGE, so registering the validator afterwards is sufficient.
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order(order);
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setM_HU_PI_Item_Product(pip);
		orderLine.setQtyEntered(new BigDecimal("480"));
		orderLine.setQtyEnteredTU(new BigDecimal("60")); // 480 / 8 = 60 TUs
		orderLine.setQtyDelivered(BigDecimal.ZERO);
		saveRecord(orderLine);  // BEFORE registering the validator

		// Now register the interceptor so it fires on the NEXT save
		POJOLookupMap.get().addModelValidator(new C_OrderLine());

		// Act: change TU quantity to 6 and save
		orderLine.setQtyEnteredTU(new BigDecimal("6"));
		saveRecord(orderLine);

		// Reload to get the persisted value
		final I_C_OrderLine reloaded = load(orderLine.getC_OrderLine_ID(), I_C_OrderLine.class);

		// Assert: QtyEntered recomputed to 6 × 8 = 48
		assertThat(reloaded.getQtyEntered())
				.as("QtyEntered should be recomputed to 6 TU × 8 CU/TU = 48 CU")
				.isEqualByComparingTo("48");
	}

	/**
	 * When there is NO finite packing instruction on the order line,
	 * editing QtyEnteredTU must NOT change QtyEntered.
	 */
	@Test
	public void qtyEnteredTU_edit_noop_without_finite_capacity()
	{
		// Arrange: purchase order
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		saveRecord(order);

		// Order line with NO packing instruction (no M_HU_PI_Item_Product)
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order(order);
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		// intentionally NO M_HU_PI_Item_Product set (virtual / infinite capacity)
		orderLine.setQtyEntered(new BigDecimal("480"));
		orderLine.setQtyDelivered(BigDecimal.ZERO);
		saveRecord(orderLine);  // BEFORE registering the validator

		// Register the interceptor
		POJOLookupMap.get().addModelValidator(new C_OrderLine());

		// Act: change TU qty — with no finite PIP this should be a no-op for QtyEntered
		orderLine.setQtyEnteredTU(new BigDecimal("6"));
		saveRecord(orderLine);

		// Reload and verify QtyEntered is unchanged
		final I_C_OrderLine reloaded = load(orderLine.getC_OrderLine_ID(), I_C_OrderLine.class);

		assertThat(reloaded.getQtyEntered())
				.as("QtyEntered must NOT change when there is no finite packing instruction")
				.isEqualByComparingTo("480");
	}

	/**
	 * Partially-received line where the recomputed QtyEntered stays above QtyDelivered.
	 *
	 * <p>QtyDelivered = 40, PIP capacity 8. Edit QtyEnteredTU = 6 → recomputed QtyEntered = 48 ≥ 40 → save succeeds.
	 */
	@Test
	public void qtyEnteredTU_edit_recomputes_on_partially_received_line_when_above_delivered()
	{
		// Arrange: purchase order
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		saveRecord(order);

		// Order line with QtyDelivered = 40 (partially received)
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order(order);
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setM_HU_PI_Item_Product(pip);
		orderLine.setQtyEntered(new BigDecimal("480"));
		orderLine.setQtyEnteredTU(new BigDecimal("60"));
		orderLine.setQtyDelivered(new BigDecimal("40"));
		saveRecord(orderLine); // BEFORE registering the validator

		// Stub the spied OrderLineBL methods used by validateQtyEntered:
		// convertQtyEnteredToStockUOM returns the orderLine's QtyEntered in the piece UOM (identity)
		// getQtyDelivered returns 40 in the piece UOM
		final OrderLineBL orderLineBL = (OrderLineBL)Services.get(IOrderLineBL.class);
		Mockito.doAnswer(inv -> Quantity.of(inv.<org.compiere.model.I_C_OrderLine>getArgument(0).getQtyEntered(), uom))
				.when(orderLineBL).convertQtyEnteredToStockUOM(any());
		Mockito.doReturn(Quantity.of(new BigDecimal("40"), uom))
				.when(orderLineBL).getQtyDelivered(any(OrderAndLineId.class));

		// Register the interceptor
		POJOLookupMap.get().addModelValidator(new C_OrderLine());

		// Act: change TU qty to 6 → recomputed QtyEntered = 6 × 8 = 48 ≥ 40 → must succeed
		orderLine.setQtyEnteredTU(new BigDecimal("6"));
		saveRecord(orderLine);

		// Assert: QtyEntered recomputed to 48
		final I_C_OrderLine reloaded = load(orderLine.getC_OrderLine_ID(), I_C_OrderLine.class);
		assertThat(reloaded.getQtyEntered())
				.as("QtyEntered should be recomputed to 6 TU × 8 CU/TU = 48 CU (≥ QtyDelivered 40 → save succeeds)")
				.isEqualByComparingTo("48");
	}

	/**
	 * Partially-received line where the recomputed QtyEntered would fall below QtyDelivered.
	 *
	 * <p>QtyDelivered = 40, PIP capacity 8. Edit QtyEnteredTU = 4 → recomputed QtyEntered = 32 < 40 → must throw.
	 */
	@Test
	public void qtyEnteredTU_edit_rejected_on_partially_received_line_when_below_delivered()
	{
		// Arrange: purchase order
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);
		saveRecord(order);

		// Order line with QtyDelivered = 40 (partially received)
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order(order);
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setM_HU_PI_Item_Product(pip);
		orderLine.setQtyEntered(new BigDecimal("480"));
		orderLine.setQtyEnteredTU(new BigDecimal("60"));
		orderLine.setQtyDelivered(new BigDecimal("40"));
		saveRecord(orderLine); // BEFORE registering the validator

		// Stub the spied OrderLineBL methods used by validateQtyEntered
		final OrderLineBL orderLineBL = (OrderLineBL)Services.get(IOrderLineBL.class);
		Mockito.doAnswer(inv -> Quantity.of(inv.<org.compiere.model.I_C_OrderLine>getArgument(0).getQtyEntered(), uom))
				.when(orderLineBL).convertQtyEnteredToStockUOM(any());
		Mockito.doReturn(Quantity.of(new BigDecimal("40"), uom))
				.when(orderLineBL).getQtyDelivered(any(OrderAndLineId.class));

		// Register the interceptor
		POJOLookupMap.get().addModelValidator(new C_OrderLine());

		// Act + Assert: QtyEnteredTU = 4 → recomputed QtyEntered = 32 < 40 → must throw
		orderLine.setQtyEnteredTU(new BigDecimal("4"));
		assertThatThrownBy(() -> saveRecord(orderLine))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyEntered")
				.hasMessageContaining("QtyDelivered");
	}
}
