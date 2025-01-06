package de.metas.invoicecandidate.async.spi.impl;

import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerRequest;
import de.metas.logging.LogManager;
import lombok.NonNull;
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
}
