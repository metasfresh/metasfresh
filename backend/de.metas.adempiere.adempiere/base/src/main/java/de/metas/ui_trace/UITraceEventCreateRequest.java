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
	@NonNull UITraceExternalId id;
	@NonNull String eventName;
	@NonNull Instant timestamp;

	@Nullable String url;
	@Nullable String username;
	@Nullable String caption;
	@Nullable String applicationId;
	@Nullable String deviceId;
	@Nullable String tabId;
	@Nullable String userAgent;

	@Nullable Map<String, Object> properties;
}
