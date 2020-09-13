/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.commons.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIInvoiceFeedbackType;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlErrorFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.desadvexport.compudata.CompuDataDesadvRoute;
import de.metas.edi.esb.invoicexport.compudata.CompuDataInvoicRoute;
import de.metas.edi.esb.desadvexport.stepcom.StepComXMLDesadvRoute;
import de.metas.edi.esb.invoicexport.stepcom.StepComXMLInvoicRoute;

/**
 * In order to avoid endless loops when processing feedback, we're NOT extending AbstractEDIRoute here.<br>
 * Instead, we're using our own exception handler here to catch any exception in feedback, log it, and stop the route from continuing execution.<br>
 *
 * <b>NOTE: Any exception that occurs here should due to misconfiguring the properties.</b>
 */
@Component
public class EDIFeedbackRoute extends RouteBuilder
{
	/**
	 * The local folder where the EDI file will be stored if the conversion is <em>not</em> successful
	 */
	private static final String EP_EDI_LOCAL_ERROR = "{{edi.local.error}}";

	private static final String EP_EDI_ERROR_COMMON = "direct:edi.error.common";

	@Override
	public void configure()
	{
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_MF_DURABLE_ROUTING_KEY);

		// Catch any exception in feedback, log it, and stop the route from continuing execution.
		onException(Exception.class)
				.handled(true)
				.log(LoggingLevel.ERROR, exchangeProperty(Exchange.EXCEPTION_CAUGHT).toString())
				.to(AbstractEDIRoute.EP_EDI_LOG_ExceptionHandler)
				.stop();

		// At the moment, there's no need to differentiate between the DeadLetterChannel and Exception,
		// so send them both to the same queue to avoid duplicate code
		from(AbstractEDIRoute.EP_EDI_ERROR)
				.routeId("Exception-To-CommonError")
				.to(EDIFeedbackRoute.EP_EDI_ERROR_COMMON);

		from(AbstractEDIRoute.EP_EDI_DEADLETTER)
				.routeId("DeadLetter-To-CommonError")
				.to(EDIFeedbackRoute.EP_EDI_ERROR_COMMON);

		final Processor errorInvoiceProcessor = new EDIXmlErrorFeedbackProcessor<EDIInvoiceFeedbackType>(EDIInvoiceFeedbackType.class,
				CompuDataInvoicRoute.EDIInvoiceFeedback_QNAME, CompuDataInvoicRoute.METHOD_setCInvoiceID); // FIXME ugly
		final Processor errorDesadvProcessor = new EDIXmlErrorFeedbackProcessor<EDIDesadvFeedbackType>(EDIDesadvFeedbackType.class,
				CompuDataDesadvRoute.EDIDesadvFeedback_QNAME, CompuDataDesadvRoute.METHOD_setEDIDesadvID); // FIXME ugly

		// @formatter:off
		from(EDIFeedbackRoute.EP_EDI_ERROR_COMMON)
				.routeId("CommonError")
				.streamCaching()
				.to(AbstractEDIRoute.EP_EDI_LOG_ExceptionHandler)
				.choice()
					.when(exchangeProperty(AbstractEDIRoute.IS_CREATE_XML_FEEDBACK).isEqualTo(true))
						.log(LoggingLevel.INFO, "EDI: Creating error feedback XML Java Object...")
						.choice()
							//.when(body().isInstanceOf(EDICctopInvoicVType.class))
							.when(header(EDIXmlFeedbackHelper.HEADER_ROUTE_ID).isEqualTo(CompuDataInvoicRoute.ROUTE_ID))
								.process(errorInvoiceProcessor)
							.when(header(EDIXmlFeedbackHelper.HEADER_ROUTE_ID).isEqualTo(StepComXMLInvoicRoute.ROUTE_ID))
								.process(errorInvoiceProcessor)
							.when(header(EDIXmlFeedbackHelper.HEADER_ROUTE_ID).isEqualTo(CompuDataDesadvRoute.ROUTE_ID))
								.process(errorDesadvProcessor)
							.when(header(EDIXmlFeedbackHelper.HEADER_ROUTE_ID).isEqualTo(StepComXMLDesadvRoute.ROUTE_ID))
								.process(errorDesadvProcessor)
							.otherwise()
								.log(LoggingLevel.ERROR, "EDI: No available feedback processor found for header[HEADER_ROUTE_ID]=" + header(EDIXmlFeedbackHelper.HEADER_ROUTE_ID))
								.stop() // if there's no error handler, just forget about it...
						.end()
						.log(LoggingLevel.INFO,"EDI: Marshalling error feedback XML Java Object -> XML document...")
						.marshal(new JaxbDataFormat(Constants.JAXB_ContextPath))

						// If errors occurred, put the feedback in the error directory, and send it to metasfresh
						.log(LoggingLevel.INFO, "EDI: Sending error response to metasfresh...")
						.setHeader(Exchange.FILE_NAME, exchangeProperty(Exchange.FILE_NAME).append(".error.xml"))
						.setHeader("rabbitmq.ROUTING_KEY").simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc

						.multicast() // store the file both locally and send it to the remote folder
							.to("{{" + Constants.EP_AMQP_TO_MF + "}}", EDIFeedbackRoute.EP_EDI_LOCAL_ERROR)
						.end()
				.end();
		// @formatter:on
	}
}
