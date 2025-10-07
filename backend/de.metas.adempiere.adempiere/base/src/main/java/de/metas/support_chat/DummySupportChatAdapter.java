package de.metas.support_chat;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class DummySupportChatAdapter implements SupportChatAdapter
{
	@Override
	public @NonNull SupportChatResponse ask(final @NotNull SupportChatRequest request)
	{
		return SupportChatResponse.builder()
				.message("Dummy support response to " + request.getMessage())
				.build();
	}
}
