package de.metas.document.archive.mailrecipient;

import de.metas.util.Check;
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
		// A CC recipient always corresponds to an AD_User; email-only recipients (id == null) are never valid as CC.
		final DocOutBoundRecipientId id = Check.assumeNotNull(recipient.getId(), "CC recipient must have a user id: {}", recipient);
		return DocOutBoundRecipientCC.builder()
				.id(id)
				.emailAddress(recipient.getEmailAddress())
				.build();
	}
}
