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
import de.metas.externalsystem.ExternalSystemInvocationContext;
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
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD tests for {@link M_InOut_ReSend_ScriptedExportConversion} process logic.
 * <p>
 * Covers:
 * <ul>
 *   <li>repo.getConfigsWithNonSentAttemptBySourceRecord: returns distinct config(s) with a non-sent attempt (status not Sent/DontSend); the re-send selection (Error/Invalid/DontSend, plus an operator-parked PInstance-stamped Pending) is covered at the service layer in ExternalSystemExportStatusServiceTest</li>
 *   <li>recordPendingAsResend: appends a NEW Pending+IsResend=Y attempt row, keeping the prior attempt (per-attempt history)</li>
 *   <li>multi-config: both configs returned when both have Error or Invalid status</li>
 *   <li>sent-only: config not returned when latest attempt is Sent</li>
 *   <li>RESEND error context value: exists and returns non-null code</li>
 * </ul>
 */
@ExtendWith(AdempiereTestWatcher.class)
public class M_InOut_ReSend_ScriptedExportConversionTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final IErrorManager errorManager = Mockito.mock(IErrorManager.class);
		Mockito.when(errorManager.createIssue(Mockito.any(IssueCreateRequest.class))).thenReturn(AdIssueId.ofRepoId(8888));
		SpringContextHolder.registerJUnitBean(IErrorManager.class, errorManager);

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		service = ExternalSystemExportStatusService.newInstanceForUnitTesting();
	}

	// -----------------------------------------------------------------------
	// Helper
	// -----------------------------------------------------------------------

	private I_ExternalSystem_Config_ScriptedExportConversion createConfig(final int adTableId)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_ScriptedExportConversion.class);
		cfg.setAD_Table_ID(adTableId);
		cfg.setExternalSystemValue("test");
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

	// -----------------------------------------------------------------------
	// 1. getConfigsWithNonSentAttemptBySourceRecord: returns config with Error attempt
	// -----------------------------------------------------------------------
	@Test
	void getConfigsWithNonSentAttempt_returnsConfig_whenErrorAttemptExists()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int tableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(tableId);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		// Simulate: initial attempt => Error
		final PInstanceId pInstance = PInstanceId.ofRepoId(1001);
		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstance);
		service.markError(pInstance, null, "Connection refused");

		final List<ExternalSystemScriptedExportConversionConfigId> nonSentConfigs =
				repo.getConfigsWithNonSentAttemptBySourceRecord(ref);

		assertThat(nonSentConfigs).containsExactly(configId);
	}

	// -----------------------------------------------------------------------
	// 2. getConfigsWithNonSentAttemptBySourceRecord: does NOT return Sent config
	// -----------------------------------------------------------------------
	@Test
	void getConfigsWithNonSentAttempt_excludes_sentConfig()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int tableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(tableId);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		// Simulate: attempt => Sent
		final PInstanceId pInstance = PInstanceId.ofRepoId(2001);
		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstance);
		service.markSent(pInstance, HttpStatus.OK);

		final List<ExternalSystemScriptedExportConversionConfigId> nonSentConfigs =
				repo.getConfigsWithNonSentAttemptBySourceRecord(ref);

		assertThat(nonSentConfigs).isEmpty();
	}

	// -----------------------------------------------------------------------
	// 3. getConfigsWithNonSentAttemptBySourceRecord: multi-config — both returned
	// -----------------------------------------------------------------------
	@Test
	void getConfigsWithNonSentAttempt_returnsMultipleConfigs_whenBothHaveNonSentAttempts()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int tableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfgA = createConfig(tableId);
		final ExternalSystemScriptedExportConversionConfigId configIdA =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgA.getExternalSystem_Config_ScriptedExportConversion_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfgB = createConfig(tableId);
		final ExternalSystemScriptedExportConversionConfigId configIdB =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfgB.getExternalSystem_Config_ScriptedExportConversion_ID());

		// Config A: Error
		final PInstanceId pA = PInstanceId.ofRepoId(3001);
		service.recordPending(configIdA, ref);
		service.markEnqueued(configIdA, ref, pA);
		service.markError(pA, null, "Error A");

		// Config B: Invalid (also non-Sent)
		final PInstanceId pB = PInstanceId.ofRepoId(3002);
		service.recordPending(configIdB, ref);
		service.markEnqueued(configIdB, ref, pB);
		service.markInvalid(pB, "Invalid B");

		final List<ExternalSystemScriptedExportConversionConfigId> nonSentConfigs =
				repo.getConfigsWithNonSentAttemptBySourceRecord(ref);

		assertThat(nonSentConfigs).containsExactlyInAnyOrder(configIdA, configIdB);
	}

	// -----------------------------------------------------------------------
	// 4. recordPendingAsResend: appends a NEW Pending+IsResend=Y attempt, keeping the prior attempt
	// -----------------------------------------------------------------------
	@Test
	void recordPendingAsResend_addsNewPendingResendAttempt_keepingPriorError()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int tableId = getM_InOutTableId();
		final TableRecordReference ref = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());

		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(tableId);
		final ExternalSystemScriptedExportConversionConfigId configId =
				ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());

		// Prior attempt => Error
		final PInstanceId pInstance = PInstanceId.ofRepoId(4001);
		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstance);
		service.markError(pInstance, null, "prior error");

		// Exactly 1 row, in Error state before re-send
		assertThat(repo.getByConfigId(configId)).hasSize(1);
		assertThat(repo.getByConfigId(configId).get(0).getStatus()).isEqualTo(ExternalSystemExportStatus.Error);

		// Call recordPendingAsResend
		service.recordPendingAsResend(configId, ref);

		// Per-attempt contract: a NEW attempt row is added; the prior Error attempt is KEPT as history —
		// so two rows now coexist (was a single in-place flip under the former single-row design).
		final List<ScriptedExportConversionStatus> rows = repo.getByConfigId(configId);
		assertThat(rows).hasSize(2);

		// the prior Error attempt is preserved
		assertThat(rows).anyMatch(r -> r.getStatus() == ExternalSystemExportStatus.Error);

		// the LATEST attempt is the new Pending + IsResend=Y
		final ScriptedExportConversionStatus latest = repo.getLatestByConfigAndRecord(configId, ref).orElse(null);
		assertThat(latest).isNotNull();
		assertThat(latest.getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);
		assertThat(latest.isResend()).isTrue();
	}

	// -----------------------------------------------------------------------
	// 5. RESEND error context: value exists and has a non-null code
	// -----------------------------------------------------------------------
	@Test
	void externalSystemErrorContext_hasResendValue()
	{
		// This will fail until RESEND is added to the enum
		assertThat(ExternalSystemInvocationContext.RESEND).isNotNull();
		assertThat(ExternalSystemInvocationContext.RESEND.getCode()).isNotBlank();
	}
}
