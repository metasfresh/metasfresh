package de.metas.rfq.exceptions;

import de.metas.rfq.RfQResponsePublisherRequest;

/*
 * #%L
 * de.metas.rfq
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

@SuppressWarnings("serial")
public class RfQPublishException extends RfQException
{
	public static final RfQPublishException wrapIfNeeded(final Throwable e)
	{
		if (e instanceof RfQPublishException)
		{
			return (RfQPublishException)e;
		}

		final Throwable cause = extractCause(e);
		return new RfQPublishException(cause.getMessage(), cause);
	}

	private RfQResponsePublisherRequest request;

	public RfQPublishException(final RfQResponsePublisherRequest request, final String message)
	{
		super(message);

		setRequest(request);
	}

	private RfQPublishException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public RfQPublishException setRequest(final RfQResponsePublisherRequest request)
	{
		this.request = request;
		resetMessageBuilt();
		return this;
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@Error@");
		if (request != null)
		{
			sb.append(" ").append(request.getSummary());
		}
		sb.append(": ");
		sb.append(getOriginalMessage());

		return sb.toString();
	}
}
