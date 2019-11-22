package de.metas.allocation.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Greeting;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.allocation.api.DefaultAllocationBuilder;
import de.metas.allocation.api.IAllocationBuilder;

public class AllocationBuilderTest
{
	static IContextAware ctxProvider;

	@BeforeAll
	public static void beforeAll()
	{
		AdempiereTestHelper.get().staticInit();
		ctxProvider = InterfaceWrapperHelper.getContextAware(InterfaceWrapperHelper.create(Env.getCtx(), I_C_Greeting.class, "trxName"));
	}

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

	}

	@Test
	public void testDefaultBuilderImpl()
	{
		final AllocationBL allocationBL = new AllocationBL();

		final IAllocationBuilder builder = allocationBL.newBuilder(ctxProvider);
		assertThat(builder).isInstanceOf(DefaultAllocationBuilder.class);
	}

	static class CustomAllocationBuilderInclClass extends DefaultAllocationBuilder
	{
		public CustomAllocationBuilderInclClass(IContextAware contextProvider)
		{
			super(contextProvider);
			assertThat(contextProvider).isInstanceOf(ctxProvider.getClass());
		}
	}

	@Test
	public void testOwnBuilderImplIncl()
	{
		final AllocationBL allocationBL = new AllocationBL();

		final IAllocationBuilder builder = allocationBL.newBuilder(ctxProvider, CustomAllocationBuilderInclClass.class);
		assertThat(builder).isInstanceOf(CustomAllocationBuilderInclClass.class);
	}
}
