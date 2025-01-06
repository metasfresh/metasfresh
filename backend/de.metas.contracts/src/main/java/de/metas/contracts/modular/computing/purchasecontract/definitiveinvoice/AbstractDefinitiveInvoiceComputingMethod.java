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

package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ContractSpecificPriceRequest;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractComputingMethodHandler;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.PURCHASE_SALES_METHODS;

@RequiredArgsConstructor
public abstract class AbstractDefinitiveInvoiceComputingMethod extends AbstractComputingMethodHandler
{
	@NonNull private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	@NonNull private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;
	@NonNull private final ModularContractPriceService modularContractPriceService;

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		if (recordRef.tableNameEqualsTo(I_M_InOutLine.Table_Name))
		{
			final I_M_InOutLine inOutLineRecord = inoutDao.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
			return !(inOutLineRecord.getMovementQty().signum() < 0) && orderId != null;
		}
		if (recordRef.tableNameEqualsTo(I_M_InventoryLine.Table_Name))
		{
			final I_M_Inventory inventory = getInventory(recordRef)
					.orElseThrow(() -> new AdempiereException("No M_Inventory found for line=" + recordRef));

			return !inventoryBL.isReversal(inventory);
		}
		if (recordRef.tableNameEqualsTo(I_C_InvoiceLine.Table_Name))
		{
			return computingMethodService.isFinalInvoiceLineForComputingMethod(
					InvoiceLineId.ofRepoId(recordRef.getRecord_ID()),
					PURCHASE_SALES_METHODS);
		}

		return false;

	}

	private @NonNull Optional<I_M_Inventory> getInventory(final @NonNull TableRecordReference recordRef)
	{
		return Optional.of(recordRef)
				.map(lineRef -> lineRef.getIdAssumingTableName(I_M_InventoryLine.Table_Name, InventoryLineId::ofRepoId))
				.map(inventoryBL::getLineById)
				.map(I_M_InventoryLine::getM_Inventory_ID)
				.map(InventoryId::ofRepoId)
				.map(inventoryBL::getById);
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name -> getContractStreamForInOut(recordRef);
			case I_M_InventoryLine.Table_Name -> contractProvider.streamModularPurchaseContractsForInventory(InventoryLineId.ofRepoId(recordRef.getRecord_ID()));
			case I_C_InvoiceLine.Table_Name -> contractProvider.streamModularPurchaseContractsForInvoiceLine(InvoiceLineId.ofRepoId(recordRef.getRecord_ID()));
			default -> Stream.empty();
		};
	}

	private @NonNull Stream<FlatrateTermId> getContractStreamForInOut(final @NonNull TableRecordReference recordRef)
	{
		final InOutLineId inoutLineId = InOutLineId.ofRepoId(recordRef.getRecord_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(inoutDao.getByLineIdInTrx(inoutLineId).isSOTrx());
		if (soTrx.isSales())
		{
			return contractProvider.streamModularPurchaseContractsForShipmentLine(inoutLineId);
		}
		else
		{
			return contractProvider.streamModularPurchaseContractsForReceiptLine(inoutLineId);
		}
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);
		final ModularContractLogEntriesList shipmentLogs = logs.subsetOf(LogEntryDocumentType.SHIPMENT);
		final ModularContractLogEntriesList invoiceLineLogs = logs.subsetOf(LogEntryDocumentType.FINAL_INVOICE);
		final ProductId productId = request.getProductId();
		final UomId uomId = productBL.getStockUOMId(productId);
		final ProductPrice productPrice = modularContractPriceService.retrievePrice(
				ContractSpecificPriceRequest.builder()
						.modularContractModuleId(request.getModularContractModuleId())
						.flatrateTermId(request.getFlatrateTermId())
						.build()
		).getProductPrice();

		final Quantity invoicedQty = invoiceLineLogs.getQtySum(uomId, uomConversionBL);
		final Quantity shippedQty = shipmentLogs.getQtySum(uomId, uomConversionBL);

		final Quantity qtyDifference = shippedQty.subtract(invoicedQty);
		final ComputingResponse.ComputingResponseBuilder responseBuilder = ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull());

		final boolean proformaLogsExists = !logs.subsetOf(LogEntryDocumentType.PROFORMA_SHIPMENT).isEmpty();
		if (qtyDifference.isZero() && proformaLogsExists)
		{
			return responseBuilder
					.price(productPrice.toZero())
					.qty(qtyDifference.toOne()) // with 0 no IC would be created, but we want to have it on the invoice, if other corrections exist
					.build();
		}
		if (!qtyDifference.isPositive())
		{
			// always ensure the qty is positive and unit price is negative
			// because the price and amt will later be negated in de.metas.invoicecandidate.api.impl.InvoiceLineImpl.negateAmounts
			return responseBuilder
					.price(productPrice.negate())
					.qty(qtyDifference.negate())
					.build();
		}
		else
		{
			return responseBuilder
					.price(productPrice)
					.qty(qtyDifference)
					.build();
		}
	}

	protected @NonNull LogEntryDocumentType getSourceLogEntryDocumentType()
	{
		return LogEntryDocumentType.MATERIAL_RECEIPT;
	}

}

