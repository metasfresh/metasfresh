/*
 * #%L
 * de.metas.externalsystem
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
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the three status-write hooks wired into the scripted-export-conversion flow:
 * (a) Pending on AFTER_COMPLETE,
 * (b) Enqueued after successful invocation,
 * (c) Invalid on "no valid Resource" path.
 *
 * <p>We test at the service seam — the interceptor and service both delegate status writes
 * to the methods added here; testing those methods directly proves the writes work without
 * requiring a full process-execution stack.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ScriptedExportStatusWiringTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService statusService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		statusService = ExternalSystemExportStatusService.newInstanceForUnitTesting();
	}

	// -----------------------------------------------------------------------
	// (a) Pending — recordPending(configId, sourceRecord) produces a Pending
	//     log row and roll-up = Pending.
	// -----------------------------------------------------------------------
	@Test
	void recordPending_withoutPInstance_createsPendingRow()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());
		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId(), 0);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		// (a) — the interceptor hook
		statusService.recordPending(configId, sourceRecord);

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByConfigAndRecord(configId, sourceRecord);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);
		assertThat(entry.get().getPInstanceId()).isNull();
	}

	// -----------------------------------------------------------------------
	// (b) Enqueued — markEnqueued updates the Pending row
	//     with the PInstance and transitions status to Enqueued.
	// -----------------------------------------------------------------------
	@Test
	void markEnqueued_transitionsPendingToEnqueued()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());
		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId(), 0);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		// (a) pending first
		statusService.recordPending(configId, sourceRecord);

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(555);

		// (b) — the invocation service hook after successful enqueue
		statusService.markEnqueued(configId, sourceRecord, pInstanceId);

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);
		assertThat(entry.get().getPInstanceId()).isEqualTo(pInstanceId);
	}

	// -----------------------------------------------------------------------
	// (c) Invalid — markInvalidByRecord marks the Pending row Invalid.
	// -----------------------------------------------------------------------
	@Test
	void markInvalidByRecord_transitionsPendingToInvalid()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());
		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId(), 0);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		// (a) pending first
		statusService.recordPending(configId, sourceRecord);

		// (c) — the invocation service hook on "no valid Resource" path
		statusService.markInvalidByRecord(configId, sourceRecord, "Process did not return a valid Resource");

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByConfigAndRecord(configId, sourceRecord);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Invalid);
		assertThat(entry.get().getStatusMessage()).isEqualTo("Process did not return a valid Resource");
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private I_ExternalSystem_Config_ScriptedExportConversion createConfig(final int adTableId, @SuppressWarnings("unused") final int statusAdColumnId)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_ScriptedExportConversion.class);
		cfg.setAD_Table_ID(adTableId);		cfg.setExternalSystemValue("test");
		cfg.setScriptIdentifier("test.js");
		cfg.setWhereClause("1=1");
		cfg.setIsTriggerOnComplete(true);
		cfg.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(cfg);
		return cfg;
	}

	private int getM_InOutTableId()
	{
		return Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
	}
}
