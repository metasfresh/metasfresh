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

package de.metas.cucumber.stepdefs.edi;

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.externalsystem.ExternalSystem_Config_ScriptedExportConversion_StepDefData;
import de.metas.esb.edi.model.I_EDI_EPCIS_Transmitted_SSCC;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_InOut;
import de.metas.util.Services;

import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for seeding {@code EDI_EPCIS_Transmitted_SSCC} — the EPCIS transmission ledger
 * recording every physical SSCC18 already sent to an EPCIS receiver, so a scenario can simulate a
 * prior successful transmission and verify that {@code get_epcis_events_json_fn} excludes it.
 */
@RequiredArgsConstructor
public class EDI_EPCIS_Transmitted_SSCC_StepDef
{
	private final @NonNull M_InOut_StepDefData inoutTable;
	private final @NonNull ExternalSystem_Config_ScriptedExportConversion_StepDefData scriptedCfgTable;

	/**
	 * Clears the {@code EDI_EPCIS_Transmitted_SSCC} ledger so a scenario starts from a known-empty
	 * state. Needed because the ledger is a real (non-rolled-back) table and the local provided-infra
	 * DB is not reset between runs: a row seeded by a previous run of this scenario would otherwise be
	 * matched by {@code get_epcis_events_json_fn}'s ledger-exclusion filter and wrongly suppress the
	 * baseline pallet. In CI (fresh DB per executor group) this is a harmless no-op. This table is used
	 * only by this scenario, so deleting all rows is safe.
	 */
	@Given("the EPCIS transmission ledger is empty")
	public void the_epcis_transmission_ledger_is_empty()
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
				.create()
				.delete();
	}

	/**
	 * Inserts a row into the {@code EDI_EPCIS_Transmitted_SSCC} ledger table, simulating a
	 * previously-successful EPCIS transmission for the given physical SSCC18.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>SSCC18</b> — (required) physical SSCC18 value already recorded as transmitted<br>
	 *   <b>ExternalSystem_Config_ScriptedExportConversion_ID</b> — (required, identifier-ref) the receiver config<br>
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the shipment the SSCC was transmitted on<br>
	 *   <b>OPT.Transmitted</b> — (optional) transmission timestamp override; defaults to the current (test) clock<br>
	 *   <b>OPT.IsActive</b> — (optional) defaults to {@code true}. Set to {@code false} to simulate a
	 *   ledger row that support has deactivated via the WebUI shipment tab — the ledger-exclusion filter
	 *   in {@code get_epcis_events_json_fn} only matches active rows, so a deactivated row must NOT
	 *   suppress re-sending its SSCC.<br>
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains EDI_EPCIS_Transmitted_SSCC:
	 *   | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID |
	 *   | 987654321000030916 | scriptedCfg_020                                    | io_020     |
	 * </pre>
	 */
	@Given("metasfresh contains EDI_EPCIS_Transmitted_SSCC:")
	public void metasfresh_contains_EDI_EPCIS_Transmitted_SSCC(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String sscc18 = row.getAsString(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18);

			final ExternalSystemScriptedExportConversionConfig cfg = scriptedCfgTable.get(
					row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID));

			final I_M_InOut inout = inoutTable.get(row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID));

			final I_EDI_EPCIS_Transmitted_SSCC record = newInstance(I_EDI_EPCIS_Transmitted_SSCC.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			record.setSSCC18(sscc18);
			record.setExternalSystem_Config_ScriptedExportConversion_ID(cfg.getId().getRepoId());
			record.setM_InOut_ID(inout.getM_InOut_ID());
			record.setTransmitted(row.getAsOptionalInstantTimestamp("OPT." + I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_Transmitted)
					.orElseGet(SystemTime::asTimestamp));
			record.setIsActive(row.getAsOptionalBoolean("OPT." + I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_IsActive).orElse(true));

			saveRecord(record);
		});
	}

	/**
	 * Polls until an ACTIVE {@code EDI_EPCIS_Transmitted_SSCC} ledger row exists for every
	 * (SSCC18, config, shipment) triple in the table — used to verify that a successful EPCIS send
	 * actually recorded the transmitted SSCC(s), whether seeded directly (as above) or written by
	 * the production success listener as the real side-effect of a live send.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>SSCC18</b> — (required) physical SSCC18 value expected to be recorded as transmitted<br>
	 *   <b>ExternalSystem_Config_ScriptedExportConversion_ID</b> — (required, identifier-ref) the receiver config<br>
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the shipment expected to have transmitted it<br>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 10s, EDI_EPCIS_Transmitted_SSCC is found:
	 *   | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID |
	 *   | 987654321000030916 | scriptedCfg_020                                    | io_020     |
	 * </pre>
	 */
	@And("^after not more than (.*)s, EDI_EPCIS_Transmitted_SSCC is found:$")
	public void after_not_more_than_epcis_transmitted_sscc_is_found(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final DataTableRow row : DataTableRows.of(dataTable).toList())
		{
			final String sscc18 = row.getAsString(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18);

			final ExternalSystemScriptedExportConversionConfig cfg = scriptedCfgTable.get(
					row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID));

			final I_M_InOut inout = inoutTable.get(row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID));

			StepDefUtil.<I_EDI_EPCIS_Transmitted_SSCC>tryAndWaitForItem()
					.maxWaitSeconds(timeoutSec)
					.checkingIntervalMs(500L)
					.workerFromOptionalSupplier(() -> Services.get(IQueryBL.class)
							.createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
							.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18, sscc18)
							.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, cfg.getId().getRepoId())
							.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID, inout.getM_InOut_ID())
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOptional(I_EDI_EPCIS_Transmitted_SSCC.class))
					.execute();
		}
	}

	/**
	 * Deactivates the {@code EDI_EPCIS_Transmitted_SSCC} ledger row(s) matching the given
	 * (SSCC18, config, shipment) triple — simulating support deactivating a ledger row via the WebUI
	 * shipment tab (the sanctioned way to unblock both re-sending the SSCC and reversing/reactivating/
	 * voiding the shipment).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>SSCC18</b> — (required) physical SSCC18 of the ledger row to deactivate<br>
	 *   <b>ExternalSystem_Config_ScriptedExportConversion_ID</b> — (required, identifier-ref) the receiver config<br>
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the shipment the ledger row is on<br>
	 * @cucumber.example
	 * <pre>
	 * And EDI_EPCIS_Transmitted_SSCC records are deactivated:
	 *   | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID |
	 *   | 987654321000030916 | scriptedCfg_020                                    | io_020     |
	 * </pre>
	 */
	@Given("EDI_EPCIS_Transmitted_SSCC records are deactivated:")
	public void epcis_transmitted_sscc_records_are_deactivated(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String sscc18 = row.getAsString(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18);

			final ExternalSystemScriptedExportConversionConfig cfg = scriptedCfgTable.get(
					row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID));

			final I_M_InOut inout = inoutTable.get(row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID));

			final List<I_EDI_EPCIS_Transmitted_SSCC> ledgerRecords = Services.get(IQueryBL.class)
					.createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
					.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18, sscc18)
					.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, cfg.getId().getRepoId())
					.addEqualsFilter(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID, inout.getM_InOut_ID())
					.create()
					.list();

			ledgerRecords.forEach(ledgerRecord -> {
				ledgerRecord.setIsActive(false);
				saveRecord(ledgerRecord);
			});
		});
	}

	/**
	 * Asserts that the set of ACTIVE {@code EDI_EPCIS_Transmitted_SSCC} ledger rows is EXACTLY the
	 * (SSCC18, config, shipment) triples given — no more, no fewer. Unlike the presence-only
	 * "is found" step, this catches BOTH a missing row (an orphaned pallet that never sent) AND a
	 * surplus row (a physical SSCC transmitted more than once): it is the exactly-once / no-orphan /
	 * no-duplicate assertion for the end-to-end send-path scenarios.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>SSCC18</b> — (required) physical SSCC18 expected in the ledger<br>
	 *   <b>ExternalSystem_Config_ScriptedExportConversion_ID</b> — (required, identifier-ref) the receiver config<br>
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the shipment that transmitted it<br>
	 * @cucumber.example
	 * <pre>
	 * Then the EPCIS transmission ledger contains exactly:
	 *   | SSCC18             | ExternalSystem_Config_ScriptedExportConversion_ID | M_InOut_ID |
	 *   | 987654321000030916 | scriptedCfg_120                                    | ioA_120    |
	 *   | 987654321000030917 | scriptedCfg_120                                    | ioB_120    |
	 * </pre>
	 */
	@Then("the EPCIS transmission ledger contains exactly:")
	public void the_epcis_transmission_ledger_contains_exactly(@NonNull final DataTable dataTable)
	{
		final List<String> expected = DataTableRows.of(dataTable).toList().stream()
				.map(row -> {
					final String sscc18 = row.getAsString(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_SSCC18);
					final ExternalSystemScriptedExportConversionConfig cfg = scriptedCfgTable.get(
							row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID));
					final I_M_InOut inout = inoutTable.get(row.getAsIdentifier(I_EDI_EPCIS_Transmitted_SSCC.COLUMNNAME_M_InOut_ID));
					return sscc18 + "|" + cfg.getId().getRepoId() + "|" + inout.getM_InOut_ID();
				})
				.sorted()
				.collect(Collectors.toList());

		final List<String> actual = Services.get(IQueryBL.class)
				.createQueryBuilder(I_EDI_EPCIS_Transmitted_SSCC.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(r -> r.getSSCC18()
						+ "|" + r.getExternalSystem_Config_ScriptedExportConversion_ID()
						+ "|" + r.getM_InOut_ID())
				.sorted()
				.collect(Collectors.toList());

		assertThat(actual)
				.as("Active EDI_EPCIS_Transmitted_SSCC rows (SSCC18|config|M_InOut_ID) must be exactly the expected set")
				.containsExactlyElementsOf(expected);
	}
}
