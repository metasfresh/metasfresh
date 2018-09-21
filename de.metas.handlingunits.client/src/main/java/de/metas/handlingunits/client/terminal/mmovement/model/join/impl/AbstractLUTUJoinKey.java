package de.metas.handlingunits.client.terminal.mmovement.model.join.impl;

import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Services;

public abstract class AbstractLUTUJoinKey extends TerminalKey implements ILUTUJoinKey
{
	private final I_M_HU hu;
	private final IHUDocumentLine documentLine;
	private final boolean virtual;

	private final String id;

	private String name;
	private KeyNamePair value;

	public AbstractLUTUJoinKey(final ITerminalContext terminalContext, final I_M_HU hu, final IHUDocumentLine documentLine, final boolean virtual)
	{
		super(terminalContext);

		this.hu = hu;
		this.documentLine = documentLine;
		this.virtual = virtual;

		final int huId = hu.getM_HU_ID();
		id = getClass().getName() + "-" + huId;

		rebuild();
	}

	@Override
	public void rebuild()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// Show TUs count in case of an LU (08422)
		final boolean showIncludedHUCount = handlingUnitsBL.isLoadingUnit(hu);

		name = handlingUnitsBL
				.buildDisplayName(hu)
				.setShowIncludedHUCount(showIncludedHUCount)
				.build();

		value = new KeyNamePair(hu.getM_HU_ID(), name);
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
		return I_M_HU.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public I_M_HU getM_HU()
	{
		return hu;
	}

	@Override
	public IHUDocumentLine getHUDocumentLine()
	{
		return documentLine;
	}

	@Override
	public boolean isVirtual()
	{
		return virtual;
	}
}
