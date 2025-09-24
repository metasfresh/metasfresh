package de.metas.email;

import com.google.common.collect.ImmutableList;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.templates.MailText;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class EMailRequest
{
	@NonNull Mailbox mailbox;

	@NonNull ImmutableList<EMailAddress> toList;
	@Nullable ImmutableList<EMailAddress> ccList;
	@Nullable String subject;
	@Nullable String message;
	boolean html;
	@NonNull ImmutableList<EMailAttachment> attachments;

	boolean failIfNotSent;
	@Nullable ILoggable debugLoggable;
	boolean forceRealEmailRecipients;

	@Builder
	private EMailRequest(
			@NonNull final Mailbox mailbox,
			@NonNull @Singular("to") final ImmutableList<EMailAddress> toList,
			@Nullable @Singular("cc") final ImmutableList<EMailAddress> ccList,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html,
			@NonNull @Singular final ImmutableList<EMailAttachment> attachments,
			@Nullable final Boolean failIfNotSent,
			@Nullable final ILoggable debugLoggable,
			final boolean forceRealEmailRecipients)
	{
		this.mailbox = mailbox;

		this.toList = Check.assumeNotEmpty(toList, "at least one to recipient shall be specified");
		this.ccList = ccList;
		this.subject = subject;
		this.message = message;
		this.html = html;
		this.attachments = attachments;
		this.failIfNotSent = failIfNotSent != null ? failIfNotSent : true;
		this.debugLoggable = debugLoggable;
		this.forceRealEmailRecipients = forceRealEmailRecipients;
	}

	@SuppressWarnings("unused")
	public static class EMailRequestBuilder
	{
		public EMailRequestBuilder mailText(@NonNull final MailText mailText)
		{
			subject(mailText.getMailHeader());
			message(mailText.getFullMailText());
			html(mailText.isHtml());
			return this;
		}

		public EMailRequestBuilder attachmentIfNotEmpty(@NonNull final String filename, @Nullable final byte[] content)
		{
			final EMailAttachment attachment = EMailAttachment.ofNullable(filename, content);
			if (attachment != null)
			{
				attachments.add(attachment);
			}
			return this;
		}
	}

	public EMailAddress getTo() {return toList.get(0);}

	public @Nullable EMailAddress getCc() {return ccList != null ? ccList.get(0) : null;}
}
