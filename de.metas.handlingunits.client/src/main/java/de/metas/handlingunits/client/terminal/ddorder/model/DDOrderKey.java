package de.metas.handlingunits.client.terminal.ddorder.model;

import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_DD_Order;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

public class DDOrderKey extends TerminalKey
{

	private final String id;
	private final String docNo;
	private final KeyNamePair value;
	private final int ddOrderId;

	public DDOrderKey(final ITerminalContext terminalContext, final I_DD_Order ddOrder)
	{
		super(terminalContext);

		Check.assumeNotNull(ddOrder, "order not null");

		id = "DD_Order#" + ddOrder.getDD_Order_ID();
		docNo = ddOrder.getDocumentNo();
		ddOrderId = ddOrder.getDD_Order_ID();
		value = new KeyNamePair(ddOrderId, docNo);
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
		return I_DD_Order.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getDD_Order_ID()
	{
		return ddOrderId;
	}

}
