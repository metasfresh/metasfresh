package de.metas.ui.web.vaadin.window.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

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

public class DataWindowDescriptor
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final List<DataRowDescriptor> tabDescriptors;

	private DataWindowDescriptor(Builder builder)
	{
		super();
		
		this.tabDescriptors = ImmutableList.copyOf(builder.tabDescriptors);
	}

	public List<DataRowDescriptor> getTabDescriptors()
	{
		return tabDescriptors;
	}
	
	public static final class Builder
	{
		private List<DataRowDescriptor.Builder> tabBuilders = new ArrayList<>();
		private List<DataRowDescriptor> tabDescriptors;
		private Builder()
		{
			super();
		}
		
		public DataWindowDescriptor build()
		{
			final ImmutableMap.Builder<Integer, DataRowDescriptor> tabDescriptors = ImmutableMap.builder();
			for (final DataRowDescriptor.Builder tabBuilder : tabBuilders)
			{
				final DataRowDescriptor tabDescriptor = tabBuilder.build();
				tabDescriptors.put(tabDescriptor.getAD_Tab_ID(), tabDescriptor);
			}
			
			final Map<Integer, DataRowDescriptor> adTabId2tabDescriptor = tabDescriptors.build();
			
			//
			// Update field's included tab descriptor
			for (final DataRowDescriptor tabDescriptor : adTabId2tabDescriptor.values())
			{
				for (final DataFieldPropertyDescriptor fieldDescriptor : tabDescriptor.getFieldDescriptors())
				{
					final int includedTabId = fieldDescriptor.getIncludedTabId();
					if (includedTabId > 0)
					{
						fieldDescriptor.setIncludedTabDescriptor(adTabId2tabDescriptor.get(includedTabId));
					}
				}
			}
			
			this.tabDescriptors = ImmutableList.copyOf(adTabId2tabDescriptor.values());
			
			return new DataWindowDescriptor(this);
		}
		
		public Builder initFrom(final GridWindowVO gridWindowVO)
		{
			tabBuilders.clear();
			for (final GridTabVO gridTabVO : gridWindowVO.getTabs())
			{
				final DataRowDescriptor.Builder tabBuilder = DataRowDescriptor.builder()
						.initFrom(gridTabVO);
				tabBuilders.add(tabBuilder);
			}

			return this;
		}
		
		public Builder initFrom(final int windowNo, final int adWindowId)
		{
			final Properties ctx = Env.getCtx();
			final GridWindowVO gridWindowVO = GridWindowVO.create(ctx, windowNo, adWindowId);
			initFrom(gridWindowVO);
			return this;
		}
	}
}
