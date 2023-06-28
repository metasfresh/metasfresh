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

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class ModularContractHandlerFactory
{
	private final Map<String, IModularContractTypeHandler<Object>> knownHandlersMap;
	private final List<IModularContractTypeHandler<Object>> knownHandlers;

	public ModularContractHandlerFactory(final List<IModularContractTypeHandler<Object>> knownHandlers)
	{
		this.knownHandlers = knownHandlers;
		this.knownHandlersMap = knownHandlers.stream().collect(ImmutableMap.toImmutableMap(handler -> handler.getClass().getName(), Function.identity()));
	}

	@Nullable
	public IModularContractTypeHandler<Object> getByClassName(@NonNull final String name)
	{
		return knownHandlersMap.get(name);
	}

	public Stream<IModularContractTypeHandler<Object>> getApplicableHandlersFor(@NonNull final Object model)
	{
		return knownHandlers.stream()
				.filter(handler -> handler.probablyAppliesTo(model));
	}

}