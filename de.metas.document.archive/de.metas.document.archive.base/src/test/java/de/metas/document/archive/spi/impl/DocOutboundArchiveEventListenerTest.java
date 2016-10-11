package de.metas.document.archive.spi.impl;

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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_Test;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.I_AD_User;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;

public class DocOutboundArchiveEventListenerTest
{
	@BeforeClass
	public static void staticInit()
	{
		Adempiere.enableUnitTestMode();
		AdempiereTestHelper.get().staticInit();
	}

	private DocOutboundArchiveEventListener archiveBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		archiveBL = new DocOutboundArchiveEventListener();
		Services.get(IArchiveEventManager.class).registerArchiveEventListener(archiveBL);
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
	 */
	@Test
	public void createLogLine_DocumentNo_ForDocuments()
	{
		final String documentNoExpected = "DocumentNotToBeUsed";

		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(bpartner);
		final I_AD_User user = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_User.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(user);
		
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo(documentNoExpected);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		invoice.setAD_User_ID(user.getAD_User_ID());
		InterfaceWrapperHelper.save(invoice);

		final I_AD_Archive archive = createArchive(invoice);

		final I_C_Doc_Outbound_Log_Line docExchangeLine = archiveBL.createLogLine(archive);

		Assert.assertEquals("Invalid DocumentNo", documentNoExpected, docExchangeLine.getDocumentNo());
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
	 */
	@Test
	public void createLogLine_DocumentNo_ForNonDocuments()
	{
		final I_Test record = InterfaceWrapperHelper.create(Env.getCtx(), I_Test.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		final I_AD_Archive archive = createArchive(record);

		final I_C_Doc_Outbound_Log_Line docExchangeLine = archiveBL.createLogLine(archive);

		// Expected document is record ID because record does not have any DocumentNo, Value or Name fields completed
		final String documentNoExpected = String.valueOf(record.getTest_ID());
		Assert.assertEquals("Log line's DocumentNo shall be record's ID", documentNoExpected, docExchangeLine.getDocumentNo());
	}

	private I_AD_Archive createArchive(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int adTableId = MTable.getTable_ID(tableName);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final I_AD_Archive archive = InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, trxName);
		archive.setAD_Table_ID(adTableId);
		archive.setRecord_ID(recordId);
		archive.setName("Dummy_" + model.toString());
		archive.setIsActive(true);
		archive.setIsReport(false);

		InterfaceWrapperHelper.save(archive);
		return archive;
	}
}
