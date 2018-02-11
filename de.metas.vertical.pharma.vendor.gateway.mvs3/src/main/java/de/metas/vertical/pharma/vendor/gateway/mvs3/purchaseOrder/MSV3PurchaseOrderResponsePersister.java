package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3SubstitutionDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungDefektgrund;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungRueckmeldungTyp;
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
public class MSV3PurchaseOrderResponsePersister
{
	public static MSV3PurchaseOrderResponsePersister createNewForOrgId(
			final int orgId,
			@NonNull final Map<BestellungAntwortPosition, Integer> bestellungAntwortPosition2PurchaseCandidateId)
	{
		return new MSV3PurchaseOrderResponsePersister(
				orgId,
				bestellungAntwortPosition2PurchaseCandidateId,
				new HashMap<>());
	}

	private final int orgId;

	private final Map<BestellungAntwortPosition, Integer> bestellungAntwortPosition2PurchaseCandidateId;

	private final Map<BestellungAnteil, Integer> bestellungAnteil2Id;

	public I_MSV3_BestellungAntwort storePurchaseOrderResponse(@NonNull final BestellungAntwort bestellungAntwort)
	{
		final I_MSV3_BestellungAntwort bestellungAntwortRecord = createBestellungAntwortRecord(bestellungAntwort);
		save(bestellungAntwortRecord);

		final List<BestellungAntwortAuftrag> auftraege = bestellungAntwort.getAuftraege();
		for (final BestellungAntwortAuftrag auftrag : auftraege)
		{
			final I_MSV3_BestellungAntwortAuftrag bestellungAntwortAuftragRecord = //
					createBestellungAntwortAuftragRecord(auftrag);

			bestellungAntwortAuftragRecord.setMSV3_BestellungAntwort(bestellungAntwortRecord);
			save(bestellungAntwortAuftragRecord);

			final List<BestellungAntwortPosition> positionen = auftrag.getPositionen();
			for (final BestellungAntwortPosition position : positionen)
			{
				final I_MSV3_BestellungAntwortPosition bestellungAntwortPositionRecord = //
						createBestellungAntwortPositionRecord(position);

				bestellungAntwortPositionRecord.setMSV3_BestellungAntwortAuftrag(bestellungAntwortAuftragRecord);
				save(bestellungAntwortPositionRecord);

				final List<BestellungAnteil> anteile = position.getAnteile();
				for (final BestellungAnteil anteil : anteile)
				{
					final I_MSV3_BestellungAnteil bestellungAnteilRecord = createBestellungAnteilRecord(anteil);

					bestellungAnteilRecord.setMSV3_BestellungAntwortPosition(bestellungAntwortPositionRecord);
					save(bestellungAnteilRecord);

					bestellungAnteil2Id.put(anteil, bestellungAnteilRecord.getMSV3_BestellungAnteil_ID());
				}
			}
		}
		return bestellungAntwortRecord;
	}

	private I_MSV3_BestellungAntwort createBestellungAntwortRecord(@NonNull final BestellungAntwort bestellungAntwort)
	{
		final I_MSV3_BestellungAntwort bestellungAntwortRecord = newInstance(I_MSV3_BestellungAntwort.class);
		bestellungAntwortRecord.setAD_Org_ID(orgId);
		bestellungAntwortRecord.setMSV3_BestellSupportId(bestellungAntwort.getBestellSupportId());
		bestellungAntwortRecord.setMSV3_Id(bestellungAntwort.getId());
		bestellungAntwortRecord.setMSV3_NachtBetrieb(bestellungAntwort.isNachtBetrieb());

		return bestellungAntwortRecord;
	}

	private I_MSV3_BestellungAntwortAuftrag createBestellungAntwortAuftragRecord(@NonNull final BestellungAntwortAuftrag auftrag)
	{
		final I_MSV3_BestellungAntwortAuftrag bestellungAntwortAuftragRecord = newInstance(I_MSV3_BestellungAntwortAuftrag.class);
		bestellungAntwortAuftragRecord.setAD_Org_ID(orgId);
		bestellungAntwortAuftragRecord.setMSV3_Auftragsart(auftrag.getAuftragsart().value());
		bestellungAntwortAuftragRecord.setMSV3_Auftragskennung(auftrag.getAuftragskennung());
		bestellungAntwortAuftragRecord.setMSV3_AuftragsSupportID(auftrag.getAuftragsSupportID());
		bestellungAntwortAuftragRecord.setMSV3_GebindeId(auftrag.getGebindeId());
		bestellungAntwortAuftragRecord.setMSV3_Id(auftrag.getId());

		final I_MSV3_FaultInfo msv3FaultInfoOrNull = Msv3FaultInfoDataPersister
				.newInstanceWithOrgId(orgId)
				.storeMsv3FaultInfoOrNull(auftrag.getAuftragsfehler());
		bestellungAntwortAuftragRecord.setMSV3_Auftragsfehler(msv3FaultInfoOrNull);

		return bestellungAntwortAuftragRecord;
	}

	private I_MSV3_BestellungAntwortPosition createBestellungAntwortPositionRecord(
			@NonNull final BestellungAntwortPosition position)
	{
		final I_MSV3_BestellungAntwortPosition bestellungAntwortPositionRecord = //
				newInstance(I_MSV3_BestellungAntwortPosition.class);
		bestellungAntwortPositionRecord.setAD_Org_ID(orgId);
		bestellungAntwortPositionRecord.setMSV3_BestellLiefervorgabe(position.getBestellLiefervorgabe().value());
		bestellungAntwortPositionRecord.setMSV3_BestellMenge(position.getBestellMenge());
		bestellungAntwortPositionRecord.setMSV3_BestellPzn(Long.toString(position.getBestellPzn()));

		bestellungAntwortPositionRecord.setC_PurchaseCandidate_ID(
				bestellungAntwortPosition2PurchaseCandidateId.get(position));

		final I_MSV3_Substitution substitutionOrNull = Msv3SubstitutionDataPersister
				.newInstanceWithOrgId(orgId)
				.storeSubstitutionOrNull(position.getSubstitution());
		bestellungAntwortPositionRecord.setMSV3_BestellungSubstitution(substitutionOrNull);

		return bestellungAntwortPositionRecord;
	}

	private I_MSV3_BestellungAnteil createBestellungAnteilRecord(@NonNull final BestellungAnteil anteil)
	{
		final I_MSV3_BestellungAnteil bestellungAnteilRecord = newInstance(I_MSV3_BestellungAnteil.class);
		bestellungAnteilRecord.setAD_Org_ID(orgId);

		Optional.ofNullable(anteil.getGrund())
				.map(BestellungDefektgrund::value)
				.ifPresent(bestellungAnteilRecord::setMSV3_Grund);

		bestellungAnteilRecord.setMSV3_Lieferzeitpunkt(MSV3Util.toTimestampOrNull(anteil.getLieferzeitpunkt()));
		bestellungAnteilRecord.setMSV3_Menge(anteil.getMenge());
		bestellungAnteilRecord.setMSV3_Tourabweichung(anteil.isTourabweichung());

		Optional.ofNullable(anteil.getTyp())
				.map(BestellungRueckmeldungTyp::value)
				.ifPresent(bestellungAnteilRecord::setMSV3_Typ);

		return bestellungAnteilRecord;
	}

	public ImmutableMap<BestellungAnteil, Integer> getBestellungAnteil2Id()
	{
		return ImmutableMap.copyOf(bestellungAnteil2Id);
	}
}
