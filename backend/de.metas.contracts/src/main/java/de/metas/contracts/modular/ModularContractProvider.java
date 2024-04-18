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
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

@Service
public class ModularContractProvider
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@NonNull
	public Stream<FlatrateTermId> streamSalesContractsForSalesOrderLine(@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_Order order = orderBL.getById(orderAndLineId.getOrderId());
		if (!order.isSOTrx())
		{
			return Stream.empty();
		}

		return flatrateBL.getByOrderLineId(orderAndLineId.getOrderLineId(), TypeConditions.MODULAR_CONTRACT)
				.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.stream();
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
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.build();

		return flatrateBL.streamModularFlatrateTermsByQuery(query)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForPurchaseOrderLine(@NonNull final OrderLineId purchaseOrderLineId)
	{
		return flatrateBL.getByOrderLineId(purchaseOrderLineId, TypeConditions.MODULAR_CONTRACT)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId)
				.stream();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForReceiptLine(@NonNull final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inoutDao.getLineByIdInTrx(receiptInOutLineId);
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(inOutLineRecord.getC_Flatrate_Term_ID()));
	}

	@NonNull
	public Stream<FlatrateTermId> streamInterimPurchaseContractsForReceiptLine(@NonNull final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inoutDao.getLineByIdInTrx(receiptInOutLineId);
		final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
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
		if (invoiceLineRecord.getC_Flatrate_Term_ID() > 0)
		{
			return Stream.of(FlatrateTermId.ofRepoId(invoiceLineRecord.getC_Flatrate_Term_ID()));
		}
		else
		{
			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLineRecord);
			if (invoiceCandidates.size() != 1)
			{
				return Stream.empty();
			}

			final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandidates.get(0);
			final int flatrateTermTableId = getTableId(I_C_Flatrate_Term.class);
			if (invoiceCandidateRecord.getAD_Table_ID() != flatrateTermTableId)
			{
				return Stream.empty();
			}

			final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(invoiceCandidateRecord.getRecord_ID());
			if(!flatrateBL.isModularContract(flatrateTermId))
			{
				return Stream.empty();
			}

			return Stream.of(flatrateTermId);
		}
	}
}
