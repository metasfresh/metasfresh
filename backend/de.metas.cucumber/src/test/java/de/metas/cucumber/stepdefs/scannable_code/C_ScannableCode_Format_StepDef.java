package de.metas.cucumber.stepdefs.scannable_code;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.scannable_code.ScannedCode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.cache.CacheMgt;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;

import java.time.LocalDate;
import java.util.HashMap;
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
 * {@code OPT.LotNo}, {@code OPT.PackId}, {@code OPT.Matches} (default {@code true}; {@code false} = the result is not a custom code).
 * An {@code OPT.} column that is present and empty expects the value to be absent.
 */
public class C_ScannableCode_Format_StepDef
{
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);

	private final Map<String, Integer> formatIdsByIdentifier = new HashMap<>();

	@Given("metasfresh contains C_ScannableCode_Format:")
	public void createFormats(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createFormat);
		resetFormatsCache();
	}

	private void createFormat(@NonNull final DataTableRow row)
	{
		final I_C_ScannableCode_Format record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_ScannableCode_Format.class);
		record.setName(row.getAsString(I_C_ScannableCode_Format.COLUMNNAME_Name));
		InterfaceWrapperHelper.saveRecord(record);

		formatIdsByIdentifier.put(row.getAsIdentifier().getAsString(), record.getC_ScannableCode_Format_ID());
	}

	@And("metasfresh contains C_ScannableCode_Format_Part:")
	public void createParts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPart);
		resetFormatsCache();
	}

	private void createPart(@NonNull final DataTableRow row)
	{
		final String formatIdentifier = row.getAsIdentifier(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID).getAsString();
		final Integer formatId = formatIdsByIdentifier.get(formatIdentifier);
		assertThat(formatId).as("format with identifier " + formatIdentifier).isNotNull();

		final I_C_ScannableCode_Format_Part record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_ScannableCode_Format_Part.class);
		record.setC_ScannableCode_Format_ID(formatId);
		record.setStartNo(row.getAsInt(I_C_ScannableCode_Format_Part.COLUMNNAME_StartNo));
		row.getAsOptionalInt(I_C_ScannableCode_Format_Part.COLUMNNAME_EndNo).ifPresent(record::setEndNo);
		record.setDataType(row.getAsString(I_C_ScannableCode_Format_Part.COLUMNNAME_DataType));
		row.getAsOptionalString(I_C_ScannableCode_Format_Part.COLUMNNAME_ConstantValue).ifPresent(record::setConstantValue);
		row.getAsOptionalString("DateFormat").ifPresent(record::setDataFormat);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * The format repository caches all formats; a saved record resets it, but the reset event may not be processed in time.
	 */
	private static void resetFormatsCache()
	{
		CacheMgt.get().reset(I_C_ScannableCode_Format.Table_Name);
		CacheMgt.get().reset(I_C_ScannableCode_Format_Part.Table_Name);
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

		final IHUQRCode qrCode = huQRCodesService.parse(ScannedCode.ofString(scannedCodeStr));

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
