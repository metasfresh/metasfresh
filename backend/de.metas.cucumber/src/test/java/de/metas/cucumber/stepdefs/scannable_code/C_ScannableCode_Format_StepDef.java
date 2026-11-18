package de.metas.cucumber.stepdefs.scannable_code;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.ScannableCodeFormatQuery;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.util.time.PatternedDateTimeFormatter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps to define scannable code formats and to check how a scanned code is parsed with them.
 * <p>
 * Intended use: features that prove a format reads the right values from real or look-alike scanned strings
 * (private customer features that run against the delivered strings).
 * <p>
 * Step {@code metasfresh contains C_ScannableCode_Format:} columns: {@code Identifier}, {@code Name}.
 * <p>
 * Step {@code metasfresh contains C_ScannableCode_Format_Part:} columns: {@code C_ScannableCode_Format_ID.Identifier} (the format),
 * {@code StartNo}, {@code OPT.EndNo} (empty = open end), {@code DataType} (the reference list code, e.g. {@code PRODUCT_CODE}, {@code PACK_ID}),
 * {@code OPT.ConstantValue}, {@code OPT.DateFormat} (pattern, e.g. {@code yyMMdd}, stored in {@code DataFormat}).
 * <p>
 * Step {@code parse scanned code and expect:} columns: {@code ScannedCode}, {@code OPT.ProductNo}, {@code OPT.BestBeforeDate} (yyyy-MM-dd),
 * {@code OPT.LotNo}, {@code OPT.PackId}, {@code OPT.Matches} (default {@code true}; {@code false} = the result is not a custom code, or no reader recognizes the code at all).
 * An {@code OPT.} column that is present and empty expects the value to be absent.
 */
@RequiredArgsConstructor
public class C_ScannableCode_Format_StepDef
{
	/**
	 * Marks the formats created by these steps, so that they can be deleted again after the scenario.
	 */
	private static final String DESCRIPTION_CUCUMBER = "created by cucumber step C_ScannableCode_Format_StepDef";

	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	private final ScannableCodeFormatService formatService = SpringContextHolder.instance.getBean(ScannableCodeFormatService.class);
	@NonNull private final C_ScannableCode_Format_StepDefData formatTable;

	/**
	 * A format and its parts are created together ({@link ScannableCodeFormatService#create}), so the names wait here until the parts step.
	 */
	private final Map<String, String> pendingFormatNames = new LinkedHashMap<>();

	@Given("metasfresh contains C_ScannableCode_Format:")
	public void createFormats(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> pendingFormatNames.put(
				row.getAsIdentifier().getAsString(),
				row.getAsString(I_C_ScannableCode_Format.COLUMNNAME_Name)));
	}

	@And("metasfresh contains C_ScannableCode_Format_Part:")
	public void createParts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.groupBy(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID + ".Identifier")
				.forEach(this::createFormatWithParts);
	}

	private void createFormatWithParts(@NonNull final String formatIdentifier, @NonNull final DataTableRows partRows)
	{
		final String name = pendingFormatNames.remove(formatIdentifier);
		assertThat(name).as("format with identifier " + formatIdentifier + " (declared by step 'metasfresh contains C_ScannableCode_Format:')").isNotNull();

		final ImmutableList<ScannableCodeFormatCreateRequest.Part> parts = partRows.stream()
				.map(C_ScannableCode_Format_StepDef::toPart)
				.collect(ImmutableList.toImmutableList());

		final ScannableCodeFormat format = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name(name)
				.description(DESCRIPTION_CUCUMBER)
				.parts(parts)
				.build());

		formatTable.putOrReplace(formatIdentifier, InterfaceWrapperHelper.load(format.getId().getRepoId(), I_C_ScannableCode_Format.class));
	}

	private static ScannableCodeFormatCreateRequest.Part toPart(@NonNull final DataTableRow row)
	{
		return ScannableCodeFormatCreateRequest.Part.builder()
				.startPosition(row.getAsInt(I_C_ScannableCode_Format_Part.COLUMNNAME_StartNo))
				.endPosition(row.getAsOptionalInt(I_C_ScannableCode_Format_Part.COLUMNNAME_EndNo).orElse(0)) // 0 = open end
				.type(ScannableCodeFormatPartType.ofCode(row.getAsString(I_C_ScannableCode_Format_Part.COLUMNNAME_DataType)))
				.constantValue(row.getAsOptionalString(I_C_ScannableCode_Format_Part.COLUMNNAME_ConstantValue).orElse(null))
				.dateFormat(row.getAsOptionalString("DateFormat").map(PatternedDateTimeFormatter::ofPattern).orElse(null))
				.build();
	}

	/**
	 * Active formats are tried first by {@link HUQRCodesService#parse}, so they must not outlive the scenario.
	 */
	@After
	public void deleteCreatedFormats()
	{
		pendingFormatNames.clear();
		formatService.delete(ScannableCodeFormatQuery.builder().description(DESCRIPTION_CUCUMBER).build());
	}

	@Given("parse scanned code and expect:")
	public void parseScannedCodeAndExpect(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::parseAndExpect);
	}

	private void parseAndExpect(@NonNull final DataTableRow row)
	{
		final String scannedCodeStr = row.getAsString("ScannedCode");
		final boolean matches = row.getAsOptionalBoolean("Matches").orElseTrue();

		final IHUQRCode qrCode;
		try
		{
			qrCode = huQRCodesService.parse(ScannedCode.ofString(scannedCodeStr));
		}
		catch (final AdempiereException e)
		{
			// no reader (custom format or other) recognizes the code: that is "not a custom code" too
			assertThat(matches).as("custom code expected but parse failed (" + e.getMessage() + "): " + scannedCodeStr).isFalse();
			return;
		}

		if (!matches)
		{
			assertThat(qrCode).as("not a custom code: " + scannedCodeStr).isNotInstanceOf(CustomHUQRCode.class);
			return;
		}

		assertThat(qrCode).as("custom code: " + scannedCodeStr).isInstanceOf(CustomHUQRCode.class);
		final CustomHUQRCode custom = (CustomHUQRCode)qrCode;

		row.getAsOptionalString("ProductNo").ifPresent(expected -> assertThat(custom.getProductNo().orElse("")).as("ProductNo of " + scannedCodeStr).isEqualTo(expected));
		row.getAsOptionalString("LotNo").ifPresent(expected -> assertThat(custom.getLotNumber().orElse("")).as("LotNo of " + scannedCodeStr).isEqualTo(expected));
		row.getAsOptionalString("PackId").ifPresent(expected -> assertThat(custom.getPackId().orElse("")).as("PackId of " + scannedCodeStr).isEqualTo(expected));
		row.getAsOptionalString("BestBeforeDate").ifPresent(expected -> {
			final LocalDate expectedDate = expected.trim().isEmpty() ? null : LocalDate.parse(expected);
			assertThat(custom.getBestBeforeDate().orElse(null)).as("BestBeforeDate of " + scannedCodeStr).isEqualTo(expectedDate);
		});
	}
}
