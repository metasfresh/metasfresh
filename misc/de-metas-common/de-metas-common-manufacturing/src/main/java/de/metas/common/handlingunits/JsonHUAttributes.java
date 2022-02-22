package de.metas.common.handlingunits;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

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
}
