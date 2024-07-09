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

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingProcessedReceipt;
import de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.AbstractDefinitiveInvoiceComputingMethod;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DefinitiveInvoiceForProcessedProductComputingMethod extends AbstractDefinitiveInvoiceComputingMethod
{
	@NonNull private final ManufacturingFacadeService manufacturingFacadeService;
	@NonNull private final ModularContractProvider contractProvider;

	public DefinitiveInvoiceForProcessedProductComputingMethod(@NonNull final ManufacturingFacadeService manufacturingFacadeService,
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodService computingMethodService)
	{
		super(contractProvider, computingMethodService);
		this.contractProvider = contractProvider;
		this.manufacturingFacadeService = manufacturingFacadeService;
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.DefinitiveInvoiceProcessedProduct;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		return super.applies(recordRef, logEntryContractType) || appliesForProductionOrder(recordRef, logEntryContractType);
	}

	private boolean appliesForProductionOrder(final TableRecordReference recordRef, final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		final ManufacturingProcessedReceipt manufacturingProcessedReceipt = manufacturingFacadeService.getManufacturingProcessedReceiptIfApplies(recordRef).orElse(null);
		if (manufacturingProcessedReceipt == null)
		{
			return false;
		}

		return manufacturingFacadeService.isModularOrder(manufacturingProcessedReceipt.getManufacturingOrderId());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		if (recordRef.tableNameEqualsTo(I_PP_Cost_Collector.Table_Name))
		{
			return contractProvider.streamModularPurchaseContractsForPPOrder(ManufacturingFacadeService.getManufacturingReceiptOrIssuedId(recordRef));
		}

		return super.streamContractIds(recordRef);
	}


	protected @NonNull LogEntryDocumentType getSourceLogEntryDocumentType()
	{
		return LogEntryDocumentType.PRODUCTION;
	}

}
