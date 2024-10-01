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

package de.metas.contracts.modular.computing.purchasecontract.storagecost;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ProductPriceWithFlags;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.workpackage.impl.AbstractShipmentLogHandler;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
class ShipmentLineLog extends AbstractShipmentLogHandler
{
	public ShipmentLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final StorageCostComputingMethod computingMethod)
	{
		super(modularContractService, modCntrInvoicingGroupRepository, computingMethod);
	}

	@Override
	public Integer computeStorageDays(
			final @NotNull CreateLogRequest createLogRequest,
			final @NonNull LocalDateAndOrgId transactionDate,
			final @Nullable LocalDateAndOrgId physicalClearanceDate)
	{
		final LocalDateAndOrgId storageDate = createLogRequest.getModularContractSettings().getStorageCostStartDate();
		final int daysBetween = (int)LocalDateAndOrgId.daysBetween(storageDate, transactionDate);
		return Math.max(daysBetween, 0);
	}

	@NonNull
	public Money computeAmount(
			final @NotNull ProductPrice contractSpecificPrice,
			final @NonNull Quantity quantity,
			final @Nullable Integer storageDays,
			final @NonNull QuantityUOMConverter uomConverter)
	{
		Check.assumeNotNull(storageDays, "StorageDays shouldn't be null");
		return contractSpecificPrice.computeAmount(quantity.multiply(storageDays), uomConverter);
	}

	@Override
	protected SOTrx getSOTrx()
	{
		return SOTrx.PURCHASE;
	}

	@Override
	public @NonNull ModularContractLogEntry calculateAmountWithNewPrice(
			final @NonNull ModularContractLogEntry logEntry,
			final @NonNull ProductPriceWithFlags newPrice,
			final @NonNull QuantityUOMConverter uomConverter)
	{
		final Quantity qty = Check.assumeNotNull(logEntry.getQuantity(), "Quantity shouldn't be null");
		final int storageDays = Check.assumeNotNull(logEntry.getStorageDays(), "StorageDays shouldn't be null");
		final ProductPrice price = newPrice.toProductPrice();
		return logEntry.toBuilder()
				.priceActual(price)
				.amount(computeAmount(price, qty, storageDays, uomConverter))
				.build();
	}
}
