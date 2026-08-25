/*
 * #%L
 * metasfresh-vatid-base
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the two failures {@link VATaxIDMassCheckService} must tell apart: a chronic
 * {@code stampAttemptInOwnTrx} failure on ONE target, which must not abort the run, and a request-side
 * rejection by the checking service, which must.
 *
 * <p>{@link VATaxIdCheckTargetRepo} is spied rather than used as-is so one target's attempt-stamp write can
 * be made to throw deterministically while the other succeeds — the one thing a real in-memory repository
 * cannot reproduce without an actual concurrent writer.
 */
class VATaxIDMassCheckServiceTest
{
	private static final BPartnerId BPARTNER_ID_BROKEN_STAMP = BPartnerId.ofRepoId(1000101);
	private static final BPartnerId BPARTNER_ID_HEALTHY = BPartnerId.ofRepoId(1000102);
	private static final BPartnerId BPARTNER_ID_SECOND = BPartnerId.ofRepoId(1000103);
	private static final BPartnerId BPARTNER_ID_THIRD = BPartnerId.ofRepoId(1000104);
	private static final BPartnerId BPARTNER_ID_BLANK_VATAXID = BPartnerId.ofRepoId(1000105);

	/**
	 * Verified live against VIES on 2026-08-15 — see {@code VIESClient.REQUEST_SIDE_ERRORS}.
	 */
	private static final String VIES_ERROR_CODE = "INVALID_REQUESTER_INFO";

	private VATaxIDCheckService checkServiceMock;
	private VATaxIdCheckTargetRepo checkTargetRepoSpy;
	private IBPartnerDAO bpartnerDAOMock;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAOMock = mock(IBPartnerDAO.class);
		Services.registerService(IBPartnerDAO.class, bpartnerDAOMock);
		checkServiceMock = mock(VATaxIDCheckService.class);

