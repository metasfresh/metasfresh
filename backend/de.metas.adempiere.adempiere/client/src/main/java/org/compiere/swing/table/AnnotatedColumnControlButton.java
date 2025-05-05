package org.compiere.swing.table;

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

import org.compiere.util.DisplayType;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.table.TableColumn;

class AnnotatedColumnControlButton extends ColumnControlButton
{
	private static final long serialVersionUID = 1L;

	public static final String PROPERTY_DisableColumnControl = AnnotatedColumnControlButton.class.getName() + ".DisableColumnControl";

	public AnnotatedColumnControlButton(JXTable table)
	{
		super(table);
	}

	@Override
	protected ColumnVisibilityAction createColumnVisibilityAction(final TableColumn column)
	{
		ColumnVisibilityAction action = super.createColumnVisibilityAction(column);
		if (action != null && !canControlColumn(column))
		{
			// NOTE: instead of just disabling it, better hide it at all
			//action.setEnabled(false);
			action = null;
		}
		return action;
	}

	private static final boolean canControlColumn(TableColumn column)
	{
		if (!(column instanceof TableColumnExt))
		{
			return false;
		}

		final TableColumnExt columnExt = (TableColumnExt)column;
		final Object disableColumnControlObj = columnExt.getClientProperty(PROPERTY_DisableColumnControl);
		if (DisplayType.toBooleanNonNull(disableColumnControlObj, false))
		{
			return false;
		}
		
		return true;
	}

}
