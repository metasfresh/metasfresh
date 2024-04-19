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
import de.metas.common.util.Check;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModularContractComputingMethodHandlerRegistry
{
	@NonNull
	private final ImmutableList<IComputingMethodHandler> handlers;

	public ModularContractComputingMethodHandlerRegistry(@NonNull final List<IComputingMethodHandler> knownHandlers)
	{
		this.handlers = ImmutableList.copyOf(knownHandlers);
	}

	@NonNull
	public List<IComputingMethodHandler> getApplicableHandlersFor(
			@NonNull final TableRecordReference recordRef,
			@NonNull final LogEntryContractType contractType)
	{
		return handlers.stream()
				.filter(handler -> handler.applies(recordRef, contractType))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public IComputingMethodHandler getApplicableHandlerFor(@NonNull final ComputingMethodType computingMethodType)
	{
		final ImmutableList<IComputingMethodHandler> computingMethodHandlers = handlers.stream()
				.filter(handler -> handler.getComputingMethodType() == computingMethodType)
				.collect(ImmutableList.toImmutableList());

		Check.assumeNotEmpty(computingMethodHandlers, "No computing method found for type {} !", computingMethodType.getCode());
		Check.assume(computingMethodHandlers.size() == 1, "Only one computing method with type {} shall exist!", computingMethodType.getCode());

		return computingMethodHandlers.get(0);
	}
}
