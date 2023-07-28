package de.metas.printing.api;

/*
 * #%L
 * de.metas.printing.base
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


import java.util.List;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;

public interface IPrintJobBL extends ISingletonService
{
	/**
	 * Aggregates the all unprocessed {@link I_C_Printing_Queue}s into new printjobs. The grouping is done by AD_Org_ID and AD_Printer_ID, where the printer is retrieved via printer routing.
	 *
	 * <p>
	 * Note that this method can deal with large numbers of unprocessed printing queue records.
	 * <p>
	 * This method is skipping items which were already printed (see {@link I_C_Printing_Queue#isProcessed()})
	 */
	void createPrintJobs(IPrintingQueueSource source, ContextForAsyncProcessing printJobContext);

	default void createPrintJobs(@NonNull final IPrintingQueueSource source)
	{
		createPrintJobs(source, ContextForAsyncProcessing.builder().build());
	}

	@Builder
	@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
	@Getter
	class ContextForAsyncProcessing
	{
		@Nullable
		PInstanceId adPInstanceId;

		@Default
		int parentAsyncBatchId = -1;
	}

	/**
	 * Creates an instructions record for the given print job.
	 *
	 * @param userToPrintId the user for whom we shall actually do the printing. Note that if this user's printing config forwards to a shared config, then the instructions instance is created for the shared
	 *            config's user
	 * @param createWithSpecificHostKey if <code>false</code>, then
	 *            <ul>
	 *            <li>the given <code>userToPrintId</code> is set to be the user, without considering any forwarding
	 *            <li>no hostkey is set to the instructions, meaning that any printing client which has a session with the given <code>userToPrintId</code> can do the printing.
	 *            </ul>
	 *            Note that even if this is <code>true</code>, it will be ignored if there is no hostkey to be obtained, see {@link IPrintClientsBL#getHostKeyOrNull(java.util.Properties)}.
	 * @param copies number of copies to print (1 means one printout).
	 */
	I_C_Print_Job_Instructions createPrintJobInstructions(
			UserId userToPrintId,
			boolean createWithSpecificHostKey,
			I_C_Print_Job_Line firstLine,
			I_C_Print_Job_Line lastLine,
			int copies);

	String getSummary(I_C_Print_Job printJob);

	List<I_C_Print_Job_Detail> getCreatePrintJobDetails(I_C_Print_Job_Line printJobLine);

	/**
	 * Enqueue print job instructions for async pdf printing
	 */
	void enqueuePrintJobInstructions(I_C_Print_Job_Instructions jobInstructions, I_C_Async_Batch asyncBatch);
}
