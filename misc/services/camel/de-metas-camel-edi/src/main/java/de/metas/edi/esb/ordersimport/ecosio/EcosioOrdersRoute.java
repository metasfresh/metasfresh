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

package de.metas.edi.esb.ordersimport.ecosio;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.sun.istack.Nullable;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.commons.route.notifyreplicationtrx.ExceptionUtil;
import de.metas.edi.esb.commons.route.notifyreplicationtrx.NotifyReplicationTrxRequest;
import de.metas.edi.esb.jaxb.metasfresh.COrderDeliveryRuleEnum;
import de.metas.edi.esb.jaxb.metasfresh.COrderDeliveryViaRuleEnum;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpADInputDataSourceLookupINType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCOLCandType;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationTypeEnum;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static de.metas.edi.esb.commons.route.notifyreplicationtrx.NotifyReplicationTrxRoute.NOTIFY_REPLICATION_TRX_UPDATE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class EcosioOrdersRoute
		extends RouteBuilder
{
	/**
	 * This place holder is evaluated via {@link Util#resolveProperty(CamelContext, String)}, that's why we don't put it in {@code {{...}}}
	 */
	private static final String INPUT_ORDERS_REMOTE = "edi.file.orders.ecosio.remote";

	private static final String ROUTE_PROPERTY_ECOSIO_ORDER_ROUTE_CONTEXT = "ECOSIO_ORDER_ROUTE_CONTEXT";

	@VisibleForTesting
	static final String INPUT_ORDERS_LOCAL = "edi.file.orders.ecosio";

	@Override
	public final void configure()
	{
		final JaxbDataFormat dataFormat = new JaxbDataFormat(EDIImpCOLCandType.class.getPackage().getName());
		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(StandardCharsets.UTF_8.name());

		final String adClientValue = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADClientValue);
		final String userEnteredById = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADUserEnteredByID);
		final String dataDestinationInternalName = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADInputDataDestination_InternalName);

		final String defaultDeliveryRule = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_DELIVERY_RULE);
		final String defaultDeliveryViaRule = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_DELIVERY_VIA_RULE);

		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_ORDERS_REMOTE, "");
		if (!Util.isEmpty(remoteEndpoint))
		{
			from(remoteEndpoint)
					.routeId("ecosio-Remote-XML-Orders-To-Local")
					.log(LoggingLevel.INFO, "Getting remote file")
					.to("{{" + INPUT_ORDERS_LOCAL + "}}");
		}

		// @formatter:off

		from("{{" + INPUT_ORDERS_LOCAL + "}}")
				.routeId("ecosio-XML-Orders-To-MF-OLCand")
				.doTry()
					.process(this::initRouteContext)
					.split(xpath("/EDI_Message/EDI_Imp_C_OLCands/EDI_Imp_C_OLCand"))
						.unmarshal(dataFormat)
						.process(exchange -> {
							final EDIImpCOLCandType olCandXML = exchange.getIn().getBody(EDIImpCOLCandType.class);

							olCandXML.setADClientValueAttr(adClientValue);
							olCandXML.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
							olCandXML.setReplicationModeAttr(ReplicationModeEnum.Table);
							olCandXML.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
							olCandXML.setVersionAttr("*");

							// there might be multiple orders in one file. so we compose the replication-name like this to make sure one order is one replication-trx
							final String trxNameAttr = olCandXML.getPOReference() + "_" + exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
							olCandXML.setTrxNameAttr(trxNameAttr);

							olCandXML.setADInputDataSourceID(new BigInteger("540215")); // hardcoded value for ecosio
							olCandXML.setADUserEnteredByID(new BigInteger(userEnteredById));

							if (olCandXML.getDeliveryRule() == null)
							{
								olCandXML.setDeliveryRule(COrderDeliveryRuleEnum.fromValue(defaultDeliveryRule)); // TODO: let metasfresh decide
							}
							if (olCandXML.getDeliveryViaRule() == null)
							{
								olCandXML.setDeliveryViaRule(COrderDeliveryViaRuleEnum.fromValue(defaultDeliveryViaRule));
							}

							rewriteDatePromised(olCandXML.getDatePromised());

							final EDIImpADInputDataSourceLookupINType dataDestinationLookup =
									Util.resolveGenericLookup(EDIImpADInputDataSourceLookupINType.class,
															  Constants.LOOKUP_TEMPLATE_InternalName.createMandatoryValueLookup(dataDestinationInternalName));
							olCandXML.setADDataDestinationID(dataDestinationLookup);

							addEDIImpCOLCandTypeToContext(olCandXML, exchange);
						})
						.marshal(dataFormat)

						.log(LoggingLevel.INFO, "EDI: Sending XML Order document to metasfresh...")
						.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
						.to("{{" + Constants.EP_AMQP_TO_MF + "}}")
						.process(this::updateContext)
					.end()
					.process(this::prepareNotifyReplicationTrxDone)
					.choice()
						.when(bodyAs(NotifyReplicationTrxRequest.class).isNull())
							.log(LoggingLevel.INFO, "Nothing to do! NotifyReplicationTrxRequest is null!")
						.otherwise()
							.split(body())
								.to(direct(NOTIFY_REPLICATION_TRX_UPDATE))
						.endChoice()
					.end()
				.endDoTry()
				.doCatch(Exception.class)
					.process(this::prepareNotifyReplicationTrxError)
					.choice()
						.when(bodyAs(NotifyReplicationTrxRequest.class).isNull())
							.log(LoggingLevel.INFO, "Nothing to do! NotifyReplicationTrxRequest is null!")
						.otherwise()
							.split(body())
								.to(direct(NOTIFY_REPLICATION_TRX_UPDATE))
						.endChoice()
					.end()
				.end();

		// @formatter:on
	}

	/**
	 * If the clearing-center sends e.g. {@code 2020-11-27T00:00:01}, then it means "throughout the day of Nov-27th".
	 * So we translate this to {@code 2020-11-27T23:59:00} to make metasfresh's tour-planning act accordingly..
	 */
	@Nullable
	private void rewriteDatePromised(@Nullable final XMLGregorianCalendar datePromised)
	{
		if (datePromised == null)
		{
			return;
		}

		if (datePromised.getHour() == 0 && datePromised.getMinute() == 0)
		{
			datePromised.setHour(23);
			datePromised.setMinute(59);
			datePromised.setSecond(0);
		}
	}

	private void initRouteContext(final Exchange exchange)
	{
		final String clientValue = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADClientValue);

		final EcosioOrdersRouteContext context = EcosioOrdersRouteContext.builder()
				.clientValue(clientValue)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_ECOSIO_ORDER_ROUTE_CONTEXT, context);
	}

	private void prepareNotifyReplicationTrxDone(@NonNull final Exchange exchange)
	{
		final EcosioOrdersRouteContext context = exchange.getProperty(ROUTE_PROPERTY_ECOSIO_ORDER_ROUTE_CONTEXT, EcosioOrdersRouteContext.class);

		final List<NotifyReplicationTrxRequest> requests = context.getTransactionDetails()
				.stream()
				.filter(EcosioOrdersRouteContext.TransactionDetails::isImported)
				.map(details -> NotifyReplicationTrxRequest.finished()
						.clientValue(context.getClientValue())
						.trxName(details.getTrxName())
						.build())
				.collect(ImmutableList.toImmutableList());

		final Object body = requests.isEmpty() ? null : requests;
		exchange.getIn().setBody(body);
	}

	private void addEDIImpCOLCandTypeToContext(@NonNull final EDIImpCOLCandType olcand, @NonNull final Exchange exchange)
	{
		final EcosioOrdersRouteContext context = exchange.getProperty(ROUTE_PROPERTY_ECOSIO_ORDER_ROUTE_CONTEXT, EcosioOrdersRouteContext.class);

		context.setCurrentTrxName(olcand.getTrxNameAttr());
		context.addTransactionDetails(EcosioOrdersRouteContext.TransactionDetails.builder().trxName(olcand.getTrxNameAttr()).build());
	}

	private void updateContext(@NonNull final Exchange exchange)
	{
		final EcosioOrdersRouteContext context = exchange.getProperty(ROUTE_PROPERTY_ECOSIO_ORDER_ROUTE_CONTEXT, EcosioOrdersRouteContext.class);

		context.transactionDetailsImported();
	}

	private void prepareNotifyReplicationTrxError(@NonNull final Exchange exchange)
	{
		final EcosioOrdersRouteContext context = exchange.getProperty(ROUTE_PROPERTY_ECOSIO_ORDER_ROUTE_CONTEXT, EcosioOrdersRouteContext.class);

		final String errorMsg = ExceptionUtil.extractErrorMessage(exchange);

		final List<NotifyReplicationTrxRequest> requests = context.getTransactionDetails()
				.stream()
				.filter(EcosioOrdersRouteContext.TransactionDetails::isImported)
				.map(details -> NotifyReplicationTrxRequest.error(errorMsg)
						.clientValue(context.getClientValue())
						.trxName(details.getTrxName())
						.build())
				.collect(ImmutableList.toImmutableList());

		final Object body = requests.isEmpty() ? null : requests;
		exchange.getIn().setBody(body);
	}
}
