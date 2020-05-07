package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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

public class RefundInvoiceCandidateRepositoryTest
{
	private RefundTestTools refundTestTools;
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundTestTools = RefundTestTools.newInstance();

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);
		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);
		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);

		refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(
				refundContractRepository,
				refundInvoiceCandidateFactory);
	}

	@Test
	public void getRefundInvoiceCandidate_same_invoicableFrom()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContract(refundCandidate.getRefundContract())
				.invoicableFrom(refundCandidate.getInvoiceableFrom())
				.build();

		// invoke the method under test
		final List<RefundInvoiceCandidate> result = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(query);
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(refundCandidate);
	}

	@Test
	public void getRefundInvoiceCandidate_earlier_invoicableFrom()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();

		final LocalDate earlierInvoiceableFrom = refundCandidate
				.getInvoiceableFrom()
				.minusDays(2); // the contract starts 3 days before this, so we are still within

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContract(refundCandidate.getRefundContract())
				.invoicableFrom(earlierInvoiceableFrom)
				.build();

		// invoke the method under test
		final List<RefundInvoiceCandidate> result = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(query);

		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(refundCandidate);
	}

	@Test
	public void getRefundInvoiceCandidate_later_invoicableFrom()
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate();

		final LocalDate earlierInvoiceableFrom = refundCandidate
				.getInvoiceableFrom()
				.plusDays(1);

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContract(refundCandidate.getRefundContract())
				.invoicableFrom(earlierInvoiceableFrom)
				.build();

		// invoke the method under test
		final List<RefundInvoiceCandidate> result = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(query);

		assertThat(result)
				.as("Expect empty result bc the query is for a time range coming after refundCandidate's date; query=%s", query)
				.isEmpty();
	}

	@Test
	public void save()
	{
		final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final RefundContractRepository refundContractRepository = refundInvoiceCandidateRepository.getRefundContractRepository();
		final RefundConfigRepository refundConfigRepository = refundContractRepository.getRefundConfigRepository();
		final InvoiceScheduleRepository invoiceScheduleRepository = refundConfigRepository.getInvoiceScheduleRepository();

		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(30)
				.invoiceDistance(6)
				.build();
		final InvoiceSchedule savedInvoiceSchedule = invoiceScheduleRepository.save(invoiceSchedule);

		final RefundConfig refundConfig_0 = RefundConfig.builder()
				.minQty(ZERO)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(Percent.of(TEN))
				.productId(productId)
				.invoiceSchedule(savedInvoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();
		final RefundConfig savedRefundConfig_0 = refundConfigRepository.save(refundConfig_0);

		final RefundConfig refundConfig_15 = RefundConfig.builder()
				.minQty(new BigDecimal("15"))
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(Percent.of(new BigDecimal("20")))
				.productId(productId)
				.invoiceSchedule(savedInvoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();
		final RefundConfig savedRefundConfig_15 = refundConfigRepository.save(refundConfig_15);

		final RefundContract refundContract = RefundContract.builder()
				.refundConfig(savedRefundConfig_15)
				.refundConfig(savedRefundConfig_0)
				.startDate(LocalDate.parse("2018-12-01"))
				.endDate(LocalDate.parse("2019-11-30"))
				.bPartnerId(BPartnerId.ofRepoId(2156423))
				.build();
		final RefundContract savedRefundContract = refundContractRepository.save(refundContract);

		final RefundInvoiceCandidate refundCandidate = RefundInvoiceCandidate
				.builder()
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(2156423, 2))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(savedRefundContract)
				.refundConfig(savedRefundConfig_0)
				.money(Money.zero(CURRENCY_ID))
				.assignedQuantity(Quantity.zero(uomRecord))
				.build();

		// invoke the method under test
		final RefundInvoiceCandidate savedRefundCandidate = refundInvoiceCandidateRepository.save(refundCandidate);

		final I_C_Invoice_Candidate resultRecord = load(savedRefundCandidate.getId(), I_C_Invoice_Candidate.class);
		assertThat(resultRecord.getM_Product_ID()).isEqualTo(productId.getRepoId());
	}
}
