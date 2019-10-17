package de.metas.handlingunits.client.terminal.report.model;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import lombok.NonNull;

public class HUADProcessKey extends TerminalKey
{
	private final AdProcessId adProcessId;
	private final String id;
	private final String name;
	private final KeyNamePair value;

	public HUADProcessKey(final ITerminalContext terminalContext, final AdProcessId adProcessId)
	{
		super(terminalContext);

		id = getClass().getName() + "-" + adProcessId;

		value = extractKeyNamePair(adProcessId);
		this.adProcessId = adProcessId;
		name = value.getName();
	}

	private static final KeyNamePair extractKeyNamePair(@NonNull final AdProcessId adProcessId)
	{
		final I_AD_Process process = Services.get(IADProcessDAO.class).getById(adProcessId);
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class);

		String caption = processTrl.getName();
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			caption += " (" + adProcessId + ")";
		}

		return KeyNamePair.of(adProcessId, caption);
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
		return I_AD_Process.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public AdProcessId getAdProcessId()
	{
		return adProcessId;
	}
}
