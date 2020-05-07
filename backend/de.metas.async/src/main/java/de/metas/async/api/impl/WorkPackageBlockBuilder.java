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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;

/* package */class WorkPackageBlockBuilder implements IWorkPackageBlockBuilder
{
	// services
	private final transient IQueueDAO dao;
	private final transient IWorkPackageQueue _workPackageQueue;

	// Parameters
	private Properties _ctx;
	private int _adPInstanceId;
	private int _queuePackageProcessorId;

	// status
	private I_C_Queue_Block _block = null;
	private final List<IWorkPackageBuilder> workpackageBuilders = new ArrayList<>();

	public WorkPackageBlockBuilder(final IWorkPackageQueue workPackageQueue, final IQueueDAO dao)
	{
		super();

		Check.assumeNotNull(workPackageQueue, "workPackageQueue not null");
		_workPackageQueue = workPackageQueue;

		Check.assumeNotNull(dao, "dao not null");
		this.dao = dao;
	}

	@Override
	public I_C_Queue_Block build()
	{
		return getCreateBlock();
	}

	/* package */final I_C_Queue_Block getCreateBlock()
	{
		if (_block == null)
		{
			_block = InterfaceWrapperHelper.create(getCtx(), I_C_Queue_Block.class, ITrx.TRXNAME_None);

			_block.setC_Queue_PackageProcessor_ID(getC_Queue_PackageProcessor_ID());
			_block.setAD_PInstance_Creator_ID(getAD_PInstance_Creator_ID());

			dao.save(_block);
		}
		return _block;
	}

	/* package */final IWorkPackageQueue getWorkPackageQueue()
	{
		return _workPackageQueue;
	}

	private final void assertBlockNotCreated()
	{
		Check.assumeNull(_block, "block was not already created");
	}

	@Override
	public IWorkPackageBlockBuilder setContext(final Properties ctx)
	{
		assertBlockNotCreated();

		_ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "ctx not null");
		return _ctx;
	}

	/* package */IWorkPackageBlockBuilder setC_Queue_PackageProcessor_ID(final int queuePackageProcessorId)
	{
		assertBlockNotCreated();

		_queuePackageProcessorId = queuePackageProcessorId;
		return this;
	}

	private final int getC_Queue_PackageProcessor_ID()
	{
		Check.assume(_queuePackageProcessorId > 0, "queuePackageProcessorId is set");
		return _queuePackageProcessorId;
	}

	@Override
	public IWorkPackageBlockBuilder setAD_PInstance_Creator_ID(final int adPInstanceId)
	{
		assertBlockNotCreated();

		_adPInstanceId = adPInstanceId;
		return this;
	}

	private final int getAD_PInstance_Creator_ID()
	{
		return _adPInstanceId;
	}

	@Override
	public IWorkPackageBuilder newWorkpackage()
	{
		final WorkPackageBuilder workpackageBuilder = new WorkPackageBuilder(this);
		workpackageBuilders.add(workpackageBuilder);
		return workpackageBuilder;
	}
}
