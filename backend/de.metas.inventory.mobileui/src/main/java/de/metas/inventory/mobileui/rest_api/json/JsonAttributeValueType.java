package de.metas.inventory.mobileui.rest_api.json;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeValueType;

public enum JsonAttributeValueType
{
	STRING,
	NUMBER,
	DATE,
	LIST,
	;

	public static JsonAttributeValueType of(@NonNull final AttributeValueType attributeValueType)
	{
		switch (attributeValueType)
		{
			case STRING:
				return JsonAttributeValueType.STRING;
			case NUMBER:
				return JsonAttributeValueType.NUMBER;
			case DATE:
				return JsonAttributeValueType.DATE;
			case LIST:
				return JsonAttributeValueType.LIST;
			default:
				throw new AdempiereException("AttributeValueType not supported: " + attributeValueType);
		}
	}
}
