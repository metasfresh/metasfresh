/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractComputingMethodHandlerRegistry;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.stream.Stream;

/**
 * Implementors should be annotated with {@link org.springframework.stereotype.Component},
 * so that one instance per class is initialized and injected into {@link ModularContractComputingMethodHandlerRegistry}.
 * <p>
 * When implementing another handler, please be sure to also add a model interceptor such as {@link de.metas.contracts.modular.interceptor.C_Order}.
 */
public interface IComputingMethodHandler
{
	@NonNull
	ComputingMethodType getComputingMethodType();

	boolean applies(@NonNull final TableRecordReference recordRef, @NonNull final LogEntryContractType contractType);

	default boolean isApplicableForSettings(@NonNull final TableRecordReference recordRef, @NonNull final ModularContractSettings settings)
	{
		return true;
	}

	/**
	 * The handler's implementation will need to somehow extract the corresponding contract(s):
	 * <ul>
	 * <li>so it can get the contract-settings and can find out if this handler plays a role in the contract</li>
	 * <li>so it can be put inside the new log record.</li>
	 * </ul>
	 */
	@NonNull
	Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef);

	@NonNull
	ComputingResponse compute(@NonNull ComputingRequest request);

	@NonNull
	Stream<ProductId> streamContractSpecificPricedProductIds(@NonNull ModularContractModuleId moduleId);
}
