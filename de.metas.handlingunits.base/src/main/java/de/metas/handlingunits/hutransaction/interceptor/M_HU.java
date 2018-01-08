package de.metas.handlingunits.hutransaction.interceptor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.quantity.Quantity;

@Interceptor(I_M_HU.class)
public final class M_HU
{

	public static final M_HU INSTANCE = new M_HU();
	
	private M_HU()
	{
	};

	/**
	 * Creates hu trx lines if the HU's
	 * <ul>
	 * <li>M_Locator_ID
	 * <li>HUStatus
	 * </ul>
	 * changes
	 * 
	 * @param vhu
	 * @task http://dewiki908/mediawiki/index.php/07827_new_Requirements_to_Lagerwertliste_11.11.2014_%28105088333438%29
	 */
	@ModelChange(timings =
		{ ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged =
		{
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
		if (!handlingUnitsBL.isVirtual(vhu))
		{
			return;
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
		// check if the locator ID changed
		if (vhuOld.getM_Locator_ID() != vhu.getM_Locator_ID())
		{
			hasRelevantChanges = true;
		}

		//
		// Check if there are any relevant changes
		if (!hasRelevantChanges)
		{
			return;
		}


		// Get Locator Old and New
		final I_M_Locator locatorOld = vhuOld.getM_Locator();
		final I_M_Locator locatorNew = vhu.getM_Locator();

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
		final List<I_M_HU_Item> vhuItems = handlingUnitsDAO.retrieveItems(vhu).stream().filter(item -> X_M_HU_Item.ITEMTYPE_Material.equals(handlingUnitsBL.getItemType(item))).collect(Collectors.toList());
		Check.errorUnless(vhuItems.size() == 1, "VHUs shall have exactly 1 material item, but vhu={} has {} item(s): {}", vhu, vhuItems.size(), vhuItems);
		final I_M_HU_Item vhuItem = vhuItems.get(0);
		
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
		final IHUTransactionCandidate huTransactionFrom = new HUTransactionCandidate(referencedModel,
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
		final IHUTransactionCandidate huTransactionTo = new HUTransactionCandidate(referencedModel,
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
}
