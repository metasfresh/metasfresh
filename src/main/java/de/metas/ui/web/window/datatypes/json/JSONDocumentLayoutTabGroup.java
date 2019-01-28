package de.metas.ui.web.window.datatypes.json;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DetailGroupId;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailGroupDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
@Builder
public class JSONDocumentLayoutTabGroup
{
	static List<JSONDocumentLayoutTabGroup> ofList(
			@NonNull final Collection<DocumentLayoutDetailGroupDescriptor> detailGroups,
			@NonNull final JSONOptions jsonOpts)
	{
		final ImmutableList.Builder<JSONDocumentLayoutTabGroup> result = ImmutableList.builder();

		for (final DocumentLayoutDetailGroupDescriptor detailGroup : detailGroups)
		{
			final List<JSONDocumentLayoutTab> tabs = JSONDocumentLayoutTab.ofList(detailGroup.getDetails(), jsonOpts);

			final JSONDocumentLayoutTabGroupBuilder tabGroup = JSONDocumentLayoutTabGroup
					.builder()
					.displayTabHeaderIfSingle(detailGroup.isDisplayTabHeaderIfSingle())
					.tabGroupId(detailGroup.getDetailGroupId())
					.caption(detailGroup.getCaption().translate(jsonOpts.getAD_Language()))
					.description(detailGroup.getDescription().translate(jsonOpts.getAD_Language()))
					.tabs(tabs);

			result.add(tabGroup.build());
		}

		return result.build();
	}

	@JsonProperty("tabGroupId")
	@NonNull
	private final DetailGroupId tabGroupId;

	@JsonProperty("internalName")
	@JsonInclude(Include.NON_EMPTY)
	@Nullable
	String internalName;

	@JsonProperty("caption")
	@JsonInclude(Include.NON_EMPTY)
	@Nullable
	String caption;

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	String description;

	@JsonProperty("displayTabHeaderIfSingle")
	boolean displayTabHeaderIfSingle;

	@JsonProperty("tabs")
	@NonNull
	List<JSONDocumentLayoutTab> tabs;
}
