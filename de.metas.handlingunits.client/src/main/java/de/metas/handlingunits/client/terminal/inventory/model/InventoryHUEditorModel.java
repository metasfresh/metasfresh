package de.metas.handlingunits.client.terminal.inventory.model;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.movement.api.IHUMovementBL;

/**
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
public class InventoryHUEditorModel extends HUEditorModel
{

	/**
	 * M_InOut SO
	 */
	private static final int WINDOW_CUSTOMER_RETURN = 53097; // FIXME: HARDCODED

	/**
	 * M_InOut PO
	 */
	private static final int WINDOW_RETURN_TO_VENDOR = 53098; // FIXME: HARDCODED

	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	private final I_M_Warehouse _warehouse;

	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

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
