/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular;

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsQuery;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.contracts.flatrate.TypeConditions.MODULAR_CONTRACT;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

@Service
@RequiredArgsConstructor
public class ModularContractProvider
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	@NonNull private final ModularContractSettingsBL modularContractSettingsBL;

	@NonNull
	public Stream<FlatrateTermId> streamSalesContractsForSalesOrderLine(@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_Order order = orderBL.getById(orderAndLineId.getOrderId());
		if (!order.isSOTrx())
		{
			return Stream.empty();
		}

		return flatrateBL.getByOrderLineId(orderAndLineId.getOrderLineId(), MODULAR_CONTRACT)
				.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.stream();
	}

	@Nullable
	public FlatrateTermId getSinglePurchaseContractsForSalesOrderLineOrNull(@Nullable final OrderAndLineId orderAndLineId)
	{
		if (orderAndLineId == null)
		{
			return null;
		}
		final Set<FlatrateTermId> contractIds = streamPurchaseContractsForSalesOrderLine(orderAndLineId)
				.collect(Collectors.toSet());
		Check.assume(contractIds.size() <= 1, "Maximum 1 Contract should be found");
		return contractIds.stream().findFirst().orElse(null);
	}

	@NonNull
	public Stream<FlatrateTermId> streamPurchaseContractsForSalesOrderLine(@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_Order order = orderBL.getById(orderAndLineId.getOrderId());
		final I_C_OrderLine orderLine = orderBL.getLineById(orderAndLineId);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());

		final YearId harvestingYearId = YearId.ofRepoId(order.getHarvesting_Year_ID());

		final CalendarId harvestingCalendarId = CalendarId.ofRepoId(order.getC_Harvesting_Calendar_ID());

		final ModularFlatrateTermQuery query = ModularFlatrateTermQuery.builder()
				.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.calendarId(harvestingCalendarId)
				.yearId(harvestingYearId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(MODULAR_CONTRACT)
				.build();

		return flatrateBL.streamModularFlatrateTermIdsByQuery(query);
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForPurchaseOrderLine(@NonNull final OrderLineId purchaseOrderLineId)
	{
		return flatrateBL.getByOrderLineId(purchaseOrderLineId, MODULAR_CONTRACT)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId)
				.stream();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForReceiptLine(@NonNull final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(receiptInOutLineId);
		final FlatrateTermId contractId = FlatrateTermId.ofRepoIdOrNull(inOutLineRecord.getC_Flatrate_Term_ID());

		return Stream.ofNullable(contractId)
				.map(flatrateBL::getById)
				.filter(contract -> MODULAR_CONTRACT.equalsByCode(contract.getType_Conditions()))
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForInventory(final InventoryLineId inventoryId)
	{
		final I_M_InventoryLine inventoryLine = inventoryBL.getLineById(inventoryId);
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()));
	}

	@NonNull
	public Stream<FlatrateTermId> streamInterimPurchaseContractsForReceiptLine(@NonNull final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(receiptInOutLineId);
		final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final FlatrateTermId modularFlatrateTermId = FlatrateTermId.ofRepoIdOrNull(inOutLineRecord.getC_Flatrate_Term_ID());
		if (inOutRecord.isSOTrx() || modularFlatrateTermId == null || inOutLineRecord.getMovementQty().signum() < 0)
		{
			return Stream.empty();
		}
		final Instant movementDate = TimeUtil.asInstant(inOutRecord.getMovementDate());
		Check.assumeNotNull(movementDate, "Instant Movement Date of receipt shouldn't be null");
		return Stream.ofNullable(flatrateBL.getInterimContractIdByModularContractIdAndDate(modularFlatrateTermId, movementDate));
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForPPOrder(@NonNull final PPCostCollectorId ppCostCollectorId)
	{
		return Optional.of(ppCostCollectorBL.getById(ppCostCollectorId).getPP_Order_ID())
				.map(PPOrderId::ofRepoId)
				.map(ppOrderBL::getById)
				.map(I_PP_Order::getModular_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoIdOrNull)
				.stream();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForInvoiceLine(@NonNull final InvoiceLineId invoiceLineId)
	{
		final I_C_InvoiceLine invoiceLineRecord = invoiceBL.getLineById(invoiceLineId);
		final FlatrateTermId flatrateTermId;
		if (invoiceLineRecord.getC_Flatrate_Term_ID() > 0)
		{
			flatrateTermId = FlatrateTermId.ofRepoId(invoiceLineRecord.getC_Flatrate_Term_ID());
		}
		else
		{
			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLineRecord);

			if (invoiceCandidates.isEmpty())
			{
				return Stream.empty();
			}

			flatrateTermId = CollectionUtils.extractSingleElement(invoiceCandidates, this::extractFlatrateTermId).orElse(null);

		}

		if (flatrateTermId == null)
		{
			return Stream.empty();
		}

		if (flatrateBL.isModularContract(flatrateTermId))
		{
			return Stream.of(flatrateTermId);
		}
		else if (flatrateBL.isInterimContract(flatrateTermId))
		{
			final I_C_Flatrate_Term interimContractRecord = flatrateBL.getById(flatrateTermId);
			return Stream.of(FlatrateTermId.ofRepoId(interimContractRecord.getModular_Flatrate_Term_ID()));
		}
		else
		{
			return Stream.empty();
		}
	}

	private Optional<FlatrateTermId> extractFlatrateTermId(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_Flatrate_Term_ID() > 0)
		{
			return Optional.of(FlatrateTermId.ofRepoId(ic.getC_Flatrate_Term_ID()));
		}

		final int flatrateTermTableId = getTableId(I_C_Flatrate_Term.class);
		if (ic.getAD_Table_ID() == flatrateTermTableId)
		{
			return Optional.of(FlatrateTermId.ofRepoId(ic.getRecord_ID()));
		}

		return Optional.empty();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForShipmentLine(@NonNull final InOutLineId inOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(inOutLineId);
		final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		if (!inOutRecord.isSOTrx() || inOutLineRecord.getMovementQty().signum() < 0)
		{
			return Stream.empty();
		}

		final OrderId orderId = OrderId.ofRepoId(inOutLineRecord.getC_Order_ID());
		return streamModularPurchaseContractBySalesOrderWithProductId(orderId, ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()));
	}

	private @NonNull Stream<FlatrateTermId> streamModularPurchaseContractBySalesOrderWithProductId(final OrderId orderId, final ProductId productId)
	{
		final I_C_Order order = orderBL.getById(orderId);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID()); // C_Order.M_Warehouse_ID is mandatory and warehouseBL.getBPartnerId demands NonNull

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(order.getHarvesting_Year_ID());

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(order.getC_Harvesting_Calendar_ID());

		if (harvestingYearId == null || harvestingCalendarId == null)
		{
			return Stream.empty();
		}

		final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoId(harvestingYearId, harvestingCalendarId);
		final List<ModularContractSettings> settings = modularContractSettingsBL.getSettingsByQuery(ModularContractSettingsQuery.builder()
				.processedProductId(productId)
				.yearAndCalendarId(yearAndCalendarId)
				.soTrx(SOTrx.PURCHASE)
				.checkHasCompletedModularCondition(true)
				.build());

		final ProductId settingsProductId = CollectionUtils.extractSingleElementOrDefault(settings, ModularContractSettings::getRawProductId, null);
		final ProductId productIdToUse = CoalesceUtil.coalesceNotNull(settingsProductId, productId);

		final ModularFlatrateTermQuery query = ModularFlatrateTermQuery.builder()
				.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
				.productId(productIdToUse)
				.yearId(harvestingYearId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(MODULAR_CONTRACT)
				.calendarId(harvestingCalendarId)
				.build();

		return flatrateBL.streamModularFlatrateTermIdsByQuery(query);
	}
}
