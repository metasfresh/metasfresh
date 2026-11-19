package de.metas.scannable_code.format;

import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.ScannedCode;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScannableCodeFormatTest
{
	@Test
	void happyCase()
	{
		// [1..4]             [5...10]     [11....18]    [19..24]    [25..30]
		//  0000               000001       11111111      122222      222223
		//  1234               567890       12345678      901234      567890
		// ------------------------------------------------------------------
		//  1000               099999       00000123      250403      250410
		// Article No. (4x) + Weight (6x) + Batch (8x) + Date (6x) + Expiry Date (6x)

		final ScannableCodeFormat format = ScannableCodeFormat.builder()
				.id(ScannableCodeFormatId.ofRepoId(1))
				.name("Test")
				.parts(ImmutableList.of(
						ScannableCodeFormatPart.builder().startPosition(1).endPosition(4).type(ScannableCodeFormatPartType.ProductCode).build(),
						ScannableCodeFormatPart.builder().startPosition(5).endPosition(10).type(ScannableCodeFormatPartType.WeightInKg).build(),
						ScannableCodeFormatPart.builder().startPosition(11).endPosition(18).type(ScannableCodeFormatPartType.LotNo).build(),
						ScannableCodeFormatPart.builder().startPosition(19).endPosition(24).type(ScannableCodeFormatPartType.ProductionDate).build(),
						ScannableCodeFormatPart.builder().startPosition(25).endPosition(30).type(ScannableCodeFormatPartType.BestBeforeDate).build()
				))
				.build();

		final ScannedCode scannedCode = ScannedCode.ofString("100009999900000123250403250410");
		final ParsedScannedCode parsedScannedCode = format.parse(scannedCode).orElseThrow();
		assertThat(parsedScannedCode.getScannedCode()).isEqualTo(scannedCode);
		assertThat(parsedScannedCode.getProductNo()).isEqualTo("1000");
		assertThat(parsedScannedCode.getWeightKg()).isEqualTo("99.999");
		assertThat(parsedScannedCode.getLotNo()).isEqualTo("123");
		assertThat(parsedScannedCode.getBestBeforeDate()).isEqualTo("2025-04-10");
		assertThat(parsedScannedCode.getProductionDate()).isEqualTo("2025-04-03");
	}

	@Nested
	class format_with_constant_markers
	{
		private ScannableCodeFormat format;

		@BeforeEach
		void beforeEach()
		{
			// [1]  [234567]     [8] [901234]     [5] [67890123]      [4] [567890]
			//  A    593707       G   000384       C   05321124        E   000001
			this.format = ScannableCodeFormat.builder()
					.id(ScannableCodeFormatId.ofRepoId(1))
					.name("Test")
					.parts(ImmutableList.of(
							ScannableCodeFormatPart.builder().startPosition(1).endPosition(1).type(ScannableCodeFormatPartType.Constant).constantValue("A").build(),
							ScannableCodeFormatPart.builder().startPosition(2).endPosition(7).type(ScannableCodeFormatPartType.ProductCode).build(),
							//
							ScannableCodeFormatPart.builder().startPosition(8).endPosition(8).type(ScannableCodeFormatPartType.Constant).constantValue("G").build(),
							ScannableCodeFormatPart.builder().startPosition(9).endPosition(14).type(ScannableCodeFormatPartType.WeightInKg).build(),
							//
							ScannableCodeFormatPart.builder().startPosition(15).endPosition(15).type(ScannableCodeFormatPartType.Constant).constantValue("C").build(),
							ScannableCodeFormatPart.builder().startPosition(16).endPosition(23).type(ScannableCodeFormatPartType.LotNo).build(),
							//
							ScannableCodeFormatPart.builder().startPosition(24).endPosition(24).type(ScannableCodeFormatPartType.Constant).constantValue("E").build(),
							ScannableCodeFormatPart.builder().startPosition(25).endPosition(30).type(ScannableCodeFormatPartType.Ignored).build()
					))
					.build();

		}

		@Test
		void happyCase()
		{
			final ScannedCode scannedCode = ScannedCode.ofString("A593707G000384C05321124E000001");
			final ParsedScannedCode parsedScannedCode = format.parse(scannedCode).orElseThrow();
			assertThat(parsedScannedCode.getScannedCode()).isEqualTo(scannedCode);
			assertThat(parsedScannedCode.getProductNo()).isEqualTo("593707");
			assertThat(parsedScannedCode.getWeightKg()).isEqualTo("0.384");
			assertThat(parsedScannedCode.getLotNo()).isEqualTo("5321124");
		}

		@Test
		void invalidMarker()
		{
			final ScannedCode scannedCode = ScannedCode.ofString("A593707G000384C05321124X000001"); // remark "X" instead of "E"
			assertThatThrownBy(() -> format.parse(scannedCode).orElseThrow())
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid constant marker, expected `E` but was `X`");
		}
	}

	@Nested
	class open_end_last_part
	{
		// (01)          ProdCode(14)      (11)         yyMMdd   (10)       lot (open end)
		// 1..4          5..18           19..22       23..28   29..32     33..to the end
		private ScannableCodeFormat format()
		{
			return ScannableCodeFormat.builder()
					.id(ScannableCodeFormatId.ofRepoId(1))
					.name("Test")
					.parts(ImmutableList.of(
							ScannableCodeFormatPart.builder().startPosition(1).endPosition(4).type(ScannableCodeFormatPartType.Constant).constantValue("(01)").build(),
							ScannableCodeFormatPart.builder().startPosition(5).endPosition(18).type(ScannableCodeFormatPartType.ProductCode).build(),
							ScannableCodeFormatPart.builder().startPosition(19).endPosition(22).type(ScannableCodeFormatPartType.Constant).constantValue("(11)").build(),
							ScannableCodeFormatPart.builder().startPosition(23).endPosition(28).type(ScannableCodeFormatPartType.ProductionDate).build(),
							ScannableCodeFormatPart.builder().startPosition(29).endPosition(32).type(ScannableCodeFormatPartType.Constant).constantValue("(10)").build(),
							ScannableCodeFormatPart.builder().startPosition(33).endPosition(0).type(ScannableCodeFormatPartType.LotNo).build()
					))
					.build();
		}

		private String label(final String lot)
		{
			return "(01)04012345678901(11)250101(10)" + lot;
		}

		@Test
		void lotOf6Characters()
		{
			assertThat(format().parse(ScannedCode.ofString(label("123456"))).orElseThrow().getLotNo()).isEqualTo("123456");
		}

		@Test
		void lotOf7Characters()
		{
			assertThat(format().parse(ScannedCode.ofString(label("1234567"))).orElseThrow().getLotNo()).isEqualTo("1234567");
		}

		@Test
		void lotOf1Character()
		{
			assertThat(format().parse(ScannedCode.ofString(label("7"))).orElseThrow().getLotNo()).isEqualTo("7");
		}

		@Test
		void lotOf20AlphanumericCharacters()
		{
			assertThat(format().parse(ScannedCode.ofString(label("AB12CD34EF56GH78IJ90"))).orElseThrow().getLotNo()).isEqualTo("AB12CD34EF56GH78IJ90");
		}

		@Test
		void noLotCharacter_notRecognized()
		{
			assertThat(format().parse(ScannedCode.ofString(label(""))).isPresent()).isFalse();
		}

		@Test
		void leadingZerosStillStripped()
		{
			assertThat(format().parse(ScannedCode.ofString(label("012345"))).orElseThrow().getLotNo()).isEqualTo("12345");
		}
	}

	@Nested
	class pack_id_part
	{
		// Look-alike label: (01) GTIN(14) (91) productNo(7) (17) yyMMdd (10) lot(9) (92) pack ID (open end)
		// 1..2 3..16 17..18 19..25 26..27 28..33 34..35 36..44 45..46 47..to the end
		private static final String GS = "\u001d";

		private ScannableCodeFormatPart part(final int startPosition, final int endPosition, final ScannableCodeFormatPartType type)
		{
			return ScannableCodeFormatPart.builder().startPosition(startPosition).endPosition(endPosition).type(type).build();
		}

		private ScannableCodeFormatPart constantPart(final int startPosition, final int endPosition, final String constantValue)
		{
			return ScannableCodeFormatPart.builder().startPosition(startPosition).endPosition(endPosition).type(ScannableCodeFormatPartType.Constant).constantValue(constantValue).build();
		}

		private ScannableCodeFormat format(final ScannableCodeFormatPart... parts)
		{
			return ScannableCodeFormat.builder()
					.id(ScannableCodeFormatId.ofRepoId(1))
					.name("Test")
					.parts(ImmutableList.copyOf(parts))
					.build();
		}

		private ScannableCodeFormatPart productPart() {return part(19, 25, ScannableCodeFormatPartType.ProductCode);}
		private ScannableCodeFormatPart bestBeforePart() {return part(28, 33, ScannableCodeFormatPartType.BestBeforeDate);}
		private ScannableCodeFormatPart lotPart() {return part(36, 44, ScannableCodeFormatPartType.LotNo);}
		private ScannableCodeFormatPart packIdPart() {return part(47, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId);}

		private ScannableCodeFormat formatWithPackId()
		{
			return format(productPart(), bestBeforePart(), lotPart(), packIdPart());
		}

		private ScannableCodeFormat formatWithoutPackId()
		{
			return format(productPart(), bestBeforePart(), lotPart());
		}

		private String label(final String packId) {return "010950600013435291P0000421726110710A1/B002C392" + packId;}

		private String labelZ(final String packId) {return "010950600013435291P0000421726110710" + "0A1/B002C" + "92" + packId;}

		private static final String OLD_LABEL = "010950600013435291P0000421726110710A1/B002C3";

		@ParameterizedTest
		@ValueSource(strings = { "1", "2", "3", "10", "007" })
		void packIdIsRawSubstring(final String packId)
		{
			final ParsedScannedCode parsed = formatWithPackId().parse(ScannedCode.ofString(label(packId))).orElseThrow();
			assertThat(parsed.getProductNo()).isEqualTo("P000042");
			assertThat(parsed.getBestBeforeDate()).isEqualTo("2026-11-07");
			assertThat(parsed.getLotNo()).isEqualTo("A1/B002C3");
			assertThat(parsed.getPackId()).isEqualTo(packId);
		}

		@Test
		void zeroLedLot_lotStripped_packIdUntouched()
		{
			final ParsedScannedCode parsed = formatWithPackId().parse(ScannedCode.ofString(labelZ("2"))).orElseThrow();
			assertThat(parsed.getLotNo()).isEqualTo("A1/B002C");
			assertThat(parsed.getPackId()).isEqualTo("2");
		}

		@Test
		void oldLabelWithoutPackId_notRecognized()
		{
			assertThat(formatWithPackId().parse(ScannedCode.ofString(OLD_LABEL)).isPresent()).isFalse();
		}

		@Test
		void formatWithoutPackIdPart_packIdIsNull()
		{
			final ParsedScannedCode parsed = formatWithoutPackId().parse(ScannedCode.ofString(label("2"))).orElseThrow();
			assertThat(parsed.getLotNo()).isEqualTo("A1/B002C3");
			assertThat(parsed.getPackId()).isNull();
		}

		// CONSTANT guards (17 at 26-27, 10 at 34-35, 92 at 45-46) are listed first, so that they, not a later part, refuse a shifted code
		private ScannableCodeFormat formatWithGuards()
		{
			return format(constantPart(26, 27, "17"), constantPart(34, 35, "10"), constantPart(45, 46, "92"), productPart(), bestBeforePart(), lotPart(), packIdPart());
		}

		@Test
		void guardedFormat_matchesLabel()
		{
			assertThat(formatWithGuards().parse(ScannedCode.ofString(label("2"))).orElseThrow().getPackId()).isEqualTo("2");
		}

		@Test
		void guardedFormat_refusesShiftedCodeBecauseOfTheConstantGuard()
		{
			final String shifted = label("2").substring(0, 25) + "]" + label("2").substring(25, 44) + "]" + label("2").substring(44); // 49 characters, "]" at 26 and 46
			assertThat(shifted).hasSize(49);
			assertThatThrownBy(() -> formatWithGuards().parse(ScannedCode.ofString(shifted)).orElseThrow())
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid constant marker, expected `17` but was `]");
		}

		@Test
		void onlyTheConstantGuard92_refusesCodeWithoutMarker92()
		{
			final ScannableCodeFormat onlyGuard92 = format(constantPart(45, 46, "92"), productPart(), packIdPart());
			final String wrongMarker = label("2").substring(0, 44) + "XX" + "2"; // same length, `XX` instead of `92`; everything else parseable
			assertThatThrownBy(() -> onlyGuard92.parse(ScannedCode.ofString(wrongMarker)).orElseThrow())
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Invalid constant marker, expected `92` but was `XX`");
		}

		@Test
		void packIdIsNotTrimmed()
		{
			// leading blank only: ScannedCode itself trims the whole code, so a trailing blank never reaches the part
			final ParsedScannedCode parsed = formatWithPackId().parse(ScannedCode.ofString(label(" 2"))).orElseThrow();
			assertThat(parsed.getPackId()).isEqualTo(" 2");
		}

		// the same label as the scanner may send it: group separators (char 29) after the variable-length fields
		// (01)GTIN 1..16 | (91)productNo 17..25 | GS 26 | (17)yyMMdd 27..34 | (10)lot 35..45 | GS 46 | (92)packId 47..
		private ScannableCodeFormat format49()
		{
			return format(
					part(19, 25, ScannableCodeFormatPartType.ProductCode),
					part(29, 34, ScannableCodeFormatPartType.BestBeforeDate),
					part(37, 45, ScannableCodeFormatPartType.LotNo),
					part(49, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId));
		}

		private String label49(final String packId) {return "0109506000134352" + "91P000042" + GS + "17261107" + "10A1/B002C3" + GS + "92" + packId;}

		@ParameterizedTest
		@ValueSource(strings = { "1", "2", "3", "10" })
		void label49_packId(final String packId)
		{
			final ParsedScannedCode parsed = format49().parse(ScannedCode.ofString(label49(packId))).orElseThrow();
			assertThat(parsed.getProductNo()).isEqualTo("P000042");
			assertThat(parsed.getBestBeforeDate()).isEqualTo("2026-11-07");
			assertThat(parsed.getLotNo()).isEqualTo("A1/B002C3");
			assertThat(parsed.getPackId()).isEqualTo(packId);
		}

		@Test
		void label49_withoutFinalGroupSeparatorAndPackId_notRecognized()
		{
			final String code = "0109506000134352" + "91P000042" + GS + "17261107" + "10A1/B002C3";
			assertThat(format49().parse(ScannedCode.ofString(code)).isPresent()).isFalse();
		}
	}

	@Test
	void negativeEndPosition_isInvalid()
	{
		assertThatThrownBy(() -> ScannableCodeFormatPart.builder().startPosition(5).endPosition(-1).type(ScannableCodeFormatPartType.LotNo).build())
				.isInstanceOf(RuntimeException.class);
	}
}
