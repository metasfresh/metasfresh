package de.metas.handlingunits.client.terminal.mmovement.model.distribute.impl;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.comparator.CardinalityOrderComparator;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.impl.HUDistributeBuilder;
import de.metas.handlingunits.client.terminal.editor.model.HUKeyVisitorAdapter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.ISplittableHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.HUDistributeCUTUPanel;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Model responsible for distributing VHU (i.e. CU) to TUs
 *
 * @author al
 */
public class HUDistributeCUTUModel extends AbstractLTCUModel
{
	private static final String ERR_SelectCUOrTUKey = "@SelectCUOrTUKey@";

	private static final String ERR_CU_QTY_NOT_ALLOWED = "@CUQtyNotAllowed@";

	//
	// Services & factories
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	//
	// Parameters
	private final IHUKey rootKeyInitial;

	public HUDistributeCUTUModel(final ITerminalContext terminalContext, final IHUKey rootKey, final ISplittableHUKey selectedHUKey)
	{
		super(terminalContext);

		Check.assumeNotNull(rootKey, "rootKey not null");
		rootKeyInitial = rootKey;

		//
		// Configure default selection
		final IKeyLayout cuKeyLayout = getCUKeyLayout();
		final IKeyLayout tuKeyLayout = getTUKeyLayout();
		cuKeyLayout.getKeyLayoutSelectionModel().setAllowMultipleSelection(false); // 1 VHU shall be selected for distribution at any time
		tuKeyLayout.getKeyLayoutSelectionModel().setAllowMultipleSelection(true); // The selected VHU shall be distributed among the selected TUs

		//
		// Get CU-key VHUs from selection and configure available VHUs
		final List<IHUKey> vhuKeys = extractVHUs(selectedHUKey);
		cuKeyLayout.setKeys(vhuKeys);
	}

	private final List<IHUKey> extractVHUs(final ISplittableHUKey selectedHUKey)
	{
		if (selectedHUKey == null)
		{
			throw new MaterialMovementException(ERR_SelectCUOrTUKey);
		}

		final I_M_HU selectedHU = selectedHUKey.getM_HU();

		final List<IHUKey> vhuKeys;
		if (handlingUnitsBL.isTransportUnit(selectedHU)) // strict (only pure TUs)
		{
			vhuKeys = selectedHUKey.getChildren();
		}
		else if (handlingUnitsBL.isVirtual(selectedHU)) // load 1 VHU key
		{
			final IHUKey vhuKey = selectedHUKey;
			vhuKeys = Collections.singletonList(vhuKey);
		}
		else
		{
			throw new MaterialMovementException(ERR_SelectCUOrTUKey);
		}

		return vhuKeys;
	}

	public void addTUKeysFrom(final Collection<? extends IHUKey> huKeys)
	{
		if (huKeys == null || huKeys.isEmpty())
		{
			return;
		}

		//
		// Get existing VHUs
		// We need them because we want to skip the TUs which are containing those
		final List<IHUKey> vhuKeysExisting = getCUKeyLayout().getKeys(IHUKey.class);

		//
		// Get existings TU keys (if any)
		final IKeyLayout tuKeyLayout = getTUKeyLayout();
		final List<IHUKey> tuKeysExisting = tuKeyLayout.getKeys(IHUKey.class);

		//
		// Iterate given huKeys and
		// * collect all the TUs included in them
		// * make sure the collected TU does not contain any of our existing VHU Keys (makes no sense to distribute on yourself)
		// * make sure each key is visited only once (optimization)
		final Set<IHUKey> tuKeys = new LinkedHashSet<>(tuKeysExisting);
		final Set<IHUKey> seenHUKeys = new HashSet<>(tuKeysExisting); // NOTE: consider existing HU Keys as already seen
		for (final IHUKey huKey : huKeys)
		{
			huKey.iterate(new HUKeyVisitorAdapter()
			{
				@Override
				public VisitResult beforeVisit(final IHUKey key)
				{
					// Make sure we did not already checked this key
					if (!seenHUKeys.add(key))
					{
						return VisitResult.SKIP_DOWNSTREAM;
					}

					// Make sure we are dealing with an HUKey
					final HUKey huKey = HUKey.castIfPossible(key);
					if (huKey == null)
					{
						return VisitResult.CONTINUE;
					}

					final I_M_HU tuHUCandidate = huKey.getM_HU();
					if (handlingUnitsBL.isTransportUnit(tuHUCandidate)) // ONLY TUs are accepted
					{
						// Make sure current TU does not contain any of given VHUs to exclude
						if (huKey.hasAnyChildOf(vhuKeysExisting))
						{
							return VisitResult.SKIP_DOWNSTREAM;
						}

						// Collect the current TU
						tuKeys.add(huKey);
						return VisitResult.SKIP_DOWNSTREAM;
					}
					else
					{
						return VisitResult.CONTINUE;
					}
				}
			});
		}

		tuKeyLayout.setKeys(tuKeys);
	}

