package de.metas.handlingunits.client.terminal.pporder.receipt.model;

import org.adempiere.util.Check;
import org.eevolution.model.I_PP_Order;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;

/**
 * HUEditor which displays all HUs for Manufacturing Order's Warehouse and BPartner.
 *
 * User can do further editing and then move the HUs forward through DD Order Line (if any).
 *
 * @author tsa
 *
 */
public class HUPPOrderReceiptHUEditorModel extends HUEditorModel
{
	//
	// Parameters
	private final I_PP_Order ppOrder;
	// private final List<I_PP_Order_BOMLine> ppOrderBOMLines;

	/**
	 * This indicates the selected cuKey (in case there is any)
	 */
	private final CUKey cuKey;

	/**
	 * Initialize the PPOrder and the pp order bom lines
	 *
	 * @param terminalContext
	 * @param ppOrder
	 * @param ppOrderBOMLines
	 * @param loadHUs in case it's true, also load all the already existing HUs from the locator
	 */
	public HUPPOrderReceiptHUEditorModel(final ITerminalContext terminalContext, final I_PP_Order ppOrder, final CUKey cuKey)
	{
		super(terminalContext);

		Check.assumeNotNull(ppOrder, "ppOrder not null");
		this.ppOrder = ppOrder;

		// The selected cuKey shall be propagated for further processing
		this.cuKey = cuKey;
	}

	/** @return PP_Order; never returns null */
	public I_PP_Order getPP_Order()
	{
		return ppOrder;
	}

	public CUKey getCUKey()
	{
		return cuKey;
	}
}
