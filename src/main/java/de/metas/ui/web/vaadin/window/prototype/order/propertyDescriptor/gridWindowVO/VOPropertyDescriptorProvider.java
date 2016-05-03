package de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.gridWindowVO;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.FieldGroupVO;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_Column;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor.Builder;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptorType;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyLayoutInfo;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;
import de.metas.ui.web.vaadin.window.prototype.order.model.TraceHelper;
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
	public PropertyDescriptor provideForWindow(final int AD_Window_ID)
	{
		final Properties ctx = Env.getCtx(); // TODO
		final int windowNo = 0; // TODO
		final GridWindowVO gridWindowVO = GridWindowVO.create(ctx, windowNo, AD_Window_ID);

		final PropertyDescriptor root = new RootPropertyDescriptorBuilder()
				.add(gridWindowVO)
				.build();

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Build root descriptor " + TraceHelper.toStringRecursivelly(root));
		System.out.println("--------------------------------------------------------------------------------");
		return root;
	}

	private static final class RootPropertyDescriptorBuilder
	{
		private static final Logger logger = LogManager.getLogger(RootPropertyDescriptorBuilder.class);

		private Properties ctx;
		private PropertyDescriptor.Builder rootBuilder;
		private List<GridTabVO> gridTabsVO;
		private Map<Object, GridTabVO> id2gridTabVO;
		private final Set<Integer> tabIdsAdded = new HashSet<>();
		private final Set<Integer> fieldIdsAdded = new HashSet<>();

		private String parent_SqlTableName = null;

		private RootPropertyDescriptorBuilder()
		{
			super();
		}

		public PropertyDescriptor build()
		{
			return rootBuilder.build();
		}

		public RootPropertyDescriptorBuilder add(final GridWindowVO gridWindowVO)
		{
			logger.debug("Adding {}", gridWindowVO);

			ctx = gridWindowVO.getCtx();
			gridTabsVO = gridWindowVO.getTabs();
			id2gridTabVO = Maps.uniqueIndex(gridTabsVO, x -> x.getAD_Tab_ID());

			
			final GridTabVO rootTab = gridTabsVO.get(0);
			final String rootTableName = rootTab.getTableName();
			rootBuilder = PropertyDescriptor.builder()
					.setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot)
					.setSqlTableName(rootTableName);
			
			
			this.parent_SqlTableName = rootTableName;
			try
			{
				createAndAddTab(rootBuilder, rootTab);
			}
			finally
			{
				parent_SqlTableName = null;
			}

			return this;
		}

		/**
		 * Creates a builder for the given <code>tab</code>. If the given tab contains an included tab, then this method also creates that included tab.
		 *
		 * @param parentBuilder
		 * @param gridTabVO
		 * @return
		 */
		private void createAndAddTab(final PropertyDescriptor.Builder parentBuilder, final GridTabVO gridTabVO)
		{
			if (!tabIdsAdded.add(gridTabVO.getAD_Tab_ID()))
			{
				logger.debug("Skip adding {} because it was already considered", gridTabVO);
				return;
			}
			
			logger.debug("Adding {} to {}", gridTabVO, parentBuilder);

			final LinkedHashMap<String, PropertyDescriptor.Builder> groupBuilders = new LinkedHashMap<>();

			gridTabVO.getFields().forEach(gridFieldVO -> {

				if (fieldIdsAdded.contains(gridFieldVO.getAD_Field_ID()))
				{
					return;
				}

				if (gridFieldVO.getIncluded_Tab_ID() > 0)
				{
					final PropertyDescriptor.Builder builder = createIncludedTab(parentBuilder.getPropertyName(), gridFieldVO);
					if (builder == null)
					{
						return;
					}

					final String key = "IncludedTab_" + gridFieldVO.getIncluded_Tab_ID();
					groupBuilders.put(key, builder);
				}
				else
				{
					final PropertyDescriptor.Builder groupBuilder = getCreateGroupBuilder(groupBuilders, gridTabVO, gridFieldVO);
					createAndAddField(groupBuilder, gridFieldVO);
				}

			});
			
			// TODO: find out other child tabs and include them here

			for (final PropertyDescriptor.Builder groupBuilder : groupBuilders.values())
			{
				final PropertyDescriptor group = groupBuilder.build();
				parentBuilder.addChildPropertyDescriptor(group);

				logger.debug("Added {} to {}", group, parentBuilder);
			}
		}

		private PropertyDescriptor.Builder getCreateGroupBuilder(final Map<String, Builder> groupBuilders, final GridTabVO gridTabVO, final GridFieldVO gridFieldVO)
		{
			final String fieldGroupName = getFieldGroupName(gridTabVO, gridFieldVO);
			final String groupKey = "FieldGroup_" + fieldGroupName;
			PropertyDescriptor.Builder groupBuilder = groupBuilders.get(groupKey);
			if (groupBuilder == null)
			{
				groupBuilder = PropertyDescriptor.builder()
						.setPropertyName(PropertyName.of(fieldGroupName))
						.setType(PropertyDescriptorType.Group)
						.setCaption(fieldGroupName);
				groupBuilders.put(groupKey, groupBuilder);
			}
			return groupBuilder;
		}

		private Builder createIncludedTab(final PropertyName parentPropertyName, final GridFieldVO gridFieldVO)
		{
			final int includedTabId = gridFieldVO.getIncluded_Tab_ID();
			if (!tabIdsAdded.add(includedTabId))
			{
				logger.debug("Skip adding included tab for {} because that tab was already considered", gridFieldVO);
				return null;
			}

			final GridTabVO includedGridTabVO = id2gridTabVO.get(includedTabId);
			if (includedGridTabVO == null)
			{
				logger.debug("Skip adding included tab for {} because tab was not found", gridFieldVO);
				return null;
			}

			final String includedTableName = includedGridTabVO.getTableName();
			final Builder includedTabBuilder = PropertyDescriptor.builder()
					.setPropertyName(PropertyName.of(parentPropertyName, gridFieldVO.getColumnName()))
					.setType(PropertyDescriptorType.Tabular)
					.setCaption(includedGridTabVO.getName())
					.setSqlTableName(includedTableName)
					.setSqlParentLinkColumnName(getParentLinkColumnNameOrNull(includedGridTabVO));
			tabIdsAdded.add(includedGridTabVO.getAD_Tab_ID());

			final String parent_SqlTableName_Old = this.parent_SqlTableName;
			this.parent_SqlTableName = includedTableName;
			try
			{
				// Add fields directly to tab builder (without creating field groups)
				includedGridTabVO.getFields().forEach(fieldOfIncludedTab -> createAndAddField(includedTabBuilder, fieldOfIncludedTab));
			}
			finally
			{
				this.parent_SqlTableName = parent_SqlTableName_Old;
			}

			return includedTabBuilder;
		}

		private String getParentLinkColumnNameOrNull(final GridTabVO gridTabVO)
		{
			final int parentColumnId = gridTabVO.getParent_Column_ID();
			if (parentColumnId > 0)
			{
				final I_AD_Column parentColumn = InterfaceWrapperHelper.create(ctx, parentColumnId, I_AD_Column.class, ITrx.TRXNAME_ThreadInherited);
				if (parentColumn == null)
				{
					return null;
				}
				return parentColumn.getColumnName();
			}
			else if (parent_SqlTableName != null)
			{
				// FIXME: hardcoded
				return parent_SqlTableName + "_ID";
			}
			else
			{
				return null;
			}
		}

		private static String getFieldGroupName(final GridTabVO gridTabVO, final GridFieldVO gridFieldVO)
		{
			final FieldGroupVO fieldGroup = gridFieldVO.getFieldGroup();
			if (fieldGroup != null && !Check.isEmpty(fieldGroup.getFieldGroupName(), true))
			{
				return fieldGroup.getFieldGroupName();
			}
			else
			{
				return gridTabVO.getName();
			}
		}

		private void createAndAddField(final PropertyDescriptor.Builder parentBuilder, final GridFieldVO gridFieldVO)
		{
			final Builder fieldBuilder = PropertyDescriptor.builder()
					.setType(PropertyDescriptorType.Value)
					.setPropertyName(PropertyName.of(parentBuilder.getPropertyName(), gridFieldVO.getColumnName()))
					.setValueType(getValueType(gridFieldVO))
					.setCaption(gridFieldVO.getHeader())
					.setLayoutInfo(createLayoutInfo(gridFieldVO))

			// shall be factored out of the descriptor
					.setSqlColumnName(gridFieldVO.getColumnName())
					.setSqlColumnSql(gridFieldVO.getColumnSQL(false))
					.setSqlDisplayType(gridFieldVO.getDisplayType())
					.setSQL_AD_Reference_Value_ID(gridFieldVO.getAD_Reference_Value_ID());

			final PropertyDescriptor fieldDescriptor = fieldBuilder.build();
			parentBuilder.addChildPropertyDescriptor(fieldDescriptor);
			fieldIdsAdded.add(gridFieldVO.getAD_Field_ID());

			logger.debug("Added {} to {} ({})", fieldDescriptor, parentBuilder, gridFieldVO);
		}
		
		private PropertyLayoutInfo createLayoutInfo(final GridFieldVO gridFieldVO)
		{
			return PropertyLayoutInfo.builder()
					.setDisplayed(gridFieldVO.isDisplayed())
					.build();
		}

		private static Class<?> getValueType(final GridFieldVO field)
		{
			final int displayType = field.getDisplayType();
			final Class<?> valueType;
			if (DisplayType.isAnyLookup(displayType))
			{
				valueType = LookupValue.class;
			}
			else if (DisplayType.isID(displayType))
			{
				valueType = Integer.class;
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
				valueType = java.math.BigDecimal.class;
			}
			else if (DisplayType.YesNo == displayType)
			{
				valueType = java.lang.Boolean.class;
			}
			else
			{
				valueType = String.class;
			}
			return valueType;
		}

	}
}
