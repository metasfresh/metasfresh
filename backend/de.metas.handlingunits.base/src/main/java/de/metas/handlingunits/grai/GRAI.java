package de.metas.handlingunits.grai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class GRAI implements Comparable<GRAI>
{
	@NonNull String value;

	@JsonCreator
	@NonNull
	public static GRAI of(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid GRAI: " + value);
		}

		return new GRAI(valueNorm);
	}

	@Nullable
	public static GRAI ofNullable(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? of(valueNorm) : null;
	}

	@Nullable
	public static String toValue(@Nullable final GRAI grai)
	{
		return grai != null ? grai.value : null;
	}

	@JsonValue
	@NonNull
	public String getValue()
	{
		return value;
	}

	@Override
	public int compareTo(@NonNull final GRAI o)
	{
		return value.compareTo(o.value);
	}
}