		// A spy, not a plain mock: the target-stream assembly (iterateSelectedTargets / the nightly
		// iterateTargetsDueForVATaxIDCheck, plus the blank-skipping and CheckTarget mapping) is real code these
		// tests exercise, while each grain query and each write below is stubbed out. Constructed only after
		// bpartnerDAOMock is registered -- the repo resolves its services in its field initializers.
		checkTargetRepoSpy = spy(new VATaxIdCheckTargetRepo());
		doNothing().when(checkTargetRepoSpy).stampVATaxIDCheckAttempt(any(BPartnerId.class), any(Instant.class));
		doNothing().when(checkTargetRepoSpy).stampVATaxIDCheckAttempt(any(BPartnerLocationId.class), any(Instant.class));
	}

	/**
	 * A stand-in for the {@code C_BPartner} selection query the process hands the service. The selection tests
	 * stub the repo spy's grain iterators, so this query is never executed -- it only has to be the object the
	 * service threads through to {@code countSelectedTargets} / {@code iterateSelectedTargets}.
	 */
	private static IQuery<I_C_BPartner> mockSelectionQuery()
	{
		@SuppressWarnings("unchecked")
		final IQuery<I_C_BPartner> selectedBPartnersQuery = mock(IQuery.class);
		return selectedBPartnersQuery;
	}

	private static I_C_BPartner newBPartnerWithVATaxID(final BPartnerId bpartnerId, final String vataxID)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setVATaxID(vataxID);
		record.setVATaxIDStatus(VATaxIDStatus.NotChecked.getCode());
		return record;
	}

	private static I_C_BPartner_Location newBPartnerLocationWithVATaxID(
			final BPartnerId bpartnerId,
			final int bpartnerLocationRepoId,
			final String vataxID)
	{
		final I_C_BPartner_Location record = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setC_BPartner_Location_ID(bpartnerLocationRepoId);
		record.setVATaxID(vataxID);
		record.setVATaxIDStatus(VATaxIDStatus.NotChecked.getCode());
		return record;
	}

	@Test
	void aStampWriteFailure_doesNotAbortTheRun_leavesOtherTargetsChecked_andIsReportedDistinctly()
	{
		final I_C_BPartner brokenStampPartner = newBPartnerWithVATaxID(BPARTNER_ID_BROKEN_STAMP, "DE111111111");
		final I_C_BPartner healthyPartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE222222222");
		final IQuery<I_C_BPartner> selectedBPartnersQuery = mockSelectionQuery();

		// The selection now streams through the query-based seam, mirroring the nightly grain stubs: the two
		// selected partners are the partner grain, there is no location grain.
		doReturn(2).when(checkTargetRepoSpy).countSelectedTargets(selectedBPartnersQuery);
		doReturn(ImmutableList.of(brokenStampPartner, healthyPartner).iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartners(selectedBPartnersQuery);
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartnerLocations(selectedBPartnersQuery);
		// the broken target's stamp write fails on every attempt -- the exact chronic-failure shape this
		// test exists to cover; the healthy target's keeps the no-op stub from beforeEach and succeeds.
		doThrow(new RuntimeException("simulated stamp-write failure (test-only, VATaxIDMassCheckServiceTest)"))
				.when(checkTargetRepoSpy).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_BROKEN_STAMP), any(Instant.class));

		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		when(checkServiceMock.check(argThat(req -> BPARTNER_ID_HEALTHY.equals(req.getBpartnerId()))))
				.thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckRequest request = VATaxIDMassCheckRequest.builder()
				.selectedBPartnersQuery(selectedBPartnersQuery)
				.maxChecksPerRun(0)
				.nightlyRun(false)
				.build();

		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			massCheckService.run(request);
		}

		// the broken target's check was never even attempted -- the stamp failure short-circuited it
		// BEFORE the check-and-refresh unit started, exactly as stampAttemptInOwnTrx's javadoc documents.
		verify(checkServiceMock, never())
				.check(argThat(req -> BPARTNER_ID_BROKEN_STAMP.equals(req.getBpartnerId())));

		// ... but the run did NOT abort: the healthy target queued behind it was still stamped and checked.
		verify(checkTargetRepoSpy).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_HEALTHY), any(Instant.class));
		verify(checkServiceMock)
				.check(argThat(req -> BPARTNER_ID_HEALTHY.equals(req.getBpartnerId())));

		// the failure is reported DISTINCTLY from an ordinary check failure: this run log line is the ONLY
		// place a chronically stamp-failing target can surface at all (no VATaxID_CheckLog row exists
		// either, since writeRequestSent runs only once the check itself starts).
		final String brokenTargetLabel = "C_BPartner_ID=" + BPARTNER_ID_BROKEN_STAMP.getRepoId();
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.anyMatch(msg -> msg.contains("attempt-stamp write failed") && msg.contains(brokenTargetLabel))
				.noneMatch(msg -> msg.contains("VAT-ID check processing failed for " + brokenTargetLabel));
	}

	/**
	 * The mirror of the test above, and the reason the two cannot share one {@code catch}: a request-side
	 * rejection is NOT a per-target failure. The configuration that produced it is the same for every
	 * remaining target, so carrying on would repeat the identical error once per target for the whole
	 * selection — up to {@code MaxChecksPerRun}, 500 by default — while marking nothing and telling the
	 * operator nothing actionable.
	 */
	@Test
	void aRequestSideRejection_abortsTheRunAfterTheFirstTarget_namingTheServiceErrorCode()
	{
		final I_C_BPartner firstPartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE222222222");
		final I_C_BPartner secondPartner = newBPartnerWithVATaxID(BPARTNER_ID_SECOND, "DE333333333");
		final I_C_BPartner thirdPartner = newBPartnerWithVATaxID(BPARTNER_ID_THIRD, "DE444444444");
		final IQuery<I_C_BPartner> selectedBPartnersQuery = mockSelectionQuery();

		doReturn(3).when(checkTargetRepoSpy).countSelectedTargets(selectedBPartnersQuery);
		doReturn(ImmutableList.of(firstPartner, secondPartner, thirdPartner).iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartners(selectedBPartnersQuery);
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartnerLocations(selectedBPartnersQuery);
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());

		// Raised for EVERY target, exactly as a misconfigured requester identity behaves: the fault is in the
		// configuration, not in any particular VAT-ID, so the service rejects each request identically.
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class)))
				.thenThrow(new VATaxIDCheckRequestRejectedException(
						VIES_ERROR_CODE,
						"VIES rejected the request: " + VIES_ERROR_CODE + ". Check the VAT-ID configuration."));

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckRequest request = VATaxIDMassCheckRequest.builder()
				.selectedBPartnersQuery(selectedBPartnersQuery)
				.maxChecksPerRun(0)
				.nightlyRun(false)
				.build();

		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		final VATaxIDMassCheckResult result;
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(request);
		}

		// The whole point: the run stops at the FIRST target instead of grinding through the selection.
		verify(checkServiceMock, times(1)).check(any(VATaxIDCheckRequest.class));
		verify(checkTargetRepoSpy, never()).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_SECOND), any(Instant.class));
		verify(checkTargetRepoSpy, never()).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_THIRD), any(Instant.class));

		// ... and it is NOT reported as an ordinary per-target failure, which would leave the operator with
		// nothing to act on.
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.anyMatch(msg -> msg.contains("ABORTED") && msg.contains(VIES_ERROR_CODE))
				.noneMatch(msg -> msg.contains("VAT-ID check processing failed for "
						+ "C_BPartner_ID=" + BPARTNER_ID_HEALTHY.getRepoId()));

		// The abort line comes from the shared checkOneIfAvailable step now, naming the target it stopped at.
		// The old per-run reconciliation ("remaining N not attempted, X of Y pending") is gone by design: a
		// streamed run cannot know the eligible total up front, so it reports checked/pending against the count
		// query instead (asserted just below), exactly as the nightly path does.
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.anyMatch(msg -> msg.contains("ABORTED")
						&& msg.contains("C_BPartner_ID=" + BPARTNER_ID_HEALTHY.getRepoId()));

		// The partial run still reports what it did. pendingCount counts the rejected target too, and that is
		// deliberate: nothing advanced its VATaxIDCheckedAt, so all three genuinely still need a check.
		assertThat(result.getCheckedCount()).as("checkedCount").isZero();
		assertThat(result.getPendingCount()).as("pendingCount").isEqualTo(3);
	}

	/**
	 * RED regression for #31060: the manual/selection path must stream too, never handing the DAO a list of
	 * ids. "Select all" in the grid reproduces the same {@code An I/O error occurred while sending to the
	 * backend} the nightly path used to hit -- one bind parameter per selected record. The mirror of
	 * {@link #nightlyRun_streamsAndNeverBuildsAnIdList()}.
	 */
	@Test
	void selectionRun_streamsAndNeverBuildsAnIdList()
	{
		final I_C_BPartner selectedPartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		final IQuery<I_C_BPartner> selectedBPartnersQuery = mockSelectionQuery();

		doReturn(1).when(checkTargetRepoSpy).countSelectedTargets(selectedBPartnersQuery);
		doReturn(ImmutableList.of(selectedPartner).iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartners(selectedBPartnersQuery);
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartnerLocations(selectedBPartnersQuery);
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result = massCheckService.run(VATaxIDMassCheckRequest.builder()
				.selectedBPartnersQuery(selectedBPartnersQuery)
				.maxChecksPerRun(0)
				.nightlyRun(false)
				.build());

		assertThat(result.getCheckedCount()).isEqualTo(1);
		// the selection path streams via the query-based iterator, exactly like the nightly path ...
		verify(checkTargetRepoSpy).iterateSelectedTargets(eq(selectedBPartnersQuery), any());
		// ... and never materialises the selection into an id list handed to the DAO (the #31060 crash).
		verify(bpartnerDAOMock, never()).getByIds(anyCollection());
	}

	/**
	 * The nightly run must select by streaming, never by handing the DAO a list of ids. A list is what
	 * produced `An I/O error occurred while sending to the backend` on a real instance: one bind parameter
	 * per due record, ~10k of them, before a single check had run. Asserting a parameter count would test
	 * the query framework; asserting that the id-list overloads are never reached tests us.
	 */
	@Test
	void nightlyRun_streamsAndNeverBuildsAnIdList()
	{
		final OrgId orgId = OrgId.ofRepoId(1_000_000);
		final I_C_BPartner duePartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		duePartner.setAD_Org_ID(orgId.getRepoId());

		when(checkServiceMock.getRecheckAfterDaysByViesEnabledOrgId()).thenReturn(ImmutableMap.of(orgId, 30));
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		doReturn(1).when(checkTargetRepoSpy).countBPartnersDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(0).when(checkTargetRepoSpy).countBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(ImmutableList.of(duePartner).iterator())
				.when(checkTargetRepoSpy).iterateBPartnersDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result;
		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(VATaxIDMassCheckRequest.builder()
					.maxChecksPerRun(0)
					.nightlyRun(true)
					.build());
		}

		assertThat(result.getCheckedCount()).isEqualTo(1);
		verify(bpartnerDAOMock, never()).getByIds(anyCollection());
		verify(checkTargetRepoSpy).iterateBPartnersDueForVATaxIDCheck(eq(orgId), any(), anyInt());
	}

	/**
	 * {@code MaxChecksPerRun} must bound what the run READS, not just what it checks — the whole point of
	 * streaming. With a budget of 1 and two due records, the second must never be pulled from the iterator.
	 */
	@Test
	void nightlyRun_stopsReadingOnceTheBudgetIsSpent()
	{
		final OrgId orgId = OrgId.ofRepoId(1_000_000);
		final I_C_BPartner first = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		final I_C_BPartner second = newBPartnerWithVATaxID(BPARTNER_ID_SECOND, "DE123456789");

		when(checkServiceMock.getRecheckAfterDaysByViesEnabledOrgId()).thenReturn(ImmutableMap.of(orgId, 30));
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		doReturn(2).when(checkTargetRepoSpy).countBPartnersDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(0).when(checkTargetRepoSpy).countBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(ImmutableList.of(first, second).iterator())
				.when(checkTargetRepoSpy).iterateBPartnersDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result;
		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(VATaxIDMassCheckRequest.builder()
					.maxChecksPerRun(1)
					.nightlyRun(true)
					.build());
		}

		assertThat(result.getCheckedCount()).isEqualTo(1);
		assertThat(result.getPendingCount()).isEqualTo(1);
		verify(checkServiceMock, times(1)).check(any(VATaxIDCheckRequest.class));
	}

	/**
	 * TODO #31060's "don't query if the budget is already exhausted". With the budget spent by partner
	 * targets alone, the location grain must never be reached: the two grains are ONE lazily concatenated
	 * iterator, so merely asking it whether it has one more element is what creates and runs the location
	 * query. The two COUNT queries are a different matter and deliberately still run -- {@code pendingCount}
	 * is derived from them.
	 */
	@Test
	void nightlyRun_doesNotQueryLocations_whenTheBudgetIsSpentByPartnersAlone()
	{
		final OrgId orgId = OrgId.ofRepoId(1_000_000);
		final I_C_BPartner duePartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		duePartner.setAD_Org_ID(orgId.getRepoId());

		when(checkServiceMock.getRecheckAfterDaysByViesEnabledOrgId()).thenReturn(ImmutableMap.of(orgId, 30));
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		doReturn(1).when(checkTargetRepoSpy).countBPartnersDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(7).when(checkTargetRepoSpy).countBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(ImmutableList.of(duePartner).iterator())
				.when(checkTargetRepoSpy).iterateBPartnersDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result;
		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(VATaxIDMassCheckRequest.builder()
					.maxChecksPerRun(1)
					.nightlyRun(true)
					.build());
		}

		// the single allowed check went to the partner target ...
		assertThat(result.getCheckedCount()).as("checkedCount").isEqualTo(1);
		verify(checkServiceMock, times(1)).check(any(VATaxIDCheckRequest.class));

		// ... and the location query was never issued, because there was no budget left to spend on it.
		verify(checkTargetRepoSpy, never())
				.iterateBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());

		// both COUNT queries still run, per organisation and before the budget break: pendingCount is
		// derived from them, so skipping them would report a run that left 7 locations behind as complete.
		verify(checkTargetRepoSpy).countBPartnersDueForVATaxIDCheck(eq(orgId), any());
		verify(checkTargetRepoSpy).countBPartnerLocationsDueForVATaxIDCheck(eq(orgId), any());
		assertThat(result.getPendingCount()).as("pendingCount").isEqualTo(7);
	}

	/**
	 * The other half of the one-iterator refactor, and the half the budget-exhaustion test above can never
	 * reach: with budget to spare, the location targets must FOLLOW the partner targets of the same
	 * organisation — concatenated in that order, and actually yielded rather than quietly lost by the lazy
	 * concatenation that keeps the location query unissued in the test above.
	 */
	@Test
	void nightlyRun_yieldsPartnerTargetsFirst_thenLocationTargetsOfTheSameOrg()
	{
		final OrgId orgId = OrgId.ofRepoId(1_000_000);
		final BPartnerLocationId dueLocationId = BPartnerLocationId.ofRepoId(BPARTNER_ID_SECOND, 2000201);

		final I_C_BPartner duePartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		duePartner.setAD_Org_ID(orgId.getRepoId());
		final I_C_BPartner_Location dueLocation = newBPartnerLocationWithVATaxID(
				BPARTNER_ID_SECOND, dueLocationId.getRepoId(), "DE987654321");
		dueLocation.setAD_Org_ID(orgId.getRepoId());

		when(checkServiceMock.getRecheckAfterDaysByViesEnabledOrgId()).thenReturn(ImmutableMap.of(orgId, 30));
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		doReturn(1).when(checkTargetRepoSpy).countBPartnersDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(1).when(checkTargetRepoSpy).countBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(ImmutableList.of(duePartner).iterator())
				.when(checkTargetRepoSpy).iterateBPartnersDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		doReturn(ImmutableList.of(dueLocation).iterator())
				.when(checkTargetRepoSpy).iterateBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result;
		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(VATaxIDMassCheckRequest.builder()
					.maxChecksPerRun(0)
					.nightlyRun(true)
					.build());
		}

		assertThat(result.getCheckedCount()).as("checkedCount").isEqualTo(2);
		assertThat(result.getPendingCount()).as("pendingCount").isZero();

		// the header first, its C_BPartner_Location_ID-bearing counterpart second -- never the other way round.
		final InOrder inOrder = inOrder(checkServiceMock);
		inOrder.verify(checkServiceMock).check(argThat(req -> req != null
				&& BPARTNER_ID_HEALTHY.equals(req.getBpartnerId())
				&& req.getBpartnerLocationId() == null));
		inOrder.verify(checkServiceMock).check(argThat(req -> req != null
				&& dueLocationId.equals(req.getBpartnerLocationId())));
	}

	/**
	 * A blank {@code VATaxID} must cost the run one record, not the whole run. The nightly query filters on
	 * {@code VATaxID IS NOT NULL} only, while the column is not mandatory and {@code ''} does reach the
	 * database (see {@code BPartnerCompositeSaver}, {@code BPartnerImportHelper}). Without the guard,
	 * building the {@code CheckTarget} throws
	 * {@code Invalid VAT ID} and every record and organisation behind it goes unchecked.
	 *
	 * <p>Budget is 1 with two due records, the blank one first: the good one can only be reached if skipping
	 * the blank one consumed nothing.
	 */
	@Test
	void nightlyRun_skipsABlankVATaxID_withoutAbortingTheRunOrSpendingBudget()
	{
		final OrgId orgId = OrgId.ofRepoId(1_000_000);
		final I_C_BPartner blankVATaxIDPartner = newBPartnerWithVATaxID(BPARTNER_ID_BLANK_VATAXID, "");
		blankVATaxIDPartner.setAD_Org_ID(orgId.getRepoId());
		final I_C_BPartner healthyPartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		healthyPartner.setAD_Org_ID(orgId.getRepoId());

		when(checkServiceMock.getRecheckAfterDaysByViesEnabledOrgId()).thenReturn(ImmutableMap.of(orgId, 30));
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		doReturn(2).when(checkTargetRepoSpy).countBPartnersDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(0).when(checkTargetRepoSpy).countBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any());
		doReturn(ImmutableList.of(blankVATaxIDPartner, healthyPartner).iterator())
				.when(checkTargetRepoSpy).iterateBPartnersDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateBPartnerLocationsDueForVATaxIDCheck(any(OrgId.class), any(), anyInt());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result;
		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(VATaxIDMassCheckRequest.builder()
					.maxChecksPerRun(1)
					.nightlyRun(true)
					.build());
		}

		// the run completed and checked the good record queued behind the blank one ...
		assertThat(result.getCheckedCount()).as("checkedCount").isEqualTo(1);
		verify(checkServiceMock, times(1)).check(any(VATaxIDCheckRequest.class));
		verify(checkServiceMock)
				.check(argThat(req -> req != null && BPARTNER_ID_HEALTHY.equals(req.getBpartnerId())));

		// ... the blank record was neither checked nor attempt-stamped ...
		verify(checkServiceMock, never())
				.check(argThat(req -> req != null && BPARTNER_ID_BLANK_VATAXID.equals(req.getBpartnerId())));
		verify(checkTargetRepoSpy, never()).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_BLANK_VATAXID), any(Instant.class));

		// ... and it did not vanish silently: a data problem an operator has to fix is named in the run log.
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.anyMatch(msg -> msg.contains("blank VAT-ID")
						&& msg.contains("C_BPartner_ID=" + BPARTNER_ID_BLANK_VATAXID.getRepoId()));
	}

	/**
	 * The divergent-policy contract's SELECTION half, and the mirror of
	 * {@link #nightlyRun_skipsABlankVATaxID_withoutAbortingTheRunOrSpendingBudget()}: on the selection path a
	 * blank {@code VATaxID} is the ordinary "this partner has no VAT-ID" case, so it is skipped SILENTLY -- no
	 * WARN line -- whereas the nightly path names the same blank as a data defect. The service enforces this by
	 * passing a no-op {@code onBlankVATaxIDSkipped} callback here; this test guards that it stays no-op.
	 */
	@Test
	void selectionRun_skipsABlankVATaxID_silently()
	{
		final I_C_BPartner blankVATaxIDPartner = newBPartnerWithVATaxID(BPARTNER_ID_BLANK_VATAXID, "");
		final I_C_BPartner healthyPartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE123456789");
		final IQuery<I_C_BPartner> selectedBPartnersQuery = mockSelectionQuery();

		doReturn(1).when(checkTargetRepoSpy).countSelectedTargets(selectedBPartnersQuery);
		doReturn(ImmutableList.of(blankVATaxIDPartner, healthyPartner).iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartners(selectedBPartnersQuery);
		doReturn(ImmutableList.<I_C_BPartner_Location>of().iterator())
				.when(checkTargetRepoSpy).iterateSelectedBPartnerLocations(selectedBPartnersQuery);
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class))).thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock, checkTargetRepoSpy);

		final VATaxIDMassCheckResult result;
		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			result = massCheckService.run(VATaxIDMassCheckRequest.builder()
					.selectedBPartnersQuery(selectedBPartnersQuery)
					.maxChecksPerRun(0)
					.nightlyRun(false)
					.build());
		}

		// the good record queued behind the blank one was still checked ...
		assertThat(result.getCheckedCount()).as("checkedCount").isEqualTo(1);
		verify(checkServiceMock)
				.check(argThat(req -> req != null && BPARTNER_ID_HEALTHY.equals(req.getBpartnerId())));

		// ... the blank record was neither checked nor attempt-stamped ...
		verify(checkServiceMock, never())
				.check(argThat(req -> req != null && BPARTNER_ID_BLANK_VATAXID.equals(req.getBpartnerId())));
		verify(checkTargetRepoSpy, never()).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_BLANK_VATAXID), any(Instant.class));

		// ... and, unlike the nightly path, its skip was SILENT: no "blank VAT-ID" line reached the run log.
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.noneMatch(msg -> msg.contains("blank VAT-ID"));
	}
}
