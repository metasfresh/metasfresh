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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.util.Env;

import de.metas.adempiere.form.IPackingDetailsModel;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.picking.terminal.PickingOKPanel;

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
	private Map<Integer, DefaultMutableTreeNode> boxes;
	private List<DefaultMutableTreeNode> availBoxes;

	public AbstractPackageTerminal(final SwingPickingOKPanel pickingOKPanel, final IPackingDetailsModel packingDetailsModel)
	{
		super();

		Check.assumeNotNull(pickingOKPanel, "pickingOKPanel not null");
		this.terminalContext = pickingOKPanel.getTerminalContext();
		this.pickingOKPanel = pickingOKPanel;

		Check.assumeNotNull(packingDetailsModel, "packingDetailsModel not null");
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

			Services.get(IShipmentSchedulePA.class) // 02217
					.deleteUnprocessedLocksForShipmentRun(0, Env.getAD_User_ID(Env.getCtx()), ITrx.TRXNAME_None);

			// NOTE: don't dispose it's parent panel
			// if (pickingOKPanel != null)
			// {
			// pickingOKPanel.dispose();
			// pickingOKPanel = null; // 05749: protect from future execution
			// }
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

	abstract public void createBoxes(Object model);

	/**
	 * gets the number of unpacked item fot a certain product
	 * 
	 * @param pck
	 * @return
	 */
	abstract public BigDecimal getQtyUnpacked(IPackingItem pck);

	public boolean isUnpacked(LegacyPackingItem pck)
	{
		BigDecimal qty = getQtyUnpacked(pck);
		if (qty.compareTo(BigDecimal.ZERO) > 0)
			return true;
		return false;
	}

	public final List<DefaultMutableTreeNode> getAvailableBoxes()
	{
		return availBoxes;
	}
	
	protected final void setAvailableBoxes(final List<DefaultMutableTreeNode> availBoxes)
	{
		this.availBoxes = availBoxes;
	}

	public final Map<Integer, DefaultMutableTreeNode> getBoxes()
	{
		return boxes;
	}
	
	protected final void setBoxes(Map<Integer, DefaultMutableTreeNode> boxes)
	{
		this.boxes = boxes;
	}

	abstract public AbstractPackageTerminalPanel createPackageTerminalPanel();
}
