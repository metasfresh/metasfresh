package de.metas.migration.async;

import ch.qos.logback.classic.Level;
import de.metas.async.QueueProcessorTestBase;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.impl.QueueProcessorsExecutor;
import de.metas.logging.LogManager;
import de.metas.migration.async.MockedExecuteSQLWorkpackageProcessor.ExecuteSQLWorkpackageExpectation;
import de.metas.util.Services;
import org.compiere.util.Env;
import org.junit.Test;

import java.util.Properties;

/*
 * #%L
 * de.metas.async
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ExecuteSQLWorkpackageProcessor_IntegrationTest extends QueueProcessorTestBase
{
	private QueueProcessorsExecutor processorsExecutor;

	@Override
	protected void beforeTestCustomized()
	{
		MockedExecuteSQLWorkpackageProcessor.reset();

		final I_C_Queue_Processor processorDef = helper.createQueueProcessor("test",
				5, // poolSize
				1000 // keepAliveTimeMillis
				);
		helper.assignPackageProcessor(processorDef, MockedExecuteSQLWorkpackageProcessor.class);

		processorsExecutor = new QueueProcessorsExecutor();
		processorsExecutor.addQueueProcessor(processorDef);
	}

	@Override
	protected void afterTestCustomized()
	{
		processorsExecutor.shutdown();
		processorsExecutor = null;
	}

	private final I_C_Queue_WorkPackage enqueueWorkpackage(final String sqlCode)
	{
		final Properties ctx = Env.getCtx();
		return Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, MockedExecuteSQLWorkpackageProcessor.class)
				.newWorkPackage()
				//
				// Workpackage Parameters
				.parameters()
				.setParameter(ExecuteSQLWorkpackageProcessor.PARAM_WORKPACKAGE_SQL, sqlCode)
				.end()
				//
				// Build & enqueue
				.buildAndEnqueue();
	}

	@Test
	public void testWithReEnqueue() throws Exception
	{
		LogManager.setLevel(Level.DEBUG);
		MockedExecuteSQLWorkpackageProcessor.setDefaultExpectation(new ExecuteSQLWorkpackageExpectation()
		{
			private int executeCount = 0;

			@Override
			public int onExecuteSql(String sql, String trxName)
			{
				executeCount++;

				if (executeCount < 3)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		});

		final I_C_Queue_WorkPackage workpackage = enqueueWorkpackage("some test sql");
		helper.markReadyForProcessing(workpackage);
		helper.waitUntilSize(MockedExecuteSQLWorkpackageProcessor.processedWorkpackages, 3, 5000);
	}

}
