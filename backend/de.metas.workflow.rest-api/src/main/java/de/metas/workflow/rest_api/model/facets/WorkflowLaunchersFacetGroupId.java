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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public final class WorkflowLaunchersFacetGroupId
{
	@NonNull String asString;

	private WorkflowLaunchersFacetGroupId(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			throw new AdempiereException("Empty/null string is not a valid group ID");
		}
		if (stringNorm.contains(WorkflowLaunchersFacetId.SEPARATOR))
		{
			throw new AdempiereException("Group ID `" + string + "` cannot contain `" + WorkflowLaunchersFacetId.SEPARATOR + "`");
		}

		this.asString = stringNorm;
	}

	@JsonCreator
	public static WorkflowLaunchersFacetGroupId ofString(@NonNull final String string)
	{
		return new WorkflowLaunchersFacetGroupId(string);
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	@NonNull
	public String getAsString() {return asString;}

	public static boolean equals(@Nullable WorkflowLaunchersFacetGroupId id1, @Nullable WorkflowLaunchersFacetGroupId id2) {return Objects.equals(id1, id2);}
}
