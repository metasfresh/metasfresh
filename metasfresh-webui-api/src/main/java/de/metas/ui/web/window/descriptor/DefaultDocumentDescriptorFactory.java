package de.metas.ui.web.window.descriptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CCache;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.sql.SqlDefaultValueExpression;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;

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

@Service
public class DefaultDocumentDescriptorFactory implements DocumentDescriptorFactory
{
	private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	private static final int MAIN_TabNo = 0;
	private static final String COLUMNNAME_IsActive = "IsActive";
	private static final String COLUMNNAME_Processed = "Processed";
	private static final String COLUMNNAME_Processing = "Processing";
	/** Column names where we shall use {@link DocumentFieldWidgetType#Switch} instead of {@link DocumentFieldWidgetType#YesNo} */
	private static final Set<String> COLUMNNAMES_Switch = ImmutableSet.of(COLUMNNAME_IsActive); // FIXME: hardcoded

	public static final Set<String> COLUMNNAMES_CreatedUpdated = ImmutableSet.of("Created", "CreatedBy", "Updated", "UpdatedBy");

	/** Logic expression which evaluates as <code>true</code> when IsActive flag exists but it's <code>false</code> */
	private static final ILogicExpression LOGICEXPRESSION_NotActive;
	/** Logic expression which evaluates as <code>true</code> when Processed flag exists and it's <code>true</code> */
	private static final ILogicExpression LOGICEXPRESSION_Processed;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_Yes;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_No;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_Zero;

	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		LOGICEXPRESSION_NotActive = expressionFactory.compile("@" + COLUMNNAME_IsActive + "/Y@=N", ILogicExpression.class);
		LOGICEXPRESSION_Processed = expressionFactory.compile("@" + COLUMNNAME_Processed + "/N@=Y | @" + COLUMNNAME_Processing + "/N@=Y", ILogicExpression.class);

