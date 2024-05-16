package de.metas.common.handlingunits;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
public class JsonHUAttribute
{
	@NonNull String code;
	@NonNull String caption;
	@Nullable Object value;

	@Builder
	@Jacksonized
	private JsonHUAttribute(
			@NonNull final String code,
			@NonNull final String caption,
			@Nullable final Object value)
	{
		this.code = code;
		this.caption = caption;
		this.value = JsonHUAttributeCodeAndValues.convertValueToJson(value);
	}
}
