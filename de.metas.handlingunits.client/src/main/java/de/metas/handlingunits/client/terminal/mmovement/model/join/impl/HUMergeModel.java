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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.ITUMergeBuilder;
import de.metas.handlingunits.allocation.transfer.impl.TUMergeBuilder;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractMaterialMovementModel;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.client.terminal.mmovement.model.join.service.ILUTUJoinOrMergeBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;

public final class HUMergeModel extends AbstractMaterialMovementModel
{
	private static final String ERR_SELECT_HU_KEY = "SelectHUKey";
	public static final String ERR_MULTIPLE_PRODUCTS = "MultipleCUsForTUMerge";

	//
	// Services
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUKeyFactory keyFactory;

	/**
	 * Selected Join Keys for merge
	 */
	private final List<ILUTUJoinKey> _selectedKeys;
	private final List<ITerminalKey> selectedChildrenKeys;
	private final int _selectedChildrenCount;

	/**
	 * The merge type selected in this model
	 */
	private final MergeType mergeType;

	/**
	 * Key Layout (supports Trading Units and Loading Units)
	 */
	private final DefaultKeyLayout keyLayout;

	public HUMergeModel(final ITerminalContext terminalContext,
			final IHUKeyFactory keyFactory,
			final List<ILUTUJoinKey> selectedKeys, final List<ITerminalKey> selectedChildrenKeys, final int selectedChildrenQty,
			final MergeType mergeType)
	{
		super(terminalContext);

		this.keyFactory = keyFactory;

		_selectedKeys = selectedKeys;
		this.selectedChildrenKeys = selectedChildrenKeys;
		_selectedChildrenCount = selectedChildrenQty;
		this.mergeType = mergeType;

		keyLayout = new DefaultKeyLayout(getTerminalContext());
		keyLayout.setRows(1);
		keyLayout.setColumns(layoutConstantsBL.getConstantAsInt(getLayoutConstants(), IHUPOSLayoutConstants.PROPERTY_HUSplit_KeyColumns));
		// keyLayout.addTerminalKeyListener(listener); // we're not using listeners here

		final List<ITerminalKey> selectedKeysConv = new ArrayList<ITerminalKey>();
		for (final ITerminalKey selectedKey : selectedKeys)
		{
			selectedKeysConv.add(selectedKey);
		}
		keyLayout.setKeys(selectedKeysConv);

		final IKeyLayoutSelectionModel selectionModel = keyLayout.getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);
		selectionModel.setAutoSelectIfOnlyOne(true);
	}

	@Override
	public void execute() throws MaterialMovementException
	{
		if (mergeType == MergeType.LoadingUnit)
		{
			mergeLoadingUnits();
		}
		else if (mergeType == MergeType.TradingUnit)
		{
			mergeTradingUnits();
		}
		else
		{
			throw new UnsupportedOperationException("MergeType not supported: " + mergeType);
		}
	}

	private void mergeLoadingUnits()
	{
		//
		// Selected LUs
		final List<LUJoinKey> selectedLUKeys = getSelectedKeys(LUJoinKey.class);
		if (selectedLUKeys.isEmpty())
		{
			throw new MaterialMovementException(HUMergeModel.ERR_SELECT_HU_KEY);
		}

		//
		// The LU that was selected in the Merge pop-up
		// Everything will be merged on this LUKey
		final LUJoinKey selectedLUKey = getSelectedKey(LUJoinKey.class);
		//
		// The TUs that will be moved on the selected LU
		final List<TUJoinKey> tuKeysToJoin;

		//
		// Selected TUs
		final List<ITerminalKey> selectedTUs = selectedChildrenKeys;
		if (selectedTUs.isEmpty())
		{
			tuKeysToJoin = createTUJoinKeysRecursively(selectedLUKeys, selectedLUKey);
		}
		else
		{
			// Partial
			tuKeysToJoin = createTUJoinKeysRecursively(selectedTUs, getSelectedChildrenCount());
		}

		Services.get(ILUTUJoinOrMergeBL.class).joinOrMergeHUs(getTerminalContext(), selectedLUKey, tuKeysToJoin);
	}

	private List<TUJoinKey> createTUJoinKeysRecursively(final List<LUJoinKey> luKeys, final LUJoinKey selectedLUKey)
	{
		final List<TUJoinKey> result = new ArrayList<TUJoinKey>();
		for (final LUJoinKey luKey : luKeys)
		{
			if (luKey.equals(selectedLUKey))
			{
				//
				// The selected key shall be ignored. No point in allocating it on itself
				continue;
			}

			final List<TUJoinKey> tuKeys = createTUJoinKeysRecursively(luKey);
			result.addAll(tuKeys);
		}

		return result;
	}

	private List<TUJoinKey> createTUJoinKeysRecursively(final LUJoinKey luKey)
	{
		final IHUKey huKey = keyFactory.createKey(luKey.getM_HU(), luKey.getHUDocumentLine());

		final List<TUJoinKey> result = new ArrayList<TUJoinKey>();
		for (final IHUKey tuHUKey : huKey.getChildren())
		{
			final List<TUJoinKey> tuJoinKeys = createTUJoinKeysRecursively(tuHUKey);
			result.addAll(tuJoinKeys);
		}

		return result;
	}

	private List<TUJoinKey> createTUJoinKeysRecursively(final List<ITerminalKey> selectedTUs, final int tuCountMax)
	{
		final List<TUJoinKey> result = new ArrayList<TUJoinKey>();
		int tuCountRemaining = tuCountMax;
		for (final ITerminalKey tuKey : selectedTUs)
		{
			if (tuCountRemaining <= 0)
			{
				//
				// We satisfied the requested qty; return
				return result;
			}

			if (tuKey instanceof TUJoinKey)
			{
				result.add((TUJoinKey)tuKey);
				tuCountRemaining--;
			}
			else if (tuKey instanceof IHUKey)
			{
				final IHUKey tuHUKey = (IHUKey)tuKey;
				final List<TUJoinKey> tuJoinKeys = createTUJoinKeysRecursively(tuHUKey);
				final int tuJoinKeysCount = tuJoinKeys.size();
				if (tuJoinKeysCount == 0)
				{
					// do nothing
				}
				else if (tuJoinKeysCount <= tuCountRemaining)
				{
					result.addAll(tuJoinKeys);
					tuCountRemaining -= tuJoinKeysCount;
				}
				else
				{
					result.addAll(tuJoinKeys.subList(0, tuCountRemaining));
					tuCountRemaining = 0;
				}
			}
		}

		return result;
	}

	private List<TUJoinKey> createTUJoinKeysRecursively(final IHUKey huKey)
	{
		final List<TUJoinKey> result = new ArrayList<TUJoinKey>();

		if (huKey.isGrouping())
		{
			//
			// We're dealing with a CompositeHUKey-TU
			for (final IHUKey tuHUKey : huKey.getChildren())
			{
				final TUJoinKey tuJoinKey = createTUJoinKeyOrNull(tuHUKey);
				if (tuJoinKey != null)
				{
					result.add(tuJoinKey);
				}
			}
		}
		else
		{
			final TUJoinKey tuJoinKey = createTUJoinKeyOrNull(huKey);
			if (tuJoinKey != null)
			{
				result.add(tuJoinKey);
			}
		}

		return result;
	}

	private TUJoinKey createTUJoinKeyOrNull(final IHUKey key)
	{
		if (!(key instanceof HUKey))
		{
			return null;
		}
		final HUKey huKey = (HUKey)key;

		final I_M_HU hu = huKey.getM_HU();
		final boolean virtual = huKey.isVirtualPI();

		final TUJoinKey tuJoinKey = new TUJoinKey(getTerminalContext(), hu, huKey.findDocumentLineOrNull(), virtual);
		return tuJoinKey;
	}

	private void mergeTradingUnits()
	{
		//
		// Selected TUs
		final List<ILUTUJoinKey> selectedTUs = _selectedKeys;
		if (selectedTUs.isEmpty())
		{
			throw new MaterialMovementException(HUMergeModel.ERR_SELECT_HU_KEY);
		}

		//
		// Selected CUs (must be one and only one):
		// for the time being, we want to merge one CU at a time. This may be changed in future
		final List<ITerminalKey> selectedCUs = selectedChildrenKeys;
		Check.assume(selectedCUs.size() == 1, "Only one selected CU to be merged at once ({})", selectedCUs);

		final ITerminalKey selectedCU = selectedCUs.get(0);
		Check.assumeInstanceOf(selectedCU, HUKey.class, "selectedCU");
		final HUKey sourceCUKey = (HUKey)selectedCU;

		final I_M_HU sourceTUHU = handlingUnitsDAO.retrieveParent(sourceCUKey.getM_HU());

		//
		// The TU that was selected in the Merge pop-up
		final ILUTUJoinKey selectedKey = keyLayout.getKeyLayoutSelectionModel().getSelectedKey(ILUTUJoinKey.class, HUMergeModel.ERR_SELECT_HU_KEY);

		final List<I_M_HU> sourceHUs = Collections.singletonList(sourceTUHU);
		mergeTUs(selectedKey.getM_HU(), sourceHUs, selectedCU);
	}

	private void mergeTUs(final I_M_HU targetTU, final List<I_M_HU> sourceHUs, final ITerminalKey selectedChildKey)
	{
		final IMutableHUContext huContextInitial = Services.get(IHandlingUnitsBL.class).createMutableHUContext(getTerminalContext());

		final ITUMergeBuilder mergeBuilder = new TUMergeBuilder(huContextInitial);
		mergeBuilder.setSourceHUs(sourceHUs);
		mergeBuilder.setTargetTU(targetTU);

		//
		// Create Allocation Request for all Qty from CU Key
		Check.assumeInstanceOf(selectedChildKey, IHUKey.class, "Selected Child Key");
		final HUKey huKey = (HUKey)selectedChildKey;
		final List<IHUProductStorage> storages = huKey.getProductStorages();

		if (storages.size() != 1)
		{
			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(huContextInitial.getCtx(), HUMergeModel.ERR_MULTIPLE_PRODUCTS));
		}

		final IHUProductStorage storage = storages.get(0);

		mergeBuilder.setCUProduct(storage.getM_Product());
		mergeBuilder.setCUUOM(storage.getC_UOM());

		final BigDecimal qtyCU = new BigDecimal(getSelectedChildrenCount());
		mergeBuilder.setCUQty(qtyCU);

		mergeBuilder.setCUTrxReferencedModel(storage.getM_HU());

		mergeBuilder.mergeTUs();
	}

	@Override
	public void dispose()
	{
		// nothing at this level
	}

	/**
	 * @return the merge type selected in this model
	 */
	public MergeType getMergeType()
	{
		return mergeType;
	}

	/**
	 * @return model KeyLayout
	 */
	public IKeyLayout getKeyLayout()
	{
		return keyLayout;
	}

	private <T extends ILUTUJoinKey> List<T> getSelectedKeys(final Class<T> keyType)
	{
		final List<T> result = new ArrayList<T>(_selectedKeys.size());
		for (final ILUTUJoinKey selectedKey : _selectedKeys)
		{
			final T selectedKeyConv = keyType.cast(selectedKey);
			result.add(selectedKeyConv);
		}

		return result;
	}

	private <T extends ILUTUJoinKey> T getSelectedKey(final Class<T> keyType)
	{
		return keyLayout.getKeyLayoutSelectionModel().getSelectedKey(keyType, HUMergeModel.ERR_SELECT_HU_KEY);
	}

	private int getSelectedChildrenCount()
	{
		// Partial
		Check.assume(_selectedChildrenCount > 0, "At least one child selected", _selectedChildrenCount);
		return _selectedChildrenCount;
	}
}
