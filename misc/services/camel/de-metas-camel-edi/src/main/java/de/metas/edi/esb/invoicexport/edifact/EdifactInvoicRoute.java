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

package de.metas.edi.esb.invoicexport.edifact;

import com.google.common.annotations.VisibleForTesting;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.processor.feedback.EDIXmlSuccessFeedbackProcessor;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.jaxb.metasfresh.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfresh.EDIInvoiceFeedbackType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.smooks.edifact.binding.d03b.BGMBeginningOfMessage;
import org.smooks.edifact.binding.d03b.C002DocumentMessageName;
import org.smooks.edifact.binding.d03b.C106DocumentMessageIdentification;
import org.smooks.edifact.binding.d03b.C507DateTimePeriod;
import org.smooks.edifact.binding.d03b.C516MonetaryAmount;
import org.smooks.edifact.binding.d03b.DTMDateTimePeriod;
import org.smooks.edifact.binding.d03b.INVOIC;
import org.smooks.edifact.binding.d03b.Interchange;
import org.smooks.edifact.binding.d03b.MOAMonetaryAmount;
import org.smooks.edifact.binding.d03b.Message;
import org.smooks.edifact.binding.service.E0001SyntaxIdentifier;
import org.smooks.edifact.binding.service.E0051ControllingAgencyCoded;
import org.smooks.edifact.binding.service.E0065MessageType;
import org.smooks.edifact.binding.service.E0081SectionIdentification;
import org.smooks.edifact.binding.service.S001SyntaxIdentifier;
import org.smooks.edifact.binding.service.S002InterchangeSender;
import org.smooks.edifact.binding.service.S003InterchangeRecipient;
import org.smooks.edifact.binding.service.S004DateAndTimeOfPreparation;
import org.smooks.edifact.binding.service.S005RecipientReferencePasswordDetails;
import org.smooks.edifact.binding.service.S009MessageIdentifier;
import org.smooks.edifact.binding.service.UNA;
import org.smooks.edifact.binding.service.UNBInterchangeHeader;
import org.smooks.edifact.binding.service.UNHMessageHeader;
import org.smooks.edifact.binding.service.UNSSectionControl;
import org.smooks.edifact.binding.service.UNTMessageTrailer;
import org.smooks.edifact.binding.service.UNZInterchangeTrailer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

/**
 * This route is getting its stuff from {@link de.metas.edi.esb.commons.route.exports.EDIExportCommonRoute}
 */
@Component
@PropertySources(value = {
		@PropertySource(value = "classpath:/invoic-customer.properties"),
		@PropertySource(value = "file:./invoic-customer.properties", ignoreResourceNotFound = true)
})
public class EdifactInvoicRoute extends AbstractEDIRoute
{
	public static final String ROUTE_ID = "MF-Invoic-To-EDIFACT-Invoic";

	@VisibleForTesting
	static final String EDI_INVOICE_FILENAME_PATTERN = "edi.file.invoic.edifact.filename";

	public static final String EP_EDI_METASFRESH_XML_INVOIC_CONSUMER = "direct:edi.invoic.edifact.consumer";

	public static final String EDI_INVOIC_SENDER_GLN = "edi.props.000.sender.gln";

	public final static QName EDIInvoiceFeedback_QNAME = Constants.JAXB_ObjectFactory.createEDIInvoiceFeedback(null).getName();
	public static final String METHOD_setCInvoiceID = "setCInvoiceID";

	/**
	 * The FILE folder where the EDI file will be stored
	 */
	public static final String EP_EDI_FILE_INVOICE = "edi.file.invoic.edifact";

