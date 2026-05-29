package de.metas.document.engine.impl;

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

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_Test;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class DocumentBLTest
{
	private interface INonDocumentWithDocumentNo
	{
		@SuppressWarnings("unused")
		String Table_Name = "NonDocumentWithDocumentNo";

		int getNonDocumentWithDocumentNo_ID();

		String getDocumentNo();

		void setDocumentNo(String documentNo);
	}

	private interface INonDocumentWithValueAndName
	{
		@SuppressWarnings("unused")
		String Table_Name = "NonDocumentWithValueAndName";

		int getNonDocumentWithValueAndName_ID();

		String getValue();

		void setValue(String value);

		String getName();

		void setName(String name);
	}

	private PlainDocumentBL documentBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		documentBL = (PlainDocumentBL)Services.get(IDocumentBL.class);
	}

	@Test
	public void test_getDocumentOrNull_Document()
	{
		test_getDocumentOrNull(I_C_Invoice.class, true);
		test_getDocumentOrNull(I_C_Order.class, true);
		test_getDocumentOrNull(I_M_InOut.class, true);
	}

	@Test
	public void test_getDocumentOrNull_NonDocument()
	{
		test_getDocumentOrNull(I_Test.class, false);
		test_getDocumentOrNull(INonDocumentWithDocumentNo.class, false);
	}

	private void test_getDocumentOrNull(final Class<?> clazz, boolean expectDocument)
	{
		final Object record = InterfaceWrapperHelper.create(Env.getCtx(), clazz, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		final IDocument document = documentBL.getDocumentOrNull(record);
		if (expectDocument)
		{
			Assertions.assertNotNull(document, "Record " + record + " (class " + clazz + ") shall be an Document");
		}
		else
		{
			Assertions.assertNull(document, "Record " + record + " (class " + clazz + ") shall NOT be an Document");
		}
	}

	// NOTE: this test applies only for PlainDocumentBL
	@Test
	public void test_getDocument_NonDocument()
	{
		final Properties ctx = Env.getCtx();
		final INonDocumentWithDocumentNo record = InterfaceWrapperHelper.create(ctx, INonDocumentWithDocumentNo.class, ITrx.TRXNAME_None);
		record.setDocumentNo("SomeDocumentNo");
		InterfaceWrapperHelper.save(record);

		Assertions.assertThrows(AdempiereException.class, () -> documentBL.getDocument(record));
	}

	@Test
	public void test_getDocumentNo_InvoiceDocument()
	{
		final String documentNoExpected = "DocumentNoToBeUsed";

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo(documentNoExpected);
		InterfaceWrapperHelper.save(invoice);

		final String documentNoActual = documentBL.getDocumentNo(invoice);
		Assertions.assertEquals(documentNoExpected, documentNoActual, "Invalid DocumentNo");
	}

	@Test
	public void test_getDocumentNo_NonDocument_withDocumentNo()
	{
		final Properties ctx = Env.getCtx();
		final String tableName = InterfaceWrapperHelper.getTableName(INonDocumentWithDocumentNo.class);
		final int adTableId = MTable.getTable_ID(tableName);

		final String documentNoExpected = "DocumentNoToBeUsed";

		final INonDocumentWithDocumentNo record = InterfaceWrapperHelper.create(ctx, INonDocumentWithDocumentNo.class, ITrx.TRXNAME_None);
		record.setDocumentNo(documentNoExpected);
		InterfaceWrapperHelper.save(record);

		final int recordId = InterfaceWrapperHelper.getId(record);
		Assertions.assertTrue(recordId > 0, "Invalid recordId=" + recordId);

		Assertions.assertEquals(documentNoExpected, documentBL.getDocumentNo(record), "Invalid DocumentNo(1)");
		Assertions.assertEquals(documentNoExpected, documentBL.getDocumentNo(ctx, adTableId, recordId), "Invalid DocumentNo(2)");
	}

	@Test
	public void test_getDocumentNo_NonDocument_withValueAndName()
	{
		final Properties ctx = Env.getCtx();
		final String tableName = InterfaceWrapperHelper.getTableName(INonDocumentWithValueAndName.class);
		final int adTableId = MTable.getTable_ID(tableName);

		final INonDocumentWithValueAndName record = InterfaceWrapperHelper.create(ctx, INonDocumentWithValueAndName.class, ITrx.TRXNAME_None);
		// record.setValue("testValue"); // NOTE: we are not setting the Value now...
		record.setName("testName");
		InterfaceWrapperHelper.save(record);

		final int recordId = InterfaceWrapperHelper.getId(record);
		Assertions.assertTrue(recordId > 0, "Invalid recordId=" + recordId);

		Assertions.assertEquals("testName", documentBL.getDocumentNo(record), "Invalid DocumentNo(1)");
		Assertions.assertEquals("testName", documentBL.getDocumentNo(ctx, adTableId, recordId), "Invalid DocumentNo(2)");

		record.setValue("testValue");
		InterfaceWrapperHelper.save(record);
		Assertions.assertEquals("testValue", documentBL.getDocumentNo(record), "Invalid DocumentNo");
		Assertions.assertEquals("testValue", documentBL.getDocumentNo(ctx, adTableId, recordId), "Invalid DocumentNo(2)");
	}

	public void test_getDocStatusOrNull_WithDocument()
	{
		final Properties ctx = Env.getCtx();
		final String tableName = InterfaceWrapperHelper.getTableName(I_C_Invoice.class);
		final int adTableId = MTable.getTable_ID(tableName);

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocStatus(IDocument.STATUS_InProgress);
		InterfaceWrapperHelper.save(invoice);

		final int recordId = InterfaceWrapperHelper.getId(invoice);
		Assertions.assertTrue(recordId > 0, "Invalid recordId=" + recordId);

		Assertions.assertEquals(IDocument.STATUS_InProgress, documentBL.getDocStatusOrNull(invoice).getCode(), "Invalid DocStatus");
	}

	public void test_getDocStatusOrNull_NonDocument()
	{
		final Properties ctx = Env.getCtx();
		final String tableName = InterfaceWrapperHelper.getTableName(I_Test.class);
		final int adTableId = MTable.getTable_ID(tableName);

		final I_Test record = InterfaceWrapperHelper.create(ctx, I_Test.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		final int recordId = InterfaceWrapperHelper.getId(record);
		Assertions.assertTrue(recordId > 0, "Invalid recordId=" + recordId);

		Assertions.assertNull(documentBL.getDocStatusOrNull(record), "Invalid DocStatus");
	}

	public void test_getDocStatusOrNull_InvalidParameters()
	{
		Assertions.assertNull(documentBL.getDocStatusOrNull(TableRecordReference.of(-1, -1)), "Invalid DocStatus");
		Assertions.assertNull(documentBL.getDocStatusOrNull(TableRecordReference.of(318, -1)), "Invalid DocStatus");
		Assertions.assertNull(documentBL.getDocStatusOrNull(TableRecordReference.of(-1, 1000)), "Invalid DocStatus");
	}
}
