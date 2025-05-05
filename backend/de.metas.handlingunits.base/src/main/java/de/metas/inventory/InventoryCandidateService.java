package de.metas.inventory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.ad_reference.ADReferenceService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory_Candidate;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import de.metas.ad_reference.ADRefList;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class InventoryCandidateService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ADReferenceService adReferenceService;
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	public InventoryCandidateService(final ADReferenceService adReferenceService) {this.adReferenceService = adReferenceService;}

	public ADRefList getDisposalReasons()
	{
		return adReferenceService.getRefListById(QtyRejectedReasonCode.REFERENCE_ID);
	}

	public void createDisposeCandidates(
			@NonNull final HuId huId,
			@NonNull final QtyRejectedReasonCode reasonCode)
	{
		trxManager.runInThreadInheritedTrx(() -> createDisposeCandidatesInTrx(huId, reasonCode));
	}

	public void createDisposeCandidatesInTrx(
			@NonNull final HuId huId,
			@NonNull final QtyRejectedReasonCode reasonCode)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (!huStatusBL.isQtyOnHand(hu.getHUStatus()))
		{
			throw new AdempiereException("Invalid HU status: " + hu.getHUStatus());
		}

		final ImmutableMap<ProductId, I_M_Inventory_Candidate> existingRecords = Maps.uniqueIndex(
				queryByHuIdAndNotProcessed(huId).create().list(),
				record -> ProductId.ofRepoId(record.getM_Product_ID()));

		handlingUnitsBL.getStorageFactory()
				.getStorage(hu)
				.getProductStorages()
				.forEach(huProductStorage -> {
					final I_M_Inventory_Candidate existingRecord = existingRecords.get(huProductStorage.getProductId());
					createOrUpdateDisposeCandidate(huProductStorage, reasonCode, existingRecord);
				});
	}

	private void createOrUpdateDisposeCandidate(
			@NonNull final IHUProductStorage huProductStorage,
			@NonNull final QtyRejectedReasonCode reasonCode,
			@Nullable final I_M_Inventory_Candidate existingRecord)
	{
		final I_M_Inventory_Candidate record;
		if (existingRecord == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_M_Inventory_Candidate.class);
			record.setM_HU_ID(huProductStorage.getHuId().getRepoId());
			record.setM_Product_ID(huProductStorage.getProductId().getRepoId());
		}
		else
		{
			record = existingRecord;
		}

		final Quantity qty = huProductStorage.getQty();

		record.setC_UOM_ID(qty.getUomId().getRepoId());
		record.setQtyToDispose(qty.toBigDecimal());
		record.setIsWholeHU(true);
		record.setDisposeReason(reasonCode.getCode());

		InterfaceWrapperHelper.save(record);
	}

	private IQueryBuilder<I_M_Inventory_Candidate> queryByHuIdAndNotProcessed(final @NonNull HuId huId)
	{
		return queryBL.createQueryBuilder(I_M_Inventory_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Inventory_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Inventory_Candidate.COLUMNNAME_Processed, false);
	}

	public boolean isDisposalPending(final @NonNull HuId huId)
	{
		return queryByHuIdAndNotProcessed(huId)
				.addNotNull(I_M_Inventory_Candidate.COLUMNNAME_DisposeReason)
				.create()
				.anyMatch();
	}
}
