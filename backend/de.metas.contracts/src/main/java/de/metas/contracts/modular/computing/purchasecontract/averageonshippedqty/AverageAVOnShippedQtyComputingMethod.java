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

package de.metas.contracts.modular.computing.purchasecontract.averageonshippedqty;

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
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class AverageAVOnShippedQtyComputingMethod implements IComputingMethodHandler
{
	@NonNull
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull
	private final ModularContractProvider contractProvider;
	@NonNull
	private final ComputingMethodService computingMethodService;

	private final CurrencyPrecision precision = CurrencyPrecision.ofInt(4);

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{

		if (recordRef.tableNameEqualsTo(I_M_InOutLine.Table_Name) && logEntryContractType.isModularContractType())
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
		return recordRef.getIdIfTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId)
				.map(contractProvider::streamModularPurchaseContractsForShipmentLine)
				.orElseGet(Stream::empty);
	}

	@Override
	public boolean isApplicableForSettings(final @NonNull TableRecordReference recordRef, final @NonNull ModularContractSettings settings)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId));
		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

		return ProductId.equals(productId, settings.getProcessedProductId()) || ProductId.equals(productId, settings.getRawProductId());
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{

		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);
		if (logs.isEmpty())
		{
			return computingMethodService.toZeroResponse(request);
		}

		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());

		final Money money = computeAverageAmount(logs, request.getCurrencyId())
				.orElseGet(() -> Money.zero(request.getCurrencyId()));

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.price(ProductPrice.builder()
						.productId(request.getProductId())
						.money(money)
						.uomId(stockUOMId)
						.build())
				.qty(computingMethodService.getQtySum(logs, stockUOMId))
				.build();
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.AverageAddedValueOnShippedQuantity;
	}

	public Optional<Money> computeAverageAmount(@NonNull final ModularContractLogEntriesList logs,
												@NonNull final CurrencyId currencyId)
	{
		final Optional<Money> totalMoney = logs.stream()
				.map(ModularContractLogEntry::getAmount)
				.filter(Objects::nonNull)
				.reduce(Money::add);

		final Optional<Quantity> totalQuantity = logs.stream()
				.map(ModularContractLogEntry::getQuantity)
				.filter(Objects::nonNull)
				.reduce(Quantity::add);

		if (totalMoney.isEmpty() || totalQuantity.isEmpty())
		{
			return Optional.empty();
		}

		final Money weightedAvgMoney = totalMoney.get().divide(totalQuantity.get().toBigDecimal(), precision);
		return Optional.of(weightedAvgMoney);
	}

}
