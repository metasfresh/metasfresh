package de.metas.document.archive.async.spi.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PrintInfo;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.model.I_C_DunningDoc;

/*
 * #%L
 * de.metas.dunning
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


/**
 * Integration test between {@link DocOutboundWorkpackageProcessor} and dunning project.
 * 
 * @author tsa
 * 
 */
public class DocOutboundWorkpackageProcessorTest extends DunningTestBase
{

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

	/**
	 * Validate requirement: http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing
	 */
	@Test
	public void test_createPrintInfo_fromDunningDoc()
	{
		final I_C_DunningDoc dunningDoc = InterfaceWrapperHelper.create(Env.getCtx(), I_C_DunningDoc.class, ITrx.TRXNAME_None);
		dunningDoc.setDocumentNo("ExpectedDocumentNo");
		dunningDoc.setC_BPartner_ID(12345);
		InterfaceWrapperHelper.save(dunningDoc);

		final PrintInfo printInfo = createPrintInfo(dunningDoc);
		Assert.assertEquals("Invalid DocumentNo", "ExpectedDocumentNo", printInfo.getName());
		Assert.assertEquals("Invalid AD_Table_ID", InterfaceWrapperHelper.getTableId(I_C_DunningDoc.class), printInfo.getAD_Table_ID());
		Assert.assertEquals("Invalid Record_ID", dunningDoc.getC_DunningDoc_ID(), printInfo.getRecord_ID());
		Assert.assertEquals("Invalid C_BPartner_ID", dunningDoc.getC_BPartner_ID(), printInfo.getC_BPartner_ID());
	}

}
