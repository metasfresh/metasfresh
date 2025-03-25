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

package de.metas.edi.esb.excelimport;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.COrderDeliveryRuleEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.COrderDeliveryViaRuleEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.ReplicationTypeEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.XLSImpCOLCandType;
import jakarta.xml.bind.JAXBElement;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.junit5.TestExecutionConfiguration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import static de.metas.edi.esb.excelimport.ExcelOLCandRoute.MAP_TO_METASFRESH_TYPE_PROCESSOR_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExcelOLCandRouteTest extends CamelTestSupport
{

	private final static String MOCK_FROM = "direct:mock-from";
	private final static String COLLECT_MAPPED_TO_OLCAND_METASFRESH_TYPE_EP = "mock:COLLECT_MAPPED_TO_OLCAND_METASFRESH_TYPE_EP";
	private final static String OL_CAND_ONE_ORDER_FILE = "TEST_OLCAND_IMPORT.xls";

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ExcelOLCandRoute();
	}

	@Override
	public void configureTest(@NonNull final TestExecutionConfiguration testExecutionConfiguration)
	{
		testExecutionConfiguration.withUseAdviceWith(true);

	}

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);
		final Properties properties = new Properties();
		try
		{
			properties.load(ExcelOLCandRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		camelContextConfiguration.withUseOverridePropertiesWithPropertiesComponent(properties);
	}

	private void prepareRouteForTesting(@NonNull final ExcelOLCandRouteTest.MockRabbitMQProcessor rabbitMQMockProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, ExcelOLCandRoute.LOCAL_ROUTE_ID,
				advice -> {
					advice.replaceFromWith(MOCK_FROM);

					advice.weaveById(MAP_TO_METASFRESH_TYPE_PROCESSOR_ID)
							.after()
							.to(COLLECT_MAPPED_TO_OLCAND_METASFRESH_TYPE_EP);

					advice.interceptSendToEndpoint("{{" + Constants.EP_AMQP_TO_MF + "}}")
							.skipSendToOriginalEndpoint()
							.process(rabbitMQMockProcessor);
				});
	}

	@Test
	public void testRoute() throws Exception
	{
		// given
		final MockRabbitMQProcessor mockRabbitMQProcessor = new MockRabbitMQProcessor();
		prepareRouteForTesting(mockRabbitMQProcessor);
		final InputStream olCandInputStream = this.getClass().getResourceAsStream(OL_CAND_ONE_ORDER_FILE);

		final MockEndpoint mappedToOLCand = getMockEndpoint(COLLECT_MAPPED_TO_OLCAND_METASFRESH_TYPE_EP);
		mappedToOLCand.expectedMessageCount(1);

		// when
		context.start();
		template.sendBody(MOCK_FROM, olCandInputStream);

		// then
		mappedToOLCand.assertIsSatisfied(1000);
		final List<JAXBElement<XLSImpCOLCandType>> result =
				(List<JAXBElement<XLSImpCOLCandType>>)mappedToOLCand.getExchanges().get(0).getIn().getBody(List.class);

		assertThat(result.size()).isEqualTo(1);

		final XLSImpCOLCandType oLCandType = result.get(0).getValue();
		assertThat(oLCandType).isNotNull();

		assertThat(oLCandType.getADInputDataSourceID()).isEqualTo(BigInteger.valueOf(160));
		assertThat(oLCandType.getADOrgID()).isEqualTo(BigInteger.valueOf(1000000));
		assertThat(oLCandType.getADUserEnteredByID()).isEqualTo(BigInteger.valueOf(160));

		assertThat(oLCandType.getCBPartnerID()).isEqualTo(BigInteger.valueOf(1000001));
		assertThat(oLCandType.getCBPartnerLocationID()).isEqualTo(BigInteger.valueOf(1000003));
		assertThat(oLCandType.getBillBPartnerID()).isEqualTo(BigInteger.valueOf(1000005));
		assertThat(oLCandType.getBillLocationID()).isEqualTo(BigInteger.valueOf(1000006));
		assertThat(oLCandType.getHandOverPartnerID()).isEqualTo(BigInteger.valueOf(1000007));
		assertThat(oLCandType.getHandOverLocationID()).isEqualTo(BigInteger.valueOf(1000008));
		assertThat(oLCandType.getDropShipBPartnerID()).isEqualTo(BigInteger.valueOf(1000009));
		assertThat(oLCandType.getDropShipLocationID()).isEqualTo(BigInteger.valueOf(1000010));

		assertThat(oLCandType.getCCurrencyID().getISOCode()).isEqualTo("CHF");
		assertThat(oLCandType.getCUOMID().getX12DE355()).isEqualTo("PCE");

		assertThat(oLCandType.getDeliveryRule()).isEqualTo(COrderDeliveryRuleEnum.Availability);
		assertThat(oLCandType.getDeliveryViaRule()).isEqualTo(COrderDeliveryViaRuleEnum.Pickup);
		assertThat(oLCandType.getDescription()).isNull();

		assertThat(oLCandType.getProductDescription()).isEqualTo("Test");
		assertThat(oLCandType.getMProductID()).isEqualTo(BigInteger.valueOf(1000004));
		assertThat(oLCandType.getMHUPIItemProductID()).isEqualTo(BigInteger.valueOf(1000002));
		assertThat(oLCandType.getQtyItemCapacity()).isEqualTo(new BigDecimal("2.0"));
		assertThat(oLCandType.getMProductPriceAttributeID()).isNull();
		assertThat(oLCandType.getMProductPriceID()).isEqualTo(BigInteger.valueOf(1000000));
		assertThat(oLCandType.getIsManualPrice()).isEqualTo("N");
		assertThat(oLCandType.getPriceEntered()).isEqualTo(new BigDecimal("3"));
		assertThat(oLCandType.getQty()).isEqualTo(new BigDecimal("20.0"));

		assertThat(oLCandType.getLine()).isEqualTo(BigInteger.valueOf(10));
		assertThat(oLCandType.getPOReference()).isEqualTo("test_po_ref");

		assertThat(oLCandType.getReplicationEventAttr()).isEqualTo(ReplicationEventEnum.AfterChange);
		assertThat(oLCandType.getReplicationModeAttr()).isEqualTo(ReplicationModeEnum.Table);
		assertThat(oLCandType.getReplicationTypeAttr()).isEqualTo(ReplicationTypeEnum.Merge);

		assertThat(mockRabbitMQProcessor.called).isEqualTo(1);
	}

	private final static class MockRabbitMQProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

}
