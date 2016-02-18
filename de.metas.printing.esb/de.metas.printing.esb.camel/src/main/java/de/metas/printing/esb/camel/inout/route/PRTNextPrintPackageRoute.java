package de.metas.printing.esb.camel.inout.route;

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

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.camel.commons.Constants;
import de.metas.printing.esb.camel.processor.bean.GetNextPrintPackageADRequestProcessor;
import de.metas.printing.esb.camel.processor.bean.GetNextPrintPackageADResponseProcessor;

public class PRTNextPrintPackageRoute extends RouteBuilder
{
	private static final String PROPERTY_ORIGINAL_BODY = "originalBody";

	@Override
	public void configure()
	{
		final DataFormat jaxb = new JaxbDataFormat(JAXBConstants.JAXB_ContextPath);

		onException(Exception.class)
				.handled(true)
				.transform(exceptionMessage())
				.to(PRTRestServiceRoute.EP_CXF_RS_ERROR);

		errorHandler(deadLetterChannel(PRTRestServiceRoute.EP_CXF_RS_ERROR));

		// @formatter:off
		from(PRTRestServiceRoute.EP_NextPrintPackage)
				
				// Use the input's sessionId and transactionId to construct a xml print package object which metasfresh will fill with the required data
				.process(new GetNextPrintPackageADRequestProcessor())
				
				.marshal(jaxb)
				// Set the body as a property for backup in case the response is null
				.setProperty(PROPERTY_ORIGINAL_BODY, bodyAs(String.class))
								
				.doTry()
					// Send the object to metasfresh and wait for a response
					.inOut(Constants.EP_JMS_TO_AD)

				.doCatch(Exception.class)
					// in case of an exception, return the original (empty) print package
					.transform(exchangeProperty(PROPERTY_ORIGINAL_BODY))
					.log(LoggingLevel.WARN, "Caught Exception: " + exceptionMessage())
				.end()

				// If the received body is null, set it with the previously set property
				.choice()
					.when(body().isNull())
						.transform(exchangeProperty(PROPERTY_ORIGINAL_BODY))
				.end()

				.unmarshal(jaxb)

				// Once we have the response, we convert the object to the printing client-specific POJO
				.process(new GetNextPrintPackageADResponseProcessor());
				// marshaling is already done by cxf
		// @formatter:on
	}
}
