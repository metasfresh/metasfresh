/**
 *
 */
package de.metas.email.impl;

import java.util.Properties;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.OrgId;
import org.adempiere.util.email.EmailValidator;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxRepository;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.email.templates.MailTemplate;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.email.templates.MailTextBuilder;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class MailBL implements IMailBL
{
	private static final Logger logger = LogManager.getLogger(MailBL.class);

	private static final String SYSCONFIG_DebugMailTo = "org.adempiere.user.api.IUserBL.DebugMailTo";

	private MailboxRepository mailboxRepository()
	{
		return Adempiere.getBean(MailboxRepository.class);
	}

	@Override
	public Mailbox findMailBox(
			@NonNull final ClientEMailConfig clientEmailConfig,
			@NonNull final OrgId orgId,
			@Nullable final AdProcessId adProcessId,
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@Nullable final EMailCustomType customType,
			@Nullable final UserEMailConfig userEmailConfig)
	{
		return mailboxRepository().findMailBox(clientEmailConfig, orgId, adProcessId, docBaseAndSubType, customType, userEmailConfig);
	}

	@Override
	public EMail createEMail(
			@NonNull final ClientEMailConfig clientEmailConfig,
			@Nullable final EMailCustomType mailCustomType,
			@Nullable final UserEMailConfig userEmailConfig,
			@Nullable final EMailAddress to,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html)
	{
		final Mailbox mailbox = mailboxRepository().findMailBox(
				clientEmailConfig,
				ProcessExecutor.getCurrentOrgId(),
				ProcessExecutor.getCurrentProcessIdOrNull(),
				(DocBaseAndSubType)null,
				mailCustomType,
				userEmailConfig);
		return createEMail(mailbox, to, subject, message, html);
	}

	@Override
	public EMail createEMail(
			@NonNull final Mailbox mailbox,
			@NonNull final EMailAddress to,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html)
	{
		if (mailbox.getEmail() == null
				// || mailbox.getUsername() == null
				// is SMTP authorization and password is null - teo_sarca [ 1723309 ]
				|| mailbox.isSmtpAuthorization() && mailbox.getPassword() == null)
		{
			throw new AdempiereException("Mailbox incomplete: " + mailbox);
		}

		return new EMail(mailbox, to, subject, message, html);
	}

	@Override
	public InternetAddress getDebugMailToAddressOrNull(final Properties ctx)
	{
		String emailStr = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_DebugMailTo,
				null,             // defaultValue
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
		if (Check.isEmpty(emailStr, true))
		{
			return null;
		}

		emailStr = emailStr.trim();

		if (emailStr.equals("-"))
		{
			return null;
		}

		final InternetAddress email;
		try
		{
			email = new InternetAddress(emailStr, true);
		}
		catch (final Exception e)
		{
			logger.warn("Invalid email address: {}", emailStr, e);
			return null;
		}

		return email;
	}

	@Override
	public void send(final EMail email)
	{
		final EMailSentStatus sentStatus = email.send();
		if (!sentStatus.isSentOK())
		{
			throw new EMailSendException(sentStatus);
		}
	}

	@Override
	public boolean isConnectionError(final Exception e)
	{
		if (e instanceof EMailSendException)
		{
			return ((EMailSendException)e).isConnectionError();
		}
		else if (e instanceof java.net.ConnectException)
		{
			return true;
		}

		return false;
	}

	@Override
	public MailTextBuilder newMailTextBuilder(@NonNull final MailTemplate mailTemplate)
	{
		return MailTextBuilder.newInstance(mailTemplate);
	}

	@Override
	public MailTextBuilder newMailTextBuilder(final MailTemplateId mailTemplateId)
	{
		final MailTemplateRepository mailTemplatesRepo = Adempiere.getBean(MailTemplateRepository.class);
		final MailTemplate mailTemplate = mailTemplatesRepo.getById(mailTemplateId);
		return newMailTextBuilder(mailTemplate);
	}

	@Override
	public void validateEmail(@Nullable final String email)
	{
		if (!Check.isEmpty(email, true) && !EmailValidator.validate(email))
		{
			throw new AdempiereException("@EmailNotValid@");

		}
	}
}
