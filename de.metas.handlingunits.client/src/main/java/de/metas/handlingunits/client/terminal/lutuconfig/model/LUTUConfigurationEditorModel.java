package de.metas.handlingunits.client.terminal.lutuconfig.model;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyByNameComparator;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModel;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

public class LUTUConfigurationEditorModel extends AbstractLTCUModel
{
	//
	// Services
	private final IHUPIItemProductDAO itemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private boolean _qtyCUReadonlyAlwaysIfNotVirtualPI = false;

	public LUTUConfigurationEditorModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
	}

	@Override
	public void execute() throws MaterialMovementException
	{
		// nothing
	}

	@Override
	protected final void onCUPressed(final ITerminalKey key)
	{
		final CUKey cuKey = (CUKey)key;

		final BigDecimal suggestedQty = cuKey == null ? null : cuKey.getSuggestedQty();
		if (suggestedQty != null)
		{
			setQtyCU(suggestedQty.signum() >= 0 ? suggestedQty : BigDecimal.ZERO);
		}

		loadChildrenToKeyLayout(cuKey, getTUKeyLayout());

		//
		// If CU Key was pressed and that CU Key as a suggested Default configuration to use
		// then set it.
		// Also distribute it's suggested Total Qty CU
		if (cuKey != null)
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = cuKey.getDefaultLUTUConfiguration();
			if (lutuConfiguration != null)
			{
				setDefaults(lutuConfiguration);
			}
			distributeTotalQtyCU(cuKey.getTotalQtyCU());
		}

		//
		// Update UI Status
		updateUIStatus();
	}

	@Override
	protected void onQtyCUChanged(final BigDecimal qtyCU, final BigDecimal qtyCUOld)
	{
		setSelectedKeySuggestedQty(getCUKeyLayout(), qtyCU);
	}

	@Override
	protected final void onTUPressed(final ITerminalKey key)
	{
		final TUKey tuKey = (TUKey)key;

		final BigDecimal suggestedQty = tuKey == null ? null : tuKey.getSuggestedQty();
		if (suggestedQty != null)
		{
			setQtyTU(suggestedQty.signum() >= 0 ? suggestedQty : BigDecimal.ZERO);
		}

		loadChildrenToKeyLayout(tuKey, getLUKeyLayout());
		updateUIStatus();
	}

	@Override
	protected void onQtyTUChanged(final BigDecimal qtyTU, final BigDecimal qtyTUOld)
	{
		setSelectedKeySuggestedQty(getTUKeyLayout(), qtyTU);
	}

	private void setSelectedKeySuggestedQty(final IKeyLayout keyLayout, final BigDecimal qty)
	{
		final ILUTUCUKey key = keyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(ILUTUCUKey.class);
		if (key == null)
		{
			// shall not happen, but skip it
			return;
		}

		key.setSuggestedQty(qty);
	}

	@Override
	protected final void onLUPressed(final ITerminalKey key)
	{
		final LUKey luKey = (LUKey)key;
		final TUKey tuKey = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(TUKey.class);
		final boolean tuKeyNoPI = tuKey == null ? true : tuKey.isNoPI() || tuKey.isVirtualPI();

		// Case: no LU Key is currently selected => set QtyLU to ZERO
		if (luKey == null)
		{
			setQtyLU(0);
		}
		// Case: selected LU Key is actually No-PI => set QtyLU to ZERO
		else if (tuKeyNoPI && luKey.isNoPI())
		{
			setQtyLU(0);
		}
		// Case: user just pressed on an LU key, the current QtyLU is <=0 => set it to ONE for convenience
		else if (getQtyLU() != null && getQtyLU().signum() <= 0)
		{
			setQtyLU(1);
		}

		updateUIStatus();
	}

	private void loadChildrenToKeyLayout(final ILUTUCUKey key, final IKeyLayout childrenKeyLayout)
	{
		final List<ILUTUCUKey> childKeys;
		if (key == null)
		{
			childKeys = Collections.emptyList();
		}
		else
		{
			// 07377:
			// set the quantity CU according to the CU per TU of the key
			if (key instanceof TUKey)
			{
				final TUKey tuKey = (TUKey)key;

				if (!tuKey.isVirtualPI())
				{
					setQtyCU(tuKey.getQtyCUsPerTU());
				}
			}

			childKeys = key.getChildren();
		}

		childrenKeyLayout.setKeys(childKeys);

		//
		// FIXME: when current selected Key is reset, no keyReturn actions are actually triggered so we are triggering them now
		final ITerminalKey selectedChildKey = childrenKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull();
		if (selectedChildKey == null)
		{
			childrenKeyLayout.keyReturned(null);
		}
	}

	public final LUKey getSelectedLUKey()
	{
		return getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(LUKey.class, ERR_SELECT_LU_KEY);
	}

	public final LUKey getSelectedLUKeyOrNull()
	{
		return getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(LUKey.class);
	}

	public final TUKey getSelectedTUKey()
	{
		return getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(TUKey.class, ERR_SELECT_TU_KEY);
	}

	public final TUKey getSelectedTUKeyOrNull()
	{
		return getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(TUKey.class);
	}

	public final CUKey getSelectedCUKey()
	{
		return getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(CUKey.class, ERR_SELECT_CU_KEY);
	}

	public final <T extends CUKey> T getSelectedCUKey(final Class<T> cuKeyClass)
	{
		return getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(cuKeyClass, ERR_SELECT_CU_KEY);
	}

	public final CUKey getSelectedCUKeyOrNull()
	{
		return getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(CUKey.class);
	}

	private final void updateUIStatus()
	{
		final LUKey luKey = getSelectedLUKeyOrNull();
		final TUKey tuKey = getSelectedTUKeyOrNull();

		setQtyLUReadonly(luKey == null || luKey.isNoPI());
		setQtyTUReadonly(tuKey == null);

		// 07501
		// Always check if the QtyCU shall be read only or not
		// NOTE: it shall be read only just if opened from Wareneingnag POS, for non virtual TUs
		setQtyCUReadonly(isQtyCUReadonly());
	}

	public void load(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final List<I_M_HU_LUTU_Configuration> altConfigurations = Collections.emptyList();
		load(lutuConfiguration, altConfigurations);
	}

	public void load(
			final I_M_HU_LUTU_Configuration mainConfiguration,
			final List<I_M_HU_LUTU_Configuration> altConfigurations)
	{
		Check.assumeNotNull(mainConfiguration, "lutuConfiguration not null");
		Check.assumeNotNull(altConfigurations, "altConfigurations not null");

		final Map<String, CUKey> cuKeys = new LinkedHashMap<>();
		final Set<Integer> seenConfigurationIds = new HashSet<>();

		//
		// Create CU Key for main configuration
		{
			final CUKey cuKey = createCUKeyRecursively(mainConfiguration);
			cuKeys.put(cuKey.getId(), cuKey);

			// flag the configuration as seen
			final int configurationId = mainConfiguration.getM_HU_LUTU_Configuration_ID();
			if (configurationId > 0)
			{
				seenConfigurationIds.add(configurationId);
			}
		}

		//
		// Create CU Key for alternative configurations (if any)
		for (final I_M_HU_LUTU_Configuration altConfiguration : altConfigurations)
		{
			// Flag the configuration as seen.
			// Skip those already seen.
			final int configurationId = altConfiguration.getM_HU_LUTU_Configuration_ID();
			if (configurationId > 0 && !seenConfigurationIds.add(configurationId))
			{
				continue;
			}

			final CUKey cuKey = createCUKeyRecursively(altConfiguration);
			final String cuKeyId = cuKey.getId();
			final CUKey cuKeyExisting = cuKeys.get(cuKeyId);
			if (cuKeyExisting == null)
			{
				cuKeys.put(cuKeyId, cuKey);
			}
			else
			{
				cuKeyExisting.mergeChildren(cuKey.getChildren());
			}
		}

		//
		// Set loaded CUKeys to our CU Key buttons layout
		getCUKeyLayout().setKeys(cuKeys.values());

		//
		// Preselect defaults (from main configuration)
		setDefaults(mainConfiguration);
	}

	public void setCUKeys(final Collection<? extends CUKey> cuKeys)
	{
		final IKeyLayout cuKeyLayout = getCUKeyLayout();

		cuKeyLayout.setKeys(cuKeys);

		// Select first CUKey by default
		if (cuKeys != null && !cuKeys.isEmpty())
		{
			final CUKey cuKey = cuKeys.iterator().next();
			cuKeyLayout.setSelectedKey(cuKey);
			onCUPressed(cuKey); // make sure depending fields are updated
		}
	}

	/**
	 *
	 * @param lutuConfiguration
	 * @return {@link CUKey}; never returns <code>null</code>
	 */
	private final CUKey createCUKeyRecursively(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		//
		// Create CU Key
		final CUKey cuKey = createCUKey(lutuConfiguration);

		loadCUKeyRecursively(cuKey, lutuConfiguration);

		return cuKey;
	}

	protected final void loadCUKeyRecursively(final CUKey cuKey, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(cuKey, "cuKey not null");
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		//
		// Create TU Keys
		final Collection<ILUTUCUKey> tuKeys = createTUKeysForCU(lutuConfiguration);
		cuKey.setChildren(tuKeys);

		//
		// Create LU Keys for each TUKey
		for (final ILUTUCUKey tuKey : cuKey.getChildren())
		{
			final I_M_HU_PI tuPI = tuKey.getM_HU_PI();
			final Collection<ILUTUCUKey> luKeys = createLUKeys(tuPI, lutuConfiguration);
			tuKey.setChildren(luKeys);
		}
	}

	private final CUKey createCUKey(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final I_M_Product cuProduct = lutuConfiguration.getM_Product();
		final CUKey cuKey = new CUKey(getTerminalContext(), cuProduct);
		return cuKey;
	}

	protected final void setDefaults(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final CUKey cuKey = createCUKey(lutuConfiguration);
		final String cuKeyId = cuKey.getId();
		// we only created this instance to get hold of its ID. now all dispose; the key is not registered anywhere at this point, but we don't want the finalizer() verification method to make a fuzz
		// TODO don't create it if we don't need it
		cuKey.dispose();

		final ITerminalKey cuKeyToSelect = getCUKeyLayout().getKeyById(cuKeyId);
		getCUKeyLayout().keyReturned(cuKeyToSelect);
		getCUKeyLayout().setSelectedKey(cuKeyToSelect);

		final TUKey tuKey = createTUKey(lutuConfiguration);
		final String tuKeyId = tuKey.getId();
		// we only created this instance to get hold of its ID. now all dispose; the key is not registered anywhere at this point, but we don't want the finalizer() verification method to make a fuzz
		// TODO don't create it if we don't need it
		tuKey.dispose();

		final ITerminalKey tuKeyToSelect = getTUKeyLayout().getKeyById(tuKeyId);
		getTUKeyLayout().keyReturned(tuKeyToSelect);
		getTUKeyLayout().setSelectedKey(tuKeyToSelect);

		final LUKey luKey = createLUKey(lutuConfiguration);
		final String luKeyId = luKey.getId();
		// we only created this instance to get hold of its ID. now all dispose; the key is not registered anywhere at this point, but we don't want the finalizer() verification method to make a fuzz
		// TODO don't create it if we don't need it
		luKey.dispose();

		final ITerminalKey luKeyToSelect = getLUKeyLayout().getKeyById(luKeyId);
		getLUKeyLayout().setSelectedKey(luKeyToSelect);
		getLUKeyLayout().keyReturned(luKeyToSelect);

		if (lutuConfiguration.isInfiniteQtyCU())
		{
			setQtyCU(BigDecimal.ZERO);
		}
		else
		{
			setQtyCU(lutuConfiguration.getQtyCU());
		}

		if (lutuConfiguration.isInfiniteQtyTU())
		{
			setQtyTU(BigDecimal.ZERO);
		}
		else
		{
			setQtyTU(lutuConfiguration.getQtyTU());
		}

		//
		// 07451: Always set LU qty to ONE
		// Background: even if 10 LUs are received, the user wants to deal with them 1-by-1 in 95% of all cases.
		//
		// Case: selected LU Key is actually No-PI => set QtyLU to ZERO
		if ((tuKey.isNoPI() || tuKey.isVirtualPI())
				&& luKey.isNoPI())
		{
			setQtyLU(0);
		}
		// Case: user just pressed on an LU key, the current QtyLU is <=0 => set it to ONE for convenience
		else
		{
			setQtyLU(1);
		}
	}

	public final void save(final ILUTUConfigurationEditor lutuConfigurationEditor)
	{
		Check.assumeNotNull(lutuConfigurationEditor, "lutuConfigurationEditor not null");

		lutuConfigurationEditor.edit(lutuConfiguration -> {
			save(lutuConfiguration);
			return lutuConfiguration;
		});
	}

	/**
	 * Save what user was edited to given <code>lutuConfiguration</code>
	 *
	 * @param lutuConfiguration
	 */
	public final void save(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		final LUKey luKey = getSelectedLUKey();
		final TUKey tuKey = getSelectedTUKey();

		final I_M_Product cuProduct = tuKey.getCuProduct();

		final int qtyTU = getQtyTU().intValueExact();
		final BigDecimal qtyCU = getQtyCU();

		//
		// LU
		if (luKey.isNoPI())
		{
			lutuConfiguration.setM_LU_HU_PI(null);
			lutuConfiguration.setM_LU_HU_PI_Item(null);
			lutuConfiguration.setIsInfiniteQtyLU(false);
			lutuConfiguration.setQtyLU(BigDecimal.ZERO);
		}
		else
		{
			Check.errorIf(luKey.isVirtualPI(), "LUKey not allowed to be a virtual PI: {}", luKey);

			final int qtyLU = getQtyLU().intValueExact();
			lutuConfiguration.setM_LU_HU_PI(luKey.getM_HU_PI());
			lutuConfiguration.setM_LU_HU_PI_Item(luKey.getM_HU_PI_Item_ForChildJoin());
			lutuConfiguration.setIsInfiniteQtyLU(false);
			lutuConfiguration.setQtyLU(BigDecimal.valueOf(qtyLU));
		}

		//
		// TU
		Check.errorIf(tuKey.isNoPI(), "TUKey not allowed to be a No PI: {}", tuKey);

		lutuConfiguration.setM_HU_PI_Item_Product(tuKey.getM_HU_PI_Item_Product());
		lutuConfiguration.setM_TU_HU_PI(tuKey.getM_HU_PI());
		lutuConfiguration.setIsInfiniteQtyTU(false);
		lutuConfiguration.setQtyTU(BigDecimal.valueOf(qtyTU));

		//
		// CU
		lutuConfiguration.setM_Product(cuProduct);
		lutuConfiguration.setC_UOM(tuKey.getCuUOM());
		lutuConfiguration.setIsInfiniteQtyCU(false);
		lutuConfiguration.setQtyCU(qtyCU);
	}

	private Collection<ILUTUCUKey> createTUKeysForCU(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final Properties ctx = getCtx();
		final I_M_Product cuProduct = lutuConfiguration.getM_Product();
		final I_C_UOM cuUOM = lutuConfiguration.getC_UOM();
		final I_C_BPartner bpartner = lutuConfiguration.getC_BPartner();

		final List<I_M_HU_PI_Item_Product> availableHUPIItemProducts = itemProductDAO.retrieveTUs(ctx, cuProduct, bpartner);

		//
		// Create TU Keys
		final Map<String, ILUTUCUKey> tuKeys = new LinkedHashMap<String, ILUTUCUKey>(availableHUPIItemProducts.size());
		for (final I_M_HU_PI_Item_Product tuPIItemProduct : availableHUPIItemProducts)
		{
			final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
			final BigDecimal qtyCUsPerTU = tuPIItemProduct.getQty();

			//
			// Note that the key already has checks for infinite capacity
			final ILUTUCUKey tuKey = createTUKey(tuPIItemProduct, tuPI, cuProduct, cuUOM, qtyCUsPerTU);
			final String tuKeyId = tuKey.getId();
			tuKeys.put(tuKeyId, tuKey);
		}

		//
		// Add the virtual PI to TU Keys
		{
			final ILUTUCUKey tuKey = createVirtualPITUKey(lutuConfiguration);
			final String tuKeyId = tuKey.getId();
			tuKeys.put(tuKeyId, tuKey);
		}

		//
		// Add the TU PI from our configuration (if it was not added yet)
		{
			final TUKey tuKey = createTUKey(lutuConfiguration);
			if (tuKeys.containsKey(tuKey.getId()))
			{
				// call dispose; the key is not registered anywhere at this point, but we don't want the finalizer() verification method to make a fuzz
				// TODO don't create it, if we don't need it
				tuKey.dispose();
			}
			else
			{
				tuKeys.put(tuKey.getId(), tuKey);
			}
		}

		//
		// Sort TU Keys
		final List<ILUTUCUKey> tuKeysToReturn = new ArrayList<>(tuKeys.values());
		Collections.sort(tuKeysToReturn, TerminalKeyByNameComparator.instance);

		//
		// Return TU Keys
		return tuKeysToReturn;
	}

	private TUKey createTUKey(
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final I_M_HU_PI tuPI,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal qtyCUPerTU)
	{
		Check.assumeNotNull(tuPIItemProduct, "tuPIItemProduct not null");

		I_C_UOM cuUOMToUse = tuPIItemProduct.getC_UOM();
		if (cuUOMToUse == null || cuUOMToUse.getC_UOM_ID() <= 0)
		{
			cuUOMToUse = cuUOM;
		}

		final boolean qtyCUPerTUInfinite = tuPIItemProduct.isInfiniteCapacity();

		final TUKey tuKey = createTUKey(tuPIItemProduct, tuPI, cuProduct, cuUOMToUse, qtyCUPerTUInfinite, qtyCUPerTU);
		return tuKey;
	}

	private TUKey createTUKey(
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final I_M_HU_PI tuPI,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final boolean qtyCUPerTUInfinite,
			final BigDecimal qtyCUPerTU)
	{
		final TUKey tuKey = new TUKey(getTerminalContext(), tuPIItemProduct, tuPI, cuProduct, cuUOM, qtyCUPerTUInfinite, qtyCUPerTU);
		return tuKey;
	}

	private TUKey createTUKey(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final I_M_HU_PI tuPI = lutuConfiguration.getM_TU_HU_PI();
		if (tuPI == null || tuPI.getM_HU_PI_ID() <= 0)
		{
			return null; // TODO VHU!
		}

		final I_M_HU_PI_Item_Product huPIIP = lutuConfiguration.getM_HU_PI_Item_Product();
		final I_M_Product cuProduct = lutuConfiguration.getM_Product();
		final I_C_UOM cuUOM = lutuConfiguration.getC_UOM();
		final boolean qtyCUPerTUInfinite = lutuConfiguration.isInfiniteQtyCU();
		final BigDecimal qtyCUPerTU = lutuConfiguration.getQtyCU();

		final TUKey tuKey = createTUKey(huPIIP, tuPI, cuProduct, cuUOM, qtyCUPerTUInfinite, qtyCUPerTU);
		return tuKey;
	}

	private TUKey createVirtualPITUKey(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final Properties ctx = getCtx();
		final I_M_HU_PI_Item virtualPIItem = handlingUnitsDAO.retrieveVirtualPIItem(ctx);
		final I_M_HU_PI virtualPI = virtualPIItem.getM_HU_PI_Version().getM_HU_PI();
		final I_M_HU_PI_Item_Product virtualItemProduct = itemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
		final BigDecimal qtyCUsPerTU = IHUCapacityDefinition.INFINITY;

		final I_M_Product cuProduct = lutuConfiguration.getM_Product();
		final I_C_UOM cuUOM = lutuConfiguration.getC_UOM();

		final TUKey tuKey = createTUKey(virtualItemProduct, virtualPI, cuProduct, cuUOM, qtyCUsPerTU);
		return tuKey;
	}

	private Collection<ILUTUCUKey> createLUKeys(final I_M_HU_PI tuPI, final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		Check.assumeNotNull(tuPI, "tuPI not null");

		final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;

		final I_C_BPartner bpartner = lutuConfiguration.getC_BPartner();
		final List<I_M_HU_PI_Item> luPIItems = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, huUnitType, bpartner);

		final Map<String, ILUTUCUKey> luKeys = new LinkedHashMap<String, ILUTUCUKey>(luPIItems.size());
		for (final I_M_HU_PI_Item luPIItem : luPIItems)
		{
			final I_M_HU_PI luPI = luPIItem.getM_HU_PI_Version().getM_HU_PI();

			final ILUTUCUKey luKey = createLUKey(luPIItem, luPI);
			final String luKeyId = luKey.getId();
			luKeys.put(luKeyId, luKey);
		}

		//
		// Add the NO PI
		{
			final LUKey luKeyNoPI = createNoPILUKey();
			final String luKeyNoPIId = luKeyNoPI.getId();
			luKeys.put(luKeyNoPIId, luKeyNoPI);
		}

		//
		// Add LU Key from configuration (if not added yet)
		if (tuPI.getM_HU_PI_ID() == lutuConfiguration.getM_TU_HU_PI_ID())
		{
			final LUKey luKey = createLUKey(lutuConfiguration);
			if (luKeys.containsKey(luKey.getId()))
			{
				// call dispose; the key is not registered anywhere at this point, but we don't want the finalizer() verification method to make a fuzz
				// TODO don't create it, if we don't need it
				luKey.dispose();
			}
			else
			{
				luKeys.put(luKey.getId(), luKey);
			}
		}

		//
		// Sort
		final List<ILUTUCUKey> luKeysToReturn = new ArrayList<ILUTUCUKey>(luKeys.values());
		Collections.sort(luKeysToReturn, TerminalKeyByNameComparator.instance);

		//
		// Return created LU Keys
		return luKeysToReturn;
	}

	private LUKey createLUKey(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final I_M_HU_PI_Item luPIItem = lutuConfiguration.getM_LU_HU_PI_Item();
		if (luPIItem == null || luPIItem.getM_HU_PI_Item_ID() <= 0)
		{
			return createNoPILUKey();
		}

		final I_M_HU_PI luPI = lutuConfiguration.getM_LU_HU_PI();
		Check.assume(luPI != null && luPI.getM_HU_PI_ID() > 0, "LU PI exists for {}", lutuConfiguration);

		final LUKey luKey = createLUKey(luPIItem, luPI);
		return luKey;
	}

	private LUKey createLUKey(final I_M_HU_PI_Item luPIItem, final I_M_HU_PI luPI)
	{
		final LUKey luKey = new LUKey(getTerminalContext(), luPI, luPIItem);
		return luKey;
	}

	private LUKey createNoPILUKey()
	{
		final I_M_HU_PI_Item noPIItem = handlingUnitsDAO.retrieveNoPIItem(getCtx());
		final I_M_HU_PI noPI = noPIItem.getM_HU_PI_Version().getM_HU_PI();

		final LUKey luKey = new LUKey(getTerminalContext(), noPI, noPIItem);
		return luKey;
	}

	/**
	 * Distribute a total amount of CUs (i.e. products) and set Qty CU, TU, LU.
	 *
	 * Quantity will be distributed only if there is an LU,TU,CU key already selected and those don't have standard infinite capacities.
	 *
	 * @param qty
	 * @return true if quantity could be distributed
	 */
	public boolean distributeTotalQtyCU(final BigDecimal qty)
	{
		if (qty == null)
		{
			return false;
		}

		if (qty.signum() <= 0)
		{
			return false;
		}

		//
		// Get CU related configs
		final CUKey cuKey = getSelectedCUKeyOrNull();
		if (cuKey == null)
		{
			return false;
		}

		//
		// Get TU related configs
		final TUKey tuKey = getSelectedTUKeyOrNull();
		if (tuKey == null)
		{
			return false;
		}

		final boolean qtyCUsPerTUInfinite = tuKey.isQtyCUsPerTUInfinite();
		final BigDecimal qtyCUsPerTU;
		if (qtyCUsPerTUInfinite)
		{
			qtyCUsPerTU = null;
		}
		else
		{
			qtyCUsPerTU = tuKey.getQtyCUsPerTU();
			if (qtyCUsPerTU.signum() <= 0)
			{
				return false;
			}
		}

		//
		// Get LU related configs
		final LUKey luKey = getSelectedLUKeyOrNull();
		if (luKey == null)
		{
			return false;
		}
		final BigDecimal qtyTUsPerLU = luKey.getQtyTUsPerLU();

		//
		// Calculate: Qty TU and Qty CU
		BigDecimal qtyTU;
		final BigDecimal qtyCU;
		if (qtyCUsPerTUInfinite)
		{
			qtyTU = BigDecimal.ONE;
			qtyCU = qty;
		}
		else
		{
			qtyTU = qty.divide(qtyCUsPerTU, 0, RoundingMode.UP);
			if (qtyTU.signum() <= 0)
			{
				// shall not be possible
				throw new IllegalStateException("qtyTU <= 0");
			}
			else if (qtyTU.compareTo(BigDecimal.ONE) == 0)
			{
				qtyCU = qty.min(qtyCUsPerTU);
			}
			else
			{
				qtyCU = qtyCUsPerTU;
			}
		}

		//
		// Calculate: Qty LU
		final BigDecimal qtyLU;
		if (qtyTUsPerLU.signum() <= 0)
		{
			qtyLU = BigDecimal.ZERO;
		}
		else
		{
			qtyLU = qtyTU.divide(qtyTUsPerLU, 0, RoundingMode.UP);
			qtyTU = qtyTU.min(qtyTUsPerLU);
		}

		//
		// Update Qtys
		setQtyCU(qtyCU);
		setQtyTU(qtyTU);
		setQtyLU(qtyLU);
		updateUIStatus();

		return true;
	}

	public final void setQtyCUReadonlyAlwaysIfNotVirtualPI()
	{
		_qtyCUReadonlyAlwaysIfNotVirtualPI = true;
	}

	public boolean isQtyCUReadonlyAlwaysIfNotVirtualPI()
	{
		return _qtyCUReadonlyAlwaysIfNotVirtualPI;
	}

	@Override
	public boolean isQtyCUReadonly()
	{

		final boolean initialQtyCUReadonly = super.isQtyCUReadonly();
		if (isQtyCUReadonlyAlwaysIfNotVirtualPI())
		{
			final TUKey tuKey = getSelectedTUKeyOrNull();
			if (tuKey == null)
			{
				// shall not happen
				return true;
			}

			// In case our TU is virtual, we need to allow user to edit the QtyCU
			if (tuKey.isVirtualPI())
			{
				return false;
			}

			return true;
		}

		return initialQtyCUReadonly;
	}

}
