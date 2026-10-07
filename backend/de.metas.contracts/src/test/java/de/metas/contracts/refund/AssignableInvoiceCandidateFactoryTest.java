package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;

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

public class AssignableInvoiceCandidateFactoryTest
{
	private int BPartnerRecordID = 10;
	private I_M_Product productRecord;
	private I_C_Invoice_Candidate assignableIcRecord;
	private Timestamp dateToInvoiceOfAssignableCand;

	private AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory;

	private static final BigDecimal NINE = new BigDecimal("9");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		save(productRecord);

		final CurrencyId currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		dateToInvoiceOfAssignableCand = SystemTime.asTimestamp();

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setC_BPartner_ID(BPartnerRecordID);
		save(partner);

		final I_C_Country country_DE = newInstance(I_C_Country.class);
		country_DE.setAD_Language("de");
		save(country_DE);

		final I_C_Location loc = newInstance(I_C_Location.class);
		loc.setC_Country_ID(country_DE.getC_Country_ID());
		save(loc);

		final I_C_BPartner_Location bpLoc = newInstance(I_C_BPartner_Location.class);
		bpLoc.setC_Location_ID(loc.getC_Location_ID());
		bpLoc.setC_BPartner_ID(partner.getC_BPartner_ID());

		save(bpLoc);

		assignableIcRecord = newInstance(I_C_Invoice_Candidate.class);
		assignableIcRecord.setBill_BPartner_ID(partner.getC_BPartner_ID());
		assignableIcRecord.setBill_Location_ID(bpLoc.getC_BPartner_Location_ID());
		assignableIcRecord.setM_Product_ID(productRecord.getM_Product_ID());
		assignableIcRecord.setDateToInvoice(dateToInvoiceOfAssignableCand);
		assignableIcRecord.setNetAmtInvoiced(ONE);
		assignableIcRecord.setNetAmtToInvoice(NINE);
		assignableIcRecord.setC_Currency_ID(currencyId.getRepoId());
		save(assignableIcRecord);

		final InvoiceScheduleRepository invoiceScheduleRepository = new InvoiceScheduleRepository();
		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(invoiceScheduleRepository);
		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);
		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);
		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);
		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(refundContractRepository, refundInvoiceCandidateFactory);
		assignableInvoiceCandidateFactory = new AssignableInvoiceCandidateFactory(
				new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository),
				new CurrencyRepository());
	}

	@Test
	public void ofRecord_AssignableInvoiceCandidate()
	{
		// invoke the method under test
		final AssignableInvoiceCandidate ofRecord = assignableInvoiceCandidateFactory.ofRecord(assignableIcRecord);

		assertThat(ofRecord.getBpartnerLocationId().getBpartnerId().getRepoId()).isEqualTo(BPartnerRecordID);

		assertThat(ofRecord.getProductId().getRepoId()).isEqualTo(productRecord.getM_Product_ID());

		// TODO move to dedicated test case
		// assertThat(cast.getAssignmentsToRefundCandidates().get(0).getRefundInvoiceCandidate().getId().getRepoId()).isEqualTo(refundContractIcRecord.getC_Invoice_Candidate_ID());
		assertThat(ofRecord.getMoney().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(ofRecord.getInvoiceableFrom()).isEqualTo(TimeUtil.asLocalDate(dateToInvoiceOfAssignableCand));
	}
}
