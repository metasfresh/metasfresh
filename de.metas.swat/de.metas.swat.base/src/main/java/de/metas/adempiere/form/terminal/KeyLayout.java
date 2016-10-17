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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.logging.LogManager;

/**
 * Abstract {@link IKeyLayout} implementation of general methods.
 *
 * @author cg
 *
 */
public abstract class KeyLayout implements IKeyLayout
{
	// Services
	protected final transient Logger logger = LogManager.getLogger(getClass());
	protected final transient IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	private final ITerminalContext tc;
	private final WeakPropertyChangeSupport _listeners;
	private final CompositeTerminalKeyListener _keyListeners;

	private List<ITerminalKey> keys = null;

	private int rows = IKeyLayout.DEFAULT_ROW_COUNT;
	private boolean _addRowsIfColumnsExceeded = false;

	private int columns = IKeyLayout.DEFAULT_COLUMN_COUNT;
	private int columnsMin = 1;
	private boolean enabledKeys = true;
	private ITerminalBasePanel basePanel;

	private final IKeyLayoutSelectionModel selectionModel;

	private Color defaultColor = null;
	private Font defaultFont = null;

	public KeyLayout(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		tc = terminalContext;

		selectionModel = new DefaultKeyLayoutSelectionModel(terminalContext);

		_listeners = terminalContext.createPropertyChangeSupport(this);

		_keyListeners = new CompositeTerminalKeyListener(terminalContext);

		terminalContext.addToDisposableComponents(this);
	}

	private boolean _disposed = false;

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		if (_disposed)
		{
			return;
		}

		// disposing keys keys is handled via TerminalContext
		// DisposableHelper.disposeAll(keys);

		// Remove keys
		setKeysNoFire(null);

		// Cleanup key listeners
		if (_keyListeners != null)
		{
			_keyListeners.clear();
		}

		// Cleanup listeners
		if (_listeners != null)
		{
			_listeners.clear();
		}

		if (internalReferences != null)
		{
			internalReferences.dispose();
		}

		basePanel = null;

