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


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.camel.commons.Constants;
import de.metas.printing.esb.camel.processor.bean.GetPrintPackageDataADRequestProcessor;
import de.metas.printing.esb.camel.processor.bean.GetPrintPackageDataADResponseProcessor;

public class PRTPrintPackageDataRoute extends RouteBuilder
{
	@Override
	public void configure()
	{
		final DataFormat jaxb = new JaxbDataFormat(JAXBConstants.JAXB_ContextPath);

		onException(Exception.class)
				.handled(true)
				.transform(exceptionMessage())
				.to(PRTRestServiceRoute.EP_CXF_RS_ERROR);

		errorHandler(deadLetterChannel(PRTRestServiceRoute.EP_CXF_RS_ERROR));

		from(PRTRestServiceRoute.EP_PrintPackageData)
				//
				// Make an xml print package object which adempiere will fill with the required data
				.process(new GetPrintPackageDataADRequestProcessor())
				//
				// Send the object to adempiere and wait for a response
				.marshal(jaxb)
				.inOut(Constants.EP_JMS_TO_AD)
				//
				// Once we have the response, we convert the object to the printing client-specific POJO
				.unmarshal(jaxb)
				//
				// Now convert the exchange to a Base64-encoded byte array (the document itself) as the response to the http request
				.process(new GetPrintPackageDataADResponseProcessor())
				//
				// Send it to archive
				.to(Constants.EP_PRINT_PACKAGE_DATA_ARCHIVE);
	}
}
