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
import java.util.Comparator;

import org.compiere.util.KeyNamePair;

public interface ITerminalKey extends IDisposable
{
	String PROPERTY_Name = "Name";
	String ACTION_StatusChanged = "StatusChanged";

	/** Comparator used to sort {@link ITerminalKey}s, ascending by their {@link #getName()} */
	Comparator<ITerminalKey> COMPARATOR_ByName = new Comparator<ITerminalKey>()
	{

		@Override
		public int compare(final ITerminalKey o1, final ITerminalKey o2)
		{
			// NOTE: nulls are not supported; it does not make sense
			final String name1 = String.valueOf(o1.getName());
			final String name2 = String.valueOf(o2.getName());
			return name1.compareTo(name2);
		}
	};

	void setStatus(ITerminalKeyStatus status);

	ITerminalKeyStatus getStatus();

	String getId();

	int getSpanX();

	int getSpanY();

	boolean isActive();

	IKeyLayout getSubKeyLayout();

	Object getName();

	String getTableName();

	KeyNamePair getValue();

	String getText();

	/**
	 * Add listener. If listener was already added, it won't be added twice.
	 *
	 * @param listener
	 */
	void addListener(PropertyChangeListener listener);

	/**
	 * Add listener. If listener was already added, it won't be added twice.
	 *
	 * @param propertyName
	 * @param listener
	 */
	void addListener(String propertyName, PropertyChangeListener listener);

	void removeListener(PropertyChangeListener listener);

	void removeListener(String propertyName, PropertyChangeListener listener);

	boolean isEnabledKey();

	void setEnabledKey(boolean enabledKey);

	/**
	 * @return debug info to be displayed if debugging is enabled
	 */
	String getDebugInfo();

	/**
	 * Set GUI Width of the Key
	 *
	 * @param width
	 */
	void setWidth(int width);

	/**
	 * @return GUI Width of the Key
	 */
	int getWidth();

	/**
	 * Set GUI Height of the Key
	 *
	 * @param height
	 */
	void setHeight(int height);

	/**
	 * @return GUI Height of the Key
	 */
	int getHeight();
}
