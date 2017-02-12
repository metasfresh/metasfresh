package de.metas.document.archive.async.spi.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_Test;
import org.compiere.model.PrintInfo;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * #%L
 * de.metas.document.archive.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */



public class DocOutboundWorkpackageProcessorTest
{
	@BeforeClass
	public static void staticInit()
	{
		Adempiere.enableUnitTestMode();
	}

	private DocOutboundWorkpackageProcessor processor;

	@Before
	public void init()
	{
		processor = new DocOutboundWorkpackageProcessor();
	}
	
	private PrintInfo createPrintInfo(final Object record)
	{
		return processor.createModelArchiver(record).createPrintInfo();
	}

	@Test
	public void test_createPrintInfo_fromInvoice()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo("ExpectedDocumentNo");
		invoice.setC_BPartner_ID(12345);
		InterfaceWrapperHelper.save(invoice);

		final PrintInfo printInfo = createPrintInfo(invoice);
		Assert.assertEquals("Invalid DocumentNo", "ExpectedDocumentNo", printInfo.getName());
		Assert.assertEquals("Invalid AD_Table_ID", InterfaceWrapperHelper.getTableId(I_C_Invoice.class), printInfo.getAD_Table_ID());
		Assert.assertEquals("Invalid Record_ID", invoice.getC_Invoice_ID(), printInfo.getRecord_ID());
		Assert.assertEquals("Invalid C_BPartner_ID", invoice.getC_BPartner_ID(), printInfo.getC_BPartner_ID());
	}

	@Test
	public void test_createPrintInfo_fromTestRecord()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		record.setName("ExpectedDocumentNo");
		record.setC_BPartner_ID(12345);
		InterfaceWrapperHelper.save(record);

		final PrintInfo printInfo = createPrintInfo(record);
		Assert.assertEquals("Invalid DocumentNo", "ExpectedDocumentNo", printInfo.getName());
		Assert.assertEquals("Invalid AD_Table_ID", InterfaceWrapperHelper.getTableId(I_Test.class), printInfo.getAD_Table_ID());
		Assert.assertEquals("Invalid Record_ID", record.getTest_ID(), printInfo.getRecord_ID());
		Assert.assertEquals("Invalid C_BPartner_ID", record.getC_BPartner_ID(), printInfo.getC_BPartner_ID());
	}

}
