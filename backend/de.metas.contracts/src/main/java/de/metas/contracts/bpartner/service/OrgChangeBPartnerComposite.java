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

import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.contracts.FlatrateTerm;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class OrgChangeBPartnerComposite
{
	@NonNull
	BPartnerComposite bPartnerComposite;

	@NonNull
	OrgMappingId bPartnerOrgMappingId;

	@NonNull
	List<FlatrateTerm> membershipSubscriptions;

	@NonNull
	List<FlatrateTerm> nonMembershipSubscriptions;

	@Builder(toBuilder = true)
	private OrgChangeBPartnerComposite(
			@NonNull final BPartnerComposite bPartnerComposite,
			@NonNull final OrgMappingId bPartnerOrgMappingId,
			@NonNull @Singular final List<FlatrateTerm> membershipSubscriptions,
			@NonNull @Singular final List<FlatrateTerm> nonMembershipSubscriptions)
	{
		this.bPartnerComposite = bPartnerComposite;
		this.bPartnerOrgMappingId = bPartnerOrgMappingId;

		this.membershipSubscriptions = membershipSubscriptions;
		this.nonMembershipSubscriptions = nonMembershipSubscriptions;
	}

	public boolean hasMembershipSubscriptions()
	{
		return !Check.isEmpty(membershipSubscriptions);
	}

	public DefaultLocations getDefaultLocations()
	{
		final BPartnerLocation billToDefaultLocation = getBillToDefaultLocationOrNull();
		final BPartnerLocation shipTpDefaultLocation = getShipToDefaultLocationOrNull();

		return DefaultLocations.builder()
				.billToDefaultLocation(billToDefaultLocation)
				.shipToDefaultLocation(shipTpDefaultLocation)
				.build();
	}

	@Nullable
	public BPartnerLocation getBillToDefaultLocationOrNull()
	{
		return getBPartnerComposite()
				.extractLocation(l -> l.getLocationType().getIsBillToDefaultOr(false))
				.orElse(null);

	}

	@Nullable
	public BPartnerLocation getShipToDefaultLocationOrNull()
	{
		return getBPartnerComposite()
				.extractLocation(l -> l.getLocationType().getIsShipToDefaultOr(false))
				.orElse(null);
	}

	@Nullable
	public BPartnerContact getDefaultContactOrNull()
	{
		return getBPartnerComposite()
				.extractContact(c -> c.getContactType().getIsDefaultContactOr(false))
				.orElse(null);
	}

	@Nullable
	public BPartnerContact getBillToDefaultContactOrNull()
	{
		return getBPartnerComposite()
				.extractContact(c -> c.getContactType().getIsBillToDefaultOr(false))
				.orElse(null);
	}

	@Nullable
	public BPartnerContact getShipToDefaultContactOrNull()
	{
		return getBPartnerComposite()
				.extractContact(c -> c.getContactType().getIsShipToDefaultOr(false))
				.orElse(null);
	}

	@Nullable
	public BPartnerContact getPurchaseDefaultContactOrNull()
	{
		return getBPartnerComposite()
				.extractContact(c -> c.getContactType().getIsPurchaseDefaultOr(false))
				.orElse(null);
	}

	@Nullable
	public BPartnerContact getSalesDefaultContactOrNull()
	{
		return getBPartnerComposite()
				.extractContact(c -> c.getContactType().getIsSalesDefaultOr(false))
				.orElse(null);
	}

}
