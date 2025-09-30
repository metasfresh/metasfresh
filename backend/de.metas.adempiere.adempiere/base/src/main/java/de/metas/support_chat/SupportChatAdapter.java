package de.metas.support_chat;

import lombok.NonNull;

public interface SupportChatAdapter
{
	@NonNull
	SupportChatResponse ask(@NonNull SupportChatRequest request);
}
