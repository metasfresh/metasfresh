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
import de.metas.contracts.modular.ModelAction;
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
import java.util.stream.Collectors;

public class ModularLogsWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final ModularContractLogHandler logHandler = SpringContextHolder.instance.getBean(ModularContractLogHandler.class);
	private final ModularLogCreateStatusService createStatusService = SpringContextHolder.instance.getBean(ModularLogCreateStatusService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @Nullable final String localTrxName)
	{
		final TableRecordReferenceSet enqueuedModels = TableRecordReferenceSet.of(retrieveItems(TableRecordReference.class));
		enqueuedModels.assertSingleTableName();

		final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(workpackage.getC_Queue_WorkPackage_ID());

		try
		{
			logHandler.handleLogs(getRequestList(enqueuedModels));

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
		MODEL_ACTION,
		CONTRACT_INFO_LIST
	}

	@NonNull
	private List<IModularContractLogHandler.HandleLogsRequest<Object>> getRequestList(final TableRecordReferenceSet enqueuedModels)
	{
		final ModelAction modelAction = getModelAction();
		final ContractInfoParameter contractInfoParameter = getContractInfoParameter();

		final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(getC_Queue_WorkPackage().getC_Queue_WorkPackage_ID());

		return enqueuedModels.streamReferences()
				.flatMap(recordReference -> {
					final List<ContractInfoParameter.ContractProcessInfo> contractInfoList = contractInfoParameter.getContractInfoByRecordId(recordReference.getRecord_ID());
					if (contractInfoList == null || contractInfoList.isEmpty())
					{
						throw new AdempiereException("Missing mandatory parameter for record reference=" + recordReference)
								.appendParametersToMessage()
								.setParameter("wpParameterName", Params.CONTRACT_INFO_LIST.name());
					}

					return contractInfoList.stream()
							.map(contractProcessInfo -> IModularContractLogHandler.HandleLogsRequest.builder()
									.model(recordReference.getModel())
									.logEntryContractType(contractProcessInfo.getContractType())
									.modelAction(modelAction)
									.workPackageId(workPackageId)
									.handlerClassname(contractProcessInfo.getHandlerClassName())
									.contractId(contractProcessInfo.getFlatrateTermId())
									.build());
				})
				.collect(Collectors.toList());
	}

	@NonNull
	private ContractInfoParameter getContractInfoParameter()
	{
		final String recordId2ContractTypesString = getParameters().getParameterAsString(Params.CONTRACT_INFO_LIST.name());

		return Optional
				.ofNullable(recordId2ContractTypesString)
				.map(recordId2ContractTypesStringParam -> JsonObjectMapperHolder.fromJson(recordId2ContractTypesStringParam, ContractInfoParameter.class))
				.orElseThrow(() -> new AdempiereException("Missing mandatory parameter!")
						.appendParametersToMessage()
						.setParameter("wpParameterName", Params.CONTRACT_INFO_LIST.name()));
	}

	@NonNull
	private ModelAction getModelAction()
	{
		return getParameters().getParameterAsEnum(Params.MODEL_ACTION.name(), ModelAction.class)
				.orElseThrow(() -> new AdempiereException("Missing mandatory parameter!")
						.appendParametersToMessage()
						.setParameter("wpParameterName", Params.MODEL_ACTION.name()));
	}
}
