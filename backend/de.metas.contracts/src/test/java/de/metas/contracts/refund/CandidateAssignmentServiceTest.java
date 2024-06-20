package de.metas.contracts.refund;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.CandidateAssignmentService.UnassignResult;
import de.metas.contracts.refund.CandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.allqties.refundconfigchange.RefundConfigChangeService;
import de.metas.currency.CurrencyRepository;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.agg.key.impl.ICHeaderAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.document.dimension.InvoiceCandidateDimensionFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.metas.contracts.refund.RefundTestTools.CONTRACT_END_DATE;
import static de.metas.contracts.refund.RefundTestTools.CONTRACT_START_DATE;
import static de.metas.contracts.refund.RefundTestTools.extractSingleConfig;
import static de.metas.util.collections.CollectionUtils.singleElement;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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
public class CandidateAssignmentServiceTest
{
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal THREE = new BigDecimal("3");
	private static final BigDecimal FOUR = new BigDecimal("4");
	private static final BigDecimal SEVEN = new BigDecimal("7");
	private static final BigDecimal THIRTEEN = new BigDecimal("13");
	private static final BigDecimal FOURTEEN = new BigDecimal("14");
	private static final BigDecimal FIFTEEN = new BigDecimal("15");
	private static final BigDecimal SIXTEEN = new BigDecimal("16");
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	private CandidateAssignmentService invoiceCandidateAssignmentService;

