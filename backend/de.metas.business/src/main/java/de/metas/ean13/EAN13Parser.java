package de.metas.ean13;

import de.metas.i18n.ExplainedOptional;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

class EAN13Parser
{
	public static ExplainedOptional<EAN13> parse(@NonNull final String barcode)
	{
		//
		// Check if the barcode length is exactly 13
		if (barcode.length() != 13)
		{
			return ExplainedOptional.emptyBecause("Invalid barcode length. It must be 13 digits.");
		}

		//
		// Validate the check digit
		final int checksum = Character.getNumericValue(barcode.charAt(12)); // Checksum digit (C)
		final int checksumExpected = computeChecksum(barcode.substring(0, 12));
		if (checksumExpected != checksum)
		{
			return ExplainedOptional.emptyBecause("Invalid checksum. Expected '" + checksumExpected + "' but got '" + checksum + "'.");
		}

		final EAN13Prefix prefix = EAN13Prefix.extractFromBarcode(barcode);

		// 28 - Variable-Weight barcodes
		if (prefix.equals(EAN13Prefix.VariableWeight))
		{
			return ExplainedOptional.of(parseVariableWeight(barcode, prefix, checksum));
		}
		// 29 - Internal Use / Variable measure
		if (prefix.equals(EAN13Prefix.InternalUseOrVariableMeasure))
		{
			return ExplainedOptional.of(parseInternalUseOrVariableMeasure(barcode, prefix, checksum));
		}
		// Parse regular product codes for other prefixes
		else
		{
			return ExplainedOptional.of(parseStandardProduct(barcode, prefix, checksum));
		}
	}

	private static int computeChecksum(final String barcodeWithoutChecksum)
	{
		int oddSum = 0;
		int evenSum = 0;

		// Loop through barcode without the checksum
		for (int i = 0; i < barcodeWithoutChecksum.length(); i++)
		{
			final int digit = Character.getNumericValue(barcodeWithoutChecksum.charAt(i));

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
		final int totalSum = oddSum + evenSum;

		// Calculate check digit (nearest multiple of 10 - total sum % 10)
		final int nearestTen = (int)Math.ceil(totalSum / 10.0) * 10;
		return nearestTen - totalSum;
	}

	private static EAN13 parseVariableWeight(@NonNull final String barcode, @NonNull final EAN13Prefix prefix, final int checksum)
	{
		final EAN13ProductCode productNo = EAN13ProductCode.ofString(barcode.substring(2, 7)); // 5 digits for article code (AAAAA)

		final String weightStr = barcode.substring(7, 12); // 5 digits for weight (GGGGG)

		// Interpret the weight/measure (assume it's in grams or kilograms)
		final BigDecimal weightInKg = new BigDecimal(weightStr).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP);

		return EAN13.builder()
				.barcode(barcode)
				.prefix(prefix)
				.productNo(productNo)
				.weightInKg(weightInKg)
				.checksum(checksum)
				.build();
	}

	private static EAN13 parseInternalUseOrVariableMeasure(@NonNull final String barcode, @NonNull final EAN13Prefix prefix, final int checksum)
	{
		// 4 digits for article code (IIII),
		// The digit at index 6 does not belong to the product code. It can be used for other purposes. For the time being it's ignored.
		// see https://www.gs1.org/docs/barcodes/SummaryOfGS1MOPrefixes20-29.pdf, page 81 (2.71 GS1 Switzerland)
		final EAN13ProductCode productNo = EAN13ProductCode.ofString(barcode.substring(2, 6));

		final String weightStr = barcode.substring(7, 12); // 5 digits for weight (GGGGG)

		// Interpret the weight/measure (assume it's in grams or kilograms)
		final BigDecimal weightInKg = new BigDecimal(weightStr).divide(new BigDecimal(1000), 3, RoundingMode.HALF_UP);

		return EAN13.builder()
				.barcode(barcode)
				.prefix(prefix)
				.productNo(productNo)
				.weightInKg(weightInKg)
				.checksum(checksum)
				.build();
	}

	private static EAN13 parseStandardProduct(@NonNull final String barcode, @NonNull final EAN13Prefix prefix, final int checksum)
	{
		final String manufacturerAndProductCode = barcode.substring(3, 12);
		final EAN13ProductCode productNo = EAN13ProductCode.ofString(manufacturerAndProductCode);
		return EAN13.builder()
				.barcode(barcode)
				.prefix(prefix)
				.productNo(productNo)
				.checksum(checksum)
				.build();
	}

}
