package de.metas.handlingunits.client.terminal.mmovement.model.join.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IdentityHashSet;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyVisitor;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.client.terminal.mmovement.model.join.service.ILUTUJoinOrMergeBL;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

public final class HUJoinModel extends AbstractLTCUModel
{
	public static final String PROPERTY_MergeableLUs = "MergeableLUs";
	public static final String PROPERTY_MergeableTUs = "MergeableTUs";

	private static final String ERR_INVALID_HU_KEY_SELECTED = "InvalidHUKeySelected";
	private static final String ERR_SET_Qty = "SetQty";
	// private static final String ERR_ONE_LU_KEY_ALLOWED_ON_FINAL_JOIN = "OneLUKeyAllowedOnFinalJoin";

	// Services
	private final transient ILUTUJoinOrMergeBL lutuJoinBL = Services.get(ILUTUJoinOrMergeBL.class);

	private boolean _mergeableLUs = false; // disable by default (requires user interaction)
	private boolean _mergeableTUs = false; // disable by default (requires user interaction)

	private final IHUKey rootKeyInitial;

	private final Set<HUKey> originalLUKeys = new IdentityHashSet<>();
	private final Set<HUKey> originalTUKeys = new IdentityHashSet<>();

	private final Set<ILUTUJoinKey> resultLUKeys = new IdentityHashSet<>();
	private final Set<ILUTUJoinKey> resultTUKeys = new IdentityHashSet<>();

	public HUJoinModel(final ITerminalContext terminalContext, final IHUKey rootKey, final Set<HUKey> selectedKeys)
	{
		super(terminalContext);

		rootKeyInitial = rootKey;

		//
		// Configure default selection
		getLUKeyLayout().getKeyLayoutSelectionModel().setAllowMultipleSelection(true);
		getTUKeyLayout().getKeyLayoutSelectionModel().setAllowMultipleSelection(true);
		getCUKeyLayout().getKeyLayoutSelectionModel().setAllowMultipleSelection(false);

		final Comparator<ITerminalKey> joinSortComparator = new Comparator<ITerminalKey>()
		{
			@Override
			public int compare(final ITerminalKey o1, final ITerminalKey o2)
			{
				final ILUTUJoinKey lutuJoinKey1 = (ILUTUJoinKey)o1;
				final ILUTUJoinKey lutuJoinKey2 = (ILUTUJoinKey)o2;
				return lutuJoinKey1.isVirtual() == lutuJoinKey2.isVirtual() ? 0 : lutuJoinKey2.isVirtual() ? 1 : -1;
			}
		};

		final Map<String, List<ITerminalKey>> joinKeys = getJoinKeys(selectedKeys);
		//
		// LUs
		{
			final List<ITerminalKey> luResult = joinKeys.get(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			Collections.sort(luResult, joinSortComparator);

			getLUKeyLayout().setKeys(luResult);
		}
		//
		// TUs
		{
			final List<ITerminalKey> tuResult = joinKeys.get(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			Collections.sort(tuResult, joinSortComparator);

			getTUKeyLayout().setKeys(tuResult);
		}

		updateReadOnly();
	}

	private final Map<String, List<ITerminalKey>> getJoinKeys(final Collection<HUKey> keys)
	{
		final Map<String, List<ITerminalKey>> result = new HashMap<>();
		//
		// Initialize result set with empty keys for LU/TU
		result.put(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, new ArrayList<ITerminalKey>());
		result.put(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, new ArrayList<ITerminalKey>());
		result.put(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI, new ArrayList<ITerminalKey>());

		for (final HUKey key : keys)
		{
			if (keys.contains(key.getParent()))
			{
				continue;
			}

			Check.assumeNotNull(key, "all selected keys are not null");

			final I_M_HU hu = key.getM_HU(); // always not null due to HUKey constructor constraints
			final String unitType = handlingUnitsBL.getHU_UnitType(hu);

			//
			// Retrieve existing result for unitType
			final List<ITerminalKey> unitTypeResult = result.get(unitType);
			Check.assumeNotNull(unitTypeResult, "unitType supported; given unitType was " + unitType);

			//
			// Create joinKey based on unitType and add it to the existing result
			final ITerminalKey unitTypeKey = getJoinKey(key, hu, unitType, key.isVirtualPI());
			unitTypeResult.add(unitTypeKey);
		}
		return result;
	}

	private final ITerminalKey getJoinKey(final HUKey key, final I_M_HU hu, final String unitType, final boolean virtual)
	{
		final ILUTUJoinKey result;

		final IHUDocumentLine documentLine = key.findDocumentLineOrNull();
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(unitType))
		{
			result = new LUJoinKey(getTerminalContext(), hu, documentLine, virtual);

			//
			// Initialize original LU keys
			originalLUKeys.add(key);

			//
			// Initialize result LU keys
			resultLUKeys.add(result);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(unitType) || X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(unitType))
		{
			result = new TUJoinKey(getTerminalContext(), hu, documentLine, virtual);

			//
			// Initialize original TU keys
			originalTUKeys.add(key);

			//
			// Initialize result TU keys
			resultTUKeys.add(result);
		}
		else
		{
			throw new MaterialMovementException(HUJoinModel.ERR_INVALID_HU_KEY_SELECTED);
		}
		return result;
	}

	@Override
	public final void execute() throws MaterialMovementException
	{
		if (isMergeableLUs())
		{
			//
			// Silently return: User just entered here to merge stuff; just return
			return;
		}

		//
		// Get LU Key
		// Allow selection of only ONE LU Key when executing the final Join
		final ILUTUJoinKey luKey;
		final List<ILUTUJoinKey> luKeys = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ILUTUJoinKey.class);
		if (luKeys.isEmpty())
		{
			//
			// Silently return: User needs to have at least one LU selected to join TUs on
			return;
		}
		else if (luKeys.size() > 1)
		{
			//
			// Silently return: Allow only one LU Key on final join
			return;
		}
		else
		{
			luKey = luKeys.get(0);
		}

		//
		// Get TU Keys
		final List<ILUTUJoinKey> tuKeys = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ILUTUJoinKey.class);
		if (tuKeys.isEmpty())
		{
			//
			// Silently return: User needs to join at least one TU to a LU
			return;
		}

