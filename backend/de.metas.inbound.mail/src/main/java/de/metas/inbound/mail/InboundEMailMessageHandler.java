package de.metas.inbound.mail;

import java.time.ZonedDateTime;
import java.util.List;

import org.compiere.util.TimeUtil;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.util.MultiValueMap;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.inbound.mail.config.InboundEMailConfig;
import de.metas.util.GuavaCollectors;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.inbound.mail
 * %%
 * Copyright (C) 2018 metas GmbH
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

class InboundEMailMessageHandler implements MessageHandler
{
	private final InboundEMailConfig config;
	private final InboundEMailService emailService;

	@Builder
	public InboundEMailMessageHandler(
			@NonNull final InboundEMailConfig config,
			@NonNull final InboundEMailService emailService)
	{
		this.config = config;
		this.emailService = emailService;
	}

	@Override
	public void handleMessage(final Message<?> message) throws MessagingException
	{
		final InboundEMail email = toInboundEMail(message);
		emailService.onInboundEMailReceived(config, email);
	}

	private static InboundEMail toInboundEMail(final Message<?> message)
	{
		final MessageHeaders messageHeaders = message.getHeaders();
		@SuppressWarnings("unchecked")
		final MultiValueMap<String, Object> messageRawHeaders = messageHeaders.get(MailHeaders.RAW_HEADERS, MultiValueMap.class);

		final MailContent mailContent = MailContentCollector.toMailContent(messageHeaders.get(InboundEMailHeaderAndContentMapper.CONTENT));

		final String messageId = toMessageId(messageRawHeaders.getFirst("Message-ID"));
		final String firstMessageIdReference = toMessageId(messageRawHeaders.getFirst("References"));
		final String initialMessageId = CoalesceUtil.coalesce(firstMessageIdReference, messageId);

		return InboundEMail.builder()
				.from(messageHeaders.get(MailHeaders.FROM, String.class))
				.to(ImmutableList.copyOf(messageHeaders.get(MailHeaders.TO, String[].class)))
				.cc(ImmutableList.copyOf(messageHeaders.get(MailHeaders.CC, String[].class)))
				.bcc(ImmutableList.copyOf(messageHeaders.get(MailHeaders.BCC, String[].class)))
				.subject(messageHeaders.get(MailHeaders.SUBJECT, String.class))
				.content(mailContent.getText())
				.contentType(messageHeaders.get(MailHeaders.CONTENT_TYPE, String.class))
				.receivedDate(toZonedDateTime(messageHeaders.get(MailHeaders.RECEIVED_DATE)))
				.messageId(messageId)
				.initialMessageId(initialMessageId)
				.headers(convertMailHeadersToJson(messageRawHeaders))
				.attachments(mailContent.getAttachments())
				.build();
	}

	private static final String toMessageId(final Object messageIdObj)
	{
		if (messageIdObj == null)
		{
			return null;
		}

		return messageIdObj.toString();
	}

	private static ZonedDateTime toZonedDateTime(final Object dateObj)
	{
		return TimeUtil.asZonedDateTime(dateObj);
	}

	private static final ImmutableMap<String, Object> convertMailHeadersToJson(final MultiValueMap<String, Object> mailRawHeaders)
	{
		return mailRawHeaders.entrySet()
				.stream()
				.map(entry -> GuavaCollectors.entry(entry.getKey(), convertListToJson(entry.getValue())))
				.filter(entry -> entry.getValue() != null)
				.collect(GuavaCollectors.toImmutableMap());
	}

	private static final Object convertListToJson(final List<Object> values)
	{
		if (values == null || values.isEmpty())
		{
			return ImmutableList.of();
		}
		else if (values.size() == 1)
		{
			return convertValueToJson(values.get(0));
		}
		else
		{
			return values.stream()
					.map(v -> convertValueToJson(v))
					.filter(Objects::nonNull)
					.collect(ImmutableList.toImmutableList());
		}
	}

	private static final Object convertValueToJson(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		return value.toString();
	}
}
