package de.metas.materialtracking.impl;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.materialtracking
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.exceptions.AdempiereException;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.util.Check;

public class PlainHandlingUnitsInfo implements IHandlingUnitsInfo
{
	private final String tuName;
	private final int qtyTU;

	public PlainHandlingUnitsInfo(final String tuName, final int qtyTU)
	{
		super();

		Check.assumeNotEmpty(tuName, "tuName not empty");
		this.tuName = tuName;

		this.qtyTU = qtyTU;
	}

	@Override
	public String toString()
	{
		return "PlainHandlingUnitsInfo [tuName=" + tuName + ", qtyTU=" + qtyTU + "]";
	}

	@Override
	public int getQtyTU()
	{
		return qtyTU;
	}

	@Override
	public String getTUName()
	{
		return tuName;
	}

	@Override
	public IHandlingUnitsInfo add(@Nullable final IHandlingUnitsInfo infoToAdd)
	{
		if (infoToAdd == null)
		{
			return this; // shall not happen
		}

		final String tuName = getTUName();
		if (!tuName.equals(infoToAdd.getTUName()))
		{
			throw new AdempiereException("TU Names are not matching."
					+ "\n This: " + this
					+ "\n Other: " + infoToAdd);
		}

		final int qtyTU = getQtyTU();
		final int qtyTU_ToAdd = infoToAdd.getQtyTU();
		final int qtyTU_New = qtyTU + qtyTU_ToAdd;

		final PlainHandlingUnitsInfo infoNew = new PlainHandlingUnitsInfo(tuName, qtyTU_New);
		return infoNew;
	}
}
