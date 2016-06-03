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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

import de.metas.edi.esb.bean.desadv.AbstractEDIDesadvCommonBean;
import de.metas.edi.esb.bean.desadv.EDIDesadvAggregateBean;
import de.metas.edi.esb.bean.desadv.EDIDesadvSingleBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.EDIDesadvFeedbackType;
import de.metas.edi.esb.jaxb.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.EDIExpMInOutType;
import de.metas.edi.esb.jaxb.EDIInOutFeedbackType;
import de.metas.edi.esb.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.route.AbstractEDIRoute;

public class EDIDesadvRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID_SINGLE = "XML-InOut-To-EDI-DESADV-Single";
	public static final String ROUTE_ID_AGGREGATE = "XML-InOut-To-EDI-DESADV-Aggregate";

	private static final String EDI_DESADV_FILENAME_PATTERN = "edi.file.desadv.filename";

	public static final String EP_EDI_DESADV_SINGLE_CONSUMER = "direct:edi.desadv.consumer.single";
	public static final String EP_EDI_DESADV_AGGREGATE_CONSUMER = "direct:edi.desadv.consumer.aggregate";

	/**
	 * Common file routing for both single and aggregated DESADV documents
	 */
	private static final String EP_EDI_DESADV_COMMON_CONSUMER = "direct:edi.desadv.consumer.common";

	public static final String EDI_DESADV_IS_TEST = "edi.props.desadv.isTest";
	public static final String EDI_DESADV_IS_AGGREGATE = "edi.props.desadv.isAggregate";

	public final static QName EDIInOutFeedback_QNAME = new QName("", "EDI_InOut_Feedback"); // FIXME see how to take it from object factory!
	public static final String METHOD_setMInOutID = "setMInOutID";

	public final static QName EDIDesadvFeedback_QNAME = new QName("", "EDI_Desadv_Feedback"); // FIXME see how to take it from object factory!
	public static final String METHOD_setEDIDesadvID = "setEDIDesadvID";

	/**
	 * The FILE folder where the EDI file will be stored
	 */
	public static final String EP_EDI_FILE_DESADV = "{{edi.file.desadv}}";

	@Override
	public void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		final SmooksDataFormat sdf = getSDFForConfiguration("edi.smooks.config.xml.desadv");

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String desadvFilenamePattern = Util.resolvePropertyPlaceholders(getContext(), EDIDesadvRoute.EDI_DESADV_FILENAME_PATTERN);

		final String isTest = Util.resolvePropertyPlaceholders(getContext(), EDIDesadvRoute.EDI_DESADV_IS_TEST);

		from(EDIDesadvRoute.EP_EDI_DESADV_SINGLE_CONSUMER)
				.routeId(ROUTE_ID_SINGLE)

				.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST).constant(isTest)

				//
				// Single route (used for pivoting feedback)
				.setProperty(EDIDesadvRoute.EDI_DESADV_IS_AGGREGATE).constant(false)

				.log(LoggingLevel.INFO, "EDI: Setting EDI feedback headers...")
				.process(new Processor()
				{
					@Override
					public void process(final Exchange exchange)
					{
						// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID_SINGLE);

						final EDIExpMInOutType xmlInOut = exchange.getIn().getBody(EDIExpMInOutType.class); // throw exceptions if mandatory fields are missing

						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlInOut.getADClientValueAttr());
						exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlInOut.getMInOutID());
					}
				})

				.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI Java Object...")
				.bean(EDIDesadvSingleBean.class, AbstractEDIDesadvCommonBean.METHOD_createEDIData)
				//
				// Send to common route
				.to(EDIDesadvRoute.EP_EDI_DESADV_COMMON_CONSUMER);

		from(EDIDesadvRoute.EP_EDI_DESADV_AGGREGATE_CONSUMER)
				.routeId(ROUTE_ID_AGGREGATE)

				.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(EDIDesadvRoute.EDI_DESADV_IS_TEST).constant(isTest)

				//
				// Aggregate route (used for pivoting feedback)
				.setProperty(EDIDesadvRoute.EDI_DESADV_IS_AGGREGATE).constant(true)

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

				.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI Java Object...")
				.bean(EDIDesadvAggregateBean.class, AbstractEDIDesadvCommonBean.METHOD_createEDIData)
				//
				// Send to common route
				.to(EDIDesadvRoute.EP_EDI_DESADV_COMMON_CONSUMER);

		from(EDIDesadvRoute.EP_EDI_DESADV_COMMON_CONSUMER)
				.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to EDI Format using SDF...")
				.marshal(sdf)

				.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(desadvFilenamePattern)

				.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to(EDIDesadvRoute.EP_EDI_FILE_DESADV)

				.log(LoggingLevel.INFO, "EDI: Creating ADempiere feedback XML Java Object...")
				//
				// @formatter:off
				.choice()
					.when(exchangeProperty(EDI_DESADV_IS_AGGREGATE).isEqualTo(false))
						.process(new EDIXmlSuccessFeedbackProcessor<EDIInOutFeedbackType>(EDIInOutFeedbackType.class, EDIDesadvRoute.EDIInOutFeedback_QNAME, EDIDesadvRoute.METHOD_setMInOutID))
					.otherwise()
						.process(new EDIXmlSuccessFeedbackProcessor<EDIDesadvFeedbackType>(EDIDesadvFeedbackType.class, EDIDesadvRoute.EDIDesadvFeedback_QNAME, EDIDesadvRoute.METHOD_setEDIDesadvID))
				.end()
				// @formatter:on

				.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(jaxb)

				.log(LoggingLevel.INFO, "EDI: Sending success response to ADempiere...")
				.to(Constants.EP_JMS_TO_AD);
	}
}
