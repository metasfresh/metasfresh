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
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_ScriptedExportConversion_Status;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.process.M_InOut_ReSend_ScriptedExportConversion;
import de.metas.process.AdProcessId;
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

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for {@code ExternalSystem_ScriptedExportConversion_Status} assertions,
 * scripted-export /ok callback simulation, {@code M_InOut.EPCIS_ExportStatus} roll-up checks,
 * and the {@code M_InOut_ReSend_ScriptedExportConversion} process invocation.
 *
 * <p>The status table holds one row per (config, source-record) pair that is updated in place
 * as the export lifecycle progresses (Pending → Enqueued → Sent / Error / Invalid / DontSend).
 */
@RequiredArgsConstructor
public class ExternalSystem_ScriptedExportConversion_Status_StepDef
{
	@NonNull private final M_InOut_StepDefData inoutTable;
	@NonNull private final ExternalSystem_Config_ScriptedExportConversion_StepDefData scriptedCfgTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	// ------------------------------------------------------------------
	// Status-row assertion (polling)
	// ------------------------------------------------------------------

	/**
	 * Polls until the single {@code ExternalSystem_ScriptedExportConversion_Status} row for the given
	 * shipment + config combination reaches the expected {@code ExportStatus}, optionally asserting
	 * {@code IsResend} and the presence of an {@code AD_Issue_ID}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) shipment identifier<br>
	 *   <b>ExternalSystem_Config_ScriptedExportConversion_ID</b> — (required, identifier-ref) config identifier<br>
	 *   <b>ExportStatus</b> — (required) expected status code (P/U/D/S/E/I/N)<br>
	 *   <b>IsResend</b> — (optional) expected IsResend flag (Y/N)<br>
	 *   <b>HasAD_Issue</b> — (optional) Y if AD_Issue_ID must be &gt; 0<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, ExternalSystem_Config_ScriptedExportConversion_StepDefData
	 * @cucumber.example <pre>
	 * Then after not more than 30s, ExternalSystem_ScriptedExportConversion_Status is found:
	 *   | M_InOut_ID | ExternalSystem_Config_ScriptedExportConversion_ID | ExportStatus | IsResend |
	 *   | io_010     | scriptedCfg_es                                    | S            | N        |
	 * </pre>
	 */
	@And("^after not more than (.*)s, ExternalSystem_ScriptedExportConversion_Status is found:$")
	public void scriptedExportConversionStatusIsFound(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRow firstRow = DataTableRows.of(dataTable).getFirstRow();

		final org.compiere.model.I_M_InOut inoutRecord = inoutTable.get(
				firstRow.getAsIdentifier(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID));
		assertThat(inoutRecord).isNotNull();
		final int inoutId = inoutRecord.getM_InOut_ID();

		final ExternalSystemScriptedExportConversionConfig cfg = scriptedCfgTable.get(
				firstRow.getAsIdentifier(I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID));
		assertThat(cfg).isNotNull();
		final int cfgId = cfg.getId().getRepoId();

		final String expectedStatus = firstRow.getAsString(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExportStatus);
		final String expectedIsResend = firstRow.getAsOptionalString(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_IsResend).orElse(null);
		final String expectedHasIssue = firstRow.getAsOptionalString("HasAD_Issue").orElse(null);

		final int m_inout_table_id = tableDAO.retrieveTableId(org.compiere.model.I_M_InOut.Table_Name);

		StepDefUtil.<I_ExternalSystem_ScriptedExportConversion_Status>tryAndWaitForItem()
				.maxWaitSeconds(timeoutSec)
				.checkingIntervalMs(500L)
				.workerFromOptionalSupplier(() -> {
					final I_ExternalSystem_ScriptedExportConversion_Status statusRow = queryBL
							.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
							.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, m_inout_table_id)
							.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, inoutId)
							.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, cfgId)
							.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExportStatus, expectedStatus)
							.orderByDescending(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID)
							.create()
							.first(I_ExternalSystem_ScriptedExportConversion_Status.class);

					if (statusRow == null)
					{
						return Optional.empty();
					}

					// optional IsResend assertion
					if (expectedIsResend != null)
					{
						final boolean expectedIsResendBool = "Y".equals(expectedIsResend);
						if (statusRow.isResend() != expectedIsResendBool)
						{
							return Optional.empty();
						}
					}

					// optional HasAD_Issue assertion
					if ("Y".equals(expectedHasIssue) && statusRow.getAD_Issue_ID() <= 0)
					{
						return Optional.empty();
					}

					return Optional.of(statusRow);
				})
				.execute();
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
}
