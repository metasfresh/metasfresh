/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.workpackage;

import de.metas.JsonObjectMapperHolder;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.SpringContextHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ModularLogsWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final ModularContractLogHandler logHandler = SpringContextHolder.instance.getBean(ModularContractLogHandler.class);
	private final ModularLogCreateStatusService createStatusService = SpringContextHolder.instance.getBean(ModularLogCreateStatusService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @Nullable final String localTrxName)
	{
		final TableRecordReferenceSet enqueuedModels = TableRecordReferenceSet.of(retrieveItems(TableRecordReference.class));

		final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(workpackage.getC_Queue_WorkPackage_ID());

		try
		{
			logHandler.handleLogs(getRequestList());

			enqueuedModels.forEach(recordReference -> createStatusService.setStatusSuccessfullyProcessed(workPackageId, recordReference));
		}
		catch (final Exception e)
		{
			// the current one will be rolled back
			trxManager.runInNewTrx(() -> enqueuedModels
					.forEach(recordReference -> createStatusService.setStatusErrored(workPackageId, recordReference, e)));

			throw e;
		}

		return Result.SUCCESS;
	}

	public enum Params
	{
		REQUESTS_TO_PROCESS
	}

	@NonNull
	private List<IModularContractLogHandler.HandleLogsRequest<Object>> getRequestList()
	{
		final ProcessModularLogAggRequest processModularLogAggRequest = getRequestListParam();

		final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(getC_Queue_WorkPackage().getC_Queue_WorkPackage_ID());

		return processModularLogAggRequest.getRequestList()
				.stream()
				.map(processRequest -> IModularContractLogHandler.HandleLogsRequest.builder()
						.model(processRequest.getRecordReference().getModel())
						.logEntryContractType(processRequest.getLogEntryContractType())
						.modelAction(processRequest.getAction())
						.workPackageId(workPackageId)
						.handlerClassname(processRequest.getHandlerClassname())
						.contractId(processRequest.getFlatrateTermId())
						.build())
				.toList();
	}

	@NonNull
	private ProcessModularLogAggRequest getRequestListParam()
	{
		final String requestsToProcess = getParameters().getParameterAsString(Params.REQUESTS_TO_PROCESS.name());

		return Optional.ofNullable(requestsToProcess)
				.map(requestsToProcessParam -> JsonObjectMapperHolder.fromJson(requestsToProcess, ProcessModularLogAggRequest.class))
				.orElseThrow(() -> new AdempiereException("Missing mandatory parameter!")
						.appendParametersToMessage()
						.setParameter("wpParameterName", Params.REQUESTS_TO_PROCESS.name()));
	}
}
