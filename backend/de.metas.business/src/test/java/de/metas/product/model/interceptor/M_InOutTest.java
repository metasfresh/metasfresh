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
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests {@link M_InOut#assertProductsAllowedForShipment(I_M_InOut)} — the product life-cycle status
 * must block COMPLETING a sales shipment that carries a Lieferstopp ('N') or Gesperrt ('G') product,
 * while:
 * <ul>
 *     <li>receipts (IsSOTrx=false) are unaffected, and</li>
 *     <li>reversal/void documents (Reversal_ID set) are exempt — an already-completed shipment must be
 *     reversible regardless of the product's current status (no retroactive invalidation).</li>
 * </ul>
 */
public class M_InOutTest
{
	private M_InOut interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
		interceptor = new M_InOut();
	}

	private I_M_Product createProduct(final String productLifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("product-" + productLifeCycleStatus);
		product.setProductLifeCycleStatus(productLifeCycleStatus);
		save(product);
		return product;
	}

	private I_M_InOut createInOut(final boolean isSOTrx, final int reversalId)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setIsSOTrx(isSOTrx);
		if (reversalId > 0)
		{
			inOut.setReversal_ID(reversalId);
		}
		save(inOut);
		return inOut;
	}

	private void addLine(final I_M_InOut inOut, final I_M_Product product)
	{
		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);
		line.setM_InOut_ID(inOut.getM_InOut_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		save(line);
	}

	@Test
	public void shipment_gesperrtProduct_throws()
	{
		final I_M_InOut shipment = createInOut(true, 0);
		addLine(shipment, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked)); // "G" blocks SHIP
		assertThatThrownBy(() -> interceptor.assertProductsAllowedForShipment(shipment))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void shipment_lieferstoppProduct_throws()
	{
		final I_M_InOut shipment = createInOut(true, 0);
		addLine(shipment, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_DeliveryStop)); // "N" blocks SHIP specifically
		assertThatThrownBy(() -> interceptor.assertProductsAllowedForShipment(shipment))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void shipment_okProduct_doesNotThrow()
	{
		final I_M_InOut shipment = createInOut(true, 0);
		addLine(shipment, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK)); // "O" allows SHIP
		assertDoesNotThrow(() -> interceptor.assertProductsAllowedForShipment(shipment));
	}

	@Test
	public void receipt_gesperrtProduct_doesNotThrow()
	{
		final I_M_InOut receipt = createInOut(false, 0); // isSOTrx=false = receipt, not a shipment
		addLine(receipt, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));
		assertDoesNotThrow(() -> interceptor.assertProductsAllowedForShipment(receipt));
	}

	@Test
	public void reversalShipment_gesperrtProduct_doesNotThrow()
	{
		// A reversal document (Reversal_ID set) of an already-completed shipment must never be blocked,
		// even if the product has since become Gesperrt/Lieferstopp — no retroactive invalidation.
		final I_M_InOut reversalShipment = createInOut(true, 999);
		addLine(reversalShipment, createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked));
		assertDoesNotThrow(() -> interceptor.assertProductsAllowedForShipment(reversalShipment));
	}
}
