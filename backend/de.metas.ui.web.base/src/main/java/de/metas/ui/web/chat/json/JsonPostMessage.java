package de.metas.ui.web.chat.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPostMessage
{
	@NonNull String message;
}
