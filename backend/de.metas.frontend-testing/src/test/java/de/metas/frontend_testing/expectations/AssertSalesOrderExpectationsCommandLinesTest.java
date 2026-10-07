package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.expectations.request.JsonOrderLineExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.calibration.CalibrationRuleId;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * The {@code Backend.expect({salesOrders: {<id>: {lines: [...]}}})} check: the order lines are matched by product and
 * carry the expected quantity and calibration data (factor, rule, uncalibrated quantity).
 */
class AssertSalesOrderExpectationsCommandLinesTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(100);
	private static final ProductId PRODUCT_1 = ProductId.ofRepoId(201);
	private static final ProductId PRODUCT_2 = ProductId.ofRepoId(202);
	private static final CalibrationRuleId RULE_1 = CalibrationRuleId.ofRepoId(301);
	private static final CalibrationRuleId RULE_2 = CalibrationRuleId.ofRepoId(302);

	private MasterdataContext context;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
		context.putIdentifier(Identifier.ofString("P1"), PRODUCT_1);
		context.putIdentifier(Identifier.ofString("P2"), PRODUCT_2);
		context.putIdentifier(Identifier.ofString("R1"), RULE_1);
		context.putIdentifier(Identifier.ofString("R2"), RULE_2);
	}

	private static I_C_OrderLine line(
			final ProductId productId,
			final String qtyEntered,
			@Nullable final String factor,
			@Nullable final CalibrationRuleId ruleId,
			@Nullable final String uncalibrated)
	{
		final I_C_OrderLine line = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(ORDER_ID.getRepoId());
		line.setM_Product_ID(productId.getRepoId());
		line.setQtyEntered(new BigDecimal(qtyEntered));
		line.setGroupCompensationCalibrationFactor(factor == null ? null : new BigDecimal(factor));
		line.setC_CompensationGroup_CalibrationRule_ID(ruleId == null ? 0 : ruleId.getRepoId());
		line.setGroupCompensationQtyEnteredUncalibrated(uncalibrated == null ? null : new BigDecimal(uncalibrated));
		InterfaceWrapperHelper.saveRecord(line);
		return line;
	}

	private static JsonOrderLineExpectation.JsonOrderLineExpectationBuilder expect(final String product)
	{
		return JsonOrderLineExpectation.builder().product(Identifier.ofString(product));
	}

	private void assertLines(final List<I_C_OrderLine> actual, final JsonOrderLineExpectation... expected)
	{
		AssertSalesOrderExpectationsCommand.assertOrderLines(actual, ORDER_ID, ImmutableList.copyOf(expected), context);
	}

	@Test
	void aCalibratedLine_matchesOnAllFields_regardlessOfScale()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15.0", "1.50", RULE_1, "10"));

		assertThatCode(() -> assertLines(actual, expect("P1")
				.qtyEntered(new BigDecimal("15"))
				.calibrationFactor(new BigDecimal("1.5"))
				.calibrationRule(Identifier.ofString("R1"))
				.qtyEnteredUncalibrated(new BigDecimal("10.00"))
				.build()))
				.doesNotThrowAnyException();
	}

	@Test
	void linesAreMatchedByProduct_notByPosition_andUnexpectedLinesAreIgnored()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(
				line(PRODUCT_1, "1", null, null, null),
				line(PRODUCT_2, "7", null, null, null));

		assertThatCode(() -> assertLines(actual, expect("P2").qtyEntered(new BigDecimal("7")).build()))
				.doesNotThrowAnyException();
	}

	@Test
	void nullExpectedFieldsAreNotAsserted()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", "1.5", RULE_1, "10"));

		assertThatCode(() -> assertLines(actual, expect("P1").build())).doesNotThrowAnyException();
	}

	@Test
	void anotherQty_fails()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").qtyEntered(new BigDecimal("16")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("QtyEntered");
	}

	@Test
	void anotherFactor_fails()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", "1.5", RULE_1, "10"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrationFactor(new BigDecimal("2")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GroupCompensationCalibrationFactor");
	}

	@Test
	void aMissingFactorOnTheLine_failsAnExpectedFactor()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrationFactor(BigDecimal.ONE).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GroupCompensationCalibrationFactor");
	}

	@Test
	void anotherRule_fails()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", "1.5", RULE_1, "10"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrationRule(Identifier.ofString("R2")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_ID");
	}

	@Test
	void anotherUncalibratedQty_fails()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", "1.5", RULE_1, "10"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").qtyEnteredUncalibrated(new BigDecimal("11")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GroupCompensationQtyEnteredUncalibrated");
	}

	@Test
	void noLineOfTheProduct_fails()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P2").build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("number of order lines of product");
	}

	@Test
	void twoLinesOfTheProduct_fails_becauseTheMatchIsAmbiguous()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(
				line(PRODUCT_1, "1", null, null, null),
				line(PRODUCT_1, "2", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").qtyEntered(BigDecimal.ONE).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("number of order lines of product");
	}

	@Test
	void notCalibrated_matchesALineWithoutFactorRuleAndUncalibratedQty()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", null, null, null));

		assertThatCode(() -> assertLines(actual, expect("P1").qtyEntered(new BigDecimal("200")).calibrated(false).build()))
				.doesNotThrowAnyException();
	}

	@Test
	void notCalibrated_failsOnALineWithFactor1AndNoRule()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", "1", null, "200"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrated(false).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GroupCompensationCalibrationFactor")
				.hasMessageContaining("GroupCompensationQtyEnteredUncalibrated");
	}

	@Test
	void notCalibrated_failsOnALineCarryingOnlyARule()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", null, RULE_1, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrated(false).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_ID");
	}

	@Test
	void calibrated_matchesALineWithFactorAndUncalibratedQty_withoutRule()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", "1", null, "200"));

		assertThatCode(() -> assertLines(actual, expect("P1").calibrated(true).build()))
				.doesNotThrowAnyException();
	}

	@Test
	void calibrated_failsOnAnUncalibratedLine()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrated(true).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GroupCompensationCalibrationFactor")
				.hasMessageContaining("GroupCompensationQtyEnteredUncalibrated");
	}

	@Test
	void notCalibrated_togetherWithAnExpectedCalibrationValue_isRejected()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrated(false).calibrationFactor(BigDecimal.ONE).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("calibrated=false cannot be combined");
	}

	@Test
	void notCalibrated_togetherWithHasCalibrationRuleTrue_isRejected()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrated(false).hasCalibrationRule(true).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("calibrated=false cannot be combined with hasCalibrationRule=true");
	}

	@Test
	void noRuleApplied_matchesACalibratedLineWithFactor1AndNoRule()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", "1", null, "200"));

		assertThatCode(() -> assertLines(actual, expect("P1")
				.qtyEntered(new BigDecimal("200"))
				.calibrationFactor(BigDecimal.ONE)
				.qtyEnteredUncalibrated(new BigDecimal("200"))
				.hasCalibrationRule(false)
				.build()))
				.doesNotThrowAnyException();
	}

	@Test
	void noRuleApplied_failsOnALineCarryingARule()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "100", "0.5", RULE_1, "200"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").hasCalibrationRule(false).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_ID");
	}

	@Test
	void ruleApplied_failsOnALineWithoutRule()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", "1", null, "200"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").hasCalibrationRule(true).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_ID");
	}

	@Test
	void noRuleApplied_togetherWithAnExpectedRule_isRejected()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "200", "1", null, "200"));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").hasCalibrationRule(false).calibrationRule(Identifier.ofString("R1")).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("hasCalibrationRule=false cannot be combined");
	}

	@Test
	void anExpectedZeroFactor_failsOnALineWithoutFactor()
	{
		final List<I_C_OrderLine> actual = ImmutableList.of(line(PRODUCT_1, "15", null, null, null));

		assertThatThrownBy(() -> assertLines(actual, expect("P1").calibrationFactor(BigDecimal.ZERO).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GroupCompensationCalibrationFactor");
	}
}