	private final I_M_HU getSelectedVHU()
	{
		final HUKey vhuKey = getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(HUKey.class, ERR_SELECT_CU_KEY);
		final I_M_HU vhu = vhuKey.getM_HU();
		return vhu;
	}

	private final List<I_M_HU> getSelectedTUs()
	{
		final IKeyLayout tuKeyLayout = getTUKeyLayout();

		//
		// Get the selected TU Keys.
		// Make sure we get the selected keys exactly in the same order they appear to user, on screen.
		// In this way user will also have predictible and understandable result
		// ... and not having the feeling that the selected HUKeys were randomly picked.
		final List<HUKey> tuKeysSelected = CardinalityOrderComparator.<HUKey> builder()
				.setCardinalityList(tuKeyLayout.getKeys(HUKey.class))
				.copyAndSort(tuKeyLayout.getKeyLayoutSelectionModel().getSelectedKeys(HUKey.class));

		final List<I_M_HU> tus = new ArrayList<>(tuKeysSelected.size());
		for (final HUKey tuKey : tuKeysSelected)
		{
			final I_M_HU tu = tuKey.getM_HU();
			tus.add(tu);
		}

		if (tus.isEmpty())
		{
			throw new MaterialMovementException(ERR_SelectCUOrTUKey);
		}

		return tus;
	}

	@Override
	public final void execute() throws MaterialMovementException
	{
		//
		// Get informations needed to perfrom the distribution
		final I_M_HU vhuToDistribute = getSelectedVHU();
		final Collection<I_M_HU> tusToDistributeOn = getSelectedTUs();
		final BigDecimal qtyCUsPerTU = getQtyCU();
		if (qtyCUsPerTU.signum() <= 0)
		{
			throw new MaterialMovementException(ERR_CU_QTY_NOT_ALLOWED);
		}

		//
		// Perform distribution
		new HUDistributeBuilder()
				.setContext(getTerminalContext())
				.setVHUToDistribute(vhuToDistribute)
				.setTUsToDistributeOn(tusToDistributeOn)
				.setQtyCUsPerTU(getQtyCU())
				.distribute();

		//
		// Refresh affected keys
		refreshAffectedKeys();
	}

	@Override
	protected final void onCUPressed(final ITerminalKey key)
	{
		pcs.firePropertyChange(HUDistributeCUTUPanel.PROPERTY_RefreshSelectAllButton, false, true);
	}

	@Override
	protected final void onTUPressed(final ITerminalKey key)
	{
		pcs.firePropertyChange(HUDistributeCUTUPanel.PROPERTY_RefreshSelectAllButton, false, true);
	}

	@Override
	protected final void onLUPressed(final ITerminalKey key)
	{
		// nothing
	}

	private void refreshAffectedKeys()
	{
		final IHUKeyFactory keyFactory = rootKeyInitial.getKeyFactory();

		//
		// Reset Key Factories cache
		// NOTE: this affects cached attribute storages which were changed
		keyFactory.clearCache();

		//
		// Remove the selected VHU from it's parent if it has no storage (also in that case the VHU will be destroyed)
		final ISplittableHUKey selectedVHUKey = getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(ISplittableHUKey.class);
		if (selectedVHUKey != null)
		{
			selectedVHUKey.destroyIfEmptyStorage();
		}

		//
		// Refresh selected TUs
		final List<ISplittableHUKey> tuKeys = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeys(ISplittableHUKey.class);
		for (final ISplittableHUKey tuKey : tuKeys)
		{
			tuKey.refresh();
		}
	}
}
