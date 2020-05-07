/**
 * 
 */
package de.metas.fresh.picking;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModelAware;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.form.SwingPackingTerminalPanel;
import de.metas.picking.service.PackingItemsMap;

/**
 * @author cg
 * 
 */
public class ProductKeyLayout extends KeyLayout implements IKeyLayoutSelectionModelAware
{
	private static final Color DEFAULT_Color = Color.GREEN;

	public ProductKeyLayout(final ITerminalContext tc)
	{
		super(tc);
		setColumns(3);
		setDefaultColor(DEFAULT_Color);

		//
		// Configure the selection model:
		final IKeyLayoutSelectionModel selectionModel = getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);
		selectionModel.setAllowMultipleSelection(false);
		selectionModel.setAutoSelectIfOnlyOne(false); // let the user select it
		selectionModel.setToggleableSelection(false);
	}

	@Override
	public String getId()
	{
		return "ProductLayout#" + 100;
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	public boolean isText()
	{
		return false;
	}

	private SwingPackingTerminalPanel getPackingTerminalPanel()
	{
		return SwingPackingTerminalPanel.cast(getBasePanel());
	}

	private PackingItemsMap getPackingItems()
	{
		return getPackingTerminalPanel().getPackingItems();
	}

	private PickingSlotKey getSelectedPickingSlotKey()
	{
		return getPackingTerminalPanel().getSelectedPickingSlotKey();
	}

	@Override
	public List<ITerminalKey> createKeys()
	{
		final List<ProductKey> productKeys = ProductKeyFactory.builder()
				.terminalContext(getTerminalContext())
				.packingItems(getPackingItems())
				.selectedPickingSlot(getSelectedPickingSlotKey())
				.build()
				.create();

		return ImmutableList.copyOf(productKeys);
	}
}
