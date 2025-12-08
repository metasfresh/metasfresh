/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.esb.desadvexport.metasfreshinhousev1;

import com.google.common.annotations.VisibleForTesting;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.commons.route.exports.ReaderTypeConverter;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvPackItemType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvPackType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvType;
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
public class MetasfreshInHouseV1DesadvRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Desadv-In-House-V1";

	public static final String EP_EDI_METASFRESH_XML_DESADV_CONSUMER = "direct:edi.desadv.inhouse.v1.consumer";

	private final static QName EDIDesadvFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIDesadvFeedback(null).getName();

	private static final String METHOD_setEDIDesadvID = "setEDIDesadvID";

	@VisibleForTesting
	static final String OUTPUT_DESADV_LOCAL = "edi.file.desadv.inhouse.v1";

	private static final String OUTPUT_DESADV_REMOTE = "edi.file.desadv.inhouse.v1.remote";

	@Override
	public void configureEDIRoute(@NonNull final DataFormat jaxb, @NonNull final DecimalFormat decimalFormat)
	{
		final JaxbDataFormat dataFormat = new JaxbDataFormat(de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvType.class.getPackage().getName());

		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(StandardCharsets.UTF_8.name());

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), MetasfreshInHouseV1DesadvRoute.EDI_ORDER_EDIMessageDatePattern);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_MF_DURABLE_ROUTING_KEY);

		final String remoteEndpoint = Util.resolveProperty(getContext(), OUTPUT_DESADV_REMOTE, "");

		final String[] endPointURIs;
		if (Util.isEmpty(remoteEndpoint)) // if we send everything to the remote-EP, then log what we send to file only on "TRACE" log level
		{
			endPointURIs = new String[] { "{{" + MetasfreshInHouseV1DesadvRoute.OUTPUT_DESADV_LOCAL + "}}" };
		}
		else
		{
			endPointURIs = new String[] { "{{" + MetasfreshInHouseV1DesadvRoute.OUTPUT_DESADV_LOCAL + "}}", remoteEndpoint };
		}

		from(MetasfreshInHouseV1DesadvRoute.EP_EDI_METASFRESH_XML_DESADV_CONSUMER)
				.routeId(ROUTE_ID)
				.streamCaching()

				.log(LoggingLevel.INFO, "Setting defaults as exchange properties...")
				.setProperty(MetasfreshInHouseV1DesadvRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)

				.process(exchange -> {
					final EDIExpDesadvType xmlDesadv = exchange.getIn().getBody(EDIExpDesadvType.class); // throw exceptions if mandatory fields are missing

					sortPacksAndItems(xmlDesadv);

					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlDesadv.getADClientValueAttr());
					exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlDesadv.getEDIDesadvID().longValue());

					final String fileName = "DESADV_" + xmlDesadv.getDocumentNo() + "_" + SystemTime.millis() + ".xml";
					exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
				})

				.log(LoggingLevel.INFO, "Converting metasfresh-XML Java Object -> MetasfreshInHouseV1-XML Java Object...")
				.bean(MetasfreshInHouseV1XMLDesadvBean.class, MetasfreshInHouseV1XMLDesadvBean.METHOD_createXMLEDIData)

				.log(LoggingLevel.INFO, "MetasfreshInHouseV1-XML Java Object -> XML...")
				.marshal(dataFormat)

				.log(LoggingLevel.INFO, "Output file's name=${in.headers." + Exchange.FILE_NAME + "}")

				.multicast()
				.stopOnException().parallelProcessing(false).to(endPointURIs)
				.end()

				.log(LoggingLevel.INFO, "Creating MetasfreshInHouseV1 success feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIDesadvFeedbackType.class, MetasfreshInHouseV1DesadvRoute.EDIDesadvFeedback_QNAME, MetasfreshInHouseV1DesadvRoute.METHOD_setEDIDesadvID))
				.log(LoggingLevel.INFO, "Marshalling MetasfreshInHouseV1 feedback XML Java Object -> XML...")
				.marshal(jaxb)
				.log(LoggingLevel.INFO, "Sending success response to MetasfreshInHouseV1...")
				.setHeader(RabbitMQConstants.ROUTING_KEY).simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
				.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
	}

	private void sortPacksAndItems(@NonNull final EDIExpDesadvType xmlDesadv)
	{
		xmlDesadv.getEDIExpDesadvPack()
				.sort(Comparator.comparing(EDIExpDesadvPackType::getSeqNo,
						Comparator.nullsLast(Comparator.naturalOrder())));

		for (final EDIExpDesadvPackType pack : xmlDesadv.getEDIExpDesadvPack())
		{
			pack.getEDIExpDesadvPackItem()
					.sort(Comparator.comparing(EDIExpDesadvPackItemType::getLine,
							Comparator.nullsLast(Comparator.naturalOrder())));
		}
	}
}
