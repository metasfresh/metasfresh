package de.metas.ui.web.window.descriptor.factory.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
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
import org.compiere.model.MLookupInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;

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
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.LayoutType;
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

/*package*/ class DefaultDocumentDescriptorLoader
{
	// services
	private static final transient Logger logger = LogManager.getLogger(DefaultDocumentDescriptorLoader.class);
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

	//
	// Parameters
	private final int AD_Window_ID;
	private final boolean debugShowColumnNamesForCaption = true;

	//
	// Status
	private boolean _executed = false;
	private IWindowUIElementsProvider _uiProvider;
	private final SpecialFieldsCollector specialFieldsCollector = new SpecialFieldsCollector();
	private GridTabVO _mainTabVO;

	/* package */ DefaultDocumentDescriptorLoader(final int AD_Window_ID)
	{
		super();
		this.AD_Window_ID = AD_Window_ID;
	}

	public DocumentDescriptor load()
	{
		// Mark as executed
		if (_executed)
		{
			throw new IllegalStateException("Already executed");
		}
		_executed = true;

		if (AD_Window_ID <= 0)
		{
			throw new AdempiereException("No window found for AD_Window_ID=" + AD_Window_ID);
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Properties ctx = Env.getCtx(); // TODO
		final int windowNo = 0; // TODO: get rid of WindowNo from GridWindowVO
		final GridWindowVO gridWindowVO = GridWindowVO.create(ctx, windowNo, AD_Window_ID);
		Check.assumeNotNull(gridWindowVO, "Parameter gridWindowVO is not null"); // shall never happen

		final DocumentDescriptor.Builder documentBuilder = DocumentDescriptor.builder();
		final DocumentLayoutDescriptor.Builder layoutBuilder = DocumentLayoutDescriptor.builder()
				.setAD_Window_ID(gridWindowVO.getAD_Window_ID())
				.setStopwatch(stopwatch);

		//
		// Layout: Create UI sections from main tab
		final GridTabVO mainTabVO = gridWindowVO.getTab(MAIN_TabNo);
		setMainTab(mainTabVO);
		final DocumentEntityDescriptor.Builder mainEntityBuilder = documentEntity(gridWindowVO, mainTabVO);
		{
			layoutBuilder.addSections(layoutSectionsList());

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
		// Layout debug properties
		layoutBuilder.putDebugProperty("generator-name", toString());

		//
		//
		return documentBuilder
				.setLayout(layoutBuilder.build())
				.setEntityDescriptor(mainEntityBuilder.build())
				.build();
	}

	private final List<I_AD_UI_Section> configureUIProviderAndGetUISections(final GridTabVO gridTabVO)
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

		_uiProvider = uiProvider;
		logger.trace("Using UI provider: {}", _uiProvider);

		return uiSections;
	}

	private IWindowUIElementsProvider getUIProvider()
	{
		if (_uiProvider == null)
		{
			throw new IllegalStateException("UI provider was not configured");
		}
		return _uiProvider;
	}

	private void setMainTab(final GridTabVO mainTabVO)
	{
		if (_mainTabVO != null)
		{
			throw new IllegalStateException("Cannot set main tab to " + mainTabVO + " because it was already set to " + _mainTabVO);
		}
		_mainTabVO = mainTabVO;
		logger.trace("Using main tab: {}", _mainTabVO);
	}

	private GridTabVO getMainTab()
	{
		return _mainTabVO;
	}

	private List<DocumentLayoutSectionDescriptor.Builder> layoutSectionsList()
	{
		final GridTabVO mainTab = getMainTab();
		final List<I_AD_UI_Section> uiSections = configureUIProviderAndGetUISections(mainTab);

		//
		// UI Sections
		final List<DocumentLayoutSectionDescriptor.Builder> layoutSectionBuilders = new ArrayList<>();
		for (final I_AD_UI_Section uiSection : uiSections)
		{
			layoutSectionBuilders.add(layoutSection(uiSection));
		}

		return layoutSectionBuilders;
	}

	private DocumentLayoutSectionDescriptor.Builder layoutSection(final I_AD_UI_Section uiSection)
	{
		final DocumentLayoutSectionDescriptor.Builder layoutSectionBuilder = DocumentLayoutSectionDescriptor.builder();

		if (!uiSection.isActive())
		{
			return layoutSectionBuilder.setInvalid("UI section not active: " + uiSection);
		}

		//
		// UI Columns
		for (final I_AD_UI_Column uiColumn : getUIProvider().getUIColumns(uiSection))
		{
			final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = layoutColumn(uiColumn);
			if (layoutColumnBuilder == null)
			{
				continue;
			}
			layoutSectionBuilder.addColumn(layoutColumnBuilder);
		}

		return layoutSectionBuilder;
	}

	private DocumentLayoutColumnDescriptor.Builder layoutColumn(final I_AD_UI_Column uiColumn)
	{
		if (!uiColumn.isActive())
		{
			return null;
		}

		final DocumentLayoutColumnDescriptor.Builder layoutColumnBuilder = DocumentLayoutColumnDescriptor.builder();

		//
		// UI Element Groups
		for (final I_AD_UI_ElementGroup uiElementGroup : getUIProvider().getUIElementGroups(uiColumn))
		{
			final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = layoutElementGroup(uiElementGroup);
			if (layoutElementGroupBuilder == null)
			{
				continue;
			}
			layoutColumnBuilder.addElementGroup(layoutElementGroupBuilder);
		}

		return layoutColumnBuilder;
	}

	private DocumentLayoutElementGroupDescriptor.Builder layoutElementGroup(final I_AD_UI_ElementGroup uiElementGroup)
	{
		if (!uiElementGroup.isActive())
		{
			return null;
		}

		final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder = DocumentLayoutElementGroupDescriptor.builder()
				.setLayoutType(uiElementGroup.getUIStyle());

		//
		// UI Elements
		for (final I_AD_UI_Element uiElement : getUIProvider().getUIElements(uiElementGroup))
		{
			final DocumentLayoutElementLineDescriptor.Builder layoutElementLineBuilder = layoutElementLine(uiElement, layoutElementGroupBuilder);
			if (layoutElementLineBuilder == null)
			{
				continue;
			}

			layoutElementGroupBuilder.addElementLine(layoutElementLineBuilder);
		}

		return layoutElementGroupBuilder;
	}

	private DocumentLayoutElementLineDescriptor.Builder layoutElementLine(final I_AD_UI_Element uiElement, final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder)
	{
		// TODO: introduce the AD_UI_ElementLine table

		final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = layoutElement(uiElement, layoutElementGroupBuilder);
		if (layoutElementBuilder == null)
		{
			return null;
		}

		final DocumentLayoutElementLineDescriptor.Builder layoutElementLineBuilder = DocumentLayoutElementLineDescriptor.builder()
				.addElement(layoutElementBuilder);

		return layoutElementLineBuilder;

	}

	private DocumentLayoutElementDescriptor.Builder layoutElement(final I_AD_UI_Element uiElement, final DocumentLayoutElementGroupDescriptor.Builder layoutElementGroupBuilder)
	{
		if (!uiElement.isActive())
		{
			return null;
		}

		final GridTabVO mainTab = getMainTab();

		// TODO: atm if we setting first element in group as primary, others as secondary.
		final boolean isFirstElementInGroup = !layoutElementGroupBuilder.hasElementLines();
		final LayoutType layoutType = layoutElementGroupBuilder.getLayoutType() == LayoutType.primary && isFirstElementInGroup ? LayoutType.primary : LayoutType.secondary;

		//
		// UI main field
		final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = DocumentLayoutElementDescriptor.builder()
				.setCaption(uiElement.getName())
				.setDescription(uiElement.getDescription())
				.setLayoutType(layoutType);
		{
			final GridFieldVO gridFieldVO = mainTab.getFieldByAD_Field_ID(uiElement.getAD_Field_ID());
			if (gridFieldVO != null)
			{
				layoutElementBuilder
						.setWidgetType(extractWidgetType(gridFieldVO))
						.addField(layoutElementField(mainTab, gridFieldVO));

				specialFieldsCollector.collect(layoutElementBuilder);
			}
		}

		//
		// UI Element Fields (if any)
		for (final I_AD_UI_ElementField uiElementField : getUIProvider().getUIElementFields(uiElement))
		{
			if (!uiElementField.isActive())
			{
				continue;
			}

			final GridFieldVO gridFieldVO = mainTab.getFieldByAD_Field_ID(uiElementField.getAD_Field_ID());
			if (gridFieldVO != null)
			{
				if (layoutElementBuilder.getWidgetType() == null)
				{
					layoutElementBuilder.setWidgetType(extractWidgetType(gridFieldVO));
				}
				final DocumentLayoutElementFieldDescriptor.Builder layoutElementFieldBuilder = layoutElementField(mainTab, gridFieldVO);
				layoutElementBuilder.addField(layoutElementFieldBuilder);

				specialFieldsCollector.collect(layoutElementBuilder);
			}
		}

		if (debugShowColumnNamesForCaption)
		{
			layoutElementBuilder.setCaptionAsFieldNames();
		}

		return layoutElementBuilder;
	}

	private static DocumentLayoutDetailDescriptor.Builder layoutDetail(final GridTabVO detailTab)
	{
		// If the detail is never displayed then don't add it to layout
		final ILogicExpression tabDisplayLogic = extractTabDisplayLogic(detailTab);
		if (tabDisplayLogic.isConstantFalse())
		{
			logger.trace("Skip adding detail tab to layout because it's never displayed: {}, tabDisplayLogic={}", detailTab, tabDisplayLogic);
			return null;
		}

		final DocumentLayoutDetailDescriptor.Builder layoutDetailBuilder = DocumentLayoutDetailDescriptor.builder()
				.setDetailId(extractDetailId(detailTab))
				.setCaption(detailTab.getName())
				.setDescription(detailTab.getDescription());

		detailTab.getFields()
				.stream()
				.filter(gridFieldVO -> gridFieldVO.isDisplayedGrid()) // only those which are displayed on grid
				.sorted(GridFieldVO.COMPARATOR_BySeqNoGrid)
				.map(gridFieldVO -> DocumentLayoutElementDescriptor.builder()
						.setCaption(gridFieldVO.getHeader())
						.setDescription(gridFieldVO.getDescription())
						.setWidgetType(extractWidgetType(gridFieldVO))
						.setLayoutTypeNone() // does not matter for detail
						.addField(layoutElementField(detailTab, gridFieldVO)))
				.forEach(layoutDetailBuilder::addElement);

		return layoutDetailBuilder;
	}

	private static DocumentEntityDescriptor.Builder documentEntity(final GridWindowVO gridWindowVO, final GridTabVO gridTabVO)
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

	private static SqlDocumentEntityDataBindingDescriptor.Builder documentEntryDataBinding(@Nullable final GridTabVO parentTab, final GridTabVO detailTabVO)
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

	private static String extractParentLinkColumnName(@Nullable final GridTabVO parentTabVO, final GridTabVO tabVO)
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

}
