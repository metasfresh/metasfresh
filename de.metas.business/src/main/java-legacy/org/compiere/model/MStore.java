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
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.email.Mailbox;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Web Store
 *
 * @author Jorg Janke
 * @version $Id: MStore.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MStore extends X_W_Store
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5836212836465405633L;

	/**
	 * Get WStore from Cache
	 *
	 * @param ctx context
	 * @param W_Store_ID id
	 * @return WStore
	 */
	public static MStore get(Properties ctx, int W_Store_ID)
	{
		Integer key = new Integer(W_Store_ID);
		MStore retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MStore(ctx, W_Store_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	}	// get

	/**
	 * Get WStore from Cache
	 *
	 * @param ctx context
	 * @param contextPath web server context path
	 * @return WStore
	 */
	public static MStore get(Properties ctx, String contextPath)
	{
		MStore wstore = null;
		Iterator<MStore> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			wstore = it.next();
			if (wstore.getWebContext().equals(contextPath))
				return wstore;
		}

		// Search by context
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM W_Store WHERE WebContext=?";
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, contextPath);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				wstore = new MStore(ctx, rs, null);
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		// Try client
		if (wstore == null)
		{
			sql = "SELECT * FROM W_Store WHERE AD_Client_ID=? AND IsActive='Y' ORDER BY W_Store_ID";
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, Env.getAD_Client_ID(ctx));
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					wstore = new MStore(ctx, rs, null);
					s_log.warn("Context " + contextPath
							+ " Not found - Found via AD_Client_ID=" + Env.getAD_Client_ID(ctx));
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				s_log.error(sql, e);
			}
			try
			{
				if (pstmt != null)
					pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				pstmt = null;
			}
		}
		// Nothing
		if (wstore == null)
			return null;

		// Save
		Integer key = new Integer(wstore.getW_Store_ID());
		s_cache.put(key, wstore);
		return wstore;
	}	// get

	/**
	 * Get active Web Stores of Clieny
	 *
	 * @param client client
	 * @return array of web stores
	 */
	public static MStore[] getOfClient(MClient client)
	{
		ArrayList<MStore> list = new ArrayList<MStore>();
		String sql = "SELECT * FROM W_Store WHERE AD_Client_ID=? AND IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, client.get_TrxName());
			pstmt.setInt(1, client.getAD_Client_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MStore(client.getCtx(), rs, client.get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		MStore[] retValue = new MStore[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getOfClient

	/**
	 * Get Active Web Stores
	 *
	 * @return cached web stores
	 */
	public static MStore[] getActive()
	{
		s_log.info("");
		try
		{
			Collection<MStore> cc = s_cache.values();
			Object[] oo = cc.toArray();
			for (int i = 0; i < oo.length; i++)
				s_log.info(i + ": " + oo[i]);
			MStore[] retValue = new MStore[oo.length];
			for (int i = 0; i < oo.length; i++)
				retValue[i] = (MStore)oo[i];
			return retValue;
		}
		catch (Exception e)
		{
			s_log.error(e.toString());
		}
		return new MStore[] {};
	}	// getActive

	/** Cache */
	private static CCache<Integer, MStore> s_cache = new CCache<Integer, MStore>("W_Store", 2);
	/** Logger */
	private static Logger s_log = LogManager.getLogger(MStore.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param W_Store_ID id
	 * @param trxName trx
	 */
	public MStore(Properties ctx, int W_Store_ID, String trxName)
	{
		super(ctx, W_Store_ID, trxName);
		if (W_Store_ID == 0)
		{
			setIsDefault(false);
			setIsMenuAssets(true);	// Y
			setIsMenuContact(true);	// Y
			setIsMenuInterests(true);	// Y
			setIsMenuInvoices(true);	// Y
			setIsMenuOrders(true);	// Y
			setIsMenuPayments(true);	// Y
			setIsMenuRegistrations(true);	// Y
			setIsMenuRequests(true);	// Y
			setIsMenuRfQs(true);	// Y
			setIsMenuShipments(true);	// Y

			// setC_PaymentTerm_ID (0);
			// setM_PriceList_ID (0);
			// setM_Warehouse_ID (0);
			// setName (null);
			// setSalesRep_ID (0);
			// setURL (null);
			// setWebContext (null);
		}
	}	// MWStore

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName trx
	 */
	public MStore(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MWStore

	/** The Messages */
	private MMailMsg[] m_msgs = null;

	/**
	 * Get Web Context
	 *
	 * @param full if true fully qualified
	 * @return web context
	 */
	public String getWebContext(boolean full)
	{
		if (!full)
			return super.getURL();
		String url = super.getURL();
		if (url == null || url.length() == 0)
			url = "http://localhost";
		if (url.endsWith("/"))
			url += url.substring(0, url.length() - 1);
		return url + getWebContext();	// starts with /
	}	// getWebContext

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("WStore[");
		sb.append(getWebContext(true))
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true if can be saved
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Context to start with /
		if (!getWebContext().startsWith("/"))
			setWebContext("/" + getWebContext());

		// Org to Warehouse
		if (newRecord || is_ValueChanged("M_Warehouse_ID") || getAD_Org_ID() == 0)
		{
			MWarehouse wh = new MWarehouse(getCtx(), getM_Warehouse_ID(), get_TrxName());
			setAD_Org_ID(wh.getAD_Org_ID());
		}

		String url = getURL();
		if (url == null)
			url = "";
		boolean urlOK = url.startsWith("http://") || url.startsWith("https://");
		if (!urlOK) // || url.indexOf("localhost") != -1)
		{
			throw new FillMandatoryException("URL");
		}

		return true;
	}	// beforeSave

	/************
	 * Create EMail from Request User
	 *
	 * @param to recipient
	 * @param subject sunject
	 * @param message nessage
	 * @return EMail
	 */
	private EMail createEMail(final String to, final String subject, final String message)
	{
		if (to == null || to.length() == 0)
		{
			log.warn("No To");
			return null;
		}
		//
		
		final String mailCustomType = null;
		final boolean html = false;
		final EMail email = Services.get(IMailBL.class).createEMail(getCtx(), getMailbox(), mailCustomType, subject, message, html);
		
		// Bcc
		email.addBcc(getWStoreEMail());
		//
		return email;
	}	// createEMail
	
	private final Mailbox getMailbox()
	{
		final I_AD_Client client = getAD_Client();
		final boolean isSmtpAuthorization = client.isSmtpAuthorization();
		final Mailbox.Builder mailboxBuilder = Mailbox.builder()
				.setSmtpHost(client.getRequestEMail())
				.setEmail(client.getRequestEMail())
				.setUsername(client.getRequestUser())
				.setPassword(client.getRequestUserPW())
				.setSmtpAuthorization(isSmtpAuthorization)
				.setSendFromServer(client.isServerEMail())
				.setAD_Client_ID(client.getAD_Client_ID());
		
		final String wstoreEMail = getWStoreEMail();
		if(!Check.isEmpty(wstoreEMail, true))
		{
			mailboxBuilder.setEmail(wstoreEMail);
		}

		// Authorization
		if (isSmtpAuthorization
				&& !Check.isEmpty(wstoreEMail, true)
				&& getWStoreUser() != null
				&& getWStoreUserPW() != null)
		{
			mailboxBuilder.setUsername(getWStoreUser());
			mailboxBuilder.setPassword(getWStoreUserPW());
		}

		return mailboxBuilder.build();
	}
	
	/**
	 * Test WebStore EMail
	 *
	 * @return OK or error
	 */
	public String testEMail()
	{
		if (getWStoreEMail() == null || getWStoreEMail().length() == 0)
			return "No Web Store EMail for " + getName();
		//
		EMail email = createEMail(getWStoreEMail(),
				"Adempiere WebStore EMail Test",
				"Adempiere WebStore EMail Test: " + toString());
		if (email == null)
			return "Could not create Web Store EMail: " + getName();
		try
		{
			final EMailSentStatus emailSentStatus = email.send();
			if (emailSentStatus.isSentOK())
			{
				log.info("Sent Test EMail to " + getWStoreEMail());
				return "OK";
			}
			else
			{
				log.warn("Could NOT send Test Email to " + getWStoreEMail() + ": " + emailSentStatus.getSentMsg());
				return emailSentStatus.getSentMsg();
			}
		}
		catch (Exception ex)
		{
			log.error(getName() + " - " + ex.getLocalizedMessage());
			return ex.getLocalizedMessage();
		}
	}	// testEMail

	/**
	 * Get Messages
	 *
	 * @param reload reload data
	 * @return array of messages
	 */
	public MMailMsg[] getMailMsgs(boolean reload)
	{
		if (m_msgs != null && !reload)
			return m_msgs;
		ArrayList<MMailMsg> list = new ArrayList<MMailMsg>();
		//
		String sql = "SELECT * FROM W_MailMsg WHERE W_Store_ID=? ORDER BY MailMsgType";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getW_Store_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MMailMsg(getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		m_msgs = new MMailMsg[list.size()];
		list.toArray(m_msgs);
		return m_msgs;
	}	// getMailMsgs

	/**
	 * Get Mail Msg and if not found create it
	 *
	 * @param MailMsgType mail message type
	 * @return message
	 */
	public MMailMsg getMailMsg(String MailMsgType)
	{
		if (m_msgs == null)
			getMailMsgs(false);

		// existing msg
		for (int i = 0; i < m_msgs.length; i++)
		{
			if (m_msgs[i].getMailMsgType().equals(MailMsgType))
				return m_msgs[i];
		}

		// create missing
		if (createMessages() == 0)
		{
			log.error("Not created/found: " + MailMsgType);
			return null;
		}
		getMailMsgs(true);
		// try again
		for (int i = 0; i < m_msgs.length; i++)
		{
			if (m_msgs[i].getMailMsgType().equals(MailMsgType))
				return m_msgs[i];
		}

		// nothing found
		log.error("Not found: " + MailMsgType);
		return null;
	}	// getMailMsg

	/**************************************************************************
	 * Create (missing) Messages
	 *
	 * @return number of messages created
	 */
	public int createMessages()
	{
		String[][] initMsgs = new String[][]
		{
				new String[] { MMailMsg.MAILMSGTYPE_UserVerification,
						"EMail Verification",
						"EMail Verification ",
						"Dear ",
						"\nYou requested the Verification Code: ",
						"\nPlease enter the verification code to get access." },
				new String[] { MMailMsg.MAILMSGTYPE_UserPassword,
						"Password Request",
						"Password Request ",
						"Dear ",
						"\nWe received a 'Send Password' request from: ",
						"\nYour password is: " },
				new String[] { MMailMsg.MAILMSGTYPE_Subscribe,
						"Subscription New",
						"New Subscription ",
						"Dear ",
						"\nYou requested to be added to the list: ",
						"\nThanks for your interest." },
				new String[] { MMailMsg.MAILMSGTYPE_UnSubscribe,
						"Subscription Removed",
						"Remove Subscription ",
						"Dear ",
						"\nYou requested to be removed from the list: ",
						"\nSorry to see you go.  This is effictive immediately." },
				new String[] { MMailMsg.MAILMSGTYPE_OrderAcknowledgement,
						"Order Acknowledgement",
						"Adempiere Web - Order ",
						"Dear ",
						"\nThank you for your purchase: ",
						"\nYou can view your Orders, Invoices, Payments in the Web Store."
								+ "\nFrom there, you also download your Assets (Documentation, etc.)" },
				new String[] { MMailMsg.MAILMSGTYPE_PaymentAcknowledgement,
						"Payment Success",
						"Adempiere Web - Payment ",
						"Dear ",
						"\nThank you for your payment of ",
						"\nYou can view your Orders, Invoices, Payments in the Web Store."
								+ "\nFrom there you also download your Assets (Documentation, etc.)" },
				new String[] { MMailMsg.MAILMSGTYPE_PaymentError,
						"Payment Error",
						"Adempiere Web - Declined Payment ",
						"Dear ",
						"\nUnfortunately your payment was declined: ",
						"\nPlease check and try again. You can pay later by going to 'My Orders' or 'My Invoices' - or by directly creating a payment in 'My Payments'" },
				new String[] { MMailMsg.MAILMSGTYPE_Request,
						"Request",
						"Request ",
						"Dear ",
						"\nThank you for your request: " + MRequest.SEPARATOR,
						MRequest.SEPARATOR + "\nPlease check back for updates." },

				new String[] { MMailMsg.MAILMSGTYPE_UserAccount,
						"Welcome Message",
						"Welcome",
						"Welcome to our Web Store",
						"This is the Validation Code to access information:",
						"" },
		};

		if (m_msgs == null)
			getMailMsgs(false);
		if (m_msgs.length == initMsgs.length)	// may create a problem if user defined own ones - unlikely
			return 0;		// nothing to do

		int counter = 0;
		for (int i = 0; i < initMsgs.length; i++)
		{
			boolean found = false;
			for (int m = 0; m < m_msgs.length; m++)
			{
				if (initMsgs[i][0].equals(m_msgs[m].getMailMsgType()))
				{
					found = true;
					break;
				}
			}	// for all existing msgs
			if (found)
				continue;
			MMailMsg msg = new MMailMsg(this, initMsgs[i][0], initMsgs[i][1],
					initMsgs[i][2], initMsgs[i][3], initMsgs[i][4], initMsgs[i][5]);
			if (msg.save())
				counter++;
			else
				log.error("Not created MailMsgType=" + initMsgs[i][0]);
		}	// for all initMsgs

		log.info("#" + counter);
		m_msgs = null;		// reset
		return counter;
	}	// createMessages

}	// MWStore
