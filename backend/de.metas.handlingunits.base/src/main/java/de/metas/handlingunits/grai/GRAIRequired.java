package de.metas.handlingunits.grai;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_BPartner;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum GRAIRequired implements ReferenceListAwareEnum
{
	No(X_C_BPartner.GRAIREQUIRED_No),
	Yes(X_C_BPartner.GRAIREQUIRED_Yes),
	YesWithDummyGRAIs(X_C_BPartner.GRAIREQUIRED_YesWithDummyGRAIs),
	;

	private static final ValuesIndex<GRAIRequired> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@NonNull
	public static GRAIRequired ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
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

	public boolean isNo() {return this == No;}
}
