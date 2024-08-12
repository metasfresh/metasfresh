package de.metas.email;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Getter;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.concurrent.Immutable;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.io.Serializable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * EMail sent status.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @see EMail#send()
 */
@Getter
@Immutable
public final class EMailSentStatus implements Serializable
{
	private static final Logger logger = LogManager.getLogger(EMailSentStatus.class);

	/* package */static final EMailSentStatus NOT_SENT = new EMailSentStatus();

	/** Mail Sent OK Status */
	@VisibleForTesting
	/* package */static final String SENT_OK = "OK";

	/* package */static EMailSentStatus ok(final String messageId)
	{
		final boolean sentConnectionError = false;
		return new EMailSentStatus(SENT_OK, sentConnectionError, messageId);
	}

	/* package */static EMailSentStatus invalid(final String errorMessage)
	{
		return error(errorMessage);
	}

	/* package */static EMailSentStatus error(final String errorMessage)
	{
		final boolean sentConnectionError = false;
		final String messageId = null;
		return new EMailSentStatus(errorMessage, sentConnectionError, messageId);
	}

	/* package */static EMailSentStatus error(final MessagingException me)
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
						for (final Address address : invalid)
						{
							errorMsgBuilder.append(" ").append(address);
						}

					}
					final Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null && validUnsent.length > 0)
					{
						errorMsgBuilder.append(" - ValidUnsent:");
						for (final Address address : validUnsent)
						{
							errorMsgBuilder.append(" ").append(address);
						}
					}
					final Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null && validSent.length > 0)
					{
						errorMsgBuilder.append(" - ValidSent:");
						for (final Address address : validSent)
						{
							errorMsgBuilder.append(" ").append(address);
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
				errorMsgBuilder.append(" - Invalid Username/Password: ").append(ex.getLocalizedMessage());
			}
			else if (ex instanceof java.net.ConnectException)
			{
				errorMsgBuilder.append(" - Connection error: ").append(ex.getLocalizedMessage());
				isSentConnectionError = true;
			}
			else
			// other MessagingException
			{
				String msg = ex.getLocalizedMessage();
				if (msg == null)
				{
					errorMsgBuilder.append(": ").append(ex);
				}
				else
				{
					if (msg.contains("Could not connect to SMTP host:"))
					{
						final int index = msg.indexOf('\n');
						if (index != -1)
						{
							msg = msg.substring(0, index);
						}
					}
					final String className = ex.getClass().getName();
					if (className.contains("MessagingException"))
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
		if (Check.isBlank(errorMsg))
		{
			errorMsg = me.toString();
		}

		// Log it
		if (LogManager.isLevelFinest())
		{
			logger.warn(errorMsg, me);
		}
		else
		{
			logger.warn(errorMsg);
		}

		//
		//
		final String messageId = null;
		return new EMailSentStatus(errorMsg, isSentConnectionError, messageId);
	}

	@JsonProperty("sentMsg")
	private final String sentMsg;
	@JsonProperty("sentConnectionError")
	private final boolean sentConnectionError;
	@JsonProperty("messageId")
	private final String messageId;

	@JsonCreator
	private EMailSentStatus(
			@JsonProperty("sentMsg") final String sentMsg //
			, @JsonProperty("sentConnectionError") final boolean sentConnectionError //
			, @JsonProperty("messageId") final String messageId //
	)
	{
		super();
		this.sentMsg = sentMsg;
		this.sentConnectionError = sentConnectionError;
		this.messageId = messageId;
	}

	/** Null/new constructor */
	private EMailSentStatus()
	{
		super();
		sentMsg = null;
		sentConnectionError = false;
		messageId = null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sentMsg", sentMsg)
				.add("sentConnectionError", sentConnectionError)
				.add("messageId", messageId)
				.toString();
	}

	@JsonIgnore
	public boolean isSentOK()
	{
		return Util.same(sentMsg, SENT_OK);
	}

}
