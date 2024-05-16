package de.metas.global_qrcodes;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
@SuppressWarnings("UnstableApiUsage")
public final class GlobalQRCodeType
{
	public static GlobalQRCodeType ofString(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null)
		{
			throw Check.mkEx("Invalid global QR code type: `" + code + "`");
		}

		return interner.intern(new GlobalQRCodeType(codeNorm));
	}

	private static final Interner<GlobalQRCodeType> interner = Interners.newStrongInterner();

	private final String code;

	private GlobalQRCodeType(@NonNull final String code)
	{
		this.code = code;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		return code;
	}

	public static boolean equals(@Nullable final GlobalQRCodeType type1, @Nullable final GlobalQRCodeType type2)
	{
		return Objects.equals(type1, type2);
	}
}
