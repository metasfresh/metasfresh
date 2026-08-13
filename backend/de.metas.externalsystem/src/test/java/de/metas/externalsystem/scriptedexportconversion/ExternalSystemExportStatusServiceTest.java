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
	// A transition (markEnqueued) updates the SAME attempt row — one enqueue, one row
	// -----------------------------------------------------------------------
	@Test
	void transition_updatesSameAttemptRow()
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

	// -----------------------------------------------------------------------
	// getResendableConfigsBySourceRecord — Error/Invalid/DontSend, plus an operator-parked
	// (PInstance-stamped) Pending; a transient auto-flow Pending is excluded
	// -----------------------------------------------------------------------

	/**
	 * A config whose latest attempt is an OPERATOR-PARKED Pending — one carrying an AD_PInstance, set via
	 * the "Change EPCIS Export Status" action — MUST be returned by the re-send selection: nothing is in
	 * flight, so a re-send is the first send. An operator parks a stuck shipment in Pending and must then
	 * be able to Re-send it.
	 */
	@Test
	void getResendableConfigs_includes_manuallyParkedPendingConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		// operator parks it in Pending via the Change action -> stamped with the process PInstance
		service.recordManualStatusChange(configId, ref, ExternalSystemExportStatus.Pending, PInstanceId.ofRepoId(1201));

		assertThat(service.getResendableConfigsBySourceRecord(ref)).containsExactly(configId);
	}

	/**
	 * A config whose latest attempt is a TRANSIENT auto-flow Pending — no AD_PInstance, the momentary
	 * state the normal export flow writes just before flipping to Enqueued — must NOT be returned: it is
	 * already on its way to being sent, so offering it for re-send would double-send. Only an
	 * operator-parked Pending (with a PInstance) is resendable.
	 */
	@Test
	void getResendableConfigs_excludes_transientPendingConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		service.recordPending(configId, ref); // auto-flow Pending, no PInstance

		assertThat(service.getResendableConfigsBySourceRecord(ref)).isEmpty();
	}

	/**
	 * Per-attempt history: a config whose OLDER attempt errored but whose LATEST attempt succeeded
	 * (a re-send that worked) must NOT be offered for re-send — otherwise the manual Re-send process
	 * would re-trigger an export that already delivered successfully.
	 */
	@Test
	void getResendableConfigs_excludesConfigWhoseLatestAttemptSucceeded()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		// older attempt errored ...
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.Error).build());
		// ... but the LATEST attempt (a successful re-send) is Sent
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.Sent).build());

		assertThat(service.getResendableConfigsBySourceRecord(ref)).isEmpty();
	}

	/**
	 * A config with an Error status must be returned so the re-send process can retry it.
	 */
	@Test
	void getResendableConfigs_includes_errorConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1101);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markError(pInstanceId, AdIssueId.ofRepoId(99), "connection refused");

		final List<ExternalSystemScriptedExportConversionConfigId> result =
				service.getResendableConfigsBySourceRecord(ref);

		assertThat(result).containsExactly(configId);
	}

	/**
	 * A config with an Invalid status must be returned so the re-send process can retry it.
	 */
	@Test
	void getResendableConfigs_includes_invalidConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1201);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markInvalid(pInstanceId, "bad data");

		final List<ExternalSystemScriptedExportConversionConfigId> result =
				service.getResendableConfigsBySourceRecord(ref);

		assertThat(result).containsExactly(configId);
	}

	/**
	 * A config whose latest attempt is DontSend ("shall not be sent" — e.g. suppressed because
	 * everything was already in the ledger) must ALSO be resendable, so a re-send can re-evaluate it.
	 */
	@Test
	void getResendableConfigs_includes_dontSendConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		service.recordDontSend(configId, ref);

		final List<ExternalSystemScriptedExportConversionConfigId> result =
				service.getResendableConfigsBySourceRecord(ref);

		assertThat(result).containsExactly(configId);
	}

	// -----------------------------------------------------------------------
	// getMatchingConfigIdsBySourceRecord — the force-resend selection (Re-send process with
	// IsOnlyNotSentSuccessfully=N): everything except DontSend and the actively-in-flight attempts
	// -----------------------------------------------------------------------

	/**
	 * The force-resend mode re-triggers an already-delivered export on purpose (e.g. the external system
	 * lost its data), so a config whose latest attempt is Sent must be offered.
	 */
	@Test
	void getMatchingConfigIds_includes_sentConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1301);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markSent(pInstanceId, HttpStatus.OK);

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).containsExactly(configId);
	}

	/**
	 * An OPERATOR-PARKED Pending — one carrying an AD_PInstance, set via the "Change EPCIS Export Status"
	 * action — is not in flight, so the force-resend mode must offer it too. Otherwise the broader mode
	 * would send LESS than the not-yet-sent-only mode ({@code getResendableConfigsBySourceRecord}, which
	 * includes it): an operator who parks a shipment in Pending and then unchecks "only not-yet-sent"
	 * would see it silently skipped.
	 */
	@Test
	void getMatchingConfigIds_includes_manuallyParkedPendingConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		// operator parks it in Pending via the Change action -> stamped with the process PInstance
		service.recordManualStatusChange(configId, ref, ExternalSystemExportStatus.Pending, PInstanceId.ofRepoId(1401));

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).containsExactly(configId);
	}

	/**
	 * A TRANSIENT auto-flow Pending — no AD_PInstance, the momentary state the normal export flow writes
	 * just before flipping to Enqueued — is on its way out and must NOT be offered: re-sending it would
	 * double-send.
	 */
	@Test
	void getMatchingConfigIds_excludes_transientPendingConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		service.recordPending(configId, ref); // auto-flow Pending, no PInstance

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).isEmpty();
	}

	/**
	 * Per-attempt history: only the LATEST attempt decides. A config whose latest attempt is in-flight
	 * (Enqueued) must NOT be offered even though an OLDER attempt is Sent — re-sending while the current
	 * attempt is still on the wire double-sends.
	 */
	@Test
	void getMatchingConfigIds_excludesConfigWhoseLatestAttemptIsInFlight()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1501);

		// older attempt was delivered ...
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.Sent).build());
		// ... but the LATEST attempt is still in flight
		service.recordPendingAsResend(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).isEmpty();
	}

	/**
	 * Per-attempt history: a config whose LATEST attempt is DontSend must NOT be offered even though an
	 * OLDER attempt is Sent — DontSend means the WhereClause excluded this record.
	 */
	@Test
	void getMatchingConfigIds_excludesConfigWhoseLatestAttemptIsDontSend()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		// older attempt was delivered ...
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.Sent).build());
		// ... but the LATEST attempt was suppressed
		service.recordDontSend(configId, ref);

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).isEmpty();
	}

	/**
	 * An errored config is not in flight and was not delivered, so the force-resend mode must offer it too —
	 * it is a superset of the not-yet-sent-only mode in everything but DontSend.
	 */
	@Test
	void getMatchingConfigIds_includes_errorConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1601);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markError(pInstanceId, AdIssueId.ofRepoId(99), "connection refused");

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).containsExactly(configId);
	}

	/**
	 * Same for an Invalid config — nothing is in flight, so it may be re-triggered.
	 */
	@Test
	void getMatchingConfigIds_includes_invalidConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1701);

		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, pInstanceId);
		service.markInvalid(pInstanceId, "bad data");

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).containsExactly(configId);
	}

	/**
	 * SendingStarted is the second actively-in-flight state (dispatched, awaiting the external system's
	 * callback) and must be excluded for the same reason as Enqueued: re-triggering would double-send.
	 */
	@Test
	void getMatchingConfigIds_excludes_sendingStartedConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.SendingStarted).build());

		assertThat(service.getMatchingConfigIdsBySourceRecord(ref)).isEmpty();
	}

	// -----------------------------------------------------------------------
	// recordManualStatusChange — writes a NEW, PInstance-stamped attempt row; prior rows are history
	// -----------------------------------------------------------------------

	/**
	 * A manual status change (the "Change EPCIS Export Status" process) must append a NEW attempt row
	 * stamped with the process PInstance (who/when audit) and leave the prior attempt untouched.
	 */
	@Test
	void recordManualStatusChange_appendsNewStampedRow_priorAttemptUntouched()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();
		final PInstanceId sendP = PInstanceId.ofRepoId(1301);
		final PInstanceId manualP = PInstanceId.ofRepoId(1302);

		// a prior, successfully-sent attempt ...
		service.recordPending(configId, ref);
		service.markEnqueued(configId, ref, sendP);
		service.markSent(sendP, HttpStatus.OK);

		// ... then an operator sets it to DontSend via the process
		service.recordManualStatusChange(configId, ref, ExternalSystemExportStatus.DontSend, manualP);

		final List<ScriptedExportConversionStatus> rows = repo.getByConfigId(configId);
		assertThat(rows).hasSize(2); // prior Sent + the new DontSend — nothing overwritten

		final ScriptedExportConversionStatus latest = repo.getLatestByConfigAndRecord(configId, ref).get();
		assertThat(latest.getStatus()).isEqualTo(ExternalSystemExportStatus.DontSend);
		assertThat(latest.getPInstanceId()).isEqualTo(manualP);
	}

	// -----------------------------------------------------------------------
	// getLatestStatusesBySourceRecord — MUST dedupe to the latest attempt PER config
	// -----------------------------------------------------------------------

	/**
	 * REGRESSION: getLatestStatusesBySourceRecord must return exactly ONE row per config — the newest
	 * attempt — not the whole per-attempt history. A config that errored and was then re-sent
	 * successfully (>=2 rows) must report only its latest (Sent); returning both rows made the WebUI
	 * "from status" ambiguous (2 distinct statuses -> null) and disabled the change process.
	 */
	@Test
	void getLatestStatusesBySourceRecord_dedupesToLatestAttemptPerConfig()
	{
		final TableRecordReference ref = newInOutRef();
		final ExternalSystemScriptedExportConversionConfigId configId = newConfigId();

		// older attempt errored ...
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.Error).build());
		// ... latest attempt (a successful re-send) is Sent
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId).sourceRecord(ref).status(ExternalSystemExportStatus.Sent).build());

		final List<ScriptedExportConversionStatus> latest = service.getLatestStatusesBySourceRecord(ref);
		assertThat(latest).hasSize(1);
		assertThat(latest.get(0).getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
	}

	/**
	 * With several configs each carrying multiple attempts, getLatestStatusesBySourceRecord returns one
	 * row per config, each being that config's newest attempt.
	 */
	@Test
	void getLatestStatusesBySourceRecord_onePerConfig_acrossConfigs()
	{
		final TableRecordReference ref = newInOutRef();

		// config A: errored then re-sent Sent
		final ExternalSystemScriptedExportConversionConfigId idA = newConfigId();
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(idA).sourceRecord(ref).status(ExternalSystemExportStatus.Error).build());
		repo.insertNewAttempt(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(idA).sourceRecord(ref).status(ExternalSystemExportStatus.Sent).build());

		// config B: single Pending attempt
		final ExternalSystemScriptedExportConversionConfigId idB = newConfigId();
		service.recordPending(idB, ref);

		final List<ScriptedExportConversionStatus> latest = service.getLatestStatusesBySourceRecord(ref);
		assertThat(latest).hasSize(2);
		assertThat(latest).extracting(ScriptedExportConversionStatus::getStatus)
				.containsExactlyInAnyOrder(ExternalSystemExportStatus.Sent, ExternalSystemExportStatus.Pending);
	}

	private int getM_InOutTableId()
	{
		return Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
	}
}
