package de.metas.handlingunits.client.terminal.inventory.model;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;

/**
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
public class InventoryHUEditorModel extends HUEditorModel
{

	private final I_M_Warehouse _warehouse;

	public InventoryHUEditorModel(final ITerminalContext terminalContext, final int warehouseId)
	{
		super(terminalContext);

		_warehouse = InterfaceWrapperHelper.create(terminalContext.getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(_warehouse, "warehouse not null");
	}

	public I_M_Warehouse getM_Warehouse()
	{
		return _warehouse;
	}

}
