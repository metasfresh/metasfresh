package de.metas.pos.payment_gateway;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public class POSCardReaderExternalId
{
	@NonNull private final String value;

	private POSCardReaderExternalId(@NonNull final String value)
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
	public static POSCardReaderExternalId ofString(@NonNull final String value)
	{
		return new POSCardReaderExternalId(value);
	}

	@Nullable
	public static POSCardReaderExternalId ofNullableString(@Nullable final String value)
	{
		final String valueNorm = normalizeValue(value);
		return valueNorm != null ? new POSCardReaderExternalId(valueNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return value;}

	@JsonValue
	public String getAsString() {return value;}

	public static boolean equals(@Nullable final POSCardReaderExternalId id1, @Nullable final POSCardReaderExternalId id2) {return Objects.equals(id1, id2);}
}
