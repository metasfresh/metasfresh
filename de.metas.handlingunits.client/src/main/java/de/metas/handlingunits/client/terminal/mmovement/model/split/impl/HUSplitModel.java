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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilder;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.ISplittableHUKey;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUDefaultQtyHandler;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModel;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.interfaces.I_C_BPartner;

public final class HUSplitModel extends AbstractLTCUModel
{
	private static final String ERR_CU_NO_STORAGE_FOUND = "CUNoStorageFound";

	// private static final String ERR_CANNOT_ALLOCATE_QTY = "CannotAllocateQty"; // FIXME: delete from db first
	private static final String ERR_CU_QTY_NOT_ALLOWED = "CUQtyNotAllowed";
	private static final String ERR_TU_QTY_NOT_ALLOWED = "TUQtyNotAllowed";
	private static final String ERR_LU_QTY_NOT_ALLOWED = "LUQtyNotAllowed";

	/**
	 * "Our" HUKey, the one which the user selected for split
	 */
	private final ISplittableHUKey huToSplitKey;

	private final Boolean allowSameTUInfiniteCapacity;

	/**
	 * Handler for calculating default quantities
	 */
	private final ILTCUDefaultQtyHandler defaultQtyHandler;

	private final IHUPIItemProductDAO itemProductDAO = Services.get(IHUPIItemProductDAO.class);

	public HUSplitModel(final ITerminalContext terminalContext, final ISplittableHUKey huToSplit)
	{
		super(terminalContext);

		Check.assumeNotNull(huToSplit, "huToSplit not null");
		Check.assume(!huToSplit.isReadonly(), "HU shall not be readonly");
		huToSplitKey = huToSplit;

		allowSameTUInfiniteCapacity = terminalContext.getProperty(ILTCUModel.PROPERTY_AllowSameTUInfiniteCapacity);
		Check.assumeNotNull(allowSameTUInfiniteCapacity, "isAllowSameTUInfiniteCapacity not null");

		defaultQtyHandler = new HUSplitDefaultQtyHandler(huToSplitKey, allowSameTUInfiniteCapacity);

		loadCUs();
	}

	/**
	 * Important: To be called after the model's panel is created, but before it's activated!
	 */
	public void init()
	{
		//
		// 08130: Always have one CU-key selected - in this case the first - before trying to calculate default Qtys
		// Bugfix is applied for when we have multiple VPIs available to be split
		final IKeyLayout cuKeyLayout = getCUKeyLayout();
		final ITerminalKey selectedCUKey = cuKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull();
		if (selectedCUKey == null)
		{
			//
			// Emulate pressing key (find / generate TU keys)
			final ITerminalKey cuKeyToSelect = cuKeyLayout.getKeys().iterator().next();
			cuKeyLayout.getKeyLayoutSelectionModel().onKeySelected(cuKeyToSelect);
			onCUPressed(cuKeyToSelect); // TODO listeners for onX events not working at this stage; maybe move autodetection at a later time

			//
			// Emulate pressing the first TU key so that we have one selected at any given time
			final ITerminalKey keyToSelectFallback = getTUKeyLayout().getKeys().iterator().next();
			getTUKeyLayout().getKeyLayoutSelectionModel().onKeySelected(keyToSelectFallback);
			onTUPressed(keyToSelectFallback); // TODO listeners for onX events not working at this stage; maybe move autodetection at a later time
		}

		//
		// 07558: Auto-detect & select qtys from given huToSplit
		defaultQtyHandler.calculateDefaultQtys(this);
	}

	private void updateQtysReadonly()
	{
		setQtyCUReadonly(getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull() == null);
		setQtyTUReadonly(getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull() == null);

		final LUSplitKey luKey = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKeyOrNull(LUSplitKey.class);
		setQtyLUReadonly(luKey == null || luKey.isNoPI());
	}

