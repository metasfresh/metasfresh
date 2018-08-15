package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.util.collections.CollectionUtils.singleElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService.UnassignResult;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.Percent;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

public class InvoiceCandidateAssignmentServiceTest
{
	private static final BigDecimal SEVEN = new BigDecimal("7");

	private static final BigDecimal FOUR = new BigDecimal("4");

	private static final BigDecimal THREE = new BigDecimal("3");

	private static final BigDecimal TWO = new BigDecimal("2");;

	private static final BigDecimal THIRTEEN = new BigDecimal("13");

	private static final BigDecimal FOURTEEN = new BigDecimal("14");

	private static final BigDecimal FIFTEEN = new BigDecimal("15");

	private static final BigDecimal SIXTEEN = new BigDecimal("16");

	private static final BigDecimal TWENTY = new BigDecimal("20");

	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private static final LocalDate NOW = LocalDate.now();

	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	private InvoiceCandidateAssignmentService invoiceCandidateAssignmentService;

	private RefundTestTools refundTestTools;
	private RefundConfigRepository refundConfigRepository;
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private RefundContractRepository refundContractRepository;

	private AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());

		refundContractRepository = new RefundContractRepository(refundConfigRepository);

		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository);

		refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(refundContractRepository, refundInvoiceCandidateFactory);

		assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(
				refundInvoiceCandidateRepository);

		final InvoiceCandidateRepository invoiceCandidateRepository = new InvoiceCandidateRepository();

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		final AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory = new AssignableInvoiceCandidateFactory();
		assignableInvoiceCandidateRepository = new AssignableInvoiceCandidateRepository(assignableInvoiceCandidateFactory);

		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				refundInvoiceCandidateRepository,
				refundInvoiceCandidateFactory,
				moneyService);

		invoiceCandidateAssignmentService = new InvoiceCandidateAssignmentService(
				refundContractRepository,
				invoiceCandidateRepository,
				assignableInvoiceCandidateRepository,
				refundInvoiceCandidateService,
				refundInvoiceCandidateRepository,
				assignmentToRefundCandidateRepository);

		refundTestTools = new RefundTestTools();
	}

	@Test
	public void unassignCandidate_AssignableInvoiceCandidate_without_assignment()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		// invoke the method under test
		final Throwable thrown = catchThrowable(() -> invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate));
		assertThat(thrown).isNotNull();
	}

	@Test
	public void unassignCandidate()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();

		final AssignmentToRefundCandidate assignmentToRefundCandidate = singleElement(assignableInvoiceCandidate.getAssignmentsToRefundCandidates());
		final RefundInvoiceCandidate refundInvoiceCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();

		// guards
		assertThat(assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate().getAsBigDecimal()).isEqualByComparingTo(ONE);
		assertThat(refundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED);
		assertThat(refundInvoiceCandidate.getAssignedQuantity().getAsBigDecimal()).isEqualByComparingTo(ONE);

		// invoke the method under test
		final UnassignResult result = invoiceCandidateAssignmentService.unassignCandidate(assignableInvoiceCandidate);

		final List<UnassignedPairOfCandidates> unAssignedPairsOfCandidates = result.getUnassignedPairs();

		assertThat(unAssignedPairsOfCandidates).hasSize(1);
		final UnassignedPairOfCandidates unAssignedPairOfCandidates = unAssignedPairsOfCandidates.get(0);

		final AssignableInvoiceCandidate unassignedAssignableInvoiceCandidate = unAssignedPairOfCandidates.getAssignableInvoiceCandidate();
		assertThat(unassignedAssignableInvoiceCandidate.isAssigned()).isFalse();

		final RefundInvoiceCandidate unAssignedRefundInvoiceCandidate = unAssignedPairOfCandidates.getRefundInvoiceCandidate();

		final InvoiceCandidateId invoiceCandidateId = unAssignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundInvoiceCandidate.getId());
		assertThat(unAssignedRefundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo("98"); // we subtract 20% of 10 from 100; 20% is set in the refund config

		// make
		final I_C_Invoice_Candidate unAssignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(unAssignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo("98");
	}

	@Test
	public void assignCandidate()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		final UnassignedPairOfCandidates unAssignedPairOfCandidates = UnassignedPairOfCandidates.builder()
				.assignableInvoiceCandidate(assignableInvoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();

		// invoke the method under test
		final IPair<AssignableInvoiceCandidate, Quantity> result = //
				invoiceCandidateAssignmentService.assignCandidates(
						unAssignedPairOfCandidates,
						assignableInvoiceCandidate.getQuantity());

		assertThat(result.getRight().isZero()).isTrue(); // everything was assigned

		final RefundInvoiceCandidate assignedRefundInvoiceCandidate = result.getLeft()
				.getAssignmentsToRefundCandidates()
				.get(0)
				.getRefundInvoiceCandidate();

		assertThat(assignedRefundInvoiceCandidate.getId()).isEqualTo(refundInvoiceCandidate.getId());

		final InvoiceCandidateId invoiceCandidateId = assignedRefundInvoiceCandidate.getId();
		assertThat(invoiceCandidateId).isEqualTo(refundInvoiceCandidate.getId());
		assertThat(assignedRefundInvoiceCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED.add(TWO)); // // we add 20% of 10; 20% is set in the refund config

		final I_C_Invoice_Candidate assignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(assignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo(HUNDRED.add(TWO));
	}

	@Test
	public void updateAssignment_not_yet_assigned()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();

		final AssignmentToRefundCandidate assignmentToRefundCandidate = assignedCandidate
				.getAssignmentsToRefundCandidates()
				.get(0);
		assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("2");

		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate()).isNotNull();
		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("102"); // according to the assignable candidate's money and the config's percentage
	}

	@Test
	public void updateAssignment_already_assinged_according_to_parameter()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate //
		refundInvoiceCandidate = singleElement(assignableInvoiceCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		assertThat(result.isUpdateWasDone()).isFalse();
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();

		final RefundInvoiceCandidate //
		resultRefundInvoiceCandidate = singleElement(assignedCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		assertThat(resultRefundInvoiceCandidate).isNotNull();
		assertThat(resultRefundInvoiceCandidate).isEqualTo(refundInvoiceCandidate); // unchanged bc nothing as really altered
	}

	@Test
	public void updateAssignment_already_assingned_in_backend()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate //
		refundInvoiceCandidate = singleElement(assignableInvoiceCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		final AssignableInvoiceCandidate candidateWithRemovedRefundCandidate = assignableInvoiceCandidate.withoutRefundInvoiceCandidates();

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(candidateWithRemovedRefundCandidate);

		assertThat(result.isUpdateWasDone()).isFalse();
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();
		assertThat(assignedCandidate.getAssignmentsToRefundCandidates()).hasSize(1);

		final RefundInvoiceCandidate //
		resultRefundInvoiceCandidate = singleElement(assignedCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		assertThat(resultRefundInvoiceCandidate).isNotNull();
		assertThat(resultRefundInvoiceCandidate.getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(resultRefundInvoiceCandidate.getMoney()).isEqualTo(refundInvoiceCandidate.getMoney()); // unchanged bc nothing has really altered
	}

	@Test
	public void updateAssignment_change_money()
	{
		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		assertThat(assignableCandidate.getAssignmentsToRefundCandidates()).hasSize(1); // guard

		final AssignmentToRefundCandidate assignementToRefundCandidate = singleElement(assignableCandidate.getAssignmentsToRefundCandidates());
		assertThat(assignableCandidate.getMoney().getValue()).isEqualByComparingTo(TEN);// guard
		assertThat(assignementToRefundCandidate.getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo(TWO); // guard

		// guard: we work with 20%, so if the assignableInvoiceCandidate has 10, then 2 is assigned to the refundCandiate
		assertThat(assignementToRefundCandidate.getRefundInvoiceCandidate().getRefundConfig().getPercent().getValueAsBigDecimal()).isEqualByComparingTo(TWENTY);

		// guard: we assume that the refund candidate has already 100 assigned, and btw, we know that 2 of those are "contributed" by 20% of 10 = 2 of our 'assignableCandidate'
		assertThat(assignementToRefundCandidate.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("100");

		final AssignableInvoiceCandidate assignableCandidateWithChange = assignableCandidate
				.toBuilder()
				.money(assignableCandidate.getMoney().multiply(TWO))
				.build();
		assertThat(assignableCandidateWithChange.getMoney().getValue()).isEqualByComparingTo(TWENTY); // guard

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidateWithChange);

		assertThat(result.isUpdateWasDone()).isTrue();
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();

		// basically, expect that instead of 2, assignableCandidate(WithChange) now adds 20% of 20 = 4. so it shall be 102 instead of 100
		assertThat(assignedCandidate.getAssignmentsToRefundCandidates()).hasSize(1);
		final AssignmentToRefundCandidate resultAssignmentToRefundCandidate = singleElement(assignedCandidate.getAssignmentsToRefundCandidates());

		assertThat(resultAssignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(resultAssignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().getValue()).isEqualByComparingTo("4"); // guard

		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate()).isNotNull();
		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(assignementToRefundCandidate.getRefundInvoiceCandidate().getId()); // guard
		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo("102"); // according to the assignable candidate's money and the config's percentage
	}

	/**
	 * existing refund-candidate with assignedQty=13
	 * per-scale-configs with min-qties zero and 15
	 * assignable candidate with qty 3
	 * expected: 1 is assigned to the existing refund-candidate, 2 to a new refund-candidate
	 */
	@Test
	public void assignCandidate_perScaleConfig1()
	{
		final RefundInvoiceCandidate savedRefundCandidate = repareContractAndRefundCandidate(RefundMode.PER_INDIVIDUAL_SCALE);
		assertThat(savedRefundCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED); // guard
		assertThat(savedRefundCandidate.getAssignedQuantity().getAsBigDecimal()).isEqualByComparingTo(THIRTEEN); // guard

		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone(THREE);
		// guards
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1);
		assertThat(assignableCandidate.getMoney().getValue()).isEqualByComparingTo(TEN);
		assertThat(assignableCandidate.getQuantity().getAsBigDecimal()).isEqualByComparingTo(THREE);

		// guard: for quantities less than 15 we work with 10%, so if the assignableInvoiceCandidate has 10, then 1 is assigned to the refundCandiate
		assertThat(savedRefundCandidate.getRefundConfig().getPercent().getValueAsBigDecimal()).isEqualByComparingTo(TEN);

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();

		final CurrencyId currencyId = refundTestTools.getCurrency().getId();
		final I_C_UOM uom = refundTestTools.getUomRecord();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(ONE, uom))
				.quantityOfRefundCandidate(Quantity.of(FOURTEEN, uom))
				// 1/3 = 0.33, so the money to assign to the first refundCandidate is 10% of 10*0.33, i.e. 0.33
				.moneyAssignedToRefundCandidate(Money.of(new BigDecimal("0.33"), currencyId))
				.moneyOfRefundCandidate(Money.of(new BigDecimal("100.33"), currencyId))

				// 2/3 = 0.66, so the money to assign to the first refundCandidate is 20% of 10*0.66, i.e. 1.33
				.quantityAssignedToRefundCandidate(Quantity.of(TWO, uom))
				.quantityOfRefundCandidate(Quantity.of(TWO, uom))
				.moneyAssignedToRefundCandidate(Money.of(new BigDecimal("1.33"), currencyId))
				.moneyOfRefundCandidate(Money.of(new BigDecimal("1.33"), currencyId))
				.build();

		assertMoneyAndQuantityAssignments(result.getAssignableInvoiceCandidate(), expectation);
	}

	/**
	 * No existing refund-candidate
	 * per-scale-configs with quantities zero and 15
	 * assignable candidate with qty 16
	 *
	 * expected: 14 is assigned one new refund-candidate, 2 to a new refund-candidate
	 */
	@Test
	public void assignCandidate_perScaleConfig2()
	{
		prepareContract(RefundMode.PER_INDIVIDUAL_SCALE);

		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone(SIXTEEN);
		// guards
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1);
		assertThat(assignableCandidate.getMoney().getValue()).isEqualByComparingTo(TEN);
		assertThat(assignableCandidate.getQuantity().getAsBigDecimal()).isEqualByComparingTo(SIXTEEN);

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();

		final CurrencyId currencyId = refundTestTools.getCurrency().getId();
		final I_C_UOM uom = refundTestTools.getUomRecord();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(FOURTEEN, uom))
				.quantityOfRefundCandidate(Quantity.of(FOURTEEN, uom))
				// 14/16 = 0.875, so the money to assign to the first refundCandidate is 10% of 10*0,875, i.e. 0.88
				.moneyAssignedToRefundCandidate(Money.of(new BigDecimal("0.88"), currencyId))
				.moneyOfRefundCandidate(Money.of(new BigDecimal("0.88"), currencyId))

				.quantityAssignedToRefundCandidate(Quantity.of(TWO, uom))
				.quantityOfRefundCandidate(Quantity.of(TWO, uom))
				// 2/16 = 0.125, so the money to assign to the first refundCandidate is 20% of 10*0.125, i.e. 0.25
				.moneyAssignedToRefundCandidate(Money.of(new BigDecimal("0.25"), currencyId))
				.moneyOfRefundCandidate(Money.of(new BigDecimal("0.25"), currencyId))
				.build();

		assertMoneyAndQuantityAssignments(result.getAssignableInvoiceCandidate(), expectation);
	}

	/**
	 * per-scale-configs with qties zero and 15.
	 * two existing assignable-candidates; one with 7, one with 10.
	 * two existing refund-candidates; one with assigned 14, one with assigned 3.
	 * the assignable-candidate with 10 is exclusively assigned to the refund-candidates with 15.
	 * the assignable-candidate with 7 is partially assigned to both refund candidates.
	 *
	 * unassign the assignable-candidates with 7.
	 */
	@Test
	public void unassignCandidate_perScaleConfig1()
	{
		final ImmutableMap<BigDecimal, AssignableInvoiceCandidate> preparedAssignableCandidates = commonSetupForUnassignWithPerScaleConfig();

		// invoke the method under test
		final UnassignResult result = invoiceCandidateAssignmentService.unassignCandidate(preparedAssignableCandidates.get(SEVEN));

		assertThat(result.getAssignableCandidate().getAssignmentsToRefundCandidates()).isEmpty();
		assertThat(result.getUnassignedPairs()).hasSize(2);
		assertThat(result.getAdditionalChangedCandidates()).isEmpty();

		final AssignableInvoiceCandidate reloadedAssignableCandidateWithTen = assignableInvoiceCandidateRepository
				.getById(preparedAssignableCandidates.get(TEN).getId())
				.toBuilder()
				.assignmentsToRefundCandidates(
						assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(preparedAssignableCandidates.get(TEN).getId()))
				.build();

		final I_C_UOM uom = refundTestTools.getUomRecord();
		final CurrencyId currentId = refundTestTools.getCurrency().getId();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(TEN, uom)) // the assignable candidate with 10 is still completely assigned
				.quantityOfRefundCandidate(Quantity.of(TEN, uom)) // used to have 14 and 4 of those belonged to the assignable candidate with 7
				.moneyAssignedToRefundCandidate(Money.of(ZERO, currentId))
				.moneyOfRefundCandidate(Money.of(HUNDRED, currentId))
				.build();

		assertMoneyAndQuantityAssignments(reloadedAssignableCandidateWithTen, expectation);
	}

	/**
	 * per-scale-configs with qties zero and 15.
	 * two existing assignable-candidates; one with 7, one with 10.
	 * two existing refund-candidates; one with assigned 14, one with assigned 3.
	 * the assignable-candidate with 10 is exclusively assigned to the refund-candidates with 15.
	 * the assignable-candidate with 7 is partially assigned to both refund candidates.
	 *
	 * unassign the assignable-candidates with 10.
	 *
	 * expected:
	 * the refund-candidate that had 3 now has zero;
	 * the refund-candidate that had 14 now has 14-7=7
	 * the assignable-candidate with 8 is now exclusively assigned to the refund-candidates with 0
	 */
	@Test
	public void unassignCandidate_perScaleConfig2()
	{
		final ImmutableMap<BigDecimal, AssignableInvoiceCandidate> preparedAssignableCandidates = commonSetupForUnassignWithPerScaleConfig();

		// invoke the method under test
		final UnassignResult result = invoiceCandidateAssignmentService.unassignCandidate(preparedAssignableCandidates.get(TEN));

		assertThat(result.getAssignableCandidate().getAssignmentsToRefundCandidates()).isEmpty();
		assertThat(result.getUnassignedPairs()).hasSize(1);

		// the candidate with seven was also affected
		assertThat(result.getAdditionalChangedCandidates()).hasSize(1);
		assertThat(result.getAdditionalChangedCandidates().get(0).getId()).isEqualTo(preparedAssignableCandidates.get(SEVEN).getId());

		final AssignableInvoiceCandidate reloadedAssignableCandidateWithTen = assignableInvoiceCandidateRepository.getById(preparedAssignableCandidates.get(TEN).getId());
		assertThat(reloadedAssignableCandidateWithTen.getAssignmentsToRefundCandidates()).isEmpty();

		final AssignableInvoiceCandidate reloadedAssignableCandidateWithSeven = assignableInvoiceCandidateRepository
				.getById(preparedAssignableCandidates.get(SEVEN).getId())
				.toBuilder()
				.assignmentsToRefundCandidates(
						assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(preparedAssignableCandidates.get(SEVEN).getId()))
				.build();

		final I_C_UOM uom = refundTestTools.getUomRecord();
		final CurrencyId currentId = refundTestTools.getCurrency().getId();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(SEVEN, uom)) // the assignable candidate with 10 is still completely assigned
				.quantityOfRefundCandidate(Quantity.of(SEVEN, uom)) // used to have 14 and 4 of those belonged to the assignable candidate with 7
				.moneyAssignedToRefundCandidate(Money.of(ONE, currentId))
				.moneyOfRefundCandidate(Money.of(HUNDRED.add(ONE), currentId))
				.build();

		assertMoneyAndQuantityAssignments(reloadedAssignableCandidateWithSeven, expectation);
	}

	@Test
	public void assignCandidate_accumulatedConfig1()
	{
		final RefundInvoiceCandidate savedRefundCandidate = repareContractAndRefundCandidate(RefundMode.ALL_MAX_SCALE);
		assertThat(savedRefundCandidate.getMoney().getValue()).isEqualByComparingTo(HUNDRED); // guard
		assertThat(savedRefundCandidate.getAssignedQuantity().getAsBigDecimal()).isEqualByComparingTo(THIRTEEN); // guard

		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone(THREE);
		// guards
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1);
		assertThat(assignableCandidate.getMoney().getValue()).isEqualByComparingTo(TEN);
		assertThat(assignableCandidate.getQuantity().getAsBigDecimal()).isEqualByComparingTo(THREE);
		// guard: for quantities less than 15 we work with 10%, so if the assignableInvoiceCandidate has 10, then 1 is assigned to the refundCandiate
		assertThat(savedRefundCandidate.getRefundConfig().getPercent().getValueAsBigDecimal()).isEqualByComparingTo(TEN);

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();

		final CurrencyId currencyId = refundTestTools.getCurrency().getId();
		final I_C_UOM uom = refundTestTools.getUomRecord();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(THREE, uom))
				.quantityOfRefundCandidate(Quantity.of(SIXTEEN, uom))
				.moneyAssignedToRefundCandidate(Money.of(TWO, currencyId))
				.moneyOfRefundCandidate(Money.of(HUNDRED.add(TWO), currencyId))
				.build();

		assertMoneyAndQuantityAssignments(result.getAssignableInvoiceCandidate(), expectation);
	}

	private ImmutableMap<BigDecimal, AssignableInvoiceCandidate> commonSetupForUnassignWithPerScaleConfig()
	{
		final RefundContract refundContract = prepareContract(RefundMode.PER_INDIVIDUAL_SCALE);

		final AssignableInvoiceCandidate assignableCandidateWithSeven = refundTestTools.createAssignableCandidateStandlone(SEVEN);
		final AssignableInvoiceCandidate assignableCandidateWithTen = refundTestTools.createAssignableCandidateStandlone(TEN);

		prepareRefundCandidate(
				refundContract,
				refundContract.getRefundConfig(ZERO),
				FOURTEEN,
				ImmutableList.of(
						ImmutablePair.of(assignableCandidateWithTen, TEN),
						ImmutablePair.of(assignableCandidateWithSeven, FOUR)));
		prepareRefundCandidate(
				refundContract,
				refundContract.getRefundConfig(FIFTEEN),
				THREE,
				ImmutableList.of(
						ImmutablePair.of(assignableCandidateWithSeven, THREE)));

		final AssignableInvoiceCandidate //
		reloadedAssignableCandidateWithSeven = assignableInvoiceCandidateRepository
				.getById(assignableCandidateWithSeven.getId())
				.toBuilder()
				.assignmentsToRefundCandidates(
						assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidateWithSeven.getId()))
				.build();
		assertThat(reloadedAssignableCandidateWithSeven.getAssignmentsToRefundCandidates()).hasSize(2);

		final AssignableInvoiceCandidate //
		reloadedAssignableCandidateWithTen = assignableInvoiceCandidateRepository
				.getById(assignableCandidateWithTen.getId())
				.toBuilder()
				.assignmentsToRefundCandidates(
						assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidateWithTen.getId()))
				.build();
		assertThat(reloadedAssignableCandidateWithTen.getAssignmentsToRefundCandidates()).hasSize(1);

		final ImmutableMap<BigDecimal, AssignableInvoiceCandidate> //
		preparedAssignableCandidates = ImmutableMap.of(SEVEN, reloadedAssignableCandidateWithSeven, TEN, reloadedAssignableCandidateWithTen);
		return preparedAssignableCandidates;
	}

	private RefundInvoiceCandidate repareContractAndRefundCandidate(@NonNull final RefundMode refundMode)
	{
		final RefundContract refundContract = prepareContract(refundMode);

		return prepareRefundCandidate(
				refundContract,
				refundContract.getRefundConfig(ZERO),
				THIRTEEN,
				null);
	}

	/**
	 * @param individualAssignments optional detailed assignments for the refundCandidate that is created.
	 */
	private RefundInvoiceCandidate prepareRefundCandidate(
			@NonNull final RefundContract refundContract,
			@NonNull final RefundConfig refundConfig,
			@NonNull final BigDecimal assignedQty,
			@Nullable final List<IPair<AssignableInvoiceCandidate, BigDecimal>> individualAssignments)
	{
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate(refundContract)
				.toBuilder()
				.refundConfig(refundConfig)
				.assignedQuantity(Quantity.of(assignedQty, refundTestTools.getUomRecord()))
				.build();
		final RefundInvoiceCandidate savedRefundCandidate = refundInvoiceCandidateRepository.save(refundCandidate);
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1); // guard

		// no point creating this within refundInvoiceCandidateRepository.save because we don't know (and in this particular case don't care for) the respective assignable candidates.
		BigDecimal remainingAssignedQty = assignedQty;
		if (individualAssignments != null)
		{
			for (final IPair<AssignableInvoiceCandidate, BigDecimal> individualAssignment : individualAssignments)
			{
				final AssignableInvoiceCandidate assignableCandidate = individualAssignment.getLeft();

				final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
				assignmentRecord.setC_Flatrate_RefundConfig_ID(refundConfig.getId().getRepoId());
				assignmentRecord.setC_Flatrate_Term_ID(refundContract.getId().getRepoId());
				assignmentRecord.setC_Invoice_Candidate_Term_ID(savedRefundCandidate.getId().getRepoId());
				assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignableCandidate.getId().getRepoId());
				assignmentRecord.setAssignedQuantity(individualAssignment.getRight());
				saveRecord(assignmentRecord);

				remainingAssignedQty = remainingAssignedQty.subtract(individualAssignment.getRight());
			}
		}

		if (remainingAssignedQty.signum() > 0)
		{
			final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
			assignmentRecord.setC_Flatrate_RefundConfig_ID(refundConfig.getId().getRepoId());
			assignmentRecord.setC_Flatrate_Term_ID(refundContract.getId().getRepoId());
			assignmentRecord.setAssignedQuantity(remainingAssignedQty);
			assignmentRecord.setC_Invoice_Candidate_Term_ID(savedRefundCandidate.getId().getRepoId());
			saveRecord(assignmentRecord);
		}

		return savedRefundCandidate;
	}

	private RefundContract prepareContract(@NonNull final RefundMode refundMode)
	{
		final RefundConfigBuilder configBuilder = refundTestTools
				.createAndInitConfigBuilder()
				.refundMode(refundMode);

		final RefundConfig refundConfig1 = configBuilder.minQty(ZERO).percent(Percent.of(TEN)).build();
		final RefundConfig refundConfig2 = configBuilder.minQty(FIFTEEN).percent(Percent.of(TWENTY)).build();

		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).isEmpty(); // guard
		final RefundContract refundContract = RefundContract.builder()
				.startDate(NOW)
				.endDate(NOW.plusDays(5))
				.refundConfig(refundConfig1)
				.refundConfig(refundConfig2)
				.bPartnerId(RefundTestTools.BPARTNER_ID)
				.build();
		final RefundContract savedRefundContract = refundContractRepository.save(refundContract);
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1); // guard
		return savedRefundContract;
	}

	@Value
	@Builder
	private static class AssignmentExpectation
	{
		@Singular("moneyAssignedToRefundCandidate")
		final List<Money> moneysAssignedToRefundCandidate;

		@Singular("moneyOfRefundCandidate")
		final List<Money> moneysOfRefundCandidate;

		@Singular("quantityAssignedToRefundCandidate")
		final List<Quantity> quantitiesAssignedToRefundCandidate;

		@Singular("quantityOfRefundCandidate")
		final List<Quantity> quantitesOfRefundCandidate;
	}

	private void assertMoneyAndQuantityAssignments(
			@NonNull final AssignableInvoiceCandidate result,
			@NonNull final AssignmentExpectation expectation)
	{
		final List<Money> moneysAssignedToRefundCandidate = expectation.getMoneysAssignedToRefundCandidate();
		final List<Money> moneysOfRefundCandidate = expectation.getMoneysOfRefundCandidate();
		final List<Quantity> quantitiesAssignedToRefundCandidate = expectation.getQuantitiesAssignedToRefundCandidate();
		final List<Quantity> quantitiesOfRefundCandidate = expectation.getQuantitesOfRefundCandidate();

		assertThat(moneysOfRefundCandidate).hasSameSizeAs(quantitiesOfRefundCandidate); // guard

		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = result.getAssignmentsToRefundCandidates();
		assertThat(assignmentsToRefundCandidates)
				.as("Given result shall have the expected number of assignments")
				.hasSameSizeAs(moneysOfRefundCandidate);

		for (int i = 0; i < moneysOfRefundCandidate.size(); i++)
		{
			final AssignmentToRefundCandidate assignmentToRefundCandidate = assignmentsToRefundCandidates.get(i);

			assertThat(assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate())
					.as("i=%s - wrong quantityAssigendToRefundCandidate", i)
					.isEqualTo(quantitiesAssignedToRefundCandidate.get(i));
			assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate())
					.as("i=%s - wrong moneyAssignedToRefundCandidate", i)
					.isEqualTo(moneysAssignedToRefundCandidate.get(i));

			final RefundInvoiceCandidate resultRefundCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();
			assertThat(resultRefundCandidate.getAssignedQuantity())
					.as("i=%s - wrong quantityOfRrefundCandidate", i)
					.isEqualTo(quantitiesOfRefundCandidate.get(i));
			assertThat(resultRefundCandidate.getMoney())
					.as("i=%s - wrong moneyOfRefundCandidate", i)
					.isEqualTo(moneysOfRefundCandidate.get(i));

			// make sure the assigned qty was also persisted:
			final RefundInvoiceCandidate reloadedResultRefundcandidate = refundInvoiceCandidateRepository.getById(resultRefundCandidate.getId());
			assertThat(reloadedResultRefundcandidate.getAssignedQuantity())
					.as("i=%s - wrong persisted quantityOfRrefundCandidate", i)
					.isEqualTo(quantitiesOfRefundCandidate.get(i));
			assertThat(reloadedResultRefundcandidate.getMoney())
					.as("i=%s - wrong persisted moneyOfRefundCandidate", i)
					.isEqualTo(moneysOfRefundCandidate.get(i));
		}
	}
}