		DEFAULT_VALUE_EXPRESSION_Yes = expressionFactory.compile(DisplayType.toBooleanString(true), IStringExpression.class);
		DEFAULT_VALUE_EXPRESSION_No = expressionFactory.compile(DisplayType.toBooleanString(false), IStringExpression.class);
		DEFAULT_VALUE_EXPRESSION_Zero = expressionFactory.compile("0", IStringExpression.class);
	}

	private final CCache<Integer, DocumentDescriptor> documentDescriptorsByWindowId = new CCache<>(I_AD_Window.Table_Name + "#DocumentDescriptor", 50);

	/* package */ DefaultDocumentDescriptorFactory()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see de.metas.ui.web.window.descriptor.DocumentDescriptorFactory#getDocumentDescriptor(int)
	 */
	@Override
	public DocumentDescriptor getDocumentDescriptor(final int AD_Window_ID)
	{
		return documentDescriptorsByWindowId.getOrLoad(AD_Window_ID, () -> createDocumentDescriptor(AD_Window_ID));
	}

	private DocumentDescriptor createDocumentDescriptor(final int AD_Window_ID)
	{
		if (AD_Window_ID <= 0)
		{
			throw new AdempiereException("No window found for AD_Window_ID=" + AD_Window_ID);
		}

		final Properties ctx = Env.getCtx(); // TODO
		final int windowNo = 0; // TODO
		final GridWindowVO gridWindowVO = GridWindowVO.create(ctx, windowNo, AD_Window_ID);
		Check.assumeNotNull(gridWindowVO, "Parameter gridWindowVO is not null"); // shall never happen

		final DocumentDescriptor.Builder documentBuilder = DocumentDescriptor.builder();
		final DocumentLayoutDescriptor.Builder layoutBuilder = DocumentLayoutDescriptor.builder()
				.setAD_Window_ID(gridWindowVO.getAD_Window_ID());

		//
		// Layout: Create UI sections from main tab
		final int mainTabNo = MAIN_TabNo; // first tab
		final GridTabVO mainTabVO = gridWindowVO.getTab(mainTabNo);
		final DocumentEntityDescriptor.Builder mainEntityBuilder = DocumentEntityDescriptor.builder()
				.setId(mainTabVO.getAD_Tab_ID())
				.setAD_Window_ID(AD_Window_ID) // legacy
				.setTabNo(mainTabNo) // legacy
				.setIsSOTrx(gridWindowVO.isSOTrx()) // legacy
				;
		{
			final SpecialFieldsCollector specialFieldsCollector = new SpecialFieldsCollector();
			layoutBuilder.addSections(createSections(mainTabVO, specialFieldsCollector));

			// Set special field names
			layoutBuilder
					.setDocNoField(specialFieldsCollector.getDocNoField())
					.setDocStatusField(specialFieldsCollector.getDocStatusField())
					.setDocActionField(specialFieldsCollector.getDocActionField());

			final SqlDocumentEntityDataBindingDescriptor.Builder mainEntityBindingsBuilder = SqlDocumentEntityDataBindingDescriptor.builder()
					.setSqlTableName(mainTabVO.getTableName())
					.setSqlTableAliasAsMaster()
					.setAD_Table_ID(mainTabVO.getAD_Table_ID()) // legacy
					.setSqlParentLinkColumnName(null) // no parent link on main tab
					.setSqlWhereClause(mainTabVO.getWhereClause())
					.setSqlOrderBy(mainTabVO.getOrderByClause());

			//
			// Fields mapping & data binding
			mainTabVO.getFields()
					.stream()
					.sorted(GridFieldVO.COMPARATOR_BySeqNo)
					.map(gridFieldVO -> documentFieldDescriptorBuilder(mainEntityBindingsBuilder, gridFieldVO).build())
					.forEach(fieldDescriptor -> {
						mainEntityBuilder.addField(fieldDescriptor);
						mainEntityBindingsBuilder.addField(fieldDescriptor.getDataBinding());
					});

			mainEntityBuilder.setDataBinding(mainEntityBindingsBuilder.build());
		}

		//
		// Layout: Create UI details from child tabs
		for (final GridTabVO detailTabVO : gridWindowVO.getChildTabs(mainTabNo))
		{
			final DocumentLayoutDetailDescriptor detail = createDetail(detailTabVO);
			if (detail.getElements().isEmpty())
			{
				continue;
			}
			layoutBuilder.addDetail(detail);

			final SqlDocumentEntityDataBindingDescriptor.Builder detailEntityBindingsBuilder = SqlDocumentEntityDataBindingDescriptor.builder()
					.setSqlTableName(detailTabVO.getTableName())
					.setDetailIdAndUpdateTableAlias(detail.getDetailId())
					.setAD_Table_ID(detailTabVO.getAD_Table_ID()) // legacy
					.setSqlParentLinkColumnName(extractParentLinkColumnName(mainTabVO, detailTabVO))
					.setSqlWhereClause(detailTabVO.getWhereClause())
					.setSqlOrderBy(detailTabVO.getOrderByClause());

			final DocumentEntityDescriptor.Builder detailEntityBuilder = DocumentEntityDescriptor.builder()
					.setId(detailTabVO.getAD_Tab_ID())
					.setDetailId(detail.getDetailId())
					.setAD_Window_ID(AD_Window_ID) // legacy
					.setTabNo(detailTabVO.getTabNo()) // legacy
					.setIsSOTrx(gridWindowVO.isSOTrx()) // legacy
					;

			//
			// Fields mapping
			detailTabVO.getFields()
					.stream()
					.sorted(GridFieldVO.COMPARATOR_BySeqNoGrid)
					.map(gridFieldVO -> documentFieldDescriptorBuilder(detailEntityBindingsBuilder, gridFieldVO).build())
					.forEach(fieldDescriptor -> {
						detailEntityBuilder.addField(fieldDescriptor);
						detailEntityBindingsBuilder.addField(fieldDescriptor.getDataBinding());
					});

			detailEntityBuilder.setDataBinding(detailEntityBindingsBuilder.build());
			mainEntityBuilder.addIncludedEntity(detailEntityBuilder.build());
		}

		//
		//
		return documentBuilder
				.setLayout(layoutBuilder.build())
				.setEntityDescriptor(mainEntityBuilder.build())
				.build();
	}

	private List<DocumentLayoutSectionDescriptor> createSections(final GridTabVO gridTabVO, final SpecialFieldsCollector specialFieldsCollector)
	{
		final List<DocumentLayoutSectionDescriptor> uiSections = new ArrayList<>();

		//
		// UI Sections
		final Properties ctx = Env.getCtx();
		final int AD_Tab_ID = gridTabVO.getAD_Tab_ID();
		for (final I_AD_UI_Section uiSection : windowDAO.retrieveUISections(ctx, AD_Tab_ID))
		{
			if (!uiSection.isActive())
			{
				continue;
			}

			final DocumentLayoutSectionDescriptor.Builder layoutSectionBuilder = DocumentLayoutSectionDescriptor.builder();

			//
			// UI Columns
			for (final I_AD_UI_Column uiColumn : windowDAO.retrieveUIColumns(uiSection))
			{
				if (!uiColumn.isActive())
				{
					continue;
				}

				final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = DocumentLayoutColumnDescriptor.builder();

				//
				// UI Element Groups
				for (final I_AD_UI_ElementGroup uiElementGroup : windowDAO.retrieveUIElementGroups(uiColumn))
				{
					if (!uiElementGroup.isActive())
					{
						continue;
					}

					final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = DocumentLayoutElementGroupDescriptor.builder()
							.setLayoutType(uiElementGroup.getUIStyle());

					//
					// UI Elements
					boolean isFirstElementInGroup = true;
					for (final I_AD_UI_Element uiElement : windowDAO.retrieveUIElements(uiElementGroup))
					{
						if (!uiElement.isActive())
						{
							continue;
						}
						
						// TODO: atm if we setting first element in group as primary, others as secondary.
						final LayoutType layoutType = layoutElementGroupBuilder.getLayoutType() == LayoutType.primary && isFirstElementInGroup ? LayoutType.primary : LayoutType.secondary;

						//
						// UI main field
						final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = DocumentLayoutElementDescriptor.builder()
								.setCaption(uiElement.getName())
								.setDescription(uiElement.getDescription())
								.setLayoutType(layoutType) 
								;

						{
							final GridFieldVO gridFieldVO = gridTabVO.getFieldByAD_Field_ID(uiElement.getAD_Field_ID());
							if (gridFieldVO != null)
							{
								final String columnName = gridFieldVO.getColumnName();
								layoutElementBuilder
										.setWidgetType(extractWidgetType(gridFieldVO))
										.addField(DocumentLayoutElementFieldDescriptor.builder()
												.setField(columnName)
												.setLookupSource(extractLookupSource(gridFieldVO))
												.build());

								specialFieldsCollector.updateFromColumnName(columnName);
							}
						}

						//
						// UI Element Fields
						for (final I_AD_UI_ElementField uiElementField : windowDAO.retrieveUIElementFields(uiElement))
						{
							if (!uiElementField.isActive())
							{
								continue;
							}

							final GridFieldVO gridFieldVO = gridTabVO.getFieldByAD_Field_ID(uiElementField.getAD_Field_ID());
							if (gridFieldVO != null)
							{
								final String columnName = gridFieldVO.getColumnName();
								layoutElementBuilder.addField(DocumentLayoutElementFieldDescriptor.builder()
										.setField(columnName)
										.build());

								specialFieldsCollector.updateFromColumnName(columnName);
							}
						}
						
						final DocumentLayoutElementLineDescriptor layoutElementLine = DocumentLayoutElementLineDescriptor.builder()
								.addElement(layoutElementBuilder.build())
								.build();

						layoutElementGroupBuilder.addElementLine(layoutElementLine);
						
						//
						isFirstElementInGroup = false;
					} // each uiElement

					layoutColumnBuilder.addElementGroupIfNotEmpty(layoutElementGroupBuilder.build());
				} // each uiElementGroup

				layoutSectionBuilder.addColumnIfNotEmpty(layoutColumnBuilder.build());
			} // each uiColumn

			uiSections.add(layoutSectionBuilder.build());
		} // each uiSection

		return uiSections;
	}

	private static DocumentLayoutDetailDescriptor createDetail(final GridTabVO tab)
	{
		final DocumentLayoutDetailDescriptor.Builder elementGroupBuilder = DocumentLayoutDetailDescriptor.builder()
				.setDetailId(extractDetailId(tab))
				.setCaption(tab.getName())
				.setDescription(tab.getDescription());

		for (final GridFieldVO gridFieldVO : tab.getFields())
		{
			if (!gridFieldVO.isDisplayedGrid())
			{
				continue;
			}

			final String columnName = gridFieldVO.getColumnName();
			final DocumentLayoutElementDescriptor element = DocumentLayoutElementDescriptor.builder()
					.setCaption(gridFieldVO.getHeader())
					.setDescription(gridFieldVO.getDescription())
					.setWidgetType(extractWidgetType(gridFieldVO))
					.setLayoutTypeNone() // does not matter for detail
					.addField(DocumentLayoutElementFieldDescriptor.builder()
							.setField(columnName)
							.setLookupSource(extractLookupSource(gridFieldVO))
							.build())
					.build();

			elementGroupBuilder.addElement(element);
		}

		return elementGroupBuilder.build();
	}

	private DocumentFieldDescriptor.Builder documentFieldDescriptorBuilder(final SqlDocumentEntityDataBindingDescriptor.Builder detailEntityBindingsBuilder, final GridFieldVO gridFieldVO)
	{
		final String sqlTableName = detailEntityBindingsBuilder.getSqlTableName();
		final String sqlTableAlias = detailEntityBindingsBuilder.getSqlTableAlias();
		final String detailId = detailEntityBindingsBuilder.getDetailId();
		//
		final String sqlColumnName = gridFieldVO.getColumnName();
		final boolean keyColumn = gridFieldVO.isKey();
		final boolean parentLinkColumn = sqlColumnName.equals(detailEntityBindingsBuilder.getSqlParentLinkColumnName());
		
		//
		//
		final int displayType;
		final int AD_Reference_Value_ID;
		final int AD_Val_Rule_ID;
		final DocumentFieldWidgetType widgetType;
		final Class<?> valueClass;
		final IStringExpression defaultValueExpression;
		final ILogicExpression readonlyLogic;
		final boolean alwaysUpdateable;
		final ILogicExpression mandatoryLogic;
		final ILogicExpression displayLogic;
		if(parentLinkColumn)
		{
			displayType = DisplayType.ID;
			AD_Reference_Value_ID = 0; // none
			AD_Val_Rule_ID = 0;
			widgetType = DocumentFieldWidgetType.Integer;
			valueClass = Integer.class;
			defaultValueExpression = IStringExpression.NULL;
			readonlyLogic = ILogicExpression.TRUE;
			alwaysUpdateable = false;
			mandatoryLogic = ILogicExpression.TRUE;
			displayLogic = ILogicExpression.FALSE;
		}
		else
		{
			displayType = gridFieldVO.getDisplayType();
			AD_Reference_Value_ID = gridFieldVO.getAD_Reference_Value_ID();
			AD_Val_Rule_ID = gridFieldVO.getAD_Val_Rule_ID();
			widgetType = extractWidgetType(gridFieldVO);
			valueClass = extractValueClass(gridFieldVO);
			defaultValueExpression = extractDefaultValueExpression(gridFieldVO);
			readonlyLogic = extractReadonlyLogic(gridFieldVO);
			alwaysUpdateable = extractAlwaysUpdateable(gridFieldVO);
			mandatoryLogic = extractMandatoryLogic(gridFieldVO);
			displayLogic = extractDisplayLogic(gridFieldVO);
		}

		//
		// ORDER BY SortNo
		int orderBySortNo = gridFieldVO.getSortNo();
		if (orderBySortNo == 0 && keyColumn)
		{
			orderBySortNo = Integer.MAX_VALUE;
		}

		final SqlDocumentFieldDataBindingDescriptor dataBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setSqlTableName(sqlTableName)
				.setSqlTableAlias(sqlTableAlias)
				.setSqlColumnName(sqlColumnName)
				.setSqlColumnSql(gridFieldVO.getColumnSQL(false))
				.setAD_Column_ID(gridFieldVO.getAD_Column_ID()) // legacy
				.setDisplayType(displayType)
				.setAD_Reference_Value_ID(AD_Reference_Value_ID)
				.setAD_Val_Rule_ID(AD_Val_Rule_ID)
				.setKeyColumn(keyColumn)
				.setParentLinkColumn(parentLinkColumn)
				.setEncrypted(gridFieldVO.isEncryptedColumn())
				.setOrderBy(orderBySortNo)
				.build();

		return DocumentFieldDescriptor.builder()
				.setFieldName(sqlColumnName)
				.setDetailId(detailId)
				//
				.setCaption(gridFieldVO.getHeader())
				.setDescription(gridFieldVO.getDescription())
				//
				.setKey(keyColumn)
				.setParentLink(parentLinkColumn)
				//
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setVirtualField(gridFieldVO.isVirtualColumn())
				.setCalculated(gridFieldVO.isCalculated())
				//
				.setDefaultValueExpression(defaultValueExpression)
				//
				.setReadonlyLogic(readonlyLogic)
				.setAlwaysUpdateable(alwaysUpdateable)
				.setMandatoryLogic(mandatoryLogic)
				.setDisplayLogic(displayLogic)
				//
				.setDataBinding(dataBinding);
	}

	private static String extractDetailId(final GridTabVO gridTabVO)
	{
		final int tabNo = gridTabVO.getTabNo();
		if (tabNo == MAIN_TabNo)
		{
			return null;
		}
		return String.valueOf(tabNo);
	}

	private static String extractParentLinkColumnName(final GridTabVO parentTabVO, final GridTabVO tabVO)
	{
		if (parentTabVO == null)
		{
			return null;
		}

		//
		// Get configured parent link if any
		{
			final GridFieldVO parentLinkField = tabVO.getParentLinkField();
			if (parentLinkField != null)
			{
				return parentLinkField.getColumnName();
			}
		}

		//
		// Try linking by parent's key field
		{
			final GridFieldVO parentKeyField = parentTabVO.getKeyField();
			if (parentKeyField != null)
			{
				final GridFieldVO parentLinkField = tabVO.getFieldByColumnName(parentKeyField.getColumnName());
				if (parentLinkField != null)
				{
					return parentLinkField.getColumnName();
				}
			}
		}

		return null;
	}

	private static DocumentFieldWidgetType extractWidgetType(final GridFieldVO gridFieldVO)
	{
		final int displayType = gridFieldVO.getDisplayType();

		//
		//
		if (displayType == DisplayType.List)
		{
			return DocumentFieldWidgetType.List;
		}
		else if (displayType == DisplayType.Location)
		{
			return DocumentFieldWidgetType.Address;
		}
		else if (displayType == DisplayType.PAttribute)
		{
			return DocumentFieldWidgetType.ProductAttributes;
		}
		else if (displayType == DisplayType.Table)
		{
			return DocumentFieldWidgetType.List;
		}
		else if (displayType == DisplayType.TableDir)
		{
			return DocumentFieldWidgetType.List;
		}
		else if (displayType == DisplayType.Search)
		{
			return DocumentFieldWidgetType.Lookup;
		}
		else if (DisplayType.isAnyLookup(displayType))
		{
			return DocumentFieldWidgetType.Lookup;
		}
		else if (displayType == DisplayType.ID)
		{
			return DocumentFieldWidgetType.Integer;
		}
		//
		//
		else if (displayType == DisplayType.Date)
		{
			return DocumentFieldWidgetType.Date;
		}
		else if (displayType == DisplayType.Time)
		{
			return DocumentFieldWidgetType.Time;
		}
		else if (displayType == DisplayType.DateTime)
		{
			return DocumentFieldWidgetType.DateTime;
		}
		//
		//
		else if (displayType == DisplayType.TextLong || displayType == DisplayType.Memo || displayType == DisplayType.Text)
		{
			return DocumentFieldWidgetType.LongText;
		}
		else if (DisplayType.isText(displayType))
		{
			return DocumentFieldWidgetType.Text;
		}
		//
		//
		else if (DisplayType.Integer == displayType)
		{
			return DocumentFieldWidgetType.Integer;
		}
		else if (displayType == DisplayType.Amount)
		{
			return DocumentFieldWidgetType.Amount;
		}
		else if (displayType == DisplayType.Number)
		{
			return DocumentFieldWidgetType.Number;
		}
		else if (displayType == DisplayType.CostPrice)
		{
			return DocumentFieldWidgetType.CostPrice;
		}
		else if (displayType == DisplayType.Quantity)
		{
			return DocumentFieldWidgetType.Quantity;
		}
		//
		//
		else if (displayType == DisplayType.YesNo)
		{
			final String columnName = gridFieldVO.getColumnName();
			if (COLUMNNAMES_Switch.contains(columnName))
			{
				return DocumentFieldWidgetType.Switch;
			}
			return DocumentFieldWidgetType.YesNo;
		}
		else if (displayType == DisplayType.Button)
		{
			return DocumentFieldWidgetType.Button;
		}
		else if (displayType == DisplayType.Image)
		{
			return DocumentFieldWidgetType.Image;
		}
		//
		//
		else
		{
			throw new IllegalArgumentException("Unknown displayType=" + displayType + " of " + gridFieldVO);
		}
	}

	private static Class<?> extractValueClass(final GridFieldVO gridFieldVO)
	{
		final int displayType = gridFieldVO.getDisplayType();

		//
		//
		if (displayType == DisplayType.List)
		{
			return StringLookupValue.class;
		}
		else if (displayType == DisplayType.Location)
		{
			return IntegerLookupValue.class;
		}
		else if (displayType == DisplayType.PAttribute)
		{
			return IntegerLookupValue.class;
		}
		else if (displayType == DisplayType.Table)
		{
			final MLookupInfo lookupInfo = gridFieldVO.getLookupInfo();
			final boolean numericKey = lookupInfo == null || lookupInfo.isNumericKey();
			return numericKey ? IntegerLookupValue.class : StringLookupValue.class;
		}
		else if (DisplayType.isAnyLookup(displayType))
		{
			return IntegerLookupValue.class;
		}
		else if (displayType == DisplayType.ID)
		{
			return Integer.class;
		}
		//
		//
		else if (displayType == DisplayType.Date)
		{
			return java.util.Date.class;
		}
		else if (displayType == DisplayType.Time)
		{
			return java.util.Date.class;
		}
		else if (displayType == DisplayType.DateTime)
		{
			return java.util.Date.class;
		}
		//
		//
		else if (displayType == DisplayType.TextLong || displayType == DisplayType.Memo || displayType == DisplayType.Text)
		{
			return String.class;
		}
		else if (DisplayType.isText(displayType))
		{
			return String.class;
		}
		//
		//
		else if (DisplayType.Integer == displayType)
		{
			return Integer.class;
		}
		else if (displayType == DisplayType.Amount)
		{
			return BigDecimal.class;
		}
		else if (displayType == DisplayType.Number)
		{
			return BigDecimal.class;
		}
		else if (displayType == DisplayType.CostPrice)
		{
			return BigDecimal.class;
		}
		else if (displayType == DisplayType.Quantity)
		{
			return BigDecimal.class;
		}
		//
		//
		else if (displayType == DisplayType.YesNo)
		{
			return Boolean.class;
		}
		else if (displayType == DisplayType.Button)
		{
			return String.class;
		}
		else if (displayType == DisplayType.Image)
		{
			return Integer.class;
		}
		//
		//
		else
		{
			throw new IllegalArgumentException("Unknown displayType=" + displayType + " of " + gridFieldVO);
		}
	}

	private static DocumentLayoutElementFieldDescriptor.LookupSource extractLookupSource(final GridFieldVO gridFieldVO)
	{
		final int displayType = gridFieldVO.getDisplayType();
		if (DisplayType.Search == displayType)
		{
			return DocumentLayoutElementFieldDescriptor.LookupSource.lookup;
		}
		else if (DisplayType.List == displayType)
		{
			return DocumentLayoutElementFieldDescriptor.LookupSource.list;
		}
		else if (DisplayType.TableDir == displayType)
		{
			return DocumentLayoutElementFieldDescriptor.LookupSource.list;
		}
		else if (DisplayType.Table == displayType)
		{
			return DocumentLayoutElementFieldDescriptor.LookupSource.list;
		}
		else if (DisplayType.isAnyLookup(displayType))
		{
			return DocumentLayoutElementFieldDescriptor.LookupSource.lookup;
		}
		else
		{
			return null;
		}
	}

	private static ILogicExpression extractReadonlyLogic(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isReadOnly())
		{
			return ILogicExpression.TRUE;
		}

		if (gridFieldVO.isVirtualColumn())
		{
			return ILogicExpression.TRUE;
		}

		if (gridFieldVO.isKey())
		{
			return ILogicExpression.TRUE;
		}

		final String columnName = gridFieldVO.getColumnName();
		if (COLUMNNAMES_CreatedUpdated.contains(columnName))
		{
			// NOTE: from UI perspective those are readonly (i.e. it will be managed by persistence layer)
			return ILogicExpression.TRUE;
		}

		ILogicExpression logicExpression = gridFieldVO.getReadOnlyLogic();

		//
		// Consider field readonly if the row is not active
		// .. and this property is not the IsActive flag.
		if (!COLUMNNAME_IsActive.equals(columnName))
		{
			logicExpression = LOGICEXPRESSION_NotActive.or(logicExpression);
		}

		//
		// Consider field readonly if the row is processed.
		// In case we deal with an AlwaysUpdateable field, this logic do not apply.
		final boolean alwaysUpdateable = extractAlwaysUpdateable(gridFieldVO);
		if (!alwaysUpdateable)
		{
			logicExpression = LOGICEXPRESSION_Processed.or(logicExpression);
		}

		return logicExpression;
	}

	private static boolean extractAlwaysUpdateable(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isVirtualColumn() || !gridFieldVO.isUpdateable())
			return false;
		return gridFieldVO.isAlwaysUpdateable();
	}

	private static ILogicExpression extractMandatoryLogic(final GridFieldVO gridFieldVO)
	{
		final String columnName = gridFieldVO.getColumnName();
		if (COLUMNNAMES_CreatedUpdated.contains(columnName))
		{
			// NOTE: from UI perspective those are not mandatory (i.e. it will be managed by persistence layer)
			return ILogicExpression.FALSE;
		}

		if (gridFieldVO.isVirtualColumn())
		{
			return ILogicExpression.FALSE;
		}

		if (gridFieldVO.isMandatory())
		{
			return ILogicExpression.TRUE;
		}
		return gridFieldVO.getMandatoryLogic();
	}

	private static ILogicExpression extractDisplayLogic(final GridFieldVO gridFieldVO)
	{
		return gridFieldVO.getDisplayLogic();
	}

	private IStringExpression extractDefaultValueExpression(final GridFieldVO gridFieldVO)
	{
		final String defaultValueStr = gridFieldVO.getDefaultValue();
		if (defaultValueStr == null || defaultValueStr.isEmpty())
		{
			final int displayType = gridFieldVO.getDisplayType();
			if (displayType == DisplayType.YesNo)
			{
				if (COLUMNNAME_IsActive.equals(gridFieldVO.getColumnName()))
				{
					return DEFAULT_VALUE_EXPRESSION_Yes;
				}
				else
				{
					return DEFAULT_VALUE_EXPRESSION_No;
				}
			}
			else if (DisplayType.isNumeric(displayType))
			{
				if (gridFieldVO.isMandatory())
				{
					// e.g. C_OrderLine.QtyReserved
					return DEFAULT_VALUE_EXPRESSION_Zero;
				}
			}

			return IStringExpression.NULL;
		}
		else if (defaultValueStr.startsWith("@SQL="))
		{
			final String sqlTemplate = defaultValueStr.substring(5).trim();
			final IStringExpression sqlTemplateStringExpression = expressionFactory.compile(sqlTemplate, IStringExpression.class);
			return SqlDefaultValueExpression.of(sqlTemplateStringExpression);
		}
		else
		{
			final IStringExpression defaultValueExpression = expressionFactory.compile(defaultValueStr, IStringExpression.class);
			return defaultValueExpression;
		}
	}

	private static final class SpecialFieldsCollector
	{
		private static final String COLUMNNAME_DocumentNo = "DocumentNo";
		private static final String COLUMNNAME_Value = "Value";
		private static final String COLUMNNAME_Name = "Name";
		private static final String COLUMNNAME_DocStatus = "DocStatus";
		private static final String COLUMNNAME_DocAction = "DocAction";
		private static final Set<String> COLUMNNAMES = ImmutableSet.of(
				COLUMNNAME_DocumentNo, COLUMNNAME_Value, COLUMNNAME_Name, COLUMNNAME_DocStatus, COLUMNNAME_DocAction);

		private final Set<String> existingColumnNames = new HashSet<>();

		public void updateFromColumnName(final String columnName)
		{
			if (COLUMNNAMES.contains(columnName))
			{
				existingColumnNames.add(columnName);
			}
		}

		public String getDocNoField()
		{
			if (existingColumnNames.contains(COLUMNNAME_DocumentNo))
			{
				return COLUMNNAME_DocumentNo;
			}
			else if (existingColumnNames.contains(COLUMNNAME_Value))
			{
				return COLUMNNAME_Value;
			}
			else if (existingColumnNames.contains(COLUMNNAME_Name))
			{
				return COLUMNNAME_Name;
			}
			return null;
		}

		public String getDocStatusField()
		{
			return existingColumnNames.contains(COLUMNNAME_DocStatus) ? COLUMNNAME_DocStatus : null;
		}

		public String getDocActionField()
		{
			return existingColumnNames.contains(COLUMNNAME_DocAction) ? COLUMNNAME_DocAction : null;
		}
	}
}
