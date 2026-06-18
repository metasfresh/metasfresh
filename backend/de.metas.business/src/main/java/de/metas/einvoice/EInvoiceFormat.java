package de.metas.einvoice;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum EInvoiceFormat implements ReferenceListAwareEnum
{
	ZUGFeRD("Z"),
	XRECHNUNG("X"),
	PEPPOL("P"),
	;

	@Getter
	@NonNull
	private final String code;

	EInvoiceFormat(@NonNull final String code)
	{
		this.code = code;
	}

	private static final ValuesIndex<EInvoiceFormat> index = ReferenceListAwareEnums.index(values());

	public static EInvoiceFormat ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static EInvoiceFormat ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
