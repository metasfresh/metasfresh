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

package de.metas.contracts.modular.computing.purchasecontract.interim;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.contracts.modular.workpackage.impl.AbstractMaterialReceiptLogHandler;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

@Component
class MaterialReceiptLineLog extends AbstractMaterialReceiptLogHandler
{
	public MaterialReceiptLineLog(
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ModularContractService contractService,
			@NonNull final InterimComputingMethod computingMethod)
	{
		super(modCntrInvoicingGroupRepository, contractService, computingMethod);
	}

	@Override
	public @NonNull LogEntryContractType getLogEntryContractType()
	{
		return LogEntryContractType.INTERIM;
	}

	@Override
	@NonNull
	protected ProductId getProductId(
			@NonNull final IModularContractLogHandler.CreateLogRequest request,
			@NonNull final I_M_InOutLine receiptLineRecord)
	{
		return ProductId.ofRepoId(receiptLineRecord.getM_Product_ID());
	}
}
