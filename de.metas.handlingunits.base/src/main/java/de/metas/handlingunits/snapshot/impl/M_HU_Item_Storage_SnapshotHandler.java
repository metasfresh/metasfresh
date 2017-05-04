package de.metas.handlingunits.snapshot.impl;

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
import java.util.Map;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Item_Storage_Snapshot;

class M_HU_Item_Storage_SnapshotHandler extends AbstractSnapshotHandler<I_M_HU_Item_Storage, I_M_HU_Item_Storage_Snapshot, I_M_HU_Item>
{
	// services
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	M_HU_Item_Storage_SnapshotHandler(final AbstractSnapshotHandler<?, ?, ?> parentHandler)
	{
		super(parentHandler);
	}

	@Override
	protected void createSnapshotsByParentIds(final Set<Integer> huItemIds)
	{
		query(I_M_HU_Item_Storage.class)
				.addInArrayOrAllFilter(I_M_HU_Item_Storage.COLUMN_M_HU_Item_ID, huItemIds)
				.create()
				.insertDirectlyInto(I_M_HU_Item_Storage_Snapshot.class)
				.mapCommonColumns()
				.mapColumnToConstant(I_M_HU_Item_Storage_Snapshot.COLUMNNAME_Snapshot_UUID, getSnapshotId())
				.execute();
	}

	@Override
	protected Map<Integer, I_M_HU_Item_Storage_Snapshot> retrieveModelSnapshotsByParent(final I_M_HU_Item huItem)
	{
		return query(I_M_HU_Item_Storage_Snapshot.class)
				.addEqualsFilter(I_M_HU_Item_Storage_Snapshot.COLUMN_M_HU_Item_ID, huItem.getM_HU_Item_ID())
				.addEqualsFilter(I_M_HU_Item_Storage_Snapshot.COLUMN_Snapshot_UUID, getSnapshotId())
				.create()
				.map(I_M_HU_Item_Storage_Snapshot.class, snapshot2ModelIdFunction);
	}

	@Override
	protected Map<Integer, I_M_HU_Item_Storage> retrieveModelsByParent(final I_M_HU_Item huItem)
	{
		return query(I_M_HU_Item_Storage.class)
				.addEqualsFilter(I_M_HU_Item_Storage.COLUMN_M_HU_Item_ID, huItem.getM_HU_Item_ID())
				.create()
				.mapById(I_M_HU_Item_Storage.class);
	}

	@Override
	protected I_M_HU_Item_Storage_Snapshot retrieveModelSnapshot(final I_M_HU_Item_Storage model)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void restoreModelWhenSnapshotIsMissing(final I_M_HU_Item_Storage model)
	{
		model.setQty(BigDecimal.ZERO);
	}

	@Override
	protected void saveRestoredModel(final I_M_HU_Item_Storage model, final I_M_HU_Item_Storage_Snapshot modelSnapshot)
	{
		//
		// Log quantity changed transactions
		final I_M_HU_Item huItem = model.getM_HU_Item();
		if (Services.get(IHandlingUnitsBL.class).isVirtual(huItem))
		{
			createQtyChangeRestoreTransactions(model);
		}

		super.saveRestoredModel(model, modelSnapshot);
	}

	private void createQtyChangeRestoreTransactions(final I_M_HU_Item_Storage model)
	{
		final I_M_HU_Item_Storage modelOld = InterfaceWrapperHelper.createOld(model, I_M_HU_Item_Storage.class);

		//
		// Make sure we did not changed values which we are not supporting to be changed
		Check.assume(modelOld.getM_Product_ID() == model.getM_Product_ID(), "Product has not changed between {} and {}", model, modelOld);
		Check.assume(modelOld.getC_UOM_ID() == model.getC_UOM_ID(), "UOM has not changed between {} and {}", model, modelOld);
		Check.assume(modelOld.getM_HU_Item_ID() == model.getM_HU_Item_ID(), "M_HU_Item_ID has not changed between {} and {}", model, modelOld);

		final I_M_HU_Item vhuItem = model.getM_HU_Item();
		final I_M_HU vhu = vhuItem.getM_HU();
		final I_M_Locator locator = vhu.getM_Locator();
		final String huStatus = vhu.getHUStatus();

		final BigDecimal qtyOld = modelOld.getQty();
		final BigDecimal qtyNew = model.getQty();
		final BigDecimal qtyDiff = qtyNew.subtract(qtyOld);

		//
		// Extract the data needed to create the HU Transactions
		final I_M_Product product = model.getM_Product();
		final I_C_UOM uom = model.getC_UOM();
		final Quantity quantity = new Quantity(qtyDiff, uom);
		final Date date = getDateTrx();
		final Object referencedModel = getReferencedModel();

		//
		// Create HU Transaction From
		final IHUTransaction huTransactionFrom = new HUTransaction(referencedModel,
				null, // huItem
				null, // vhuItem
				product,
				quantity.negate(),
				date,
				locator,
				huStatus);
		huTransactionFrom.setSkipProcessing(); // i.e. don't change HU's storage

		//
		// Create HU Transaction To: New HUStatus, New Locator, plus storage Qty
		final IHUTransaction huTransactionTo = new HUTransaction(referencedModel,
				vhuItem, // huItem
				vhuItem, // vhuItem
				product,
				quantity,
				date,
				locator,
				huStatus);
		huTransactionTo.setSkipProcessing(); // i.e. don't change HU's storage

		huTransactionFrom.pair(huTransactionTo);

		//
		// Create allocation result with no quantity (the quantity will be set from the IHUTransactions). We need the allocation result to create the transaction headers.
		final IMutableAllocationResult allocationResult = AllocationUtils.createMutableAllocationResult(BigDecimal.ZERO);
		allocationResult.addTransaction(huTransactionFrom);
		allocationResult.addTransaction(huTransactionTo);

		//
		// Create the actual M_HU_Trx_Lines
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(getContext());
		huTrxBL.createTrx(huContext, allocationResult);
	}

	@Override
	protected int getModelId(final I_M_HU_Item_Storage_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Item_Storage_ID();
	}
	
	@Override
	protected I_M_HU_Item_Storage getModel(I_M_HU_Item_Storage_Snapshot modelSnapshot)
	{
		return modelSnapshot.getM_HU_Item_Storage();
	}
}
