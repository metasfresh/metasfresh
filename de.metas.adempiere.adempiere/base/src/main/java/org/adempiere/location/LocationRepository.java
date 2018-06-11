package org.adempiere.location;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Repository;

import de.metas.adempiere.service.ILocationBL;
import lombok.NonNull;

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
public class LocationRepository
{
	public Location ofRecord(@NonNull final I_C_Location locationRecord)
	{
		final String address = Services.get(ILocationBL.class).mkAddress(locationRecord);

		return Location.builder()
				.id(LocationId.ofRepoId(locationRecord.getC_Location_ID()))
				.address(address)
				.build();
	}

	public Location getByLocationId(@NonNull final LocationId locationId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Location.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Location.COLUMN_C_Location_ID, locationId.getRepoId())
				.create()
				.stream()
				.map(this::ofRecord)
				.findFirst()
				.orElse(null);
	}
}
