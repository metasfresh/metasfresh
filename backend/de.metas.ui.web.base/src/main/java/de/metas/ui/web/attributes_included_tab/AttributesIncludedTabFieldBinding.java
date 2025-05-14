package de.metas.ui.web.attributes_included_tab;

import de.metas.attributes_included_tab.AttributesIncludedTabService;
import de.metas.attributes_included_tab.data.AttributesIncludedTabData;
import de.metas.attributes_included_tab.data.AttributesIncludedTabDataField;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabFieldDescriptor;
import de.metas.ui.web.pattribute.ASILookupDescriptor;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.IDocumentFieldView;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.impl.AttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
public class AttributesIncludedTabFieldBinding implements DocumentFieldDataBindingDescriptor
{
	@NonNull private final AttributesIncludedTabFieldDescriptor includedTabField;
	@NonNull @Getter private final String columnName;
	@NonNull @Getter private final AttributeId attributeId;
	@NonNull @Getter private final AttributeValueType attributeValueType;
	@Getter private final boolean mandatory;

	@NonNull @Getter private final Class<?> valueClass;
	@NonNull @Getter private final DocumentFieldWidgetType widgetType;
	@Nullable @Getter private final LookupDescriptor lookupDescriptor;
	@NonNull private final ValueReader valueReader;
	@NonNull private final ValueWriter valueWriter;

	@Builder
	private AttributesIncludedTabFieldBinding(@NonNull final AttributesIncludedTabService attributesIncludedTabService, @NonNull final AttributesIncludedTabFieldDescriptor includedTabField)
	{
		this.includedTabField = includedTabField;
		this.columnName = computeFieldName(includedTabField);
		this.attributeId = includedTabField.getAttributeId();
		this.attributeValueType = includedTabField.getAttributeValueType();
		this.mandatory = includedTabField.isMandatory();

		if (AttributeValueType.DATE.equals(attributeValueType))
		{
			this.valueClass = LocalDate.class;
			this.widgetType = DocumentFieldWidgetType.LocalDate;
			this.lookupDescriptor = null;
			this.valueReader = AttributesIncludedTabData::getValueAsLocalDate;
			this.valueWriter = AttributesIncludedTabFieldBinding::writeValueAsLocalDate;
		}
		else if (AttributeValueType.LIST.equals(attributeValueType))
		{
			this.valueClass = LookupValue.StringLookupValue.class;
			this.widgetType = DocumentFieldWidgetType.List;
			this.lookupDescriptor = computeLookupDescriptor(includedTabField, attributesIncludedTabService);
			this.valueReader = AttributesIncludedTabData::getValueAsString;
			this.valueWriter = AttributesIncludedTabFieldBinding::writeValueAsListItem;
		}
		else if (AttributeValueType.NUMBER.equals(attributeValueType))
		{
			if (AttributesBL.isInteger(includedTabField.getUomId()))
			{
				this.valueClass = Integer.class;
				this.widgetType = DocumentFieldWidgetType.Integer;
				this.lookupDescriptor = null;
				this.valueReader = AttributesIncludedTabData::getValueAsInteger;
				this.valueWriter = AttributesIncludedTabFieldBinding::writeValueAsInteger;
			}
			else
			{
				this.valueClass = BigDecimal.class;
				this.widgetType = DocumentFieldWidgetType.Number;
				this.lookupDescriptor = null;
				this.valueReader = AttributesIncludedTabData::getValueAsBigDecimal;
				this.valueWriter = AttributesIncludedTabFieldBinding::writeValueAsBigDecimal;
			}
		}
		else if (AttributeValueType.STRING.equals(attributeValueType))
		{
			this.valueClass = String.class;
			this.widgetType = DocumentFieldWidgetType.Text;
			this.lookupDescriptor = null;
			this.valueReader = AttributesIncludedTabData::getValueAsString;
			this.valueWriter = AttributesIncludedTabFieldBinding::writeValueAsString;
		}
		else
		{
			throw new AdempiereException("Unknown attribute type " + attributeValueType);
		}
	}

	@Nullable
	private static LookupDescriptor computeLookupDescriptor(@NonNull final AttributesIncludedTabFieldDescriptor includedTabField, @NonNull final AttributesIncludedTabService attributesIncludedTabService)
	{
		if (includedTabField.getAttributeValueType().isList())
		{
			final IAttributeValuesProvider attributeValuesProvider = attributesIncludedTabService.createAttributeValuesProvider(includedTabField.getAttributeId());
			if (attributeValuesProvider != null)
			{
				return ASILookupDescriptor.of(attributeValuesProvider);
			}
		}

		return null;
	}

	public static String computeFieldName(final AttributesIncludedTabFieldDescriptor includedTabField)
	{
		return includedTabField.getAttributeCode().getCode();
	}

	private static AttributesIncludedTabDataField writeValueAsLocalDate(AttributesIncludedTabDataField dataField, IDocumentFieldView documentField)
	{
		return dataField.withDateValue(documentField.getValueAs(LocalDate.class));
	}

	private static AttributesIncludedTabDataField writeValueAsInteger(AttributesIncludedTabDataField dataField, IDocumentFieldView documentField)
	{
		return dataField.withNumberValue(documentField.getValueAs(Integer.class));
	}

	private static AttributesIncludedTabDataField writeValueAsBigDecimal(AttributesIncludedTabDataField dataField, IDocumentFieldView documentField)
	{
		return dataField.withNumberValue(documentField.getValueAs(BigDecimal.class));
	}

	private static AttributesIncludedTabDataField writeValueAsString(AttributesIncludedTabDataField dataField, IDocumentFieldView documentField)
	{
		return dataField.withStringValue(documentField.getValueAs(String.class));
	}

	private static AttributesIncludedTabDataField writeValueAsListItem(final AttributesIncludedTabDataField dataField, final IDocumentFieldView documentField)
	{
		final StringLookupValue lookupValue = documentField.getValueAs(StringLookupValue.class);
		final String valueString = lookupValue != null ? lookupValue.getIdAsString() : null;
		final AttributeValueId valueItemId = documentField.getDescriptor().getLookupDescriptor().get().cast(ASILookupDescriptor.class).getAttributeValueId(valueString);

		return dataField.withListValue(valueString, valueItemId);
	}

	public Object getValue(final @NonNull AttributesIncludedTabData data)
	{
		return valueReader.readValue(data, attributeId);
	}

	public AttributesIncludedTabDataField updateData(final @NonNull AttributesIncludedTabDataField dataField, final IDocumentFieldView documentField)
	{
		return valueWriter.writeValue(dataField, documentField);
	}

	//
	//
	//
	//
	//

	@FunctionalInterface
	private interface ValueReader
	{
		Object readValue(AttributesIncludedTabData data, AttributeId attributeId);
	}

	@FunctionalInterface
	private interface ValueWriter
	{
		AttributesIncludedTabDataField writeValue(AttributesIncludedTabDataField dataField, IDocumentFieldView documentField);
	}
}

