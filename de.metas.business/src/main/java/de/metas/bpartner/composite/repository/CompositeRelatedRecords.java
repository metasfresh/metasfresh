package de.metas.bpartner.composite.repository;

import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;

import lombok.NonNull;
import lombok.Value;

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
final class CompositeRelatedRecords
{
	@NonNull
	ImmutableListMultimap<Integer, I_AD_User> bpartnerId2Users;

	@NonNull
	ImmutableListMultimap<Integer, I_C_BPartner_Location> bpartnerId2BPartnerLocations;

	@NonNull
	ImmutableMap<Integer, I_C_Location> locationId2Location;

	@NonNull
	ImmutableMap<Integer, I_C_Postal> postalId2Postal;

	@NonNull
	ImmutableMap<Integer, I_C_Country> countryId2Country;

	@NonNull
	ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries;
}
