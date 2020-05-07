package de.metas.ui.web.window.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.menu.MenuNode;
import de.metas.ui.web.menu.MenuTree;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReference;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesGroup;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesGroup.JSONDocumentReferencesGroupBuilder;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesGroupList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.util.lang.UIDStringUtil;
import lombok.Builder;
import lombok.NonNull;

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

/**
 * Aggregates {@link DocumentReferences} to {@link JSONDocumentReferencesGroupList}s.
 */
public class JSONDocumentReferencesGroupsAggregator
{
	private static final AdMessageKey MSG_MiscGroupCaption = AdMessageKey.of("DocumentReferences.group.Others");

	// Sort by Caption, but keep the "misc group" last
	private static final Comparator<JSONDocumentReferencesGroup> sorting = Comparator.<JSONDocumentReferencesGroup> //
			comparingInt(group -> group.isMiscGroup() ? 1 : 0)
			.thenComparing(JSONDocumentReferencesGroup::getCaption);

	private final MenuTree menuTree;
	private final String othersMenuCaption;
	private final JSONOptions jsonOpts;

	private final String othersGroupId = "_others_" + UIDStringUtil.createRandomUUID();
	private final HashMap<String, JSONDocumentReferencesGroupBuilder> groupsBuilders = new HashMap<>();

	@Builder
	private JSONDocumentReferencesGroupsAggregator(
			@NonNull final MenuTreeRepository menuTreeRepository,
			@NonNull final IMsgBL msgBL,
			@NonNull final JSONOptions jsonOpts,
			//
			@NonNull final UserRolePermissionsKey userRolePermissionsKey)
	{
		menuTree = menuTreeRepository.getMenuTree(userRolePermissionsKey, jsonOpts.getAdLanguage());

		othersMenuCaption = msgBL
				.getTranslatableMsgText(MSG_MiscGroupCaption)
				.translate(jsonOpts.getAdLanguage());

		this.jsonOpts = jsonOpts;
	}

	public JSONDocumentReferencesGroupsAggregator addAll(@NonNull final Collection<DocumentReference> documentReferences)
	{
		documentReferences.forEach(this::add);
		return this;
	}

	public JSONDocumentReferencesGroupsAggregator add(@NonNull final DocumentReference documentReference)
	{
		final JSONDocumentReference jsonDocumentReference = JSONDocumentReference.of(documentReference, jsonOpts);
		if (jsonDocumentReference == null)
		{
			return this;
		}

		final MenuNode topLevelMenuGroup = menuTree.getTopLevelMenuGroupOrNull(documentReference.getWindowId());
		final String topLevelMenuGroupId = topLevelMenuGroup != null ? topLevelMenuGroup.getId() : othersGroupId;

		final JSONDocumentReferencesGroupBuilder groupBuilder = groupsBuilders.computeIfAbsent(topLevelMenuGroupId, k -> {
			final boolean isMiscGroup = topLevelMenuGroup == null;
			final String caption = topLevelMenuGroup != null ? topLevelMenuGroup.getCaption() : othersMenuCaption;
			return JSONDocumentReferencesGroup.builder().caption(caption).isMiscGroup(isMiscGroup);
		});

		groupBuilder.reference(jsonDocumentReference);

		return this;
	}

	public JSONDocumentReferencesGroupList flush()
	{
		final List<JSONDocumentReferencesGroup> groups = flushGroups();
		return !groups.isEmpty()
				? new JSONDocumentReferencesGroupList(groups)
				: JSONDocumentReferencesGroupList.EMPTY;
	}

	private ImmutableList<JSONDocumentReferencesGroup> flushGroups()
	{
		if (groupsBuilders.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<JSONDocumentReferencesGroup> groups = groupsBuilders.values()
				.stream()
				.map(groupBuilder -> groupBuilder.build())
				.filter(group -> !group.isEmpty())
				.sorted(sorting)
				.collect(ImmutableList.toImmutableList());

		groupsBuilders.clear();

		return groups;
	}

	public void addAndFlush(
			@NonNull final DocumentReference documentReference,
			@NonNull final SseEmitter sseEmiter)
	{
		add(documentReference);
		final ImmutableList<JSONDocumentReferencesGroup> groups = flushGroups();
		if (groups.isEmpty())
		{
			return;
		}

		for (final JSONDocumentReferencesGroup group : groups)
		{
			try
			{
				sseEmiter.send(JSONDocumentReferencesEvent.partialResult(group));
			}
			catch (final IOException ex)
			{
				throw new AdempiereException("Failed sending " + group, ex);
			}
		}
	}
}
