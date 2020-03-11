package de.metas.edi.esb.route.imports;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.jaxb.XLSImpCOLCandTypeBuilder;
import de.metas.edi.esb.jaxb.metasfresh.XLSImpCOLCandType;
import de.metas.edi.esb.route.AbstractEDIRoute;
import de.metas.edi.esb.xls.XLSConfigurationContext;
import de.metas.edi.esb.xls.XLS_OLCand_Row;
import de.metas.edi.esb.xls.XLS_OLCand_Row_Builder;
import de.metas.edi.esb.xls.XlsToMapListConverter;

/**
 * Camel Route for importing customer's Excel files to order candidates.
 * It reads excel files from a configured folder and sends them to metasfresh amqp endpoint as {@link XLSImpCOLCandType} messages.
 *
 * The data flow is:
 * <ul>
 * <li>from: Excel file (from {@link #EP_OLCAND_INPUT_ORDERS})
 * <li>{@link XLS_OLCand_Row} (internal representation of an customer's excel row)
 * <li> {@link XLSImpCOLCandType} (XML representation of an excel row/C_OLCand that will be sent to metasfresh)
 * <li>to: {@link Constants#EP_AMQP_TO_MF}
 * </ul>
 *
 * @author tsa
 * @task 08839
 */
@Component
public class XLSOLCandRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "Excel-Orders-To-MF-OLCand";
	private static final transient Logger logger = LoggerFactory.getLogger(ROUTE_ID);

	public static final String EP_OLCAND_INPUT_ORDERS = "{{xls.order.input}}";

	@Override
	protected void configureEDIRoute(DataFormat jaxb, DecimalFormat decimalFormat)
	{
		final XLSConfigurationContext xlsConfigContext = XLSConfigurationContext.createFromCamelContext(getContext());

		final XlsToMapListConverter xls2mapListConverter = XlsToMapListConverter.builder()
				.setDiscardNoNameHeaders()
				.setConsiderEmptyStringAsNull(true)
				.setConsiderNullStringAsNull(true)
				.setDiscardRepeatingHeaders(true)
				.setRowNumberMapKey(XLS_OLCand_Row_Builder.MAPKEY_LineNo)
				.setUseRowTypeColumn(true)
				.build();

		from(EP_OLCAND_INPUT_ORDERS)
				.routeId(ROUTE_ID) // NOTE: the route ID will be used also for logger name
				//
				// Set XLS configuration properties to our exchange
				// .setProperty(Exchange.FILE_NAME, header(Exchange.FILE_NAME))
				.process(xlsConfigContext.asSetPropertiesToExchangeProcessor())
				//
				// Convert input XLS filename to a list of EDIImpCOLCandType
				// Input: Filename
				// Output: List of JAXBElement<XLSImpCOLCandType>
				//
				// NOTE: we process the whole excel file in one operation so everything is fine or everything will fail.
				.log(LoggingLevel.INFO, "Importing $simple{in.header.CamelFilePath}")
				.process(new Processor()
				{

					@Override
					public void process(final Exchange exchange) throws Exception
					{
						final XLSConfigurationContext ctx = XLSConfigurationContext.createFromExchange(exchange);
						logger.info("Configuration: {}", ctx);

						final InputStream xlsInputStream = exchange.getIn().getBody(InputStream.class);
						final List<Map<String, Object>> rows = xls2mapListConverter.convert(xlsInputStream);
						logger.info("Parsed {} rows", rows.size());

						final List<Object> output = new ArrayList<Object>(rows.size());
						for (final Map<String, Object> row : rows)
						{
							final XLS_OLCand_Row xlsRow = XLS_OLCand_Row.ofMap(row);

							//
							// Discard rows on which user has ordered nothing.
							if (xlsRow.getQtyCUs().signum() <= 0)
							{
								logger.debug("Skip row because has no Qty to order: {}", xlsRow);
								continue;
							}

							final XLSImpCOLCandType xmlOLCand = XLSImpCOLCandTypeBuilder.newBuilder()
									.setFromContext(ctx)
									.setFromRow(xlsRow)
									.build();

							// NOTE: because our XML object is not annotated with @XmlRootElement the JAXB marshaler,
							// we need to convert it to JAXBElement explicitelly,
							// else, the marshaler will fail.
							final JAXBElement<XLSImpCOLCandType> jaxbOLCand = Constants.JAXB_ObjectFactory.createXLSImpCOLCand(xmlOLCand);
							output.add(jaxbOLCand);
						}

						logger.info("We have {} of {} valid rows to be sent forward", output.size(), rows.size());

						exchange.getOut().setBody(output);
					}
				})
				.split(body())
				.marshal(jaxb)
				//
				.log(LoggingLevel.INFO, "XLS_OLCand: Sending XML Order document to metasfresh...")
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.ISO_8859_1.name())
				.to(Constants.EP_AMQP_TO_MF);
	}
}
