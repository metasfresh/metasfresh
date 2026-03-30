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
	 * Tests for {@link OrderBOMLineQuantities#assertQtyToIssueToleranceIsRespected()}
	 * with very small quantities where the qty scale exceeds the UOM's StdPrecision.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/28242">me03#28242</a>
	 */
	@Nested
	class assertQtyToIssueToleranceIsRespected_SmallQuantities
	{
		/**
		 * Issuing exactly the required qty (0.00384 kg) must be within 1% tolerance,
		 * even though the qty's scale (5) exceeds the UOM's StdPrecision (3 for kg).
		 */
		@Test
		void issuingExactQty_withinPercentageTolerance()
		{
			final Quantity qtyRequired = new Quantity(new BigDecimal("0.00384"), uomKg);
			final Quantity qtyIssued = new Quantity(new BigDecimal("0.00384"), uomKg);

			final OrderBOMLineQuantities qtys = OrderBOMLineQuantities.builder()
					.qtyRequired(qtyRequired)
					.qtyRequiredBeforeClose(qtyRequired)
					.qtyIssuedOrReceived(qtyIssued)
					.qtyIssuedOrReceivedActual(qtyIssued)
					.issuingToleranceSpec(IssuingToleranceSpec.ofPercent(Percent.of(1)))
					.qtyReject(Quantity.zero(uomKg))
					.qtyScrap(Quantity.zero(uomKg))
					.qtyUsageVariance(Quantity.zero(uomKg))
					.qtyPost(Quantity.zero(uomKg))
					.qtyReserved(Quantity.zero(uomKg))
					.build();

			assertThatCode(qtys::assertQtyToIssueToleranceIsRespected)
					.doesNotThrowAnyException();
		}

		/**
		 * Issuing a qty significantly above the upper tolerance bound must fail.
		 */
		@Test
		void issuingTooMuch_exceedsTolerance()
		{
			final Quantity qtyRequired = new Quantity(new BigDecimal("0.00384"), uomKg);
			// 0.00500 is ~30% more than 0.00384, well outside 1% tolerance
			final Quantity qtyIssued = new Quantity(new BigDecimal("0.00500"), uomKg);

			final OrderBOMLineQuantities qtys = OrderBOMLineQuantities.builder()
					.qtyRequired(qtyRequired)
					.qtyRequiredBeforeClose(qtyRequired)
					.qtyIssuedOrReceived(qtyIssued)
					.qtyIssuedOrReceivedActual(qtyIssued)
					.issuingToleranceSpec(IssuingToleranceSpec.ofPercent(Percent.of(1)))
					.qtyReject(Quantity.zero(uomKg))
					.qtyScrap(Quantity.zero(uomKg))
					.qtyUsageVariance(Quantity.zero(uomKg))
					.qtyPost(Quantity.zero(uomKg))
					.qtyReserved(Quantity.zero(uomKg))
					.build();

			assertThatThrownBy(qtys::assertQtyToIssueToleranceIsRespected)
					.isInstanceOf(AdempiereException.class);
		}
	}
}