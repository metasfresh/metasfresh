/**
 *
 */
package de.metas.notification.impl;

import java.util.List;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.ProcessUtil;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_DocType;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.notification.IMailBL;
import de.metas.notification.IMailDAO;
import de.metas.session.jaxrs.IServerService;

/**
 * @author Cristina Ghita, Metas.RO
 * @see http://dewiki908/mediawiki/index.php/US901:_Postfächer_pro_Organisation_einstellen_können_%282010110510000031 %29
 */
public class MailBL implements IMailBL
{
	private final Logger log = LogManager.getLogger(getClass());

	private static final String SYSCONFIG_DebugMailTo = "org.adempiere.user.api.IUserBL.DebugMailTo";

	private static class Mailbox implements IMailbox
	{
		private final String smtpHost;
		private final String email;
		private final String username;
		private final String password;
		private final boolean isSmtpAuthorization;
		private final boolean isSendFromServer;
		private final int adClientId;
		private final int adUserId;

		public Mailbox(final String smtpHost, final String email,
				final String username, final String password,
				final boolean isSmtpAuthorization,
				final boolean isSendFromServer,
				final int AD_Client_ID, final int AD_User_ID)
		{
			this.smtpHost = smtpHost;
			this.email = email;
			this.username = username;
			this.password = password;
			this.isSmtpAuthorization = isSmtpAuthorization;
			this.isSendFromServer = isSendFromServer;
			adClientId = AD_Client_ID;
			adUserId = AD_User_ID;
		}

		@Override
		public String getSmtpHost()
		{
			return smtpHost;
		}

		@Override
		public String getEmail()
		{
			return email;
		}

		@Override
		public String getUsername()
		{
			return username;
		}

		@Override
		public String getPassword()
		{
			return password;
		}

		@Override
		public boolean isSmtpAuthorization()
		{
			return isSmtpAuthorization;
		}

		@Override
		public boolean isSendFromServer()
		{
			return isSendFromServer;
		}

		@Override
		public int getAD_Client_ID()
		{
			return adClientId;
		}

		@Override
		public int getAD_User_ID()
		{
			return adUserId;
		}

		@Override
		public String toString()
		{
			return "Mailbox [smtpHost=" + smtpHost
					+ ", email=" + email
					+ ", username=" + username
					+ ", password=" + (password == null ? "(empty)" : "********")
					+ ", isSmtpAuthorization=" + isSmtpAuthorization
					+ ", isSendFromServer=" + isSendFromServer
					+ ", clientId=" + adClientId
					+ ", userId=" + adUserId
					+ "]";
		}
	}

	// @Cached
	@Override
	public IMailbox findMailBox(final I_AD_Client client,
			final int AD_Org_ID,
			final int AD_Process_ID,
			final I_C_DocType docType,
			final String customType,
			final I_AD_User user)
	{
		IMailbox mailbox = findMailBox(client, AD_Org_ID, AD_Process_ID, docType, customType);
		Check.errorIf(mailbox == null, "Unable to find IMailbox for AD_Client={}, AD_Org_ID={}, AD_Process_ID={}, customeType={}",
				client, AD_Org_ID, AD_Process_ID, customType);
		if (user != null)
		{
			mailbox = new Mailbox(mailbox.getSmtpHost(),
					user.getEMail(),
					user.getEMailUser(),
					user.getEMailUserPW(),
					mailbox.isSmtpAuthorization(),
					mailbox.isSendFromServer(),
					mailbox.getAD_Client_ID(),
					user.getAD_User_ID());
		}
		return mailbox;
	}

