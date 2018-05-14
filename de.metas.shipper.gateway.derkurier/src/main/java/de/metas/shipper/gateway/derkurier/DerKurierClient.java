package de.metas.shipper.gateway.derkurier;

import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.report.IJasperService;
import org.compiere.util.Env;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.process.ProcessInfo;
import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.misc.DerKurierDeliveryOrderEmailer;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine;
import de.metas.shipper.gateway.derkurier.restapi.models.Routing;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder.DeliveryOrderBuilder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DerKurierClient implements ShipperGatewayClient
{

	@VisibleForTesting
	@Getter(value = AccessLevel.PACKAGE)
	private final RestTemplate restTemplate;

	private final Converters converters;

	private final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository;

	private final DerKurierDeliveryOrderEmailer derKurierDeliveryOrderEmailer;

	public DerKurierClient(
			@NonNull final RestTemplate restTemplate,
			@NonNull final Converters converters,
			@NonNull final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository,
			@NonNull final DerKurierDeliveryOrderEmailer derKurierDeliveryOrderEmailer)
	{
		this.derKurierDeliveryOrderRepository = derKurierDeliveryOrderRepository;
		this.restTemplate = restTemplate;
		this.converters = converters;
		this.derKurierDeliveryOrderEmailer = derKurierDeliveryOrderEmailer;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDeliveryOrder(@NonNull final DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException
	{
		throw new UnsupportedOperationException("DerKurierClient.createDeliveryOrder is not implemented");
	}

	@VisibleForTesting
	Routing postRoutingRequest(@NonNull final RoutingRequest routingRequest)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<RoutingRequest> entity = new HttpEntity<>(routingRequest, httpHeaders);
		try
		{
			final ResponseEntity<Routing> result = restTemplate.exchange("/routing/request", HttpMethod.POST, entity, Routing.class);
			return result.getBody();
		}
		catch (final HttpClientErrorException e)
		{
			final ShipperGatewayException shipperGatewayException = //
					new ShipperGatewayException("HttpClientErrorException with statusCode=" + e.getStatusCode());

			throw AdempiereException.wrapIfNeeded(shipperGatewayException)
					.appendParametersToMessage()
					.setParameter("responseBodyAsString", e.getResponseBodyAsString())
					.setParameter("routingRequest", routingRequest);
		}
	}

	@Override
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final RoutingRequest routingRequest = converters.createRoutingRequestFrom(deliveryOrder);
		final Routing routing = postRoutingRequest(routingRequest);

		final DeliveryOrder completedDeliveryOrder = createDeliveryOrderFromResponse(routing, deliveryOrder);

		final List<String> csvLines = converters.createCsv(completedDeliveryOrder);

		final AttachmentEntry attachmentEntry = derKurierDeliveryOrderRepository
				.attachCsvToDeliveryOrder(deliveryOrder, csvLines);

		printPackageLabels(deliveryOrder);

		// we want to only send the mail after creating the CSV and creating the labels worked.
		derKurierDeliveryOrderEmailer.sendAttachmentAsEmail(deliveryOrder.getShipperId(), attachmentEntry);

		return completedDeliveryOrder;
	}

	private DeliveryOrder createDeliveryOrderFromResponse(
			@NonNull final Routing routing,
			@NonNull final DeliveryOrder originalDeliveryOrder)
	{
		final OrderId orderId = OrderId.of(
				getShipperGatewayId(),
				Integer.toString(originalDeliveryOrder.getRepoId()));

		final DeliveryOrderBuilder builder = originalDeliveryOrder.toBuilder()
				.orderId(orderId)

				// Pickup
				.pickupDate(PickupDate.builder()
						.date(routing.getSendDate())
						.timeTo(routing.getSender().getPickupUntil())
						.build())

				// Delivery
				.deliveryDate(DeliveryDate.builder()
						.date(routing.getDeliveryDate())
						.timeFrom(routing.getConsignee().getEarliestTimeOfDelivery())
						.build())
				// prepare adding the updated delivery positions by clearing the old ones
				.clearDeliveryPositions();

		for (final DeliveryPosition originalDeliveryPosition : originalDeliveryOrder.getDeliveryPositions())
		{
			final DerKurierDeliveryData originalDerKurierDeliveryData = //
					DerKurierDeliveryData.ofDeliveryPosition(originalDeliveryPosition)
							.toBuilder()
							.station(routing.getConsignee().getStationFormatted())
							.build();

			final DeliveryPosition newDeliveryPosition = originalDeliveryPosition
					.toBuilder()
					.customDeliveryData(originalDerKurierDeliveryData)
					.build();
			builder.deliveryPosition(newDeliveryPosition);
		}

		return builder.build();
	}

	@Override
	public DeliveryOrder voidDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		throw new UnsupportedOperationException("Der Kurier doesn't support voiding delivery orders via software");
	}

	private void printPackageLabels(@NonNull final DeliveryOrder deliveryOrder)
	{
		final int adProcessId = retrievePackageLableAdProcessId();

		final ITableRecordReference deliveryOrderTableRecordReference = //
				derKurierDeliveryOrderRepository.toTableRecordReference(deliveryOrder);

		final ImmutableList<DeliveryPosition> deliveryPositions = deliveryOrder.getDeliveryPositions();
		for (final DeliveryPosition deliveryPosition : deliveryPositions)
		{
			final DerKurierDeliveryData derKurierDeliveryData = //
					DerKurierDeliveryData.ofDeliveryPosition(deliveryPosition);

			ProcessInfo.builder()
					.setTitle("Label-" + derKurierDeliveryData.getParcelNumber())
					.setCtx(Env.getCtx())
					.setAD_Process_ID(adProcessId)
					.setRecord(deliveryOrderTableRecordReference) // we want the jasper to be archived and attached to the delivery order
					.addParameter(
							IJasperService.PARAM_PrintCopies,
							1)
					.addParameter(
							I_DerKurier_DeliveryOrderLine.COLUMNNAME_DerKurier_DeliveryOrderLine_ID,
							deliveryOrderTableRecordReference.getRecord_ID())
					.setPrintPreview(false)
					// Execute report in a new transaction
					.buildAndPrepareExecution()
					.onErrorThrowException(true)
					.executeSync();

			Loggables.get().addLog("Created package label for {}", deliveryOrderTableRecordReference);
		}
	}

	private int retrievePackageLableAdProcessId()
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final String sysconfigKey = DerKurierConstants.SYSCONFIG_DERKURIER_LABEL_PROCESS_ID;
		final int adProcessId = Services.get(ISysConfigBL.class).getIntValue(sysconfigKey, -1, adClientId, adOrgId);
		Check.errorIf(adProcessId <= 0, "Missing sysconfig value for 'Der Kurier' package label jasper report; name={}; AD_Client_ID={}; AD_Org_ID={}",
				sysconfigKey, adClientId, adOrgId);
		return adProcessId;
	}

	/**
	 * Returns an empty list, because https://leoz.derkurier.de:13000/rs/api/v1/document/label does not yet work,
	 * so we need to fire up our own jasper report and print them ourselves. This is done in {@link #completeDeliveryOrder(DeliveryOrder)}.
	 */
	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder)
	{
		return ImmutableList.of();
	}
}
