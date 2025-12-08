/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workflow.rest_api.model.facets;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

@Value
public class WorkflowLaunchersFacetGroupList
{
	public static final WorkflowLaunchersFacetGroupList EMPTY = new WorkflowLaunchersFacetGroupList(ImmutableList.of());

	@NonNull ImmutableList<WorkflowLaunchersFacetGroup> groups;

	private WorkflowLaunchersFacetGroupList(@NonNull final ImmutableList<WorkflowLaunchersFacetGroup> groups)
	{
		this.groups = groups;
	}

	public static WorkflowLaunchersFacetGroupList of(final WorkflowLaunchersFacetGroup... groups)
	{
		if (groups == null || groups.length == 0)
		{
			return EMPTY;
		}

		return new WorkflowLaunchersFacetGroupList(ImmutableList.copyOf(groups));
	}

	public static WorkflowLaunchersFacetGroupList ofList(@Nullable final List<WorkflowLaunchersFacetGroup> groups)
	{
		if (groups == null || groups.isEmpty())
		{
			return EMPTY;
		}

		return new WorkflowLaunchersFacetGroupList(ImmutableList.copyOf(groups));
	}

	public Stream<WorkflowLaunchersFacetGroup> stream() {return groups.stream();}
}
