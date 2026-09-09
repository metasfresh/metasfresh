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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDConfigRepository;
import org.adempiere.ad.session.AdSessionId;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pins the one organisation resolution a reader can plausibly get wrong: when the VAT-ID lives on a
 * {@code C_BPartner_Location}, the check is scheduled for the LOCATION's own {@code AD_Org_ID}, not its
 * parent partner's.
 *
 * <p>This is not hair-splitting. {@link VATaxIDCheckTrigger} gates the enqueue on that organisation's
 * {@code IsVIESCheckEnabled}, and {@code VATaxIDCheckService} independently re-resolves it at processing
 * time through {@code VATaxIDParentStatusRepository}, which for a request carrying a location reads
 * <em>the location record's</em> {@code AD_Org_ID}. The two must name the same organisation or the gate at
 * one end answers a different question from the gate at the other. Swapping in the parent partner's org
 * would still pass every test that gives both records the same organisation — which is every other test
 * there is — so this one deliberately gives them different ones.
 */
class C_BPartner_LocationTest
{
	/** The organisation on the LOCATION — the one that must govern. */
	private static final OrgId LOCATION_ORG_ID = OrgId.ofRepoId(1_000_001);

	/** A DIFFERENT organisation on the parent partner — reading this one would be the bug. */
	private static final OrgId PARTNER_ORG_ID = OrgId.ofRepoId(1_000_002);

	private static final AdSessionId AD_SESSION_ID = AdSessionId.ofRepoId(3_000_000);
	private static final String VATAXID = "DE136695976";

	private VATaxIDCheckTrigger trigger;
	private C_BPartner_Location interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Registered before the interceptor is constructed: its sessionBL is an inline-initialised
		// Services.get field, so it is resolved in the constructor.
		final ISessionBL sessionBL = mock(ISessionBL.class);
		when(sessionBL.getCurrentOrCreateSessionId(any(Properties.class))).thenReturn(AD_SESSION_ID);
		Services.registerService(ISessionBL.class, sessionBL);

		trigger = mock(VATaxIDCheckTrigger.class);
		interceptor = new C_BPartner_Location(trigger, mock(VATaxIDConfigRepository.class));
	}

	@Nested
	class ScheduleVATaxIDCheck
	{
		@Test
		void usesTheLocationsOwnOrg_notTheParentPartners()
		{
			final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			partner.setAD_Org_ID(PARTNER_ORG_ID.getRepoId());
			InterfaceWrapperHelper.saveRecord(partner);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

			final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
			bpLocation.setAD_Org_ID(LOCATION_ORG_ID.getRepoId());
			bpLocation.setC_BPartner_ID(bpartnerId.getRepoId());
			bpLocation.setVATaxID(VATAXID);
			InterfaceWrapperHelper.saveRecord(bpLocation);

			interceptor.scheduleVATaxIDCheck(bpLocation);

			verify(trigger).scheduleCheckAfterCommit(
					LOCATION_ORG_ID,
					bpartnerId,
					BPartnerLocationId.ofRepoId(bpartnerId, bpLocation.getC_BPartner_Location_ID()),
					VATAXID,
					AD_SESSION_ID);
		}
	}
}
