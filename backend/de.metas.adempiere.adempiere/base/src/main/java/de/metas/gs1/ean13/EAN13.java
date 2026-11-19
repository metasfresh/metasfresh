package de.metas.gs1.ean13;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.gs1.GTIN;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * EAN-13 (European Article Number, 13 digits):
 * A specific implementation of GTIN, namely GTIN-13.
 * Always has 13 digits: a GS1 prefix (country/organization code), manufacturer code, product code, and a checksum.
 * Used globally (not only in Europe any more) for retail products.
 */
@Builder
@EqualsAndHashCode
public final class EAN13
{
	@NonNull private final String barcode;
	@Getter @NonNull private final EAN13Prefix prefix;
	@Getter @NonNull private final EAN13ProductCode productNo;
	@Nullable private final BigDecimal weightInKg;
	@Getter private final int checksum;

	public static ExplainedOptional<EAN13> ofString(@NonNull final String barcode)
	{
		return EAN13Parser.parse(barcode);
	}

	@JsonCreator
	@Nullable
	public static EAN13 ofNullableString(@Nullable final String barcode)
	{
		final String barcodeNorm = StringUtils.trimBlankToNull(barcode);
		return barcodeNorm != null
				? EAN13Parser.parse(barcodeNorm).orElseThrow()
				: null;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return barcode;}

	/**
	 * @return true if standard/fixed code (i.e. not starting with prefix 28 nor 29)
	 */
	public boolean isFixed() {return prefix.isFixed();}

	public boolean isVariable() {return prefix.isFixed();}

	/**
	 * @return true if variable weight EAN13 (i.e. starts with prefix 28)
	 */
	public boolean isVariableWeight() {return prefix.isVariableWeight();}

	/**
	 * @return true if internal or variable measure EAN13 (i.e. starts with prefix 29)
	 */
	public boolean isInternalUseOrVariableMeasure() {return prefix.isInternalUseOrVariableMeasure();}

	public Optional<BigDecimal> getWeightInKg() {return Optional.ofNullable(weightInKg);}

	public GTIN toGTIN() {return GTIN.ofEAN13(this);}

	public boolean isMatching(@NonNull final EAN13ProductCode expectedProductNo)
	{
		return EAN13ProductCode.equals(this.productNo, expectedProductNo);
	}

	public boolean productCodeEndsWith(final @NonNull EAN13ProductCode expectedProductCode)
	{
		return this.productNo.endsWith(expectedProductCode);
	}
}
