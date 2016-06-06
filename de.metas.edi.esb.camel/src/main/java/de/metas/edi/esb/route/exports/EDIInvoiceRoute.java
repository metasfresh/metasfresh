package de.metas.edi.esb.route.exports;

/*
 * #%L
 * de.metas.edi.esb
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.text.DecimalFormat;

import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.spi.DataFormat;
import org.milyn.smooks.camel.dataformat.SmooksDataFormat;

import de.metas.edi.esb.bean.invoice.EDICctopInvoiceBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.EDIInvoiceFeedbackType;
import de.metas.edi.esb.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.route.AbstractEDIRoute;

public class EDIInvoiceRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "XML-Invoice-To-EDI-Invoic";

	private static final String EDI_INVOICE_FILENAME_PATTERN = "edi.file.invoice.filename";

	public static final String EP_EDI_INVOICE_CONSUMER = "direct:edi.invoice.consumer";

	public static final String EDI_INVOICE_SENDER_GLN = "edi.props.000.sender.gln";
	public static final String EDI_INVOICE_IS_TEST = "edi.props.invoice.isTest";

	public final static QName EDIInvoiceFeedback_QNAME = new QName("", "EDI_Invoice_Feedback"); // FIXME see how to take it from object factory!
	public static final String METHOD_setCInvoiceID = "setCInvoiceID";

	/**
	 * The FILE folder where the EDI file will be stored
	 */
	public static final String EP_EDI_FILE_INVOICE = "{{edi.file.invoice}}";

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final SmooksDataFormat sdf = getSDFForConfiguration("edi.smooks.config.xml.invoices");

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String invoiceFilenamePattern = Util.resolvePropertyPlaceholders(getContext(), EDIInvoiceRoute.EDI_INVOICE_FILENAME_PATTERN);

		final String senderGln = Util.resolvePropertyPlaceholders(getContext(), EDIInvoiceRoute.EDI_INVOICE_SENDER_GLN);
		final String isTest = Util.resolvePropertyPlaceholders(getContext(), EDIInvoiceRoute.EDI_INVOICE_IS_TEST);

		from(EDIInvoiceRoute.EP_EDI_INVOICE_CONSUMER)
				.routeId(ROUTE_ID)

		.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(EDIInvoiceRoute.EDI_INVOICE_SENDER_GLN).constant(senderGln)
				.setProperty(EDIInvoiceRoute.EDI_INVOICE_IS_TEST).constant(isTest)

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

		.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI Java Object...")
				.bean(EDICctopInvoiceBean.class, EDICctopInvoiceBean.METHOD_createEDIData)

		.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to EDI Format using SDF...")
				.marshal(sdf)

		.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(invoiceFilenamePattern)

		.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to(EDIInvoiceRoute.EP_EDI_FILE_INVOICE)

		.log(LoggingLevel.INFO, "EDI: Creating ADempiere feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<EDIInvoiceFeedbackType>(EDIInvoiceFeedbackType.class, EDIInvoiceRoute.EDIInvoiceFeedback_QNAME, EDIInvoiceRoute.METHOD_setCInvoiceID))

		.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

		.log(LoggingLevel.INFO, "EDI: Sending success response to ADempiere...")
				.to(Constants.EP_JMS_TO_AD);
	}
}
