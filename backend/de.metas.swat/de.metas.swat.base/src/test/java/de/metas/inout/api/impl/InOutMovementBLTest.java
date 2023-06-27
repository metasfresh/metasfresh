package de.metas.inout.api.impl;

import de.metas.acct.api.ProductActivityProvider;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_M_Movement;
import de.metas.product.IProductActivityProvider;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

public class InOutMovementBLTest
{

	private static final BigDecimal THIRTEEN = new BigDecimal(13);
	private static final BigDecimal THREEHUNDRED = new BigDecimal(300);
	private I_M_Warehouse warehouseForIssues;
	private I_M_Locator locatorForIssues;
	private InOutMovementBL inOutMovementBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		warehouseForIssues = newInstanceOutOfTrx(I_M_Warehouse.class);
		warehouseForIssues.setIsIssueWarehouse(true);
		warehouseForIssues.setName("Warehouse for Issues");
		warehouseForIssues.setValue("Warehouse for Issues");
		save(warehouseForIssues);

		locatorForIssues = createLocator(warehouseForIssues);

		Services.registerService(IProductActivityProvider.class, ProductActivityProvider.createInstanceForUnitTesting());

		inOutMovementBL = new InOutMovementBL();
	}

	@Test
	public void generateMovementFromReceiptLines_SameLocator_NoIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");
		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);
		save(receipt);

		final BigDecimal qty1 = THREEHUNDRED;
		createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		final List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt, I_M_InOutLine.class);

		// invoke the method under test
		final List<I_M_Movement> movements = inOutMovementBL.generateMovementFromReceiptLines(receiptLines, LocatorId.ofRecordOrNull(receiptLocator));

		assertThat(movements)
				.as("No movement shall be created is the warehouses from receipt are identical")
				.isEmpty();
	}

	@Test
	public void generateMovementFromReceiptLines_SameWarehouse_WithIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);
		save(receipt);

		final BigDecimal qty1 = THREEHUNDRED;
		createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		final BigDecimal qty2 = THIRTEEN;
		final I_M_InOutLine receiptLine = createReceiptLine("Product2", receiptLocator, receipt, qty2, true);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt, I_M_InOutLine.class);

		// invoke the method under test
		final List<I_M_Movement> movements = inOutMovementBL.generateMovementFromReceiptLines(receiptLines, LocatorId.ofRecordOrNull(receiptLocator));

		Assertions.assertEquals(1, movements.size(), "1 movements shall be created");

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movements.get(0));

		Assertions.assertEquals(1, movementLines.size(), "1 movement line shall be created");

		Assertions.assertEquals(receiptLine.getMovementQty(), movementLines.get(0).getMovementQty(), "Wrong qty in movement line");
		Assertions.assertEquals(receiptLine.getM_Product_ID(), movementLines.get(0).getM_Product_ID(), "Wrong product in movement line");
		Assertions.assertEquals(receiptLocator, movementLines.get(0).getM_Locator(), "Wrong locator in movement line");
		Assertions.assertEquals(locatorForIssues, movementLines.get(0).getM_LocatorTo(), "Wrong locator To in movement line");

	}

	@Test
	public void generateMovementFromReceiptLines_1MovementWithoutIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");
		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_Locator destinationLotator = createLocator(receiptWarehouse);
		destinationLotator.setX("A");
		save(destinationLotator);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty1 = THREEHUNDRED;
		final I_M_InOutLine receiptLine = createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt, I_M_InOutLine.class);

		// invoke the method under test
		final List<I_M_Movement> movements = inOutMovementBL.generateMovementFromReceiptLines(receiptLines, LocatorId.ofRecordOrNull(destinationLotator));

		Assertions.assertEquals(1, movements.size(), "1 movements shall be created");

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movements.get(0));

		Assertions.assertEquals(1, movementLines.size(), "1 movement line shall be created");

		Assertions.assertEquals(receiptLine.getMovementQty(), movementLines.get(0).getMovementQty(), "Wrong qty in movement line");
		Assertions.assertEquals(receiptLine.getM_Product_ID(), movementLines.get(0).getM_Product_ID(), "Wrong product in movement line");
		Assertions.assertEquals(receiptLocator, movementLines.get(0).getM_Locator(), "Wrong locator in movement line");
	}

	@Test
	public void generateMovementFromReceiptLines_1MovementWithIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty2 = THIRTEEN;
		final I_M_InOutLine receiptLine = createReceiptLine("Product2", receiptLocator, receipt, qty2, true);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt, I_M_InOutLine.class);

		// invoke the method under test
		final List<I_M_Movement> movements = inOutMovementBL.generateMovementFromReceiptLines(receiptLines, null);

		Assertions.assertEquals(1, movements.size(), "1 movements shall be created");

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movements.get(0));

		Assertions.assertEquals(1, movementLines.size(), "1 movement line shall be created");

		Assertions.assertEquals(receiptLine.getMovementQty(), movementLines.get(0).getMovementQty(), "Wrong qty in movement line");
		Assertions.assertEquals(receiptLine.getM_Product_ID(), movementLines.get(0).getM_Product_ID(), "Wrong product in movement line");
		Assertions.assertEquals(receiptLocator, movementLines.get(0).getM_Locator(), "Wrong locator in movement line");
		Assertions.assertEquals(locatorForIssues, movementLines.get(0).getM_LocatorTo(), "Wrong locator To in movement line");
	}

	/**
	 * Create a receipt with on "normal" and one "inDispute" line.
	 * Expect two movements (one towards the inDispute-warehouse).
	 */
	@Test
	public void generateMovementFromReceiptLinesr_2Movements()
	{
		final I_M_Warehouse destinationWarehouse = createWarehouse("Destination Warehouse");
		final LocatorId destinationId = LocatorId.ofRecordOrNull(createLocator(destinationWarehouse));

		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");
		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);
		final I_M_InOut receipt = createReceipt(receiptLocator);

		createReceiptLine("Product1", receiptLocator, receipt, THREEHUNDRED, false);
		createReceiptLine("Product2", receiptLocator, receipt, THIRTEEN, true);

		final List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt, I_M_InOutLine.class);

		// invoke the method under test
		final List<I_M_Movement> movements = inOutMovementBL.generateMovementFromReceiptLines(receiptLines, destinationId);
		assertThat(movements).as("2 movements shall be created").hasSize(2);

		Assertions.assertEquals(receipt, movements.get(0).getM_InOut(), "M_InOut_ID shall be set in the movements");
		Assertions.assertEquals(receipt, movements.get(1).getM_InOut(), "M_InOut_ID shall be set in the movements");
	}

	private I_M_InOut createReceipt(final I_M_Locator receiptLocator)
	{
		final I_C_BPartner receiptPartner = createBPartner("Receipt Partner");

		// NOTE: we need to use some dummy transaction, else movement generation will fail
		final String trxName = Services.get(ITrxManager.class).createTrxName("DummyTrx", true);

		final I_M_InOut receipt = create(Env.getCtx(), I_M_InOut.class, trxName);
		receipt.setAD_Org_ID(receiptLocator.getAD_Org_ID());
		receipt.setM_Warehouse_ID(receiptLocator.getM_Warehouse_ID());
		receipt.setC_BPartner_ID(receiptPartner.getC_BPartner_ID());
		save(receipt);

		return receipt;
	}

	private de.metas.inout.model.I_M_InOutLine createReceiptLine(final String productName,
			final I_M_Locator locator,
			final I_M_InOut receipt,
			final BigDecimal qty,
			final boolean isInDispute)
	{
		final I_M_Product product = createProduct(productName, locator);

		final de.metas.inout.model.I_M_InOutLine line = newInstance(de.metas.inout.model.I_M_InOutLine.class);

		line.setAD_Org_ID(receipt.getAD_Org_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setMovementQty(qty);
		line.setIsInDispute(isInDispute);
		line.setM_InOut_ID(receipt.getM_InOut_ID());
		line.setM_Locator_ID(locator.getM_Locator_ID());
		save(line);

		return line;
	}

	private I_M_Warehouse createWarehouse(final String name)
	{
		final I_M_Warehouse wh = newInstanceOutOfTrx(I_M_Warehouse.class);
		wh.setValue(name);
		wh.setName(name);
		// wh.setAD_Org_ID(org.getAD_Org_ID());
		save(wh);
		return wh;
	}

	private I_M_Locator createLocator(final I_M_Warehouse warehouse)
	{
		final I_M_Locator locator = newInstanceOutOfTrx(I_M_Locator.class);
		locator.setAD_Org_ID(warehouse.getAD_Org_ID());
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locator.setValue(warehouse.getName() + "_Locator");
		locator.setX("X");
		locator.setY("Y");
		locator.setZ("Z");
		save(locator);
		return locator;
	}

	private I_M_Product createProduct(final String productName, final I_M_Locator locator)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue(productName);
		product.setName(productName);

		if (locator != null)
		{
			product.setM_Locator_ID(locator.getM_Locator_ID());
		}

		save(product);
		return product;
	}

	private I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bp = newInstanceOutOfTrx(I_C_BPartner.class);
		bp.setValue(name);
		bp.setName(name);
		save(bp);
		return bp;
	}

}
