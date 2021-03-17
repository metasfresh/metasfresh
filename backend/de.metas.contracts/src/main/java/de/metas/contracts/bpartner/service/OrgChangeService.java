/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.contracts.bpartner.repository.OrgChangeRepository;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrgChangeService
{
	final OrgChangeRepository orgChangeRepo;
	final BPartnerCompositeRepository bpCompositeRepo;

	public OrgChangeService(final @NonNull OrgChangeRepository orgChangeRepo,
			final @NonNull BPartnerCompositeRepository bpCompositeRepo)
	{
		this.orgChangeRepo = orgChangeRepo;
		this.bpCompositeRepo = bpCompositeRepo;
	}

	public void moveBPartnerToOrg(final @NonNull OrgChangeRequest orgChangeRequest)
	{
		final OrgChangeBPartnerComposite initialBPartnerComposite = orgChangeRepo.getByIdAndOrgChangeDate(orgChangeRequest.getBpartnerId(),
																										  orgChangeRequest.getStartDate());



	}

	public boolean hasMembershipSubscriptions(final @NonNull BPartnerId partnerId, final @NonNull LocalDate maxSubscriptionDate)
	{
		return orgChangeRepo.hasMembershipSubscriptions(partnerId, maxSubscriptionDate);
	}

	public boolean hasAnyMembershipProduct(final OrgId orgId)
	{
		return orgChangeRepo.hasAnyMembershipProduct(orgId);
	}

}
