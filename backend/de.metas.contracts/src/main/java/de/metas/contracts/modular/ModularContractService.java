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

package de.metas.contracts.modular;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.workpackage.ProcessModularLogsEnqueuer;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class ModularContractService
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	@NonNull
	private final ModularContractHandlerFactory modularContractHandlerFactory;
	@NonNull
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull
	private final ProcessModularLogsEnqueuer processLogsEnqueuer;

	public <T> void invokeWithModel(@NonNull final T model, @NonNull final ModelAction action, @NonNull final LogEntryContractType logEntryContractType)
	{
		modularContractHandlerFactory.getApplicableHandlersFor(model, logEntryContractType)
				.forEach(handler -> invokeWithModel(handler, model, action, logEntryContractType));
	}

	private <T> void invokeWithModel(
			@NonNull final IModularContractTypeHandler<T> handler,
			@NonNull final T model,
			@NonNull final ModelAction action,
			@NonNull final LogEntryContractType logEntryContractType)
	{
		createContractNowIfRequired(handler, model, action);

		handler.streamContractIds(model)
				.filter(flatrateTermId -> isApplicableContract(handler, flatrateTermId))
				.forEach(flatrateTermId -> invokeWithModel(handler, model, action, flatrateTermId, logEntryContractType));
	}

	private <T> void createContractNowIfRequired(
			@NonNull final IModularContractTypeHandler<T> handler,
			@NonNull final T model,
			@NonNull final ModelAction action)
	{
		if (ModelAction.COMPLETED == action)
		{
			handler.createContractIfRequired(model);
		}
	}

	private <T> boolean isApplicableContract(@NonNull final IModularContractTypeHandler<T> handler, @NonNull final FlatrateTermId flatrateTermId)
	{
		if (!isModularOrInterimContract(flatrateTermId))
		{
			return false;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
		return isHandlerApplicableForSettings(handler, settings);
	}

	private boolean isModularOrInterimContract(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term flatrateTermRecord = flatrateDAO.getById(flatrateTermId);
		final TypeConditions typeConditions = TypeConditions.ofCode(flatrateTermRecord.getType_Conditions());
		return typeConditions.isModularOrInterim();
	}

	private static <T> boolean isHandlerApplicableForSettings(
			@NonNull final IModularContractTypeHandler<T> handler,
			@Nullable final ModularContractSettings settings)
	{
		if (settings == null)
		{
			return false;
		}
		final String handlerClassName = handler.getClass().getName();
		return settings.getModuleConfigs()
				.stream()
				.anyMatch(config -> config.isMatchingClassName(handlerClassName));
	}

	private <T> void invokeWithModel(
			@NonNull final IModularContractTypeHandler<T> handler,
			@NonNull final T model,
			@NonNull final ModelAction action,
			@NonNull final FlatrateTermId flatrateTermId,
			@NonNull final LogEntryContractType logEntryContractType)
	{
		handler.validateDocAction(model, action);

		processLogsEnqueuer.enqueueAfterCommit(TableRecordReference.of(model), action, logEntryContractType);

		handleAction(handler, model, action, flatrateTermId);
	}

	private <T> void handleAction(
			@NonNull final IModularContractTypeHandler<T> handler,
			@NonNull final T model,
			@NonNull final ModelAction action,
			@NonNull final FlatrateTermId flatrateTermId)
	{
		switch (action) //FIXME
		{
			case VOIDED -> handler.cancelLinkedContractsIfAllowed(model, flatrateTermId);
		}

		handler.handleAction(model, action, this);
	}
}
