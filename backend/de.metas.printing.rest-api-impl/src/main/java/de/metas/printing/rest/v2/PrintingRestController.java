/*
 * #%L
 * de.metas.printing.rest-api-impl
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.printing.rest.v2;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CacheMgt;
import de.metas.common.rest_api.v2.printing.request.JsonPrintingResultRequest;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingDataResponse;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.printing.model.X_C_Print_Job_Line;
import de.metas.printing.printingdata.PrintingData;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@RestController
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/printing"})
public class PrintingRestController
{
	private final PrintingDataFactory printingDataFactory;
	private final PrintDataRequestHandler printDataRequestHandler;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PrintingRestController(
			@NonNull final PrintingDataFactory printingDataFactory,
			@NonNull final PrintDataRequestHandler printDataRequestHandler)
	{
		this.printingDataFactory = printingDataFactory;
		this.printDataRequestHandler = printDataRequestHandler;
	}

	@Nullable
	@PostMapping("/getPrintingData/{printingQueueId}")
	public JsonPrintingDataResponse getNextPrintingData(
			@PathVariable("printingQueueId") final int printingQueueId,
			@Autowired final HttpServletResponse response)
	{
		final I_C_Printing_Queue queueRecord = InterfaceWrapperHelper.load(printingQueueId, I_C_Printing_Queue.class);
		if(queueRecord == null)
		{
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		final ImmutableList<PrintingData> data = printingDataFactory.createPrintingDataForQueueItem(queueRecord);
		return printDataRequestHandler.createResponse(data);
	}

	/**
	 * Sends feedback regarding the print
	 */
	@PostMapping("/setPrintingResult/{printingQueueId}")
	public ResponseEntity<Object> setPrintingResult(
			@PathVariable("printingQueueId") final int printingQueueId,
			@RequestBody @NonNull final JsonPrintingResultRequest input)
	{
		final I_C_Printing_Queue queueRecord = InterfaceWrapperHelper.load(printingQueueId, I_C_Printing_Queue.class);
		if(queueRecord == null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		final I_C_Print_Job_Line printJobLineRecord = queryBL.createQueryBuilder(I_C_Print_Job_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(X_C_Print_Job_Line.COLUMNNAME_C_Printing_Queue_ID, printingQueueId)
				.create()
				.firstOnlyNotNull(I_C_Print_Job_Line.class);

		final int printJobId = printJobLineRecord.getC_Print_Job_ID();
		final I_C_Print_Job_Instructions printJobInstructionsRecord = queryBL.createQueryBuilder(I_C_Print_Job_Instructions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(X_C_Print_Job_Instructions.COLUMNNAME_C_Print_Job_ID, printJobId)
				.create()
				.firstOnlyNotNull(I_C_Print_Job_Instructions.class);

		if(input.isProcessed())
		{
			final I_C_Print_Job printJobRecord = InterfaceWrapperHelper.load(printJobId, I_C_Print_Job.class);
			printJobRecord.setProcessed(true);
			save(printJobRecord);
			CacheMgt.get().reset(X_C_Print_Job.Table_Name, printJobId);
			printJobInstructionsRecord.setStatus(X_C_Print_Job_Instructions.STATUS_Done);
		}
		else
		{
			if(!Check.isEmpty(input.getErrorMsg()))
			{
				printJobInstructionsRecord.setErrorMsg(input.getErrorMsg());
			}

			printJobInstructionsRecord.setStatus(X_C_Print_Job_Instructions.STATUS_Error);
		}
		save(printJobInstructionsRecord);
		CacheMgt.get().reset(X_C_Print_Job_Instructions.Table_Name, printJobInstructionsRecord.getC_Print_Job_Instructions_ID());

		return ResponseEntity.ok().build();
	}
}
