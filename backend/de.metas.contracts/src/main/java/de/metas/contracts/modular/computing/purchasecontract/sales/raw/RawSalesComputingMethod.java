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

package de.metas.contracts.modular.computing.purchasecontract.sales.raw;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractRawComputingMethod;
import de.metas.contracts.modular.computing.ComputingMethodService;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RawSalesComputingMethod extends AbstractRawComputingMethod
{

	public RawSalesComputingMethod(
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodService computingMethodService)
	{
		super(contractProvider, computingMethodService, ComputingMethodType.SalesOnRawProduct);
	}
}
