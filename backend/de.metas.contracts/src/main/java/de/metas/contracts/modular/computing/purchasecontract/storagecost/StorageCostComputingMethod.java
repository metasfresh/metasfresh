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

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class StorageCostComputingMethod implements IComputingMethodHandler
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.StorageCost;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (recordRef.getTableName().equals(I_M_InOutLine.Table_Name) && logEntryContractType.isModularContractType())
		{
			final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
			final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
			if (orderId == null)
			{
				return false;
			}
			return inOutRecord.isSOTrx();
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		if (recordRef.getTableName().equals(I_M_InOutLine.Table_Name))
		{
			return contractProvider.streamModularPurchaseContractsForShipmentLine(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
		}
		return Stream.empty();
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);

		final ProductPrice logProductPrice = logs.getUniqueProductPriceOrError().orElse(null);

		final Money price;
		final UomId initialStockUOMId;
		if (logProductPrice != null)
		{
			initialStockUOMId = productBL.getStockUOMId(logProductPrice.getProductId());
			Check.assumeEquals(request.getCurrencyId(), logProductPrice.getCurrencyId(), "Log and Invoice Currency should be the same");
			Check.assumeEquals(initialStockUOMId, logProductPrice.getUomId(), "Log Price UOM and initial product stockUOM should be the same");
			price = logProductPrice.toMoney();
		}
		else
		{
			return computingMethodService.toZeroResponseWithQtyOne(request);
		}

		final Money money = logs.stream()
				.map((log) -> getMoneyToAdd(log, price, initialStockUOMId))
				.reduce(Money.of(BigDecimal.ZERO, request.getCurrencyId()), Money::add);

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.price(ProductPrice.builder()
						.productId(request.getProductId())
						.money(money)
						.uomId(stockUOMId)
						.build())
				.qty(Quantitys.createOne(stockUOMId))
				.build();
	}

	private @NonNull Money getMoneyToAdd(
			@NonNull final ModularContractLogEntry logEntry,
			@NonNull final Money price,
	        @NonNull final UomId initialStockUOMId)
	{
		final Quantity qty = logEntry.getQuantity();
		Check.assumeNotNull(qty, "Log qty shouldn't be null");
		Check.assumeEquals(qty.getUomId(), initialStockUOMId, "Log Price UOM and initial product stockUOM should be the same");

		Check.assumeNotNull(logEntry.getStorageDays(), "StorageDays shouldn't be null");
		final BigDecimal storageDays = BigDecimal.valueOf(logEntry.getStorageDays());

		return price.multiply(qty.toBigDecimal()).multiply(storageDays);
	}
}
