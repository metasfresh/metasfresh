package de.metas.ui.web.window.datatypes.json;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.ToString;

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
@ToString
public class JSONDocumentList
{
	private final List<JSONDocument> result;
	private final Set<DocumentId> missingIds;

	@Builder
	private JSONDocumentList(
			final List<JSONDocument> result,
			final Set<DocumentId> missingIds)
	{
		this.result = result != null ? ImmutableList.copyOf(result) : ImmutableList.of();
		this.missingIds = missingIds != null ? ImmutableSet.copyOf(missingIds) : ImmutableSet.of();
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class JSONDocumentListBuilder
	{
	}
}
