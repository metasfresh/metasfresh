package de.metas.gs1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
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
@EqualsAndHashCode(doNotUseGetters = true, exclude = { "ean13Holder" })
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public final class GTIN
{
	@NonNull private final String value;

	private transient ExplainedOptional<EAN13> ean13Holder; // lazy

	private GTIN(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid GTIN value: " + value);
		}
		this.value = valueNorm;
	}

	private GTIN(@NonNull final EAN13 ean13)
	{
		this.value = ean13.getAsString();
		this.ean13Holder = ExplainedOptional.of(ean13);
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

	public static GTIN ofEAN13(@NonNull final EAN13 ean13)
	{
		return new GTIN(ean13);
	}

	public static Optional<GTIN> ofScannedCode(@NonNull final ScannedCode scannedCode)
	{
		return optionalOfNullableString(scannedCode.getAsString());
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return value;}

	public ExplainedOptional<EAN13> toEAN13()
	{
		ExplainedOptional<EAN13> ean13Holder = this.ean13Holder;
		if (ean13Holder == null)
		{
			ean13Holder = this.ean13Holder = EAN13.fromString(value);
		}
		return ean13Holder;
	}

	public boolean isMatching(final @NonNull EAN13ProductCode expectedProductCode)
	{
		final EAN13 ean13 = toEAN13().orElse(null);
		return ean13 != null && ean13.isMatching(expectedProductCode);
	}

	public boolean productCodeEndsWith(final @NonNull EAN13ProductCode expectedProductCode)
	{
		final EAN13 ean13 = toEAN13().orElse(null);
		return ean13 != null && ean13.productCodeEndsWith(expectedProductCode);
	}

	/**
	 * @return true if fixed code (e.g. not a variable weight EAN13 etc)
	 */
	public boolean isFixed()
	{
		final EAN13 ean13 = toEAN13().orElse(null);
		return ean13 == null || ean13.isFixed();
	}

	/**
	 * @return true if fixed code (e.g. not a variable weight EAN13 etc)
	 */
	public boolean isVariable()
	{
		final EAN13 ean13 = toEAN13().orElse(null);
		return ean13 == null || ean13.isVariableWeight();
	}
}
