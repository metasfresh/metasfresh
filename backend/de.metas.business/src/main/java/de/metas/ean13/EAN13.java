package de.metas.ean13;

import de.metas.i18n.ExplainedOptional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Builder
@EqualsAndHashCode
@ToString
public class EAN13
{
	public static final String PREFIX_VariableWeight = "28";
	public static final String PREFIX_InternalUseOrVariableMeasure = "29";

	@NonNull @Getter private final String prefix;
	@NonNull @Getter private final String productNo;
	@Nullable private final BigDecimal weightInKg;
	@Getter private final int checksum;

	public static ExplainedOptional<EAN13> fromString(@NonNull final String barcode)
	{
		//
		// Check if the barcode length is exactly 13
		if (barcode.length() != 13)
		{
			return ExplainedOptional.emptyBecause("Invalid barcode length. It must be 13 digits.");
		}

		//
		// Validate the check digit
		int checksum = Character.getNumericValue(barcode.charAt(12)); // Checksum digit (C)
		int checksumExpected = computeChecksum(barcode.substring(0, 12));
		if (checksumExpected != checksum)
		{
			return ExplainedOptional.emptyBecause("Invalid checksum. Expected '" + checksumExpected + "' but got '" + checksum + "'.");
		}

		final String prefix = barcode.substring(0, 2);

		//
		// 28 - Variable-Weight barcodes
		// 29 - Internal Use / Variable measure
		if (prefix.equals(PREFIX_VariableWeight) || prefix.equals(PREFIX_InternalUseOrVariableMeasure))
		{
			final String productNo = barcode.substring(2, 7); // 5 digits for article code (AAAAA)
			final String weightStr = barcode.substring(7, 12); // 5 digits for weight (GGGGG)

			// Interpret the weight/measure (assume it's in grams or kilograms)
			final BigDecimal weightInKg = new BigDecimal(weightStr).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP);

			return ExplainedOptional.of(
					builder()
							.prefix(prefix)
							.productNo(productNo)
							.weightInKg(weightInKg)
							.checksum(checksumExpected)
							.build()
			);
		}
		else
		{
			return ExplainedOptional.emptyBecause("Invalid barcode prefix.");
		}
	}

	private static int computeChecksum(final String barcodeWithoutChecksum)
	{
		int oddSum = 0;
		int evenSum = 0;

		// Loop through barcode without the checksum
		for (int i = 0; i < barcodeWithoutChecksum.length(); i++)
		{
			int digit = Character.getNumericValue(barcodeWithoutChecksum.charAt(i));

			if (i % 2 == 0)
			{
				// Odd positions (1st, 3rd, 5th, etc.)
				oddSum += digit;
			}
			else
			{
				// Even positions (2nd, 4th, 6th, etc.)
				evenSum += digit;
			}
		}

		// Multiply the even positions' sum by 3
		evenSum *= 3;

		// Total sum
		int totalSum = oddSum + evenSum;

		// Calculate check digit (nearest multiple of 10 - total sum % 10)
		int nearestTen = (int)Math.ceil(totalSum / 10.0) * 10;
		return nearestTen - totalSum;
	}

	public Optional<BigDecimal> getWeightInKg() {return Optional.ofNullable(weightInKg);}
}
