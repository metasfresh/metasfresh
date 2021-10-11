package de.metas.handlingunits.picking;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum OnOverDelivery implements ReferenceListAwareEnum
{
	WHOLE_HU("true"),
	SPLIT("false"),
	/** option for supporting old code, when we have from config that we can over deliver,
	but we actually get an exception and when from config says we can not over deliver,
	we can actually over deliver, by taking the whole HU */
	FALLBACK("fallback");

	@Getter
	private final String code;

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

	private static final ReferenceListAwareEnums.ValuesIndex<OnOverDelivery> index = ReferenceListAwareEnums.index(values());

	public boolean isSplit()
	{
		return SPLIT.equals(this);
	}

	public boolean isFallback()
	{
		return FALLBACK.equals(this);
	}

	public static OnOverDelivery ofTakeWholeHUFlag(boolean isTakeWholeHU)
	{
		return isTakeWholeHU ? WHOLE_HU : SPLIT;
	}
}
