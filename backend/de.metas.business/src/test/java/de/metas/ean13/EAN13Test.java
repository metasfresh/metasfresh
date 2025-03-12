package de.metas.ean13;

import de.metas.i18n.ExplainedOptional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EAN13Test
{
	@Nested
	class test_fromString_withPrefix28
	{
		@Test
		void happyCase()
		{
			final EAN13 ean13 = EAN13.fromString("2859414004825").get();

			assertThat(ean13.getPrefix()).contains(EAN13.PREFIX_VariableWeight);
			assertThat(ean13.getProductNo()).contains("59414");
			assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.482"));
		}

		@Test
		void happyCase2()
		{
			final EAN13 ean13 = EAN13.fromString("2800027002616").get();

			assertThat(ean13.getPrefix()).contains(EAN13.PREFIX_VariableWeight);
			assertThat(ean13.getProductNo()).contains("00027");
			assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.261"));
		}
	}

	@Nested
	class test_fromString_withPrefix29
	{
		@Test
		void happyCase()
		{
			final ExplainedOptional<EAN13> result = EAN13.fromString("2912345005009");
			assertThat(result.isPresent()).isTrue();
			final EAN13 qrCode = result.get();
			assertThat(qrCode.getPrefix()).isEqualTo("29");
			assertThat(qrCode.getProductNo()).isEqualTo("1234");
			assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("0.500"));
			assertThat(qrCode.getChecksum()).isEqualTo(9);

		}

		@Test
		void happyCase2()
		{
			final ExplainedOptional<EAN13> result = EAN13.fromString("2948882005745");
			assertThat(result.isPresent()).isTrue();
			final EAN13 qrCode = result.get();
			assertThat(qrCode.getPrefix()).isEqualTo("29");
			assertThat(qrCode.getProductNo()).isEqualTo("4888");
			assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("0.574"));
			assertThat(qrCode.getChecksum()).isEqualTo(5);

		}

		@Test
		void invalidChecksum()
		{
			final ExplainedOptional<EAN13> result = EAN13.fromString("2912345005004"); // Invalid checksum (last digit)
			assertThat(result.isPresent()).isFalse();
			System.out.println(result.getExplanation());
			assertThat(result.getExplanation().getDefaultValue()).contains("Invalid checksum");
		}

		@Test
		void invalidLength()
		{
			final ExplainedOptional<EAN13> result = EAN13.fromString("29123450050"); // Only 12 digits
			assertThat(result.isPresent()).isFalse();
			System.out.println(result.getExplanation());
			assertThat(result.getExplanation().getDefaultValue()).contains("Invalid barcode length");
		}
	}
	@Test
	void unsupportedPrefix()
	{
		final ExplainedOptional<EAN13> result = EAN13.fromString("2748882005741"); // Prefix 27 is not supported
		assertThat(result.isPresent()).isFalse();
		System.out.println(result.getExplanation());
		assertThat(result.getExplanation().getDefaultValue()).contains("Unsupported barcode prefix: 27");
	}
}
