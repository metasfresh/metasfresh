package de.metas.material.planning.pporder;

import de.metas.business.BusinessTestHelper;
import de.metas.product.IssuingToleranceSpec;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderBOMLineQuantitiesTest
{
	private I_C_UOM uomEach;
	private I_C_UOM uomKg;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();
		uomKg = BusinessTestHelper.createUomKg();
	}

	@Test
	void convertQuantities()
	{
		final OrderBOMLineQuantities qtys = OrderBOMLineQuantities.ofQtyRequired(Quantity.of("123.123", uomEach));
		final OrderBOMLineQuantities qtys2 = qtys.convertQuantities(qty -> Quantity.of(qty.toBigDecimal().multiply(BigDecimal.TEN), uomKg));
		Assertions.assertThat(qtys2.getQtyRequired()).isEqualTo(Quantity.of("1231.23", uomKg));
	}

	/**
	 * Tests for {@link IssuingToleranceSpec#addTo(Quantity)} and {@link IssuingToleranceSpec#subtractFrom(Quantity)}
	 * with very small quantities where the qty scale exceeds the UOM's StdPrecision.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/28242">me03#28242</a>
	 */
	@Nested
	class IssuingToleranceSpec_SmallQuantities
	{
		@Test
		void addTo_smallQty_percentageTolerance()
		{
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofPercent(Percent.of(1));
			assertThat(toleranceSpec.addTo(Quantity.of("0.00384", uomKg)).toBigDecimal())
					.isEqualByComparingTo("0.00388"); // 0.00384 * 1.01 = 0.0038784
		}

		@Test
		void subtractFrom_smallQty_percentageTolerance()
		{
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofPercent(Percent.of(1));
			assertThat(toleranceSpec.subtractFrom(Quantity.of("0.00384", uomKg)).toBigDecimal())
					.isEqualByComparingTo("0.00380"); // 0.00384 * 0.99 = 0.0038016
		}
	}

	/**
	 * Tests for {@link OrderBOMLineQuantities#assertQtyToIssueToleranceIsRespected()}
	 * with very small quantities. Indirectly tests {@link IssuingToleranceSpec} via
	 * the tolerance assertion that calls {@link IssuingToleranceSpec#addTo(Quantity)}
	 * and {@link IssuingToleranceSpec#subtractFrom(Quantity)}.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/28242">me03#28242</a>
	 */
	@Nested
	class assertQtyToIssueToleranceIsRespected_SmallQuantities
	{
		@Test
		void issuingExactQty_withinPercentageTolerance()
		{
			final OrderBOMLineQuantities qtys = buildWithSmallQtyAndTolerance(
					/* qtyRequired */ "0.00384",
					/* qtyIssued */ "0.00384",
					/* tolerancePerc */ 1);

			assertThatCode(qtys::assertQtyToIssueToleranceIsRespected)
					.doesNotThrowAnyException();
		}

		@Test
		void issuingTooMuch_exceedsTolerance()
		{
			final OrderBOMLineQuantities qtys = buildWithSmallQtyAndTolerance(
					/* qtyRequired */ "0.00384",
					/* qtyIssued */ "0.00500", // ~30% over, well outside 1% tolerance
					/* tolerancePerc */ 1);

			assertThatThrownBy(qtys::assertQtyToIssueToleranceIsRespected)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("CannotIssueMoreThan");
		}

		private OrderBOMLineQuantities buildWithSmallQtyAndTolerance(
				final String qtyRequiredStr,
				final String qtyIssuedStr,
				final int tolerancePerc)
		{
			return OrderBOMLineQuantities.builder()
					.qtyRequired(Quantity.of(qtyRequiredStr, uomKg))
					.qtyRequiredBeforeClose(Quantity.of(qtyRequiredStr, uomKg))
					.qtyIssuedOrReceived(Quantity.of(qtyIssuedStr, uomKg))
					.qtyIssuedOrReceivedActual(Quantity.of(qtyIssuedStr, uomKg))
					.issuingToleranceSpec(IssuingToleranceSpec.ofPercent(Percent.of(tolerancePerc)))
					.qtyReject(Quantity.zero(uomKg))
					.qtyScrap(Quantity.zero(uomKg))
					.qtyUsageVariance(Quantity.zero(uomKg))
					.qtyPost(Quantity.zero(uomKg))
					.qtyReserved(Quantity.zero(uomKg))
					.build();
		}
	}
}