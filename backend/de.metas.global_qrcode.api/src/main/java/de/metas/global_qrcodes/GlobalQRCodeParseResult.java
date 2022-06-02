package de.metas.global_qrcodes;

import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class GlobalQRCodeParseResult
{
	@Nullable GlobalQRCode globalQRCode;
	@Nullable String error;

	public static GlobalQRCodeParseResult error(@NonNull final String error)
	{
		Check.assumeNotEmpty(error, "error is not empty");
		return new GlobalQRCodeParseResult(null, error);
	}

	public static GlobalQRCodeParseResult ok(@NonNull final GlobalQRCode globalQRCode)
	{
		return new GlobalQRCodeParseResult(globalQRCode, null);
	}

	public GlobalQRCodeParseResult(@Nullable final GlobalQRCode globalQRCode, @Nullable final String error)
	{
		if (CoalesceUtil.countNotNulls(globalQRCode, error) != 1)
		{
			throw Check.mkEx("One and only one shall be specified: " + globalQRCode + ", " + error);
		}

		this.globalQRCode = globalQRCode;
		this.error = error;
	}

	public GlobalQRCode orThrow()
	{
		if (globalQRCode == null)
		{
			throw Check.mkEx(error);
		}
		else
		{
			return globalQRCode;
		}
	}

	public GlobalQRCode orNullIfError()
	{
		return globalQRCode;
	}
}
