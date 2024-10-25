/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Teo Sarca                                                  *
 *****************************************************************************/
package org.compiere.model;

import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.i18n.Language;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.impl.ClientDAO;
import org.adempiere.util.LegacyAdapters;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 *  Client Model
 *
 *  @author Jorg Janke
 *  @version $Id: MClient.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 *
 * @author Carlos Ruiz - globalqss
 *    integrate bug fix reported by Teo Sarca
 *    [ 1619085 ] Client setup creates duplicate trees
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1886480 ] Print Format Item Trl not updated even if not multilingual
 */
public class MClient extends X_AD_Client
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6482473737885701403L;


	/**
	 * Get client
	 *
	 * @param ctx context
	 * @param AD_Client_ID id
	 * @return client
	 * @deprecated Please use {@link IClientDAO#retriveClient(Properties, int)}
	 */
	@Deprecated
	public static MClient get (Properties ctx, int AD_Client_ID)
	{
		if (AD_Client_ID < 0)
		{
			return null;
		}

		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx, AD_Client_ID);
		return LegacyAdapters.convertToPO(client);
	}	//	get

	/**
	 * Get all clients
	 *
	 * @param ctx context
	 * @return clients
	 * @deprecated Please use {@link IClientDAO#retrieveAllClients(Properties)}
	 */
	@Deprecated
	public static MClient[] getAll(final Properties ctx)
	{
		final List<I_AD_Client> clients = Services.get(IClientDAO.class).retrieveAllClients(ctx);
		return LegacyAdapters.convertToPOArray(clients, MClient.class);
	}	// getAll

	/**
	 * Get optionally cached client
	 *
	 * @param ctx context
	 * @return client
	 * @deprecated Please use {@link IClientDAO#retriveClient(Properties)}
	 */
	@Deprecated
	public static MClient get(Properties ctx)
	{
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx);
		return LegacyAdapters.convertToPO(client);
	}	// get

	public MClient (Properties ctx, int AD_Client_ID, String trxName)
	{
		super (ctx, AD_Client_ID, trxName);
		if (is_new())
		{
			// setValue (null);
			// setName (null);
			setAD_Org_ID(0);
			setIsMultiLingualDocument(false);
			setIsSmtpAuthorization(false);
			setIsUseBetaFunctions(true);
			setIsServerEMail(false);
			setAD_Language(Language.getBaseAD_Language());
			setAutoArchive(AUTOARCHIVE_None);
			setMMPolicy(MMPOLICY_FiFo);	// F
			setIsPostImmediate(false);
			setIsCostImmediate(false);
		}
	}

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MClient (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MClient

	/**
	 * 	Simplified Constructor
	 * 	@param ctx context
	 *	@param trxName transaction
	 */
	public MClient (Properties ctx, String trxName)
	{
		this (ctx, Env.getAD_Client_ID(ctx), trxName);
	}	//	MClient

	/** Language					*/
	private Language			m_language = null;

	/**
	 *	Get SMTP Host
	 *	@return SMTP or loaclhost
	 */
	@Override
	public String getSMTPHost()
	{
		String s = super.getSMTPHost();
		if (s == null)
		{
			s = "localhost";
		}
		return s;
	}	//	getSMTPHost

	/**
	 *	Get Client Info
	 *	@return Client Info
	 */
	public I_AD_ClientInfo getInfo()
	{
		return Services.get(IClientDAO.class).retrieveClientInfo(getCtx(), getAD_Client_ID());
	}	//	getMClientInfo

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		return "MClient[" + get_ID() + "-" + getValue() + "]";
	}

	/**
	 * 	Get Language
	 *	@return client language
	 */
	public Language getLanguage()
	{
		if (m_language == null)
		{
			m_language = Language.getLanguage(getAD_Language());
			m_language = Env.verifyLanguageFallbackToBase(m_language);
		}
		return m_language;
	}	//	getLanguage


	/**
	 * 	Set AD_Language
	 *	@param AD_Language new language
	 */
	@Override
	public void setAD_Language (String AD_Language)
	{
		m_language = null;
		super.setAD_Language (AD_Language);
	}	//	setAD_Language

	/**
	 * 	Get AD_Language
	 *	@return Language
	 */
	@Override
	public String getAD_Language ()
	{
		String s = super.getAD_Language ();
		if (s == null)
		{
			return Language.getBaseAD_Language();
		}
		return s;
	}	//	getAD_Language

	/**
	 * 	Get Locale
	 *	@return locale
	 */
	public Locale getLocale()
	{
		Language lang = getLanguage();
		if (lang != null)
		{
			return lang.getLocale();
		}
		return Locale.getDefault();
	}	//	getLocale

	/**
	 * Send EMail from Request User - with trace
	 * 
	 * @param recipientUserId recipient
	 * @param subject subject
	 * @param message message
	 * @param attachment optional attachment
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public boolean sendEMail (UserId recipientUserId, String subject, String message, File attachment)
	{
		Collection<File> attachments = new ArrayList<>();
		if (attachment != null)
		{
			attachments.add(attachment);
		}
		return sendEMailAttachments(recipientUserId, subject, message, attachments);
	}

	/**
	 * Send EMail from Request User - with trace
	 * 
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	private boolean sendEMailAttachments (UserId recipientUserId, String subject, String message, Collection<File> attachments)
	{
		return sendEMailAttachments(recipientUserId, subject, message, attachments, false);
	}

	/**
	 * Send EMail from Request User - with trace
	 * 
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	private boolean sendEMailAttachments (
			final UserId recipientUserId, 
			String subject, 
			String message, 
			Collection<File> attachments, 
			boolean html)
	{
		final I_AD_User to = Services.get(IUserDAO.class).getById(recipientUserId);
		String toEMail = to.getEMail();
		if (toEMail == null || toEMail.isEmpty())
		{
			log.warn("No EMail for recipient: {}", to);
			return false;
		}
		EMail email = createEMail(null, to, subject, message, html);
		if (email == null)
		{
			return false;
		}
		email.addAttachments(attachments);
		try
		{
			return sendEmailNow(null, to, email);
		}
		catch (Exception ex)
		{
			log.error("Failed sending email: {}", email, ex);
			return false;
		}
	}	//	sendEMail

	/**
	 * Send EMail from Request User - no trace
	 * 
	 * @param to recipient email address
	 * @param subject subject
	 * @param message message
	 * @param attachment optional attachment
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public boolean sendEMail (EMailAddress to, String subject, String message, File attachment)
	{
		return sendEMail(to, subject, message, attachment, false);
	}

	/**
	 * Send EMail from Request User - no trace
	 * 
	 * @param to recipient email address
	 * @param subject subject
	 * @param message message
	 * @param attachment optional attachment
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public boolean sendEMail (EMailAddress to, String subject, String message, File attachment, boolean html)
	{
		final EMail email = createEMail(to, subject, message, html);
		if (email == null)
		{
			return false;
		}
		if (attachment != null)
		{
			email.addAttachment(attachment);
		}
		try
		{
			final EMailSentStatus emailSentStatus = email.send();
			if (emailSentStatus.isSentOK())
			{
				log.info("Sent EMail {} to {}", subject, to);
				return true;
			}
			else
			{
				log.warn("Could NOT Send Email: {} to {}: {} ({})", subject, to, emailSentStatus.getSentMsg(), getName());
				return false;
			}
		}
		catch (Exception ex)
		{
			log.error("Failed sending mail: {}", email, ex);
			return false;
		}
	}	//	sendEMail

	/**
	 * Send EMail from User
	 * 
	 * @param from sender
	 * @param to recipient
	 * @param subject subject
	 * @param message message
	 * @param attachment optional attachment
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public boolean sendEMail (UserEMailConfig from, I_AD_User to, String subject, String message, File attachment)
	{
		return sendEMail(from, to, subject, message, attachment, false);
	}

	/**
	 * Send EMail from User
	 * 
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public boolean sendEMail (UserEMailConfig fromUserEmailConfig, I_AD_User to, String subject, String message, File attachment, boolean isHtml)
	{
		EMail email = createEMail(fromUserEmailConfig, to, subject, message, isHtml);
		if (email == null)
		{
			return false;
		}

		if (attachment != null)
		{
			email.addAttachment(attachment);
		}
		try
		{
			return sendEmailNow(fromUserEmailConfig, to, email);
		}
		catch (Exception ex)
		{
			log.error("Failed sending mail: {}", email, ex);
			return false;
		}
	}	//	sendEMail

	/**
	 * Send Email Now
	 * 
	 * @param from optional from user
	 * @param to to user
	 * @param email email
	 * @return true if sent
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public boolean sendEmailNow(UserEMailConfig from, I_AD_User to, EMail email)
	{
		final EMailSentStatus emailSentStatus = email.send();
		//
		X_AD_UserMail um = new X_AD_UserMail(getCtx(), 0, null);
		um.setClientOrg(this);
		um.setAD_User_ID(to.getAD_User_ID());
		um.setSubject(email.getSubject());
		um.setMailText(email.getMessageCRLF());
		if (emailSentStatus.isSentOK())
		{
			um.setMessageID(emailSentStatus.getMessageId());
			um.setIsDelivered(X_AD_UserMail.ISDELIVERED_Yes);
		}
		else
		{
			um.setMessageID(emailSentStatus.getSentMsg());
			um.setIsDelivered(X_AD_UserMail.ISDELIVERED_No);
		}
		um.saveEx();

		//
		if (emailSentStatus.isSentOK())
		{
			if (from != null)
			{
				log.info("Sent Email: {} from {} to {}", email.getSubject(), from.getEmail(), to.getEMail());
			}
			else
			{
				log.info("Sent Email: {} to {}", email.getSubject(), to.getEMail());
			}
			return true;
		}
		else
		{
			if (from != null)
			{
				log.warn("Could NOT Send Email: {} from {} to {}: {} ({})", email.getSubject(), from.getEmail(), to.getEMail(), emailSentStatus.getSentMsg(), getName());
			}
			else
			{
				log.warn("Could NOT Send Email: {} to {}: {} ({})", email.getSubject(), to.getEMail(), emailSentStatus.getSentMsg(), getName());
			}
			return false;
		}
	}	//	sendEmailNow

	/**
	 * Create EMail from Request User
	 * 
	 * @param to recipient
	 * @param subject sunject
	 * @param message nessage
	 * @return EMail
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public EMail createEMail (EMailAddress to, String subject, String message)
	{
		return createEMail(null, to, subject, message, false);
	}

	/************
	 * Create EMail from Request User
	 * 
	 * @param to recipient
	 * @param subject sunject
	 * @param message message
	 * @return EMail
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public EMail createEMail (EMailAddress to, String subject, String message, boolean html)
	{
		return createEMail(null, to, subject, message, html);
	}
	
	/**
	 * Create EMail from User
	 * 
	 * @return EMail
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public EMail createEMail (UserEMailConfig fromUserEmailConfig, I_AD_User to, String subject, String message)
	{
		return createEMail(fromUserEmailConfig, to, subject, message, false);
	}

	/**
	 * Create EMail from User
	 * 
	 * @return EMail
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	private EMail createEMail (UserEMailConfig fromUserEmailConfig, I_AD_User userTo, String subject, String message, boolean html)
	{
		if (userTo == null)
		{
			log.warn("No To user");
			return null;
		}
		if (userTo.getEMail() == null || userTo.getEMail().isEmpty())
		{
			log.warn("No To address: {}", userTo);
			return null;
		}
		
		return createEMail(fromUserEmailConfig, EMailAddress.ofString(userTo.getEMail()), subject, message, html);

	}	//	createEMail

	/**
	 * Create EMail from User
	 * 
	 * @param fromUserEmailConfig optional sender
	 * @param to recipient
	 * @param subject sunject
	 * @param message nessage
	 * @return EMail
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public EMail createEMail (
			final UserEMailConfig fromUserEmailConfig, 
			final EMailAddress to, 
			final String subject,
			final String message,
			final boolean html)
	{
		try
		{
			final MailService mailService = Adempiere.getBean(MailService.class);
			
			final ClientEMailConfig tenantEmailConfig = ClientDAO.toClientEMailConfig(this);
			return mailService.createEMail(tenantEmailConfig, null, fromUserEmailConfig, to, subject, message, html);
		}
		catch (Exception ex)
		{
			log.error("Failed to create the email", ex);
			return null;
		}
	}	//	createEMail

	// metas end
}	//	MClient
