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
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.bean.desadv.StepComXMLDesadvBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.stepcom.desadv.ObjectFactory;
import de.metas.edi.esb.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.route.AbstractEDIRoute;

@Component
public class StepComXMLDesadvRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Desadv-To-STEPCOM-XML-Desadv";

	private static final String EDI_DESADV_XML_FILENAME_PATTERN = "edi.file.desadv.stepcom-xml.filename";

	public static final String EP_EDI_STEPCOM_XML_DESADV_CONSUMER = "direct:edi.xml.desadv.consumer";

	public static final String EDI_XML_OWNER_ID = "edi.props.stepcom.owner.id";

	public static final String EDI_XML_SUPPLIER_GLN = "edi.props.desadv.stepcom.supplier.gln";

	private final static QName EDIDesadvFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIDesadvFeedback(null).getName();

	private static final String METHOD_setEDIDesadvID = "setEDIDesadvID";

	private static final String OUTPUT_DESADV_LOCAL = "{{edi.file.desadv.stepcom-xml}}";

	private static final String OUTPUT_DESADV_REMOTE = "edi.file.desadv.stepcom-xml.remote";

	private static final String JAXB_DESADV_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final String charset = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_STEPCOM_CHARSET_NAME);

		final JaxbDataFormat dataFormat = new JaxbDataFormat(JAXB_DESADV_CONTEXTPATH);
		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(charset);

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String desadvFilenamePattern = Util.resolveProperty(getContext(), StepComXMLDesadvRoute.EDI_DESADV_XML_FILENAME_PATTERN);

		final String ownerId = Util.resolveProperty(getContext(), StepComXMLDesadvRoute.EDI_XML_OWNER_ID);
		final String supplierGln = Util.resolveProperty(getContext(), StepComXMLDesadvRoute.EDI_XML_SUPPLIER_GLN);

		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), StepComXMLDesadvRoute.EDI_ORDER_EDIMessageDatePattern);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_AD_DURABLE_ROUTING_KEY);

		final String remoteEndpoint = Util.resolveProperty(getContext(), OUTPUT_DESADV_REMOTE, "");

		final LoggingLevel fileEPLogLevel;
		if (Util.isEmpty(remoteEndpoint)) // if we send everything to the remote-EP, then log what we send to file only on "TRACE" log level
		{
			fileEPLogLevel = LoggingLevel.INFO;
		}
		else
		{
			fileEPLogLevel = LoggingLevel.TRACE;
		}

		final RouteDefinition routeDefinition = from(StepComXMLDesadvRoute.EP_EDI_STEPCOM_XML_DESADV_CONSUMER)
				.routeId(ROUTE_ID)

				.log(LoggingLevel.INFO, "Setting defaults as exchange properties...")
				.setProperty(StepComXMLDesadvRoute.EDI_XML_OWNER_ID).constant(ownerId)
				.setProperty(StepComXMLDesadvRoute.EDI_XML_SUPPLIER_GLN).constant(supplierGln)
				.setProperty(StepComXMLDesadvRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.log(LoggingLevel.INFO, "Setting EDI feedback headers...")
				.process(exchange -> {
					// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);

					final EDIExpDesadvType xmlDesadv = exchange.getIn().getBody(EDIExpDesadvType.class); // throw exceptions if mandatory fields are missing

					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlDesadv.getADClientValueAttr());
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlDesadv.getEDIDesadvID().longValue());
				})

				.log(LoggingLevel.INFO, "Converting metasfresh-XML Java Object -> stepCOM-XML Java Object...")
				.bean(StepComXMLDesadvBean.class, StepComXMLDesadvBean.METHOD_createXMLEDIData)
				.log(LoggingLevel.INFO, "Marshalling stepCOM-XML Java Object -> XML...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(desadvFilenamePattern)

				.log(fileEPLogLevel, "Storing stepCOM-XML to the FILE endpoint:\r\n" + body())
				.to(StepComXMLDesadvRoute.OUTPUT_DESADV_LOCAL);

		if (!Util.isEmpty(remoteEndpoint))
		{
			routeDefinition
					.log(LoggingLevel.INFO, "Uploading stepCOM-XML file to remote endpoint:\r\n" + body())
					.to(remoteEndpoint);
		}

		routeDefinition
				.log(LoggingLevel.INFO, "Creating metasfresh feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIDesadvFeedbackType.class, StepComXMLDesadvRoute.EDIDesadvFeedback_QNAME, StepComXMLDesadvRoute.METHOD_setEDIDesadvID))

				.log(LoggingLevel.INFO, "Marshalling metasfresh feedback XML Java Object -> XML...")
				.marshal(jaxb)

				.log(LoggingLevel.INFO, "Sending success response to metasfresh...")
				.setHeader("rabbitmq.ROUTING_KEY").simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
				.to(Constants.EP_AMQP_TO_AD);

	}
}
