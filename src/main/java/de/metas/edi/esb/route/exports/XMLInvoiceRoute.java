/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.edi.esb.route.exports;

import de.metas.edi.esb.bean.invoice.EDIXMLInvoiceBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.EDIInvoiceFeedbackType;
import de.metas.edi.esb.pojo.invoice.ObjectFactory;
import de.metas.edi.esb.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.route.AbstractEDIRoute;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.text.DecimalFormat;

@Component
public class XMLInvoiceRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "XML-Invoice-To-XML-EDI-Invoic";

	private static final String EDI_INVOICE_XML_FILENAME_PATTERN = "edi.file.invoice.xml.filename";

	public static final String EP_EDI_INVOICE_XML_CONSUMER = "direct:edi.invoice.xml.consumer";

	public static final String EDI_INVOICE_SENDER_GLN = "edi.props.000.sender.gln";
	public static final String EDI_XML_INVOICE_IS_TEST = "edi.xml.props.invoice.isTest";

	public final static QName EDIInvoiceFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIInvoiceFeedback(null).getName();
	public static final String METHOD_setCInvoiceID = "setCInvoiceID";

	/**
	 * The FILE folder where the EDI file will be stored
	 */
	public static final String EP_EDI_XML_FILE_INVOICE = "{{edi.file.invoice.xml}}";

	private static final String JAXB_INVOICE_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		JaxbDataFormat dataFormat = new JaxbDataFormat(JAXB_INVOICE_CONTEXTPATH);
		dataFormat.setCamelContext(getContext());

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String invoiceXMLFilenamePattern = Util.resolvePropertyPlaceholders(getContext(), XMLInvoiceRoute.EDI_INVOICE_XML_FILENAME_PATTERN);

		final String senderGln = Util.resolvePropertyPlaceholders(getContext(), XMLInvoiceRoute.EDI_INVOICE_SENDER_GLN);
		final String isTest = Util.resolvePropertyPlaceholders(getContext(), XMLInvoiceRoute.EDI_XML_INVOICE_IS_TEST);
		final String defaultEDIMessageDatePattern = Util.resolvePropertyPlaceholders(getContext(), XMLInvoiceRoute.EDI_ORDER_EDIMessageDatePattern);

		from(XMLInvoiceRoute.EP_EDI_INVOICE_XML_CONSUMER)
				.routeId(ROUTE_ID)

				.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(XMLInvoiceRoute.EDI_INVOICE_SENDER_GLN).constant(senderGln)
				.setProperty(XMLInvoiceRoute.EDI_XML_INVOICE_IS_TEST).constant(isTest)
				.setProperty(XMLInvoiceRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.log(LoggingLevel.INFO, "EDI: Setting EDI feedback headers...")
				.process(new Processor()
				{
					@Override
					public void process(final Exchange exchange)
					{
						// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);

						final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlCctopInvoice.getADClientValueAttr());
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlCctopInvoice.getCInvoiceID());
					}
				})

				.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI XML Java Object...")
				.bean(EDIXMLInvoiceBean.class, EDIXMLInvoiceBean.METHOD_createXMLEDIData)

				.log(LoggingLevel.INFO, "EDI: Marshalling EDI XML Java Object to XML...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(invoiceXMLFilenamePattern)

				.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to(XMLInvoiceRoute.EP_EDI_XML_FILE_INVOICE)

				.log(LoggingLevel.INFO, "EDI: Creating ADempiere feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<EDIInvoiceFeedbackType>(EDIInvoiceFeedbackType.class, XMLInvoiceRoute.EDIInvoiceFeedback_QNAME, XMLInvoiceRoute.METHOD_setCInvoiceID))

				.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

				.log(LoggingLevel.INFO, "EDI: Sending success response to ADempiere...")
				.to(Constants.EP_AMQP_TO_AD);
	}
}
