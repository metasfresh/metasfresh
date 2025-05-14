package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SumUpCardReaderExternalId
{
	@NonNull private final String value;

	private SumUpCardReaderExternalId(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid card reader id: " + value);
		}
		this.value = valueNorm;
	}

	@JsonCreator
	@NonNull
	public static SumUpCardReaderExternalId ofString(@NonNull final String value)
	{
		return new SumUpCardReaderExternalId(value);
	}

	@Nullable
	public static SumUpCardReaderExternalId ofNullableString(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? new SumUpCardReaderExternalId(valueNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return value;}
}
