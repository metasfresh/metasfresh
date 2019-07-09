/**
 *
 */
package de.metas.email.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.OrgId;
import org.adempiere.util.email.EmailValidator;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.email.IMailDAO;
import de.metas.email.Mailbox;
import de.metas.email.MailboxNotFoundException;
import de.metas.email.templates.MailTemplate;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.email.templates.MailTextBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/**
 * @author Cristina Ghita, Metas.RO
 * @see http://dewiki908/mediawiki/index.php/US901:_Postfächer_pro_Organisation_einstellen_können_%282010110510000031 %29
 */
public class MailBL implements IMailBL
{
	private final Logger log = LogManager.getLogger(getClass());

	private static final String SYSCONFIG_DebugMailTo = "org.adempiere.user.api.IUserBL.DebugMailTo";

	@Override
	public Mailbox findMailBox(
			@NonNull final I_AD_Client client,
			final OrgId orgId,
			final AdProcessId adProcessId,
			final DocBaseAndSubType docBaseAndSubType,
			final EMailCustomType customType,
			@Nullable final I_AD_User user)
	{
		final Mailbox mailbox = findMailBox(
				client, 
				orgId, 
				adProcessId, 
				docBaseAndSubType, 
				customType);
		Check.errorIf(mailbox == null, "Unable to find IMailbox for AD_Client={}, AD_Org_ID={}, AD_Process_ID={}, customeType={}",
				client, orgId, adProcessId, customType);

		if (user == null)
		{
			return mailbox;
		}

		// use smtpHost from AD_MailConfig, but user data from AD_User
		return mailbox.toBuilder()
				.email(EMailAddress.ofNullableString(user.getEMail()))
				.username(user.getEMailUser())
				.password(user.getEMailUserPW())
				.adUserId(UserId.ofRepoId(user.getAD_User_ID()))
				.build();
	}

	private Mailbox findMailBox(
			@NonNull final I_AD_Client client,
			@NonNull final OrgId orgId,
			@Nullable final AdProcessId adProcessId,
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@Nullable final EMailCustomType customType)
	{
		log.debug("Looking for AD_Client_ID={}, AD_Org_ID={}, AD_Process_ID={}, customType={}", client, orgId, adProcessId, customType);

		final IMailDAO mailDAO = Services.get(IMailDAO.class);
		
		final ClientId clientId = ClientId.ofRepoId(client.getAD_Client_ID());
		final List<I_AD_MailConfig> configs = mailDAO.retrieveMailConfigs(clientId, orgId, adProcessId, docBaseAndSubType, customType);

		for (final I_AD_MailConfig config : configs)
		{
			final OrgId configOrgId = OrgId.ofRepoIdOrAny(config.getAD_Org_ID());
			
			if (OrgId.equals(configOrgId, orgId) || configOrgId.isAny())
			{
				final I_AD_MailBox adMailbox = config.getAD_MailBox();
				final Mailbox mailbox = Mailbox.builder()
						.smtpHost(adMailbox.getSMTPHost())
						.smtpPort(adMailbox.getSMTPPort())
						.startTLS(adMailbox.isStartTLS())
						.email(EMailAddress.ofString(adMailbox.getEMail()))
						.username(adMailbox.getUserName())
						.password(adMailbox.getPassword())
						.smtpAuthorization(adMailbox.isSmtpAuthorization())
						.sendFromServer(client.isServerEMail())
						.adClientId(clientId)
						.adUserId(null)
						.columnUserTo(config.getColumnUserTo())
						.build();

				if (log.isDebugEnabled())
				{
					log.debug("Found: {} => {}", toString(config), mailbox);
				}

				return mailbox;
			}
		}

		final String smtpHost = client.getSMTPHost();
		if (Check.isEmpty(smtpHost, true))
		{
			final String messageString = StringUtils.formatMessage(
					"Mail System not configured. Please define some AD_MailConfig or set AD_Client.SMTPHost; "
							+ "AD_MailConfig search parameters: AD_Client_ID={}; AD_Org_ID={}; AD_Process_ID={}; C_DocType={}; CustomType={}",
					clientId, orgId, adProcessId, docBaseAndSubType, customType);

			throw new MailboxNotFoundException(TranslatableStrings.constant(messageString));
		}

		final Mailbox mailbox = Mailbox.builder()
				.smtpHost(smtpHost)
				.smtpPort(client.getSMTPPort())
				.startTLS(client.isStartTLS())
				.email(EMailAddress.ofNullableString(client.getRequestEMail()))
				.username(client.getRequestUser())
				.password(client.getRequestUserPW())
				.smtpAuthorization(client.isSmtpAuthorization())
				.sendFromServer(client.isServerEMail())
				.adClientId(ClientId.ofRepoId(client.getAD_Client_ID()))
				.adUserId(null)
				.columnUserTo(null)
				.build();
		log.debug("Fallback to AD_Client settings: {}", mailbox);
		return mailbox;
	}

	@Override
	public EMail createEMail(
			final I_AD_Client client,
			final EMailCustomType mailCustomType,
			final EMailAddress to,
			final String subject,
			final String message,
			final boolean html)
	{
		final I_AD_User from = null;
		return createEMail(client, mailCustomType, from, to, subject, message, html);
	}

	@Override
	public EMail createEMail(
			final I_AD_Client client,
			final EMailCustomType mailCustomType,
			final I_AD_User from,
			final EMailAddress to,
			final String subject,
			final String message,
			final boolean html)
	{
		final Mailbox mailbox = findMailBox(
				client,
				ProcessExecutor.getCurrentOrgId(),
				ProcessExecutor.getCurrentProcessIdOrNull(),
				(DocBaseAndSubType)null, // C_DocType - Task FRESH-203 : This shall work as before
				mailCustomType,
				from);
		return createEMail(mailbox, to, subject, message, html);
	}

	@Override
	public EMail createEMail(
			@NonNull final Mailbox mailbox,
			@NonNull final EMailAddress to,
			final String subject,
			String message,
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
		return email;
	}

	private String toString(final I_AD_MailConfig config)
	{
		return config.getClass().getSimpleName() + "["
				+ "AD_Client_ID=" + config.getAD_Client_ID()
				+ ", AD_Org_ID=" + config.getAD_Org_ID()
				+ ", AD_Process_ID=" + config.getAD_Process_ID()
				+ ", CustomType=" + config.getCustomType()
				+ ", IsActive=" + config.isActive()
				+ ", AD_MailConfig_ID=" + config.getAD_MailConfig_ID()
				+ "]";
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
			log.warn("Invalid email address: {}", emailStr, e);
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
