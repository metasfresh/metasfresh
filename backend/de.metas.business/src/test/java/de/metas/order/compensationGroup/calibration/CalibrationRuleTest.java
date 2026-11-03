package de.metas.order.compensationGroup.calibration;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The factor is a 100-based percent: 80 = 80 %, 100 = unchanged, 0 = Qty 0.
 * The calibrated qty is rounded UP (away from zero) to the UOM precision.
 */
class CalibrationRuleTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static BigDecimal computeQtyCalibrated(final String qtyUncalibrated, final int uomPrecision, final String factorPercent)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setStdPrecision(uomPrecision);
		saveRecord(uom);

		final CalibrationRule rule = CalibrationRule.builder()
				.orgId(OrgId.ANY)
				.bpartnerId(BPartnerId.ofRepoId(1))
				.factor(Percent.of(new BigDecimal(factorPercent)))
				.build();

		return rule.computeQtyCalibrated(Quantity.of(new BigDecimal(qtyUncalibrated), uom)).toBigDecimal();
	}

	@Test
	void factorIsAPercent()
	{
		assertThat(computeQtyCalibrated("375", 2, "80")).isEqualByComparingTo("300");
		assertThat(computeQtyCalibrated("200", 2, "50")).isEqualByComparingTo("100");
		assertThat(computeQtyCalibrated("100", 2, "100")).isEqualByComparingTo("100");
	}

	@Test
	void up_tie_precision0()
	{
		assertThat(computeQtyCalibrated("3", 0, "50")).isEqualByComparingTo("2");
	}

	@Test
	void up_belowHalf_precision0()
	{
		// 4 x 30 % = 1.2; half-up would give 1
		assertThat(computeQtyCalibrated("4", 0, "30")).isEqualByComparingTo("2");
		// 2 x 70 % = 1.4
		assertThat(computeQtyCalibrated("2", 0, "70")).isEqualByComparingTo("2");
	}

	@Test
	void up_precision2()
	{
		// 0.15 x 66.7 % = 0.10005; half-up would give 0.10
		assertThat(computeQtyCalibrated("0.15", 2, "66.7")).isEqualByComparingTo("0.11");
	}

	@Test
	void up_precision3()
	{
		// 0.125 x 33.3 % = 0.041625
		assertThat(computeQtyCalibrated("0.125", 3, "33.3")).isEqualByComparingTo("0.042");
		// 0.125 x 33.6 % = 0.042; exact, no rounding
		assertThat(computeQtyCalibrated("0.125", 3, "33.6")).isEqualByComparingTo("0.042");
		// 0.125 x 32.9 % = 0.041125; half-up would give 0.041
		assertThat(computeQtyCalibrated("0.125", 3, "32.9")).isEqualByComparingTo("0.042");
	}

	@Test
	void up_negative_isAwayFromZero()
	{
		// -4 x 30 % = -1.2; half-up would give -1
		assertThat(computeQtyCalibrated("-4", 0, "30")).isEqualByComparingTo("-2");
		// tie: -3 x 50 % = -1.5
		assertThat(computeQtyCalibrated("-3", 0, "50")).isEqualByComparingTo("-2");
	}

	@Test
	void roundedBase_times120Percent()
	{
		// the base 0.375 is rounded to 0.38 (UOM precision 2) before the factor is applied: 0.38 x 120 % = 0.456
		assertThat(computeQtyCalibrated("0.38", 2, "120")).isEqualByComparingTo("0.46");
		// 0.31 x 120 % = 0.372; half-up would give 0.37
		assertThat(computeQtyCalibrated("0.31", 2, "120")).isEqualByComparingTo("0.38");
	}

	@Test
	void nonZeroFactor_neverRoundsToZero()
	{
		// half-up would give 0 for each of these
		assertThat(computeQtyCalibrated("1", 0, "40")).isEqualByComparingTo("1");
		assertThat(computeQtyCalibrated("0.01", 2, "40")).isEqualByComparingTo("0.01");
		assertThat(computeQtyCalibrated("0.001", 3, "40")).isEqualByComparingTo("0.001");
		assertThat(computeQtyCalibrated("-1", 0, "40")).isEqualByComparingTo("-1");
	}

	@Test
	void factorZero_givesZero()
	{
		assertThat(computeQtyCalibrated("6", 2, "0")).isEqualByComparingTo("0");
		assertThat(computeQtyCalibrated("0.37", 2, "0")).isEqualByComparingTo("0");
	}

	@Test
	void factor100_keepsTheAlreadyRoundedBase()
	{
		assertThat(computeQtyCalibrated("0.12", 2, "100")).isEqualByComparingTo("0.12");
		assertThat(computeQtyCalibrated("1.5", 1, "100")).isEqualByComparingTo("1.5");
	}
}
