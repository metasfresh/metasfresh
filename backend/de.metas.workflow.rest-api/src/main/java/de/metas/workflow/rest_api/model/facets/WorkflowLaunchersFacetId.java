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
import com.google.common.base.Splitter;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.util.JSONObjectMapper;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
public final class WorkflowLaunchersFacetId
{
	@NonNull @Getter private final WorkflowLaunchersFacetGroupId groupId;
	@NonNull private final String value;

	static final String SEPARATOR = "_";
	private static final String QTY_SEPARATOR = "|";

	private WorkflowLaunchersFacetId(
			@NonNull final WorkflowLaunchersFacetGroupId groupId,
			@NonNull final String value)
	{
		this.groupId = groupId;
		this.value = value;
	}

	@JsonCreator
	@Nullable
	public static WorkflowLaunchersFacetId fromJson(@Nullable final String json)
	{
		final String jsonNorm = StringUtils.trimBlankToNull(json);
		if (jsonNorm == null)
		{
			return null;
		}

		final int idx = jsonNorm.indexOf(SEPARATOR);
		if (idx <= 0)
		{
			throw new AdempiereException("Canont convert JSON `" + jsonNorm + "` to " + WorkflowLaunchersFacetId.class);
		}

		final String groupIdPart = json.substring(0, idx);
		final String facetIdPart = json.substring(idx + SEPARATOR.length());
		if (facetIdPart.isEmpty())
		{
			throw new AdempiereException("Canont convert JSON `" + jsonNorm + "` to " + WorkflowLaunchersFacetId.class);
		}

		final WorkflowLaunchersFacetGroupId groupId = WorkflowLaunchersFacetGroupId.ofString(groupIdPart);
		return new WorkflowLaunchersFacetId(groupId, facetIdPart);
	}

	public static WorkflowLaunchersFacetId ofString(@NonNull final WorkflowLaunchersFacetGroupId groupId, @NonNull final String string)
	{
		return new WorkflowLaunchersFacetId(groupId, string);
	}

	public static WorkflowLaunchersFacetId ofId(@NonNull final WorkflowLaunchersFacetGroupId groupId, @NonNull final RepoIdAware id)
	{
		return ofString(groupId, String.valueOf(id.getRepoId()));
	}

	public static WorkflowLaunchersFacetId ofLocalDate(@NonNull final WorkflowLaunchersFacetGroupId groupId, @NonNull final LocalDate localDate)
	{
		return ofString(groupId, localDate.toString());
	}

	// NOTE: Quantity class is not accessible from this project :(
	public static WorkflowLaunchersFacetId ofQuantity(@NonNull final WorkflowLaunchersFacetGroupId groupId, @NonNull final BigDecimal value, @NonNull final RepoIdAware uomId)
	{
		return ofString(groupId, value + QTY_SEPARATOR + uomId.getRepoId());
	}

	@Override
	@Deprecated
	public String toString() {return toJsonString();}

	@JsonValue
	@NonNull
	public String toJsonString() {return groupId.getAsString() + SEPARATOR + value;}

	@NonNull
	public <T extends RepoIdAware> T getAsId(@NonNull final Class<T> type) {return RepoIdAwares.ofObject(value, type);}

	@NonNull
	public LocalDate getAsLocalDate() {return LocalDate.parse(value);}

	// NOTE: Quantity class is not accessible from this project :(
	@NonNull
	public ImmutablePair<BigDecimal, Integer> getAsQuantity()
	{
		try
		{
			final List<String> parts = Splitter.on(QTY_SEPARATOR).splitToList(value);
			if (parts.size() != 2)
			{
				throw new AdempiereException("Cannot get Quantity from " + this);
			}
			
			return ImmutablePair.of(new BigDecimal(parts.get(0)), Integer.parseInt(parts.get(1)));
		}
		catch (AdempiereException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Cannot get Quantity from " + this, ex);
		}
	}

	@NonNull
	public String getAsString() {return value;}

	@NonNull
	public <T> T deserializeTo(@NonNull final Class<T> targetClass)
	{
		return JSONObjectMapper.forClass(targetClass).readValue(value);
	}
}
