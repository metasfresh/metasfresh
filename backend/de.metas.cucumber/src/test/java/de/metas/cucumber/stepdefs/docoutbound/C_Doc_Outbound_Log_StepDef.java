/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.docoutbound;

import com.google.common.collect.ImmutableSet;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_C_BPartner_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_Record_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_Line_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Doc_Outbound_Log_StepDef
{
	private static final Logger logger = LogManager.getLogger(C_Doc_Outbound_Log_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	private final C_Doc_Outbound_Log_StepDefData docOutboundLogTable;
	private final C_Doc_Outbound_Log_Line_StepDefData docOutboundLogLineTable;
	private final AD_Archive_StepDefData archiveTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Order_StepDefData orderTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final IdentifiersResolver identifiersResolver;
	/** State per touched config id, as it was before the first change by this scenario. */
	private final Map<Integer, ConfigState> originalConfigStates = new HashMap<>();

	public C_Doc_Outbound_Log_StepDef(
			@NonNull final C_Doc_Outbound_Log_StepDefData docOutboundLogTable,
			@NonNull final C_Doc_Outbound_Log_Line_StepDefData docOutboundLogLineTable,
			@NonNull final AD_Archive_StepDefData archiveTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final IdentifiersResolver identifiersResolver)
	{
		this.docOutboundLogTable = docOutboundLogTable;
		this.docOutboundLogLineTable = docOutboundLogLineTable;
		this.archiveTable = archiveTable;
		this.bpartnerTable = bpartnerTable;
		this.orderTable = orderTable;
		this.invoiceTable = invoiceTable;
		this.identifiersResolver = identifiersResolver;
	}

	/**
	 * Makes sure the given table is configured for outbound documents: activates the existing configuration of that table
	 * and sets its <code>DocBaseType</code> if given, otherwise creates a configuration.
	 * The configuration is shared by all scenarios of the DB, so the original state (absent, or its IsActive and DocBaseType)
	 * is remembered and restored after the scenario.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Table.Name</b> — (required) table name of the configured document, e.g. C_Order<br>
	 *   <b>DocBaseType</b> — (optional) restricts the configuration to documents of this base type, e.g. SOO<br>
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Doc_Outbound_Config:
	 *   | AD_Table.Name | DocBaseType |
	 *   | C_Order       | SOO         |
	 * </pre>
	 */
	@And("metasfresh contains C_Doc_Outbound_Config:")
	public void metasfresh_contains_C_Doc_Outbound_Config(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final int adTableId = tableDAO.retrieveTableId(row.getAsString(I_AD_Table.Table_Name + "." + I_AD_Table.COLUMNNAME_Name));
			final Optional<String> docBaseType = row.getAsOptionalString(I_C_Doc_Outbound_Config.COLUMNNAME_DocBaseType).filter(Check::isNotBlank);

			final I_C_Doc_Outbound_Config existingConfig = queryBL.createQueryBuilder(I_C_Doc_Outbound_Config.class)
					.addEqualsFilter(I_C_Doc_Outbound_Config.COLUMNNAME_AD_Table_ID, adTableId)
					.orderBy(I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID)
					.create()
					.first(I_C_Doc_Outbound_Config.class);

			final I_C_Doc_Outbound_Config config;
			if (existingConfig != null)
			{
				// remember the state before the scenario's first change; a repeated call finds the entry (also of a created config) and keeps it
				originalConfigStates.putIfAbsent(existingConfig.getC_Doc_Outbound_Config_ID(), ConfigState.existing(existingConfig.isActive(), existingConfig.getDocBaseType()));
				config = existingConfig;
			}
			else
			{
				config = newInstance(I_C_Doc_Outbound_Config.class);
				config.setAD_Table_ID(adTableId);
			}
			config.setIsActive(true);
			docBaseType.ifPresent(config::setDocBaseType);
			saveRecord(config);

			if (existingConfig == null)
			{
				originalConfigStates.put(config.getC_Doc_Outbound_Config_ID(), ConfigState.created());
			}
		});
	}

	/**
	 * Restores the outbound configurations changed by this scenario: deletes the ones it created, resets IsActive and DocBaseType of the others.
	 * Runs on scenario pass and failure, so no scenario leaks its configuration into the next one of the shared DB.
	 */
	@After
	public void restoreDocOutboundConfigs()
	{
		originalConfigStates.forEach((configId, originalState) -> {
			final I_C_Doc_Outbound_Config config = load(configId, I_C_Doc_Outbound_Config.class);
			if (originalState.createdByScenario)
			{
				InterfaceWrapperHelper.delete(config);
			}
			else
			{
				config.setIsActive(originalState.isActive);
				config.setDocBaseType(originalState.docBaseType);
				saveRecord(config);
			}
		});
		originalConfigStates.clear();
	}

	private static final class ConfigState
	{
		private final boolean createdByScenario;
		private final boolean isActive;
		@Nullable private final String docBaseType;

		static ConfigState created()
		{
			return new ConfigState(true, false, null);
		}

		static ConfigState existing(final boolean isActive, @Nullable final String docBaseType)
		{
			return new ConfigState(false, isActive, docBaseType);
		}

		private ConfigState(final boolean createdByScenario, final boolean isActive, @Nullable final String docBaseType)
		{
			this.createdByScenario = createdByScenario;
			this.isActive = isActive;
			this.docBaseType = docBaseType;
		}
	}

	/**
	 * Runs the "Massendruck PDF" outbound-log process for the given log: takes the last archive of the log and marks it as printed,
	 * which writes a <code>pdf</code> line to the log. The process itself lives in a module the cucumber module does not depend on,
	 * so the same service calls are made here (inactive logs are skipped as in the process): {@link IArchiveBL#getLastArchive} then {@link IArchiveBL#updatePrintedRecords}.
	 * In production this is triggered by the user selecting the log in the outbound documents window and running the process;
	 * a direct call is used because the process needs a window view as input.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Doc_Outbound_Log_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And Massendruck PDF is run for C_Doc_Outbound_Log "orderOutboundLog"
	 * </pre>
	 */
	@And("Massendruck PDF is run for C_Doc_Outbound_Log {string}")
	public void massendruck_PDF_is_run_for_C_Doc_Outbound_Log(@NonNull final String logIdentifier)
	{
		final I_C_Doc_Outbound_Log docOutboundLog = docOutboundLogTable.get(logIdentifier);
		// reload: the stored instance may predate the last refresh of the header
		final I_C_Doc_Outbound_Log reloadedLog = load(docOutboundLog.getC_Doc_Outbound_Log_ID(), I_C_Doc_Outbound_Log.class);

		if (!reloadedLog.isActive())
		{
			return; // the process skips inactive logs
		}

		final AdArchive lastArchive = archiveBL.getLastArchive(TableRecordReference.ofReferenced(reloadedLog))
				.orElseThrow(() -> new AdempiereException("No archive found for C_Doc_Outbound_Log_ID=" + reloadedLog.getC_Doc_Outbound_Log_ID()));

		archiveBL.updatePrintedRecords(ImmutableSet.of(lastArchive.getId()), Env.getLoggedUserId());
	}

	/**
	 * Re-creates the PDF of the given order or invoice, as the "re-create archive" workpackage does for a selected outbound log:
	 * the document archiver produces a new archive of the document in its current state.
	 * In production the user triggers this on the outbound log; the workpackage only loads the document and calls the archiver,
	 * which is what is done here synchronously.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the document PDF of "order_1" is re-created
	 * </pre>
	 */
	@And("the document PDF of {string} is re-created")
	public void the_document_PDF_is_re_created(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordReference = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		trxManager.runInNewTrx(() -> {
			final PO po = TableModelLoader.instance.getPO(recordReference);
			DefaultModelArchiver.of(po).archive();
		});
	}

	/**
	 * Prints a report of the given document directly, as a user does when running a report process for it from a window:
	 * the report PDF is archived for the document without any business partner, and does not go through the document archiver.
	 * No report process runs in the scenario, so AD_Process_ID and AD_PInstance_ID of the archive stay unset (a real print sets them; the outbound log
	 * only looks at record, partner and configuration of the archive).
	 * The archive request is the one the report starter's print service sends, without the direct print-queue enqueueing (which needs a configured printer).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And a direct report print of "order_1" is archived
	 * </pre>
	 */
	@And("a direct report print of {string} is archived")
	public void a_direct_report_print_of_is_archived(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordReference = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		final ArchiveInfo archiveInfo = new ArchiveInfo("Direct report print", recordReference);

		// same request as MassPrintingService.print(), minus the direct print-queue enqueueing (needs a configured printer)
		final ArchiveResult archiveResult = archiveBL.archive(ArchiveRequest.builder()
				.ctx(Env.getCtx())
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.data(new ByteArrayResource(createOnePagePdf()))
				.force(true)
				.save(true)
				.isReport(archiveInfo.isReport())
				.recordRef(archiveInfo.getRecordRef())
				.processId(archiveInfo.getProcessId())
				.pinstanceId(archiveInfo.getPInstanceId())
				.archiveName(archiveInfo.getName())
				.bpartnerId(archiveInfo.getBpartnerId())
				.copies(archiveInfo.getCopies())
				.build());

		assertThat(archiveResult.isNoArchive()).as("direct report print of %s archived", recordReference).isFalse();
	}

	/**
	 * Polls until the outbound log of the given record exists AND all given columns match,
	 * then stores it under the given identifier. Fails with the last mismatch on timeout.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Doc_Outbound_Log_ID</b> — (required, identifier-ref) identifier under which the log is stored<br>
	 *   <b>Record_ID</b> — (required, identifier-ref) the order or invoice the log belongs to<br>
	 *   <b>AD_Table.Name</b> — (required) C_Order or C_Invoice<br>
	 *   <b>CurrentEMailAddress</b>, <b>CurrentEMailAddressCC</b> — (optional) expected address; the literal <code>null</code> expects none<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected business partner; the literal <code>null</code> expects none<br>
	 *   <b>C_BP_Group_ID</b> — (optional) expected business partner group id (a virtual column derived from the partner)<br>
	 *   <b>DocBaseType</b> — (optional) expected base type of the log's document type<br>
	 *   <b>DocStatus</b>, <b>POReference</b>, <b>DocumentNo</b> — (optional) expected values<br>
	 *   <b>DateDoc</b> — (optional) expected document date, e.g. 2022-05-17<br>
	 *   <b>IsInvoiceEmailEnabled</b> — (optional) expected flag<br>
	 * @cucumber.depends StepDefData: C_Doc_Outbound_Log_StepDefData, C_BPartner_StepDefData, C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s validate C_Doc_Outbound_Log:
	 *   | C_Doc_Outbound_Log_ID | Record_ID | AD_Table.Name | C_BPartner_ID | DocStatus |
	 *   | orderOutboundLog      | order_1   | C_Order       | bpartner_1    | CO        |
	 * </pre>
	 */
	@And("^after not more than (.*)s validate C_Doc_Outbound_Log:$")
	public void validate_C_Doc_Outbound_Log(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final AtomicReference<String> lastMismatch = new AtomicReference<>("no C_Doc_Outbound_Log found");

			StepDefUtil.tryAndWait(
					timeoutSec,
					500,
					() -> {
						final I_C_Doc_Outbound_Log docOutboundLog = retrieveDocOutboundLog(row);
						if (docOutboundLog == null)
						{
							lastMismatch.set("no C_Doc_Outbound_Log found for the record");
							return false;
						}

						final Optional<String> mismatch = findMismatch(row, docOutboundLog);
						if (mismatch.isPresent())
						{
							lastMismatch.set(mismatch.get());
							return false;
						}

						final Optional<I_C_Doc_Outbound_Log> knownLog = docOutboundLogTable.getOptional(row.getAsIdentifier(COLUMNNAME_C_Doc_Outbound_Log_ID));
						assertThat(knownLog.map(I_C_Doc_Outbound_Log::getC_Doc_Outbound_Log_ID).orElse(docOutboundLog.getC_Doc_Outbound_Log_ID()))
								.as("the outbound document must stay the same one (C_Doc_Outbound_Log_ID)")
								.isEqualTo(docOutboundLog.getC_Doc_Outbound_Log_ID());
						docOutboundLogTable.putOrReplace(row.getAsIdentifier(COLUMNNAME_C_Doc_Outbound_Log_ID), docOutboundLog);
						return true;
					},
					() -> logger.error("C_Doc_Outbound_Log did not reach the expected state: {}", lastMismatch.get()));
		});
	}

	/**
	 * Validates the history lines of an outbound log, in creation order
	 * (the n-th table row of a log is checked against its n-th line) and stores each line under its identifier.
	 * The log must have exactly as many lines as the table has rows for it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Doc_Outbound_Log_Line_ID</b> — (required, identifier-ref) identifier under which the line is stored<br>
	 *   <b>C_Doc_Outbound_Log_ID</b> — (required, identifier-ref) the log whose lines are validated<br>
	 *   <b>Record_ID</b> — (required, identifier-ref) the order or invoice the line belongs to<br>
	 *   <b>AD_Table.Name</b> — (required) C_Order or C_Invoice<br>
	 *   <b>DocBaseType</b>, <b>DocStatus</b> — (optional) expected values<br>
	 *   <b>AD_Archive_ID</b> — (optional, identifier) the PDF the line references: an identifier not used yet is bound to the line's archive (which must not be bound to another identifier), a known one must match the line's archive<br>
	 *   <b>AD_Archive.C_BPartner_ID</b> — (optional, identifier-ref) expected business partner of the line's archive; the literal <code>null</code> expects none<br>
	 * @cucumber.depends StepDefData: C_Doc_Outbound_Log_StepDefData, C_Doc_Outbound_Log_Line_StepDefData, AD_Archive_StepDefData, C_BPartner_StepDefData, C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate C_Doc_Outbound_Log_Line:
	 *   | C_Doc_Outbound_Log_Line_ID | C_Doc_Outbound_Log_ID | Record_ID | AD_Table.Name | DocStatus |
	 *   | line_1                     | orderOutboundLog      | order_1   | C_Order       | CO        |
	 *   | line_2                     | orderOutboundLog      | order_1   | C_Order       | IP        |
	 * </pre>
	 */
	@And("validate C_Doc_Outbound_Log_Line:")
	public void validate_C_Doc_Outbound_Log_Line(@NonNull final DataTable dataTable)
	{
		final DataTableRows rows = DataTableRows.of(dataTable);
		final Map<Integer, Integer> expectedLineCountByLogId = new HashMap<>();
		rows.stream().forEach(row -> expectedLineCountByLogId.merge(
				row.getAsIdentifier(I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_ID).lookupNotNullIn(docOutboundLogTable).getC_Doc_Outbound_Log_ID(),
				1,
				Integer::sum));
		final Map<Integer, Integer> rowCountByLogId = new HashMap<>();

		rows.forEach(row -> {
			final I_C_Doc_Outbound_Log docOutboundLog = row.getAsIdentifier(I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_ID).lookupNotNullIn(docOutboundLogTable);

			final List<I_C_Doc_Outbound_Log_Line> docOutboundLogLines = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class)
					.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, docOutboundLog.getC_Doc_Outbound_Log_ID())
					.orderBy(I_C_Doc_Outbound_Log_Line.COLUMNNAME_Created)
					.orderBy(COLUMNNAME_C_Doc_Outbound_Log_Line_ID)
					.create()
					.list(I_C_Doc_Outbound_Log_Line.class);

			assertThat(docOutboundLogLines)
					.as("C_Doc_Outbound_Log_Line count of C_Doc_Outbound_Log_ID=%s", docOutboundLog.getC_Doc_Outbound_Log_ID())
					.hasSize(expectedLineCountByLogId.get(docOutboundLog.getC_Doc_Outbound_Log_ID()));

			final int lineIndex = rowCountByLogId.merge(docOutboundLog.getC_Doc_Outbound_Log_ID(), 1, Integer::sum) - 1;
			final I_C_Doc_Outbound_Log_Line docOutboundLogLine = docOutboundLogLines.get(lineIndex);

			final TableRecordReference recordReference = getTableRecordReference(row);

			assertThat(docOutboundLogLine.getRecord_ID()).isEqualTo(recordReference.getRecord_ID());
			assertThat(docOutboundLogLine.getAD_Table_ID()).isEqualTo(recordReference.getAD_Table_ID());

			row.getAsOptionalString(I_C_DocType.COLUMNNAME_DocBaseType)
					.filter(Check::isNotBlank)
					.ifPresent(docBaseType -> {
						final I_C_DocType docType = load(docOutboundLogLine.getC_DocType_ID(), I_C_DocType.class);
						assertThat(docType.getDocBaseType()).isEqualTo(docBaseType);
					});

			row.getAsOptionalString(I_C_Doc_Outbound_Log_Line.COLUMNNAME_DocStatus)
					.filter(Check::isNotBlank)
					.ifPresent(docStatus -> assertThat(docOutboundLogLine.getDocStatus()).isEqualTo(docStatus));

			final I_AD_Archive lineArchive = docOutboundLogLine.getAD_Archive_ID() > 0 ? load(docOutboundLogLine.getAD_Archive_ID(), I_AD_Archive.class) : null;

			row.getAsOptionalIdentifier(I_C_Doc_Outbound_Log_Line.COLUMNNAME_AD_Archive_ID).ifPresent(archiveIdentifier -> {
				assertThat(lineArchive).as("AD_Archive of the log line %s", docOutboundLogLine.getC_Doc_Outbound_Log_Line_ID()).isNotNull();
				final Optional<I_AD_Archive> knownArchive = archiveTable.getOptional(archiveIdentifier);
				if (knownArchive.isPresent())
				{
					assertThat(lineArchive.getAD_Archive_ID())
							.as("AD_Archive_ID of the log line %s, expected to be the archive %s", docOutboundLogLine.getC_Doc_Outbound_Log_Line_ID(), archiveIdentifier.getAsString())
							.isEqualTo(knownArchive.get().getAD_Archive_ID());
				}
				else
				{
					archiveTable.put(archiveIdentifier, lineArchive); // fails if the archive is already bound to another identifier
				}
			});

			row.getAsOptionalIdentifier(I_AD_Archive.Table_Name + "." + COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				assertThat(lineArchive).as("AD_Archive of the log line %s", docOutboundLogLine.getC_Doc_Outbound_Log_Line_ID()).isNotNull();
				final int expectedBPartnerId = bpIdentifier.isNullPlaceholder() ? 0 : bpIdentifier.lookupNotNullIn(bpartnerTable).getC_BPartner_ID();
				assertThat(Math.max(lineArchive.getC_BPartner_ID(), 0))
						.as("C_BPartner_ID of the archive of the log line %s", docOutboundLogLine.getC_Doc_Outbound_Log_Line_ID())
						.isEqualTo(expectedBPartnerId);
			});

			docOutboundLogLineTable.putOrReplace(row.getAsIdentifier(COLUMNNAME_C_Doc_Outbound_Log_Line_ID), docOutboundLogLine);
		});
	}

	/**
	 * Verifies that the given order or invoice has no outbound log with the given status.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And no C_Doc_Outbound_Log with DocStatus CO exists for record "order_1"
	 * </pre>
	 */
	@And("no C_Doc_Outbound_Log with DocStatus {word} exists for record {string}")
	public void no_C_Doc_Outbound_Log_with_DocStatus_exists_for_record(@NonNull final String docStatus, @NonNull final String recordIdentifier)
	{
		final TableRecordReference recordReference = getTableRecordReference(recordIdentifier);

		final List<Integer> matchingLogIds = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addEqualsFilter(COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.addEqualsFilter(COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus, docStatus)
				.create()
				.listIds();

		assertThat(matchingLogIds)
				.as("C_Doc_Outbound_Log_IDs with DocStatus=%s for record %s", docStatus, recordReference)
				.isEmpty();
	}

	/** A minimal readable PDF as report data of a direct print. */
	@NonNull
	private static byte[] createOnePagePdf()
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final Document document = new Document();
		try
		{
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(new Paragraph("Direct report print"));
			document.close();
		}
		catch (final DocumentException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		return out.toByteArray();
	}

	@NonNull
	private Optional<String> findMismatch(@NonNull final DataTableRow row, @NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		// blank cell = not checked; literal "null" = expect no address (legacy: blank was never checked)
		final Optional<String> currentEmailAddress = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress).filter(Check::isNotBlank);
		if (currentEmailAddress.isPresent() && !matchesString(currentEmailAddress.get(), docOutboundLog.getCurrentEMailAddress()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress, currentEmailAddress.get(), docOutboundLog.getCurrentEMailAddress());
		}

		final Optional<String> currentEmailAddressCC = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddressCC).filter(Check::isNotBlank);
		if (currentEmailAddressCC.isPresent() && !matchesString(currentEmailAddressCC.get(), docOutboundLog.getCurrentEMailAddressCC()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddressCC, currentEmailAddressCC.get(), docOutboundLog.getCurrentEMailAddressCC());
		}

		// literal "null" = the partner must not be set
		final Optional<StepDefDataIdentifier> bpIdentifier = row.getAsOptionalIdentifier(COLUMNNAME_C_BPartner_ID);
		if (bpIdentifier.isPresent())
		{
			final int expectedBPartnerId = bpIdentifier.get().isNullPlaceholder() ? -1 : bpIdentifier.get().lookupNotNullIn(bpartnerTable).getC_BPartner_ID();
			final boolean matches = expectedBPartnerId <= 0 ? docOutboundLog.getC_BPartner_ID() <= 0 : docOutboundLog.getC_BPartner_ID() == expectedBPartnerId;
			if (!matches)
			{
				return mismatch(COLUMNNAME_C_BPartner_ID, expectedBPartnerId <= 0 ? "not set" : expectedBPartnerId, docOutboundLog.getC_BPartner_ID());
			}
		}

		final Optional<Integer> bpGroupId = row.getAsOptionalInt(I_C_Doc_Outbound_Log.COLUMNNAME_C_BP_Group_ID);
		if (bpGroupId.isPresent() && bpGroupId.get() != docOutboundLog.getC_BP_Group_ID())
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_C_BP_Group_ID, bpGroupId.get(), docOutboundLog.getC_BP_Group_ID());
		}

		final Optional<String> docBaseType = row.getAsOptionalString(I_C_DocType.COLUMNNAME_DocBaseType).filter(Check::isNotBlank);
		if (docBaseType.isPresent())
		{
			final I_C_DocType docType = load(docOutboundLog.getC_DocType_ID(), I_C_DocType.class);
			if (!docBaseType.get().equals(docType.getDocBaseType()))
			{
				return mismatch(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType.get(), docType.getDocBaseType());
			}
		}

		final Optional<String> docStatus = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus).filter(Check::isNotBlank);
		if (docStatus.isPresent() && !docStatus.get().equals(docOutboundLog.getDocStatus()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus, docStatus.get(), docOutboundLog.getDocStatus());
		}

		final Optional<String> poReference = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_POReference).filter(Check::isNotBlank);
		if (poReference.isPresent() && !matchesString(poReference.get(), docOutboundLog.getPOReference()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_POReference, poReference.get(), docOutboundLog.getPOReference());
		}

		final Optional<String> documentNo = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_DocumentNo).filter(Check::isNotBlank);
		if (documentNo.isPresent() && !documentNo.get().equals(docOutboundLog.getDocumentNo()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_DocumentNo, documentNo.get(), docOutboundLog.getDocumentNo());
		}

		final Optional<Timestamp> dateDoc = row.getAsOptionalLocalDateTimestamp(I_C_Doc_Outbound_Log.COLUMNNAME_DateDoc);
		if (dateDoc.isPresent() && !dateDoc.get().equals(docOutboundLog.getDateDoc()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_DateDoc, dateDoc.get(), docOutboundLog.getDateDoc());
		}

		final Boolean isInvoiceEmailEnabled = row.getAsOptionalBoolean(I_C_Doc_Outbound_Log.COLUMNNAME_IsInvoiceEmailEnabled).toBooleanOrNull();
		if (isInvoiceEmailEnabled != null && isInvoiceEmailEnabled != docOutboundLog.isInvoiceEmailEnabled())
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_IsInvoiceEmailEnabled, isInvoiceEmailEnabled, docOutboundLog.isInvoiceEmailEnabled());
		}

		return Optional.empty();
	}

	private static boolean matchesString(@NonNull final String expected, @Nullable final String actual)
	{
		return "null".equals(expected) ? Check.isBlank(actual) : expected.equals(actual);
	}

	@NonNull
	private static Optional<String> mismatch(final String columnName, final Object expected, @Nullable final Object actual)
	{
		return Optional.of(columnName + ": expected <" + expected + "> but was <" + actual + ">");
	}

	@NonNull
	private TableRecordReference getTableRecordReference(@NonNull final DataTableRow row)
	{
		final String recordIdentifier = row.getAsIdentifier(COLUMNNAME_Record_ID).getAsString();
		final String tableName = row.getAsString(I_AD_Table.Table_Name + "." + I_AD_Table.COLUMNNAME_Name);

		switch (tableName)
		{
			case I_C_Order.Table_Name:
				return TableRecordReference.of(orderTable.get(recordIdentifier));

			case I_C_Invoice.Table_Name:
				return TableRecordReference.of(invoiceTable.get(recordIdentifier));

			default:
				throw new AdempiereException("Unhandled tableName")
						.appendParametersToMessage()
						.setParameter("TableName", tableName);
		}
	}

	/** Resolves an order or invoice identifier (orders first). */
	@NonNull
	private TableRecordReference getTableRecordReference(@NonNull final String recordIdentifier)
	{
		final Optional<I_C_Order> order = orderTable.getOptional(recordIdentifier);
		if (order.isPresent())
		{
			return TableRecordReference.of(order.get());
		}

		final Optional<I_C_Invoice> invoice = invoiceTable.getOptional(recordIdentifier);
		if (invoice.isPresent())
		{
			return TableRecordReference.of(invoice.get());
		}

		throw new AdempiereException("No order or invoice registered for identifier").setParameter("Identifier", recordIdentifier);
	}

	@Nullable
	private I_C_Doc_Outbound_Log retrieveDocOutboundLog(@NonNull final DataTableRow row)
	{
		final TableRecordReference recordReference = getTableRecordReference(row);

		final List<I_C_Doc_Outbound_Log> logs = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addEqualsFilter(COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.addEqualsFilter(COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.create()
				.list(I_C_Doc_Outbound_Log.class);

		assertThat(logs).as("C_Doc_Outbound_Logs of %s (exactly one expected)", recordReference).hasSizeLessThanOrEqualTo(1);
		return logs.isEmpty() ? null : logs.get(0);
	}
}
