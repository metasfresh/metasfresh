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


import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class DefaultKeyLayoutSelectionModel implements IKeyLayoutSelectionModel
{
	private final WeakPropertyChangeSupport pcs;

	private boolean allowKeySelection = false;
	private boolean allowMultipleSelection = false;

	private Color selectedKeyColor = Color.BLACK;

	/**
	 * Selected key (used when {@link #allowKeySelection} is false)
	 */
	private ITerminalKey selectedKey = null;

	/**
	 * Selected keys (used when {@link #allowKeySelection} is true).
	 *
	 * Map of KeyId to {@link ITerminalKey}.
	 */
	private final Map<String, ITerminalKey> selectedKeys = new HashMap<String, ITerminalKey>();

	/**
	 * @see #selectedKeys
	 */
	private final Collection<ITerminalKey> selectedKeysRO = Collections.unmodifiableCollection(selectedKeys.values());

	private boolean autoSelectIfOnlyOne = false;
	private boolean isToggleableSelection = false;

	public DefaultKeyLayoutSelectionModel(final ITerminalContext terminalContext)
	{
		pcs = terminalContext.createPropertyChangeSupport(this);
		terminalContext.addToDisposableComponents(this);
	}

	private boolean _disposed = false;

	@Override
	public final void dispose()
	{
		if (_disposed)
		{
			return;
		}
		_disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return _disposed;
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		if (_disposed)
		{
			return;
		}

		// make sure we are not adding it twice
		pcs.removePropertyChangeListener(listener);

		final boolean weak = true;
		pcs.addPropertyChangeListener(listener, weak);
	}

	@Override
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		if (_disposed)
		{
			return;
		}

		// make sure we are not adding it twice
		pcs.removePropertyChangeListener(propertyName, listener);

		final boolean weak = true;
		pcs.addPropertyChangeListener(propertyName, listener, weak);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
	}

	@Override
	public boolean isAllowKeySelection()
	{
		return allowKeySelection;
	}

	@Override
	public void setAllowKeySelection(final boolean allowKeySelection)
	{
		final boolean allowKeySelectionOld = this.allowKeySelection;
		this.allowKeySelection = allowKeySelection;
		pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_AllowKeySelection, allowKeySelectionOld, this.allowKeySelection);

		// If we switched to not allow selection, we need to reset the selected key
		if (allowKeySelectionOld && !allowKeySelection)
		{
			onKeySelected(null);
		}
	}

	@Override
	public Color getKeySelectionColor()
	{
		return selectedKeyColor;
	}

	@Override
	public void setKeySelectionColor(final Color color)
	{
		final Color selectedKeyColorOld = selectedKeyColor;
		selectedKeyColor = color;
		pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_KeySelectionColor, selectedKeyColorOld, selectedKeyColor);
	}

	@Override
	public ITerminalKey getSelectedKeyOrNull()
	{
		Check.assume(!isAllowMultipleSelection(), "multiple selection is disallowed when attempting to retrieve a single key");
		return selectedKey;
	}

	@Override
	public <T extends ITerminalKey> T getSelectedKeyOrNull(final Class<T> terminalKeyClass) throws ClassCastException
	{
		Check.assumeNotNull(terminalKeyClass, "terminalKeyClass not null");

		final ITerminalKey selectedKey = getSelectedKeyOrNull();
		if (selectedKey == null)
		{
			return null;
		}

		return terminalKeyClass.cast(selectedKey);
	}

	@Override
	public <T extends ITerminalKey> T getSelectedKey(final Class<T> terminalKeyClass, final String errorMsgNotSelected) throws ClassCastException, TerminalException
	{
		final T selectedKey = getSelectedKeyOrNull(terminalKeyClass);
		if (selectedKey == null)
		{
			throw new TerminalException(errorMsgNotSelected);
		}
		return selectedKey;
	}

	@Override
	public Collection<ITerminalKey> getSelectedKeys()
	{
		Check.assume(isAllowMultipleSelection(), "multiple selection is allowed when attempting to retrieve entire selection");
		return selectedKeysRO;
	}

	@Override
	public <T extends ITerminalKey> List<T> getSelectedKeys(final Class<T> terminalKeyClass) throws ClassCastException
	{
		final Collection<ITerminalKey> selectedKeys = getSelectedKeys();

		final List<T> selectedKeysConv = new ArrayList<T>();
		for (final ITerminalKey selectedKey : selectedKeys)
		{
			final T currentKey = terminalKeyClass.cast(selectedKey);
			if (!selectedKeysConv.contains(currentKey))
			{
				selectedKeysConv.add(terminalKeyClass.cast(selectedKey));
			}
		}
		return Collections.unmodifiableList(selectedKeysConv);
	}

	@Override
	public void setSelectedKey(final ITerminalKey selectedKey)
	{
		setSelectedKey(selectedKey, true); // selectDirectly=true
	}

	@Override
	public void onKeySelected(final ITerminalKey selectedKey)
	{
		setSelectedKey(selectedKey, false); // selectDirectly=false
	}

	private void setSelectedKey(final ITerminalKey selectedKey, final boolean selectDirectly)
	{
		if (_disposed)
		{
			return;
		}

		if (!isAllowMultipleSelection())
		{
			final ITerminalKey selectedKeyOld = this.selectedKey;
			//
			// if selected directly, just set the key, otherwise apply toggling options
			this.selectedKey = selectDirectly ? selectedKey : getNewSelectedKeyBasedOnToggle(selectedKeyOld, selectedKey);
			pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_SelectedKey, selectedKeyOld, this.selectedKey);
		}
		else
		{
			if (selectedKey == null)
			{
				return;
			}

			final List<ITerminalKey> selectedKeysOld = new ArrayList<ITerminalKey>(selectedKeys.values());

			//
			// If selected directly, just set the key, otherwise apply toggling options
			final String selectedKeyId = selectedKey.getId();
			if (!selectDirectly && selectedKeys.containsKey(selectedKeyId))
			{
				selectedKeys.remove(selectedKeyId);
			}
			else
			{
				selectedKeys.put(selectedKeyId, selectedKey);
			}

			final List<ITerminalKey> selectedKeysNew = new ArrayList<ITerminalKey>(selectedKeys.values());
			pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_SelectedKeys, selectedKeysOld, selectedKeysNew);
		}
	}

	private ITerminalKey getNewSelectedKeyBasedOnToggle(final ITerminalKey selectedKeyOld, final ITerminalKey selectedKey)
	{
		if (isToggleableSelection // if second key press means deselect
				&& Util.same(selectedKeyOld, selectedKey)) // and if it's an already-selected key
		{
			//
			// pressed key will be deselected
			return null;
		}

		//
		// otherwise, the selected key is just selected, so use it
		return selectedKey;
	}

	@Override
	public void setAutoSelectIfOnlyOne(final boolean autoSelectIfOnlyOne)
	{
		final boolean autoSelectIfOnlyOneOld = this.autoSelectIfOnlyOne;
		this.autoSelectIfOnlyOne = autoSelectIfOnlyOne;
		pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_AutoSelectIfOnlyOne, autoSelectIfOnlyOneOld, this.autoSelectIfOnlyOne);
	}

	@Override
	public boolean isAutoSelectIfOnlyOne()
	{
		return autoSelectIfOnlyOne;
	}

	@Override
	public void setToggleableSelection(final boolean isToggleableSelection)
	{
		this.isToggleableSelection = isToggleableSelection;
	}

	@Override
	public boolean isToggleableSelection()
	{
		return isToggleableSelection;
	}

	@Override
	public void setAllowMultipleSelection(final boolean allowMultipleSelection)
	{
		if (isAllowMultipleSelection() == allowMultipleSelection)
		{
			return;
		}

		Check.assumeNull(selectedKey, "changing selection model can be done only when selection was not used yet");
		Check.assume(selectedKeys.isEmpty(), "changing selection model can be done only when selection was not used yet");

		this.allowMultipleSelection = allowMultipleSelection;
	}

	@Override
	public boolean isAllowMultipleSelection()
	{
		return allowMultipleSelection;
	}

	@Override
	public void clearSelection()
	{
		if (isAllowMultipleSelection())
		{
			final List<ITerminalKey> selectedKeysOld = new ArrayList<ITerminalKey>(selectedKeys.values());
			selectedKeys.clear();
			final List<ITerminalKey> selectedKeysNew = new ArrayList<ITerminalKey>(selectedKeys.values());
			pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_SelectedKeys, selectedKeysOld, selectedKeysNew);
		}
		else
		{
			final ITerminalKey selectedKeyOld = selectedKey;
			selectedKey = null;
			pcs.firePropertyChange(IKeyLayoutSelectionModel.PROPERTY_SelectedKey, selectedKeyOld, selectedKey);
		}
	}

	@Override
	public boolean isSelected(final ITerminalKey key)
	{
		if (key == null)
		{
			return false;
		}

		final String keyId = key.getId();

		//
		// Get which is the selected key we need to check/compare with given "key"
		final ITerminalKey selectedKeyToCheck;
		if (isAllowMultipleSelection())
		{
			selectedKeyToCheck = selectedKeys.get(keyId);
		}
		else
		{
			selectedKeyToCheck = selectedKey;
		}

		if (selectedKeyToCheck == null)
		{
			return false;
		}

		final boolean selected = Check.equals(keyId, selectedKeyToCheck.getId());
		return selected;
	}

	@Override
	public boolean isEmpty()
	{
		if (isAllowMultipleSelection())
		{
			return selectedKeys.isEmpty();
		}
		else
		{
			return selectedKey == null;
		}
	}
}
