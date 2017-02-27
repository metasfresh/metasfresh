package de.metas.handlingunits.allocation.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;

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

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * This producer is used if stuff needs to be allocated into a "flat" HU and no capacity constraints need to be taken into account.
 * I.e. don't invoke it with a palet and expect it to magically discover or create and allocate to included HUs.
 * <p>
 * For most real world use cases, you will probably want to use {@link LUTUProducerDestination} instead of this one.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUProducerDestination extends AbstractProducerDestination
{
	public static final HUProducerDestination of(final I_M_HU_PI huPI)
	{
		return new HUProducerDestination(huPI);
	}

	/**
	 * @return producer which will create one VHU
	 */
	public static final HUProducerDestination ofVirtualPI()
	{
		final I_M_HU_PI vhuPI = Services.get(IHandlingUnitsDAO.class).retrieveVirtualPI(Env.getCtx());
		return new HUProducerDestination(vhuPI)
				.setMaxHUsToCreate(1); // we want one VHU
	}
	
	public static final HUProducerDestination ofM_HU_PI_Item_Product_ID(final int M_HU_PI_Item_Product_ID)
	{
		final I_M_HU_PI_Item_Product piItemProduct = InterfaceWrapperHelper.create(Env.getCtx(), M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		return ofM_HU_PI_Item_Product(piItemProduct);
	}
	
	public static final HUProducerDestination ofM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product piItemProduct)
	{
		final I_M_HU_PI huPI = piItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		return new HUProducerDestination(huPI);
	}

	
	private final I_M_HU_PI _huPI;

	private boolean _allowCreateNewHU = true;

	protected IAllocationStrategyFactory allocationStrategyFactory = Services.get(IAllocationStrategyFactory.class);

	/**
	 * Maximum number of HUs allowed to be created
	 */
	private int maxHUsToCreate = Integer.MAX_VALUE;

	private I_M_HU_Item parentHUItem;

	public HUProducerDestination(final I_M_HU_PI huPI)
	{
		Check.assumeNotNull(huPI, "huPI not null");
		_huPI = huPI;
	}

	@Override
	protected I_M_HU_PI getM_HU_PI()
	{
		return _huPI;
	}

	@Override
	protected IAllocationResult loadHU(final I_M_HU hu, final IAllocationRequest request)
	{
		final IAllocationStrategy allocationStrategy = allocationStrategyFactory.getAllocationStrategy(hu);
		final IAllocationResult result = allocationStrategy.execute(hu, request);
		return result;
	}

	@Override
	public boolean isAllowCreateNewHU()
	{
		if (!_allowCreateNewHU)
		{
			return false;
		}

		//
		// Check if we already reached the maximum number of HUs that we are allowed to create
		if (getCreatedHUsCount() >= maxHUsToCreate)
		{
			return false;
		}

		return true;
	}

	public HUProducerDestination setAllowCreateNewHU(final boolean allowCreateNewHU)
	{
		_allowCreateNewHU = allowCreateNewHU;
		return this;
	}

	public HUProducerDestination setMaxHUsToCreate(final int maxHUsToCreate)
	{
		this.maxHUsToCreate = maxHUsToCreate;
		return this;
	}

	/**
	 * Then this producer creates a new HU, than i uses the given {@code parentHUItem} for the new HU's {@link I_M_HU#COLUMN_M_HU_Item_Parent_ID}.
	 * 
	 * @param parentHUItem
	 */
	public HUProducerDestination setParent_HU_Item(final I_M_HU_Item parentHUItem)
	{
		this.parentHUItem = parentHUItem;
		return this;
	}

	@Override
	protected I_M_HU_Item getParent_HU_Item()
	{
		return parentHUItem;
	}
}
