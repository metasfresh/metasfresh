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

import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AbstractAverageAVOnShippedQtyComputingMethod extends AbstractComputingMethodHandler
{
	@NonNull protected final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull protected final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final ComputingMethodService computingMethodService;

	// computeAverageAmount with at least 12 digit precision, will be rounded on IC creation according to priceList precision
	protected final CurrencyPrecision precision = CurrencyPrecision.ofInt(12);

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{

		if (recordRef.tableNameEqualsTo(I_M_InOutLine.Table_Name) && logEntryContractType.isModularContractType())
		{
			final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));

			final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
			if (orderId == null)
			{
				return false;
			}
			return inOutRecord.isSOTrx() && !inOutBL.isProformaShipment(inOutRecord);
		}
		return false;
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
				.qty(computingMethodService.getQtySum(logs, stockUOMId))
				.build();
	}
}
