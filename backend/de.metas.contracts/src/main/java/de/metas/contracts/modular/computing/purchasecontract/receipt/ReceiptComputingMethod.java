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
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractModuleId;
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
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.Receipt;

@Component
@RequiredArgsConstructor
public class ReceiptComputingMethod implements IComputingMethodHandler
{
	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ComputingMethodService computingMethodService;
	@NonNull private final ModularContractProvider contractProvider;

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return Receipt;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, final @NonNull LogEntryContractType contractType)
	{
		if (!contractType.isModularContractType())
		{
			return false;
		}

		switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				final I_M_InOutLine inOutLineRecord = inoutDao.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
				final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
				final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
				return SOTrx.ofBoolean(inOutRecord.isSOTrx()).isPurchase() && !(inOutLineRecord.getMovementQty().signum() < 0) && orderId != null;
			}
			case I_C_OrderLine.Table_Name ->
			{
				final I_C_Order orderRecord = orderBL.getById(orderLineBL.getOrderIdByOrderLineId(OrderLineId.ofRepoId(recordRef.getRecord_ID())));
				return SOTrx.ofBoolean(orderRecord.isSOTrx()).isPurchase();
			}
			case I_C_Flatrate_Term.Table_Name ->
			{
				final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
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
			default -> {return false;}
		}
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				return contractProvider.streamModularPurchaseContractsForReceiptLine(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			}
			case I_C_OrderLine.Table_Name ->
			{
				return contractProvider.streamModularPurchaseContractsForPurchaseOrderLine(OrderLineId.ofRepoId(recordRef.getRecord_ID()));
			}
			case I_C_Flatrate_Term.Table_Name ->
			{
				return Stream.of(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
			}
			default -> {return Stream.empty();}
		}
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

	@Override
	public @NonNull Stream<ProductId> streamContractSpecificPricedProductIds(@NonNull final ModularContractModuleId moduleId)
	{
		return Stream.empty();
	}

}
