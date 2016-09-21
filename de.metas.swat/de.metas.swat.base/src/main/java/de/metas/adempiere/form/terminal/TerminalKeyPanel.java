/**
 *
 */
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.swing.SwingUtilities;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;

/**
 * @author tsa
 *
 */
public abstract class TerminalKeyPanel implements ITerminalKeyPanel
{
	/** Logger */
	protected final Logger log = LogManager.getLogger(getClass());

	private final ITerminalContext tc;

	private String keyFixedWidth = null;
	private String keyFixedHeight = null;

	private IKeyPanelRenderer renderer = DefaultKeyPanelRenderer.instance;

	private IKeyLayout keyLayout;
	private IKeyLayoutSelectionModel keyLayoutSelectionModel;
	/** Caller */
	private ITerminalKeyListener caller;

	/** Map of KeyLayout_ID to {@link IKeyLayout} */
	private final Map<String, KeyLayoutInfo> keyLayoutInfoMap = new HashMap<String, KeyLayoutInfo>();

	/**
	 * {@link ITerminalKey} button action listener.
	 *
	 * This action listener is called when user presses a key button.
	 */
	private PropertyChangeListener buttonActionListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (ITerminalButton.PROPERTY_Action.equals(evt.getPropertyName()) && evt.getNewValue() != null)
			{
				final String keyId = evt.getNewValue().toString();
				try
				{
					onPOSKeyEvent(keyId);
				}
				catch (final AdempiereException ex)
				{
					getTerminalFactory().showWarning(TerminalKeyPanel.this, ITerminalFactory.TITLE_ERROR, ex);
					log.warn(ex.getLocalizedMessage(), ex);
				}
				catch (final Exception ex)
				{
					log.warn(ex.getLocalizedMessage(), ex);
				}
			}
		}
	};

	/**
	 * {@link IKeyLayout} (aka model) listener
	 */
	private final PropertyChangeListener keyLayoutStatusListener = new PropertyChangeListener()
	{

		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final Object source = evt.getSource();

			//
			// KeyLayout content changed
			if (IKeyLayout.PROPERTY_KeysChanged.equals(evt.getPropertyName()))
			{
				if (source instanceof IKeyLayout)
				{
					final IKeyLayout keyLayout = (IKeyLayout)source;
					onKeyLayoutContentChanged(keyLayout);
				}
			}
			//
			// Button Status changed
			else if (source instanceof ITerminalKey)
			{
				final ITerminalKey terminalKey = (ITerminalKey)source;
				final KeyLayoutInfo keyLayoutInfo = getCurrentKeyLayoutInfo();
				if (keyLayoutInfo == null)
				{
					return;
				}

				for (final TerminalKeyInfo tki : keyLayoutInfo.getKeysInfo().values())
				{
					if (tki.key == terminalKey)
					{
						updatePOSKeyButton(tki);
					}
				}
			}

		}
	};

	/**
	 * Listener for keyLayoutSelectionModel
	 */
	private final PropertyChangeListener selectionModelListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final String propertyName = evt.getPropertyName();
			if (IKeyLayoutSelectionModel.PROPERTY_KeySelectionColor.equals(propertyName))
			{
				final ITerminalKey selectedKey = keyLayoutSelectionModel.getSelectedKeyOrNull();
				final TerminalKeyInfo selectedKeyInfo = getTerminalKeyInfo(selectedKey);
				if (selectedKeyInfo != null)
				{
					unselectButtonUI(selectedKeyInfo.button);
					selectButtonUI(selectedKeyInfo.button);
				}
			}
			else if (IKeyLayoutSelectionModel.PROPERTY_SelectedKey.equals(propertyName))
			{
				final ITerminalKey selectedKeyOld = (ITerminalKey)evt.getOldValue();
				final ITerminalKey selectedKeyNew = (ITerminalKey)evt.getNewValue();
				updateSelectedKeyUI(selectedKeyOld, selectedKeyNew);
			}
			else if (IKeyLayoutSelectionModel.PROPERTY_SelectedKeys.equals(propertyName))
			{
				@SuppressWarnings("unchecked")
				final List<ITerminalKey> selectedKeysOld = (List<ITerminalKey>)evt.getOldValue();
				@SuppressWarnings("unchecked")
				final List<ITerminalKey> selectedKeyNew = (List<ITerminalKey>)evt.getNewValue();
				updateSelectedKeysUI(selectedKeysOld, selectedKeyNew);
			}
		}
	};

	private boolean disposed = false;

	protected class TerminalKeyInfo
	{
		public ITerminalKey key;
		public ITerminalButton button;
		public IKeyLayout keyLayout;

		@Override
		public String toString()
		{
			return "TerminalKeyInfo [key=" + key + ", button=" + button + ", keyLayout=" + keyLayout + "]";
		}
	}

	protected static class KeyLayoutInfo
	{
		private final IKeyLayout keyLayout;
		private IComponent keyLayoutComponent;
		private final Map<String, TerminalKeyInfo> keysInfoMap = new HashMap<String, TerminalKeyInfo>();

		public KeyLayoutInfo(final IKeyLayout keyLayout)
		{
			super();
			Check.assumeNotNull(keyLayout, "keyLayout not null");
			this.keyLayout = keyLayout;
		}

		public IKeyLayout getKeyLayout()
		{
			return keyLayout;
		}

		public IComponent getKeyLayoutComponent()
		{
			return keyLayoutComponent;
		}

		public void setKeyLayoutComponent(final IComponent keyLayoutComponent)
		{
			this.keyLayoutComponent = keyLayoutComponent;
		}

		/**
		 *
		 * @return KeyID to {@link TerminalKeyInfo} map; never return null
		 */
		public Map<String, TerminalKeyInfo> getKeysInfo()
		{
			return keysInfoMap;
		}
	}

	protected TerminalKeyPanel(final ITerminalContext tc,
			final IKeyLayout keyLayout,
			final ITerminalKeyListener caller)
	{
		// Creates panel without size restrictions.
		this(tc, keyLayout, caller, ITerminalKeyPanel.KEYFIXEDSIZE_Auto, ITerminalKeyPanel.KEYFIXEDSIZE_Auto);
	}

	protected TerminalKeyPanel(final ITerminalContext tc,
			final IKeyLayout keyLayout,
			final ITerminalKeyListener caller,
			final String fixedButtonHeight,
			final String fixedButtonWidth)
	{
		super();
		Check.assumeNotNull(tc, "tc not null");
		this.tc = tc;

		Check.assumeNotNull(keyLayout, "keyLayout not null");
		setKeyLayout(keyLayout);

		this.caller = caller;
		keyFixedWidth = fixedButtonWidth;
		keyFixedHeight = fixedButtonHeight;

		init();

		tc.addToDisposableComponents(this);
	}

	protected void init()
	{
		addPOSKeyLayout(keyLayout);
		showPOSKeyKayout(keyLayout);
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		buttonActionListener = null;

		 // not created here, so we don't dispose them
		caller = NullTerminalKeyListener.instance;
		renderer = DefaultKeyPanelRenderer.instance;

		keyLayout = null;

		if (keyLayoutInfoMap != null)
		{
			keyLayoutInfoMap.clear();
		}
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public void setRenderer(final IKeyPanelRenderer renderer)
	{
		if (this.renderer == renderer)
		{
			// nothing changed
			return;
		}

		Check.assumeNotNull(renderer, "renderer not null");
		this.renderer = renderer;

		updateUI();
	}

	/**
	 * Update UI: i.e. re-render all buttons
	 */
	private void updateUI()
	{
		for (final KeyLayoutInfo keyLayoutInfo : keyLayoutInfoMap.values())
		{
			for (final TerminalKeyInfo keyInfo : keyLayoutInfo.getKeysInfo().values())
			{
				updatePOSKeyButton(keyInfo);
			}
		}
	}

	@Override
	public IKeyPanelRenderer getRenderer()
	{
		return renderer;
	}

	protected final void setKeyLayout(final IKeyLayout keyLayout)
	{
		final IKeyLayoutSelectionModel keyLayoutSelectionModelOld = keyLayoutSelectionModel;

		final ITerminalKey selectedKeyOld;
		final List<ITerminalKey> selectedKeysOld = new ArrayList<ITerminalKey>();
		if (keyLayoutSelectionModelOld == null)
		{
			//
			// Nothing to copy
			selectedKeyOld = null;
		}
		else if (!keyLayoutSelectionModelOld.isAllowMultipleSelection())
		{
			//
			// Single selection supported; use it's selected key
			selectedKeyOld = keyLayoutSelectionModelOld.getSelectedKeyOrNull();
			keyLayoutSelectionModelOld.removePropertyChangeListener(selectionModelListener);
		}
		else
		{
			//
			// Multiple selection supported; use the already-selected keys; flag the old key as null
			selectedKeyOld = null;
			selectedKeysOld.addAll(keyLayoutSelectionModelOld.getSelectedKeys());
			keyLayoutSelectionModelOld.removePropertyChangeListener(selectionModelListener);
		}

		this.keyLayout = keyLayout;

		//
		// Get or create new selection model
		keyLayoutSelectionModel = getCreateKeyLayoutSelectionModel(this.keyLayout, keyLayoutSelectionModelOld);
		// final ITerminalKey selectedKeyNew = keyLayoutSelectionModel.getSelectedKey();
		keyLayoutSelectionModel.addPropertyChangeListener(selectionModelListener);

		final boolean allowMultipleSelectionNew = keyLayoutSelectionModel.isAllowMultipleSelection();
		if (!allowMultipleSelectionNew)
		{
			final ITerminalKey selectedKeyNew = keyLayoutSelectionModel.getSelectedKeyOrNull();
			setKeyLayoutSingle(selectedKeyNew != null ? selectedKeyNew : selectedKeyOld);
		}
		else if (allowMultipleSelectionNew)
		{
			setKeyLayoutMultiple(selectedKeysOld);
		}
	}

	private static IKeyLayoutSelectionModel getCreateKeyLayoutSelectionModel(
			final IKeyLayout keyLayout,
			final IKeyLayoutSelectionModel keyLayoutSelectionModelOld)
	{
		final IKeyLayoutSelectionModel keyLayoutSelectionModel;
		if (keyLayout instanceof IKeyLayoutSelectionModel)
		{
			keyLayoutSelectionModel = (IKeyLayoutSelectionModel)keyLayout;
		}
		else if (keyLayout instanceof IKeyLayoutSelectionModelAware)
		{
			final IKeyLayoutSelectionModelAware keyLayoutSelectionModelAware = (IKeyLayoutSelectionModelAware)keyLayout;
			keyLayoutSelectionModel = keyLayoutSelectionModelAware.getKeyLayoutSelectionModel();
		}
		else
		{
			keyLayoutSelectionModel = new DefaultKeyLayoutSelectionModel(keyLayout.getTerminalContext());
		}

		// Layout selection properties must be copied to the newly-created selection model
		if (keyLayoutSelectionModelOld != null)
		{
			keyLayoutSelectionModel.setAllowMultipleSelection(keyLayoutSelectionModelOld.isAllowMultipleSelection());
		}

		return keyLayoutSelectionModel;
	}

	private void setKeyLayoutSingle(final ITerminalKey selectedKeyOld)
	{
		final ITerminalKey selectedKeyNew = setSelectedKeyNewFromOld(selectedKeyOld);
		updateSelectedKeyUI(selectedKeyOld, selectedKeyNew);
		// keyLayoutSelectionModel.setSelectedKey(null);
	}

	private void setKeyLayoutMultiple(final List<ITerminalKey> selectedKeysOld)
	{
		for (final ITerminalKey selectedKeyOld : selectedKeysOld)
		{
			setKeyLayoutSingle(selectedKeyOld);
			// keyLayoutSelectionModel.setSelectedKey(null);
		}
	}

	@Override
	public final IKeyLayout getKeyLayout()
	{
		return keyLayout;
	}

	private final KeyLayoutInfo getCurrentKeyLayoutInfo()
	{
		if (keyLayout == null)
		{
			return null;
		}
		return keyLayoutInfoMap.get(keyLayout.getId());
	}

	/**
	 * Creates {@link KeyLayoutInfo} and adds it to {@link #keyLayoutInfoMap}.
	 *
	 * @param keyLayout
	 * @return created {@link KeyLayoutInfo}
	 */
	private KeyLayoutInfo createKeyLayoutInfo(final IKeyLayout keyLayout)
	{
		if (keyLayout == null)
		{
			return null;
		}

		// Recursive call protection
		final String keyLayoutId = keyLayout.getId();
		if (keyLayoutInfoMap.containsKey(keyLayoutId))
		{
			return null;
		}

		final KeyLayoutInfo keyLayoutInfo = new KeyLayoutInfo(keyLayout);
		keyLayoutInfoMap.put(keyLayoutId, keyLayoutInfo);

		final int ROWS = getRows();

		int cols = keyLayout.getColumns();
		if (cols <= 0)
		{
			cols = IKeyLayout.DEFAULT_COLUMN_COUNT; // Default columns
		}

		int buttons = 0;

		// Content
		final IContainer content = createPOSKeyContent(cols);
		for (final ITerminalKey terminalKey : keyLayout.getKeys())
		{
			final IKeyLayout subKeyLayout = terminalKey.getSubKeyLayout();
			if (subKeyLayout != null)
			{
				addPOSKeyLayout(subKeyLayout);
			}

			final TerminalKeyInfo keyInfo = new TerminalKeyInfo();
			keyInfo.keyLayout = keyLayout;
			keyInfo.key = terminalKey;
			keyLayoutInfo.getKeysInfo().put(terminalKey.getId(), keyInfo);

			keyInfo.button = createPOSKeyButton(terminalKey);
			updatePOSKeyButton(keyInfo);

			final int size = addPOSKeyButton(content, keyInfo.button, terminalKey);
			buttons = buttons + size;
		}

		int rows = Math.max(buttons / cols, ROWS);
		if (buttons > rows * cols)
		{
			rows = rows + 1;
		}
		// Make sure we have at least one row, even if there are no rows, else the display will look ugly
		if (rows < 1)
		{
			rows = 1;
		}

		for (int i = buttons; i < rows * cols; i++)
		{
			addPOSKeyButtonEmpty(content);
		}

		final IComponent keyLayoutComp = createPOSKeyComponent(content);
		keyLayoutInfo.setKeyLayoutComponent(keyLayoutComp);

		//
		// Add event listener after invoking "keyLayout.getKeys()" because that one will fire another UI refresh event
		// Else we will get some weird errors and more then one panels will overlap
		keyLayout.addListener(keyLayoutStatusListener);

		return keyLayoutInfo;
	}

	private ITerminalButton createPOSKeyButton(final ITerminalKey key)
	{
		final String captionHtml = ""; // it will updated later in updatePOSKeyButton
		final ITerminalButton button = getTerminalFactory().createButton(captionHtml);
		button.addListener(buttonActionListener);
		return button;
	}

	/**
	 * Update button's UI (e.g. text, font, border etc).
	 *
	 * @param button
	 * @param updater
	 */
	protected final void updatePOSKeyButtonUI(final ITerminalButton button, final Runnable updater)
	{
		// NOTE: we delay calling this method because:
		// * it would involve some calculations (i.e. building button text) and maybe the button is not even visible to user
		// * executeBeforePainingSupport is removing duplicate events, so we will update our components once
		// * we need to do this for any small button UI update because else, first direct component update will trigger the enqueued updaters to be executed

		// final IExecuteBeforePainingSupport executeBeforePainingSupport = button; // TODO - will fuck up kommissionier terminal login panel; if optimizing like this, make sure to test that too
		final IExecuteBeforePainingSupport executeBeforePainingSupport = DirectExecuteBeforePainingSupport.instance;

		executeBeforePainingSupport.executeBeforePainting(updater);
	}

	/**
	 * Calls {@link #updatePOSKeyButtonNow(TerminalKeyInfo)} via {@link #updatePOSKeyButtonUI(ITerminalButton, Runnable)}.
	 *
	 * @param keyInfo
	 */
	private void updatePOSKeyButton(final TerminalKeyInfo keyInfo)
	{
		updatePOSKeyButtonUI(keyInfo.button, new Runnable()
		{
			/** Consider equals when they have the same class. We do this to remove duplicate updaters */
			@Override
			public boolean equals(final Object obj)
			{
				if (this == obj)
				{
					return true;
				}
				if (obj == null)
				{
					return false;
				}
				final boolean sameClass = getClass() == obj.getClass();
				return sameClass;
			}

			@Override
			public int hashCode()
			{
				// using the Object.hashCode is just perfect for our need (different hashCodes for different objects)
				// (doing this explicitly to avoid the warning about having equals() method but not hashCode())
				return super.hashCode();
			}

			@Override
			public void run()
			{
				updatePOSKeyButtonNow(keyInfo);
			}
		});
	}

	private final void updatePOSKeyButtonNow(final TerminalKeyInfo keyInfo)
	{
		final IKeyLayout keyLayout = keyInfo.keyLayout;
		final ITerminalButton button = keyInfo.button;
		final ITerminalKey terminalKey = keyInfo.key;

		//
		// Misc
		button.setAction(terminalKey.getId());
		button.setFocusable(false);

		//
		// Enabled/Disabled
		button.setEnabled(keyLayout.isEnabledKeys()
				&& terminalKey.isActive()
				&& terminalKey.isEnabledKey());

		//
		// Button Caption (Text)
		{
			final StringBuilder captionHTML = new StringBuilder("");
			captionHTML.append("<html><p>");
			captionHTML.append(terminalKey.getName());
			captionHTML.append("</p></html>");
			button.setText(captionHTML.toString());
		}

		//
		// Debug info (text)
		if (getTerminalContext().isShowDebugInfo())
		{
			final String debugInfo = terminalKey.getDebugInfo();
			final String tooltipText;
			if (!Check.isEmpty(debugInfo, true))
			{
				tooltipText = "<html><pre>" + Util.maskHTML(debugInfo) + "</pre></html>";
			}
			else
			{
				tooltipText = null;
			}
			button.setToolTipText(tooltipText);
		}

		final IKeyPanelRenderer renderer = getRenderer();
		renderer.renderKey(button, terminalKey, keyLayout);
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public void setAllowKeySelection(final boolean allowKeySelection)
	{
		keyLayoutSelectionModel.setAllowKeySelection(allowKeySelection);
	}

	@Override
	public boolean isAllowKeySelection()
	{
		return keyLayoutSelectionModel.isAllowKeySelection();
	}

	private void setSelectedKey(final ITerminalKey key)
	{
		if (!keyLayoutSelectionModel.isAllowKeySelection())
		{
			return;
		}

		keyLayoutSelectionModel.setSelectedKey(key);
	}

	@Override
	public void onKeySelected(final ITerminalKey key)
	{
		if (!keyLayoutSelectionModel.isAllowKeySelection())
		{
			return;
		}

		keyLayoutSelectionModel.onKeySelected(key);
	}

	/**
	 * Gets {@link TerminalKeyInfo} for given <code>key</code>
	 *
	 * @param key
	 * @return {@link TerminalKeyInfo} or null
	 */
	private TerminalKeyInfo getTerminalKeyInfo(final ITerminalKey key)
	{
		if (key == null)
		{
			return null;
		}

		final KeyLayoutInfo keyLayoutInfo = getCurrentKeyLayoutInfo();
		if (keyLayoutInfo == null)
		{
			return null;
		}

		final Map<String, TerminalKeyInfo> map = keyLayoutInfo.getKeysInfo();
		for (final Map.Entry<String, TerminalKeyInfo> e : map.entrySet())
		{
			final TerminalKeyInfo keyInfo = e.getValue();
			if (keyInfo.key == key)
			{
				return keyInfo;
			}
		}

		return null;
	}

	@Override
	public List<ITerminalKey> getKeys()
	{
		final KeyLayoutInfo keyLayoutInfo = getCurrentKeyLayoutInfo();
		if (keyLayoutInfo == null)
		{
			return null;
		}

		final Map<String, TerminalKeyInfo> map = keyLayoutInfo.getKeysInfo();
		if (map == null || map.isEmpty())
		{
			return null;
		}

		final List<ITerminalKey> list = new ArrayList<ITerminalKey>();
		for (final Map.Entry<String, TerminalKeyInfo> e : map.entrySet())
		{
			final TerminalKeyInfo keyInfo = e.getValue();
			list.add(keyInfo.key);
		}
		return list;
	}

	/**
	 * Check and set new selected key based on old selected key
	 *
	 * @param selectedKeyOld
	 * @param systemAction
	 * @return
	 */
	private final ITerminalKey setSelectedKeyNewFromOld(final ITerminalKey selectedKeyOld)
	{
		boolean firePOSKeyPressed = false;
		//
		// Check if selected key is still in our keys list
		ITerminalKey selectedKeyNew;
		if (selectedKeyOld != null)
		{
			selectedKeyNew = keyLayout.getKeyById(selectedKeyOld.getId());
		}
		else
		{
			selectedKeyNew = null;
		}

		//
		// If no selected key found and "AutoSelectIfOnlyOne" was activated
		// then pick the only key if we have only one key in our layout
		if (selectedKeyNew == null
				&& keyLayoutSelectionModel.isAutoSelectIfOnlyOne()
				&& keyLayout.getKeysCount() == 1)
		{
			selectedKeyNew = keyLayout.getKeyByIndex(0);
			firePOSKeyPressed = true;
		}

		//
		// Update selected key
		if (firePOSKeyPressed)
		{
			onPOSKeyEvent(selectedKeyNew.getId());
		}
		else
		{
			setSelectedKey(selectedKeyNew);
		}
		return selectedKeyNew;
	}

	private final void updateSelectedKeyUI(final ITerminalKey selectedKeyOld, final ITerminalKey selectedKeyNew)
	{
		if (!keyLayoutSelectionModel.isAllowKeySelection())
		{
			return;
		}

		final boolean changed = !Util.same(selectedKeyOld, selectedKeyNew);
		// NOTE: We are not returning here if there is no change because we want to always refresh

		//
		// Deselect current selected key (if any) and only if there was a change
		if (changed)
		{
			final TerminalKeyInfo selectedKeyInfoOld = getTerminalKeyInfo(selectedKeyOld);
			if (selectedKeyInfoOld != null)
			{
				unselectButtonUI(selectedKeyInfoOld.button);
			}
		}

		//
		// Select new key
		final TerminalKeyInfo selectedKeyInfoNew = getTerminalKeyInfo(selectedKeyNew);
		if (selectedKeyInfoNew != null)
		{
			selectButtonUI(selectedKeyInfoNew.button);
		}
	}

	private final void updateSelectedKeysUI(final List<ITerminalKey> selectedKeysOld, final List<ITerminalKey> selectedKeysNew)
	{
		if (!keyLayoutSelectionModel.isAllowKeySelection())
		{
			return;
		}

		final List<ITerminalKey> unselectedKeys = new ArrayList<ITerminalKey>(selectedKeysOld);

		for (final ITerminalKey selectedKeyNew : selectedKeysNew)
		{
			unselectedKeys.remove(selectedKeyNew);
			final ITerminalKey selectedKeyOld = selectedKeyNew;

			updateSelectedKeyUI(selectedKeyOld, selectedKeyNew);
		}

		for (final ITerminalKey selectedKeyOld : unselectedKeys)
		{
			updateSelectedKeyUI(selectedKeyOld, null);
		}
	}

	@Override
	public ITerminalKey getSelectedKey()
	{
		return keyLayoutSelectionModel.getSelectedKeyOrNull();
	}

	@Override
	public Color getKeySelectionColor()
	{
		return keyLayoutSelectionModel.getKeySelectionColor();
	}

	@Override
	public void setKeySelectionColor(final Color color)
	{
		keyLayoutSelectionModel.setKeySelectionColor(color);
	}

	protected abstract void selectButtonUI(ITerminalButton button);

	protected abstract void unselectButtonUI(ITerminalButton button);

	/**
	 * Method called when our key layout content was changed
	 *
	 * @param keyLayout
	 */
	private void onKeyLayoutContentChanged(final IKeyLayout keyLayout)
	{
		Check.assumeNotNull(keyLayout, "keyLayout not null");

		final IKeyLayoutSelectionModel keyLayoutSelectionModel = keyLayout.getKeyLayoutSelectionModel();
		final boolean allowMultipleSelection = keyLayoutSelectionModel.isAllowMultipleSelection();

		//
		// Get old Selected Key
		final ITerminalKey selectedKeyOld;
		if (allowMultipleSelection)
		{
			// If multiple keys are supported, do not select old keys
			selectedKeyOld = null;
		}
		else
		{
			// Get the old selected key (if any)
			selectedKeyOld = getSelectedKey();
		}

		//
		// Remove/Add/Display current keyLayout
		// NOTE: this is not efficient but is the only way now to fully redraw all keys
		removeLayout(keyLayout.getId());
		addPOSKeyLayout(keyLayout);
		showPOSKeyKayout(keyLayout);

		//
		// Update Selected Key (if any)
		if (allowMultipleSelection)
		{
			//
			// Get old selected keys (clone) & clear current selection model
			final List<ITerminalKey> selectedKeysOld = new ArrayList<ITerminalKey>(keyLayoutSelectionModel.getSelectedKeys());
			keyLayoutSelectionModel.clearSelection();

			for (final ITerminalKey selectedKeyOld0 : selectedKeysOld)
			{
				if (selectedKeyOld0 == null)
				{
					continue;
				}
				final ITerminalKey newSelectableKey = keyLayout.getKeyById(selectedKeyOld0.getId());
				if (newSelectableKey == null)
				{
					continue;
				}

				//
				// Select the key again if found in the new select-able keys (as system, directly)
				keyLayoutSelectionModel.setSelectedKey(newSelectableKey);
			}
		}
		else
		{
			setSelectedKeyNewFromOld(selectedKeyOld);
		}
	}

	/**
	 * Called when user pressed a Key
	 *
	 * @param keyId
	 */
	protected void onPOSKeyEvent(final String keyId)
	{
		final KeyLayoutInfo keyLayoutInfo = getCurrentKeyLayoutInfo();
		if (keyLayoutInfo == null)
		{
			return;
		}

		final Map<String, TerminalKeyInfo> currentKeymap = keyLayoutInfo.getKeysInfo();
		final TerminalKeyInfo keyInfo = currentKeymap.get(keyId);
		final ITerminalKey key = keyInfo.key;

		//
		// Switch layout
		final IKeyLayout subKeyLayout = key.getSubKeyLayout();
		if (subKeyLayout != null)
		{
			showPOSKeyKayout(subKeyLayout);
		}
		//
		// Regular key was pressed
		else
		{
			// Fire listeners (model level): Key Returning
			if (keyLayout != null)
			{
				keyLayout.keyReturning(key);
			}

			keyLayoutSelectionModel.onKeySelected(key);

			// Fire listeners (view level)
			// NOTE: this is deprecated but we keep it here for backward compatibility
			if (caller != null)
			{
				caller.keyReturned(key);
			}

			// Fire listeners (model level): Key Returned
			if (keyLayout != null)
			{
				keyLayout.keyReturned(key);
			}
		}
	}

	@Override
	public void fireKeyPressed(final String keyId)
	{
		onPOSKeyEvent(keyId);
	}

	@Override
	public final void addPOSKeyLayout(final IKeyLayout keyLayout)
	{
		//
		// Create Key Layout Info and add it to map
		final KeyLayoutInfo keyLayoutInfo = createKeyLayoutInfo(keyLayout);
		if (keyLayoutInfo == null)
		{
			// already added
			return;
		}

		// UI
		addPOSKeyLayoutUI(keyLayoutInfo);

		//
		// After UI component creation & binding, set the model's dimensions accordingly
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				for (final TerminalKeyInfo keyInfo : keyLayoutInfo.getKeysInfo().values())
				{
					final ITerminalButton terminalButton = keyInfo.button;
					final ITerminalKey terminalKey = keyInfo.key;
					terminalKey.setWidth(terminalButton.getWidth());
					terminalKey.setHeight(terminalButton.getHeight());
				}
			}
		});
	}

	@Override
	public void removeLayout(final String keyLayoutId)
	{
		final KeyLayoutInfo keyLayoutInfo = keyLayoutInfoMap.remove(keyLayoutId);
		if (keyLayoutInfo == null)
		{
			// KeyLayout was not added, nothing to do
			return;
		}

		// Remove status listener
		final IKeyLayout keyLayout = keyLayoutInfo.getKeyLayout();
		keyLayout.removeListener(keyLayoutStatusListener);

		// UI
		removeKeyLayoutUI(keyLayoutInfo);
	}

	@Override
	public void showPOSKeyKayout(final IKeyLayout keyLayout)
	{
		setKeyLayout(keyLayout);
		showKeyKayoutUI(keyLayout);
	}

	protected abstract void addPOSKeyLayoutUI(final KeyLayoutInfo keyLayoutInfo);

	protected abstract void removeKeyLayoutUI(final KeyLayoutInfo keyLayoutInfo);

	protected abstract void showKeyKayoutUI(IKeyLayout keyLayout);

	/**
	 * Creates UI container for Key buttons
	 *
	 * @param cols
	 * @return container
	 */
	protected abstract IContainer createPOSKeyContent(int cols);

	/**
	 * Wraps given UI keys container, adds scrolls if needed and returns the new component
	 *
	 * @param content UI keys container (which was created by {@link #createPOSKeyContent(int)})
	 * @return created component
	 */
	protected abstract IComponent createPOSKeyComponent(IContainer content);

	/**
	 * Adds an empty button (as a placeholder) to given keys content
	 *
	 * @param content keys content (created by {@link #createPOSKeyContent(int)}
	 */
	protected abstract void addPOSKeyButtonEmpty(IContainer content);

	/**
	 * Adds given <code>button</code> key to key container.
	 *
	 * @param content key container (which was created by {@link #createPOSKeyContent(int)})
	 * @param button button to add
	 * @param key button's associated {@link ITerminalKey}
	 * @return how many grid cells were occupied by button's component (this depends on {@link ITerminalKey#getSpanX()} and {@link ITerminalKey#getSpanY()}).
	 */
	protected abstract int addPOSKeyButton(IContainer content, ITerminalButton button, ITerminalKey key);

	public int getRows()
	{
		return keyLayout.getRows();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	public ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
	}

	@Override
	public String getKeyFixedWidth()
	{
		return keyFixedWidth;
	}

	@Override
	public void setKeyFixedWidth(final String keyFixedWidth)
	{
		if (Check.equals(this.keyFixedWidth, keyFixedWidth))
		{
			// nothing changed
			return;
		}

		this.keyFixedWidth = keyFixedWidth;

		//
		// Notify that the whole key layout shall be re-rendered
		final IKeyLayout keyLayout = getKeyLayout();
		if (keyLayout != null)
		{
			onKeyLayoutContentChanged(keyLayout);
		}
	}

	@Override
	public String getKeyFixedHeight()
	{
		return keyFixedHeight;
	}

	@Override
	public void setKeyFixedHeight(final String keyFixedHeight)
	{
		if (Check.equals(this.keyFixedHeight, keyFixedHeight))
		{
			// nothing changed
			return;
		}

		this.keyFixedHeight = keyFixedHeight;

		// Notify that the whole key layout shall be re-rendered
		final IKeyLayout keyLayout = getKeyLayout();
		if (keyLayout != null)
		{
			onKeyLayoutContentChanged(keyLayout);
		}
	}
}
