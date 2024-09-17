package de.metas.pos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public class POSOrderExternalId
{
	@NonNull private final String value;

	private POSOrderExternalId(@NonNull final String value)
	{
		final String valueNorm = normalizeValue(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid external ID: `" + value + "`");
		}

		this.value = valueNorm;
	}

	@Nullable
	private static String normalizeValue(@Nullable final String value)
	{
		return StringUtils.trimBlankToNull(value);
	}

	@JsonCreator
	public static POSOrderExternalId ofString(@NonNull final String value)
	{
		return new POSOrderExternalId(value);
	}

	@Override
	@Deprecated
	public String toString() {return value;}

	@JsonValue
	public String getAsString() {return value;}

	public static boolean equals(@Nullable final POSOrderExternalId id1, @Nullable final POSOrderExternalId id2) {return Objects.equals(id1, id2);}

}
