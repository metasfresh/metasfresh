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

import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModularContractService
{
	public enum ModelAction
	{
		COMPLETED, REVERSED, REACTIVATED
	}

	private final ImmutableList<IModularContractTypeHandler> handlers;
	private final ModularContractSettingsDAO contractSettingsDAO;

	private final ModularContractLogDAO contractLogDAO;

	public ModularContractService(@NonNull final ImmutableList<IModularContractTypeHandler> handlers,
			final ModularContractSettingsDAO contractSettingsDAO,
			final ModularContractLogDAO contractLogDAO)
	{
		this.handlers = handlers;
		this.contractSettingsDAO = contractSettingsDAO;
		this.contractLogDAO = contractLogDAO;
	}

	public void invokeWithModel(@NonNull final Object model, @NonNull final ModelAction action)
	{
		for (final IModularContractTypeHandler handler : handlers)
		{
			final Optional<FlatrateTermId> contractId = handler.getContractId(model);
			if (contractId.isEmpty())
			{
				continue;
			}

			final ModularContractSettings settings = contractSettingsDAO.getFor(contractId.get());
			if (handler.probablyAppliesTo(model, settings))
			{
				switch (action)
				{
					case COMPLETED ->
					{
						final Optional<LogEntryCreateRequest> request = handler.createLogEntryCreateRequest(model, settings);
						request.ifPresent(contractLogDAO::create);
					}
					case REVERSED ->
					{
						final Optional<LogEntryReverseRequest> request = handler.createLogEntryReverseRequest(model);
						request.ifPresent(contractLogDAO::reverse);
					}
					case REACTIVATED ->
					{
						final Optional<LogEntryDeleteRequest> request = handler.createLogEntryDeleteRequest(model);
						request.ifPresent(contractLogDAO::delete);
					}
				}

			}
		}
	}
}