	private void loadCUs()
	{
		final List<ITerminalKey> keys = new ArrayList<>();

		final List<IHUProductStorage> productStorages = huToSplitKey.getProductStorages();
		if (productStorages.isEmpty())
		{
			//
			// This means that something is wrong with this line, and the Qty cannot be split
			// Otherwise, the user gets an empty Split window
			throw new MaterialMovementException(HUSplitModel.ERR_CU_NO_STORAGE_FOUND);
		}

		for (final IProductStorage productStorage : productStorages)
		{
			final I_M_Product product = productStorage.getM_Product();
			final BigDecimal qty = productStorage.getQty(); // qty is the full qty available in the source. how much we will really split is also restricted by the destination
			final I_C_UOM uom = productStorage.getC_UOM();
			final CUSplitKey key = new CUSplitKey(getTerminalContext(), product, qty, uom);
			keys.add(key);
		}

		getCUKeyLayout().setKeys(keys);

		updateQtysReadonly();
	}

	@Override
	protected final void onCUPressed(final ITerminalKey key)
	{
		final CUSplitKey cuKey = (CUSplitKey)key;

		//
		// Load TUs
		final List<ITerminalKey> tuKeys = createTUKeys(cuKey);
		getTUKeyLayout().setKeys(tuKeys);

		updateQtysReadonly();
	}

