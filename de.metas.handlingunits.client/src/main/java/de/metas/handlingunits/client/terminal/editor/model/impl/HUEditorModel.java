package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.base.Supplier;

import de.metas.adempiere.form.terminal.BreadcrumbKeyLayout;
import de.metas.adempiere.form.terminal.CompositePropertiesPanelModel;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IPropertiesPanelModel;
import de.metas.adempiere.form.terminal.IPropertiesPanelModelConfigurator;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.PropertiesPanelModelConfigurator;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.client.terminal.editor.model.HUKeyVisitorAdapter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.ISplittableHUKey;
import de.metas.handlingunits.client.terminal.helper.HUTerminalHelper;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.assign.impl.HUAssignTULUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.distribute.impl.HUDistributeCUTUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.join.impl.HUJoinModel;
import de.metas.handlingunits.client.terminal.mmovement.model.split.impl.HUSplitModel;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;

public class HUEditorModel implements IDisposable
{
	private static final transient Logger logger = LogManager.getLogger(HUEditorModel.class);

	// services
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String SYSCONFIG_HUKeyFilterEnabled = "de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel.HUKeyFilterEnabled";
	private static final boolean DEFAULT_HUKeyFilterEnabled = false;

	private final ITerminalContext _terminalContext;
	private final WeakPropertyChangeSupport pcs;
	private final BreadcrumbKeyLayout breadcrumbKeyLayout;
	private final PropertyChangeListener breadcrumbKeyLayoutListener; // NOTE: keep a hard-reference to make sure is not GCed
	private final HUFilterPropertiesModel _huKeyFilterModel;
	private final HUKeyLayout handlingUnitsKeyLayout;
	private final CompositePropertiesPanelModel propertiesPanelModel;
	private boolean attributesEditableOnlyIfVHU = false;

	private IHUKey rootHUKey;
	private IHUKey _currentKey;
	private final HUKeyChangesTracker changesTracker = new HUKeyChangesTracker();

	/**
	 * Selected {@link IHUKey}s.
	 *
	 * Map: "HU Key Id" to {@link IHUKey}.
	 */
	private final Map<String, IHUKey> _selectedKeyIds = new HashMap<String, IHUKey>();

	private boolean displayBarcode = true;
	private boolean updateHUAllocationsOnSave = true;

	/**
	 * Event fired when current key changed
	 */
	public static final String PROPERTY_CurrentKey = "CurrentKey";

	/**
	 * Event fired when {@link IHUKey}s selection changed.
	 *
	 * Event details:
	 * <ul>
	 * <li>Old value: false
	 * <li>New value: true
	 * </ul>
	 */
	public static final String PROPERTY_HUKeySelectionChanged = "HUKeySelectionChanged";

	public static final Predicate<IHUKey> HUKeyFilter_WeightableButNotWeighted = new Predicate<IHUKey>()
	{
		@Override
		public boolean evaluate(final IHUKey huKey)
		{
			// guard agaist null
			if (huKey == null)
			{
				return false;
			}

			final IWeightable weight = huKey.getWeightOrNull();

			// if there is no weightable support, don't accept it
			if (weight == null)
			{
				return false;
			}
			if (!weight.isWeightable())
			{
				return false;
			}

			// don't accept those which are already weighted
			if (weight.isWeighted())
			{
				return false;
			}

			// Accept it: we deal with a weightable but not weight HU key
			return true;
		}
	};

	//
	// Misc options
	private boolean allowSelectingReadonlyKeys = false;

	private boolean disposed = false;

