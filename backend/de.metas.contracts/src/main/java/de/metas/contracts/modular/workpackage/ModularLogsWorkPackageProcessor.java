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

import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.jetbrains.annotations.Nullable;

public class ModularLogsWorkPackageProcessor extends WorkpackageProcessorAdapter
{
	private final ModularContractLogHandler logHandler = SpringContextHolder.instance.getBean(ModularContractLogHandler.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @Nullable final String localTrxName)
	{
		logHandler.handleLogs(getRequest());

		return Result.SUCCESS;
	}

	public enum Params
	{
		MODEL_ACTION,
		LOG_ENTRY_CONTRACT_TYPE
	}

	@NonNull
	private IModularContractLogHandler.HandleLogsRequest<Object> getRequest()
	{
		final TableRecordReference enqueuedModel = CollectionUtils.singleElement(retrieveItems(TableRecordReference.class));
		final ModelAction modelAction = getParameters().getParameterAsEnum(Params.MODEL_ACTION.name(), ModelAction.class)
				.orElseThrow(() -> new AdempiereException("Missing mandatory parameter!")
						.appendParametersToMessage()
						.setParameter("wpParameterName", Params.MODEL_ACTION.name()));

		final LogEntryContractType logEntryContractType = getParameters().getParameterAsEnum(Params.LOG_ENTRY_CONTRACT_TYPE.name(), LogEntryContractType.class)
				.orElseThrow(() -> new AdempiereException("Missing mandatory parameter!")
						.appendParametersToMessage()
						.setParameter("wpParameterName", Params.LOG_ENTRY_CONTRACT_TYPE.name()));

		return IModularContractLogHandler.HandleLogsRequest.builder()
				.model(enqueuedModel.getModel())
				.logEntryContractType(logEntryContractType)
				.modelAction(modelAction)
				.workPackageId(QueueWorkPackageId.ofRepoId(getC_Queue_WorkPackage().getC_Queue_WorkPackage_ID()))
				.build();
	}
}
