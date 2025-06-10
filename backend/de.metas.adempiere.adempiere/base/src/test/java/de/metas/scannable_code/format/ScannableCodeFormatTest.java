package de.metas.scannable_code.format;

import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.ScannedCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
						ScannableCodeFormatPart.builder().startPosition(19).endPosition(24).type(ScannableCodeFormatPartType.Ignored).build(),
						ScannableCodeFormatPart.builder().startPosition(25).endPosition(30).type(ScannableCodeFormatPartType.BestBeforeDate).build()
				))
				.build();

		final ScannedCode scannedCode = ScannedCode.ofString("100009999900000123250403250410");
		final ParsedScannedCode parsedScannedCode = format.parse(scannedCode).orElseThrow();
		assertThat(parsedScannedCode.getScannedCode()).isEqualTo(scannedCode);
		assertThat(parsedScannedCode.getProductNo()).isEqualTo("1000");
		assertThat(parsedScannedCode.getWeightKg()).isEqualTo("099999");
		assertThat(parsedScannedCode.getLotNo()).isEqualTo("00000123");
		assertThat(parsedScannedCode.getBestBeforeDate()).isEqualTo("2025-04-10");
	}
}