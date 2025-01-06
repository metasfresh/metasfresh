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

package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.processed;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingProcessedReceipt;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.contracts.modular.workpackage.impl.AbstractManufacturingProcessedReceiptLogHandler;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ManufacturingOrderLog extends AbstractManufacturingProcessedReceiptLogHandler
{
	public ManufacturingOrderLog(
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ModularContractService modularContractService,
			@NonNull final ManufacturingFacadeService manufacturingFacadeService,
			@NonNull final DefinitiveInvoiceForProcessedProductComputingMethod computingMethod)
	{
		super(modCntrInvoicingGroupRepository, manufacturingFacadeService, modularContractService, computingMethod);
	}

	@Override
	public boolean applies(@NonNull final CreateLogRequest request)
	{
		final ManufacturingProcessedReceipt manufacturingProcessedReceipt = manufacturingFacadeService.getManufacturingProcessedReceiptIfApplies(request.getRecordRef()).orElse(null);
		return manufacturingProcessedReceipt != null
				&& ProductId.equals(manufacturingProcessedReceipt.getProcessedProductId(), request.getProductId());
	}

	@NonNull
	protected ProductId extractProductIdToLog(
			@NonNull final IModularContractLogHandler.CreateLogRequest request,
			@NonNull final ManufacturingProcessedReceipt manufacturingProcessedReceipt)
	{
		return manufacturingProcessedReceipt.getProcessedProductId();
	}

}
