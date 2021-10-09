package de.metas.handlingunits.receiptschedule.impl;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AbstractAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Helper class used to iterate TUs assigned to a receipt schedule and adjust their product storage with their WeightNet attribute.
 *
 * The difference between HU's Product Storage and HU's WeightNet attribute will be added/removed from receipt schedule.
 *
 * @author tsa
 *
 */
public class HUReceiptScheduleWeightNetAdjuster
{
	//
	// Services
	private static final transient Logger logger = LogManager.getLogger(HUReceiptScheduleWeightNetAdjuster.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IHUContext _huContextInitial;
	private Set<HuId> inScopeHU_IDs = null;

	public HUReceiptScheduleWeightNetAdjuster(final Properties ctx, final String trxName)
	{
		_huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, trxName);
	}

	@VisibleForTesting
	/* package */ HUReceiptScheduleWeightNetAdjuster(@NonNull final IHUContext huContext)
	{
		_huContextInitial = huContext.copyAsMutable();
	}

	private IHUContext createHUContext(final String trxName)
	{
		final IMutableHUContext huContext = _huContextInitial.copyAsMutable();
		huContext.setTrxName(trxName);

		// Advice the HU transaction listeners that we adjusting the HU Storages based on WeightNet attribute
		// We expect those transaction listeners or any other code to NOT increase those Weight attributes because some storage Qty was added/subtract.
		// See 08728.
		huContext.setProperty(IHUContext.PROPERTY_IsStorageAdjustmentFromWeightAttribute, true);
		return huContext;
	}

	private String getInitialTrxName()
	{
		return _huContextInitial.getTrxName();
	}

	public void setInScopeHU_IDs(final Set<HuId> inScopeHU_IDs)
	{
		this.inScopeHU_IDs = inScopeHU_IDs;
	}

	public void addReceiptSchedule(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assume(!receiptSchedule.isProcessed(), "receiptSchedule not processed: {}", receiptSchedule);
		logger.debug("Adding {}", receiptSchedule);

		// Skip receipt schedules which are not in a Weight UOM
		final UomId uomId = UomId.ofRepoIdOrNull(receiptSchedule.getC_UOM_ID());
		final UOMType uomType = uomId != null ? uomDAO.getUOMTypeById(uomId) : UOMType.Other;
		if (!uomType.isWeight())
		{
			logger.debug("Skip receiptSchedule because its UOM is not weightable; receiptSchedule={}", receiptSchedule);
			return;
		}

		//
		// Get the VHUs assigned to this receipt schedule
		final Collection<I_M_HU> vhus = retrieveVHUs(receiptSchedule);
		if (vhus.isEmpty())
		{
			// NOTE: we are logging this with WARNING because in most of the cases this is an issue
			logger.warn("No VHUs found for {}", receiptSchedule);
		}

		//
		// Iterate the VHUs and adjust their HU Storage to be the same as their WeightNet attribute.
		for (final I_M_HU vhu : vhus)
		{
			adjustHUStorageToWeightNet(vhu, receiptSchedule);
		}

		logger.debug("Done adjusting the receipt schedule: {}", receiptSchedule);
	}

	private Collection<I_M_HU> retrieveVHUs(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Build up a map of VHU's M_HU_ID to "SUM of QtyAllocated" (if not ZERO)
		final Map<Integer, BigDecimal> huId2qtyAllocatedMap = new HashMap<>();
		final Map<Integer, I_M_HU> huId2hu = new HashMap<>();
		final String trxName = getInitialTrxName();

		final List<I_M_ReceiptSchedule_Alloc> allocsAll = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, trxName);
		logger.debug("Found {} receipt schedule allocations", allocsAll.size());