	private RefundTestTools refundTestTools;
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private RefundContractRepository refundContractRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);
		aggregationFactory.setDefaultAggregationKeyBuilder(I_C_Invoice_Candidate.class, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header, ICHeaderAggregationKeyBuilder_OLD.instance);

		refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();

		refundContractRepository = refundInvoiceCandidateRepository.getRefundContractRepository();

		final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(
				refundInvoiceCandidateRepository);

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		final AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory = AssignableInvoiceCandidateFactory.newForUnitTesting();
		assignableInvoiceCandidateRepository = new AssignableInvoiceCandidateRepository(assignableInvoiceCandidateFactory);

		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				refundInvoiceCandidateRepository,
				moneyService);

		final RefundConfigChangeService refundConfigChangeService = new RefundConfigChangeService(
				assignmentToRefundCandidateRepository,
				moneyService,
				refundInvoiceCandidateService);

		invoiceCandidateAssignmentService = new CandidateAssignmentService(
				refundContractRepository,
				refundInvoiceCandidateService,
				assignableInvoiceCandidateRepository,
				assignmentToRefundCandidateRepository,
				refundInvoiceCandidateRepository,
				refundConfigChangeService);

		refundTestTools = RefundTestTools.newInstance();


		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new InvoiceCandidateDimensionFactory());

		final DimensionService dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(new DimensionService(dimensionFactories));
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
		assertThat(assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate().toBigDecimal()).isEqualByComparingTo(ONE);
		assertThat(refundInvoiceCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(HUNDRED);
		assertThat(refundInvoiceCandidate.getAssignedQuantity().toBigDecimal()).isEqualByComparingTo(ONE);

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
		assertThat(unAssignedRefundInvoiceCandidate.getMoney().toBigDecimal()).isEqualByComparingTo("98"); // we subtract 20% of 10 from 100; 20% is set in the refund config

		// make
		final I_C_Invoice_Candidate unAssignedRefundInvoiceCandidateRecord = RefundTestTools.retrieveRecord(invoiceCandidateId);
		assertThat(unAssignedRefundInvoiceCandidateRecord.getPriceActual()).isEqualByComparingTo("98");
	}

	@Test
	public void updateAssignment_not_yet_assigned()
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = refundTestTools.createRefundCandidate();
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateStandlone();

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		assertThat(result.isUpdateWasDone()).isTrue(); // the update was not necessary, but we didn't have a stable implementation to check it
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();

		final AssignmentToRefundCandidate assignmentToRefundCandidate = assignedCandidate
				.getAssignmentsToRefundCandidates()
				.get(0);
		assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().toBigDecimal()).isEqualByComparingTo("2");

		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate()).isNotNull();
		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(assignmentToRefundCandidate.getRefundInvoiceCandidate().getMoney().toBigDecimal()).isEqualByComparingTo("102"); // according to the assignable candidate's money and the config's percentage
	}

	/**
	 * Verifies that the {@code updateAssignment} checks the actual status in the DB and reassigns if there already was one.
	 */
	@Test
	public void updateAssignment_already_assinged_according_to_parameter()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate //
		refundInvoiceCandidate = singleElement(assignableInvoiceCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableInvoiceCandidate);

		assertThat(result.isUpdateWasDone()).isTrue(); // the update was not necessary, but we didn't have a stable implementation to check it
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();

		final RefundInvoiceCandidate //
		resultRefundInvoiceCandidate = singleElement(assignedCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		assertThat(resultRefundInvoiceCandidate).isNotNull();
		assertThat(resultRefundInvoiceCandidate).isEqualTo(refundInvoiceCandidate); // unchanged bc nothing as really altered
	}

	/**
	 * Verifies that the {@code updateAssignment} checks the actual status in the DB and reassigns if there already was one.
	 */
	@Test
	public void updateAssignment_already_assingned_in_backend()
	{
		final AssignableInvoiceCandidate assignableInvoiceCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		final RefundInvoiceCandidate //
		refundInvoiceCandidate = singleElement(assignableInvoiceCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		// prepare a parameter that indicates no assignment exists, but in the DB/backend there is one
		final AssignableInvoiceCandidate candidateWithRemovedRefundCandidate = assignableInvoiceCandidate.withoutRefundInvoiceCandidates();

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(candidateWithRemovedRefundCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();
		assertThat(assignedCandidate.getAssignmentsToRefundCandidates()).hasSize(1);

		final RefundInvoiceCandidate //
		resultRefundInvoiceCandidate = singleElement(assignedCandidate.getAssignmentsToRefundCandidates()).getRefundInvoiceCandidate();

		assertThat(resultRefundInvoiceCandidate).isNotNull();
		assertThat(resultRefundInvoiceCandidate.getId()).isEqualTo(refundInvoiceCandidate.getId()); // guard
		assertThat(resultRefundInvoiceCandidate.getMoney()).isEqualTo(refundInvoiceCandidate.getMoney()); // unchanged bc nothing has really altered
	}

	/**
	 * existing refund-candidate and assigned candidate.
	 * the assigned candidate has a money amount of 10 and a refund config with 20%, so money=2 is assigned to the refund candidate.
	 *
	 * change the assigned candidate's record's money amount from 10 to 20,
	 * then invoke the method under test
	 *
	 * expected: now, money=4 is assigned to the refund candidate.
	 */
	@Test
	public void updateAssignment_change_money()
	{
		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateWithAssignment();
		assertThat(assignableCandidate.getAssignmentsToRefundCandidates()).hasSize(1); // guard

		final AssignmentToRefundCandidate assignementToRefundCandidate = singleElement(assignableCandidate.getAssignmentsToRefundCandidates());
		assertThat(assignableCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(TEN);// guard
		assertThat(assignementToRefundCandidate.getMoneyAssignedToRefundCandidate().toBigDecimal()).isEqualByComparingTo(TWO); // guard

		// guard: we work with 20%, so if the assignableInvoiceCandidate has 10, then 2 is assigned to the refundCandiate
		assertThat(extractSingleConfig(assignementToRefundCandidate.getRefundInvoiceCandidate()).getPercent().toBigDecimal()).isEqualByComparingTo(TWENTY);

		// guard: we assume that the refund candidate has already 100 assigned, and btw, we know that 2 of those are "contributed" by 20% of 10 = 2 of our 'assignableCandidate'
		assertThat(assignementToRefundCandidate.getRefundInvoiceCandidate().getMoney().toBigDecimal()).isEqualByComparingTo("100");

		final I_C_Invoice_Candidate assignableCandidateRecord = load(assignableCandidate.getId(), I_C_Invoice_Candidate.class);
		assignableCandidateRecord.setNetAmtInvoiced(TWENTY);
		assignableCandidateRecord.setNetAmtToInvoice(ZERO);
		saveRecord(assignableCandidateRecord);

		final AssignableInvoiceCandidate assignableCandidateWithChange = assignableInvoiceCandidateRepository.ofRecord(assignableCandidateRecord);
		assertThat(assignableCandidateWithChange.getMoney().toBigDecimal()).isEqualByComparingTo(TWENTY); // guard

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidateWithChange);

		assertThat(result.isUpdateWasDone()).isTrue();
		final AssignableInvoiceCandidate assignedCandidate = result.getAssignableInvoiceCandidate();

		// basically, expect that instead of 2, assignableCandidate(WithChange) now adds 20% of 20 = 4. so it shall be 102 instead of 100
		assertThat(assignedCandidate.getAssignmentsToRefundCandidates()).hasSize(1);
		final AssignmentToRefundCandidate resultAssignmentToRefundCandidate = singleElement(assignedCandidate.getAssignmentsToRefundCandidates());

		assertThat(resultAssignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()).isNotNull();
		assertThat(resultAssignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().toBigDecimal()).isEqualByComparingTo(FOUR);

		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate()).isNotNull();
		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate().getId()).isEqualTo(assignementToRefundCandidate.getRefundInvoiceCandidate().getId());
		assertThat(resultAssignmentToRefundCandidate.getRefundInvoiceCandidate().getMoney().toBigDecimal()).isEqualByComparingTo("102"); // according to the assignable candidate's money and the config's percentage
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
		final RefundInvoiceCandidate savedRefundCandidate = repareContractAndRefundCandidate(RefundMode.APPLY_TO_EXCEEDING_QTY);
		assertThat(savedRefundCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(ONE); // guard
		assertThat(savedRefundCandidate.getAssignedQuantity().toBigDecimal()).isEqualByComparingTo(THIRTEEN); // guard

		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone(THREE);
		// guards
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1);
		assertThat(assignableCandidate.getId()).isNotNull();
		assertThat(assignableCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(assignableCandidate.getQuantity().toBigDecimal()).isEqualByComparingTo(THREE);

		// guard: for quantities less than 15 we work with 10%, so if the assignableInvoiceCandidate has 10, then 1 is assigned to the refundCandiate
		assertThat(extractSingleConfig(savedRefundCandidate).getPercent().toBigDecimal()).isEqualByComparingTo(TEN);

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();

		final CurrencyId currencyId = refundTestTools.getCurrencyId();
		final I_C_UOM uom = refundTestTools.getUomRecord();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(ONE, uom))
				.quantityOfRefundCandidate(Quantity.of(FOURTEEN, uom))
				// 1/3 = 0.33, so the money to assign to the first refundCandidate is 10% of 10*0.33, i.e. 0.33
				.moneyAssignedToRefundCandidate(Money.of("0.33", currencyId))
				.moneyOfRefundCandidate(Money.of("1.33", currencyId))

				// 2/3 = 0.66, so the money to assign to the first refundCandidate is 20% of 10*0.66, i.e. 1.33
				.quantityAssignedToRefundCandidate(Quantity.of(TWO, uom))
				.quantityOfRefundCandidate(Quantity.of(TWO, uom))
				.moneyAssignedToRefundCandidate(Money.of("1.33", currencyId))
				.moneyOfRefundCandidate(Money.of("1.33", currencyId))
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
		prepareContract(RefundMode.APPLY_TO_EXCEEDING_QTY);

		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone(SIXTEEN);
		// guards
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1);
		assertThat(assignableCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(assignableCandidate.getQuantity().toBigDecimal()).isEqualByComparingTo(SIXTEEN);

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();

		final CurrencyId currencyId = refundTestTools.getCurrencyId();
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
	 *
	 * expected: the remaining assigned quantity of refund-candidates is 10
	 *
	 * Hint: if this one fails, first check if the tests for {@link CandidateAssignmentService#updateAssignment(AssignableInvoiceCandidate)} work.
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
				.getById(preparedAssignableCandidates.get(TEN).getId());

		final I_C_UOM uom = refundTestTools.getUomRecord();
		final CurrencyId currentId = refundTestTools.getCurrencyId();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(TEN, uom)) // the assignable candidate with 10 is still completely assigned
				.quantityOfRefundCandidate(Quantity.of(TEN, uom)) // used to have 14 and 4 of those belonged to the assignable candidate with 7
				.moneyAssignedToRefundCandidate(Money.of(ONE, currentId))
				.moneyOfRefundCandidate(Money.of(ONE, currentId))
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
	 *
	 * Hint: if this one fails, first check if the tests for {@link CandidateAssignmentService#updateAssignment(AssignableInvoiceCandidate)} work.
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
				.getById(preparedAssignableCandidates.get(SEVEN).getId());

		final I_C_UOM uom = refundTestTools.getUomRecord();
		final CurrencyId currentId = refundTestTools.getCurrencyId();

		final AssignmentExpectation expectation = AssignmentExpectation
				.builder()
				.quantityAssignedToRefundCandidate(Quantity.of(SEVEN, uom)) // the assignable candidate with 10 is still completely assigned
				.quantityOfRefundCandidate(Quantity.of(SEVEN, uom)) // used to have 14 and 4 of those belonged to the assignable candidate with 7
				.moneyAssignedToRefundCandidate(Money.of(ONE, currentId))
				.moneyOfRefundCandidate(Money.of(ONE, currentId))
				.build();

		assertMoneyAndQuantityAssignments(reloadedAssignableCandidateWithSeven, expectation);
	}

	/**
	 * existing refund-candidate with qty = 13
	 * all-max-scale-configs with quantities zero and 15
	 * assignable candidate with qty = 3 and money = 10.
	 *
	 * expected:
	 *
	 * <li>the assignable candidate's 3 are assigned to the existing refund-candidate; 1 is assigned via the config with MinQty=0, and 2 is assigned via the config with MinQty=15
	 * <li>There are 2 assignments, one with assigned-qty = 14, one with 2
	 * <li>Overall, the refund-candidate has an assigned quantity of 16, so the 2nd refund config with 20% is used.<br>
	 */
	@Test
	public void assignCandidate_accumulatedConfig_percent1()
	{
		final CurrencyId currencyId = refundTestTools.getCurrencyId();
		final I_C_UOM uom = refundTestTools.getUomRecord();

		final AssignableInvoiceCandidate preAssignedAssignableCandidate = refundTestTools.createAssignableCandidateStandlone(THIRTEEN);
		assertThat(preAssignedAssignableCandidate.getMoney()).isEqualTo(Money.of(TEN, currencyId));
		assertThat(preAssignedAssignableCandidate.getQuantity()).isEqualTo(Quantity.of(THIRTEEN, uom));
		assertThat(POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class)).isEmpty();

		final RefundInvoiceCandidate savedRefundCandidate = repareContractAndRefundCandidate(
				RefundMode.APPLY_TO_ALL_QTIES,
				ImmutableList.of(new IndividualTestAssignment(preAssignedAssignableCandidate, THIRTEEN, ONE)));
		assertThat(POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class)).hasSize(1);

		final List<RefundConfig> refundConfigs = savedRefundCandidate.getRefundContract().getRefundConfigs();
		assertThat(refundConfigs).hasSize(2); // guard
		final RefundConfig configWithMinQty0 = refundConfigs.get(1);
		assertThat(configWithMinQty0.getMinQty()).isEqualByComparingTo(ZERO);
		assertThat(configWithMinQty0.getPercent()).isEqualTo(Percent.of(TEN));

		final RefundConfig configWithMinQty15 = refundConfigs.get(0);
		assertThat(configWithMinQty15.getMinQty()).isEqualByComparingTo(FIFTEEN);
		assertThat(configWithMinQty15.getPercent()).isEqualTo(Percent.of(TWENTY));

		assertThat(savedRefundCandidate.getMoney()).isEqualTo(Money.of(ONE, currencyId));
		assertThat(savedRefundCandidate.getAssignedQuantity()).isEqualTo(Quantity.of(THIRTEEN, uom));

		final AssignableInvoiceCandidate assignableCandidate = refundTestTools.createAssignableCandidateStandlone(THREE);
		assertThat(assignableCandidate.getMoney().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(assignableCandidate.getQuantity().toBigDecimal()).isEqualByComparingTo(THREE);

		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1);
		assertThat(POJOLookupMap.get().getRecords(I_C_Invoice_Candidate_Assignment.class)).hasSize(1);

		// invoke the method under test
		final UpdateAssignmentResult result = invoiceCandidateAssignmentService.updateAssignment(assignableCandidate);

		assertThat(result.isUpdateWasDone()).isTrue();

		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = result.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();

		final ImmutableList<RefundInvoiceCandidate> distinctRefundCandidates = CollectionUtils.extractDistinctElements(assignmentsToRefundCandidates, AssignmentToRefundCandidate::getRefundInvoiceCandidate);
		assertThat(distinctRefundCandidates).hasSize(1);
		assertThat(distinctRefundCandidates.get(0).getAssignedQuantity()).isEqualTo(Quantity.of(SIXTEEN, uom)); // 13 plus 3
		assertThat(distinctRefundCandidates.get(0).getMoney()).isEqualTo(Money.of(FOUR, currencyId)); // 20% of (10+10)

		assertThat(assignmentsToRefundCandidates).hasSize(2);
		assertThat(assignmentsToRefundCandidates)
				.filteredOn(a -> a.getRefundConfigId().equals(configWithMinQty0.getId()))
				.hasSize(1) //
				.allSatisfy(a -> {
					assertThat(a.getQuantityAssigendToRefundCandidate()).isEqualTo(Quantity.of(THREE, uom));
					assertThat(a.getMoneyAssignedToRefundCandidate()).isEqualTo(Money.of(ONE, currencyId));   // 10% of 10
					assertThat(a.isUseAssignedQtyInSum()).isTrue();
				});

		assertThat(assignmentsToRefundCandidates)
				.filteredOn(a -> a.getRefundConfigId().equals(configWithMinQty15.getId()))
				.hasSize(1) //
				.allSatisfy(a -> {
					assertThat(a.getQuantityAssigendToRefundCandidate()).isEqualTo(Quantity.of(THREE, uom));
					assertThat(a.getMoneyAssignedToRefundCandidate()).isEqualTo(Money.of(ONE, currencyId));   // 10% 10
					assertThat(a.isUseAssignedQtyInSum()).isFalse();
				});
	}

	private ImmutableMap<BigDecimal, AssignableInvoiceCandidate> commonSetupForUnassignWithPerScaleConfig()
	{
		final RefundContract refundContract = prepareContract(RefundMode.APPLY_TO_EXCEEDING_QTY);

		final AssignableInvoiceCandidate assignableCandidateWithSeven = refundTestTools.createAssignableCandidateStandlone(SEVEN);
		final AssignableInvoiceCandidate assignableCandidateWithTen = refundTestTools.createAssignableCandidateStandlone(TEN);

		final RefundInvoiceCandidate refundCandidate0 = prepareRefundCandidate(
				refundContract,
				refundContract.getRefundConfig(ZERO),
				FOURTEEN, // assignedQty
				ImmutableList.of( // individualAssignments
						new IndividualTestAssignment(assignableCandidateWithTen, TEN, ONE),
						new IndividualTestAssignment(assignableCandidateWithSeven, FOUR, ONE)));

		final RefundInvoiceCandidate refundCandidate15 = prepareRefundCandidate(
				refundContract,
				refundContract.getRefundConfig(FIFTEEN),
				THREE, // assignedQty
				ImmutableList.of( // individualAssignments
						new IndividualTestAssignment(assignableCandidateWithSeven, THREE, ONE)));
		final CurrencyId currencyId = refundTestTools.getCurrencyId();
		final I_C_UOM uom = refundTestTools.getUomRecord();

		//
		// reload the two assignable candidate with their assigned refund candidates
		final AssignableInvoiceCandidate //
		reloadedAssignableCandidateWithSeven = assignableInvoiceCandidateRepository
				.getById(assignableCandidateWithSeven.getId());
		final AssignableInvoiceCandidate //
		reloadedAssignableCandidateWithTen = assignableInvoiceCandidateRepository
				.getById(assignableCandidateWithTen.getId());

		//
		// guards for reloadedAssignableCandidateWithSeven
		assertThat(reloadedAssignableCandidateWithSeven.getAssignmentsToRefundCandidates()).hasSize(2);
		assertThat(reloadedAssignableCandidateWithSeven.getAssignmentsToRefundCandidates())
				.filteredOn(a -> a.getRefundInvoiceCandidate().getId().equals(refundCandidate0.getId()))
				.hasSize(1)
				.allSatisfy(r -> {
					assertThat(r.getRefundInvoiceCandidate().getMoney()).isEqualTo(Money.of(TWO, currencyId));
					assertThat(r.getRefundInvoiceCandidate().getAssignedQuantity()).isEqualTo(Quantity.of(FOURTEEN, uom));
				});
		assertThat(reloadedAssignableCandidateWithSeven.getAssignmentsToRefundCandidates())
				.filteredOn(a -> a.getRefundInvoiceCandidate().getId().equals(refundCandidate15.getId()))
				.hasSize(1)
				.allSatisfy(r -> {
					assertThat(r.getRefundInvoiceCandidate().getMoney()).isEqualTo(Money.of(ONE, currencyId));
					assertThat(r.getRefundInvoiceCandidate().getAssignedQuantity()).isEqualTo(Quantity.of(THREE, uom));
				});

		//
		// guards for reloadedAssignableCandidateWithTen
		assertThat(reloadedAssignableCandidateWithTen.getAssignmentsToRefundCandidates()).hasSize(1);
		assertThat(reloadedAssignableCandidateWithTen.getAssignmentsToRefundCandidates())
				.filteredOn(a -> a.getRefundInvoiceCandidate().getId().equals(refundCandidate0.getId()))
				.hasSize(1)
				.allSatisfy(r -> {
					assertThat(r.getRefundInvoiceCandidate().getMoney()).isEqualTo(Money.of(TWO, currencyId));
					assertThat(r.getRefundInvoiceCandidate().getAssignedQuantity()).isEqualTo(Quantity.of(FOURTEEN, uom));
				});
		assertThat(reloadedAssignableCandidateWithTen.getAssignmentsToRefundCandidates())
				.filteredOn(a -> a.getRefundInvoiceCandidate().getId().equals(refundCandidate15.getId()))
				.isEmpty();

		//
		// finally return the result
		return ImmutableMap.of(
				SEVEN, reloadedAssignableCandidateWithSeven,
				TEN, reloadedAssignableCandidateWithTen);
	}

	private RefundInvoiceCandidate repareContractAndRefundCandidate(@NonNull final RefundMode refundMode)
	{
		return repareContractAndRefundCandidate(refundMode, null);
	}

	private RefundInvoiceCandidate repareContractAndRefundCandidate(
			@NonNull final RefundMode refundMode,
			@Nullable final List<IndividualTestAssignment> individualAssignments)
	{
		final RefundContract refundContract = prepareContract(refundMode);

		return prepareRefundCandidate(
				refundContract,
				refundContract.getRefundConfig(ZERO),
				THIRTEEN,
				individualAssignments);
	}

	/**
	 * @param individualAssignments optional detailed assignments for the refundCandidate that is created.
	 */
	private RefundInvoiceCandidate prepareRefundCandidate(
			@NonNull final RefundContract refundContract,
			@NonNull final RefundConfig refundConfig,
			@NonNull final BigDecimal assignedQty,
			@Nullable final List<IndividualTestAssignment> individualAssignments)
	{
		final CurrencyId currencyId = refundTestTools.getCurrencyId();
		final RefundInvoiceCandidate refundCandidate = refundTestTools.createRefundCandidate(refundContract)
				.toBuilder()
				.money(Money.of(ONE, currencyId))
				.assignedQuantity(Quantity.of(assignedQty, refundTestTools.getUomRecord()))
				.build();

		RefundInvoiceCandidate savedRefundCandidate = refundInvoiceCandidateRepository.save(refundCandidate);
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(1); // guard

		// no point creating this within refundInvoiceCandidateRepository.save because we don't know (and in this particular case don't care for) the respective assignable candidates.
		BigDecimal remainingAssignedQty = assignedQty;
		BigDecimal assignedMoneySum = ZERO;
		if (individualAssignments != null)
		{
			for (final IndividualTestAssignment individualAssignment : individualAssignments)
			{
				final AssignableInvoiceCandidate assignableCandidate = individualAssignment.getAssignableCandidate();

				final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
				assignmentRecord.setC_Flatrate_RefundConfig_ID(refundConfig.getId().getRepoId());
				assignmentRecord.setC_Flatrate_Term_ID(refundContract.getId().getRepoId());
				assignmentRecord.setC_Invoice_Candidate_Term_ID(savedRefundCandidate.getId().getRepoId());
				assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignableCandidate.getId().getRepoId());
				assignmentRecord.setAssignedQuantity(individualAssignment.getAssignedQuantity());
				assignmentRecord.setAssignedMoneyAmount(individualAssignment.getAssignedMoney());
				assignmentRecord.setBaseMoneyAmount(assignableCandidate.getMoney().toBigDecimal());
				assignmentRecord.setIsAssignedQuantityIncludedInSum(refundConfig.isIncludeAssignmentsWithThisConfigInSum());
				saveRecord(assignmentRecord);

				remainingAssignedQty = remainingAssignedQty.subtract(individualAssignment.getAssignedQuantity());
				assignedMoneySum = assignedMoneySum.add(individualAssignment.getAssignedMoney());
			}

			savedRefundCandidate = savedRefundCandidate.toBuilder().money(Money.of(assignedMoneySum, currencyId)).build();
			savedRefundCandidate = refundInvoiceCandidateRepository.save(savedRefundCandidate);
		}

		if (remainingAssignedQty.signum() > 0)
		{
			final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);
			assignmentRecord.setC_Flatrate_RefundConfig_ID(refundConfig.getId().getRepoId());
			assignmentRecord.setC_Flatrate_Term_ID(refundContract.getId().getRepoId());
			assignmentRecord.setAssignedQuantity(remainingAssignedQty);
			assignmentRecord.setC_Invoice_Candidate_Term_ID(savedRefundCandidate.getId().getRepoId());
			assignmentRecord.setC_Invoice_Candidate_Assigned_ID(30); // we need "some" ID because it may not be null in the respective AssignmentToRefundCandidate instance
			assignmentRecord.setIsAssignedQuantityIncludedInSum(refundConfig.isIncludeAssignmentsWithThisConfigInSum());
			saveRecord(assignmentRecord);
		}

		return savedRefundCandidate;
	}

	@Value
	private class IndividualTestAssignment
	{
		AssignableInvoiceCandidate assignableCandidate;

		BigDecimal assignedQuantity;

		BigDecimal assignedMoney;
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
				.startDate(CONTRACT_START_DATE)
				.endDate(CONTRACT_END_DATE)
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
		/** money assigned to the refund candidate via a particular assignment */
		@Singular("moneyAssignedToRefundCandidate")
		final List<Money> moneysAssignedToRefundCandidate;

		/** overall money of the refund candidate */
		@Singular("moneyOfRefundCandidate")
		final List<Money> moneysOfRefundCandidate;

		/** quantity assigned to the refund candidate via a particular assignment */
		@Singular("quantityAssignedToRefundCandidate")
		final List<Quantity> quantitiesAssignedToRefundCandidate;

		/** *overall* quantity assigned to the refund candidate */
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
					.as("i=%s - wrong quantityOfRefundCandidate", i)
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
