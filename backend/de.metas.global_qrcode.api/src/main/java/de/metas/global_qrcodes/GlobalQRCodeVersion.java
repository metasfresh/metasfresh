package de.metas.global_qrcodes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
@SuppressWarnings("UnstableApiUsage")
public final class GlobalQRCodeVersion
{
	public static GlobalQRCodeVersion ofInt(final int value)
	{
		if (value <= 0)
		{
			throw Check.mkEx("Invalid global QR code version: " + value);
		}

		return interner.intern(new GlobalQRCodeVersion(value));
	}

	@JsonCreator
	public static GlobalQRCodeVersion ofString(final String string)
	{
		try
		{
			return ofInt(Integer.parseInt(string));
		}
		catch (final NumberFormatException e)
		{
			throw Check.mkEx("Invalid global QR code version: `" + string + "`", e);
		}
	}

	private static final Interner<GlobalQRCodeVersion> interner = Interners.newStrongInterner();

	private final int value;

	private GlobalQRCodeVersion(final int value)
	{
		this.value = value;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return String.valueOf(toJson());
	}

	@JsonValue
	public int toJson()
	{
		return value;
	}

	public static boolean equals(@Nullable final GlobalQRCodeVersion o1, @Nullable final GlobalQRCodeVersion o2)
	{
		return Objects.equals(o1, o2);
	}

}
