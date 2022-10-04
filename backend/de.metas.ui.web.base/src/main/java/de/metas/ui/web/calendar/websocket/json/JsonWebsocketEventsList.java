package de.metas.ui.web.calendar.websocket.json;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonWebsocketEventsList
{
	@NonNull List<JsonWebsocketEvent> events;

	public static JsonWebsocketEventsList ofList(@NonNull final List<JsonWebsocketEvent> list)
	{
		return JsonWebsocketEventsList.builder()
				.events(ImmutableList.copyOf(list))
				.build();
	}

	public static JsonWebsocketEventsList ofEvent(@NonNull final JsonWebsocketEvent event)
	{
		return JsonWebsocketEventsList.builder()
				.events(ImmutableList.of(event))
				.build();
	}

}