	private List<ITerminalKey> createTUKeys(final CUSplitKey cuKey)
	{
		final Properties ctx = getTerminalContext().getCtx();
		final I_M_Product cuProduct = cuKey.getM_Product();

		final I_M_HU huToSplit = huToSplitKey.getM_HU();
		final int originalPartnerId = huToSplit.getC_BPartner_ID();
		final I_C_BPartner originalPartner = originalPartnerId <= 0 ? null : InterfaceWrapperHelper.create(ctx, originalPartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);

		final List<I_M_HU_PI_Item_Product> availableHUPIItemProducts = itemProductDAO.retrieveTUs(ctx, cuProduct, originalPartner);

		//
		// Create TU Keys
		final List<ITerminalKey> keys = new ArrayList<>(availableHUPIItemProducts.size());
		for (final I_M_HU_PI_Item_Product tuPIItemProduct : availableHUPIItemProducts)
		{
			final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
			if (isSkipInfiniteCapacity(huToSplit, tuPI, tuPIItemProduct))
			{
				continue;
			}

			final BigDecimal capacity = tuPIItemProduct.getQty();

			//
			// Note that the key already has checks for infinite capacity
			final TUSplitKey key = new TUSplitKey(getTerminalContext(), tuPIItemProduct, tuPI, cuProduct, capacity, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			keys.add(key);
		}
		//
		// Add the virtual PI to TU Keys
		{
			final I_M_HU_PI_Item virtualPIItem = handlingUnitsDAO.retrieveVirtualPIItem(ctx);
			final I_M_HU_PI virtualPI = virtualPIItem.getM_HU_PI_Version().getM_HU_PI();
			final I_M_HU_PI_Item_Product virtualItemProduct = itemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
			final BigDecimal capacity = BigDecimal.valueOf(99999); // infinite

			final TUSplitKey key = new TUSplitKey(getTerminalContext(), virtualItemProduct, virtualPI, cuProduct, capacity, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			keys.add(key);
		}
		return keys;
	}

	private final boolean isSkipInfiniteCapacity(final I_M_HU huToSplit, final I_M_HU_PI tuPI, final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		//
		// If not infinite capacity, don't skip
		final boolean isTUInfiniteCapacity = tuPIItemProduct.isInfiniteCapacity();
		if (!isTUInfiniteCapacity)
		{
			return false;
		}

		//
		// Skip for loading unit TU PI only if none of the included ones match
		if (handlingUnitsBL.isLoadingUnit(huToSplit))
		{
			boolean skipLUInfiniteCapacity = true;
			final List<I_M_HU> includedTUs = handlingUnitsDAO.retrieveIncludedHUs(huToSplit);
			for (final I_M_HU includedTU : includedTUs)
			{
				final boolean skipTUInfiniteCapacity = isSkipInfiniteCapacity(includedTU, tuPI, tuPIItemProduct);
				if (!skipTUInfiniteCapacity)
				{
					skipLUInfiniteCapacity = false;
				}
			}
			return skipLUInfiniteCapacity;
		}

		//
		// Skip if we're not splitting a TU
		if (!handlingUnitsBL.isTransportUnit(huToSplit))
		{
			return true;
		}

		//
		// If the TU PI is exacly the same from our HU than accept it right away even if is infinite capacity or not.
		// We are doing this because else we won't have it in our TU lane and we will end-up in other errors.
		final I_M_HU_PI_Version huToSplitPIV = Services.get(IHandlingUnitsBL.class).getPIVersion(huToSplit);
		if (tuPI.getM_HU_PI_ID() == huToSplitPIV.getM_HU_PI_ID())
		{
			return false; // don't skip
		}

		//
		// Skip if configured to disallow
		if (!allowSameTUInfiniteCapacity)
		{
			return true;
		}

		return false;
	}

	@Override
	protected final void onTUPressed(final ITerminalKey key)
	{
		final TUSplitKey tuKey = (TUSplitKey)key;

		//
		// Load LUs
		final List<ITerminalKey> luKeys = createLUKeys(tuKey);
		getLUKeyLayout().setKeys(luKeys);

		if (!tuKey.isQtyCUsPerTUInfinite())
		{
			setQtyCU(tuKey.getQtyCUsPerTU());
		}

		defaultQtyHandler.calculateDefaultQtys(this);

		updateQtysReadonly();
	}

	private List<ITerminalKey> createLUKeys(final TUSplitKey tuKey)
	{
		final I_M_HU_PI tuPI = tuKey.getM_HU_PI();
		final Properties ctx = InterfaceWrapperHelper.getCtx(tuPI);
		final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;

		final I_C_BPartner bpartner = null; // TODO do we want to filter LUs by BP?
		final List<I_M_HU_PI_Item> luPIItems = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, huUnitType, bpartner);

		// FIXME: Load NO-HU-PI to handle when e.g IFCOs are not in a Palette
		// FIXME: Load Original LU if that's the case (???)
		final List<ITerminalKey> keys = new ArrayList<>(luPIItems.size());
		for (final I_M_HU_PI_Item luPIItem : luPIItems)
		{
			final I_M_HU_PI luPI = luPIItem.getM_HU_PI_Version().getM_HU_PI();

			final LUSplitKey key = new LUSplitKey(getTerminalContext(), luPIItem, luPI, huUnitType);
			keys.add(key);
		}

		//
		// Add the virtual PI
		{
			final I_M_HU_PI_Item noPIItem = handlingUnitsDAO.retrievePackingItemTemplatePIItem(ctx);
			final I_M_HU_PI virtualPI = noPIItem.getM_HU_PI_Version().getM_HU_PI();

			final LUSplitKey key = new LUSplitKey(getTerminalContext(), noPIItem, virtualPI, huUnitType);
			keys.add(key);
		}

		return keys;
	}

	@Override
	protected final void onLUPressed(final ITerminalKey key)
	{
		defaultQtyHandler.calculateDefaultQtys(this); // Task 07084

		updateQtysReadonly();
	}

	@Override
	public void execute()
	{
		final CUSplitKey cuKey = getCUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(CUSplitKey.class, AbstractLTCUModel.ERR_SELECT_CU_KEY);
		final TUSplitKey tuKey = getTUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(TUSplitKey.class, AbstractLTCUModel.ERR_SELECT_TU_KEY);
		final LUSplitKey luKey = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(LUSplitKey.class, AbstractLTCUModel.ERR_SELECT_LU_KEY);

		//
		// Validate CU-TU-LU quantities (Note: this area should never fail and it should be fixed from GUI)
		final BigDecimal qtyCU = getQtyCU();
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new MaterialMovementException(HUSplitModel.ERR_CU_QTY_NOT_ALLOWED);
		}
		final BigDecimal qtyTU = getQtyTU();
		if (qtyTU == null || qtyTU.signum() <= 0)
		{
			throw new MaterialMovementException(HUSplitModel.ERR_TU_QTY_NOT_ALLOWED);
		}
		final BigDecimal qtyLU = getQtyLU();
		if (qtyLU == null || qtyLU.signum() < 0)
		{
			throw new MaterialMovementException(HUSplitModel.ERR_LU_QTY_NOT_ALLOWED);
		}

		splitHUs(cuKey, tuKey, luKey);
	}

