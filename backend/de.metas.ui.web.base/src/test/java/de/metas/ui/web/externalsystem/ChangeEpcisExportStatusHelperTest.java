/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.externalsystem;

import de.metas.ad_reference.ADReferenceService;
import de.metas.externalsystem.ExternalSystemExportStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.externalsystem.ExternalSystemExportStatus.DontSend;
import static de.metas.externalsystem.ExternalSystemExportStatus.Enqueued;
import static de.metas.externalsystem.ExternalSystemExportStatus.Error;
import static de.metas.externalsystem.ExternalSystemExportStatus.Invalid;
import static de.metas.externalsystem.ExternalSystemExportStatus.Pending;
import static de.metas.externalsystem.ExternalSystemExportStatus.SendingStarted;
import static de.metas.externalsystem.ExternalSystemExportStatus.Sent;
import static org.assertj.core.api.Assertions.assertThat;

class ChangeEpcisExportStatusHelperTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		// the helper resolves AD_Reference_ID via ADReferenceService at class-init; a mocked one is enough
		// for the pure transition-matrix assertions here (they never hit the lookup-value methods).
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
	}

	// Transition matrix (see ChangeEpcisExportStatusHelper javadoc):
	//   Pending  -> DontSend            (suppress a not-yet-sent record)
	//   DontSend -> Pending             (un-suppress -> re-queue)
	//   Error / Sent / Invalid / Enqueued / SendingStarted -> both (Pending, DontSend)

	@Test
	void pending_offersOnly_dontSend()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(Pending))
				.containsExactly(DontSend);
	}

	@Test
	void dontSend_offersOnly_pending()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(DontSend))
				.containsExactly(Pending);
	}

	@Test
	void error_offersBoth()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(Error))
				.containsExactly(Pending, DontSend);
	}

	@Test
	void sent_offersBoth()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(Sent))
				.containsExactly(Pending, DontSend);
	}

	@Test
	void invalid_offersBoth()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(Invalid))
				.containsExactly(Pending, DontSend);
	}

	@Test
	void inflight_enqueued_offersBoth()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(Enqueued))
				.containsExactly(Pending, DontSend);
	}

	@Test
	void inflight_sendingStarted_offersBoth()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(SendingStarted))
				.containsExactly(Pending, DontSend);
	}

	@Test
	void nullFrom_offersNothing()
	{
		assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(null))
				.isEmpty();
	}

	// A target status is never a no-op self-transition: the "from" value must not appear in its own targets.
	@Test
	void noTargetIsASelfTransition()
	{
		for (final ExternalSystemExportStatus from : ExternalSystemExportStatus.values())
		{
			assertThat(ChangeEpcisExportStatusHelper.getAvailableTargetExportStatuses(from))
					.as("targets for %s must not contain %s itself", from, from)
					.doesNotContain(from);
		}
	}
}
