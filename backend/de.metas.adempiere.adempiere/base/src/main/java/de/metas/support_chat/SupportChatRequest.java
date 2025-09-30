package de.metas.support_chat;

import de.metas.common.util.time.SystemTime;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class SupportChatRequest
{
	@NonNull @Default Instant timestamp = SystemTime.asInstant();
	@NonNull UserId userId;
	@NonNull String message;
}
