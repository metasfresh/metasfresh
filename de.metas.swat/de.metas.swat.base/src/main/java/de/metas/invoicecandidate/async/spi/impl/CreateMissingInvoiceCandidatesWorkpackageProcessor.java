package de.metas.invoicecandidate.async.spi.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.lock.exceptions.LockFailedException;

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

/**
 * Creates {@link I_C_Invoice_Candidate}s for given models.
 *
 * To schedule an invoice candidates creation for a given model, please use {@link #schedule(Object)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreateMissingInvoiceCandidatesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	/**
	 * Schedule given model (document or table record) to be evaluated and {@link I_C_Invoice_Candidate}s records to be generated for it, asynchronously.
	 *
	 * NOTE: the workpackages are not created right away, but the models are collected per database transaction and a workpackage is enqueued when the transaction is committed.
	 *
	 * @param model
	 */
	public static final void schedule(final Object model)
	{
		SCHEDULER.schedule(model);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<Object> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<Object>(CreateMissingInvoiceCandidatesWorkpackageProcessor.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final Object model)
		{
			//
			// Check if we shall create the invoice candidates
			final Properties ctx = InterfaceWrapperHelper.getCtx(model);
			final String tableName = InterfaceWrapperHelper.getModelTableName(model);
			final List<IInvoiceCandidateHandler> handlers = Services.get(IInvoiceCandidateHandlerBL.class).retrieveImplementationsForTable(ctx, tableName);
			boolean isCreateCandidates = false;
			for (final IInvoiceCandidateHandler handler : handlers)
			{
				isCreateCandidates = handler.isCreateMissingCandidatesAutomatically(model);
				if (isCreateCandidates)
				{
					break;
				}
			}
			if (!isCreateCandidates)
			{
				return false;
			}

			return true;
		};

		@Override
		protected Properties extractCtxFromItem(final Object item)
		{
			return InterfaceWrapperHelper.getCtx(item);
		}

		@Override
		protected String extractTrxNameFromItem(final Object item)
		{
			return InterfaceWrapperHelper.getTrxName(item);
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final Object item)
		{
			return TableRecordReference.of(item);
		}
	};

	// services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		try (final IAutoCloseable updateInProgressCloseable = invoiceCandBL.setUpdateProcessInProgress())
		{
			final List<Object> models = queueDAO.retrieveItemsSkipMissing(workpackage, Object.class, localTrxName);
			for (final Object model : models)
			{
				final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateHandlerBL.createMissingCandidatesFor(model);
				Loggables.get().addLog("Created " + invoiceCandidates.size() + " invoice candidate for " + model);
			}
		}
		catch (final LockFailedException e)
		{
			// One of the models could not be locked => postpone processing this workpackage
			throw WorkpackageSkipRequestException.createWithThrowable("Skip processing because: " + e.getLocalizedMessage(), e);
		}

		return Result.SUCCESS;
	}
}
