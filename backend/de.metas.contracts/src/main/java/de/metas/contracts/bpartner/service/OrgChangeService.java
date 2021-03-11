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
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class OrgChangeService
{
	final OrgChangeRepository repo;

	public OrgChangeService(final @NonNull OrgChangeRepository repo)
	{
		this.repo = repo;
	}

	public void moveBPartnerToOrg(final OrgChangeParameters orgChangeParameters)
	{

		final BPartnerId counterpartBPartnerId = repo.retrieveOrCloneBPartner(orgChangeParameters);

		repo.retrieveOrCloneLocations(orgChangeParameters, counterpartBPartnerId);

	}

	public boolean hasMembershipSubscriptions(final BPartnerId partnerId)
	{
		return repo.hasMembershipSubscriptions(partnerId);
	}

	public boolean hasAnyMembershipProduct(final OrgId orgId)
	{
		return repo.hasAnyMembershipProduct(orgId);
	}

}
