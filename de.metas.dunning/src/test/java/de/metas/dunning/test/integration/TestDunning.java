/**
 * 
 */
package de.metas.dunning.test.integration;

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


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.NullLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningEventDispatcher;
import de.metas.dunning.api.impl.PlainDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.invoice.api.impl.DunnableDocBuilder;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.impl.MockedDunningCandidateListener;
import de.metas.interfaces.I_C_DocType;

/**
 * @author tsa
 * 
 */
public class TestDunning extends DunningTestBase
{
	// Invoices
	private I_C_Invoice invoice1;
	private I_C_Invoice invoice2;
	private I_C_Invoice invoice3;
	private I_C_Invoice invoice4;

	// Dunning context
	private PlainDunningContext dunningContext;

	// Dunning levels
	private I_C_DunningLevel dunningLevel1;
	private I_C_DunningLevel dunningLevel2;

	// Listener
	private MockedDunningCandidateListener writeOffListener;

	// Lists of candidates
	private List<I_C_Dunning_Candidate> candidates1;
	private List<I_C_Dunning_Candidate> candidates2;
	private List<I_C_Dunning_Candidate> candidates4;

	// Candidates
	private I_C_Dunning_Candidate candidate1;
	private I_C_Dunning_Candidate candidate2;
	private I_C_Dunning_Candidate candidate3;
	private I_C_Dunning_Candidate candidate4;

	// Candidate for level 2
	private I_C_Dunning_Candidate candidate2_2;

	@Test
	public void testCreateInvoices()
	{
		prepareWriteOff();
		assertThat("Invoice is already written off: " + invoice1, invoiceBL.isInvoiceWroteOff(invoice1), is(false));
		Assert.assertFalse("Invoice not wrote off " + invoice2, invoiceBL.isInvoiceWroteOff(invoice2));
		Assert.assertFalse("Invoice not wrote off " + invoice3, invoiceBL.isInvoiceWroteOff(invoice3));
		Assert.assertFalse("Invoice not wrote off " + invoice4, invoiceBL.isInvoiceWroteOff(invoice4));
	}

	@Test
	public void testNumberOfCreatedCandidates()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		Assert.assertEquals("Only one candidate1 shall be generated for " + invoice1, 1, candidates1.size());

		Assert.assertEquals("Only one candidate2 shall be generated for " + invoice2, 1, candidates2.size());

