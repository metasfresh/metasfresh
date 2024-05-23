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

package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.processed;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingFacadeService;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingOrder;
import de.metas.contracts.modular.computing.facades.manufacturing.ManufacturingProcessedReceipt;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DefinitiveInvoiceForProcessedProductComputingMethod implements IComputingMethodHandler
{
	@NonNull private final ManufacturingFacadeService manufacturingFacadeService;
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ComputingMethodService computingMethodService;

	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);


	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.DefinitiveInvoiceProcessedProduct;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		if (recordRef.tableNameEqualsTo(I_M_InOutLine.Table_Name))
		{
			final I_M_InOut inOut = getInOut(recordRef)
					.orElseThrow(() -> new AdempiereException("No M_InOut found for line=" + recordRef));

			return !inOutBL.isReversal(inOut) && inOut.isSOTrx();
		}
		if (recordRef.tableNameEqualsTo(I_M_InventoryLine.Table_Name))
		{
			final I_M_Inventory inventory = getInventory(recordRef)
					.orElseThrow(() -> new AdempiereException("No M_Inventory found for line=" + recordRef));

			return !inventoryBL.isReversal(inventory);
		}

		final ManufacturingProcessedReceipt manufacturingProcessedReceipt = manufacturingFacadeService.getManufacturingProcessedReceiptIfApplies(recordRef).orElse(null);
		if (manufacturingProcessedReceipt == null)
		{
			return false;
		}

		return manufacturingFacadeService.isModularOrder(manufacturingProcessedReceipt.getManufacturingOrderId());
	}

	private @NonNull Optional<I_M_InOut> getInOut(final @NonNull TableRecordReference recordRef)
	{
		return Optional.of(recordRef)
				.map(lineRef -> lineRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId))
				.map(inOutBL::getLineByIdInTrx)
				.map(I_M_InOutLine::getM_InOut_ID)
				.map(InOutId::ofRepoId)
				.map(inOutBL::getById);
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
		return contractProvider.streamModularPurchaseContractsForPPOrder(ManufacturingFacadeService.getManufacturingReceiptOrIssuedId(recordRef));
		// TODO : do for this, shipment and inventory
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);
		if (logs.isEmpty())
		{
			return computingMethodService.toZeroResponse(request);
		}

		final ProductPrice price = logs.getUniqueProductPriceOrErrorNotNull();
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.price(computingMethodService.productPriceToUOM(price, stockUOMId))
				.qty(computingMethodService.getQtySumInStockUOM(logs))
				.build();
	}
}
