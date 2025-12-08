package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.descriptor.LayoutElementType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIColumnId;
import org.adempiere.ad.element.api.AdUIElementGroupId;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.ad.element.api.AdUISectionId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

final class DAOWindowUIElementsProvider implements IWindowUIElementsProvider
{
	private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	@NonNull private final ArrayListMultimap<AdTabId, I_AD_UI_Section> uiSectionsByTabId = ArrayListMultimap.create();
	@NonNull private final ArrayListMultimap<AdUISectionId, I_AD_UI_Column> uiColumnsBySectionId = ArrayListMultimap.create();
	@NonNull private final ArrayListMultimap<AdUIColumnId, I_AD_UI_ElementGroup> uiElementGroupByColumnId = ArrayListMultimap.create();
	@NonNull private final ArrayListMultimap<AdUIElementGroupId, I_AD_UI_Element> uiElementsByElementGroupId = ArrayListMultimap.create();
	@NonNull private final ArrayListMultimap<AdTabId, I_AD_UI_Element> uiElementsByTabId = ArrayListMultimap.create();
	@NonNull private final ArrayListMultimap<AdUIElementId, I_AD_UI_ElementField> uiFieldsByElementId = ArrayListMultimap.create();

	@Override
	public List<I_AD_UI_Section> getUISections(final AdTabId adTabId)
	{
		return uiSectionsByTabId.get(adTabId);
	}

	@Override
	public List<I_AD_UI_Column> getUIColumns(final I_AD_UI_Section uiSection)
	{
		return uiColumnsBySectionId.get(extractUISectionId(uiSection));
	}

	@Override
	public List<I_AD_UI_ElementGroup> getUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		return uiElementGroupByColumnId.get(extractUIColumnId(uiColumn));
	}

	@Override
	public List<I_AD_UI_Element> getUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		return uiElementsByElementGroupId.get(extractUIElementGroupId(uiElementGroup));
	}

	@Override
	public List<I_AD_UI_Element> getUIElementsOfType(@NonNull final AdTabId adTabId, @NonNull final LayoutElementType layoutElementType)
	{
		return uiElementsByTabId.get(adTabId).stream()
				.filter(uiElement -> layoutElementType.getCode().equals(uiElement.getAD_UI_ElementType()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<I_AD_UI_ElementField> getUIElementFields(final I_AD_UI_Element uiElement)
	{
		return uiFieldsByElementId.get(extractUIElementId(uiElement));
	}

	public void warmUp(final Set<AdTabId> adTabIds)
	{
		windowDAO.retrieveUISections(adTabIds)
				.forEach(uiSection -> uiSectionsByTabId.put(extractTabId(uiSection), uiSection));

		windowDAO.retrieveUIColumns(extractUISectionIds(uiSectionsByTabId.values()))
				.forEach(uiColumn -> uiColumnsBySectionId.put(extractUISectionId(uiColumn), uiColumn));

		windowDAO.retrieveUIElementGroups(extractUIColumnIds(uiColumnsBySectionId.values()))
				.forEach(uiElementGroup -> uiElementGroupByColumnId.put(extractUIColumnId(uiElementGroup), uiElementGroup));

		windowDAO.retrieveUIElements(extractUIElementGroupIds(uiElementGroupByColumnId.values()))
				.forEach(uiElement -> {
					uiElementsByElementGroupId.put(extractUIElementGroupId(uiElement), uiElement);
					uiElementsByTabId.put(extractTabId(uiElement), uiElement);
				});

		windowDAO.retrieveUIElementFields(extractUIElementIds(uiElementsByElementGroupId.values()))
				.forEach(uiElementField -> uiFieldsByElementId.put(extractUIElementId(uiElementField), uiElementField));
	}

	private static AdTabId extractTabId(final I_AD_UI_Section from) {return AdTabId.ofRepoId(from.getAD_Tab_ID());}

	private static ImmutableSet<AdUISectionId> extractUISectionIds(final Collection<I_AD_UI_Section> uiSections)
	{
		return uiSections.stream().map(DAOWindowUIElementsProvider::extractUISectionId).distinct().collect(ImmutableSet.toImmutableSet());
	}

	private static AdUISectionId extractUISectionId(final I_AD_UI_Section from) {return AdUISectionId.ofRepoId(from.getAD_UI_Section_ID());}

	private ImmutableSet<AdUIColumnId> extractUIColumnIds(final Collection<I_AD_UI_Column> uiColumns)
	{
		return uiColumns.stream().map(DAOWindowUIElementsProvider::extractUIColumnId).distinct().collect(ImmutableSet.toImmutableSet());
	}

	private static AdUIColumnId extractUIColumnId(final I_AD_UI_Column from) {return AdUIColumnId.ofRepoId(from.getAD_UI_Column_ID());}

	private static AdUISectionId extractUISectionId(final I_AD_UI_Column from) {return AdUISectionId.ofRepoId(from.getAD_UI_Section_ID());}

	private static AdUIColumnId extractUIColumnId(final I_AD_UI_ElementGroup from) {return AdUIColumnId.ofRepoId(from.getAD_UI_Column_ID());}

	private ImmutableSet<AdUIElementGroupId> extractUIElementGroupIds(final Collection<I_AD_UI_ElementGroup> froms)
	{
		return froms.stream().map(DAOWindowUIElementsProvider::extractUIElementGroupId).distinct().collect(ImmutableSet.toImmutableSet());
	}

	private static AdUIElementGroupId extractUIElementGroupId(final I_AD_UI_ElementGroup from) {return AdUIElementGroupId.ofRepoId(from.getAD_UI_ElementGroup_ID());}

	private static AdUIElementGroupId extractUIElementGroupId(final I_AD_UI_Element from) {return AdUIElementGroupId.ofRepoId(from.getAD_UI_ElementGroup_ID());}

	private static AdTabId extractTabId(final I_AD_UI_Element from) {return AdTabId.ofRepoId(from.getAD_Tab_ID());}

	private ImmutableSet<AdUIElementId> extractUIElementIds(final Collection<I_AD_UI_Element> froms)
	{
		return froms.stream().map(DAOWindowUIElementsProvider::extractUIElementId).distinct().collect(ImmutableSet.toImmutableSet());
	}

	private static AdUIElementId extractUIElementId(final I_AD_UI_Element from) {return AdUIElementId.ofRepoId(from.getAD_UI_Element_ID());}

	private static AdUIElementId extractUIElementId(final I_AD_UI_ElementField from) {return AdUIElementId.ofRepoId(from.getAD_UI_Element_ID());}
}
