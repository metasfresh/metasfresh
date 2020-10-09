package de.metas.uom;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_UOM;

import java.time.temporal.TemporalUnit;

/**
 * Utility class with more or less "trivial", but reusable for UOM-code.
 */
@UtilityClass
public class UOMUtil
{

	public static boolean isMinute(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.MINUTE.equals(x12de355);
	}

	public static boolean isHour(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.HOUR.equals(x12de355);
	}

	public static boolean isDay(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.DAY.equals(x12de355);
	}

	public static boolean isWorkDay(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.DAY_WORK.equals(x12de355);
	}

	public static boolean isWeek(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.WEEK.equals(x12de355);
	}

	public static boolean isMonth(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.MONTH.equals(x12de355);
	}

	public static boolean isWorkMonth(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.MONTH_WORK.equals(x12de355);
	}

	public static boolean isYear(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofNullableCode(uom.getX12DE355());
		return X12DE355.YEAR.equals(x12de355);
	}

	/**
	 * @return true if is time UOM
	 */
	public static boolean isTime(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(uom.getX12DE355());
		return x12de355.isTemporalUnit();
	}

	@NonNull
	public static TemporalUnit toTemporalUnit(final I_C_UOM uom)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(uom.getX12DE355());
		return x12de355.getTemporalUnit();
	}
}
