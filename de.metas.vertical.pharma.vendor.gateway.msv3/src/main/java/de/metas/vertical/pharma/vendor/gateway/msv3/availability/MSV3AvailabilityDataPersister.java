package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableMap;

import de.metas.organization.OrgId;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3SubstitutionDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsantwortArtikel;
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
public class MSV3AvailabilityDataPersister
{
	public static MSV3AvailabilityDataPersister createNewInstance(@NonNull final OrgId orgId)
	{
		return new MSV3AvailabilityDataPersister(orgId);
	}

	private final Map<StockAvailabilityResponseItemPart, Integer> verfuegbarkeitAnteil2DataRecordId = new HashMap<>();

	@NonNull
	private final OrgId orgId;

	public I_MSV3_VerfuegbarkeitsanfrageEinzelne storeAvailabilityRequest(
			@NonNull final StockAvailabilityQuery verfuegbarkeitsanfrageEinzelne,
			@NonNull final ImmutableMap<StockAvailabilityQueryItem, MSV3ArtikelContextInfo> immutableMap)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelneRecord = newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelne.class);
		verfuegbarkeitsanfrageEinzelneRecord.setMSV3_Id(verfuegbarkeitsanfrageEinzelne.getId());
		verfuegbarkeitsanfrageEinzelneRecord.setAD_Org_ID(orgId.getRepoId());

		save(verfuegbarkeitsanfrageEinzelneRecord);

		final List<StockAvailabilityQueryItem> artikel = verfuegbarkeitsanfrageEinzelne.getItems();
		for (final StockAvailabilityQueryItem singleArticle : artikel)
		{
			final I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel articleRecord = //
					createVerfuegbarkeitsanfrageEinzelne_Artikel(singleArticle, immutableMap);

			articleRecord.setMSV3_VerfuegbarkeitsanfrageEinzelne(verfuegbarkeitsanfrageEinzelneRecord);
			save(articleRecord);
		}

		return verfuegbarkeitsanfrageEinzelneRecord;
	}

	private I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel createVerfuegbarkeitsanfrageEinzelne_Artikel(
			@NonNull final StockAvailabilityQueryItem singleArticle,
			@NonNull final ImmutableMap<StockAvailabilityQueryItem, MSV3ArtikelContextInfo> immutableMap)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel articleRecord = newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelne_Artikel.class);
		articleRecord.setAD_Org_ID(orgId.getRepoId());
		articleRecord.setMSV3_Bedarf(singleArticle.getRequirementType().getCode());
		articleRecord.setMSV3_Menge(singleArticle.getQtyRequired().getValueAsInt());
		articleRecord.setMSV3_Pzn(singleArticle.getPzn().getValueAsString());

		final MSV3ArtikelContextInfo availabilityRequestItem = immutableMap.get(singleArticle);
		if (availabilityRequestItem != null)
		{
			articleRecord.setC_OrderLineSO_ID(availabilityRequestItem.getSalesOrderLineId());
			articleRecord.setC_PurchaseCandidate_ID(availabilityRequestItem.getPurchaseCandidateId());
		}
		return articleRecord;
	}

	public I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort storeAvailabilityResponse(
			@NonNull final StockAvailabilityResponse response,
			@NonNull ImmutableMap<StockAvailabilityResponseItem, MSV3ArtikelContextInfo> artikel2ContextInfo)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort responseRecord = createAndSaveRecord(response);

		final List<StockAvailabilityResponseItem> items = response.getItems();
		for (final StockAvailabilityResponseItem item : items)
		{
			final I_MSV3_VerfuegbarkeitsantwortArtikel itemRecord = createRecord(item, artikel2ContextInfo);
			itemRecord.setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort(responseRecord);
			save(itemRecord);

			for (final StockAvailabilityResponseItemPart itemPart : item.getParts())
			{
				final I_MSV3_VerfuegbarkeitAnteil verfuegbarkeitAnteilRecord = createRecord(itemPart);

				verfuegbarkeitAnteilRecord.setMSV3_VerfuegbarkeitsantwortArtikel(itemRecord);
				save(verfuegbarkeitAnteilRecord);
				verfuegbarkeitAnteil2DataRecordId.put(itemPart, verfuegbarkeitAnteilRecord.getMSV3_VerfuegbarkeitAnteil_ID());
			}
		}

		return responseRecord;
	}

	private I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort createAndSaveRecord(@NonNull final StockAvailabilityResponse response)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort record = newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class);

		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_Id(response.getId());
		record.setMSV3_VerfuegbarkeitTyp(response.getAvailabilityType().value());

		save(record);

		return record;
	}

	private I_MSV3_VerfuegbarkeitsantwortArtikel createRecord(
			@NonNull final StockAvailabilityResponseItem item,
			@NonNull ImmutableMap<StockAvailabilityResponseItem, MSV3ArtikelContextInfo> artikel2ContextInfo)
	{
		final I_MSV3_VerfuegbarkeitsantwortArtikel verfuegbarkeitsantwortArtikelRecord = newInstance(I_MSV3_VerfuegbarkeitsantwortArtikel.class);

		verfuegbarkeitsantwortArtikelRecord.setAD_Org_ID(orgId.getRepoId());
		verfuegbarkeitsantwortArtikelRecord.setMSV3_AnfrageMenge(item.getQty().getValueAsInt());
		verfuegbarkeitsantwortArtikelRecord.setMSV3_AnfragePzn(item.getPzn().getValueAsString());

		final I_MSV3_Substitution substitutionOrNull = Msv3SubstitutionDataPersister
				.newInstanceWithOrgId(orgId)
				.storeSubstitutionOrNull(item.getSubstitution());
		verfuegbarkeitsantwortArtikelRecord.setMSV3_VerfuegbarkeitSubstitution(substitutionOrNull);

		final MSV3ArtikelContextInfo availabilityRequestItem = artikel2ContextInfo.get(item);
		if (availabilityRequestItem != null)
		{
			verfuegbarkeitsantwortArtikelRecord.setC_OrderLineSO_ID(availabilityRequestItem.getSalesOrderLineId());
			verfuegbarkeitsantwortArtikelRecord.setC_PurchaseCandidate_ID(availabilityRequestItem.getPurchaseCandidateId());
		}

		return verfuegbarkeitsantwortArtikelRecord;
	}

	private I_MSV3_VerfuegbarkeitAnteil createRecord(@NonNull final StockAvailabilityResponseItemPart itemPart)
	{
		final I_MSV3_VerfuegbarkeitAnteil record = newInstance(I_MSV3_VerfuegbarkeitAnteil.class);

		record.setAD_Org_ID(orgId.getRepoId());
		record.setMSV3_Grund(itemPart.getReason().value());
		record.setMSV3_Lieferzeitpunkt(TimeUtil.asTimestamp(itemPart.getDeliveryDate()));
		record.setMSV3_Menge(itemPart.getQty().getValueAsInt());
		record.setMSV3_Tourabweichung(itemPart.isTourDeviation());
		record.setMSV3_Typ(itemPart.getType().value());

		return record;
	}

	public int getVerfuegbarkeitAnteilDataRecordId(@NonNull final StockAvailabilityResponseItemPart itemPart)
	{
		return verfuegbarkeitAnteil2DataRecordId.get(itemPart);
	}
}
