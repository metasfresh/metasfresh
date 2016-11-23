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
package org.compiere.process;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.compiere.model.MAsset;
import org.compiere.model.MAssetDelivery;
import org.compiere.model.MClient;
import org.compiere.model.MProductDownload;
import org.compiere.model.MUser;
import org.compiere.model.MUserMail;
import org.compiere.util.DB;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 *	Deliver Assets Electronically
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: AssetDelivery.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 * 	@author 	Michael Judd BF [ 2736995 ] - toURL() in java.io.File has been deprecated
 */
public class AssetDelivery extends JavaProcess
{
	private MClient		m_client = null;

	private int			m_A_Asset_Group_ID = 0;
	private int			m_M_Product_ID = 0;
	private int			m_C_BPartner_ID = 0;
	private int			m_A_Asset_ID = 0;
	private Timestamp	m_GuaranteeDate = null;
	private int			m_NoGuarantee_MailText_ID = 0;
	private boolean		m_AttachAsset = false;
	//
	private IMailTextBuilder m_MailText;


	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("A_Asset_Group_ID"))
				m_A_Asset_Group_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_ID"))
				m_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				m_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("A_Asset_ID"))
				m_A_Asset_ID = para[i].getParameterAsInt();
			else if (name.equals("GuaranteeDate"))
				m_GuaranteeDate = (Timestamp)para[i].getParameter();
			else if (name.equals("NoGuarantee_MailText_ID"))
				m_NoGuarantee_MailText_ID = para[i].getParameterAsInt();
			else if (name.equals("AttachAsset"))
				m_AttachAsset = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		if (m_GuaranteeDate == null)
			m_GuaranteeDate = new Timestamp (System.currentTimeMillis());
		//
		m_client = MClient.get(getCtx());
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message to be translated
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		log.info("");
		long start = System.currentTimeMillis();

		//	Test
		if (m_client.getSMTPHost() == null || m_client.getSMTPHost().length() == 0)
			throw new Exception ("No Client SMTP Info");
		if (m_client.getRequestEMail() == null)
			throw new Exception ("No Client Request User");

		//	Asset selected
		if (m_A_Asset_ID != 0)
		{
			String msg = deliverIt (m_A_Asset_ID);
			addLog (m_A_Asset_ID, null, null, msg);
			return msg;
		}
		//
		StringBuffer sql = new StringBuffer ("SELECT A_Asset_ID, GuaranteeDate "
			+ "FROM A_Asset a"
			+ " INNER JOIN M_Product p ON (a.M_Product_ID=p.M_Product_ID) "
			+ "WHERE ");
		if (m_A_Asset_Group_ID != 0)
			sql.append("a.A_Asset_Group_ID=").append(m_A_Asset_Group_ID).append(" AND ");
		if (m_M_Product_ID != 0)
			sql.append("p.M_Product_ID=").append(m_M_Product_ID).append(" AND ");
		if (m_C_BPartner_ID != 0)
			sql.append("a.C_BPartner_ID=").append(m_C_BPartner_ID).append(" AND ");
		String s = sql.toString();
		if (s.endsWith(" WHERE "))
			throw new Exception ("@RestrictSelection@");
		//	No mail to expired
		if (m_NoGuarantee_MailText_ID == 0)
		{
			sql.append("TRUNC(GuaranteeDate) >= ").append(DB.TO_DATE(m_GuaranteeDate, true));
			s = sql.toString();
		}
		//	Clean up
		if (s.endsWith(" AND "))
			s = sql.substring(0, sql.length()-5);
		//
		Statement stmt = null;
		int count = 0;
		int errors = 0;
		int reminders = 0;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(s);
			while (rs.next())
			{
				int A_Asset_ID = rs.getInt(1);
				Timestamp GuaranteeDate = rs.getTimestamp(2);

				//	Guarantee Expired
				if (GuaranteeDate != null && GuaranteeDate.before(m_GuaranteeDate))
				{
					if (m_NoGuarantee_MailText_ID != 0)
					{
						sendNoGuaranteeMail (A_Asset_ID, m_NoGuarantee_MailText_ID, get_TrxName());
						reminders++;
					}
				}
				else	//	Guarantee valid
				{
					String msg = deliverIt (A_Asset_ID);
					addLog (A_Asset_ID, null, null, msg);
					if (msg.startsWith ("** "))
						errors++;
					else
						count++;
				}
			}
  		}
		catch (Exception e)
		{
			log.error(s, e);
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null; stmt = null;
		}
		
		log.info("Count=" + count + ", Errors=" + errors + ", Reminder=" + reminders
			+ " - " + (System.currentTimeMillis()-start) + "ms");
		return "@Sent@=" + count + " - @Errors@=" + errors;
	}	//	doIt


	/**
	 * 	Send No Guarantee EMail
	 * 	@param A_Asset_ID asset
	 * 	@param R_MailText_ID mail to send
	 * 	@return message - delivery errors start with **
	 */
	private String sendNoGuaranteeMail (int A_Asset_ID, int R_MailText_ID, String trxName)
	{
		MAsset asset = new MAsset (getCtx(), A_Asset_ID, trxName);
		if (asset.getAD_User_ID() == 0)
			return "** No Asset User";
		MUser user = new MUser (getCtx(), asset.getAD_User_ID(), get_TrxName());
		if (user.getEMail() == null || user.getEMail().length() == 0)
			return "** No Asset User Email";
		
		if (m_MailText == null || m_MailText.getR_MailText_ID() != R_MailText_ID)
			m_MailText = Services.get(IMailBL.class).newMailTextBuilder(getCtx(), R_MailText_ID);
		if (m_MailText.getMailHeader() == null || m_MailText.getMailHeader().length() == 0)
			return "** No Subject";

		//	Create Mail
		EMail email = m_client.createEMail(user.getEMail(), null, null);
		m_MailText.setAD_User(user);
		m_MailText.setRecord(asset);
		String message = m_MailText.getFullMailText();
		if (m_MailText.isHtml())
			email.setMessageHTML(m_MailText.getMailHeader(), message);
		else
		{
			email.setSubject (m_MailText.getMailHeader());
			email.setMessageText (message);
		}
		final EMailSentStatus emailSentStatus = email.send();
		new MUserMail(getCtx(), m_MailText.getR_MailText_ID(), asset.getAD_User_ID(), email, emailSentStatus).save();
		if (!emailSentStatus.isSentOK())
			return "** Not delivered: " + user.getEMail() + " - " + emailSentStatus.getSentMsg();
		//
		return user.getEMail();
	}	//	sendNoGuaranteeMail

	
	/**************************************************************************
	 * 	Deliver Asset
	 * 	@param A_Asset_ID asset
	 * 	@return message - delivery errors start with **
	 */
	private String deliverIt (int A_Asset_ID)
	{
		log.debug("A_Asset_ID=" + A_Asset_ID);
		long start = System.currentTimeMillis();
		//
		MAsset asset = new MAsset (getCtx(), A_Asset_ID, get_TrxName());
		if (asset.getAD_User_ID() == 0)
			return "** No Asset User";
		MUser user = new MUser (getCtx(), asset.getAD_User_ID(), get_TrxName());
		if (user.getEMail() == null || user.getEMail().length() == 0)
			return "** No Asset User Email";
		if (asset.getProductR_MailText_ID() == 0)
			return "** Product Mail Text";
		if (m_MailText == null || m_MailText.getR_MailText_ID() != asset.getProductR_MailText_ID())
		{
			m_MailText = Services.get(IMailBL.class).newMailTextBuilder(getCtx(), asset.getProductR_MailText_ID());
		}
		if (m_MailText.getMailHeader() == null || m_MailText.getMailHeader().length() == 0)
			return "** No Subject";

		//	Create Mail
		EMail email = m_client.createEMail(user.getEMail(), null, null);
		if (!email.isValid())
		{
			asset.setHelp(asset.getHelp() + " - Invalid EMail");
			asset.setIsActive(false);
			return "** Invalid EMail: " + user.getEMail();
		}
		m_MailText.setAD_User(user);
		m_MailText.setRecord(asset);
		String message = m_MailText.getFullMailText();
		if (m_MailText.isHtml() || m_AttachAsset)
			email.setMessageHTML(m_MailText.getMailHeader(), message);
		else
		{
			email.setSubject (m_MailText.getMailHeader());
			email.setMessageText (message);
		}
		if (m_AttachAsset)
		{
			MProductDownload[] pdls = asset.getProductDownloads();
			if (pdls != null)
			{
				for (int i = 0; i < pdls.length; i++)
				{
					URI url = pdls[i].getDownloadURL(m_client.getDocumentDir());
					if (url != null)
						email.addAttachment(url);
				}
			}
			else
				log.warn("No DowloadURL for A_Asset_ID=" + A_Asset_ID);
		}
		final EMailSentStatus emailSentStatus = email.send();
		new MUserMail(getCtx(), m_MailText.getR_MailText_ID(), asset.getAD_User_ID(), email, emailSentStatus).save();
		if (!emailSentStatus.isSentOK())
			return "** Not delivered: " + user.getEMail() + " - " + emailSentStatus.getSentMsg();

		MAssetDelivery ad = asset.confirmDelivery(email, user.getAD_User_ID());
		ad.save();
		asset.save();
		//
		log.debug((System.currentTimeMillis()-start) + " ms");
		//	success
		return user.getEMail() + " - " + asset.getProductVersionNo();
	}	//	deliverIt

}	//	AssetDelivery
