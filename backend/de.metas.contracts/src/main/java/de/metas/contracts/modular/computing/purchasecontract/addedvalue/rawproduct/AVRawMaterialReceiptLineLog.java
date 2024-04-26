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

package de.metas.contracts.modular.computing.purchasecontract.addedvalue.rawproduct;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.contracts.modular.workpackage.impl.AbstractMaterialReceiptLogHandler;
import de.metas.product.ProductPrice;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AVRawMaterialReceiptLineLog extends AbstractMaterialReceiptLogHandler
{
	@NonNull @Getter private final AVRawComputingMethod computingMethod;

	public AVRawMaterialReceiptLineLog(
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final AVRawComputingMethod computingMethod,
			@NonNull final ModularContractService modularContractService)
	{
		super(modCntrInvoicingGroupRepository, modularContractService);
		this.computingMethod = computingMethod;
	}

	@NonNull
	@Override
	protected ProductPrice getPriceActual(@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		return modularContractService.getContractSpecificPrice(request.getModularContractModuleId(), request.getContractId())
				.negateIf(request.isCostsType());
	}
}
