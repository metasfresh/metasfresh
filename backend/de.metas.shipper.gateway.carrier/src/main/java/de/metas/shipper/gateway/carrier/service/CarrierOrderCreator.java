/*
 * #%L
 * de.metas.shipper.gateway.carrier
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

package de.metas.shipper.gateway.carrier.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.carrier.CarrierConstants;
import de.metas.shipper.gateway.carrier.CarrierShipperProduct;
import de.metas.shipper.gateway.carrier.model.ShipmentOrderData;
import de.metas.shipper.gateway.carrier.model.ShipmentOrderItem;
import de.metas.shipper.gateway.carrier.model.ShipmentOrderParcel;
import de.metas.shipper.gateway.commons.DeliveryOrderUtil;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
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
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static de.metas.shipper.gateway.commons.DeliveryOrderUtil.getPOReferences;

@Service
@RequiredArgsConstructor
public class CarrierOrderCreator implements DraftDeliveryOrderCreator
{
	private static final BigDecimal DEFAULT_PackageWeightInKg = BigDecimal.ONE;

	//TODO inject this from a separate configuration table, like we do for DHL
	private final static UomId HARDCODE_CM_UOM_ID = UomId.ofRepoId(540047);//CM

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final PurchaseOrderToShipperTransportationRepository purchaseOrderToShipperTransportationRepository;

	@Override
	public String getShipperGatewayId()
	{
		return CarrierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDraftDeliveryOrder(final CreateDraftDeliveryOrderRequest request)
	{
		final DeliveryOrderKey deliveryOrderKey = request.getDeliveryOrderKey();

		final I_C_BPartner pickupFromBPartner = bpartnerOrgBL.retrieveLinkedBPartner(deliveryOrderKey.getFromOrgId());
		final I_C_Location pickupFromLocation = bpartnerOrgBL.retrieveOrgLocation(OrgId.ofRepoId(deliveryOrderKey.getFromOrgId()));

		final BPartnerId deliverToBPId = BPartnerId.ofRepoId(deliveryOrderKey.getDeliverToBPartnerId());
		final I_C_BPartner deliverToBP = bpartnerDAO.getById(deliverToBPId);
		final I_C_Location deliveryLocation = bpartnerDAO.getLocationByBpartnerLocationIdOrNull(BPartnerLocationId.ofRepoId(deliverToBPId, deliveryOrderKey.getDeliverToBPartnerLocationId()));

		Check.assumeNotNull(deliveryLocation, "deliveryLocation");
		return DeliveryOrder.builder()
				.shipperId(deliveryOrderKey.getShipperId())
				.shipperTransportationId(deliveryOrderKey.getShipperTransportationId())
				.deliveryDate(DeliveryDate.builder()
						.date(deliveryOrderKey.getPickupDate())
						.build())
				.pickupAddress(DeliveryOrderUtil.prepareAddressFromLocation(pickupFromLocation)
						.companyName1(pickupFromBPartner.getName())
						.companyName2(pickupFromBPartner.getName2())
						.build())
				.deliveryAddress(DeliveryOrderUtil.prepareAddressFromLocation(deliveryLocation)
						.companyName1(deliverToBP.getName())
						.companyName2(deliverToBP.getName2())
						.bpartnerId(deliverToBP.getC_BPartner_ID())
						.build())
				.shipperProduct(CarrierShipperProduct.DHL)//TODO make this configurable
				.customerReference(getPOReferences(request.getPackageInfos()))
				.customDeliveryData(ShipmentOrderData.builder()
						.shipperEORI(pickupFromBPartner.getEORI())
						.receiverEORI(deliverToBP.getEORI())
						.build())
				.deliveryOrderLines(createDeliveryOrderLines(request.getPackageInfos()))
				.build();
	}

	private ImmutableList<DeliveryOrderLine> createDeliveryOrderLines(final @NonNull Set<CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		return packageInfos.stream()
				.map(packageInfo -> {
					final ImmutableList<ShipmentOrderItem> deliveryOrderItems = purchaseOrderToShipperTransportationRepository.getPackageById(packageInfo.getPackageId()).getPackageContents()
							.stream()
							.map(this::createDeliveryOrderItems)
							.collect(ImmutableList.toImmutableList());
					return DeliveryOrderLine.builder()
							.packageDimensions(getPackageDimensions(packageInfo.getPackageId(), HARDCODE_CM_UOM_ID))
							.packageId(packageInfo.getPackageId())
							.grossWeightKg(packageInfo.getWeightInKgOr(DEFAULT_PackageWeightInKg))
							.content(packageInfo.getDescription())
							.customDeliveryLineData(ShipmentOrderParcel.builder()
									.items(deliveryOrderItems)
									.build())
							.build();
				})
				.collect(ImmutableList.toImmutableList());
	}

	private ShipmentOrderItem createDeliveryOrderItems(final PackageItem packageItem)
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

		return ShipmentOrderItem.builder()
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


