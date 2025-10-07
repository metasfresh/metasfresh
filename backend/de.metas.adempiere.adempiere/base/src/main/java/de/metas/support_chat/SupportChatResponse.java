package de.metas.support_chat;

import de.metas.common.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class SupportChatResponse
{
	@NonNull @Builder.Default Instant timestamp = SystemTime.asInstant();
	@NonNull String message;
}
