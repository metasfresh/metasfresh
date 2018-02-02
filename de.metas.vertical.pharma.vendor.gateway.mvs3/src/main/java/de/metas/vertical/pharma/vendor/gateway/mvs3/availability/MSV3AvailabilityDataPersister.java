package de.metas.vertical.pharma.vendor.gateway.mvs3.availability;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3SubstitutionDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsantwortArtikel;
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
public class MSV3AvailabilityDataPersister
{
	public static MSV3AvailabilityDataPersister createNewInstance(final int orgId)
	{
		return new MSV3AvailabilityDataPersister(orgId);
	}

	private final Map<VerfuegbarkeitAnteil, Integer> verfuegbarkeitAnteil2DataRecordId = new HashMap<>();

	private final int orgId;

	public I_MSV3_VerfuegbarkeitsanfrageEinzelne storeAvailabilityRequest(
			@NonNull final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne,
			@NonNull final ImmutableMap<Artikel, MSV3ArtikelContextInfo> artikel2ContextInfo)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelneRecord = newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelne.class);
		verfuegbarkeitsanfrageEinzelneRecord.setMSV3_Id(verfuegbarkeitsanfrageEinzelne.getId());
		verfuegbarkeitsanfrageEinzelneRecord.setAD_Org_ID(orgId);

		save(verfuegbarkeitsanfrageEinzelneRecord);

		final List<Artikel> artikel = verfuegbarkeitsanfrageEinzelne.getArtikel();
		for (final Artikel singleArticle : artikel)
		{
			final I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel articleRecord = //
					createVerfuegbarkeitsanfrageEinzelne_Artikel(singleArticle, artikel2ContextInfo);

			articleRecord.setMSV3_VerfuegbarkeitsanfrageEinzelne(verfuegbarkeitsanfrageEinzelneRecord);
			save(articleRecord);
		}

		return verfuegbarkeitsanfrageEinzelneRecord;
	}

	private I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel createVerfuegbarkeitsanfrageEinzelne_Artikel(
			@NonNull final Artikel singleArticle,
			@NonNull final ImmutableMap<Artikel, MSV3ArtikelContextInfo> artikel2ContextInfo)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel articleRecord = newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class);
		articleRecord.setAD_Org_ID(orgId);
		articleRecord.setMSV3_Bedarf(singleArticle.getBedarf());
		articleRecord.setMSV3_Menge(singleArticle.getMenge());
		articleRecord.setMSV3_Pzn(Long.toString(singleArticle.getPzn()));

		final MSV3ArtikelContextInfo availabilityRequestItem = artikel2ContextInfo.get(singleArticle);
		if (availabilityRequestItem != null)
		{
			articleRecord.setC_OrderLineSO_ID(availabilityRequestItem.getSalesOrderLineId());
			articleRecord.setC_PurchaseCandidate_ID(availabilityRequestItem.getPurchaseCandidateId());
		}
		return articleRecord;
	}

	public I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort storeAvailabilityResponse(
			@NonNull final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort,
			@NonNull ImmutableMap<VerfuegbarkeitsantwortArtikel, MSV3ArtikelContextInfo> artikel2ContextInfo)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = //
				createVerfuegbarkeitsanfrageEinzelneAntwortRecord(verfuegbarkeitsanfrageEinzelneAntwort);
		save(verfuegbarkeitsanfrageEinzelneAntwortRecord);

		final List<VerfuegbarkeitsantwortArtikel> artikel = verfuegbarkeitsanfrageEinzelneAntwort.getArtikel();
		for (final VerfuegbarkeitsantwortArtikel singleArtikel : artikel)
		{
			final I_MSV3_VerfuegbarkeitsantwortArtikel verfuegbarkeitsantwortArtikelRecord = //
					createVerfuegbarkeitsantwortArtikelRecord(
							singleArtikel,
							artikel2ContextInfo);

			verfuegbarkeitsantwortArtikelRecord.setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort(verfuegbarkeitsanfrageEinzelneAntwortRecord);
			save(verfuegbarkeitsantwortArtikelRecord);

			final List<VerfuegbarkeitAnteil> anteile = singleArtikel.getAnteile();
			for (final VerfuegbarkeitAnteil anteil : anteile)
			{
				final I_MSV3_VerfuegbarkeitAnteil verfuegbarkeitAnteilRecord = createVerfuegbarkeitAnteilRecord(anteil);

				verfuegbarkeitAnteilRecord.setMSV3_VerfuegbarkeitsantwortArtikel(verfuegbarkeitsantwortArtikelRecord);
				save(verfuegbarkeitAnteilRecord);
				verfuegbarkeitAnteil2DataRecordId.put(anteil, verfuegbarkeitAnteilRecord.getMSV3_VerfuegbarkeitAnteil_ID());
			}
		}

		return verfuegbarkeitsanfrageEinzelneAntwortRecord;
	}

	private I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort createVerfuegbarkeitsanfrageEinzelneAntwortRecord(
			@NonNull final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = //
				newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class);

		verfuegbarkeitsanfrageEinzelneAntwortRecord.setAD_Org_ID(orgId);
		verfuegbarkeitsanfrageEinzelneAntwortRecord
				.setMSV3_Id(verfuegbarkeitsanfrageEinzelneAntwort.getId());
		verfuegbarkeitsanfrageEinzelneAntwortRecord
				.setMSV3_VerfuegbarkeitTyp(verfuegbarkeitsanfrageEinzelneAntwort.getRTyp().value());

		return verfuegbarkeitsanfrageEinzelneAntwortRecord;
	}

	private I_MSV3_VerfuegbarkeitsantwortArtikel createVerfuegbarkeitsantwortArtikelRecord(
			@NonNull final VerfuegbarkeitsantwortArtikel singleArtikel,
			@NonNull ImmutableMap<VerfuegbarkeitsantwortArtikel, MSV3ArtikelContextInfo> artikel2ContextInfo)
	{
		final I_MSV3_VerfuegbarkeitsantwortArtikel verfuegbarkeitsantwortArtikelRecord =//
				newInstance(I_MSV3_VerfuegbarkeitsantwortArtikel.class);

		verfuegbarkeitsantwortArtikelRecord.setAD_Org_ID(orgId);
		verfuegbarkeitsantwortArtikelRecord.setMSV3_AnfrageMenge(singleArtikel.getAnfrageMenge());
		verfuegbarkeitsantwortArtikelRecord.setMSV3_AnfragePzn(Long.toString(singleArtikel.getAnfragePzn()));

		final I_MSV3_Substitution substitutionOrNull = Msv3SubstitutionDataPersister
				.newInstanceWithOrgId(orgId)
				.storeSubstitutionOrNull(singleArtikel.getSubstitution());
		verfuegbarkeitsantwortArtikelRecord.setMSV3_VerfuegbarkeitSubstitution(substitutionOrNull);

		final MSV3ArtikelContextInfo availabilityRequestItem = artikel2ContextInfo.get(singleArtikel);
		if (availabilityRequestItem != null)
		{
			verfuegbarkeitsantwortArtikelRecord.setC_OrderLineSO_ID(availabilityRequestItem.getSalesOrderLineId());
			verfuegbarkeitsantwortArtikelRecord.setC_PurchaseCandidate_ID(availabilityRequestItem.getPurchaseCandidateId());
		}

		return verfuegbarkeitsantwortArtikelRecord;
	}

	private I_MSV3_VerfuegbarkeitAnteil createVerfuegbarkeitAnteilRecord(@NonNull final VerfuegbarkeitAnteil anteil)
	{
		final I_MSV3_VerfuegbarkeitAnteil verfuegbarkeitAnteilRecord = newInstance(I_MSV3_VerfuegbarkeitAnteil.class);

		verfuegbarkeitAnteilRecord.setAD_Org_ID(orgId);
		verfuegbarkeitAnteilRecord.setMSV3_Grund(anteil.getGrund().value());
		verfuegbarkeitAnteilRecord.setMSV3_Lieferzeitpunkt(MSV3Util.toTimestampOrNull(anteil.getLieferzeitpunkt()));
		verfuegbarkeitAnteilRecord.setMSV3_Menge(anteil.getMenge());
		verfuegbarkeitAnteilRecord.setMSV3_Tourabweichung(anteil.isTourabweichung());
		verfuegbarkeitAnteilRecord.setMSV3_Typ(anteil.getTyp().value());
		return verfuegbarkeitAnteilRecord;
	}

	public int getVerfuegbarkeitAnteilDataRecordId(@NonNull final VerfuegbarkeitAnteil verfuegbarkeitAnteil)
	{
		return verfuegbarkeitAnteil2DataRecordId.get(verfuegbarkeitAnteil);
	}
}
