package de.metas.acct.vatcode;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

@Getter
public enum VATCodeAmountType implements ReferenceListAwareEnum
{
	Net("N"),
	Tax("T");

	public static final int AD_REFERENCE_ID = 542087;

	@NonNull private final String code;

	VATCodeAmountType(@NonNull final String code)
	{
		this.code = code;
	}

	private static final ReferenceListAwareEnums.ValuesIndex<VATCodeAmountType> index
			= ReferenceListAwareEnums.index(values());

	public static VATCodeAmountType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
