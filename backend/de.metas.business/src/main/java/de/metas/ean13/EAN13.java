package de.metas.ean13;

import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Builder
@EqualsAndHashCode
public class EAN13
{
	@NonNull private final String barcode;
	@Getter @NonNull private final EAN13Prefix prefix;
	@Getter @NonNull private final EAN13ProductCode productNo;
	@Nullable private final BigDecimal weightInKg;
	@Getter private final int checksum;

	public static ExplainedOptional<EAN13> fromScannedCode(@NonNull final ScannedCode scannedCode)
	{
		return fromString(scannedCode.getAsString());
	}

	public static ExplainedOptional<EAN13> fromString(@NonNull final String barcode)
	{
		return EAN13Parser.parse(barcode);
	}

	@Override
	@Deprecated
	public String toString() {return barcode;}

	public Optional<BigDecimal> getWeightInKg()
	{
		return Optional.ofNullable(weightInKg);
	}
}
