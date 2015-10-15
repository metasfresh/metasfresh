package de.metas.printing.esb.camel.processor.route;

/*
 * #%L
 * de.metas.printing.esb.camel
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.cxf.message.MessageContentsList;

import de.metas.printing.esb.api.PRTRestServiceConstants;

public final class SessionIdProcessor implements Processor
{
	public static final transient SessionIdProcessor instance = new SessionIdProcessor();

	private SessionIdProcessor()
	{
		super();
	}

	@Override
	public void process(Exchange exchange)
	{
		final Message inMessage = exchange.getIn();
		final MessageContentsList list = inMessage.getBody(MessageContentsList.class);

		final Integer sessionId = (Integer)list.remove(0);
		if (sessionId == null)
		{
			throw new RuntimeCamelException("Missing sessionId");
		}
		
		// NOTE: we are tolerating sessionId<=0 because some of the routes does not need an SessionId (e.g. LoginRequest)

		inMessage.setHeader(PRTRestServiceConstants.PARAM_SessionId, sessionId);
	}
}
