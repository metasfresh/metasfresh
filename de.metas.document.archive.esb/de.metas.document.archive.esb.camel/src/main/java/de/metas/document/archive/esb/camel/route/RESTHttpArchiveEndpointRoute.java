package de.metas.document.archive.esb.camel.route;

/*
 * #%L
 * de.metas.document.archive.esb.camel
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


import java.util.Arrays;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import de.metas.document.archive.esb.api.IArchiveEndpoint;
import de.metas.document.archive.esb.camel.commons.CamelConstants;
import de.metas.document.archive.esb.camel.cxf.jaxrs.RESTHttpArchiveEndpoint;

/**
 * RESTful routes for document archive
 * 
 * @author al
 */
public class RESTHttpArchiveEndpointRoute extends RouteBuilder
{
	public static final String EP_CXF_RS = CamelConstants.EP_REST_HTTP_PREFIX + RESTHttpArchiveEndpoint.class.getName();

	public static final String EP_GetArchiveData = "direct:get_archive_data";
	public static final String EP_SetArchiveData = "direct:set_archive_data";

	@Override
	public void configure() throws Exception
	{
		org.apache.cxf.jaxrs.provider.ProviderFactory.getSharedInstance().setUserProviders(Arrays.asList(new JacksonJsonProvider()));

		onException(Exception.class)
				.handled(true)
				// .transform(exceptionMessage())
				.to(CamelConstants.EP_ERRORS);

		errorHandler(deadLetterChannel(CamelConstants.EP_ERRORS));

		// @formatter:off
		from(RESTHttpArchiveEndpointRoute.EP_CXF_RS)
				.setExchangePattern(ExchangePattern.InOut)

				// detect from which RESTful controller the request is coming from
				.choice()
					//
					// retrieve archive data
					.when(header(CxfConstants.OPERATION_NAME).isEqualTo(IArchiveEndpoint.OPERATION_GetArchiveData))
						.to(RESTHttpArchiveEndpointRoute.EP_GetArchiveData)
					//
					// send archive data
					.when(header(CxfConstants.OPERATION_NAME).isEqualTo(IArchiveEndpoint.OPERATION_SetArchiveData))
						.to(RESTHttpArchiveEndpointRoute.EP_SetArchiveData)
		;
		// @formatter:on
	}
}
