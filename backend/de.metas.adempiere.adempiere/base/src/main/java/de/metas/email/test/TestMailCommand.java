package de.metas.email.test;

import ch.qos.logback.classic.Level;
import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.impl.EMailSendException;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

@Builder
public class TestMailCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(TestMailCommand.class);
	@NonNull private final MailService mailService;

	@NonNull private final TestMailRequest request;

	public void execute()
	{
		final ILoggable loggable = request.getLoggable() != null ? request.getLoggable() : Loggables.getLoggableOrLogger(logger, Level.INFO);

		final MailboxQuery mailboxQuery = MailboxQuery.builder()
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.adProcessId(request.getProcessId())
				.customType(request.getEmailCustomType())
				.fromUserId(request.getFromUserId())
				.build();
		loggable.addLog("Mailbox query: " + mailboxQuery);

		Mailbox mailbox = mailService.findMailbox(mailboxQuery);
		loggable.addLog("Mailbox: " + mailbox);

		final EMail email = mailService.createEMail(mailbox, request.getMailTo(), request.getSubject(), request.getMessage(), request.isHtml());
		email.setDebugMode(true);
		email.setDebugLoggable(loggable);

		loggable.addLog("Sending: " + email);
		final EMailSentStatus emailSentStatus = email.send();
		loggable.addLog("Got response: " + emailSentStatus);

		emailSentStatus.throwIfNotOK();
		if (!emailSentStatus.isSentOK())
		{
			throw new EMailSendException(emailSentStatus);
		}
	}
}
