package de.metas.contracts.refund.allqties.refundconfigchange;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.currency.CurrencyRepository;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.money.MoneyService;
import de.metas.util.lang.Percent;

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

public class RefundConfigChangeServiceTests
{
	private static final LocalDate NOW = LocalDate.now();

	private RefundConfigChangeService refundConfigChangeService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();

		final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(
				refundInvoiceCandidateRepository);

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				refundInvoiceCandidateRepository,
				moneyService);

		refundConfigChangeService = new RefundConfigChangeService(
				assignmentToRefundCandidateRepository,
				moneyService,
				refundInvoiceCandidateService);
	}

	@Test
	public void getRefundConfigRange()
	{
		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(5))
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(1)
				.build();

		final RefundConfig refundConfig1 = RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(invoiceSchedule)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.minQty(ZERO)
				.percent(Percent.of(20))
				.build();

		final RefundConfig refundConfig2 = refundConfig1.toBuilder()
				.minQty(new BigDecimal("5"))
				.percent(Percent.of(30))
				.build();

		final RefundConfig refundConfig3 = refundConfig1.toBuilder()
				.minQty(TEN)
				.build();
		final RefundConfig refundConfig4 = refundConfig1.toBuilder()
				.minQty(new BigDecimal("15"))
				.build();

		final RefundContract refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(100))
				.bPartnerId(BPartnerId.ofRepoId(200))
				.startDate(NOW)
				.endDate(NOW.plusDays(10))
				.clearRefundConfigs()
				.refundConfig(refundConfig1)
				.refundConfig(refundConfig2)
				.refundConfig(refundConfig3)
				.refundConfig(refundConfig4)
				.build();

		assertThat(refundConfigChangeService.getRefundConfigRange(refundContract, refundConfig1, refundConfig4))
				.containsExactlyInAnyOrder(refundConfig2, refundConfig3, refundConfig4);

		assertThat(refundConfigChangeService.getRefundConfigRange(refundContract, refundConfig2, refundConfig4))
				.containsExactlyInAnyOrder(refundConfig3, refundConfig4);

		assertThat(refundConfigChangeService.getRefundConfigRange(refundContract, refundConfig1, refundConfig2))
				.containsExactlyInAnyOrder(refundConfig2);

		assertThat(refundConfigChangeService.getRefundConfigRange(refundContract, refundConfig4, refundConfig1))
				.containsExactlyInAnyOrder(refundConfig4, refundConfig3, refundConfig2);

		assertThat(refundConfigChangeService.getRefundConfigRange(refundContract, refundConfig3, refundConfig1))
				.containsExactlyInAnyOrder(refundConfig3, refundConfig2);

		assertThat(refundConfigChangeService.getRefundConfigRange(refundContract, refundConfig3, refundConfig2))
				.containsExactlyInAnyOrder(refundConfig3);
	}
}
