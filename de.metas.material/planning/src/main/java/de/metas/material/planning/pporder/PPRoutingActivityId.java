package de.metas.material.planning.pporder;

import java.util.Collection;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
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

/**
 * Routing activity ID.
 * i.e. {@link PPRoutingId} / AD_WF_Node_ID
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PPRoutingActivityId implements RepoIdAware
{
	@JsonCreator
	public static PPRoutingActivityId ofAD_WF_Node_ID(final PPRoutingId routingId, final int repoId)
	{
		return new PPRoutingActivityId(routingId, repoId);
	}

	public static int toRepoId(final PPRoutingActivityId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	PPRoutingId routingId;
	int repoId;

	private PPRoutingActivityId(@NonNull final PPRoutingId routingId, final int repoId)
	{
		this.routingId = routingId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_WF_Node_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static PPRoutingId extractSingleRoutingId(final Collection<PPRoutingActivityId> activityIds)
	{
		Check.assumeNotEmpty(activityIds, "activityIds is not empty");
		return activityIds.stream()
				.map(PPRoutingActivityId::getRoutingId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Activities are from multiple routings: " + activityIds)));
	}
}
