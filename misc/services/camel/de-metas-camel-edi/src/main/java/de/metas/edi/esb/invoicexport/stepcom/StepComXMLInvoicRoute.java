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

package de.metas.edi.esb.invoicexport.stepcom;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.commons.route.exports.ReaderTypeConverter;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIInvoiceFeedbackType;
import de.metas.edi.esb.jaxb.stepcom.invoice.ObjectFactory;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Arrays;

@Component
@PropertySources(value = {
		@PropertySource(value = "classpath:/invoic-customer.properties"),
		@PropertySource(value = "file:./invoic-customer.properties", ignoreResourceNotFound = true)
})
public class StepComXMLInvoicRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Invoic-To-STEPCOM-XML-Invoic";

	public static final String EP_EDI_STEPCOM_XML_INVOICE_CONSUMER = "direct:edi.invoic.stepcom.consumer";

	public static final String EDI_XML_OWNER_ID = "edi.props.stepcom.owner.id";

	private static final String EDI_INVOICE_SENDER_GLN = "edi.props.000.sender.gln";

	private final static QName EDIInvoiceFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIInvoiceFeedback(null).getName();
	private static final String METHOD_setCInvoiceID = "setCInvoiceID";

	private static final String OUTPUT_INVOIC_LOCAL = "{{edi.file.invoic.stepcom}}";

	private static final String OUTPUT_INVOIC_REMOTE = "edi.file.invoic.stepcom.remote";

	private static final String JAXB_INVOIC_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final String charset = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_STEPCOM_CHARSET_NAME);

		final JaxbDataFormat dataFormat = new JaxbDataFormat(JAXB_INVOIC_CONTEXTPATH);
		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(charset);

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String senderGln = Util.resolveProperty(getContext(), EDI_INVOICE_SENDER_GLN);
		final String ownerId = Util.resolveProperty(getContext(), EDI_XML_OWNER_ID);
		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_MF_DURABLE_ROUTING_KEY);

		final String remoteEndpoint = Util.resolveProperty(getContext(), OUTPUT_INVOIC_REMOTE, "");
		final String[] endPointURIs;
		if (Util.isEmpty(remoteEndpoint)) // if we send everything to the remote-EP, then log what we send to file only on "TRACE" log level
		{
			endPointURIs = new String[] { OUTPUT_INVOIC_LOCAL };
		}
		else
		{
			endPointURIs = new String[] { OUTPUT_INVOIC_LOCAL, remoteEndpoint };
		}

		from(EP_EDI_STEPCOM_XML_INVOICE_CONSUMER)
				.routeId(ROUTE_ID)
				.streamCaching()

				.log(LoggingLevel.INFO, "Setting defaults as exchange properties...")
				.setProperty(EDI_INVOICE_SENDER_GLN).constant(senderGln)
				.setProperty(EDI_XML_OWNER_ID).constant(ownerId)
				.setProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.log(LoggingLevel.INFO, "Setting EDI feedback headers...")
				.process(exchange -> {
					// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);

					final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlCctopInvoice.getADClientValueAttr());
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlCctopInvoice.getCInvoiceID().longValue());
				})

				.log(LoggingLevel.INFO, "Converting XML Java Object -> EDI XML Java Object...")
				.bean(StepComXMLInvoicBean.class, StepComXMLInvoicBean.METHOD_createXMLEDIData)

				.log(LoggingLevel.INFO, "Marshalling EDI XML Java Object to XML...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "Output filename=${in.headers." + Exchange.FILE_NAME + "}; endpointUri=" + Arrays.toString(endPointURIs))
				.log(LoggingLevel.INFO, "Sending STEPcom-XML to the endpoint(s):\r\n" + body())
				.multicast().stopOnException().parallelProcessing(false).to(endPointURIs)
				.end()

				.log(LoggingLevel.INFO, "Creating ecosio feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIInvoiceFeedbackType.class, EDIInvoiceFeedback_QNAME, METHOD_setCInvoiceID))
				.log(LoggingLevel.INFO, "Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)
				.log(LoggingLevel.INFO, "Sending success response to ecosio...")
				.setHeader(RabbitMQConstants.ROUTING_KEY).simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
				.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
	}
}
