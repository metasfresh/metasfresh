package de.metas.invoicecandidate.async.spi.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQuery;
import de.metas.async.api.impl.WorkPackageQuery;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.common.util.Check;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerRequest;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Properties;

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

class UpdateInvalidInvoiceCandidatesWorkpackageProcessorScheduler extends WorkpackagesOnCommitSchedulerTemplate<IInvoiceCandUpdateSchedulerRequest>
{
	private final static Logger logger = LogManager.getLogger(UpdateInvalidInvoiceCandidatesWorkpackageProcessorScheduler.class);

	public UpdateInvalidInvoiceCandidatesWorkpackageProcessorScheduler(final boolean createOneWorkpackagePerAsyncBatch)
	{
		super(UpdateInvalidInvoiceCandidatesWorkpackageProcessor.class);
		super.setCreateOneWorkpackagePerAsyncBatch(createOneWorkpackagePerAsyncBatch);
	}

	@Override
	protected Properties extractCtxFromItem(@NonNull final IInvoiceCandUpdateSchedulerRequest item)
	{
		return item.getCtx();
	}

	@Override
	protected String extractTrxNameFromItem(@NonNull final IInvoiceCandUpdateSchedulerRequest item)
	{
		return item.getTrxName();
	}

	@Override
	protected Object extractModelToEnqueueFromItem(@Nullable final Collector collector, @Nullable final IInvoiceCandUpdateSchedulerRequest item)
	{
		return null; // there is no actual model to be collected
	}

	@Override
	protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
	{
		return true;
	}

	/**
	 * Return {@code true} only if there is not yet an existing unprocessed workpackage for the {@link UpdateInvalidInvoiceCandidatesWorkpackageProcessor}.
	 * This is important to avoid making metasfresh to deal with a large number of such WBs where actually the first one already did all the work.
	 */
	protected boolean isEligibleForScheduling(@NonNull final IInvoiceCandUpdateSchedulerRequest item)
	{
		if (Adempiere.isUnitTestMode())
		{
			return true;
		}

		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final String classname = UpdateInvalidInvoiceCandidatesWorkpackageProcessor.class.getName();
		final QueuePackageProcessorId packageProcessorId = queueDAO.retrieveQueuePackageProcessorIdFor(classname);

		// if there is already a workpackage, don't enqueue another one
		final ImmutableSet<QueuePackageProcessorId> packageProcessorIds = ImmutableSet.of(
				Check.assumeNotNull(
						packageProcessorId,
						"There is an active C_Queue_PackageProcessor record for classname {}." + classname));

		final IWorkPackageQuery existingWorkpackageQuery = new WorkPackageQuery()
				.setPackageProcessorIds(packageProcessorIds)
				.setProcessed(false)
				.setError(false)
				.setReadyForProcessing(true);

		final IQuery<I_C_Queue_WorkPackage> query = queueDAO.createQuery(item.getCtx(), existingWorkpackageQuery);

		// The item is eligible if there is one unprocessed WP or zero
		// If found that one mass-invocing cucumber-test failed when I checked for <=2 packages.
		// I'm not 100% sure why (very probably trx and multithreading-related), but for good measure I'm even adding another WP if there are already 10 existing.
		final int existingWorkpackagesCnt = query.count();
		final boolean result = existingWorkpackagesCnt <= 10;

		Loggables.withLogger(logger, Level.DEBUG).addLog("UpdateInvalidInvoiceCandidatesWorkpackageProcessorScheduler.isEligibleForScheduling - existingWorkpackagesCnt={} => returning {}", existingWorkpackagesCnt, result);
		return result;
	}
}
