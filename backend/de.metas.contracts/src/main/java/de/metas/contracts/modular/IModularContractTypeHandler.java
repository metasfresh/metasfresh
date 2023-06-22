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
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import lombok.NonNull;

import java.util.Optional;

/**
 * Implementors
 * <ul>
 * <li>should be annotated with {@link org.springframework.stereotype.Component},
 * so that one instance per class is initialized and injected into {@link ModularContractService}.</li>
 * </ul>
 * <p>
 * At this stage I think there will be one implementation for {@link org.compiere.model.I_C_Order},
 * one for {@link de.metas.inout.model.I_M_InOut} and so on.
 * When implementing another handler, please be sure to also add a model interceptor such as {@link de.metas.contracts.modular.interceptor.C_Order}.
 */
public interface IModularContractTypeHandler
{
	/**
	 * Implementations of this method should be very fast. It's OK to return {@code true} even if not 100% sure that there will be a log record coming out of this.
	 */
	boolean probablyAppliesTo(@NonNull Object model, @NonNull ModularContractSettings settings);

	/**
	 * Return a request if the framework shall create the log, or {@link Optional#empty()} otherwise.
	 */
	@NonNull
	Optional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull Object model, @NonNull ModularContractSettings settings);

	/**
	 * Return a request if the framework shall create a reversal-record log record, or {@link Optional#empty()} otherwise.
	 * This method has no {@code settings} parameter because i *think* there are existing log records that have to be reversed independently of settings.
	 */
	@NonNull
	Optional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull Object model);

	/**
	 * Return a request if the framework shall delete the log, or {@link Optional#empty()} otherwise.
	 * This method has no {@code settings} parameter because i *think* there are existing log records that have to be deleted independently of settings.
	 */
	@NonNull
	Optional<LogEntryDeleteRequest> createLogEntryDeleteRequest(Object model);

	/**
	 * The handler's implementation will need to somehow extract the corresponding contract:
	 * <ul>
	 * <li>so it can get the contract-settings and can find out if this handler plays a role in the contract</li>
	 * <li>so it can be put inside the new log record.</li>
	 * </ul>
	 */
	@NonNull
	Optional<FlatrateTermId> getContractId(@NonNull Object model);


}
