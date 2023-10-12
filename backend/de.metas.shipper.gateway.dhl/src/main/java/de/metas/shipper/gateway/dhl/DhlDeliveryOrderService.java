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
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlCustomsDocument;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.shipper.gateway.dhl.DhlDeliveryOrderRepository.getAllShipmentOrdersForRequest;

@Service
@AllArgsConstructor
public class DhlDeliveryOrderService implements DeliveryOrderService
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

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
							.packageId(po.getPackageId())
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
		po.getPackageId();
		// inOutDAO.retrieveLinesForInOutId(InOutId.ofRepoId())

		return DhlCustomsDocument.builder()
				.consigneeEORI(consigneeBpartner.getEORI())
				.shipperEORI(orgBpEORI)
				.build();
	}
}