	private void splitHUs(final CUSplitKey cuKey, final TUSplitKey tuKey, final LUSplitKey luKey)
	{
		final IMutableHUContext huContextInitial = handlingUnitsBL.createMutableHUContextForProcessing(getTerminalContext());
		final List<I_M_HU> splitHUs = splitHUs0(huContextInitial, cuKey, tuKey, luKey);

		//
		// After split
		final IHUKey rootKey = huToSplitKey.getRoot();
		final IHUDocumentLine documentLine = huToSplitKey.findDocumentLineOrNull();
		final IHUKeyFactory keyFactory = rootKey.getKeyFactory();

		//
		// Reset Key Factories cache
		// NOTE: this affects cached attribute storages which were changed
		keyFactory.clearCache();

		//
		// Gets the most top level parent of the HUKey which we just split
		// and entirely rebuid all HUKey structure from that level down,
		// just to make sure the entire HU structure is up2date.
		ISplittableHUKey huToRebuild = huToSplitKey;
		IHUKey huToRebuildParent = huToRebuild.getParent();
		while (huToRebuildParent != null) // go all the way up to the top level HU
		{
			huToRebuildParent = huToRebuildParent.getParent();

			if (huToRebuildParent instanceof ISplittableHUKey)
			{
				huToRebuild = (ISplittableHUKey)huToRebuildParent;
			}
		}
		huToRebuild = rebuild(huToRebuild);

		//
		// Add all split nodes to Root Key
		final List<IHUKey> splitHUKeys = keyFactory.createKeys(splitHUs, documentLine);
		rootKey.addChildren(splitHUKeys);
	}

	/**
	 * Creates an new key for the underlying HU and adds it to key's parent.
	 * 
	 * The old <code>key</code> will be removed.
	 * 
	 * @param key
	 * @return rebuilt key or <code>null</code> if the underlying HU was destroyed in meantime
	 */
	private final ISplittableHUKey rebuild(final ISplittableHUKey key)
	{
		final IHUKey parentKey = key.getParent();
		if (parentKey == null)
		{
			// shall not happen, but just to make sure
			return null;
		}

		final I_M_HU hu = key.getM_HU();
		final IHUDocumentLine documentLine = key.findDocumentLineOrNull();
		final IHUKeyFactory keyFactory = key.getKeyFactory();

		key.removeFromParent();

		InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_None); // make sure we are out-of-trx
		if (handlingUnitsBL.isDestroyedRefreshFirst(hu))
		{
			return null;
		}

		final IHUKey keyNew = keyFactory.createKey(hu, documentLine);
		parentKey.addChild(keyNew);

		return (ISplittableHUKey)keyNew;
	}

	private List<I_M_HU> splitHUs0(final IMutableHUContext huContextInitial, final CUSplitKey cuKey, final TUSplitKey tuKey, final LUSplitKey luKey)
	{
		final IHUSplitBuilder splitBuilder = new HUSplitBuilder(huContextInitial);

		//
		// "Our" HU, the one which the user selected for split
		final I_M_HU huToSplit = huToSplitKey.getM_HU();
		splitBuilder.setHUToSplit(huToSplit);

		//
		// DocumentLine / Trx Referenced model (if available)
		final IHUDocumentLine documentLine = huToSplitKey.findDocumentLineOrNull();
		splitBuilder.setDocumentLine(documentLine);
		splitBuilder.setCUTrxReferencedModel(documentLine == null ? null : documentLine.getTrxReferencedModel());

		splitBuilder.setCUProduct(cuKey.getM_Product());
		splitBuilder.setCUQty(cuKey.getQty());
		splitBuilder.setCUUOM(cuKey.getC_UOM());

		splitBuilder.setCUPerTU(getQtyCU());
		splitBuilder.setTUPerLU(getQtyTU());
		splitBuilder.setMaxLUToAllocate(getQtyLU());

		splitBuilder.setTU_M_HU_PI_Item(tuKey.getM_HU_PI_Item_Product().getM_HU_PI_Item());
		splitBuilder.setLU_M_HU_PI_Item(luKey.getM_HU_PI_Item_ForChildJoin());

		splitBuilder.setSplitOnNoPI(luKey.isNoPI());

		return splitBuilder.split();
	}
}
