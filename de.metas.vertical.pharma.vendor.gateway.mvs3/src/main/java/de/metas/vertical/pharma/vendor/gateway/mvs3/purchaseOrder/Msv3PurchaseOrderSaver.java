package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.FaultInfoSaver;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.SubstitutionSaver;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungPosition;
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

@Service
public class Msv3PurchaseOrderSaver
{
	private FaultInfoSaver faultInfoPersistanceMapper;
	private SubstitutionSaver substitutionPersistanceMapper;

	public Msv3PurchaseOrderSaver(
			@NonNull final FaultInfoSaver faultInfoPersistanceMapper,
			@NonNull final SubstitutionSaver substitutionPersistanceMapper)
	{
		this.faultInfoPersistanceMapper = faultInfoPersistanceMapper;
		this.substitutionPersistanceMapper = substitutionPersistanceMapper;
	}

	public I_MSV3_Bestellung storePurchaseOrder(@NonNull final Bestellung bestellung)
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
		bestellungRecord.setMSV3_BestellSupportId(bestellung.getBestellSupportId());
		bestellungRecord.setMSV3_Id(bestellung.getId());
		return bestellungRecord;
	}

	private I_MSV3_BestellungAuftrag createBestellungAuftragRecord(@NonNull final BestellungAuftrag auftrag)
	{
		final I_MSV3_BestellungAuftrag bestellungAuftrag = newInstance(I_MSV3_BestellungAuftrag.class);
		bestellungAuftrag.setMSV3_Auftragsart(auftrag.getAuftragsart().toString());
		bestellungAuftrag.setMSV3_Auftragskennung(auftrag.getAuftragskennung());
		bestellungAuftrag.setMSV3_AuftragsSupportID(auftrag.getAuftragsSupportID());
		bestellungAuftrag.setMSV3_GebindeId(auftrag.getGebindeId());
		bestellungAuftrag.setMSV3_Id(auftrag.getId());

		return bestellungAuftrag;
	}

	private I_MSV3_BestellungPosition createBestellungPosition(@NonNull final BestellungPosition position)
	{
		final I_MSV3_BestellungPosition bestellungPositionRecord = newInstance(I_MSV3_BestellungPosition.class);
		bestellungPositionRecord.setMSV3_Liefervorgabe(position.getLiefervorgabe().toString());
		bestellungPositionRecord.setMSV3_Menge(position.getMenge());
		bestellungPositionRecord.setMSV3_Pzn(Long.toString(position.getPzn()));
		return bestellungPositionRecord;
	}

	public I_MSV3_BestellungAntwort storePurchaseOrderResponse(@NonNull final BestellungAntwort bestellungAntwort)
	{
		final I_MSV3_BestellungAntwort bestellungAntwortRecord = createBestellungAntwortRecord(bestellungAntwort);
		save(bestellungAntwortRecord);

		final List<BestellungAntwortAuftrag> auftraege = bestellungAntwort.getAuftraege();
		for (final BestellungAntwortAuftrag auftrag : auftraege)
		{
			final I_MSV3_BestellungAntwortAuftrag bestellungAntwortAuftragRecord = createBestellungAntwortAuftragRecord(auftrag);

			bestellungAntwortAuftragRecord.setMSV3_BestellungAntwort(bestellungAntwortRecord);
			save(bestellungAntwortAuftragRecord);

			final List<BestellungAntwortPosition> positionen = auftrag.getPositionen();
			for (final BestellungAntwortPosition position : positionen)
			{
				final I_MSV3_BestellungAntwortPosition bestellungAntwortPositionRecord = createBestellungAntwortPositionRecord(position);

				bestellungAntwortPositionRecord.setMSV3_BestellungAntwortAuftrag(bestellungAntwortAuftragRecord);
				save(bestellungAntwortPositionRecord);

				final List<BestellungAnteil> anteile = position.getAnteile();
				for (final BestellungAnteil anteil : anteile)
				{
					final I_MSV3_BestellungAnteil bestellungAnteilRecord = createBestellungAnteilRecord(anteil);

					bestellungAnteilRecord.setMSV3_BestellungAntwortPosition(bestellungAntwortPositionRecord);
					save(bestellungAnteilRecord);
				}
			}
		}
		return bestellungAntwortRecord;
	}

	private I_MSV3_BestellungAntwort createBestellungAntwortRecord(@NonNull final BestellungAntwort bestellungAntwort)
	{
		final I_MSV3_BestellungAntwort bestellungAntwortRecord = newInstance(I_MSV3_BestellungAntwort.class);
		bestellungAntwortRecord.setMSV3_BestellSupportId(bestellungAntwort.getBestellSupportId());
		bestellungAntwortRecord.setMSV3_Id(bestellungAntwort.getId());
		bestellungAntwortRecord.setMSV3_NachtBetrieb(bestellungAntwort.isNachtBetrieb());
		return bestellungAntwortRecord;
	}

	private I_MSV3_BestellungAntwortAuftrag createBestellungAntwortAuftragRecord(@NonNull final BestellungAntwortAuftrag auftrag)
	{
		final I_MSV3_BestellungAntwortAuftrag bestellungAntwortAuftragRecord = newInstance(I_MSV3_BestellungAntwortAuftrag.class);
		bestellungAntwortAuftragRecord.setMSV3_Auftragsart(auftrag.getAuftragsart().toString());
		bestellungAntwortAuftragRecord.setMSV3_Auftragsfehler(faultInfoPersistanceMapper.storeMsv3FaultInfoOrNull(auftrag.getAuftragsfehler()));
		bestellungAntwortAuftragRecord.setMSV3_Auftragskennung(auftrag.getAuftragskennung());
		bestellungAntwortAuftragRecord.setMSV3_AuftragsSupportID(auftrag.getAuftragsSupportID());
		bestellungAntwortAuftragRecord.setMSV3_GebindeId(auftrag.getGebindeId());
		bestellungAntwortAuftragRecord.setMSV3_Id(auftrag.getId());
		return bestellungAntwortAuftragRecord;
	}

	private I_MSV3_BestellungAntwortPosition createBestellungAntwortPositionRecord(@NonNull final BestellungAntwortPosition position)
	{
		final I_MSV3_BestellungAntwortPosition bestellungAntwortPositionRecord = newInstance(I_MSV3_BestellungAntwortPosition.class);
		bestellungAntwortPositionRecord.setMSV3_BestellLiefervorgabe(position.getBestellLiefervorgabe().toString());
		bestellungAntwortPositionRecord.setMSV3_BestellMenge(position.getBestellMenge());
		bestellungAntwortPositionRecord.setMSV3_BestellPzn(Long.toString(position.getBestellPzn()));
		bestellungAntwortPositionRecord.setMSV3_BestellungSubstitution(substitutionPersistanceMapper.storeSubstitutionOrNull(position.getSubstitution()));
		return bestellungAntwortPositionRecord;
	}

	private I_MSV3_BestellungAnteil createBestellungAnteilRecord(@NonNull final BestellungAnteil anteil)
	{
		final I_MSV3_BestellungAnteil bestellungAnteilRecord = newInstance(I_MSV3_BestellungAnteil.class);
		bestellungAnteilRecord.setMSV3_Grund(anteil.getGrund().toString());
		bestellungAnteilRecord.setMSV3_Lieferzeitpunkt(MSV3Util.toTimestampOrNull(anteil.getLieferzeitpunkt()));
		bestellungAnteilRecord.setMSV3_Menge(anteil.getMenge());
		bestellungAnteilRecord.setMSV3_Tourabweichung(anteil.isTourabweichung());
		bestellungAnteilRecord.setMSV3_Typ(anteil.getTyp().toString());
		return bestellungAnteilRecord;
	}


}
