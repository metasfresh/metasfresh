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

package de.metas.vatid.interceptor;

import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageParamsBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDConfig;
import de.metas.vatid.VATaxIDConfigId;
import de.metas.vatid.VATaxIDConfigRepository;
import de.metas.vatid.VATaxIDOnServiceUnavailableAction;
import de.metas.vatid.async.VATaxIDCheckWorkpackageProcessor;
import org.adempiere.ad.session.AdSessionId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the enqueue gate: a save must not queue an online check for an organisation whose
 * {@code IsVIESCheckEnabled} is off.
 *
 * <p>The gate has to be here rather than only in {@code VATaxIDCheckService}, which evaluates it at
 * PROCESSING time — a package enqueued while the online check was off would be let through by a service-side
 * gate re-read after someone switched it on, checking a save the configuration in force at the time said not
 * to check. Only an assertion at the enqueue boundary can prove that.
 *
 * <p>{@link IWorkPackageQueueFactory} is mocked rather than driven for real: the real queue needs a
 * {@code C_Queue_PackageProcessor} record and the queue-processor descriptor index, none of which is what is
 * under test here. The boundary itself is the assertion — the trigger's whole contract is whether it reaches
 * that boundary at all, and with which parameters.
 */
class VATaxIDCheckTriggerTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1_000_000);
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(2_000_000);
	private static final AdSessionId AD_SESSION_ID = AdSessionId.ofRepoId(3_000_000);

	/** One of the VAT-IDs the cucumber scenarios use, so the two layers stay recognisably about the same case. */
	private static final String VATAXID = "DE136695976";

	private VATaxIDConfigRepository configRepository;
	private IWorkPackageQueueFactory queueFactory;
	private IWorkPackageBuilder workPackageBuilder;
	private IWorkPackageParamsBuilder paramsBuilder;

	private VATaxIDCheckTrigger trigger;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		paramsBuilder = mock(IWorkPackageParamsBuilder.class);
		workPackageBuilder = mock(IWorkPackageBuilder.class);
		when(paramsBuilder.setParameter(anyString(), any())).thenReturn(paramsBuilder);
		when(paramsBuilder.end()).thenReturn(workPackageBuilder);
		when(workPackageBuilder.bindToThreadInheritedTrx()).thenReturn(workPackageBuilder);
		when(workPackageBuilder.parameters()).thenReturn(paramsBuilder);

		final IWorkPackageQueue queue = mock(IWorkPackageQueue.class);
		when(queue.newWorkPackage()).thenReturn(workPackageBuilder);

		queueFactory = mock(IWorkPackageQueueFactory.class);
		when(queueFactory.getQueueForEnqueuing(VATaxIDCheckWorkpackageProcessor.class)).thenReturn(queue);
		Services.registerService(IWorkPackageQueueFactory.class, queueFactory);

		configRepository = mock(VATaxIDConfigRepository.class);

		trigger = new VATaxIDCheckTrigger(configRepository);
	}

	private void givenViesCheckEnabled(final boolean viesCheckEnabled)
	{
		when(configRepository.getByOrgId(ORG_ID)).thenReturn(VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(1_000_000))
				.formatCheckEnabled(true)
				.viesCheckEnabled(viesCheckEnabled)
				.restApiBaseURL("https://ec.europa.eu/taxation_customs/vies/rest-api")
				.recheckAfterDays(30)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build());
	}

	private void whenPartnerIsSavedWith(final String vataxIDValue)
	{
		trigger.scheduleCheckAfterCommit(ORG_ID, BPARTNER_ID, null, vataxIDValue, AD_SESSION_ID);
	}

	private void thenNothingWasEnqueued()
	{
		verify(queueFactory, never()).getQueueForEnqueuing(VATaxIDCheckWorkpackageProcessor.class);
		verify(workPackageBuilder, never()).buildAndEnqueue();
	}

	@Nested
	class ScheduleCheckAfterCommit
	{
		@Test
		void viesCheckDisabled_enqueuesNothing()
		{
			givenViesCheckEnabled(false);

			whenPartnerIsSavedWith(VATAXID);

			thenNothingWasEnqueued();
		}

		@Test
		void viesCheckEnabled_enqueuesTheCheckOfTheSavedValue()
		{
			givenViesCheckEnabled(true);

			whenPartnerIsSavedWith(VATAXID);

			verify(workPackageBuilder).buildAndEnqueue();
			verify(paramsBuilder).setParameter("C_BPartner_ID", BPARTNER_ID.getRepoId());
			verify(paramsBuilder).setParameter("VATaxID", VATAXID);
			verify(paramsBuilder).setParameter("AD_Session_ID", AD_SESSION_ID.getRepoId());
		}

		/**
		 * The pre-existing cleared-value guard, kept covered so the added configuration gate cannot be the
		 * only reason nothing is enqueued.
		 */
		@Test
		void viesCheckEnabled_butVATaxIDCleared_enqueuesNothing()
		{
			givenViesCheckEnabled(true);

			whenPartnerIsSavedWith(" ");

			thenNothingWasEnqueued();
		}
	}
}
