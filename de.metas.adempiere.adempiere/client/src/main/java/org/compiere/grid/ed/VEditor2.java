package org.compiere.grid.ed;

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


import java.beans.PropertyChangeEvent;

/**
 * Extension to {@link VEditor} which introduces more fine control methods. </p> Note that
 * <ul>
 * <li>if {@link org.compiere.grid.GridController} handles a VetoableChangeEvent and</li>
 * <li>if that event's source is <code>instanceof VEditor2</code> and</li>
 * <li>if the event is not considered a "real" change by the GridController, but by this instance (see {@link #isRealChange(PropertyChangeEvent)})</li>
 * <li>then the new value is set in the respective GridTable with forced==true, because the GridTable would otherwise ignore the new value</li>
 * </ul>
 * 
 * @author tsa
 * 
 */
public interface VEditor2 extends VEditor
{
	/**
	 * Checks if given event shall produce a "real" value change.
	 * 
	 * NOTE: this method is called by {@link org.compiere.grid.GridController} when the fired change event does not seem to be a concrete change (i.e. new value is null) but it wants to make sure. If
	 * the method returns <code>true</code>, then the change event is processed as a concrete change
	 * 
	 * @param e
	 * @return true if given change event shall be considered a real change
	 */
	boolean isRealChange(PropertyChangeEvent e);
}
