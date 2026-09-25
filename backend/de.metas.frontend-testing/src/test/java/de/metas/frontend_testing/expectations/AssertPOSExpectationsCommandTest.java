package de.metas.frontend_testing.expectations;

import de.metas.uom.UomId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link AssertPOSExpectationsCommand#assertQtyMatches}: a POS-return / credit-memo line's expected qty (AC4,
 * AC4b) must match BOTH the numeric value AND the UOM — a scale-insensitive value match ({@code 0.3} vs
 * {@code 0.300}) alone is not enough, since a qty stated in the wrong UOM (e.g. base UOM instead of the
 * catch-weight UOM) would silently pass a value-only check.
 */
class AssertPOSExpectationsCommandTest
{
	private static final UomId KGM = UomId.ofRepoId(1);
	private static final UomId EACH = UomId.ofRepoId(2);

	@Test
	void passes_whenQtyAndUomMatch_evenAtDifferentScale()
	{
		assertThatCode(() -> AssertPOSExpectationsCommand.assertQtyMatches(
				new BigDecimal("0.300"), KGM, new BigDecimal("0.3"), KGM, "return line[0]"))
				.doesNotThrowAnyException();
	}

	@Test
	void fails_whenUomDiffers_evenIfNumericValueMatches()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertQtyMatches(
				new BigDecimal("0.300"), KGM, new BigDecimal("0.300"), EACH, "return line[0]"))
				.hasMessageContaining("UOM");
	}

	@Test
	void fails_whenQtyValueDiffers()
	{
		assertThatThrownBy(() -> AssertPOSExpectationsCommand.assertQtyMatches(
				new BigDecimal("0.300"), KGM, new BigDecimal("0.482"), KGM, "return line[0]"))
				.hasMessageContaining("qty");
	}
}
