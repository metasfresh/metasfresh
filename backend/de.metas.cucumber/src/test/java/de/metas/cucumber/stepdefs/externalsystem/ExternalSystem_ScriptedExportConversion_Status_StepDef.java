/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.externalsystem;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.api.APIRequest;
import de.metas.cucumber.stepdefs.api.RESTUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.api.impl.EpcisExportStatusChangeService;
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_ScriptedExportConversion_Status;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.process.M_InOut_ReSend_ScriptedExportConversion;
import de.metas.inout.InOutId;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.SpringContextHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for {@code ExternalSystem_ScriptedExportConversion_Status} assertions,
 * scripted-export /ok callback simulation, {@code M_InOut.EPCIS_ExportStatus} roll-up checks,
 * the {@code M_InOut_ReSend_ScriptedExportConversion} and "Change EPCIS Export Status" process
 * invocations, and deactivating a shipment's status row (the escape-hatch that releases a shipment
 * blocked by an in-flight export).
 *
 * <p>The status table holds one row per export ATTEMPT: each enqueue / re-send inserts a fresh
 * row (the per-attempt history), and the transitions of that attempt (Pending → Enqueued →
 * Sent / Error / Invalid / DontSend) update its own row in place, correlated by AD_PInstance_ID.
 */
@RequiredArgsConstructor
public class ExternalSystem_ScriptedExportConversion_Status_StepDef
{
	@NonNull private final M_InOut_StepDefData inoutTable;
	@NonNull private final ExternalSystem_Config_ScriptedExportConversion_StepDefData scriptedCfgTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final EpcisExportStatusChangeService epcisExportStatusChangeService = SpringContextHolder.instance.getBean(EpcisExportStatusChangeService.class);

	// ------------------------------------------------------------------
	// Status-row assertion (polling)
	// ------------------------------------------------------------------

	/**
	 * Polls until every expected {@code ExternalSystem_ScriptedExportConversion_Status} row is found.
	 * Each data-table row describes ONE expected status row, matched NEWEST-FIRST by its {@code ExportStatus}
	 * (the status tab's grid order). A single data row asserts the latest attempt; several data rows assert
	 * that each attempt's data coexists — e.g. after a re-send, the Sent re-send attempt AND the retained
	 * errored first attempt, each with its own {@code IsResend} / {@code HttpResponseCode} / {@code AD_Issue}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment identifier<br>
	 *   <b>ExternalSystem_Config_ScriptedExportConversion_ID</b> — (required, identifier-ref) config identifier<br>
	 *   <b>ExportStatus</b> — (required) expected status code (P/U/D/S/E/I/N)<br>
	 *   <b>IsResend</b> — (optional) expected IsResend flag (Y/N)<br>
	 *   <b>HttpResponseCode</b> — (optional) expected HTTP response code; blank cell = not asserted<br>
	 *   <b>HasAD_Issue</b> — (optional) Y if AD_Issue_ID must be &gt; 0, N if it must be 0<br>
	 *   <b>HasAD_PInstance</b> — (optional) Y if AD_PInstance_ID must be &gt; 0 (who/when audit stamp), N if it must be 0<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, ExternalSystem_Config_ScriptedExportConversion_StepDefData
	 * @cucumber.example <pre>
	 * Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
	 *   | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
	 *   | io_010     | scriptedCfg_es                                    | S            | N        |
	 *
	 * # several rows — the two attempts of a re-send coexist (newest-first):
	 * Then after not more than 10s, ExternalSystem_ScriptedExportConversion_Status is found:
	 *   | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend | HttpResponseCode | HasAD_Issue |
	 *   | io_030     | scriptedCfg_es                                    | S            | Y        | 200              | N           |
	 *   | io_030     | scriptedCfg_es                                    | E            | N        |                  | Y           |
	 * </pre>
	 */
	@And("^after not more than (.*)s, ExternalSystem_ScriptedExportConversion_Status is found:$")
	public void scriptedExportConversionStatusIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final int m_inout_table_id = tableDAO.retrieveTableId(org.compiere.model.I_M_InOut.Table_Name);

