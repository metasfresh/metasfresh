package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

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

	public static HUQRCodeUniqueId ofUUID(@NonNull final UUID uuid)
	{
		final String uuidStr = uuid.toString().replace("-", "");
		final String uuidFirstPart = uuidStr.substring(0, uuidStr.length() - 4);
		final String uuidLastPart = uuidStr.substring(uuidStr.length() - 4);
		final String uuidLastPartNorm = Strings.padStart(
				String.valueOf(Integer.parseInt(uuidLastPart, 16)),
				5, // because 4 hex digits can lead to 5 decimal digits (i.e. FFFF -> 65535)
				'0');
		return new HUQRCodeUniqueId(uuidFirstPart, uuidLastPartNorm);
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
}
