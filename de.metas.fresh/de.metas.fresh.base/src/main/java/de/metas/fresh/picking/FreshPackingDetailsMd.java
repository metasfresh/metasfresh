package de.metas.fresh.picking;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.time.SystemTime;

import de.metas.adempiere.form.IPackingDetailsModel;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.PackingTreeModel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.model.I_M_PickingSlot;

/**
 * 
 * @author cg
 */
public class FreshPackingDetailsMd implements IPackingDetailsModel
{
	private final transient WeakPropertyChangeSupport pcs;

	private final Collection<IPackingItem> unallocatedLines;

	private final List<I_M_ShipmentSchedule> nonItemScheds;
	private final List<PickingSlotKey> availablePickingSlots;
	private final List<PackingMaterialKey> availablePackingMaterialKeys;

	public FreshPackingDetailsMd(final ITerminalContext terminalContext,
			final Collection<IPackingItem> unallocatedLines,
			final List<I_M_ShipmentSchedule> nonItemScheds)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");

		this.pcs = terminalContext.createPropertyChangeSupport(this);

		Check.assumeNotNull(unallocatedLines, "unallocatedLines not null");
		Check.assume(!unallocatedLines.isEmpty(), "unallocatedLines not empty");
		this.unallocatedLines = Collections.unmodifiableCollection(new ArrayList<IPackingItem>(unallocatedLines));

		Check.assumeNotNull(nonItemScheds, "nonItemScheds not null");
		this.nonItemScheds = nonItemScheds;

		final Date date = SystemTime.asDayTimestamp();
		final PackingMaterialKeyBuilder packingMaterialKeysBuilder = new PackingMaterialKeyBuilder(terminalContext, date);
		final PickingSlotKeyBuilder pickingSlotKeysBuilder = new PickingSlotKeyBuilder(terminalContext);
		for (final IPackingItem pi : unallocatedLines)
		{
			final IFreshPackingItem freshPackingItem = FreshPackingItemHelper.cast(pi);

			final int productId = freshPackingItem.getProductId();
			final int bpartnerId = freshPackingItem.getC_BPartner_ID();
			final int bpartnerLocationId = freshPackingItem.getC_BPartner_Location_ID();
			final Set<Integer> warehouseIds = freshPackingItem.getWarehouseIds();

			//
			// Get/Create PackingMaterialKeys
			packingMaterialKeysBuilder.addProduct(productId, bpartnerId, bpartnerLocationId);

			//
			// Get PickingSlots
			pickingSlotKeysBuilder.addBPartner(bpartnerId, bpartnerLocationId, warehouseIds);
		}

		this.availablePickingSlots = pickingSlotKeysBuilder.getPickingSlotKeys();
		if (availablePickingSlots.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @" + I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID + "@");
		}

		//
		// Build available packing materials
		this.availablePackingMaterialKeys = packingMaterialKeysBuilder.getPackingMaterialKeys();
		if (availablePackingMaterialKeys.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_HU_PI_ID@");
		}
	}

	/**
	 * 
	 * @return unallocated lines (read-only collection)
	 */
	public Collection<IPackingItem> getUnallocatedLines()
	{
		return unallocatedLines;
	}

	public List<PackingMaterialKey> getAvailablePackingMaterialKeys()
	{
		return Collections.unmodifiableList(availablePackingMaterialKeys);
	}

	public List<PickingSlotKey> getAvailablePickingSlots()
	{
		return Collections.unmodifiableList(availablePickingSlots);
	}

	public void addPropertyChangeListener(final PropertyChangeListener l)
	{
		pcs.addPropertyChangeListener(l);
	}

	@Override
	public List<I_M_ShipmentSchedule> getNonItems()
	{
		return nonItemScheds;
	}

	@Override
	public int getValidState()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSelectedShipper()
	{
		return 0;
	}

	@Override
	public PackingTreeModel getPackingTreeModel()
	{
		return null;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
	
	
}