		// Resolve each data-table row to an expected status row (identifiers resolved once, up front).
		final List<ExpectedStatusRow> expectedRows = new ArrayList<>();
		for (final DataTableRow row : DataTableRows.of(dataTable).toList())
		{
			final org.compiere.model.I_M_InOut inoutRecord = inoutTable.get(
					row.getAsIdentifier(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID));
			assertThat(inoutRecord).isNotNull();

			final ExternalSystemScriptedExportConversionConfig cfg = scriptedCfgTable.get(
					row.getAsIdentifier(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID));
			assertThat(cfg).isNotNull();

			expectedRows.add(new ExpectedStatusRow(
					inoutRecord.getM_InOut_ID(),
					cfg.getId().getRepoId(),
					row.getAsString(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExportStatus),
					// blank cell for an optional column ⇒ that column is not asserted
					row.getAsOptionalString(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_IsResend).map(String::trim).filter(s -> !s.isEmpty()).orElse(null),
					row.getAsOptionalString(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_HttpResponseCode).map(String::trim).filter(s -> !s.isEmpty()).orElse(null),
					row.getAsOptionalString("HasAD_Issue").map(String::trim).filter(s -> !s.isEmpty()).orElse(null),
					row.getAsOptionalString("HasAD_PInstance").map(String::trim).filter(s -> !s.isEmpty()).orElse(null)));
		}

