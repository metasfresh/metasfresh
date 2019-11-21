package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;

import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.expectations.InOutLineExpectation;
import de.metas.inoutcandidate.spi.impl.ReceiptQty;
import de.metas.inoutcandidate.spi.impl.ReceiptQtyExpectation;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;

public class InOutCandidateBLTest
{
	private InOutCandidateBL inOutCandidateBL;
	private IContextAware context;

	private UomId stockUomId;
	private ProductId productId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = PlainContextAware.newOutOfTrx();

		this.inOutCandidateBL = new InOutCandidateBL();

		final I_C_UOM stockUomRecord = newInstance(I_C_UOM.class);
		saveRecord(stockUomRecord);
		stockUomId = UomId.ofRepoId(stockUomRecord.getC_UOM_ID());

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

	}

	@Test
	public void test_getQtyAndQuality_NotInDispute()
	{
		final StockQtyAndUOMQty qtys_33 = StockQtyAndUOMQtys.create(
				new BigDecimal("33"), productId,
				new BigDecimal("33"), stockUomId);

		final I_M_InOutLine inoutLine = new InOutLineExpectation<>(null, context)
				.qtys(qtys_33)
				.inDispute(false)
				.qualityNote("note 1. note 2. note 3")
				.createInOutLine(I_M_InOutLine.class);

		final ReceiptQty qtys = inOutCandidateBL.getQtyAndQuality(inoutLine);
		ReceiptQtyExpectation.newInstance()
				.qty("33")
				.qtyWithIssuesExact("0")
				.qualityNotices("note 1. note 2. note 3")
				.assertExpected(qtys);
	}

	@Test
	public void test_getQtyAndQuality_InDispute()
	{
		final StockQtyAndUOMQty qtys_33 = StockQtyAndUOMQtys.create(
				new BigDecimal("33"), productId,
				new BigDecimal("33"), stockUomId);

		final I_M_InOutLine inoutLine = new InOutLineExpectation<>(null, context)
				.qtys(qtys_33)
				.inDispute(true)
				.qualityNote("note 1. note 2. note 3")
				.createInOutLine(I_M_InOutLine.class);

		final ReceiptQty qtys = inOutCandidateBL.getQtyAndQuality(inoutLine);
		ReceiptQtyExpectation.newInstance()
				.qty("33")
				.qtyWithIssuesExact("33")
				.qualityNotices("note 1. note 2. note 3")
				.assertExpected(qtys);
	}

}
