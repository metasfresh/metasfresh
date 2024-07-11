package de.metas.letter.process;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.letter.LetterConstants;
import de.metas.letter.service.async.spi.impl.C_Letter_CreateFromMKTG_ContactPerson_Async;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;

import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
	private final ContactPersonRepository contactPersonRepo = Adempiere.getBean(ContactPersonRepository.class);

	private int campaignId;

	@Override
	protected void prepare()
	{
		campaignId = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<Integer> campaignContactPersonIds = contactPersonRepo.getIdsByCampaignId(campaignId);

		if (campaignContactPersonIds.isEmpty())
		{
			return MSG_Error + ": 0 records enqueued";
		}

		final I_C_Async_Batch asyncbatch = createAsyncBatch();
		for (final Integer campaignContactPersonId : campaignContactPersonIds)
		{
			enqueue(asyncbatch, campaignContactPersonId);
		}

		return MSG_OK;
	}

	private I_C_Async_Batch createAsyncBatch()
	{
		// Create Async Batch for tracking
		return asyncBatchBL.newAsyncBatch()
				.setContext(getCtx())
				.setC_Async_Batch_Type(LetterConstants.C_Async_Batch_InternalName_CreateLettersAsync)
				.setAD_PInstance_Creator_ID(getPinstanceId())
				.setName("Create Letters for Campaign " + campaignId)
				.build();
	}

	private void enqueue(@NonNull final I_C_Async_Batch asyncBatch, final Integer campaignContactPersonId)
	{
		if (campaignContactPersonId == null || campaignContactPersonId <= 0)
		{
			// should not happen
			return;
		}

		final I_MKTG_Campaign_ContactPerson campaignContactPerson = load(campaignContactPersonId, I_MKTG_Campaign_ContactPerson.class);

		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), C_Letter_CreateFromMKTG_ContactPerson_Async.class);
		queue.newWorkPackage()
				.setC_Async_Batch(asyncBatch) // set the async batch in workpackage in order to track it
				.addElement(campaignContactPerson)
				.setUserInChargeId(getUserId())
				.buildAndEnqueue();
	}
}
