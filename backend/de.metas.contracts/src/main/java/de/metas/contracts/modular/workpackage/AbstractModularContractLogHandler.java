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

package de.metas.contracts.modular.workpackage;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.product.ProductPrice;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public abstract class AbstractModularContractLogHandler implements IModularContractLogHandler
{
	@NonNull private final ModularContractService modularContractService;

	@Nullable
	public ProductPrice getPriceActual(final @NonNull ModularContractLogEntry logEntry)
	{
		Check.assumeNotNull(logEntry.getPriceActual(), "PriceActual shouldn't be null");
		final boolean isCostsType = modularContractService.getByModuleId(logEntry.getModularContractModuleId()).isCostsType();
		return logEntry.getPriceActual().negateIf(isCostsType);
	}

	@NonNull
	protected ProductPrice getPriceActual(@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		return modularContractService.getContractSpecificPrice(request.getModularContractModuleId(), request.getContractId())
				.negateIf(request.isCostsType());
	}
}
