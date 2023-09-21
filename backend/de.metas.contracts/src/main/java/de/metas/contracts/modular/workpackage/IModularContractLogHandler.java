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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfigId;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

public interface IModularContractLogHandler<T>
{
	LogAction getLogAction(@NonNull HandleLogsRequest<T> request);

	BooleanWithReason doesRecordStateRequireLogCreation(@NonNull T model);

	@NonNull
	ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull CreateLogRequest<T> createLogRequest);

	@NonNull
	ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull HandleLogsRequest<T> handleLogsRequest);

	@NonNull
	IModularContractTypeHandler<T> getModularContractTypeHandler();

	@NonNull
	default Class<? extends IModularContractTypeHandler<T>> getModularContractTypeHandlerClass()
	{
		//noinspection unchecked
		return (Class<? extends IModularContractTypeHandler<T>>)getModularContractTypeHandler().getClass();
	}

	default boolean applies(@NonNull final HandleLogsRequest<T> request)
	{
		final IModularContractTypeHandler<T> contractTypeHandler = getModularContractTypeHandler();

		final boolean isHandlerMatchingRequest = contractTypeHandler.getClass().getName().equals(request.getHandlerClassname())
				&& contractTypeHandler.applies(request.getLogEntryContractType())
				&& contractTypeHandler.applies(request.getModel());

		final boolean isHandlerMatchingContract = getModularContractTypeHandler()
				.streamContractIds(request.getModel())
				.anyMatch(contractId -> contractId.equals(request.getContractId()));

		if (isHandlerMatchingRequest && !isHandlerMatchingContract)
		{
			Loggables.addLog("Handler: {} is matching request, but not the contractId! see request: {}!", this.getClass().getName(), request);
		}

		return isHandlerMatchingContract && isHandlerMatchingRequest;
	}

	@NonNull
	default Class<T> getType()
	{
		return getModularContractTypeHandler().getType();
	}

	@NonNull
	default LogEntryDeleteRequest getDeleteRequestFor(@NonNull final HandleLogsRequest<T> handleLogsRequest)
	{
		return LogEntryDeleteRequest.builder()
				.referencedModel(handleLogsRequest.getModelRef())
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(handleLogsRequest.getLogEntryContractType())
				.build();
	}

	@Value
	@Builder
	class HandleLogsRequest<T>
	{
		@NonNull T model;
		@NonNull LogEntryContractType logEntryContractType;
		@NonNull ModelAction modelAction;
		@NonNull QueueWorkPackageId workPackageId;
		@NonNull String handlerClassname;
		@NonNull FlatrateTermId contractId;

		public TableRecordReference getModelRef()
		{
			return TableRecordReference.of(model);
		}
	}

	@Value
	@Builder
	class CreateLogRequest<T>
	{
		@NonNull HandleLogsRequest<T> handleLogsRequest;
		@NonNull ModularContractSettings modularContractSettings;
		@NonNull ModuleConfigId configId;
		@NonNull ModularContractTypeId typeId;

		public FlatrateTermId getContractId()
		{
			return handleLogsRequest.getContractId();
		}
	}

	enum LogAction
	{
		CREATE,
		REVERSE,
		RECOMPUTE
	}
}
