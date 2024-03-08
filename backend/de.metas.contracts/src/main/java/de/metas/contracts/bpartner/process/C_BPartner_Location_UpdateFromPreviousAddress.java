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

package de.metas.contracts.bpartner.process;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.bpartner.command.BPartnerLocationReplaceCommand;
import de.metas.location.LocationId;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.I_C_BPartner_Location;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * This process is scheduled to be run each night to perform business partner address updates that were scheduled, where one location is to replace another.
 * They include updating flags from the old location, updating location ids in C_OLCand, C_Invoice_Candidate and C_Flatrate_Term and disabling the old location.
 */
public class C_BPartner_Location_UpdateFromPreviousAddress extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected String doIt() throws Exception
	{
		updateFromPreviousAddress();
		return MSG_OK;
	}

	private void updateFromPreviousAddress()
	{
		final Instant now = SystemTime.asInstant();

		final Map<Integer, I_C_BPartner_Location> newLocations = queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addCompareFilter(I_C_BPartner_Location.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, now)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_BPartner_Location.COLUMNNAME_Previous_ID)
				.orderBy(I_C_BPartner_Location.COLUMNNAME_ValidFrom)
				.create()
				.map(I_C_BPartner_Location.class, I_C_BPartner_Location::getC_BPartner_Location_ID);

		final Map<Integer, Integer> oldLocToNewLoc = newLocations.values()
				.stream()
				.collect(Collectors.toMap(I_C_BPartner_Location::getPrevious_ID, I_C_BPartner_Location::getC_BPartner_Location_ID));

		final List<BPartnerLocationId> partnerLocationIdsToDeactivate = newLocations.values()
				.stream()
				.map(loc -> BPartnerLocationId.ofRepoId(loc.getC_BPartner_ID(), loc.getPrevious_ID()))
				.collect(Collectors.toList());
		final Map<Integer, I_C_BPartner_Location> locationsToDeactivate = queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, partnerLocationIdsToDeactivate)
				.addOnlyActiveRecordsFilter()
				.create()
				.map(I_C_BPartner_Location.class, I_C_BPartner_Location::getC_BPartner_Location_ID);

		if (Check.isEmpty(locationsToDeactivate.keySet()))
		{
			// nothing to do
			return;
		}

		locationsToDeactivate.forEach((oldLocId, location) -> replaceLocation(location, newLocations.get(oldLocToNewLoc.get(oldLocId))));
	}

	private void replaceLocation(final I_C_BPartner_Location oldLocation, final I_C_BPartner_Location newLocation)
	{
		final BPartnerLocationId oldBPLocationId = BPartnerLocationId.ofRepoId(oldLocation.getC_BPartner_ID(), oldLocation.getC_BPartner_Location_ID());
		final LocationId oldLocationId = LocationId.ofRepoId(oldLocation.getC_Location_ID());
		final BPartnerLocationId newBPLocationId = BPartnerLocationId.ofRepoId(newLocation.getC_BPartner_ID(), newLocation.getC_BPartner_Location_ID());
		final LocationId newLocationId = LocationId.ofRepoId(newLocation.getC_Location_ID());

		logDeactivation(oldBPLocationId, newBPLocationId);

		BPartnerLocationReplaceCommand.builder()
				.oldLocationId(oldLocationId)
				.oldBPLocationId(oldBPLocationId)
				.oldLocation(oldLocation)
				.newBPLocationId(newBPLocationId)
				.newLocationId(newLocationId)
				.newLocation(newLocation)
				.build()
				.execute();
	}

	@VisibleForTesting
	protected void logDeactivation(final BPartnerLocationId oldBPLocationId, final BPartnerLocationId newBPLocationId)
	{
		addLog("Business Partner Location {} was deactivated because it's being replaced by {}.",
				oldBPLocationId.getRepoId(), newBPLocationId.getRepoId());
	}

}
