package de.metas.inventory.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.mobileui.deps.products.Attribute;
import de.metas.inventory.mobileui.deps.products.Attributes;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonAttribute
{
	@NonNull AttributeCode code;
	@NonNull String caption;
	@NonNull JsonAttributeValueType valueType;
	@Nullable Object value;
	@Nullable String valueFormatted;

	public static JsonAttribute of(@NonNull Attribute attribute, @NonNull final String adLanguage)
	{
		return JsonAttribute.builder()
				.code(attribute.getAttributeCode())
				.caption(attribute.getDisplayName().translate(adLanguage))
				.valueType(JsonAttributeValueType.of(attribute.getValueType()))
				.value(attribute.getValueAsJson())
				.valueFormatted(attribute.getValueAsTranslatableString().translate(adLanguage))
				.build();
	}

	public static List<JsonAttribute> ofList(@NonNull List<Attribute> attributes, @NonNull final String adLanguage)
	{
		return attributes.stream()
				.map(attribute -> JsonAttribute.of(attribute, adLanguage))
				.collect(ImmutableList.toImmutableList());
	}

	public static List<JsonAttribute> of(@NonNull Attributes attributes, @NonNull final String adLanguage)
	{
		return ofList(attributes.getAttributes(), adLanguage);
	}

}
