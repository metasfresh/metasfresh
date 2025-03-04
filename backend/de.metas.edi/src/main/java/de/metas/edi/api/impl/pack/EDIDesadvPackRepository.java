/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.edi.api.impl.pack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.EDIDesadvPackItemId;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class EDIDesadvPackRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public EDIDesadvPack createDesadvPack(@NonNull final CreateEDIDesadvPackRequest createEDIDesadvPackRequest)
	{
		final I_EDI_Desadv_Pack desadvPackRecord = newInstance(I_EDI_Desadv_Pack.class);
		desadvPackRecord.setSeqNo(createEDIDesadvPackRequest.getSeqNo());
		desadvPackRecord.setAD_Org_ID(createEDIDesadvPackRequest.getOrgId().getRepoId());
		desadvPackRecord.setEDI_Desadv_ID(EDIDesadvId.toRepoId(createEDIDesadvPackRequest.getEdiDesadvId()));
		desadvPackRecord.setSeqNo(createEDIDesadvPackRequest.getSeqNo());
		desadvPackRecord.setM_HU_ID(HuId.toRepoId(createEDIDesadvPackRequest.getHuId()));
		desadvPackRecord.setIPA_SSCC18(createEDIDesadvPackRequest.getSscc18());
		desadvPackRecord.setIsManual_IPA_SSCC18(createEDIDesadvPackRequest.getIsManualIpaSSCC());
		desadvPackRecord.setM_HU_PackagingCode_ID(PackagingCodeId.toRepoId(createEDIDesadvPackRequest.getHuPackagingCodeID()));
		desadvPackRecord.setGTIN_PackingMaterial(createEDIDesadvPackRequest.getGtinPackingMaterial());

		saveRecord(desadvPackRecord);
		final EDIDesadvPackId packId = EDIDesadvPackId.ofRepoId(desadvPackRecord.getEDI_Desadv_Pack_ID());

		final ImmutableList.Builder<I_EDI_Desadv_Pack_Item> createdEDIDesadvPackItemRecordsBuilder = ImmutableList.builder();
		for (final CreateEDIDesadvPackItemRequest createEDIDesadvPackItemRequest : createEDIDesadvPackRequest.getCreateEDIDesadvPackItemRequests())
	{
			final I_EDI_Desadv_Pack_Item packItemRecord = upsertPackItemRecord(createEDIDesadvPackItemRequest, packId);
			createdEDIDesadvPackItemRecordsBuilder.add(packItemRecord);
		}

		return ofRecord(desadvPackRecord, createdEDIDesadvPackItemRecordsBuilder.build());
	}

	@NonNull
	public EDIDesadvPack.EDIDesadvPackItem createDesadvPackItem(@NonNull final CreateEDIDesadvPackItemRequest createPackItemRequest)
	{
		final EDIDesadvPackId packId = Check.assumeNotNull(
				createPackItemRequest.getEdiDesadvPackId(),
				"When this method is called, the given request's ediDesadvPackId may not be null; request={}", createPackItemRequest);

		Check.assumeNull(
				createPackItemRequest.getEdiDesadvPackItemId(),
				"When this method is called, the given request's ediDesadvPackItemId has to be null; request={}", createPackItemRequest);

		final I_EDI_Desadv_Pack_Item packItemRecord = upsertPackItemRecord(createPackItemRequest, packId);
		return ofPackItemRecord(packItemRecord, packId);
	}

	private static @NonNull I_EDI_Desadv_Pack_Item upsertPackItemRecord(
			@NonNull final CreateEDIDesadvPackItemRequest createPackItemRequest,
			@NonNull final EDIDesadvPackId packId)
	{
		final I_EDI_Desadv_Pack_Item packItemRecord = InterfaceWrapperHelper.loadOrNew(createPackItemRequest.getEdiDesadvPackItemId(),
																					   I_EDI_Desadv_Pack_Item.class);
		packItemRecord.setEDI_Desadv_Pack_ID(packId.getRepoId());
		packItemRecord.setEDI_DesadvLine_ID(EDIDesadvLineId.toRepoId(createPackItemRequest.getEdiDesadvLineId()));
		packItemRecord.setM_InOut_ID(InOutId.toRepoId(createPackItemRequest.getInOutId()));
		packItemRecord.setM_InOutLine_ID(InOutLineId.toRepoId(createPackItemRequest.getInOutLineId()));
		packItemRecord.setLine(createPackItemRequest.getLine());
		packItemRecord.setQtyItemCapacity(createPackItemRequest.getQtyItemCapacity());
		packItemRecord.setQtyTU(NumberUtils.asIntOrZero(createPackItemRequest.getQtyTu()));
		packItemRecord.setMovementQty(createPackItemRequest.getMovementQtyInStockUOM());
		packItemRecord.setQtyCUsPerTU(createPackItemRequest.getQtyCUsPerTU());
		packItemRecord.setQtyCUsPerTU_InInvoiceUOM(createPackItemRequest.getQtyCUPerTUinInvoiceUOM());
		packItemRecord.setQtyCUsPerLU(createPackItemRequest.getQtyCUsPerLU());
		packItemRecord.setQtyCUsPerLU_InInvoiceUOM(createPackItemRequest.getQtyCUsPerLUinInvoiceUOM());
		packItemRecord.setBestBeforeDate(createPackItemRequest.getBestBeforeDate());
		packItemRecord.setLotNumber(createPackItemRequest.getLotNumber());
		packItemRecord.setM_HU_PackagingCode_TU_ID(PackagingCodeId.toRepoId(createPackItemRequest.getHuPackagingCodeTUID()));
		packItemRecord.setGTIN_TU_PackingMaterial(createPackItemRequest.getGtinTUPackingMaterial());

		saveRecord(packItemRecord);
		return packItemRecord;
	}

	public void deletePackById(@NonNull final EDIDesadvPackId ediDesadvPackId)
	{
		final I_EDI_Desadv_Pack desadvPackRecord = InterfaceWrapperHelper.load(ediDesadvPackId, I_EDI_Desadv_Pack.class);

		deleteRecord(desadvPackRecord);
	}

	public void removePackAndItemRecords(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final InOutLineId inOutLineId = InOutLineId.ofRepoId(inOutLineRecord.getM_InOutLine_ID());

		final List<I_EDI_Desadv_Pack_Item> packItems = retrievePackItemRecords(EdiDesadvPackItemQuery.ofInOutLineId(inOutLineId));

		final ImmutableSet.Builder<EDIDesadvPackId> packIdsCollector = ImmutableSet.builder();
		for (final I_EDI_Desadv_Pack_Item packItem : packItems)
		{
			packIdsCollector.add(EDIDesadvPackId.ofRepoId(packItem.getEDI_Desadv_Pack_ID()));
			delete(packItem);
		}

		final Set<EDIDesadvPackId> packIdsToDeleteIfEmpty = packIdsCollector.build();

		for (final EDIDesadvPackId packId : packIdsToDeleteIfEmpty)
		{
			final List<I_EDI_Desadv_Pack_Item> packItemRecords = retrievePackItemRecords(EdiDesadvPackItemQuery.ofDesadvPackId(packId));

			if (packItemRecords.isEmpty())
			{
				deletePackById(packId);
			}
		}
	}

	@Nullable
	public BPartnerId retrieveBPartnerFromEdiDesadvPack(@NonNull final EDIDesadvPackId ediDesadvPackId)
	{
		final I_EDI_Desadv desadv = queryBL
				.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.addEqualsFilter(I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_Pack_ID, ediDesadvPackId)
				.andCollect(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class)
				.create()
				.firstOnlyNotNull(I_EDI_Desadv.class);

		return BPartnerId.ofRepoIdOrNull(desadv.getC_BPartner_ID());
	}

	@NonNull
	public ImmutableList<EDIDesadvPack> getPacksByEDIDesadvLineId(@NonNull final EDIDesadvLineId ediDesadvLineId)
	{
		final Map<Integer, List<I_EDI_Desadv_Pack_Item>> packId2packItemRecords = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_DesadvLine_ID, ediDesadvLineId)
				.create()
				.stream()
				.collect(Collectors.groupingBy(I_EDI_Desadv_Pack_Item::getEDI_Desadv_Pack_ID));

		return ofPackIdAndItemRecords(packId2packItemRecords);
	}

	@Nullable
	public EDIDesadvPack getPackByDesadvLineAndHUId(@NonNull final HuId huId)
	{
		// with the recent changes, there can't be more than one pack per HU, but in old DESADVs there may be packs that share the same M_HU_ID,
		final I_EDI_Desadv_Pack packRecord = queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID, huId)
				.orderBy(I_EDI_Desadv_Pack.COLUMNNAME_SeqNo)
				.create()
				.first(I_EDI_Desadv_Pack.class);
		if (packRecord == null)
		{
			return null;
		}

		final List<I_EDI_Desadv_Pack_Item> packItemRecords = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID, packRecord.getEDI_Desadv_Pack_ID())
				.orderBy(I_EDI_Desadv_Pack_Item.COLUMNNAME_Line)
				.create()
				.list();

		return ofRecord(packRecord, packItemRecords);
	}

	private static ImmutableList<EDIDesadvPack> ofPackIdAndItemRecords(
			@NonNull final Map<Integer, List<I_EDI_Desadv_Pack_Item>> packId2packItemRecords)
	{
		final ImmutableList.Builder<EDIDesadvPack> packBuilder = ImmutableList.builder();
		for (final Map.Entry<Integer, List<I_EDI_Desadv_Pack_Item>> entry : packId2packItemRecords.entrySet())
		{
			final I_EDI_Desadv_Pack packRecord = InterfaceWrapperHelper.load(entry.getKey(), I_EDI_Desadv_Pack.class);
			final EDIDesadvPack pack = ofRecord(packRecord, entry.getValue());

			packBuilder.add(pack);
		}

		return packBuilder.build();
	}

	@NonNull
	public List<I_EDI_Desadv_Pack_Item> retrievePackItemRecords(@NonNull final EdiDesadvPackItemQuery query)
	{
		final IQueryBuilder<I_EDI_Desadv_Pack_Item> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class);

		if (query.getInOutLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID, query.getInOutLineId());
		}

		if (query.getEdiDesadvPackId() != null)
		{
			queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID, query.getEdiDesadvPackId());
		}

		if (query.getDesadvLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_DesadvLine_ID, query.getDesadvLineId());
		}

		if (query.getWithInOutLineId() != null)
		{
			if (query.getWithInOutLineId())
			{
				queryBuilder.addNotNull(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID);
			}
			else
			{
				queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID, null);
			}
		}

		return queryBuilder
				.create()
				.list();
	}

	@NonNull
	private static EDIDesadvPack ofRecord(
			@NonNull final I_EDI_Desadv_Pack packRecord,
			@NonNull final List<I_EDI_Desadv_Pack_Item> packItemRecords)
	{
		final EDIDesadvPack.EDIDesadvPackBuilder packBuilder = EDIDesadvPack.builder();

		final EDIDesadvPackId packId = EDIDesadvPackId.ofRepoId(packRecord.getEDI_Desadv_Pack_ID());

		packBuilder.seqNo(packRecord.getSeqNo());
		packBuilder.ediDesadvPackId(packId);
		packBuilder.ediDesadvId(EDIDesadvId.ofRepoId(packRecord.getEDI_Desadv_ID()));
		packBuilder.sscc18(packRecord.getIPA_SSCC18());
		packBuilder.isManualIpaSscc(packRecord.isManual_IPA_SSCC18());
		packBuilder.huId(HuId.ofRepoIdOrNull(packRecord.getM_HU_ID()));
		packBuilder.huPackagingCodeId(PackagingCodeId.ofRepoIdOrNull(packRecord.getM_HU_PackagingCode_ID()));
		packBuilder.gtinPackingMaterial(packRecord.getGTIN_PackingMaterial());

		for (final I_EDI_Desadv_Pack_Item packItemRecord : packItemRecords)
		{
			final EDIDesadvPack.EDIDesadvPackItem packItem = ofPackItemRecord(packItemRecord, packId);
			packBuilder.ediDesadvPackItem(packItem);
		}

		return packBuilder.build();
	}

	@NonNull
	private static EDIDesadvPack.EDIDesadvPackItem ofPackItemRecord(
			@NonNull final I_EDI_Desadv_Pack_Item ediDesadvPackItemRecord,
			@NonNull final EDIDesadvPackId packId)
	{
		final EDIDesadvPack.EDIDesadvPackItem.EDIDesadvPackItemBuilder packItemBuilder = EDIDesadvPack.EDIDesadvPackItem.builder();

		packItemBuilder.ediDesadvPackItemId(EDIDesadvPackItemId.ofRepoId(ediDesadvPackItemRecord.getEDI_Desadv_Pack_Item_ID()));
		packItemBuilder.ediDesadvPackId(packId);
		packItemBuilder.ediDesadvLineId(EDIDesadvLineId.ofRepoId(ediDesadvPackItemRecord.getEDI_DesadvLine_ID()));
		packItemBuilder.line(ediDesadvPackItemRecord.getLine());
		packItemBuilder.inOutId(InOutId.ofRepoIdOrNull(ediDesadvPackItemRecord.getM_InOut_ID()));
		packItemBuilder.inOutLineId(InOutLineId.ofRepoIdOrNull(ediDesadvPackItemRecord.getM_InOutLine_ID()));
		packItemBuilder.qtyItemCapacity(ediDesadvPackItemRecord.getQtyItemCapacity());
		packItemBuilder.qtyTu(ediDesadvPackItemRecord.getQtyTU());
		packItemBuilder.movementQty(ediDesadvPackItemRecord.getMovementQty());
		packItemBuilder.qtyCUsPerTU(ediDesadvPackItemRecord.getQtyCUsPerTU());
		packItemBuilder.qtyCUPerTUinInvoiceUOM(ediDesadvPackItemRecord.getQtyCUsPerTU_InInvoiceUOM());
		packItemBuilder.qtyCUsPerLU(ediDesadvPackItemRecord.getQtyCUsPerLU());
		packItemBuilder.qtyCUsPerLUinInvoiceUOM(ediDesadvPackItemRecord.getQtyCUsPerLU_InInvoiceUOM());
		packItemBuilder.bestBeforeDate(ediDesadvPackItemRecord.getBestBeforeDate());
		packItemBuilder.lotNumber(ediDesadvPackItemRecord.getLotNumber());
		packItemBuilder.huPackagingCodeTuId(PackagingCodeId.ofRepoIdOrNull(ediDesadvPackItemRecord.getM_HU_PackagingCode_TU_ID()));
		packItemBuilder.gtinTuPackingMaterial(ediDesadvPackItemRecord.getGTIN_TU_PackingMaterial());

		return packItemBuilder.build();
	}

	@Value
	public static class EdiDesadvPackItemQuery
	{
		@Nullable
		InOutLineId inOutLineId;

		@Nullable
		EDIDesadvPackId ediDesadvPackId;

		@Nullable
		EDIDesadvLineId desadvLineId;

		@Nullable
		Boolean withInOutLineId;

		@NonNull
		public static EdiDesadvPackItemQuery ofInOutLineId(@NonNull final InOutLineId inOutLineId)
		{
			return EdiDesadvPackItemQuery.builder()
					.inOutLineId(inOutLineId)
					.build();
		}

		@NonNull
		public static EdiDesadvPackItemQuery ofDesadvPackId(@NonNull final EDIDesadvPackId desadvPackId)
		{
			return EdiDesadvPackItemQuery.builder()
					.ediDesadvPackId(desadvPackId)
					.build();
		}

		@NonNull
		public static EdiDesadvPackItemQuery ofDesadvLineWithNoInOutLine(@NonNull final EDIDesadvLineId desadvLineId)
		{
			return EdiDesadvPackItemQuery.builder()
					.desadvLineId(desadvLineId)
					.withInOutLineId(false)
					.build();
		}

		@Builder
		private EdiDesadvPackItemQuery(
				@Nullable final InOutLineId inOutLineId,
				@Nullable final EDIDesadvPackId ediDesadvPackId,
				@Nullable final EDIDesadvLineId desadvLineId,
				@Nullable final Boolean withInOutLineId)
		{
			if (CoalesceUtil.coalesce(inOutLineId, ediDesadvPackId, desadvLineId, withInOutLineId) == null)
			{
				throw new AdempiereException("inOutLineId, ediDesadvPackId, desadvLineId && withInOutLineId cannot be all null!");
			}

			this.inOutLineId = inOutLineId;
			this.ediDesadvPackId = ediDesadvPackId;
			this.desadvLineId = desadvLineId;
			this.withInOutLineId = withInOutLineId;
		}
	}
}
