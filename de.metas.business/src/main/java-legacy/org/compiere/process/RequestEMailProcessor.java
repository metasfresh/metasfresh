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

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.compiere.model.MRequest;
import org.compiere.model.MUser;
import org.compiere.util.DB;

import de.metas.email.MailAuthenticator;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *	Request Email Processor
 *	
 *  @author Carlos Ruiz based on initial work by Jorg Janke - sponsored by DigitalArmour
 *  @version $Id: RequestEMailProcessor.java,v 1.2 2006/10/23 06:01:20 cruiz Exp $
 */
public class RequestEMailProcessor extends SvrProcess
{
	private String	p_IMAPHost = null;
	private String	p_IMAPUser = null;
	private String	p_IMAPPwd = null;
	private String	p_RequestFolder = null;
	private String	p_InboxFolder = null;
	private String	p_ErrorFolder = null;
	private int C_BPartner_ID = 0;
	private int AD_User_ID = 0;
	private int AD_Role_ID = 0;
	private int SalesRep_ID = 0;
	private int R_RequestType_ID = 0;
	private String p_DefaultPriority = null;
	private String p_DefaultConfidentiality = null;

	private int noProcessed = 0;
	private int noRequest = 0;
	private int noError = 0;
	/**	Session				*/
	private Session 	m_session = null;
	/**	Store				*/
	private Store 		m_store = null;
	/**	Process Error				*/
	private static final int		ERROR = 0;
	/**	Process Request				*/
	private static final int		REQUEST = 1;
	/**	Process Workflow			*/
	// private static final int		WORKFLOW = 2;
	/**	Process Delivery Confirm	*/
	// private static final int		DELIVERY = 9;
	
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
			else if (name.equals("p_IMAPHost"))
				p_IMAPHost = ((String)para[i].getParameter());
			else if (name.equals("p_IMAPUser"))
				p_IMAPUser = ((String)para[i].getParameter());
			else if (name.equals("p_IMAPPwd"))
				p_IMAPPwd = ((String)para[i].getParameter());
			else if (name.equals("p_RequestFolder"))
				p_RequestFolder = ((String)para[i].getParameter());
			else if (name.equals("p_InboxFolder"))
				p_InboxFolder = ((String)para[i].getParameter());
			else if (name.equals("p_ErrorFolder"))
				p_ErrorFolder = ((String)para[i].getParameter());
			else if (name.equals("C_BPartner_ID"))
				C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_User_ID"))
				AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Role_ID"))
				AD_Role_ID = para[i].getParameterAsInt();
			else if (name.equals("SalesRep_ID"))
				SalesRep_ID = para[i].getParameterAsInt();
			else if (name.equals("R_RequestType_ID"))
				R_RequestType_ID = para[i].getParameterAsInt();
			else if (name.equals("p_DefaultPriority"))
				p_DefaultPriority = ((String)para[i].getParameter());
			else if (name.equals("p_DefaultConfidentiality"))
				p_DefaultConfidentiality = ((String)para[i].getParameter());
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
		
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("doIt - IMAPHost=" + p_IMAPHost +
				       " IMAPUser=" + p_IMAPUser  +
				       // " IMAPPwd=" + p_IMAPPwd +
				       " RequestFolder=" + p_RequestFolder +
				       " InboxFolder=" + p_InboxFolder +
				       " ErrorFolder=" + p_ErrorFolder);
		
		try
		{
			getSession();
			getStore();
			processInBox();
		}
		catch (Exception e)
		{
			log.error("processInBox", e);
		}
		//	Cleanup
		try
		{
			if (m_store.isConnected())
				m_store.close();
		}
		catch (Exception e)
		{
		}
		
