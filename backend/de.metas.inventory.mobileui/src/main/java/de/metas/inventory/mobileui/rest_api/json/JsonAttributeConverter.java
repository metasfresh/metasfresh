package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.attribute.json.JsonAttribute;
import de.metas.handlingunits.attribute.json.JsonAttributeValueType;
import de.metas.inventory.mobileui.deps.products.Attribute;
import de.metas.inventory.mobileui.deps.products.Attributes;
import lombok.NonNull;

import java.util.List;

/**
 * Adapts this module's own {@link Attribute}/{@link Attributes} domain wrapper to the shared
 * {@code de.metas.handlingunits.attribute.json.JsonAttribute} DTO (moved out of this module in issue #31771
 * Task 6 so it can be reused by other mobile-UI apps without an app-to-app module dependency).
 */
public class JsonAttributeConverter
{
	public static JsonAttribute of(@NonNull final Attribute attribute, @NonNull final String adLanguage)
	{
		return JsonAttribute.builder()
				.code(attribute.getAttributeCode())
				.caption(attribute.getDisplayName().translate(adLanguage))
				.valueType(JsonAttributeValueType.of(attribute.getValueType()))
				.value(attribute.getValueAsJson())
				.valueFormatted(attribute.getValueAsTranslatableString().translate(adLanguage))
				.build();
	}

	public static List<JsonAttribute> ofList(@NonNull final List<Attribute> attributes, @NonNull final String adLanguage)
	{
		return attributes.stream()
				.map(attribute -> of(attribute, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	public static List<JsonAttribute> of(@NonNull final Attributes attributes, @NonNull final String adLanguage)
	{
		return ofList(attributes.getAttributes(), adLanguage);
	}
}
