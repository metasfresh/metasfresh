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

package de.metas.contracts.modular.computing.purchasecontract;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.interest.log.ModularLogInterest;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationRepository;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractInterestComputingMethod implements IComputingMethodHandler
{
	@NonNull private final ShippingNotificationRepository shippingNotificationRepository;
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ModCntrInvoicingGroupRepository invoicingGroupRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final ModularLogInterestRepository modularLogInterestRepository;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		if (recordRef.tableNameEqualsTo(I_M_Shipping_NotificationLine.Table_Name))
		{
			final I_M_Shipping_NotificationLine line = shippingNotificationRepository.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecord_ID()));

			final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(line.getC_Order_ID()));
			final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoIdOrNull(salesOrder.getHarvesting_Year_ID(), salesOrder.getC_Harvesting_Calendar_ID());

			return yearAndCalendarId != null;
		}

		if (recordRef.tableNameEqualsTo(I_C_InvoiceLine.Table_Name))
		{
			final I_C_Invoice invoice = Optional.of(recordRef)
					.map(lineRef -> lineRef.getIdAssumingTableName(I_C_InvoiceLine.Table_Name, InvoiceLineId::ofRepoId))
					.map(invoiceBL::getLineById)
					.map(I_C_InvoiceLine::getC_Invoice_ID)
					.map(InvoiceId::ofRepoId)
					.map(invoiceBL::getById)
					.orElseThrow(() -> new AdempiereException("No C_Invoice found for line=" + recordRef));

			return !invoice.isSOTrx() && invoiceBL.isDownPayment(invoice);
		}

		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		if (recordRef.tableNameEqualsTo(I_M_Shipping_NotificationLine.Table_Name))
		{
			final I_M_Shipping_NotificationLine line = shippingNotificationRepository.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecord_ID()));

			return contractProvider.streamPurchaseContractsForSalesOrderLine(OrderAndLineId.ofRepoIds(line.getC_Order_ID(), line.getC_OrderLine_ID()));
		}
		if (recordRef.tableNameEqualsTo(I_C_InvoiceLine.Table_Name))
		{
			return contractProvider.streamModularPurchaseContractsForInvoiceLine(InvoiceLineId.ofRepoId(recordRef.getRecord_ID()));
		}

		return Stream.empty();
	}

	@Override
	public boolean isApplicableForSettings(@NonNull final TableRecordReference recordRef, @NonNull final ModularContractSettings settings)
	{
		return invoicingGroupRepository.getInvoicingGroupIdFor(settings.getRawProductId(), settings.getYearAndCalendarId())
				.isPresent();
	}

	@NonNull
	@Override
	public ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final ImmutableSet.Builder<ModularContractLogEntryId> logEntryIdsCollector = ImmutableSet.builder();
		final Money amount = streamInterestRecords(request)
				.peek(interestLog -> {
					logEntryIdsCollector.add(interestLog.getShippingNotificationLogId());
					if (interestLog.getInterimInvoiceLogId() != null)
					{
						logEntryIdsCollector.add(interestLog.getInterimInvoiceLogId());
					}
				})
				.map(ModularLogInterest::getFinalInterest)
				.filter(Objects::nonNull)
				.filter(amt -> !amt.isZero())
				.reduce(Money.zero(request.getCurrencyId()), Money::add);

		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final Quantity qty = amount.signum() == 0
				? Quantity.of(BigDecimal.ZERO, stockUOM)
				: Quantity.of(BigDecimal.ONE, stockUOM);

		return ComputingResponse.builder()
				.ids(logEntryIdsCollector.build())
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(amount.negate())
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}

	@NonNull
	private Stream<ModularLogInterest> streamInterestRecords(final @NonNull ComputingRequest request)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.processed(false)
				.billable(true)
				.flatrateTermId(request.getFlatrateTermId())
				.contractModuleId(request.getModularContractModuleId())
				.lockOwner(request.getLockOwner())
				.build();
		final PInstanceId selectionId = modularContractLogService.getModularContractLogEntrySelection(query);
		if (selectionId == null)
		{
			return Stream.empty();
		}
		final ModularLogInterestRepository.LogInterestQuery logInterestQuery = ModularLogInterestRepository.LogInterestQuery.builder()
				.modularLogSelection(selectionId)
				.build();

		return modularLogInterestRepository.streamInterestEntries(logInterestQuery);
	}
}
