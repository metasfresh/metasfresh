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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers {@link VATaxIDCheckRunService}'s own defence against {@code stampAttemptInOwnTrx}'s chronic
 * failure — a stamp write that fails on every attempt for one target, e.g. row-lock contention with an
 * unrelated job on the same record, or a save-veto interceptor unconnected to VAT-ID checking (see
 * {@code stampAttemptInOwnTrx}'s own javadoc).
 *
 * <p>{@link IBPartnerDAO} is replaced with a hand-built mock (not the POJO-backed real implementation) so
 * this test can make its per-target write deterministically throw for exactly one target while leaving
 * the other target's write to succeed — the one thing a real, in-memory DAO cannot reproduce without an
 * actual concurrent writer.
 */
class VATaxIDCheckRunServiceTest
{
	private static final BPartnerId BPARTNER_ID_BROKEN_STAMP = BPartnerId.ofRepoId(1000101);
	private static final BPartnerId BPARTNER_ID_HEALTHY = BPartnerId.ofRepoId(1000102);

	private VATaxIDCheckService checkServiceMock;
	private VATaxIDOrderTaxRefresher orderTaxRefresherMock;
	private IBPartnerDAO bpartnerDAOMock;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAOMock = mock(IBPartnerDAO.class);
		Services.registerService(IBPartnerDAO.class, bpartnerDAOMock);

		checkServiceMock = mock(VATaxIDCheckService.class);
		orderTaxRefresherMock = mock(VATaxIDOrderTaxRefresher.class);
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
		doThrow(new RuntimeException("simulated stamp-write failure (test-only, VATaxIDCheckRunServiceTest)"))
				.when(bpartnerDAOMock).stampVATaxIDCheckAttempt(eq(BPARTNER_ID_BROKEN_STAMP), any(Instant.class));

		when(checkServiceMock.getUnavailableCountryCodes(any(OrgId.class))).thenReturn(ImmutableSet.of());
		when(checkServiceMock.check(argThat(req -> req != null && BPARTNER_ID_HEALTHY.equals(req.getBpartnerId()))))
				.thenReturn(VATaxIDStatus.Valid);

		final VATaxIDCheckRunService runService = new VATaxIDCheckRunService(checkServiceMock, orderTaxRefresherMock);

		final VATaxIDCheckRunRequest request = VATaxIDCheckRunRequest.builder()
				.selectedBPartnerIds(ImmutableList.of(BPARTNER_ID_BROKEN_STAMP, BPARTNER_ID_HEALTHY))
				.maxChecksPerRun(0)
				.nightlyRun(false)
				.build();

		final PlainStringLoggable log = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(log))
		{
			runService.run(request);
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
}
