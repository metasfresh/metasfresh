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
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests for {@link ScriptedExportStatusErrorListener}.
 * <p>
 * AC-10: the listener must be a no-op (no throw) when there is no matching log row.
 * Main path: when a pInstanceId has a matching Enqueued log row and an AD_Issue exists
 * for that pInstanceId, the listener must transition the row to Error, set the message,
 * and link the AD_Issue_ID.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ScriptedExportStatusErrorListenerTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService statusService;
	private ScriptedExportStatusErrorListener listener;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		statusService = ExternalSystemExportStatusService.newInstanceForUnitTesting(repo);
		listener = new ScriptedExportStatusErrorListener(statusService);
	}

	// -----------------------------------------------------------------------
	// applies() — the listener must apply to every error context (it uses
	// pInstanceId lookup to decide if it owns the row, not the context code).
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
	// Main path: Enqueued log row → Error + message stored + AD_Issue linked
	// -----------------------------------------------------------------------
	@Test
	void onInvocationError_setsErrorStatus_andLinksAdIssue_whenMatchingLogRowExists()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId());
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1001);
		statusService.recordPending(configId, sourceRecord);
		statusService.bindPInstanceAndMarkEnqueued(configId, sourceRecord, pInstanceId);

		// precondition: row is Enqueued
		final Optional<ExternalSystemExportStatusLogEntry> before = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(before).isPresent();
		assertThat(before.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);

		// Simulate ExternalSystemService.createIssue() creating an AD_Issue stamped with pInstanceId.
		// This mirrors the production path: the issue is created BEFORE the listener is called.
		final I_AD_Issue adIssue = InterfaceWrapperHelper.newInstance(I_AD_Issue.class);
		adIssue.setAD_PInstance_ID(pInstanceId.getRepoId());
		adIssue.setIssueSummary("Something went wrong");
		adIssue.setIssueCategory("O");
		InterfaceWrapperHelper.saveRecord(adIssue);
		final int expectedAdIssueId = adIssue.getAD_Issue_ID();

		// act
		listener.onInvocationError(pInstanceId, ExternalSystemErrorContext.UNKNOWN, "Something went wrong");

		// assert: status=Error, message set, and AD_Issue linked
		final Optional<ExternalSystemExportStatusLogEntry> after = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(after).isPresent();
		assertThat(after.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(after.get().getStatusMessage()).isEqualTo("Something went wrong");
		assertThat(after.get().getAdIssueId())
				.as("Log row must be linked to the AD_Issue created for this pInstanceId")
				.isEqualTo(expectedAdIssueId);
	}

	// -----------------------------------------------------------------------
	// Fallback: no AD_Issue exists for pInstanceId → adIssueId stays 0 (no throw)
	// -----------------------------------------------------------------------
	@Test
	void onInvocationError_setsErrorStatus_adIssueZero_whenNoAdIssueExists()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId());
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1002);
		statusService.recordPending(configId, sourceRecord);
		statusService.bindPInstanceAndMarkEnqueued(configId, sourceRecord, pInstanceId);

		// No AD_Issue created for this pInstanceId — fallback behaviour: adIssueId=0, no throw

		listener.onInvocationError(pInstanceId, ExternalSystemErrorContext.UNKNOWN, "Some error");

		final Optional<ExternalSystemExportStatusLogEntry> after = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(after).isPresent();
		assertThat(after.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(after.get().getAdIssueId())
				.as("No AD_Issue exists for this pInstanceId — adIssueId must remain 0")
				.isZero();
	}

	// -----------------------------------------------------------------------
	// AC-10: no matching log row → no-op, no throw
	// -----------------------------------------------------------------------
	@Test
	void onInvocationError_noopAndNoThrow_whenNoMatchingLogRow()
	{
		final PInstanceId unknownPInstance = PInstanceId.ofRepoId(99997);
		assertThatCode(() -> listener.onInvocationError(
				unknownPInstance,
				ExternalSystemErrorContext.UNKNOWN,
				"Some error"))
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
		cfg.setExternalSystemValue("test-error-listener");
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
