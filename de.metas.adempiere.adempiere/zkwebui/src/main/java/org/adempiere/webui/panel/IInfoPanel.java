package org.adempiere.webui.panel;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import org.adempiere.webui.event.ValueChangeListener;

public interface IInfoPanel extends org.zkoss.zul.api.Window
{

	/**
	 *  Loaded correctly
	 *  @return true if loaded OK
	 */
	boolean loadedOK();   //  loadedOK

	/**
	 *	Get selected Keys
	 *  @return selected keys (Integers)
	 */
	Object[] getSelectedKeys();	//	getSelectedKeys;

	/**
	 *	Get (first) selected Key
	 *  @return selected key
	 */
	Object getSelectedKey();	//	getSelectedKey

	/**
	 *	Is cancelled?
	 *	- if pressed Cancel = true
	 *	- if pressed OK or window closed = false
	 *  @return true if cancelled
	 */
	boolean isCancelled();	//	isCancelled

	/**
	 *	Get where clause for (first) selected key
	 *  @return WHERE Clause
	 */
	String getSelectedSQL();	//	getSelectedSQL;

	void addValueChangeListener(ValueChangeListener listener);

	Object setAttribute(String name, Object value);
}
