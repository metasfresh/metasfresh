package de.metas.inbound.mail;

import java.time.ZonedDateTime;
import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.request.RequestId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
@Builder(toBuilder = true)
public class InboundEMail
{
	String from;
	ImmutableList<String> to;
	ImmutableList<String> cc;
	ImmutableList<String> bcc;

	String subject;
	String content;
	String contentType;

	@NonNull
	ZonedDateTime receivedDate;

	@NonNull
	String messageId;
	@NonNull
	String initialMessageId;

	@NonNull
	ImmutableMap<String, Object> headers;
	
	@NonNull
	@Singular
	ImmutableList<InboundEMailAttachment> attachments;

	RequestId requestId;

	public InboundEMail withRequestId(final RequestId requestId)
	{
		if (Objects.equals(this.requestId, requestId))
		{
			return this;
		}

		return toBuilder().requestId(requestId).build();
	}
}
