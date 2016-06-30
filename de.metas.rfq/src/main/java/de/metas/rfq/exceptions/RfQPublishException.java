package de.metas.rfq.exceptions;

import de.metas.rfq.model.I_C_RfQResponse;

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

	private I_C_RfQResponse rfqResponse;

	public RfQPublishException(final I_C_RfQResponse rfqResponse, final String message)
	{
		super(message);

		setC_RfQResponse(rfqResponse);
	}

	private RfQPublishException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public RfQPublishException setC_RfQResponse(final I_C_RfQResponse rfqResponse)
	{
		this.rfqResponse = rfqResponse;
		resetMessageBuilt();
		return this;
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@Error@");
		if (rfqResponse != null)
		{
			sb.append(" ").append(buildInfoString(rfqResponse));
		}
		sb.append(": ");
		sb.append(getOriginalMessage());

		return sb.toString();
	}
}
