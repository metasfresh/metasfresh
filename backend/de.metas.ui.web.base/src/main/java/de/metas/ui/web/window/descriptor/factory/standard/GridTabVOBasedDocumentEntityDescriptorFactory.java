package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.service.IColumnBL;
import de.metas.elasticsearch.IESSystem;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.reflist.ReferenceId;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.session.WebRestApiContextProvider;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor.ButtonFieldActionType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDefaultFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Builder;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.IncludedTabNewRecordInputMode;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentFieldValueProvider;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.lookup.TimeZoneLookupDescriptor;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.GridFieldDefaultFilterDescriptor;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.X_AD_UI_ElementField;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.coalesce;

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

	private final ImmutableMap<Integer, String> _adFieldId2columnName;
	private final DefaultValueExpressionsFactory defaultValueExpressionsFactory;
	private final SpecialDocumentFieldsCollector _specialFieldsCollector;

	//
	// State
	private final DocumentEntityDescriptor.Builder _documentEntityBuilder;
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IESSystem esSystem = Services.get(IESSystem.class);

	@lombok.Builder
	private GridTabVOBasedDocumentEntityDescriptorFactory(
			@NonNull final GridTabVO gridTabVO,
			@Nullable final GridTabVO parentTabVO,
			final boolean isSOTrx,
			@NonNull final List<I_AD_UI_Element> labelsUIElements)
	{
		final boolean rootEntity = parentTabVO == null;

		_specialFieldsCollector = rootEntity ? new SpecialDocumentFieldsCollector() : null;

		_adFieldId2columnName = gridTabVO.getFields()
				.stream()
				.filter(gridFieldVO -> gridFieldVO.getAD_Field_ID() > 0)
				.filter(gridFieldVO -> gridFieldVO.getDisplayType() != DocumentFieldWidgetType.BinaryData.getDisplayType()) // exclude BinaryData columns
				.collect(ImmutableMap.toImmutableMap(GridFieldVO::getAD_Field_ID, GridFieldVO::getColumnName));

		defaultValueExpressionsFactory = DefaultValueExpressionsFactory.newInstance(gridTabVO.getTableName(), gridTabVO.getTabLevel() > 0);

		//
		// Create initial document entity & field builders
		_documentEntityBuilder = createDocumentEntityBuilder(gridTabVO, parentTabVO, isSOTrx, labelsUIElements);

		//
		// Document summary
		if (rootEntity)
		{
			final IDocumentFieldValueProvider summaryValueProvider = GenericDocumentSummaryValueProvider.of(_documentEntityBuilder);
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
		return _documentEntityBuilder;
	}

	private DocumentEntityDescriptor.Builder createDocumentEntityBuilder(
			@NonNull final GridTabVO gridTabVO,
			@Nullable final GridTabVO parentTabVO,
			final boolean isSOTrx,
			@NonNull final List<I_AD_UI_Element> labelsUIElements)
	{
		final String tableName = gridTabVO.getTableName();

		final DetailId detailId = parentTabVO == null ? null : DetailId.fromAD_Tab_ID(gridTabVO.getAdTabId());

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
				.setDocumentType(gridTabVO.getAdWindowId())
				.setDetailId(detailId)
				.setInternalName(gridTabVO.getInternalName())
				//
				.setCaption(gridTabVO.getNameTrls())
				.setDescription(gridTabVO.getDescriptionTrls())
				//
				.setReadonlyLogic(readonlyLogic)
				.setAllowCreateNewLogic(allowCreateNewLogic)
				.setAllowDeleteLogic(allowDeleteLogic)
				.setDisplayLogic(displayLogic)
				.setQuickInputSupport(QuickInputSupportDescriptorLoader.extractFrom(gridTabVO))
				.setIncludedTabNewRecordInputMode(IncludedTabNewRecordInputMode.ofNullableCodeOrAllAvailable(gridTabVO.getIncludedTabNewRecordInputMode()))
				.setAutodetectDefaultDateFilter(gridTabVO.isAutodetectDefaultDateFilter())
				//
				.setDataBinding(dataBinding)
				.setHighVolume(gridTabVO.IsHighVolume)
				//
				.setAD_Tab_ID(gridTabVO.getAdTabId().getRepoId()) // legacy
				.setTableName(tableName) // legacy
				.setIsSOTrx(isSOTrx) // legacy
				.setViewPageLength(dataBinding.getPOInfo().getWebuiViewPageLength())
				//
				.setPrintProcessId(gridTabVO.getPrintProcessId())
				//
				.setRefreshViewOnChangeEvents(gridTabVO.isRefreshViewOnChangeEvents());

		// Fields descriptor
		gridTabVO
				.getFields()
				.forEach(gridFieldVO -> createAndAddField_Standard(
						entityDescriptorBuilder,
						gridFieldVO,
						isTreatFieldAsKey(gridFieldVO, gridTabVO)));

		//
		// Labels field descriptors
		labelsUIElements
				// .stream().filter(uiElement -> X_AD_UI_Element.AD_UI_ELEMENTTYPE_Labels.equals(uiElement.getAD_UI_ElementType())) // assume they are already filtered
				.forEach(labelUIElement -> createAndAddField_Labels(entityDescriptorBuilder, labelUIElement));

		return entityDescriptorBuilder;
	}

	// keyColumn==true will mean "readOnly" further down the road
	private static boolean isTreatFieldAsKey(
			@NonNull final GridFieldVO gridFieldVO,
			@NonNull final GridTabVO gridTabVO)
	{
		final boolean gridTabVOHasKeyColumns = gridTabVO.getFields().stream().anyMatch(GridFieldVO::isKey);

		if (gridTabVOHasKeyColumns)
		{
			return gridFieldVO.isKey();
		}
		else
		{
			return gridFieldVO.isParentLink();
		}
	}

	public DocumentFieldDescriptor.Builder documentFieldByAD_Field_ID(final int adFieldId)
	{
		final String fieldName = _adFieldId2columnName.get(adFieldId);
		return documentField(fieldName);
	}

	public DocumentFieldDescriptor.Builder documentFieldByAD_UI_ElementField(@NonNull final I_AD_UI_ElementField elementFieldRecord)
	{
		final Builder builder = documentFieldByAD_Field_ID(elementFieldRecord.getAD_Field_ID());
		if (X_AD_UI_ElementField.TYPE_Tooltip.equals(elementFieldRecord.getType()))
		{
			final String tooltipIconName = Check.assumeNotEmpty(elementFieldRecord.getTooltipIconName(),
					"An elementFieldRecord with type=tooltip needs to have a tooltipIcon; elementFieldRecord={}", elementFieldRecord);
			builder.setTooltipIconName(tooltipIconName);
		}
		return builder;
	}

	DocumentFieldDescriptor.Builder documentField(final String fieldName)
	{
		return documentEntity().getFieldBuilder(fieldName);
	}

	private void createAndAddField_Standard(
			final DocumentEntityDescriptor.Builder entityDescriptorBuilder,
			final GridFieldVO gridFieldVO,
			final boolean keyColumn)
	{
		// From entry data-binding:
		final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings = entityDescriptorBuilder.getDataBindingBuilder(SqlDocumentEntityDataBindingDescriptor.Builder.class);

		// From GridFieldVO:
		//
		DocumentFieldWidgetType widgetType;
		final Class<?> valueClass;
		final Optional<IExpression<?>> defaultValueExpression;
		final boolean alwaysUpdateable;
		final LookupDescriptorProvider lookupDescriptorProvider;
		final Optional<LookupDescriptor> lookupDescriptor;
		ILogicExpression readonlyLogic;

		final boolean isParentLinkColumn = isCurrentlyUsedParentLinkField(gridFieldVO, entityDescriptorBuilder);
		final String sqlColumnName = gridFieldVO.getColumnName();

		if (isParentLinkColumn) // assumes that the column is not only flagged as parent link, but is also the parent link *in this particular document*
		{
			widgetType = DocumentFieldWidgetType.Integer;
			valueClass = widgetType.getValueClass();
			alwaysUpdateable = false;

			lookupDescriptorProvider = LookupDescriptorProviders.NULL;
			lookupDescriptor = Optional.empty();

			defaultValueExpression = Optional.empty();
			readonlyLogic = ConstantLogicExpression.TRUE;
		}
		else if (gridFieldVO.isKey()) // single key column
		{
			widgetType = DocumentFieldWidgetType.Integer;
			valueClass = widgetType.getValueClass();
			alwaysUpdateable = false;

			lookupDescriptorProvider = LookupDescriptorProviders.NULL;
			lookupDescriptor = Optional.empty();

			defaultValueExpression = Optional.empty();
			readonlyLogic = ConstantLogicExpression.TRUE;
		}
		else
		{
			if (WindowConstants.FIELDNAME_TimeZone.contentEquals(sqlColumnName))
			{
				lookupDescriptorProvider = TimeZoneLookupDescriptor.provider;
				widgetType = DocumentFieldWidgetType.Lookup;
			}
			else
			{
				final int displayType = gridFieldVO.getDisplayType();
				widgetType = DescriptorsFactoryHelper.extractWidgetType(sqlColumnName, displayType);
				final String ctxTableName = tableDAO.retrieveTableName(gridFieldVO.getAD_Table_ID());
				lookupDescriptorProvider = wrapFullTextSeachFilterDescriptorProvider(
						SqlLookupDescriptor.builder()
								.setCtxTableName(ctxTableName)
								.setCtxColumnName(sqlColumnName)
								.setWidgetType(widgetType)
								.setDisplayType(displayType)
								.setAD_Reference_Value_ID(gridFieldVO.getAD_Reference_Value_ID())
								.setAD_Val_Rule_ID(gridFieldVO.getAD_Val_Rule_ID())
								.buildProvider());
			}

			lookupDescriptor = lookupDescriptorProvider.provide();
			valueClass = DescriptorsFactoryHelper.getValueClass(widgetType, lookupDescriptor);

			defaultValueExpression = defaultValueExpressionsFactory.extractDefaultValueExpression(
					gridFieldVO.getDefaultValue(),
					sqlColumnName,
					widgetType,
					valueClass,
					gridFieldVO.isMandatory(),
					gridFieldVO.isUseDocSequence());

			final OptionalBoolean tabAllowsCreateNew = entityDescriptorBuilder.getAllowCreateNewLogic().toOptionalBoolean();
			readonlyLogic = extractReadOnlyLogic(gridFieldVO, keyColumn, isParentLinkColumn, tabAllowsCreateNew);
			alwaysUpdateable = extractAlwaysUpdateable(gridFieldVO);
		}

		//
		// Button action
		final ButtonFieldActionDescriptor buttonAction;
		if (!isParentLinkColumn && widgetType.isButton())
		{
			buttonAction = extractButtonFieldActionDescriptor(
					entityDescriptorBuilder.getTableNameOrNull(),
					sqlColumnName,
					gridFieldVO.AD_Process_ID);
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

		final SqlDocumentFieldDataBindingDescriptor fieldBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setFieldName(sqlColumnName)
				.setTableName(entityBindings.getTableName())
				.setTableAlias(entityBindings.getTableAlias())
				.setColumnName(sqlColumnName)
				.setVirtualColumnSql(extractVirtualColumnSql(gridFieldVO, entityBindings.getTableName()))
				.setMandatory(gridFieldVO.isMandatoryDB())
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setSqlValueClass(entityBindings.getPOInfo().getColumnClass(sqlColumnName))
				.setLookupDescriptor(lookupDescriptor.orElse(null))
				.setKeyColumn(keyColumn)
				.setEncrypted(gridFieldVO.isEncryptedColumn())
				.setDefaultOrderBy(orderBySortNo)
				.build();

		final String parentLinkFieldName = isParentLinkColumn ? entityBindings.getSqlParentLinkColumnName() : null;
		final int fieldMaxLength = widgetType.isStrictText() && gridFieldVO.getFieldLength() > 0 ? gridFieldVO.getFieldLength() : 0;

		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(sqlColumnName)
				.setCaption(gridFieldVO.getHeaderTrls(), gridFieldVO.getHeader())
				.setDescription(gridFieldVO.getDescriptionTrls(), gridFieldVO.getDescription())
				//
				.setKey(keyColumn)
				.setParentLink(isParentLinkColumn, parentLinkFieldName)
				//
				.setWidgetType(widgetType)
				.setFieldMaxLength(fieldMaxLength)
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
				.setDefaultFilterInfo(createDefaultFilterDescriptor(gridFieldVO.getDefaultFilterDescriptor(), sqlColumnName, widgetType, fieldBinding.getValueClass(), lookupDescriptorProvider))
				//
				.setDataBinding(fieldBinding);

		//
		// Add Field builder to document entity
		entityDescriptorBuilder.addField(fieldBuilder);

		//
		// Add Field's data binding to entity data binding
		entityBindings.addField(fieldBinding);

		//
		// Collect special field
		collectSpecialField(fieldBuilder);
	}

	@Nullable
	private static ColumnSql extractVirtualColumnSql(final GridFieldVO gridFieldVO, final String contextTableName)
	{
		if(gridFieldVO.isVirtualColumn())
		{
			return gridFieldVO.getColumnSql(contextTableName);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @return true if the given {@code gridFieldVO} is flagged as parent link and also matches the parent-link columName.
	 * Logically there can be only one parent link field.
	 */
	private static boolean isCurrentlyUsedParentLinkField(
			@NonNull final GridFieldVO gridFieldVO,
			@NonNull final DocumentEntityDescriptor.Builder entityDescriptorBuilder)
	{
		// issue https://github.com/metasfresh/metasfresh/issues/4622 :
		// Even if it's not flagged as parent-link in AD_Column, it can be configured that way in AD_Tab
		// if (!gridFieldVO.isParentLink())
		// {
		// return false;
		// }

		final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings = entityDescriptorBuilder.getDataBindingBuilder(SqlDocumentEntityDataBindingDescriptor.Builder.class);
		final String parentLinkColumnName = entityBindings.getSqlParentLinkColumnName();

		// if there is a parent link column, only the respective gridFieldVO is a key
		return Objects.equals(gridFieldVO.getColumnName(), parentLinkColumnName);
	}

	private LookupDescriptorProvider wrapFullTextSeachFilterDescriptorProvider(@NonNull final LookupDescriptorProvider databaseLookupDescriptorProvider)
	{
		final String modelTableName = databaseLookupDescriptorProvider.getTableName().orElse(null);
		if (modelTableName == null)
		{
			return databaseLookupDescriptorProvider;
		}

		if (esSystem.getEnabled().isFalse())
		{
			return databaseLookupDescriptorProvider;
		}

		// TODO: implement
		return databaseLookupDescriptorProvider;
		// final ESModelIndexersRegistry esModelIndexersRegistry = SpringContextHolder.instance.getBean(ESModelIndexersRegistry.class);
		// final ESModelIndexer modelIndexer = esModelIndexersRegistry.getFullTextSearchModelIndexer(modelTableName)
		// 		.orElse(null);
		// if (modelIndexer == null)
		// {
		// 	return databaseLookupDescriptorProvider;
		// }
		//
		// final Client elasticsearchClient = Adempiere.getBean(org.elasticsearch.client.Client.class);
		//
		// return FullTextSearchLookupDescriptorProvider.builder()
		// 		.elasticsearchClient(elasticsearchClient)
		// 		.modelTableName(modelIndexer.getModelTableName())
		// 		.esIndexName(modelIndexer.getIndexName())
		// 		.esSearchFieldNames(modelIndexer.getFullTextSearchFieldNames())
		// 		.databaseLookupDescriptorProvider(databaseLookupDescriptorProvider)
		// 		.build();
	}

	private static ILogicExpression extractReadOnlyLogic(final GridFieldVO gridFieldVO, final boolean keyColumn, final boolean isParentLinkColumn, final OptionalBoolean tabAllowsCreateNew)
	{
		if (keyColumn)
		{
			return ConstantLogicExpression.TRUE;
		}
		else if (gridFieldVO.isVirtualColumn())
		{
			return ConstantLogicExpression.TRUE;
		}
		//
		// Readonly logic in case of parent link column which is not parent link in this window.
		// NOTE: in SwingUI/application dictionary, in case a column is flagged as ParentLink it is automatically flagged as IsUpdateable=N.
		// So, here we are identifying this case and consider it as editable.
		// e.g. BPartner (pharma) window -> Product tab
		else if (!gridFieldVO.isUpdateable()
				&& gridFieldVO.isParentLink() && !isParentLinkColumn
				&& gridFieldVO.isMandatory()
				&& tabAllowsCreateNew.isTrue())
		{
			return ConstantLogicExpression.FALSE;
		}
		else if (gridFieldVO.isReadOnly())
		{
			return ConstantLogicExpression.TRUE;
		}
		//
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
			return ConstantLogicExpression.FALSE;
		}
		else
		{
			return gridFieldVO.getReadOnlyLogic();
		}
	}

	@Nullable
	private DocumentFieldDefaultFilterDescriptor createDefaultFilterDescriptor(
			@Nullable final GridFieldDefaultFilterDescriptor gridFieldDefaultFilterInfo,
			@NonNull final String fieldName,
			@NonNull final DocumentFieldWidgetType widgetType,
			@NonNull final Class<?> valueClass,
			@Nullable final LookupDescriptorProvider lookupDescriptorProvider)
	{
		if (gridFieldDefaultFilterInfo == null)
		{
			return null;
		}

		final Object autoFilterInitialValue = extractAutoFilterInitialValue(gridFieldDefaultFilterInfo, fieldName, widgetType, valueClass, lookupDescriptorProvider);

		return DocumentFieldDefaultFilterDescriptor.builder()
				//
				.defaultFilter(gridFieldDefaultFilterInfo.isDefaultFilter())
				.defaultFilterSeqNo(gridFieldDefaultFilterInfo.getDefaultFilterSeqNo())
				.operator(DocumentFieldDefaultFilterDescriptor.FilterOperator.ofNullableStringOrEquals(gridFieldDefaultFilterInfo.getOperator()))
				.showFilterIncrementButtons(gridFieldDefaultFilterInfo.isShowFilterIncrementButtons())
				.autoFilterInitialValue(autoFilterInitialValue)
				.showFilterInline(gridFieldDefaultFilterInfo.isShowFilterInline())
				//
				.facetFilter(gridFieldDefaultFilterInfo.isFacetFilter())
				.facetFilterSeqNo(gridFieldDefaultFilterInfo.getFacetFilterSeqNo())
				.maxFacetsToFetch(gridFieldDefaultFilterInfo.getMaxFacetsToFetch())
				//
				.build();
	}

	@Nullable
	private Object extractAutoFilterInitialValue(
			@NonNull final GridFieldDefaultFilterDescriptor gridFieldDefaultFilterInfo,
			@NonNull final String fieldName,
			@NonNull final DocumentFieldWidgetType widgetType,
			@NonNull final Class<?> valueClass,
			@Nullable final LookupDescriptorProvider lookupDescriptorProvider)
	{
		final String autoFilterInitialValueStr = gridFieldDefaultFilterInfo.getDefaultValue();

		if (Check.isBlank(autoFilterInitialValueStr))
		{
			return null;
		}
		else if (widgetType.isDateOrTime()
				&& DocumentFilterParamDescriptor.AUTOFILTER_INITIALVALUE_DATE_NOW.equalsIgnoreCase(autoFilterInitialValueStr.trim()))
		{
			return DocumentFilterParamDescriptor.AUTOFILTER_INITIALVALUE_DATE_NOW;
		}
		else if (widgetType.isLookup()
				&& DocumentFilterParamDescriptor.AUTOFILTER_INITIALVALUE_CURRENT_LOGGED_USER.equalsIgnoreCase(autoFilterInitialValueStr.trim()))
		{
			return DocumentFilterParamDescriptor.AUTOFILTER_INITIALVALUE_CURRENT_LOGGED_USER;
		}
		else
		{
			final LookupDataSource lookupDataSource = widgetType.isLookup()
					? createFilterLookupDataSourceOrNull(lookupDescriptorProvider)
					: null;
			return DataTypes.convertToValueClass(fieldName, autoFilterInitialValueStr, widgetType, valueClass, lookupDataSource);
		}
	}

	@Nullable
	private LookupDataSource createFilterLookupDataSourceOrNull(@Nullable final LookupDescriptorProvider lookupDescriptorProvider)
	{
		if (lookupDescriptorProvider == null)
		{
			return null;
		}

		final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.provideForFilter().orElse(null);
		if (lookupDescriptor == null)
		{
			return null;
		}

		return LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);
	}

	@Nullable
	private ButtonFieldActionDescriptor extractButtonFieldActionDescriptor(final String tableName, final String fieldName, final int adProcessId)
	{
		// Process call
		if (adProcessId > 0)
		{
			// FIXME: hardcoded, exclude field when considering ProcessButton widget
			// because it's AD_Process_ID it's a placeholder-ish one.
			if (
				//WindowConstants.FIELDNAME_DocAction.equals(fieldName) ||
					WindowConstants.FIELDNAME_Processing.equals(fieldName))
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

	private void createAndAddField_Labels(final DocumentEntityDescriptor.Builder entityDescriptor, final I_AD_UI_Element labelsUIElement)
	{
		final String tableName = entityDescriptor.getTableName().orElseThrow(() -> new AdempiereException("No tableName defined for " + entityDescriptor));
		final LabelsLookup lookupDescriptor = createLabelsLookup(labelsUIElement, tableName);

		final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings = entityDescriptor.getDataBindingBuilder(SqlDocumentEntityDataBindingDescriptor.Builder.class);
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setFieldName(lookupDescriptor.getFieldName())
				.setTableName(entityBindings.getTableName())
				.setTableAlias(entityBindings.getTableAlias())
				.setColumnName(lookupDescriptor.getFieldName())
				.setVirtualColumnSql(lookupDescriptor.getSqlForFetchingValueIdsByLinkId(tableName))
				.setMandatory(false)
				.setWidgetType(DocumentFieldWidgetType.Labels)
				.setValueClass(DocumentFieldWidgetType.Labels.getValueClass())
				.setSqlValueClass(DocumentFieldWidgetType.Labels.getValueClass())
				.setLookupDescriptor(lookupDescriptor)
				.build();
		entityBindings.addField(fieldBinding);

		final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(labelsUIElement);

		final ITranslatableString caption = coalesce(getLabelFieldCaptionByName(labelsUIElement),
				trlMap.getColumnTrl(I_AD_UI_Element.COLUMNNAME_Name, labelsUIElement.getName())
		);

		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(lookupDescriptor.getFieldName())
				.setCaption(caption)
				.setDescription(trlMap.getColumnTrl(I_AD_UI_Element.COLUMNNAME_Description, labelsUIElement.getDescription()))
				.setKey(fieldBinding.isKeyColumn())
				.setWidgetType(fieldBinding.getWidgetType())
				.setValueClass(fieldBinding.getValueClass())
				.setLookupDescriptorProvider(lookupDescriptor)
				.setReadonlyLogic(false)
				.setDisplayLogic(extractLabelDisplayLogic(labelsUIElement))
				.setVirtualField(fieldBinding.isVirtualColumn())
				.setDefaultFilterInfo(createLabelsDefaultFilterInfo(labelsUIElement))
				.setDataBinding(fieldBinding);

		//
		// Add Field builder to document entity
		entityDescriptor.addField(fieldBuilder);

		//
		// Collect special field
		collectSpecialField(fieldBuilder);
	}

	@Nullable
	private ITranslatableString getLabelFieldCaptionByName(final I_AD_UI_Element labelsUIElement)
	{
		if (labelsUIElement.getAD_Name_ID() <= 0)
		{
			return null;
		}
		final I_AD_Element adElement = InterfaceWrapperHelper.load(labelsUIElement.getAD_Name_ID(), I_AD_Element.class);
		final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(adElement);
		return trlMap.getColumnTrl(I_AD_Element.COLUMNNAME_Name, adElement.getName());
	}

	private static ILogicExpression extractLabelDisplayLogic(final I_AD_UI_Element labelsUIElement)
	{
		final I_AD_Field labelField = labelsUIElement.getLabels_Selector_Field();
		if (labelField == null)
		{
			throw new AdempiereException(I_AD_UI_Element.COLUMNNAME_Labels_Selector_Field_ID + " shall not be null for " + labelsUIElement);
		}

		return StringUtils.trimBlankToOptional(labelField.getDisplayLogic())
				.map(LogicExpressionCompiler.instance::compile)
				.orElse(ConstantLogicExpression.TRUE);
	}

	private static LabelsLookup createLabelsLookup(
			@NonNull final I_AD_UI_Element labelsUIElement,
			@NonNull final String tableName)
	{
		final I_AD_Tab labelsTab = labelsUIElement.getLabels_Tab();
		final AdTableId adTableId = AdTableId.ofRepoId(labelsTab.getAD_Table_ID());
		final String labelsTableName = TableIdsCache.instance.getTableName(adTableId);

		final String linkColumnName;
		if (labelsTab.getParent_Column_ID() > 0)
		{
			linkColumnName = labelsTab.getParent_Column().getColumnName();
		}
		else
		{
			linkColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		}

		final String labelsLinkColumnName;
		if (labelsTab.getAD_Column_ID() > 0)
		{
			labelsLinkColumnName = labelsTab.getAD_Column().getColumnName();
		}
		else
		{
			labelsLinkColumnName = linkColumnName;
		}

		final I_AD_Field labelsSelectorField = labelsUIElement.getLabels_Selector_Field();

		final I_AD_Column labelsValueColumn = labelsSelectorField.getAD_Column();
		final String labelsValueColumnName = labelsValueColumn.getColumnName();

		final int referenceIDToUse = labelsSelectorField.getAD_Reference_ID() > 0
				? labelsSelectorField.getAD_Reference_ID()
				: labelsValueColumn.getAD_Reference_ID();

		final int referenceValueIDToUse = labelsSelectorField.getAD_Reference_Value_ID() > 0
				? labelsSelectorField.getAD_Reference_Value_ID()
				: labelsValueColumn.getAD_Reference_Value_ID();

		final int valRuleIDToUse = labelsSelectorField.getAD_Val_Rule_ID() > 0
				? labelsSelectorField.getAD_Val_Rule_ID()
				: labelsValueColumn.getAD_Val_Rule_ID();

		final SqlLookupDescriptor.Builder labelsValuesLookupDescriptorBuilder = SqlLookupDescriptor.builder()
				.setCtxTableName(labelsTableName)
				.setCtxColumnName(labelsValueColumnName)
				.setDisplayType(referenceIDToUse)
				.setWidgetType(DescriptorsFactoryHelper.extractWidgetType(labelsValueColumnName, referenceIDToUse))
				.setAD_Reference_Value_ID(referenceValueIDToUse)
				.setAD_Val_Rule_ID(valRuleIDToUse);

		if (Check.isNotBlank(labelsTab.getWhereClause()))
		{
			final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
			labelsValuesLookupDescriptorBuilder.addValidationRule(validationRuleFactory.createSQLValidationRule(labelsTab.getWhereClause()));
		}

		final LookupDescriptor labelsValuesLookupDescriptor = labelsValuesLookupDescriptorBuilder.buildForDefaultScope();

		return LabelsLookup.builder()
				.fieldName(extractLabelsFieldName(labelsUIElement))
				.labelsTableName(labelsTableName)
				.labelsValueColumnName(labelsValueColumnName)
				.labelsValuesLookupDescriptor(labelsValuesLookupDescriptor)
				.labelsLinkColumnName(labelsLinkColumnName)
				.labelsValueReferenceId(ReferenceId.ofRepoIdOrNull(referenceValueIDToUse))
				.tableName(tableName)
				.linkColumnName(linkColumnName)
				.build();
	}

	@NonNull
	public static String extractLabelsFieldName(final @NonNull I_AD_UI_Element labelsUIElement)
	{
		return "Labels_" + labelsUIElement.getAD_UI_Element_ID();
	}

	@Nullable
	private static DocumentFieldDefaultFilterDescriptor createLabelsDefaultFilterInfo(final I_AD_UI_Element labelsUIElement)
	{
		if (!labelsUIElement.isAllowFiltering())
		{
			return null;
		}

		return DocumentFieldDefaultFilterDescriptor.builder()
				.seqNo(Integer.MAX_VALUE)
				.defaultFilter(true)
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
	@Nullable
	private static IPair<String, String> extractChildParentLinkColumnNames(final GridTabVO childTabVO, @Nullable final GridTabVO parentTabVO)
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
			return ImmutablePair.of(parentLinkColumnName, parentLinkColumnName);
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
				.map(this::documentField)
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
		if (gridFieldVO.isMandatoryLogicExpression())
		{
			return gridFieldVO.getMandatoryLogic();
		}
		else if (gridFieldVO.isMandatory())
		{
			// consider it mandatory only if is displayed
			return gridFieldVO.getDisplayLogic();
		}
		else
		{
			return ConstantLogicExpression.FALSE;
		}
	}

	private void collectSpecialField(final DocumentFieldDescriptor.Builder field)
	{
		if (_specialFieldsCollector == null)
		{
			return;
		}

		_specialFieldsCollector.collect(field);
	}

	private void collectSpecialFieldsDone()
	{
		if (_specialFieldsCollector == null)
		{
			return;
		}

		_specialFieldsCollector.collectFinish();
	}

	@Nullable
	public DocumentFieldDescriptor.Builder getSpecialField_DocumentSummary()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocumentSummary();
	}

	@Nullable
	public Map<Characteristic, DocumentFieldDescriptor.Builder> getSpecialField_DocSatusAndDocAction()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocStatusAndDocAction();
	}
}
