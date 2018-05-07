/**
 *
 */
package de.metas.email.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.email.EmailValidator;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_R_MailText;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.email.IMailDAO;
import de.metas.email.IMailTextBuilder;
import de.metas.email.Mailbox;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutor;

/**
 * @author Cristina Ghita, Metas.RO
 * @see http://dewiki908/mediawiki/index.php/US901:_Postfächer_pro_Organisation_einstellen_können_%282010110510000031 %29
 */
public class MailBL implements IMailBL
{
	private final Logger log = LogManager.getLogger(getClass());

	private static final String SYSCONFIG_DebugMailTo = "org.adempiere.user.api.IUserBL.DebugMailTo";

	// @Cached
	@Override
	public Mailbox findMailBox(final I_AD_Client client,
			final int AD_Org_ID,
			final int AD_Process_ID,
			final I_C_DocType docType,
			final String customType,
			final I_AD_User user)
	{
		final Mailbox mailbox = findMailBox(client, AD_Org_ID, AD_Process_ID, docType, customType);
		Check.errorIf(mailbox == null, "Unable to find IMailbox for AD_Client={}, AD_Org_ID={}, AD_Process_ID={}, customeType={}",
				client, AD_Org_ID, AD_Process_ID, customType);

		if (user == null)
		{
			return mailbox;
		}

		// use smtpHost from AD_MailConfig, but user data from AD_User
		return mailbox.toBuilder()
				.email(user.getEMail())
				.username(user.getEMailUser())
				.password(user.getEMailUserPW())
				.adUserId(user.getAD_User_ID())
				.build();
	}

	private Mailbox findMailBox(final I_AD_Client client, final int adOrgId, final int processID, final I_C_DocType docType, final String customType)
	{
		log.debug("Looking for AD_Client_ID={}, AD_Org_ID={}, AD_Process_ID={}, customType={}", client, adOrgId, processID, customType);

		final IMailDAO mailDAO = Services.get(IMailDAO.class);
		final List<I_AD_MailConfig> configs = mailDAO.retrieveMailConfigs(client, adOrgId, processID, docType, customType);

		for (final I_AD_MailConfig config : configs)
		{
			if (config.getAD_Org_ID() == adOrgId
					|| config.getAD_Org_ID() != adOrgId && config.getAD_Org_ID() == Env.CTXVALUE_AD_Org_ID_System)
			{
				final I_AD_MailBox adMailbox = config.getAD_MailBox();
				final Mailbox mailbox = Mailbox.builder()
						.smtpHost(adMailbox.getSMTPHost())
						.smtpPort(adMailbox.getSMTPPort())
						.startTLS(adMailbox.isStartTLS())
						.email(adMailbox.getEMail())
						.username(adMailbox.getUserName())
						.password(adMailbox.getPassword())
						.smtpAuthorization(adMailbox.isSmtpAuthorization())
						.sendFromServer(client.isServerEMail())
						.adClientId(client.getAD_Client_ID())
						.adUserId(-1)
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
		if(Check.isEmpty(smtpHost, true))
		{
			throw new AdempiereException("Mail System not configured. Please define some AD_MailConfig or set AD_Client.SMTPHost.");
		}

		final Mailbox mailbox = Mailbox.builder()
				.smtpHost(smtpHost)
				.smtpPort(client.getSMTPPort())
				.startTLS(client.isStartTLS())
				.email(client.getRequestEMail())
				.username(client.getRequestUser())
				.password(client.getRequestUserPW())
				.smtpAuthorization(client.isSmtpAuthorization())
				.sendFromServer(client.isServerEMail())
				.adClientId(client.getAD_Client_ID())
				.adUserId(-1)
				.columnUserTo(null)
				.build();
		log.debug("Fallback to AD_Client settings: {}", mailbox);
		return mailbox;
	}

	@Override
	public EMail createEMail(final I_AD_Client client,
			final String mailCustomType,
			final String to,
			final String subject,
			final String message,
			final boolean html)
	{
		final I_AD_User from = null;
		return createEMail(client, mailCustomType, from, to, subject, message, html);
	}

	@Override
	public EMail createEMail(final I_AD_Client client,
			final String mailCustomType,
			final I_AD_User from,
			final String to,
			final String subject,
			final String message,
			final boolean html)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(client);
		final I_C_DocType docType = null; // C_DocType - Task FRESH-203 : This shall work as before
		final Mailbox mailbox = findMailBox(
				client //
				, ProcessExecutor.getCurrentOrgId() //
				, ProcessExecutor.getCurrentProcessId() //
				, docType //
				, mailCustomType //
				, from //
		);
		return createEMail(ctx, mailbox, to, subject, message, html);
	}

	@Override
	public EMail createEMail(final Properties ctx,
			final Mailbox mailbox,
			final String to,
			final String subject,
			String message,
			final boolean html)
	{
		Check.assumeNotEmpty(to, "Param 'to' is not empty");
		Check.assumeNotNull(mailbox, "Param 'mailbox' is not null");

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
	public IMailTextBuilder newMailTextBuilder(final I_R_MailText mailText)
	{
		return MailTextBuilder.of(mailText);
	}

	@Override
	public IMailTextBuilder newMailTextBuilder(final Properties ctx, final int R_MailText_ID)
	{
		final I_R_MailText mailTextDef = InterfaceWrapperHelper.create(ctx, R_MailText_ID, I_R_MailText.class, ITrx.TRXNAME_None);
		if (mailTextDef == null)
		{
			throw new AdempiereException("@Notfound@ @R_MailText_ID@=" + R_MailText_ID);
		}
		return MailTextBuilder.of(mailTextDef);
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
