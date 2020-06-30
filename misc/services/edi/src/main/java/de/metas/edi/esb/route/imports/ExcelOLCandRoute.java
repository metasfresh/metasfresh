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

package de.metas.edi.esb.route.imports;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import de.metas.edi.esb.commons.Util;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.bean.imports.excel.ExcelConfigurationContext;
import de.metas.edi.esb.bean.imports.excel.Excel_OLCand_Row;
import de.metas.edi.esb.bean.imports.excel.Excel_OLCand_Row_Builder;
import de.metas.edi.esb.bean.imports.excel.ExcelToMapListConverter;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.bean.imports.excel.ExcelImpCOLCandTypeBuilder;
import de.metas.edi.esb.jaxb.metasfresh.XLSImpCOLCandType;
import de.metas.edi.esb.route.AbstractEDIRoute;

/**
 * Camel Route for importing customer's Excel files to order candidates.
 * It reads excel files from a configured folder and sends them to metasfresh amqp endpoint as {@link XLSImpCOLCandType} messages.
 * <p>
 * The data flow is:
 * <ul>
 *     <li>Optionally from: remote excel file to local folder </li>
 *     <li>from: Excel file (from {@link #INPUT_EXCEL_LOCAL})</li>
 *     <li>{@link Excel_OLCand_Row} (internal representation of an customer's excel row)</li>
 *     <li> {@link XLSImpCOLCandType} (XML representation of an excel row/C_OLCand that will be sent to metasfresh)</li>
 *     <li>to: {@link Constants#EP_AMQP_TO_MF}</li>
 * </ul>
 *
 * @author tsa
 * @task 08839
 */
@Component
public class ExcelOLCandRoute extends AbstractEDIRoute
{
	public static final String LOCAL_ROUTE_ID = "Excel-Orders-To-MF-OLCand";
	private static final transient Logger logger = LoggerFactory.getLogger(LOCAL_ROUTE_ID);

	/** This place holder is evaluated via {@link Util#resolveProperty(CamelContext, String)}, that's why we don't put it in {@code {{...}}} */
	public static final String INPUT_EXCEL_REMOTE = "excel.file.orders.remote";

	public static final String INPUT_EXCEL_LOCAL = "{{excel.file.orders}}";

	@Override
	protected void configureEDIRoute(DataFormat jaxb, DecimalFormat decimalFormat)
	{
		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_EXCEL_REMOTE, "");
		if (!Util.isEmpty(remoteEndpoint))
		{
			from(remoteEndpoint)
					.routeId("Excel-Remote-Orders-To-Local")
					.log(LoggingLevel.TRACE, "Excel: Getting remote file")
					.to(INPUT_EXCEL_LOCAL);
		}

		final ExcelConfigurationContext xlsConfigContext = ExcelConfigurationContext.createFromCamelContext(getContext());

		final ExcelToMapListConverter xls2mapListConverter = ExcelToMapListConverter.builder()
				.setDiscardNoNameHeaders()
				.setConsiderEmptyStringAsNull(true)
				.setConsiderNullStringAsNull(true)
				.setDiscardRepeatingHeaders(true)
				.setRowNumberMapKey(Excel_OLCand_Row_Builder.MAPKEY_LineNo)
				.setUseRowTypeColumn(true)
				.build();

		from(INPUT_EXCEL_LOCAL)
				.routeId(LOCAL_ROUTE_ID) // NOTE: the route ID will be used also for logger name
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
				.log(LoggingLevel.INFO, "Excel: Importing $simple{in.header.CamelFilePath}")
				.process(new Processor()
				{

					@Override
					public void process(final Exchange exchange) throws Exception
					{
						final ExcelConfigurationContext ctx = ExcelConfigurationContext.createFromExchange(exchange);
						logger.info("Excel: Configuration: {}", ctx);

						final InputStream xlsInputStream = exchange.getIn().getBody(InputStream.class);
						final List<Map<String, Object>> rows = xls2mapListConverter.convert(xlsInputStream);
						logger.info("Excel: Parsed {} rows", rows.size());

						final List<Object> output = new ArrayList<Object>(rows.size());
						for (final Map<String, Object> row : rows)
						{
							final Excel_OLCand_Row xlsRow = Excel_OLCand_Row.ofMap(row);

							// Discard rows on which user has ordered nothing.
							if (xlsRow.getQtyUOM() == null || xlsRow.getQtyUOM().signum() <= 0)
							{
								logger.debug("Excel: Skip row because has no Qty to order: {}", xlsRow);
								continue;
							}

							final XLSImpCOLCandType xmlOLCand = ExcelImpCOLCandTypeBuilder.newBuilder()
									.setFromContext(ctx)
									.setFromRow(xlsRow)
									.build();

							// NOTE: because our XML object is not annotated with @XmlRootElement the JAXB marshaler,
							// we need to convert it to JAXBElement explicitelly,
							// else, the marshaler will fail.
							final JAXBElement<XLSImpCOLCandType> jaxbOLCand = Constants.JAXB_ObjectFactory.createXLSImpCOLCand(xmlOLCand);
							output.add(jaxbOLCand);
						}

						logger.info("Excel: We have {} of {} valid rows to be sent forward", output.size(), rows.size());

						exchange.getOut().setBody(output);
					}
				})
				.split(body())
				.marshal(jaxb)
				//
				.log(LoggingLevel.INFO, "Excel: Sending XML Order document to metasfresh...")
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
				.to(Constants.EP_AMQP_TO_MF);
	}
}
