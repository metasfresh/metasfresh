/**
 *
 */
package de.metas.fresh.picking.form;

import java.awt.Dimension;

import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.PackingDetailsModel;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemsMap;
import lombok.NonNull;

/**
 * Packing window (second window)
 */
public class SwingPackingTerminal implements FormPanel
{
	private final ITerminalContext terminalContext;
	/** picking first window */
	private final SwingPickingOKPanel pickingOKPanel;
	/** Window content panel */
	private SwingPackingTerminalPanel panel = null;
	private FormFrame frame;

	private final PackingDetailsModel packingDetailsModel;

	private PackingItemsMap packingItems;

	private boolean disposing = false;

	public SwingPackingTerminal(@NonNull final SwingPickingOKPanel pickingOKPanel, final PackingDetailsModel packingDetailsModel)
	{
		this.terminalContext = pickingOKPanel.getTerminalContext();
		this.pickingOKPanel = pickingOKPanel;
		this.packingDetailsModel = packingDetailsModel;

		// get packing items
		final ImmutableList<IPackingItem> unallocatedLines = packingDetailsModel.getUnallocatedLines();
		packingItems = PackingItemsMap.ofUnpackedItems(unallocatedLines);
	}

	@Override
	public void init(final int windowNo, final FormFrame frame)
	{
		panel = new SwingPackingTerminalPanel(getTerminalContext(), this);
		panel.init(windowNo, frame);
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

	private ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public FormFrame getFrame()
	{
		return frame;
	}

	public FormFrame getPickingFrame()
	{
		if (pickingOKPanel == null)
		{
			return null;
		}

		return pickingOKPanel.getPickingFrame();
	}

	public SwingPickingOKPanel getPickingOKPanel()
	{
		return pickingOKPanel;
	}

	public final PackingItemsMap getPackingItems()
	{
		return packingItems;
	}

	public void setPackingItems(@NonNull PackingItemsMap packingItems)
	{
		this.packingItems = packingItems;
	}

	public PackingDetailsModel getPackingDetailsModel()
	{
		return packingDetailsModel;
	}
}
