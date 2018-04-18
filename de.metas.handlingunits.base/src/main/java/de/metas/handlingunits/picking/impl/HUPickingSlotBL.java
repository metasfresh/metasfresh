package de.metas.handlingunits.picking.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TrxRunnable;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_PickingSlot_Trx;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_PickingSlot_Trx;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.impl.HUPickingSlotBLs.RetrieveAvailableHUsToPick;
import de.metas.handlingunits.picking.impl.HUPickingSlotBLs.RetrieveAvailableHUsToPickFilters;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.picking.api.impl.PickingSlotBL;
import lombok.NonNull;

public class HUPickingSlotBL
		extends PickingSlotBL
		implements IHUPickingSlotBL
{

	public static final class QueueActionResult implements IHUPickingSlotBL.IQueueActionResult
	{
		private final I_M_PickingSlot_Trx pickingSlotTrx;
		private final I_M_PickingSlot_HU pickingSlotHU;

		private QueueActionResult(final I_M_PickingSlot_Trx pickingSlotTrx, final I_M_PickingSlot_HU pickingSlotHU)
		{
			this.pickingSlotTrx = pickingSlotTrx;
			this.pickingSlotHU = pickingSlotHU;
		}

		@Override
		public I_M_PickingSlot_Trx getM_PickingSlot_Trx()
		{
			return pickingSlotTrx;
		}

		@Override
		public I_M_PickingSlot_HU getI_M_PickingSlot_HU()
		{
			return pickingSlotHU;
		}

		@Override
		public String toString()
		{
			return "QueueActionResult [pickingSlotTrx=" + pickingSlotTrx + ", pickingSlotHU=" + pickingSlotHU + "]";
		}
	}

	@Override
	public IQueueActionResult createCurrentHU(final I_M_PickingSlot pickingSlot, final I_M_HU_PI_Item_Product itemProduct)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		
		//
		// Check: there is no current HU in this picking slot
		if (pickingSlot.getM_HU_ID() > 0)
		{
			// We already have an HU in this slot => ERROR
			final I_M_HU currentHU = pickingSlot.getM_HU();
			final String currentHUStr = currentHU == null ? "-"
					: currentHU.getValue() + " - " + handlingUnitsBL.getPIVersion(currentHU).getName();
			throw new AdempiereException("@HandlingUnitAlreadyOpen@: " + currentHUStr);
		}

		//
		// Create HU Context
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(pickingSlot);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		//
		// Create the new HU
		final I_M_HU[] huArr = new I_M_HU[] { null };
		Services.get(IHUTrxBL.class)
				.createHUContextProcessorExecutor(huContext)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						//
						// Create a new HU instance
						final IHUBuilder huBuilder = Services.get(IHandlingUnitsDAO.class).createHUBuilder(huContext);
						huBuilder.setM_HU_Item_Parent(null); // no parent
						huBuilder.setM_HU_PI_Item_Product(itemProduct);

						final I_M_Warehouse warehouse = pickingSlot.getM_Warehouse();
						huBuilder.setM_Locator(getCurrentLocator(warehouse));

						// We are creating the new HUs on picking slot as picked, to avoid some allocation business logic to consider them
						huBuilder.setHUStatus(X_M_HU.HUSTATUS_Picked);

						final I_M_HU_PI huPI = itemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
						huArr[0] = huBuilder.create(huPI);
						return NULL_RESULT;
					}
				});
		final I_M_HU hu = huArr[0];

		//
		// Set current picking slot's HU
		pickingSlot.setM_HU(hu);
		final I_M_PickingSlot_Trx pickingSlotTrx = createPickingSlotTrx(pickingSlot, hu, X_M_PickingSlot_Trx.ACTION_Set_Current_HU);
		InterfaceWrapperHelper.save(pickingSlot);

		return new QueueActionResult(pickingSlotTrx, null);
	}

	private I_M_Locator getCurrentLocator(final I_M_Warehouse warehouse)
	{
		return Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
	}

	@Override
	public IQueueActionResult closeCurrentHU(final I_M_PickingSlot pickingSlot)
	{
		final boolean addToQueue = true;
		final I_M_HU closedHU = closeCurrentHU(pickingSlot, addToQueue);
		if (closedHU == null)
		{
			return new QueueActionResult(null, null); // there was nothing to be done
		}
		return new QueueActionResult(createPickingSlotTrx(pickingSlot, closedHU, X_M_PickingSlot_Trx.ACTION_Close_Current_HU), null);
	}

	/**
	 *
	 * @param pickingSlot
	 * @param addToQueue if <code>true</code>, the current HU is not only closed, but also added to the picking slot queue.
	 * @return the HU which was unassigned from the given pickingSlot, or <code>null</code>
	 */
	private I_M_HU closeCurrentHU(final I_M_PickingSlot pickingSlot, final boolean addToQueue)
	{
		if (pickingSlot.getM_HU_ID() <= 0)
		{
			// no current/opened HU => nothing to do
			return null;
		}

		final IMutable<I_M_HU> unassignedHURef = new Mutable<>();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(pickingSlot);
		// trxManager.assertTrxNull(context);
		trxManager.run(context.getTrxName(), new TrxRunnable()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				final I_M_PickingSlot pickingSlotInTrx = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);
				final I_M_HU currentHU = pickingSlot.getM_HU();
				unassignedHURef.setValue(currentHU);

				pickingSlotInTrx.setM_HU(null);
				InterfaceWrapperHelper.save(pickingSlotInTrx);

				final boolean destroyed = Services.get(IHandlingUnitsBL.class).destroyIfEmptyStorage(currentHU);

				if (!destroyed)
				{
					//
					// Add current HU to picking slot queue
					if (addToQueue)
					{
						addToPickingSlotQueue(pickingSlotInTrx, currentHU);
					}
				}
				InterfaceWrapperHelper.save(pickingSlotInTrx);
			}
		});

		// mark the picking slot as staled (and needs to be reloaded in case of usage) because we modified it in transaction
		InterfaceWrapperHelper.markStaled(pickingSlot);

		//
		// Get the unassigned HU.
		// Reset it's trxName.
		final I_M_HU unassignedHU = unassignedHURef.getValue();
		if (unassignedHU == null)
		{
			return null;
		}
		InterfaceWrapperHelper.setTrxName(unassignedHU, ITrx.TRXNAME_None);
		return unassignedHU;
	}

	public boolean isCurrentHU(final I_M_PickingSlot pickingSlot, final I_M_HU hu)
	{
		Check.assumeNotNull(pickingSlot, "pickingSlot not null");
		Check.assumeNotNull(hu, "hu not null");

		if (hu.getM_HU_ID() <= 0)
		{
			return false;
		}

		return pickingSlot.getM_HU_ID() == hu.getM_HU_ID();
	}

	@Override
	public IQueueActionResult addToPickingSlotQueue(final de.metas.picking.model.I_M_PickingSlot pickingSlot, final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final List<IQueueActionResult> results = addToPickingSlotQueue(pickingSlot, Collections.singletonList(hu));
		Check.assumeNotEmpty(results, "results not empty"); // shall not happen
		Check.assume(results.size() == 1, "only one result was expected but we got {}", results); // shall not happen
		return results.get(0);
	}

	@Override
	public List<IQueueActionResult> addToPickingSlotQueue(
			@NonNull final de.metas.picking.model.I_M_PickingSlot pickingSlot,
			@NonNull final List<I_M_HU> hus)
	{
		if (Check.isEmpty(hus))
		{
			return Collections.emptyList();
		}

		// services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		//
		// Context
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(pickingSlot);
		final IHUContext huContextInitial = handlingUnitsBL.createMutableHUContextForProcessing(contextProvider);

		//
		// Execute everything in a HU Context Processor,
		// to make sure everything is logged and updated correctly.
		final List<IQueueActionResult> queueActionResults = new ArrayList<>(hus.size());
		huTrxBL.createHUContextProcessorExecutor(huContextInitial)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						for (final I_M_HU hu : hus)
						{
							final IQueueActionResult result = addToPickingSlotQueue0(huContext, pickingSlot, hu);
							queueActionResults.add(result);
						}
						return NULL_RESULT;
					}
				});

		return queueActionResults;
	}

	private final IQueueActionResult addToPickingSlotQueue0(
			@NonNull final IHUContext huContext,
			@NonNull final de.metas.picking.model.I_M_PickingSlot pickingSlot,
			@NonNull final I_M_HU hu)
	{
		// services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		//
		// create the actual picking-slot-queue record
		I_M_PickingSlot_HU pickingSlotHU = huPickingSlotDAO.retrievePickingSlotHU(pickingSlot, hu);
		if (pickingSlotHU == null)
		{
			pickingSlotHU = InterfaceWrapperHelper.newInstance(I_M_PickingSlot_HU.class, huContext);
			pickingSlotHU.setM_PickingSlot_ID(pickingSlot.getM_PickingSlot_ID());
			pickingSlotHU.setM_HU(hu);
		}

		pickingSlotHU.setAD_Org_ID(pickingSlot.getAD_Org_ID());
		pickingSlotHU.setIsActive(true);

		save(pickingSlotHU, ITrx.TRXNAME_ThreadInherited);

		//
		// Make sure HU has the picking slot's BPartner
		if (pickingSlot.getC_BPartner_ID() > 0)
		{
			hu.setC_BPartner_ID(pickingSlot.getC_BPartner_ID());
			hu.setC_BPartner_Location_ID(pickingSlot.getC_BPartner_Location_ID());
		}

		//
		// Change HU status to Picked
		// (the HU will be saved a couple of lines below)
		handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Picked);

		// Take it out from it's parent, if any
		huTrxBL.setParentHU(huContext,
				null, // parentHUItem
				hu,
				true // destroyOldParentIfEmptyStorage
		);

		// If we have an after picking locator, set that to the HU (06902)
		final I_M_PickingSlot pickingSlotEx = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);
		if (pickingSlotEx.getM_Locator_ID() > 0)
		{
			hu.setM_Locator_ID(pickingSlotEx.getM_Locator_ID()); // will be propagated down by the M_HU model interceptor
		}

		InterfaceWrapperHelper.setThreadInheritedTrxName(hu);
		//
		// Save HU
		handlingUnitsDAO.saveHU(hu);

		//
		// Create the picking slot transaction and return it.
		final I_M_PickingSlot_Trx pickingSlotTrx = createPickingSlotTrx(pickingSlot, hu, X_M_PickingSlot_Trx.ACTION_Add_HU_To_Queue);
		return new QueueActionResult(pickingSlotTrx, pickingSlotHU);
	}

	@Override
	public IQueueActionResult removeFromPickingSlotQueue(final de.metas.picking.model.I_M_PickingSlot pickingSlot, final I_M_HU hu)
	{
		Check.assumeNotNull(pickingSlot, "pickingSlot not null");
		Check.assumeNotNull(hu, "hu not null");

		final I_M_PickingSlot pickingSlotExt = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);

		//
		// Check if HU is the current active from picking slot
		if (isCurrentHU(pickingSlotExt, hu))
		{
			// NOTE: don't add to picking slot queue because we would delete it from queue anyway (check below)
			final boolean addToQueue = false;
			closeCurrentHU(pickingSlotExt, addToQueue);
		}

		final IHUPickingSlotDAO pickingSlotDAO = Services.get(IHUPickingSlotDAO.class);

		//
		// Check if our handling unit is in a picking slot queue
		final I_M_PickingSlot_HU pickingSlotHU = pickingSlotDAO.retrievePickingSlotHU(pickingSlotExt, hu);
		if (pickingSlotHU != null)
		{
			InterfaceWrapperHelper.delete(pickingSlotHU);
		}

		//
		// 06178 release picking slot when removing PickingSlot-HU association as long,
		// BUT ONLY as long as the picking slot is not in use on the other end
		if (pickingSlotExt.getM_HU() == null)
		{
			releasePickingSlotIfPossible(pickingSlotExt);
		}

		final I_M_PickingSlot_Trx createPickingSlotTrx = createPickingSlotTrx(pickingSlot, hu, X_M_PickingSlot_Trx.ACTION_Remove_HU_From_Queue);
		return new QueueActionResult(createPickingSlotTrx, pickingSlotHU);
	}

	@Override
	public void removeFromPickingSlotQueue(final I_M_HU hu)
	{
		final IHUPickingSlotDAO pickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
		final I_M_PickingSlot pickingSlot = pickingSlotDAO.retrievePickingSlotForHU(hu);
		if (pickingSlot == null)
		{
			return;
		}

		removeFromPickingSlotQueue(pickingSlot, hu);
	}

	@Override
	public void removeFromPickingSlotQueueRecursivelly(final I_M_HU hu)
	{
		// services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Downstream iterate given HU until TU level.
		// Try to remove from picking slot each LU/TU found along the road.
		final HUIterator huIterator = new HUIterator();
		huIterator.setEnableStorageIteration(false); // we don't care about storages
		huIterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				// If this HU is an TU don't go below that because there is no point
				if (handlingUnitsBL.isTransportUnit(hu.getValue()))
				{
					return Result.SKIP_DOWNSTREAM;
				}

				return Result.CONTINUE;
			}

			@Override
			public Result afterHU(final I_M_HU hu)
			{
				removeFromPickingSlotQueue(hu);
				return Result.CONTINUE;
			}
		});
		huIterator.iterate(hu);
	}

	/**
	 * Creates M_PickingSlot_Trx for the given picking slot, hu and the hu's action.<br>
	 * The actions are: create current hu, add to picking slots queue, close current hu and remove from picking slots queue
	 *
	 * <br>
	 * TODO: this method should be public and the methods of this BL which call it should be private instead,<br>
	 * so that all sorts of actions shall be done by creating a picking slot transaction and then processing it
	 *
	 * @param pickingSlot
	 * @param hu
	 * @param action
	 * @return
	 */
	private I_M_PickingSlot_Trx createPickingSlotTrx(final de.metas.picking.model.I_M_PickingSlot pickingSlot, final I_M_HU hu, final String action)
	{
		final I_M_PickingSlot ps = InterfaceWrapperHelper.create(pickingSlot, I_M_PickingSlot.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ps);
		final String trxName = InterfaceWrapperHelper.getTrxName(ps);
		final I_M_PickingSlot_Trx pickingSlotTrx = InterfaceWrapperHelper.create(ctx, I_M_PickingSlot_Trx.class, trxName);

		pickingSlotTrx.setAD_Org_ID(ps.getAD_Org_ID());
		pickingSlotTrx.setM_HU_ID(hu.getM_HU_ID());
		pickingSlotTrx.setM_PickingSlot_ID(ps.getM_PickingSlot_ID());
		pickingSlotTrx.setAction(action);
		pickingSlotTrx.setProcessed(true);

		InterfaceWrapperHelper.setThreadInheritedTrxName(pickingSlotTrx);

		InterfaceWrapperHelper.save(pickingSlotTrx);

		return pickingSlotTrx;
	}

	@Override
	public boolean isAvailableForProduct(final I_M_PickingSlot pickingSlot, final I_M_HU_PI_Item_Product itemProduct)
	{
		Check.assumeNotNull(pickingSlot, "pickingSlot not null");

		final int bpartnerId = itemProduct.getC_BPartner_ID();

		//
		// NOTE: itemProduct does not have BP Location so we cannot validate for Location
		if (!Services.get(IHUPickingSlotBL.class).isAvailableForBPartnerID(pickingSlot, bpartnerId))
		{
			return false;
		}

		return true;
	}

	@Override
	public void allocatePickingSlotIfPossible(final I_M_PickingSlot pickingSlot, final int bpartnerId, final int bpartnerLocationId)
	{
		//
		// Not dynamic picking slot; gtfo
		if (!pickingSlot.isDynamic())
		{
			return;
		}

		//
		// Already allocated for a different partner?
		if (!isAvailableForAnyBPartner(pickingSlot))
		{
			return;
		}

		pickingSlot.setC_BPartner_ID(bpartnerId);
		pickingSlot.setC_BPartner_Location_ID(bpartnerLocationId);

		InterfaceWrapperHelper.save(pickingSlot);
	}

	@Override
	public void allocatePickingSlotIfPossible(final int pickingSlotId, final int bpartnerId, final int bpartnerLocationId)
	{
		final I_M_PickingSlot pickingSlot = load(pickingSlotId, I_M_PickingSlot.class);
		allocatePickingSlotIfPossible(pickingSlot, bpartnerId, bpartnerLocationId);
	}

	@Override
	public void releasePickingSlotIfPossible(final I_M_PickingSlot pickingSlot)
	{
		//
		// Not dynamic picking slot; gtfo
		if (!pickingSlot.isDynamic())
		{
			return;
		}

		//
		// Not allocated at all?
		if (isAvailableForAnyBPartner(pickingSlot))
		{
			return;
		}

		//
		// There still are PickingSlot-HU assignments; do nothing
		if (!Services.get(IHUPickingSlotDAO.class).isEmpty(pickingSlot))
		{
			return;
		}

		//
		// There still not closed picking candidates; do nothing
		final PickingCandidateRepository pickingCandidatesRepo = Adempiere.getBean(PickingCandidateRepository.class);
		if (pickingCandidatesRepo.hasNotClosedCandidatesForPickingSlot(pickingSlot.getM_PickingSlot_ID()))
		{
			return;
		}

		pickingSlot.setC_BPartner(null);
		pickingSlot.setC_BPartner_Location(null);
		InterfaceWrapperHelper.save(pickingSlot);
	}
	
	@Override
	public void releasePickingSlotIfPossible(final int pickingSlotId)
	{
		final I_M_PickingSlot pickingSlot = load(pickingSlotId, I_M_PickingSlot.class);
		releasePickingSlotIfPossible(pickingSlot);
	}

	@Override
	public List<I_M_HU> retrieveAvailableSourceHUs(@NonNull final PickingHUsQuery query)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();

		final Function<List<I_M_HU>, List<I_M_HU>> vhuToEndResultFunction = //
				vhus -> sourceHuService.retrieveParentHusThatAreSourceHUs(vhus);

		return RetrieveAvailableHUsToPick.retrieveAvailableHUsToPick(query, vhuToEndResultFunction);
	}

	@Override
	public List<I_M_HU> retrieveAvailableHUsToPick(@NonNull final PickingHUsQuery query)
	{
		final Function<List<I_M_HU>, List<I_M_HU>> vhuToEndResultFunction = vhus -> RetrieveAvailableHUsToPickFilters.retrieveFullTreeAndExcludePickingHUs(vhus);

		return RetrieveAvailableHUsToPick.retrieveAvailableHUsToPick(query, vhuToEndResultFunction);
	}

	@Override
	public List<Integer> retrieveAvailableHUIdsToPick(@NonNull final PickingHUsQuery request)
	{
		return retrieveAvailableHUsToPick(request)
				.stream()
				.map(I_M_HU::getM_HU_ID)
				.collect(ImmutableList.toImmutableList());
	}

}
