package de.metas.document.archive.mailrecipient;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
public class DocOutBoundRecipients
{
	@NonNull DocOutBoundRecipient to;
	@Nullable DocOutBoundRecipientCC cc;

	public static Optional<DocOutBoundRecipients> optionalOfTo(@Nullable DocOutBoundRecipient to)
	{
		return to != null
				? Optional.of(builder().to(to).build())
				: Optional.empty();
	}

	public boolean isInvoiceAsEmail()
	{
		return to.isInvoiceAsEmail();
	}
}
