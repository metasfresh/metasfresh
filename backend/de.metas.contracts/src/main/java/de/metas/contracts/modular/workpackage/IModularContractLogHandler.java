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

import com.google.common.collect.ImmutableSet;
import de.metas.async.QueueWorkPackageId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.i18n.ExplainedOptional;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Set;

public interface IModularContractLogHandler<T>
{
	LogAction getLogAction(@NonNull HandleLogsRequest<T> request);

	@NonNull
	ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull CreateLogRequest<T> handleLogsRequest);

	@NonNull
	ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull HandleLogsRequest<T> handleLogsRequest, @NonNull FlatrateTermId contractId);

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

		return contractTypeHandler.applies(request.getLogEntryContractType())
				&& contractTypeHandler.applies(request.getModel());
	}

	@NonNull
	default Class<T> getType()
	{
		return getModularContractTypeHandler().getType();
	}

	default Set<FlatrateTermId> getContractIds(@NonNull final T model)
	{
		return getModularContractTypeHandler()
				.streamContractIds(model)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Value
	@Builder
	class HandleLogsRequest<T>
	{
		@NonNull T model;
		@NonNull LogEntryContractType logEntryContractType;
		@NonNull ModelAction modelAction;
		@NonNull QueueWorkPackageId workPackageId;

		public TableRecordReference getModelRef() {return TableRecordReference.of(model);}
	}

	@Value
	@Builder
	class CreateLogRequest<T>
	{
		@NonNull HandleLogsRequest<T> handleLogsRequest;
		@NonNull ModularContractSettings modularContractSettings;
		@NonNull ModularContractTypeId typeId;
		@NonNull FlatrateTermId contractId;
	}

	enum LogAction
	{
		CREATE,
		REVERSE
		//RECOMPUTE TBD
	}
}
