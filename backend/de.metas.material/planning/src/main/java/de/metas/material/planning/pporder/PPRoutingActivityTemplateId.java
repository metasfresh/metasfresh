package de.metas.material.planning.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-planning
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
public class PPRoutingActivityTemplateId implements RepoIdAware
{
	@JsonCreator
	public static PPRoutingActivityTemplateId ofRepoId(final int repoId)
	{
		return new PPRoutingActivityTemplateId(repoId);
	}

	@Nullable
	public static PPRoutingActivityTemplateId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final PPRoutingActivityTemplateId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private PPRoutingActivityTemplateId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_WF_Node_Template_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
