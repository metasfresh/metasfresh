package de.metas.freighcost;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class FreightCostId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static FreightCostId ofRepoId(final int repoId)
	{
		return new FreightCostId(repoId);
	}

	public static FreightCostId ofRepoIdOrNull(@Nullable final int repoId)
	{
		return repoId > 0 ? new FreightCostId(repoId) : null;
	}

	public static int toRepoId(final FreightCostId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private FreightCostId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_FreightCost_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(final FreightCostId id1, final FreightCostId id2)
	{
		return Objects.equals(id1, id2);
	}
}
