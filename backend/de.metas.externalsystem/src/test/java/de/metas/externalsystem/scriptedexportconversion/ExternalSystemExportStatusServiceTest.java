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

import de.metas.error.AdIssueId;
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
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemExportStatusServiceTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		service = ExternalSystemExportStatusService.newInstanceForUnitTesting();
	}

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

	private TableRecordReference newInOutRef()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		return TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());
	}

	private ExternalSystemScriptedExportConversionConfigId newConfigId()
	{
		final I_ExternalSystem_Config_ScriptedExportConversion cfg = createConfig(getM_InOutTableId());
		return ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());
	}

	// -----------------------------------------------------------------------
	// recordDontSend → terminal DontSend
	// -----------------------------------------------------------------------
	@Test
	void recordDontSend_setsDontSendStatus()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		service.recordDontSend(configId, ref);

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByConfigAndRecord(configId, ref);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.DontSend);
	}

	// -----------------------------------------------------------------------
	// recordPending → Pending
	// -----------------------------------------------------------------------
	@Test
	void recordPending_createsPendingRow()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		service.recordPending(configId, ref);

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByConfigAndRecord(configId, ref);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// Pending → Enqueued → Sent
	// -----------------------------------------------------------------------
	@Test
	void transitions_Pending_Enqueued_Sent()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(201);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markSent(pInstanceId, HttpStatus.OK);

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(entry.get().getHttpResponseCode()).isEqualTo(HttpStatus.OK);
	}

	// -----------------------------------------------------------------------
	// markEnqueued — at-most-one-in-flight guard: second call must not re-enqueue
	// -----------------------------------------------------------------------
	@Test
	void markEnqueued_atMostOneInFlight_guardsAgainstDoubleEnqueue()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId first = PInstanceId.ofRepoId(701);
		final PInstanceId second = PInstanceId.ofRepoId(702);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, first);
		// already in-flight (Enqueued, not Pending) → second markEnqueued is a no-op
		service.markEnqueued(configId, ref, second);

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByConfigAndRecord(configId, ref);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);
		assertThat(entry.get().getPInstanceId()).isEqualTo(first);
	}

	// -----------------------------------------------------------------------
	// markError records Error + issue ID + message
	// -----------------------------------------------------------------------
	@Test
	void markError_setsErrorStatus()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(301);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markError(pInstanceId, AdIssueId.ofRepoId(42), "Something went wrong");

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(entry.get().getAdIssueId()).isEqualTo(AdIssueId.ofRepoId(42));
		assertThat(entry.get().getStatusMessage()).isEqualTo("Something went wrong");
	}

	// -----------------------------------------------------------------------
	// markInvalid records Invalid
	// -----------------------------------------------------------------------
	@Test
	void markInvalid_setsInvalidStatus()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(401);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markInvalid(pInstanceId, "Bad data");

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Invalid);
		assertThat(entry.get().getStatusMessage()).isEqualTo("Bad data");
	}

	// -----------------------------------------------------------------------
	// markInvalidByRecord — pre-send revalidation failure (no pInstance)
	// -----------------------------------------------------------------------
	@Test
	void markInvalidByRecord_setsInvalidStatus()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		service.recordPending(configId, ref);
		service.markInvalidByRecord(configId, ref, "revalidation failed");

		final Optional<ScriptedExportConversionStatus> entry = repo.getLatestByConfigAndRecord(configId, ref);
		assertThat(entry).isPresent();
		assertThat(entry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Invalid);
	}

	// -----------------------------------------------------------------------
	// Roll-up: Error beats in-flight beats Sent
	// -----------------------------------------------------------------------
	@Test
	void rollUp_Error_beats_inFlight_beats_Sent()
	{
		final TableRecordReference ref = newInOutRef();

		final ExternalSystemScriptedExportConversionConfigId idA = newConfigId();
		final PInstanceId pA = PInstanceId.ofRepoId(501);
		service.recordPending(idA, ref);
		service.markEnqueued(idA, ref, pA);
		service.markSent(pA, HttpStatus.OK);

		final ExternalSystemScriptedExportConversionConfigId idB = newConfigId();
		service.recordPending(idB, ref);

		final ExternalSystemScriptedExportConversionConfigId idC = newConfigId();
		final PInstanceId pC = PInstanceId.ofRepoId(503);
		service.recordPending(idC, ref);
		service.markEnqueued(idC, ref, pC);
		service.markError(pC, AdIssueId.ofRepoId(9), "oops");

		final List<ScriptedExportConversionStatus> rows = repo.getLatestBySourceRecord(ref);
		assertThat(service.computeRollUp(rows)).isEqualTo(ExternalSystemExportStatus.Error);
	}

	@Test
	void rollUp_inFlight_beats_Sent()
	{
		final TableRecordReference ref = newInOutRef();

		final ExternalSystemScriptedExportConversionConfigId idA = newConfigId();
		final PInstanceId pA = PInstanceId.ofRepoId(601);
		service.recordPending(idA, ref);
		service.markEnqueued(idA, ref, pA);
		service.markSent(pA, HttpStatus.OK);

		final ExternalSystemScriptedExportConversionConfigId idB = newConfigId();
		service.recordPending(idB, ref);

		final List<ScriptedExportConversionStatus> rows = repo.getLatestBySourceRecord(ref);
		assertThat(service.computeRollUp(rows)).isEqualTo(ExternalSystemExportStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// No-op / no-throw when pInstanceId has no matching status row
	// -----------------------------------------------------------------------
	@Test
	void markSent_noopAndNoThrow_whenNoMatchingLogRow()
	{
		final PInstanceId unknownPInstance = PInstanceId.ofRepoId(99999);
		assertThatCode(() -> service.markSent(unknownPInstance, HttpStatus.OK))
				.doesNotThrowAnyException();
	}

	@Test
	void markError_noopAndNoThrow_whenNoMatchingLogRow()
	{
		final PInstanceId unknownPInstance = PInstanceId.ofRepoId(99998);
		assertThatCode(() -> service.markError(unknownPInstance, null, "msg"))
				.doesNotThrowAnyException();
	}

	// -----------------------------------------------------------------------
	// Upsert semantics: second transition on same key keeps a single row
	// -----------------------------------------------------------------------
	@Test
	void upsert_sameRow_onSecondCall()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(901);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);

		final List<ScriptedExportConversionStatus> byConfig = repo.getByConfigId(configId);
		assertThat(byConfig).hasSize(1);
		assertThat(byConfig.get(0).getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);
	}

	private int getM_InOutTableId()
	{
		return Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
	}
}
