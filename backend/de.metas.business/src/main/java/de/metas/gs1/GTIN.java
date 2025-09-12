package de.metas.gs1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * GTIN (Global Trade Item Number):
 * <p>
 * An umbrella term defined by GS1 for product identifiers used worldwide.
 * Can have different lengths depending on the format:
 * <ul>
 * <li>GTIN-8 (8 digits)</li>
 * <li>GTIN-12 (12 digits, typically UPC in the U.S.)</li>
 * <li>GTIN-13 (13 digits, typically EAN-13 in Europe/most of the world)</li>
 * <li>GTIN-14 (14 digits, used for cases/cartons).</li>
 * </ul>
 */
@EqualsAndHashCode(doNotUseGetters = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public final class GTIN
{
	@NonNull private final String value;

	private GTIN(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid GTIN value: " + value);
		}
		this.value = valueNorm;
	}

	@JsonCreator
	public static GTIN ofString(@NonNull final String value)
	{
		return new GTIN(value);
	}

	public static GTIN ofNullableString(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? ofString(valueNorm) : null;
	}

	public static Optional<GTIN> optionalOfNullableString(@Nullable final String value) {return Optional.ofNullable(ofNullableString(value));}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return value;}
}
