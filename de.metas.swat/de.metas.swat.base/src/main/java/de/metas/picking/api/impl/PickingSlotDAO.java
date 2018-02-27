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

import com.google.common.base.Predicates;
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
		return isWarehouseMatchingFilter(query.getWarehouseId())
				.and(isAvailableForBPartnerFilter(query.getAvailableForBPartnerId(), query.getAvailableForBPartnerLocationId()))
				.and(isAssignedToBPartnerFilter(query.getAssignedToBPartnerId(), query.getAssignedToBPartnerLocationId()))
				.and(isPickingSlotMatchingBarcodeFilter(query.getBarcode()));
	}

	private static final Predicate<I_M_PickingSlot> isWarehouseMatchingFilter(final int warehouseId)
	{
		if (warehouseId <= 0)
		{
			return Predicates.alwaysTrue();
		}

		return pickingSlot -> pickingSlot.getM_Warehouse_ID() == warehouseId;
	}

	private static final Predicate<I_M_PickingSlot> isAvailableForBPartnerFilter(final int bpartnerId, final int bpartnerLocationId)
	{
		final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
		return pickingSlot -> pickingSlotBL.isAvailableForBPartnerAndLocation(pickingSlot, bpartnerId, bpartnerLocationId);
	}

	private static final Predicate<I_M_PickingSlot> isAssignedToBPartnerFilter(final int bpartnerId, final int bpartnerLocationId)
	{
		if (bpartnerId <= 0)
		{
			return Predicates.alwaysTrue();
		}

		if (bpartnerLocationId <= 0)
		{
			return pickingSlot -> pickingSlot.getC_BPartner_ID() == bpartnerId;
		}
		else
		{
			return pickingSlot -> pickingSlot.getC_BPartner_ID() == bpartnerId && pickingSlot.getC_BPartner_Location_ID() == bpartnerLocationId;
		}
	}

	private static final Predicate<I_M_PickingSlot> isPickingSlotMatchingBarcodeFilter(final String barcode)
	{
		if (barcode == null)
		{
			return Predicates.alwaysTrue();
		}

		final String barcodeNorm = barcode.trim();
		if (barcodeNorm.isEmpty())
		{
			return Predicates.alwaysTrue();
		}

		return pickingSlot -> Objects.equals(pickingSlot.getPickingSlot(), barcode);
	}
}