		return "processInBox - Total=" + noProcessed + 
				" - Requests=" + noRequest + 
				" - Errors=" + noError;
	}	//	doIt
	
	/**************************************************************************
	 * 	Get Session
	 *	@return Session
	 *	@throws Exception
	 */
	private Session getSession() throws Exception
	{
		if (m_session != null)
			return m_session;
		
		//	Session
		final Properties props = new Properties(System.getProperties());
		props.put("mail.store.protocol", "smtp");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.host", p_IMAPHost);
		props.put("mail.smtp.auth","true");
		MailAuthenticator auth = MailAuthenticator.of(p_IMAPUser, p_IMAPPwd);
		//
		m_session = Session.getDefaultInstance(props, auth);
		m_session.setDebug(LogManager.isLevelFinest());
		log.debug("getSession: {}", m_session);
		return m_session;
	}	//	getSession
	
	
	/**
	 * 	Get Store
	 *	@return Store
	 *	@throws Exception
	 */
	private Store getStore() throws Exception
	{
		if (m_store != null)
			return m_store;
		if (getSession() == null)
			throw new IllegalStateException("No Session");
		
		//	Get IMAP Store
		m_store = m_session.getStore("imap");
		//	Connect
		m_store.connect();
		//
		log.debug("getStore - " + m_store);
		return m_store;
	}	//	getStore	
	
	/**
	 * 	Process InBox
	 *	@return number of processed
	 *	@throws Exception
	 */
	private void processInBox() throws Exception
	{
		//	Folder
		Folder folder;
		folder = m_store.getDefaultFolder();
		if (folder == null)
			throw new IllegalStateException("No default folder");
		//	Open Inbox
		Folder inbox = folder.getFolder(p_InboxFolder);
		if (!inbox.exists())
			throw new IllegalStateException("No Inbox");
		inbox.open(Folder.READ_WRITE);
		log.debug("processInBox - " + inbox.getName() 
			+ "; Messages Total=" + inbox.getMessageCount()
			+ "; New=" + inbox.getNewMessageCount());
		
		//	Open Request
		Folder requestFolder = folder.getFolder(p_RequestFolder);
		if (!requestFolder.exists() && !requestFolder.create(Folder.HOLDS_MESSAGES))
			throw new IllegalStateException("Cannot create Request Folder");
		requestFolder.open(Folder.READ_WRITE);

		//	Open Workflow
		// Folder workflowFolder = folder.getFolder("CWorkflow");
		// if (!workflowFolder.exists() && !workflowFolder.create(Folder.HOLDS_MESSAGES))
			// throw new IllegalStateException("Cannot create Workflow Folder");
		// workflowFolder.open(Folder.READ_WRITE);
		
		//	Open Error
		Folder errorFolder = folder.getFolder(p_ErrorFolder);
		if (!errorFolder.exists() && !errorFolder.create(Folder.HOLDS_MESSAGES))
			throw new IllegalStateException("Cannot create Error Folder");
		errorFolder.open(Folder.READ_WRITE);
		
		//	Messages
		Message[] messages = inbox.getMessages();
		/**
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.FLAGS);
		fp.add("X-Mailer");
		inbox.fetch(messages, fp);
		**/
		//
		for (int i = 0; i < messages.length; i++)
//		for (int i = messages.length-1; i >= 0; i--)	//	newest first
		{
			Message msg = messages[i];
			int result = processMessage (msg);
			if (result == REQUEST)
			{
				String[] hdrs = msg.getHeader("Message-ID");
				//	Copy to processed
				try {
					if (createRequest(msg)) {
						msg.setFlag(Flags.Flag.SEEN, true);
						msg.setFlag(Flags.Flag.ANSWERED, true);
						requestFolder.appendMessages(new Message[]{msg});
						// log movement
						log.info("message " + hdrs[0] + " moved to " + p_RequestFolder + " folder");
						log.info("message info: Sent -> " + msg.getSentDate() + " From -> " + msg.getFrom()[0].toString());
						// Delete in InBox
						msg.setFlag(Flags.Flag.DELETED, true);
						Message[] deleted = inbox.expunge();
						
						noRequest++;
					}
				} catch (Exception e) {
					log.info("message " + hdrs[0] + " threw error");
					e.printStackTrace();
				}
			}
//			else if (result == WORKFLOW)
//			{
//				msg.setFlag(Flags.Flag.SEEN, true);
//				msg.setFlag(Flags.Flag.ANSWERED, true);
//				//	Copy to processed
//				workflowFolder.appendMessages(new Message[]{msg});
//			}
//			else if (result == DELIVERY)
//			{
//				msg.setFlag(Flags.Flag.SEEN, true);
//				msg.setFlag(Flags.Flag.ANSWERED, true);
//			}
			else	//	error
			{
				errorFolder.appendMessages(new Message[]{msg});
				String[] hdrs = msg.getHeader("Message-ID");
				log.warn("message " + hdrs[0] + " moved to " + p_ErrorFolder + " folder");
				log.warn("message info: Sent -> " + msg.getSentDate() + " From -> " + msg.getFrom()[0].toString());
				noError++;
			}
			noProcessed++;
		}
		
		log.info("processInBox - Total=" + noProcessed + 
				" - Requests=" + noRequest + 
				" - Errors=" + noError);
		//	Fini
		errorFolder.close(false);
		requestFolder.close(false);
		// workflowFolder.close(false);
		//
		inbox.close(true);
	}	//	processInBox
	
	/**
	 * 	Create request
	 *	@param msg message
	 * @return 
	 *	@return Type of Message
	 * @throws MessagingException 
	 */
	private boolean createRequest(Message msg) throws MessagingException, SQLException {
		// Assign from variable
		Address[] from = msg.getFrom();
		String fromAddress;
		// Carlos Ruiz <c_ruiz@myrealbox.com>
		if (from[0].toString().indexOf('<')!= -1 && from[0].toString().indexOf('>')!= -1) {
			fromAddress = from[0].toString().substring(from[0].toString().indexOf('<')+1, from[0].toString().indexOf('>'));
			log.info("fromAddress stripped: "+fromAddress);
		} else {
			fromAddress = from[0].toString(); 
		}
		// Message-ID as documentNo
		String[] hdrs = msg.getHeader("Message-ID");
		
		// Review if the e-mail was already created, comparing Message-ID+From+body
		int retValuedup = 0;
		String sqldup = "select r_request_id from r_request "
			 + "where ad_client_id = ? "
			 + "and documentno = ? "
			 + "and startdate = ?";
		PreparedStatement pstmtdup = null;
			pstmtdup = DB.prepareStatement (sqldup, null);
			pstmtdup.setInt(1, getAD_Client_ID());
			pstmtdup.setString(2, hdrs[0].substring(0,30));
			pstmtdup.setTimestamp(3, new Timestamp(msg.getSentDate().getTime()));
			ResultSet rsdup = pstmtdup.executeQuery ();
			if (rsdup.next ())
				retValuedup = rsdup.getInt(1);
			rsdup.close ();
			pstmtdup.close ();
			pstmtdup = null;
		if (retValuedup > 0) {
			log.info("request already existed for msg -> " + hdrs[0]);
			return true;
		}
		
		// Analyze subject if Re: find the original request by subject + e-mail and add an action
		int request_upd = 0;
		String sqlupd = "SELECT r_request_id "
			 + "  FROM r_request "
			 + " WHERE ad_client_id = ? "
			 + "   AND summary LIKE 'FROM: ' || ? || '%' "
			 + "   AND (   documentno = "
			 + "              SUBSTR "
			 + "                 (?, "
			 + "                  INSTR "
			 + "                      (?, "
			 + "                       '<' "
			 + "                      ) "
			 + "                 ) "
			 + "        OR (    ? LIKE 'Re: %' "
			 + "            AND summary = "
			 + "                      'FROM: ' "
			 + "                   || ? "
			 + "                   || CHR (10) "
			 + "                   || SUBSTR (?, 5) "
			 + "           ) "
			 + "       ) ";
		PreparedStatement pstmtupd = null;
			pstmtupd = DB.prepareStatement (sqlupd, null);
			pstmtupd.setInt(1, getAD_Client_ID());
			pstmtupd.setString(2, fromAddress);
			pstmtupd.setString(3, msg.getSubject());
			pstmtupd.setString(4, msg.getSubject());
			pstmtupd.setString(5, msg.getSubject());
			pstmtupd.setString(6, fromAddress);
			pstmtupd.setString(7, msg.getSubject());
			ResultSet rsupd = pstmtupd.executeQuery ();
			if (rsupd.next ())
				request_upd = rsupd.getInt(1);
			rsupd.close ();
			pstmtupd.close ();
			pstmtupd = null;
		if (request_upd > 0) {
			log.info("msg -> " + hdrs[0] + " is an answer for req " + request_upd);
			return updateRequest(request_upd, msg);
		}
		
		MRequest req = new MRequest(getCtx(), 0, get_TrxName());
		// Subject as summary
		req.setSummary("FROM: " + fromAddress + "\n" + msg.getSubject());
		// Body as result
		req.setResult("FROM: " + from[0].toString() + "\n" + getMessage(msg));
		// Message-ID as documentNo
		if (hdrs != null)
			req.setDocumentNo(hdrs[0].substring(0,30));

		// Default request type for this process
		if (R_RequestType_ID > 0)
			req.setR_RequestType_ID(R_RequestType_ID);
		else
			req.setR_RequestType_ID();
			
		// set Default sales representative 
		if (SalesRep_ID > 0)
			req.setSalesRep_ID(SalesRep_ID);
		
		// set Default role
		if (AD_Role_ID > 0)
			req.setAD_Role_ID(AD_Role_ID);

		// Look for user via e-mail
		if (from != null)
		{
			int retValueu = -1;
			String sqlu = "SELECT ad_user_id "
				+ "  FROM ad_user "
				+ " WHERE UPPER (email) = UPPER (?) "
				+ "   AND ad_client_id = ?";
			PreparedStatement pstmtu = null;
				pstmtu = DB.prepareStatement (sqlu, null);
				pstmtu.setString(1, fromAddress);
				pstmtu.setInt(2, getAD_Client_ID());
				ResultSet rsu = pstmtu.executeQuery ();
				if (rsu.next ())
					retValueu = rsu.getInt(1);
				rsu.close ();
				pstmtu.close ();
				pstmtu = null;
			if (retValueu > 0) {
				req.setAD_User_ID(retValueu);
			} else {
				// set default user
				if (AD_User_ID > 0)
					req.setAD_User_ID(AD_User_ID);
			}
		}
		// Look BP
		if (req.getAD_User_ID() > 0) {
			MUser us = new MUser(getCtx(), req.getAD_User_ID(), get_TrxName());
			if (us.getC_BPartner_ID() > 0)
				req.setC_BPartner_ID(us.getC_BPartner_ID());
		}
		if (req.getC_BPartner_ID() <= 0 && C_BPartner_ID > 0) {
			// set default business partner
			req.setC_BPartner_ID(C_BPartner_ID);
		}
		
		// Set start date as sent date of e-mail
		req.setStartDate(new Timestamp(msg.getSentDate().getTime()));
		
		// defaults priority Medium, confidenciality partner
		if (p_DefaultConfidentiality != null) {
			req.setConfidentialType (p_DefaultConfidentiality);
			req.setConfidentialTypeEntry (p_DefaultConfidentiality);
		}
		if (p_DefaultPriority != null) {
			req.setPriority(p_DefaultPriority);
			req.setPriorityUser(p_DefaultPriority);
		}
		
		if (req.save(get_TrxName())) {
			log.info("created request " + req.getR_Request_ID() + " from msg -> " + hdrs[0]);
			return true;
		} else {
			return false;
		}
	}

	private boolean updateRequest(int request_upd, Message msg) throws MessagingException, SQLException {
		MRequest requp = new MRequest(getCtx(), request_upd, get_TrxName());
		// Body as result
		Address[] from = msg.getFrom();
		requp.setResult("FROM: " + from[0].toString() + "\n" + getMessage(msg));
		return requp.save();
	}

	/**
	 * 	Process Message
	 *	@param msg message
	 *	@return Type of Message
	 *	@throws Exception
	 */
	private int processMessage (Message msg) throws Exception
	{
		dumpEnvelope (msg);
		dumpBody (msg);
		printOut (":::::::::::::::");
		printOut (getSubject(msg));
		printOut (":::::::::::::::");
		printOut (getMessage(msg));
		printOut (":::::::::::::::");
		String delivery = getDeliveryReport(msg);
		printOut (delivery);
		printOut (":::::::::::::::");
		
	//	if (delivery != null)
	//		return DELIVERY;
		
		Address[] from;
		// FROM
		if ((from = msg.getFrom()) != null)
		{
			// send to error messages from postmaster@CONSULTDESK
			// TODO: possible enhancement - put error from accounts in a table
			if (from[0].toString().equalsIgnoreCase("postmaster@CONSULTDESK"))
				return ERROR;
		} else {
			// there is no from
			return ERROR;
		}
		
		// By now this account is to process requests
		return REQUEST; 
		
	}	//	processMessage
	
	/**
	 * 	Get Subject
	 *	@param msg message
	 *	@return subject or ""
	 */
	private String getSubject (Message msg)
	{
		try
		{
			String str = msg.getSubject();
			if (str != null)
				return str.trim();
		}
		catch (MessagingException e)
		{
			log.error("getSubject", e);
		}
		return "";
	}	//	getSubject
	
	/**
	 * 	Get Message
	 *	@param msg Message
	 *	@return message or ""
	 */
	private String getMessage (Part msg)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			//	Text
			if (msg.isMimeType("text/plain"))
			{
				sb.append(msg.getContent());
			}
			//	Other Text (e.g. html/xml) 
			else if (msg.isMimeType("text/*"))
			{
				sb.append(msg.getContent());
			}
			//	Nested
			else if (msg.isMimeType("message/rfc822"))
			{
				sb.append(msg.getContent());
			}
			//	Multi Part Alternative
			else if (msg.isMimeType("multipart/alternative"))
			{
				String plainText = null;
				String otherStuff = null;
				//
				Multipart mp = (Multipart)msg.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++)
				{
					Part part = mp.getBodyPart(i);
					Object content = part.getContent();
					if (content == null || content.toString().trim().length() == 0)
						continue;
					if (part.isMimeType("text/plain"))
						plainText = content.toString();
					else
						otherStuff = content.toString();
				}
				if (plainText != null)
					sb.append(plainText);
				else if (otherStuff != null)
					sb.append(otherStuff);
			}
			//	Multi Part
			else if (msg.isMimeType("multipart/*"))
			{
				Multipart mp = (Multipart)msg.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++)
				{
					String str = getMessage(mp.getBodyPart(i));
					if (str.length() > 0)
					{
						if (sb.length() > 0)
							sb.append("\n-----\n");
						sb.append(str);
					}
				}
			}
			else
			{
				/*
				 * If we actually want to see the data, and it's not a
				 * MIME type we know, fetch it and check its Java type.
				 */
				Object o = msg.getContent();
				if (o instanceof String)
				{
					sb.append(o);
				}
			}
		}
		catch (Exception e)
		{
			log.error("getMessage", e);
		}
		return sb.toString().trim();
	}	//	getMessage

	/**
	 * 	Get Delivery Report
	 *	@param msg message
	 *	@return delivery info or null
	 */
	private String getDeliveryReport (Part msg)
	{
		try
		{
			if (msg.isMimeType("multipart/report"))
			{
				String deliveryMessage = null;
				String otherStuff = null;
				//
				Multipart mp = (Multipart)msg.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++)
				{
					Part part = mp.getBodyPart(i);
					Object content = part.getContent();
					if (content == null)
						continue;
					if (part.isMimeType("message/*"))
						deliveryMessage = getDeliveredReportDetail (part);
					else
						otherStuff = content.toString().trim();
				}
				if (deliveryMessage != null)
					return deliveryMessage;
				return otherStuff;
			}
			else if (msg.isMimeType("message/*"))
			{
				return getDeliveredReportDetail (msg);
			}
		}
		catch (Exception e)
		{
			log.error("getDeliveryReport", e);
		}
		//	Nothing
		return null;
	}	//	getDeliveryReport
	
	/**
	 * 	Get Delivered Report Detail
	 *	@param part Mime Type message/*
	 *	@return info or null
	 *	@throws Exception
	 */
	private String getDeliveredReportDetail (Part part) throws Exception
	{
		Object content = part.getContent();
		if (content == null)
			return null;
		
		String deliveryMessage = null;
		if (content instanceof InputStream)
		{
			StringBuffer sb = new StringBuffer();
			InputStream is = (InputStream)content;
			int c;
			while ((c = is.read()) != -1)
				sb.append((char)c);
			deliveryMessage = sb.toString().trim();
		}
		else
			deliveryMessage = content.toString().trim();
		//
		if (deliveryMessage == null)
			return null;
		
		//	Final-Recipient: RFC822; jjanke@compiere.org
		int index = deliveryMessage.indexOf("Final-Recipient:");
		if (index != -1)
		{
			String finalRecipient = deliveryMessage.substring(index);
			int atIndex = finalRecipient.indexOf("@");
			if (atIndex != -1)
			{
				index = finalRecipient.lastIndexOf(' ', atIndex);
				if (index != -1)
					finalRecipient = finalRecipient.substring(index+1);
				atIndex = finalRecipient.indexOf("@");
				if (atIndex != -1)
					index = finalRecipient.indexOf(' ', atIndex);
				if (index != -1)
					finalRecipient = finalRecipient.substring(0, index);
				index = finalRecipient.indexOf('\n');
				if (index != -1)
					finalRecipient = finalRecipient.substring(0, index);
				return finalRecipient.trim();
			}
		}
		return deliveryMessage;
	}	//	getDeliveredReportDetail
	
	
	/**************************************************************************
	 * 	Print Envelope
	 *	@param m message
	 *	@throws Exception
	 */
	private void dumpEnvelope(Message m) throws Exception
	{
		printOut("-----------------------------------------------------------------");
		Address[] a;
		// FROM
		if ((a = m.getFrom()) != null)
		{ 
			for (int j = 0; j < a.length; j++)
				printOut("FROM: " + a[j].toString());
		}

		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null)
		{
			for (int j = 0; j < a.length; j++)
				printOut("TO: " + a[j].toString());
		}

		// SUBJECT
		printOut("SUBJECT: " + m.getSubject());

		// DATE
		java.util.Date d = m.getSentDate();
		printOut("SendDate: " + (d != null ? d.toString() : "UNKNOWN"));

		// FLAGS
		Flags flags = m.getFlags();
		StringBuffer sb = new StringBuffer();
		Flags.Flag[] sf = flags.getSystemFlags(); // get the system flags

		boolean first = true;
		for (int i = 0; i < sf.length; i++)
		{
			String s;
			Flags.Flag f = sf[i];
			if (f == Flags.Flag.ANSWERED)
				s = "\\Answered";
			else if (f == Flags.Flag.DELETED)
				s = "\\Deleted";
			else if (f == Flags.Flag.DRAFT)
				s = "\\Draft";
			else if (f == Flags.Flag.FLAGGED)
				s = "\\Flagged";
			else if (f == Flags.Flag.RECENT)
				s = "\\Recent";
			else if (f == Flags.Flag.SEEN)
				s = "\\Seen";
			else
				continue;	// skip it
			if (first)
				first = false;
			else
				sb.append(' ');
			sb.append(s);
		}

		String[] uf = flags.getUserFlags(); // get the user flag strings
		for (int i = 0; i < uf.length; i++)
		{
			if (first)
				first = false;
			else
				sb.append(' ');
			sb.append(uf[i]);
		}
		printOut("FLAGS: " + sb.toString());

		// X-MAILER
		String[] hdrs = m.getHeader("X-Mailer");
		if (hdrs != null)
		{
			StringBuffer sb1 = new StringBuffer("X-Mailer: ");
			for (int i = 0; i < hdrs.length; i++)
				sb1.append(hdrs[i]).append("  ");
			printOut(sb1.toString());
		}
		else
			printOut("X-Mailer NOT available");
		
		//	Message ID
		hdrs = m.getHeader("Message-ID");
		if (hdrs != null)
		{
			StringBuffer sb1 = new StringBuffer("Message-ID: ");
			for (int i = 0; i < hdrs.length; i++)
				sb1.append(hdrs[i]).append("  ");
			printOut(sb1.toString());
		}
		else
			printOut("Message-ID NOT available");
		
		//	All
		printOut("ALL HEADERs:");
		Enumeration en = m.getAllHeaders();
		while (en.hasMoreElements())
		{
			Header hdr = (Header)en.nextElement();
			printOut ("  " + hdr.getName() + " = " + hdr.getValue());
		}
		
		
		printOut("-----------------------------------------------------------------");
	}	//	printEnvelope

	/**
	 * 	Print Body
	 *	@param p
	 *	@throws Exception
	 */
	private void dumpBody (Part p) throws Exception
	{
		//	http://www.iana.org/assignments/media-types/
		printOut("=================================================================");
		printOut("CONTENT-TYPE: " + p.getContentType());
		/**
		Enumeration en = p.getAllHeaders();
		while (en.hasMoreElements())
		{
			Header hdr = (Header)en.nextElement();
			printOut ("  " + hdr.getName() + " = " + hdr.getValue());
		}
		printOut("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
		/** **/
		
		/**
		 * Using isMimeType to determine the content type avoids
		 * fetching the actual content data until we need it.
		 */
		if (p.isMimeType("text/plain"))
		{
			printOut("Plain text ---------------------------");
			printOut((String)p.getContent());
		}
		else if (p.getContentType().toUpperCase().startsWith("TEXT"))
		{
			printOut("Other text ---------------------------");
			printOut((String)p.getContent());
		}
		else if (p.isMimeType("multipart/*"))
		{
			printOut("Multipart ---------------------------");
			Multipart mp = (Multipart)p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				dumpBody(mp.getBodyPart(i));
		}
		else if (p.isMimeType("message/rfc822"))
		{
			printOut("Nested ---------------------------");
			dumpBody((Part)p.getContent());
		}
		else
		{
			/*
			 * If we actually want to see the data, and it's not a
			 * MIME type we know, fetch it and check its Java type.
			 */
			Object o = p.getContent();
			if (o instanceof String)
			{
				printOut("This is a string ---------------------------");
				printOut((String)o);
			}
			else if (o instanceof InputStream)
			{
				printOut("This is just an input stream ---------------------------");
				// TODO: process attachment
				// InputStream is = (InputStream)o;
				// int c;
				// while ((c = is.read()) != -1)
					// System.out.write(c);  // must be log. ??
			}
			else
			{
				printOut("This is an unknown type ---------------------------");
				printOut(o.toString());
			}
		}
		printOut("=================================================================");
	}	//	printBody

	/**
	 * 	Print
	 *	@param s string
	 */
	private void printOut(String s)
	{
	//    System.out.print(indentStr.substring(0, level * 2));
		// System.out.println(s);
		log.trace(s);
	}	

}	//	RequestEMailProcessor
