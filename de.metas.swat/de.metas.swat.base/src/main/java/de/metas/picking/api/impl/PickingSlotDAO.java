package de.metas.picking.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.i18n.IMsgBL;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;

public class PickingSlotDAO implements IPickingSlotDAO
{
	@Override
	@Cached(cacheName = I_M_PickingSlot.Table_Name)
	public List<I_M_PickingSlot> retrievePickingSlots(final @CacheCtx Properties ctx, final @CacheTrx String trxName)
	{
		final IQueryBuilder<I_M_PickingSlot> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot.class, ctx, trxName);

		queryBuilder.orderBy()
				.addColumn(I_M_PickingSlot.COLUMNNAME_PickingSlot);

		return queryBuilder
				.create()
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list(I_M_PickingSlot.class);
	}

	@Override
	public List<I_M_PickingSlot> retrivePickingSlotsForBPartner(final Properties ctx, final int bpartnerId, final int bpartnerLocationId)
	{
		final List<I_M_PickingSlot> pickingSlotsAll = retrievePickingSlots(ctx, ITrx.TRXNAME_None);

		final List<I_M_PickingSlot> result = new ArrayList<I_M_PickingSlot>();
		for (final I_M_PickingSlot pickingSlot : pickingSlotsAll)
		{
			if (!Services.get(IPickingSlotBL.class).isAvailableForBPartnerAndLocation(pickingSlot, bpartnerId, bpartnerLocationId))
			{
				continue;
			}

			result.add(pickingSlot);
		}

		if (result.isEmpty())
		{
			final I_C_BPartner bpartner = bpartnerId <= 0 ? null : InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
			final String bpartnerStr = bpartner == null ? "<" + bpartnerId + ">" : bpartner.getValue();

			final I_C_BPartner_Location bparterLocation = bpartnerLocationId <= 0 ? null : InterfaceWrapperHelper.create(ctx, bpartnerLocationId, I_C_BPartner_Location.class, ITrx.TRXNAME_None);
			final String bpartnerLocationStr = bparterLocation == null ? "<" + bpartnerLocationId + ">" : bparterLocation.getAddress();

			final String translatedErrMsgWithParams = Services.get(IMsgBL.class).parseTranslation(ctx, "@PickingSlot_NotFoundFor_PartnerAndLocation@");

			final String exceptionMessage = MessageFormat.format(translatedErrMsgWithParams, bpartnerStr, bpartnerLocationStr);
			throw new AdempiereException(exceptionMessage);
		}

		return result;
	}

	@Override
	public List<I_M_PickingSlot> retrivePickingSlotsForBPartners(final Properties ctx, final Collection<Integer> bpartnerIds, final Collection<Integer> bpLocIds)
	{
		final IQueryBuilder<I_M_PickingSlot> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PickingSlot.class, ctx, ITrx.TRXNAME_None)
				.filter(new InArrayQueryFilter<I_M_PickingSlot>(I_M_PickingSlot.COLUMNNAME_C_BPartner_ID, bpartnerIds))
				.filter(new InArrayQueryFilter<I_M_PickingSlot>(I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID, bpLocIds));

		queryBuilder.orderBy()
				.addColumn(I_M_PickingSlot.COLUMNNAME_PickingSlot);

		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.list(I_M_PickingSlot.class);
	}

}
