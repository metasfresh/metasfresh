package de.metas.contracts.refund.exceedingqty;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.AssignableInvoiceCandidateFactory;
import de.metas.contracts.refund.AssignableInvoiceCandidateRepository;
import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.CandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundTestTools;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.service.InvoiceScheduleRepository;
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

@ExtendWith(AdempiereTestWatcher.class)
public class CandidateAssignServiceExceedingQty_Percent_Test
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);

	private static final Money money_1_40 = Money.of(new BigDecimal("1.4"), CURRENCY_ID);
	private static final Money money_2_40 = Money.of(new BigDecimal("2.4"), CURRENCY_ID);
	private static final Money money_2_80 = Money.of(new BigDecimal("2.8"), CURRENCY_ID);
	private static final Money money_12 = Money.of(new BigDecimal("12"), CURRENCY_ID);
	private static final Money money_14 = Money.of(new BigDecimal("14"), CURRENCY_ID);
	private static final Money money_26 = Money.of(new BigDecimal("26"), CURRENCY_ID);

	private Quantity quantity_0;
	private Quantity quantity_12;
	private Quantity quantity_14;
	private Quantity quantity_26;

	private CandidateAssignServiceExceedingQty candidateAssignServiceExceedingQty;
	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	private ProductId productId;

	private RefundContract savedRefundContract;

	private RefundConfig savedRefundConfig_15;
	private RefundConfig savedRefundConfig_0;

	private RefundInvoiceCandidate savedRefundCandidate_0;
	private RefundInvoiceCandidate savedRefundCandidate_14;

	private RefundTestTools refundTestTools;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final Currency currency = PlainCurrencyDAO.prepareCurrency()
				.currencyId(CURRENCY_ID)
				.currencyCode(CurrencyCode.EUR)
				.precision(CurrencyPrecision.TWO)
				.build();

		refundTestTools = RefundTestTools.builder()
				.currency(currency)
				.build();

		candidateAssignServiceExceedingQty = CandidateAssignServiceExceedingQty.createInstanceForUnitTesting();

		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = candidateAssignServiceExceedingQty.getRefundInvoiceCandidateRepository();
		final RefundContractRepository refundContractRepository = refundInvoiceCandidateRepository.getRefundContractRepository();
		final RefundConfigRepository refundConfigRepository = refundContractRepository.getRefundConfigRepository();
		final InvoiceScheduleRepository invoiceScheduleRepository = refundConfigRepository.getInvoiceScheduleRepository();

		final AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory = new AssignableInvoiceCandidateFactory(
				candidateAssignServiceExceedingQty.getAssignmentToRefundCandidateRepository(),
				new CurrencyRepository());

		this.assignableInvoiceCandidateRepository = new AssignableInvoiceCandidateRepository(assignableInvoiceCandidateFactory);

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		quantity_0 = Quantity.zero(uomRecord);
		quantity_12 = Quantity.of("12", uomRecord);
		quantity_14 = Quantity.of("14", uomRecord);
		quantity_26 = Quantity.of("26", uomRecord);

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
				.refundMode(RefundMode.APPLY_TO_EXCEEDING_QTY)
				.build();
		savedRefundConfig_0 = refundConfigRepository.save(refundConfig_0);

		final RefundConfig refundConfig_15 = RefundConfig.builder()
				.minQty(new BigDecimal("15"))
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(Percent.of("20"))
				.productId(productId)
				.invoiceSchedule(savedInvoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_EXCEEDING_QTY)
				.build();
		savedRefundConfig_15 = refundConfigRepository.save(refundConfig_15);

		final RefundContract refundContract = RefundContract.builder()
				.refundConfig(refundConfig_15)
				.refundConfig(refundConfig_0)
				.startDate(LocalDate.parse("2018-12-01"))
				.endDate(LocalDate.parse("2019-11-30"))
				.bPartnerId(refundTestTools.billBPartnerLocationId.getBpartnerId())
				.build();
		savedRefundContract = refundContractRepository.save(refundContract);

		final RefundInvoiceCandidate refundCandidate_14 = RefundInvoiceCandidate
				.builder()
				.bpartnerId(refundTestTools.billBPartnerLocationId.getBpartnerId())
				.bpartnerLocationId(refundTestTools.billBPartnerLocationId)
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(savedRefundContract)
				.refundConfig(savedRefundConfig_15)
				.money(money_2_80)
				.assignedQuantity(quantity_14)
				.build();
		savedRefundCandidate_14 = refundInvoiceCandidateRepository.save(refundCandidate_14);

		final RefundInvoiceCandidate refundCandidate_0 = RefundInvoiceCandidate
				.builder()
				.bpartnerId(refundTestTools.billBPartnerLocationId.getBpartnerId())
				.bpartnerLocationId(refundTestTools.billBPartnerLocationId)
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(savedRefundContract)
				.refundConfig(savedRefundConfig_0)
				.money(Money.zero(CURRENCY_ID))
				.assignedQuantity(quantity_0)
				.build();
		savedRefundCandidate_0 = refundInvoiceCandidateRepository.save(refundCandidate_0);
	}

	/**
	 * Create two percent-based refund configs:
	 * <li>0 - 14 with 10%
	 * <li>15 and greater with 20%
	 * <p/>
	 * Create two refund candidates (one per config) and one assignable candidate with quantity 26
	 * <p/>
	 * Invoke the method under test and verify that the assignable candidate is assigned to both refund candidates in accordance to its quantity and the two refund configs.
	 */
	@Test
	public void updateAssignment()
	{

		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(refundTestTools.billBPartnerLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_26)
				.precision(2)
				.quantity(quantity_26)
				.build();

		final AssignableInvoiceCandidate savedAssignableCandidate = assignableInvoiceCandidateRepository.saveNew(assignableCandidate);
		final AssignableInvoiceCandidate savedAssignableCandidateWithAssignments = savedAssignableCandidate.toBuilder()
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(savedRefundConfig_15.getId())
						.assignableInvoiceCandidateId(savedAssignableCandidate.getId())
						.refundInvoiceCandidate(savedRefundCandidate_14)
						.moneyBase(money_12)
						.moneyAssignedToRefundCandidate(money_2_40) // 20%
						.quantityAssigendToRefundCandidate(quantity_12)
						.useAssignedQtyInSum(true)
						.build())
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(savedRefundConfig_0.getId())
						.assignableInvoiceCandidateId(savedAssignableCandidate.getId())
						.refundInvoiceCandidate(savedRefundCandidate_0)
						.moneyBase(money_14)
						.moneyAssignedToRefundCandidate(money_1_40) // 10%
						.quantityAssigendToRefundCandidate(quantity_14)
						.useAssignedQtyInSum(true)
						.build())
				.build();

		final List<RefundInvoiceCandidate> refundCandidatesToAssignTo = ImmutableList.of(
				savedRefundCandidate_14,
				savedRefundCandidate_0);

		// invoke the method under test
		final UpdateAssignmentResult updateAssignment = candidateAssignServiceExceedingQty.updateAssignment(savedAssignableCandidateWithAssignments, refundCandidatesToAssignTo, savedRefundContract);

		assertThat(updateAssignment.isUpdateWasDone()).isTrue();

		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
		assertThat(assignmentsToRefundCandidates)
				.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
				.containsOnly(
						tuple(savedRefundCandidate_0.getId(), savedRefundConfig_0.getId(), savedAssignableCandidateWithAssignments.getId(), money_14, money_1_40/* 10% */, quantity_14, true),
						tuple(savedRefundCandidate_14.getId(), savedRefundConfig_15.getId(), savedAssignableCandidateWithAssignments.getId(), money_12, money_2_40/* 20% */, quantity_12, true));

		assertThat(retrieveAllAssignmentsToRefundCandidates())
				.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
				.containsOnlyOnce(
						tuple(savedRefundCandidate_0.getId(), savedRefundConfig_0.getId(), savedAssignableCandidateWithAssignments.getId(), money_14, money_1_40/* 10% */, quantity_14, true),
						tuple(savedRefundCandidate_14.getId(), savedRefundConfig_15.getId(), savedAssignableCandidateWithAssignments.getId(), money_12, money_2_40/* 20% */, quantity_12, true));
	}

	private ImmutableList<AssignmentToRefundCandidate> retrieveAllAssignmentsToRefundCandidates()
	{
		return RefundTestTools
				.retrieveAllAssignmentsToRefundCandidates(
						candidateAssignServiceExceedingQty.getAssignmentToRefundCandidateRepository());
	}
}
