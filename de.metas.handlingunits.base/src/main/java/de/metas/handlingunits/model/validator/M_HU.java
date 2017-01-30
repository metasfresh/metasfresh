package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.storage.IStorageListeners;
import de.metas.storage.spi.hu.impl.StorageSegmentFromHU;

@Validator(I_M_HU.class)
public class M_HU
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Checks if HU is valid.
	 *
	 * @param hu
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Check: LUs shall always be Top-Level
		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("Loading units shall always be top level"
						+ "\n@M_HU_ID@: " + hu.getValue() + " (ID=" + hu.getM_HU_ID() + ")");
			}
		}

		// FIXME: DEBUG
		if (hu.getM_HU_ID() > 0)
		{
			final String trxName = InterfaceWrapperHelper.getTrxName(hu);
			if (trxName == null || trxName.startsWith("POSave"))
			{
				final HUException ex = new HUException("Changing HUs out of transaction is not allowed"
						+ "\n HU: " + hu
						+ "\n trxName: " + trxName);
				if (Services.get(IDeveloperModeBL.class).isEnabled())
				{
					throw ex;
				}
				else
				{
					logger.warn(ex.getLocalizedMessage() + " [ IGNORED ]", ex);
				}
			}
		}
	}

	/**
	 * Updates the status, locator BP and BPL for child handling units.
	 * 
	 * Note that this method only updates the direct children, but that will cause it to be called again and so on.
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = {
					I_M_HU.COLUMNNAME_HUStatus,
					I_M_HU.COLUMNNAME_IsActive,
					I_M_HU.COLUMNNAME_C_BPartner_ID,
					I_M_HU.COLUMNNAME_C_BPartner_Location_ID,
					I_M_HU.COLUMNNAME_M_Locator_ID
			})
	public void updateChildren(final I_M_HU hu)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Retrieve included HUs
		final List<I_M_HU> childHUs = handlingUnitsDAO.retrieveIncludedHUs(hu);
		if (childHUs.isEmpty())
		{
			// no children => nothing to do
			return;
		}

		//
		// Extract relevant fields from parent
		// NOTE: make sure these fields are in the list of columns changed of ModelChange annotation
		final String parentHUStatus = hu.getHUStatus();
		final boolean parentIsActive = hu.isActive();
		final int parentBPartnerId = hu.getC_BPartner_ID();
		final int parentBPLocationId = hu.getC_BPartner_Location_ID();
		final int parentLocatorId = hu.getM_Locator_ID();

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(hu);
		final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

		//
		// Iterate children and update relevant fields from parent
		for (final I_M_HU childHU : childHUs)
		{
			Services.get(IHandlingUnitsBL.class).setHUStatus(huContext, childHU, parentHUStatus);
			childHU.setIsActive(parentIsActive);
			childHU.setC_BPartner_ID(parentBPartnerId);
			childHU.setC_BPartner_Location_ID(parentBPLocationId);
			childHU.setM_Locator_ID(parentLocatorId);
			handlingUnitsDAO.saveHU(childHU);
		}
	}

	/**
	 * When a picked HU is destroyed, it has to be removed from the picking slot.
	 * 
	 * NOTE: We don't know if the old status of the HU is picked, but {@link}removeFromPickingSlotQueue
	 *
	 * @param hu
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = {
					I_M_HU.COLUMNNAME_HUStatus
			})
	public void onDestroyedHU(final I_M_HU hu)
	{
		//
		// Make sure our HU is top level
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final boolean isTopLevel = handlingUnitsBL.isTopLevel(hu);
		if (!isTopLevel)
		{
			// Only do this for the top level HUs
			return;
		}

		//
		// Make sure our HU was destroyed
		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Destroyed.equals(huStatus))
		{
			// Do nothing in case the HU was not destroyed
			return;
		}

		//
		// At this point, it means we have a top-level, destroyed HU. We need to take it out from the picking slots
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
		huPickingSlotBL.removeFromPickingSlotQueue(hu);
	}

	/**
	 * 
	 * @param vhu
	 * @task http://dewiki908/mediawiki/index.php/07827_new_Requirements_to_Lagerwertliste_11.11.2014_%28105088333438%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_M_HU.COLUMNNAME_M_Locator_ID,
					I_M_HU.COLUMNNAME_HUStatus
			})
	public void createHUTrxLinesOnHUChanges(final I_M_HU vhu)
	{
		//
		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Only create movement transactions on VHU level
		if (!handlingUnitsBL.isVirtual(vhu) || handlingUnitsBL.isAggregateHU(vhu))
		{
			return; // aggregate HUs are virtual too, but not "really" virtual..
		}

		//
		// Load old HU values so that we can identify the previous locator
		final I_M_HU vhuOld = InterfaceWrapperHelper.createOld(vhu, I_M_HU.class);

		boolean hasRelevantChanges = false; // true if there are any HU relevant changes and it makes sense to create -/+ M_HU_Trx_Lines

		//
		// Get HUStatus Old and New
		final String huStatusOld = vhuOld.getHUStatus();
		final String huStatusNew = vhu.getHUStatus();
		if (Check.isEmpty(huStatusNew) || Check.isEmpty(huStatusOld))
		{
			return;
		}
		final boolean huStatusChanged = !Objects.equals(huStatusOld, huStatusNew);
		if (huStatusChanged)
		{
			hasRelevantChanges = true;
		}

		//
		// Get Locator Old and New
		final I_M_Locator locatorOld = vhuOld.getM_Locator();
		final I_M_Locator locatorNew = vhu.getM_Locator();
		if (locatorNew == null || locatorOld == null)
		{
			return;
		}
		final boolean locatorChanged = locatorOld.getM_Locator_ID() != locatorNew.getM_Locator_ID();
		if (locatorChanged)
		{
			hasRelevantChanges = true;
		}

		//
		// Check if there are any relevant changes
		if (!hasRelevantChanges)
		{
			return;
		}

		//
		// Services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

		//
		// Consider VHU as the context and create and process transaction
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(vhu);
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(contextProvider);
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();

		//
		// Get VHU Storage
		final List<I_M_HU_Item> vhuItems = handlingUnitsDAO.retrieveItems(vhu);
		Check.errorUnless(vhuItems.size() == 1, "VHUs shall have exactly 1 material item, but vhu={} has {} item(s): {}", vhu, vhuItems.size(), vhuItems);
		final I_M_HU_Item vhuItem = vhuItems.iterator().next();

		final IHUStorage vhuStorage = huStorageFactory.getStorage(vhu);
		final List<IHUProductStorage> productStorages = vhuStorage.getProductStorages();
		if (productStorages.isEmpty())
		{
			return;
		}
		Check.assume(productStorages.size() == 1, "VHUs can have only one product storage");
		final IHUProductStorage productStorage = productStorages.iterator().next();

		//
		// Extract the data needed to create the HU Transactions
		final I_M_Product product = productStorage.getM_Product();
		final BigDecimal qty = productStorage.getQty();
		final I_C_UOM uom = productStorage.getC_UOM();
		final Quantity quantity = new Quantity(qty, uom);
		final Date date = huContext.getDate();
		final Object referencedModel = vhu; // there's no document model for our HUTransactions

		//
		// Create HU Transaction From: Old HUStatus, Old Locator, minus storage Qty
		final IHUTransaction huTransactionFrom = new HUTransaction(referencedModel,
				vhuItem, // huItem
				vhuItem, // vhuItem
				product,
				quantity.negate(),
				date,
				locatorOld,
				huStatusOld);
		huTransactionFrom.setSkipProcessing(); // i.e. don't change HU's storage

		//
		// Create HU Transaction To: New HUStatus, New Locator, plus storage Qty
		final IHUTransaction huTransactionTo = new HUTransaction(referencedModel,
				vhuItem, // huItem
				vhuItem, // vhuItem
				product,
				quantity,
				date,
				locatorNew,
				huStatusNew);
		huTransactionTo.setSkipProcessing(); // i.e. don't change HU's storage

		huTransactionTo.pair(huTransactionFrom);

		//
		// Create allocation result with no quantity (the quantity will be set from the IHUTransactions). We need the allocation result to create the transaction headers.
		final IMutableAllocationResult allocationResult = AllocationUtils.createMutableAllocationResult(BigDecimal.ZERO);
		allocationResult.addTransaction(huTransactionFrom);
		allocationResult.addTransaction(huTransactionTo);
		// Create the actual M_HU_Trx_Lines
		huTrxBL.createTrx(huContext, allocationResult);

		// FIXME - extract this to a trx collector in ThreadLocal
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE },
			ifColumnsChanged = {
					I_M_HU.COLUMNNAME_HUStatus,
					I_M_HU.COLUMNNAME_IsActive,
					I_M_HU.COLUMNNAME_C_BPartner_ID,
					I_M_HU.COLUMNNAME_M_Locator_ID
			})
	public void fireStorageSegmentChanged(final I_M_HU hu)
	{
		// Consider only VHUs
		if (!Services.get(IHandlingUnitsBL.class).isVirtual(hu))
		{
			return;
		}

		final StorageSegmentFromHU storageSegment = new StorageSegmentFromHU(hu);
		Services.get(IStorageListeners.class).notifyStorageSegmentChanged(storageSegment);
	}
}
