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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
class ModularContractLogHandlerRegistry
{
	@NonNull
	private final List<IModularContractLogHandler<?>> handlers;

	@NonNull <T> Stream<IModularContractLogHandler<T>> streamHandlers(@NonNull final IModularContractLogHandler.HandleLogsRequest<T> request)
	{
		return handlers.stream()
				.filter(handler -> handler.getType().isAssignableFrom(request.getModel().getClass()))
				.map(handler -> (IModularContractLogHandler<T>)handler)
				.filter(handler -> handler.applies(request));
	}
}
