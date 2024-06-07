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
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractDefinitiveInvoiceComputingMethod implements IComputingMethodHandler
{

	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

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
		final ModularContractLogEntriesList productionLogs = logs.subsetOf(getSourceLogEntryDocumentType());
		final ModularContractLogEntriesList shipmentLogs = logs.subsetOf(LogEntryDocumentType.SHIPMENT);
		final ProductPrice productPrice = logs.getUniqueProductPriceOrErrorNotNull();
		final UomId uomId = productPrice.getUomId();

		final Quantity producedQty = productionLogs.getQtySum(uomId, uomConversionBL);
		final Quantity shippedQty = shipmentLogs.getQtySum(uomId, uomConversionBL);

		final Quantity qtyDifference = shippedQty.subtract(producedQty);
		if (qtyDifference.isZero())
		{
			return ComputingResponse.builder()
					.ids(logs.getIds())
					.price(productPrice.toZero())
					.qty(qtyDifference.toOne())
					.build();
		}
		if (!qtyDifference.isPositive())
		{
			// always ensure the qty is positive and unit price is negative
			// because the price and amt will later be negated in de.metas.invoicecandidate.api.impl.InvoiceLineImpl.negateAmounts
			return ComputingResponse.builder()
					.ids(logs.getIds())
					.price(productPrice.negate())
					.qty(qtyDifference.negate())
					.build();
		}
		else
		{
			return ComputingResponse.builder()
					.ids(logs.getIds())
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

