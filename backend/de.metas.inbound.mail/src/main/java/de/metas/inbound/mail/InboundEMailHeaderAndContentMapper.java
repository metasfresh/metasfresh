package de.metas.inbound.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.integration.mail.support.DefaultMailHeaderMapper;

import de.metas.logging.LogManager;

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

final class InboundEMailHeaderAndContentMapper extends DefaultMailHeaderMapper
{
	public static InboundEMailHeaderAndContentMapper newInstance()
	{
		return new InboundEMailHeaderAndContentMapper();
	}

	/**
	 * IMPORTANT: the reason why we are fetching the content here is because if we do it later we will get FolderClosedException
	 */
	public static final String CONTENT = "content";

	private static final Logger logger = LogManager.getLogger(InboundEMailHeaderAndContentMapper.class);

	private InboundEMailHeaderAndContentMapper()
	{
	}

	@Override
	public Map<String, Object> toHeaders(MimeMessage source)
	{
		final Map<String, Object> headers = super.toHeaders(source);

		try
		{
			final MailContent content = MailContentCollector.toMailContent(source.getContent());
			headers.put(CONTENT, content);
		}
		catch (Exception ex)
		{
			logger.warn("Failed extracting content from message {}. Ignored.", source, ex);
		}

		return headers;
	}

}
