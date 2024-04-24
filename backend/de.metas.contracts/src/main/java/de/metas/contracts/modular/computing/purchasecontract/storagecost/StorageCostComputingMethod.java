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

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
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
		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final List<ModularContractLogEntry> logs = computingMethodService.retrieveLogsForCalculation(request);

		computingMethodService.validateLogs(logs);
		// final Money money = logs.stream()
		// 		.map((log) -> getMoneyToAdd(log, request.getCurrencyId()))
		// 		.reduce(Quantity.zero(stockUOM), Quantity::add);

		return ComputingResponse.builder()
				.ids(ImmutableSet.of())
				.price(ProductPrice.builder()
						.productId(request.getProductId())
						.money(Money.of(BigDecimal.ZERO, request.getCurrencyId()))
						.uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
						.build())
				.qty(Quantity.one(stockUOM))
				.build();
	}

	private @NonNull Money getMoneyToAdd(@NonNull final ModularContractLogEntry logEntry, @NonNull final CurrencyId currencyId)
	{

		return null;
	}
}
