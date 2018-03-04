package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.process.AD_Window_CreateUIElements.IWindowUIElementsGeneratorConsumer;
import org.adempiere.ad.window.process.AD_Window_CreateUIElements.WindowUIElementsGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class InMemoryUIElementsProvider implements IWindowUIElementsGeneratorConsumer, IWindowUIElementsProvider
{
	private static final Logger logger = LogManager.getLogger(InMemoryUIElementsProvider.class);

	private final ListMultimap<Integer, I_AD_UI_Section> adTabId2sections = LinkedListMultimap.create();
	private final ListMultimap<I_AD_UI_Section, I_AD_UI_Column> section2columns = Multimaps.newListMultimap(Maps.newIdentityHashMap(), () -> Lists.newLinkedList());
	private final ListMultimap<I_AD_UI_Column, I_AD_UI_ElementGroup> column2elementGroups = Multimaps.newListMultimap(Maps.newIdentityHashMap(), () -> Lists.newLinkedList());
	private final ListMultimap<I_AD_UI_ElementGroup, I_AD_UI_Element> elementGroup2elements = Multimaps.newListMultimap(Maps.newIdentityHashMap(), () -> Lists.newLinkedList());
	private final ListMultimap<I_AD_UI_Element, I_AD_UI_ElementField> element2elementFields = Multimaps.newListMultimap(Maps.newIdentityHashMap(), () -> Lists.newLinkedList());

	@Override
	public void consume(final I_AD_UI_Section uiSection, final I_AD_Tab parent)
	{
		logger.info("Generated in memory {} for {}", uiSection, parent);
		adTabId2sections.put(parent.getAD_Tab_ID(), uiSection);
	}

	@Override
	public List<I_AD_UI_Section> getUISections(final int AD_Tab_ID)
	{
		// Generate the UI elements if needed
		if (!adTabId2sections.containsKey(AD_Tab_ID))
		{
			final WindowUIElementsGenerator generator = WindowUIElementsGenerator.forConsumer(this);

			final I_AD_Tab adTab = InterfaceWrapperHelper.create(Env.getCtx(), AD_Tab_ID, I_AD_Tab.class, ITrx.TRXNAME_ThreadInherited);
			
			final boolean primaryTab = adTab.getTabLevel() == 0;
			if (primaryTab)
			{
				generator.migratePrimaryTab(adTab);
			}
			else
			{
				generator.migrateDetailTab(adTab);
			}
		}

		return adTabId2sections.get(AD_Tab_ID);
	}

	@Override
	public void consume(final I_AD_UI_Column uiColumn, final I_AD_UI_Section parent)
	{
		logger.info("Generated in memory {} for {}", uiColumn, parent);
		section2columns.put(parent, uiColumn);
	}

	@Override
	public List<I_AD_UI_Column> getUIColumns(final I_AD_UI_Section uiSection)
	{
		return section2columns.get(uiSection);
	}

	@Override
	public void consume(final I_AD_UI_ElementGroup uiElementGroup, final I_AD_UI_Column parent)
	{
		logger.info("Generated in memory {} for {}", uiElementGroup, parent);
		column2elementGroups.put(parent, uiElementGroup);
	}

	@Override
	public List<I_AD_UI_ElementGroup> getUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		return column2elementGroups.get(uiColumn);
	}

	@Override
	public void consume(final I_AD_UI_Element uiElement, final I_AD_UI_ElementGroup parent)
	{
		logger.info("Generated in memory {} for {}", uiElement, parent);
		elementGroup2elements.put(parent, uiElement);
	}

	@Override
	public List<I_AD_UI_Element> getUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		return elementGroup2elements.get(uiElementGroup);
	}
	
	@Override
	public List<I_AD_UI_Element> getUIElementsOfTypeLabels(final int adTabId)
	{
		return ImmutableList.of();
	}

	@Override
	public void consume(final I_AD_UI_ElementField uiElementField, final I_AD_UI_Element parent)
	{
		logger.info("Generated in memory {} for {}", uiElementField, parent);
		element2elementFields.put(parent, uiElementField);
	}

	@Override
	public List<I_AD_UI_ElementField> getUIElementFields(final I_AD_UI_Element uiElement)
	{
		return element2elementFields.get(uiElement);
	}
}