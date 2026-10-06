package de.metas.order.compensationGroup.calibration;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CalibratedQtyCalculatorTest
{
	private static CalibratedQty compute(final String templateQty, final String menuQty, final String factor, final int precision)
	{
		return CalibratedQtyCalculator.compute(new BigDecimal(templateQty), new BigDecimal(menuQty), new BigDecimal(factor), precision);
	}

	@Test
	void halfUp_exactHalf()
	{
		assertThat(compute("3", "1", "0.5", 0).getCalibrated()).isEqualTo(new BigDecimal("2"));
	}

	@Test
	void halfUp_notUp_boundary()
	{
		// RoundingMode.UP would give 0.13
		assertThat(compute("0.121", "1", "1", 2).getCalibrated()).isEqualTo(new BigDecimal("0.12"));
	}

	@Test
	void zeroFloor_precision0()
	{
		assertThat(compute("1", "1", "0.4", 0).getCalibrated()).isEqualTo(new BigDecimal("1"));
	}

	@Test
	void zeroFloor_precision2()
	{
		assertThat(compute("0.01", "1", "0.4", 2).getCalibrated()).isEqualTo(new BigDecimal("0.01"));
	}

	@Test
	void zeroFloor_precision3()
	{
		assertThat(compute("0.001", "1", "0.4", 3).getCalibrated()).isEqualTo(new BigDecimal("0.001"));
	}

	@Test
	void zeroFloor_notWhenBaseRoundsToZero()
	{
		final CalibratedQty result = compute("0.004", "1", "1", 2);
		assertThat(result.getCalibrated()).isEqualTo(new BigDecimal("0.00"));
		assertThat(result.getUncalibrated()).isEqualTo(new BigDecimal("0.00"));
	}

	@Test
	void zeroFloor_negativeKeepsSign()
	{
		assertThat(compute("1", "-1", "0.4", 0).getCalibrated()).isEqualTo(new BigDecimal("-1"));
	}
}
