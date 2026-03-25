package de.metas.handlingunits.grai;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

public enum GRAIRequired implements ReferenceListAwareEnum
{
	No("N"),
	Yes("Y"),
	Dummy("D"),
	;

	@Getter
	private final String code;

	GRAIRequired(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static GRAIRequired ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static Optional<GRAIRequired> optionalOfNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}

	@NonNull
	public static GRAIRequired ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<GRAIRequired> index = ReferenceListAwareEnums.index(values());
}
