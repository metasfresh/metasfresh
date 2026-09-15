package de.metas.cucumber.stepdefs.dataimport;

import de.metas.cucumber.stepdefs.C_DataImport_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.X_AD_ImpFormat;
import org.compiere.model.X_C_DataImport;
import org.compiere.util.DB;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for creating AD_ImpFormat + C_DataImport configs and importing CSV data
 * through the full {@link DataImportService} pipeline.
 */
@RequiredArgsConstructor
public class DataImport_StepDef
{
	private final DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	@NonNull private final AD_ImpFormat_StepDefData impFormatTable;
	@NonNull private final C_DataImport_StepDefData dataImportTable;

	/**
	 * Creates an AD_ImpFormat record (comma-separated, UTF-8) and its column rows.
	 *
	 * @cucumber.columns
	 * <b>ColumnName</b> — column name in the target import table (e.g. Value, Name, ProductType)<br>
	 * <b>DataType</b> — data type code: S=String, N=Number, D=Date, C=Constant, B=YesNo
	 */
	@Given("AD_ImpFormat {string} for table {string} with columns:")
	public void ad_ImpFormat_for_table_with_columns(
			@NonNull final String formatIdentifierStr,
			@NonNull final String tableName,
			@NonNull final DataTable dataTable)
	{
		createImpFormat(formatIdentifierStr, tableName, X_AD_ImpFormat.FORMATTYPE_CommaSeparated, 0, dataTable);
	}

	/**
	 * Creates an AD_ImpFormat record with configurable format type and header skip.
	 * <p>
	 * Format types: C=Comma, T=Tab, S=Semicolon, F=Fixed, X=XML
	 *
	 * @cucumber.columns
	 * <b>ColumnName</b> — column name in the target import table (e.g. Value, Name, ProductType)<br>
	 * <b>DataType</b> — data type code: S=String, N=Number, D=Date, C=Constant, B=YesNo
	 */
	@Given("AD_ImpFormat {string} for table {string} with format {string} and skipFirstNRows {int} and columns:")
	public void ad_ImpFormat_for_table_with_format_and_skip(
			@NonNull final String formatIdentifierStr,
			@NonNull final String tableName,
			@NonNull final String formatType,
			final int skipFirstNRows,
			@NonNull final DataTable dataTable)
	{
		createImpFormat(formatIdentifierStr, tableName, formatType, skipFirstNRows, dataTable);
	}

	private void createImpFormat(
			@NonNull final String identifierStr,
			@NonNull final String tableName,
			@NonNull final String formatType,
			final int skipFirstNRows,
			@NonNull final DataTable dataTable)
	{
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(identifierStr);

		final I_AD_ImpFormat format = InterfaceWrapperHelper.newInstance(I_AD_ImpFormat.class);
		format.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		format.setName(identifierStr + "_" + Instant.now());
		format.setAD_Table_ID(adTableDAO.retrieveTableId(tableName));
		format.setFormatType(formatType);
		format.setFileCharset(StandardCharsets.UTF_8.name().toLowerCase());
		format.setSkipFirstNRows(skipFirstNRows);
		format.setIsMultiLine(false);
		format.setIsManualImport(false);
		InterfaceWrapperHelper.saveRecord(format);

		// Clean up any auto-created or orphaned AD_ImpFormat_Row records for this format
		// before adding our specific column definitions
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_ID=" + format.getAD_ImpFormat_ID(),
				ITrx.TRXNAME_None);

		final AtomicInteger seqNo = new AtomicInteger(10);
		final AtomicInteger csvColumnNo = new AtomicInteger(1);
		DataTableRows.of(dataTable).forEach(row -> createImpFormatRow(format, tableName, row, seqNo.getAndAdd(10), csvColumnNo.getAndIncrement()));

		impFormatTable.putOrReplace(identifier, format);
	}

	private void createImpFormatRow(
			@NonNull final I_AD_ImpFormat format,
			@NonNull final String tableName,
			@NonNull final DataTableRow row,
			final int seqNo,
			final int csvColumnNo)
	{
		final String columnName = row.getAsString("ColumnName");
		final String dataType = row.getAsString("DataType");

		final AdColumnId columnId = adTableDAO.retrieveColumnId(tableName, columnName);

		final I_AD_ImpFormat_Row formatRow = InterfaceWrapperHelper.newInstance(I_AD_ImpFormat_Row.class);
		formatRow.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		formatRow.setAD_ImpFormat_ID(format.getAD_ImpFormat_ID());
		formatRow.setAD_Column_ID(columnId.getRepoId());
		formatRow.setName(columnName);
		formatRow.setSeqNo(seqNo);
		formatRow.setDataType(dataType);
		formatRow.setDecimalPoint(".");
		formatRow.setDivideBy100(false);
		formatRow.setStartNo(csvColumnNo);
		formatRow.setEndNo(0);
		InterfaceWrapperHelper.saveRecord(formatRow);
	}

	/**
	 * Creates a C_DataImport config record referencing a previously created AD_ImpFormat.
	 */
	@And("C_DataImport config {string} using AD_ImpFormat {string}")
	public void c_DataImport_config_using_AD_ImpFormat(
			@NonNull final String configIdentifierStr,
			@NonNull final String formatIdentifierStr)
	{
		final StepDefDataIdentifier formatIdentifier = StepDefDataIdentifier.ofString(formatIdentifierStr);
		final I_AD_ImpFormat format = formatIdentifier.lookupNotNullIn(impFormatTable);

		final StepDefDataIdentifier configIdentifier = StepDefDataIdentifier.ofString(configIdentifierStr);

		final I_C_DataImport config = InterfaceWrapperHelper.newInstance(I_C_DataImport.class);
		config.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		config.setAD_ImpFormat_ID(format.getAD_ImpFormat_ID());
		config.setDataImport_ConfigType(X_C_DataImport.DATAIMPORT_CONFIGTYPE_Standard);
		config.setInternalName(configIdentifierStr + "_" + Instant.now());
		InterfaceWrapperHelper.saveRecord(config);

		dataImportTable.putOrReplace(configIdentifier, config);
	}

	/**
	 * Imports data content through the full DataImportService pipeline.
	 * The content is parsed using the AD_ImpFormat (CSV, TSV, etc.), loaded into the import staging table,
	 * validated, and then processed into target tables — all synchronously.
	 */
	@When("the following CSV is imported via data import config {string}:")
	@When("the following data is imported via data import config {string}:")
	public void the_following_CSV_is_imported_via_data_import_config(
			@NonNull final String configIdentifierStr,
			@NonNull final String csvContent)
	{
		final StepDefDataIdentifier configIdentifier = StepDefDataIdentifier.ofString(configIdentifierStr);
		final I_C_DataImport config = configIdentifier.lookupNotNullIn(dataImportTable);
		final DataImportConfigId configId = DataImportConfigId.ofRepoId(config.getC_DataImport_ID());

		final ByteArrayResource csvResource = new ByteArrayResource(csvContent.getBytes(StandardCharsets.UTF_8));

		final DataImportResult result = dataImportService.importDataFromResource(DataImportRequest.builder()
				.data(csvResource)
				.dataImportConfigId(configId)
				.clientId(StepDefConstants.CLIENT_ID)
				.orgId(StepDefConstants.ORG_ID)
				.userId(UserId.METASFRESH)
				.completeDocuments(false)
				.processImportRecordsSynchronously(true)
				.stopOnFirstError(true)
				.build());

		assertThat(result.hasErrors())
				.as("CSV import via config '%s' should complete without errors", configIdentifierStr)
				.isFalse();
	}
}
