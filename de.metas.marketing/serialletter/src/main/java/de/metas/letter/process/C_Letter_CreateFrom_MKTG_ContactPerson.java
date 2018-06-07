package de.metas.letter.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import com.google.common.collect.ImmutableList;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.letter.LetterConstants;
import de.metas.letter.service.async.spi.impl.C_Letter_CreateFromMKTG_ContactPerson_Async;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.process.JavaProcess;
import lombok.NonNull;

/*
 * #%L
 * de.metas.marketing.serialletter
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_Letter_CreateFrom_MKTG_ContactPerson extends JavaProcess
{
	// Services
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private int campaignId;

	@Override
	protected void prepare()
	{
		campaignId = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<Integer> campaignContactPersonIds = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateAndStream()
				.map(I_MKTG_Campaign_ContactPerson::getMKTG_Campaign_ContactPerson_ID)
				.collect(ImmutableList.toImmutableList());

		if (campaignContactPersonIds.isEmpty())
		{
			return MSG_Error + ": 0 records enqueued";
		}

		final I_C_Async_Batch asyncbatch = createAsycnBatch(campaignContactPersonIds.size());
		final IWorkPackageBuilder workPackageBuilder = createWorkPackageBuilde(asyncbatch);
		campaignContactPersonIds.forEach(campaignContactPersonId -> addElement(workPackageBuilder, campaignContactPersonId));
		workPackageBuilder.build();
		return MSG_OK;
	}

	private I_C_Async_Batch createAsycnBatch(final int size)
	{
		// Create Async Batch for tracking
		return asyncBatchBL.newAsyncBatch()
				.setContext(getCtx())
				.setC_Async_Batch_Type(LetterConstants.C_Async_Batch_InternalName_CreateLettersAsync)
				.setAD_PInstance_Creator_ID(getAD_PInstance_ID())
				.setName("Create Letters for Campaign " + campaignId)
				.build();
	}

	private IWorkPackageBuilder createWorkPackageBuilde(@NonNull final I_C_Async_Batch asyncbatch)
	{
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(getCtx(), C_Letter_CreateFromMKTG_ContactPerson_Async.class);
		return queue
				.newBlock()
				.setAD_PInstance_Creator_ID(getAD_PInstance_ID())
				.setContext(getCtx())
				.newWorkpackage()
				.setC_Async_Batch(asyncbatch)
				.bindToThreadInheritedTrx();

	}

	private void addElement(@NonNull final IWorkPackageBuilder workPackageBuilder, final Integer campaignContactPersonId)
	{
		if (campaignContactPersonId == null || campaignContactPersonId <= 0)
		{
			// should not happen
			return ;
		}

		final I_MKTG_Campaign_ContactPerson campaignContactPerson = load(campaignContactPersonId, I_MKTG_Campaign_ContactPerson.class);
		workPackageBuilder.addElement(campaignContactPerson);
	}
}
