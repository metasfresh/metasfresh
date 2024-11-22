package de.metas.attributes_included_tab.data;

import de.metas.util.Check;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.compiere.util.TimeUtil;

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

	public AttributesIncludedTabDataField withValue(@Nullable Object valueObj)
	{
		String valueStringNew = null;
		BigDecimal valueNumberNew = null;
		LocalDate valueDateNew = null;
		AttributeValueId valueItemIdNew = null;

		switch (valueType)
		{
			case STRING:
				valueStringNew = valueObj != null ? valueObj.toString() : null;
				break;
			case NUMBER:
				valueNumberNew = NumberUtils.asBigDecimal(valueObj);
				break;
			case DATE:
				valueDateNew = TimeUtil.asLocalDate(valueObj);
				break;
			case LIST:
				// TODO is it right?!
				valueStringNew = valueObj != null ? valueObj.toString() : null;
				break;
			default:
				throw new AdempiereException("Unexpected value type: " + valueType);
		}

		if (Objects.equals(this.valueString, valueStringNew)
				&& Objects.equals(this.valueNumber, valueNumberNew)
				&& Objects.equals(this.valueDate, valueDateNew)
				&& Objects.equals(this.valueItemId, valueItemIdNew))
		{
			return this; // no change
		}

		return toBuilder()
				.valueString(valueStringNew)
				.valueNumber(valueNumberNew)
				.valueDate(valueDateNew)
				.valueItemId(valueItemIdNew)
				.build();
	}
}