	@Override
	public void configureEDIRoute(
			@NonNull final DataFormat metasfreshInhauseJaxb, 
			@NonNull final DecimalFormat decimalFormat)
	{
		// set up the format to convert edifact-java => edifact-XML
		final JAXBContext smooksEdifactJaxbContext;
		try
		{
			smooksEdifactJaxbContext = JAXBContext.newInstance(
					Interchange.class, 
					org.smooks.edifact.binding.service.ObjectFactory.class, 
					org.smooks.edifact.binding.d03b.ObjectFactory.class);
		}
		catch (final JAXBException e)
		{
			throw new RuntimeException(e);
		}
		final JaxbDataFormat smooksEdifactJaxbDataFormat = new JaxbDataFormat(smooksEdifactJaxbContext);
		smooksEdifactJaxbDataFormat.setCamelContext(getContext());
		smooksEdifactJaxbDataFormat.setEncoding(StandardCharsets.UTF_8.name());

		// FRESH-360: provide our own converter, so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
		//final ReaderTypeConverter readerTypeConverter = new ReaderTypeConverter();
		//getContext().getTypeConverterRegistry().addTypeConverters(readerTypeConverter);

		final String invoiceFilenamePattern = Util.resolveProperty(getContext(), EdifactInvoicRoute.EDI_INVOICE_FILENAME_PATTERN);
		final String feedbackMessageRoutingKey = Util.resolveProperty(getContext(), Constants.EP_AMQP_TO_MF_DURABLE_ROUTING_KEY);
		final String senderGln = Util.resolveProperty(getContext(), EdifactInvoicRoute.EDI_INVOIC_SENDER_GLN);

		from(EdifactInvoicRoute.EP_EDI_METASFRESH_XML_INVOIC_CONSUMER)
				.routeId(ROUTE_ID)

		.log(LoggingLevel.INFO, "EDI: Setting defaults as exchange properties...")
				.setProperty(EdifactInvoicRoute.EDI_INVOIC_SENDER_GLN).constant(senderGln)

		.log(LoggingLevel.INFO, "EDI: Setting EDI feedback headers...")
		.process(exchange -> {
			// i'm sure that there are better ways, but we want the EDIFeedbackRoute to identify that the error is coming from *this* route.
			exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ROUTE_ID, ROUTE_ID);

			final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

			exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_ADClientValueAttr, xmlCctopInvoice.getADClientValueAttr());
			exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_RecordID, xmlCctopInvoice.getCInvoiceID().longValue());
		})

		.log(LoggingLevel.INFO, "EDI: Converting XML Java Object -> EDI Java Object...")
				.process(exchange -> {
					// convert the EDICctopInvoicVType into an edifact interchange

					final EDICctopInvoicVType xmlCctopInvoice = exchange.getIn().getBody(EDICctopInvoicVType.class);

					// Build Java model        
					final Interchange interchange = new Interchange().
							withUNA(new UNA().
									withCompositeSeparator(":").
									withFieldSeparator("+").
									withDecimalSeparator(".").
									withEscapeCharacter("?").
									withRepeatSeparator("*").
									withSegmentTerminator("'")).
							withUNB(new UNBInterchangeHeader().
									withS001(new S001SyntaxIdentifier().
											withE0001(E0001SyntaxIdentifier.UNOC).withE0002("4")).
									withS002(new S002InterchangeSender().
											withE0004("5790000274017").
											withE0007("14")).
									withS003(new S003InterchangeRecipient().
											withE0010("5708601000836").
											withE0007("14")).
									withS004(new S004DateAndTimeOfPreparation().
											withE0017(new BigDecimal(990420)).
											withE0019(new BigDecimal(1137))).
									withE0020("17").
									withS005(new S005RecipientReferencePasswordDetails().withE0022("")).
									withE0026("INVOIC").
									withE0035(new BigDecimal(1))).
							withMessage(new Message().
									withContent(new JAXBElement<>(new QName("UNH"), UNHMessageHeader.class, new UNHMessageHeader().
											withE0062("30").
											withS009(new S009MessageIdentifier().
													withE0065(E0065MessageType.INVOIC).
													withE0052("D").
													withE0054("03B").
													withE0051(E0051ControllingAgencyCoded.UN)))).
									withContent(new JAXBElement<>(new QName("http://www.ibm.com/dfdl/edi/un/edifact/D03B", "INVOIC", "D03B"), INVOIC.class, new INVOIC().
											withBGM(new BGMBeginningOfMessage().
													withC002(new C002DocumentMessageName().withE1001("380")).
													withC106(new C106DocumentMessageIdentification().withE1004("539602"))).
											withDTM(new DTMDateTimePeriod().
													withC507(new C507DateTimePeriod().
															withE2005("137").
															withE2380("19990420").
															withE2379("102"))).
											withUNS(new UNSSectionControl().
													withE0081(E0081SectionIdentification.S)).
											withSegGrp50(new INVOIC.SegGrp50().
													withMOA(new MOAMonetaryAmount().
															withC516(new C516MonetaryAmount().
																	withE5025("64").
																	withE5004(new BigDecimal("100.95")).
																	withE6345("GBP")))))).
									withContent(new JAXBElement<>(new QName("UNT"), UNTMessageTrailer.class, new UNTMessageTrailer().
											withE0074(new BigDecimal(36)).
											withE0062("30")))).
							withUNZ(new UNZInterchangeTrailer().
									withE0036(new BigDecimal(1)).
									withE0020("17"));
					
					exchange.getIn().setBody(interchange);
				})
				
		.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to XML using JAXB...")		
				.marshal(smooksEdifactJaxbDataFormat)
				.log(LoggingLevel.INFO, "This is what we send to smooks:\n${body}")
				
		.log(LoggingLevel.INFO, "EDI: Marshalling EDI Java Object to EDI Format using Smooks...")
				.to("smooks:"+getSmooksConfigurationPath("edi.smooks.config.xml.invoic.edifact"))
				.log(LoggingLevel.INFO, "This is what we got back from smooks:${body}")
				
		.log(LoggingLevel.INFO, "EDI: Setting output filename pattern from properties...")
				.setHeader(Exchange.FILE_NAME).simple(invoiceFilenamePattern)

		.log(LoggingLevel.INFO, "EDI: Sending the EDI file to the FILE component...")
				.to("{{" + EdifactInvoicRoute.EP_EDI_FILE_INVOICE + "}}")

		.log(LoggingLevel.INFO, "EDI: Creating metasfresh feedback XML Java Object...")
				.process(new EDIXmlSuccessFeedbackProcessor<>(EDIInvoiceFeedbackType.class, EdifactInvoicRoute.EDIInvoiceFeedback_QNAME, EdifactInvoicRoute.METHOD_setCInvoiceID))

		.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object feedback -> XML document...")
				.marshal(metasfreshInhauseJaxb)

		.log(LoggingLevel.INFO, "EDI: Sending success response to metasfresh...")
		
		.setHeader(RabbitMQConstants.ROUTING_KEY).simple(feedbackMessageRoutingKey) // https://github.com/apache/camel/blob/master/components/camel-rabbitmq/src/main/docs/rabbitmq-component.adoc
 		.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
		.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
	}
}
