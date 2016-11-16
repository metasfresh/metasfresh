package de.metas.async.process;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.IQuery;

import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkpackageProcessorFactory;
import de.metas.async.processor.impl.WorkpackageProcessor2Wrapper;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor2;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.SvrProcess;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Quick and dirty process that allows to process a selection of workpackages which are not flagged as ready for processing, or are flagged as error.
 * It would be cleaner to use the <code>WorkpackageProcessorTask</code>, but I wasn't able to refactor it and make it usable.
 * Note that the WPs are processed with the context of this process.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_Queue_WorkPackage_ProcessSelection extends SvrProcess
{

	private final IWorkpackageProcessorFactory workpackageProcessorFactory = Services.get(IWorkpackageProcessorFactory.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final transient IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		// acquire one overall lock for all the workpackages that we are going to process
		final String lockName = "AD_PInstance_ID=" + getAD_PInstance_ID() + "_" + C_Queue_WorkPackage_ProcessSelection.class.getSimpleName();

		final ILockCommand logCommand = lockManager.lock()
				.setAutoCleanup(true)
				.setOwner(LockOwner.forOwnerName(lockName)) // don't use LockOwner.NONE; we need the owner for lockManager.getLockedByFilter
				.setFailIfAlreadyLocked(false)
				.setSetRecordsByFilter(I_C_Queue_WorkPackage.class, getProcessInfo().getQueryFilter());

		try (final ILockAutoCloseable lock = logCommand.acquire().asAutoCloseable())
		{
			// get an iterator over the workpackages which we just locked
			final IQueryFilter<I_C_Queue_WorkPackage> lockedByFilter = lockManager.getLockedByFilter(I_C_Queue_WorkPackage.class, lock.getLock());

			final Iterator<I_C_Queue_WorkPackage> workpackages = Services.get(IQueryBL.class).createQueryBuilder(I_C_Queue_WorkPackage.class, this)
					.addOnlyActiveRecordsFilter()
					.filter(lockedByFilter)
					.orderBy()
					.addColumn(I_C_Queue_WorkPackage.COLUMN_C_Queue_WorkPackage_ID)
					.endOrderBy()
					.create()
					.setOption(IQuery.OPTION_IteratorBufferSize, 50)
					.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
					.iterate(I_C_Queue_WorkPackage.class);

			for (final I_C_Queue_WorkPackage workPackage : IteratorUtils.asIterable(workpackages))
			{
				// Idea: move the current wp from the overall lock to an individual lock. that way we can release it when it's processed
				// However, this doesn't work. I get an IllegalMonitorException
				// final ILockCommand wpLockCommand = lock
				// .getLock()
				// .split()
				// .setOwner(LockOwner.newOwner(lockName + "_" + workPackage.getC_Queue_WorkPackage_ID()))
				// .addRecordByModel(workPackage);
				try // (final ILockAutoCloseable wpLock = wpLockCommand.acquire().asAutoCloseable())
				{
					// now do the actual processing
					if (workPackage.isProcessed())
					{
						addLog("Skipping {} because it is already processed", workPackage);
						continue; // already done
					}
					if (workPackage.isReadyForProcessing() && !workPackage.isError())
					{
						addLog("Skipping {} because it is flagged as ready for processing and not error", workPackage);
						continue; // might be processed by the server
					}

					final IWorkpackageProcessor workpackageProcessor = workpackageProcessorFactory.getWorkpackageProcessor(workPackage.getC_Queue_Block().getC_Queue_PackageProcessor());
					final IWorkpackageProcessor2 workPackageProcessorWrapped = WorkpackageProcessor2Wrapper.wrapIfNeeded(workpackageProcessor);
					final IParams workpackageParameters = workpackageParamDAO.retrieveWorkpackageParams(workPackage);
					workPackageProcessorWrapped.setParameters(workpackageParameters);

					// Prepare: set workpackage definition
					workPackageProcessorWrapped.setC_Queue_WorkPackage(workPackage);

					workpackageProcessor.processWorkPackage(workPackage, getTrxName());
					workPackage.setIsError(false);
					workPackage.setProcessed(true);
					addLog("Processed {}", workPackage);
				}
				catch (Exception e)
				{
					workPackage.setIsError(true);
				}
				finally
				{
					InterfaceWrapperHelper.save(workPackage);
				}
			}
		}
		return MSG_OK;
	}

}
