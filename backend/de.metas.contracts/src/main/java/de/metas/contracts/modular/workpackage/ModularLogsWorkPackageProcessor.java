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
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.SpringContextHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModularLogsWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final ModularContractLogHandler logHandler = SpringContextHolder.instance.getBean(ModularContractLogHandler.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @Nullable final String localTrxName)
	{
		logHandler.handleLogs(getRequestList());

		return Result.SUCCESS;
	}

	public enum Params
	{
		MODEL_ACTION,
		LOG_ENTRY_CONTRACT_TYPE
	}

	@NonNull
	private List<IModularContractLogHandler.HandleLogsRequest<Object>> getRequestList()
	{
		final TableRecordReferenceSet enqueuedModels = TableRecordReferenceSet.of(retrieveItems(TableRecordReference.class));
		enqueuedModels.assertSingleTableName();

		final ModelAction modelAction = getModelAction();
		final Map<Integer, List<LogEntryContractType>> recordId2ContractTypes = getRecordId2ContractType();

		final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(getC_Queue_WorkPackage().getC_Queue_WorkPackage_ID());

		return enqueuedModels.streamReferences()
				.flatMap(recordReference -> {
					final List<LogEntryContractType> contractTypes = recordId2ContractTypes.get(recordReference.getRecord_ID());

					if (contractTypes == null || contractTypes.isEmpty())
					{
						throw new AdempiereException("Missing mandatory parameter for record reference=" + recordReference)
								.appendParametersToMessage()
								.setParameter("wpParameterName", Params.LOG_ENTRY_CONTRACT_TYPE.name());
					}

					return contractTypes.stream()
							.map(contractType -> IModularContractLogHandler.HandleLogsRequest.builder()
									.model(recordReference.getModel())
									.logEntryContractType(contractType)
									.modelAction(modelAction)
									.workPackageId(workPackageId)
									.build());
				})
				.collect(Collectors.toList());
	}

	@NonNull
	private Map<Integer, List<LogEntryContractType>> getRecordId2ContractType()
	{
		final String recordId2ContractTypesString = getParameters().getParameterAsString(Params.LOG_ENTRY_CONTRACT_TYPE.name());

		final ContractTypeParameter contractTypeParameter = Optional
				.ofNullable(recordId2ContractTypesString)
				.map(recordId2ContractTypesStringParam -> JsonObjectMapperHolder.fromJson(recordId2ContractTypesStringParam, ContractTypeParameter.class))
				.orElseThrow(() -> new AdempiereException("Missing mandatory parameter!")
						.appendParametersToMessage()
						.setParameter("wpParameterName", Params.LOG_ENTRY_CONTRACT_TYPE.name()));

		return contractTypeParameter.getRecordId2ContractType();
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
