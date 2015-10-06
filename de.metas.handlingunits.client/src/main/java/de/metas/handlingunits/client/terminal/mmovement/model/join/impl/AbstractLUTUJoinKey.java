package de.metas.handlingunits.client.terminal.mmovement.model.join.impl;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Services;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;

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
