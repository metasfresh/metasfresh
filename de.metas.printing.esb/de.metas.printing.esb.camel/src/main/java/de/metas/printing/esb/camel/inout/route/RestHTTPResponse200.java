package de.metas.printing.esb.camel.inout.route;

/*
 * #%L
 * Metas :: Components :: Request-Response Framework for Mass Printing (SMX-4.5.2)
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


import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public final class RestHTTPResponse200 implements Processor
{
	public static final RestHTTPResponse200 instance = new RestHTTPResponse200();

	private RestHTTPResponse200()
	{
		super();
	}

	@Override
	public void process(Exchange exchange)
	{
		final String str = exchange.getIn().getBody(String.class);
		final Response r = Response.status(200)
				.entity(str)
				.build();
		exchange.getOut().setBody(r);
	}
}
