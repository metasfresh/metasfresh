package de.metas.bpartner.composite.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;

import java.util.Optional;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
@Getter(AccessLevel.NONE)
class CompositeRelatedRecords
{
	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_AD_User> bpartnerId2Users = ImmutableListMultimap.of();

	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> bpartnerId2BPartnerLocations = ImmutableListMultimap.of();

	@NonNull
	@Builder.Default
	ImmutableMap<LocationId, I_C_Location> locationId2Location = ImmutableMap.of();

	@NonNull
	@Builder.Default
	ImmutableMap<PostalId, I_C_Postal> postalId2Postal = ImmutableMap.of();

	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> bpartnerId2BankAccounts = ImmutableListMultimap.of();

	@NonNull
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default
	ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = ImmutableListMultimap.of();

	public ImmutableList<I_C_BPartner_Location> getBPartnerLocationsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2BPartnerLocations.get(bpartnerId);
	}

	public Optional<I_C_Location> getLocationById(@NonNull final LocationId locationId) { return Optional.ofNullable(locationId2Location.get(locationId)); }

	public Optional<I_C_Postal> getPostalById(@NonNull final PostalId postalId) { return Optional.ofNullable(postalId2Postal.get(postalId));}

	public ImmutableList<I_AD_User> getContactsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2Users.get(bpartnerId);
	}

	public ImmutableList<I_C_BP_BankAccount> getBankAccountsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2BankAccounts.get(bpartnerId);
	}
}
