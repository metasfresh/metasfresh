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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * A key layout contains a number of {@link ITerminalKey}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IKeyLayout extends IDisposable
{
	Integer DEFAULT_COLUMN_COUNT = 3;
	Integer DEFAULT_ROW_COUNT = 3;

	/**
	 * Property fired when internal keys were changed
	 */
	String PROPERTY_KeysChanged = IKeyLayout.class.getName() + "#KeysChanged";

	String getId();

	ITerminalContext getTerminalContext();

	@Override
	void dispose();

	/**
	 *
	 * @param key
	 * @return default key color to be used for the specific key
	 */
	Color getDefaultColor(ITerminalKey key);

	void setDefaultColor(Color color);

	/**
	 *
	 * @return default key font to be used
	 */
	Font getDefaultFont();

	/**
	 *
	 * @return true if they Key Layout has no keys inside
	 */
	boolean isEmpty();

	/**
	 *
	 * @return how many keys are currently in this Key Layout
	 */
	int getKeysCount();

	List<ITerminalKey> getKeys();

	/**
	 * Return all keys, cast to the given <code>keyType</code>.
	 *
	 * @param keyType
	 * @return
	 */
	<KT extends ITerminalKey> List<KT> getKeys(final Class<KT> keyType);

	/**
	 * Gets {@link ITerminalKey} for given <code>keyId</code> or null.
	 *
	 * @param keyId
	 * @return terminal key
	 */
	ITerminalKey getKeyById(String keyId);

	/**
	 * Gets {@link ITerminalKey} for given <code>index</code>.
	 *
	 * @param index
	 * @return terminal key
	 * @throws IndexOutOfBoundsException
	 */
	ITerminalKey getKeyByIndex(int index);

	ITerminalKey findKeyByValue(int id);

	int getColumns();

	void setColumns(int columns);

	boolean isText();

	boolean isNumeric();

	/**
	 * Add listener.
	 *
	 * NOTE: listeners are adding by using a weak reference, so make sure you keep a reference to your listener in case you don't want to be removed automatically.
	 *
	 * @param listener
	 */
	void addListener(PropertyChangeListener listener);

	void removeListener(PropertyChangeListener listener);

	void setRows(int rows);

	/**
	 * If true, instead of adding more columns and expanding width, expand height by adding more rows instead
	 *
	 * @param addRowsIfColumnsExceeded
	 */
	void setAddRowsIfColumnsExceeded(boolean addRowsIfColumnsExceeded);

	int getRows();

	boolean isEnabledKeys();

	void setEnabledKeys(boolean enabledKeys);

	void setBasePanel(ITerminalBasePanel base);

	ITerminalBasePanel getBasePanel();

	/**
	 * Reset current keys. Dispose the old keys.
	 */
	void resetKeys();

	/**
	 * Called when user presses a Key, before any other listeners are called, key is selected.
	 *
	 * If there are exceptions thrown from this method, no further actions will be performed.
	 *
	 * @param key
	 * @see ITerminalKeyListener#keyReturning(ITerminalKey)
	 */
	void keyReturning(ITerminalKey key);

	/**
	 * Called when user presses a Key, after {@link #keyReturning(ITerminalKey)} and after current pressed key is selected.
	 *
	 * @param key
	 * @see ITerminalKeyListener#keyReturned(ITerminalKey)
	 */
	void keyReturned(ITerminalKey key);

	void addTerminalKeyListener(ITerminalKeyListener listener);

	IKeyLayoutSelectionModel getKeyLayoutSelectionModel();

	/**
	 * Set the new keys. Dispose the old keys.
	 *
	 * @param keysNew
	 */
	void setKeys(Collection<? extends ITerminalKey> keysNew);

	void setSelectedKey(ITerminalKey selectedKey);

	void setSelectedKeyById(String keyId);

	void selectFirstKeyIfAny();
}
