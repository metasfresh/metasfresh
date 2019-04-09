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

import de.metas.edi.esb.bean.desadv.EDIXMLDesadvBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;
import de.metas.edi.esb.pojo.desadv.ObjectFactory;
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
public class XMLDesadvRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID_AGGREGATE = "XML-InOut-To-XML-EDI-DESADV-Aggregate";

	private static final String EDI_DESADV_XML_FILENAME_PATTERN = "edi.file.desadv.xml.filename";

	public static final String EP_EDI_XML_DESADV_AGGREGATE = "direct:edi.xml.desadv.consumer";

	public static final String EDI_XML_DESADV_IS_TEST = "edi.xml.props.desadv.isTest";

	public final static QName EDIDesadvFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIDesadvFeedback(null).getName();

	public static final String METHOD_setEDIDesadvID = "setEDIDesadvID";

	public static final String EP_EDI_FILE_DESADV_XML = "{{edi.file.desadv.xml}}";

	private static final String JAXB_DESADV_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{

		de.metas.edi.esb.jaxb.ObjectFactory objFac = new de.metas.edi.esb.jaxb.ObjectFactory();
		objFac.createEDIDesadvFeedback(null).getName();
		JaxbDataFormat dataFormat = new JaxbDataFormat(JAXB_DESADV_CONTEXTPATH);
		dataFormat.setCamelContext(getContext());

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String desadvFilenamePattern = Util.resolvePropertyPlaceholders(getContext(), XMLDesadvRoute.EDI_DESADV_XML_FILENAME_PATTERN);

		final String isTest = Util.resolvePropertyPlaceholders(getContext(), XMLDesadvRoute.EDI_XML_DESADV_IS_TEST);
		final String defaultEDIMessageDatePattern = Util.resolvePropertyPlaceholders(getContext(), XMLDesadvRoute.EDI_ORDER_EDIMessageDatePattern);

		from(XMLDesadvRoute.EP_EDI_XML_DESADV_AGGREGATE)
				.routeId(ROUTE_ID_AGGREGATE)

				.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(XMLDesadvRoute.EDI_XML_DESADV_IS_TEST).constant(isTest)
				.setProperty(XMLDesadvRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.log(LoggingLevel.INFO, "EDI: Setting EDI feedback headers...")
				.process(new Processor()
				{
					@Override
					public void process(final Exchange exchange)
					{
						// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID_AGGREGATE);

						final EDIExpDesadvType xmlDesadv = exchange.getIn().getBody(EDIExpDesadvType.class); // throw exceptions if mandatory fields are missing

						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlDesadv.getADClientValueAttr());
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlDesadv.getEDIDesadvID());
					}
				})

				.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> XML Java Object...")
				.bean(EDIXMLDesadvBean.class, EDIXMLDesadvBean.METHOD_createXMLEDIData)
				.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to XML EDI Format...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(desadvFilenamePattern)

				.log(LoggingLevel.INFO, "EDI: Sending the XML EDI file to the FILE component...")
				.to(XMLDesadvRoute.EP_EDI_FILE_DESADV_XML)

				.log(LoggingLevel.INFO, "EDI: Creating ADempiere feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIDesadvFeedbackType.class, XMLDesadvRoute.EDIDesadvFeedback_QNAME, XMLDesadvRoute.METHOD_setEDIDesadvID))

				.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

				.log(LoggingLevel.INFO, "EDI: Sending success response to ADempiere...")
				.to(Constants.EP_AMQP_TO_AD);
	}
}
