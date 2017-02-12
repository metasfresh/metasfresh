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

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.camel.commons.Constants;
import de.metas.printing.esb.camel.processor.route.SessionIdProcessor;
import de.metas.printing.esb.camel.processor.route.TransactionIdProcessor;

/**
 * Sets up the cxf endpoint and decides to which specific routes the request shall be forwarded.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PRTRestServiceRoute extends RouteBuilder
{
	public static final String EP_CXF_RS_ERROR = "{{cxf.rs.error}}";

	public static final String EP_PrinterHW = "direct:printer_cfg";
	public static final String EP_NextPrintPackage = "direct:next_print_pckg";
	public static final String EP_PrintPackageData = "direct:print_pckg_data";
	public static final String EP_PrintPackageResponse = "direct:print_pckg_response";

	@Override
	public void configure()
	{
		onException(Exception.class)
				.handled(true)
				.transform(exceptionMessage())
				.to(EP_CXF_RS_ERROR);

		errorHandler(deadLetterChannel(EP_CXF_RS_ERROR));

		// @formatter:off
		from(Constants.EP_CXF_RS)
				.setExchangePattern(ExchangePattern.InOut)
							
				// store the sessionID in the header
				.process(SessionIdProcessor.instance)

				// detect from which RESTful controller the request is coming from
				.choice()
					.routeId("rest-operation-switch")
					
					.when(header(CxfConstants.OPERATION_NAME).isEqualTo(PRTRestServiceConstants.OPERATION_Login))
						.to(PRTLoginRoute.EP_Client_Login)
						
					.when(header(CxfConstants.OPERATION_NAME).isEqualTo(PRTRestServiceConstants.OPERATION_AddPrinterHW))
						// send the data to adempiere
						.to(EP_PrinterHW)
						// return status 200 (success) regardless of the outcome
						.process(RestHTTPResponse200.instance)

					.otherwise()
						// store the transactionId in the header
						.process(TransactionIdProcessor.instance)

						.choice()
							.when(header(CxfConstants.OPERATION_NAME).isEqualTo(PRTRestServiceConstants.OPERATION_GetNextPrintPackage))
								.to(EP_NextPrintPackage)
							//
							.when(header(CxfConstants.OPERATION_NAME).isEqualTo(PRTRestServiceConstants.OPERATION_GetPrintPackageData))
								.to(EP_PrintPackageData)
							//
							.when(header(CxfConstants.OPERATION_NAME).isEqualTo(PRTRestServiceConstants.OPERATION_SendPrintPackageResponse))
								.to(EP_PrintPackageResponse)
								
							// return status 200 (success) regardless of the outcome
							.process(RestHTTPResponse200.instance);
		// @formatter:on
	}
}
