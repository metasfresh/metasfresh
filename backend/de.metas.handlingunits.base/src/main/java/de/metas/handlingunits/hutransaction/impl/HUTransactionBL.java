package de.metas.handlingunits.hutransaction.impl;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.hutransaction.IHUTransactionBL;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Optional;

public class HUTransactionBL implements IHUTransactionBL
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public IHUTransactionCandidate createLUTransactionForAttributeTransfer(
			@NonNull final I_M_HU luHU,
			@NonNull final I_M_HU_PI_Item luItemPI,
			@NonNull final IAllocationRequest request)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU_Item luItem = handlingUnitsDAO.retrieveItemIfExists(luHU, luItemPI).orElse(null);

		return HUTransactionCandidate.builder()
				.model(AllocationUtils.getReferencedModel(request))
				.huItem(luItem)
				.vhuItem(null)
				.productId(request.getProductId())
				.quantity(request.getQuantity().toZero())
				.date(request.getDate())
				.build();
	}

	@Override
	public boolean isLatestHUTrx(@NonNull final HuId huId, @NonNull final TableRecordReference tableRecordReference)
	{
		final TableRecordReference recordRefOfLastTrx = getRecordRefOfLastTrx(huId).orElse(null);
		return TableRecordReference.equals(tableRecordReference, recordRefOfLastTrx);
	}

	private Optional<TableRecordReference> getRecordRefOfLastTrx(final @NonNull HuId huId)
	{
		final I_M_HU_Trx_Line lastQtyChangeHUTrx = queryBL
				.createQueryBuilder(I_M_HU_Trx_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_M_HU_ID, huId)
				.addNotEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_Qty, 0)
				.orderByDescending(I_M_HU_Trx_Line.COLUMN_M_HU_Trx_Line_ID)
				.create()
				.first();

		if (lastQtyChangeHUTrx == null)
		{
			return Optional.empty();
		}

		final int counterpartTrxLineId = lastQtyChangeHUTrx.getParent_HU_Trx_Line_ID();
		if (counterpartTrxLineId <= 0)
		{
			// those are some very old trx
			return Optional.empty();
		}

		final I_M_HU_Trx_Line counterpartTrxLine = InterfaceWrapperHelper.load(counterpartTrxLineId, I_M_HU_Trx_Line.class);
		final TableRecordReference recordRef = TableRecordReference.ofOrNull(counterpartTrxLine.getAD_Table_ID(), counterpartTrxLine.getRecord_ID());
		return Optional.ofNullable(recordRef);
	}
}
