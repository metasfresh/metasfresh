package de.metas.mforecast.generator;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

/**
 * Defines which historical sales period is used as the basis for the forecast calculation.
 * Stored in PP_Product_Planning.Forecast_CalculationMethod (AD_Reference 542072).
 * <p>
 * <ul>
 *   <li>{@link #AVG_52_WEEKS} — rolling average of the last 52 weeks</li>
 *   <li>{@link #AVG_26_WEEKS} — rolling average of the last 26 weeks</li>
 *   <li>{@link #AVG_12_WEEKS} — rolling average of the last 12 weeks</li>
 *   <li>{@link #AVG_PREV_CALENDAR_YEAR} — average over the full previous calendar year</li>
 *   <li>{@link #AVG_SAME_PERIOD_PREV_YEAR} — same calendar period shifted back 1 year (phase-aligned, no rescaling)</li>
 * </ul>
 */
@Getter
@RequiredArgsConstructor
public enum ForecastCalculationMethod implements ReferenceListAwareEnum
{
	AVG_52_WEEKS("0"),
	AVG_26_WEEKS("1"),
	AVG_12_WEEKS("2"),
	AVG_PREV_CALENDAR_YEAR("3"),
	AVG_SAME_PERIOD_PREV_YEAR("4");

	private static final ValuesIndex<ForecastCalculationMethod> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static ForecastCalculationMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ForecastCalculationMethod ofNullableCode(@Nullable final String code)
	{
		return code != null ? index.ofCode(code) : null;
	}
}
