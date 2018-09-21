package de.metas.fresh.picking;

import org.compiere.model.I_C_BPartner;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

public class BPartnerKey extends TerminalKey
{
	private final String id;
	private final String name;
	private final KeyNamePair value;

	/* package */BPartnerKey(final ITerminalContext terminalContext, final KeyNamePair bpartnerKNP)
	{
		super(terminalContext);

		Check.assumeNotNull(bpartnerKNP, "bpartnerKNP not null");
		this.value = bpartnerKNP;

		this.id = "C_BPartner#" + bpartnerKNP.getKey();
		this.name = bpartnerKNP.getName();
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
		return value.getKey();
	}
}
