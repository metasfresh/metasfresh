package org.compiere.model;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum TabIncludeFiltersStrategy implements ReferenceListAwareEnum
{
	None(X_AD_Tab.INCLUDEFILTERSSTRATEGY_None),
	Explicit(X_AD_Tab.INCLUDEFILTERSSTRATEGY_Explicit),
	Auto(X_AD_Tab.INCLUDEFILTERSSTRATEGY_Auto),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<TabIncludeFiltersStrategy> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static TabIncludeFiltersStrategy ofCode(@NonNull String code) {return index.ofCode(code);}

	@Nullable
	public static TabIncludeFiltersStrategy ofNullableCode(@Nullable String code) {return index.ofNullableCode(code);}

	public static Optional<TabIncludeFiltersStrategy> optionalOfNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}
}
