package de.metas.attributes_included_tab.data;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
public class AttributesIncludedTabDataField
{
	@NonNull AttributeId attributeId;
	@NonNull AttributeValueType valueType;
	@Nullable String valueString;
	@Nullable BigDecimal valueNumber;
	@Nullable LocalDate valueDate;
	@Nullable AttributeValueId valueItemId;

	@SuppressWarnings("unused")
	public static class AttributesIncludedTabDataFieldBuilder
	{
		AttributesIncludedTabDataFieldBuilder clearValues()
		{
			valueString(null);
			valueNumber(null);
			valueDate(null);
			valueItemId(null);
			return this;
		}
	}

	public boolean isNullValues()
	{
		return (valueString == null || valueString.isEmpty())
				&& valueNumber == null
				&& valueDate == null
				&& valueItemId == null;
	}

	public static boolean equals(@Nullable AttributesIncludedTabDataField field1, @Nullable AttributesIncludedTabDataField field2)
	{
		return Objects.equals(field1, field2);
	}

	public AttributesIncludedTabDataField withDateValue(@Nullable LocalDate valueDate)
	{
		Check.assumeEquals(valueType, AttributeValueType.DATE, "Expected DATE type: {}", this);
		return toBuilder().clearValues().valueDate(valueDate).build();
	}

	public AttributesIncludedTabDataField withListValue(@Nullable String valueString, @Nullable AttributeValueId valueItemId)
	{
		Check.assumeEquals(valueType, AttributeValueType.LIST, "Expected list type: {}", this);
		return toBuilder().clearValues().valueString(valueString).valueItemId(valueItemId).build();
	}

	public AttributesIncludedTabDataField withNumberValue(@Nullable Integer valueInt)
	{
		return withNumberValue(valueInt != null ? BigDecimal.valueOf(valueInt) : null);
	}

	public AttributesIncludedTabDataField withNumberValue(@Nullable BigDecimal valueNumber)
	{
		Check.assumeEquals(valueType, AttributeValueType.NUMBER, "Expected NUMBER type: {}", this);
		return toBuilder().clearValues().valueNumber(valueNumber).build();
	}

	public AttributesIncludedTabDataField withStringValue(@Nullable String valueString)
	{
		Check.assumeEquals(valueType, AttributeValueType.STRING, "Expected STRING type: {}", this);
		return toBuilder().clearValues().valueString(valueString).build();
	}
}
