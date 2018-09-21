package de.metas.handlingunits.client.terminal.receipt.model;

import org.compiere.model.I_C_Order;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

/**
 * A terminal key that encapsulates a (purchase) order.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class PurchaseOrderKey extends TerminalKey
{
	private final String id;
	private final String docNo;
	private final KeyNamePair value;
	private final int orderId;

	/* package */ PurchaseOrderKey(final ITerminalContext terminalContext, final I_C_Order order)
	{
		super(terminalContext);

		Check.assumeNotNull(order, "order not null");

		id = "C_Order#" + order.getC_Order_ID();
		docNo = order.getDocumentNo();
		orderId = order.getC_Order_ID();
		value = new KeyNamePair(orderId, docNo);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return docNo;
	}

	@Override
	public String getTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getC_Order_ID()
	{
		return orderId;
	}
}
