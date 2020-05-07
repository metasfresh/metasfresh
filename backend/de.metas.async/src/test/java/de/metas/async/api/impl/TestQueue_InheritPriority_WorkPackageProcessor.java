package de.metas.async.api.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.IWorkpackageProcessor;

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

/**
 * Works together with {@link TestQueue_InheritPriority}. can't be an inner class of that test class because of reflection-instantiation problems.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TestQueue_InheritPriority_WorkPackageProcessor implements IWorkpackageProcessor
{
	/**
	 * To be set by the test class {@link TestQueue_InheritPriority}.
	 */
	static IWorkpackagePrioStrategy expectedPriority;

	static boolean returnDirectly = false;

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		if (returnDirectly)
		{
			return Result.SUCCESS;
		}

		// guard assertions, just to make illustrate that we deal with the package 'wp1' from test_retrieveAllElements()
		final List<I_C_Queue_Element> elements = Services.get(IQueueDAO.class).retrieveQueueElements(workpackage, false);
		assertThat(elements.size(), is(1));
		assertThat(elements.get(0).getAD_Table_ID(), is(12345));
		assertThat(elements.get(0).getRecord_ID(), is(456));

		final IWorkPackageQueue queueForEnqueuing = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(
						InterfaceWrapperHelper.getCtx(workpackage),
						TestQueue_InheritPriority_WorkPackageProcessor.class);
		final I_C_Queue_WorkPackage wp2 = queueForEnqueuing
				.newBlock()
				.newWorkpackage()
				.build();

		// this is the actual test!
		assertThat("wp2 did not inherit wp1's prio", wp2.getPriority(), is(expectedPriority.getPrioriy(queueForEnqueuing)));

		// comment in to make the test fail and thus verify that the test-code works.
		// assertThat("wp2 did not inherit wp1's prio", wp2.getPriority(), is(ConstantWorkpackagePrio.medium().getPrioriy(queueForEnqueuing)));

		returnDirectly = true; // without this, the the new WP would also be processed/verified in this method, and so on.
		return Result.SUCCESS;
	}
}