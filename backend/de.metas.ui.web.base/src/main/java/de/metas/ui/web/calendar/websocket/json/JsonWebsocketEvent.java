package de.metas.ui.web.calendar.websocket.json;

import lombok.NonNull;

public interface JsonWebsocketEvent
{
	@NonNull String getType();
}
