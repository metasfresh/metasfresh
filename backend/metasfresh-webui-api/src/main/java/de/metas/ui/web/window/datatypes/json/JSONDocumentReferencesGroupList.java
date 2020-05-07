package de.metas.ui.web.window.datatypes.json;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.menu.MenuNode;
import de.metas.ui.web.menu.MenuTree;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesGroup.JSONDocumentReferencesGroupBuilder;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.util.lang.UIDStringUtil;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class JSONDocumentReferencesGroupList
{
	public static JSONDocumentReferencesGroupList of( //
			final Collection<DocumentReference> documentReferences //
			, final MenuTree menuTree //
			, final String othersMenuCaption //
			, final JSONOptions jsonOpts //
	)
	{
		if (documentReferences.isEmpty())
		{
			return EMPTY;
		}

		final Map<String, JSONDocumentReferencesGroupBuilder> groupsBuilders = new HashMap<>();

		final String othersGroupId = "_others_" + UIDStringUtil.createRandomUUID();

		for (final DocumentReference documentReference : documentReferences)
		{
			final JSONDocumentReference jsonDocumentReference = JSONDocumentReference.of(documentReference, jsonOpts);
			if (jsonDocumentReference == null)
			{
				continue;
			}

			final MenuNode topLevelMenuGroup = menuTree.getTopLevelMenuGroupOrNull(documentReference.getWindowId());
			final String topLevelMenuGroupId = topLevelMenuGroup != null ? topLevelMenuGroup.getId() : othersGroupId;

			final JSONDocumentReferencesGroupBuilder groupBuilder = groupsBuilders.computeIfAbsent(topLevelMenuGroupId, k -> {
				final boolean isMiscGroup = topLevelMenuGroup == null;
				final String caption = topLevelMenuGroup != null ? topLevelMenuGroup.getCaption() : othersMenuCaption;
				return JSONDocumentReferencesGroup.builder().caption(caption).isMiscGroup(isMiscGroup);
			});

			groupBuilder.reference(jsonDocumentReference);
		}

		// Sort by Caption, but keep the "misc group" last
		Comparator<JSONDocumentReferencesGroup> sorting = Comparator.<JSONDocumentReferencesGroup>comparingInt(group -> group.isMiscGroup() ? 1 : 0)
				.thenComparing(JSONDocumentReferencesGroup::getCaption);

		final List<JSONDocumentReferencesGroup> groups = groupsBuilders.values()
				.stream()
				.map(groupBuilder -> groupBuilder.build())
				.filter(group -> !group.isEmpty())
				.sorted(sorting)
				.collect(ImmutableList.toImmutableList());

		return new JSONDocumentReferencesGroupList(groups);
	}

	public static final JSONDocumentReferencesGroupList EMPTY = new JSONDocumentReferencesGroupList(ImmutableList.of());

	@JsonProperty("groups")
	private final List<JSONDocumentReferencesGroup> groups;

	// TODO: delete this property after https://github.com/metasfresh/metasfresh-webui-frontend/issues/719 is implemented.
	@JsonProperty("references")
	@Deprecated
	private final List<JSONDocumentReference> references;

	@JsonCreator
	private JSONDocumentReferencesGroupList(@JsonProperty("groups") final List<JSONDocumentReferencesGroup> groups)
	{
		this.groups = groups == null || groups.isEmpty() ? ImmutableList.of() : ImmutableList.copyOf(groups);

		references = this.groups.stream()
				.flatMap(group -> group.getReferences().stream())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(groups)
				.toString();
	}

}
