package de.metas.contracts.refund.allqties;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
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
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;

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

public class CandidateAssignServiceAllQties_Amount_Test
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);

	private static final Money money_0_30 = Money.of("0.3", CURRENCY_ID);
	private static final Money money_0_45 = Money.of("0.45", CURRENCY_ID);
	private static final Money money_0_70 = Money.of("0.7", CURRENCY_ID);
	private static final Money money_0_75 = Money.of("0.75", CURRENCY_ID);
	private static final Money money_0_90 = Money.of("0.9", CURRENCY_ID);
	private static final Money money_1_50 = Money.of("1.5", CURRENCY_ID);
	private static final Money money_1_80 = Money.of("1.8", CURRENCY_ID);
	private static final Money money_2_70 = Money.of("2.7", CURRENCY_ID);
	private static final Money money_3_00 = Money.of("3", CURRENCY_ID);
	private static final Money money_3_50 = Money.of("3.5", CURRENCY_ID);
	private static final Money money_2_10 = Money.of("2.1", CURRENCY_ID);
	private static final Money money_3_60 = Money.of("3.6", CURRENCY_ID);
	private static final Money money_4_20 = Money.of("4.2", CURRENCY_ID);
	private static final Money money_4_50 = Money.of("4.5", CURRENCY_ID);
	private static final Money money_5_00 = Money.of("5", CURRENCY_ID);
	private static final Money money_6_00 = Money.of("6", CURRENCY_ID);
	private static final Money money_6_90 = Money.of("6.9", CURRENCY_ID);
	private static final Money money_7_50 = Money.of("7.5", CURRENCY_ID);
	private static final Money money_9_00 = Money.of("9", CURRENCY_ID);
	private static final Money money_11_50 = Money.of("11.5", CURRENCY_ID);
	private static final Money money_11_70 = Money.of("11.7", CURRENCY_ID);
	private static final Money money_12_00 = Money.of("12", CURRENCY_ID);
	private static final Money money_13_80 = Money.of("13.8", CURRENCY_ID);
	private static final Money money_14_00 = Money.of("14", CURRENCY_ID);
	private static final Money money_20_00 = Money.of("20", CURRENCY_ID);
	private static final Money money_20_70 = Money.of("20.7", CURRENCY_ID);
	private static final Money money_30_00 = Money.of("30", CURRENCY_ID);
	private static final Money money_38_50 = Money.of("38.5", CURRENCY_ID);
	private static final Money money_46_00 = Money.of("46", CURRENCY_ID);
	private static final Money money_53_20 = Money.of("53.2", CURRENCY_ID);
	private static final Money money_59_50 = Money.of("59.5", CURRENCY_ID);

	private Quantity quantity_3;
	private Quantity quantity_6;
	private Quantity quantity_9;
	private Quantity quantity_12;
	private Quantity quantity_14;
	private Quantity quantity_20;
	private Quantity quantity_26;
	private Quantity quantity_30;
	private Quantity quantity_46;
	private Quantity quantity_55;
	private Quantity quantity_76;
	private Quantity quantity_85;

	private CandidateAssignServiceAllQties candidateAssignServiceAllQties;

	private ProductId productId;

	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	private RefundConfig savedRefundConfig_0;
	private RefundConfig savedRefundConfig_15;
	private RefundConfig savedRefundConfig_50;

	private RefundContract savedRefundContract;

	private RefundInvoiceCandidate savedRefundCandidate;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		PlainCurrencyDAO.prepareCurrency()
				.currencyCode(CurrencyCode.EUR)
				.currencyId(CURRENCY_ID)
				.build();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		quantity_3 = Quantity.of("3", uomRecord);
		quantity_6 = Quantity.of("6", uomRecord);
		quantity_9 = Quantity.of("9", uomRecord);
		quantity_14 = Quantity.of("14", uomRecord);
		quantity_12 = Quantity.of("12", uomRecord);
		quantity_20 = Quantity.of("20", uomRecord);
		quantity_26 = Quantity.of("26", uomRecord);
		quantity_30 = Quantity.of("30", uomRecord);
		quantity_46 = Quantity.of("46", uomRecord);
		quantity_55 = Quantity.of("55", uomRecord);
		quantity_76 = Quantity.of("76", uomRecord);
		quantity_85 = Quantity.of("85", uomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		this.candidateAssignServiceAllQties = CandidateAssignServiceAllQties.createInstanceForUnitTesting();

		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = candidateAssignServiceAllQties.getRefundInvoiceCandidateRepository();
		final RefundContractRepository refundContractRepository = refundInvoiceCandidateRepository.getRefundContractRepository();
		final RefundConfigRepository refundConfigRepository = refundContractRepository.getRefundConfigRepository();
		final InvoiceScheduleRepository invoiceScheduleRepository = refundConfigRepository.getInvoiceScheduleRepository();

		final AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory = new AssignableInvoiceCandidateFactory(
				candidateAssignServiceAllQties.getAssignmentToRefundCandidateRepository(),
				new CurrencyRepository());

		this.assignableInvoiceCandidateRepository = new AssignableInvoiceCandidateRepository(assignableInvoiceCandidateFactory);

		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(30)
				.invoiceDistance(6)
				.build();
		final InvoiceSchedule savedInvoiceSchedule = invoiceScheduleRepository.save(invoiceSchedule);

		final RefundConfig refundConfig_0 = RefundConfig.builder()
				.minQty(ZERO)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.AMOUNT_PER_UNIT)
				.amount(money_0_30)
				.productId(productId)
				.invoiceSchedule(savedInvoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();
		savedRefundConfig_0 = refundConfigRepository.save(refundConfig_0);

		final RefundConfig refundConfig_15 = RefundConfig.builder()
				.minQty(new BigDecimal("15"))
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.AMOUNT_PER_UNIT)
				.amount(money_0_45) // 0.15 more than the previous config
				.productId(productId)
				.invoiceSchedule(savedInvoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();
		savedRefundConfig_15 = refundConfigRepository.save(refundConfig_15);

		final RefundConfig refundConfig_50 = RefundConfig.builder()
				.minQty(new BigDecimal("50"))
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.AMOUNT_PER_UNIT)
				.amount(money_0_70) // 0.25 more than the previous config
				.productId(productId)
				.invoiceSchedule(savedInvoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();
		savedRefundConfig_50 = refundConfigRepository.save(refundConfig_50);

		final RefundContract refundContract = RefundContract.builder()
				.refundConfig(savedRefundConfig_50)
				.refundConfig(savedRefundConfig_15)
				.refundConfig(savedRefundConfig_0)
				.startDate(LocalDate.parse("2018-12-01"))
				.endDate(LocalDate.parse("2019-11-30"))
				.bPartnerId(BPartnerId.ofRepoId(2156423))
				.build();
		savedRefundContract = refundContractRepository.save(refundContract);

		final Money money_0 = Money.zero(CURRENCY_ID);
		final RefundInvoiceCandidate refundCandidate = RefundInvoiceCandidate
				.builder()
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(2156423, 2))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(savedRefundContract)
				.refundConfig(savedRefundConfig_0)
				.money(money_0)
				.assignedQuantity(Quantity.zero(uomRecord))
				.build();
		savedRefundCandidate = refundInvoiceCandidateRepository.save(refundCandidate);
	}

	/**
	 * Create a refund candidate and then assign four different assignable candidates to it, verifying after each assignment.
	 * Special: the 2nd and 3trd assignable candidate are both in the range of {@link #savedRefundConfig_15}.
	 */
	@Test
	public void updateAssignment()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final AssignableInvoiceCandidate assignableCandidate_14 = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_14_00)
				.precision(2)
				.quantity(quantity_14)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_14 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_14);

		final RefundInvoiceCandidate refundCandidateAfter1stInvocation;
		{
			// invoke the method under test
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_14, savedRefundCandidate, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_4_20/* 14*0.3 */, quantity_14, true));

			refundCandidateAfter1stInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);
			assertThat(refundCandidateAfter1stInvocation.getAssignedQuantity()).isEqualTo(quantity_14);
			assertThat(refundCandidateAfter1stInvocation.getMoney()).isEqualTo(money_4_20);
			assertThat(refundCandidateAfter1stInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0);
		}

		final AssignableInvoiceCandidate assignableCandidate_12 = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_12_00)
				.precision(2)
				.quantity(quantity_12)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_12 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_12);

		final RefundInvoiceCandidate refundCandidateAfter2ndInvocation;
		{
			// invoke the method under test a 2nd time
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_12, refundCandidateAfter1stInvocation, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_3_60/* 12*0.3 */, quantity_12, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_1_80/* 12*0.15 */, quantity_12, false));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_4_20/* 14*0.3 */, quantity_14, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_3_60/* 12*0.3 */, quantity_12, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_2_10/* 14*0.15 */, quantity_14, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_1_80/* 12*0.15 */, quantity_12, false));

			refundCandidateAfter2ndInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);
			assertThat(refundCandidateAfter2ndInvocation.getAssignedQuantity()).isEqualTo(quantity_26);
			assertThat(refundCandidateAfter2ndInvocation.getMoney()).isEqualTo(money_11_70); // 26*0.45
			assertThat(refundCandidateAfter2ndInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0, savedRefundConfig_15);
		}

		// add a 3rd assignable candidate, but note that with its quantity, we stay within savedRefundConfig_15
		final AssignableInvoiceCandidate assignableCandidate_20 = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_20_00)
				.precision(2)
				.quantity(quantity_20)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_20 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_20);

		final RefundInvoiceCandidate refundCandidateAfter3rdInvocation;
		{
			// invoke the method under test a 3rd time
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_20, refundCandidateAfter2ndInvocation, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_6_00/* 20*0.3 */, quantity_20, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_3_00/* 20*0.15 */, quantity_20, false));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_4_20/* 14*0.3 */, quantity_14, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_3_60/* 12*0.3 */, quantity_12, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_6_00/* 20*0.3 */, quantity_20, true),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_2_10/* 14*0.15 */, quantity_14, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_1_80/* 12*0.15 */, quantity_12, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_3_00/* 20*0.15 */, quantity_20, false));

			refundCandidateAfter3rdInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);

			assertThat(refundCandidateAfter3rdInvocation.getAssignedQuantity()).isEqualTo(quantity_46);
			assertThat(refundCandidateAfter3rdInvocation.getMoney()).isEqualTo(money_20_70); // 46 * 0.45
			assertThat(refundCandidateAfter3rdInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0, savedRefundConfig_15);
		}

		final AssignableInvoiceCandidate assignableCandidate_30 = AssignableInvoiceCandidate.builder()
				.id(InvoiceCandidateId.ofRepoId(1000025))
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_30_00)
				.precision(2)
				.quantity(quantity_30)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_30 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_30);

		{
			// invoke the method under test a 4th time
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_30, refundCandidateAfter3rdInvocation, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_9_00/* 30*0.3 */, quantity_30, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_4_50/* 30*0.15 */, quantity_30, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_7_50/* 30*0.25 */, quantity_30, false));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_4_20/* 14*0.3 */, quantity_14, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_3_60/* 12*0.3 */, quantity_12, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_6_00/* 20*0.3 */, quantity_20, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_9_00/* 30*0.3 */, quantity_30, true),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_2_10/* 14*0.15 */, quantity_14, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_1_80/* 12*0.15 */, quantity_12, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_3_00/* 20*0.15 */, quantity_20, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_4_50/* 30*0.15 */, quantity_30, false),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_14.getId(), money_14_00, money_3_50/* 14*0.25 */, quantity_14, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_12.getId(), money_12_00, money_3_00/* 12*0.25 */, quantity_12, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_20.getId(), money_20_00, money_5_00/* 20*0.25 */, quantity_20, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_7_50/* 30*0.25 */, quantity_30, false));

			final RefundInvoiceCandidate refundCandidateAfter4thdInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);

			assertThat(refundCandidateAfter4thdInvocation.getAssignedQuantity()).isEqualTo(quantity_76);
			assertThat(refundCandidateAfter4thdInvocation.getMoney()).isEqualTo(money_53_20); // 76*0.7
			assertThat(refundCandidateAfter4thdInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0, savedRefundConfig_15, savedRefundConfig_50);
		}
	}

	/**
	 * Create a refund candidate and then assign four different assignable candidates to it, verifying after each assignment.
	 * Special: the 1st and 2nd assignable candidate are both in the range of {@link #savedRefundConfig_0} and the 3rd assignable candidate skips right into the range of {@link #savedRefundConfig_50}.
	 */
	@Test
	public void updateAssignment2()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final AssignableInvoiceCandidate assignableCandidate_3 = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_3_00)
				.precision(2)
				.quantity(quantity_3)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_3 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_3);

		final RefundInvoiceCandidate refundCandidateAfter1stInvocation;
		{
			// invoke the method under test
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_3, savedRefundCandidate, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_90/* 3*0.3 */, quantity_3, true));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_90/* 3*0.3 */, quantity_3, true));

			refundCandidateAfter1stInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);
			assertThat(refundCandidateAfter1stInvocation.getAssignedQuantity()).isEqualTo(quantity_3);
			assertThat(refundCandidateAfter1stInvocation.getMoney()).isEqualTo(money_0_90);
			assertThat(refundCandidateAfter1stInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0);
		}

		// add a 2nd assignable candidate, but note that with its quantity, we stay within savedRefundConfig_0
		final AssignableInvoiceCandidate assignableCandidate_6 = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_6_00)
				.precision(2)
				.quantity(quantity_6)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_6 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_6);

		final RefundInvoiceCandidate refundCandidateAfter2ndInvocation;
		{
			// invoke the method under test a 2nd time
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_6, refundCandidateAfter1stInvocation, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_1_80/* 6*0.3 */, quantity_6, true));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_90/* 3*0.3 */, quantity_3, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_1_80/* 6*0.3 */, quantity_6, true));

			refundCandidateAfter2ndInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);
			assertThat(refundCandidateAfter2ndInvocation.getAssignedQuantity()).isEqualTo(quantity_9);
			assertThat(refundCandidateAfter2ndInvocation.getMoney()).isEqualTo(money_2_70); // 9*0.3
			assertThat(refundCandidateAfter2ndInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0);
		}

		// add a 3rd assignable candidate, but note that with its quantity, jump right into savedRefundConfig_50
		final AssignableInvoiceCandidate assignableCandidate_46 = AssignableInvoiceCandidate.builder()
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_46_00)
				.precision(2)
				.quantity(quantity_46)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_46 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_46);

		final RefundInvoiceCandidate refundCandidateAfter3rdInvocation;
		{
			// invoke the method under test a 3rd time
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_46, refundCandidateAfter2ndInvocation, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_13_80/* 46*0.3 */, quantity_46, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_6_90/* 46*0.15 */, quantity_46, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_11_50/* 46*0.25 */, quantity_46, false));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_90/* 3*0.3 */, quantity_3, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_1_80/* 6*0.3 */, quantity_6, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_13_80/* 46*0.3 */, quantity_46, true),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_45/* 3*0.15 */, quantity_3, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_0_90/* 6*0.15 */, quantity_6, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_6_90/* 46*0.15 */, quantity_46, false),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_75/* 3*0.25 */, quantity_3, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_1_50/* 6*0.25 */, quantity_6, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_11_50/* 46*0.25 */, quantity_46, false));

			refundCandidateAfter3rdInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);

			assertThat(refundCandidateAfter3rdInvocation.getAssignedQuantity()).isEqualTo(quantity_55);
			assertThat(refundCandidateAfter3rdInvocation.getMoney()).isEqualTo(money_38_50); // 55*0.7
			assertThat(refundCandidateAfter3rdInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0, savedRefundConfig_15, savedRefundConfig_50);
		}

		final AssignableInvoiceCandidate assignableCandidate_30 = AssignableInvoiceCandidate.builder()
				.id(InvoiceCandidateId.ofRepoId(1000025))
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(productId)
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_30_00)
				.precision(2)
				.quantity(quantity_30)
				.build();
		final AssignableInvoiceCandidate savedAssignableCandidate_30 = assignableInvoiceCandidateRepository.saveNew(assignableCandidate_30);

		{
			// invoke the method under test a 4th time
			final UpdateAssignmentResult updateAssignment = candidateAssignServiceAllQties.updateAssignment(savedAssignableCandidate_30, refundCandidateAfter3rdInvocation, savedRefundContract);

			assertThat(updateAssignment.isUpdateWasDone()).isTrue();

			final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
			assertThat(assignmentsToRefundCandidates)
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_9_00/* 30*0.3 */, quantity_30, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_4_50/* 30*0.15 */, quantity_30, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_7_50/* 30*0.25 */, quantity_30, false));

			assertThat(retrieveAllAssignmentsToRefundCandidates())
					.extracting("refundInvoiceCandidate.id", "refundConfigId", "assignableInvoiceCandidateId", "moneyBase", "moneyAssignedToRefundCandidate", "quantityAssigendToRefundCandidate", "useAssignedQtyInSum")
					.containsOnly(
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_90/* 3*0.3 */, quantity_3, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_1_80/* 6*0.3 */, quantity_6, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_13_80/* 46*0.3 */, quantity_46, true),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_0.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_9_00/* 30*0.3 */, quantity_30, true),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_45/* 3*0.15 */, quantity_3, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_0_90/* 6*0.15 */, quantity_6, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_6_90/* 46*0.15 */, quantity_46, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_15.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_4_50/* 30*0.15 */, quantity_30, false),

							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_3.getId(), money_3_00, money_0_75/* 3*0.25 */, quantity_3, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_6.getId(), money_6_00, money_1_50/* 6*0.25 */, quantity_6, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_46.getId(), money_46_00, money_11_50/* 46*0.25 */, quantity_46, false),
							tuple(savedRefundCandidate.getId(), savedRefundConfig_50.getId(), savedAssignableCandidate_30.getId(), money_30_00, money_7_50/* 30*0.25 */, quantity_30, false));

			final RefundInvoiceCandidate refundCandidateAfter4thdInvocation = CollectionUtils.extractSingleElement(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);

			assertThat(refundCandidateAfter4thdInvocation.getAssignedQuantity()).isEqualTo(quantity_85);
			assertThat(refundCandidateAfter4thdInvocation.getMoney()).isEqualTo(money_59_50); // 85*0.7
			assertThat(refundCandidateAfter4thdInvocation.getRefundConfigs()).containsOnly(savedRefundConfig_0, savedRefundConfig_15, savedRefundConfig_50);
		}
	}

	private ImmutableList<AssignmentToRefundCandidate> retrieveAllAssignmentsToRefundCandidates()
	{
		return RefundTestTools
				.retrieveAllAssignmentsToRefundCandidates(
						candidateAssignServiceAllQties.getAssignmentToRefundCandidateRepository());
	}
}
