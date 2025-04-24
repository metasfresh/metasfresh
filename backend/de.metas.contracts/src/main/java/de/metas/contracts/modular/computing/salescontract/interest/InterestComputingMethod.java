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

package de.metas.contracts.modular.computing.salescontract.interest;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractShipmentComputingMethod;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.Money;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InterestComputingMethod extends AbstractShipmentComputingMethod
{
	// use low precision in Logs to be more accurate on aggregation of logs on IC creation with lower precision
	protected final CurrencyPrecision precision = CurrencyPrecision.ofInt(12);

	@NonNull final ComputingMethodService computingMethodService;

	public InterestComputingMethod(
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodService computingMethodService)
	{
		super(contractProvider, computingMethodService, ComputingMethodType.SalesInterest);
		this.computingMethodService = computingMethodService;
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
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());

		final Money money = logs.computePricePerQtyUnit()
				.orElseGet(() -> Money.zero(request.getCurrencyId()));

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull())
				.price(ProductPrice.builder()
						.productId(request.getProductId())
						.money(money)
						.uomId(stockUOMId)
						.build())
				.qty(qtyInStockUOM)
				.build();
	}
}
