package de.metas.shipper.gateway.nshift;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.nshift.client.NShiftShipperProduct;
import de.metas.shipper.gateway.spi.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderItem;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.ShipperId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.shipping.mpackage.PackageItem;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static de.metas.shipper.gateway.commons.DeliveryOrderUtil.getPOReferences;

@Component
@RequiredArgsConstructor
class NShiftDraftDeliveryOrderCreator
{
	// @NonNull private final ExternalSystemMessageSender externalSystemMessageSender;
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final PurchaseOrderToShipperTransportationRepository purchaseOrderToShipperTransportationRepository;

	//TODO inject this from a separate configuration table, like we do for DHL
	private final static UomId HARDCODE_CM_UOM_ID = UomId.ofRepoId(540047);//CM
	private static final BigDecimal DEFAULT_PackageWeightInKg = BigDecimal.ONE;

	@NonNull
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

		final boolean isInternationalShipment = pickupFromLocation.getC_Country_ID() != deliverToLocation.getC_Country_ID();

		return DeliveryOrder.builder()
				.shipperId(shipperId)
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				//

				.shipperProduct(isInternationalShipment ? NShiftShipperProduct.DHL_INTERNATIONAL : NShiftShipperProduct.DHL_NATIONAL) // TODO this should be made user-selectable. Ref: https://github.com/metasfresh/me03/issues/3128
				.customerReference(getPOReferences(request.getPackageInfos()))
				.shipperEORI(pickupFromBPartner.getEORI())
				.receiverEORI(deliverToBPartner.getEORI())
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
				.deliveryOrderParcels(toDeliveryOrderLines(request.getPackageInfos()))
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

		final Language bpLanguage = Language.asLanguage(deliverToBPartner.getAD_Language());
		return ContactPerson.builder()
				.name(deliverToBPartner.getName())
				.emailAddress(deliverToBPartner.getEMail())
				.simplePhoneNumber(deliverToPhoneNumber)
				.languageCode(bpLanguage != null ? bpLanguage.getLanguageCode() : null)
				.build();
	}

	private ImmutableList<DeliveryOrderParcel> toDeliveryOrderLines(@NotNull final Set<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		return packageInfos.stream()
				.map(packageInfo -> {
					final ImmutableList<DeliveryOrderItem> deliveryOrderItems = purchaseOrderToShipperTransportationRepository.getPackageById(packageInfo.getPackageId()).getPackageContents()
							.stream()
							.map(this::createDeliveryOrderItems)
							.collect(ImmutableList.toImmutableList());
					return DeliveryOrderParcel.builder()
							.packageDimensions(getPackageDimensions(packageInfo.getPackageId(), HARDCODE_CM_UOM_ID))
							.packageId(packageInfo.getPackageId())
							.grossWeightKg(packageInfo.getWeightInKgOr(DEFAULT_PackageWeightInKg))
							.content(packageInfo.getDescription())
							.items(deliveryOrderItems)
							.build();
				})
				.collect(ImmutableList.toImmutableList());
	}

	private DeliveryOrderItem createDeliveryOrderItems(final PackageItem packageItem)
	{
		Check.assumeNotNull(packageItem.getQuantity(), "quantity must not be null, for packageItem " + packageItem);
		final ProductId productId = packageItem.getProductId();
		final I_M_Product product = productDAO.getById(productId);
		final BigDecimal weightInKg = computeNominalGrossWeightInKg(packageItem).orElse(BigDecimal.ZERO);
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(packageItem.getOrderLineId());

		final UomId targetUOMID = CoalesceUtil.coalesceNotNull(UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID()), packageItem.getQuantity().getUomId());

		final Quantity quantity = uomConversionBL.convertQuantityTo(packageItem.getQuantity(), productId, targetUOMID);
		final Money unitPrice = Money.of(orderLine.getPriceEntered(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));
		final Money totalPackageValue = unitPrice.multiply(quantity.toBigDecimal());

		return DeliveryOrderItem.builder()
				.productName(product.getName())
				.productValue(product.getValue())
				.totalWeightInKg(weightInKg)
				.shippedQuantity(packageItem.getQuantity())
				.unitPrice(unitPrice)
				.totalValue(totalPackageValue)
				.build();
	}

	private Optional<BigDecimal> computeNominalGrossWeightInKg(final PackageItem packageItem)
	{
		final ProductId productId = packageItem.getProductId();
		final Quantity quantity = packageItem.getQuantity();
		return productBL.computeGrossWeight(productId, quantity)
				.map(weight -> uomConversionBL.convertToKilogram(weight, productId))
				.map(Quantity::getAsBigDecimal);
	}

	@NonNull
	public static PackageDimensions getPackageDimensions(@NonNull final PackageId packageId, @NonNull final UomId toUomId)
	{
		final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
		final I_M_HU_PackingMaterial packingMaterial = packingMaterialDAO.retrievePackingMaterialOrNull(packageId);

		if (packingMaterial == null)
		{
			throw new AdempiereException("There is no packing material for M_Package_HU_ID=" + packageId.getRepoId() + ". Please create a packing material and set its correct dimensions.");
		}

		return packingMaterialDAO.retrievePackageDimensions(packingMaterial, toUomId);
	}
}
