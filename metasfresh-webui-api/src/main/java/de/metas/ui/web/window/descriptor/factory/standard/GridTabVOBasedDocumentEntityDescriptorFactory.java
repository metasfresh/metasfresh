package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor.Builder;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.ExpressionDocumentFieldCallout;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;

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

/*package */class GridTabVOBasedDocumentEntityDescriptorFactory
{
	// Services
	private static final Logger logger = LogManager.getLogger(GridTabVOBasedDocumentEntityDescriptorFactory.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	private final DocumentsRepository documentsRepository = SqlDocumentsRepository.instance;

	private final Map<Integer, String> _adFieldId2columnName;
	private final DefaultValueExpressionsFactory defaultValueExpressionsFactory;
	private final SpecialDocumentFieldsCollector _specialFieldsCollector;

	//
	// State
	private final Builder _documentEntryBuilder;

	public GridTabVOBasedDocumentEntityDescriptorFactory(final GridTabVO gridTabVO, final GridTabVO parentTabVO, final boolean isSOTrx)
	{
		super();

		final boolean rootEntity = parentTabVO == null;

		_specialFieldsCollector = rootEntity ? new SpecialDocumentFieldsCollector() : null;

		_adFieldId2columnName = gridTabVO.getFields()
				.stream()
				.filter(gridFieldVO -> gridFieldVO.getAD_Field_ID() > 0)
				.collect(Collectors.toMap(GridFieldVO::getAD_Field_ID, GridFieldVO::getColumnName));

		defaultValueExpressionsFactory = new DefaultValueExpressionsFactory(gridTabVO.getTabLevel() > 0);

		//
		// Create initial document entity & field builders
		_documentEntryBuilder = createDocumentEntityBuilder(gridTabVO, parentTabVO, isSOTrx);

		//
		// FIXME: HARDCODED: C_Order's DocumentSummary
		if (rootEntity && I_C_Order.Table_Name.equals(_documentEntryBuilder.getTableName()))
		{
			// final IExpression<?> valueProvider = expressionFactory.compile("@DocumentNo@ @DateOrdered@ @GrandTotal@", IStringExpression.class);
			final IExpression<?> valueProvider = HARDCODED_OrderDocumentSummaryExpression.instance;
			addInternalVirtualField(
					WindowConstants.FIELDNAME_DocumentSummary // fieldName
					, DocumentFieldWidgetType.Text // widgetType
					, String.class // valueType
					, true // publicField
					, valueProvider // valueProvider
			);
		}

	}

	public String getTableName()
	{
		return documentEntity().getTableName();
	}

	public ILogicExpression getTabDisplayLogic()
	{
		return documentEntity().getDisplayLogic();
	}

	public ILogicExpression getTabReadonlyLogic()
	{
		return documentEntity().getReadonlyLogic();
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

	public DocumentEntityDescriptor.Builder documentEntity()
	{
		return _documentEntryBuilder;
	}

	private DocumentEntityDescriptor.Builder createDocumentEntityBuilder(final GridTabVO gridTabVO, final GridTabVO parentTabVO, final boolean isSOTrx)
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
		final ILogicExpression displayLogic = gridTabVO.getDisplayLogic();

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

		return entityDescriptorBuilder;
	}

	public DocumentFieldDescriptor.Builder documentFieldByAD_Field_ID(final int adFieldId)
	{
		final String fieldName = _adFieldId2columnName.get(adFieldId);
		return documentField(fieldName);
	}

	private DocumentFieldDescriptor.Builder documentField(final String fieldName)
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
		final DocumentFieldWidgetType widgetType;
		final Class<?> valueClass;
		final Optional<IExpression<?>> defaultValueExpression;
		final boolean alwaysUpdateable;
		final LookupDescriptorProvider lookupDescriptorProvider;
		final LookupDescriptor lookupDescriptor;
		final ILogicExpression readonlyLogic;

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

			lookupDescriptorProvider = SqlLookupDescriptor.builder()
					.setColumnName(sqlColumnName)
					.setDisplayType(displayType)
					.setAD_Reference_Value_ID(gridFieldVO.getAD_Reference_Value_ID())
					.setAD_Val_Rule_ID(gridFieldVO.getAD_Val_Rule_ID())
					.buildProvider();

			lookupDescriptor = lookupDescriptorProvider.provideForScope(LookupDescriptorProvider.LookupScope.DocumentField);
			valueClass = DescriptorsFactoryHelper.getValueClass(widgetType, lookupDescriptor);

			defaultValueExpression = defaultValueExpressionsFactory.extractDefaultValueExpression(
					gridFieldVO.getDefaultValue() //
					, fieldName  //
					, widgetType //
					, valueClass //
					, gridFieldVO.isMandatory() //
			);
			if (gridFieldVO.isReadOnly())
			{
				readonlyLogic = ConstantLogicExpression.TRUE;
			}
			else
			{
				readonlyLogic = gridFieldVO.getReadOnlyLogic();
			}
		}

		//
		// ORDER BY SortNo
		int orderBySortNo = gridFieldVO.getSortNo();
		if (orderBySortNo == 0 && keyColumn)
		{
			orderBySortNo = Integer.MAX_VALUE;
		}

		final IStringExpression sqlColumnSql = expressionFactory.compile(gridFieldVO.getColumnSQL(false), IStringExpression.class);

		final SqlDocumentFieldDataBindingDescriptor dataBinding = SqlDocumentFieldDataBindingDescriptor.builder()
				.setFieldName(sqlColumnName)
				.setTableName(entityBindings.getTableName())
				.setTableAlias(entityBindings.getTableAlias())
				.setColumnName(sqlColumnName)
				.setColumnSql(sqlColumnSql)
				.setVirtualColumn(gridFieldVO.isVirtualColumn())
				.setMandatory(gridFieldVO.isMandatoryDB())
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setLookupDescriptor(lookupDescriptor)
				.setKeyColumn(keyColumn)
				.setEncrypted(gridFieldVO.isEncryptedColumn())
				.setOrderBy(orderBySortNo)
				.build();

		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(sqlColumnName)
				.setCaption(gridFieldVO.getHeaderTrls(), gridFieldVO.getHeader())
				.setDescription(gridFieldVO.getDescriptionTrls(), gridFieldVO.getDescription())
				//
				.setKey(keyColumn)
				.setParentLink(isParentLinkColumn)
				//
				.setWidgetType(widgetType)
				.setLookupDescriptorProvider(lookupDescriptorProvider)
				.setValueClass(dataBinding.getValueClass())
				.setVirtualField(dataBinding.isVirtualColumn())
				.setCalculated(gridFieldVO.isCalculated())
				//
				.setDefaultValueExpression(defaultValueExpression)
				//
				.addCharacteristicIfTrue(keyColumn, Characteristic.SideListField)
				.addCharacteristicIfTrue(keyColumn, Characteristic.GridViewField)
				.addCharacteristicIfTrue(gridFieldVO.isSelectionColumn(), Characteristic.AllowFiltering)
				//
				.setReadonlyLogic(readonlyLogic)
				.setAlwaysUpdateable(alwaysUpdateable)
				.setMandatoryLogic(gridFieldVO.getMandatoryLogic())
				.setDisplayLogic(gridFieldVO.getDisplayLogic())
				//
				.setDataBinding(dataBinding);

		//
		// Add Field builder to document entity
		entityDescriptor.addField(fieldBuilder);

		//
		// Add Field's data binding to entity data binding
		entityBindings.addField(dataBinding);

		//
		// Collect special field
		collectSpecialField(fieldBuilder);
	}

	public final DocumentFieldDescriptor.Builder addInternalVirtualField(
			final String fieldName //
			, final DocumentFieldWidgetType widgetType //
			, final Class<?> valueClass //
			, final boolean publicField //
			, final IExpression<?> valueProvider //
	)
	{
		final DocumentEntityDescriptor.Builder documentEntity = documentEntity();

		final ExpressionDocumentFieldCallout callout = ExpressionDocumentFieldCallout.of(fieldName, valueProvider);
		final DocumentFieldDescriptor.Builder fieldBuilder = DocumentFieldDescriptor.builder(fieldName)
				//
				.setKey(false)
				.setParentLink(false)
				//
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setVirtualField(true)
				.setCalculated(true)
				//
				// Default value: use our expression
				.setDefaultValueExpression(valueProvider)
				//
				// Characteristics: none, it's an internal field
				.addCharacteristicIfTrue(publicField, Characteristic.PublicField)
				//
				// Logics:
				.setReadonlyLogic(ILogicExpression.TRUE) // yes, always readonly for outside
				.setAlwaysUpdateable(false)
				.setMandatoryLogic(ILogicExpression.FALSE) // not mandatory
				.setDisplayLogic(ILogicExpression.FALSE) // never display it
				//
				.setDataBinding(null) // no databinding !
				//
				.addCallout(callout);

		//
		// Add Field builder to document entity
		documentEntity.addField(fieldBuilder);

		//
		// Add Field's data binding to entity data binding
		// entityBindingsBuilder.addField(dataBinding); // no databinding!

		//
		// Collect special field
		collectSpecialField(fieldBuilder);

		return fieldBuilder;
	}

	/** @return a pair of "child link column name" - "parent link column name" */
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
		if(parentLinkColumnName == null)
		{
			parentLinkColumnName = parentTabVO.getKeyColumnName();
		}
		if(parentLinkColumnName == null)
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
		return gridFieldVO.isAlwaysUpdateable();
	}

	private final void collectSpecialField(final DocumentFieldDescriptor.Builder field)
	{
		if (_specialFieldsCollector == null)
		{
			return;
		}

		_specialFieldsCollector.collect(field);
	}

	public DocumentFieldDescriptor.Builder getSpecialField_DocumentNo()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocumentNo();
	}

	public DocumentFieldDescriptor.Builder getSpecialField_DocumentSummary()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocumentSummary();
	}

	public Map<Characteristic, DocumentFieldDescriptor.Builder> getSpecialField_DocSatusAndDocAction()
	{
		return _specialFieldsCollector == null ? null : _specialFieldsCollector.getDocStatusAndDocAction();
	}

	
	private DocumentEntityDescriptor createQuickInputEntityDescriptor_SalesOrder(DocumentEntityDescriptor.Builder documentDescriptor)
	{
		final DocumentEntityDescriptor.Builder quickInputDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentDescriptor.getDocumentTypeId())
				.disableCallouts()
				// Defaults:
				.setDetailId(null)
				.setAD_Tab_ID(0)
				.setTableName(documentDescriptor.getTableName())
				.setIsSOTrx(true)
				//
				;

		// TODO: implement
		throw new UnsupportedOperationException();
	}
}
