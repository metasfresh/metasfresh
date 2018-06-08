package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyRepository;
import de.metas.money.MoneyFactory;

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

public class InvoiceCandidateFactoryTest
{
	private static final BigDecimal THREE = new BigDecimal("3");;
	private static final BigDecimal NINE = new BigDecimal("9");

	private I_C_BPartner bPartnerRecord;
	private I_M_Product productRecord;
	private I_C_Invoice_Candidate assignableIcRecord;
	private I_C_Invoice_Candidate refundContractIcRecord;
	private Timestamp dateToInvoiceOfAssignableCand;
	private I_C_Flatrate_Term refundContractRecord;
	private I_C_Invoice_Candidate_Assignment assignmentRecord;
	private InvoiceCandidateFactory invoiceCandidateFactory;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		dateToInvoiceOfAssignableCand = SystemTime.asTimestamp();

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setStdPrecision(2);
		save(currencyRecord);

		bPartnerRecord = newInstance(I_C_BPartner.class);
		save(bPartnerRecord);

		productRecord = newInstance(I_M_Product.class);
		save(productRecord);

		assignableIcRecord = newInstance(I_C_Invoice_Candidate.class);
		assignableIcRecord.setBill_BPartner(bPartnerRecord);
		assignableIcRecord.setM_Product(productRecord);
		assignableIcRecord.setDateToInvoice(dateToInvoiceOfAssignableCand);
		assignableIcRecord.setNetAmtInvoiced(ONE);
		assignableIcRecord.setNetAmtToInvoice(NINE);
		assignableIcRecord.setC_Currency(currencyRecord);
		save(assignableIcRecord);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditionsRecord);

		final I_C_InvoiceSchedule invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily);
		saveRecord(invoiceSchedule);

		final I_C_Flatrate_RefundConfig refundConfigRecord = newInstance(I_C_Flatrate_RefundConfig.class);
		refundConfigRecord.setC_Flatrate_Conditions(conditionsRecord);
		refundConfigRecord.setM_Product(productRecord);
		refundConfigRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
		refundConfigRecord.setC_InvoiceSchedule(invoiceSchedule);
		refundConfigRecord.setPercent(THREE);
		saveRecord(refundConfigRecord);

		refundContractRecord = newInstance(I_C_Flatrate_Term.class);
		refundContractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		refundContractRecord.setC_Flatrate_Conditions(conditionsRecord);
		refundContractRecord.setM_Product(productRecord);
		refundContractRecord.setC_Currency(currencyRecord);
		refundContractRecord.setStartDate(TimeUtil.asTimestamp(RefundTestTools.CONTRACT_START_DATE));
		refundContractRecord.setEndDate(TimeUtil.asTimestamp(RefundTestTools.CONTRACT_END_DATE));
		save(refundContractRecord);

		refundContractIcRecord = newInstance(I_C_Invoice_Candidate.class);
		refundContractIcRecord.setBill_BPartner(bPartnerRecord);
		refundContractIcRecord.setM_Product(productRecord);
		refundContractIcRecord.setDateToInvoice(dateToInvoiceOfAssignableCand);
		refundContractIcRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		refundContractIcRecord.setRecord_ID(refundContractRecord.getC_Flatrate_Term_ID());
		refundContractIcRecord.setC_Currency(currencyRecord);
		refundContractIcRecord.setPriceActual(TEN);
		save(refundContractIcRecord);

		assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
		assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignableIcRecord.getC_Invoice_Candidate_ID());
		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundContractIcRecord.getC_Invoice_Candidate_ID());
		save(assignmentRecord);

		invoiceCandidateFactory = new InvoiceCandidateRepository(
				new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())),
				new MoneyFactory(new CurrencyRepository()))
						.getInvoiceCandidateFactory();
	}

	@Test
	public void ofRecord_AssignableInvoiceCandidate()
	{
		// invoke the method under test
		final InvoiceCandidate ofRecord = invoiceCandidateFactory.ofRecord(assignableIcRecord);

		assertThat(ofRecord).isInstanceOf(AssignableInvoiceCandidate.class);
		final AssignableInvoiceCandidate cast = AssignableInvoiceCandidate.cast(ofRecord);

		assertThat(cast.getBpartnerId().getRepoId()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
		assertThat(cast.getProductId().getRepoId()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(cast.getAssignmentToRefundCandidate().getRefundInvoiceCandidate().getId().getRepoId()).isEqualTo(refundContractIcRecord.getC_Invoice_Candidate_ID());
		assertThat(cast.getMoney().getValue()).isEqualByComparingTo(TEN);
		assertThat(cast.getInvoiceableFrom()).isEqualTo(TimeUtil.asLocalDate(dateToInvoiceOfAssignableCand));
	}

	@Test
	public void ofRecord_RefundContractInvoiceCandidate()
	{
		// invoke the method under test
		final InvoiceCandidate ofRecord = invoiceCandidateFactory.ofRecord(refundContractIcRecord);

		assertThat(ofRecord).isInstanceOf(RefundInvoiceCandidate.class);
		final RefundInvoiceCandidate cast = RefundInvoiceCandidate.cast(ofRecord);

		assertThat(cast.getBpartnerId().getRepoId()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
		assertThat(cast.getRefundContract().getId().getRepoId()).isEqualTo(refundContractRecord.getC_Flatrate_Term_ID());
		assertThat(cast.getMoney().getValue()).isEqualByComparingTo(TEN);
		assertThat(cast.getInvoiceableFrom()).isEqualTo(TimeUtil.asLocalDate(dateToInvoiceOfAssignableCand));

		final RefundConfig refundConfig = cast.getRefundContract().getRefundConfig();
		assertThat(refundConfig.getProductId().getRepoId()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(refundConfig.getPercent().getValueAsBigDecimal()).isEqualByComparingTo(THREE);
	}

	@Test
	public void ofRecord_AssignableInvoiceCandidate_no_assignment_record()
	{
		delete(assignmentRecord);

		// invoke the method under test
		final InvoiceCandidate ofRecord = invoiceCandidateFactory.ofRecord(assignableIcRecord);

		assertThat(ofRecord).isInstanceOf(AssignableInvoiceCandidate.class);
		final AssignableInvoiceCandidate cast = AssignableInvoiceCandidate.cast(ofRecord);

		assertThat(cast.getBpartnerId().getRepoId()).isEqualTo(bPartnerRecord.getC_BPartner_ID());
		assertThat(cast.getProductId().getRepoId()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(cast.getAssignmentToRefundCandidate()).isNull();;
		assertThat(cast.getInvoiceableFrom()).isEqualTo(TimeUtil.asLocalDate(dateToInvoiceOfAssignableCand));
	}
}
