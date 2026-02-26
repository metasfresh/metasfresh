package de.metas.gs1;

import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GS1ProductCodesCollectionTest
{
	private static EAN13 ean13(String string)
	{
		return EAN13.ofString(string).orElseThrow();
	}

	@SuppressWarnings("SameParameterValue")
	private static GS1ProductCodesCollection collection(final GS1ProductCodes defaultCodes)
	{
		return GS1ProductCodesCollection.builder()
				.productValue("XXXX")
				.defaultCodes(defaultCodes)
				.build();
	}

	@Nested
	class isValidProductNo
	{
		@Nested
		class standardCode
		{
			@Test
			void matchedBy_ean13productCode()
			{
				final GS1ProductCodesCollection collection = collection(
						GS1ProductCodes.builder()
								.ean13ProductCode(EAN13ProductCode.ofString("404961230"))
								.build()
				);
				Assertions.assertThat(collection.isValidProductNo(ean13("7614049612303"), null)).isTrue();
				Assertions.assertThat(collection.isValidProductNo(ean13("5946644598983"), null)).isFalse();
			}

			@Test
			void matchedBy_ean13()
			{
				final GS1ProductCodesCollection collection = collection(
						GS1ProductCodes.builder()
								.ean13(EAN13.ofString("7614049612303").orElseThrow())
								.build()
				);
				Assertions.assertThat(collection.isValidProductNo(ean13("7614049612303"), null)).isTrue();
				Assertions.assertThat(collection.isValidProductNo(ean13("5946644598983"), null)).isFalse();
			}

			@Test
			void matchedBy_gtin()
			{
				final GS1ProductCodesCollection collection = collection(
						GS1ProductCodes.builder()
								.gtin(GTIN.ofString("7614049612303"))
								.build()
				);
				Assertions.assertThat(collection.isValidProductNo(ean13("7614049612303"), null)).isTrue();
				Assertions.assertThat(collection.isValidProductNo(ean13("5946644598983"), null)).isFalse();
			}
		}

		@Test
		void variableWeight_prefix28()
		{
			final GS1ProductCodesCollection collection = collection(
					GS1ProductCodes.builder()
							.ean13ProductCode(EAN13ProductCode.ofString("59414"))
							.build()
			);
			Assertions.assertThat(collection.isValidProductNo(ean13("2859414004825"), null)).isTrue();
			Assertions.assertThat(collection.isValidProductNo(ean13("2800027002616"), null)).isFalse();
		}

		@Test
		void internalUseOrVariableMeasure_prefix29()
		{
			final GS1ProductCodesCollection collection = collection(
					GS1ProductCodes.builder()
							.ean13ProductCode(EAN13ProductCode.ofString("1234"))
							.build()
			);
			Assertions.assertThat(collection.isValidProductNo(ean13("2912345005009"), null)).isTrue();
			Assertions.assertThat(collection.isValidProductNo(ean13("2948882005745"), null)).isFalse();
		}
	}
}