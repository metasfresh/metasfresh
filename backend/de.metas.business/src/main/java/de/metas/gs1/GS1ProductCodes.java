package de.metas.gs1;

import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class GS1ProductCodes
{
	private static final GS1ProductCodes EMPTY = builder().build();

	@Nullable GTIN gtin;
	@Nullable EAN13 ean13;
	@Nullable EAN13ProductCode ean13ProductCode;

	public boolean isEmpty() {return this.equals(EMPTY);}

	public boolean isMatching(@NonNull final EAN13ProductCode expectedProductCode)
	{
		return
				(gtin != null && gtin.isMatching(expectedProductCode))
						|| (ean13 != null && ean13.isMatching(expectedProductCode))
						|| (ean13ProductCode != null && EAN13ProductCode.equals(ean13ProductCode, expectedProductCode));
	}

	public boolean endsWith(final @NonNull EAN13ProductCode expectedProductCode)
	{
		return (gtin != null && gtin.productCodeEndsWith(expectedProductCode))
				|| (ean13 != null && ean13.productCodeEndsWith(expectedProductCode))
				|| (ean13ProductCode != null && ean13ProductCode.endsWith(expectedProductCode));
	}

	public @NonNull GS1ProductCodes fallbackTo(final @NonNull GS1ProductCodes defaults)
	{
		final GS1ProductCodes result = toBuilder()
				.gtin(gtin != null ? gtin : defaults.gtin)
				.ean13(ean13 != null ? ean13 : defaults.ean13)
				.ean13ProductCode(ean13ProductCode != null ? ean13ProductCode : defaults.ean13ProductCode)
				.build();

		return Objects.equals(this, result) ? this : result;
	}
}
