package de.metas.cucumber.stepdefs.scannable_code;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.ScannableCodeFormatQuery;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.util.time.PatternedDateTimeFormatter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ad.dao.IQueryBL;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps to define scannable code formats and to check how a scanned code is parsed with them.
 * <p>
 * Step {@code metasfresh contains C_ScannableCode_Format:} columns: {@code Identifier} (required), {@code Name} (required),
 * {@code IsUnrelated} (optional, default {@code false}; {@code true} = a stand-in for a format that exists independently of the scenario, e.g. one added by a customer migration).
 * <p>
 * Step {@code metasfresh contains C_ScannableCode_Format_Part:} columns, all plain names (no {@code .Identifier} suffix):
 * required: {@code C_ScannableCode_Format_ID} (the format identifier), {@code StartNo}, {@code DataType} (the reference list code, e.g. {@code PRODUCT_CODE}, {@code PACK_ID});
 * optional: {@code EndNo} (empty or {@code 0} = open end), {@code ConstantValue}, {@code DateFormat} (pattern, e.g. {@code yyMMdd}, stored in {@code DataFormat}).
 * <p>
 * <p>
 * Step {@code parse scanned code and expect:} parses with the scenario's own formats only: while it runs, every other active format (an unrelated one,
 * or one that already exists in the database, e.g. from a migration) is deactivated and afterwards restored.
 * Formats are global ({@link HUQRCodesService#parse} tries all active ones, the first that reads the code wins), so otherwise the result would depend on the database's content.
 * <p>
 * Step {@code parse scanned code and expect:} columns: required: {@code ScannedCode};
 * optional: {@code IsMatching} (default {@code true}; {@code false} = the result is not a custom code, or no reader recognizes the code at all),
 * {@code ProductNo}, {@code LotNo}, {@code PackId}, {@code BestBeforeDate} (yyyy-MM-dd).
 * <p>
 * An optional expectation column that is missing <b>or has an empty cell</b> is not checked: Cucumber passes empty cells as {@code null}
 * ({@code ArgumentMatcher.emptyCellsToNull}), which {@code DataTableRow#getAsOptionalString} reports as absent.
 * So an empty cell can not assert "no value"; write the expected value explicitly.
 * <p>
 * Example:
 * <pre>
 * Given metasfresh contains C_ScannableCode_Format:
 *   | Identifier | Name       |
 *   | fmt        | label_2026 |
 * And metasfresh contains C_ScannableCode_Format_Part:
 *   | C_ScannableCode_Format_ID | StartNo | EndNo | DataType         | DateFormat |
 *   | fmt                       | 1       | 4     | PRODUCT_CODE     |            |
 *   | fmt                       | 5       | 10    | BEST_BEFORE_DATE | yyMMdd     |
 *   | fmt                       | 11      | 0     | PACK_ID          |            |
 * Then parse scanned code and expect:
 *   | ScannedCode   | ProductNo | BestBeforeDate | PackId | IsMatching |
 *   | P10026113077  | P100      | 2026-11-30     | 77     | true       |
 * </pre>
 */
public class C_ScannableCode_Format_StepDef
{
	/**
	 * Marks the formats created by these steps, so that they can be deleted again after the scenario.
	 */
	private static final String DESCRIPTION_CUCUMBER = "created by cucumber step C_ScannableCode_Format_StepDef";

	/**
	 * Marks the formats created as {@code IsUnrelated}: they are active during the scenario, but are not "the scenario's formats".
	 */
	private static final String DESCRIPTION_CUCUMBER_UNRELATED = "created by cucumber step C_ScannableCode_Format_StepDef (unrelated to the scenario)";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final ScannableCodeFormatService formatService = SpringContextHolder.instance.getBean(ScannableCodeFormatService.class);

	/**
	 * A format and its parts are created together ({@link ScannableCodeFormatService#create}), so the names wait here until the parts step.
	 */
	private final Map<String, String> pendingFormatNames = new LinkedHashMap<>();
	private final Set<String> unrelatedFormatIdentifiers = new HashSet<>();

	/**
	 * Declares the formats (identifier + {@code Name}); they are created by the parts step.
	 *
	 * @see #createParts(DataTable)
	 */
	@Given("metasfresh contains C_ScannableCode_Format:")
	public void createFormats(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String identifier = row.getAsIdentifier().getAsString();
			pendingFormatNames.put(identifier, row.getAsString(I_C_ScannableCode_Format.COLUMNNAME_Name));
			if (row.getAsOptionalBoolean("IsUnrelated").orElseFalse())
			{
				unrelatedFormatIdentifiers.add(identifier);
			}
		});
	}

	/**
	 * Creates each declared format together with its parts.
	 *
	 * @see ScannableCodeFormatService#create
	 * @see #createFormats(DataTable)
	 */
	@And("metasfresh contains C_ScannableCode_Format_Part:")
	public void createParts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.groupBy(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID)
				.forEach(this::createFormatWithParts);
	}

	private void createFormatWithParts(@NonNull final String formatIdentifier, @NonNull final DataTableRows partRows)
	{
		final String name = pendingFormatNames.remove(formatIdentifier);
		assertThat(name).as("format with identifier " + formatIdentifier + " (declared by step 'metasfresh contains C_ScannableCode_Format:')").isNotNull();

		final ImmutableList<ScannableCodeFormatCreateRequest.Part> parts = partRows.stream()
				.map(C_ScannableCode_Format_StepDef::toPart)
				.collect(ImmutableList.toImmutableList());

		formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name(name)
				.description(unrelatedFormatIdentifiers.remove(formatIdentifier) ? DESCRIPTION_CUCUMBER_UNRELATED : DESCRIPTION_CUCUMBER)
				.parts(parts)
				.build());
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
		unrelatedFormatIdentifiers.clear();
		formatService.delete(ScannableCodeFormatQuery.builder().description(DESCRIPTION_CUCUMBER).build());
		formatService.delete(ScannableCodeFormatQuery.builder().description(DESCRIPTION_CUCUMBER_UNRELATED).build());
	}

	/**
	 * Parses each {@code ScannedCode} and checks whether a format recognizes it ({@code IsMatching}) and the expected values.
	 *
	 * @see HUQRCodesService#parse
	 */
	@Given("parse scanned code and expect:")
	public void parseScannedCodeAndExpect(@NonNull final DataTable dataTable)
	{
		final List<I_C_ScannableCode_Format> deactivated = deactivateFormatsNotOfTheScenario();
		try
		{
			DataTableRows.of(dataTable).forEach(this::parseAndExpect);
		}
		finally
		{
			reactivate(deactivated);
		}
	}

	/**
	 * Makes the scenario's own formats the only active ones: a format that is not one of the scenario's (e.g. added by a migration) could read the same codes
	 * and, being tried first, change the result. Test isolation only; the expectations are not touched.
	 */
	private List<I_C_ScannableCode_Format> deactivateFormatsNotOfTheScenario()
	{
		final List<I_C_ScannableCode_Format> deactivated = new ArrayList<>();
		queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.filter(format -> !DESCRIPTION_CUCUMBER.equals(format.getDescription()))
				.forEach(format -> {
					format.setIsActive(false);
					InterfaceWrapperHelper.save(format);
					deactivated.add(format);
				});
		return deactivated;
	}

	private static void reactivate(@NonNull final List<I_C_ScannableCode_Format> formats)
	{
		formats.forEach(format -> {
			format.setIsActive(true);
			InterfaceWrapperHelper.save(format);
		});
	}

	private void parseAndExpect(@NonNull final DataTableRow row)
	{
		final String scannedCodeStr = row.getAsString("ScannedCode");
		final boolean isMatching = row.getAsOptionalBoolean("IsMatching").orElseTrue();

		final IHUQRCode qrCode;
		try
		{
			qrCode = huQRCodesService.parse(ScannedCode.ofString(scannedCodeStr));
		}
		catch (final AdempiereException e)
		{
			// no reader (custom format or other) recognizes the code: that is "not a custom code" too
			if (isMatching)
			{
				throw new AssertionError("custom code expected but parse failed: " + scannedCodeStr, e);
			}
			return;
		}

		if (!isMatching)
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
			// an empty cell never reaches here: Cucumber passes it as null, i.e. "not checked"
			assertThat(custom.getBestBeforeDate().orElse(null)).as("BestBeforeDate of " + scannedCodeStr).isEqualTo(LocalDate.parse(expected));
		});
	}
}
