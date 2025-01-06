/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dhl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.handlingunits.impl.InOutPackageRepository;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.mpackage.Package;
import de.metas.mpackage.PackageId;
import de.metas.mpackage.PackageItem;
import de.metas.order.IOrderDAO;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlCustomsDocument;
import de.metas.shipper.gateway.dhl.model.DhlCustomsItem;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static de.metas.shipper.gateway.dhl.DhlDeliveryOrderRepository.getAllShipmentOrdersForRequest;

@Service
@AllArgsConstructor
public class DhlDeliveryOrderService implements DeliveryOrderService
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final InOutPackageRepository inOutPackageRepository;
	private final CurrencyRepository currencyRepository;
	private final DhlDeliveryOrderRepository dhlDeliveryOrderRepository;

	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@NonNull
	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null for deliveryOrder " + deliveryOrder);
		return TableRecordReference.of(I_DHL_ShipmentOrderRequest.Table_Name, deliveryOrderRepoId);
	}

	@NonNull
	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderRepoId)
	{
		return dhlDeliveryOrderRepository.getByRepoId(deliveryOrderRepoId)
				.withCustomDeliveryData(getDhlCustomDeliveryData(deliveryOrderRepoId));
	}

	/**
	 * Explanation of the different data structures:
	 * <p>
	 * - DeliveryOrder is the DTO
	 * - I_DHL_ShipmentOrderRequest is the persisted object for that DTO with data relevant for DHL.
	 * Each different shipper has its own "shipper-PO" with its own relevant data.
	 */
	@NonNull
	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		return dhlDeliveryOrderRepository.save(deliveryOrder);
	}

	private DhlCustomDeliveryData getDhlCustomDeliveryData(@NonNull final DeliveryOrderId deliveryOrderRepoId)
	{
		final List<I_DHL_ShipmentOrder> ordersPo = getAllShipmentOrdersForRequest(deliveryOrderRepoId);

		final I_DHL_ShipmentOrder firstOrder = ordersPo.get(0);
		final I_C_BPartner orgBPartner = bpartnerDAO.retrieveOrgBPartner(InterfaceWrapperHelper.getCtx(firstOrder), firstOrder.getAD_Org_ID(), I_C_BPartner.class, ITrx.TRXNAME_None);
		final String orgBpEori = orgBPartner.getEORI();

		final ImmutableList<DhlCustomDeliveryDataDetail> dhlCustomDeliveryDataDetail = ordersPo.stream()
				.map(po -> {
					DhlCustomsDocument customsDocument = null;
					if (po.isInternationalDelivery())
					{
						customsDocument = getCustomsDocument(firstOrder, po, orgBpEori);
					}

					return DhlCustomDeliveryDataDetail.builder()
							.packageId(PackageId.ofRepoId(po.getPackageId()))
							.awb(po.getawb())
							.sequenceNumber(DhlSequenceNumber.of(po.getDHL_ShipmentOrder_ID()))
							.pdfLabelData(po.getPdfLabelData())
							.trackingUrl(po.getTrackingURL())
							.internationalDelivery(po.isInternationalDelivery())
							.customsDocument(customsDocument)
							.build();
				})
				.collect(ImmutableList.toImmutableList());

		return DhlCustomDeliveryData.builder()
				.details(dhlCustomDeliveryDataDetail)
				.build();
	}

	private DhlCustomsDocument getCustomsDocument(@NonNull final I_DHL_ShipmentOrder firstOrder, @NonNull final I_DHL_ShipmentOrder po, @Nullable final String orgBpEORI)
	{
		final I_C_BPartner consigneeBpartner = bpartnerDAO.getById(firstOrder.getC_BPartner_ID());
		final Package mPackage = inOutPackageRepository.getPackageById(PackageId.ofRepoId(po.getPackageId()));

		final ImmutableList<DhlCustomsItem> dhlCustomsItems = mPackage.getPackageContents()
				.stream()
				.map(this::toDhlCustomsItem)
				.collect(ImmutableList.toImmutableList());

		return DhlCustomsDocument.builder()
				.consigneeEORI(consigneeBpartner.getEORI())
				.shipperEORI(orgBpEORI)
				.items(dhlCustomsItems)
				.build();
	}

	private DhlCustomsItem toDhlCustomsItem(@NonNull final PackageItem packageItem)
	{
		final ProductId productId = packageItem.getProductId();
		final I_M_Product product = productDAO.getById(productId);
		final BigDecimal weightInKg = productBL.getWeight(product, productBL.getWeightUOM(product));
		Quantity packagedQuantity;
		try
		{
			packagedQuantity = uomConversionBL.convertQuantityTo(packageItem.getQuantity(), productId, UomId.EACH);
		}
		catch (final NoUOMConversionException exception)
		{
			//can't convert to EACH, so we don't have an exact number. Just put 1
			packagedQuantity = Quantitys.of(1, UomId.EACH);
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(packageItem.getOrderLineId());
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));

		return DhlCustomsItem.builder()
				.itemDescription(product.getName())
				.weightInKg(weightInKg)
				.packagedQuantity(packagedQuantity.intValueExact())
				.itemValue(Amount.of(orderLine.getPriceEntered(), currencyCode))
				.build();
	}
}
