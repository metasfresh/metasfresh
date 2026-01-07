package de.metas.ean13;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.gs1.ean13.EAN13;
import de.metas.i18n.ExplainedOptional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EAN13Test
{
	@Test
	public void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(EAN13.ofString("2859414004825").orElseThrow());
	}

	private static void testSerializeDeserialize(final EAN13 ean13) throws JsonProcessingException
	{
		System.out.println("EAN13: " + ean13);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(ean13);
		System.out.println("JSON: " + json);

		final EAN13 ean13Deserialized = jsonObjectMapper.readValue(json, EAN13.class);
		System.out.println("EAN13 deserialized: " + ean13Deserialized);
		
		assertThat(ean13Deserialized).isEqualTo(ean13);
	}

	@Nested
	class parse
	{
		@Nested
		class VariableWeight_prefix28
		{
			@Test
			void happyCase()
			{
				final EAN13 ean13 = EAN13.ofString("2859414004825").get();

				assertThat(ean13.getPrefix().getAsString()).isEqualTo("28");
				assertThat(ean13.getProductNo().getAsString()).contains("59414");
				assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.482"));
			}

			@Test
			void happyCase2()
			{
				final EAN13 ean13 = EAN13.ofString("2800027002616").get();

				assertThat(ean13.getPrefix().getAsString()).isEqualTo("28");
				assertThat(ean13.getProductNo().getAsString()).contains("00027");
				assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.261"));
			}
		}

		@Nested
		class InternalUseOrVariableMeasure_prefix29
		{
			@Test
			void happyCase()
			{
				final ExplainedOptional<EAN13> result = EAN13.ofString("2912345005009");
				assertThat(result.isPresent()).isTrue();
				final EAN13 ean13 = result.get();
				assertThat(ean13.getPrefix().getAsString()).isEqualTo("29");
				assertThat(ean13.getProductNo().getAsString()).isEqualTo("1234");
				assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.500"));
				assertThat(ean13.getChecksum()).isEqualTo(9);

			}

			@Test
			void happyCase2()
			{
				final ExplainedOptional<EAN13> result = EAN13.ofString("2948882005745");
				assertThat(result.isPresent()).isTrue();
				final EAN13 qrCode = result.get();
				assertThat(qrCode.getPrefix().getAsString()).isEqualTo("29");
				assertThat(qrCode.getProductNo().getAsString()).isEqualTo("4888");
				assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("0.574"));
				assertThat(qrCode.getChecksum()).isEqualTo(5);
			}

			@Test
			void invalidChecksum()
			{
				final ExplainedOptional<EAN13> result = EAN13.ofString("2912345005004"); // Invalid checksum (last digit)
				assertThat(result.isPresent()).isFalse();
				assertThat(result.getExplanation().getDefaultValue()).contains("Invalid checksum");
			}

			@Test
			void invalidLength()
			{
				final ExplainedOptional<EAN13> result = EAN13.ofString("29123450050"); // Only 12 digits
				assertThat(result.isPresent()).isFalse();
				assertThat(result.getExplanation().getDefaultValue()).contains("Invalid barcode length");
			}
		}

		@Nested
		class StandardProductCodes
		{
			@Test
			void happyCase()
			{
				final ExplainedOptional<EAN13> result = EAN13.ofString("5901234123457");
				assertThat(result.isPresent()).isTrue();
				final EAN13 qrCode = result.get();
				assertThat(qrCode.getPrefix().getAsString()).isEqualTo("590");
				assertThat(qrCode.getProductNo().getAsString()).isEqualTo("123412345");
				assertThat(qrCode.getWeightInKg()).isEmpty();
				assertThat(qrCode.getChecksum()).isEqualTo(7);
			}

			@Test
			void happyCase2()
			{
				final ExplainedOptional<EAN13> result = EAN13.ofString("7617027667210");
				assertThat(result.isPresent()).isTrue();
				final EAN13 qrCode = result.get();
				assertThat(qrCode.getPrefix().getAsString()).isEqualTo("761");
				assertThat(qrCode.getProductNo().getAsString()).isEqualTo("702766721");
				assertThat(qrCode.getWeightInKg()).isEmpty();
				assertThat(qrCode.getChecksum()).isZero();
			}
		}
	}
}
