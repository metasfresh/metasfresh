package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
public final class HUQRCodeUniqueId
{
	@NonNull String idBase;

	@Getter
	@NonNull String displayableSuffix;

	private HUQRCodeUniqueId(@NonNull final String idBase, @NonNull final String displayableSuffix)
	{
		this.idBase = idBase;
		this.displayableSuffix = displayableSuffix;
	}

	public static HUQRCodeUniqueId random()
	{
		return ofUUID(UUID.randomUUID());
	}

	public static HUQRCodeUniqueId ofUUID(@NonNull final UUID uuid)
	{

		final String uuidStr = uuid.toString().replace("-", ""); // expect 32 chars

		final StringBuilder uuidFirstPart = new StringBuilder(32);
		final StringBuilder uuidLastPart = new StringBuilder();

		// TO reduce the change of having duplicate displayable suffix,
		// we cannot just take the last 4 chars but we will pick some of them from the middle
		// See https://www.ietf.org/rfc/rfc4122.txt section 3, to understand the UUID v4 format.
		for (int i = 0; i < uuidStr.length(); i++)
		{
			final char ch = uuidStr.charAt(i);

			if (// from time_low (0-7):
					i == 5
							// from: time_mid (8-11):
							|| i == 8
							|| i == 11
							// from: time_hi_and_version (12-15)
							|| i == 13
				// from: clock_seq_hi_and_reserved (16-17)
				// from: clock_seq_low (18-19)
				// from: node (20-31)
			)
			{
				uuidLastPart.append(ch);
			}
			else
			{
				uuidFirstPart.append(ch);
			}
		}

		final String uuidLastPartNorm = Strings.padStart(
				String.valueOf(Integer.parseInt(uuidLastPart.toString(), 16)),
				5, // because 4 hex digits can lead to 5 decimal digits (i.e. FFFF -> 65535)
				'0');
		return new HUQRCodeUniqueId(uuidFirstPart.toString(), uuidLastPartNorm);
	}

	@JsonCreator
	public static HUQRCodeUniqueId ofJson(@NonNull final String json)
	{
		final int idx = json.indexOf('-');
		if (idx <= 0)
		{
			throw new AdempiereException("Invalid ID: `" + json + "`");
		}

		final String idBase = json.substring(0, idx);
		final String displayableSuffix = json.substring(idx + 1);
		return new HUQRCodeUniqueId(idBase, displayableSuffix);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return idBase + "-" + displayableSuffix;
	}

	public static boolean equals(@Nullable final HUQRCodeUniqueId id1, @Nullable final HUQRCodeUniqueId id2)
	{
		return Objects.equals(id1, id2);
	}

}
