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
package de.metas.email;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Nullable;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.sun.mail.smtp.SMTPMessage;

import de.metas.email.mailboxes.Mailbox;
import de.metas.logging.LogManager;
import de.metas.session.jaxrs.IServerService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * EMail builder and sender.
 *
 * To create a new email, please use {@link MailService}'s createMail methods.
 *
 * To send the message, please use {@link #send()}.
 *
 * NOTE: This is basically a reimplementation of the class <code>org.compiere.util.EMail</code> which was authored (according to the javadoc) by author Joerg Janke.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("serial")
@JsonAutoDetect(getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class EMail implements Serializable
{
	private static final transient Logger logger = LogManager.getLogger(EMail.class);

	@JsonProperty("mailbox")
	private final Mailbox _mailbox;
	@JsonIgnore
	private transient EMailAddress _from;

	/** To Address */
	@JsonProperty("to")
	private final List<EMailAddress> _to = new ArrayList<>();
	@JsonIgnore
	private final List<InternetAddress> _toAddresses = new ArrayList<>();
	/** CC Addresses */
	@JsonProperty("cc")
	private final List<EMailAddress> _cc = new ArrayList<>();
	@JsonIgnore
	private final List<InternetAddress> _ccAddresses = new ArrayList<>();
	/** BCC Addresses */
	@JsonProperty("bcc")
	private final List<EMailAddress> _bcc = new ArrayList<>();
	@JsonIgnore
	private final List<InternetAddress> _bccAddresses = new ArrayList<>();
	/** Reply To Address */
	@JsonProperty("replyTo")
	private EMailAddress _replyTo;
	@JsonIgnore
	private InternetAddress _replyToAddress;

	@JsonIgnore
	private InternetAddress _debugMailToAddress;

	/** Mail Subject */
	@JsonProperty("subject")
	private String _subject;
	/** Mail Plain Message */
	@JsonProperty("messageText")
	private String _messageText;
	/** Mail HTML Message */
	@JsonProperty("messageHTML")
	private String _messageHTML;
	/** Attachments */
	@JsonProperty("attachments")
	private final List<EMailAttachment> _attachments = new ArrayList<>();

	//
	// Status
	@JsonIgnore
	private transient boolean _valid = false;
	@JsonIgnore
	private transient EMailSentStatus _status = EMailSentStatus.NOT_SENT;

	EMail(
			@NonNull final Mailbox mailbox,
			@Nullable final EMailAddress to,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html)
	{
		this(mailbox);

		addTo(to);

		if (Check.isEmpty(subject, true))
		{
			setSubject(".");	// pass validation
		}
		else
		{
			setSubject(subject);
		}

		if (!Check.isEmpty(message, true))
		{
			if (html)
			{
				setMessageHTML(message);
			}
			else
			{
				setMessageText(message);
			}
		}

		if (checkValid())
		{
			markValid();
		}
		else
		{
			markInvalid();
		}
	}

	@JsonCreator
	private EMail(
			@JsonProperty("mailbox") final Mailbox mailbox //
			, @JsonProperty("to") final List<EMailAddress> to //
			, @JsonProperty("cc") final List<EMailAddress> cc //
			, @JsonProperty("bcc") final List<EMailAddress> bcc //
			, @JsonProperty("replyTo") final EMailAddress replyTo //
			, @JsonProperty("subject") final String subject //
			, @JsonProperty("messageText") final String messageText //
			, @JsonProperty("messageHTML") final String messageHTML //
			, @JsonProperty("attachments") final List<EMailAttachment> attachments //
	)
	{
		this(mailbox);

		if (to != null && !to.isEmpty())
		{
			to.stream().forEach(this::addTo);
		}
		if (cc != null && !cc.isEmpty())
		{
			cc.stream().forEach(this::addCc);
		}
		if (bcc != null && !bcc.isEmpty())
		{
			bcc.stream().forEach(this::addBcc);
		}

		if (replyTo != null)
		{
			setReplyTo(replyTo);
		}

		_subject = subject;
		_messageText = messageText;
		_messageHTML = messageHTML;

		if (attachments != null && !attachments.isEmpty())
		{
			attachments.stream().forEach(this::addAttachment);
		}

		if (checkValid())
		{
			markValid();
		}
		else
		{
			markInvalid();
		}
	}

	/** Base constructor */
	private EMail(@NonNull final Mailbox mailbox)
	{
		_mailbox = mailbox;
		setFrom(mailbox.getEmail());
	}

	public void setDebugMailToAddress(@Nullable final InternetAddress debugMailToAddress)
	{
		this._debugMailToAddress = debugMailToAddress;
	}

	private InternetAddress getDebugMailToAddress()
	{
		return _debugMailToAddress;
	}

	public EMailSentStatus send()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Sending email from={}, tos={} mailbox={}", getFrom(), getTos(), getMailbox());
		}

		//
		// Reset status
		resetSentStatus();

		//
		// Validate email fields
		{
			final StringBuilder invalidReason = new StringBuilder();
			if (!checkValid(invalidReason))
			{
				markInvalid();
				return setStatus(EMailSentStatus.invalid("Invalid Data - " + invalidReason));
			}

			markValid();
		}

		//
		// Send the email now
		final Mailbox mailbox = getMailbox();
		final boolean sendEmailsFromServer = mailbox.isSendEmailsFromServer() && Ini.isSwingClient();
		if (sendEmailsFromServer)
		{
			final EMailSentStatus sentStatus = Services.get(IServerService.class).sendEMail(this);
			return setStatus(sentStatus);
		}
		else
		{
			final EMailSentStatus sentStatus = sendNow();
			return setStatus(sentStatus);
		}
	}	// send

	private EMailSentStatus sendNow()
	{
		//
		// Create session
		final Session session;
		try
		{
			session = createSession();
		}
		catch (final Exception e)
		{
			logger.error("Failed creating the session (mailbox={})", getMailbox(), e);
			return EMailSentStatus.invalid(e.toString());
		}

		try
		{
			final SMTPMessage msg = new SMTPMessage(session);

			// Addresses
			msg.setFrom(getFrom().toInternetAddress());

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

			Transport.send(msg);

			final String messageId = msg.getMessageID();
			logger.debug("Success - MessageID={}", messageId);

			//
			if (logger.isTraceEnabled())
			{
				dumpMessage(msg);
			}

			return EMailSentStatus.ok(messageId);
		}
		catch (final MessagingException me)
		{
			return EMailSentStatus.error(me);
		}
		catch (final Exception e)
		{
			logger.error("Send error", e);
			return EMailSentStatus.error(e.getLocalizedMessage());
		}
	}

	private Session createSession()
	{
		final Mailbox mailbox = getMailbox();
		final String smtpHost = mailbox.getSmtpHost();

		final MailAuthenticator auth;
		if (mailbox.isSmtpAuthorization())
		{
			auth = MailAuthenticator.of(mailbox.getUsername(), mailbox.getPassword());
		}
		else
		{
			auth = null;
		}

		//
		// Setup mail system properties
		final Properties props = new Properties(System.getProperties());
		props.put("mail.store.protocol", "smtp");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.host", smtpHost);
		props.put("mail.smtp.port", mailbox.getSmtpPort());
		if (mailbox.isStartTLS())
		{
			props.put("mail.smtp.starttls.enable", "true");
		}
		if (auth != null)
		{
			props.put("mail.smtp.auth", "true");
		}
		if (logger.isTraceEnabled())
		{
			props.put("mail.debug", "true");
		}

		final Session session = Session.getInstance(props, auth);
		session.setDebug(logger.isDebugEnabled());
		return session;
	}

	private void resetSentStatus()
	{
		setStatus(EMailSentStatus.NOT_SENT);
	}

	private EMailSentStatus setStatus(final EMailSentStatus status)
	{
		Check.assumeNotNull(status, "status not null");
		_status = status;
		return status;
	}

	/**
	 * @return last sent status
	 */
	@Deprecated
	public EMailSentStatus getLastSentStatus()
	{
		return _status;
	}

	/**
	 * Sets recipients.
	 *
	 * <b>NOTE: If {@link #getDebugMailToAddress()} returns a valid mail address, it will send to that instead!</b>
	 *
	 * @param message
	 * @param type
	 * @param addresses
	 * @throws MessagingException
	 */
	private void setRecipients(
			final SMTPMessage message,
			final RecipientType type,
			final List<? extends Address> addresses) throws MessagingException
	{
		if (addresses == null || addresses.isEmpty())
		{
			return;
		}

		final InternetAddress debugMailTo = getDebugMailToAddress();
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

	private Mailbox getMailbox()
	{
		return _mailbox;
	}

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
				logger.trace("- " + e.nextElement());
			}
		}
		catch (final MessagingException ex)
		{
			logger.warn(msg.toString(), ex);
		}
	}	// dumpMessage

	/**
	 * Get Sender
	 *
	 * @return Sender's internet address
	 */
	public EMailAddress getFrom()
	{
		return _from;
	}   // getFrom

	/**
	 * Set Sender
	 *
	 * @param from Sender's email address
	 */
	private void setFrom(final EMailAddress from)
	{
		if (from == null)
		{
			markInvalid();
			return;
		}
		else
		{
			_from = from;
		}
	}

	/**
	 * Add To Recipient
	 *
	 * @param to Recipient's email address
	 * @return true if valid
	 */
	public boolean addTo(final EMailAddress to)
	{
		if (to == null)
		{
			markInvalid();
			return false;
		}

		final InternetAddress ia;
		try
		{
			ia = to.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid To address: {}", to, e);
			markInvalid();
			return false;
		}

		_to.add(to);
		_toAddresses.add(ia);
		return true;
	}   // addTo

	/**
	 * Get Recipient
	 *
	 * @return Recipient's internet address
	 */
	public EMailAddress getTo()
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
	 * @return Recipient's Internet addresses
	 */
	private List<InternetAddress> getTos()
	{
		return ImmutableList.copyOf(_toAddresses);
	}   // getTos

	/**
	 * Add CC recipient.
	 *
	 * @param cc cc Recipient
	 * @return true if valid and added
	 */
	public boolean addCc(final EMailAddress cc)
	{
		if (cc == null)
		{
			return false;
		}

		final InternetAddress ia;
		try
		{
			ia = cc.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid CC address: {}", cc, e);
			return false;
		}

		_cc.add(cc);
		_ccAddresses.add(ia);
		return true;
	}	// addCc

	/**
	 * Get CC Recipients
	 *
	 * @return Recipient's Internet addresses
	 */
	private List<InternetAddress> getCcs()
	{
		return ImmutableList.copyOf(_ccAddresses);
	}   // getCcs

	/**
	 * Add BCC Recipient
	 *
	 * @param bcc EMail cc Recipient
	 * @return true if valid
	 */
	public boolean addBcc(final EMailAddress bcc)
	{
		if (bcc == null)
		{
			return false;
		}
		final InternetAddress ia;
		try
		{
			ia = bcc.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid BCC address: {}", bcc, e);
			return false;
		}

		_bcc.add(bcc);
		_bccAddresses.add(ia);
		return true;
	}	// addBcc

	/**
	 * Get BCC Recipients
	 *
	 * @return Recipient's internet address
	 */
	private List<InternetAddress> getBccs()
	{
		return ImmutableList.copyOf(_bccAddresses);
	}   // getBccs

	/**
	 * Set Reply to Address
	 *
	 * @param replyTo email address
	 * @return true if valid
	 */
	public boolean setReplyTo(final EMailAddress replyTo)
	{
		if (replyTo == null)
		{
			return false;
		}
		final InternetAddress ia;
		try
		{
			ia = replyTo.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid ReplyTo address: {}", replyTo, e);
			return false;
		}

		_replyTo = replyTo;
		_replyToAddress = ia;
		return true;
	}   // setReplyTo

	/**
	 * Get Reply To
	 *
	 * @return Reply To internet address
	 */
	private InternetAddress getReplyTo()
	{
		return _replyToAddress;
	}   // getReplyTo

	/**************************************************************************
	 * Set Subject
	 *
	 * @param subject Subject
	 */
	public void setSubject(final String subject)
	{
		if (Check.isEmpty(subject, true))
		{
			markInvalid();
		}
		else
		{
			_subject = subject.trim();
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
	 * @param messageText message
	 */
	public void setMessageText(final String messageText)
	{
		if (Check.isEmpty(messageText, true))
		{
			markInvalid();
		}
		else
		{
			_messageText = messageText;
			if (!_messageText.endsWith("\n"))
			{
				_messageText += "\n";
			}
		}
	}

	/**
	 * @return message text with CRLF line endings.
	 */
	@JsonIgnore
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

		return sb.toString();
	}

	/**
	 * Set HTML Message
	 *
	 * @param messageHtml message (HTML)
	 */
	public void setMessageHTML(final String messageHtml)
	{
		if (Check.isEmpty(messageHtml, true))
		{
			markInvalid();
		}
		else
		{
			_messageHTML = messageHtml;
			if (!_messageHTML.endsWith("\n"))
			{
				_messageHTML += "\n";
			}
		}
	}

	/**
	 * Set HTML Message
	 *
	 * @param subject subject repeated in message as H2
	 * @param message message
	 */
	public void setMessageHTML(final String subject, final String message)
	{
		_subject = subject;
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
	}

	/**
	 * Get HTML Message
	 *
	 * @return message
	 */
	public String getMessageHTML()
	{
		return _messageHTML;
	}

	/**
	 * Add file Attachment
	 *
	 * @param file file to attach
	 */
	public void addAttachment(final File file)
	{
		if (file == null)
		{
			logger.warn("Skip adding file attachment because file is null");
			return;
		}
		addAttachment(EMailAttachment.of(file));
	}

	public void addAttachment(@NonNull final Resource resource)
	{
		addAttachment(EMailAttachment.of(resource));
	}

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
		for (final File file : files)
		{
			addAttachment(file);
		}
	}

	/**
	 * Add URL based file Attachment
	 *
	 * @param uri URL content to attach
	 */
	public void addAttachment(final URI uri)
	{
		if (uri == null)
		{
			logger.warn("Skip adding URL attachment because URL is null");
			return;
		}
		addAttachment(EMailAttachment.of(uri));
	}

	public void addAttachment(final String filename, final byte[] content)
	{
		if (content == null || content.length == 0)
		{
			logger.warn("Skip adding byte attachment because the content is empty for {}", filename);
			return;
		}
		addAttachment(EMailAttachment.of(filename, content));
	}

	public void addAttachment(@NonNull final EMailAttachment emailAttachment)
	{
		_attachments.add(emailAttachment);
	}

	/**
	 * Set the message content and attachments.
	 *
	 * @param msg
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void setContent(final MimeMessage msg) throws MessagingException, IOException
	{
		final String subject = getSubject();
		msg.setSubject(subject, getCharsetName());

		//
		// Simple Message (no attachments)
		final List<EMailAttachment> attachments = getAttachments();
		if (attachments.isEmpty())
		{
			setMessageContent(msg);
			logger.debug("(simple) {}", subject);
		}
		//
		// Multi-part message (with attachments)
		else
		{
			final Multipart mp = new MimeMultipart();

			//
			// First Part - the Message
			{
				final MimeBodyPart mbp_message = new MimeBodyPart();
				setMessageContent(mbp_message);
				mp.addBodyPart(mbp_message);
				logger.debug("Composing multipart message for subject {} - {}", subject, mbp_message);
			}

			//
			// for all attachments
			for (final EMailAttachment attachment : attachments)
			{
				final DataSource ds = attachment.createDataSource();

				// Attachment Part
				final MimeBodyPart mbp_attachment = new MimeBodyPart();
				mbp_attachment.setDataHandler(new DataHandler(ds));
				mbp_attachment.setFileName(ds.getName());
				logger.debug("Added Attachment {} - {}", ds.getName(), mbp_attachment);
				mp.addBodyPart(mbp_attachment);
			}

			// Add to Message
			msg.setContent(mp);
		}
	}

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

	private void setMessageContent(final MimePart part) throws MessagingException
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

	/**
	 * @return true if email is valid and can be sent
	 */
	public boolean isValid()
	{
		return _valid;
	}

	private void markInvalid()
	{
		_valid = false;
	}

	private void markValid()
	{
		_valid = true;
	}

	/**
	 * Checks if the email is valid and can be sent.
	 *
	 * NOTE: this method is NOT setting the {@link #isValid()} flag.
	 *
	 * @return true if email is valid and can be sent
	 */
	public boolean checkValid()
	{
		final StringBuilder notValidReasonCollector = new StringBuilder();
		if (!checkValid(notValidReasonCollector))
		{
			logger.warn("Invalid mail data: {}", notValidReasonCollector);
			return false;
		}

		return true;
	}

	/**
	 * Checks if the email is valid and can be sent.
	 *
	 * NOTE: this method is NOT setting the {@link #isValid()} flag.
	 *
	 * @param notValidReasonCollector
	 * @return true if email is valid and can be sent
	 */
	private boolean checkValid(final StringBuilder notValidReasonCollector)
	{
		boolean valid = true;

		// From
		final EMailAddress from = getFrom();
		if (!isValidAddress(from))
		{
			final String errmsg = "No From address";
			if (notValidReasonCollector.length() > 0)
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}

		// To
		final List<InternetAddress> toList = getTos();
		if (toList.isEmpty())
		{
			final String errmsg = "No To addresses";
			if (notValidReasonCollector.length() > 0)
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}
		else
		{
			for (final InternetAddress to : toList)
			{
				if (!isValidAddress(to))
				{
					final String errmsg = "To address is invalid (" + to + ")";
					if (notValidReasonCollector.length() > 0)
					{
						notValidReasonCollector.append("; ");
					}
					notValidReasonCollector.append(errmsg);
					valid = false;
				}
			}
		}

		// Host
		final Mailbox mailbox = getMailbox();
		final String smtpHost = mailbox.getSmtpHost();
		if (Check.isEmpty(smtpHost, true))
		{
			final String errmsg = "No SMTP Host";
			if (notValidReasonCollector.length() > 0)
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}

		// Subject
		final String subject = getSubject();
		if (Check.isEmpty(subject, true))
		{
			final String errmsg = "Subject is empty";
			if (notValidReasonCollector.length() > 0)
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}

		return valid;
	}

	private static boolean isValidAddress(final EMailAddress emailAddress)
	{
		if (emailAddress == null)
		{
			return false;
		}

		try
		{
			return isValidAddress(emailAddress.toInternetAddress());
		}
		catch (AddressException e)
		{
			return false;
		}
	}

	private static boolean isValidAddress(final InternetAddress emailAddress)
	{
		if (emailAddress == null)
		{
			return false;
		}

		final String addressStr = emailAddress.getAddress();
		return addressStr != null
				&& addressStr.length() > 0
				&& addressStr.indexOf(' ') < 0;
	}

	/**
	 * @return attachments array or empty array. This method will never return null.
	 */
	public List<EMailAttachment> getAttachments()
	{
		return ImmutableList.copyOf(_attachments);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("to", _to)
				.add("cc", _cc.isEmpty() ? null : _cc)
				.add("bcc", _bcc.isEmpty() ? null : _bcc)
				.add("replyTo", _replyTo)
				.add("subject", _subject)
				.add("attachments", _attachments.isEmpty() ? null : _attachments)
				.add("mailbox", _mailbox)
				.toString();
	}
}	// EMail
