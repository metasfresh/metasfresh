package de.metas.handlingunits.attribute.json;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeValueType;

/**
 * Shared, generic mobile-UI JSON view of {@link AttributeValueType}.
 * <p>
 * Lifted from {@code de.metas.inventory.mobileui.rest_api.json.JsonAttributeValueType} into
 * this module so it can be reused by any mobile-UI app that already depends on {@code de.metas.handlingunits.base}
 * (e.g. {@code de.metas.manufacturing.rest-api}), without creating a bad app-to-app dependency between sibling
 * mobile-UI app modules.
 */
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
