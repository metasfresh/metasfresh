package org.compiere.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Convenient class to implement a {@link ListCellRenderer} which is rendering the value to a string label.
 * 
 * Usually, developer will extend this class inline and will implement {@link #renderToString(Object)} method.
 * 
 * @author tsa
 *
 * @param <T> value type
 */
public class ToStringListCellRenderer<T> extends DefaultListCellRenderer
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3068158390813947550L;

	@Override
	public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
	{
		@SuppressWarnings("unchecked")
		final T valueCasted = (T)value;
		final String valueAsString = renderToString(valueCasted);
		return super.getListCellRendererComponent(list, valueAsString, index, isSelected, cellHasFocus);
	}

	protected String renderToString(final T value)
	{
		return value == null ? "" : value.toString();
	}
}
