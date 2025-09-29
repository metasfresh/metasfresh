package de.metas.ui.web.chat.json;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonMessages
{
	@NonNull ImmutableList<JsonMessageLine> messages;
}
