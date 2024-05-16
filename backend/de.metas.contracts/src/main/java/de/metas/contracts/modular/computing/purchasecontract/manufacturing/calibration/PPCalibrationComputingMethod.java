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

package de.metas.contracts.modular.computing.purchasecontract.manufacturing.calibration;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingOrder;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingProcessedReceipt;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingRawIssued;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PPCalibrationComputingMethod implements IComputingMethodHandler
{
	@NonNull private final ManufacturingFacadeService manufacturingFacadeService;
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	@Getter @NonNull ComputingMethodType computingMethodType = ComputingMethodType.ReductionCalibration;

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}

		final ManufacturingProcessedReceipt manufacturingProcessedReceipt = manufacturingFacadeService.getManufacturingProcessedReceiptIfApplies(recordRef).orElse(null);
		final ManufacturingRawIssued manufacturingRawIssued = manufacturingFacadeService.getManufacturingRawIssuedIfApplies(recordRef).orElse(null);
		if (manufacturingProcessedReceipt != null)
		{
			return manufacturingFacadeService.isModularOrder(manufacturingProcessedReceipt.getManufacturingOrderId());
		}
		else if (manufacturingRawIssued != null)
		{
			return manufacturingFacadeService.isModularOrder(manufacturingRawIssued.getManufacturingOrderId());
		}
		return false;
	}

	@Override
	public boolean isApplicableForSettings(final @NonNull TableRecordReference recordRef, final @NonNull ModularContractSettings settings)
	{
		if (settings.getProcessedProductId() == null)
		{
			return false;
		}

		final ManufacturingProcessedReceipt manufacturingProcessedReceipt = manufacturingFacadeService.getManufacturingProcessedReceiptIfApplies(recordRef).orElse(null);
		final ManufacturingRawIssued manufacturingRawIssued = manufacturingFacadeService.getManufacturingRawIssuedIfApplies(recordRef).orElse(null);
		if (manufacturingProcessedReceipt != null)
		{
			final ManufacturingOrder manufacturingOrder = manufacturingFacadeService.getManufacturingOrder(manufacturingProcessedReceipt.getManufacturingOrderId());
			return ProductId.equals(manufacturingOrder.getProcessedProductId(), settings.getProcessedProductId());
		}
		else if (manufacturingRawIssued != null)
		{
			final ManufacturingOrder manufacturingOrder = manufacturingFacadeService.getManufacturingOrder(manufacturingRawIssued.getManufacturingOrderId());
			return ProductId.equals(manufacturingOrder.getProcessedProductId(), settings.getProcessedProductId())
					&& ProductId.equals(manufacturingRawIssued.getRawProductId(), settings.getRawProductId());
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return contractProvider.streamModularPurchaseContractsForPPOrder(ManufacturingFacadeService.getManufacturingReceiptOrIssuedId(recordRef));
	}

	@Override
	public @NonNull ComputingResponse compute(@NonNull final ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.zero(request.getCurrencyId()))
							   .uomId(stockUOMId)
							   .build())
				.qty(computingMethodService.getQtySum(logs, stockUOMId))
				.build();
	}

	@NonNull
	@Override
	public Stream<ProductId> streamContractSpecificPricedProductIds(@NonNull final ModularContractModuleId moduleId)
	{
		return Stream.empty();
	}
}
