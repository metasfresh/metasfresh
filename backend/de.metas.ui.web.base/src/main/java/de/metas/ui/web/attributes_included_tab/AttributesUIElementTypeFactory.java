package de.metas.ui.web.attributes_included_tab;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptor;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptorService;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabFieldDescriptor;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabId;
import de.metas.logging.LogManager;
import de.metas.ui.web.pattribute.ASILookupDescriptor;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.impl.AttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_Record_Attribute;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AttributesUIElementTypeFactory
{
	//
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(AttributesUIElementTypeFactory.class);
	@NonNull private final AttributesIncludedTabDescriptorService attributesIncludedTabDescriptorService;

	//
	// Params
	@NonNull private final ImmutableList<AttributesUIElement> attributesUIElements;

	//
	// State
	@NonNull private final AtomicBoolean executed = new AtomicBoolean();
	@NonNull private final ArrayListMultimap<AdUIElementId, String> generatedFieldNamesByUIElementId = ArrayListMultimap.create();

	@Builder
	private AttributesUIElementTypeFactory(
			@NonNull final AttributesIncludedTabDescriptorService attributesIncludedTabDescriptorService,
			@NonNull final List<I_AD_UI_Element> attributesUIElements)
	{
		this.attributesIncludedTabDescriptorService = attributesIncludedTabDescriptorService;
		this.attributesUIElements = toAttributesUIElement(attributesUIElements);
	}

	private static ImmutableList<AttributesUIElement> toAttributesUIElement(final List<I_AD_UI_Element> attributesUIElementRecords)
	{
		final ImmutableList.Builder<AttributesUIElement> result = ImmutableList.builder();

		for (final I_AD_UI_Element attributesUIElementRecord : attributesUIElementRecords)
		{
			final AdUIElementId uiElementId = AdUIElementId.ofRepoId(attributesUIElementRecord.getAD_UI_Element_ID());

			final AdFieldId attributesProviderFieldId = AdFieldId.ofRepoIdOrNull(attributesUIElementRecord.getAD_Field_ID());
			if (attributesProviderFieldId == null)
			{
				logger.warn("Skip Attributes UI element because AD_Field_ID is not set: {}", attributesUIElementRecord);
				continue;
			}

			result.add(
					AttributesUIElement.builder()
							.id(uiElementId)
							.attributesProviderFieldId(attributesProviderFieldId)
							.build()
			);
		}

		return result.build();
	}

	public void createAndAddDocumentFieldDescriptors(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		if (executed.getAndSet(true))
		{
			throw new AdempiereException("Already executed");
		}

		if (attributesUIElements.isEmpty())
		{
			return;
		}

		final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings = entityDescriptor.getDataBindingBuilder(SqlDocumentEntityDataBindingDescriptor.Builder.class);

		for (final AttributesUIElement attributesUIElement : attributesUIElements)
		{
			final DocumentFieldDescriptor.Builder attributesProviderField = entityDescriptor.getFieldBuilder(attributesUIElement.getAttributesProviderFieldId());
			if (attributesProviderField == null)
			{
				logger.warn("Skip Attributes UI element because attributesProviderField was not found: {}", attributesUIElement);
				continue;
			}

			for (final AttributesIncludedTabDescriptor attributesIncludedTabDescriptor : getAttributesIncludedTabDescriptor(attributesProviderField))
			{
				for (final AttributesIncludedTabFieldDescriptor attributesIncludedTabFieldDescriptor : attributesIncludedTabDescriptor.getFieldsInOrder())
				{
					final SqlDocumentFieldDataBindingDescriptor fieldBinding = createFieldBinding(
							attributesIncludedTabFieldDescriptor,
							attributesIncludedTabDescriptor.getId(),
							entityBindings,
							attributesProviderField.getFieldName());
					entityBindings.addField(fieldBinding);

					final DocumentFieldDescriptor.Builder fieldBuilder = createField(fieldBinding, attributesIncludedTabFieldDescriptor);
					entityDescriptor.addField(fieldBuilder);

					generatedFieldNamesByUIElementId.put(attributesUIElement.getId(), fieldBuilder.getFieldName());
				}
			}
		}
	}

	private List<AttributesIncludedTabDescriptor> getAttributesIncludedTabDescriptor(final DocumentFieldDescriptor.Builder attributesProviderField)
	{
		final LookupDescriptor attributesProviderLookupDescriptor = attributesProviderField.getLookupDescriptor().orElse(null);
		if (attributesProviderLookupDescriptor == null)
		{
			throw new AdempiereException("No lookup descriptor found for " + attributesProviderField.getFieldName());
		}

		final String referencedTableName = attributesProviderLookupDescriptor.getTableName().orElse(null);
		if (referencedTableName == null)
		{
			throw new AdempiereException("No referenced table found for " + attributesProviderField.getFieldName())
					.setParameter("lookup", attributesProviderLookupDescriptor);
		}
		final AdTableId referencedTableId = TableIdsCache.instance.getTableIdNotNull(referencedTableName);

		return attributesIncludedTabDescriptorService.listBy(referencedTableId);
	}

	private SqlDocumentFieldDataBindingDescriptor createFieldBinding(
			@NonNull final AttributesIncludedTabFieldDescriptor attributeFieldDescriptor,
			@NonNull final AttributesIncludedTabId attributesTabDescriptorId,
			@NonNull final SqlDocumentEntityDataBindingDescriptor.Builder entityBindings,
			@NonNull final String joinFieldName)
	{
		final SqlDocumentFieldDataBindingDescriptor joinField = entityBindings.getField(joinFieldName);
		final LookupDescriptor joinFieldLookup = Check.assumeNotNull(joinField.getLookupDescriptor(), "No lookup descriptor found for {}", joinFieldName);
		final String joinFieldReferencedTableName = joinFieldLookup.getTableName().orElseThrow(() -> new AdempiereException("No referenced table found for " + joinField.getFieldName()));
		final AdTableId joinFieldReferencedTableId = TableIdsCache.instance.getTableIdNotNull(joinFieldReferencedTableName);

		final AttributeValueType attributeValueType = attributeFieldDescriptor.getAttributeValueType();

		final DocumentFieldWidgetType widgetType;
		final Class<?> valueClass;
		final Class<?> sqlValueClass;
		final String attributeValueColumnName;
		final LookupDescriptor lookupDescriptor;

		if (AttributeValueType.DATE.equals(attributeValueType))
		{
			valueClass = LocalDate.class;
			sqlValueClass = java.sql.Timestamp.class;
			widgetType = DocumentFieldWidgetType.LocalDate;
			attributeValueColumnName = I_Record_Attribute.COLUMNNAME_ValueDate;
			lookupDescriptor = null;
		}
		else if (AttributeValueType.LIST.equals(attributeValueType))
		{
			valueClass = LookupValue.StringLookupValue.class;
			sqlValueClass = String.class;
			widgetType = DocumentFieldWidgetType.List;
			attributeValueColumnName = I_Record_Attribute.COLUMNNAME_ValueString;
			final IAttributeValuesProvider attributeValuesProvider = attributesIncludedTabDescriptorService.createAttributeValuesProvider(attributeFieldDescriptor.getAttributeId());
			lookupDescriptor = attributeValuesProvider != null ? ASILookupDescriptor.of(attributeValuesProvider) : null;
		}
		else if (AttributeValueType.NUMBER.equals(attributeValueType))
		{
			attributeValueColumnName = I_Record_Attribute.COLUMNNAME_ValueNumber;
			lookupDescriptor = null;
			if (AttributesBL.isInteger(attributeFieldDescriptor.getUomId()))
			{
				valueClass = Integer.class;
				sqlValueClass = Integer.class;
				widgetType = DocumentFieldWidgetType.Integer;
			}
			else
			{
				valueClass = BigDecimal.class;
				sqlValueClass = BigDecimal.class;
				widgetType = DocumentFieldWidgetType.Number;
			}
		}
		else if (AttributeValueType.STRING.equals(attributeValueType))
		{
			valueClass = String.class;
			sqlValueClass = String.class;
			widgetType = DocumentFieldWidgetType.Text;
			attributeValueColumnName = I_Record_Attribute.COLUMNNAME_ValueString;
			lookupDescriptor = null;
		}
		else
		{
			throw new AdempiereException("Unknown attribute type " + attributeValueType);
		}

		final String fieldName = joinFieldName + "$" + attributesTabDescriptorId.getRepoId() + "$" + attributeFieldDescriptor.getAttributeId().getRepoId();

		return SqlDocumentFieldDataBindingDescriptor.builder()
				.setFieldName(fieldName)
				.setTableName(entityBindings.getTableName())
				.setTableAlias(entityBindings.getTableAlias())
				.setColumnName(fieldName)
				.setVirtualColumnSql(ColumnSql.ofSql(
						"SELECT ra." + attributeValueColumnName
								+ " FROM " + I_Record_Attribute.Table_Name + " ra"
								+ " WHERE ra." + I_Record_Attribute.COLUMNNAME_AD_Table_ID + "=" + joinFieldReferencedTableId.getRepoId()
								+ " AND ra." + I_Record_Attribute.COLUMNNAME_Record_ID + "=(" + joinField.getSqlSelectValue().withJoinOnTableNameOrAlias(entityBindings.getTableName()).toSqlString() + ")"
								+ " AND ra." + I_Record_Attribute.COLUMNNAME_M_AttributeSet_IncludedTab_ID + "=" + attributesTabDescriptorId.getRepoId()
								+ " AND ra." + I_Record_Attribute.COLUMNNAME_M_Attribute_ID + "=" + attributeFieldDescriptor.getAttributeId().getRepoId(),
						entityBindings.getTableName()
				))
				.setMandatory(false)
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				.setSqlValueClass(sqlValueClass)
				.setLookupDescriptor(lookupDescriptor)
				.setHideGridColumnIfEmpty(true)
				.build();
	}

	private DocumentFieldDescriptor.Builder createField(
			@NonNull final SqlDocumentFieldDataBindingDescriptor fieldBinding,
			@NonNull final AttributesIncludedTabFieldDescriptor attributeFieldDescriptor)
	{
		return DocumentFieldDescriptor.builder(fieldBinding.getFieldName())
				.setCaption(attributeFieldDescriptor.getCaption())
				.setDescription(attributeFieldDescriptor.getDescription())
				.setKey(fieldBinding.isKeyColumn())
				.setWidgetType(fieldBinding.getWidgetType())
				.setValueClass(fieldBinding.getValueClass())
				.setLookupDescriptorProvider(LookupDescriptorProviders.ofNullableInstance(fieldBinding.getLookupDescriptor()))
				.setReadonlyLogic(false)
				.setVirtualField(fieldBinding.isVirtualColumn())
				.setDataBinding(fieldBinding);
	}

	public List<String> getGeneratedFieldNames(final AdUIElementId uiElementId)
	{
		return ImmutableList.copyOf(generatedFieldNamesByUIElementId.get(uiElementId));
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class AttributesUIElement
	{
		@NonNull AdUIElementId id;
		@NonNull AdFieldId attributesProviderFieldId;
	}
}
