package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.compiere.util.TimeUtil;

import de.metas.organization.OrgId;
import de.metas.vendor.gateway.api.order.MSV3OrderResponsePackageItemPartRepoId;
import de.metas.vertical.pharma.msv3.protocol.order.MSV3PurchaseCandidateId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderDefectReason;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponse;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackage;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItem;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemPart.Type;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3SubstitutionDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution;
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
public class MSV3PurchaseOrderResponsePersister
{
	public static MSV3PurchaseOrderResponsePersister createNewForOrgId(@NonNull final OrgId orgId)
	{
		return new MSV3PurchaseOrderResponsePersister(orgId);
	}

	@NonNull
	private final OrgId orgId;
	private final MSV3OrderResponsePackageItemPartRepoIds responseItemPartRepoIds = MSV3OrderResponsePackageItemPartRepoIds.newMutableInstance();

	public I_MSV3_BestellungAntwort storePurchaseOrderResponse(@NonNull final OrderResponse response)
	{
		final I_MSV3_BestellungAntwort responseRecord = createRecord(response);
		saveRecord(responseRecord);

		for (final OrderResponsePackage responseOrder : response.getOrderPackages())
		{
			final I_MSV3_BestellungAntwortAuftrag responseOrderRecord = createRecord(responseOrder);

			responseOrderRecord.setMSV3_BestellungAntwort(responseRecord);
			saveRecord(responseOrderRecord);

			for (final OrderResponsePackageItem responseOrderItem : responseOrder.getItems())
			{
				final I_MSV3_BestellungAntwortPosition responseOrderItemRecord = createRecord(responseOrderItem);

				responseOrderItemRecord.setMSV3_BestellungAntwortAuftrag(responseOrderRecord);
				saveRecord(responseOrderItemRecord);

				for (final OrderResponsePackageItemPart itemPart : responseOrderItem.getParts())
				{
					final I_MSV3_BestellungAnteil itemPartRecord = createRecord(itemPart);

					itemPartRecord.setMSV3_BestellungAntwortPosition(responseOrderItemRecord);
					saveRecord(itemPartRecord);

					responseItemPartRepoIds.putRepoId(itemPart.getId(), MSV3OrderResponsePackageItemPartRepoId.ofRepoId(itemPartRecord.getMSV3_BestellungAnteil_ID()));
				}
			}
		}

		return responseRecord;
	}

	private I_MSV3_BestellungAntwort createRecord(@NonNull final OrderResponse response)
	{
		final I_MSV3_BestellungAntwort record = newInstanceOutOfTrx(I_MSV3_BestellungAntwort.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_BestellSupportId(response.getSupportId().getValueAsInt());
		record.setMSV3_Id(response.getOrderId().getValueAsString());
		record.setMSV3_NachtBetrieb(response.isNightOperation());

		return record;
	}

	private I_MSV3_BestellungAntwortAuftrag createRecord(@NonNull final OrderResponsePackage responseOrder)
	{
		final I_MSV3_BestellungAntwortAuftrag record = newInstanceOutOfTrx(I_MSV3_BestellungAntwortAuftrag.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_Auftragsart(responseOrder.getOrderType().getV2SoapCode().value());
		record.setMSV3_Auftragskennung(responseOrder.getOrderIdentification());
		record.setMSV3_AuftragsSupportID(responseOrder.getSupportId().getValueAsInt());
		record.setMSV3_GebindeId(responseOrder.getPackingMaterialId());
		record.setMSV3_Id(responseOrder.getId().getValueAsString());

		final I_MSV3_FaultInfo faultInfoRecord = Msv3FaultInfoDataPersister
				.newInstanceWithOrgId(orgId)
				.storeMsv3FaultInfoOrNull(responseOrder.getFaultInfo());
		record.setMSV3_Auftragsfehler(faultInfoRecord);

		return record;
	}

	private I_MSV3_BestellungAntwortPosition createRecord(@NonNull final OrderResponsePackageItem responseItem)
	{
		final I_MSV3_BestellungAntwortPosition record = newInstanceOutOfTrx(I_MSV3_BestellungAntwortPosition.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_BestellLiefervorgabe(responseItem.getDeliverySpecifications().getV2SoapCode().value());
		record.setMSV3_BestellMenge(responseItem.getQty().getValueAsInt());
		record.setMSV3_BestellPzn(responseItem.getPzn().getValueAsString());
		record.setC_PurchaseCandidate_ID(MSV3PurchaseCandidateId.toRepoId(responseItem.getPurchaseCandidateId()));

		final I_MSV3_Substitution substitutionRecord = Msv3SubstitutionDataPersister.newInstanceWithOrgId(orgId)
				.storeSubstitutionOrNull(responseItem.getSubstitution());
		record.setMSV3_BestellungSubstitution(substitutionRecord);

		return record;
	}

	private I_MSV3_BestellungAnteil createRecord(@NonNull final OrderResponsePackageItemPart anteil)
	{
		final I_MSV3_BestellungAnteil bestellungAnteilRecord = newInstanceOutOfTrx(I_MSV3_BestellungAnteil.class);
		bestellungAnteilRecord.setAD_Org_ID(orgId.getRepoId());

		Optional.ofNullable(anteil.getDefectReason())
				.map(OrderDefectReason::value)
				.ifPresent(bestellungAnteilRecord::setMSV3_Grund);

		bestellungAnteilRecord.setMSV3_Lieferzeitpunkt(TimeUtil.asTimestamp(anteil.getDeliveryDate()));
		bestellungAnteilRecord.setMSV3_Menge(anteil.getQty().getValueAsInt());
		bestellungAnteilRecord.setMSV3_Tourabweichung(anteil.isTourDeviation());
		bestellungAnteilRecord.setMSV3_Typ(Type.getValueOrNull(anteil.getType()));

		return bestellungAnteilRecord;
	}

	public MSV3OrderResponsePackageItemPartRepoIds getResponseItemPartRepoIds()
	{
		return responseItemPartRepoIds.copyAsImmutable();
	}
}
