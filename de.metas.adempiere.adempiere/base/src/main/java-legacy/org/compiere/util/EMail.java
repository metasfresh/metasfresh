/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.sun.mail.smtp.SMTPMessage;

import de.metas.logging.LogManager;
import de.metas.notification.IMailBL;

/**
 * EMail Object. Resources: http://java.sun.com/products/javamail/index.html http://java.sun.com/products/javamail/FAQ.html
 *
 * <p>
 * When I try to send a message, I get javax.mail.SendFailedException: 550 Unable to relay for my-address <br>
 * This is an error reply from your SMTP mail server. It indicates that your mail server is not configured to allow you to send mail through it.
 *
 * @author Jorg Janke
 * @version $Id: EMail.java,v 1.4 2006/07/30 00:54:35 jjanke Exp $
 * @author Michael Judd BF [ 2736995 ] - toURL() in java.io.File has been depreciated
 */
public final class EMail implements Serializable
{
	private static final long serialVersionUID = 9050603098727350152L;
	
	// use in server bean
	public final static String HTML_MAIL_MARKER = "ContentType=text/html;";

	/**
	 * Full Constructor
	 *
	 * @param ctx context
	 * @param smtpHost The mail server
	 * @param from Sender's EMail address
	 * @param to Recipient EMail address
	 * @param subject Subject of message
	 * @param message The message
	 * @param html html email
	 */
	public EMail(
			final Properties ctx,
			final String smtpHost,
			final String from,
			final String to,
			final String subject,
			final String message,
			final boolean html)
	{
		super();
		this._ctx = ctx;
		
		setSmtpHost(smtpHost);
		setFrom(from);
		addTo(to);
		
		if (Check.isEmpty(subject, true))
		{
			setSubject(".");	// pass validation
		}
		else
		{
			setSubject(subject);
		}
		
		if(!Check.isEmpty(message, true))
		{
			if (html)
			{
				// setMessageHTML(subject, message);
				setMessageHTML(message); // metas-2009_0017_AP1_G41: do not include subject in body
			}
			else
			{
				setMessageText(message);
			}
		}
		
		_valid = checkValid();
	}

	/** From Address */
	private InternetAddress _from;
	/** To Address */
	private List<InternetAddress> _to;
	/** CC Addresses */
	private List<InternetAddress> _cc;
	/** BCC Addresses */
	private List<InternetAddress> _bcc;
	/** Reply To Address */
	private InternetAddress _replyTo;
	/** Mail Subject */
	private String _subject;
	/** Mail Plain Message */
	private String _messageText;
	/** Mail HTML Message */
	private String _messageHTML;
	/** Mail SMTP Server */
	private String _smtpHost;
	/** Mail SMTP Server Port */
	// @TODO - make port configurable - private int m_smtpPort = 0;
	/** SMTP enable start TLS */
	// @TODO - make tls configurable - private boolean m_smtpStarttlsEnable = false;
	/** Attachments */
	private List<Object> _attachments;
	/** UserName and Password */
	private EMailAuthenticator _auth = null;
	/** Context - may be null */
	private final Properties _ctx;

	//
	// Status
	/** Info Valid */
	private boolean _valid = false;
	/** Send result Message */
	private String _sentMsg = null;
	private boolean _sentConnectionError = false; // metas
	private String _messageId = null;

	/** Mail Sent OK Status */
	public static final String SENT_OK = new String("OK");

	/** Logger */
	protected static Logger log = LogManager.getLogger(EMail.class);

