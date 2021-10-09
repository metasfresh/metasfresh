package de.metas.inoutcandidate.api.impl;

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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.inoutcandidate.expectations.ReceiptScheduleExpectation;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.modelvalidator.ReceiptScheduleValidator;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ReceiptScheduleQtysHandlerTest
{
	private IContextAware context;
	private I_M_ReceiptSchedule receiptSchedule;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = PlainContextAware.newOutOfTrx();

		SpringContextHolder.registerJUnitBean(IDocumentLocationBL.class, new DocumentLocationBL(new BPartnerBL(new UserRepository())));
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(ReceiptScheduleValidator.instance);

		//
		// Master data
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("test").getC_BPartner_ID());
		final WarehouseId warehouseId = WarehouseId.ofRepoId(BusinessTestHelper.createWarehouse("test").getM_Warehouse_ID());

		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("test");
		final I_M_Product productRecord = BusinessTestHelper.createProduct("test", stockUOMRecord);

		this.receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class, context);
		receiptSchedule.setM_Warehouse_ID(warehouseId.getRepoId());
		receiptSchedule.setC_BPartner_ID(bpartnerId.getRepoId());
		receiptSchedule.setM_Product_ID(productRecord.getM_Product_ID());
		InterfaceWrapperHelper.save(receiptSchedule);
	}

	@Test
	public void testQtysUpdate_Discount_FirstTime()
	{
		// Wareneingang POS, open the line for the first time
		createReceiptScheduleAlloc("20", "10");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("20")
				.qtyMovedWithIssues("10")
				.qualityDiscountPercent("50")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the second time
		createReceiptScheduleAlloc("80", "10");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("100")
				.qtyMovedWithIssues("20")
				.qualityDiscountPercent("20")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the third time
		createReceiptScheduleAlloc("100", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("200")
				.qtyMovedWithIssues("20")
				.qualityDiscountPercent("10")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 4th time
		createReceiptScheduleAlloc("100", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("300")
				.qtyMovedWithIssues("20")
				.qualityDiscountPercent("6.67")
				.assertExpected(receiptSchedule);

	}

	@Test
	public void testQtysUpdate_Not_Discount_FirstTime()
	{
		// Wareneingang POS, open the line for the first time
		createReceiptScheduleAlloc("20", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("20")
				.qtyMovedWithIssues("0")
				.qualityDiscountPercent("0")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the second time
		createReceiptScheduleAlloc("80", "20");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("100")
				.qtyMovedWithIssues("20")
				.qualityDiscountPercent("20")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the third time
		createReceiptScheduleAlloc("100", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("200")
				.qtyMovedWithIssues("20")
				.qualityDiscountPercent("10")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 4th time
		createReceiptScheduleAlloc("100", "80");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("300")
				.qtyMovedWithIssues("100")
				.qualityDiscountPercent("33.33")
				.assertExpected(receiptSchedule);

	}

	// Test case described here :
	// https://docs.google.com/spreadsheets/d/1R1_JH-rsHA2jtQbRBw7sOH4cQlFPoQ4EghuwmlvMkgU/edit#gid=0

	/**
	 * Test case described here : https://docs.google.com/spreadsheets/d/1R1_JH-rsHA2jtQbRBw7sOH4cQlFPoQ4EghuwmlvMkgU/edit#gid=0
	 */
	@Test
	public void testQtysUpdate_QualityDiscountPercent()
	{
		// Wareneingang POS, open the line for the first time
		createReceiptScheduleAlloc("625", "31.25");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("625")
				.qtyMovedWithIssues("31.25")
				.qualityDiscountPercent("5")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the second time
		createReceiptScheduleAlloc("425", "42.5");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("1050")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("7.02")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the third time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("1480")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("4.98")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 4th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("1910")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("3.86")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 5th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("2340")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("3.15")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 6th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("2770")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("2.66")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 7th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("3200")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("2.30")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 8th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("3630")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("2.03")
				.assertExpected(receiptSchedule);

		// Wareneingang POS, open the line for the 9th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("4060")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("1.82")
				.assertExpected(receiptSchedule);

		// / Wareneingang POS, open the line for the 10th time
		createReceiptScheduleAlloc("430", "0");
		InterfaceWrapperHelper.refresh(receiptSchedule);
		ReceiptScheduleExpectation.newExpectation()
				.qtyMoved("4490")
				.qtyMovedWithIssues("73.75")
				.qualityDiscountPercent("1.64")
				.assertExpected(receiptSchedule);
	}

	private I_M_ReceiptSchedule_Alloc createReceiptScheduleAlloc(
			@NonNull final String qtyAllocatedStr,
			@NonNull final String qtyWithIssuesStr)
	{
		final I_M_ReceiptSchedule_Alloc rsa = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class, receiptSchedule);
		rsa.setM_ReceiptSchedule(receiptSchedule);
		rsa.setQtyAllocated(new BigDecimal(qtyAllocatedStr));
		rsa.setQtyWithIssues(new BigDecimal(qtyWithIssuesStr));

		InterfaceWrapperHelper.save(rsa);
		return rsa;
	}
}
