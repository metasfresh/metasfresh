package de.metas.mforecast.generator;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@Getter
@RequiredArgsConstructor
public enum ForecastComparisonPeriod implements ReferenceListAwareEnum
{
	AVG_52_WEEKS("0"),
	AVG_26_WEEKS("1"),
	AVG_12_WEEKS("2"),
	AVG_PREV_CALENDAR_YEAR("3"),
	AVG_SAME_PERIOD_PREV_YEAR("4");

	private static final ValuesIndex<ForecastComparisonPeriod> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static ForecastComparisonPeriod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ForecastComparisonPeriod ofNullableCode(@Nullable final String code)
	{
		return code != null ? index.ofCode(code) : null;
	}
}