		//
		// Join TUs to LU
		joinHUs(luKey, tuKeys);
	}

	private final void joinHUs(final ILUTUJoinKey luKey, final List<ILUTUJoinKey> tuKeys)
	{
		final List<I_M_HU> joinedHUs = lutuJoinBL.joinOrMergeHUs(getTerminalContext(), luKey, tuKeys);

		//
		// Note that refresh decision is not this model's, but the callee's
		//

		//
		// Remove joined HUs from result
		removeJoinedHUsFromResult(resultLUKeys, joinedHUs);
		removeJoinedHUsFromResult(resultTUKeys, joinedHUs);

		//
		// Re-add the joined LU-Key to the result
		resultLUKeys.add(luKey);
	}

	private final void removeJoinedHUsFromResult(final Set<ILUTUJoinKey> resultKeys, final List<I_M_HU> joinedHUs)
	{
		final Iterator<ILUTUJoinKey> resultKeysIterator = resultKeys.iterator();
		while (resultKeysIterator.hasNext())
		{
			final ILUTUJoinKey resultKey = resultKeysIterator.next();

			for (final I_M_HU joinedHU : joinedHUs)
			{
				if (resultKey.getM_HU().getM_HU_ID() == joinedHU.getM_HU_ID())
				{
					//
					// Joined key shall be removed from the result
					resultKeysIterator.remove();
				}
			}
		}
	}

	/**
	 * Used to cache created {@link IHUKey} luKeys (for Composite/HUKey TU child retrieval)
	 */
	private final Map<Integer, IHUKey> cachedHULUKeys = new HashMap<>();

	@Override
	protected final void onLUPressed(final ITerminalKey key)
	{
		updateReadOnly();

		//
		// Add original TU keys at the beginning of the panel
		final List<ITerminalKey> resultTUKeys = new ArrayList<>(this.resultTUKeys);

		//
		// Only add the other TUKeys if there are multiple LUs selected
		if (isMergeableLUs())
		{
			//
			// Populate LU lane with
			final List<LUJoinKey> luKeys = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(LUJoinKey.class);
			final IHUKeyFactory keyFactory = rootKeyInitial.getKeyFactory();
			for (final LUJoinKey luKey : luKeys)
			{
				//
				// Add the LU-Key's children to the TU-selectable list (attempt to get it from the cache first)
				final IHUKey luHUKey = getCreateHUKey(luKey, keyFactory, cachedHULUKeys, false);
				final List<IHUKey> children = getRefreshChildren(luHUKey);
				resultTUKeys.addAll(children);
			}
		}

		//
		// Finally, set the result TUKeys to the layout
		getTUKeyLayout().setKeys(resultTUKeys);

		onTUPressed(null);
	}

	@Override
	protected final void onTUPressed(final ITerminalKey key)
	{
		// On TU pressed, we don't need to force the update.
		updateCUKeys(false);
	}

	private final void updateCUKeys(final boolean forced)
	{
		updateReadOnly();

		final List<ITerminalKey> resultCUKeys;
		if (isMergeableTUs())
		{
			resultCUKeys = getTUCUKeys(forced);
		}
		else
		{
			resultCUKeys = Collections.emptyList();
		}
		getCUKeyLayout().setKeys(resultCUKeys);

		updateReadOnly();
	}

	private final void updateTUKeys(final boolean forced)
	{
		updateReadOnly();

		final List<IHUKey> resultTUKeys;
		if (isMergeableTUs() || isMergeableLUs())
		{
			resultTUKeys = getTULUKeys(forced);
		}
		else
		{
			resultTUKeys = Collections.emptyList();
		}
		getTUKeyLayout().setKeys(resultTUKeys);

		onLUPressed(null); // refresh entire structure
		updateReadOnly();
	}

	private final List<IHUKey> getTULUKeys(final boolean forced)
	{
		final List<IHUKey> resultHUTUKeys = new ArrayList<>();

		final List<LUJoinKey> luKeysUnordered = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(LUJoinKey.class);
		final List<LUJoinKey> luKeys = new ArrayList<>(luKeysUnordered);

		//
		// Sort selected LU-keys so that their TU-sub-keys are displayed in the order
		final List<ITerminalKey> luKeysInOrder = getLUKeyLayout().getKeys();
		Collections.sort(luKeys, new Comparator<ITerminalKey>()
		{
			@Override
			public int compare(final ITerminalKey o1, final ITerminalKey o2)
			{
				final int o1Index = luKeysInOrder.indexOf(o1);
				final int o2Index = luKeysInOrder.indexOf(o2);

				return o1Index - o2Index;
			}
		});

		final IHUKeyFactory keyFactory = rootKeyInitial.getKeyFactory();
		for (final LUJoinKey luKey : luKeys)
		{
			//
			// (re-)Creation LU-HUKey again
			final IHUKey luHUKey = getCreateHUKey(luKey, keyFactory, cachedHULUKeys, forced);

			//
			// Add the TU-Key's children to the CU-selectable list (attempt to get it from the cache first)

			//
			// Note:
			final List<IHUKey> children = getRefreshChildren(luHUKey);
			resultHUTUKeys.addAll(children);
		}
		return resultHUTUKeys;
	}

	/**
	 * Try to retrieve key from cache unless <code>forced=true</code>, otherwise create (or replace it with) a new one.<br>
	 *
	 * @param lutuKey
	 * @param keyFactory
	 * @param cache
	 * @param forced
	 * @return key which was created
	 */
	private final IHUKey getCreateHUKey(final ILUTUJoinKey lutuKey, final IHUKeyFactory keyFactory, final Map<Integer, IHUKey> cache, final boolean forced)
	{
		final I_M_HU hu = lutuKey.getM_HU();
		final int huId = hu.getM_HU_ID();

		IHUKey huKey = cache.get(huId);
		if (forced && huKey != null)
		{
			// If we have a forced refresh, we remove the key from the cache and re-create.
			cache.remove(huId);
			huKey = null;
		}

		if (huKey == null) // will be not-null if not forced
		{
			huKey = keyFactory.createKey(hu, lutuKey.getHUDocumentLine());
		}
		return huKey;
	}

	private final List<ITerminalKey> getTUCUKeys(final boolean forced)
	{
		final List<ITerminalKey> resultCUKeys = new ArrayList<>();

		//
		// Populate CU lane with keys.
		final List<ITerminalKey> tuTerminalKeysUnOrdered = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ITerminalKey.class);
		final List<ITerminalKey> tuTerminalKeys = new ArrayList<>(tuTerminalKeysUnOrdered);

		//
		// Sort selected TU-keys so that their CU-sub-keys are displayed in the order
		final List<ITerminalKey> tuTerminalKeysInOrder = getTUKeyLayout().getKeys();
		Collections.sort(tuTerminalKeys, new Comparator<ITerminalKey>()
		{
			@Override
			public int compare(final ITerminalKey o1, final ITerminalKey o2)
			{
				final int o1Index = tuTerminalKeysInOrder.indexOf(o1);
				final int o2Index = tuTerminalKeysInOrder.indexOf(o2);

				return o1Index - o2Index;
			}
		});

		final IHUKeyFactory keyFactory = rootKeyInitial.getKeyFactory();
		for (final ITerminalKey tuTerminalKey : tuTerminalKeys)
		{
			if (!(tuTerminalKey instanceof TUJoinKey))
			{
				//
				// Keys are not only TUJoinKey => ClassCastException
				continue;
			}
			final ILUTUJoinKey tuKey = (TUJoinKey)tuTerminalKey;

			//
			// Add the TU-Key's children to the CU-selectable list (attempt to get them from the cache first)
			final List<IHUKey> children = getCreateIncludedVirtualKeys(tuKey, keyFactory);
			resultCUKeys.addAll(children);
		}
		return resultCUKeys;
	}

	/**
	 * Create included CU-keys for virtual PIs
	 *
	 * @param lutuKey
	 * @param keyFactory
	 * @return CU-keys
	 */
	private List<IHUKey> getCreateIncludedVirtualKeys(final ILUTUJoinKey lutuKey, final IHUKeyFactory keyFactory)
	{
		final List<IHUKey> includedVirtualKeys = new ArrayList<>();

		final I_M_HU hu = lutuKey.getM_HU();

		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(hu);
		for (final I_M_HU includedHU : includedHUs)
		{
			final IHUKey huKey = keyFactory.createVirtualKeyWithParent(includedHU, lutuKey.getHUDocumentLine());
			includedVirtualKeys.add(huKey);
		}
		return includedVirtualKeys;
	}

	@Override
	protected final void onCUPressed(final ITerminalKey key)
	{
		// nothing at this level
	}

	private final void updateReadOnly()
	{
		final boolean mergeableLUs = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys().size() > 1;
		setQtyTUReadonly(!mergeableLUs);
		setMergeableLU(mergeableLUs);

		final boolean mergeableTUs = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys().size() > 1 && !mergeableLUs;
		setQtyCUReadonly(!mergeableTUs);
		setMergeableTU(mergeableTUs);
	}

	public final void doHUMerge(final Predicate<HUMergeModel> editorCallback, final MergeType mergeType)
	{
		final boolean edited;
		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			Check.assumeNotNull(editorCallback, "editorCallback not null");
			final HUMergeModel mergeModel = createHUMergeModel(mergeType);

			edited = editorCallback.test(mergeModel);
		}
		afterMerge(edited, mergeType);
	}

	private final HUMergeModel createHUMergeModel(final MergeType mergeType)
	{
		final List<ILUTUJoinKey> selectedKeys;
		final List<ITerminalKey> selectedChildrenKeys;

		final int selectedChildrenQty;
		if (mergeType == MergeType.LoadingUnit)
		{
			selectedKeys = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ILUTUJoinKey.class);
			selectedChildrenKeys = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ITerminalKey.class);
			selectedChildrenQty = getQtyTU().intValueExact();
		}
		else if (mergeType == MergeType.TradingUnit)
		{
			final List<ILUTUJoinKey> selectedKeysFromModel = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ILUTUJoinKey.class);
			selectedKeys = new ArrayList<>(selectedKeysFromModel);

			final HUKey selectedChildKey = getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(HUKey.class, AbstractLTCUModel.ERR_SELECT_CU_KEY);

			//
			// The child is actually a VirtualPI; we're comparing parents (or in this case transport units) here
			final I_M_HU selectedChildHU = selectedChildKey.getM_HU();
			final I_M_HU selectedHU = handlingUnitsDAO.retrieveParent(selectedChildHU);

			//
			// Remove already-selected child from merge options (we're merging FROM it)
			final Iterator<ILUTUJoinKey> selectedKeyCandidatesIterator = selectedKeys.iterator();
			while (selectedKeyCandidatesIterator.hasNext())
			{
				final ILUTUJoinKey selectedKeyCandidate = selectedKeyCandidatesIterator.next();
				final I_M_HU candidateHU = selectedKeyCandidate.getM_HU();

				if (selectedHU.getM_HU_ID() != candidateHU.getM_HU_ID())
				{
					continue;
				}
				selectedKeyCandidatesIterator.remove();
			}

			selectedChildrenKeys = Collections.singletonList((ITerminalKey)selectedChildKey); // doesn't allow super classes (pretty stupid)
			selectedChildrenQty = getQtyCU().intValueExact();

			if (selectedChildrenQty <= 0)
			{
				throw new MaterialMovementException(ERR_SET_Qty); // only in this case (when merging LUs, it can be 0 = merge all)
			}
		}
		else
		{
			throw new UnsupportedOperationException("MergeType not supported: " + mergeType);
		}

		final HUMergeModel mergeModel = new HUMergeModel(getTerminalContext(),
				rootKeyInitial.getKeyFactory(),
				selectedKeys, selectedChildrenKeys,
				selectedChildrenQty,
				mergeType);
		return mergeModel;
	}

	private final void afterMerge(final boolean edited, final MergeType mergeType)
	{
		if (!edited)
		{
			//
			// If user cancelled, do nothing
			return;
		}

		final boolean forced = true;

		//
		// Set original keys back to their layouts
		final List<ILUTUJoinKey> luKeys = getLUTUJoinKeysNotDestroyed(resultLUKeys);
		getLUKeyLayout().setKeys(luKeys);
		final List<ILUTUJoinKey> tuKeys = getLUTUJoinKeysNotDestroyed(resultTUKeys);
		getTUKeyLayout().setKeys(tuKeys);

		if (mergeType == MergeType.LoadingUnit)
		{
			updateTUKeys(forced); // refresh TU join panel
		}
		else if (mergeType == MergeType.TradingUnit)
		{
			updateCUKeys(forced); // refresh CU join panel
		}
		else
		{
			throw new UnsupportedOperationException("MergeType not supported: " + mergeType);
		}
	}

	private final List<ILUTUJoinKey> getLUTUJoinKeysNotDestroyed(final Collection<ILUTUJoinKey> lutuJoinKeys)
	{
		final List<ILUTUJoinKey> result = new ArrayList<>();
		for (final ILUTUJoinKey lutuJoinKey : lutuJoinKeys)
		{
			final I_M_HU hu = lutuJoinKey.getM_HU();

			if (handlingUnitsBL.isDestroyedRefreshFirst(hu))
			{
				continue;
			}

			lutuJoinKey.rebuild(); // rebuild it first
			result.add(lutuJoinKey);
		}
		return result;
	}

	public final void refreshRootKey()
	{
		//
		// Remove all original keys from parent & dump storages if empty
		rebuildRootKey(rootKeyInitial);

		final IHUKeyFactory keyFactory = rootKeyInitial.getKeyFactory();

		//
		// Create New HUKeys from the ILUTUJoinKeys
		createBindResultHUKeys(keyFactory, resultLUKeys);
		createBindResultHUKeys(keyFactory, resultTUKeys);
	}

	private final void createBindResultHUKeys(final IHUKeyFactory keyFactory, final Set<ILUTUJoinKey> resultKeys)
	{
		for (final ILUTUJoinKey resultKey : resultKeys)
		{
			final I_M_HU hu = resultKey.getM_HU();

			if (handlingUnitsBL.isDestroyedRefreshFirst(hu))
			{
				continue; // silently skip
			}

			if (!handlingUnitsBL.isTopLevel(hu))
			{
				continue;
			}

			final Boolean[] childAlreadyRebuilt = new Boolean[] { false };

			//
			// Remove those keys which were already existing
			rootKeyInitial.iterate(new IHUKeyVisitor()
			{
				@Override
				public VisitResult beforeVisit(final IHUKey key)
				{
					final HUKey rootChildKey = HUKey.castIfPossible(key);
					if (rootChildKey == null)
					{
						return VisitResult.CONTINUE;
					}
					final I_M_HU rootChildHU = rootChildKey.getM_HU();
					if (hu.getM_HU_ID() == rootChildHU.getM_HU_ID())
					{
						childAlreadyRebuilt[0] = true;
					}

					return VisitResult.SKIP_DOWNSTREAM;
				}

				@Override
				public VisitResult afterVisit(final IHUKey key)
				{
					return VisitResult.CONTINUE;
				}
			});

			//
			// If the key was not already re-added, construct it
			if (!childAlreadyRebuilt[0])
			{
				final IHUKey resultHUKey = keyFactory.createKey(hu, resultKey.getHUDocumentLine());
				rootKeyInitial.addChild(resultHUKey);
			}
		}
	}

	private final void setMergeableTU(final boolean mergeableTUs)
	{
		final boolean mergeableTUsOld = isMergeableTUs();
		_mergeableTUs = mergeableTUs;
		pcs.firePropertyChange(HUJoinModel.PROPERTY_MergeableTUs, mergeableTUsOld, mergeableTUs);
	}

	/**
	 * @return true if TUs can be merged
	 */
	public final boolean isMergeableTUs()
	{
		return _mergeableTUs;
	}

	private final void setMergeableLU(final boolean mergeableLUs)
	{
		final boolean mergeableLUsOld = isMergeableLUs();
		_mergeableLUs = mergeableLUs;
		pcs.firePropertyChange(HUJoinModel.PROPERTY_MergeableLUs, mergeableLUsOld, mergeableLUs);
	}

	/**
	 * @return true if LUs can be merged
	 */
	public final boolean isMergeableLUs()
	{
		return _mergeableLUs;
	}
}