		for (final I_M_ReceiptSchedule_Alloc rsa : allocsAll)
		{
			if (!isEligible(rsa))
			{
				logger.debug("Skip allocation because it's not eligible: {}", rsa);
				continue;
			}

			final I_M_HU vhu = rsa.getVHU();
			Check.assumeNotNull(vhu, "vhu may not be not null for rsa={}",rsa); // shall not be null at this point

			final int vhuId = vhu.getM_HU_ID();
			final BigDecimal rsaQtyAllocated = rsa.getHU_QtyAllocated();
			final BigDecimal huQtyAllocatedBefore = huId2qtyAllocatedMap.get(vhuId);
			final BigDecimal huQtyAllocated;
			if (huQtyAllocatedBefore == null)
			{
				huQtyAllocated = rsaQtyAllocated;
			}
			else
			{
				huQtyAllocated = huQtyAllocatedBefore.add(rsaQtyAllocated);
			}

			if (huQtyAllocated.signum() == 0)
			{
				// If Qty Allocated is zero, we consider that this TU is not assigned
				huId2qtyAllocatedMap.remove(vhuId);
				huId2hu.remove(vhuId);
			}
			else
			{
				huId2qtyAllocatedMap.put(vhuId, huQtyAllocated);
				huId2hu.put(vhuId, vhu);
			}
		}

