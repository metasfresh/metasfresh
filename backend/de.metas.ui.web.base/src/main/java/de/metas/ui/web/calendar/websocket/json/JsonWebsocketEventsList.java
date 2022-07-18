package de.metas.ui.web.calendar.websocket.json;

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
}
