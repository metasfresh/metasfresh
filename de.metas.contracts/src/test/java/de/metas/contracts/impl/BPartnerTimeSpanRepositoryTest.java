package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_TimeSpan;
import org.compiere.model.X_C_BPartner_TimeSpan;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.StartupListener;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.invoice.ContractInvoiceService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.time.FixedTimeSource;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * de.metas.contracts
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, BPartnerTimeSpanRepository.class, ContractInvoiceService.class })
public class BPartnerTimeSpanRepositoryTest
{

	private BPartnerTimeSpanRepository repository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		repository = Adempiere.getBean(BPartnerTimeSpanRepository.class);

		SystemTime.setTimeSource(new FixedTimeSource(2018, 12, 13, 0, 0, 0));
	}

	@Test
	public void setNewCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.setNewCustomer(bpartnerId);

		refresh(timespan);

		assertThat(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde).isEqualTo(timespan.getC_BPartner_TimeSpan());
	}

	@Test
	public void setRegularCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.setRegularCustomer(bpartnerId);

		refresh(timespan);

		assertThat(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Stammkunde).isEqualTo(timespan.getC_BPartner_TimeSpan());
	}

	@Test
	public void setNonSubscriptionCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.setNonSubscriptionCustomer(bpartnerId);

		refresh(timespan);

		assertThat(timespan.getC_BPartner_TimeSpan()).isNullOrEmpty();
	}

	@Test
	public void retrieveBPartnerTimeSpanThreshol_Default()
	{

		final int bPartnerTimeSpanThreshold = repository.retrieveBPartnerTimeSpanThreshold();

		assertThat(repository.DEFAULT_Threshold_BPartner_TimeSpan).isEqualTo(bPartnerTimeSpanThreshold);
	}

	@Test
	public void retrieveBPartnerTimeSpanThreshol_Configured()
	{

		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);

		sysConfig.setName(repository.SYS_CONFIG_C_BPartner_TimeSpan_Threshold);
		sysConfig.setValue("23");

		save(sysConfig);

		final int bPartnerTimeSpanThreshold = repository.retrieveBPartnerTimeSpanThreshold();

		assertThat(23).isEqualTo(bPartnerTimeSpanThreshold);
	}

	@Test
	public void dateExceedsThreshold()
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(repository.SYS_CONFIG_C_BPartner_TimeSpan_Threshold);
		sysConfig.setValue("12");

		save(sysConfig);

		Timestamp contractEndDate = TimeUtil.parseTimestamp("2017-12-12");

		final Timestamp dateToCompare = SystemTime.asDayTimestamp();
		boolean dateExceedsThreshold = repository.dateExceedsThreshold(contractEndDate, dateToCompare);

		assertTrue(dateExceedsThreshold);

		contractEndDate = TimeUtil.parseTimestamp("2017-12-14");
		dateExceedsThreshold = repository.dateExceedsThreshold(contractEndDate, dateToCompare);

		assertFalse(dateExceedsThreshold);
	}

	@Test
	public void updateTimeSpan_NewCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final Timestamp masterEndDate = TimeUtil.parseTimestamp("2017-12-14");
		createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.updateTimeSpan(bpartnerId);

		refresh(timespan);

		assertThat(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde).isEqualTo(timespan.getC_BPartner_TimeSpan());
	}

	@Test
	public void updateTimeSpan_NoCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final Timestamp masterEndDate = TimeUtil.parseTimestamp("2017-12-11");
		createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.updateTimeSpan(bpartnerId);

		refresh(timespan);

		assertThat(timespan.getC_BPartner_TimeSpan()).isNullOrEmpty();
	}

	@Test
	public void updateTimeSpan_NewCustomer_InvoiceExceedsDate()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final Timestamp masterEndDate1 = TimeUtil.parseTimestamp("2017-12-11");
		final I_C_Flatrate_Term term1 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate1);

		final I_C_Invoice_Candidate cand1 = createInvoiceCandidate(term1.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice1 = createInvoice(partner.getC_BPartner_ID(),"documentNo1", masterEndDate1);

		final I_C_InvoiceLine invoiceLine1 = createInvoiceLine(invoice1.getC_Invoice_ID());

		createInvoiceLineAlloc(cand1.getC_Invoice_Candidate_ID(), invoiceLine1.getC_InvoiceLine_ID());

		final Timestamp masterEndDate2 = TimeUtil.parseTimestamp("2018-12-11");
		final I_C_Flatrate_Term term2 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate2);

		final I_C_Invoice_Candidate cand2 = createInvoiceCandidate(term2.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice2 = createInvoice(partner.getC_BPartner_ID(),"documentNo2", SystemTime.asTimestamp());

		final I_C_InvoiceLine invoiceLine2 = createInvoiceLine(invoice2.getC_Invoice_ID());

		createInvoiceLineAlloc(cand2.getC_Invoice_Candidate_ID(), invoiceLine2.getC_InvoiceLine_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.updateTimeSpan(bpartnerId);

		refresh(timespan);

		assertThat(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde).isEqualTo(timespan.getC_BPartner_TimeSpan());


	}

	@Test
	public void updateTimeSpan_RegularCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_BPartner_TimeSpan timespan = createBPartnerTimeSpan(partner.getC_BPartner_ID());

		final Timestamp masterEndDate1 = TimeUtil.parseTimestamp("2017-12-13");
		final I_C_Flatrate_Term term1 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate1);

		final I_C_Invoice_Candidate cand1 = createInvoiceCandidate(term1.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice1 = createInvoice(partner.getC_BPartner_ID(),"documentNo1", masterEndDate1);

		final I_C_InvoiceLine invoiceLine1 = createInvoiceLine(invoice1.getC_Invoice_ID());

		createInvoiceLineAlloc(cand1.getC_Invoice_Candidate_ID(), invoiceLine1.getC_InvoiceLine_ID());

		final Timestamp masterEndDate2 = TimeUtil.parseTimestamp("2018-12-11");
		final I_C_Flatrate_Term term2 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate2);

		final I_C_Invoice_Candidate cand2 = createInvoiceCandidate(term2.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice2 = createInvoice(partner.getC_BPartner_ID(),"documentNo2", SystemTime.asTimestamp());

		final I_C_InvoiceLine invoiceLine2 = createInvoiceLine(invoice2.getC_Invoice_ID());

		createInvoiceLineAlloc(cand2.getC_Invoice_Candidate_ID(), invoiceLine2.getC_InvoiceLine_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.updateTimeSpan(bpartnerId);

		refresh(timespan);
		assertThat(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Stammkunde).isEqualTo(timespan.getC_BPartner_TimeSpan());

	}

	private I_C_Invoice_Line_Alloc createInvoiceLineAlloc(final int candId, final int invoiceLineId)
	{
		final I_C_Invoice_Line_Alloc alloc = newInstance(I_C_Invoice_Line_Alloc.class);
		alloc.setC_Invoice_Candidate_ID(candId);
		alloc.setC_InvoiceLine_ID(invoiceLineId);

		save(alloc);

		return alloc;
	}

	private I_C_InvoiceLine createInvoiceLine(final int invoiceId)
	{
		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoiceId);
		save(invoiceLine);
		return invoiceLine;
	}

	private I_C_Invoice createInvoice(final int partnerId, final String documentNo, final Timestamp dateInvoiced)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);

		invoice.setC_BPartner_ID(partnerId);
		invoice.setDocumentNo(documentNo);
		invoice.setDateInvoiced(dateInvoiced);
		invoice.setIsSOTrx(true);

		save(invoice);

		return invoice;
	}

	private I_C_Invoice_Candidate createInvoiceCandidate(int termId)
	{
		final I_C_Invoice_Candidate cand = newInstance(I_C_Invoice_Candidate.class);
		cand.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		cand.setRecord_ID(termId);

		save(cand);
		return cand;
	}

	private I_C_Flatrate_Term createFlatrateTerm(final int partnerId, final Timestamp masterEndDate)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);

		term.setBill_BPartner_ID(partnerId);
		term.setMasterEndDate(masterEndDate);

		save(term);
		return term;

	}

	private I_C_BPartner_TimeSpan createBPartnerTimeSpan(int c_BPartner_ID)
	{
		final I_C_BPartner_TimeSpan timeSpan = newInstance(I_C_BPartner_TimeSpan.class);

		timeSpan.setC_BPartner_ID(c_BPartner_ID);

		save(timeSpan);

		return timeSpan;
	}

	private I_C_BPartner createPartner(final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);

		partner.setName(name);

		save(partner);

		return partner;
	}
}
