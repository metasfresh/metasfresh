package de.metas.scannable_code.format.repository;

import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.util.time.PatternedDateTimeFormatter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Open end of a part ({@code C_ScannableCode_Format_Part.EndNo} empty = 0): the loader computes the end of the part.
 */
class ScannableCodeFormatRepositoryTest
{
	private ScannableCodeFormatRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new ScannableCodeFormatRepository();
	}

	private static ScannableCodeFormatCreateRequest.Part part(final int start, final int end, final ScannableCodeFormatPartType type)
	{
		return ScannableCodeFormatCreateRequest.Part.builder().startPosition(start).endPosition(end).type(type).build();
	}

	private static ScannableCodeFormatCreateRequest.Part constant(final int start, final int end, final String value)
	{
		return ScannableCodeFormatCreateRequest.Part.builder().startPosition(start).endPosition(end).type(ScannableCodeFormatPartType.Constant).constantValue(value).build();
	}

	/** (01) 1..4, GTIN 5..18, (11) 19..22, production date 23..28, (10) 29..32, lot 33..open */
	private ScannableCodeFormat createLabelFormat()
	{
		return repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("open end label")
				.parts(ImmutableList.of(
						constant(1, 4, "(01)"),
						part(5, 18, ScannableCodeFormatPartType.GTIN),
						constant(19, 22, "(11)"),
						ScannableCodeFormatCreateRequest.Part.builder().startPosition(23).endPosition(28).type(ScannableCodeFormatPartType.ProductionDate).dateFormat(PatternedDateTimeFormatter.ofPattern("yyMMdd")).build(),
						constant(29, 32, "(10)"),
						part(33, 0, ScannableCodeFormatPartType.LotNo)))
				.build());
	}

	private static String label(final String lot)
	{
		return "(01)04012345678901(11)250101(10)" + lot;
	}

	@Test
	void lastPartWithOpenEnd_readsLotsOfAnyLength()
	{
		final ScannableCodeFormat format = createLabelFormat();

		for (final String lot : new String[] { "1", "123456", "1234567", "AB12CD34EF56GH78IJ90" })
		{
			final ParsedScannedCode parsed = format.parse(ScannedCode.ofString(label(lot))).orElseThrow();
			assertThat(parsed.getLotNo()).isEqualTo(lot);
			assertThat(parsed.getProductionDate()).hasToString("2025-01-01");
		}
	}

	@Test
	void lastPartWithOpenEnd_noLotCharacter_notRecognized()
	{
		assertThat(createLabelFormat().parse(ScannedCode.ofString(label(""))).isPresent()).isFalse();
	}

	@Test
	void middlePartWithOpenEnd_endsWhereNextPartStarts()
	{
		// lot 1..open (ends at 6), constant "|" at 7, product code 8..open
		final ScannableCodeFormat format = repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("middle open")
				.parts(ImmutableList.of(
						part(1, 0, ScannableCodeFormatPartType.LotNo),
						constant(7, 7, "|"),
						part(8, 0, ScannableCodeFormatPartType.ProductCode)))
				.build());

		final ParsedScannedCode parsed = format.parse(ScannedCode.ofString("AB12CD|P-1000")).orElseThrow();
		assertThat(parsed.getLotNo()).isEqualTo("AB12CD");
		assertThat(parsed.getProductNo()).isEqualTo("P-1000");
	}

	@Test
	void partsGivenOutOfStartNoOrder_neighbourIsByStartNo()
	{
		final ScannableCodeFormat format = repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("out of order")
				.parts(ImmutableList.of(
						part(8, 0, ScannableCodeFormatPartType.ProductCode),
						part(1, 0, ScannableCodeFormatPartType.LotNo),
						constant(7, 7, "|")))
				.build());

		final ParsedScannedCode parsed = format.parse(ScannedCode.ofString("AB12CD|P-1000")).orElseThrow();
		assertThat(parsed.getLotNo()).isEqualTo("AB12CD");
		assertThat(parsed.getProductNo()).isEqualTo("P-1000");
	}

	@Test
	void filledEnd_staysExact()
	{
		final ScannableCodeFormat format = repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("filled")
				.parts(ImmutableList.of(part(1, 6, ScannableCodeFormatPartType.LotNo)))
				.build());

		assertThat(format.parse(ScannedCode.ofString("1234567")).orElseThrow().getLotNo()).isEqualTo("123456");
		assertThat(format.parse(ScannedCode.ofString("12345")).isPresent()).isFalse();
	}

	@Test
	void openEndSharingStartNoWithNextPart_isInvalid()
	{
		assertThatThrownBy(() -> repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("duplicate start")
				.parts(ImmutableList.of(
						part(1, 0, ScannableCodeFormatPartType.LotNo),
						part(1, 3, ScannableCodeFormatPartType.ProductCode)))
				.build()))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void negativeEnd_isInvalid()
	{
		assertThatThrownBy(() -> repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("negative end")
				.parts(ImmutableList.of(part(3, -1, ScannableCodeFormatPartType.LotNo)))
				.build()))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void loadAll_skipsAnInvalidFormat_andKeepsTheValidOne()
	{
		// valid format (all ends filled) created through the normal path
		repository.create(ScannableCodeFormatCreateRequest.builder()
				.name("valid format")
				.parts(ImmutableList.of(
						constant(1, 4, "(01)"),
						part(5, 18, ScannableCodeFormatPartType.GTIN),
						constant(19, 22, "(11)"),
						part(23, 28, ScannableCodeFormatPartType.Ignored),
						constant(29, 32, "(10)"),
						part(33, 39, ScannableCodeFormatPartType.LotNo)))
				.build());

		// broken format: EndNo (2) < StartNo (5) -- cannot be built
		final I_C_ScannableCode_Format broken = InterfaceWrapperHelper.newInstance(I_C_ScannableCode_Format.class);
		broken.setName("broken format");
		InterfaceWrapperHelper.saveRecord(broken);
		final I_C_ScannableCode_Format_Part brokenPart = InterfaceWrapperHelper.newInstance(I_C_ScannableCode_Format_Part.class);
		brokenPart.setC_ScannableCode_Format_ID(broken.getC_ScannableCode_Format_ID());
		brokenPart.setStartNo(5);
		brokenPart.setEndNo(2);
		brokenPart.setDataType(ScannableCodeFormatPartType.LotNo.getCode());
		InterfaceWrapperHelper.saveRecord(brokenPart);

		final ScannableCodeFormatsCollection all = repository.getAll();

		assertThat(all.parse(ScannedCode.ofString(label("7654321"))).orElseThrow().getLotNo()).isEqualTo("7654321");
		assertThat(all.toList()).extracting(ScannableCodeFormat::getName).containsExactly("valid format");
	}
}
