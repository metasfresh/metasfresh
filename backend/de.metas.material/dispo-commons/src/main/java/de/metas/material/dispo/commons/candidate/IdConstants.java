package de.metas.material.dispo.commons.candidate;

import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

/**
 * Class to help getting a grip on when an repoId <= 0 means "null" and when it means "not specified".
 * The distinction is important when querying or storing stuff.
 *
 */
public final class IdConstants
{
	public static final int UNSPECIFIED_REPO_ID = Integer.MAX_VALUE - 10;
	public static final int NULL_REPO_ID = Integer.MAX_VALUE - 20;

	private IdConstants()
	{
	}

	public static int toRepoId(final int id)
	{
		if (id == UNSPECIFIED_REPO_ID)
		{
			return 0;
		}
		else if (id == NULL_REPO_ID)
		{
			return 0;
		}
		return id;
	}

	public static int toUnspecifiedIfZero(final int repoId)
	{
		if (repoId <= 0)
		{
			return UNSPECIFIED_REPO_ID;
		}
		return repoId;
	}

	public static void assertValidId(final int id, @NonNull final String name)
	{
		Check.errorUnless(id > 0 || id == UNSPECIFIED_REPO_ID || id == NULL_REPO_ID,
				"{}={} needs to be >0 or ==IdConstants.UNSPECIFIED_REPO_ID or ==IdConstants.NULL_REPO_ID", name, id);
	}
}