	/**
	 * Creates a new instance and adds itself to the given <code>terminalContext</code>'s disposable components.
	 *
	 * @param terminalContext
	 */
	public HUEditorModel(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		_terminalContext = terminalContext;

		// Reset the whole cache before starting the HU Editor, just to make sure we are are not working with stale data.
		// NOTE: even though it's not smart to call methods from ctor, in this case only "terminalContext" is needed
		clearCache();

		//
		// Configure default terminal context properties
		terminalContext.setProperty(ILTCUModel.PROPERTY_AllowSameTUInfiniteCapacity, false);

		//
		// Configure terminal context available services
		terminalContext.registerService(IPropertiesPanelModelConfigurator.class, new PropertiesPanelModelConfigurator(terminalContext, getClass().getName()));

		pcs = terminalContext.createPropertyChangeSupport(this, true); // weakDefault=true

		breadcrumbKeyLayout = new BreadcrumbKeyLayout(terminalContext);
		breadcrumbKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturning(final ITerminalKey key)
			{
				saveHUProperties();
			}

			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final IHUKey huKey = (IHUKey)key;
				onBreadcrumbKeyPressed(huKey);
			}
		});

		//
		// Listener to clear breadcrumb key layout
		breadcrumbKeyLayoutListener = new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				if (IHUKey.ACTION_GroupingRemoved.equals(evt.getPropertyName()))
				{
					cleanupBreadcrumbGroupingKeys();
				}
			}
		};
		breadcrumbKeyLayout.addListener(breadcrumbKeyLayoutListener);

		//
		// HU Key Filters
		if (Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_HUKeyFilterEnabled, DEFAULT_HUKeyFilterEnabled))
		{
			final Runnable callback = new Runnable()
			{
				@Override
				public void run()
				{
					setCurrentHUKey(rootHUKey);
				}
			};
			_huKeyFilterModel = new HUFilterPropertiesModel(terminalContext, callback);
		}
		else
		{
			_huKeyFilterModel = null;
		}

		handlingUnitsKeyLayout = new HUKeyLayout(terminalContext);
		handlingUnitsKeyLayout.setRows(3);
		handlingUnitsKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturning(final ITerminalKey key)
			{
				saveHUProperties();
			}

			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final IHUKey huKey = (IHUKey)key;
				onHUKeyPressed(huKey);

				if (_huKeyFilterModel != null)
				{
					_huKeyFilterModel.commitEdit();
				}
			}
		});

		//
		// 07618: Resize font on HU keys to make them fit more stuff in them
		HUTerminalHelper.decreaseKeyLayoutFont(breadcrumbKeyLayout);
		HUTerminalHelper.decreaseKeyLayoutFont(handlingUnitsKeyLayout);

		//
		// Properties
		propertiesPanelModel = new CompositePropertiesPanelModel(terminalContext);
		propertiesPanelModel.setSaveChildrenOnRemove(true);
		propertiesPanelModel.setDisposeChildrenOnRemove(true);

		terminalContext.addToDisposableComponents(this);
	}

	/**
	 * Registers given listener (weak).
	 *
	 * Make sure you keep a reference to that listener if you don't want to be GCed.
	 *
	 * @param propertyName
	 * @param listener
	 */
	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		dispose();
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		if (isDisposed())
		{
			return; // nothing to do
		}
		// pcs.clear(); // not needed since pcs was created by the terminalContext

		// not needed since they added themselves as disposable components in their constructors
		// DisposableHelper.disposeAll(breadcrumbKeyLayout, _huKeyFilterModel, handlingUnitsKeyLayout, propertiesPanelModel);

		rootHUKey = null;
		_currentKey = null;
		_selectedKeyIds.clear();

		clearCache();
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	private void onBreadcrumbKeyPressed(final IHUKey huKey)
	{
		setCurrentHUKey(huKey);
	}

	private void onHUKeyPressed(final IHUKey huKey)
	{
		if (huKey != null)
		{
			huKey.notifyKeyPressed();
		}
		setCurrentHUKey(huKey);
	}

	/**
	 * @return terminal context; never returns null
	 */
	public final ITerminalContext getTerminalContext()
	{
		return _terminalContext;
	}

	public final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	protected final IHUKeyFactory getHUKeyFactory()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		return keyFactory;
	}

	public void setRootHUKey(final IHUKey rootHUKey)
	{
		Check.assumeNotNull(rootHUKey, "rootHUKey not null");
		this.rootHUKey = rootHUKey;
		clearSelectedKeyIds();
		changesTracker.set(rootHUKey);

		setCurrentHUKey(rootHUKey);

		//
		// Initialize filter's root key
		if (_huKeyFilterModel != null)
		{
			_huKeyFilterModel.setHUKey(rootHUKey);
		}
	}

	public IHUKey getRootHUKey()
	{
		return rootHUKey;
	}

	private final List<I_M_HU> _originalTopLevelHUs = new ArrayList<>();

	public final void setOriginalTopLevelHUs(final Collection<I_M_HU> hus)
	{
		_originalTopLevelHUs.clear();
		_originalTopLevelHUs.addAll(hus);
	}

	private final List<I_M_HU> getOriginalTopLevelHUs()
	{
		return _originalTopLevelHUs;
	}

	/**
	 *
	 * @param huKey
	 * @return <code>true</code> if the given <code>huKey</code> is not <code>null</code> an has children.
	 */
	public boolean canDetail(final IHUKey huKey)
	{
		if (huKey == null)
		{
			return false;
		}

		return huKey.hasChildren();
	}

	public void setCurrentHUKey(final IHUKey currentKey)
	{
		if (currentKey == null)
		{
			return;
		}

		final IHUKey currentKeyOld = getCurrentHUKey();
		if (canDetail(currentKey))
		{
			// Make sure our breadcrumb element is the last one
			breadcrumbKeyLayout.setLastItem(currentKey);

			handlingUnitsKeyLayout.setParentHUKey(currentKey);
		}
		else
		{
			breadcrumbKeyLayout.setSelectedKey(null);
		}

		this._currentKey = currentKey;

		//
		// Update Properties Panel Model
		loadHUProperties(currentKey);

		//
		// Fire property changed
		pcs.firePropertyChange(HUEditorModel.PROPERTY_CurrentKey, currentKeyOld, this._currentKey);
	}

	private void cleanupBreadcrumbGroupingKeys()
	{
		final List<ITerminalKey> oldKeys = new ArrayList<ITerminalKey>(breadcrumbKeyLayout.getKeys());
		final Iterator<ITerminalKey> oldKeysIterator = oldKeys.iterator();

		boolean changed = false;
		while (oldKeysIterator.hasNext())
		{
			final ITerminalKey oldKey = oldKeysIterator.next();
			if (!(oldKey instanceof IHUKey))
			{
				continue; // only applied to HUKeys
			}

			final IHUKey oldHUKey = (IHUKey)oldKey;
			if (oldHUKey.hasChildren())
			{
				continue; // only applied when key doesn't have any more children
			}

			if (!oldKeysIterator.hasNext())
			{
				continue; // only applied when key doesn't have any more items after it
			}
			oldKeysIterator.remove(); // remove current element because it's no longer used
			changed = true;
		}

		if (changed)
		{
			breadcrumbKeyLayout.resetKeys();
			for (final ITerminalKey newKey : oldKeys)
			{
				breadcrumbKeyLayout.setLastItem(newKey);
			}
		}
	}

	public IHUKey getCurrentHUKey()
	{
		return _currentKey;
	}

	public IKeyLayout getBreadcrumbKeyLayout()
	{
		return breadcrumbKeyLayout;
	}

	/**
	 * @return {@link HUFilterPropertiesModel} or <code>null</code>
	 */
	public HUFilterPropertiesModel getHUKeyFilterModel()
	{
		return _huKeyFilterModel;
	}

	public IKeyLayout getHandlingUnitsKeyLayout()
	{
		return handlingUnitsKeyLayout;
	}

	public IPropertiesPanelModel getPropertiesPanelModel()
	{
		return propertiesPanelModel;
	}

	private ISplittableHUKey getCurrentSplittableHUKey()
	{
		final IHUKey key = getCurrentHUKey();
		Check.assumeNotNull(key, "key not null");

		//
		// Only splittable HUKeys are allowed to be split
		if (!(key instanceof ISplittableHUKey))
		{
			throw new AdempiereException("Cannot split " + key.getName()); // TODO TRL
		}

		//
		// Splitting a readonly HUKey is not allowed
		if (key.isReadonly())
		{
			throw new AdempiereException("Cannot split " + key.getName()); // TODO TRL
		}

		final ISplittableHUKey splittableHUKey = (ISplittableHUKey)key;
		return splittableHUKey;
	}

	private HUAssignTULUModel createAssignTULUModel()
	{
		final Set<HUKey> selectedKeys = getSelectedHUKeys();
		final HUAssignTULUModel assignLUTUModel = new HUAssignTULUModel(getTerminalContext(), getRootHUKey(), selectedKeys);
		return assignLUTUModel;
	}

	public void doAssignTULU(final Predicate<HUAssignTULUModel> editorCallback)
	{
		Check.assumeNotNull(editorCallback, "editorCallback not null");
		final HUAssignTULUModel assignLUTUModel = createAssignTULUModel();

		//
		// Do nothing & keep selection if the user cancelled
		final boolean edited = editorCallback.evaluate(assignLUTUModel);
		if (!edited)
		{
			return;
		}

		//
		// Refresh root key regardless of outcome after user presses OK
		assignLUTUModel.refreshRootKey();

		//
		// Remove previous selection
		clearSelectedKeyIds();

		//
		// Navigate back to root
		setCurrentHUKey(getRootHUKey());
	}

	private HUDistributeCUTUModel createDistributeCUTUModel()
	{
		final ISplittableHUKey currentKey = getCurrentSplittableHUKey();
		final IHUKey rootHUKey = getRootHUKey();
		final HUDistributeCUTUModel distributeCUTUModel = new HUDistributeCUTUModel(getTerminalContext(), rootHUKey, currentKey);

		//
		// Add available TUs to split on
		final Set<HUKey> selectedHUKeys = getSelectedHUKeys();
		if (selectedHUKeys.isEmpty())
		{
			distributeCUTUModel.addTUKeysFrom(Collections.singleton(rootHUKey));
		}
		else
		{
			distributeCUTUModel.addTUKeysFrom(selectedHUKeys);
		}

		return distributeCUTUModel;
	}

	public void doDistributeCUTU(final Predicate<HUDistributeCUTUModel> editorCallback)
	{
		Check.assumeNotNull(editorCallback, "editorCallback not null");
		final HUDistributeCUTUModel distributeCUTUModel = createDistributeCUTUModel();

		//
		// Do nothing & keep selection if the user cancelled
		final boolean edited = editorCallback.evaluate(distributeCUTUModel);
		if (!edited)
		{
			return;
		}

		//
		// Remove previous selection
		clearSelectedKeyIds();

		//
		// Navigate back to root
		setCurrentHUKey(getRootHUKey());
	}

	private HUSplitModel createSplitModel()
	{
		final ISplittableHUKey splittableHUKey = getCurrentSplittableHUKey();

		final HUSplitModel splitModel = new HUSplitModel(getTerminalContext(), splittableHUKey);
		return splitModel;
	}

	public void doSplit(final Predicate<HUSplitModel> editorCallback)
	{
		Check.assumeNotNull(editorCallback, "editorCallback not null");
		final HUSplitModel splitModel = createSplitModel();

		final boolean edited = editorCallback.evaluate(splitModel);
		if (!edited)
		{
			//
			// If user cancelled, do nothing
			return;
		}

		//
		// Split was performed, so all created keys were already added to the Root, so we are navigating there
		setCurrentHUKey(getRootHUKey());

		// Finally, clear selection.
		clearSelectedKeyIds();
	}

	private HUJoinModel createJoinModel()
	{
		final Set<HUKey> selectedKeys = getSelectedHUKeys();
		final HUJoinModel joinModel = new HUJoinModel(getTerminalContext(), getRootHUKey(), selectedKeys);
		return joinModel;
	}

	public void doJoin(final Predicate<HUJoinModel> editorCallback)
	{
		Check.assumeNotNull(editorCallback, "editorCallback not null");
		final HUJoinModel joinModel = createJoinModel();

		editorCallback.evaluate(joinModel);

		//
		// Refresh root key regardless of outcome (user might have just merged!)
		joinModel.refreshRootKey();

		//
		// Remove previous selection
		clearSelectedKeyIds();

		//
		// Navigate back to root
		setCurrentHUKey(getRootHUKey());
	}

	/**
	 * Checks if given key is selectable.
	 *
	 * A {@link IHUKey} is selectable if it's not null and is directly linked to Root (i.e. top level)
	 *
	 * @param huKey
	 * @return true if selectable
	 */
	public boolean isSelectable(final IHUKey huKey)
	{
		if (huKey == null)
		{
			return false;
		}

		//
		// Only HU Keys are selectable (because those contain actual HUs)
		if (!(huKey instanceof HUKey))
		{
			return false;
		}

		//
		// Only HU Keys which are not readonly/locked can be selected
		if (!allowSelectingReadonlyKeys && huKey.isReadonly())
		{
			return false;
		}

		//
		// Get parent key, but skip composite keys in between
		IHUKey parentKey = huKey.getParent();
		while (parentKey != null)
		{
			if (!parentKey.isGrouping())
			{
				break;
			}
			parentKey = parentKey.getParent();
		}

		if (parentKey == null)
		{
			// No Parent? shall not happen, but anyway is not selectable
			return false;
		}

		// 06989: We disable selection if one of the parent HUs has already been selected.
		if (isSelectedOrParentSelected(huKey.getParent()))
		{
			return false;
		}

		return true;
	}

	/**
	 *
	 * @param huKey
	 * @return true if given HU Key is selected
	 */
	public boolean isSelected(final IHUKey huKey)
	{
		if (!isSelectable(huKey))
		{
			return false;
		}

		final String keyId = huKey.getId();
		final boolean selected = _selectedKeyIds.containsKey(keyId);
		return selected;
	}

	public boolean isSelectedOrParentSelected(final IHUKey huKey)
	{
		IHUKey key = huKey;
		while (key != null)
		{
			if (isSelected(key))
			{
				return true;
			}
			key = key.getParent();
		}

		return false;
	}

	/**
	 * Select/Deselect given key
	 *
	 * @param huKey
	 * @param selectedNew true if it shall be selected; false if it shall be deselected
	 */
	private void setSelected0(final IHUKey huKey, final boolean selectedNew)
	{
		updateSelectedKey(huKey, selectedNew);
	}

	/**
	 * Clear {@link IHUKey} selection.
	 */
	protected final void clearSelectedKeyIds()
	{
		_selectedKeyIds.clear();
		pcs.firePropertyChange(PROPERTY_HUKeySelectionChanged, false, true);
	}

	/**
	 * Modifies the internal selection map.
	 *
	 * @param huKey
	 * @param selectedNew
	 */
	private void updateSelectedKey(final IHUKey huKey, final boolean selectedNew)
	{
		final String keyId = huKey.getId();
		boolean selectionChanged = false;
		if (selectedNew)
		{
			final IHUKey huKeyOld = _selectedKeyIds.put(keyId, huKey);
			if (huKeyOld != huKey)
			{
				selectionChanged = true;
			}
			logger.debug("updateSelectedKey(): this-ID={}: added keyId={}, huKey={} to _selectedKeyIds; selectionChanged={}; this={}",
					System.identityHashCode(this), keyId, huKey, selectionChanged, this);
		}
		else
		{
			final IHUKey huKeyOld = _selectedKeyIds.remove(keyId);
			if (huKeyOld != null)
			{
				selectionChanged = true;
			}
			logger.debug("updateSelectedKey(): this-ID={}: removed keyId={}, huKey={} from _selectedKeyIds; selectionChanged={}; this={}",
					System.identityHashCode(this), keyId, huKey, selectionChanged, this);
		}

		if (selectionChanged)
		{
			pcs.firePropertyChange(PROPERTY_HUKeySelectionChanged, false, true);
		}
	}

	public boolean toggleSelected(final IHUKey huKey)
	{
		if (!isSelectable(huKey))
		{
			// shall not happen
			// return: not selected
			return false;
		}

		final boolean selectedOld = isSelected(huKey);
		final boolean selectedNew = !selectedOld;
		setSelected0(huKey, selectedNew);

		fireHUKeyStatusChangedRecursively(getRootHUKey());

		return selectedNew;
	}

	/**
	 *
	 * @param huKey
	 * @return true if given <code>huKey</code> was selected
	 */
	public boolean setSelected(final IHUKey huKey)
	{
		if (!isSelectable(huKey))
		{
			// shall not happen
			// return: not selected
			logger.warn("setSelected(): this-ID={}: huKey={} is not selectable; this={}", System.identityHashCode(this), huKey, this);
			return false;
		}

		final boolean selected = isSelected(huKey);
		if (selected)
		{
			logger.debug("setSelected(): this-ID={}: huKey={} is already selected; this={}", System.identityHashCode(this), huKey, this);
			return true; // already selected
		}

		setSelected0(huKey, true);

		fireHUKeyStatusChangedRecursively(getRootHUKey());

		return true;
	}

	/**
	 * Select {@link IHUKey}s by given {@link I_M_HU}
	 *
	 * @param hu
	 * @return true if at least one {@link IHUKey} was found for our given <code>hu</code> and it was selected
	 */
	public boolean setSelected(final I_M_HU hu)
	{
		if (hu == null)
		{
			// shall not happen
			// return: not selected
			logger.warn("setSelected(): this-ID={} parameter 'hu' is null; this={}", System.identityHashCode(this), this);
			return false;
		}

		final int huId = hu.getM_HU_ID();

		//
		// Get Root Key
		final IHUKey rootKey = getRootHUKey();
		if (rootKey == null)
		{
			logger.warn("setSelected(): this-ID={} rootKey=null; this={}", System.identityHashCode(this), this);
			return false; // no root key???
		}

		//
		// Iterate through all our IHUKeys and pick those who match our given HU (shall be only one)
		final List<IHUKey> huKeysForId = new ArrayList<IHUKey>();
		rootKey.iterate(new HUKeyVisitorAdapter()
		{
			@Override
			public VisitResult beforeVisit(final IHUKey key)
			{
				final HUKey huKey = HUKey.castIfPossible(key);
				if (huKey != null && huKey.getM_HU().getM_HU_ID() == huId)
				{
					huKeysForId.add(huKey);
				}

				// => continue iteration
				return VisitResult.CONTINUE;
			}
		});

		//
		// Select what we found
		logger.debug("setSelected(): this-ID={}m hu={}: found huKeysForId={}; this={}", System.identityHashCode(this), hu, huKeysForId, this);
		boolean selected = false;
		for (final IHUKey huKey : huKeysForId)
		{
			if (setSelected(huKey))
			{
				selected = true;
			}
		}

		return selected;
	}

	private void fireHUKeyStatusChangedRecursively(final IHUKey huKey)
	{
		huKey.fireStatusChanged();
		// TODO: consider getting only the loaded children because if they are not loaded for sure there is no status listener
		for (final IHUKey child : huKey.getChildren())
		{
			fireHUKeyStatusChangedRecursively(child);
		}
	}

	public boolean hasSelectedKeys()
	{
		return !_selectedKeyIds.isEmpty();
	}

	/**
	 *
	 * @return the <code>M_HU_ID</code> for each selected {@link IHUKey}. See {@link #getSelectedHUKeys()} for details about which keys' HU-IDs are returned.
	 */
	public Set<Integer> getSelectedHUIds()
	{
		final Set<Integer> huIds = getSelectedHUKeys()
				.stream()
				.map(key -> key.getM_HU().getM_HU_ID())
				.collect(Collectors.toSet());
		return huIds;
	}

	/**
	 *
	 * @return the <code>M_HU</code> for each selected {@link IHUKey}. See {@link #getSelectedHUKeys()} for details about which keys' HUs are returned.
	 */
	public Set<I_M_HU> getSelectedHUs()
	{
		final Set<I_M_HU> hus = getSelectedHUKeys()
				.stream()
				.map(key -> key.getM_HU())
				.collect(Collectors.toSet());
		return hus;
	}

	public List<IHUProductStorage> getSelectedHUProductStorages(final I_M_Product product)
	{
		final List<IHUProductStorage> productStorages = new ArrayList<IHUProductStorage>();
		for (final HUKey huKey : getSelectedHUKeys())
		{
			final IHUProductStorage productStorage = huKey.getProductStorageOrNull(product);
			if (productStorage == null)
			{
				continue;
			}
			if (productStorage.isEmpty())
			{
				continue;
			}

			productStorages.add(productStorage);
		}
		return productStorages;
	}

	/**
	 * @see #getSelectedHUKeys(Predicate)
	 */
	public Set<HUKey> getSelectedHUKeys()
	{
		final Predicate<IHUKey> filter = null; // no filter
		return getSelectedHUKeys(filter);
	}

	/**
	 * Gets all selected {@link HUKey}s which are accepted by <code>filter</code>.
	 *
	 * <br>
	 * NOTE:
	 * <ul>
	 * <li>if user selected a HU Key and one of it's children, only the parent HU key will be returned
	 * </ul>
	 *
	 * @param filter filter use to decide if a selected {@link HUKey} is accepted or not; if it's null it will be ignored
	 * @return
	 */
	public Set<HUKey> getSelectedHUKeys(final Predicate<IHUKey> filter)
	{
		final Set<Integer> huIds = new HashSet<Integer>();
		final Set<HUKey> huKeys = new HashSet<HUKey>();
		for (final IHUKey key : _selectedKeyIds.values())
		{
			if (!isSelected(key))
			{
				logger.debug("getSelectedHUKeys: this-ID={} skipping key={} because it is not selected; this={}", System.identityHashCode(this), key, this);
				continue;
			}

			final HUKey huKey = HUKey.castIfPossible(key);
			if (huKey == null)
			{
				logger.debug("getSelectedHUKeys: this-ID={} skipping key={} because it does not wrap a HU; this={}", System.identityHashCode(this), key, this);
				continue;
			}

			// Avoid adding the same HU more then once
			final I_M_HU hu = huKey.getM_HU();
			final int huId = hu.getM_HU_ID();
			if (!huIds.add(huId))
			{
				// already added
				logger.debug("getSelectedHUKeys: this-ID={} skipping key={} because its M_HU_ID was already added to the selection; this={}", System.identityHashCode(this), key, this);
				continue;
			}

			// If there is a filter, ask it we accepts current Key
			if (filter != null && !filter.evaluate(key))
			{
				logger.debug("getSelectedHUKeys: this-ID={} skipping key={} because the given filter={} evaluated to false; this={}", System.identityHashCode(this), key, filter, this);
				continue;
			}

			logger.debug("getSelectedHUKeys: this-ID={} adding key={} to the selection; this={}", System.identityHashCode(this), key, filter, this);
			huKeys.add(huKey);
		}

		return huKeys;
	}

	/**
	 * Gets selected {@link IHUKey}s which are weightable but they were not weighted.
	 *
	 * @return selected keys which are weightable but they were not weighted; never return null
	 */
	public Set<HUKey> getSelectedHUKeysWeightableButNotWeighted()
	{
		return getSelectedHUKeys(HUKeyFilter_WeightableButNotWeighted);
	}

	public boolean hasSelectedHUKeysWeightableButNotWeighted()
	{
		return !getSelectedHUKeysWeightableButNotWeighted().isEmpty();
	}

	/**
	 *
	 * @return true if the barcode input field is displayed in Packtischdialog, else false
	 */
	public boolean isDisplayBarcode()
	{
		return displayBarcode;
	}

	/**
	 * true if we want to display the barcode input field in Packtischdialog, false otherwise
	 *
	 * @param displayBarcode
	 */
	public void setDisplayBarcode(final boolean displayBarcode)
	{
		this.displayBarcode = displayBarcode;
	}

	/**
	 * Update {@link #propertiesPanelModel}
	 *
	 * @param currentKey
	 */
	private void loadHUProperties(final IHUKey currentKey)
	{
		currentKey.getAttributeSet().assertNotDisposedTree();

		//
		// Save old models (before removing them from composite and adding the new ones and before creating the new models which are binding attributeStorage)
		propertiesPanelModel.commitEdit();

		//
		// Set the new models to our composite
		propertiesPanelModel.setChildModels(new Supplier<Collection<IPropertiesPanelModel>>()
		{
			@Override
			public Collection<IPropertiesPanelModel> get()
			{
				final List<IPropertiesPanelModel> childPropertiesPanelModels = new ArrayList<IPropertiesPanelModel>();

				//
				// Attribute Set Model
				final HUAttributeSetPropertiesModel attributeSetModel = new HUAttributeSetPropertiesModel(getTerminalContext());
				attributeSetModel.setReadonly(currentKey.isReadonly());
				attributeSetModel.setAttributeStorage(currentKey.getAttributeSet());
				attributeSetModel.setAttributesEditableOnlyIfVHU(attributesEditableOnlyIfVHU);
				childPropertiesPanelModels.add(attributeSetModel);

				//
				// HU Properties if any (used to directly edit the HU properties like BPartner, BPartner Location etc)
				final HUKey huKey = HUKey.castIfPossible(currentKey);
				if (huKey != null)
				{
					final HUPropertiesModel huProperties = new HUPropertiesModel(getTerminalContext(), huKey.getM_HU());
					if (huKey.isReadonly())
					{
						huProperties.setEditable(false);
					}
					childPropertiesPanelModels.add(huProperties);
				}

				return childPropertiesPanelModels;
			}
		});
	}

	/**
	 * Saves the current HU properties, i.e. commits the values from the internal <code>propertiesPanelModel</code> to DB. Does <b>not</b> so allocations between HUs and document lines.
	 */
	public void saveHUProperties()
	{
		if (propertiesPanelModel == null)
		{
			return;
		}

		propertiesPanelModel.validateUI();
		propertiesPanelModel.commitEdit();
	}

	/**
	 * Save changes (if any) to database. This methods starts by calling {@link #saveHUProperties()}.
	 */
	public void save()
	{
		saveHUProperties();

		final IHUKey rootHUKey = getRootHUKey();
		if (rootHUKey == null)
		{
			// nothing to save
			return;
		}

		if (updateHUAllocationsOnSave)
		{
			final IHUKeyFactory keyFactory = rootHUKey.getKeyFactory();
			trxManager.run(new TrxRunnable()
			{
				@Override
				public void run(final String localTrxName)
				{
					final List<I_M_HU> originalTopLevelHUs = getOriginalTopLevelHUs();
					keyFactory.createHUAllocations(rootHUKey, originalTopLevelHUs);
				}
			});
		}
	}

	/**
	 * @return true if there are user changes in this HU Editor
	 */
	public final boolean hasChanges()
	{
		final IHUKey root = getRootHUKey();
		return changesTracker.hasChanges(root);
	}

	public final void setUpdateHUAllocationsOnSave(final boolean updateHUAllocationsOnSave)
	{
		this.updateHUAllocationsOnSave = updateHUAllocationsOnSave;
	}

	public final void setAllowSelectingReadonlyKeys(final boolean allowSelectingReadonlyKeys)
	{
		this.allowSelectingReadonlyKeys = allowSelectingReadonlyKeys;
	}

	public final boolean isAllowSelectingReadonlyKeys()
	{
		return allowSelectingReadonlyKeys;
	}

	public void setAttributesEditableOnlyIfVHU(final boolean attributesEditableOnlyIfVHU)
	{
		this.attributesEditableOnlyIfVHU = attributesEditableOnlyIfVHU;
	}

	protected final void clearCache()
	{
		final ITerminalContext terminalContext = _terminalContext;
		if (terminalContext == null)
		{
			return;
		}

		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);

		//
		// Clear (attribute) cache before opening editor
		keyFactory.clearCache();
	}

	/**
	 * Sets Quality Inspection flag for currently selected HUs
	 *
	 * @task 08639
	 */
	public final void setQualityInspectionFlagForSelectedHUs(final boolean qualityInspectionFlag)
	{
		for (final HUKey huKey : getSelectedHUKeys())
		{
			final IQualityInspectionSchedulable qualityInspectionAware = huKey.asQualityInspectionSchedulable().orNull();
			if (qualityInspectionAware == null)
			{
				// skip because it's not supported
				continue;
			}

			qualityInspectionAware.setQualityInspection(qualityInspectionFlag);
		}

		clearSelectedKeyIds();
		setCurrentHUKey(getRootHUKey());
	}

	@Override
	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("HUEditorModel [_terminalContext=").append(_terminalContext)
				.append(", pcs=").append(pcs)
				.append(", breadcrumbKeyLayout=").append(breadcrumbKeyLayout)
				.append(", breadcrumbKeyLayoutListener=").append(breadcrumbKeyLayoutListener)
				.append(", _huKeyFilterModel=").append(_huKeyFilterModel)
				.append(", handlingUnitsKeyLayout=").append(handlingUnitsKeyLayout)
				.append(", propertiesPanelModel=").append(propertiesPanelModel)
				.append(", attributesEditableOnlyIfVHU=").append(attributesEditableOnlyIfVHU)
				.append(", rootHUKey=").append(rootHUKey)
				.append(", _currentKey=").append(_currentKey)
				.append(", changesTracker=").append(changesTracker)
				.append(", _selectedKeyIds=").append(_selectedKeyIds)
				.append(", displayBarcode=").append(displayBarcode)
				.append(", updateHUAllocationsOnSave=").append(updateHUAllocationsOnSave)
				.append(", allowSelectingReadonlyKeys=").append(allowSelectingReadonlyKeys)
				.append(", disposed=").append(disposed)
				.append(", _originalTopLevelHUs=").append(_originalTopLevelHUs)
				.append("]");

		return stringBuilder.toString();
	}

}
