package de.metas.async.api.impl;

/*
 * #%L
 * de.metas.async
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Function;

import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.impl.ConstantWorkpackagePrio;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;

/**
 * Enqueue a number of workpackages and verify their priorities.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TestQueue_SizeBasedPrio
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private Properties ctx;

	/**
	 * Sets up the {@link SizeBasedWorkpackagePrio} with a simple size2prio function.
	 */
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		//
		// Setup test data
		ctx = Env.getCtx();

		((SizeBasedWorkpackagePrio)SizeBasedWorkpackagePrio.INSTANCE)
				.setAlternativeSize2constantPrio(
				new Function<Integer, ConstantWorkpackagePrio>()
				{
					@Override
					public ConstantWorkpackagePrio apply(final Integer input)
					{
						switch (input)
						{
							case 0:
								return ConstantWorkpackagePrio.urgent();
							case 1:
								return ConstantWorkpackagePrio.high();
							case 2:
								return ConstantWorkpackagePrio.medium();
							case 3:
								return ConstantWorkpackagePrio.low();
							case 4:
								return ConstantWorkpackagePrio.minor();
							default:
								throw new AdempiereException("input=" + input + " shall not happen in this test");
						}
					}
				});
	}

	@After
	public void cleanup()
	{
		((SizeBasedWorkpackagePrio)SizeBasedWorkpackagePrio.INSTANCE)
				.setAlternativeSize2constantPrio(null);
	}

	/**
	 * Enqueues a number of packages. using {@link IWorkPackageBlockBuilder} and {@link IWorkPackageBuilder}. Also see {@link #init()}.
	 */
	@Test
	public void testWithWorkPackageBuilder()
	{
		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		final IWorkPackageQueue queueForEnqueuing = workPackageQueueFactory.getQueueForEnqueuing(ctx, TestQueue_InheritPriority_WorkPackageProcessor.class);

		final IWorkPackageBlockBuilder blockBuilder = queueForEnqueuing.newBlock();

		final I_C_Queue_WorkPackage wp1 = blockBuilder.newWorkpackage().build();
		assertThat(wp1.getPriority(), is(X_C_Queue_WorkPackage.PRIORITY_Urgent));

		final I_C_Queue_WorkPackage wp2 = blockBuilder.newWorkpackage().build();
		assertThat(wp2.getPriority(), is(X_C_Queue_WorkPackage.PRIORITY_High));

		final I_C_Queue_WorkPackage wp3 = blockBuilder.newWorkpackage().build();
		assertThat(wp3.getPriority(), is(X_C_Queue_WorkPackage.PRIORITY_Medium));

		final I_C_Queue_WorkPackage wp4 = blockBuilder.newWorkpackage().build();
		assertThat(wp4.getPriority(), is(X_C_Queue_WorkPackage.PRIORITY_Low));

		final I_C_Queue_WorkPackage wp5 = blockBuilder.newWorkpackage().build();
		assertThat(wp5.getPriority(), is(X_C_Queue_WorkPackage.PRIORITY_Minor));
	}
}
