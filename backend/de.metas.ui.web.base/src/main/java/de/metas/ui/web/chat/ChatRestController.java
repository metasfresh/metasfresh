package de.metas.ui.web.chat;

import de.metas.ui.web.chat.json.JsonPostMessage;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChatRestController.ENDPOINT)
@RequiredArgsConstructor
public class ChatRestController
{
	protected static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/chat";

	@NonNull private final UserSession userSession;
	@NonNull private final WebsocketSender websocketSender;

	@PostMapping("/post")
	public void postMessage(@RequestBody final JsonPostMessage request)
	{
		websocketSender.convertAndSend(
				WebsocketTopicNames.buildChatTopicName(Env.getLoggedUserId()),
				JsonPostMessage.builder()
						.message("AI replying to " + request.getMessage())
						.build()
		);
	}
}
