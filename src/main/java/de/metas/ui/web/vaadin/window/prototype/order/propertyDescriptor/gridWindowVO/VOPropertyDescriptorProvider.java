package de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.gridWindowVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_Column;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.gwt.dev.util.collect.HashMap;
import com.ibm.icu.math.BigDecimal;

import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor.Builder;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptorType;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;
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

		final ImmutableMap<Integer, GridTabVO> id2gridTabTabVO = Maps.uniqueIndex(gridTabsVO, x -> x.AD_Tab_ID);

		final GridTabVO rootTab = gridTabsVO.get(0);

		rootBuilder
				.setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot)
				.setSqlTableName(rootTab.TableName);

		Map<Integer, PropertyDescriptor> level2currentDescriptor = new HashMap<>();

		final Set<Integer> tabIdsAdded = new HashSet<>();

		gridTabsVO.forEach(tab -> {

			if (!tabIdsAdded.add(tab.AD_Tab_ID))
			{
				// was already handled
				return;
			}

			// we again look also at the root tab now.
			final PropertyDescriptor tabDescriptor = createAndAddTabDescriptor(rootBuilder, tab, tabIdsAdded, id2gridTabTabVO);
			level2currentDescriptor.put(tab.TabLevel, tabDescriptor);

		});

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

	/**
	 * Creates a builder for the given <code>tab</code>. If the given tab contains an included tab, then this method also creates that included tab.
	 *
	 * @param parentBuilder
	 * @param tab
	 * @param tabIdsAdded
	 * @param id2gridTabTabVO
	 * @return
	 */
	private PropertyDescriptor createAndAddTabDescriptor(
			final Builder parentBuilder,
			final GridTabVO tab,
			final Set<Integer> tabIdsAdded,
			final Map<Integer, GridTabVO> id2gridTabTabVO)
	{
		final ArrayList<GridFieldVO> fields = tab.getFields();
		final ImmutableMultimap<String, GridFieldVO> fieldGroups = Multimaps.index(fields, VOPropertyDescriptorProvider::getFieldGroupName);

		final Builder tabBuilder = PropertyDescriptor.builder()
				.setPropertyName(PropertyName.of(parentBuilder.getPropertyName(), tab.TableName))
				.setType(PropertyDescriptorType.Group);
		setParentColumnnameIfApplicable(tab, tabBuilder);

		final Set<Integer> fieldIdsAdded = new HashSet<>();

		fields.forEach(field -> {

			if (fieldIdsAdded.contains(field.getAD_Field_ID()))
			{
				return;
			}

			if (field.Included_Tab_ID > 0)
			{
				// the tab's fields, in a tabular fashion
				// ignore fieldgroups
				final GridTabVO includedGridTabVO = id2gridTabTabVO.get(field.Included_Tab_ID);

				final Builder includedTabBuilder = PropertyDescriptor.builder()
						.setPropertyName(PropertyName.of(tabBuilder.getPropertyName(), field.getColumnName()))
						.setType(PropertyDescriptorType.Tabular)
						.setSqlTableName(includedGridTabVO.TableName);

				setParentColumnnameIfApplicable(includedGridTabVO, includedTabBuilder);

				includedGridTabVO.getFields().forEach(fieldOfInclTab -> {

					// create this field's PropertyDescriptor
					fieldIdsAdded.add(
							createFieldBuilder(includedTabBuilder, fieldOfInclTab));
				});

				tabIdsAdded.add(includedGridTabVO.AD_Tab_ID);

				// ...

				tabBuilder.addChildPropertyDescriptor(includedTabBuilder.build());
			}
			else
			{
				final String fieldGroupName = getFieldGroupName(field);

				final Builder groupBuilder = PropertyDescriptor.builder()
						.setPropertyName(PropertyName.of(tabBuilder.getPropertyName(), fieldGroupName))
						.setType(PropertyDescriptorType.ComposedValue);

				fieldGroups.get(fieldGroupName).forEach(fieldOfGroup -> {

					// create this field's PropertyDescriptor
					fieldIdsAdded.add(
							createFieldBuilder(groupBuilder, fieldOfGroup));

				});

				// ...

				tabBuilder.addChildPropertyDescriptor(groupBuilder.build());
			}

		});

		final PropertyDescriptor tabDescriptor = tabBuilder.build();
		parentBuilder.addChildPropertyDescriptor(tabDescriptor);

		return tabDescriptor;
	}

	private void setParentColumnnameIfApplicable(final GridTabVO gridTabVO, final Builder tabBuilder)
	{
		if (gridTabVO.getParent_Column_ID() <= 0)
		{
			return;
		}
		final I_AD_Column parentColumn = InterfaceWrapperHelper.create(Env.getCtx(), gridTabVO.getParent_Column_ID(), I_AD_Column.class, ITrx.TRXNAME_ThreadInherited);
		if (parentColumn == null)
		{
			return;
		}
		tabBuilder.setSqlParentLinkColumnName(parentColumn.getColumnName());
	}

	private int createFieldBuilder(Builder parentBuilder, GridFieldVO field)
	{
		final Builder fieldBuilder = PropertyDescriptor.builder()
				.setType(PropertyDescriptorType.Value)
				.setPropertyName(PropertyName.of(parentBuilder.getPropertyName(), field.getColumnName()))
				.setComposedValuePartName(field.getColumnName()) // hopefully not a problem if this isn't an actual composed value
				.setValueType(getValueType(field))

		// shall be factored out of the descriptor
				.setSqlColumnSql(field.getColumnSQL(true))
				.setSqlColumnName(field.getColumnName())
				;

		parentBuilder.addChildPropertyDescriptor(fieldBuilder.build());
		return field.getAD_Field_ID();
	}

	private Class<?> getValueType(GridFieldVO field)
	{
		final int displayType = field.getDisplayType();
		final Class<?> valueType;
		if (DisplayType.isLookup(displayType))
		{
			valueType = LookupValue.class;
		}
		else if (DisplayType.isDate(displayType))
		{
			valueType = Date.class;
		}
		else if (DisplayType.isText(displayType))
		{
			valueType = String.class;
		}
		else if (DisplayType.Integer == displayType)
		{
			valueType = Integer.class;
		}
		else if (DisplayType.isNumeric(displayType))
		{
			valueType = BigDecimal.class;
		}
		else
		{
			valueType = String.class;
		}
		return valueType;
	}

}
