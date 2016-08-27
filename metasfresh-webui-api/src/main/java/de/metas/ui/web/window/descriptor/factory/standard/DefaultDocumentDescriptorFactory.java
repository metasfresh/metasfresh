package de.metas.ui.web.window.descriptor.factory.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.ad.window.process.AD_Window_CreateUIElements.IWindowUIElementsGeneratorConsumer;
import org.adempiere.ad.window.process.AD_Window_CreateUIElements.WindowUIElementsGenerator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_Tab;
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
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.FieldType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.LayoutType;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
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
	// services
	private static final Logger logger = LogManager.getLogger(DefaultDocumentDescriptorFactory.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	private static final int MAIN_TabNo = 0;
	/** Column names where we shall use {@link DocumentFieldWidgetType#Switch} instead of {@link DocumentFieldWidgetType#YesNo} */
	private static final Set<String> COLUMNNAMES_Switch = ImmutableSet.of(WindowConstants.FIELDNAME_IsActive); // FIXME: hardcoded

	/** Logic expression which evaluates as <code>true</code> when IsActive flag exists but it's <code>false</code> */
	private static final ILogicExpression LOGICEXPRESSION_NotActive;
	/** Logic expression which evaluates as <code>true</code> when Processed flag exists and it's <code>true</code> */
	private static final ILogicExpression LOGICEXPRESSION_Processed;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_Yes;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_No;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_Zero;
	private static final IStringExpression DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID;

	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		LOGICEXPRESSION_NotActive = expressionFactory.compile("@" + WindowConstants.FIELDNAME_IsActive + "/Y@=N", ILogicExpression.class);
		LOGICEXPRESSION_Processed = expressionFactory.compile("@" + WindowConstants.FIELDNAME_Processed + "/N@=Y | @" + WindowConstants.FIELDNAME_Processing + "/N@=Y", ILogicExpression.class);

		DEFAULT_VALUE_EXPRESSION_Yes = expressionFactory.compile(DisplayType.toBooleanString(true), IStringExpression.class);
		DEFAULT_VALUE_EXPRESSION_No = expressionFactory.compile(DisplayType.toBooleanString(false), IStringExpression.class);
		DEFAULT_VALUE_EXPRESSION_Zero = expressionFactory.compile("0", IStringExpression.class);
		DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID = expressionFactory.compile(String.valueOf(IAttributeDAO.M_AttributeSetInstance_ID_None), IStringExpression.class);
	}

	private final CCache<Integer, DocumentDescriptor> documentDescriptorsByWindowId = new CCache<>(I_AD_Window.Table_Name + "#DocumentDescriptor", 50);

	private final boolean debugShowColumnNamesForCaption = true;

	/* package */ DefaultDocumentDescriptorFactory()
	{
		super();
	}

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
		final GridTabVO mainTabVO = gridWindowVO.getTab(MAIN_TabNo);
		final DocumentEntityDescriptor.Builder mainEntityBuilder = documentEntity(gridWindowVO, mainTabVO);
		{
			final SpecialFieldsCollector specialFieldsCollector = new SpecialFieldsCollector();
			layoutBuilder.addSections(layoutSectionsList(mainTabVO, specialFieldsCollector));

			// Set special field names
			layoutBuilder
					.setDocumentNoElement(specialFieldsCollector.buildDocumentNoElementAndConsume())
					.setDocActionElement(specialFieldsCollector.buildDocActionElementAndConsume());

			final SqlDocumentEntityDataBindingDescriptor.Builder mainEntityBindingsBuilder = documentEntryDataBinding((GridTabVO)null, mainTabVO);

			//
			// Fields mapping & data binding
			mainTabVO.getFields()
					.stream()
					.sorted(GridFieldVO.COMPARATOR_BySeqNo)
					.map(gridFieldVO -> documentField(mainEntityBindingsBuilder, mainTabVO, gridFieldVO))
					.forEach(fieldDescriptor -> {
						mainEntityBuilder.addField(fieldDescriptor);
						mainEntityBindingsBuilder.addField(fieldDescriptor.getDataBinding());
					});

			mainEntityBuilder.setDataBinding(mainEntityBindingsBuilder.build());
		}

		//
		// Layout: Create UI details from child tabs
		for (final GridTabVO detailTabVO : gridWindowVO.getChildTabs(MAIN_TabNo))
		{
			layoutBuilder.addDetailIfValid(layoutDetail(detailTabVO));

			final SqlDocumentEntityDataBindingDescriptor.Builder detailEntityBindingsBuilder = documentEntryDataBinding(mainTabVO, detailTabVO);
			final DocumentEntityDescriptor.Builder detailEntityBuilder = documentEntity(gridWindowVO, detailTabVO);

			//
			// Fields mapping
			detailTabVO.getFields()
					.stream()
					.sorted(GridFieldVO.COMPARATOR_BySeqNoGrid)
					.map(gridFieldVO -> documentField(detailEntityBindingsBuilder, detailTabVO, gridFieldVO))
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

	private List<DocumentLayoutSectionDescriptor.Builder> layoutSectionsList(final GridTabVO gridTabVO, final SpecialFieldsCollector specialFieldsCollector)
	{
		//
		// Pick the right UI elements provider (DAO, fallback to InMemory),
		// and fetch the UI sections
		IWindowUIElementsProvider uiProvider = new DAOWindowUIElementsProvider();
		final int AD_Tab_ID = gridTabVO.getAD_Tab_ID();
		List<I_AD_UI_Section> uiSections = uiProvider.getUISections(AD_Tab_ID);
		if (uiSections.isEmpty())
		{
			uiProvider = new InMemoryUIElements();
			logger.warn("No UI Sections found for {}. Switching to {}", gridTabVO, uiProvider);

			uiSections = uiProvider.getUISections(AD_Tab_ID);
		}

		//
		// UI Sections
		final List<DocumentLayoutSectionDescriptor.Builder> layoutSectionBuilders = new ArrayList<>();
		for (final I_AD_UI_Section uiSection : uiSections)
		{
			final DocumentLayoutSectionDescriptor.Builder layoutSectionBuilder = layoutSection(uiProvider, uiSection, gridTabVO, specialFieldsCollector);
			if (layoutSectionBuilder == null)
			{
				continue;
			}
			layoutSectionBuilders.add(layoutSectionBuilder);
		}

		return layoutSectionBuilders;
	}

	private DocumentLayoutSectionDescriptor.Builder layoutSection(final IWindowUIElementsProvider uiProvider, final I_AD_UI_Section uiSection, final GridTabVO gridTabVO,
			final SpecialFieldsCollector specialFieldsCollector)
	{
		if (!uiSection.isActive())
		{
			return null;
		}

		final DocumentLayoutSectionDescriptor.Builder layoutSectionBuilder = DocumentLayoutSectionDescriptor.builder();

		//
		// UI Columns
		for (final I_AD_UI_Column uiColumn : uiProvider.getUIColumns(uiSection))
		{
			final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = layoutColumn(uiProvider, uiColumn, gridTabVO, specialFieldsCollector);
			if (layoutColumnBuilder == null)
			{
				continue;
			}
			layoutSectionBuilder.addColumn(layoutColumnBuilder);
		}

		return layoutSectionBuilder;
	}

	private DocumentLayoutColumnDescriptor.Builder layoutColumn(final IWindowUIElementsProvider uiProvider, final I_AD_UI_Column uiColumn, final GridTabVO gridTabVO,
			final SpecialFieldsCollector specialFieldsCollector)
	{
		if (!uiColumn.isActive())
		{
			return null;
		}

		final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = DocumentLayoutColumnDescriptor.builder();

		//
		// UI Element Groups
		for (final I_AD_UI_ElementGroup uiElementGroup : uiProvider.getUIElementGroups(uiColumn))
		{
			final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = layoutElementGroup(uiProvider, uiElementGroup, gridTabVO, specialFieldsCollector);
			if (layoutElementGroupBuilder == null)
			{
				continue;
			}
			layoutColumnBuilder.addElementGroup(layoutElementGroupBuilder);
		}

		return layoutColumnBuilder;
	}

	private DocumentLayoutElementGroupDescriptor.Builder layoutElementGroup(final IWindowUIElementsProvider uiProvider, final I_AD_UI_ElementGroup uiElementGroup, final GridTabVO gridTabVO,
			final SpecialFieldsCollector specialFieldsCollector)
	{
		if (!uiElementGroup.isActive())
		{
			return null;
		}

		final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = DocumentLayoutElementGroupDescriptor.builder()
				.setLayoutType(uiElementGroup.getUIStyle());

		//
		// UI Elements
		boolean isFirstElementInGroup = true;
		for (final I_AD_UI_Element uiElement : uiProvider.getUIElements(uiElementGroup))
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
					.setLayoutType(layoutType);
			{
				final GridFieldVO gridFieldVO = gridTabVO.getFieldByAD_Field_ID(uiElement.getAD_Field_ID());
				if (gridFieldVO != null)
				{
					layoutElementBuilder
							.setWidgetType(extractWidgetType(gridFieldVO))
							.addField(layoutElementField(gridTabVO, gridFieldVO));

					specialFieldsCollector.collect(layoutElementBuilder);
				}
			}

			//
			// UI Element Fields (if any)
			for (final I_AD_UI_ElementField uiElementField : uiProvider.getUIElementFields(uiElement))
			{
				if (!uiElementField.isActive())
				{
					continue;
				}

				final GridFieldVO gridFieldVO = gridTabVO.getFieldByAD_Field_ID(uiElementField.getAD_Field_ID());
				if (gridFieldVO != null)
				{
					if (layoutElementBuilder.getWidgetType() == null)
					{
						layoutElementBuilder.setWidgetType(extractWidgetType(gridFieldVO));
					}
					final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = layoutElementField(gridTabVO, gridFieldVO);
					layoutElementBuilder.addField(layoutElementFieldBuilder);

					specialFieldsCollector.collect(layoutElementBuilder);
				}
			}

			if (debugShowColumnNamesForCaption)
			{
				layoutElementBuilder.setCaptionAsFieldNames();
			}

			final DocumentLayoutElementLineDescriptor.Builder layoutElementLineBuilder = DocumentLayoutElementLineDescriptor.builder()
					.addElement(layoutElementBuilder);

			layoutElementGroupBuilder.addElementLine(layoutElementLineBuilder);

			//
			isFirstElementInGroup = false;
		} /* each uiElement */

		return layoutElementGroupBuilder;
	}

	private static DocumentLayoutDetailDescriptor.Builder layoutDetail(final GridTabVO tab)
	{
		// If the detail is never displayed then don't add it to layout
		final ILogicExpression tabDisplayLogic = extractTabDisplayLogic(tab);
		if (tabDisplayLogic.isConstantFalse())
		{
			logger.trace("Skip adding detail tab to layout because it's never displayed: {}, tabDisplayLogic={}", tab, tabDisplayLogic);
			return null;
		}

		final DocumentLayoutDetailDescriptor.Builder layoutDetailBuilder = DocumentLayoutDetailDescriptor.builder()
				.setDetailId(extractDetailId(tab))
				.setCaption(tab.getName())
				.setDescription(tab.getDescription());

		tab.getFields()
				.stream()
				.filter(gridFieldVO -> gridFieldVO.isDisplayedGrid()) // only those which are displayed on grid
				.sorted(GridFieldVO.COMPARATOR_BySeqNoGrid)
				.map(gridFieldVO -> DocumentLayoutElementDescriptor.builder()
						.setCaption(gridFieldVO.getHeader())
						.setDescription(gridFieldVO.getDescription())
						.setWidgetType(extractWidgetType(gridFieldVO))
						.setLayoutTypeNone() // does not matter for detail
						.addField(layoutElementField(tab, gridFieldVO)))
				.forEach(layoutDetailBuilder::addElement);

		return layoutDetailBuilder;
	}

	private DocumentEntityDescriptor.Builder documentEntity(final GridWindowVO gridWindowVO, final GridTabVO gridTabVO)
	{
		final ILogicExpression allowInsert = ConstantLogicExpression.of(gridTabVO.isInsertRecord());
		final ILogicExpression allowDelete = ConstantLogicExpression.of(gridTabVO.isDeleteable());
		final ILogicExpression readonly = extractTabReadonlyLogic(gridTabVO);

		final ILogicExpression allowCreateNewLogic = allowInsert.andNot(readonly);
		final ILogicExpression allowDeleteLogic = allowDelete.andNot(readonly);

		final ILogicExpression displayLogic = extractTabDisplayLogic(gridTabVO);

		return DocumentEntityDescriptor.builder()
				.setDetailId(extractDetailId(gridTabVO))
				//
				.setAllowCreateNewLogic(allowCreateNewLogic)
				.setAllowDeleteLogic(allowDeleteLogic)
				.setDisplayLogic(displayLogic)
				//
				.setAD_Window_ID(gridTabVO.getAD_Window_ID()) // legacy
				.setAD_Tab_ID(gridTabVO.getAD_Tab_ID()) // legacy
				.setTabNo(gridTabVO.getTabNo()) // legacy
				.setIsSOTrx(gridWindowVO.isSOTrx()) // legacy
				;
	}
	
	private SqlDocumentEntityDataBindingDescriptor.Builder documentEntryDataBinding(final GridTabVO parentTab, final GridTabVO detailTabVO)
	{
		return SqlDocumentEntityDataBindingDescriptor.builder()
				.setSqlTableName(detailTabVO.getTableName())
				.setSqlTableAliasFromDetailId(extractDetailId(detailTabVO))
				.setAD_Table_ID(detailTabVO.getAD_Table_ID()) // legacy
				.setSqlParentLinkColumnName(extractParentLinkColumnName(parentTab, detailTabVO))
				.setSqlWhereClause(detailTabVO.getWhereClause())
				.setSqlOrderBy(detailTabVO.getOrderByClause());

		
	}

	private DocumentFieldDescriptor documentField(
			final SqlDocumentEntityDataBindingDescriptor.Builder detailEntityBindingsBuilder //
			, final GridTabVO gridTabVO //
			, final GridFieldVO gridFieldVO //
	)
	{
		// From entry data-binding:
		final String sqlTableName = detailEntityBindingsBuilder.getSqlTableName();
		final String sqlTableAlias = detailEntityBindingsBuilder.getSqlTableAlias();
		final String sqlParentLinkColumnName = detailEntityBindingsBuilder.getSqlParentLinkColumnName();
		
		// From GridTabVO:
		final String detailId = extractDetailId(gridTabVO);
		
		// From GridFieldVO:
		final String sqlColumnName = gridFieldVO.getColumnName();
		final boolean keyColumn = gridFieldVO.isKey();
		
		//
		final boolean isParentLinkColumn = sqlColumnName.equals(sqlParentLinkColumnName);

		//
		//
		final int displayType;
		final int AD_Reference_Value_ID;
		final int AD_Val_Rule_ID;
		final DocumentFieldWidgetType widgetType;
		final Class<?> valueClass;
		final IStringExpression defaultValueExpression;
		final boolean publicField;
		final ILogicExpression readonlyLogic;
		final boolean alwaysUpdateable;
		final ILogicExpression mandatoryLogic;
		final ILogicExpression displayLogic;
		if (isParentLinkColumn)
		{
			displayType = DisplayType.ID;
			AD_Reference_Value_ID = 0; // none
			AD_Val_Rule_ID = 0;
			widgetType = DocumentFieldWidgetType.Integer;
			valueClass = Integer.class;
			defaultValueExpression = IStringExpression.NULL;
			publicField = false;
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
			readonlyLogic = extractFieldReadonlyLogic(gridTabVO, gridFieldVO);
			alwaysUpdateable = extractAlwaysUpdateable(gridFieldVO);
			displayLogic = extractFieldDisplayLogic(gridTabVO, gridFieldVO);
			publicField = isPublicField(keyColumn, displayLogic);
			mandatoryLogic = extractMandatoryLogic(gridFieldVO, publicField);
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
				.setParentLinkColumn(isParentLinkColumn)
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
				.setParentLink(isParentLinkColumn)
				//
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setVirtualField(gridFieldVO.isVirtualColumn())
				.setCalculated(gridFieldVO.isCalculated())
				//
				.setDefaultValueExpression(defaultValueExpression)
				//
				.setPublicField(publicField)
				.setReadonlyLogic(readonlyLogic)
				.setAlwaysUpdateable(alwaysUpdateable)
				.setMandatoryLogic(mandatoryLogic)
				.setDisplayLogic(displayLogic)
				//
				.setDataBinding(dataBinding)
				//
				.build();
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
		// If this is the master tab then there is no parent link
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
			if (gridFieldVO.getAD_Reference_Value_ID() > 0)
			{
				return StringLookupValue.class;
			}
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
		else if (DisplayType.Button == displayType && gridFieldVO.getAD_Reference_Value_ID() > 0)
		{
			return DocumentLayoutElementFieldDescriptor.LookupSource.list;
		}
		else
		{
			return null;
		}
	}

	private static boolean isPublicField(final boolean isKey, final ILogicExpression displayLogic)
	{
		// Always publish the key columns, else the client won't know what to talk about ;)
		if (isKey)
		{
			return true;
		}

		// If display logic is not constant then we don't know if this field will be ever visible
		// so we are publishing it
		if (!displayLogic.isConstant())
		{
			return true;
		}

		// Publish this field only if it's displayed
		return displayLogic.constantValue() == true;
	}

	private static ILogicExpression extractTabReadonlyLogic(final GridTabVO gridTabVO)
	{
		if (gridTabVO.isView())
		{
			return ILogicExpression.TRUE;
		}

		//
		// Check if tab is always readonly
		if (gridTabVO.isReadOnly())
		{
			return ILogicExpression.TRUE;
		}

		//
		// Check if tab's readonly expression
		final ILogicExpression tabReadonlyLogic = gridTabVO.getReadOnlyLogic();
		if (tabReadonlyLogic.isConstantTrue())
		{
			return ILogicExpression.TRUE;
		}

		return tabReadonlyLogic;
	}

	private static ILogicExpression extractFieldReadonlyLogic(final GridTabVO gridTabVO, final GridFieldVO gridFieldVO)
	{
		// If the tab is always readonly, we can assume any field in that tab is readonly
		final ILogicExpression tabReadonlyLogic = extractTabReadonlyLogic(gridTabVO);
		if (tabReadonlyLogic.isConstantTrue())
		{
			return ILogicExpression.TRUE;
		}

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
		if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(columnName))
		{
			// NOTE: from UI perspective those are readonly (i.e. it will be managed by persistence layer)
			return ILogicExpression.TRUE;
		}

		if (WindowConstants.FIELDNAME_DocStatus.equals(columnName))
		{
			// NOTE: DocStatus field shall always be readonly
			return ILogicExpression.TRUE;
		}

		final ILogicExpression fieldReadonlyLogic = gridFieldVO.getReadOnlyLogic();

		// FIXME: not sure if using tabReadonlyLogic here is OK, because the tab logic shall be applied to parent tab!
		ILogicExpression readonlyLogic = tabReadonlyLogic.or(fieldReadonlyLogic);

		//
		// Consider field readonly if the row is not active
		// .. and this property is not the IsActive flag.
		if (!WindowConstants.FIELDNAME_IsActive.equals(columnName))
		{
			readonlyLogic = LOGICEXPRESSION_NotActive.or(readonlyLogic);
		}

		//
		// Consider field readonly if the row is processed.
		// In case we deal with an AlwaysUpdateable field, this logic do not apply.
		final boolean alwaysUpdateable = extractAlwaysUpdateable(gridFieldVO);
		if (!alwaysUpdateable)
		{
			readonlyLogic = LOGICEXPRESSION_Processed.or(readonlyLogic);
		}

		return readonlyLogic;
	}

	private static boolean extractAlwaysUpdateable(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isVirtualColumn() || !gridFieldVO.isUpdateable())
		{
			return false;
		}
		return gridFieldVO.isAlwaysUpdateable();
	}

	private static ILogicExpression extractMandatoryLogic(final GridFieldVO gridFieldVO, final boolean publicField)
	{
		final String columnName = gridFieldVO.getColumnName();
		if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(columnName))
		{
			// NOTE: from UI perspective those are not mandatory (i.e. it will be managed by persistence layer)
			return ILogicExpression.FALSE;
		}

		if (gridFieldVO.isVirtualColumn())
		{
			return ILogicExpression.FALSE;
		}

		// FIXME: hardcoded M_AttributeSetInstance_ID mandatory logic = false
		// Reason: even if we set it's default value to "0" some callouts are setting it to NULL,
		// and then the document saving API is failing because it considers this column as NOT filled.
		if (WindowConstants.FIELDNAME_M_AttributeSetInstance_ID.equals(columnName))
		{
			return ILogicExpression.FALSE;
		}

		// Corner case:
		// e.g. C_Order.M_Shipper_ID has AD_Field.IsMandatory=Y, AD_Field.IsDisplayed=N, AD_Column.IsMandatory=N
		// => we need to NOT enforce setting it because it's not needed, user cannot change it and it might be no callouts to set it.
		// Else, we won't be able to save our document.
		if (!publicField && gridFieldVO.isMandatory() && !gridFieldVO.isMandatoryDB())
		{
			return ILogicExpression.FALSE;
		}

		if (gridFieldVO.isMandatory())
		{
			return ILogicExpression.TRUE;
		}

		return gridFieldVO.getMandatoryLogic();
	}

	private static ILogicExpression extractTabDisplayLogic(final GridTabVO gridTabVO)
	{
		return gridTabVO.getDisplayLogic();
	}

	private static ILogicExpression extractFieldDisplayLogic(final GridTabVO gridTabVO, final GridFieldVO gridFieldVO)
	{
		if (gridTabVO.getTabNo() == MAIN_TabNo)
		{
			if (!gridFieldVO.isDisplayed())
			{
				return ILogicExpression.FALSE;
			}
		}
		else
		{
			if (!gridFieldVO.isDisplayedGrid())
			{
				return ILogicExpression.FALSE;
			}
		}

		// FIXME: not sure if using tabDisplayLogic here is OK, because the tab logic shall be applied to parent tab!
		final ILogicExpression tabDisplayLogic = extractTabDisplayLogic(gridTabVO);
		final ILogicExpression fieldDisplayLogic = gridFieldVO.getDisplayLogic();
		return tabDisplayLogic.and(fieldDisplayLogic);
	}

	private IStringExpression extractDefaultValueExpression(final GridFieldVO gridFieldVO)
	{
		final String defaultValueStr = gridFieldVO.getDefaultValue();
		if (defaultValueStr == null || defaultValueStr.isEmpty())
		{
			final int displayType = gridFieldVO.getDisplayType();
			if (displayType == DisplayType.YesNo)
			{
				if (WindowConstants.FIELDNAME_IsActive.equals(gridFieldVO.getColumnName()))
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
			else if (DisplayType.PAttribute == displayType)
			{
				return DEFAULT_VALUE_EXPRESSION_M_AttributeSetInstance_ID;
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

	private static final DocumentLayoutElementFieldDescriptor.Builder layoutElementField(final GridTabVO gridTabVO, final GridFieldVO gridFieldVO)
	{
		final ILogicExpression displayLogic = extractFieldDisplayLogic(gridTabVO, gridFieldVO);
		final boolean publicField = isPublicField(gridFieldVO.isKey(), displayLogic);

		return DocumentLayoutElementFieldDescriptor.builder(gridFieldVO.getColumnName())
				.setLookupSource(extractLookupSource(gridFieldVO))
				.setPublicField(publicField);
	}

	private static final class SpecialFieldsCollector
	{
		private static final Set<String> COLUMNNAMES = ImmutableSet.of(
				WindowConstants.FIELDNAME_DocumentNo //
				, WindowConstants.FIELDNAME_Value //
				, WindowConstants.FIELDNAME_Name //
				, WindowConstants.FIELDNAME_DocStatus //
				, WindowConstants.FIELDNAME_DocAction //
		);

		private final Map<String, DocumentLayoutElementDescriptor.Builder> existingFields = new HashMap<>();

		public void collect(final DocumentLayoutElementDescriptor.Builder layoutElementBuilder)
		{
			for (final String fieldName : layoutElementBuilder.getFieldNames())
			{
				if (COLUMNNAMES.contains(fieldName))
				{
					existingFields.put(fieldName, layoutElementBuilder);
				}
			}
		}

		public DocumentLayoutElementDescriptor buildDocumentNoElementAndConsume()
		{
			for (final String fieldName : Arrays.asList(WindowConstants.FIELDNAME_DocumentNo, WindowConstants.FIELDNAME_Value, WindowConstants.FIELDNAME_Name))
			{
				final DocumentLayoutElementDescriptor.Builder elementBuilder = existingFields.get(fieldName);
				if (elementBuilder == null)
				{
					continue;
				}
				if (elementBuilder.isConsumed())
				{
					continue;
				}

				final DocumentLayoutElementDescriptor element = elementBuilder
						.setLayoutTypeNone() // not relevant
						.build();
				return element;
			}

			return null;
		}

		public DocumentLayoutElementDescriptor buildDocActionElementAndConsume()
		{
			final DocumentLayoutElementFieldDescriptor.Builder docStatusFieldBuilder = getExistingField(WindowConstants.FIELDNAME_DocStatus);
			if (docStatusFieldBuilder == null)
			{
				return null;
			}

			final DocumentLayoutElementFieldDescriptor.Builder docActionFieldBuilder = getExistingField(WindowConstants.FIELDNAME_DocAction);
			if (docActionFieldBuilder == null)
			{
				return null;
			}

			return DocumentLayoutElementDescriptor.builder()
					.setCaption(null) // not relevant
					.setDescription(null) // not relevant
					.setLayoutTypeNone() // not relevant
					.setWidgetType(DocumentFieldWidgetType.ActionButton)
					.addField(docStatusFieldBuilder.setFieldType(FieldType.ActionButtonStatus))
					.addField(docActionFieldBuilder.setFieldType(FieldType.ActionButton))
					.build();
		}

		private final DocumentLayoutElementFieldDescriptor.Builder getExistingField(final String fieldName)
		{
			final DocumentLayoutElementDescriptor.Builder elementBuilder = existingFields.get(fieldName);
			if (elementBuilder == null || elementBuilder.isConsumed())
			{
				return null;
			}

			final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = elementBuilder.getField(fieldName);
			if (fieldBuilder == null || fieldBuilder.isConsumed())
			{
				return null;
			}

			return fieldBuilder;
		}
	}

	private static interface IWindowUIElementsProvider
	{
		List<I_AD_UI_Section> getUISections(int AD_Tab_ID);

		List<I_AD_UI_Column> getUIColumns(I_AD_UI_Section uiSection);

		List<I_AD_UI_ElementGroup> getUIElementGroups(I_AD_UI_Column uiColumn);

		List<I_AD_UI_Element> getUIElements(I_AD_UI_ElementGroup uiElementGroup);

		List<I_AD_UI_ElementField> getUIElementFields(I_AD_UI_Element uiElement);
	}

	private static final class DAOWindowUIElementsProvider implements IWindowUIElementsProvider
	{
		private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

		@Override
		public List<I_AD_UI_Section> getUISections(final int AD_Tab_ID)
		{
			final Properties ctx = Env.getCtx();
			return windowDAO.retrieveUISections(ctx, AD_Tab_ID);
		}

		@Override
		public List<I_AD_UI_Column> getUIColumns(final I_AD_UI_Section uiSection)
		{
			return windowDAO.retrieveUIColumns(uiSection);
		}

		@Override
		public List<I_AD_UI_ElementGroup> getUIElementGroups(final I_AD_UI_Column uiColumn)
		{
			return windowDAO.retrieveUIElementGroups(uiColumn);
		}

		@Override
		public List<I_AD_UI_Element> getUIElements(final I_AD_UI_ElementGroup uiElementGroup)
		{
			return windowDAO.retrieveUIElements(uiElementGroup);
		}

		@Override
		public List<I_AD_UI_ElementField> getUIElementFields(final I_AD_UI_Element uiElement)
		{
			return windowDAO.retrieveUIElementFields(uiElement);
		}
	}

	private static final class InMemoryUIElements implements IWindowUIElementsGeneratorConsumer, IWindowUIElementsProvider
	{
		private static final Logger logger = LogManager.getLogger(InMemoryUIElements.class);

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
				WindowUIElementsGenerator.forConsumer(this).generateForMainTabId(AD_Tab_ID);
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
}
