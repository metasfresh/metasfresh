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

package de.metas.contracts.modular.computing.purchasecontract;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ContractSpecificPriceRequest;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.salescontract.AbstractShipmentComputingMethod;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.stream.Stream;

public abstract class AbstractShipmentForPurchaseComputingMethod extends AbstractShipmentComputingMethod
{
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;
	@NonNull private final ModularContractPriceService modularContractPriceService;

    protected AbstractShipmentForPurchaseComputingMethod(
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodService computingMethodService,
			@NonNull final ModularContractPriceService modularContractPriceService,
			@NonNull final ComputingMethodType computingMethodType)
    {
        super(contractProvider, computingMethodService, computingMethodType);
        this.contractProvider = contractProvider;
		this.modularContractPriceService = modularContractPriceService;
        this.computingMethodService = computingMethodService;
    }

    @Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return contractProvider.streamModularPurchaseContractsForShipmentLine(getShipmentLineId(recordRef));
	}
	
	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);
		if (logs.isEmpty())
		{
			return computingMethodService.toZeroResponse(request);
		}

		final Quantity qtyInStockUOM = computingMethodService.getQtySumInStockUOM(logs);

		final ProductPrice productPrice = modularContractPriceService.retrievePrice(
				ContractSpecificPriceRequest.builder()
						.modularContractModuleId(request.getModularContractModuleId())
						.flatrateTermId(request.getFlatrateTermId())
						.build()
		).getProductPrice();
		final ProductPrice pricePerStockUOM = computingMethodService.productPriceToUOM(productPrice, qtyInStockUOM.getUomId());

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull())
				.price(pricePerStockUOM)
				.qty(qtyInStockUOM)
				.build();
	}
}
