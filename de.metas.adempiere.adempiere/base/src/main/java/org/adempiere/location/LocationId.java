package org.adempiere.location;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

/** C_Location_ID */
@Value
public class LocationId implements RepoIdAware
{
	public static LocationId ofRepoId(final int repoId)
	{
		return new LocationId(repoId);
	}

	public static LocationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	int repoId;

	private LocationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Location_ID");
	}

	public static int toRepoId(LocationId locationId)
	{
		return toRepoIdOr(locationId, -1);
	}

	public static int toRepoIdOr(final LocationId locationId, final int defaultValue)
	{
		return locationId != null ? locationId.getRepoId() : defaultValue;
	}
}
