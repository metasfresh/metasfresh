package de.metas.fresh.picking;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.text.MessageFormat;

/*
 * #%L
 * de.metas.fresh.base
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.i18n.IMsgBL;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.IPickingSlotDAO.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import lombok.NonNull;

public class PickingSlotKeyBuilder
{
	private final ITerminalContext terminalContext;

	private final Map<Integer, PickingSlotKey> pickingSlotsKeys = new HashMap<>();

	/**
	 * Sort by PickingSlot string
	 */
	private static final Comparator<PickingSlotKey> pickingSlotKeysComparator = new AccessorComparator<>(
			ComparableComparator.<String> getInstance(),
			new TypedAccessor<String>()
			{

				@Override
				public String getValue(Object o)
				{
					if (o == null)
					{
						return "";
					}
					final PickingSlotKey pickingSlotKey = (PickingSlotKey)o;
					return pickingSlotKey.getM_PickingSlot().getPickingSlot();
				}
			});

	public PickingSlotKeyBuilder(final ITerminalContext terminalContext)
	{
		super();
		this.terminalContext = terminalContext;
	}

	public void addBPartner(final int bpartnerId, final int bpartnerLocationId, final Set<Integer> allowedWarehouseIds)
	{
		final PickingSlotQuery pickingSlotRequest = PickingSlotQuery.builder()
				.availableForBPartnerId(bpartnerId)
				.availableForBPartnerLocationId(bpartnerLocationId)
				.build();

		final List<I_M_PickingSlot> bpPickingSlots = Services.get(IPickingSlotDAO.class).retrievePickingSlots(pickingSlotRequest);
		assertPickingSlotsListNotEmpty(bpPickingSlots, pickingSlotRequest);

		for (final I_M_PickingSlot pickingSlot : bpPickingSlots)
		{
			addIfValid(pickingSlot, allowedWarehouseIds);
		}
	}
	
	private void assertPickingSlotsListNotEmpty(@NonNull final List<I_M_PickingSlot> result, @NonNull final PickingSlotQuery query)
	{
		if (!result.isEmpty())
		{
			return;
		}

		final int bpartnerId = query.getAvailableForBPartnerId();
		final int bpartnerLocationId = query.getAvailableForBPartnerLocationId();

		final I_C_BPartner bpartner = bpartnerId <= 0 ? null : loadOutOfTrx(bpartnerId, I_C_BPartner.class);
		final String bpartnerStr = bpartner == null ? "<" + bpartnerId + ">" : bpartner.getValue();

		final I_C_BPartner_Location bparterLocation = bpartnerLocationId <= 0 ? null : loadOutOfTrx(bpartnerLocationId, I_C_BPartner_Location.class);
		final String bpartnerLocationStr = bparterLocation == null ? "<" + bpartnerLocationId + ">" : bparterLocation.getAddress();

		final String translatedErrMsgWithParams = Services.get(IMsgBL.class).parseTranslation(Env.getCtx(), "@PickingSlot_NotFoundFor_PartnerAndLocation@");

		final String exceptionMessage = MessageFormat.format(translatedErrMsgWithParams, bpartnerStr, bpartnerLocationStr);
		throw new AdempiereException(exceptionMessage)
				.setParameter("query", query);
	}


	private void addIfValid(
			@NonNull final I_M_PickingSlot pickingSlot,
			@NonNull final Set<Integer> allowedWarehouseIds)
	{
		if (!pickingSlot.isActive())
		{
			return;
		}

		final int pickingSlotId = pickingSlot.getM_PickingSlot_ID();
		if (pickingSlotsKeys.containsKey(pickingSlotId))
		{
			return; // already added
		}

		// Filter by warehouse
		if (allowedWarehouseIds != null && !allowedWarehouseIds.isEmpty())
		{
			final int warehouseId = pickingSlot.getM_Warehouse_ID();
			if (!allowedWarehouseIds.contains(warehouseId))
			{
				return; // skip because it's not in our list of accepted warehouses
			}
		}

		final PickingSlotKey pickingSlotKey = new PickingSlotKey(terminalContext, pickingSlot);
		pickingSlotsKeys.put(pickingSlotId, pickingSlotKey);
	}

	public List<PickingSlotKey> getPickingSlotKeys()
	{
		if (pickingSlotsKeys.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<PickingSlotKey> result = new ArrayList<>(pickingSlotsKeys.values());
		Collections.sort(result, pickingSlotKeysComparator);

		return result;
	}
}
