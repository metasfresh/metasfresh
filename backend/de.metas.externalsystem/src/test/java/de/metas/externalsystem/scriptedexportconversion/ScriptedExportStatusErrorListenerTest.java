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

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Tests for {@link ScriptedExportStatusErrorListener}.
 * <p>
 * AC-10: the listener must be a no-op (no throw) when there is no matching log row.
 * Main path: when a pInstanceId has a matching Enqueued log row, the listener must transition
 * the row to Error, set the message, and link the AD_Issue created via {@link IErrorManager}.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ScriptedExportStatusErrorListenerTest
{
	private static final AdIssueId STUB_AD_ISSUE_ID = AdIssueId.ofRepoId(8888);

	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService statusService;
	private ScriptedExportStatusErrorListener listener;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final IErrorManager errorManager = Mockito.mock(IErrorManager.class);
		Mockito.when(errorManager.createIssue(Mockito.any(IssueCreateRequest.class))).thenReturn(STUB_AD_ISSUE_ID);
		SpringContextHolder.registerJUnitBean(IErrorManager.class, errorManager);

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		statusService = ExternalSystemExportStatusService.newInstanceForUnitTesting();
		listener = new ScriptedExportStatusErrorListener(statusService);
	}

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
	// Main path: Enqueued row → Error + message stored + AD_Issue (via IErrorManager) linked
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
		statusService.markEnqueued(configId, sourceRecord, pInstanceId);

		final Optional<ScriptedExportConversionStatus> before = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(before).isPresent();
		assertThat(before.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);

		listener.onInvocationError(pInstanceId, ExternalSystemErrorContext.UNKNOWN, "Something went wrong");

		final Optional<ScriptedExportConversionStatus> after = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(after).isPresent();
		assertThat(after.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(after.get().getStatusMessage()).isEqualTo("Something went wrong");
		assertThat(after.get().getAdIssueId())
				.as("Status row must be linked to the AD_Issue created via IErrorManager")
				.isEqualTo(STUB_AD_ISSUE_ID);
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
