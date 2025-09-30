package de.metas.support_chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportChatService
{
	@NonNull SupportChatAdapter supportChatAdapter;

	public SupportChatResponse ask(@NonNull SupportChatRequest request)
	{
		return supportChatAdapter.ask(request);
	}
}
