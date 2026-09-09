package de.metas.einvoice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
public enum EInvoiceFormat implements ReferenceListAwareEnum
{
	ZUGFeRD(X_C_BPartner.EINVOICETYPE_ZUGFeRD),
	XRECHNUNG(X_C_BPartner.EINVOICETYPE_XRechnung),
	PEPPOL(X_C_BPartner.EINVOICETYPE_PEPPOL),
	;

	public static final int AD_REFERENCE_ID = X_C_BPartner.EINVOICETYPE_AD_Reference_ID;

	@NonNull private static final ValuesIndex<EInvoiceFormat> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@JsonCreator
	@NonNull
	public static EInvoiceFormat ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static EInvoiceFormat ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static Optional<EInvoiceFormat> optionalOfCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	@JsonValue
	public String toJson()
	{
		return getCode();
	}

	public boolean isZUGFeRD()
	{
		return this == ZUGFeRD;
	}

	public boolean isXRechnung()
	{
		return this == XRECHNUNG;
	}

	public boolean isPeppol()
	{
		return this == PEPPOL;
	}
}
