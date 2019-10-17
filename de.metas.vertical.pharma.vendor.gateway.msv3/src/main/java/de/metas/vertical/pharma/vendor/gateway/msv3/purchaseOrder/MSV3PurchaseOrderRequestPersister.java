package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import de.metas.organization.OrgId;
import de.metas.vertical.pharma.msv3.protocol.order.MSV3PurchaseCandidateId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequestPackageItem;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Bestellung;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungPosition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MSV3PurchaseOrderRequestPersister
{
	public static MSV3PurchaseOrderRequestPersister createNewForOrgId(final OrgId orgId)
	{
		return new MSV3PurchaseOrderRequestPersister(orgId);
	}

	@NonNull
	private final OrgId orgId;

	public I_MSV3_Bestellung storePurchaseOrderRequest(@NonNull final OrderCreateRequest request)
	{
		final I_MSV3_Bestellung bestellungRecord = createRecord(request);
		save(bestellungRecord);

		for (final OrderCreateRequestPackage requestOrder : request.getOrderPackages())
		{
			final I_MSV3_BestellungAuftrag requestOrderRecord = createRecord(requestOrder);
			requestOrderRecord.setMSV3_Bestellung(bestellungRecord);
			save(requestOrderRecord);

			for (final OrderCreateRequestPackageItem requestItem : requestOrder.getItems())
			{
				final I_MSV3_BestellungPosition bestellungPositionRecord = createRecord(requestItem);
				bestellungPositionRecord.setMSV3_BestellungAuftrag(requestOrderRecord);
				save(bestellungPositionRecord);
			}
		}
		return bestellungRecord;
	}

	private I_MSV3_Bestellung createRecord(@NonNull final OrderCreateRequest request)
	{
		final I_MSV3_Bestellung record = newInstanceOutOfTrx(I_MSV3_Bestellung.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_BestellSupportId(request.getSupportId().getValueAsInt());
		record.setMSV3_Id(request.getOrderId().getValueAsString());

		return record;
	}

	private I_MSV3_BestellungAuftrag createRecord(@NonNull final OrderCreateRequestPackage requestOrder)
	{
		final I_MSV3_BestellungAuftrag record = newInstanceOutOfTrx(I_MSV3_BestellungAuftrag.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_Auftragsart(requestOrder.getOrderType().getV2SoapCode().value());
		record.setMSV3_Auftragskennung(requestOrder.getOrderIdentification());
		record.setMSV3_AuftragsSupportID(requestOrder.getSupportId().getValueAsInt());
		record.setMSV3_GebindeId(requestOrder.getPackingMaterialId());
		record.setMSV3_Id(requestOrder.getId().getValueAsString());

		return record;
	}

	private I_MSV3_BestellungPosition createRecord(@NonNull final OrderCreateRequestPackageItem requestItem)
	{
		final I_MSV3_BestellungPosition record = newInstanceOutOfTrx(I_MSV3_BestellungPosition.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_Liefervorgabe(requestItem.getDeliverySpecifications().getV2SoapCode().value());
		record.setMSV3_Menge(requestItem.getQty().getValueAsInt());
		record.setMSV3_Pzn(requestItem.getPzn().getValueAsString());
		record.setC_PurchaseCandidate_ID(MSV3PurchaseCandidateId.toRepoId(requestItem.getPurchaseCandidateId()));

		return record;
	}
}
