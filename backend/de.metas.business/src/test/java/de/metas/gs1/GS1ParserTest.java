package de.metas.gs1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class GS1ParserTest
{
	private static GS1Element element(GS1ApplicationIdentifier ai, String valueStr)
	{
		return GS1Element.builder()
				.identifier(ai)
				.key(ai.getKey())
				.value(valueStr)
				.build();
	}

	@SuppressWarnings("SameParameterValue")
	private static GS1Element element(GS1ApplicationIdentifier ai, BigDecimal valueBD)
	{
		return GS1Element.builder()
				.identifier(ai)
				.key(ai.getKey())
				.value(valueBD)
				.build();
	}

	@SuppressWarnings("SameParameterValue")
	private static GS1Element element(GS1ApplicationIdentifier ai, LocalDateTime date)
	{
		return GS1Element.builder()
				.identifier(ai)
				.key(ai.getKey())
				.value(date)
				.build();
	}

	@Nested
	class parseElements
	{
		@Test
		void sscc()
		{
			final GS1Elements elements = GS1Parser.parseElements("00000000000010026083");
			assertThat(elements.toList())
					.contains(
							element(GS1ApplicationIdentifier.SSCC, "000000000010026083")
					);
		}

		@Test
		void gtin_weightInKg_bestBeforeDate()
		{
			final GS1Elements elements = GS1Parser.parseElements("0197311876341811310300752015170809");
			assertThat(elements.toList())
					.contains(
							element(GS1ApplicationIdentifier.GTIN, "97311876341811"),
							element(GS1ApplicationIdentifier.ITEM_NET_WEIGHT_KG, new BigDecimal("7.520")),
							element(GS1ApplicationIdentifier.BEST_BEFORE_DATE, LocalDateTime.parse("2017-08-09T00:00"))
					);
		}
	}
}