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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.model.MInterestArea;
import org.compiere.model.MStore;
import org.compiere.model.MUserMail;
import org.compiere.util.DB;

import com.google.common.base.Stopwatch;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.i18n.Msg;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;

/**
 *  Send Mail to Interest Area Subscribers
 *
 *  @author Jorg Janke
 *  @version $Id: SendMailText.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class SendMailText extends JavaProcess
{
	private final MailService mailService = Adempiere.getBean(MailService.class);
	
	/** What to send			*/
	private MailTemplateId mailTemplateId;
	private MailTextBuilder mailTextBuilder;

	/**	From (sender)			*/
	private UserId m_AD_User_ID = null;
	/** Client Info				*/
	private MClient			m_client = null;
	/**	From					*/
	private UserEMailConfig fromUserEmailConfig = null;
	/** Recipient List to prevent duplicate mails	*/
	private ArrayList<Integer>	m_list = new ArrayList<>();

	
	private int 			m_counter = 0;
	private int 			m_errors = 0;
	/**	To Subscribers 			*/
	private int				m_R_InterestArea_ID = -1;
	/** Interest Area			*/
	private MInterestArea 	m_ia = null;
	/** To Customer Type		*/
	private int				m_C_BP_Group_ID = -1;
	/** To Purchaser of Product	*/
	//	comes here


	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				
			}
			else if (name.equals("R_InterestArea_ID"))
			{
				m_R_InterestArea_ID = element.getParameterAsInt();
			}
			else if (name.equals("R_MailText_ID"))
			{
				mailTemplateId = MailTemplateId.ofRepoId(element.getParameterAsInt());
			}
			else if (name.equals("C_BP_Group_ID"))
			{
				m_C_BP_Group_ID = element.getParameterAsInt();
			}
			else if (name.equals("AD_User_ID"))
			{
				m_AD_User_ID = element.getParameterAsRepoId(UserId::ofRepoIdOrNull);
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt()
	{
		//	Mail Text
		this.mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId);
		
		//	Client Info
		m_client = MClient.get (getCtx());
		if (m_client.getAD_Client_ID() == 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Client_ID@");
		}
		if (m_client.getSMTPHost() == null || m_client.getSMTPHost().length() == 0)
		{
			throw new AdempiereException ("No SMTP Host found");
		}
		//
		if (m_AD_User_ID != null)
		{
			fromUserEmailConfig = Services.get(IUserBL.class).getEmailConfigById(m_AD_User_ID);
		}
		log.debug("From {}", fromUserEmailConfig);
		final Stopwatch stopwatch = Stopwatch.createStarted();
		
		if (m_R_InterestArea_ID > 0)
		{
			sendInterestArea();
		}
		if (m_C_BP_Group_ID > 0)
		{
			sendBPGroup();
		}

		return "@Created@=" + m_counter + ", @Errors@=" + m_errors + " - " + stopwatch;
	}

	/**
	 * 	Send to InterestArea
	 */
	private void sendInterestArea()
	{
		log.info("R_InterestArea_ID=" + m_R_InterestArea_ID);
		m_ia = MInterestArea.get(getCtx(), m_R_InterestArea_ID);
		String unsubscribe = null;
		if (m_ia.isSelfService())
		{
			unsubscribe = "\n\n---------.----------.----------.----------.----------.----------\n"
				+ Msg.getElement(getCtx(), "R_InterestArea_ID")
				+ ": " + m_ia.getName()
				+ "\n" + Msg.getMsg(getCtx(), "UnsubscribeInfo")
				+ "\n";
			MStore[] wstores = MStore.getOfClient(m_client);
			int index = 0;
			for (int i = 0; i < wstores.length; i++)
			{
				if (wstores[i].isDefault())
				{
					index = i;
					break;
				}
			}
			if (wstores.length > 0)
			{
				unsubscribe += wstores[index].getWebContext(true);
			}
		}

		//
		String sql = "SELECT u.Name, u.EMail, u.AD_User_ID "
			+ "FROM R_ContactInterest ci"
			+ " INNER JOIN AD_User u ON (ci.AD_User_ID=u.AD_User_ID) "
			+ "WHERE ci.IsActive='Y' AND u.IsActive='Y'"
			+ " AND ci.OptOutDate IS NULL"
			+ " AND u.EMail IS NOT NULL"
			+ " AND ci.R_InterestArea_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, m_R_InterestArea_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Boolean ok = sendIndividualMail (rs.getString(1), rs.getInt(3), unsubscribe);
				if (ok == null)
				{
					
				}
				else if (ok.booleanValue())
				{
					m_counter++;
				}
				else
				{
					m_errors++;
				}
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(sql, ex);
		}
		//	Clean Up
		try
		{
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		m_ia = null;
	}	//	sendInterestArea
	
	
	/**
	 * 	Send to BPGroup
	 */
	private void sendBPGroup()
	{
		log.info("C_BP_Group_ID=" + m_C_BP_Group_ID);
		String sql = "SELECT u.Name, u.EMail, u.AD_User_ID "
			+ "FROM AD_User u"
			+ " INNER JOIN C_BPartner bp ON (u.C_BPartner_ID=bp.C_BPartner_ID) "
			+ "WHERE u.IsActive='Y' AND bp.IsActive='Y'"
			+ " AND u.EMail IS NOT NULL"
			+ " AND bp.C_BP_Group_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, m_C_BP_Group_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				Boolean ok = sendIndividualMail (rs.getString(1), rs.getInt(3), null);
				if (ok == null)
				{
					
				}
				else if (ok.booleanValue())
				{
					m_counter++;
				}
				else
				{
					m_errors++;
				}
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(sql, ex);
		}
		//	Clean Up
		try
		{
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
	}	//	sendBPGroup
	
	/**
	 * 	Send Individual Mail
	 *	@param Name user name
	 *	@param AD_User_ID user
	 *	@param unsubscribe unsubscribe message
	 *	@return true if mail has been sent
	 */
	private Boolean sendIndividualMail (String Name, int AD_User_ID, String unsubscribe)
	{
		//	Prevent two email
		Integer ii = new Integer (AD_User_ID);
		if (m_list.contains(ii))
		{
			return null;
		}
		m_list.add(ii);
		//
		I_AD_User to = Services.get(IUserDAO.class).getById(AD_User_ID);
		mailTextBuilder.bpartnerContact(to);		//	parse context
		String message = mailTextBuilder.getFullMailText();
		//	Unsubscribe
		if (unsubscribe != null)
		{
			message += unsubscribe;
		}
		//
		EMail email = m_client.createEMail(fromUserEmailConfig, to, mailTextBuilder.getMailHeader(), message);
		if (mailTextBuilder.isHtml())
		{
			email.setMessageHTML(mailTextBuilder.getMailHeader(), message);
		}
		else
		{
			email.setSubject (mailTextBuilder.getMailHeader());
			email.setMessageText (message);
		}
		if (!email.isValid() && !email.checkValid())
		{
			log.warn("NOT VALID - " + email);
			to.setIsActive(false);
			addDescription(to, "Invalid EMail");
			InterfaceWrapperHelper.save(to);
			return Boolean.FALSE;
		}

		final EMailSentStatus emailSentStatus = email.send();
		final boolean OK = emailSentStatus.isSentOK();
		new MUserMail(getCtx(),
				mailTemplateId.getRepoId(), 
				AD_User_ID, 
				email, 
				emailSentStatus)
		.save();
		//
		if (OK)
		{
			log.debug(to.getEMail());
		}
		else
		{
			log.warn("FAILURE - " + to.getEMail());
		}
		addLog(0, null, null, (OK ? "@OK@" : "@ERROR@") + " - " + to.getEMail());
		return new Boolean(OK);
	}	//	sendIndividualMail
	
	private static final void addDescription(final I_AD_User user, String description)
	{
		if (description == null || description.length() == 0)
		{
			return;
		}
		String descr = user.getDescription();
		if (descr == null || descr.length() == 0)
		{
			user.setDescription(description);
		}
		else
		{
			user.setDescription(descr + " - " + description);
		}
	}	// addDescription


}	//	SendMailText
