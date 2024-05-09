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

package de.metas.contracts.modular.computing.purchasecontract.sales.processed;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ProcessedSalesComputingMethod implements IComputingMethodHandler
{
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	@Override
	public @NonNull ComputingMethodType getComputingMethodType() {return ComputingMethodType.SalesOnProcessedProduct;}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType()
				|| !recordRef.tableNameEqualsTo(I_PP_Order.Table_Name))
		{
			return false;
		}

		final PPOrderId ppOrderId = getPPOrderId(recordRef);
		return ppOrderBL.isModularOrder(ppOrderId);
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		if (recordRef.tableNameEqualsTo(I_PP_Order.Table_Name))
		{
			return contractProvider.streamModularContractsForPPOrder(getPPOrderId(recordRef));
		}
		else
		{
			return Stream.empty();
		}
	}

	@Override
	public boolean isContractIdEligible(
			final @NonNull TableRecordReference recordRef,
			final @NonNull FlatrateTermId contractId,
			final @NonNull ModularContractSettings settings)
	{
		if (settings.getProcessedProductId() == null)
		{
			return false;
		}

		final PPOrderId ppOrderId = getPPOrderId(recordRef);
		final I_PP_Order orderRecord = ppOrderBL.getById(ppOrderId);
		final ProductId processedProductId = ProductId.ofRepoId(orderRecord.getM_Product_ID());
		return ProductId.equals(settings.getProcessedProductId(), processedProductId);
	}

	private static PPOrderId getPPOrderId(final @NonNull TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId);
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);
		if (logs.isEmpty())
		{
			return computingMethodService.toZeroResponse(request);
		}

		final ProductPrice price = logs.getUniqueProductPriceOrErrorNotNull();
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.price(computingMethodService.productPriceToUOM(price, stockUOMId))
				.qty(computingMethodService.getQtySumInStockUOM(logs))
				.build();
	}
}
