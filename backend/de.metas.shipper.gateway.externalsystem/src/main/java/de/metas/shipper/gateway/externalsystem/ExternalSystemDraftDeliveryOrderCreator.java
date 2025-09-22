package de.metas.shipper.gateway.externalsystem;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExternalSystemDraftDeliveryOrderCreator implements DraftDeliveryOrderCreator
{
	// @NonNull private final ExternalSystemMessageSender externalSystemMessageSender;
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);

	private static final BigDecimal DEFAULT_PackageWeightInKg = BigDecimal.ONE;

	@Override
	public ShipperGatewayId getShipperGatewayId() {return null; /* default */}

	@NonNull
	@Override
	public @NotNull DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();

		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));
		final LocalDate pickupDate = deliveryOrderKey.getPickupDate();

		final BPartnerLocationId deliverToBPartnerLocationId = BPartnerLocationId.ofRepoId(deliveryOrderKey.getDeliverToBPartnerId(), deliveryOrderKey.getDeliverToBPartnerLocationId());
		final I_C_BPartner deliverToBPartner = bpartnerBL.getById(deliverToBPartnerLocationId.getBpartnerId());
		final I_C_BPartner_Location deliverToBPLocation = Check.assumeNotNull(bpartnerDAO.getBPartnerLocationByIdInTrx(deliverToBPartnerLocationId), "bp location not null");
		final I_C_Location deliverToLocation = locationDAO.getById(LocationId.ofRepoId(deliverToBPLocation.getC_Location_ID()));

		final ShipperId shipperId = deliveryOrderKey.getShipperId();

		return DeliveryOrder.builder()
				.shipperId(shipperId)
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				//

				.shipperProduct(null) // TODO this should be made user-selectable. Ref: https://github.com/metasfresh/me03/issues/3128
				.customerReference(getPOReferences(request.getPackageInfos()))
				.customDeliveryData(null)
				//
				// Pickup aka Shipper
				.pickupAddress(toPickFromAddress(pickupFromBPartner, pickupFromLocation))
				.pickupDate(PickupDate.builder()
						.date(pickupDate)
						.build())
				//
				// Delivery aka Receiver
				.deliveryAddress(toDeliverToAddress(deliverToBPartner, deliverToLocation))
				.deliveryContact(toDeliverToContact(deliverToBPartner, deliverToBPLocation))
				//
				// Delivery content
				.deliveryOrderLines(toDeliveryOrderLines(request.getPackageInfos()))
				//
				.build();

	}

	private static Address toPickFromAddress(final I_C_BPartner pickupFromBPartner, final I_C_Location pickupFromLocation)
	{
		return DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
				.companyName1(pickupFromBPartner.getName())
				.companyName2(pickupFromBPartner.getName2())
				.build();
	}

	private static Address toDeliverToAddress(final I_C_BPartner deliverToBPartner, final I_C_Location deliverToLocation)
	{
		return DeliveryOrderUtil.prepareAddressFromLocation(deliverToLocation)
				.companyName1(deliverToBPartner.getName())
				.companyName2(deliverToBPartner.getName2())
				.bpartnerId(deliverToBPartner.getC_BPartner_ID()) // afaics used only for logging
				.build();
	}

	private static ContactPerson toDeliverToContact(final I_C_BPartner deliverToBPartner, final I_C_BPartner_Location deliverToBPLocation)
	{
		final String deliverToPhoneNumber = CoalesceUtil.firstNotEmptyTrimmed(deliverToBPLocation.getPhone(), deliverToBPLocation.getPhone2(), deliverToBPartner.getPhone2());

		return ContactPerson.builder()
				.emailAddress(deliverToBPartner.getEMail())
				.simplePhoneNumber(deliverToPhoneNumber)
				.build();
	}

	private ImmutableList<DeliveryOrderLine> toDeliveryOrderLines(@NotNull final Set<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		return packageInfos.stream()
				.map(packageInfo -> DeliveryOrderLine.builder()
						.packageDimensions(getPackageDimensions(packageInfo.getPackageId()))
						.packageId(packageInfo.getPackageId())
						.grossWeightKg(packageInfo.getWeightInKgOr(DEFAULT_PackageWeightInKg))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private PackageDimensions getPackageDimensions(@NonNull final PackageId packageId)
	{
		// FIXME hardcoded
		return PackageDimensions.builder()
				.widthInCM(100)
				.heightInCM(100)
				.lengthInCM(100)
				.build();
		// final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
		// final I_M_HU_PackingMaterial packingMaterial = packingMaterialDAO.retrievePackingMaterialOrNull(packageId);
		//
		// if (packingMaterial == null)
		// {
		// 	throw new AdempiereException("There is no packing material for M_Package_HU_ID=" + packageId.getRepoId() + ". Please create a packing material and set its correct dimensions.");
		// }
		//
		// return packingMaterialDAO.retrievePackageDimensions(packingMaterial, toUomId);
	}

	private static String getPOReferences(@NonNull final Collection<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		return packageInfos.stream()
				.map(CreateDraftDeliveryOrderRequest.PackageInfo::getPoReference)
				.map(StringUtils::trimBlankToNull)
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.joining(", "));
	}
}
