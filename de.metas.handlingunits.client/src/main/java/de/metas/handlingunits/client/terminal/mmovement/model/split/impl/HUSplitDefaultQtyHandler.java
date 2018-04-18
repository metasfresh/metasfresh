package de.metas.handlingunits.client.terminal.mmovement.model.split.impl;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.ISplittableHUKey;
import de.metas.handlingunits.client.terminal.lutuconfig.model.ILUTUCUKey;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUKey;
import de.metas.handlingunits.client.terminal.lutuconfig.model.TUKey;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUDefaultQtyHandler;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.logging.LogManager;

/**
 * Handler for calculating CU-TU-LU quantities automatically for {@link HUSplitModel}.
 *
 * @author al
 */
/* package */final class HUSplitDefaultQtyHandler implements ILTCUDefaultQtyHandler
{
	//
	// Logger
	private static final transient Logger logger = LogManager.getLogger(HUSplitDefaultQtyHandler.class.getName());

	//
	// Services
	protected final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	protected final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private final ISplittableHUKey huToSplitKey;
	private final boolean allowSameTUInfiniteCapacity;

	public HUSplitDefaultQtyHandler(final ISplittableHUKey huToSplitKey, final boolean allowSameTUInfiniteCapacity)
	{
		super();

		Check.assumeNotNull(huToSplitKey, "huToSplitKey not null");
		this.huToSplitKey = huToSplitKey;
		this.allowSameTUInfiniteCapacity = allowSameTUInfiniteCapacity;
	}

	@Override
	public void calculateDefaultQtys(final ILTCUModel model)
	{
		Check.assumeNotNull(model, "model not null");

		final I_M_HU huToSplit = huToSplitKey.getM_HU();

		final boolean isCalculatedForExistingConfig = calculateDefaultQtysForExistingConfiguration(model, huToSplit);
		if (isCalculatedForExistingConfig)
		{
			return; // only calculate defaults depending on capacity IF we have not matched the HU's existing configuration
		}
		calculateDefaultLUTUQtys(model);
	}

	/**
	 * @param hu
	 * @return true if the given <code>HU</code> is among the selected LUs or TUs
	 */
	private boolean calculateDefaultQtysForExistingConfiguration(final ILTCUModel model, final I_M_HU hu)
	{
		final I_M_HU_PI huPI = handlingUnitsBL.getPI(hu);

		final IKeyLayout tuKeyLayout = model.getTUKeyLayout();
		final ILUTUCUKey selectedTUKey = tuKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(ILUTUCUKey.class);

		final IKeyLayout luKeyLayout = model.getLUKeyLayout();
		final ILUTUCUKey selectedLUKey = luKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(ILUTUCUKey.class);

		autoDetectIfNoneSelected(model, hu, selectedTUKey, selectedLUKey);

		final HUMatchResult matchResultTU = isHUMatchLUTUKey(selectedTUKey, huPI); // given HU is a TU
		final HUMatchResult matchResultLU = isHUMatchLUTUKey(selectedLUKey, huPI); // given HU is an LU

		final boolean isTUHUMatch = matchResultTU == HUMatchResult.Match;
		final boolean isLUHUMatch = matchResultLU == HUMatchResult.Match;

		//
		// These matches are mutually-exclusive (by the given HU's PI)
		if (isTUHUMatch)
		{
			calculateForExistingTradingUnit(model, hu);
		}
		else if (isLUHUMatch)
		{
			calculateForExistingLoadingUnit(model, hu);
		}
		else
		{
			calculateDefaultLUTUQtys(model);
		}

		return false;
	}

	private void autoDetectIfNoneSelected(final ILTCUModel model, final I_M_HU hu, final ILUTUCUKey selectedTUKey, final ILUTUCUKey selectedLUKey)
	{
		if (selectedTUKey == null)
		{
			if (handlingUnitsBL.isVirtual(hu) // VPIs and TUs should be handled the same way
					|| handlingUnitsBL.isTransportUnit(hu))
			{
				final IKeyLayout tuKeyLayout = model.getTUKeyLayout();

				final ILUTUCUKey lutuKey = autoDetectKeyForSelection(tuKeyLayout, hu);
				if (lutuKey != null)
				{
					model.setTUKey(lutuKey);
				}
			}
			else if (handlingUnitsBL.isLoadingUnit(hu))
			{
				autoDetectTUForTargetLU(model, hu);
			}
			else
			{
				// nothing, ignore
			}
		}

		if (selectedLUKey == null)
		{
			if (handlingUnitsBL.isTransportUnit(hu)
					|| handlingUnitsBL.isVirtual(hu))
			{
				autoDetectLUForTargetTU(model, hu);
			}
			else if (handlingUnitsBL.isLoadingUnit(hu))
			{
				final IKeyLayout luKeyLayout = model.getLUKeyLayout();

				final ILUTUCUKey lutuKey = autoDetectKeyForSelection(luKeyLayout, hu);
				if (lutuKey != null)
				{
					model.setLUKey(lutuKey);
				}
			}
			else
			{
				// nothing, ignore
			}
		}
	}

	/**
	 * NOTE: Do not ever fucking ever return null. I don't want to spend another new year's pre-eve debugging this shit. We want a key here. No exceptions.<br>
	 * Only case when null is when there are no keys
	 *
	 * @param keyLayout
	 * @param hu
	 * @return detect key and select it, then return it
	 */
	private ILUTUCUKey autoDetectKeyForSelection(final IKeyLayout keyLayout, final I_M_HU hu)
	{
		Check.assume(keyLayout.getKeysCount() > 0, "keyLayout {} shall be loaded with keys", keyLayout);

		final boolean virtual = handlingUnitsBL.isVirtual(hu);
		final I_M_HU_PI huPI = handlingUnitsBL.getPI(hu);

		final List<ILUTUCUKey> lutuKeys = keyLayout.getKeys(ILUTUCUKey.class);
		for (final ILUTUCUKey lutuKey : lutuKeys)
		{
			final I_M_HU_PI lutuPI = lutuKey.getM_HU_PI();
			if (lutuPI.getM_HU_PI_ID() != huPI.getM_HU_PI_ID()
					&& !virtual)      // if virtual, then all PIs can be applied
			{
				continue;
			}
			keyLayout.getKeyLayoutSelectionModel().onKeySelected(lutuKey);
			return lutuKey;
		}

		//
		// Assume that in such cases the dev made sure to have *something* preselected
		final ILUTUCUKey alreadySelectedKeyFallback = (ILUTUCUKey)keyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull();
		// Check.assumeNotNull(alreadySelectedKeyFallback, "alreadySelectedKeyFallback not null"); // not applicable for VPI on LU
		return alreadySelectedKeyFallback;
	}

	private void autoDetectTUForTargetLU(final ILTCUModel model, final I_M_HU luHU)
	{
		// group luHU's trade units by their M_HU_PI_ID
		// TODO: figure out how to also do the next step of getting maxOccurrencePIId in an more elegant way
		final Map<Integer, Long> includedTUCounts = handlingUnitsDAO
				.retrieveIncludedHUs(luHU)
				.stream()
				.map(tu -> handlingUnitsBL.getEffectivePIVersion(tu).getM_HU_PI_ID())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		// find the M_HU_PI_ID that occurs most often
		long maxOccurrences = 0;
		Integer maxOccurrencePIId = null;
		for (final Entry<Integer, Long> includedTUCount : includedTUCounts.entrySet())
		{
			final long occurrences = includedTUCount.getValue();
			if (maxOccurrences < occurrences)
			{
				maxOccurrences = occurrences;
				maxOccurrencePIId = includedTUCount.getKey();
			}
		}
		Check.assumeNotNull(maxOccurrencePIId, "maxOccurrencePI not null");

		final I_M_HU_PI noPI = handlingUnitsDAO.retrievePackingItemTemplatePI(InterfaceWrapperHelper.getCtx(luHU));
		final I_M_HU_PI virtualPI = handlingUnitsDAO.retrieveVirtualPI(InterfaceWrapperHelper.getCtx(luHU));

		final IKeyLayout tuKeyLayout = model.getTUKeyLayout();

		// iterate the possible TU-keys and pick the *last* one that has
		// the M_HU_PI which occurred most often and
		// does not represent "no handling unit"
		// ..unless the the M_HU_PI which occurred most often is the VirtualPI-one..in that case, just take the last
		ILUTUCUKey tuKeyToSelect = null;
		for (final ILUTUCUKey tuKey : tuKeyLayout.getKeys(ILUTUCUKey.class))
		{
			final I_M_HU_PI tuKeyPI = tuKey.getM_HU_PI();
			if (tuKeyPI.getM_HU_PI_ID() != maxOccurrencePIId
					&& tuKeyPI.getM_HU_PI_ID() != noPI.getM_HU_PI_ID() && maxOccurrencePIId != virtualPI.getM_HU_PI_ID())
			{
				continue;
			}
			tuKeyToSelect = tuKey;
		}

		Check.assumeNotNull(tuKeyToSelect, "tuKeyToSelect not null"); // there *must* be one at this point
		tuKeyLayout.getKeyLayoutSelectionModel().onKeySelected(tuKeyToSelect);
		model.setTUKey(tuKeyToSelect);
	}

	private void autoDetectLUForTargetTU(final ILTCUModel model, final I_M_HU tuHU)
	{
		final IKeyLayout luKeyLayout = model.getLUKeyLayout();
		final List<ILUTUCUKey> lutuKeys = luKeyLayout.getKeys(ILUTUCUKey.class);

		final int noHUPIId = handlingUnitsDAO.getPackingItemTemplate_HU_PI_ID();
		final boolean isTopLevelHU = handlingUnitsBL.isTopLevel(tuHU);

		final I_M_HU luHU = handlingUnitsDAO.retrieveParent(tuHU);
		final I_M_HU_PI luPI;
		if (luHU != null)
		{
			luPI = handlingUnitsBL.getPI(luHU);
		}
		else
		{
			luPI = null;
		}

		for (final ILUTUCUKey lutuKey : lutuKeys)
		{
			final I_M_HU_PI lutuPI = lutuKey.getM_HU_PI();

			if (isTopLevelHU && lutuPI.getM_HU_PI_ID() == noHUPIId // No Handling Unit PI
					|| !isTopLevelHU && lutuPI.getM_HU_PI_ID() == luPI.getM_HU_PI_ID())      // LU-PI matched
			{
				luKeyLayout.getKeyLayoutSelectionModel().onKeySelected(lutuKey);
				model.setLUKey(lutuKey);
			}
		}
	}

	private void calculateForExistingTradingUnit(final ILTCUModel model, final I_M_HU tuHU)
	{
		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuHU), "HU is Transport Unit ({})", tuHU);

		final boolean isTopLevelHU = handlingUnitsBL.isTopLevel(tuHU);
		if (isTopLevelHU)
		{
			calculateDefaultLUTUQtys(model);
		}
		else
		{
			final BigDecimal qtyCUPerTU = getKeyFactory().getStorageFactory()
					.getStorage(tuHU)
					.getQtyForProductStorages();
			model.setQtyCU(qtyCUPerTU);
			model.setQtyTU(1);
			model.setQtyLU(1); // we are currently splitting the full TU off
		}
	}

	private void calculateForExistingLoadingUnit(final ILTCUModel model, final I_M_HU luHU)
	{
		Check.assume(handlingUnitsBL.isLoadingUnit(luHU), "HU is Loading Unit ({})", luHU);
		Check.assume(handlingUnitsBL.isTopLevel(luHU), "HU is top-level ({})", luHU);

		final TUKey tuKey = model.getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(TUKey.class, AbstractLTCUModel.ERR_SELECT_TU_KEY);
		final I_M_HU_PI selectedTUPI = tuKey.getM_HU_PI();

		BigDecimal maxQtyCUPerTU = BigDecimal.ZERO;

		final List<I_M_HU> includedTUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		for (final I_M_HU includedTU : includedTUs)
		{
			final I_M_HU_PI includedTUPI = handlingUnitsBL.getPI(includedTU);
			if (includedTUPI.getM_HU_PI_ID() != selectedTUPI.getM_HU_PI_ID())
			{
				return;
			}

			final BigDecimal qtyCUPerTU = getKeyFactory().getStorageFactory()
					.getStorage(includedTU)
					.getQtyForProductStorages();

			if (maxQtyCUPerTU.compareTo(qtyCUPerTU) < 0)
			{
				maxQtyCUPerTU = qtyCUPerTU;
			}
		}

		model.setQtyCU(maxQtyCUPerTU);
		model.setQtyTU(includedTUs.size()); // use the amount of TUs already on the LU
		model.setQtyLU(1);
	}

	/**
	 * Calculates default LU-TU-CU quantities
	 *
	 * @param model
	 */
	private void calculateDefaultLUTUQtys(final ILTCUModel model)
	{
		//
		// Calculate full qty to split from the original selected HU
		final I_M_HU huToSplit = huToSplitKey.getM_HU();
		final BigDecimal fullCUQty = getKeyFactory().getStorageFactory()
				.getStorage(huToSplit)
				.getQtyForProductStorages();

		//
		// Calculate TU Qty (note that we should always have a TU key selected at this point)
		final TUKey tuKey = model.getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(TUKey.class);
		if (tuKey == null)
		{
			//
			// Reason why we're doing this and not directly throwing an exception in the user's face:
			// Due to the high number of test cases and low-to-none GUI test coverage, there are cases where auto-detection does not work.
			// Therefore, we want to skip the initial qty calculation and not interfere with the user's work (albeit a bit annoying that the qtys are not calculated)
			final AdempiereException ex = new AdempiereException("Developer error: tuKey was null (not detected) for huToSplit value={}", new Object[] { huToSplit.getValue() });
			logger.error(ex.getLocalizedMessage(), ex);

			return; // protection: log the exception message and do not attempt to calculate any further.
		}

		final boolean tuCapacityInfinite = tuKey.isQtyCUsPerTUInfinite();
		final BigDecimal qtyCU = model.getQtyCU();
		BigDecimal tuCapacity = tuKey.getQtyCUsPerTU();

		final boolean tuNoPI = tuKey.isVirtualPI()
				|| tuKey.isNoPI(); // backward compatiblity: in past there were some cases where for some reason the NoPI was used instead of VPI
		if (tuCapacityInfinite && !tuNoPI)
		{
			if (allowSameTUInfiniteCapacity)
			{
				tuCapacity = fullCUQty;
			}
			else
			{
				// If infinite capacity TUs are disallowed, do nothing
				return;
			}
		}

		final BigDecimal deltaTU;
		if (tuNoPI)      // Virtual PIs shall use full qty to split; same goes for division by zero
		{
			model.setQtyCU(fullCUQty);
			deltaTU = fullCUQty; // divide by full qty to split
		}
		else if (qtyCU == null || qtyCU.signum() == 0)
		{
			model.setQtyCU(tuCapacity); // set the CUQty to the TU's capacity if it's zero
			deltaTU = tuCapacity; // divide by TU capacity
		}
		else if (qtyCU.compareTo(tuCapacity) >= 0)
		{
			deltaTU = tuCapacity; // divide by TU capacity (we'll get more TUs)
		}
		else
		{
			deltaTU = qtyCU; // divide by CUQty
		}

		//
		// NOTE that this can be changed, depending on how many LUs are involved (if more than 1, then we'll use the LU capacity)
		//
		// TUQty = AmountToSplit / Delta, with scale=0, rounded up so that we get the maximum amount of TUs necessary
		Check.assume(deltaTU.signum() != 0, "Error in DeltaTU calculation (division by zero)");
		final BigDecimal qtyTUCandidate = fullCUQty.divide(deltaTU,
				0,      // no scale
				RoundingMode.CEILING); // always round up

		//
		// Calculate LU Qty
		final LUKey luKey = model.getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(LUKey.class);
		final BigDecimal luCapacity;

		final BigDecimal qtyLUNew;
		if (luKey != null && !luKey.isNoPI())
		{
			luCapacity = luKey.getQtyTUsPerLU();

			final BigDecimal deltaLU;
			if (qtyTUCandidate.compareTo(luCapacity) >= 0
					&& luCapacity.signum() != 0)      // make sure that the LU's capacity is not 0
			{
				deltaLU = luCapacity;
			}
			else
			{
				deltaLU = qtyTUCandidate;
			}

			//
			// TUQty = QtyTUNew / DeltaLU, with scale=0, rounded up so that we get the maximum amount of LUs necessary
			qtyLUNew = qtyTUCandidate.divide(deltaLU,
					0,      // no scale
					RoundingMode.CEILING); // always round up
		}
		else
		{
			// don't change the TU Qty but set the LU Qty to ZERO
			luCapacity = BigDecimal.ZERO;
			qtyLUNew = BigDecimal.ZERO;
		}

		//
		// Finally set LU qty
		model.setQtyLU(qtyLUNew);

		//
		// As specified above: we're deciding whether to use LUPerTU, or the full amount of needed TUs (i.e No LU, or only one is needed)
		final BigDecimal qtyTUNew;
		if (qtyLUNew.compareTo(BigDecimal.ONE) <= 0)
		{
			qtyTUNew = qtyTUCandidate;
		}
		else
		{
			qtyTUNew = luCapacity;
		}
		model.setQtyTU(qtyTUNew);
	}

	private IHUKeyFactory getKeyFactory()
	{
		return huToSplitKey.getKeyFactory();
	}

	/**
	 * @param lutuKey
	 * @param huPI
	 * @return true if given HU PI matches the one attached on the LU-TU key
	 */
	private HUMatchResult isHUMatchLUTUKey(final ILUTUCUKey lutuKey, final I_M_HU_PI huPI)
	{
		final HUMatchResult match;
		if (lutuKey != null)
		{
			final I_M_HU_PI selectedTUPI = lutuKey.getM_HU_PI();
			match = huPI.getM_HU_PI_ID() == selectedTUPI.getM_HU_PI_ID() ? HUMatchResult.Match : HUMatchResult.NoMatch;
		}
		else
		{
			match = HUMatchResult.NoKeySelected;
		}
		return match;
	}

	private static enum HUMatchResult
	{
		NoMatch, Match, NoKeySelected
	}
}
