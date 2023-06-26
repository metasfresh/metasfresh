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

import de.metas.contracts.modular.log.ModularContractLogDAO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModularContractService
{
	public enum ModelAction
	{
		COMPLETED, REVERSED, REACTIVATED
	}

	private final ModularContractLogDAO contractLogDAO;
	private final List<IModularContractTypeHandler> knownHandlers;

	public ModularContractService(
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final List<IModularContractTypeHandler> knownHandlers)
	{
		this.contractLogDAO = contractLogDAO;
		this.knownHandlers = knownHandlers;
	}

	public void invokeWithModel(@NonNull final Object model, @NonNull final ModelAction action)
	{
		knownHandlers.stream()
				.filter(handler -> handler.probablyAppliesTo(model))
				.forEach(handler -> invokeWithModel(model, action, handler));
	}

	private <T> void invokeWithModel(final @NonNull T model, final @NonNull ModelAction action, final IModularContractTypeHandler<T> handler)
	{
		switch (action)
		{
			case COMPLETED ->
			{
				handler.createLogEntryCreateRequest(model)
						.forEach(contractLogDAO::create);
			}
			case REVERSED ->
			{
				handler.createLogEntryReverseRequest(model)
						.forEach(contractLogDAO::reverse);
			}
			case REACTIVATED ->
			{
				handler.createLogEntryDeleteRequest(model)
						.forEach(contractLogDAO::delete);
			}
		}
	}
}
