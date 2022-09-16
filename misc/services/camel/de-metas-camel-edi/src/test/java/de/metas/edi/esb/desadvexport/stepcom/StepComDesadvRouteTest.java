/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.edi.esb.desadvexport.stepcom;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Properties;

import static de.metas.edi.esb.desadvexport.stepcom.StepComXMLDesadvRoute.ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

class StepComDesadvRouteTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new StepComXMLDesadvRoute();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(StepComDesadvRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			properties.load(StepComDesadvRouteTest.class.getClassLoader().getResourceAsStream("desadv-customer.properties"));

			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void desadv_as_ordered() throws Exception
	{
		testAndValidateResult(
				"/de/metas/edi/esb/desadvexport/stepcom/DESADV_as_ordered.xml",
				"./src/test/resources/de/metas/edi/esb/desadvexport/stepcom/DESADV_as_ordered_expected_output.xml");
	}

	@Test
	void desadv_no_packs() throws Exception
	{
		testAndValidateResult(
				"/de/metas/edi/esb/desadvexport/stepcom/DESADV_no_packs.xml",
				"./src/test/resources/de/metas/edi/esb/desadvexport/stepcom/DESADV_no_packs_expected_output.xml");
	}

	@Test
	void desadv_with_and_without_packs() throws Exception
	{
		testAndValidateResult(
				"/de/metas/edi/esb/desadvexport/stepcom/DESADV_with_and_without_packs.xml",
				"./src/test/resources/de/metas/edi/esb/desadvexport/stepcom/DESADV_with_and_without_packs_expected_output.xml");
	}

	private void testAndValidateResult(
			@NonNull final String inputStrPath,
			@NonNull final String expectedOutputPath) throws Exception
	{
		// given
		SystemTime.setTimeSource(() -> Instant.parse("2021-02-07T20:35:30.00Z").toEpochMilli());

		final var inputStr = StepComDesadvRouteTest.class.getResourceAsStream(inputStrPath);
		assertThat(inputStr).isNotNull();

		final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final JAXBElement<EDIExpDesadvType> inputXml = (JAXBElement)unmarshaller.unmarshal(inputStr);

		final Exchange exchange = new DefaultExchange(template.getCamelContext());

		final var numberFormat = NumberFormat.getInstance();
		numberFormat.setGroupingUsed(false);

		exchange.setProperty(Constants.DECIMAL_FORMAT, numberFormat);
		exchange.getIn().setBody(inputXml.getValue());
		exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, inputXml.getValue());

		final MockDocument mockDocument = new MockDocument();
		final MockAmqpToMF mockAmqpToMF = new MockAmqpToMF();

		prepareRouteForTesting(mockDocument, mockAmqpToMF);

		context.start();

		// when
		template.send(
				StepComXMLDesadvRoute.EP_EDI_STEPCOM_XML_DESADV_CONSUMER /*endpoint-URI*/,
				exchange);

		// then
		assertThat(mockDocument.called).isEqualTo(1);
		assertThat(mockAmqpToMF.called).isEqualTo(1);
		assertThat(mockDocument.result).isEqualTo(contentOf(new File(expectedOutputPath))
														  .replaceAll("\r", ""));
	}

	private void prepareRouteForTesting(
			@NonNull final MockDocument mockDocument,
			@NonNull final MockAmqpToMF mockAmqpToMF) throws Exception
	{
		AdviceWith.adviceWith(context, ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint(StepComXMLDesadvRoute.OUTPUT_DESADV_LOCAL)
										  .skipSendToOriginalEndpoint()
										  .process(mockDocument);

								  advice.interceptSendToEndpoint("{{" + Constants.EP_AMQP_TO_MF + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockAmqpToMF);
							  });
	}

	private static class MockDocument implements Processor
	{
		private int called = 0;
		private String result;

		@Override
		public void process(final Exchange exchange)
		{
			called++;

			result = exchange.getIn().getBody(String.class);
		}
	}

	private static class MockAmqpToMF implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}