package de.metas.scannable_code.format;

import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.ScannedCode;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
}