		StepDefUtil.<Boolean>tryAndWaitForItem()
				.maxWaitSeconds(timeoutSec)
				.checkingIntervalMs(500L)
				.workerFromOptionalSupplier(() -> {
					for (final ExpectedStatusRow expected : expectedRows)
					{
						// newest-first by Status_ID (the grid order); match this expected attempt by its
						// ExportStatus, so several coexisting attempts are each verified independently
						final I_ExternalSystem_ScriptedExportConversion_Status statusRow = queryBL
								.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
								.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, m_inout_table_id)
								.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, expected.inoutId)
								.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, expected.cfgId)
								.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExportStatus, expected.exportStatus)
								.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID)
								.create()
								.first(I_ExternalSystem_ScriptedExportConversion_Status.class);

						if (statusRow == null)
						{
							return Optional.empty();
						}
						if (expected.isResend != null && "Y".equals(expected.isResend) != statusRow.isResend())
						{
							return Optional.empty();
						}
						if (expected.httpResponseCode != null && Integer.parseInt(expected.httpResponseCode) != statusRow.getHttpResponseCode())
						{
							return Optional.empty();
						}
						if (expected.hasIssue != null && "Y".equals(expected.hasIssue) != (statusRow.getAD_Issue_ID() > 0))
						{
							return Optional.empty();
						}
						if (expected.hasPInstance != null && "Y".equals(expected.hasPInstance) != (statusRow.getAD_PInstance_ID() > 0))
						{
							return Optional.empty();
						}
					}
					return Optional.of(Boolean.TRUE);
				})
				.execute();
	}

	/** One expected status row, resolved from a data-table row (identifiers already resolved to repo ids). */
	private static final class ExpectedStatusRow
	{
		private final int inoutId;
		private final int cfgId;
		private final String exportStatus;
		private final String isResend;
		private final String httpResponseCode;
		private final String hasIssue;
		private final String hasPInstance;

		private ExpectedStatusRow(
				final int inoutId,
				final int cfgId,
				@NonNull final String exportStatus,
				final String isResend,
				final String httpResponseCode,
				final String hasIssue,
				final String hasPInstance)
		{
			this.inoutId = inoutId;
			this.cfgId = cfgId;
			this.exportStatus = exportStatus;
			this.isResend = isResend;
			this.httpResponseCode = httpResponseCode;
			this.hasIssue = hasIssue;
			this.hasPInstance = hasPInstance;
		}
	}

	// ------------------------------------------------------------------
	// M_InOut.EPCIS_ExportStatus roll-up assertion (polling)
	// ------------------------------------------------------------------

	/**
	 * Polls until {@code M_InOut.EPCIS_ExportStatus} matches the expected value.
	 * This column is a virtual roll-up derived from the status table.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment identifier<br>
	 *   <b>EPCIS_ExportStatus</b> — (required) expected status code (S/E/P/U etc.)<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * And after not more than 10s, M_InOut EPCIS_ExportStatus is:
	 *   | M_InOut_ID | EPCIS_ExportStatus |
	 *   | io_010     | S                  |
	 * </pre>
	 */
	@And("^after not more than (.*)s, M_InOut EPCIS_ExportStatus is:$")
	public void m_inout_epcis_export_status_is(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRow firstRow = DataTableRows.of(dataTable).getFirstRow();

		final org.compiere.model.I_M_InOut inout = inoutTable.get(
				firstRow.getAsIdentifier(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID));
		assertThat(inout).isNotNull();
		final int inoutId = inout.getM_InOut_ID();

		final String expectedStatus = firstRow.getAsString(org.compiere.model.I_M_InOut.COLUMNNAME_EPCIS_ExportStatus);

		StepDefUtil.<org.compiere.model.I_M_InOut>tryAndWaitForItem()
				.maxWaitSeconds(timeoutSec)
				.checkingIntervalMs(500L)
				.workerFromOptionalSupplier(() -> {
					final org.compiere.model.I_M_InOut fresh = queryBL
							.createQueryBuilder(org.compiere.model.I_M_InOut.class)
							.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID, inoutId)
							.create()
							.firstOnlyNotNull(org.compiere.model.I_M_InOut.class);

					final String actualStatus = fresh.getEPCIS_ExportStatus();
					return expectedStatus.equals(actualStatus)
							? Optional.of(fresh)
							: Optional.empty();
				})
				.execute();
	}

	// ------------------------------------------------------------------
	// /ok callback simulation
	// ------------------------------------------------------------------

	/**
	 * Posts the scripted-export success (/ok) callback for the given shipment.
	 * Reads the most-recent status row for the shipment to determine the pInstance for the callback URL.
	 *
	 * <p>In production this fires when the external system responds with a 2xx HTTP status to the
	 * scripted-export HTTP endpoint. The direct REST call here provides deterministic timing:
	 * the test controls exactly when the callback arrives, avoiding race conditions with the real
	 * async delivery path.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example <pre>
	 * When the scripted-export /ok callback is posted for shipment io_010 with HTTP code 200
	 * </pre>
	 */
	@When("^the scripted-export /ok callback is posted for shipment (.*) with HTTP code (\\d+)$")
	public void scriptedExportOkCallback(final String inoutIdentifierStr, final int httpCode) throws IOException
	{
		final org.compiere.model.I_M_InOut inout = inoutTable.get(StepDefDataIdentifier.ofString(inoutIdentifierStr));
		assertThat(inout).isNotNull();
		final int inoutId = inout.getM_InOut_ID();
		final int m_inout_table_id = tableDAO.retrieveTableId(org.compiere.model.I_M_InOut.Table_Name);

		// Retrieve the most-recent status row that has an AD_PInstance_ID set (i.e. Enqueued or later)
		final I_ExternalSystem_ScriptedExportConversion_Status statusRow = queryBL
				.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, m_inout_table_id)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, inoutId)
				.addNotEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_PInstance_ID, 0)
				.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID)
				.create()
				.first(I_ExternalSystem_ScriptedExportConversion_Status.class);

		assertThat(statusRow)
				.as("No scripted-export status row with AD_PInstance_ID found for M_InOut %s", inoutIdentifierStr)
				.isNotNull();

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(statusRow.getAD_PInstance_ID());

		final String endpointPath = "/api/v2/externalsystem/externalstatus/" + pInstanceId.getRepoId() + "/ok?httpResponseCode=" + httpCode;
		final String authToken = RESTUtil.getAuthToken("metasfresh", "WebUI");

		RESTUtil.performHTTPRequest(
				APIRequest.builder()
						.authToken(authToken)
						.endpointPath(endpointPath)
						.method("POST")
						.expectedStatusCode(200)
						.build()
		);
	}

	// ------------------------------------------------------------------
	// Re-send process invocation
	// ------------------------------------------------------------------

	/**
	 * Invokes the {@code M_InOut_ReSend_ScriptedExportConversion} AD_Process for the given shipment identifier.
	 * In production this corresponds to a user clicking "Re-send" on the shipment action menu.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example <pre>
	 * When M_InOut_ReSend_ScriptedExportConversion process is run for shipment io_030
	 * </pre>
	 */
	@When("^M_InOut_ReSend_ScriptedExportConversion process is run for shipment (.*)$")
	public void runResendProcess(final String inoutIdentifierStr)
	{
		final org.compiere.model.I_M_InOut inout = inoutTable.get(StepDefDataIdentifier.ofString(inoutIdentifierStr));
		assertThat(inout).isNotNull();

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(M_InOut_ReSend_ScriptedExportConversion.class);
		assertThat(processId).as("AD_Process not found for M_InOut_ReSend_ScriptedExportConversion").isNotNull();

		final ProcessExecutor executor = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setRecord(org.compiere.model.I_M_InOut.Table_Name, inout.getM_InOut_ID())
				.buildAndPrepareExecution()
				.executeSync();
		executor.getResult().propagateErrorIfAny();
	}

	// ------------------------------------------------------------------
	// Change-export-status process invocation
	// ------------------------------------------------------------------

	/**
	 * Marks a shipment's EPCIS scripted-export status via the "Change EPCIS Export Status" shipment action —
	 * writing a new, process-instance-stamped attempt row per EPCIS config (prior attempts kept as history).
	 *
	 * <p><b>Direct-service invocation (documented exemption).</b> In production this fires when an operator
	 * runs the "Change EPCIS Export Status" process from the shipment action menu (the
	 * {@code ChangeEpcisExportStatus_M_InOut_SingleView} AD_Process). That process class lives in
	 * {@code de.metas.ui.web.base}, which this cucumber module does not (and should not) depend on.
	 * The process is a thin parameter-reader that delegates to {@link EpcisExportStatusChangeService#changeStatus},
	 * so invoking that service directly exercises the same transition + who/when-audit behaviour. A real
	 * {@code AD_PInstance} for the actual process (resolved by its AD_Process Value) is created and passed,
	 * so the audit stamp on the written row is faithful to production.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * When Change EPCIS Export Status process is run for shipment io_050 with target status DontSend
	 * </pre>
	 */
	@When("^Change EPCIS Export Status process is run for shipment (.*) with target status (.*)$")
	public void runChangeEpcisExportStatusProcess(final String inoutIdentifierStr, final String targetStatusStr)
	{
		final org.compiere.model.I_M_InOut inout = inoutTable.get(StepDefDataIdentifier.ofString(inoutIdentifierStr));
		assertThat(inout).isNotNull();

		final ExternalSystemExportStatus targetStatus = ExternalSystemExportStatus.valueOf(targetStatusStr.trim());

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue("ChangeEpcisExportStatus_M_InOut_SingleView");
		assertThat(processId).as("AD_Process not found for Value=ChangeEpcisExportStatus_M_InOut_SingleView").isNotNull();

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(
				pInstanceDAO.createAD_PInstance(processId).getAD_PInstance_ID());

		epcisExportStatusChangeService.changeStatus(
				InOutId.ofRepoId(inout.getM_InOut_ID()),
				targetStatus,
				pInstanceId);
	}

	/**
	 * Deactivates every active scripted-export status row for the given shipment — simulating support
	 * deactivating a stuck in-flight export-status row via the WebUI shipment tab. This is the
	 * sanctioned escape-hatch that releases a shipment the reverse/reactivate/void guard is blocking
	 * because its EPCIS export is still in-flight (Enqueued/SendingStarted) and the external system
	 * never called back.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * When the EPCIS scripted-export status row for shipment io_140 is deactivated
	 * </pre>
	 */
	@When("^the EPCIS scripted-export status row for shipment (.*) is deactivated$")
	public void deactivateStatusRowForShipment(final String inoutIdentifierStr)
	{
		final org.compiere.model.I_M_InOut inout = inoutTable.get(StepDefDataIdentifier.ofString(inoutIdentifierStr));
		assertThat(inout).isNotNull();
		final int m_inout_table_id = tableDAO.retrieveTableId(org.compiere.model.I_M_InOut.Table_Name);

		final List<I_ExternalSystem_ScriptedExportConversion_Status> statusRows = queryBL
				.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, m_inout_table_id)
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, inout.getM_InOut_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		statusRows.forEach(row -> {
			row.setIsActive(false);
			saveRecord(row);
		});
	}
}
