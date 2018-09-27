/**
 *
 */
package de.metas.fresh.picking.form;

import com.google.common.collect.ImmutableList;

import de.metas.fresh.picking.PackingDetailsModel;
import de.metas.fresh.picking.form.swing.FreshSwingPickingOKPanel;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.terminal.form.swing.AbstractPackageDataPanel;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminal;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;
import lombok.NonNull;

/**
 * Packing window (second window)
 *
 * @author cg
 *
 */
public class FreshSwingPackageTerminal extends AbstractPackageTerminal
{
	private final PackingDetailsModel packingDetailsModel;
	private PackingItemsMap packingItems;

	public FreshSwingPackageTerminal(final FreshSwingPickingOKPanel freshSwingPickingOKPanel, final PackingDetailsModel packingDetailsModel)
	{
		super(freshSwingPickingOKPanel);
		
		this.packingDetailsModel = packingDetailsModel;

		// get packing items
		final ImmutableList<IPackingItem> unallocatedLines = packingDetailsModel.getUnallocatedLines();
		packingItems = PackingItemsMap.ofUnpackedItems(unallocatedLines);
	}

	public final PackingItemsMap getPackingItems()
	{
		return packingItems;
	}

	public void setPackingItems(@NonNull PackingItemsMap packingItems)
	{
		this.packingItems = packingItems;
	}

	@Override
	public AbstractPackageTerminalPanel createPackageTerminalPanel()
	{
		final FreshSwingPackageTerminalPanel packageTerminalPanel = new FreshSwingPackageTerminalPanel(getTerminalContext(), this);

		final AbstractPackageDataPanel packageDataPanel = packageTerminalPanel.getPickingData();
		packageDataPanel.validateModel();

		return packageTerminalPanel;
	}

	public static FreshSwingPackageTerminal cast(final AbstractPackageTerminal packageTerminal)
	{
		return (FreshSwingPackageTerminal)packageTerminal;
	}

	public PackingDetailsModel getPackingDetailsModel()
	{
		return packingDetailsModel;
	}
}
