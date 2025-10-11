package de.metas.inventory.mobileui.rest_api.json;

import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonResolveHUResponseAttribute
{
	@NonNull AttributeCode code;
	@NonNull String caption;
	@NonNull JsonAttributeValueType valueType;
	@Nullable Object value;

	public static JsonResolveHUResponseAttribute of(@NonNull ResolveHUResponse.Attribute attribute, @NonNull final String adLanguage)
	{
		return builder()
				.code(attribute.getAttributeCode())
				.caption(attribute.getDisplayName().translate(adLanguage))
				.valueType(JsonAttributeValueType.of(attribute.getValueType()))
				.value(extractJsonValue(attribute))
				.build();
	}

	private static Object extractJsonValue(final ResolveHUResponse.Attribute attribute)
	{
		final AttributeValueType valueType = attribute.getValueType();
		if (AttributeValueType.STRING.equals(valueType))
		{
			return attribute.getValueAsString();
		}
		else if (AttributeValueType.NUMBER.equals(valueType))
		{
			return attribute.getValueAsBigDecimal();
		}
		else if (AttributeValueType.DATE.equals(valueType))
		{
			return attribute.getValueAsLocalDate();
		}
		else if (AttributeValueType.LIST.equals(valueType))
		{
			return attribute.getValueAsString();
		}
		else
		{
			throw new AdempiereException("Unknown attribute type: " + valueType);
		}
	}
}
