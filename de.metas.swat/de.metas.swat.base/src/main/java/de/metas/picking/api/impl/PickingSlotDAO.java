package de.metas.picking.api.impl;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;
import lombok.NonNull;

public class PickingSlotDAO implements IPickingSlotDAO
{
	@Override
	@Cached(cacheName = I_M_PickingSlot.Table_Name)
	public List<I_M_PickingSlot> retrievePickingSlots(final @CacheCtx Properties ctx, final @CacheTrx String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_M_PickingSlot.COLUMNNAME_PickingSlot)
				.create()
				.listImmutable(I_M_PickingSlot.class);
	}

	@Override
	public List<I_M_PickingSlot> retrievePickingSlots(@NonNull final PickingSlotQuery query)
	{
		return retrievePickingSlots(Env.getCtx(), ITrx.TRXNAME_None)
				.stream()
				.filter(toPredicate(query))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<Integer> retrievePickingSlotIds(@NonNull final PickingSlotQuery query)
	{
		return retrievePickingSlots(query)
				.stream()
				.map(I_M_PickingSlot::getM_PickingSlot_ID)
				.collect(ImmutableList.toImmutableList());
	}

	private static Predicate<I_M_PickingSlot> toPredicate(final PickingSlotQuery query)
	{
		return pickingSlot -> isPickingSlotMatching(pickingSlot, query);
	}

	private static boolean isPickingSlotMatching(final I_M_PickingSlot pickingSlot, final PickingSlotQuery query)
	{
		if (query.getWarehouseId() > 0 && query.getWarehouseId() != pickingSlot.getM_Warehouse_ID())
		{
			return false;
		}

		final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
		if (!pickingSlotBL.isAvailableForBPartnerAndLocation(pickingSlot, query.getAvailableForBPartnerId(), query.getAvailableForBPartnerLocationId()))
		{
			return false;
		}

		// Check assigned BP
		final int assignedToBPartnerId = query.getAssignedToBPartnerId();
		final int assignedToBPartnerLocationId = query.getAssignedToBPartnerLocationId();
		if (assignedToBPartnerId > 0)
		{
			if (assignedToBPartnerId != pickingSlot.getC_BPartner_ID())
			{
				return false;
			}
			if (assignedToBPartnerLocationId > 0 && assignedToBPartnerLocationId != pickingSlot.getC_BPartner_Location_ID())
			{
				return false;
			}
		}

		// Barcode
		final String barcode = query.getBarcode();
		if (barcode != null)
		{
			final String barcodeNorm = barcode.trim();
			if (!barcodeNorm.isEmpty() && !Objects.equals(pickingSlot.getPickingSlot(), barcode))
			{
				return false;
			}
		}

		//
		return true;
	}
}
