package de.metas.picking.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.i18n.IMsgBL;
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
				.orderBy()
				.addColumn(I_M_PickingSlot.COLUMNNAME_PickingSlot).endOrderBy()
				.create()
				.list(I_M_PickingSlot.class);
	}

	@Override
	public List<I_M_PickingSlot> retrivePickingSlots(@NonNull final PickingSlotQuery request)
	{
		final List<I_M_PickingSlot> pickingSlotsAll = retrievePickingSlots(Env.getCtx(), ITrx.TRXNAME_None);

		final List<I_M_PickingSlot> result = filter(pickingSlotsAll, request);

		assertResultNotEmpty(result, request);

		return result;
	}

	private List<I_M_PickingSlot> filter(final List<I_M_PickingSlot> pickingSlotsAll, final PickingSlotQuery request)
	{
		final int bpartnerId = request.getBpartnerId();
		final int bpartnerLocationId = request.getBpartnerLocationId();

		final Predicate<I_M_PickingSlot> warehouseFilter = ps -> request.getWarehouseId() <= 0 || request.getWarehouseId() == ps.getM_Warehouse_ID();
		final Predicate<I_M_PickingSlot> partnerFilter = ps -> Services.get(IPickingSlotBL.class).isAvailableForBPartnerAndLocation(ps, bpartnerId, bpartnerLocationId);

		final List<I_M_PickingSlot> result = pickingSlotsAll.stream()
				.filter(warehouseFilter)
				.filter(partnerFilter)
				.collect(Collectors.toList());
		return result;
	}

	private void assertResultNotEmpty(
			@NonNull final List<I_M_PickingSlot> result,
			@NonNull final PickingSlotQuery request)
	{
		if (!result.isEmpty())
		{
			return;
		}

		final int bpartnerId = request.getBpartnerId();
		final int bpartnerLocationId = request.getBpartnerLocationId();

		final I_C_BPartner bpartner = bpartnerId <= 0 ? null : loadOutOfTrx(bpartnerId, I_C_BPartner.class);
		final String bpartnerStr = bpartner == null ? "<" + bpartnerId + ">" : bpartner.getValue();

		final I_C_BPartner_Location bparterLocation = bpartnerLocationId <= 0 ? null : loadOutOfTrx(bpartnerLocationId, I_C_BPartner_Location.class);
		final String bpartnerLocationStr = bparterLocation == null ? "<" + bpartnerLocationId + ">" : bparterLocation.getAddress();

		final String translatedErrMsgWithParams = Services.get(IMsgBL.class).parseTranslation(Env.getCtx(), "@PickingSlot_NotFoundFor_PartnerAndLocation@");

		final String exceptionMessage = MessageFormat.format(translatedErrMsgWithParams, bpartnerStr, bpartnerLocationStr);
		throw new AdempiereException(exceptionMessage);
	}

}
