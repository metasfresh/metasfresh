package de.metas.ui_trace;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Map;

@Value
@Builder
public class UITraceEventCreateRequest
{
	@NonNull String id;
	@NonNull String eventName;
	@NonNull Instant timestamp;
	@Nullable Map<String, Object> properties;
}
