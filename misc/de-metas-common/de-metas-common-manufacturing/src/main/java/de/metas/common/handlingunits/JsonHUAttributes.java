package de.metas.common.handlingunits;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonHUAttributes
{
	@NonNull List<JsonHUAttribute> list;

	public JsonHUAttributeCodeAndValues toJsonHUAttributeCodeAndValues()
	{
		final JsonHUAttributeCodeAndValues result = new JsonHUAttributeCodeAndValues();
		for (final JsonHUAttribute attribute : list)
		{
			result.putAttribute(attribute.getCode(), attribute.getValue());
		}
		return result;
	}

	public static JsonHUAttributes ofJsonHUAttributeCodeAndValues(@NonNull JsonHUAttributeCodeAndValues attributeCodeAndValues)
	{
		final ArrayList<JsonHUAttribute> list = new ArrayList<>();
		for (final Map.Entry<String, Object> attributeCodeAndValue : attributeCodeAndValues.getAttributes().entrySet())
		{
			list.add(JsonHUAttribute.builder()
					.code(attributeCodeAndValue.getKey())
					.caption(attributeCodeAndValue.getKey())
					.value(attributeCodeAndValue.getValue())
					.build());
		}

		return JsonHUAttributes.builder().list(list).build();
	}

	public JsonHUAttributes retainOnlyAttributesInOrder(@NonNull final List<String> codes)
	{
		final ArrayList<JsonHUAttribute> filteredList = new ArrayList<>();
		final ImmutableListMultimap<String, JsonHUAttribute> attributesByCode = Multimaps.index(list, JsonHUAttribute::getCode);

		for (String code : codes)
		{
			filteredList.addAll(attributesByCode.get(code));
		}

		return builder().list(filteredList).build();
	}
}
