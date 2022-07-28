package de.metas.common.rest_api.v2.i18n;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class JsonMessages
{
	@NonNull String language;
	@NonNull Map<String, Object> messages;
}
