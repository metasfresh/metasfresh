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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the two failures {@link VATaxIDMassCheckService} must tell apart: a chronic
 * {@code stampAttemptInOwnTrx} failure on ONE target, which must not abort the run, and a request-side
 * rejection by the checking service, which must.
 *
 * <p>{@link IBPartnerDAO} is a hand-built mock rather than the POJO-backed implementation so one target's
 * write can be made to throw deterministically while the other succeeds — the one thing a real in-memory
 * DAO cannot reproduce without an actual concurrent writer.
 */
class VATaxIDMassCheckServiceTest
{
	private static final BPartnerId BPARTNER_ID_BROKEN_STAMP = BPartnerId.ofRepoId(1000101);
	private static final BPartnerId BPARTNER_ID_HEALTHY = BPartnerId.ofRepoId(1000102);
	private static final BPartnerId BPARTNER_ID_SECOND = BPartnerId.ofRepoId(1000103);
	private static final BPartnerId BPARTNER_ID_THIRD = BPartnerId.ofRepoId(1000104);

	/** Verified live against VIES on 2026-08-15 — see {@code VIESClient.REQUEST_SIDE_ERRORS}. */
	private static final String VIES_ERROR_CODE = "INVALID_REQUESTER_INFO";

	private VATaxIDCheckService checkServiceMock;
	private IBPartnerDAO bpartnerDAOMock;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAOMock = mock(IBPartnerDAO.class);
		Services.registerService(IBPartnerDAO.class, bpartnerDAOMock);

		checkServiceMock = mock(VATaxIDCheckService.class);
	}

	private static I_C_BPartner newBPartnerWithVATaxID(final BPartnerId bpartnerId, final String vataxID)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setVATaxID(vataxID);
		record.setVATaxIDStatus(VATaxIDStatus.NotChecked.getCode());
		return record;
	}

	@Test
	void aStampWriteFailure_doesNotAbortTheRun_leavesOtherTargetsChecked_andIsReportedDistinctly()
	{
		final I_C_BPartner brokenStampPartner = newBPartnerWithVATaxID(BPARTNER_ID_BROKEN_STAMP, "DE111111111");
		final I_C_BPartner healthyPartner = newBPartnerWithVATaxID(BPARTNER_ID_HEALTHY, "DE222222222");

		when(bpartnerDAOMock.getByIds(anyCollection())).thenReturn(ImmutableList.of(brokenStampPartner, healthyPartner));
		when(bpartnerDAOMock.retrieveBPartnerLocationsWithVATaxID(anyCollection()))
				.thenReturn(ImmutableList.<I_C_BPartner_Location>of());
		// the broken target's stamp write fails on every attempt -- the exact chronic-failure shape this
		// test exists to cover; the healthy target's stamp write is left unstubbed (succeeds, no-op mock).
		doThrow(new RuntimeException("simulated stamp-write failure (test-only, VATaxIDMassCheckServiceTest)"))
				.when(bpartnerDAOMock).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_BROKEN_STAMP), any(Instant.class));

		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		when(checkServiceMock.check(argThat(req -> req != null && BPARTNER_ID_HEALTHY.equals(req.getBpartnerId()))))
				.thenReturn(VATaxIDStatus.Valid);

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock);

		final VATaxIDMassCheckRequest request = VATaxIDMassCheckRequest.builder()
				.selectedBPartnerIds(ImmutableList.of(BPARTNER_ID_BROKEN_STAMP, BPARTNER_ID_HEALTHY))
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
				.check(argThat(req -> req != null && BPARTNER_ID_BROKEN_STAMP.equals(req.getBpartnerId())));

		// ... but the run did NOT abort: the healthy target queued behind it was still stamped and checked.
		verify(bpartnerDAOMock).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_HEALTHY), any(Instant.class));
		verify(checkServiceMock)
				.check(argThat(req -> req != null && BPARTNER_ID_HEALTHY.equals(req.getBpartnerId())));

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

		when(bpartnerDAOMock.getByIds(anyCollection()))
				.thenReturn(ImmutableList.of(firstPartner, secondPartner, thirdPartner));
		when(bpartnerDAOMock.retrieveBPartnerLocationsWithVATaxID(anyCollection()))
				.thenReturn(ImmutableList.<I_C_BPartner_Location>of());
		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());

		// Raised for EVERY target, exactly as a misconfigured requester identity behaves: the fault is in the
		// configuration, not in any particular VAT-ID, so the service rejects each request identically.
		when(checkServiceMock.check(any(VATaxIDCheckRequest.class)))
				.thenThrow(new VATaxIDCheckRequestRejectedException(
						VIES_ERROR_CODE,
						"VIES rejected the request: " + VIES_ERROR_CODE + ". Check the VAT-ID configuration."));

		final VATaxIDMassCheckService massCheckService = new VATaxIDMassCheckService(checkServiceMock);

		final VATaxIDMassCheckRequest request = VATaxIDMassCheckRequest.builder()
				.selectedBPartnerIds(ImmutableList.of(BPARTNER_ID_HEALTHY, BPARTNER_ID_SECOND, BPARTNER_ID_THIRD))
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
		verify(bpartnerDAOMock, never()).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_SECOND), any(Instant.class));
		verify(bpartnerDAOMock, never()).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_THIRD), any(Instant.class));

		// ... and it is NOT reported as an ordinary per-target failure, which would leave the operator with
		// nothing to act on.
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.anyMatch(msg -> msg.contains("ABORTED") && msg.contains(VIES_ERROR_CODE))
				.noneMatch(msg -> msg.contains("VAT-ID check processing failed for "
						+ "C_BPartner_ID=" + BPARTNER_ID_HEALTHY.getRepoId()));

		// TWO, not three: the rejected target WAS attempted -- it was attempt-stamped and its request did
		// reach the service, which is what "attempted" means everywhere else in this class (see the class
		// javadoc, "Starvation guard"). Only the two behind it were never started. Counting the rejected one
		// among them would tell the operator the run got less far than it did.
		// ... and the abort line reconciles that 2 with the 3 the process footer reports as pending, so the
		// two numbers cannot read as a contradiction to an operator skimming the log.
		assertThat(log.getSingleMessages())
				.as("run log lines")
				.anyMatch(msg -> msg.contains("ABORTED")
						&& msg.contains("remaining 2 targets were not attempted")
						&& msg.contains("3 of 3 remain pending"));

		// The partial run still reports what it did. pendingCount counts the rejected target too, and that is
		// deliberate: nothing advanced its VATaxIDCheckedAt, so all three genuinely still need a check.
		assertThat(result.getCheckedCount()).as("checkedCount").isZero();
		assertThat(result.getPendingCount()).as("pendingCount").isEqualTo(3);
	}
}
