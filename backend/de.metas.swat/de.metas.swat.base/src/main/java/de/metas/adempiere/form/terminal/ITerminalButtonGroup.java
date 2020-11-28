package de.metas.adempiere.form.terminal;

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


import java.beans.PropertyChangeListener;
import java.util.List;

public interface ITerminalButtonGroup<T> extends IDisposable
{
	String ACTION_ValueChanged = ITerminalField.ACTION_ValueChanged;

	void setValue(final T value);

	T getValue();

	ITerminalButton addButton(final T value, final String buttonText);

	void removeListener(final String propertyName, final PropertyChangeListener listener);

	void removeListener(final PropertyChangeListener listener);

	void addListener(final String propertyName, final PropertyChangeListener listener);

	void addListener(final PropertyChangeListener listener);

	List<ITerminalButton> getButtons();
}
