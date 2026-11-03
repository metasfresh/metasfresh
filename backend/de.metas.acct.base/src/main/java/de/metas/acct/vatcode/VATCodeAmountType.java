package de.metas.acct.vatcode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.acct.model.X_C_VAT_Code;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum VATCodeAmountType implements ReferenceListAwareEnum
{
	Net(X_C_VAT_Code.AMOUNTTYPE_Net),
	Tax(X_C_VAT_Code.AMOUNTTYPE_Tax),
	;

	@NonNull private static final ValuesIndex<VATCodeAmountType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static VATCodeAmountType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static VATCodeAmountType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static Optional<VATCodeAmountType> optionalOfNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}

	@JsonValue
	public String toJson()
	{
		return code;
	}
}
