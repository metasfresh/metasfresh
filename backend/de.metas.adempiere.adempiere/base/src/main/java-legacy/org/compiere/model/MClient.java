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

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.adempiere.service.IClientDAO;
import org.adempiere.service.impl.ClientDAO;
import org.adempiere.util.LegacyAdapters;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.i18n.Language;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;

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
	/** Client Info Setup Tree for Account	*/
	private int					m_AD_Tree_Account_ID;

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
		StringBuilder sb = new StringBuilder ("MClient[")
			.append(get_ID()).append("-").append(getValue())
			.append("]");
		return sb.toString();
	}	//	toString

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


	/**************************************************************************
	 * 	Create Trees and Setup Client Info
	 * 	@param language language
	 * 	@return true if created
	 */
	public boolean setupClientInfo (String language)
	{
		//	Create Trees
		String sql = null;
		if (Env.isBaseLanguage (language, "AD_Ref_List"))
		{
			sql = "SELECT Value, Name FROM AD_Ref_List WHERE AD_Reference_ID=120 AND IsActive='Y'";
		}
		else
		{
			sql = "SELECT l.Value, t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t "
				+ "WHERE l.AD_Reference_ID=120 AND l.AD_Ref_List_ID=t.AD_Ref_List_ID AND l.IsActive='Y'"
				+ " AND t.AD_Language=" + DB.TO_STRING(language);
		}

		//  Tree IDs
		int AD_Tree_Org_ID=0, AD_Tree_BPartner_ID=0, AD_Tree_Project_ID=0,
			AD_Tree_SalesRegion_ID=0, AD_Tree_Product_ID=0,
			AD_Tree_Campaign_ID=0, AD_Tree_Activity_ID=0;

		boolean success = false;
		try
		{
			PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
			ResultSet rs = stmt.executeQuery();
			MTree_Base tree = null;
			while (rs.next())
			{
				String value = rs.getString(1);
				String name = getName() + " " + rs.getString(2);
				//
				if (value.equals(X_AD_Tree.TREETYPE_Organization))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_Org_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_BPartner))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_BPartner_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_Project))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_Project_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_SalesRegion))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_SalesRegion_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_Product))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_Product_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_ElementValue))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					m_AD_Tree_Account_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_Campaign))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_Campaign_ID = tree.getAD_Tree_ID();
				}
				else if (value.equals(X_AD_Tree.TREETYPE_Activity))
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
					AD_Tree_Activity_ID = tree.getAD_Tree_ID();
				}
				// metas: adding category tree
				else if (value.equals(X_AD_Tree.TREETYPE_ProductCategory))
				{
					success = setupCategoryTree(value, name);
				}
				// metas end
				// metas: begin: ignore "Other" tree type
				else if (value.equals(X_AD_Tree.TREETYPE_Other))
				{
					success = true;
				}
				// metas: end
				else if (value.equals(X_AD_Tree.TREETYPE_Menu))
				{
					success = true;
				}
				else	//	PC (Product Category), BB (BOM)
				{
					tree = new MTree_Base (this, name, value);
					success = tree.save();
				}
				if (!success)
				{
					log.error("Tree NOT created: " + name);
					break;
				}
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e1)
		{
			log.error("Trees", e1);
			success = false;
		}

		if (!success)
		{
			return false;
		}

		//	Create ClientInfo
		MClientInfo clientInfo = new MClientInfo (this,
			AD_Tree_Org_ID, AD_Tree_BPartner_ID, AD_Tree_Project_ID,
			AD_Tree_SalesRegion_ID, AD_Tree_Product_ID,
			AD_Tree_Campaign_ID, AD_Tree_Activity_ID, get_TrxName());
		success = clientInfo.save();
		return success;
	}	//	createTrees

	/**
	 * 	Get AD_Tree_Account_ID created in setup client info
	 *	@return Account Tree ID
	 */
	public int getSetup_AD_Tree_Account_ID()
	{
		return m_AD_Tree_Account_ID;
	}	//	getSetup_AD_Tree_Account_ID

	/**
	 * 	Is Auto Archive on
	 *	@return true if auto archive
	 */
	public boolean isAutoArchive()
	{
		String aa = getAutoArchive();
		return aa != null && !aa.equals(AUTOARCHIVE_None);
	}	//	isAutoArchive

	/**************************************************************************
	 * Test EMail
	 *
	 * @return OK or error
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public String testEMail()
	{
		final ClientEMailConfig clientEmailConfig = ClientDAO.toClientEMailConfig(this);
		if(!Check.isEmpty(clientEmailConfig.getUsername()))
		{
			return "No Request EMail for " + getName();
		}
		
		final String subject = Adempiere.getName() + " EMail Test";
		final EMail email = createEMail(clientEmailConfig.getEmail(), subject, "");
		if (email == null)
		{
			return "Could not create EMail: " + getName();
		}

		final String message = Adempiere.getName() + " EMail Test\n " + email;
		email.setMessageText(message);

		try
		{
			final EMailSentStatus emailSentStatus = email.send();
			if (emailSentStatus.isSentOK())
			{
				log.info("Sent Test EMail: {}", email);
				return "OK";
			}
			else
			{
				log.warn("Could NOT send Test EMail: {}", email);
				return emailSentStatus.getSentMsg();
			}
		}
		catch (Exception ex)
		{
			log.error("Sending email failed", ex);
			return ex.getLocalizedMessage();
		}
	}	//	testEMail

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
		if (toEMail == null || toEMail.length() == 0)
		{
			log.warn("No EMail for recipient: " + to);
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
			log.error(getName() + " - " + ex.getLocalizedMessage());
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
	 * @param html
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
				log.info("Sent EMail " + subject + " to " + to);
				return true;
			}
			else
			{
				log.warn("Could NOT Send Email: " + subject 
					+ " to " + to + ": " + emailSentStatus.getSentMsg()
					+ " (" + getName() + ")");
				return false;
			}
		}
		catch (Exception ex)
		{
			log.error(getName() + " - " + ex.getLocalizedMessage());
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
		EMailAddress emailFrom = email.getFrom();
		try
		{
			return sendEmailNow(fromUserEmailConfig, to, email);
		}
		catch (Exception ex)
		{
			log.error(getName() + " - from " + emailFrom + " to " + to + ": " + ex.getLocalizedMessage(), ex);
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
				log.info("Sent Email: " + email.getSubject()
					+ " from " + from.getEmail()
					+ " to " + to.getEMail());
			}
			else
			{
				log.info("Sent Email: " + email.getSubject()
					+ " to " + to.getEMail());
			}
			return true;
		}
		else
		{
			if (from != null)
			{
				log.warn("Could NOT Send Email: " + email.getSubject()
					+ " from " + from.getEmail()
					+ " to " + to.getEMail() + ": " + emailSentStatus.getSentMsg()
					+ " (" + getName() + ")");
			}
			else
			{
				log.warn("Could NOT Send Email: " + email.getSubject()
					+ " to " + to.getEMail() + ": " + emailSentStatus.getSentMsg()
					+ " (" + getName() + ")");
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
		final UserEMailConfig from = null;
		final boolean html = false;
		return createEMail(from, to, subject, message, html);
	}

	/************
	 * Create EMail from Request User
	 * 
	 * @param to recipient
	 * @param subject sunject
	 * @param message message
	 * @param html
	 * @return EMail
	 * @deprecated please use {@link de.metas.email.MailService} instead, and extend it as required.
	 */
	@Deprecated
	public EMail createEMail (EMailAddress to, String subject, String message, boolean html)
	{
		final UserEMailConfig from = null;
		return createEMail(from, to, subject, message, html);
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
		if (userTo.getEMail() == null || userTo.getEMail().length() == 0)
		{
			log.warn("No To address: " + userTo);
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
	 * @param html
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
			
			final EMailCustomType mailCustomType = null;
			final ClientEMailConfig tenantEmailConfig = ClientDAO.toClientEMailConfig(this);
			return mailService.createEMail(tenantEmailConfig, mailCustomType, fromUserEmailConfig, to, subject, message, html);
		}
		catch (Exception ex)
		{
			log.error("Failed to create the email", ex);
			return null;
		}
	}	//	createEMail

	/**
	 * Creates a product category tree and initializes it according to the
	 * client's products (if any).
	 */
	private boolean setupCategoryTree(final String value, final String name) throws SQLException
	{
		boolean success;
		final MTree_Base tree = new MTree_Base(this, name, value);
		success = tree.save();
		if (!success) {
			return false;
		}

		// TODO: figure out if there can in any case be existing product
		// categories for a new client
		final StringBuilder where = new StringBuilder(
				"IsActive='Y' AND AD_Client_ID=");
		where.append(tree.getAD_Client_ID());
		final int[] categoryIds = PO.getAllIDs(I_M_Product_Category.Table_Name,
				where.toString(), null);

		final StringBuilder sqlInsert = new StringBuilder();
		sqlInsert.append("INSERT INTO ");
		sqlInsert.append(MTree_Base
				.getNodeTableName(X_AD_Tree.TREETYPE_ProductCategory));
		sqlInsert
				.append("(AD_Client_ID, AD_Org_ID, IsActive, CreatedBy, UpdatedBy,");
		sqlInsert.append("AD_Tree_ID, Node_ID, Parent_ID, SeqNo)");
		sqlInsert.append("VALUES(");
		sqlInsert.append(tree.getAD_Client_ID());
		sqlInsert.append(",0,'Y',0,0,");
		sqlInsert.append(tree.getAD_Tree_ID());
		sqlInsert.append(",?,?,?)");

		final PreparedStatement pstmt = DB.prepareStatement(sqlInsert
				.toString(), null);
		try {
			for (int i = 0; i < categoryIds.length; i++)
			{
				final MProductCategory currentCat = MProductCategory.get(Env.getCtx(), categoryIds[i]);
				pstmt.setInt(1, currentCat.getM_Product_Category_ID());
				pstmt.setInt(2, currentCat.getM_Product_Category_Parent_ID());
				pstmt.setInt(3, (i + 1) * 10);

				pstmt.execute();
			}
		} finally {
			DB.close(pstmt);
		}
		return success;
	}
// metas end
}	//	MClient
