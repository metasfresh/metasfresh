/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion;

import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemExportStatusServiceTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService service;

	/**
	 * AD_Column_ID for M_InOut.Description as resolved by the unit-test mock of IADTableDAO.
	 * We use Description (a nullable String column present in all jar versions) as
	 * the roll-up target in the "target-column write" tests.
	 */
	private int descriptionAdColumnId;

	/** Column name used as status target in tests (must exist in I_M_InOut and its jar). */
	private static final String STATUS_TARGET_COLUMN = I_M_InOut.COLUMNNAME_Description;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		service = ExternalSystemExportStatusService.newInstanceForUnitTesting(repo);

		// Create an I_AD_Column record for M_InOut.Description so that
		// InterfaceWrapperHelper.load(id, I_AD_Column.class) can resolve the column name
		// inside writeRollUpToSourceRecord. The service loads it by repo ID.
		final I_AD_Column col = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		col.setColumnName(STATUS_TARGET_COLUMN);
		InterfaceWrapperHelper.saveRecord(col);
		descriptionAdColumnId = col.getAD_Column_ID();
	}

	// -----------------------------------------------------------------------
	// Helper: create a minimal scripted-export config record for a given
	// AD_Table_ID.  statusAdColumnId <= 0 means "no status column" (no roll-up).
	// -----------------------------------------------------------------------
	private I_ExternalSystem_Config_ScriptedExportConversion createConfig(
			final int adTableId,
			final int statusAdColumnId)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_ScriptedExportConversion.class);
		cfg.setAD_Table_ID(adTableId);
		cfg.setStatus_AD_Column_ID(statusAdColumnId);
		cfg.setExternalSystemValue("test");
		cfg.setScriptIdentifier("test.js");
		cfg.setWhereClause("1=1");
		cfg.setIsTriggerOnComplete(true);
		cfg.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(cfg);
		return cfg;
	}

	// -----------------------------------------------------------------------
	// 1.  recordPending  →  row exists with status Pending
	// -----------------------------------------------------------------------
	@Test
	void recordPending_createsLogRow()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				createConfig(getM_InOutTableId(), 0);

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(101);
		service.recordPending(
				pInstanceId,
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID()),
				TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID()));

		final Optional<ExternalSystemExportStatusLogEntry> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// 2.  Pending → Enqueued → Sent  transition
	// -----------------------------------------------------------------------
	@Test
	void transitions_Pending_Enqueued_Sent()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				createConfig(getM_InOutTableId(), 0);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(201);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		service.recordPending(pInstanceId, configId, ref);
		service.recordEnqueued(pInstanceId);
		service.markSent(pInstanceId, 200);

		final Optional<ExternalSystemExportStatusLogEntry> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(entry.get().getHttpResponseCode()).isEqualTo(200);
	}

	// -----------------------------------------------------------------------
	// 3.  markError records Error status + issue ID + message
	// -----------------------------------------------------------------------
	@Test
	void markError_setsErrorStatus()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				createConfig(getM_InOutTableId(), 0);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(301);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		service.recordPending(pInstanceId, configId, ref);
		service.markError(pInstanceId, 42, "Something went wrong");

		final Optional<ExternalSystemExportStatusLogEntry> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(entry.get().getAdIssueId()).isEqualTo(42);
		assertThat(entry.get().getStatusMessage()).isEqualTo("Something went wrong");
	}

	// -----------------------------------------------------------------------
	// 4.  markInvalid records Invalid status
	// -----------------------------------------------------------------------
	@Test
	void markInvalid_setsInvalidStatus()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				createConfig(getM_InOutTableId(), 0);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(401);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		service.recordPending(pInstanceId, configId, ref);
		service.markInvalid(pInstanceId, "Bad data");

		final Optional<ExternalSystemExportStatusLogEntry> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Invalid);
		assertThat(entry.get().getStatusMessage()).isEqualTo("Bad data");
	}

	// -----------------------------------------------------------------------
	// 5.  Roll-up: Error beats in-flight beats Sent
	// -----------------------------------------------------------------------
	@Test
	void rollUp_Error_beats_inFlight_beats_Sent()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int m_InOutTableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		// Config A → Sent
		final I_ExternalSystem_Config_ScriptedExportConversion cfgA = createConfig(m_InOutTableId, 0);
		final PInstanceId pA = PInstanceId.ofRepoId(501);
		final ExternalSystemScriptedExportConversionConfigId idA =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgA.getExternalSystem_Config_ScriptedExportConversion_ID());
		service.recordPending(pA, idA, ref);
		service.markSent(pA, 200);

		// Config B → still Pending (in-flight)
		final I_ExternalSystem_Config_ScriptedExportConversion cfgB = createConfig(m_InOutTableId, 0);
		final PInstanceId pB = PInstanceId.ofRepoId(502);
		final ExternalSystemScriptedExportConversionConfigId idB =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgB.getExternalSystem_Config_ScriptedExportConversion_ID());
		service.recordPending(pB, idB, ref);

		// Config C → Error
		final I_ExternalSystem_Config_ScriptedExportConversion cfgC = createConfig(m_InOutTableId, 0);
		final PInstanceId pC = PInstanceId.ofRepoId(503);
		final ExternalSystemScriptedExportConversionConfigId idC =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgC.getExternalSystem_Config_ScriptedExportConversion_ID());
		service.recordPending(pC, idC, ref);
		service.markError(pC, 0, "oops");

		final List<ExternalSystemExportStatusLogEntry> rows =
				repo.getLatestBySourceRecord(ref);
		final ExternalSystemExportStatus rollUp = service.computeRollUp(rows);

		assertThat(rollUp).isEqualTo(ExternalSystemExportStatus.Error);
	}

	@Test
	void rollUp_inFlight_beats_Sent()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int m_InOutTableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfgA = createConfig(m_InOutTableId, 0);
		final PInstanceId pA = PInstanceId.ofRepoId(601);
		service.recordPending(pA,
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgA.getExternalSystem_Config_ScriptedExportConversion_ID()),
				ref);
		service.markSent(pA, 200);

		final I_ExternalSystem_Config_ScriptedExportConversion cfgB = createConfig(m_InOutTableId, 0);
		final PInstanceId pB = PInstanceId.ofRepoId(602);
		service.recordPending(pB,
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgB.getExternalSystem_Config_ScriptedExportConversion_ID()),
				ref);
		// pB stays Pending (in-flight)

		final List<ExternalSystemExportStatusLogEntry> rows = repo.getLatestBySourceRecord(ref);
		assertThat(service.computeRollUp(rows)).isEqualTo(ExternalSystemExportStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// 6.  Target-column write: when Status_AD_Column_ID is set the roll-up
	//     code is written into M_InOut.EPCIS_ExportStatus
	// -----------------------------------------------------------------------
	@Test
	void targetColumnWrite_whenColumnConfigured()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int m_InOutTableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		// Create config WITH a status column configured (using Description as proxy target)
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				createConfig(m_InOutTableId, descriptionAdColumnId);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(701);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		service.recordPending(pInstanceId, configId, ref);
		service.markSent(pInstanceId, 200);

		// Now reload the M_InOut record and verify the column was written (via dynamic read)
		final I_M_InOut reloaded = InterfaceWrapperHelper.load(inout.getM_InOut_ID(), I_M_InOut.class);
		assertThat(reloaded.getDescription()).isEqualTo(ExternalSystemExportStatus.Sent.getCode());
	}

	// -----------------------------------------------------------------------
	// 7.  No-op when Status_AD_Column_ID is 0 (column not configured)
	// -----------------------------------------------------------------------
	@Test
	void targetColumnWrite_noop_whenNoColumnConfigured()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int m_InOutTableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(m_InOutTableId, 0);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(801);

		service.recordPending(pInstanceId,
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID()),
				ref);
		service.markSent(pInstanceId, 200);

		// No exception; Description must remain null (roll-up was not written)
		final I_M_InOut reloaded = InterfaceWrapperHelper.load(inout.getM_InOut_ID(), I_M_InOut.class);
		assertThat(reloaded.getDescription()).isNull();
	}

	// -----------------------------------------------------------------------
	// 8.  AC-10 safety: no throw when pInstanceId has no matching log row
	// -----------------------------------------------------------------------
	@Test
	void markSent_noopAndNoThrow_whenNoMatchingLogRow()
	{
		final PInstanceId unknownPInstance = PInstanceId.ofRepoId(99999);
		assertThatCode(() -> service.markSent(unknownPInstance, 200))
				.doesNotThrowAnyException();
	}

	@Test
	void markError_noopAndNoThrow_whenNoMatchingLogRow()
	{
		final PInstanceId unknownPInstance = PInstanceId.ofRepoId(99998);
		assertThatCode(() -> service.markError(unknownPInstance, 0, "msg"))
				.doesNotThrowAnyException();
	}

	// -----------------------------------------------------------------------
	// 9.  Upsert semantics: second call for same pInstance updates, not inserts
	// -----------------------------------------------------------------------
	@Test
	void upsert_sameRow_onSecondCall()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				createConfig(getM_InOutTableId(), 0);
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(901);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		service.recordPending(pInstanceId, configId, ref);
		service.recordEnqueued(pInstanceId);

		// There should be exactly ONE log row for this pInstance
		final List<ExternalSystemExportStatusLogEntry> byConfig =
				repo.getByConfigId(configId);
		assertThat(byConfig).hasSize(1);
		assertThat(byConfig.get(0).getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/**
	 * Returns the M_InOut table ID via IADTableDAO so that we use the same
	 * JUnit-generated ID that the service will resolve at runtime.
	 * IADTableDAO.retrieveTableId auto-creates the table entry in unit-test mode.
	 */
	private int getM_InOutTableId()
	{
		return Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
	}
}
