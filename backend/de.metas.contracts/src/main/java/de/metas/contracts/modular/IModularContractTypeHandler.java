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
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.NonNull;

import java.util.stream.Stream;

/**
 * Implementors should be annotated with {@link org.springframework.stereotype.Component},
 * so that one instance per class is initialized and injected into {@link ModularContractHandlerFactory}.
 * <p>
 * At this stage I think there will be one implementation for {@link org.compiere.model.I_C_Order},
 * one for {@link de.metas.inout.model.I_M_InOut} and so on.
 * When implementing another handler, please be sure to also add a model interceptor such as {@link de.metas.contracts.modular.interceptor.C_Order}.
 */
public interface IModularContractTypeHandler<T>
{
	@NonNull
	Class<T> getType();

	boolean applies(@NonNull final T model);

	boolean applies(@NonNull final LogEntryContractType logEntryContractType);

	/**
	 * The handler's implementation will need to somehow extract the corresponding contract(s):
	 * <ul>
	 * <li>so it can get the contract-settings and can find out if this handler plays a role in the contract</li>
	 * <li>so it can be put inside the new log record.</li>
	 * </ul>
	 */
	@NonNull
	Stream<FlatrateTermId> streamContractIds(@NonNull T model);

	void validateDocAction(@NonNull final T model, @NonNull final ModelAction action);

	default void cancelLinkedContractsIfAllowed(@NonNull final T model, @NonNull final FlatrateTermId flatrateTermId) {}

	default void createContractIfRequired(@NonNull final T model) {}

	default void handleAction(@NonNull final T model, @NonNull final ModelAction modelAction, @NonNull final ModularContractService contractService) {}
}
