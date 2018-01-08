package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.ConcurrentModificationException;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_MRP;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.planning.pporder.LiberoException;

public class BoundedMRPRecordAndQtyTest
{
	private IContextAware context;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new PlainContextAware(Env.getCtx());
	}

	@Test
	public void test_QtyLessThanBounded()
	{
		final IMutableMRPRecordAndQty mrp = createMRPRecordAndQty(230);
		final BoundedMRPRecordAndQty mrpBounded = new BoundedMRPRecordAndQty(mrp, new BigDecimal(100));

		Assert.assertThat("Invalided qty", new BigDecimal("230"), Matchers.comparesEqualTo(mrp.getQty()));
		Assert.assertThat("Invalided bounded qty", new BigDecimal("100"), Matchers.comparesEqualTo(mrpBounded.getQty()));

		mrpBounded.setQty(BigDecimal.ZERO);
		Assert.assertThat("Invalided qty", new BigDecimal("130"), Matchers.comparesEqualTo(mrp.getQty()));
		Assert.assertThat("Invalided bounded qty", new BigDecimal("0"), Matchers.comparesEqualTo(mrpBounded.getQty()));
	}

	@Test
	public void test_QtyGreaterThanBounded()
	{
		final IMutableMRPRecordAndQty mrp = createMRPRecordAndQty(230);
		final BoundedMRPRecordAndQty mrpBounded = new BoundedMRPRecordAndQty(mrp, new BigDecimal(300));

		Assert.assertThat("Invalided qty", new BigDecimal("230"), Matchers.comparesEqualTo(mrp.getQty()));
		Assert.assertThat("Invalided bounded qty", new BigDecimal("230"), Matchers.comparesEqualTo(mrpBounded.getQty()));

		mrpBounded.setQty(BigDecimal.ZERO);
		Assert.assertThat("Invalided qty", new BigDecimal("0"), Matchers.comparesEqualTo(mrp.getQty()));
		Assert.assertThat("Invalided bounded qty", new BigDecimal("0"), Matchers.comparesEqualTo(mrpBounded.getQty()));
	}

	@Test(expected = LiberoException.class)
	public void test_SetingNegativeQtyNotAllowed()
	{
		final IMutableMRPRecordAndQty mrp = createMRPRecordAndQty(230);
		final BoundedMRPRecordAndQty mrpBounded = new BoundedMRPRecordAndQty(mrp, new BigDecimal(100));

		// this shall throw an exception because negative quantities are not allowed,
		// even if the actual qty that will be set in underlying mrp record will be positive.
		mrpBounded.setQty(new BigDecimal("-10"));
	}

	@Test(expected = ConcurrentModificationException.class)
	public void test_ConcurrentModificationException()
	{
		final IMutableMRPRecordAndQty mrp = createMRPRecordAndQty(230);
		final BoundedMRPRecordAndQty mrpBounded = new BoundedMRPRecordAndQty(mrp, new BigDecimal(100));

		mrp.setQty(new BigDecimal("300"));

		// shall throw exception because the Qty from underlying mrp record has changed, which is not allowed
		mrpBounded.getQty();
	}

	public IMutableMRPRecordAndQty createMRPRecordAndQty(final int qtyInt)
	{
		final BigDecimal qty = BigDecimal.valueOf(qtyInt);
		final I_PP_MRP mrpRecord = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, context);
		mrpRecord.setQty(qty);
		mrpRecord.setQtyRequiered(qty);
		final MRPRecordAndQty mrp = new MRPRecordAndQty(mrpRecord);
		return mrp;
	}
}
