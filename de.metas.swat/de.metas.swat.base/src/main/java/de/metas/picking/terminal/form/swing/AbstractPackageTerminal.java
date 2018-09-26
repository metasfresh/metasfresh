/**
 *
 */
package de.metas.picking.terminal.form.swing;

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

import java.awt.Dimension;
import java.math.BigDecimal;

import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.picking.legacy.form.IPackingDetailsModel;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.terminal.PickingOKPanel;
import lombok.NonNull;

/**
 * Packing window (second window)
 *
 * @author cg
 *
 */
public abstract class AbstractPackageTerminal implements FormPanel
{
	private final ITerminalContext terminalContext;

	/**
	 * i.e. picking first window
	 */
	private final PickingOKPanel pickingOKPanel;
	private final IPackingDetailsModel model;

	/**
	 * Window content panel
	 */
	private AbstractPackageTerminalPanel panel = null;

	public AbstractPackageTerminalPanel getPackageTerminalPanel()
	{
		return panel;
	}

	private PackingItemsMap packItems;

	public AbstractPackageTerminal(
			@NonNull final SwingPickingOKPanel pickingOKPanel,
			@NonNull final IPackingDetailsModel packingDetailsModel)
	{
		this.terminalContext = pickingOKPanel.getTerminalContext();
		this.pickingOKPanel = pickingOKPanel;
		this.model = packingDetailsModel;
	}

	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public PickingOKPanel getPickingOKPanel()
	{
		return pickingOKPanel;
	}

	protected IPackingDetailsModel getPackingDetailsModel()
	{
		return model;
	}

	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		panel = createPackageTerminalPanel();
		panel.init(WindowNo, frame);
		this.frame = frame;

		// Set second frame's dimension to be maximum of our resolution
		// see 05863 Fenster Kommissionierung - bessere Ausnutzung Knopfefelder fur Textausgabe (102244669218)
		frame.setMinimumSize(new Dimension(1024, 740));
		frame.setMaximumSize(new Dimension(1024, 740));

	}

	@Override
	public final void dispose()
	{
		if (disposing)
		{
			return;
		}

		disposing = true;
		try
		{
			if (panel != null)
			{
				panel.dispose();
				panel = null;
			}
			if (frame != null)
			{
				frame.dispose();
				frame = null;
			}
		}
		finally
		{
			disposing = false;
		}
	}

	private boolean disposing = false;

	private FormFrame frame;

	public FormFrame getFrame()
	{
		return frame;
	}

	/**
	 * Get Picking Window (first window)
	 *
	 * @return picking window
	 */
	public FormFrame getPickingFrame()
	{
		if (pickingOKPanel == null)
		{
			return null;
		}

		return (FormFrame)pickingOKPanel.getPickingFrame();
	}

	public final PackingItemsMap getPackingItems()
	{
		return packItems;
	}

	protected final void setPackingItems(PackingItemsMap packItems)
	{
		this.packItems = packItems;
	}

	/**
	 * gets the number of unpacked item fot a certain product
	 *
	 * @param pck
	 * @return
	 */
	abstract public BigDecimal getQtyUnpacked(IPackingItem pck);

	abstract public AbstractPackageTerminalPanel createPackageTerminalPanel();
}
