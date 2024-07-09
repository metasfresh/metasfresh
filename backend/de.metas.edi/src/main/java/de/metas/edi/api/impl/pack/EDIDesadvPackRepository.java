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

		desadvPackRecord.setAD_Org_ID(createEDIDesadvPackRequest.getOrgId().getRepoId());
		desadvPackRecord.setEDI_Desadv_ID(EDIDesadvId.toRepoId(createEDIDesadvPackRequest.getEdiDesadvId()));

		desadvPackRecord.setM_HU_ID(HuId.toRepoId(createEDIDesadvPackRequest.getHuId()));
		desadvPackRecord.setIPA_SSCC18(createEDIDesadvPackRequest.getSscc18());
		desadvPackRecord.setIsManual_IPA_SSCC18(createEDIDesadvPackRequest.getIsManualIpaSSCC());
		desadvPackRecord.setM_HU_PackagingCode_LU_ID(PackagingCodeId.toRepoId(createEDIDesadvPackRequest.getHuPackagingCodeLUID()));
		desadvPackRecord.setGTIN_LU_PackingMaterial(createEDIDesadvPackRequest.getGtinLUPackingMaterial());

		saveRecord(desadvPackRecord);

		final ImmutableList.Builder<I_EDI_Desadv_Pack_Item> createdEDIDesadvPackItemRecordsBuilder = ImmutableList.builder();
		for (final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest createEDIDesadvPackItemRequest : createEDIDesadvPackRequest.getCreateEDIDesadvPackItemRequests())
		{
			final I_EDI_Desadv_Pack_Item ediDesadvPackItemRecord = InterfaceWrapperHelper.loadOrNew(createEDIDesadvPackItemRequest.getEdiDesadvPackItemId(),
																									I_EDI_Desadv_Pack_Item.class);

			ediDesadvPackItemRecord.setEDI_Desadv_Pack_ID(desadvPackRecord.getEDI_Desadv_Pack_ID());
			ediDesadvPackItemRecord.setEDI_DesadvLine_ID(EDIDesadvLineId.toRepoId(createEDIDesadvPackItemRequest.getEdiDesadvLineId()));
			ediDesadvPackItemRecord.setM_InOut_ID(InOutId.toRepoId(createEDIDesadvPackItemRequest.getInOutId()));
			ediDesadvPackItemRecord.setM_InOutLine_ID(InOutLineId.toRepoId(createEDIDesadvPackItemRequest.getInOutLineId()));
			ediDesadvPackItemRecord.setQtyItemCapacity(createEDIDesadvPackItemRequest.getQtyItemCapacity());
			ediDesadvPackItemRecord.setQtyTU(NumberUtils.asIntOrZero(createEDIDesadvPackItemRequest.getQtyTu()));
			ediDesadvPackItemRecord.setMovementQty(createEDIDesadvPackItemRequest.getMovementQtyInStockUOM());
			ediDesadvPackItemRecord.setQtyCUsPerTU(createEDIDesadvPackItemRequest.getQtyCUsPerTU());
			ediDesadvPackItemRecord.setQtyCUsPerTU_InInvoiceUOM(createEDIDesadvPackItemRequest.getQtyCUPerTUinInvoiceUOM());
			ediDesadvPackItemRecord.setQtyCUsPerLU(createEDIDesadvPackItemRequest.getQtyCUsPerLU());
			ediDesadvPackItemRecord.setQtyCUsPerLU_InInvoiceUOM(createEDIDesadvPackItemRequest.getQtyCUsPerLUinInvoiceUOM());
			ediDesadvPackItemRecord.setBestBeforeDate(createEDIDesadvPackItemRequest.getBestBeforeDate());
			ediDesadvPackItemRecord.setLotNumber(createEDIDesadvPackItemRequest.getLotNumber());
			ediDesadvPackItemRecord.setM_HU_PackagingCode_TU_ID(PackagingCodeId.toRepoId(createEDIDesadvPackItemRequest.getHuPackagingCodeTUID()));
			ediDesadvPackItemRecord.setGTIN_TU_PackingMaterial(createEDIDesadvPackItemRequest.getGtinTUPackingMaterial());

			saveRecord(ediDesadvPackItemRecord);

			createdEDIDesadvPackItemRecordsBuilder.add(ediDesadvPackItemRecord);
		}

		return ofRecord(desadvPackRecord, createdEDIDesadvPackItemRecordsBuilder.build());
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

		for (final EDIDesadvPackId ediDesadvPackId : packIdsToDeleteIfEmpty)
		{
			final List<I_EDI_Desadv_Pack_Item> packItemRecords = retrievePackItemRecords(EdiDesadvPackItemQuery.ofDesadvPackId(ediDesadvPackId));

			if (packItemRecords.isEmpty())
			{
				deletePackById(ediDesadvPackId);
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
		final ImmutableList.Builder<EDIDesadvPack> ediDesadvPackBuilder = ImmutableList.builder();

		queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_DesadvLine_ID, ediDesadvLineId)
				.create()
				.stream()
				.collect(Collectors.groupingBy(I_EDI_Desadv_Pack_Item::getEDI_Desadv_Pack_ID))
				.forEach((packId, itemList) -> {

					final I_EDI_Desadv_Pack desadvPackRecord = InterfaceWrapperHelper.load(packId, I_EDI_Desadv_Pack.class);
					final EDIDesadvPack ediDesadvPack = ofRecord(desadvPackRecord, itemList);

					ediDesadvPackBuilder.add(ediDesadvPack);
				});

		return ediDesadvPackBuilder.build();
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
			@NonNull final I_EDI_Desadv_Pack desadvPackRecord,
			@NonNull final List<I_EDI_Desadv_Pack_Item> ediDesadvPackItemRecords)
	{
		final EDIDesadvPack.EDIDesadvPackBuilder ediDesadvPackBuilder = EDIDesadvPack.builder();

		final EDIDesadvPackId ediDesadvPackId = EDIDesadvPackId.ofRepoId(desadvPackRecord.getEDI_Desadv_Pack_ID());

		ediDesadvPackBuilder.ediDesadvPackId(ediDesadvPackId);
		ediDesadvPackBuilder.ediDesadvId(EDIDesadvId.ofRepoId(desadvPackRecord.getEDI_Desadv_ID()));
		ediDesadvPackBuilder.sscc18(desadvPackRecord.getIPA_SSCC18());
		ediDesadvPackBuilder.isManualIpaSscc(desadvPackRecord.isManual_IPA_SSCC18());
		ediDesadvPackBuilder.huId(HuId.ofRepoIdOrNull(desadvPackRecord.getM_HU_ID()));
		ediDesadvPackBuilder.huPackagingCodeLuId(PackagingCodeId.ofRepoIdOrNull(desadvPackRecord.getM_HU_PackagingCode_LU_ID()));
		ediDesadvPackBuilder.gtinLuPackingMaterial(desadvPackRecord.getGTIN_LU_PackingMaterial());

		for (final I_EDI_Desadv_Pack_Item ediDesadvPackItemRecord : ediDesadvPackItemRecords)
		{
			final EDIDesadvPack.EDIDesadvPackItem.EDIDesadvPackItemBuilder ediDesadvPackItemBuilder = EDIDesadvPack.EDIDesadvPackItem.builder();

			ediDesadvPackItemBuilder.ediDesadvPackItemId(EDIDesadvPackItemId.ofRepoId(ediDesadvPackItemRecord.getEDI_Desadv_Pack_Item_ID()));
			ediDesadvPackItemBuilder.ediDesadvPackId(ediDesadvPackId);
			ediDesadvPackItemBuilder.ediDesadvLineId(EDIDesadvLineId.ofRepoId(ediDesadvPackItemRecord.getEDI_DesadvLine_ID()));
			ediDesadvPackItemBuilder.inOutId(InOutId.ofRepoIdOrNull(ediDesadvPackItemRecord.getM_InOut_ID()));
			ediDesadvPackItemBuilder.inOutLineId(InOutLineId.ofRepoIdOrNull(ediDesadvPackItemRecord.getM_InOutLine_ID()));
			ediDesadvPackItemBuilder.qtyItemCapacity(ediDesadvPackItemRecord.getQtyItemCapacity());
			ediDesadvPackItemBuilder.qtyTu(ediDesadvPackItemRecord.getQtyTU());
			ediDesadvPackItemBuilder.movementQty(ediDesadvPackItemRecord.getMovementQty());
			ediDesadvPackItemBuilder.qtyCUsPerTU(ediDesadvPackItemRecord.getQtyCUsPerTU());
			ediDesadvPackItemBuilder.qtyCUPerTUinInvoiceUOM(ediDesadvPackItemRecord.getQtyCUsPerTU_InInvoiceUOM());
			ediDesadvPackItemBuilder.qtyCUsPerLU(ediDesadvPackItemRecord.getQtyCUsPerLU());
			ediDesadvPackItemBuilder.qtyCUsPerLUinInvoiceUOM(ediDesadvPackItemRecord.getQtyCUsPerLU_InInvoiceUOM());
			ediDesadvPackItemBuilder.bestBeforeDate(ediDesadvPackItemRecord.getBestBeforeDate());
			ediDesadvPackItemBuilder.lotNumber(ediDesadvPackItemRecord.getLotNumber());
			ediDesadvPackItemBuilder.huPackagingCodeTuId(PackagingCodeId.ofRepoIdOrNull(ediDesadvPackItemRecord.getM_HU_PackagingCode_TU_ID()));
			ediDesadvPackItemBuilder.gtinTuPackingMaterial(ediDesadvPackItemRecord.getGTIN_TU_PackingMaterial());

			ediDesadvPackBuilder.ediDesadvPackItem(ediDesadvPackItemBuilder.build());
		}

		return ediDesadvPackBuilder.build();
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
