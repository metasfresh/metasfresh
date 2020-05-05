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



/**
 * Set the selected table column (see ColumnInfo#selectionColumn() ) to <code>false</code> for all those table rows which user selected in table.
 * 
 * @author tsa
 */
public class DeselectAction extends AbstractManageSelectableRowsAction
{
	private static final long serialVersionUID = 1L;

	public DeselectAction()
	{
		super("Button.UnselectAll");
	}

	@Override
	protected boolean isEnabledForRow(SelectableRowAdapter row)
	{
		return row.isSelected(); // we can deselect the given row if it's selected
	}

	/** Deselect given row */
	@Override
	protected void executeActionOnRow(SelectableRowAdapter row)
	{
		row.setSelected(false);
	}
}
