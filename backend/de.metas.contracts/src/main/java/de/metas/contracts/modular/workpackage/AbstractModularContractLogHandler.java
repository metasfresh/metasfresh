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

import de.metas.contracts.modular.ContractSpecificPriceRequest;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ProductPriceWithFlags;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.product.ProductPrice;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractModularContractLogHandler implements IModularContractLogHandler
{
	@NonNull
	private final ModularContractService modularContractService;

	@NonNull
	public final ProductPriceWithFlags getPriceActualWithFlags(
			final @NonNull ProductPrice productPrice,
			final @NonNull ModularContractLogEntry logEntry)
	{
		return ProductPriceWithFlags.builder()
				.price(productPrice)
				.isCost(modularContractService.getByModuleId(logEntry.getModularContractModuleId()).isCostsType())
				.isSubtractedValue(getComputingMethod().getComputingMethodType().isSubtractedValue())
				.build();
	}

	@NonNull
	protected ProductPriceWithFlags getContractSpecificPriceWithFlags(@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		return ProductPriceWithFlags.builder()
				.price(getContractSpecificPrice(request))
				.isCost(request.isCostsType())
				.isSubtractedValue(getComputingMethod().getComputingMethodType().isSubtractedValue())
				.build();
	}

	@NonNull
	private ProductPrice getContractSpecificPrice(@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		return modularContractService.getContractSpecificPrice(ContractSpecificPriceRequest.builder()
																	   .modularContractModuleId(request.getModularContractModuleId())
																	   .flatrateTermId(request.getContractId())
																	   .build());
	}
}
