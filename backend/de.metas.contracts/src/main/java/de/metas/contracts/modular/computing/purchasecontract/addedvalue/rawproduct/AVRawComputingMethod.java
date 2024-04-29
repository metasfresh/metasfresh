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

package de.metas.contracts.modular.computing.purchasecontract.addedvalue.rawproduct;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class AVRawComputingMethod implements IComputingMethodHandler
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, final @NonNull LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType()
				|| !recordRef.tableNameEqualsTo(I_M_InOutLine.Table_Name))
		{
			return false;
		}

		final InOutLineId receiptLineId = getInOutLineId(recordRef);
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(receiptLineId);
		final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));

		return !inOutRecord.isSOTrx() && !inOutBL.isReversal(inOutRecord);
	}

	private static InOutLineId getInOutLineId(final @NotNull TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId);
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return contractProvider.streamModularPurchaseContractsForReceiptLine(getInOutLineId(recordRef));
	}

	@Override
	public boolean isContractIdEligible(
			final @NonNull TableRecordReference recordRef,
			final @NonNull FlatrateTermId contractId,
			final @NonNull ModularContractSettings settings)
	{
		final InOutLineId receiptLineId = getInOutLineId(recordRef);
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(receiptLineId);

		return settings.getRawProductId().getRepoId() == inOutLineRecord.getM_Product_ID();
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.AddValueOnRawProduct;
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
}