	/**
	 * Send Mail direct
	 *
	 * @return OK or error message
	 */
	public String send()
	{
		if(log.isInfoEnabled())
			log.info("({}) {} -> {}", getSmtpHost(), getFrom(), getTos());
		
		//
		// Reset status
		resetSentStatus();
		
		//
		// Validate email fields
		{
			final StringBuilder invalidReason = new StringBuilder();
			if (!checkValid(invalidReason))
			{
				return setSendError("Invalid Data - " + invalidReason);
			}
		}
		
		//
		// Create session
		final Session session;
		try
		{
			session = createSession();
		}
		catch (final Exception e)
		{
			log.error("Failed creating the session (auth={})", getAuthenticator(), e);
			return setSendError(e.toString());
		}

		try
		{
			final SMTPMessage msg = new SMTPMessage(session);
			
			// Addresses
			msg.setFrom(getFrom());

			//
			// Note: setRecipients can and will use a default debug email address if configured (see JavaDoc & impl)
			setRecipients(msg, Message.RecipientType.TO, getTos());
			setRecipients(msg, Message.RecipientType.CC, getCcs());
			setRecipients(msg, Message.RecipientType.BCC, getBccs());

			final InternetAddress replyTo = getReplyTo();
			if (replyTo != null)
			{
				msg.setReplyTo(new Address[] { replyTo });
			}
			//
			msg.setSentDate(new java.util.Date());
			msg.setHeader("Comments", "mail send by metasfresh");
			// m_msg.setDescription("Description");
			// SMTP specifics
			msg.setAllow8bitMIME(true);
			// Send notification on Failure & Success - no way to set envid in Java yet
			// m_msg.setNotifyOptions (SMTPMessage.NOTIFY_FAILURE | SMTPMessage.NOTIFY_SUCCESS);
			// Bounce only header
			msg.setReturnOption(SMTPMessage.RETURN_HDRS);
			// m_msg.setHeader("X-Mailer", "msgsend");
			//
			setContent(msg);
			msg.saveChanges();
			// log.debug("message =" + m_msg);
			//
			// Transport.send(msg);
			// Transport t = session.getTransport("smtp");
			// log.debug("transport=" + t);
			// t.connect();
			// t.connect(m_smtpHost, user, password);
			// log.debug("transport connected");
			Transport.send(msg);
			// t.sendMessage(msg, msg.getAllRecipients());
			
			this._messageId = msg.getMessageID();
			log.debug("Success - MessageID={}", _messageId);
			
			//
			if (LogManager.isLevelFinest())
			{
				dumpMessage(msg);
			}
			
			return setSendOK();
		}
		catch (final MessagingException me)
		{
			return setSendError(me);
		}
		catch (final Exception e)
		{
			log.error("", e);
			return setSendError(e.getLocalizedMessage());
		}
	}	// send
	
	private Properties getCtx()
	{
		return _ctx;
	}
	
	private Session createSession()
	{
		final String smtpHost = getSmtpHost();
		final Authenticator auth = getAuthenticator();
		
		//
		// Setup mail system properties
		final Properties props = new Properties(System.getProperties());
		props.put("mail.store.protocol", "smtp");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.host", smtpHost);
		if (log.isTraceEnabled())
		{
			props.put("mail.debug", "true");
		}

		//
		if (auth != null)
		{
			props.put("mail.smtp.auth", "true");
		}
		if ("smtp.gmail.com".equalsIgnoreCase(smtpHost))
		{
			// TODO: make it configurable
			// Enable gmail port and ttls - Hardcoded
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
		}

		final Session session = Session.getInstance(props, auth);
		session.setDebug(log.isDebugEnabled());
		return session;
	}
	
	private void resetSentStatus()
	{
		_sentMsg = null;
		_sentConnectionError = false;
		_messageId = null;
	}
	
	private final String setSendOK()
	{
		_sentMsg = SENT_OK;
		_sentConnectionError = false;
		return SENT_OK;
	}

	private final String setSendError(final String errorMsg)
	{
		_sentMsg = errorMsg;
		_sentConnectionError = false;
		return errorMsg;
	}

