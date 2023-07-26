package de.metas.email;

import java.util.Properties;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.email.EmailValidator;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.document.DocBaseAndSubType;
import de.metas.email.impl.EMailSendException;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxNotFoundException;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.email.mailboxes.MailboxRepository;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.email.templates.MailTemplate;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.email.templates.MailTextBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class MailService
{
	private static final Logger logger = LogManager.getLogger(MailService.class);
	private final MailboxRepository mailboxRepo;
	private final MailTemplateRepository mailTemplatesRepo;

	private static final String SYSCONFIG_DebugMailTo = "org.adempiere.user.api.IUserBL.DebugMailTo";

	public MailService(
			@NonNull final MailboxRepository mailboxRepo,
			@NonNull final MailTemplateRepository mailTemplatesRepo)
	{
		this.mailboxRepo = mailboxRepo;
		this.mailTemplatesRepo = mailTemplatesRepo;
	}

	public Mailbox findMailBox(
			@NonNull final ClientEMailConfig tenantEmailConfig,
			@NonNull final OrgId orgId)
	{
		final AdProcessId adProcessId = null;
		final DocBaseAndSubType docBaseAndSubType = null;
		final EMailCustomType customType = null;
		return findMailBox(tenantEmailConfig, orgId, adProcessId, docBaseAndSubType, customType);
	}

	public Mailbox findMailBox(
			@NonNull final ClientEMailConfig tenantEmailConfig,
			@NonNull final OrgId orgId,
			@Nullable final AdProcessId adProcessId,
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@Nullable final EMailCustomType customType)
	{
		final MailboxQuery query = MailboxQuery.builder()
				.clientId(tenantEmailConfig.getClientId())
				.orgId(orgId)
				.adProcessId(adProcessId)
				.customType(customType)
				.build();

		return mailboxRepo
				.findMailBox(query)
				.orElseGet(() -> createClientMailbox(tenantEmailConfig))
				.withSendEmailsFromServer(tenantEmailConfig.isSendEmailsFromServer());
	}

	@NonNull
	private static Mailbox createClientMailbox(@NonNull final ClientEMailConfig client)
	{
		final String smtpHost = client.getSmtpHost();
		if (Check.isEmpty(smtpHost, true))
		{
			final String messageString = StringUtils.formatMessage(
					"Mail System not configured. Please define some AD_MailConfig or set AD_Client.SMTPHost; "
							+ "AD_MailConfig search parameters: AD_Client_ID={}",
					client);

			throw new MailboxNotFoundException(TranslatableStrings.constant(messageString));
		}

		return Mailbox.builder()
				.smtpHost(smtpHost)
				.smtpPort(client.getSmtpPort())
				.startTLS(client.isStartTLS())
				.email(client.getEmail())
				.username(client.getUsername())
				.password(client.getPassword())
				.smtpAuthorization(client.isSmtpAuthorization())
				.sendEmailsFromServer(client.isSendEmailsFromServer())
				.build();
	}

	public EMail createEMail(
			@NonNull final ClientEMailConfig clientEmailConfig,
			@Nullable final EMailCustomType mailCustomType,
			@Nullable final UserEMailConfig userEmailConfig,
			@Nullable final EMailAddress to,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html)
	{
		final Mailbox mailbox = findMailBox(clientEmailConfig,
				ProcessExecutor.getCurrentOrgId(),
				ProcessExecutor.getCurrentProcessIdOrNull(),
				(DocBaseAndSubType)null,
				mailCustomType)
						.mergeFrom(userEmailConfig);
		return createEMail(mailbox, to, subject, message, html);
	}

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

		final EMail email = new EMail(mailbox, to, subject, message, html);
		email.setDebugMailToAddress(getDebugMailToAddressOrNull());
		return email;
	}

	/**
	 * Gets the EMail TO address to be used when sending mails.
	 * If present, this address will be used to send all emails to a particular address instead of actual email addresses.
	 *
	 * @return email address or null
	 */
	@Nullable
	public InternetAddress getDebugMailToAddressOrNull()
	{
		final Properties ctx = Env.getCtx();
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

	public void send(final EMail email)
	{
		final EMailSentStatus sentStatus = email.send();
		if (!sentStatus.isSentOK())
		{
			throw new EMailSendException(sentStatus);
		}
	}

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

	public MailTextBuilder newMailTextBuilder(@NonNull final MailTemplate mailTemplate)
	{
		return MailTextBuilder.newInstance(mailTemplate);
	}

	public MailTextBuilder newMailTextBuilder(final MailTemplateId mailTemplateId)
	{
		final MailTemplate mailTemplate = mailTemplatesRepo.getById(mailTemplateId);
		return newMailTextBuilder(mailTemplate);
	}

	public void validateEmail(@Nullable final String email)
	{
		if (!Check.isEmpty(email, true) && !EmailValidator.validate(email))
		{
			throw new AdempiereException("@EmailNotValid@");

		}
	}
}
