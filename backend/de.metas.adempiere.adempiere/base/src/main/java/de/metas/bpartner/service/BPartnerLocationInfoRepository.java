package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class BPartnerLocationInfoRepository
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public BPartnerLocationInfo getByBPartnerLocationId(@NonNull final BPartnerLocationId bplocationId)
	{
		final I_C_BPartner_Location bpLocation = bpartnerDAO.getBPartnerLocationByIdInTrx(bplocationId);
		return toBPartnerLocation(bpLocation);
	}

	private static BPartnerLocationInfo toBPartnerLocation(@NonNull final I_C_BPartner_Location bpartnerLocationRecord)
	{
		return BPartnerLocationInfo.builder()
				.id(BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID()), bpartnerLocationRecord.getC_BPartner_Location_ID()))
				.bpartnerId(BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID()))
				.locationId(LocationId.ofRepoId(bpartnerLocationRecord.getC_Location_ID()))
				.build();
	}

}
