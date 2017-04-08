package de.metas.handlingunits.impl;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HULockBL implements IHULockBL
{
	@Override
	public boolean isLocked(final I_M_HU hu)
	{
		return hu.isLocked();
	}

	@Override
	public void lock(final I_M_HU hu)
	{
		hu.setLocked(true);
		Services.get(IHandlingUnitsDAO.class).saveHU(hu);
	}

	@Override
	public IQueryFilter<I_M_HU> isLockedFilter()
	{
		return new EqualsQueryFilter<>(I_M_HU.COLUMN_Locked, true);
	}
}
