package de.metas.shipper.gateway.nshift.client;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.commons.model.ShipmentOrderParcel;
import de.metas.shipper.gateway.nshift.NShiftConstants;
import de.metas.shipper.gateway.nshift.config.NShiftConfig;
import de.metas.shipper.gateway.nshift.json.JsonPackageResponse;
import de.metas.shipper.gateway.nshift.json.JsonShipmentDocument;
import de.metas.shipper.gateway.nshift.json.JsonShipmentResponse;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
public class NShiftShipperGatewayClient implements ShipperGatewayClient {
    private static final Logger logger = LogManager.getLogger(NShiftShipperGatewayClient.class);

    @NonNull private final NShiftShipmentService shipmentService;
    //TODO implement as provided by carrier
	private final static PackageLabelType DEFAULT_LABEL_TYPE = new PackageLabelType() {};@NonNull private final NShiftConfig config;

    @Override
    @NonNull
    public ShipperGatewayId getShipperGatewayId() {
        return NShiftConstants.SHIPPER_GATEWAY_ID;
    }

    @Override
    @NonNull
    public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException {
        final JsonShipmentResponse response = shipmentService.createShipment(deliveryOrder);
        logger.debug("Received nShift response: {}", response);

        final String mainTrackingNumber = response.getLines().stream()
                .flatMap(line -> line.getPkgs().stream())
                .map(JsonPackageResponse::getPkgNo)
                .findFirst()
                .orElse(response.getShpNo());

        final String language = "de"; //TODO introduce something like deliveryOrder.getDeliveryAddress().getBpartnerLanguage();

        final ImmutableList<NShiftCustomDeliveryDataDetail> details = response.getShpDocuments().stream()
                .map(document -> toCustomDeliveryDataDetail(document, language))
                .filter(Objects::nonNull)
                .collect(ImmutableList.toImmutableList());

        final NShiftCustomDeliveryData customDeliveryData = NShiftCustomDeliveryData.builder()
                .details(details)
                .build();

        return deliveryOrder.toBuilder()
                .trackingNumber(mainTrackingNumber)
                .customDeliveryData(customDeliveryData) // why this is custom data and not standard/common data?
                .build();
    }

	@Nullable
	private NShiftCustomDeliveryDataDetail toCustomDeliveryDataDetail(@NonNull final JsonShipmentDocument document, @NonNull final String language) {
	    //TODO validate if this is correct
	    if (!"Label".equalsIgnoreCase(document.getDocumentType())) {
	        return null;
	    }
	    if (Check.isEmpty(document.getContent(), true)) {
	        logger.warn("Label document received from nShift without content: {}", document);
	        return null;
	    }
	    final String pkgNo = document.getPkgNo();
	    if (Check.isEmpty(pkgNo, true)) {
	        logger.warn("Label document received from nShift without PkgNo, it will be skipped: {}", document);
	        return null;
	    }

	    final byte[] labelData = Base64.getDecoder().decode(document.getContent());
	    final String trackingUrl = config.getTrackingUrl(pkgNo, language);

	    return NShiftCustomDeliveryDataDetail.builder()
	            .pdfLabelData(labelData)
	            .trackingNumber(pkgNo)
	            .trackingUrl(trackingUrl)
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
				.map(parcel -> createPackageLabel(parcel.getLabelPdfBase64(), parcel.getAwb(), orderIdAsString))
				.collect(Collectors.toList());
	}

	@NonNull
	private static PackageLabels createPackageLabel(final byte[] labelData, @NonNull final String awb, @NonNull final String deliveryOrderIdAsString)
	{
		return PackageLabels.builder()
				.orderId(OrderId.of(NShiftConstants.SHIPPER_GATEWAY_ID, deliveryOrderIdAsString))
				.defaultLabelType(DEFAULT_LABEL_TYPE)
				.label(PackageLabel.builder()
						.type(DEFAULT_LABEL_TYPE)
						.labelData(labelData)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.fileName(awb)
						.build())
				.build();
	}

    // @Override
    // @NonNull
    // public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException {
    //     final NShiftCustomDeliveryData customDeliveryData = NShiftCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData());
    //     if (customDeliveryData == null || customDeliveryData.getDetails().isEmpty()) {
    //         return ImmutableList.of();
    //     }
	//
    //     final OrderId orderId = OrderId.of(getShipperGatewayId(), deliveryOrder.getId());
	//
    //     final Map<String, List<NShiftCustomDeliveryDataDetail>> detailsByTrackingNumber = customDeliveryData.getDetails().stream()
    //             .filter(detail -> Check.isNotBlank(detail.getTrackingNumber()))
    //             .collect(Collectors.groupingBy(NShiftCustomDeliveryDataDetail::getTrackingNumber));
	//
    //     return detailsByTrackingNumber.entrySet().stream()
    //             .map(entry -> {
    //                 final String trackingNumber = entry.getKey();
    //                 final List<NShiftCustomDeliveryDataDetail> packageDetails = entry.getValue();
	//
    //                 final ImmutableList<PackageLabel> packageLabels = packageDetails.stream()
    //                         .map(detail -> PackageLabel.builder()
    //                                 .type(NShiftPackageLabelType.DEFAULT)
    //                                 .contentType(PackageLabel.CONTENTTYPE_PDF)
    //                                 .labelData(detail.getPdfLabelData())
    //                                 .fileName(trackingNumber)
    //                                 .build())
    //                         .collect(ImmutableList.toImmutableList());
	//
    //                 if (packageLabels.isEmpty()) {
    //                     return null;
    //                 }
	//
    //                 return PackageLabels.builder()
    //                         .orderId(orderId)
    //                         .defaultLabelType(NShiftPackageLabelType.DEFAULT)
    //                         .labels(packageLabels)
    //                         .build();
    //             })
    //             .filter(Objects::nonNull)
    //             .collect(Collectors.toList());
    // }

}