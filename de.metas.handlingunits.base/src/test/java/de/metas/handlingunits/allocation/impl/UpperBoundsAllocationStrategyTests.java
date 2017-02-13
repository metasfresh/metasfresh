package de.metas.handlingunits.allocation.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class UpperBoundsAllocationStrategyTests
{

	private HUTestHelper helper;
	private final BigDecimal sixThousand = new BigDecimal("6000");

	/**
	 * a hu that is set up in {@link #init()} to contain one virtual {@link I_M_HU_Item}.
	 */
	private I_M_HU vhu;

	@Before
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		vhu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.save(vhu);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		// create the hu-item that shall hold the tomatoes
		final I_M_HU_Item vhuItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class);
		vhuItem.setM_HU(vhu);
		vhuItem.setM_HU_PI_Item_ID(handlingUnitsDAO.getVirtual_HU_PI_Item_ID());
		vhuItem.setItemType(X_M_HU_Item.ITEMTYPE_Material);
		InterfaceWrapperHelper.save(vhuItem);
	}

	/**
	 * Verifies that if there is a virtual HU item with type {@link X_M_HU_Item#ITEMTYPE_Material} then the full qty is allocated to that item.
	 */
	@Test
	public void testAllocateAllToVirtualItemWithoutUpperBound()
	{
		// allocate this, despite it not really fitting (because that's not the testee's business).
		final BigDecimal requestQty = sixThousand.add(new BigDecimal("0.321"));
		final IAllocationRequest request = mkRequest(requestQty);

		final UpperBoundAllocationStrategy testee = new UpperBoundAllocationStrategy(null);
		final IAllocationResult result = testee.execute(vhu, request);

		assertThat(result.isCompleted(), is(true));
		assertThat(result.getQtyAllocated(), comparesEqualTo(requestQty));
		assertThat(result.getQtyToAllocate(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(result.getTransactions().size(), is(1));

		final IHUTransaction huTransaction = result.getTransactions().get(0);
		assertThat(huTransaction.getProduct(), is(helper.pTomato));
		assertThat(huTransaction.getQuantity().getQty(), is(requestQty));
		assertThat(huTransaction.getQuantity().getUOM(), is(helper.uomKg));
		assertThat(huTransaction.getM_HU(), is(vhu));
	}

	/**
	 * Similar to {@link #testAllocateAllToVirtualItemWithoutUpperBound()}, but in this test, the testee has an upper bound to the quantity that may be allocated.
	 */
	@Test
	public void testAllocateAllToVirtualItemWithUpperBound()
	{
		// request this, note that the 0.321 are above the upper build that we will specify.
		final BigDecimal requestQty = sixThousand.add(new BigDecimal("0.321"));

		final IAllocationRequest request = mkRequest(requestQty);

		final IHUCapacityDefinition capacity = Services.get(IHUCapacityBL.class).createCapacity(sixThousand, helper.pTomato, helper.uomKg, false);

		final UpperBoundAllocationStrategy testee = new UpperBoundAllocationStrategy(capacity);
		final IAllocationResult result = testee.execute(vhu, request);

		assertThat(result.isCompleted(), is(false));
		assertThat(result.getQtyAllocated(), comparesEqualTo(sixThousand));
		assertThat(result.getQtyToAllocate(), comparesEqualTo(new BigDecimal("0.321")));
		assertThat(result.getTransactions().size(), is(1));

		final IHUTransaction huTransaction = result.getTransactions().get(0);
		assertThat(huTransaction.getProduct(), is(helper.pTomato));
		assertThat(huTransaction.getQuantity().getQty(), is(sixThousand));
		assertThat(huTransaction.getQuantity().getUOM(), is(helper.uomKg));
		assertThat(huTransaction.getM_HU(), is(vhu));
	}

	private IAllocationRequest mkRequest(final BigDecimal qtyTomatoes)
	{
		final IMutableHUContext huContext0 = helper.createMutableHUContextOutOfTransaction();

		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext0,
				helper.pTomato, // product
				qtyTomatoes, // qty
				helper.uomKg, // uom
				SystemTime.asTimestamp());
		return request;
	}
}
