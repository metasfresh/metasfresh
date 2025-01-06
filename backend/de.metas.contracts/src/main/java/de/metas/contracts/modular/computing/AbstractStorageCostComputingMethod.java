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

package de.metas.contracts.modular.computing;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.Optional;

public abstract class AbstractStorageCostComputingMethod extends AbstractShipmentComputingMethod
{
	@NonNull protected final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ComputingMethodService computingMethodService;

    protected AbstractStorageCostComputingMethod(
			@NonNull final ComputingMethodService computingMethodService,
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodType computingMethodType
	)
    {
        super(contractProvider, computingMethodService, computingMethodType);
        this.computingMethodService = computingMethodService;
    }

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);

		final ProductPrice pricePerUnitPerDay = getPricePerUnitPerDay(logs).orElse(null);
		if (pricePerUnitPerDay == null)
		{
			return computingMethodService.toZeroResponse(request);
		}
		Check.assumeEquals(request.getCurrencyId(), pricePerUnitPerDay.getCurrencyId(), "Log and Invoice Currency should be the same");

		final Money storageCosts = computeStorageCosts(logs, pricePerUnitPerDay);

		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());
		final ProductPrice priceWithStockUOM = ProductPrice.builder()
				.productId(request.getProductId())
				.money(storageCosts)
				.uomId(stockUOMId)
				.build();

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull())
				.price(priceWithStockUOM)
				.qty(storageCosts.isZero() ? Quantitys.zero(stockUOMId) : Quantitys.one(stockUOMId))
				.build();
	}

	private Optional<ProductPrice> getPricePerUnitPerDay(@NonNull final ModularContractLogEntriesList logs)
	{
		final ProductPrice productPrice = logs.getUniqueProductPriceOrError().orElse(null);
		if (productPrice == null)
		{
			return Optional.empty();
		}
		return Optional.of(productPrice);
	}

	private Money computeStorageCosts(
			@NonNull final ModularContractLogEntriesList logs,
			@NonNull final ProductPrice pricePerUnitPerDay)
	{
		final Quantity qty = computingMethodService.getQtyXStorageDaysSum(logs, pricePerUnitPerDay.getUomId());
		return pricePerUnitPerDay.computeAmount(qty);
	}
}
