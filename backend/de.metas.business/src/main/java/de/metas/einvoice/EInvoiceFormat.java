package de.metas.einvoice;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum EInvoiceFormat implements ReferenceListAwareEnum
{
	ZUGFeRD("Z"),
	XRECHNUNG("X"),
	PEPPOL("P"),
	;

	@NonNull @Getter private final String code;

	@NonNull private static final ValuesIndex<EInvoiceFormat> index = ReferenceListAwareEnums.index(values());

	public static EInvoiceFormat ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static EInvoiceFormat ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	@JsonValue
	public String toJson()
	{
		return getCode();
	}
}