		_disposed = true;
	};

	@Override
	public boolean isDisposed()
	{
		return _disposed;
	}

	private final void assertNotDisposed()
	{
		if (isDisposed())
		{
			throw new IllegalStateException("Already disposed: " + this);
		}
	}

	@Override
	public final boolean isEmpty()
	{
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null || keys.isEmpty())
		{
			return true;
		}

		return false;
	}

	@Override
	public final int getKeysCount()
	{
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null)
		{
			return 0;
		}

		return keys.size();
	}

	@Override
	public final List<ITerminalKey> getKeys()
	{
		List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null)
		{
			if (!_disposed)
			{
				keys = createKeys();
				for (final ITerminalKey key : keys)
				{
					getTerminalContext().addToDisposableComponents(key);
				}
			}

			if (keys == null)
			{
				keys = Collections.emptyList();
			}

			setKeys(keys);
		}

		return Collections.unmodifiableList(keys);
	}

	@Override
	public final <KT extends ITerminalKey> List<KT> getKeys(final Class<KT> keyType)
	{
		final List<ITerminalKey> keys = getKeys();
		final List<KT> keysToReturn = new ArrayList<KT>(keys.size());
		for (final ITerminalKey key : keys)
		{
			@SuppressWarnings("unchecked")
			final KT keyToReturn = (KT)key;
			keysToReturn.add(keyToReturn);
		}

		return keysToReturn;
	}

	@Override
	public ITerminalKey getKeyByIndex(final int index)
	{
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null)
		{
			throw new IndexOutOfBoundsException("layout has no keys");
		}

		// NOTE: throws IndexOutOfBoundsException
		return keys.get(index);
	}

	@Override
	public final ITerminalKey getKeyById(final String keyId)
	{
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null || keys.isEmpty())
		{
			return null;
		}

		for (final ITerminalKey key : keys)
		{
			if (Check.equals(key.getId(), keyId))
			{
				return key;
			}
		}

		return null;
	}

	public final boolean hasKeyForId(final String keyId)
	{
		return getKeyById(keyId) != null;
	}

	/**
	 * Add current registered listeners to given <code>keys</code>.
	 *
	 * @param keys
	 */
	private final void addCurrentListeners(final Collection<? extends ITerminalKey> keys)
	{
		if (keys == null || keys.isEmpty())
		{
			return;
		}
		final PropertyChangeListener[] listeners = _listeners.getPropertyChangeListeners();
		if (listeners == null || listeners.length == 0)
		{
			return;
		}

		for (final ITerminalKey key : keys)
		{
			for (final PropertyChangeListener listener : listeners)
			{
				key.addListener(listener);
			}
		}
	}

	private final void removeCurrentListeners(final List<ITerminalKey> keys)
	{
		if (keys == null || keys.isEmpty())
		{
			return;
		}
		final PropertyChangeListener[] listeners = _listeners.getPropertyChangeListeners();
		if (listeners == null || listeners.length == 0)
		{
			return;
		}

		for (final ITerminalKey key : keys)
		{
			for (final PropertyChangeListener listener : listeners)
			{
				key.removeListener(listener);
			}
		}

	}

	/**
	 * Gets actual existing keys inner list. If keys were not already loaded, it won't try to load them. If you want that, please use {@link #getKeys()}.
	 *
	 * @return inner list of keys
	 */
	protected final List<ITerminalKey> getKeysOrNull()
	{
		if (keys == null)
		{
			return null;
		}
		return keys;
	}

	@Override
	public void resetKeys()
	{
		setKeys(null);
		getKeys(); // make sure keys are refreshed
	}

	@Override
	public final void setKeys(final Collection<? extends ITerminalKey> keysNew)
	{
		final boolean changed = setKeysNoFire(keysNew);

		// Fire property KeysChanged
		if (changed)
		{
			fireKeysChangedEvent();
		}
	}

	private ITerminalContextReferences internalReferences = null;

	/**
	 * Helper method for subclasses.
	 * Disposes this instance's internal {@link ITerminalContextReferences}, then creates a new one.
	 * Then it actually invokes the given callable and finally detaches the newly created references.
	 * This way, the references is owned by this keyLayout.
	 * The components that belong to this keyLayout (whether this keyLayout directly knows them or not) will be disposed when they aren't needed anymore while this keyLayout itself can live on.
	 *
	 * @param f the code that (re)creates and (re)sets this keyLayout's keys by calling {@link #setKeys(Collection)}.
	 * @see ITerminalContext#detachReferences(ITerminalContextReferences).
	 * @task https://github.com/metasfresh/metasfresh/issues/458
	 */
	protected void disposeCreateDetachReverences(final Callable<Void> f)
	{
		if (internalReferences != null)
		{
			internalReferences.dispose();
		}
		internalReferences = getTerminalContext().newReferences();

		try
		{
			f.call();
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		finally
		{
			getTerminalContext().detachReferences(internalReferences);
		}

	}

	/**
	 * @param keysNew
	 * @return true if keysNew were actually set; false if there was no change
	 */
	private final boolean setKeysNoFire(final Collection<? extends ITerminalKey> keysNew)
	{
		assertNotDisposed();

		final boolean emptyOld = keys == null || keys.isEmpty();
		final boolean emptyNew = keysNew == null || keysNew.isEmpty();
		if (emptyOld && emptyNew)
		{
			// there are no old keys and there are no new keys
			return false;
		}

		// disposing anything is not this method's job!
		// the keys might be used in other parts of the component.
		// DisposableHelper.disposeAll(keys);

		// Remove listeners from old keys
		removeCurrentListeners(keys);

		// don't dispose the old keys! that they might not be used anymore in this KeyLayout (btw, we don't know that; they might be also included in keysNew)
		// does not mean they aren't used at all. They might still be used elsewhere.
		// So, instead use TerminalContext-References
		// DisposableHelper.disposeAll(keys);

		// Set new keys
		if (keysNew == null)
		{
			keys = null;
		}
		else
		{
			keys = new ArrayList<ITerminalKey>(keysNew);

			// Check a common mistake: more then one key has the same ID
			final Set<String> keyIds = new HashSet<>();
			for (final Iterator<ITerminalKey> keysIterator = keys.iterator(); keysIterator.hasNext();)
			{
				final ITerminalKey key = keysIterator.next();
				final String keyId = key.getId();

				// Make sure we we don't have another key with same ID
				if (!keyIds.add(keyId))
				{
					final TerminalException ex = new TerminalException("More then one key has the same ID: " + keyId
							+ "\n Current key: " + key
							+ "\n Seen IDs: " + keyIds
							+ "\n Keys: " + keys);
					if (developerModeBL.isEnabled())
					{
						throw ex;
					}

					// In case we are not in developer mode, just log a WARNING and skip the duplicate.
					logger.warn(ex.getLocalizedMessage(), ex);
					keysIterator.remove();
					continue;
				}
			}
		}

		addRowsIfColumnsExceeded();

		// Add listeners to new keys
		addCurrentListeners(keys);

		return true;
	}

	public final void addKeys(final Collection<? extends ITerminalKey> keysToAdd)
	{
		if (keysToAdd == null || keysToAdd.isEmpty())
		{
			return;
		}

		assertNotDisposed();

		if (keys == null)
		{
			setKeys(keysToAdd);
			return;
		}

		boolean added = false;
		for (final ITerminalKey keyToAdd : keysToAdd)
		{
			if (keyToAdd == null)
			{
				continue;
			}

			final String keyId = keyToAdd.getId();
			if (hasKeyForId(keyId))
			{
				continue;
			}

			keys.add(keyToAdd);
			addCurrentListeners(Collections.singleton(keyToAdd));
			added = true;
		}

		addRowsIfColumnsExceeded();

		if (added)
		{
			fireKeysChangedEvent();
		}
	}

	// TODO: make it final
	public void addKey(final ITerminalKey keyToAdd)
	{
		Check.assumeNotNull(keyToAdd, "keyToAdd not null");
		addKeys(Collections.singleton(keyToAdd));
	}

	protected final void fireKeysChangedEvent()
	{
		final boolean oldValue = false;
		final boolean newValue = true;
		_listeners.firePropertyChange(IKeyLayout.PROPERTY_KeysChanged, oldValue, newValue);
	}

	/**
	 * Create {@link ITerminalKey}s. The keys created by this method are added to the terminal context using {@link ITerminalContext#addToDisposableComponents(IDisposable)}.
	 * Therefore, implementors don't need to retain a reference to their created keys and don not to take care of disposing them.
	 *
	 * @return list of terminal keys
	 */
	protected abstract List<ITerminalKey> createKeys();

	@Override
	public final boolean isEnabledKeys()
	{
		return enabledKeys;
	}

	@Override
	public final void setEnabledKeys(final boolean enabledKeys)
	{
		this.enabledKeys = enabledKeys;
	}

	@Override
	public final void addListener(final PropertyChangeListener listener)
	{
		if (_disposed)
		{
			return;
		}

		Check.assumeNotNull(listener, "listener not null");

		_listeners.removePropertyChangeListener(listener); // make sure we are not adding it twice

		_listeners.addPropertyChangeListener(listener);

		for (final ITerminalKey key : getKeys())
		{
			key.addListener(listener);
		}
	}

	@Override
	public final void removeListener(final PropertyChangeListener listener)
	{
		// If listener is null, we have nothing to do.
		if (listener == null)
		{
			return;
		}

		_listeners.removePropertyChangeListener(listener);

		// Get only the current keys.
		// We are not using #getKeys() because that method will create the keys if they not already created,
		// and in our case this is not desirable because:
		// * it's pointless, newly created keys won't have this given listener, for sure
		// * it could create unpredicable results because maybe the caller wan't to create the keys a bit later when it has all data in place.
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys != null && !keys.isEmpty())
		{
			for (final ITerminalKey key : keys)
			{
				key.removeListener(listener);
			}
		}
	}

	@Override
	public void setBasePanel(final ITerminalBasePanel base)
	{
		assertNotDisposed();
		basePanel = base;
	}

	@Override
	public ITerminalBasePanel getBasePanel()
	{
		return basePanel;
	}

	@Override
	public void setRows(final int rows)
	{
		this.rows = rows;
	}

	@Override
	public final void setAddRowsIfColumnsExceeded(final boolean addRowsIfColumnsExceeded)
	{
		_addRowsIfColumnsExceeded = addRowsIfColumnsExceeded;
	}

	private final void addRowsIfColumnsExceeded()
	{
		if (!_addRowsIfColumnsExceeded)
		{
			return;
		}

		final int keyCount = getKeysCount();
		final int columnCount = getColumns();

		final int rowsToAdd = BigDecimal.valueOf(keyCount)
				.divide(BigDecimal.valueOf(columnCount), RoundingMode.UP) // keyCount / columnCount, always rounded up
				.intValueExact();
		if (getRows() != rowsToAdd)
		{
			setRows(rowsToAdd); // increment rows as needed
		}
	}

	@Override
	public int getRows()
	{
		return rows;
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return tc;
	}

	protected final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	public final ITerminalKey findKeyByValue(final int id)
	{
		final List<ITerminalKey> keys = getKeys();
		if (keys == null || keys.isEmpty())
		{
			return null;
		}

		for (final ITerminalKey key : keys)
		{
			final KeyNamePair knp = key.getValue();
			if (knp != null && knp.getKey() == id)
			{
				return key;
			}
		}

		return null;
	}

	public final void setDefaultFont(final Font defaultFont)
	{
		this.defaultFont = defaultFont;
	}

	@Override
	public Font getDefaultFont()
	{
		if (defaultFont != null)
		{
			return defaultFont;
		}

		//
		// Fallback to terminal's default font
		return getTerminalContext().getTerminalFactory().getDefaultFieldFont();
	}

	@Override
	public Color getDefaultColor(final ITerminalKey key)
	{
		return getDefaultColor();
	}

	@Override
	public final void setDefaultColor(final Color defaultColor)
	{
		this.defaultColor = defaultColor;
	}

	public final Color getDefaultColor()
	{
		return defaultColor;
	}

	@Override
	public final void keyReturning(final ITerminalKey key)
	{
		_keyListeners.keyReturning(key);
	}

	@Override
	public final void keyReturned(final ITerminalKey key)
	{
		_keyListeners.keyReturned(key);
	}

	@Override
	public final void addTerminalKeyListener(final ITerminalKeyListener listener)
	{
		if (_disposed)
		{
			return;
		}

		_keyListeners.addTerminalKeyListener(listener);
	}

	@Override
	public final int getColumns()
	{
		return columns;
	}

	protected void setColumnsMin(final int columnsMin)
	{
		Check.assume(columnsMin > 0, "columnsMin > 0");
		this.columnsMin = columnsMin;
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public final void setColumns(final int columns)
	{
		if (columnsMin > 0 && columns < columnsMin)
		{
			throw new IllegalArgumentException("columns shall be at least " + columnsMin);
		}

		this.columns = columns;
	}

	@Override
	public final IKeyLayoutSelectionModel getKeyLayoutSelectionModel()
	{
		return selectionModel;
	}

	@Override
	public final void setSelectedKey(final ITerminalKey selectedKey)
	{
		selectionModel.onKeySelected(selectedKey);
	}

	@Override
	public final void setSelectedKeyById(final String keyId)
	{
		final ITerminalKey selectedKey = getKeyById(keyId);
		setSelectedKey(selectedKey);
	}

	@Override
	public void selectFirstKeyIfAny()
	{
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null || keys.isEmpty())
		{
			return;
		}

		final ITerminalKey firstKey = keys.get(0);
		setSelectedKey(firstKey);
	}

}
