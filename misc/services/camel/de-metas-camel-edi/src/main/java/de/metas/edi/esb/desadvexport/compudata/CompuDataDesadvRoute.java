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

package de.metas.edi.esb.desadvexport.compudata;

import java.text.DecimalFormat;

import javax.xml.namespace.QName;

import de.metas.edi.esb.commons.route.exports.ReaderTypeConverter;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.spi.DataFormat;
import org.milyn.smooks.camel.dataformat.SmooksDataFormat;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.desadvexport.AbstractEDIDesadvCommonBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;

@Component
public class CompuDataDesadvRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Desadv-To-COMPUDATA-Desadv";

	private static final String EDI_DESADV_FILENAME_PATTERN = "edi.file.desadv.compudata.filename";

	public static final String EP_EDI_DESADV_SINGLE_CONSUMER = "direct:edi.desadv.consumer.single";
	public static final String EP_EDI_COMPUDATA_DESADV_CONSUMER = "direct:edi.desadv.consumer.aggregate";

	public static final String EDI_DESADV_IS_TEST = "edi.props.desadv.isTest";
	public static final String EDI_DESADV_IS_AGGREGATE = "edi.props.desadv.isAggregate";

	public final static QName EDIDesadvFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIDesadvFeedback(null).getName();
	public static final String METHOD_setEDIDesadvID = "setEDIDesadvID";

	/**
	 * The FILE folder where the EDI file will be stored
	 */
	public static final String EP_EDI_FILE_DESADV = "{{edi.file.desadv.compudata}}";

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final SmooksDataFormat sdf = getSDFForConfiguration("edi.smooks.config.xml.desadv");

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String desadvFilenamePattern = Util.resolveProperty(getContext(), CompuDataDesadvRoute.EDI_DESADV_FILENAME_PATTERN);

		final String isTest = Util.resolveProperty(getContext(), CompuDataDesadvRoute.EDI_DESADV_IS_TEST);

		from(CompuDataDesadvRoute.EP_EDI_COMPUDATA_DESADV_CONSUMER)
				.routeId(ROUTE_ID)

				.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(CompuDataDesadvRoute.EDI_DESADV_IS_TEST).constant(isTest)

				//
				// Aggregate route (used for pivoting feedback)
				.setProperty(CompuDataDesadvRoute.EDI_DESADV_IS_AGGREGATE).constant(true)

				.log(LoggingLevel.INFO, "EDI: Setting EDI feedback headers...")
				.process(new Processor()
				{
					@Override
					public void process(final Exchange exchange)
					{
						// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);

						final EDIExpDesadvType xmlDesadv = exchange.getIn().getBody(EDIExpDesadvType.class); // throw exceptions if mandatory fields are missing

						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlDesadv.getADClientValueAttr());
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlDesadv.getEDIDesadvID().longValue());
					}
				})

				.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI Java Object...")
				.bean(CompuDataDesadvBean.class, AbstractEDIDesadvCommonBean.METHOD_createEDIData)

				.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to EDI Format using SDF...")
				.marshal(sdf)

				.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(desadvFilenamePattern)

				.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to(CompuDataDesadvRoute.EP_EDI_FILE_DESADV)

				.log(LoggingLevel.INFO, "EDI: Creating metasfresh feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<EDIDesadvFeedbackType>(EDIDesadvFeedbackType.class, CompuDataDesadvRoute.EDIDesadvFeedback_QNAME, CompuDataDesadvRoute.METHOD_setEDIDesadvID))

				.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

				.log(LoggingLevel.INFO, "EDI: Sending success response to metasfresh...")
				.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
	}
}
