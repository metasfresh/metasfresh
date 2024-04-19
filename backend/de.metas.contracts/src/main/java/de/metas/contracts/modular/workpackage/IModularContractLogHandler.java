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
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.contracts.modular.settings.ModuleConfigAndSettingsId;
import de.metas.i18n.ExplainedOptional;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

public interface IModularContractLogHandler
{
	default boolean applies(@NonNull final CreateLogRequest ignoredRequest) {return true;}

	@NonNull
	String getSupportedTableName();

	@NonNull
	default LogEntryContractType getLogEntryContractType() {return LogEntryContractType.MODULAR_CONTRACT;}

	@NonNull
	ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull CreateLogRequest createLogRequest);

	@NonNull
	ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull HandleLogsRequest handleLogsRequest);

	@NonNull
	IComputingMethodHandler getComputingMethod();

	@NonNull
	default LogEntryDeleteRequest toLogEntryDeleteRequest(@NonNull final HandleLogsRequest handleLogsRequest)
	{
		return LogEntryDeleteRequest.builder()
				.referencedModel(handleLogsRequest.getTableRecordReference())
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(getLogEntryContractType())
				.build();
	}

	@Value
	@Builder
	class HandleLogsRequest
	{
		@NonNull TableRecordReference tableRecordReference;
		@NonNull LogEntryContractType logEntryContractType;
		@NonNull ModelAction modelAction;
		@NonNull QueueWorkPackageId workPackageId;
		@NonNull ComputingMethodType computingMethodType;
		@NonNull FlatrateTermInfo contractInfo;

		@NonNull
		public FlatrateTermId getContractId()
		{
			return contractInfo.getFlatrateTermId();
		}
	}

	@Value
	@Builder
	class CreateLogRequest
	{
		@NonNull HandleLogsRequest handleLogsRequest;
		@NonNull ModularContractSettings modularContractSettings;
		@NonNull String productName;
		@NonNull ModuleConfig moduleConfig;
		@NonNull ModularContractTypeId typeId;

		public @NonNull FlatrateTermId getContractId()
		{
			return handleLogsRequest.getContractId();
		}

		public @NonNull TableRecordReference getRecordRef()
		{
			return handleLogsRequest.getTableRecordReference();
		}

		public @NonNull ModuleConfigAndSettingsId getConfigId()
		{
			return moduleConfig.getId();
		}
	}

	@Value
	@Builder
	class FlatrateTermInfo
	{
		@NonNull FlatrateTermId flatrateTermId;
		@Nullable ProductId productId;
	}
}
