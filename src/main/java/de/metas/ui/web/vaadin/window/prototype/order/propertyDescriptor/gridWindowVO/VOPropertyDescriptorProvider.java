package de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.gridWindowVO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.Check;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimaps;
import com.google.gwt.dev.util.collect.HashMap;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor.Builder;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptorType;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.IPropertyDescriptorProvider;

/*
 * #%L
 * metasfresh-webui
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

public class VOPropertyDescriptorProvider implements IPropertyDescriptorProvider
{

	@Override
	public PropertyDescriptor provideEntryWindowdescriptor()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PropertyDescriptor provideForWindow(int AD_Window_ID)
	{
		final GridWindowVO gridWindowVO = GridWindowVO.create(Env.getCtx(), 0, AD_Window_ID);

		final Builder rootBuilder = PropertyDescriptor.builder().setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot);
		final List<GridTabVO> gridTabsVO = gridWindowVO.getTabs();

		final GridTabVO rootTab = gridTabsVO.get(0);

		rootBuilder
				.setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot)
				.setSqlTableName(rootTab.TableName);

		Map<Integer, PropertyDescriptor> level2currentDescriptor = new HashMap<>();

		for (final GridTabVO tab : gridTabsVO)
		{
			// we again look also at the root tab now.
			final Builder tabBuilder = PropertyDescriptor.builder();

			tabBuilder
					.setPropertyName(tab.TableName)
					.setType(PropertyDescriptorType.Group);

			final ArrayList<GridFieldVO> fields = tab.getFields();

			final ImmutableMultimap<String, GridFieldVO> fieldGroups = Multimaps.index(fields, VOPropertyDescriptorProvider::getFieldGroupName);

			final Set<Integer> fieldIdsAdded = new HashSet<>();

			fields.forEach(p -> {
				if (fieldIdsAdded.contains(p.getAD_Field_ID()))
				{
					return;
				}

				// create this field's PropertyDescriptor
				// TODO here we need that path-propertyname
				final Builder fieldBuilder = PropertyDescriptor.builder()
						.setPropertyName(p.getColumnName());

				fieldIdsAdded.add(p.getAD_Field_ID());

				// iterate all other fields of this field's field group and flag them as added, then go on with this loop
			});

			final PropertyDescriptor tabDescriptor = tabBuilder.build();
			rootBuilder.addChildPropertyDescriptor(tabDescriptor);
			level2currentDescriptor.put(tab.TabLevel, tabDescriptor);

		}

		return rootBuilder.build();
	}

	final static String NOFIELDGROUP = "NOFIELDGROUP";

	private static String getFieldGroupName(final GridFieldVO field)
	{
		final String fieldGroupName;
		if (field.getFieldGroup() == null)
		{
			fieldGroupName = NOFIELDGROUP;
		}
		else
		{
			if (Check.isEmpty(field.getFieldGroup().getFieldGroupName(), true))
			{
				fieldGroupName = NOFIELDGROUP;
			}
			else
			{
				fieldGroupName = field.getFieldGroup().getFieldGroupName();
			}
		}
		return fieldGroupName;
	}

}
