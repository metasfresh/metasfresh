package de.metas.contracts.refund;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.Percent;
import de.metas.money.CurrencyRepository;
import de.metas.money.MoneyService;

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

public class RefundInvoiceCandidateServiceTest
{
	private static final BigDecimal TWENTY = new BigDecimal("20");

	private static final LocalDate NOW = LocalDate.now();

	private final static BigDecimal FIVE = new BigDecimal("5");

	private RefundInvoiceCandidateService refundInvoiceCandidateService;
	private I_C_UOM uomRecord;

	private InvoiceSchedule invoiceSchedule;

	private InvoiceCandidateRepository invoiceCandidateRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_ILCandHandler icHandlerRecord = newInstance(I_C_ILCandHandler.class);
		icHandlerRecord.setClassname(FlatrateTerm_Handler.class.getName());
		saveRecord(icHandlerRecord);

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final RefundContractRepository refundContractRepository = new RefundContractRepository(
				new RefundConfigRepository(
						new InvoiceScheduleRepository()));

		invoiceCandidateRepository = new InvoiceCandidateRepository(refundContractRepository);

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(5))
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(1)
				.build();

		refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				invoiceCandidateRepository,
				moneyService);
	}

	@Test
	public void retrieveOrCreateMatchingCandidate_create()
	{

		final I_C_Invoice_Candidate assignableRecord = InvoiceCandidateRepositoryTest.createAssignableCandidateRecord();
		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.cast(invoiceCandidateRepository.ofRecord(assignableRecord));

		final RefundConfig refundConfig1 = RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(invoiceSchedule)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.refundMode(RefundMode.ALL_MAX_SCALE)
				.minQty(ZERO)
				.percent(Percent.of(20))
				.build();

		final RefundConfig refundConfig2 = refundConfig1.toBuilder()
				.minQty(FIVE)
				.percent(Percent.of(30))
				.build();

		final RefundContract refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(100))
				.startDate(NOW)
				.endDate(NOW.plusDays(10))
				.refundConfig(refundConfig2)
				.refundConfig(refundConfig1)
				.build();

		// invoke the method under test
		final RefundInvoiceCandidate result = refundInvoiceCandidateService.retrieveOrCreateMatchingCandidate(assignableCandidate, refundContract);

		assertThat(result.getRefundContract()).isEqualTo(refundContract);
		assertThat(result.getRefundConfig()).isEqualTo(refundConfig2);
	}

}
