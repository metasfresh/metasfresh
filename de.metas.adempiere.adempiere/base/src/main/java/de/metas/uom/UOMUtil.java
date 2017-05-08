package de.metas.uom;

import org.compiere.model.I_C_UOM;

/**
 * Utility class with more or less "trivial", but reusable for UOM-code.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class UOMUtil
{
	/**
	 * Second
	 *
	 * @param uom
	 * @return true if UOM is second
	 */
	public static boolean isSecond(final I_C_UOM uom)
	{
		return UOMConstants.X12_SECOND.equals(uom.getX12DE355());
	}

	/**
	 * Minute
	 * 
	 * @param uom
	 * @return true if UOM is minute
	 */
	public static boolean isMinute(final I_C_UOM uom)
	{
		return UOMConstants.X12_MINUTE.equals(uom.getX12DE355());
	}

	/**
	 * Hour
	 * 
	 * @param uom
	 * @return true if UOM is hour
	 */
	public static boolean isHour(final I_C_UOM uom)
	{
		return UOMConstants.X12_HOUR.equals(uom.getX12DE355());
	}

	/**
	 * Day
	 * 
	 * @param uom
	 * @return true if UOM is Day
	 */
	public static boolean isDay(final I_C_UOM uom)
	{
		return UOMConstants.X12_DAY.equals(uom.getX12DE355());
	}

	/**
	 * 
	 * WorkDay
	 * 
	 * @param uom
	 * @return true if UOM is work day
	 */
	public static boolean isWorkDay(final I_C_UOM uom)
	{
		return UOMConstants.X12_DAY_WORK.equals(uom.getX12DE355());
	}

	/**
	 * Week
	 * 
	 * @param uom
	 * @return true if UOM is Week
	 */
	public static boolean isWeek(final I_C_UOM uom)
	{
		return UOMConstants.X12_WEEK.equals(uom.getX12DE355());
	}

	/**
	 * Month
	 * 
	 * @param uom
	 * @return true if UOM is Month
	 */
	public static boolean isMonth(final I_C_UOM uom)
	{
		return UOMConstants.X12_MONTH.equals(uom.getX12DE355());
	}

	/**
	 * WorkMonth
	 * 
	 * @param uom
	 * @return true if UOM is Work Month
	 */
	public static boolean isWorkMonth(final I_C_UOM uom)
	{
		return UOMConstants.X12_MONTH_WORK.equals(uom.getX12DE355());
	}

	/**
	 * Year
	 * 
	 * @param uom
	 * @return true if UOM is year
	 */
	public static boolean isYear(final I_C_UOM uom)
	{
		return UOMConstants.X12_YEAR.equals(uom.getX12DE355());
	}

	/**
	 * Check if it's an UOM that measures time
	 * 
	 * @param uom
	 * @return true if is time UOM
	 */
	public static boolean isTime(I_C_UOM uom)
	{
		final String x12de355 = uom.getX12DE355();

		return UOMConstants.X12_SECOND.equals(x12de355)
				|| UOMConstants.X12_MINUTE.equals(x12de355)
				|| UOMConstants.X12_HOUR.equals(x12de355)
				|| UOMConstants.X12_DAY.equals(x12de355)
				|| UOMConstants.X12_DAY_WORK.equals(x12de355)
				|| UOMConstants.X12_WEEK.equals(x12de355)
				|| UOMConstants.X12_MONTH.equals(x12de355)
				|| UOMConstants.X12_MONTH_WORK.equals(x12de355)
				|| UOMConstants.X12_YEAR.equals(x12de355);
	}
}
