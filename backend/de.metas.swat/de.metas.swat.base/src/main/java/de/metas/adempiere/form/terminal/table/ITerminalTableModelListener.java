package de.metas.adempiere.form.terminal.table;

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


public interface ITerminalTableModelListener
{
	/**
	 * Event fired when model is requesting the UI to stop editing and commit the changes.
	 * 
	 * NOTE: this event shall not be postponed but it shall be executed ASAP because after this event model will change and mode events will come.
	 */
	void fireRequestStopEditing();

	void fireTableStructureChanged();

	void fireTableRowsInserted(int rowFirst, int rowLast);

	void fireTableRowsUpdated(int rowFirst, int rowLast);

	void fireTableRowsDeleted(int rowFirst, int rowLast);

	void fireSelectionChanged();

}
