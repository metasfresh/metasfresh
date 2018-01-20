package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.GridFieldDefaultFilterDescriptor;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.i18n.IModelTranslationMap;
import de.metas.logging.LogManager;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.session.WebRestApiContextProvider;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor.ButtonFieldActionType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDefaultFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentFieldValueProvider;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */class GridTabVOBasedDocumentEntityDescriptorFactory
{
	// Services
	private static final Logger logger = LogManager.getLogger(GridTabVOBasedDocumentEntityDescriptorFactory.class);
	private final transient IColumnBL adColumnBL = Services.get(IColumnBL.class);
	private final DocumentsRepository documentsRepository = SqlDocumentsRepository.instance;

	private final Map<Integer, String> _adFieldId2columnName;
	private final DefaultValueExpressionsFactory defaultValueExpressionsFactory;
	private final SpecialDocumentFieldsCollector _specialFieldsCollector;

	//
	// State
	private final DocumentEntityDescriptor.Builder _documentEntryBuilder;

	public GridTabVOBasedDocumentEntityDescriptorFactory(final GridTabVO gridTabVO,
			final GridTabVO parentTabVO,
			final boolean isSOTrx,
			final List<I_AD_UI_Element> labelsUIElements)
	{
		final boolean rootEntity = parentTabVO == null;

		_specialFieldsCollector = rootEntity ? new SpecialDocumentFieldsCollector() : null;

		_adFieldId2columnName = gridTabVO.getFields()
				.stream()
				.filter(gridFieldVO -> gridFieldVO.getAD_Field_ID() > 0)
				.filter(gridFieldVO -> gridFieldVO.getDisplayType() != DocumentFieldWidgetType.BinaryData.getDisplayType()) // exclude BinaryData columns
				.collect(Collectors.toMap(GridFieldVO::getAD_Field_ID, GridFieldVO::getColumnName));

		defaultValueExpressionsFactory = DefaultValueExpressionsFactory.newInstance(gridTabVO.getTableName(), gridTabVO.getTabLevel() > 0);

		//
		// Create initial document entity & field builders
		_documentEntryBuilder = createDocumentEntityBuilder(gridTabVO, parentTabVO, isSOTrx, labelsUIElements);

		//
		// Document summary
		if (rootEntity)
		{
			final IDocumentFieldValueProvider summaryValueProvider = GenericDocumentSummaryValueProvider.of(_documentEntryBuilder);
			if (summaryValueProvider != null)
			{
				addInternalVirtualField(WindowConstants.FIELDNAME_DocumentSummary, DocumentFieldWidgetType.Text, summaryValueProvider);
			}
		}

		//
		collectSpecialFieldsDone();
	}

	public ILogicExpression getTabDisplayLogic()
	{
		return documentEntity().getDisplayLogic();
	}

	private static ILogicExpression extractTabReadonlyLogic(final GridTabVO gridTabVO)
	{
		if (gridTabVO.isView())
		{
			return ConstantLogicExpression.TRUE;
		}

		//
		// Check if tab is always readonly
		if (gridTabVO.isReadOnly())
		{
			return ConstantLogicExpression.TRUE;
		}

		//
		// Check if tab's readonly expression
		final ILogicExpression tabReadonlyLogic = gridTabVO.getReadOnlyLogic();
		if (tabReadonlyLogic.isConstantTrue())
		{
			return ConstantLogicExpression.TRUE;
		}

		return tabReadonlyLogic;
	}

	public DocumentEntityDescriptor.Builder documentEntity()
	{
		return _documentEntryBuilder;
	}

	private DocumentEntityDescriptor.Builder createDocumentEntityBuilder(final GridTabVO gridTabVO,
			final GridTabVO parentTabVO,
			final boolean isSOTrx,
			final List<I_AD_UI_Element> labelsUIElements)
	{
		final String tableName = gridTabVO.getTableName();

		final DetailId detailId = DetailId.fromTabNoOrNull(gridTabVO.getTabNo());

		//
		// Entity Data binding
		if (!Check.isEmpty(gridTabVO.getOrderByClause(), true))
		{
			logger.warn("Ignoring SQL order by for {}. See https://github.com/metasfresh/metasfresh/issues/412.", gridTabVO);
		}
		final SqlDocumentEntityDataBindingDescriptor.Builder dataBinding = SqlDocumentEntityDataBindingDescriptor.builder()
				.setDocumentsRepository(documentsRepository)
				.setTableName(tableName)
				.setTableAliasFromDetailId(detailId)
				.setChildToParentLinkColumnNames(extractChildParentLinkColumnNames(gridTabVO, parentTabVO))
				.setSqlWhereClause(gridTabVO.getWhereClause());

		final ILogicExpression allowInsert = ConstantLogicExpression.of(gridTabVO.isInsertRecord());
		final ILogicExpression allowDelete = ConstantLogicExpression.of(gridTabVO.isDeleteable());
		final ILogicExpression readonlyLogic = extractTabReadonlyLogic(gridTabVO);
		final ILogicExpression allowCreateNewLogic = allowInsert.andNot(readonlyLogic);
		final ILogicExpression allowDeleteLogic = allowDelete.andNot(readonlyLogic);
		//
		final ILogicExpression displayLogic = gridTabVO.getDisplayLogic()
				.evaluatePartial(Evaluatees.mapBuilder()
						.put(WebRestApiContextProvider.CTXNAME_IsWebUI, DisplayType.toBooleanString(true))
						.build());

		//
		// Entity descriptor
		final DocumentEntityDescriptor.Builder entityDescriptorBuilder = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.Window, gridTabVO.getAD_Window_ID())
				.setDetailId(detailId)
				//
				.setCaption(gridTabVO.getNameTrls(), gridTabVO.getName())
				.setDescription(gridTabVO.getDescriptionTrls(), gridTabVO.getDescription())
				//
				.setReadonlyLogic(readonlyLogic)
				.setAllowCreateNewLogic(allowCreateNewLogic)
				.setAllowDeleteLogic(allowDeleteLogic)
				.setDisplayLogic(displayLogic)
				//
				.setDataBinding(dataBinding)
				.setHighVolume(gridTabVO.IsHighVolume)
				//
				.setAD_Tab_ID(gridTabVO.getAD_Tab_ID()) // legacy
				.setTableName(tableName) // legacy
				.setIsSOTrx(isSOTrx) // legacy
				//
				.setPrintAD_Process_ID(gridTabVO.getPrint_Process_ID());

		//
		// Fields descriptor
		gridTabVO
				.getFields()
				.stream()
				.forEach(gridFieldVO -> createAndAddDocumentField(entityDescriptorBuilder, gridFieldVO));

		//
		// Labels field descriptors
		labelsUIElements
				// .stream().filter(uiElement -> X_AD_UI_Element.AD_UI_ELEMENTTYPE_Labels.equals(uiElement.getAD_UI_ElementType())) // assume they are already filtered
				.forEach(labelUIElement -> createAndAddLabelsDocumentField(entityDescriptorBuilder, labelUIElement));

		return entityDescriptorBuilder;
	}

	public DocumentFieldDescriptor.Builder documentFieldByAD_Field_ID(final int adFieldId)
	{
		final String fieldName = _adFieldId2columnName.get(adFieldId);
		return documentField(fieldName);
	}

	DocumentFieldDescriptor.Builder documentField(final String fieldName)
	{
		return documentEntity().getFieldBuilder(fieldName);
	}

	private final void createAndAddDocumentField(final DocumentEntityDescriptor.Builder entityDescriptor, final GridFieldVO gridFieldVO)
	{
		// From entry data-binding:
		final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings = entityDescriptor.getDataBindingBuilder(SqlDocumentEntityDataBindingDescriptor.Builder.class);

		// From GridFieldVO:
		final String fieldName = gridFieldVO.getColumnName();
		final String sqlColumnName = fieldName;
		final boolean keyColumn = gridFieldVO.isKey();

		//
		final boolean isParentLinkColumn = sqlColumnName.equals(entityBindings.getSqlParentLinkColumnName());

		//
		//
		DocumentFieldWidgetType widgetType;
		final Class<?> valueClass;
		final Optional<IExpression<?>> defaultValueExpression;
		final boolean alwaysUpdateable;
		final LookupDescriptorProvider lookupDescriptorProvider;
		final LookupDescriptor lookupDescriptor;
		ILogicExpression readonlyLogic;

		if (isParentLinkColumn)
		{
			widgetType = DocumentFieldWidgetType.Integer;
			valueClass = Integer.class;
			alwaysUpdateable = false;

			lookupDescriptorProvider = LookupDescriptorProvider.NULL;
			lookupDescriptor = null;

			defaultValueExpression = Optional.empty();
			readonlyLogic = ConstantLogicExpression.TRUE;
		}
		else
		{
			final int displayType = gridFieldVO.getDisplayType();
			widgetType = DescriptorsFactoryHelper.extractWidgetType(sqlColumnName, displayType);

			alwaysUpdateable = extractAlwaysUpdateable(gridFieldVO);

			final String ctxTableName = Services.get(IADTableDAO.class).retrieveTableName(gridFieldVO.getAD_Table_ID());

			lookupDescriptorProvider = SqlLookupDescriptor.builder()
					.setCtxTableName(ctxTableName)
					.setCtxColumnName(sqlColumnName)

					.setWidgetType(widgetType)
					.setDisplayType(displayType)
					.setAD_Reference_Value_ID(gridFieldVO.getAD_Reference_Value_ID())
					.setAD_Val_Rule_ID(gridFieldVO.getAD_Val_Rule_ID())
					.buildProvider();

			lookupDescriptor = lookupDescriptorProvider.provideForScope(LookupDescriptorProvider.LookupScope.DocumentField);
			valueClass = DescriptorsFactoryHelper.getValueClass(widgetType, lookupDescriptor);

			defaultValueExpression = defaultValueExpressionsFactory.extractDefaultValueExpression(
					gridFieldVO.getDefaultValue(),
					fieldName,
					widgetType,
					valueClass,
					gridFieldVO.isMandatory(),
					gridFieldVO.isUseDocSequence());

			if (gridFieldVO.isReadOnly())
			{
				readonlyLogic = ConstantLogicExpression.TRUE;
			}
			// Readonly logic in case of not Updateable
			// NOTE: in Swing UI, this property was interpreted as: allow the field to be read-write until it's saved. After that, it's readonly.
			// But here, on Webui we no longer have this concept, since we are auto-saving it.
			// NOTE2: we are checking the AD_Field/AD_Column.IsParentLink and in case is true, we consider the only reason why Updateable=N is because of them.
			// So basically in that case we ignore it.
			//
			// Example where this rule is needed: have a table which has a column which is parent link, but in some windows we are displaying it as header (first tab),
			// and we want to allow the user setting it.
			else if (!gridFieldVO.isUpdateable() && !gridFieldVO.isParentLink())
			{
				readonlyLogic = ConstantLogicExpression.FALSE;
			}
			else
			{
				readonlyLogic = gridFieldVO.getReadOnlyLogic();
			}
		}

		//
		// Button action
		final ButtonFieldActionDescriptor buttonAction;
		if (!isParentLinkColumn && widgetType.isButton())
		{
			buttonAction = extractButtonFieldActionDescriptor(entityDescriptor.getTableNameOrNull(), fieldName, gridFieldVO.AD_Process_ID);
			if (buttonAction != null)
			{
				final ButtonFieldActionType actionType = buttonAction.getActionType();
				if (actionType == ButtonFieldActionType.processCall)
				{
					widgetType = DocumentFieldWidgetType.ProcessButton;
				}
				else if (actionType == ButtonFieldActionType.genericZoomInto)
				{
					widgetType = DocumentFieldWidgetType.ZoomIntoButton;
					readonlyLogic = ConstantLogicExpression.FALSE; // allow pressing the button
				}
			}
		}
		else
		{
			buttonAction = null;
		}

		//
		// ORDER BY SortNo
		int orderBySortNo = gridFieldVO.getSortNo();
		if (orderBySortNo == 0 && keyColumn)
		{
			orderBySortNo = Integer.MAX_VALUE;
		}

		final String sqlColumnSql = gridFieldVO.getColumnSQL(false);

		final SqlDocumentFieldDataBindingDescriptor fieldBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setFieldName(sqlColumnName)
				.setTableName(entityBindings.getTableName())
				.setTableAlias(entityBindings.getTableAlias())
				.setColumnName(sqlColumnName)
				.setColumnSql(sqlColumnSql)
				.setVirtualColumn(gridFieldVO.isVirtualColumn())
				.setMandatory(gridFieldVO.isMandatoryDB())
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setSqlValueClass(entityBindings.getPOInfo().getColumnClass(sqlColumnName))
				.setLookupDescriptor(lookupDescriptor)
				.setKeyColumn(keyColumn)
				.setEncrypted(gridFieldVO.isEncryptedColumn())
				.setDefaultOrderBy(orderBySortNo)
				.build();

		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(sqlColumnName)
				.setCaption(gridFieldVO.getHeaderTrls(), gridFieldVO.getHeader())
				.setDescription(gridFieldVO.getDescriptionTrls(), gridFieldVO.getDescription())
				//
				.setKey(keyColumn)
				.setParentLink(isParentLinkColumn)
				//
				.setWidgetType(widgetType)
				.setButtonActionDescriptor(buttonAction)
				.setLookupDescriptorProvider(lookupDescriptorProvider)
				.setValueClass(fieldBinding.getValueClass())
				.setVirtualField(fieldBinding.isVirtualColumn())
				.setCalculated(gridFieldVO.isCalculated())
				//
				.setDefaultValueExpression(defaultValueExpression)
				//
				.addCharacteristicIfTrue(keyColumn, Characteristic.SideListField)
				.addCharacteristicIfTrue(keyColumn, Characteristic.GridViewField)
				//
				.setReadonlyLogic(readonlyLogic)
				.setAlwaysUpdateable(alwaysUpdateable)
				.setMandatoryLogic(extractMandatoryLogic(gridFieldVO))
				.setDisplayLogic(gridFieldVO.getDisplayLogic())
				//
				.setDefaultFilterInfo(createDefaultFilterDescriptor(gridFieldVO.getDefaultFilterDescriptor(), sqlColumnName, widgetType))
				//
				.setDataBinding(fieldBinding);

		//
		// Add Field builder to document entity
		entityDescriptor.addField(fieldBuilder);

		//
		// Add Field's data binding to entity data binding
		entityBindings.addField(fieldBinding);

		//
		// Collect special field
		collectSpecialField(fieldBuilder);
	}

	private DocumentFieldDefaultFilterDescriptor createDefaultFilterDescriptor(
			final GridFieldDefaultFilterDescriptor gridFieldDefaultFilterInfo,
			final String fieldName,
			final DocumentFieldWidgetType widgetType)
	{
		if (gridFieldDefaultFilterInfo == null)
		{
			return null;
		}

		final Object autoFilterInitialValue = extractAutoFilterInitialValue(gridFieldDefaultFilterInfo, fieldName, widgetType);

		return DocumentFieldDefaultFilterDescriptor.builder()
				.seqNo(gridFieldDefaultFilterInfo.getSeqNo())
				.rangeFilter(gridFieldDefaultFilterInfo.isRangeFilter())
				.showFilterIncrementButtons(gridFieldDefaultFilterInfo.isShowFilterIncrementButtons())
				.autoFilterInitialValue(autoFilterInitialValue)
				.build();
	}

	private Object extractAutoFilterInitialValue(final GridFieldDefaultFilterDescriptor gridFieldDefaultFilterInfo, final String fieldName, final DocumentFieldWidgetType widgetType)
	{
		final String autoFilterInitialValueStr = gridFieldDefaultFilterInfo.getDefaultValue();

		if (Check.isEmpty(autoFilterInitialValueStr, true))
		{
			return null;
		}
		else if (widgetType.isDateOrTime()
				&& DocumentFieldDefaultFilterDescriptor.AUTOFILTER_INITIALVALUE_DATE_NOW.equalsIgnoreCase(autoFilterInitialValueStr.trim()))
		{
			return DocumentFieldDefaultFilterDescriptor.AUTOFILTER_INITIALVALUE_DATE_NOW;
		}
		else if (widgetType.getValueClassOrNull() == null) // no default value class are not supported
		{
			return null;
		}
		else if (widgetType == DocumentFieldWidgetType.Lookup) // lookups are not supported
		{
			return null;
		}
		else if (widgetType == DocumentFieldWidgetType.List)
		{
			return autoFilterInitialValueStr.trim();
		}
		else
		{
			final Class<?> valueClass = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null; // does not matter, we already excluded Lookups above
			return DocumentFieldDescriptor.convertToValueClass(fieldName, autoFilterInitialValueStr, widgetType, valueClass, lookupDataSource);
		}
	}

	private ButtonFieldActionDescriptor extractButtonFieldActionDescriptor(final String tableName, final String fieldName, final int adProcessId)
	{
		// Process call
		if (adProcessId > 0)
		{
			// FIXME: hardcoded, exclude field when considering ProcessButton widget
			// because it's AD_Process_ID it's a placeholder-ish one.
			if (WindowConstants.FIELDNAME_DocAction.equals(fieldName)
					|| WindowConstants.FIELDNAME_Processing.equals(fieldName))
			{
				return null;
			}

			return ButtonFieldActionDescriptor.processCall(ProcessId.ofAD_Process_ID(adProcessId));
			// TODO widgetType = DocumentFieldWidgetType.ProcessButton;
		}

		// Generic ZoomInto button
		if (tableName != null)
		{
			if (adColumnBL.isRecordIdColumnName(fieldName))
			{
				final String zoomIntoTableIdFieldName = adColumnBL.getTableIdColumnName(tableName, fieldName).orElse(null);
				if (zoomIntoTableIdFieldName != null)
				{
					return ButtonFieldActionDescriptor.genericZoomInto(zoomIntoTableIdFieldName);
				}
			}
		}

		//
		return null;
	}

	private final void createAndAddLabelsDocumentField(final DocumentEntityDescriptor.Builder entityDescriptor, final I_AD_UI_Element labelsUIElement)
	{
		final String labelsFieldName = getLabelsFieldName(labelsUIElement);
		final String tablename = entityDescriptor.getTableName().get();
		final LabelsLookup lookupDescriptor = createLabelsLookup(labelsUIElement, tablename);

		final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings = entityDescriptor.getDataBindingBuilder(SqlDocumentEntityDataBindingDescriptor.Builder.class);
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setFieldName(labelsFieldName)
				// .setTableName(entityBindings.getTableName())
				// .setTableAlias(entityBindings.getTableAlias())
				// .setColumnName(sqlColumnName)
				// .setColumnSql(sqlColumnSql)
				.setVirtualColumn(false)
				.setMandatory(false)
				.setWidgetType(DocumentFieldWidgetType.Labels)
				.setValueClass(DocumentFieldWidgetType.Labels.getValueClass())
				.setSqlValueClass(DocumentFieldWidgetType.Labels.getValueClass())
				.setLookupDescriptor(lookupDescriptor)
				.build();
		entityBindings.addField(fieldBinding);

		final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(labelsUIElement);
		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(labelsFieldName)
				.setCaption(trlMap.getColumnTrl(I_AD_UI_Element.COLUMNNAME_Name, labelsUIElement.getName()))
				.setDescription(trlMap.getColumnTrl(I_AD_UI_Element.COLUMNNAME_Description, labelsUIElement.getDescription()))
				//
				.setKey(false)
				.setParentLink(false)
				//
				.setWidgetType(DocumentFieldWidgetType.Labels)
				.setValueClass(fieldBinding.getValueClass())
				.setLookupDescriptorProvider(lookupDescriptor)
				//
				// .setDefaultValueExpression(defaultValueExpression)
				//
				// .addCharacteristicIfTrue(keyColumn, Characteristic.SideListField)
				// .addCharacteristicIfTrue(keyColumn, Characteristic.GridViewField)
				// .addCharacteristicIfTrue(gridFieldVO.isSelectionColumn(), Characteristic.AllowFiltering)
				//
				// .setReadonlyLogic(readonlyLogic)
				// .setAlwaysUpdateable(alwaysUpdateable)
				// .setMandatoryLogic(gridFieldVO.isMandatory() ? ConstantLogicExpression.TRUE : gridFieldVO.getMandatoryLogic())
				// .setDisplayLogic(gridFieldVO.getDisplayLogic())
				//
				.setDefaultFilterInfo(createLabelsDefaultFilterInfo(labelsUIElement))
				.setDataBinding(fieldBinding);

		//
		// Add Field builder to document entity
		entityDescriptor.addField(fieldBuilder);

		//
		// Collect special field
		collectSpecialField(fieldBuilder);
	}

	private static final LabelsLookup createLabelsLookup(final I_AD_UI_Element labelsUIElement, final String tableName)
	{
		final I_AD_Tab labelsTab = labelsUIElement.getLabels_Tab();
		final String labelsTableName = labelsTab.getAD_Table().getTableName();
		final String labelsLinkColumnName;
		if (labelsTab.getAD_Column_ID() > 0)
		{
			labelsLinkColumnName = labelsTab.getAD_Column().getColumnName();
		}
		else
		{
			labelsLinkColumnName = InterfaceWrapperHelper.getKeyColumnName(labelsTableName);
		}

		final I_AD_Column labelsListColumn = labelsUIElement.getLabels_Selector_Field().getAD_Column();
		final String labelsListColumnName = labelsListColumn.getColumnName();
		final int labelsListReferenceId = labelsListColumn.getAD_Reference_Value_ID();

		final String linkColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);

		return LabelsLookup.builder()
				.labelsTableName(labelsTableName)
				.labelsListColumnName(labelsListColumnName)
				.labelsListReferenceId(labelsListReferenceId)
				.labelsLinkColumnName(labelsLinkColumnName)
				.tableName(tableName)
				.linkColumnName(linkColumnName)
				.build();
	}

	public static final String getLabelsFieldName(final I_AD_UI_Element uiElement)
	{
		return "Labels_" + uiElement.getAD_UI_Element_ID();
	}

	private static DocumentFieldDefaultFilterDescriptor createLabelsDefaultFilterInfo(final I_AD_UI_Element labelsUIElement)
	{
		if (!labelsUIElement.isAllowFiltering())
		{
			return null;
		}

		return DocumentFieldDefaultFilterDescriptor.builder()
				.seqNo(Integer.MAX_VALUE)
				.build();
	}

	public final DocumentFieldDescriptor.Builder addInternalVirtualField(
			final String fieldName //
			, final DocumentFieldWidgetType widgetType //
			, final IDocumentFieldValueProvider valueProvider //
	)
	{
		final DocumentEntityDescriptor.Builder documentEntity = documentEntity();

		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(fieldName)
				.setWidgetType(widgetType)
				.setVirtualField(valueProvider)
				.setDisplayLogic(false); // never display it because it's internal

		//
		// Add Field builder to document entity
		documentEntity.addField(fieldBuilder);

		//
		// Collect special field
		collectSpecialField(fieldBuilder);

		return fieldBuilder;
	}

	/**
	 * @return a pair of "child link column name" - "parent link column name"
	 */
	private static final IPair<String, String> extractChildParentLinkColumnNames(final GridTabVO childTabVO, final GridTabVO parentTabVO)
	{
		// If this is the master tab then there is no parent link
		if (parentTabVO == null)
		{
			return null;
		}

		//
		// Find parent's link column name
		final int parentLinkColumnId = childTabVO.getParent_Column_ID();
		String parentLinkColumnName = parentTabVO.getColumnNameByAD_Column_ID(parentLinkColumnId);
		if (parentLinkColumnName == null)
		{
			parentLinkColumnName = parentTabVO.getKeyColumnName();
		}
		if (parentLinkColumnName == null)
		{
			return null;
		}

		//
		// Find child's link column name and then return the pair
		final Set<String> childLinkColumnNames = childTabVO.getLinkColumnNames();
		if (childLinkColumnNames.isEmpty())
		{
			// No child link columns
			return null;
		}
		else if (childLinkColumnNames.size() == 1)
		{
			final String childLinkColumnName = childLinkColumnNames.iterator().next();
			return ImmutablePair.of(childLinkColumnName, parentLinkColumnName);
		}
		else if (childLinkColumnNames.contains(parentLinkColumnName))
		{
			final String childLinkColumnName = parentLinkColumnName;
			return ImmutablePair.of(childLinkColumnName, parentLinkColumnName);
		}
		else
		{
			return null;
		}
	}

	public void addFieldsCharacteristic(final Set<String> fieldNames, final Characteristic characteristic)
	{
		Check.assumeNotNull(characteristic, "Parameter characteristic is not null");
		fieldNames
				.stream()
				.map(fieldName -> documentField(fieldName))
				.forEach(field -> field.addCharacteristic(characteristic));
	}

	private static boolean extractAlwaysUpdateable(final GridFieldVO gridFieldVO)
	{
		if (gridFieldVO.isVirtualColumn() || !gridFieldVO.isUpdateable())
		{
			return false;
		}

		// HARDCODED: DocAction shall always be updateable
		if (WindowConstants.FIELDNAME_DocAction.equals(gridFieldVO.getColumnName()))
		{
			return true;
		}

		return gridFieldVO.isAlwaysUpdateable();
	}

	private static ILogicExpression extractMandatoryLogic(@NonNull final GridFieldVO gridFieldVO)
	{
		return gridFieldVO.isMandatoryLogicExpression() ? gridFieldVO.getMandatoryLogic() : ConstantLogicExpression.of(gridFieldVO.isMandatory());
	}

	private final void collectSpecialField(final DocumentFieldDescriptor.Builder field)
	{
		if (_specialFieldsCollector == null)
		{
			return;
		}

		_specialFieldsCollector.collect(field);
	}

	private final void collectSpecialFieldsDone()
	{
		if (_specialFieldsCollector == null)
		{
			return;
		}

		_specialFieldsCollector.collectFinish();
	}

	public DocumentFieldDescriptor.Builder getSpecialField_DocumentSummary()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocumentSummary();
	}

	public Map<Characteristic, DocumentFieldDescriptor.Builder> getSpecialField_DocSatusAndDocAction()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocStatusAndDocAction();
	}
}
