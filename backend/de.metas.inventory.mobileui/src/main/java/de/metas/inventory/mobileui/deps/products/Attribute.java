package de.metas.inventory.mobileui.deps.products;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class Attribute
{
	@NonNull AttributeCode attributeCode;
	@NonNull ITranslatableString displayName;
	@NonNull AttributeValueType valueType;
	@Nullable Object value;
	
	public static Attribute of(@NonNull final org.adempiere.mm.attributes.api.Attribute attributeDescriptor)
	{
		return Attribute.builder()
				.attributeCode(attributeDescriptor.getAttributeCode())
				.displayName(attributeDescriptor.getDisplayName())
				.valueType(attributeDescriptor.getValueType())
				.value(null)
				.build();
	}

	public String getValueAsString()
	{
		return value != null ? value.toString() : null;
	}

	public BigDecimal getValueAsBigDecimal()
	{
		return value != null ? NumberUtils.asBigDecimal(value) : null;
	}

	public LocalDate getValueAsLocalDate()
	{
		return value == null ? null : TimeUtil.asLocalDate(value);
	}

	public Object getValueAsJson()
	{
		if (AttributeValueType.STRING.equals(valueType))
		{
			return getValueAsString();
		}
		else if (AttributeValueType.NUMBER.equals(valueType))
		{
			return getValueAsBigDecimal();
		}
		else if (AttributeValueType.DATE.equals(valueType))
		{
			return getValueAsLocalDate().toString();
		}
		else if (AttributeValueType.LIST.equals(valueType))
		{
			return getValueAsString();
		}
		else
		{
			throw new AdempiereException("Unknown attribute type: " + valueType);
		}
	}

	public ITranslatableString getValueAsTranslatableString()
	{
		switch (valueType)
		{
			case STRING:
			case LIST:
			{
				final String valueStr = getValueAsString();
				return TranslatableStrings.anyLanguage(valueStr);
			}
			case NUMBER:
			{
				final BigDecimal valueBD = getValueAsBigDecimal();
				return valueBD != null
						? TranslatableStrings.number(valueBD, de.metas.common.util.NumberUtils.isInteger(valueBD) ? DisplayType.Integer : DisplayType.Number)
						: TranslatableStrings.empty();
			}
			case DATE:
			{
				final LocalDate valueDate = getValueAsLocalDate();
				return valueDate != null
						? TranslatableStrings.date(valueDate, DisplayType.Date)
						: TranslatableStrings.empty();
			}
			default:
			{
				return TranslatableStrings.empty();
			}
		}
	}

}
