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
public enum ForecastPrecisionUnit implements ReferenceListAwareEnum
{
	WEEK("W"),
	MONTH("M");

	private static final ValuesIndex<ForecastPrecisionUnit> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static ForecastPrecisionUnit ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ForecastPrecisionUnit ofNullableCode(@Nullable final String code)
	{
		return code != null ? index.ofCode(code) : null;
	}
}
