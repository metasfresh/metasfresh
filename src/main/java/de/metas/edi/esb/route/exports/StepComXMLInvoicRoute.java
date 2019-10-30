/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.edi.esb.route.exports;

import java.text.DecimalFormat;

import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.bean.invoic.StepComXMLInvoicBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIInvoiceFeedbackType;
import de.metas.edi.esb.jaxb.stepcom.invoice.ObjectFactory;
import de.metas.edi.esb.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.route.AbstractEDIRoute;

@Component
public class StepComXMLInvoicRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Invoice-To-STEPCOM-XML-Invoic";

	private static final String EDI_STEPCOM_XML_INVOICE_FILENAME_PATTERN = "edi.file.invoic.stepcom-xml.filename";

	public static final String EP_EDI_STEPCOM_XML_INVOICE_CONSUMER = "direct:edi.invoic.stepcom-xml.consumer";

	public static final String EDI_XML_OWNER_ID = "edi.props.stepcom.owner.id";
	public static final String EDI_XML_APPLICATION_REF = "edi.props.stepcom.application.ref";

	private static final String EDI_INVOICE_SENDER_GLN = "edi.props.000.sender.gln";

	private final static QName EDIInvoiceFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIInvoiceFeedback(null).getName();
	private static final String METHOD_setCInvoiceID = "setCInvoiceID";

	private static final String EP_EDI_XML_FILE_INVOICE = "{{edi.file.invoic.stepcom-xml}}";

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

		final String invoiceXMLFilenamePattern = Util.resolveProperty(getContext(), EDI_STEPCOM_XML_INVOICE_FILENAME_PATTERN);

		final String senderGln = Util.resolveProperty(getContext(), EDI_INVOICE_SENDER_GLN);
		final String ownerId = Util.resolveProperty(getContext(), EDI_XML_OWNER_ID);
		final String applicationRef = Util.resolveProperty(getContext(), EDI_XML_APPLICATION_REF);
		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_AD_DURABLE_ROUTING_KEY);

		from(EP_EDI_STEPCOM_XML_INVOICE_CONSUMER)
				.routeId(ROUTE_ID)

				.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(EDI_INVOICE_SENDER_GLN).constant(senderGln)
				.setProperty(EDI_XML_OWNER_ID).constant(ownerId)
				.setProperty(EDI_XML_APPLICATION_REF).constant(applicationRef)
				.setProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.log(LoggingLevel.INFO, "EDI: Setting EDI feedback headers...")
				.process(exchange -> {
					// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);

					final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlCctopInvoice.getADClientValueAttr());
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlCctopInvoice.getCInvoiceID().longValue());
				})

				.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI XML Java Object...")
				.bean(StepComXMLInvoicBean.class, StepComXMLInvoicBean.METHOD_createXMLEDIData)

				.log(LoggingLevel.INFO, "EDI: Marshalling EDI XML Java Object to XML...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(invoiceXMLFilenamePattern)

				.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to(EP_EDI_XML_FILE_INVOICE)

				.log(LoggingLevel.INFO, "EDI: Creating metasfresh feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIInvoiceFeedbackType.class, EDIInvoiceFeedback_QNAME, METHOD_setCInvoiceID))

				.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

				.log(LoggingLevel.INFO, "EDI: Sending success response to metasfresh...")
				.setHeader("rabbitmq.ROUTING_KEY").simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
				.to(Constants.EP_AMQP_TO_AD);
	}
}
