package de.metas.handlingunits.allocation.transfer.impl;

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

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;

import com.jgoodies.common.base.Objects;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.impl.AbstractProducerDestination;
import de.metas.handlingunits.allocation.impl.UpperBoundAllocationStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

/**
 * Creates TUs.
 *
 * But, instead of using standard capacity definition of given TU PI, we will use the a constrained capacity which is provided.
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
	private final Map<Integer, IHUCapacityDefinition> productId2capacity = new HashMap<>();

	/** How many TUs to produce (maximum) */
	private int maxTUs = Integer.MAX_VALUE;

	private I_M_HU_Item parentItem = null;

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

	public void addCapacityConstraint(final IHUCapacityDefinition capacity)
	{
		if (capacity == null)
		{
			return;
		}

		final int productId = capacity.getM_Product().getM_Product_ID();
		productId2capacity.put(productId, capacity);
	}

	public void addCapacityConstraints(final Collection<? extends IHUCapacityDefinition> capacities)
	{
		if (Check.isEmpty(capacities))
		{
			return;
		}

		for (final IHUCapacityDefinition capacity : capacities)
		{
			addCapacityConstraint(capacity);
		}
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

	public void setParentItem(final I_M_HU_Item parentItem)
	{
		this.parentItem = parentItem;
	}

	@Override
	protected I_M_HU_Item getParent_HU_Item()
	{
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
	 *
	 * @param tuHU TU to load. might also be the VHU of the LU's aggregation item.
	 * @param request
	 * @return allocation result
	 */
	@Override
	protected IAllocationResult loadHU(final I_M_HU tuHU, final IAllocationRequest request)
	{
		final IAllocationStrategy allocationStrategy = getAllocationStrategy(request);
		final IAllocationResult result = allocationStrategy.execute(tuHU, request);
		return result;
	}

	private final IAllocationStrategy getAllocationStrategy(final IAllocationRequest request)
	{
		final int productId = request.getProduct().getM_Product_ID();
		final IHUCapacityDefinition capacityToUse;
		final IHUCapacityDefinition capacityOverride = productId2capacity.get(productId);
		if (capacityOverride == null)
		{
			// So there was no override capacity provided for this product.
			// The allocationStrategy we are creating just now might execute against the aggregate VHU which does not have any M_HU_PI_Item_Products and therefore does not know the TUs actual capacity.
			// To compensate for this, we now find out the TU's capacity and make it the allocation strategie's upper bound
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
			final IHUCapacityDefinition capacity = capacityBL.getCapacity(itemProduct, request.getProduct(), request.getC_UOM());
			
			capacityToUse = capacity;
		}
		else
		{
			capacityToUse = capacityOverride;
		}
		final IAllocationStrategy allocationStrategy = new UpperBoundAllocationStrategy(capacityToUse);
		return allocationStrategy;
	}

}
