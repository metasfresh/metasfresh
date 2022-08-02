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

package de.metas.edi.esb.invoicexport.compudata;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.commons.route.exports.ReaderTypeConverter;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIInvoiceFeedbackType;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.spi.DataFormat;
import org.smooks.cartridges.camel.dataformat.SmooksDataFormat;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

@Component
public class CompuDataInvoicRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Invoic-To-COMPUDATA-Invoic";

	private static final String EDI_INVOICE_FILENAME_PATTERN = "edi.file.invoic.compudata.filename";

	public static final String EP_EDI_COMPUDATA_INVOIC_CONSUMER = "direct:edi.invoic.consumer";

	public static final String EDI_INVOIC_SENDER_GLN = "edi.props.000.sender.gln";

	public final static QName EDIInvoiceFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIInvoiceFeedback(null).getName();
	public static final String METHOD_setCInvoiceID = "setCInvoiceID";

	/**
	 * The FILE folder where the EDI file will be stored
	 */
	public static final String EP_EDI_FILE_INVOICE = "edi.file.invoic.compudata";

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final SmooksDataFormat sdf = getSDFForConfiguration("edi.smooks.config.xml.invoic");
		sdf.setCamelContext(getContext());

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String invoiceFilenamePattern = Util.resolveProperty(getContext(), CompuDataInvoicRoute.EDI_INVOICE_FILENAME_PATTERN);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_MF_DURABLE_ROUTING_KEY);
		final String senderGln = Util.resolveProperty(getContext(), CompuDataInvoicRoute.EDI_INVOIC_SENDER_GLN);

		from(CompuDataInvoicRoute.EP_EDI_COMPUDATA_INVOIC_CONSUMER)
				.routeId(ROUTE_ID)

		.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(CompuDataInvoicRoute.EDI_INVOIC_SENDER_GLN).constant(senderGln)

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
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlCctopInvoice.getCInvoiceID().longValue());
					}
				})

		.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI Java Object...")
				.bean(CompuDataInvoicBean.class, CompuDataInvoicBean.METHOD_createEDIData)

		.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to EDI Format using SDF...")
				.marshal(sdf)

		.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(invoiceFilenamePattern)

		.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to("{{" + CompuDataInvoicRoute.EP_EDI_FILE_INVOICE + "}}")

		.log(LoggingLevel.INFO, "EDI: Creating metasfresh feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIInvoiceFeedbackType.class, CompuDataInvoicRoute.EDIInvoiceFeedback_QNAME, CompuDataInvoicRoute.METHOD_setCInvoiceID))

		.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

		.log(LoggingLevel.INFO, "EDI: Sending success response to metasfresh...")
		
		.setHeader(RabbitMQConstants.ROUTING_KEY).simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
 		.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
		.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
	}
}
