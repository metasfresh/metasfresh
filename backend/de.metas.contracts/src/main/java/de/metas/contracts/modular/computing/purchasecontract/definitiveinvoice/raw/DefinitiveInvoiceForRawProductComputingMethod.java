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

package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.raw;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.AbstractDefinitiveInvoiceComputingMethod;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DefinitiveInvoiceForRawProductComputingMethod extends AbstractDefinitiveInvoiceComputingMethod
{

	public DefinitiveInvoiceForRawProductComputingMethod(@NonNull final ManufacturingFacadeService manufacturingFacadeService,
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodService computingMethodService)
	{
		super(contractProvider, computingMethodService);
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.DefinitiveInvoiceRawProduct;
	}

}
