package de.metas.ean13;

import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.gs1.ean13.EAN13ProductCodes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EAN13ProductCodesTest
{
	private static EAN13 ean13(String string)
	{
		return EAN13.fromString(string).orElseThrow();
	}

	@SuppressWarnings("SameParameterValue")
	private static EAN13ProductCodes ean13ProductCodes(final String defaultCode)
	{
		return EAN13ProductCodes.builder()
				.productValue("XXXX")
				.defaultCode(EAN13ProductCode.ofString(defaultCode))
				.build();
	}

	@Nested
	class isValidProductNo
	{
		@Test
		void standardCode()
		{
			final EAN13ProductCodes ean13ProductCodes = ean13ProductCodes("404961230");
			Assertions.assertThat(ean13ProductCodes.isValidProductNo(ean13("7614049612303"), null)).isTrue();
			Assertions.assertThat(ean13ProductCodes.isValidProductNo(ean13("5946644598983"), null)).isFalse();
		}

		@Test
		void variableWeight_prefix28()
		{
			final EAN13ProductCodes ean13ProductCodes = ean13ProductCodes("59414");
			Assertions.assertThat(ean13ProductCodes.isValidProductNo(ean13("2859414004825"), null)).isTrue();
			Assertions.assertThat(ean13ProductCodes.isValidProductNo(ean13("2800027002616"), null)).isFalse();
		}

		@Test
		void internalUseOrVariableMeasure_prefix29()
		{
			final EAN13ProductCodes ean13ProductCodes = ean13ProductCodes("1234");
			Assertions.assertThat(ean13ProductCodes.isValidProductNo(ean13("2912345005009"), null)).isTrue();
			Assertions.assertThat(ean13ProductCodes.isValidProductNo(ean13("2948882005745"), null)).isFalse();
		}
	}
}