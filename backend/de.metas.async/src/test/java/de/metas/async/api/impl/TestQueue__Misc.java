package de.metas.async.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class TestQueue__Misc
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void markReadyForProcessingAfterTrxCommit_NoThreadIneritedTrx()
	{
		final I_C_Queue_WorkPackage workpackage = newInstance(I_C_Queue_WorkPackage.class);
		save(workpackage);
		assertThat(workpackage.isReadyForProcessing()).isFalse();

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queueForEnqueuing = workPackageQueueFactory.getQueueForEnqueuing(NOPWorkpackageProcessor.class);
		queueForEnqueuing.markReadyForProcessingAfterTrxCommit(workpackage, ITrx.TRXNAME_ThreadInherited);
		assertThat(workpackage.isReadyForProcessing()).isTrue();
	}

	private static final class NOPWorkpackageProcessor extends WorkpackageProcessorAdapter
	{
		@Override
		public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
		{
			return Result.SUCCESS;
		}
	}
}