		logger.debug("Collected VHUs to be adjusted: {}", huId2hu);
		return huId2hu.values();
	}

	private void adjustHUStorageToWeightNet(final I_M_HU vhu, final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Run in a sub-transaction of the original HUContext if possible
		final String initialTrxName = getInitialTrxName();
		trxManager.run(initialTrxName, (TrxRunnable)localTrxName -> adjustHUStorageToWeightNet0(vhu, receiptSchedule, localTrxName));
	}

	private void adjustHUStorageToWeightNet0(final I_M_HU vhu, final I_M_ReceiptSchedule receiptSchedule, final String trxName)
	{
		Check.assume(handlingUnitsBL.isVirtual(vhu), "{} is VHU", vhu);

		final IHUContext huContext = createHUContext(trxName);

		logger.debug("HUContext: {}", huContext);
		logger.debug("VHU: {}", vhu);
		logger.debug("Receipt schedule: {}", receiptSchedule);

		final IAttributeStorage vhuAttributeStorage = huContext.getHUAttributeStorageFactory()
				.getAttributeStorage(vhu);
		final IWeightable weightable = Weightables.wrap(vhuAttributeStorage);
		if (!weightable.hasWeightNet())
		{
			logger.debug("Skip weight adjusting because attribute storage has no WeightNet: {}", vhuAttributeStorage);
			return;
		}

		final BigDecimal weightNet = weightable.getWeightNetOrNull();
		if (weightNet == null || weightNet.signum() <= 0)
		{
			logger.debug("Skip weight adjusting because Net Weight is not valid: {}", weightNet);
			return;
		}
		final I_C_UOM weightNetUOM = weightable.getWeightNetUOM();
		Check.assumeNotNull(weightNetUOM, "Weight Net UOM was set");

		final ProductId productId = ProductId.ofRepoId(receiptSchedule.getM_Product_ID());
		final IHUStorage vhuStorage = huContext.getHUStorageFactory()
				.getStorage(vhu);
		if (!vhuStorage.isSingleProductStorage())
		{
			// We are considering only those storages which have maximum one M_Product_ID
			logger.debug("Skip weight adjusting because HU Storage is not single product storage: {}", vhuStorage);
			return;
		}

		final BigDecimal qtyHUStorage = vhuStorage.getQty(productId, weightNetUOM);
		final BigDecimal qtyAllocatedTarget = weightNet; // how much we really need to allocate to this receipt schedule
		final BigDecimal qtyToAllocateAbs = qtyAllocatedTarget.subtract(qtyHUStorage);
		logger.debug("HU Storage Qty: {}", qtyHUStorage);
		logger.debug("Target Qty(WeightNet): {}", qtyAllocatedTarget);
		logger.debug("Qty To Allocate: {}", qtyToAllocateAbs);

		//
		// Case: WeightNet is same as HU Storage Qty
		if (qtyToAllocateAbs.signum() == 0)
		{
			logger.debug("HU Storage's Qty is same as WeightNet -> nothing to do");
			return;
		}

		final IAllocationSource source;
		final IAllocationDestination destination;
		final BigDecimal qtyToAllocate;

		//
		// Case: WeightNet > HU Storage Qty
		// =>Transfer from Receipt schedule to HU Storage
		if (qtyToAllocateAbs.signum() > 0)
		{
			source = createSourceOrDestForReceiptSchedule(receiptSchedule);
			destination = createSourceOrDestForVHU(vhu);
			qtyToAllocate = qtyToAllocateAbs;
		}
		//
		// Case: WeightNet < HU Storage Qty
		// =>Transfer from HU Storage to Receipt schedule
		else
		{
			source = createSourceOrDestForVHU(vhu);
			destination = createSourceOrDestForReceiptSchedule(receiptSchedule);
			qtyToAllocate = qtyToAllocateAbs.negate();
		}

		//
		// Create Allocation Request
		IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(
				huContext,
				productId,
				Quantity.of(qtyToAllocate, weightNetUOM),
				SystemTime.asZonedDateTime(),
				receiptSchedule, // referenceModel
				true // forceAllocation => we want to transfer that quantity, no matter what
		);
		allocationRequest = huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, receiptSchedule);
		logger.debug("Allocation request: {}", allocationRequest);

		final HULoader huloader = HULoader.of(source, destination);
		// Don't transfer the attributes because this will override the ones that were set by the user in Wareneingang-POS (see FRESH-92)
		huloader.setSkipAttributesTransfer(true);
		logger.debug("HULoader: {}", huloader);

		final IAllocationResult allocationResult = huloader.load(allocationRequest);
		logger.debug("Allocation result: {}", allocationRequest);

		if (!allocationResult.isCompleted())
		{
			throw new AdempiereException("Could not adjust storage qty to Weight Net."
					+ "\nVHU: " + vhu
					+ "\nReceipt Schedule: " + receiptSchedule
					+ "\nAllocation Request: " + allocationRequest
					+ "\nAllocation Result: " + allocationResult);
		}
	}

	private HUListAllocationSourceDestination createSourceOrDestForVHU(final I_M_HU vhu)
	{
		final HUListAllocationSourceDestination huListAllocationSourceDestination = HUListAllocationSourceDestination.of(vhu);

		// gh #943: in case of aggregate HUs we *only* want to change the storage. we don't want AggregateHUTrxListener do change the TU-per-CU quantity or do other stuff.
		huListAllocationSourceDestination.setStoreCUQtyBeforeProcessing(false);
		return huListAllocationSourceDestination;
	}

	private AbstractAllocationSourceDestination createSourceOrDestForReceiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IProductStorage storage = huReceiptScheduleBL.createProductStorage(receiptSchedule);
		final Object trxReferencedModel = receiptSchedule;
		return new GenericAllocationSourceDestination(storage, trxReferencedModel);
	}

	private boolean isEligible(final I_M_ReceiptSchedule_Alloc rsa)
	{
		if (!rsa.isActive())
		{
			logger.debug("Allocation not eligible because it's not active: {}", rsa);
			return false;
		}

		// Consider only TU/VHU allocations
		final int tuHU_ID = rsa.getM_TU_HU_ID();
		if (tuHU_ID <= 0)
		{
			logger.debug("Allocation not eligible because there is no M_TU_HU_ID: {}", rsa);
			return false;
		}

		// Make sure the RSA's LU or TU is in our scope (if any)
		final int luTU_ID = rsa.getM_LU_HU_ID();
		if (!isInScopeHU(tuHU_ID) && !isInScopeHU(luTU_ID))
		{
			logger.debug("Allocation not eligible because the LU is not in scope: {}", rsa);
			logger.debug("In Scope HUs are: {}", inScopeHU_IDs);
			return false;
		}

		return true;
	}

	private boolean isInScopeHU(final int huId)
	{
		if (huId <= 0)
		{
			return false;
		}

		if (inScopeHU_IDs == null)
		{
			return true;
		}

		if (inScopeHU_IDs.isEmpty())
		{
			return true;
		}

		return inScopeHU_IDs.contains(HuId.ofRepoId(huId));
	}
}
