/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_VATaxID_CheckLog;
import org.compiere.model.X_VATaxID_CheckLog;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link VATaxIDCheckRepository}.
 *
 * <p>Covers both paths named in the task's "Done when": {@link VATaxIDCheckRepository#writeRequestSent}
 * appends a {@link VATaxIDStatus#RequestSent} row, and {@link VATaxIDCheckRepository#completeCheck}
 * updates exactly that row to its final status — leaving a second, unrelated row untouched — and refuses
 * to run a second time on an already-completed row, which is what makes the "append-only except one
 * transition" lifecycle actually hold.
 */
class VATaxIDCheckRepositoryTest
{
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(1000001);
	private static final BPartnerId OTHER_BPARTNER_ID = BPartnerId.ofRepoId(1000002);

	private VATaxIDCheckRepository vataxIDCheckRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		vataxIDCheckRepository = VATaxIDCheckRepository.newInstanceForUnitTesting();
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	private I_VATaxID_CheckLog loadRecord(final VATaxIDCheckLogId id)
	{
		return InterfaceWrapperHelper.load(id, I_VATaxID_CheckLog.class);
	}

	@Test
	void writeRequestSent_appendsARequestSentRow_withTheCauseIdsPersisted()
	{
		final VATaxIDCheckRequest request = VATaxIDCheckRequest.builder()
				.bpartnerId(BPARTNER_ID)
				.vataxID("DE123456789")
				.pinstanceId(PInstanceId.ofRepoId(2000001))
				.adSessionId(3000001)
				.build();

		final VATaxIDCheckLogId checkLogId = vataxIDCheckRepository.writeRequestSent(request);

		final I_VATaxID_CheckLog record = loadRecord(checkLogId);
		assertThat(record.getC_BPartner_ID()).isEqualTo(BPARTNER_ID.getRepoId());
		assertThat(record.getC_BPartner_Location_ID()).isEqualTo(-1);
		assertThat(record.getVATaxID()).isEqualTo("DE123456789");
		assertThat(record.getVATaxIDStatus()).isEqualTo(X_VATaxID_CheckLog.VATAXIDSTATUS_RequestSent);
		assertThat(record.getRequestDate()).isNotNull();
		assertThat(record.getResponseDate()).isNull();
		assertThat(record.getAD_PInstance_ID()).isEqualTo(2000001);
		assertThat(record.getAD_Session_ID()).isEqualTo(3000001);
		assertThat(record.getRequestIdentifier()).isNull();
		assertThat(record.getRawResponse()).isNull();
	}

	@Test
	void completeCheck_updatesOnlyTheTargetedRow_toItsFinalStatus()
	{
		final VATaxIDCheckLogId targetId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder().bpartnerId(BPARTNER_ID).vataxID("DE111111111").build());
		final VATaxIDCheckLogId otherId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder().bpartnerId(OTHER_BPARTNER_ID).vataxID("DE222222222").build());

		vataxIDCheckRepository.completeCheck(
				targetId,
				VATaxIDCheckResult.builder()
						.status(VATaxIDStatus.Valid)
						.requestIdentifier("WAPIQ-123")
						.rawResponse("<valid>true</valid>")
						.build());

		final I_VATaxID_CheckLog targetRecord = loadRecord(targetId);
		assertThat(targetRecord.getVATaxIDStatus()).isEqualTo(X_VATaxID_CheckLog.VATAXIDSTATUS_Valid);
		assertThat(targetRecord.getResponseDate()).isNotNull();
		assertThat(targetRecord.getRequestIdentifier()).isEqualTo("WAPIQ-123");
		assertThat(targetRecord.getRawResponse()).isEqualTo("<valid>true</valid>");

		// the second, unrelated row must be completely untouched by completing the first
		final I_VATaxID_CheckLog otherRecord = loadRecord(otherId);
		assertThat(otherRecord.getVATaxIDStatus()).isEqualTo(X_VATaxID_CheckLog.VATAXIDSTATUS_RequestSent);
		assertThat(otherRecord.getResponseDate()).isNull();
		assertThat(otherRecord.getRequestIdentifier()).isNull();
		assertThat(otherRecord.getRawResponse()).isNull();
	}

	@Test
	void completeCheck_refusesToRunTwiceOnTheSameRow()
	{
		final VATaxIDCheckLogId checkLogId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder().bpartnerId(BPARTNER_ID).vataxID("DE333333333").build());

		vataxIDCheckRepository.completeCheck(
				checkLogId,
				VATaxIDCheckResult.builder().status(VATaxIDStatus.Invalid).build());

		assertThatThrownBy(() -> vataxIDCheckRepository.completeCheck(
				checkLogId,
				VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build()))
				.isInstanceOf(AdempiereException.class);

		// the first, successful completion must not be clobbered by the rejected second attempt
		final I_VATaxID_CheckLog record = loadRecord(checkLogId);
		assertThat(record.getVATaxIDStatus()).isEqualTo(X_VATaxID_CheckLog.VATAXIDSTATUS_Invalid);
	}

	@Test
	void completeCheck_refusesAFinalStatusOfRequestSent()
	{
		final VATaxIDCheckLogId checkLogId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder().bpartnerId(BPARTNER_ID).vataxID("DE444444444").build());

		assertThatThrownBy(() -> vataxIDCheckRepository.completeCheck(
				checkLogId,
				VATaxIDCheckResult.builder().status(VATaxIDStatus.RequestSent).build()))
				.isInstanceOf(AdempiereException.class);

		final I_VATaxID_CheckLog record = loadRecord(checkLogId);
		assertThat(record.getVATaxIDStatus()).isEqualTo(X_VATaxID_CheckLog.VATAXIDSTATUS_RequestSent);
	}

	/**
	 * Pins the query filter's status predicate via observable behaviour rather than the query's internal
	 * shape: the harness backing repository calls in unit-test mode ({@code POJOQuery}) never builds real
	 * SQL, so there is no WHERE-clause artifact a test could inspect. Instead, the row is pushed out of
	 * {@link VATaxIDStatus#RequestSent} through a path independent of {@link VATaxIDCheckRepository#completeCheck}
	 * itself (a direct save, not a prior {@code completeCheck} call, unlike
	 * {@link #completeCheck_refusesToRunTwiceOnTheSameRow()}), then {@code completeCheck} must still refuse
	 * and must not have written any of its four completion fields — if a future edit dropped the status
	 * predicate from the query builder, this call would instead succeed and overwrite them.
	 */
	@Test
	void completeCheck_refusesWhenTheRowIsNotAtRequestSent_andLeavesItUntouched()
	{
		final VATaxIDCheckLogId checkLogId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder().bpartnerId(BPARTNER_ID).vataxID("DE555555555").build());

		final I_VATaxID_CheckLog record = loadRecord(checkLogId);
		record.setVATaxIDStatus(X_VATaxID_CheckLog.VATAXIDSTATUS_Invalid);
		InterfaceWrapperHelper.saveRecord(record);

		assertThatThrownBy(() -> vataxIDCheckRepository.completeCheck(
				checkLogId,
				VATaxIDCheckResult.builder()
						.status(VATaxIDStatus.Valid)
						.requestIdentifier("SHOULD-NOT-BE-WRITTEN")
						.rawResponse("<should-not-be-written/>")
						.build()))
				.isInstanceOf(AdempiereException.class);

		final I_VATaxID_CheckLog reloaded = loadRecord(checkLogId);
		assertThat(reloaded.getVATaxIDStatus()).isEqualTo(X_VATaxID_CheckLog.VATAXIDSTATUS_Invalid);
		assertThat(reloaded.getRequestIdentifier()).isNull();
		assertThat(reloaded.getRawResponse()).isNull();
	}

	@Test
	void completeCheck_refreshesTheUpdatedAndUpdatedByAuditColumns()
	{
		final UserId requestingUser = UserId.ofRepoId(3000002);
		final UserId completingUser = UserId.ofRepoId(3000003);

		SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-01-01T10:00:00Z"));
		Env.setLoggedUserId(Env.getCtx(), requestingUser);
		final VATaxIDCheckLogId checkLogId = vataxIDCheckRepository.writeRequestSent(
				VATaxIDCheckRequest.builder().bpartnerId(BPARTNER_ID).vataxID("DE666666666").build());

		final I_VATaxID_CheckLog beforeRecord = loadRecord(checkLogId);
		assertThat(beforeRecord.getUpdatedBy()).isEqualTo(requestingUser.getRepoId());

		SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-01-02T11:00:00Z"));
		Env.setLoggedUserId(Env.getCtx(), completingUser);
		vataxIDCheckRepository.completeCheck(
				checkLogId,
				VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build());

		final I_VATaxID_CheckLog afterRecord = loadRecord(checkLogId);
		assertThat(afterRecord.getUpdated()).isNotEqualTo(beforeRecord.getUpdated());
		assertThat(afterRecord.getUpdated()).isEqualTo(java.sql.Timestamp.from(java.time.Instant.parse("2026-01-02T11:00:00Z")));
		assertThat(afterRecord.getUpdatedBy()).isEqualTo(completingUser.getRepoId());
	}
}
