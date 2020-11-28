package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Customer_Retention;
import org.compiere.model.X_C_Customer_Retention;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.CustomerRetentionId;
import de.metas.contracts.invoice.ContractInvoiceService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;

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

public class CustomerRetentionRepositoryTest
{

	private CustomerRetentionRepository repository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		repository = new CustomerRetentionRepository(new ContractInvoiceService());
		de.metas.common.util.time.SystemTime.setFixedTimeSource("2018-12-13T00:00:00+01:00");
	}

	@Test
	public void setNewCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		repository.setNewCustomer(CustomerRetentionId.ofRepoId(customerRetention.getC_Customer_Retention_ID()));

		refresh(customerRetention);

		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde).isEqualTo(customerRetention.getCustomerRetention());
	}

	@Test
	public void setRegularCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		repository.setRegularCustomer(CustomerRetentionId.ofRepoId(customerRetention.getC_Customer_Retention_ID()));

		refresh(customerRetention);

		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde).isEqualTo(customerRetention.getCustomerRetention());
	}

	@Test
	public void setNonSubscriptionCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		repository.setNonSubscriptionCustomer(CustomerRetentionId.ofRepoId(customerRetention.getC_Customer_Retention_ID()));

		refresh(customerRetention);

		assertThat(customerRetention.getCustomerRetention()).isNullOrEmpty();
	}

	@Test
	public void retrieveCustomerRetentionThreshol_Default()
	{

		final int customerRetentionThreshold = repository.retrieveCustomerRetentionThreshold();

		assertThat(repository.DEFAULT_Threshold_CustomerRetention).isEqualTo(customerRetentionThreshold);
	}

	@Test
	public void retrieveCustomerRetentionThreshol_Configured()
	{

		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);

		sysConfig.setName(repository.SYS_CONFIG_C_CUSTOMER_RETENTION_Threshold);
		sysConfig.setValue("23");

		save(sysConfig);

		final int customerRetentionThreshold = repository.retrieveCustomerRetentionThreshold();

		assertThat(23).isEqualTo(customerRetentionThreshold);
	}

	@Test
	public void dateExceedsThreshold()
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(repository.SYS_CONFIG_C_CUSTOMER_RETENTION_Threshold);
		sysConfig.setValue("12");

		save(sysConfig);

		LocalDate contractEndDate = TimeUtil.asLocalDate("2017-12-12");

		final LocalDate dateToCompare = de.metas.common.util.time.SystemTime.asLocalDate();
		boolean dateExceedsThreshold = repository.dateExceedsThreshold(contractEndDate, dateToCompare);

		assertThat(dateExceedsThreshold).isTrue();

		contractEndDate = TimeUtil.asLocalDate("2017-12-14");
		dateExceedsThreshold = repository.dateExceedsThreshold(contractEndDate, dateToCompare);

		assertThat(dateExceedsThreshold).isFalse();
	}

	@Test
	public void updateCustomerRetention_NewCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		final Timestamp masterEndDate = TimeUtil.parseTimestamp("2017-12-14");
		createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.createUpdateCustomerRetention(bpartnerId);

		refresh(customerRetention);

		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde).isEqualTo(customerRetention.getCustomerRetention());
	}

	@Test
	public void updateCustomerRetention_NoCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		final Timestamp masterEndDate = TimeUtil.parseTimestamp("2017-12-11");
		createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.createUpdateCustomerRetention(bpartnerId);

		refresh(customerRetention);

		assertThat(customerRetention.getCustomerRetention()).isNullOrEmpty();
	}

	@Test
	public void updateCustomerRetention_NewCustomer_InvoiceExceedsDate_But_Not_MasterEndDate()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		final Timestamp masterEndDate1 = TimeUtil.parseTimestamp("2018-12-11");
		final Timestamp invoiceDate1 = TimeUtil.parseTimestamp("2017-12-11");
		final I_C_Flatrate_Term term1 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate1);

		final I_C_Invoice_Candidate cand1 = createInvoiceCandidate(term1.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice1 = createInvoice(partner.getC_BPartner_ID(), "documentNo1", invoiceDate1);

		final I_C_InvoiceLine invoiceLine1 = createInvoiceLine(invoice1.getC_Invoice_ID());

		createInvoiceLineAlloc(cand1.getC_Invoice_Candidate_ID(), invoiceLine1.getC_InvoiceLine_ID());

		final Timestamp masterEndDate2 = TimeUtil.parseTimestamp("2018-12-11");
		final I_C_Flatrate_Term term2 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate2);

		final I_C_Invoice_Candidate cand2 = createInvoiceCandidate(term2.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice2 = createInvoice(partner.getC_BPartner_ID(), "documentNo2", de.metas.common.util.time.SystemTime.asTimestamp());

		final I_C_InvoiceLine invoiceLine2 = createInvoiceLine(invoice2.getC_Invoice_ID());

		createInvoiceLineAlloc(cand2.getC_Invoice_Candidate_ID(), invoiceLine2.getC_InvoiceLine_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.createUpdateCustomerRetention(bpartnerId);

		refresh(customerRetention);

		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde).isEqualTo(customerRetention.getCustomerRetention());

	}
	
	
	
	@Test
	public void updateCustomerRetention_NewCustomer_InvoiceExceedsDate()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		final Timestamp masterEndDate1 = TimeUtil.parseTimestamp("2015-12-11");
		final I_C_Flatrate_Term term1 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate1);

		final I_C_Invoice_Candidate cand1 = createInvoiceCandidate(term1.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice1 = createInvoice(partner.getC_BPartner_ID(), "documentNo1", masterEndDate1);

		final I_C_InvoiceLine invoiceLine1 = createInvoiceLine(invoice1.getC_Invoice_ID());

		createInvoiceLineAlloc(cand1.getC_Invoice_Candidate_ID(), invoiceLine1.getC_InvoiceLine_ID());

		final Timestamp masterEndDate2 = TimeUtil.parseTimestamp("2018-12-11");
		final I_C_Flatrate_Term term2 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate2);

		final I_C_Invoice_Candidate cand2 = createInvoiceCandidate(term2.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice2 = createInvoice(partner.getC_BPartner_ID(), "documentNo2", de.metas.common.util.time.SystemTime.asTimestamp());

		final I_C_InvoiceLine invoiceLine2 = createInvoiceLine(invoice2.getC_Invoice_ID());

		createInvoiceLineAlloc(cand2.getC_Invoice_Candidate_ID(), invoiceLine2.getC_InvoiceLine_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.createUpdateCustomerRetention(bpartnerId);

		refresh(customerRetention);

		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Neukunde).isEqualTo(customerRetention.getCustomerRetention());

	}

	@Test
	public void updateCustomerRetention_RegularCustomer()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		final Timestamp masterEndDate1 = TimeUtil.parseTimestamp("2017-12-13");
		final I_C_Flatrate_Term term1 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate1);

		final I_C_Invoice_Candidate cand1 = createInvoiceCandidate(term1.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice1 = createInvoice(partner.getC_BPartner_ID(), "documentNo1", masterEndDate1);

		final I_C_InvoiceLine invoiceLine1 = createInvoiceLine(invoice1.getC_Invoice_ID());

		createInvoiceLineAlloc(cand1.getC_Invoice_Candidate_ID(), invoiceLine1.getC_InvoiceLine_ID());

		final Timestamp masterEndDate2 = TimeUtil.parseTimestamp("2018-12-11");
		final I_C_Flatrate_Term term2 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate2);

		final I_C_Invoice_Candidate cand2 = createInvoiceCandidate(term2.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice2 = createInvoice(partner.getC_BPartner_ID(), "documentNo2", de.metas.common.util.time.SystemTime.asTimestamp());

		final I_C_InvoiceLine invoiceLine2 = createInvoiceLine(invoice2.getC_Invoice_ID());

		createInvoiceLineAlloc(cand2.getC_Invoice_Candidate_ID(), invoiceLine2.getC_InvoiceLine_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.createUpdateCustomerRetention(bpartnerId);

		refresh(customerRetention);
		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde).isEqualTo(customerRetention.getCustomerRetention());

	}

	@Test
	public void updateCustomerRetention_SameInvoiceDate()
	{
		final I_C_BPartner partner = createPartner("Partner1");

		final I_C_Customer_Retention customerRetention = createCustomerRetention(partner.getC_BPartner_ID());

		final Timestamp masterEndDate1 = de.metas.common.util.time.SystemTime.asTimestamp();
		final I_C_Flatrate_Term term1 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate1);

		final I_C_Invoice_Candidate cand1 = createInvoiceCandidate(term1.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice1 = createInvoice(partner.getC_BPartner_ID(), "documentNo1", masterEndDate1);

		final I_C_InvoiceLine invoiceLine1 = createInvoiceLine(invoice1.getC_Invoice_ID());

		createInvoiceLineAlloc(cand1.getC_Invoice_Candidate_ID(), invoiceLine1.getC_InvoiceLine_ID());

		final Timestamp masterEndDate2 = SystemTime.asTimestamp();
		final I_C_Flatrate_Term term2 = createFlatrateTerm(partner.getC_BPartner_ID(), masterEndDate2);

		final I_C_Invoice_Candidate cand2 = createInvoiceCandidate(term2.getC_Flatrate_Term_ID());

		final I_C_Invoice invoice2 = createInvoice(partner.getC_BPartner_ID(), "documentNo2", de.metas.common.util.time.SystemTime.asTimestamp());

		final I_C_InvoiceLine invoiceLine2 = createInvoiceLine(invoice2.getC_Invoice_ID());

		createInvoiceLineAlloc(cand2.getC_Invoice_Candidate_ID(), invoiceLine2.getC_InvoiceLine_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		repository.createUpdateCustomerRetention(bpartnerId);

		refresh(customerRetention);
		assertThat(X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde).isEqualTo(customerRetention.getCustomerRetention());

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
		invoice.setDocStatus(DocStatus.Completed.getCode());

		save(invoice);

		return invoice;
	}

	private I_C_Invoice_Candidate createInvoiceCandidate(final int termId)
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

	private I_C_Customer_Retention createCustomerRetention(final int partnerId)
	{
		final I_C_Customer_Retention customerRetention = newInstance(I_C_Customer_Retention.class);

		customerRetention.setC_BPartner_ID(partnerId);

		save(customerRetention);

		return customerRetention;
	}

	private I_C_BPartner createPartner(final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);

		partner.setName(name);

		save(partner);

		return partner;
	}
}
