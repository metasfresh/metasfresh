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


import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBuilder;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;

import java.util.Optional;
import java.util.Properties;

class AsyncBatchBuilder implements IAsyncBatchBuilder
{
	// services
	private final transient AsyncBatchBL asyncBatchBL;
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IWorkpackageProcessorContextFactory contextFactory = Services.get(IWorkpackageProcessorContextFactory.class);

	// Parameters
	private Properties _ctx;
	private PInstanceId adPInstanceId;
	private AsyncBatchId _parentAsyncBatchId;
	private int _countExpected;
	private String _name;
	private String _description;
	private I_C_Async_Batch_Type _asyncBatchType;
	private OrgId orgId;

	AsyncBatchBuilder(final AsyncBatchBL asyncBatchBL)
	{
		this.asyncBatchBL = asyncBatchBL;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public I_C_Async_Batch build()
	{
		final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.create(getCtx(), I_C_Async_Batch.class, ITrx.TRXNAME_None);
		asyncBatch.setAD_PInstance_ID(PInstanceId.toRepoId(getAD_PInstance_Creator_ID()));
		asyncBatch.setParent_Async_Batch_ID(AsyncBatchId.toRepoId(getParentAsyncBatchId()));
		asyncBatch.setName(getName());
		asyncBatch.setDescription(getDescription());
		if (getOrgId() != null)
		{
			final int orgIdRepoId = getOrgId().getRepoId();
			asyncBatch.setAD_Org_ID(orgIdRepoId);
		}
		if (getCountExpected()>0)
		{
			asyncBatch.setCountExpected(getCountExpected());
		}
		asyncBatch.setC_Async_Batch_Type(getC_Async_Batch_Type());
		queueDAO.save(asyncBatch);

		// the orders it's very important: first enque and then set the batch
		// otherwise, will be counted also the workpackage for the batch
		asyncBatchBL.enqueueAsyncBatch(AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID()));

		return asyncBatch;
	}

	@Override
	public IAsyncBatchBuilder setContext(final Properties ctx)
	{
		_ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "ctx not null");
		return _ctx;
	}


	@Override
	public IAsyncBatchBuilder setCountExpected(int expected)
	{
		_countExpected = expected;
		return this;
	}

	private final int getCountExpected()
	{
		return _countExpected;
	}

	@Override
	public IAsyncBatchBuilder setAD_PInstance_Creator_ID(final PInstanceId adPInstanceId)
	{
		this.adPInstanceId = adPInstanceId;
		return this;
	}

	private final PInstanceId getAD_PInstance_Creator_ID()
	{
		return adPInstanceId;
	}

	private AsyncBatchId getParentAsyncBatchId()
	{
		return Optional.ofNullable(_parentAsyncBatchId)
				.orElse(contextFactory.getThreadInheritedWorkpackageAsyncBatch());
	}

	@Override
	public IAsyncBatchBuilder setParentAsyncBatchId(final AsyncBatchId parentAsyncBatchId)
	{
		_parentAsyncBatchId = parentAsyncBatchId;
		return this;
	}

	@Override
	public IAsyncBatchBuilder setName(final String name)
	{
		_name = name;
		return this;
	}

	public String getName()
	{
		return _name;
	}

	@Override
	public IAsyncBatchBuilder setDescription(final String description)
	{
		_description = description;
		return this;
	}

	private String getDescription()
	{
		return _description;
	}

	private OrgId getOrgId()
	{
		return orgId;
	}

	@Override
	public IAsyncBatchBuilder setC_Async_Batch_Type(final String internalName)
	{
		_asyncBatchType = asyncBatchDAO.retrieveAsyncBatchType(getCtx(), internalName);
		return this;
	}

	public I_C_Async_Batch_Type getC_Async_Batch_Type()
	{
		Check.assumeNotNull(_asyncBatchType, "_asyncBatchType not null");
		return _asyncBatchType;
	}

	@Override
	public IAsyncBatchBuilder setOrgId(final OrgId orgId)
	{
		this.orgId = orgId;
		return this;
	}

}
