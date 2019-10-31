package org.adempiere.misc.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;

public final class POService implements IPOService
{
	@Override
	public void save(final Object po, final String trxName)
	{
		InterfaceWrapperHelper.save(po, trxName);
	}

	/**
	 * If the table of the given PO has a column with the given name, the PO's value is returned.
	 * 
	 * This method can be used to access non-standard columns that are not present in every ADempiere database.
	 * 
	 * @param po
	 * @param columnName
	 * @return the PO's value of the given column or <code>null</code> if the PO doesn't have a column with the given
	 *         name.
	 * @throws IllegalArgumentException
	 *             if the given object is not <code>instanceof PO</code>
	 */
	@Override
	public Object getValue(final Object po, final String columnName)
	{
		doChecks(po, columnName);

		if (MiscUtils.asPO(po).get_ColumnIndex(columnName) != -1)
		{
			return MiscUtils.asPO(po).get_Value(columnName);
		}
		// TODO throw an Exception
		return null;
	}

	private void doChecks(final Object po, final String columnName)
	{
		if (columnName == null)
		{
			throw new NullPointerException("columnName");
		}
		if (po == null)
		{
			throw new NullPointerException("po");
		}
	}

	/**
	 * If the table of the given PO has a column with the given name, the PO's value is set to the given value.
	 * 
	 * This method can be used to access non-standard columns that are not present in every ADempiere database.
	 * 
	 * @param po
	 * @param columnName
	 * @param value
	 *            may be <code>null</code>
	 */
	@Override
	public void setValue(final Object po, final String columnName,
			final Object value)
	{
		if (columnName == null)
		{
			throw new NullPointerException("columnName");
		}

		if (MiscUtils.asPO(po).get_ColumnIndex(columnName) != -1)
		{
			MiscUtils.asPO(po).set_ValueOfColumn(columnName, value);
		}
		else
		{
			// TODO throw an Exception
			return;
		}
	}

	@Override
	public void copyValue(final Object source, final Object dest, final String columnName)
	{
		setValue(dest, columnName, getValue(source, columnName));
	}
}
