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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModularContractComputingMethodHandlerRegistry
{
	@NonNull private static final Logger logger = LogManager.getLogger(ModularContractComputingMethodHandlerRegistry.class);
	@NonNull private final ImmutableMap<ComputingMethodType, IComputingMethodHandler> handlersByType;

	public ModularContractComputingMethodHandlerRegistry(@NonNull final List<IComputingMethodHandler> knownHandlers)
	{
		final List<IComputingMethodHandler> currentHandlers = knownHandlers.stream()
				.filter(this::isNotDeprecated)
				.toList();
		this.handlersByType = Maps.uniqueIndex(currentHandlers, IComputingMethodHandler::getComputingMethodType);
		logger.info("Handlers: {}", this.handlersByType);
	}

	private boolean isNotDeprecated(@NonNull final IComputingMethodHandler iComputingMethodHandler)
	{
		return !iComputingMethodHandler.getClass().isAnnotationPresent(Deprecated.class);
	}

	@NonNull
	public List<IComputingMethodHandler> getApplicableHandlersFor(
			@NonNull final TableRecordReference recordRef,
			@NonNull final LogEntryContractType contractType)
	{
		return handlersByType.values().stream()
				.filter(handler -> handler.applies(recordRef, contractType))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public IComputingMethodHandler getApplicableHandlerFor(@NonNull final ComputingMethodType computingMethodType)
	{
		final IComputingMethodHandler handler = handlersByType.get(computingMethodType);
		if (handler == null)
		{
			throw new AdempiereException("No handler found for " + computingMethodType);
		}
		return handler;
	}
}
