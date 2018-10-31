package org.compiere.minigrid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import de.metas.util.Check;

/**
 * Extension on {@link DefaultTableModel} which has following changes:
 * <ul>
 * <li>when setting a value an event is fired only if the value was really changed
 * </ul>
 * 
 * @author tsa
 * 
 */
public class MiniTableModel extends DefaultTableModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1672846860594628976L;

	public MiniTableModel()
	{
		super();
	}

	/**
	 * Same as {@link DefaultTableModel#setValueAt(Object, int, int)} but is firing the event ONLY if the value was really changed.
	 * 
	 * In case exception is thrown in firing, the value is reverted.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object aValue, int row, int column)
	{
		@SuppressWarnings("rawtypes")
		final Vector rowVector = (Vector)dataVector.elementAt(row);

		// Check if value was really changed. If not, do nothing
		final Object valueOld = rowVector.get(column);
		if (Check.equals(valueOld, aValue))
		{
			return;
		}

		boolean valueSet = false;
		try
		{
			rowVector.setElementAt(aValue, column);
			fireTableCellUpdated(row, column);
			valueSet = true;
		}
		finally
		{
			// Rollback changes
			if (!valueSet)
			{
				rowVector.setElementAt(valueOld, column);
			}
		}
	}

}
