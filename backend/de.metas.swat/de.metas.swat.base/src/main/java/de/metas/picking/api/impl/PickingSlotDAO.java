package de.metas.picking.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public class PickingSlotDAO implements IPickingSlotDAO
{
	@Override
	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		final I_M_PickingSlot record = getById(pickingSlotId);
		return toPickingSlotIdAndCaption(record);
	}

	@Override
	public Optional<PickingSlotIdAndCaption> getPickingSlotIdAndCaptionByCode(@NonNull final String pickingSlotCode)
	{
		return retrievePickingSlots(Env.getCtx(), ITrx.TRXNAME_None)
				.stream()
				.filter(pickingSlot -> pickingSlotCode.equals(pickingSlot.getPickingSlot()))
				.map(PickingSlotDAO::toPickingSlotIdAndCaption)
				.findFirst();
	}

	private static PickingSlotIdAndCaption toPickingSlotIdAndCaption(@NonNull final I_M_PickingSlot record)
	{
		return PickingSlotIdAndCaption.of(PickingSlotId.ofRepoId(record.getM_PickingSlot_ID()), record.getPickingSlot());
	}

	@Override
	public I_M_PickingSlot getById(final PickingSlotId pickingSlotId)
	{
		return getById(pickingSlotId, I_M_PickingSlot.class);
	}

	@Override
	public <T extends I_M_PickingSlot> T getById(@NonNull final PickingSlotId pickingSlotId, final Class<T> modelClass)
	{
		final T record = load(pickingSlotId, modelClass);
		if (record == null)
		{
			throw new AdempiereException("No picking slot found for " + pickingSlotId);
		}
		return record;
	}

	@Override
	@Cached(cacheName = I_M_PickingSlot.Table_Name)
	public List<I_M_PickingSlot> retrievePickingSlots(final @CacheCtx Properties ctx, final @CacheTrx @Nullable String trxName)
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
	public Set<PickingSlotId> retrievePickingSlotIds(@NonNull final PickingSlotQuery query)
	{
		return retrievePickingSlots(query)
				.stream()
				.map(record -> PickingSlotId.ofRepoId(record.getM_PickingSlot_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Set<PickingSlotIdAndCaption> retrievePickingSlotIdAndCaptions(@NonNull final PickingSlotQuery query)
	{
		return retrievePickingSlots(query)
				.stream()
				.map(PickingSlotDAO::toPickingSlotIdAndCaption)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void save(@NonNull final I_M_PickingSlot slot)
	{
		InterfaceWrapperHelper.save(slot);
	}

	private static Predicate<I_M_PickingSlot> toPredicate(final PickingSlotQuery query)
	{
		return pickingSlot -> isPickingSlotMatching(pickingSlot, query);
	}

	private static boolean isPickingSlotMatching(final I_M_PickingSlot pickingSlot, final PickingSlotQuery query)
	{
		// QR Code
		final PickingSlotQRCode qrCode = query.getQrCode();
		if (qrCode != null)
		{
			if (pickingSlot.getM_PickingSlot_ID() != qrCode.getPickingSlotId().getRepoId())
			{
				return false;
			}
		}

		if (query.getWarehouseId() != null && query.getWarehouseId().getRepoId() != pickingSlot.getM_Warehouse_ID())
		{
			return false;
		}

		final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
		if (!pickingSlotBL.isAvailableForBPartnerAndLocation(pickingSlot, query.getAvailableForBPartnerId(), query.getAvailableForBPartnerLocationId()))
		{
			return false;
		}

		// Check assigned BP
		final BPartnerId assignedToBPartnerId = query.getAssignedToBPartnerId();
		final BPartnerLocationId assignedToBPartnerLocationId = query.getAssignedToBPartnerLocationId();
		if (assignedToBPartnerId != null)
		{
			if (assignedToBPartnerId.getRepoId() != pickingSlot.getC_BPartner_ID())
			{
				return false;
			}
			if (assignedToBPartnerLocationId != null && assignedToBPartnerLocationId.getRepoId() != pickingSlot.getC_BPartner_Location_ID())
			{
				return false;
			}
		}

		//
		return true;
	}
}
