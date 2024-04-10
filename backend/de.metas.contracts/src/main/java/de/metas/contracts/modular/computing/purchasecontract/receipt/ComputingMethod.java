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

package de.metas.contracts.modular.computing.purchasecontract.receipt;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.CalculationRequest;
import de.metas.contracts.modular.computing.CalculationResponse;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.IModularContractComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.RECEIPT;

@Component
@RequiredArgsConstructor
public class ComputingMethod implements IModularContractComputingMethodHandler
{
	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ComputingMethodService computingMethodService;
	@NonNull private final ModularContractProvider contractProvider;

	@Override
	public boolean applies(final @NonNull TableRecordReference tableRecordReference, final @NonNull LogEntryContractType contractType)
	{
		switch (tableRecordReference.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				if (!contractType.isModularOrInterim()) { return false; }
				final I_M_InOutLine inOutLineRecord = inoutDao.getLineByIdInTrx(InOutLineId.ofRepoId(tableRecordReference.getRecord_ID()));
				final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
				final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
				return SOTrx.ofBoolean(inOutRecord.isSOTrx()).isPurchase() && !(inOutLineRecord.getMovementQty().signum() < 0) && orderId != null;
			}
			case I_C_OrderLine.Table_Name ->
			{
				if (!contractType.isModularContractType()) { return false; }
				final I_C_Order orderRecord = orderBL.getById(orderLineBL.getOrderIdByOrderLineId(OrderLineId.ofRepoId(tableRecordReference.getRecord_ID())));
				return SOTrx.ofBoolean(orderRecord.isSOTrx()).isPurchase();
			}
			case I_C_Flatrate_Term.Table_Name ->
			{
				if (!contractType.isModularOrInterim()) { return false; }
				final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(tableRecordReference.getRecord_ID()));
				if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularContractType())
				{
					return false;
				}

				final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(flatrateTermRecord.getC_OrderLine_Term_ID());
				if (orderLineId == null)
				{
					return false;
				}

				final OrderId orderId = orderLineBL.getOrderIdByOrderLineId(orderLineId);
				return SOTrx.ofBoolean(orderBL.getById(orderId).isSOTrx()).isPurchase();
			}
			default -> { return false; }
		}
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference tableRecordReference)
	{
		switch (tableRecordReference.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				return contractProvider.streamModularPurchaseContractsForReceiptLine(InOutLineId.ofRepoId(tableRecordReference.getRecord_ID()));
			}
			case I_C_OrderLine.Table_Name ->
			{
				return contractProvider.streamModularPurchaseContractsForPurchaseOrderLine(OrderLineId.ofRepoId(tableRecordReference.getRecord_ID()));
			}
			case I_C_Flatrate_Term.Table_Name ->
			{
				return Stream.of(FlatrateTermId.ofRepoId(tableRecordReference.getRecord_ID()));
			}
			default -> { return Stream.empty(); }
		}
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return RECEIPT;
	}

	@Override
	public @Nullable CalculationResponse calculate(@NonNull final CalculationRequest request)
	{
		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final Quantity qty = Quantity.of(BigDecimal.ZERO, stockUOM);
		final List<ModularContractLogEntry> logs = computingMethodService.retrieveLogsForCalculation(request, LogEntryContractType.MODULAR_CONTRACT);

		computingMethodService.validateLogs(logs);
		logs.forEach((log) -> qty.add(computingMethodService.getQtyToAdd(log, request.getProductId())));

		return CalculationResponse.builder()
				.ids(logs.stream().map(ModularContractLogEntry::getId).collect(Collectors.toSet()))
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.of(BigDecimal.ZERO, request.getCurrencyId()))
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}

	@Override
	public @NonNull CalculationResponse calculateForInterim(@NonNull final CalculationRequest request)
	{
		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final Quantity qty = Quantity.of(BigDecimal.ZERO, stockUOM);
		final List<ModularContractLogEntry> logs = computingMethodService.retrieveLogsForCalculation(request, LogEntryContractType.INTERIM);

		computingMethodService.validateLogs(logs);
		logs.forEach((log) -> qty.add(computingMethodService.getQtyToAdd(log, request.getProductId())));

		return CalculationResponse.builder()
				.ids(logs.stream().map(ModularContractLogEntry::getId).collect(Collectors.toSet()))
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.of(BigDecimal.ZERO, request.getCurrencyId()))
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}
}
