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

package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.averageonshippedqty;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractAverageAVOnShippedQtyComputingMethod;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DefinitiveInvoiceAverageAVOnShippedQtyComputingMethod extends AbstractAverageAVOnShippedQtyComputingMethod
{
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	@NonNull @Getter ComputingMethodType computingMethodType = ComputingMethodType.DefinitiveInvoiceAverageAVOnShippedQty;

	public DefinitiveInvoiceAverageAVOnShippedQtyComputingMethod(
			@NonNull final ComputingMethodService computingMethodService,
			@NonNull final ModularContractProvider contractProvider
	)
	{
		super(computingMethodService);
		this.contractProvider = contractProvider;
		this.computingMethodService = computingMethodService;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{

		if (recordRef.tableNameEqualsTo(I_C_InvoiceLine.Table_Name) && logEntryContractType.isModularContractType())
		{
			return computingMethodService.isFinalInvoiceLineForComputingMethod(
					InvoiceLineId.ofRepoId(recordRef.getRecord_ID()),
					ComputingMethodType.PurchaseAverageAddedValueOnShippedQuantity);
		}
		return super.applies(recordRef, logEntryContractType);
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name -> contractProvider.streamModularPurchaseContractsForShipmentLine(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			case I_C_InvoiceLine.Table_Name -> contractProvider.streamModularPurchaseContractsForInvoiceLine(InvoiceLineId.ofRepoId(recordRef.getRecord_ID()));
			default -> Stream.empty();
		};
	}

	@Override
	public boolean isApplicableForSettings(final @NonNull TableRecordReference recordRef, final @NonNull ModularContractSettings settings)
	{
		switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId));
				final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

				return ProductId.equals(productId, settings.getProcessedProductId()) || ProductId.equals(productId, settings.getRawProductId()) && settings.getSoTrx().isPurchase();
			}
			case I_C_InvoiceLine.Table_Name ->
			{
				return settings.getSoTrx().isPurchase();
			}
			default ->
			{
				return false;
			}
		}
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

		final ModularContractLogEntriesList shipmentLogs = logs.subsetOf(LogEntryDocumentType.SHIPMENT);
		final Money definitiveTotalAmount = shipmentLogs.getAmountSum()
				.orElseGet(() -> Money.zero(request.getCurrencyId()));

		final ModularContractLogEntriesList invoiceLineLogs = logs.subsetOf(LogEntryDocumentType.FINAL_INVOICE);
		final Money finalTotalAmount = invoiceLineLogs.getAmountSum()
				.orElseGet(() -> Money.zero(request.getCurrencyId()));

		final Money diffAmount = definitiveTotalAmount.subtract(finalTotalAmount);
		if(diffAmount.isZero())
		{
			return computingMethodService.toZeroResponse(request);
		}

		final Quantity definitiveTotalQty = computingMethodService.getQtySum(shipmentLogs, stockUOMId);
		final Quantity finalTotalQty = computingMethodService.getQtySum(invoiceLineLogs, stockUOMId);
		final Quantity diffQty = definitiveTotalQty.subtract(finalTotalQty);
		Check.assume(diffQty.isPositive(), "Qty diff should be positive at this point");

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull())
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(diffAmount.divide(diffQty.toBigDecimal(), precision))
							   .uomId(stockUOMId)
							   .build())
				.qty(diffQty)
				.build();
	}
}
