package de.metas.inout.model.validator;

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

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOut;
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
 * Tests {@link M_InOutLine#assertProductAllowedForShipment(I_M_InOutLine)} — a life-cycle-blocked
 * product must not be added to a shipment line, while receipts stay unaffected.
 */
public class M_InOutLineTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		//
		// Install the interceptor under test
		POJOLookupMap.get().addModelValidator(new M_InOutLine(mock(MatchInvoiceService.class)));
	}

	private I_M_Product createProduct(final String productLifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("product-" + productLifeCycleStatus);
		product.setProductLifeCycleStatus(productLifeCycleStatus);
		save(product);
		return product;
	}

	private I_M_InOut createInOut(final boolean isSOTrx)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setIsSOTrx(isSOTrx);
		save(inOut);
		return inOut;
	}

	private I_M_InOutLine newInOutLine(final I_M_InOut inOut, final I_M_Product product)
	{
		final I_M_InOutLine inOutLine = newInstance(I_M_InOutLine.class);
		inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
		inOutLine.setM_Product_ID(product.getM_Product_ID());
		return inOutLine;
	}

	@Test
	public void shipmentLine_blockedProduct_throws()
	{
		final I_M_InOut shipment = createInOut(true); // isSOTrx = shipment
		final I_M_Product blockedProduct = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Gesperrt); // "G" blocks everything incl. SHIP
		final I_M_InOutLine inOutLine = newInOutLine(shipment, blockedProduct);

		assertThatThrownBy(() -> save(inOutLine))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void receiptLine_blockedProduct_doesNotThrow()
	{
		final I_M_InOut receipt = createInOut(false); // isSOTrx=false = receipt, not blocked
		final I_M_Product blockedProduct = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Gesperrt);
		final I_M_InOutLine inOutLine = newInOutLine(receipt, blockedProduct);

		assertDoesNotThrow(() -> save(inOutLine));
	}

	@Test
	public void shipmentLine_okProduct_doesNotThrow()
	{
		final I_M_InOut shipment = createInOut(true);
		final I_M_Product okProduct = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK); // "O" allows SHIP
		final I_M_InOutLine inOutLine = newInOutLine(shipment, okProduct);

		assertDoesNotThrow(() -> save(inOutLine));
	}
}
