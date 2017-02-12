/**
 * 
 */
package de.metas.fresh.picking.form;

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


import java.math.BigDecimal;
import java.util.Collection;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.fresh.picking.FreshPackingDetailsMd;
import de.metas.fresh.picking.form.swing.FreshSwingPickingOKPanel;
import de.metas.picking.terminal.form.swing.AbstractPackageDataPanel;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminal;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;

/**
 * Packing window (second window)
 * 
 * @author cg
 * 
 */
public class FreshSwingPackageTerminal extends AbstractPackageTerminal
{
	public FreshSwingPackageTerminal(final FreshSwingPickingOKPanel freshSwingPickingOKPanel, final FreshPackingDetailsMd packingDetailsModel)
	{
		super(freshSwingPickingOKPanel, packingDetailsModel);

		createBoxes(packingDetailsModel);
	}

	@Override
	public void createBoxes(final Object model)
	{
		final FreshPackingDetailsMd freshModel = (FreshPackingDetailsMd)model;
		
		final PackingItemsMap packItems = new PackingItemsMap();

		// get packing items
		final Collection<IPackingItem> unallocatedLines = freshModel.getUnallocatedLines();
		packItems.addUnpackedItems(unallocatedLines);
		
		setPackingItems(packItems);
	}

	@Override
	public AbstractPackageTerminalPanel createPackageTerminalPanel()
	{
		final FreshSwingPackageTerminalPanel packageTerminalPanel = new FreshSwingPackageTerminalPanel(getTerminalContext(), this);
		
		final AbstractPackageDataPanel packageDataPanel = packageTerminalPanel.getPickingData();
		packageDataPanel.validateModel();
		
		return packageTerminalPanel;
	}

	@Override
	public BigDecimal getQtyUnpacked(IPackingItem pck)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
