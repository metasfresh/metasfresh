package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.ui.web.view.json.JSONViewOrderBy;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE) // cannot use it because of "otherProperties"
@EqualsAndHashCode
@ToString
public class JSONDocumentList
{
	@NonNull private final List<JSONDocument> result;
	@NonNull private final Set<DocumentId> missingIds;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@NonNull private final List<JSONViewOrderBy> orderBys;

	@Builder(toBuilder = true)
	@Jacksonized
	private JSONDocumentList(
			@Nullable final List<JSONDocument> result,
			@Nullable final Set<DocumentId> missingIds,
			@Nullable final List<JSONViewOrderBy> orderBys)
	{
		this.result = result != null ? ImmutableList.copyOf(result) : ImmutableList.of();
		this.missingIds = normalizeMissingIds(missingIds);
		this.orderBys = orderBys != null ? ImmutableList.copyOf(orderBys) : ImmutableList.of();
	}

	private static ImmutableSet<DocumentId> normalizeMissingIds(final @Nullable Set<DocumentId> missingIds)
	{
		return missingIds != null ? ImmutableSet.copyOf(missingIds) : ImmutableSet.of();
	}

	public static JSONDocumentList ofDocumentsList(
			@NonNull final OrderedDocumentsList documents,
			@NonNull final JSONDocumentOptions options,
			@Nullable final Boolean hasComments)
	{
		return builder()
				.result(JSONDocument.ofDocumentsList(documents.toList(), options, hasComments))
				.orderBys(JSONViewOrderBy.ofList(documents.getOrderBys()))
				.build();
	}

	public List<JSONDocument> toList() {return result;}

	public JSONDocumentList withMissingIdsUpdatedFromExpectedRowIds(@NonNull final DocumentIdsSelection expectedRowIds)
	{
		final ImmutableSet<DocumentId> missingIdsNew = normalizeMissingIds(computeMissingIdsFromExpectedRowIds(expectedRowIds));
		if (Objects.equals(this.missingIds, missingIdsNew))
		{
			return this;
		}

		return toBuilder().missingIds(missingIdsNew).build();
	}

	public Set<DocumentId> computeMissingIdsFromExpectedRowIds(final DocumentIdsSelection expectedRowIds)
	{
		if (expectedRowIds.isEmpty() || expectedRowIds.isAll())
		{
			return null;
		}
		else
		{
			final ImmutableSet<DocumentId> existingRowIds = getRowIds();
			return Sets.difference(expectedRowIds.toSet(), existingRowIds);
		}
	}

	private ImmutableSet<DocumentId> getRowIds()
	{
		return result.stream()
				.map(JSONDocument::getRowId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
