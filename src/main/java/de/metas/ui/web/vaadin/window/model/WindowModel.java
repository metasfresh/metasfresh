package de.metas.ui.web.vaadin.window.model;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataWindowDescriptor;
/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class WindowModel
{
	private static final Logger logger = LogManager.getLogger(WindowModel.class);

	private final DataWindowDescriptor descriptor;
	private final Map<Integer, TabModel> tabModels;

	public WindowModel(final WindowContext windowContext, final DataWindowDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;

		//
		// Create tab models
		final ImmutableMap.Builder<Integer, TabModel> tabModelsBuilder = ImmutableMap.builder();
		for (final DataRowDescriptor tabDescriptor : descriptor.getTabDescriptors())
		{
			final TabModel tabModel = new TabModel(windowContext, tabDescriptor);
			tabModelsBuilder.put(tabModel.getTabNo(), tabModel);
		}
		this.tabModels = tabModelsBuilder.build();

		//
		// Link tabs
		for (final TabModel tabModel : tabModels.values())
		{
			// Link parent <-> child tab models
			final TabModel parentTabModel = findParentTabModel(tabModel, tabModels);
			if (parentTabModel != null)
			{
				tabModel.setParentTabModel(parentTabModel);
				parentTabModel.addChildTabModel(tabModel);
			}
		}
	}

	private static TabModel findParentTabModel(final TabModel tabModel, final Map<Integer, TabModel> tabNo2tabModel)
	{
		final int tabNo = tabModel.getTabNo();
		final int tabLevel = tabModel.getTabLevel();
		if (tabLevel <= 0)
		{
			return null;
		}

		// usually, the parent tab's level is currentLevel - 1, but sometimes the "level-gap" might be larger, like e.g. in the Rechnung window (MatchInv-level is 2, parent tab's level is 0)
		final int parentLevelMax = tabLevel - 1;

		int currentLevel = tabLevel;
		int currentTabNo = tabNo;
		TabModel currentTabModel = null;
		while (parentLevelMax < currentLevel)
		{
			if (currentTabNo < 0)
			{
				logger.warn("No parent TabNo found for '" + tabModel + "': TabNo=" + tabModel.getTabNo() + ", TabLevel=" + tabModel.getTabLevel() + ", expected parent TabLevel=" + parentLevelMax);
				break;
			}
			currentTabNo--;

			currentTabModel = tabNo2tabModel.get(currentTabNo);
			if (currentTabModel == null)
			{
				continue;
			}
			currentLevel = currentTabModel.getTabLevel();
		}
		return currentTabModel;
	}

	public DataWindowDescriptor getDescriptor()
	{
		return descriptor;
	}

	public Collection<TabModel> getTabModels()
	{
		return tabModels.values();
	}

}
