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

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractRawComputingMethod implements IComputingMethodHandler
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	@Getter @NonNull private final ComputingMethodType computingMethodType;

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType()
				|| !recordRef.tableNameEqualsTo(I_M_InOutLine.Table_Name))
		{
			return false;
		}

		final I_M_InOutLine inOutLineRecord = getReceiptLine(recordRef);
		final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));

		return !inOutRecord.isSOTrx() && !inOutBL.isReversal(inOutRecord);
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return contractProvider.streamModularPurchaseContractsForReceiptLine(getReceiptLineId(recordRef));
	}

	@Override
	public boolean isApplicableForSettings(final @NonNull TableRecordReference recordRef, final @NonNull ModularContractSettings settings)
	{
		final I_M_InOutLine receiptLine = getReceiptLine(recordRef);
		final ProductId receivedProductId = ProductId.ofRepoId(receiptLine.getM_Product_ID());
		return ProductId.equals(receivedProductId, settings.getRawProductId());
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

		final ProductPrice price = logs.getUniqueProductPriceOrErrorNotNull();
		final ProductPrice pricePerStockUOM = computingMethodService.productPriceToUOM(price, qtyInStockUOM.getUomId());

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.price(pricePerStockUOM)
				.qty(qtyInStockUOM)
				.build();
	}

	private static InOutLineId getReceiptLineId(final @NotNull TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId);
	}

	private I_M_InOutLine getReceiptLine(final @NotNull TableRecordReference recordRef)
	{
		final InOutLineId receiptLineId = getReceiptLineId(recordRef);
		return inOutBL.getLineByIdInTrx(receiptLineId);
	}
}
