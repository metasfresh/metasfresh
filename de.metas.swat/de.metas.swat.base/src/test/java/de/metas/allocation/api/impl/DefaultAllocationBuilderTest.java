package de.metas.allocation.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Greeting;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.allocation.api.DefaultAllocationBuilder;
import de.metas.allocation.api.DefaultAllocationLineBuilder;
import de.metas.allocation.api.IAllocationLineBuilder;

public class DefaultAllocationBuilderTest
{

	static final IContextAware ctxProvider = InterfaceWrapperHelper.getContextAware(POJOWrapper.create(Env.getCtx(), I_C_Greeting.class, "trxName"));

	@Before
	public void enableUnitTestMode()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void addLineTest()
	{
		final DefaultAllocationBuilder builder = new DefaultAllocationBuilder(ctxProvider);
		final IAllocationLineBuilder line = builder.addLine();
		assertThat(line, instanceOf(DefaultAllocationLineBuilder.class));
		assertTrue(line.getParent() == builder);
	}

	public static class CustomLineBuilder extends DefaultAllocationLineBuilder
	{
		public CustomLineBuilder(DefaultAllocationBuilder parent)
		{
			super(parent);
		}

	};

	@Test
	public void addLineWithCustomBuilderTest()
	{
		final DefaultAllocationBuilder builder = new DefaultAllocationBuilder(ctxProvider);
		final IAllocationLineBuilder line = builder.addLine(CustomLineBuilder.class);

		assertThat(line, instanceOf(CustomLineBuilder.class));
		assertTrue(line.getParent() == builder);
	}
}
