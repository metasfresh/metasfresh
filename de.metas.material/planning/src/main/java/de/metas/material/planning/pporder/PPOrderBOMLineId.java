package de.metas.material.planning.pporder;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-planning
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPOrderBOMLineId implements RepoIdAware
{
	@JsonCreator
	public static PPOrderBOMLineId ofRepoId(final int repoId)
	{
		return new PPOrderBOMLineId(repoId);
	}

	public static PPOrderBOMLineId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PPOrderBOMLineId(repoId) : null;
	}

	public static Optional<PPOrderBOMLineId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(final PPOrderBOMLineId id)
	{
		return toRepoIdOr(id, -1);
	}

	public static int toRepoIdOr(final PPOrderBOMLineId id, final int defaultValue)
	{
		return id != null ? id.getRepoId() : defaultValue;
	}

	int repoId;

	private PPOrderBOMLineId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_BOMLine_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
