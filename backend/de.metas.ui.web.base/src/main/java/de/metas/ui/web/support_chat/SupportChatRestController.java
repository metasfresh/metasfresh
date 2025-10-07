package de.metas.ui.web.support_chat;

import de.metas.support_chat.SupportChatRequest;
import de.metas.support_chat.SupportChatResponse;
import de.metas.support_chat.SupportChatService;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.support_chat.json.JsonPostMessage;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.user.UserId;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SupportChatRestController.ENDPOINT)
@RequiredArgsConstructor
public class SupportChatRestController
{
	protected static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/support-chat";

	@NonNull private final SupportChatService supportChatService;
	@NonNull private final WebsocketSender websocketSender;

	@PostMapping("/post")
	public void postMessage(@RequestBody final JsonPostMessage request)
	{
		final UserId userId = Env.getLoggedUserId();

		final SupportChatResponse response = supportChatService.ask(SupportChatRequest.builder()
				.userId(userId)
				.message(request.getMessage())
				.build());
		
		websocketSender.convertAndSend(
				WebsocketTopicNames.buildChatTopicName(userId),
				JsonPostMessage.builder()
						.message(response.getMessage())
						.build()
		);
	}
}
