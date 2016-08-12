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
import org.compiere.util.CCache;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;

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
public class DocumentDescriptorFactory
{
	private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	private static final int MAIN_TabNo = 0;
	private static final String COLUMNNAME_IsActive = "IsActive";
	private static final String COLUMNNAME_Processed = "Processed";
	private static final String COLUMNNAME_Processing = "Processing";
	/** Column names where we shall use {@link DocumentFieldWidgetType#Switch} instead of {@link DocumentFieldWidgetType#YesNo} */
	private static final Set<String> COLUMNNAMES_Switch = ImmutableSet.of(COLUMNNAME_IsActive); // FIXME: hardcoded

	/** Logic expression which evaluates as <code>true</code> when IsActive flag exists but it's <code>false</code> */
	private static final ILogicExpression LOGICEXPRESSION_NotActive;
	/** Logic expression which evaluates as <code>true</code> when Processed flag exists and it's <code>true</code> */
	private static final ILogicExpression LOGICEXPRESSION_Processed;

	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		LOGICEXPRESSION_NotActive = expressionFactory.compile("@" + COLUMNNAME_IsActive + "/Y@=N", ILogicExpression.class);
		LOGICEXPRESSION_Processed = expressionFactory.compile("@" + COLUMNNAME_Processed + "/N@=Y | @" + COLUMNNAME_Processing + "/N@=Y", ILogicExpression.class);
	}

	private final CCache<Integer, DocumentDescriptor> documentDescriptorsByWindowId = new CCache<>(I_AD_Window.Table_Name + "#DocumentDescriptor", 50);

	/* package */ DocumentDescriptorFactory()
	{
		super();
	}

	public DocumentDescriptor getDocumentDescriptor(final int AD_Window_ID)
	{
		return documentDescriptorsByWindowId.getOrLoad(AD_Window_ID, () -> createDocumentDescriptor(AD_Window_ID));
	}

	public DocumentDescriptor createDocumentDescriptor(final int AD_Window_ID)
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
				.setTabNo(mainTabNo) // legacy
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
					.map(gridFieldVO -> documentFieldDescriptorBuilder(mainTabVO, mainEntityBindingsBuilder.getSqlTableAlias(), gridFieldVO).build())
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
					.setSqlTableAliasFromDetailId(detail.getDetailId())
					.setAD_Table_ID(detailTabVO.getAD_Table_ID()) // legacy
					.setSqlParentLinkColumnName(extractParentLinkColumnName(mainTabVO, detailTabVO))
					.setSqlWhereClause(detailTabVO.getWhereClause())
					.setSqlOrderBy(detailTabVO.getOrderByClause());

			final DocumentEntityDescriptor.Builder detailEntityBuilder = DocumentEntityDescriptor.builder()
					.setId(detailTabVO.getAD_Tab_ID())
					.setDetailId(detail.getDetailId())
					.setTabNo(detailTabVO.getTabNo()) // legacy
					;

			//
			// Fields mapping
			detailTabVO.getFields()
					.stream()
					.sorted(GridFieldVO.COMPARATOR_BySeqNoGrid)
					.map(gridFieldVO -> documentFieldDescriptorBuilder(detailTabVO, detailEntityBindingsBuilder.getSqlTableAlias(), gridFieldVO)
							.setParentLink(gridFieldVO.getColumnName().equals(detailEntityBindingsBuilder.getSqlParentLinkColumnName()))
							.build())
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
							.setType(uiElementGroup.getUIStyle());

					//
					// UI Elements
					for (final I_AD_UI_Element uiElement : windowDAO.retrieveUIElements(uiElementGroup))
					{
						if (!uiElement.isActive())
						{
							continue;
						}

						//
						// UI main field
						final DocumentLayoutElementDescriptor.Builder layoutElementBuilder = DocumentLayoutElementDescriptor.builder()
								.setCaption(uiElement.getName())
								.setDescription(uiElement.getDescription());

						{
							final GridFieldVO gridFieldVO = gridTabVO.getFieldByAD_Field_ID(uiElement.getAD_Field_ID());
							if (gridFieldVO != null)
							{
								final String columnName = gridFieldVO.getColumnName();
								layoutElementBuilder
										.setWidgetType(extractWidgetType(gridFieldVO))
										.addField(DocumentLayoutElementFieldDescriptor.builder()
												.setField(columnName)
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

						layoutElementGroupBuilder.addElement(layoutElementBuilder.build());
					}

					layoutColumnBuilder.addElementGroupIfNotEmpty(layoutElementGroupBuilder.build());
				}

				layoutSectionBuilder.addColumnIfNotEmpty(layoutColumnBuilder.build());
			}

			uiSections.add(layoutSectionBuilder.build());
		}

		return uiSections;
	}

	private DocumentLayoutDetailDescriptor createDetail(final GridTabVO tab)
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
					.addField(DocumentLayoutElementFieldDescriptor.builder()
							.setField(columnName)
							.build())
					.build();

			elementGroupBuilder.addElement(element);
		}

		return elementGroupBuilder.build();
	}

	private DocumentFieldDescriptor.Builder documentFieldDescriptorBuilder(final GridTabVO gridTabVO, final String sqlTableAlias, final GridFieldVO gridFieldVO)
	{
		final String sqlTableName = gridTabVO.getTableName();
		final String detailId = extractDetailId(gridTabVO);
		//
		final boolean keyColumn = gridFieldVO.isKey();

		int orderBySortNo = gridFieldVO.getSortNo();
		if (orderBySortNo == 0 && keyColumn)
		{
			orderBySortNo = Integer.MAX_VALUE;
		}

		final SqlDocumentFieldDataBindingDescriptor dataBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setSqlTableName(sqlTableName)
				.setSqlTableAlias(sqlTableAlias)
				.setSqlColumnName(gridFieldVO.getColumnName())
				.setSqlColumnSql(gridFieldVO.getColumnSQL(false))
				.setAD_Column_ID(gridFieldVO.getAD_Column_ID()) // legacy
				.setDisplayType(gridFieldVO.getDisplayType())
				.setAD_Reference_Value_ID(gridFieldVO.getAD_Reference_Value_ID())
				.setAD_Val_Rule_ID(gridFieldVO.getAD_Val_Rule_ID())
				.setKeyColumn(keyColumn)
				.setEncrypted(gridFieldVO.isEncryptedColumn())
				.setOrderBy(orderBySortNo)
				.build();

		return DocumentFieldDescriptor.builder()
				.setName(gridFieldVO.getColumnName())
				.setDetailId(detailId)
				//
				.setCaption(gridFieldVO.getHeader())
				.setDescription(gridFieldVO.getDescription())
				//
				.setKey(keyColumn)
				.setWidgetType(extractWidgetType(gridFieldVO))
				.setValueClass(extractValueClass(gridFieldVO))
				//
				.setDefaultValueExpression(extractDefaultValueExpression(gridFieldVO))
				//
				.setReadonlyLogic(extractReadonlyLogic(gridFieldVO))
				.setAlwaysUpdateable(extractAlwaysUpdateable(gridFieldVO))
				.setMandatoryLogic(extractMandatoryLogic(gridFieldVO))
				.setDisplayLogic(extractDisplayLogic(gridFieldVO))
				//
				.setDataBinding(dataBinding);
	}

	private String extractDetailId(final GridTabVO gridTabVO)
	{
		final int tabNo = gridTabVO.getTabNo();
		if (tabNo == MAIN_TabNo)
		{
			return null;
		}
		return String.valueOf(tabNo);
	}

	private String extractParentLinkColumnName(final GridTabVO parentTabVO, final GridTabVO tabVO)
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

	private DocumentFieldWidgetType extractWidgetType(final GridFieldVO gridFieldVO)
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

	private Class<?> extractValueClass(final GridFieldVO gridFieldVO)
	{
		final int displayType = gridFieldVO.getDisplayType();

		//
		//
		if (displayType == DisplayType.List)
		{
			return LookupValue.class;
		}
		else if (displayType == DisplayType.Location)
		{
			return LookupValue.class;
		}
		else if (displayType == DisplayType.PAttribute)
		{
			return LookupValue.class;
		}
		else if (displayType == DisplayType.Table)
		{
			return LookupValue.class;
		}
		else if (DisplayType.isAnyLookup(displayType))
		{
			return LookupValue.class;
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

	private ILogicExpression extractReadonlyLogic(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isReadOnly())
		{
			return ILogicExpression.TRUE;
		}
		
		if (gridFieldVO.isVirtualColumn())
		{
			return ILogicExpression.TRUE;
		}

		final String columnName = gridFieldVO.getColumnName();
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
	
	private boolean extractAlwaysUpdateable(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isVirtualColumn() || !gridFieldVO.isUpdateable())
			return false;
		return gridFieldVO.isAlwaysUpdateable();
	}

	private ILogicExpression extractMandatoryLogic(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isMandatory())
		{
			return ILogicExpression.TRUE;
		}
		return gridFieldVO.getMandatoryLogic();
	}

	private ILogicExpression extractDisplayLogic(final GridFieldVO gridFieldVO)
	{
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
