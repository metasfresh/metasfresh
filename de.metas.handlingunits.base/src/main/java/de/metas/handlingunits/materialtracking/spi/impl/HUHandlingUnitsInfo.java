package de.metas.handlingunits.materialtracking.spi.impl;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.Adempiere;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.materialtracking.IHandlingUnitsInfo;

/* package */class HUHandlingUnitsInfo implements IHandlingUnitsInfo
{
	public static HUHandlingUnitsInfo cast(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		return (HUHandlingUnitsInfo)handlingUnitsInfo;
	}

	private final I_M_HU_PI _tuPI;
	private int _qtyTU;
	private final String _tuPIName;

	private final boolean _isQtyWritable;

	/* package */ HUHandlingUnitsInfo(final I_M_HU_PI tuPI, final int qtyTU)
	{
		this(tuPI, qtyTU, false);
	}

	/* package */ HUHandlingUnitsInfo(final I_M_HU_PI tuPI, final int qtyTU, final boolean isReadWrite)
	{
		// task 09688: currently, when in unit testing mode, we allow tuPI to be null
		Check.assume(tuPI != null || Adempiere.isUnitTestMode(), "tuPI not null");

		_tuPI = tuPI;
		_tuPIName = tuPI != null ? tuPI.getName() : null;

		_qtyTU = qtyTU;

		_isQtyWritable = isReadWrite;
	}

	@Override
	public int getQtyTU()
	{
		return _qtyTU;
	}

	@Override
	public String getTUName()
	{
		return _tuPIName;
	}

	public final I_M_HU_PI getTU_HU_PI()
	{
		return _tuPI;
	}

	@Override
	public IHandlingUnitsInfo add(@Nullable final IHandlingUnitsInfo infoToAdd)
	{
		if (infoToAdd == null)
		{
			return this;
		}

		// TU PI
		final I_M_HU_PI tuPI = getTU_HU_PI();
		// TODO make sure tuPIs are compatible

		// Qty TU
		final int qtyTU = getQtyTU();
		final int qtyTU_ToAdd = infoToAdd.getQtyTU();

		final int qtyTU_New = qtyTU + qtyTU_ToAdd;

		final boolean isReadWrite = false;
		final HUHandlingUnitsInfo infoNew = new HUHandlingUnitsInfo(tuPI, qtyTU_New, isReadWrite);
		return infoNew;
	}

	protected void setQtyTUInner(final int qtyTU)
	{
		Check.errorIf(!_isQtyWritable, "This instance {} is read-only", this);
		_qtyTU = qtyTU;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

}
