package de.metas.ui.web.window.descriptor.legacy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
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
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.IPropertyDescriptorProvider;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.PropertyDescriptor.Builder;
import de.metas.ui.web.window.descriptor.PropertyDescriptors;
import de.metas.ui.web.window.descriptor.SqlDataBindingInfo;
import de.metas.ui.web.window.descriptor.SqlLookupDescriptor;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorType;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorValueType;
import de.metas.ui.web.window.shared.descriptor.PropertyLayoutInfo;

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

/**
 * A {@link IPropertyDescriptorProvider} implementation that generates a descriptor from the application dictionary,
 * meaning {@link GridWindowVO}, {@link GridTabVO}, {@link GridFieldVO} etc.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class VOPropertyDescriptorProvider implements IPropertyDescriptorProvider
{
	/** Logic expression which evaluates as <code>true</code> when IsActive flag exists but it's <code>false</code> */
	private static final ILogicExpression LOGICEXPRESSION_NotActive;
	/** Logic expression which evaluates as <code>true</code> when Processed flag exists and it's <code>true</code> */
	private static final ILogicExpression LOGICEXPRESSION_Processed;

	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		LOGICEXPRESSION_NotActive = expressionFactory.compile("@" + WindowConstants.PROPERTYNAME_IsActive + "/Y@=N", ILogicExpression.class);
		LOGICEXPRESSION_Processed = expressionFactory.compile("@" + WindowConstants.PROPERTYNAME_Processed + "/N@=Y", ILogicExpression.class);
	}

	@Override
	public PropertyDescriptor provideEntryWindowdescriptor()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PropertyDescriptor provideForWindow(final int AD_Window_ID)
	{
		// TODO: caching

		final Properties ctx = Env.getCtx(); // TODO
		final int windowNo = 0; // TODO
		final GridWindowVO gridWindowVO = GridWindowVO.create(ctx, windowNo, AD_Window_ID);

		final PropertyDescriptor root = new RootPropertyDescriptorBuilder()
				.add(gridWindowVO)
				.build();

		return root;
	}

	private static final class RootPropertyDescriptorBuilder
	{
		private static final Logger logger = LogManager.getLogger(RootPropertyDescriptorBuilder.class);
		private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

		private Properties ctx;
		private PropertyDescriptor.Builder rootBuilder;
		private List<GridTabVO> gridTabVOs;
		private Map<Object, GridTabVO> id2gridTabVO;
		private final Set<Integer> tabIdsAdded = new HashSet<>();
		private final Set<Integer> columnIdsAdded = new HashSet<>();

		private String parent_SqlTableName = null;

		private RootPropertyDescriptorBuilder()
		{
			super();
		}

		public PropertyDescriptor build()
		{
			final PropertyDescriptor rootDescriptor = rootBuilder.build();

			if (logger.isTraceEnabled())
			{
				System.out.println("--------------------------------------------------------------------------------");
				System.out.println("Build root descriptor " + PropertyDescriptors.toStringRecursivelly(rootDescriptor));
				System.out.println("--------------------------------------------------------------------------------");
			}

			return rootDescriptor;
		}

		public RootPropertyDescriptorBuilder add(final GridWindowVO gridWindowVO)
		{
			if (rootBuilder != null)
			{
				throw new IllegalArgumentException("Cannot add more then one window");
			}

			logger.debug("Adding {}", gridWindowVO);

			ctx = gridWindowVO.getCtx();
			gridTabVOs = gridWindowVO.getTabs();
			id2gridTabVO = Maps.uniqueIndex(gridTabVOs, x -> x.getAD_Tab_ID());

			final GridTabVO rootTab = gridTabVOs.get(0);
			final String rootTableName = rootTab.getTableName();
			rootBuilder = PropertyDescriptor.builder()
					.setPropertyName(WindowConstants.PROPERTYNAME_WindowRoot)
					.setCaption(gridWindowVO.getName())
					.setDataBindingInfo(SqlDataBindingInfo.builder()
							.setSqlTableName(rootTableName)
							.build());

			parent_SqlTableName = rootTableName;
			try
			{
				createAndAddTab(rootBuilder, rootTab);
				rootBuilder.setPrintProcessId(rootTab.getPrint_Process_ID());
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

			gridTabVO.getFields()
					.stream()
					.sorted(GridFieldVO.COMPARATOR_BySeqNo)
					.filter(gridFieldVO -> columnIdsAdded.add(gridFieldVO.getAD_Column_ID()))
					.forEach(gridFieldVO -> {
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

			//
			// Child tabs
			// FIXME: commented out because atm is breaking the "Save" BL
			//@formatter:off
//			for (final GridTabVO childTab : getChildTabs(gridTabVO))
//			{
//				if (!tabIdsAdded.add(childTab.getAD_Tab_ID()))
//				{
//					logger.debug("Skip adding {} because it was already considered", childTab);
//					continue;
//				}
//
//				final PropertyName includedTabPropertyName = PropertyName.of("IncludedTab_" + childTab.getAD_Tab_ID());
//				final PropertyDescriptor.Builder builder = createIncludedTab(includedTabPropertyName, childTab);
//				groupBuilders.put(includedTabPropertyName.toString(), builder);
//			}
			//@formatter:on

			//
			//
			for (final PropertyDescriptor.Builder groupBuilder : groupBuilders.values())
			{
				final PropertyDescriptor group = groupBuilder.build();
				parentBuilder.addChildPropertyDescriptor(group);

				logger.debug("Added {} to {}", group, parentBuilder);
			}
		}

		private List<GridTabVO> getChildTabs(final GridTabVO parentTab)
		{
			boolean foundParentTab = false;
			final List<GridTabVO> childTabs = new ArrayList<>();
			for (final GridTabVO tab : gridTabVOs)
			{
				if (!foundParentTab)
				{
					foundParentTab = tab == parentTab;
				}
				else if (tab.getTabLevel() == parentTab.getTabLevel() + 1)
				{
					childTabs.add(tab);
				}
				else
				{
					break;
				}
			}

			return childTabs;
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

			final PropertyName includedTabPropertyName = PropertyName.of(parentPropertyName, gridFieldVO.getColumnName());
			return createIncludedTab(includedTabPropertyName, includedGridTabVO);
		}

		private Builder createIncludedTab(final PropertyName includedTabPropertyName, final GridTabVO includedGridTabVO)
		{

			final String includedTableName = includedGridTabVO.getTableName();
			final Builder includedTabBuilder = PropertyDescriptor.builder()
					.setPropertyName(includedTabPropertyName)
					.setType(PropertyDescriptorType.Tabular)
					.setCaption(includedGridTabVO.getName())
					.setDataBindingInfo(SqlDataBindingInfo.builder()
							.setSqlTableName(includedTableName)
							.setSqlParentLinkColumnName(getParentLinkColumnNameOrNull(includedGridTabVO))
							.build());
			tabIdsAdded.add(includedGridTabVO.getAD_Tab_ID());

			final String parent_SqlTableName_Old = parent_SqlTableName;
			parent_SqlTableName = includedTableName;
			try
			{
				// Add fields directly to tab builder (without creating field groups)
				includedGridTabVO.getFields()
						.stream()
						.sorted(GridFieldVO.COMPARATOR_BySeqNoGrid)
						.forEach(fieldOfIncludedTab -> createAndAddField(includedTabBuilder, fieldOfIncludedTab));
			}
			finally
			{
				parent_SqlTableName = parent_SqlTableName_Old;
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
			final FieldGroupVO fieldGroup = gridFieldVO == null ? null : gridFieldVO.getFieldGroup();
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
			final PropertyName propertyName = PropertyName.of(parentBuilder.getPropertyName(), gridFieldVO.getColumnName());
			final PropertyDescriptorValueType valueType = extractValueType(gridFieldVO);
			final int adReferenceValueId = extractAD_Reference_Value_ID(gridFieldVO);
			
			final Builder fieldBuilder = PropertyDescriptor.builder()
					.setType(PropertyDescriptorType.Value)
					.setPropertyName(propertyName)
					.setValueType(valueType)
					.setCaption(gridFieldVO.getHeader())
					.setLayoutInfo(createLayoutInfo(gridFieldVO))
					//
					.setDefaultValueExpression(extractDefaultValueExpression(gridFieldVO))

			// Logic
					.setReadonlyLogic(extractReadonlyLogic(propertyName, gridFieldVO))
					.setMandatoryLogic(extractMandatoryLogic(propertyName, gridFieldVO))
					.setDisplayLogic(extractDisplayLogic(propertyName, gridFieldVO))

			// SQL related meta info
					.setDataBindingInfo(SqlDataBindingInfo.builder()
							.setSqlColumnName(gridFieldVO.getColumnName())
							.setSqlColumnSql(gridFieldVO.getColumnSQL(false))
							.setSqlLookupDescriptor(valueType.isLookup() ? SqlLookupDescriptor.of(valueType, gridFieldVO.getColumnName(), adReferenceValueId) : null)
							.build())
							//
							;

			final PropertyDescriptor fieldDescriptor = fieldBuilder.build();
			parentBuilder.addChildPropertyDescriptor(fieldDescriptor);

			logger.debug("Added {} to {} ({})", fieldDescriptor, parentBuilder, gridFieldVO);
		}

		private ILogicExpression extractReadonlyLogic(final PropertyName propertyName, final GridFieldVO gridFieldVO)
		{
			if (gridFieldVO.isReadOnly())
			{
				return ILogicExpression.TRUE;
			}

			ILogicExpression logicExpression = gridFieldVO.getReadOnlyLogic();

			//
			// Consider field readonly if the row is not active
			// .. and this property is not the IsActive flag.
			if (!WindowConstants.PROPERTYNAME_IsActive.equals(propertyName))
			{
				logicExpression = LOGICEXPRESSION_NotActive.or(logicExpression);
			}

			//
			// Consider field readonly if the row is processed
			if (!gridFieldVO.isAlwaysUpdateable())
			{
				logicExpression = LOGICEXPRESSION_Processed.or(logicExpression);
			}

			return logicExpression;
		}

		private ILogicExpression extractMandatoryLogic(final PropertyName propertyName, final GridFieldVO gridFieldVO)
		{
			if (gridFieldVO.isMandatory())
			{
				return ILogicExpression.TRUE;
			}
			return gridFieldVO.getMandatoryLogic();
		}

		private ILogicExpression extractDisplayLogic(final PropertyName propertyName, final GridFieldVO gridFieldVO)
		{
			if (!gridFieldVO.isDisplayed())
			{
				return ILogicExpression.FALSE;
			}
			return gridFieldVO.getDisplayLogic();
		}

		private IStringExpression extractDefaultValueExpression(final GridFieldVO gridFieldVO)
		{
			final String defaultValueStr = gridFieldVO.getDefaultValue();
			if (defaultValueStr == null || defaultValueStr.isEmpty())
			{
				return IStringExpression.NULL;
			}
			else if (defaultValueStr.startsWith("@SQL="))
			{
				// TODO: implement
				return IStringExpression.NULL;
			}
			else
			{
				final IStringExpression defaultValueExpression = expressionFactory.compile(defaultValueStr, IStringExpression.class);
				return defaultValueExpression;
			}
		}

		private PropertyLayoutInfo createLayoutInfo(final GridFieldVO gridFieldVO)
		{
			return PropertyLayoutInfo.builder()
					.setDisplayed(gridFieldVO.isDisplayed())
					.build();
		}

		private static PropertyDescriptorValueType extractValueType(final GridFieldVO field)
		{
			final int displayType = field.getDisplayType();

			if (displayType == DisplayType.List)
			{
				return PropertyDescriptorValueType.List;
			}
			else if (displayType == DisplayType.Location)
			{
				return PropertyDescriptorValueType.Location;
			}
			else if (displayType == DisplayType.PAttribute)
			{
				return PropertyDescriptorValueType.PAttribute;
			}
			else if (displayType == DisplayType.Assignment)
			{
				return PropertyDescriptorValueType.ResourceAssignment;
			}
			else if (displayType == DisplayType.Account)
			{
				return PropertyDescriptorValueType.Account;
			}
			else if (DisplayType.isAnyLookup(displayType))
			{
				return PropertyDescriptorValueType.SearchLookup;
			}
			else if (displayType == DisplayType.ID)
			{
				return PropertyDescriptorValueType.ID;
			}
			//
			else if (displayType == DisplayType.Date)
			{
				return PropertyDescriptorValueType.Date;
			}
			else if (displayType == DisplayType.Time)
			{
				return PropertyDescriptorValueType.Time;
			}
			else if (displayType == DisplayType.DateTime)
			{
				return PropertyDescriptorValueType.DateTime;
			}
			//
			else if (displayType == DisplayType.TextLong || displayType == DisplayType.Memo || displayType == DisplayType.Text)
			{
				return PropertyDescriptorValueType.TextLong;
			}
			else if (DisplayType.isText(displayType))
			{
				return PropertyDescriptorValueType.Text;
			}
			//
			else if (DisplayType.Integer == displayType)
			{
				return PropertyDescriptorValueType.Integer;
			}
			else if (displayType == DisplayType.Amount)
			{
				return PropertyDescriptorValueType.Amount;
			}
			else if (displayType == DisplayType.Number)
			{
				return PropertyDescriptorValueType.Number;
			}
			else if (displayType == DisplayType.CostPrice)
			{
				return PropertyDescriptorValueType.CostPrice;
			}
			else if (displayType == DisplayType.Quantity)
			{
				return PropertyDescriptorValueType.Quantity;
			}
			//
			else if (displayType == DisplayType.YesNo)
			{
				return PropertyDescriptorValueType.YesNo;
			}
			else if (displayType == DisplayType.Button)
			{
				return PropertyDescriptorValueType.Button;
			}
			else if (displayType == DisplayType.Binary)
			{
				return PropertyDescriptorValueType.Binary;
			}
			else if (displayType == DisplayType.Image)
			{
				return PropertyDescriptorValueType.Image;
			}
			//
			else
			{
				throw new IllegalArgumentException("Unknown displayType=" + displayType + " of " + field);
			}
		}
		
		private static int extractAD_Reference_Value_ID(final GridFieldVO field)
		{
			final int displayType = field.getDisplayType();
			final int adReferenceValueId = field.getAD_Reference_Value_ID();
			
			if (displayType == DisplayType.Account && adReferenceValueId <= 0)
			{
				return WindowConstants.AD_REFERENCE_ID_Account;
			}
			
			return adReferenceValueId;
		}
	}
}
