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

package de.metas.edi.esb.desadvexport.ecosio;

import com.google.common.annotations.VisibleForTesting;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.commons.route.exports.ReaderTypeConverter;
import de.metas.edi.esb.jaxb.metasfresh.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import lombok.NonNull;
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
import java.util.Comparator;

@Component
@PropertySources(value = {
		@PropertySource(value = "classpath:/desadv-customer.properties"),
		@PropertySource(value = "file:./desadv-customer.properties", ignoreResourceNotFound = true)
})
public class EcosioDesadvRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Desadv-To-ecosio";

	public static final String EP_EDI_METASFRESH_XML_DESADV_CONSUMER = "direct:edi.desadv.ecosio.consumer";

	private final static QName EDIDesadvFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIDesadvFeedback(null).getName();

	private static final String METHOD_setEDIDesadvID = "setEDIDesadvID";

	@VisibleForTesting
	static final String OUTPUT_DESADV_LOCAL = "edi.file.desadv.ecosio";

	private static final String OUTPUT_DESADV_REMOTE = "edi.file.desadv.ecosio.remote";

	@Override
	public void configureEDIRoute(@NonNull final DataFormat jaxb, @NonNull final DecimalFormat decimalFormat)
	{
		final JaxbDataFormat dataFormat = new JaxbDataFormat(EDIExpDesadvType.class.getPackage().getName());
		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(StandardCharsets.UTF_8.name());

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), EcosioDesadvRoute.EDI_ORDER_EDIMessageDatePattern);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_MF_DURABLE_ROUTING_KEY);

		final String remoteEndpoint = Util.resolveProperty(getContext(), OUTPUT_DESADV_REMOTE, "");

		final String[] endPointURIs;
		if (Util.isEmpty(remoteEndpoint)) // if we send everything to the remote-EP, then log what we send to file only on "TRACE" log level
		{
			endPointURIs = new String[] { "{{" + EcosioDesadvRoute.OUTPUT_DESADV_LOCAL + "}}" };
		}
		else
		{
			endPointURIs = new String[] { "{{" + EcosioDesadvRoute.OUTPUT_DESADV_LOCAL + "}}", remoteEndpoint };
		}

		from(EcosioDesadvRoute.EP_EDI_METASFRESH_XML_DESADV_CONSUMER)
				.routeId(ROUTE_ID)
				.streamCaching()

				.log(LoggingLevel.INFO, "Setting defaults as exchange properties...")
				.setProperty(EcosioDesadvRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.process(exchange -> {
					final EDIExpDesadvType xmlDesadv = exchange.getIn().getBody(EDIExpDesadvType.class); // throw exceptions if mandatory fields are missing
					// make sure that our lines are sorted by line number
					xmlDesadv.getEDIExpDesadvLine().sort(Comparator.comparing(EDIExpDesadvLineType::getLine));

					// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlDesadv.getADClientValueAttr());
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlDesadv.getEDIDesadvID().longValue());

					final String fileName = "DESADV_" + xmlDesadv.getDocumentNo() + "_" + SystemTime.millis() + ".xml";
					exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
				})

				.log(LoggingLevel.INFO, "Marshalling XML Java Object -> XML...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "Output file's name=${in.headers." + Exchange.FILE_NAME + "}")
				//.log(LoggingLevel.INFO, "Sending this XML to the " + endPointURIs.length + " endpoint(s):\r\n" + body())
				.multicast()
				.stopOnException().parallelProcessing(false).to(endPointURIs)
				.end()

				.log(LoggingLevel.INFO, "Creating metasfresh success feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIDesadvFeedbackType.class, EcosioDesadvRoute.EDIDesadvFeedback_QNAME, EcosioDesadvRoute.METHOD_setEDIDesadvID))
				.log(LoggingLevel.INFO, "Marshalling metasfresh feedback XML Java Object -> XML...")
				.marshal(jaxb)
				.log(LoggingLevel.INFO, "Sending success response to metasfresh...")
				.setHeader(RabbitMQConstants.ROUTING_KEY).simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
				.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
	}
}
