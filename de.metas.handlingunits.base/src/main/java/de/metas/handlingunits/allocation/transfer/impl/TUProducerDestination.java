package de.metas.handlingunits.allocation.transfer.impl;

import java.math.BigDecimal;

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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;

import com.jgoodies.common.base.Objects;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.impl.AbstractProducerDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.UpperBoundAllocationStrategy;
import de.metas.handlingunits.impl.HandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;

/**
 * Creates TUs.
 *
 * But, instead of using standard capacity definition of given TU PI, we will use a constrained capacity which is provided via {@link #addCapacityConstraint(IHUCapacityDefinition)}
 * Its allocation strategy is the {@link UpperBoundAllocationStrategy}.
 *
 * @author tsa
 *
 */
/* package */final class TUProducerDestination extends AbstractProducerDestination
{
	private final I_M_HU_PI tuPI;

	//
	// Constrained capacity to use
	private final Map<Integer, Capacity> productId2capacity = new HashMap<>();

	/** How many TUs to produce (maximum) */
	private int maxTUs = Integer.MAX_VALUE;

	private I_M_HU_Item parentItem = null;

	private I_M_HU_Item parentItemOverride = null;

	private I_M_HU_PI_Item parentPIItem;

	public TUProducerDestination(final I_M_HU_PI tuPI)
	{
		super();
		Check.assumeNotNull(tuPI, "tuPI not null");
		this.tuPI = tuPI;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void addCapacityConstraint(final Capacity capacity)
	{
		if (capacity == null)
		{
			return;
		}

		final int productId = capacity.getM_Product().getM_Product_ID();
		productId2capacity.put(productId, capacity);
	}

	/**
	 * Convenience method that invokes {@link #addCapacityConstraint(Capacity)} for each item of the given collection.
	 *
	 * @param capacities may be {@code null}. In that case, nothing is done.
	 */
	public void addCapacityConstraints(final Collection<? extends Capacity> capacities)
	{
		if (capacities == null)
		{
			return;
		}
		capacities.forEach(c -> addCapacityConstraint(c));
	}

	/**
	 * Sets how many TUs produce (maximum). After those TUs are produced this producer will stop.
	 *
	 * @param maxTUs
	 */
	public final void setMaxTUs(final int maxTUs)
	{
		Check.assume(maxTUs > 0, "maxTUs > 0 but it was {}", maxTUs);
		this.maxTUs = maxTUs;
	}

	/**
	 * Can be set in order to enable this instance to allocate a remaining qty not to an aggregate VHU but to a real one.
	 * Also see {@link #getParent_HU_Item()} and {@link #loadHU(I_M_HU, IAllocationRequest)}.
	 *
	 * @param parentItem
	 */
	public void setParentItem(final I_M_HU_Item parentItem)
	{
		this.parentItem = parentItem;
	}

	/**
	 * See {@link #getParent_HU_Item()}.
	 *
	 * @param parentPIItem
	 */
	public void setParentPIItem(final I_M_HU_PI_Item parentPIItem)
	{
		this.parentPIItem = parentPIItem;
	}

	/**
	 * Return the HU item that was set via {@link #setParentItem(I_M_HU_Item)},
	 * <b>or</b> an "override" item which this instance created using
	 * <li>the parent-HU of the last aggregate VHU that this instance saw via {@link #loadHU(I_M_HU, IAllocationRequest)}
	 * <li>the HU PI item that was set using {@link #setParentPIItem(I_M_HU_PI_Item)}.
	 */
	@Override
	protected I_M_HU_Item getParent_HU_Item()
	{
		if (parentItemOverride != null)
		{
			return parentItemOverride;
		}
		return parentItem;
	}

	@Override
	public boolean isAllowCreateNewHU()
	{
		// only create TUs as long as they fit in one LU (qtyTUperLU)
		final int createdHUCount = getCreatedHUsCount();
		return createdHUCount < maxTUs;
	}

	@Override
	protected I_M_HU_PI getM_HU_PI()
	{
		return tuPI;
	}

	/**
	 * Allocates the given request to the given <code>tuHU</code>, using {@link UpperBoundAllocationStrategy}.
	 * <p>
	 * If the given {@code tuHU} is actually an aggregate VHU and if the request's qty would only partially fill one of the TUs represented within the given {@code tuHU},
	 * then this method allocates nothing, but generates a HU item so that the remaining qty will be allocated not to an aggregate VHU, but to a "real" one.
	 *
	 * @param tuHU TU to load. might also be the VHU of the LU's aggregation item.
	 * @param request
	 * @return allocation result
	 */
	@Override
	protected IAllocationResult loadHU(final I_M_HU tuHU, final IAllocationRequest request)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final Capacity capacityPerTU = getCapacity(request, tuHU);

		if (handlingUnitsBL.isAggregateHU(tuHU))
		{
			// check if the TU's capacity exceeds the current request
			final CapacityInterface exceedingCapacityOfTU = capacityPerTU.subtractQuantity(request.getQuantity());

			if (HandlingUnitsDAO.VIRTUAL_HU_PI_ID == parentPIItem.getIncluded_HU_PI_ID()
					|| exceedingCapacityOfTU.getCapacityQty().signum() > 0)
			{
				// Either this loading is about putting CUs directly on an LU which can be done, but then an aggregate HU is not supported and doesn't make sense (issue gh #1194).
				// or the request's capacity is less than a full TU.
				// In both cases it means means that we can't allocate against the aggregate tuHU.

				// Instead we need to create a "real" (virtual or partially filled) TU now and then allocate our stuff against that one.

				// get the aggregate VHU's parent and make sure it now has a "real" HU item with type "HandlingUnit". It will be preferred as parent item next time child HU (i.e. a sibling for tuHU) is created.
				// therefore this sibling will be a "real" HU and not an aggregate one.
				final I_M_HU parentHU = handlingUnitsDAO.retrieveParent(tuHU);
				Check.errorIf(parentHU == null, "given tuHU is an aggregate VHU, but has no parent HU; tuHU={}", tuHU);

				parentItemOverride = handlingUnitsDAO.createHUItemIfNotExists(parentHU, parentPIItem).getLeft();

				// don't allocate anything in this round, to trigger our caller to create a new HU
				return AllocationUtils.createMutableAllocationResult(request);
			}

			final I_M_HU_Item haItem = tuHU.getM_HU_Item_Parent();

			// specify the HU PI Item that is represented in this aggregate
			haItem.setM_HU_PI_Item(parentPIItem);

			// updating the qty is also done by an IHUTrxListener after loading took place, *but* only for items whose HU were a loading *source*;
			// here we also set the qty for new items
			haItem.setQty(haItem.getQty().add(BigDecimal.ONE));

			InterfaceWrapperHelper.save(haItem);
		}

		final IAllocationStrategy allocationStrategy = getAllocationStrategy(request, capacityPerTU);
		final IAllocationResult result = allocationStrategy.execute(tuHU, request);

		return result;
	}

	private final IAllocationStrategy getAllocationStrategy(final IAllocationRequest request, final Capacity capacityToUse)
	{
		final IAllocationStrategy allocationStrategy = new UpperBoundAllocationStrategy(capacityToUse);
		return allocationStrategy;
	}

	/**
	 * Get the capacity of the given {@code tu}. Hint: also check the comments in this method.
	 *
	 * @param request the request which contains e.g. the product in question.
	 * @param hu the HU of we want to find the capacity.
	 * @return
	 */
	private Capacity getCapacity(final IAllocationRequest request, final I_M_HU hu)
	{
		final int productId = request.getProduct().getM_Product_ID();
		final Capacity capacityToUse;
		final Capacity capacityOverride = productId2capacity.get(productId);

		if (capacityOverride == null)
		{
			// So there was no override capacity provided for this product.
			// The allocationStrategy we are creating just now might execute against the aggregate VHU which does not have any M_HU_PI_Item_Products and therefore does not know the TUs actual capacity.
			// To compensate for this, we now find out the TU's capacity and make it the allocation strategy's upper bound
			final List<I_M_HU_PI_Item> materialPIItems = handlingUnitsDAO.retrievePIItems(tuPI, getC_BPartner()).stream()
					.filter(piItem -> Objects.equals(X_M_HU_PI_Item.ITEMTYPE_Material, piItem.getItemType()))
					.collect(Collectors.toList());

			Check.errorIf(materialPIItems.size() != 1, "There has to be exactly one M_HU_PI_Item for the TU's M_HU_PI and and C_BPartner;\n "
					+ "M_HU_PI={};\n "
					+ "C_BPartner={};\n "
					+ "M_HU_PI_Item(s) found={}\n "
					+ "this={}", tuPI, getC_BPartner(), materialPIItems, this);

			final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
			final I_M_HU_PI_Item_Product itemProduct = hupiItemProductDAO.retrievePIMaterialItemProduct(materialPIItems.get(0), getC_BPartner(), request.getProduct(), request.getDate());

			final IHUCapacityBL capacityBL = Services.get(IHUCapacityBL.class);
			final Capacity capacity = capacityBL.getCapacity(itemProduct, request.getProduct(), request.getC_UOM());

			capacityToUse = capacity;
		}
		else
		{
			capacityToUse = capacityOverride; // we can go with capacityOverride==null, if the given hu is a "real" one (not aggregate), because the code will use the hu's PI-item.
		}
		return capacityToUse;
	}
}
