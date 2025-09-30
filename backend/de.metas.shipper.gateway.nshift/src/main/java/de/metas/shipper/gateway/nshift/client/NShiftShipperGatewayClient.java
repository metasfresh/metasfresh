package de.metas.shipper.gateway.nshift.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.commons.json.JsonDeliveryOrderRequest;
import de.metas.shipper.gateway.commons.json.JsonDeliveryOrderResponse;
import de.metas.shipper.gateway.commons.json.JsonDeliveryOrderResponseItem;
import de.metas.shipper.gateway.commons.model.ShipmentOrderParcel;
import de.metas.shipper.gateway.nshift.NShiftConstants;
import de.metas.shipper.gateway.nshift.config.NShiftConfig;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLineId;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
public class NShiftShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LogManager.getLogger(NShiftShipperGatewayClient.class);

	@NonNull private final NShiftShipmentService shipmentService;
	//TODO implement as provided by carrier
	private final static PackageLabelType DEFAULT_LABEL_TYPE = new PackageLabelType() {};
	@NonNull private final NShiftConfig config;

	@Override
	@NonNull
	public ShipperGatewayId getShipperGatewayId()
	{
		return NShiftConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	@NonNull
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final JsonDeliveryOrderResponse response = shipmentService.createShipment(toJsonDeliveryOrder(deliveryOrder));
		logger.debug("Received nShift response: {}", response);

		//TODO log request into Carrier_ShipmentOrder_Log

		return updateDeliveryOrder(deliveryOrder, response);

	}

	private DeliveryOrder updateDeliveryOrder(final @NonNull DeliveryOrder deliveryOrder, @NonNull final JsonDeliveryOrderResponse response)
	{
		final String language = "de"; //TODO introduce something like deliveryOrder.getDeliveryAddress().getBpartnerLanguage();

		final ImmutableMap<DeliveryOrderLineId, JsonDeliveryOrderResponseItem> lineIdToResponseMap = response.getItems().stream()
				.collect(ImmutableMap.toImmutableMap(JsonDeliveryOrderResponseItem::getLineId, Function.identity()));
		final ImmutableList<DeliveryOrderLine> updatedDeliveryOrderLines = deliveryOrder.getDeliveryOrderLines()
				.stream()
				.map(line -> updateDeliveryOrderLine(line, lineIdToResponseMap.get(line.getId()), language))
				.collect(ImmutableList.toImmutableList());
		return deliveryOrder.withDeliveryOrderLines(updatedDeliveryOrderLines);
	}

	private DeliveryOrderLine updateDeliveryOrderLine(@NonNull final DeliveryOrderLine line, @NonNull final JsonDeliveryOrderResponseItem jsonDeliveryOrderResponseItem, @NonNull final String language)
	{
		final ShipmentOrderParcel shipmentOrderParcel = ShipmentOrderParcel.ofDeliveryOrderLineOrNull(line);
		if (shipmentOrderParcel == null)
		{
			throw new ShipperGatewayException("Cannot update delivery order line because it is not in the proper format: " + line);
		}

		final String awb = jsonDeliveryOrderResponseItem.getAwb();
		final byte[] labelData = Base64.getDecoder().decode(jsonDeliveryOrderResponseItem.getLabelPdfBase64());
		final String trackingUrl = config.getTrackingUrl(awb, language);

		final ShipmentOrderParcel updatedShipmentOrderParcel = shipmentOrderParcel.toBuilder()
				.awb(awb)
				.trackingUrl(trackingUrl)
				.labelPdfBase64(labelData)
				.build();

		return line.withCustomDeliveryData(updatedShipmentOrderParcel);
	}

	private @NonNull JsonDeliveryOrderRequest toJsonDeliveryOrder(final @NonNull DeliveryOrder deliveryOrder)
	{
		return JsonDeliveryOrderRequest.builder()
				.id(deliveryOrder.getId())

				//TODO
				.build();
	}

	@NonNull
	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final String orderIdAsString = String.valueOf(deliveryOrder.getId());
		return deliveryOrder.getDeliveryOrderLines()
				.stream()
				.map(ShipmentOrderParcel::ofDeliveryOrderLineOrNull)
				.filter(Objects::nonNull)
				.map(parcel -> createPackageLabel(parcel.getLabelPdfBase64(), parcel.getAwb(), orderIdAsString))
				.collect(Collectors.toList());
	}

	@NonNull
	private static PackageLabels createPackageLabel(final byte[] labelData, @NonNull final String awb, @NonNull final String deliveryOrderIdAsString)
	{
		return PackageLabels.builder()
				.orderId(OrderId.of(NShiftConstants.SHIPPER_GATEWAY_ID, deliveryOrderIdAsString))
				.defaultLabelType(NShiftPackageLabelType.DEFAULT)
				.label(PackageLabel.builder()
						.type(NShiftPackageLabelType.DEFAULT)
						.labelData(labelData)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.fileName(awb)
						.build())
				.build();
	}

}