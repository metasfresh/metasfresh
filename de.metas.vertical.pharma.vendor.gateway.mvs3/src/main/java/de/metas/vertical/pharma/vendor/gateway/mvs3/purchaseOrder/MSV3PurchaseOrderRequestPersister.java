package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;
import java.util.Map;

import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungPosition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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
	public static MSV3PurchaseOrderRequestPersister createNewForOrgId(
			final int orgId,
			final Map<BestellungPosition, Integer> bestellungPosition2PurchaseCandidateId)
	{
		return new MSV3PurchaseOrderRequestPersister(
				orgId,
				bestellungPosition2PurchaseCandidateId);
	}

	private final int orgId;

	private final Map<BestellungPosition, Integer> bestellungPosition2PurchaseCandidateId;


	public I_MSV3_Bestellung storePurchaseOrderRequest(@NonNull final Bestellung bestellung)
	{
		final I_MSV3_Bestellung bestellungRecord = createBestellungRecord(bestellung);
		save(bestellungRecord);

		final List<BestellungAuftrag> auftraege = bestellung.getAuftraege();
		for (final BestellungAuftrag auftrag : auftraege)
		{
			final I_MSV3_BestellungAuftrag bestellungAuftragRecord = createBestellungAuftragRecord(auftrag);
			bestellungAuftragRecord.setMSV3_Bestellung(bestellungRecord);
			save(bestellungAuftragRecord);

			final List<BestellungPosition> positionen = auftrag.getPositionen();
			for (final BestellungPosition position : positionen)
			{
				final I_MSV3_BestellungPosition bestellungPositionRecord = createBestellungPosition(position);
				bestellungPositionRecord.setMSV3_BestellungAuftrag(bestellungAuftragRecord);
				save(bestellungPositionRecord);
			}
		}
		return bestellungRecord;
	}

	private I_MSV3_Bestellung createBestellungRecord(@NonNull final Bestellung bestellung)
	{
		final I_MSV3_Bestellung bestellungRecord = newInstance(I_MSV3_Bestellung.class);
		bestellungRecord.setAD_Org_ID(orgId);
		bestellungRecord.setMSV3_BestellSupportId(bestellung.getBestellSupportId());
		bestellungRecord.setMSV3_Id(bestellung.getId());

		return bestellungRecord;
	}

	private I_MSV3_BestellungAuftrag createBestellungAuftragRecord(@NonNull final BestellungAuftrag auftrag)
	{
		final I_MSV3_BestellungAuftrag bestellungAuftragRecord = newInstance(I_MSV3_BestellungAuftrag.class);
		bestellungAuftragRecord.setAD_Org_ID(orgId);
		bestellungAuftragRecord.setMSV3_Auftragsart(auftrag.getAuftragsart().value());
		bestellungAuftragRecord.setMSV3_Auftragskennung(auftrag.getAuftragskennung());
		bestellungAuftragRecord.setMSV3_AuftragsSupportID(auftrag.getAuftragsSupportID());
		bestellungAuftragRecord.setMSV3_GebindeId(auftrag.getGebindeId());
		bestellungAuftragRecord.setMSV3_Id(auftrag.getId());

		return bestellungAuftragRecord;
	}

	private I_MSV3_BestellungPosition createBestellungPosition(@NonNull final BestellungPosition position)
	{
		final I_MSV3_BestellungPosition bestellungPositionRecord = newInstance(I_MSV3_BestellungPosition.class);
		bestellungPositionRecord.setAD_Org_ID(orgId);
		bestellungPositionRecord.setMSV3_Liefervorgabe(position.getLiefervorgabe().value());
		bestellungPositionRecord.setMSV3_Menge(position.getMenge());
		bestellungPositionRecord.setMSV3_Pzn(Long.toString(position.getPzn()));
		bestellungPositionRecord.setC_PurchaseCandidate_ID(bestellungPosition2PurchaseCandidateId.get(position));

		return bestellungPositionRecord;
	}
}