	private final String setSendError(final MessagingException me)
	{
		Exception ex = me;
		final StringBuilder errorMsgBuilder = new StringBuilder("(ME)");
		boolean isSentConnectionError = false;
		boolean printed = false;
		do
		{
			if (ex instanceof SendFailedException)
			{
				final SendFailedException sfex = (SendFailedException)ex;
				final Address[] invalid = sfex.getInvalidAddresses();
				if (!printed)
				{
					if (invalid != null && invalid.length > 0)
					{
						errorMsgBuilder.append(" - Invalid:");
						for (int i = 0; i < invalid.length; i++)
						{
							errorMsgBuilder.append(" ").append(invalid[i]);
						}

					}
					final Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null && validUnsent.length > 0)
					{
						errorMsgBuilder.append(" - ValidUnsent:");
						for (int i = 0; i < validUnsent.length; i++)
						{
							errorMsgBuilder.append(" ").append(validUnsent[i]);
						}
					}
					final Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null && validSent.length > 0)
					{
						errorMsgBuilder.append(" - ValidSent:");
						for (int i = 0; i < validSent.length; i++)
						{
							errorMsgBuilder.append(" ").append(validSent[i]);
						}
					}
					printed = true;
				}
				if (sfex.getNextException() == null)
				{
					errorMsgBuilder.append(" ").append(sfex.getLocalizedMessage());
				}
			}
			else if (ex instanceof AuthenticationFailedException)
			{
				errorMsgBuilder.append(" - Invalid Username/Password - " + getAuthenticator());
			}
			else if (ex instanceof java.net.ConnectException)
			{
				errorMsgBuilder.append(" - Connection error: " + ex.getLocalizedMessage());
				isSentConnectionError = true;
			}
			else
			// other MessagingException
			{
				String msg = ex.getLocalizedMessage();
				if (msg == null)
				{
					errorMsgBuilder.append(": ").append(ex.toString());
				}
				else
				{
					if (msg.indexOf("Could not connect to SMTP host:") != -1)
					{
						final int index = msg.indexOf('\n');
						if (index != -1)
						{
							msg = msg.substring(0, index);
						}
						String cc = "??";
						final Properties ctx = getCtx();
						if (ctx != null)
						{
							cc = ctx.getProperty("#AD_Client_ID");
						}
						msg += " - AD_Client_ID=" + cc;
					}
					final String className = ex.getClass().getName();
					if (className.indexOf("MessagingException") != -1)
					{
						errorMsgBuilder.append(": ").append(msg);
					}
					else
					{
						errorMsgBuilder.append(" ").append(className).append(": ").append(msg);
					}
				}
			}
			// Next Exception
			if (ex instanceof MessagingException)
			{
				ex = ((MessagingException)ex).getNextException();
			}
			else
			{
				ex = null;
			}
		}
		while (ex != null);	// error loop
		
		//
		// Build the final error message
		String errorMsg = errorMsgBuilder.toString();
		if (Check.isEmpty(errorMsg, true))
		{
			errorMsg = me.toString();
		}
		
		// Log it
		if (LogManager.isLevelFinest())
		{
			log.warn(errorMsg, me);
		}
		else
		{
			log.warn(errorMsg);
		}

		//
		// Set it and return it
		_sentMsg = errorMsg;
		_sentConnectionError = isSentConnectionError;
		return errorMsg;
	}

	/**
	 * Sets recipients.
	 * 
	 * <b>NOTE: If {@link IMailBL#getDebugMailToAddressOrNull(Properties)} returns a valid mail address, it will send to that instead!</b>
	 *
	 * @param message
	 * @param type
	 * @param addresses
	 * @throws MessagingException
	 */
	private void setRecipients(final SMTPMessage message, final RecipientType type, final List<? extends Address> addresses) throws MessagingException
	{
		if (addresses == null || addresses.isEmpty())
		{
			return;
		}

		final InternetAddress debugMailTo = Services.get(IMailBL.class).getDebugMailToAddressOrNull(getCtx());
		if (debugMailTo != null)
		{
			if (Message.RecipientType.TO.equals(type))
			{
				message.setRecipient(Message.RecipientType.TO, debugMailTo);
			}

			for (final Address address : addresses)
			{
				message.addHeader("X-metasfreshDebug-Original-Address" + type.toString(), address.toString());
			}
		}
		else
		{
			final Address[] addressesArr = addresses.toArray(new Address[addresses.size()]);
			message.setRecipients(Message.RecipientType.TO, addressesArr);
		}
	}

	/**
	 * Get Send Result Msg
	 *
	 * @return msg
	 */
	public String getSentMsg()
	{
		return _sentMsg;
	}	// getSentMsg

	/**
	 * Was sending the Msg OK
	 *
	 * @return msg == OK
	 */
	public boolean isSentOK()
	{
		return _sentMsg != null && SENT_OK.equals(_sentMsg);
	}	// isSentOK

	/**
	 * Dump Message Info
	 */
	private static void dumpMessage(final MimeMessage msg)
	{
		if (msg == null)
		{
			return;
		}
		try
		{
			@SuppressWarnings("rawtypes")
			final Enumeration e = msg.getAllHeaderLines();
			while (e.hasMoreElements())
			{
				log.debug("- " + e.nextElement());
			}
		}
		catch (final MessagingException ex)
		{
			log.warn(msg.toString(), ex);
		}
	}	// dumpMessage

	/**
	 * Get Message ID or null
	 *
	 * @return Message ID
	 */
	public String getMessageID()
	{
		return _messageId;
	}

	/**
	 * Create Authenticator for User
	 *
	 * @param username user name
	 * @param password user password
	 * @return Authenticator or null
	 */
	public void createAuthenticator(final String username, final String password)
	{
		if (username == null || password == null)
		{
			log.warn("Ignored because user or password is empty");
			_auth = null;
		}
		else
		{
			_auth = new EMailAuthenticator(username, password);
		}
	}	// createAuthenticator
	
	private Authenticator getAuthenticator()
	{
		return _auth;
	}

	/**
	 * Get Sender
	 *
	 * @return Sender's internet address
	 */
	public InternetAddress getFrom()
	{
		return _from;
	}   // getFrom

	/**
	 * Set Sender
	 *
	 * @param newFrom Sender's email address
	 */
	private void setFrom(final String newFrom)
	{
		if (newFrom == null)
		{
			setValid(false);
			return;
		}
		try
		{
			_from = new InternetAddress(newFrom, true);
		}
		catch (final Exception e)
		{
			log.warn("Invalid from: {}", newFrom, e);
			setValid(false);
			return;
		}
	}   // setFrom

	/**
	 * Add To Recipient
	 *
	 * @param newTo Recipient's email address
	 * @return true if valid
	 */
	public boolean addTo(final String newTo)
	{
		if(Check.isEmpty(newTo, true))
		{
			setValid(false);
			return false;
		}
		
		InternetAddress ia = null;
		try
		{
			ia = new InternetAddress(newTo, true);
		}
		catch (final Exception e)
		{
			log.warn("Invalid To address: {}", newTo, e);
			setValid(false);
			return false;
		}
		
		if (_to == null)
		{
			_to = new ArrayList<InternetAddress>();
		}
		_to.add(ia);
		return true;
	}   // addTo

	/**
	 * Get Recipient
	 *
	 * @return Recipient's internet address
	 */
	public InternetAddress getTo()
	{
		if (_to == null || _to.isEmpty())
		{
			return null;
		}
		return _to.get(0);
	}   // getTo

	/**
	 * Get TO Recipients
	 *
	 * @return Recipient's internet address
	 */
	private List<InternetAddress> getTos()
	{
		if (_to == null || _to.isEmpty())
		{
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(_to);
	}   // getTos

	/**
	 * Add CC Recipient
	 *
	 * @param newCc EMail cc Recipient
	 * @return true if valid
	 */
	public boolean addCc(final String newCc)
	{
		if(Check.isEmpty(newCc, true))
		{
			return false;
		}
		
		InternetAddress ia = null;
		try
		{
			ia = new InternetAddress(newCc, true);
		}
		catch (final Exception e)
		{
			log.warn("Invalid CC address: {}", newCc, e);
			return false;
		}
		if (_cc == null)
		{
			_cc = new ArrayList<InternetAddress>();
		}
		_cc.add(ia);
		return true;
	}	// addCc

	/**
	 * Get CC Recipients
	 *
	 * @return Recipient's internet address
	 */
	private List<InternetAddress> getCcs()
	{
		if (_cc == null || _cc.isEmpty())
		{
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(_cc);
	}   // getCcs

	/**
	 * Add BCC Recipient
	 *
	 * @param newBcc EMail cc Recipient
	 * @return true if valid
	 */
	public boolean addBcc(final String newBcc)
	{
		if(Check.isEmpty(newBcc, true))
		{
			return false;
		}
		InternetAddress ia = null;
		try
		{
			ia = new InternetAddress(newBcc, true);
		}
		catch (final Exception e)
		{
			log.warn("Invalid BCC address: {}", newBcc, e);
			return false;
		}
		if (_bcc == null)
		{
			_bcc = new ArrayList<InternetAddress>();
		}
		_bcc.add(ia);
		return true;
	}	// addBcc

	/**
	 * Get BCC Recipients
	 *
	 * @return Recipient's internet address
	 */
	public List<InternetAddress> getBccs()
	{
		if(_bcc == null || _bcc.isEmpty())
		{
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(_bcc);
	}   // getBccs

	/**
	 * Set Reply to Address
	 *
	 * @param newReplyTo email address
	 * @return true if valid
	 */
	public boolean setReplyTo(final String newReplyTo)
	{
		if(Check.isEmpty(newReplyTo, true))
		{
			return false;
		}
		InternetAddress ia = null;
		try
		{
			ia = new InternetAddress(newReplyTo, true);
		}
		catch (final Exception e)
		{
			log.warn("Invalid ReplyTo address: {}", newReplyTo, e);
			return false;
		}
		_replyTo = ia;
		return true;
	}   // setReplyTo

	/**
	 * Get Reply To
	 *
	 * @return Reply To internet address
	 */
	private InternetAddress getReplyTo()
	{
		return _replyTo;
	}   // getReplyTo

	/**************************************************************************
	 * Set Subject
	 *
	 * @param newSubject Subject
	 */
	public void setSubject(final String newSubject)
	{
		if(Check.isEmpty(newSubject, true))
		{
			setValid(false);
		}
		else
		{
			_subject = newSubject.trim();
		}
	}   // setSubject

	/**
	 * Get Subject
	 *
	 * @return subject
	 */
	public String getSubject()
	{
		return _subject;
	}   // getSubject

	/**
	 * Set Message
	 *
	 * @param newMessage message
	 */
	public void setMessageText(final String newMessage)
	{
		if(Check.isEmpty(newMessage, true))
		{
			setValid(false);
		}
		else
		{
			_messageText = newMessage;
			if (!_messageText.endsWith("\n"))
			{
				_messageText += "\n";
			}
		}
	}   // setMessage

	/**
	 * Get MIME String Message - line ending with CRLF.
	 *
	 * @return message
	 */
	public String getMessageCRLF()
	{
		if (_messageText == null)
		{
			return "";
		}
		final char[] chars = _messageText.toCharArray();
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++)
		{
			final char c = chars[i];
			if (c == '\n')
			{
				final int previous = i - 1;
				if (previous >= 0 && chars[previous] == '\r')
				{
					sb.append(c);
				}
				else
				{
					sb.append("\r\n");
				}
			}
			else
			{
				sb.append(c);
			}
		}
		// log.debug("IN " + m_messageText);
		// log.debug("OUT " + sb);

		return sb.toString();
	}   // getMessageCRLF

	/**
	 * Set HTML Message
	 *
	 * @param html message
	 */
	public void setMessageHTML(final String html)
	{
		if(Check.isEmpty(html, true))
		{
			setValid(false);
		}
		else
		{
			_messageHTML = html;
			if (!_messageHTML.endsWith("\n"))
			{
				_messageHTML += "\n";
			}
		}
	}   // setMessageHTML

	/**
	 * Set HTML Message
	 *
	 * @param subject subject repeated in message as H2
	 * @param message message
	 */
	public void setMessageHTML(final String subject, final String message)
	{
		this._subject = subject;
		final StringBuilder sb = new StringBuilder("<HTML>\n")
				.append("<HEAD>\n")
				.append("<TITLE>\n")
				.append(subject + "\n")
				.append("</TITLE>\n")
				.append("</HEAD>\n");
		sb.append("<BODY>\n")
				.append("<H2>" + subject + "</H2>" + "\n")
				.append(message)
				.append("\n")
				.append("</BODY>\n");
		sb.append("</HTML>\n");
		_messageHTML = sb.toString();
	}   // setMessageHTML

	/**
	 * Get HTML Message
	 *
	 * @return message
	 */
	public String getMessageHTML()
	{
		return _messageHTML;
	}   // getMessageHTML

	/**
	 * Add file Attachment
	 *
	 * @param file file to attach
	 */
	public void addAttachment(final File file)
	{
		if (file == null)
		{
			return;
		}
		if (_attachments == null)
		{
			_attachments = new ArrayList<Object>();
		}
		_attachments.add(file);
	}	// addAttachment

	/**
	 * Add a collection of attachments
	 *
	 * @param files collection of files
	 */
	public void addAttachments(final Collection<File> files)
	{
		if (files == null || files.isEmpty())
		{
			return;
		}
		for (final File f : files)
		{
			addAttachment(f);
		}
	}

	/**
	 * Add url based file Attachment
	 *
	 * @param uri url content to attach
	 */
	public void addAttachment(final URI url)
	{
		if (url == null)
		{
			return;
		}
		if (_attachments == null)
		{
			_attachments = new ArrayList<Object>();
		}
		_attachments.add(url);
	}	// addAttachment

	/**
	 * Set the message content
	 *
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void setContent(final MimeMessage msg) throws MessagingException, IOException
	{
		final String subject = getSubject();
		msg.setSubject(subject, getCharsetName());
		
		//
		// Simple Message
		final List<Object> attachments = getAttachments();
		if (attachments.isEmpty())
		{
			setMessageContent(msg);
			log.debug("(simple) {}", subject);
		}
		//
		// Multi part message
		else
		{
			final Multipart mp = new MimeMultipart();
			
			//
			// First Part - Message
			{
				final MimeBodyPart mbp_message = new MimeBodyPart();
				setMessageContent(mbp_message);
				mp.addBodyPart(mbp_message);
				log.debug("Composing multipart message for subject {} - {}", subject, mbp_message);
			}

			//
			// for all attachments
			for (final Object attachment : attachments)
			{
				final DataSource ds = createAttachmentDataSource(attachment);
				if(ds == null)
				{
					// NOTE: an error was already logged
					continue;
				}
				
				// Attachment Part
				final MimeBodyPart mbp_attachment = new MimeBodyPart();
				mbp_attachment.setDataHandler(new DataHandler(ds));
				mbp_attachment.setFileName(ds.getName());
				log.debug("Added Attachment {} - {}", ds.getName(), mbp_attachment);
				mp.addBodyPart(mbp_attachment);
			}

			// Add to Message
			msg.setContent(mp);
		}  	// multi=part
	}	// setContent
	
	private String getCharsetName()
	{
		// Local Character Set
		String charSetName = Ini.getCharset().name();
		if (charSetName == null || charSetName.isEmpty())
		{
			charSetName = "iso-8859-1";	// WebEnv.ENCODING - alternative iso-8859-1
		}
		return charSetName;
	}
	
	private final void setMessageContent(final MimePart part) throws MessagingException
	{
		final String charSetName = getCharsetName();
		final String messageHtml = getMessageHTML();
		if (Check.isEmpty(messageHtml, true))
		{
			part.setText(getMessageCRLF(), charSetName, "plain"); // just explicitly adding "plain" for better contrast with "html"
		}
		else
		{
			part.setText(messageHtml, charSetName, "html"); // #107 FRESH-445: this call solves the issue
		}
	}
	
	private final DataSource createAttachmentDataSource(final Object attachment)
	{
		final DataSource ds;
		if (attachment instanceof File)
		{
			final File file = (File)attachment;
			if (file.exists())
			{
				ds = new FileDataSource(file);
			}
			else
			{
				log.warn("File does not exist: {}", file);
				return null;
			}
		}
		else if (attachment instanceof URI)
		{
			final URI url = (URI)attachment;
			try
			{
				ds = new URLDataSource(url.toURL());
			}
			catch (MalformedURLException e)
			{
				log.warn("Invalid attachment URL: {}", url, e);
				return null;
			}
		}
		else if (attachment instanceof DataSource)
		{
			ds = (DataSource)attachment;
		}
		else
		{
			log.warn("Attachement type unknown: {}", attachment);
			return null;
		}
		
		return ds;
	}

	/**************************************************************************
	 * Set SMTP Host or address
	 *
	 * @param newSmtpHost Mail server
	 */
	private void setSmtpHost(final String newSmtpHost)
	{
		if(Check.isEmpty(newSmtpHost, true))
		{
			setValid(false);
		}
		else
		{
			_smtpHost = newSmtpHost.trim();
		}
	}   // setSMTPHost

	/**
	 * Get Mail Server name or address
	 *
	 * @return mail server
	 */
	public String getSmtpHost()
	{
		return _smtpHost;
	}   // getSmtpHosr

	/**
	 * Is Info valid to send EMail
	 *
	 * @return true if email is valid and can be sent
	 */
	public boolean isValid()
	{
		return _valid;
	}   // isValid
	
	private void setValid(final boolean valid)
	{
		this._valid = valid;
	}

	/**
	 * Re-Check Info if valid to send EMail
	 *
	 * NOTE: this method is NOT setting the {@link #isValid()} flag.
	 * 
	 * @return true if email is valid and can be sent
	 */
	public boolean checkValid()
	{
		final StringBuilder reasonCollector = new StringBuilder();
		if (!checkValid(reasonCollector))
		{
			log.warn("Invalid mail data: {}", reasonCollector);
			return false;
		}
		
		return true;
	}

	private boolean checkValid(final StringBuilder reason)
	{
		boolean valid = true;
		
		// From
		final InternetAddress from = getFrom();
		if (!isValidAddress(from))
		{
			final String errmsg = "From is invalid=" + from;
			if(reason.length() > 0)
				reason.append("; ");
			reason.append(errmsg);
			valid = false;
		}
		
		// To
		final List<InternetAddress> toList = getTos();
		if (toList.isEmpty())
		{
			final String errmsg = "No To";
			if(reason.length() > 0)
				reason.append("; ");
			reason.append(errmsg);
			valid = false;
		}
		else
		{
			for (final InternetAddress to : toList)
			{
				if(!isValidAddress(to))
				{
					final String errmsg = "To is invalid: " + to;
					if(reason.length() > 0)
						reason.append("; ");
					reason.append(errmsg);
					valid = false;
				}
			}
		}

		// Host
		final String smtpHost = getSmtpHost();
		if(Check.isEmpty(smtpHost, true))
		{
			final String errmsg = "SMTP Host is invalid" + smtpHost;
			if(reason.length() > 0)
				reason.append("; ");
			reason.append(errmsg);
			valid = false;
		}

		// Subject
		final String subject = getSubject();
		if (Check.isEmpty(subject, true))
		{
			final String errmsg = "Subject is invalid=" + subject;
			if(reason.length() > 0)
				reason.append("; ");
			reason.append(errmsg);
			valid = false;
		}
		
		return valid;
	}
	
	private static final boolean isValidAddress(final InternetAddress from)
	{
		if(from == null)
		{
			return false;
		}
		
		final String address = from.getAddress();
		return address != null
				&& address.length() > 0
				&& address.indexOf(' ') < 0;
	}

	/**
	 * @return attachments array or empty array. This method will never return null.
	 */
	public List<Object> getAttachments()
	{
		if(_attachments == null || _attachments.isEmpty())
		{
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(_attachments);
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("from", _from)
				.add("to", _to)
				.add("subject", _subject)
				.toString();
	}	// toString

	public boolean isSentConnectionError()
	{
		return _sentConnectionError;
	}
}	// EMail
