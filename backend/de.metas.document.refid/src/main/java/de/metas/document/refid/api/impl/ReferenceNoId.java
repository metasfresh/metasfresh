/*
 * #%L
 * de.metas.document.refid
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.document.refid.api.impl;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class ReferenceNoId implements RepoIdAware
{
	int repoId;

	public static ReferenceNoId ofRepoId(final int repoId)
	{
		return new ReferenceNoId(repoId);
	}

	private ReferenceNoId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_ReferenceNo");
	}
}
