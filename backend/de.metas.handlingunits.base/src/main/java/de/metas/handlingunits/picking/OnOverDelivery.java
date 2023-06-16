package de.metas.handlingunits.picking;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum OnOverDelivery implements ReferenceListAwareEnum
{
	TAKE_WHOLE_HU("take_whole_hu"),
	SPLIT_HU("split_hu"),
	FAIL("fail"),
	;

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<OnOverDelivery> index = ReferenceListAwareEnums.index(values());

	OnOverDelivery(@NonNull final String code)
	{
		this.code = code;
	}

	public static OnOverDelivery ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static OnOverDelivery ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	@NonNull
	public static OnOverDelivery ofConfigs(final boolean splitHUIfOverDelivery, final boolean isAllowOverDelivery)
	{
		if (!isAllowOverDelivery && !splitHUIfOverDelivery)
		{
			return FAIL;
		}
		
		return splitHUIfOverDelivery ? SPLIT_HU : TAKE_WHOLE_HU;
	}

	public static OnOverDelivery ofTakeWholeHUFlag(final boolean isTakeWholeHU)
	{
		return isTakeWholeHU ? TAKE_WHOLE_HU : SPLIT_HU;
	}
}
