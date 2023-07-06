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
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Objects;

@Service
public class ModularContractService
{
	public enum ModelAction
	{
		COMPLETED, REVERSED, REACTIVATED, VOIDED
	}

	private final ModularContractLogDAO contractLogDAO;
	private final ModularContractHandlerFactory modularContractHandlerFactory;
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	public ModularContractService(
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractHandlerFactory modularContractHandlerFactory,
			@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.contractLogDAO = contractLogDAO;
		this.modularContractHandlerFactory = modularContractHandlerFactory;
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	public <T> void invokeWithModel(@NonNull final T model, @NonNull final ModelAction action)
	{
		modularContractHandlerFactory.getApplicableHandlersFor(model)
				.forEach(handler -> invokeWithModel(handler, model, action));
	}

	private <T> void invokeWithModel(@NonNull final IModularContractTypeHandler<T> handler, final @NonNull T model, final @NonNull ModelAction action)
	{
		handler.streamContractIds(model)
				.filter(flatrateTermId -> isApplicableContract(handler, flatrateTermId))
				.forEach(flatrateTermId -> invokeWithModel(handler, model, action, flatrateTermId));
	}

	private <T> boolean isApplicableContract(@NonNull final IModularContractTypeHandler<T> handler, @NonNull final FlatrateTermId flatrateTermId)
	{
		if (!isModularContract(flatrateTermId))
		{
			return false;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
		return isHandlerApplicableForSettings(handler, settings);
	}

	private boolean isModularContract(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.getById(flatrateTermId);
		return Objects.equals(X_C_Flatrate_Term.TYPE_CONDITIONS_ModularContract, flatrateTerm.getType_Conditions());
	}

	private static <T> boolean isHandlerApplicableForSettings(@NonNull final IModularContractTypeHandler<T> handler, @Nullable final ModularContractSettings settings)
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

	private <T> void invokeWithModel(@NonNull final IModularContractTypeHandler<T> handler, final @NonNull T model, final @NonNull ModelAction action, @NonNull final FlatrateTermId flatrateTermId)
	{
		handler.validateDocAction(model, action);

		createLogEntries(handler, model, action, flatrateTermId);
	}

	private <T> void createLogEntries(@NonNull final IModularContractTypeHandler<T> handler, final @NonNull T model, final @NonNull ModelAction action, @NonNull final FlatrateTermId flatrateTermId)
	{
		switch (action)
		{
			case COMPLETED -> handler.createLogEntryCreateRequest(model, flatrateTermId)
					.ifPresent(contractLogDAO::create);
			case VOIDED, REACTIVATED, REVERSED -> handler.createLogEntryReverseRequest(model, flatrateTermId)
					.ifPresent(contractLogDAO::reverse);
		}
	}
}