		Assert.assertEquals("Only one candidate1 shall be generated for " + invoice4, 1, candidates4.size());
	}

	@Test
	public void testDunningCandidates()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		Assert.assertFalse("Candidate1 - processed", candidate1.isProcessed());
		Assert.assertEquals("Candidate1 - invalid dunning level", dunningLevel1, candidate1.getC_DunningLevel());
		Assert.assertNull("Candidate1 - DunningDateEffective", candidate1.getDunningDateEffective());
		Assert.assertFalse("Candidate1 - WriteOff", candidate1.isWriteOff());
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate1);

		Assert.assertFalse("Candidate2 - processed", candidate2.isProcessed());
		// Still level 1 because we are dunning sequentially
		Assert.assertEquals("Candidate2 - invalid dunning level", dunningLevel1, candidate2.getC_DunningLevel());
		Assert.assertNull("Candidate2 - DunningDateEffective", candidate2.getDunningDateEffective());
		Assert.assertFalse("Candidate2 - WriteOff", candidate2.isWriteOff());
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate2);

	}

	@Test
	public void testProcessDunningCandidates_FirstLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		// Not generated for invoice that has a dunning grace.
		Assert.assertNull("Candidate3 - null", candidate3);

		Assert.assertFalse("Candidate4 - processed", candidate4.isProcessed());
		Assert.assertEquals("Candidate4 - invalid dunning level", dunningLevel1, candidate4.getC_DunningLevel());
		Assert.assertNull("Candidate4 - DunningDateEffective", candidate4.getDunningDateEffective());
		Assert.assertFalse("Candidate4 - WriteOff", candidate4.isWriteOff());
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate4);

		candidate4.setDunningGrace(TimeUtil.getDay(2014, 01, 01));
		InterfaceWrapperHelper.save(candidate4);

		//
		// Process all dunning candidates => Dunning Docs will be generated
		dunningBL.processCandidates(dunningContext);
		loadDunningCandidates(); // reload candidates
		Assert.assertTrue("Candidate1 - processed", candidate1.isProcessed());
		Assert.assertFalse("Candidate1 - IsDunningDocProcessed", candidate1.isDunningDocProcessed());
		Assert.assertTrue("Candidate2 - processed", candidate2.isProcessed());
		Assert.assertFalse("Candidate2 - IsDunningDocProcessed", candidate2.isDunningDocProcessed());

		//
		// Assert candidate with dunning grace not processed.
		Assert.assertEquals("Candidate4 - processed", false, candidate4.isProcessed());

	}

	@Test
	public void testProcessDunningDocs()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		//
		// Process all dunning docs => candidates shall be marked as doc processed
		processDunningDocs(dunningContext);
		loadDunningCandidates();
		Assert.assertTrue("Candidate1 - IsDunningDocProcessed", candidate1.isDunningDocProcessed());
		Assert.assertEquals("Candidate1 - DunningDateEffective", dunningContext.getDunningDate(), candidate1.getDunningDateEffective());
		Assert.assertTrue("Candidate2 - IsDunningDocProcessed", candidate2.isDunningDocProcessed());
		Assert.assertEquals("Candidate2 - DunningDateEffective", dunningContext.getDunningDate(), candidate2.getDunningDateEffective());

	}

	@Test
	public void testWriteOffDunningDocs_FirstLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs(dunningContext);

		//
		// Assert no invoice shall be wrote-off at this moment (we are still on level1)
		Services.get(IInvoiceSourceBL.class).writeOffDunningDocs(dunningContext.getCtx(), "writeoff test", NullLoggable.instance);
		Assert.assertFalse("Invoice1 - Invalid WriteOff: " + invoice1, invoiceBL.isInvoiceWroteOff(invoice1));
		Assert.assertFalse("Invoice2 - InvalidWriteOff: " + invoice2, invoiceBL.isInvoiceWroteOff(invoice2));
	}

	@Test
	public void testGenerateDunningCands_Level2()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs(dunningContext);

		//
		// Create candidates without processing them - Second run => Level 2 shall be generated
		generateDunningCandidates(dunningContext);

		//
		//
		candidate2_2 = dao.retrieveDunningCandidate(dunningContext, invoice2, dunningLevel2);
		Assert.assertNotNull("Candidate2_2 - not null", candidate2_2);
		assertThat("Candidate2_2 references wrong invoice", candidate2_2.getRecord_ID(), is(invoice2.getC_Invoice_ID()));
		Assert.assertEquals("Candidate2_2 - processed", false, candidate2_2.isProcessed());
		Assert.assertEquals("Candidate2_2 - invalid dunning level", dunningLevel2, candidate2_2.getC_DunningLevel());
		Assert.assertEquals("Candidate2 - WriteOff", true, candidate2_2.isWriteOff());
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate2_2);
	}

	@Test
	public void testProcessDunningCandidatesAndDocs_SecondLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs(dunningContext);

		//
		// Create candidates without processing them - Second run => Level 2 shall be generated
		generateDunningCandidates(dunningContext);

		candidate2_2 = dao.retrieveDunningCandidate(dunningContext, invoice2, dunningLevel2);

		//
		// Process second round of candidates, generate dunning docs and process them
		processDunningCandidates(dunningContext);
		processDunningDocs(dunningContext);

		//
		// Check write-off because we actually executing the write-off
		{
			final List<I_C_DunningDoc_Line_Source> sourcesToWriteOff = IteratorUtils.asList(dao.retrieveDunningDocLineSourcesToWriteOff(dunningContext));
			Assert.assertEquals("Only one candidate shall be in writeoff list", 1, sourcesToWriteOff.size());

			final I_C_DunningDoc_Line_Source sourceToWriteOff = sourcesToWriteOff.get(0);
			Assert.assertEquals("Source to writeoff - Invalid - Processed", true, sourceToWriteOff.isProcessed());
			Assert.assertEquals("Source to writeoff - Invalid - IsWriteOff", true, sourceToWriteOff.isWriteOff());
			Assert.assertEquals("Source to writeoff - Invalid - IsWriteOffApplied", false, sourceToWriteOff.isWriteOffApplied());
			Assert.assertEquals("Source to writeoff - Invalid - C_Dunning_Candidate_ID", candidate2_2.getC_Dunning_Candidate_ID(), sourceToWriteOff.getC_Dunning_Candidate_ID());
		}
	}

	@Test
	public void testWriteOffDunningDocs_SecondLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs(dunningContext);

		//
		// Create candidates without processing them - Second run => Level 2 shall be generated
		generateDunningCandidates(dunningContext);

		candidate2_2 = dao.retrieveDunningCandidate(dunningContext, invoice2, dunningLevel2);

		//
		// Process second round of candidates, generate dunning docs and process them
		processDunningCandidates(dunningContext);
		processDunningDocs(dunningContext);

		//
		// Execute writeoff process
		Services.get(IInvoiceSourceBL.class).writeOffDunningDocs(dunningContext.getCtx(), "writeoff test 2", NullLoggable.instance);

		//
		// Assert no invoice shall be wrote-off at this moment (we are still on level1)
		loadDunningCandidates();
		Assert.assertFalse("Invoice1 - Invalid WriteOff: " + invoice1, invoiceBL.isInvoiceWroteOff(invoice1));
		Assert.assertTrue("Invoice2 - InvalidWriteOff: " + invoice2, invoiceBL.isInvoiceWroteOff(invoice2));
		writeOffListener.assertTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate2_2);
	}

	private IDunnableDoc createDunningDoc(final I_C_Invoice invoice, final Date dueDate, final int daysDue, final Date graceDate)
	{
		final IDunnableDoc dunnableDoc = new DunnableDocBuilder()
				.setRecord(invoice)
				.setDocumentNo(invoice.getDocumentNo())
				.setC_BPartner_ID(invoice.getC_BPartner_ID())
				.setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID())
				.setContact_ID(invoice.getAD_User_ID())
				.setC_Currency(invoice.getC_Currency())
				.setTotalAmt(invoice.getGrandTotal()) // totalAmt,
				.setOpenAmt(invoice.getGrandTotal()) // openAmt,
				.setDueDate(dueDate) // dueDate,
				.setGraceDate(graceDate)
				.setDaysDue(daysDue) // daysDue,
				.setInDispute(false) // isInDispute
				.create();
		return dunnableDoc;
	}

	/**
	 * Prepare the invoices, dunning levels and context that are about to be tested
	 */
	private void prepareWriteOff()
	{
		writeOffListener = new MockedDunningCandidateListener();
		Services.get(IDunningEventDispatcher.class).registerDunningCandidateListener(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, writeOffListener);

		final I_C_Dunning dunning = createDunning("DunningWriteoff");
		dunning.setCreateLevelsSequentially(true);
		InterfaceWrapperHelper.save(dunning);

		// dunning, DaysBetweenDunning, DaysAfterDue, InterestPercent
		dunningLevel1 = createDunningLevel(dunning, 0, 10, 0);
		dunningLevel1.setName("Level1");
		dunningLevel1.setIsWriteOff(false);
		InterfaceWrapperHelper.save(dunningLevel1);

		dunningLevel2 = createDunningLevel(dunning, 0, 20, 0);
		dunningLevel2.setName("Level2");
		dunningLevel2.setIsWriteOff(true);
		InterfaceWrapperHelper.save(dunningLevel2);

		//
		// Setup dunning context
		final Date dunningDate = TimeUtil.getDay(2012, 02, 01);
		dunningContext = createPlainDunningContext();
		dunningContext.setDunningDate(dunningDate);

		final I_C_DocType type = db.newInstance(I_C_DocType.class);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(type);

		invoice1 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice1.setAD_Org_ID(123);
		invoice1.setC_BPartner_ID(2);
		invoice1.setC_BPartner_Location_ID(2);
		invoice1.setAD_User_ID(2);
		invoice1.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		invoice1.setC_DocType_ID(type.getC_DocType_ID());
		invoice1.setIsSOTrx(true);
		invoice1.setProcessed(true);
		invoice1.setGrandTotal(new BigDecimal("110"));
		invoice1.setDateAcct(SystemTime.asDayTimestamp());
		invoice1.setDateInvoiced(SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice1);

		invoice2 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice2.setAD_Org_ID(123);
		invoice2.setC_BPartner_ID(2);
		invoice2.setC_BPartner_Location_ID(2);
		invoice2.setAD_User_ID(2);
		invoice2.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		invoice2.setC_DocType_ID(type.getC_DocType_ID());
		invoice2.setIsSOTrx(true);
		invoice2.setProcessed(true);
		invoice2.setGrandTotal(new BigDecimal("110"));
		invoice2.setDateAcct(SystemTime.asDayTimestamp());
		invoice2.setDateInvoiced(SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice2);

		// Invoice with dunning grace.
		invoice3 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice3.setAD_Org_ID(123);
		invoice3.setC_BPartner_ID(2);
		invoice3.setC_BPartner_Location_ID(2);
		invoice3.setAD_User_ID(2);
		invoice3.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		invoice3.setC_DocType_ID(type.getC_DocType_ID());
		invoice3.setIsSOTrx(true);
		invoice3.setProcessed(true);
		invoice3.setDateAcct(SystemTime.asDayTimestamp());
		invoice3.setGrandTotal(new BigDecimal("110"));
		invoice3.setDateInvoiced(SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice3);

		invoice4 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice4.setAD_Org_ID(123);
		invoice4.setC_BPartner_ID(2);
		invoice4.setC_BPartner_Location_ID(2);
		invoice4.setAD_User_ID(2);
		invoice4.setC_Currency_ID(currencyEUR.getC_Currency_ID());
		invoice4.setC_DocType_ID(type.getC_DocType_ID());
		invoice4.setIsSOTrx(true);
		invoice4.setProcessed(true);
		invoice4.setDateAcct(SystemTime.asDayTimestamp());
		invoice4.setGrandTotal(new BigDecimal("110"));
		invoice4.setDateInvoiced(SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice4);
	}

	private void prepareGenerateDunningCandidates()
	{
		//
		// Setup dunnable documents
		final List<IDunnableDoc> documents = getLiveDunnableDocList(dunningContext);

		documents.add(createDunningDoc(invoice1, TimeUtil.getDay(2012, 01, 01), 15, null));

		documents.add(createDunningDoc(invoice2, TimeUtil.getDay(2012, 01, 01), 25, null));

		documents.add(createDunningDoc(invoice3, TimeUtil.getDay(2012, 01, 01), 15, TimeUtil.getDay(2014, 01, 01)));

		documents.add(createDunningDoc(invoice4, TimeUtil.getDay(2012, 01, 01), 15, null));

		//
		// Create candidates without processing them.
		generateDunningCandidates(dunningContext);
	}

	private void loadDunningCandidates()
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		
		candidates1 = dao.retrieveDunningCandidates(dunningContext, adTableDAO.retrieveTableId(I_C_Invoice.Table_Name), invoice1.getC_Invoice_ID());
		candidate1 = candidates1.get(0);

		candidates2 = dao.retrieveDunningCandidates(dunningContext, adTableDAO.retrieveTableId(I_C_Invoice.Table_Name), invoice2.getC_Invoice_ID());
		candidate2 = candidates2.get(0);

		candidate3 = dao.retrieveDunningCandidate(dunningContext, invoice3, dunningLevel1);

		candidates4 = dao.retrieveDunningCandidates(dunningContext, adTableDAO.retrieveTableId(I_C_Invoice.Table_Name), invoice4.getC_Invoice_ID());
		candidate4 = candidates4.get(0);
	}
}
