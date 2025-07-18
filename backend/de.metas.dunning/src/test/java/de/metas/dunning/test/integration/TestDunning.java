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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.acct.posting.DocumentPostingUserNotificationService;
import de.metas.common.util.time.SystemTime;
import de.metas.dunning.DunningDocDocumentHandlerProvider;
import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningEventDispatcher;
import de.metas.dunning.api.impl.PlainDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.dunning.invoice.api.impl.DunnableDocBuilder;
import de.metas.dunning.invoice.api.impl.InvoiceSourceBL;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.impl.MockedDunningCandidateListener;
import de.metas.interfaces.I_C_DocType;
import de.metas.record.warning.RecordWarningRepository;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tsa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		StartupListener.class,
		ShutdownListener.class,
		DunningDocDocumentHandlerProvider.class,
		RecordWarningRepository.class,
		DocumentPostingUserNotificationService.class,
})
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

	@SuppressWarnings({ "SameParameterValue", "deprecation" })
	Timestamp timestamp(final int year, final int month, final int day) {return TimeUtil.getDay(year, month, day);}

	@Test
	public void testCreateInvoices()
	{
		prepareWriteOff();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice1)).as("Invoice is already written off: " + invoice1).isFalse();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice2)).as("Invoice not wrote off " + invoice2).isFalse();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice3)).as("Invoice not wrote off " + invoice3).isFalse();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice4)).as("Invoice not wrote off " + invoice4).isFalse();
	}

	@Test
	public void testNumberOfCreatedCandidates()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		assertThat(candidates1).as("Only one candidate1 shall be generated for " + invoice1).hasSize(1);
		assertThat(candidates2).as("Only one candidate2 shall be generated for " + invoice2).hasSize(1);
		assertThat(candidates4).as("Only one candidate1 shall be generated for " + invoice4).hasSize(1);
	}

	@Test
	public void testDunningCandidates()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		assertThat(candidate1.isProcessed()).as("Candidate1 - processed").isFalse();
		assertThat(candidate1.getC_DunningLevel()).as("Candidate1 - invalid dunning level").isEqualTo(dunningLevel1);
		assertThat(candidate1.getDunningDateEffective()).as("Candidate1 - DunningDateEffective").isNull();
		assertThat(candidate1.isWriteOff()).as("Candidate1 - WriteOff").isFalse();
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate1);

		assertThat(candidate2.isProcessed()).as("Candidate2 - processed").isFalse();
		// Still level 1 because we are dunning sequentially
		assertThat(candidate2.getC_DunningLevel()).as("Candidate2 - invalid dunning level").isEqualTo(dunningLevel1);
		assertThat(candidate2.getDunningDateEffective()).as("Candidate2 - DunningDateEffective").isNull();
		assertThat(candidate2.isWriteOff()).as("Candidate2 - WriteOff").isFalse();
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate2);

	}

	@Test
	public void testProcessDunningCandidates_FirstLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		// Not generated for invoice that has a dunning grace.
		assertThat(candidate3).as("Candidate3 - null").isNull();

		assertThat(candidate4.isProcessed()).as("Candidate4 - processed").isFalse();
		assertThat(candidate4.getC_DunningLevel()).as("Candidate4 - invalid dunning level").isEqualTo(dunningLevel1);
		assertThat(candidate4.getDunningDateEffective()).as("Candidate4 - DunningDateEffective").isNull();
		assertThat(candidate4.isWriteOff()).as("Candidate4 - WriteOff").isFalse();
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate4);

		candidate4.setDunningGrace(timestamp(2014, 1, 1));
		InterfaceWrapperHelper.save(candidate4);

		//
		// Process all dunning candidates => Dunning Docs will be generated
		dunningBL.processCandidates(dunningContext);
		loadDunningCandidates(); // reload candidates
		assertThat(candidate1.isProcessed()).as("Candidate1 - processed").isTrue();
		assertThat(candidate1.isDunningDocProcessed()).as("Candidate1 - IsDunningDocProcessed").isFalse();
		assertThat(candidate2.isProcessed()).as("Candidate2 - processed").isTrue();
		assertThat(candidate2.isDunningDocProcessed()).as("Candidate2 - IsDunningDocProcessed").isFalse();

		//
		// Assert candidate with dunning grace not processed.
		assertThat(candidate4.isProcessed()).as("Candidate4 - processed").isFalse();

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
		processDunningDocs();
		loadDunningCandidates();
		assertThat(candidate1.isDunningDocProcessed()).as("Candidate1 - IsDunningDocProcessed").isTrue();
		assertThat(candidate1.getDunningDateEffective()).as("Candidate1 - DunningDateEffective").isEqualTo(dunningContext.getDunningDate());
		assertThat(candidate2.isDunningDocProcessed()).as("Candidate2 - IsDunningDocProcessed").isTrue();
		assertThat(candidate2.getDunningDateEffective()).as("Candidate2 - DunningDateEffective").isEqualTo(dunningContext.getDunningDate());

	}

	@Test
	public void testWriteOffDunningDocs_FirstLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs();

		//
		// Assert no invoice shall be wrote-off at this moment (we are still on level1)
		Services.get(IInvoiceSourceBL.class).writeOffDunningDocs(dunningContext.getCtx(), "writeoff test");
		assertThat(invoiceBL.isInvoiceWroteOff(invoice1)).as("Invoice1 - Invalid WriteOff: " + invoice1).isFalse();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice2)).as("Invoice2 - InvalidWriteOff: " + invoice2).isFalse();
	}

	@Test
	public void testGenerateDunningCands_Level2()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs();

		//
		// Create candidates without processing them - Second run => Level 2 shall be generated
		generateDunningCandidates(dunningContext);

		//
		//
		candidate2_2 = dao.retrieveDunningCandidate(dunningContext, invoice2, dunningLevel2);
		assertThat(candidate2_2).as("Candidate2_2 - not null").isNotNull();
		assertThat(candidate2_2.getRecord_ID()).as("Candidate2_2 references wrong invoice").isEqualTo(invoice2.getC_Invoice_ID());
		assertThat(candidate2_2.isProcessed()).as("Candidate2_2 - processed").isFalse();
		assertThat(candidate2_2.getC_DunningLevel()).as("Candidate2_2 - invalid dunning level").isEqualTo(dunningLevel2);
		assertThat(candidate2_2.isWriteOff()).as("Candidate2 - WriteOff").isTrue();
		writeOffListener.assertNotTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate2_2);
	}

	@Test
	public void testProcessDunningCandidatesAndDocs_SecondLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs();

		//
		// Create candidates without processing them - Second run => Level 2 shall be generated
		generateDunningCandidates(dunningContext);

		candidate2_2 = dao.retrieveDunningCandidate(dunningContext, invoice2, dunningLevel2);

		//
		// Process second round of candidates, generate dunning docs and process them
		processDunningCandidates(dunningContext);
		processDunningDocs();

		//
		// Check write-off because we actually executing the write-off
		{
			final List<I_C_DunningDoc_Line_Source> sourcesToWriteOff = IteratorUtils.asList(dao.retrieveDunningDocLineSourcesToWriteOff(dunningContext));
			assertThat(sourcesToWriteOff).as("Only one candidate shall be in writeoff list").hasSize(1);

			final I_C_DunningDoc_Line_Source sourceToWriteOff = sourcesToWriteOff.get(0);
			assertThat(sourceToWriteOff.isProcessed()).as("Source to writeoff - Invalid - Processed").isTrue();
			assertThat(sourceToWriteOff.isWriteOff()).as("Source to writeoff - Invalid - IsWriteOff").isTrue();
			assertThat(sourceToWriteOff.isWriteOffApplied()).as("Source to writeoff - Invalid - IsWriteOffApplied").isFalse();
			assertThat(sourceToWriteOff.getC_Dunning_Candidate_ID()).as("Source to writeoff - Invalid - C_Dunning_Candidate_ID").isEqualTo(candidate2_2.getC_Dunning_Candidate_ID());
		}
	}

	@Test
	public void testWriteOffDunningDocs_SecondLevel()
	{
		prepareWriteOff();

		prepareGenerateDunningCandidates();

		loadDunningCandidates();

		dunningBL.processCandidates(dunningContext);

		processDunningDocs();

		//
		// Create candidates without processing them - Second run => Level 2 shall be generated
		generateDunningCandidates(dunningContext);

		candidate2_2 = dao.retrieveDunningCandidate(dunningContext, invoice2, dunningLevel2);

		//
		// Process second round of candidates, generate dunning docs and process them
		processDunningCandidates(dunningContext);
		processDunningDocs();

		//
		// Execute writeoff process
		new InvoiceSourceBL().writeOffDunningDocs(dunningContext.getCtx(), "writeoff test 2");

		//
		// Assert no invoice shall be wrote-off at this moment (we are still on level1)
		loadDunningCandidates();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice1)).as("Invoice1 - Invalid WriteOff: " + invoice1).isFalse();
		assertThat(invoiceBL.isInvoiceWroteOff(invoice2)).as("Invoice2 - InvalidWriteOff: " + invoice2).isTrue();
		writeOffListener.assertTriggered(IInvoiceSourceBL.EVENT_AfterInvoiceWriteOff, candidate2_2);
	}

	private IDunnableDoc createDunningDoc(final I_C_Invoice invoice, final Date dueDate, final int daysDue, final Date graceDate)
	{
		return new DunnableDocBuilder()
				.setRecord(invoice)
				.setDocumentNo(invoice.getDocumentNo())
				.setC_BPartner_ID(invoice.getC_BPartner_ID())
				.setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID())
				.setContact_ID(invoice.getAD_User_ID())
				.setC_Currency_ID(invoice.getC_Currency_ID())
				.setTotalAmt(invoice.getGrandTotal()) // totalAmt,
				.setOpenAmt(invoice.getGrandTotal()) // openAmt,
				.setDueDate(dueDate) // dueDate,
				.setGraceDate(graceDate)
				.setDaysDue(daysDue) // daysDue,
				.setInDispute(false) // isInDispute
				.create();
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
		final Date dunningDate = timestamp(2012, 2, 1);
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
		invoice1.setC_Currency_ID(currencyEUR.getRepoId());
		invoice1.setC_DocType_ID(type.getC_DocType_ID());
		invoice1.setIsSOTrx(true);
		invoice1.setProcessed(true);
		invoice1.setGrandTotal(new BigDecimal("110"));
		invoice1.setDateAcct(de.metas.common.util.time.SystemTime.asDayTimestamp());
		invoice1.setDateInvoiced(de.metas.common.util.time.SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice1);

		invoice2 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice2.setAD_Org_ID(123);
		invoice2.setC_BPartner_ID(2);
		invoice2.setC_BPartner_Location_ID(2);
		invoice2.setAD_User_ID(2);
		invoice2.setC_Currency_ID(currencyEUR.getRepoId());
		invoice2.setC_DocType_ID(type.getC_DocType_ID());
		invoice2.setIsSOTrx(true);
		invoice2.setProcessed(true);
		invoice2.setGrandTotal(new BigDecimal("110"));
		invoice2.setDateAcct(de.metas.common.util.time.SystemTime.asDayTimestamp());
		invoice2.setDateInvoiced(SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice2);

		// Invoice with dunning grace.
		invoice3 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice3.setAD_Org_ID(123);
		invoice3.setC_BPartner_ID(2);
		invoice3.setC_BPartner_Location_ID(2);
		invoice3.setAD_User_ID(2);
		invoice3.setC_Currency_ID(currencyEUR.getRepoId());
		invoice3.setC_DocType_ID(type.getC_DocType_ID());
		invoice3.setIsSOTrx(true);
		invoice3.setProcessed(true);
		invoice3.setDateAcct(de.metas.common.util.time.SystemTime.asDayTimestamp());
		invoice3.setGrandTotal(new BigDecimal("110"));
		invoice3.setDateInvoiced(de.metas.common.util.time.SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice3);

		invoice4 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice4.setAD_Org_ID(123);
		invoice4.setC_BPartner_ID(2);
		invoice4.setC_BPartner_Location_ID(2);
		invoice4.setAD_User_ID(2);
		invoice4.setC_Currency_ID(currencyEUR.getRepoId());
		invoice4.setC_DocType_ID(type.getC_DocType_ID());
		invoice4.setIsSOTrx(true);
		invoice4.setProcessed(true);
		invoice4.setDateAcct(de.metas.common.util.time.SystemTime.asDayTimestamp());
		invoice4.setGrandTotal(new BigDecimal("110"));
		invoice4.setDateInvoiced(de.metas.common.util.time.SystemTime.asDayTimestamp());
		InterfaceWrapperHelper.save(invoice4);
	}

	private void prepareGenerateDunningCandidates()
	{
		//
		// Setup dunnable documents
		final List<IDunnableDoc> documents = getLiveDunnableDocList(dunningContext);

		documents.add(createDunningDoc(invoice1, timestamp(2012, 1, 1), 15, null));

		documents.add(createDunningDoc(invoice2, timestamp(2012, 1, 1), 25, null));

		documents.add(createDunningDoc(invoice3, timestamp(2012, 1, 1), 15, timestamp(2014, 1, 1)));

		documents.add(createDunningDoc(invoice4, timestamp(2012, 1, 1), 15, null));

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