	public IMailbox findMailBox(final I_AD_Client client, final int orgID, final int processID, final I_C_DocType docType, final String customType)
	{
		log.debug("Looking for AD_Client_ID=" + client.getAD_Client_ID() + ", AD_Org_ID=" + orgID + ", AD_Process_ID=" + processID + ", customType=" + customType);

		final List<I_AD_MailConfig> configs = Services.get(IMailDAO.class).retrieveMailCOnfigs(client, orgID, processID, docType, customType);

		for (final I_AD_MailConfig config : configs)
		{
			if (config.getAD_Org_ID() == orgID
					|| config.getAD_Org_ID() != orgID && config.getAD_Org_ID() == 0)
			{
				final I_AD_MailBox adMailbox = config.getAD_MailBox();
				final Mailbox mailbox = new Mailbox(adMailbox.getSMTPHost(),
						adMailbox.getEMail(),
						adMailbox.getUserName(),
						adMailbox.getPassword(),
						adMailbox.isSmtpAuthorization(),
						client.isServerEMail(),
						client.getAD_Client_ID(),
						-1 // AD_User_ID
				);
				log.debug("Found: " + getSummary(config) + "=>" + mailbox);
				return mailbox;
			}
		}

		final Mailbox mailbox = new Mailbox(client.getSMTPHost(),
				client.getRequestEMail(),
				client.getRequestUser(),
				client.getRequestUserPW(),
				client.isSmtpAuthorization(),
				client.isServerEMail(),
				client.getAD_Client_ID(),
				-1 // AD_User_ID
		);
		log.debug("Fallback to AD_Client settings: " + mailbox);
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
		final Properties ctx = POWrapper.getCtx(client);
		final IMailbox mailbox = findMailBox(
				client, ProcessUtil.getCurrentOrgId(), ProcessUtil.getCurrentProcessId(), null // C_DocType - Task FRESH-203 : This shall work as before
				, mailCustomType, from);
		return createEMail(ctx, mailbox, to, subject, message, html);
	}

	@Override
	public EMail createEMail(final Properties ctx,
			final IMailbox mailbox,
			final String to,
			final String subject,
			String message,
			final boolean html)
	{
		Check.assumeNotEmpty(to, "Param 'to' is not empty");
		Check.assumeNotNull(mailbox, "Param 'mailbox' is not null");

		if (mailbox.getEmail() == null
				|| mailbox.getUsername() == null
				// is SMTP authorisation and password is null - teo_sarca [ 1723309 ]
				|| mailbox.isSmtpAuthorization() && mailbox.getPassword() == null)
		{
			throw new AdempiereException("Mailbox incomplete: " + mailbox);
		}

		EMail email = null;
		if (mailbox.isSendFromServer() && Ini.isClient())
		{
			log.debug("Creating on server");
			final IServerService server = Services.get(IServerService.class);
			try
			{
				if (html && message != null)
				{
					message = EMail.HTML_MAIL_MARKER + message;
				}

				if (mailbox.getAD_User_ID() < 0)
				{
					email = server.createEMail(Env.getRemoteCallCtx(ctx), mailbox.getAD_Client_ID(),
							to, subject, message);
				}
				else
				{
					email = server.createEMail(Env.getRemoteCallCtx(ctx), mailbox.getAD_Client_ID(),
							mailbox.getAD_User_ID(),
							to, subject, message);
				}
			}
			catch (final Exception ex)
			{
				log.error("" + mailbox + " - AppsServer error: " + ex.getLocalizedMessage(), ex);
			}
		}

		if (email == null)
		{
			log.debug("Creating on client");
			email = new EMail(ctx, mailbox.getSmtpHost(), mailbox.getEmail(), to, subject, message, html);
			if (mailbox.isSmtpAuthorization())
			{
				email.createAuthenticator(mailbox.getUsername(), mailbox.getPassword());
			}
		}
		return email;
	}

	public String getSummary(final I_AD_MailConfig config)
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

	public String getSummary(final I_AD_MailBox mailbox)
	{
		return mailbox.getClass().getSimpleName() + "["
				+ "SMTPHost=" + mailbox.getSMTPHost()
				+ ", EMail=" + mailbox.getEMail()
				+ ", Password=" + (Check.isEmpty(mailbox.getPassword()) ? "(empty)" : "******")
				+ ", IsSMTPAuth=" + mailbox.isSmtpAuthorization()
				+ ", AD_MailBox_ID=" + mailbox.getAD_MailBox_ID()
				+ "]";
	}

	@Override
	public InternetAddress getDebugMailToAddressOrNull(final Properties ctx)
	{
		String emailStr = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_DebugMailTo,
				null,           // defaultValue
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
			log.warn("Invalid email address: " + emailStr, e.getLocalizedMessage());
			return null;
		}

		return email;
	}

	@Override
	public void send(final EMail email)
	{
		final String msg = email.send();
		if (!msg.equals(EMail.SENT_OK))
		{
			throw new EMailSendException(msg, email.isSentConnectionError());
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

	public static class EMailSendException extends AdempiereException
	{
		/**
		 *
		 */
		private static final long serialVersionUID = -4519372831111638967L;

		private final boolean connectionError;

		public EMailSendException(final String msg, final boolean connectionError)
		{
			super(msg);
			this.connectionError = connectionError;
		}

		public boolean isConnectionError()
		{
			return connectionError;
		}
	}
}
