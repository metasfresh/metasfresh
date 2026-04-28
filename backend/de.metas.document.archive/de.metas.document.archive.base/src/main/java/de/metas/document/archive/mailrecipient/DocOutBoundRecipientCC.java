package de.metas.document.archive.mailrecipient;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class DocOutBoundRecipientCC
{
	@NonNull DocOutBoundRecipientId id;
	@Nullable String emailAddress;

	public static DocOutBoundRecipientCC of(@NonNull final DocOutBoundRecipient recipient)
	{
		return DocOutBoundRecipientCC.builder()
				.id(recipient.getId())
				.emailAddress(recipient.getEmailAddress())
				.build();
	}
}
