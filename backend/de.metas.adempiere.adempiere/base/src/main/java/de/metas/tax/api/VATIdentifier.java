package de.metas.tax.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
public final class VATIdentifier
{
	@NonNull private final String value;

	private VATIdentifier(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid VAT ID");
		}

		this.value = valueNorm;
	}

	@NonNull
	public static VATIdentifier of(@NonNull final String value)
	{
		return new VATIdentifier(value);
	}

	@Nullable
	@JsonCreator
	public static VATIdentifier ofNullable(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? of(valueNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@NonNull
	@JsonValue
	public String getAsString() {return value;}

	@Nullable
	public static String toString(@Nullable final VATIdentifier vatId)
	{
		return vatId != null ? vatId.getAsString() : null;
	}
}
