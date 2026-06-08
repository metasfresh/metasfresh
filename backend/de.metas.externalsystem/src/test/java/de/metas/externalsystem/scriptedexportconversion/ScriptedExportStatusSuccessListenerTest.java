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

import de.metas.externalsystem.ExternalSystemErrorContext;
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
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests for {@link ScriptedExportStatusSuccessListener}.
 * <p>
 * AC-10: the listener must be a no-op (no throw) when there is no matching log row.
 * Main path: when a pInstanceId has a matching Enqueued log row,
 * the listener must transition the row to Sent and store the httpResponseCode.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ScriptedExportStatusSuccessListenerTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService statusService;
	private ScriptedExportStatusSuccessListener listener;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		statusService = ExternalSystemExportStatusService.newInstanceForUnitTesting(repo);
		listener = new ScriptedExportStatusSuccessListener(statusService);
	}

	// -----------------------------------------------------------------------
	// applies() — the listener must apply to every context (uses pInstanceId
	// lookup; context is not used for filtering — mirrors error listener).
	// -----------------------------------------------------------------------
	@Test
	void applies_trueForAllContexts()
	{
		for (final ExternalSystemErrorContext ctx : ExternalSystemErrorContext.values())
		{
			assertThat(listener.applies(ctx))
					.as("applies() must return true for context %s", ctx)
					.isTrue();
		}
	}

	// -----------------------------------------------------------------------
	// Main path: Enqueued log row → Sent + httpResponseCode stored
	// -----------------------------------------------------------------------
	@Test
	void onInvocationSuccess_setsSentStatus_andHttpResponseCode_whenMatchingLogRowExists()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId());
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(2001);
		statusService.recordPending(configId, sourceRecord);
		statusService.bindPInstanceAndMarkEnqueued(configId, sourceRecord, pInstanceId);

		// precondition: row is Enqueued
		final Optional<ExternalSystemExportStatusLogEntry> before = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(before).isPresent();
		assertThat(before.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);

		// act
		listener.onInvocationSuccess(pInstanceId, ExternalSystemErrorContext.UNKNOWN, 200);

		// assert: status=Sent, httpResponseCode stored
		final Optional<ExternalSystemExportStatusLogEntry> after = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(after).isPresent();
		assertThat(after.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(after.get().getHttpResponseCode())
				.as("HttpResponseCode must be stored on the log row")
				.isEqualTo(200);
	}

	// -----------------------------------------------------------------------
	// Roll-up: Sent transitions roll-up to Sent when it's the only entry
	// -----------------------------------------------------------------------
	@Test
	void onInvocationSuccess_rollUpIsSent_whenOnlyOneEntry()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId());
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(2002);
		statusService.recordPending(configId, sourceRecord);
		statusService.bindPInstanceAndMarkEnqueued(configId, sourceRecord, pInstanceId);

		listener.onInvocationSuccess(pInstanceId, ExternalSystemErrorContext.UNKNOWN, 201);

		final Optional<ExternalSystemExportStatusLogEntry> after = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(after).isPresent();
		assertThat(after.get().getStatus())
				.as("Single-entry roll-up must be Sent after success")
				.isEqualTo(ExternalSystemExportStatus.Sent);
	}

	// -----------------------------------------------------------------------
	// AC-10: no matching log row → no-op, no throw
	// -----------------------------------------------------------------------
	@Test
	void onInvocationSuccess_noopAndNoThrow_whenNoMatchingLogRow()
	{
		final PInstanceId unknownPInstance = PInstanceId.ofRepoId(99998);
		assertThatCode(() -> listener.onInvocationSuccess(
				unknownPInstance,
				ExternalSystemErrorContext.UNKNOWN,
				200))
				.doesNotThrowAnyException();
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private I_ExternalSystem_Config_ScriptedExportConversion createConfig(final int adTableId)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_ScriptedExportConversion.class);
		cfg.setAD_Table_ID(adTableId);
		cfg.setStatus_AD_Column_ID(0);
		cfg.setExternalSystemValue("test-success-listener");
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
