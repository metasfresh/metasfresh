package de.metas.handlingunits.client.terminal.select.model;

import org.compiere.model.I_C_BPartner;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

/**
 * A terminal key that encapsulates a (vendor) bPartner.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class VendorKey extends TerminalKey
{
	private final String id;
	private final String name;
	private final KeyNamePair value;
	private final int bpartnerId;

	/**
	 *
	 * @param terminalContext
	 * @param order
	 */
	/* package */ VendorKey(final ITerminalContext terminalContext, final I_C_BPartner bpartner)
	{
		super(terminalContext);

		Check.assumeNotNull(bpartner, "bpartner not null");

		id = "C_BPartner#" + bpartner.getC_BPartner_ID();
		name = bpartner.getName();

		bpartnerId = bpartner.getC_BPartner_ID();
		value = new KeyNamePair(bpartnerId, name);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return name;
	}

	@Override
	public String getTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}
}
