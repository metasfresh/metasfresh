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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractComputingMethodHandler;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.interest.log.ModularLogInterest;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.invoice.InvoiceLineId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationRepository;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractInterestComputingMethod extends AbstractComputingMethodHandler
{
	@NonNull private final ShippingNotificationRepository shippingNotificationRepository;
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ModCntrInvoicingGroupRepository invoicingGroupRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final ModularLogInterestRepository modularLogInterestRepository;

	private final IProductBL productBL = Services.get(IProductBL.class);

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

		if (recordRef.tableNameEqualsTo(I_C_Flatrate_Term.Table_Name))
		{
			return contractProvider.streamModularPurchaseContractsForContract(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
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
		final AtomicReference<Money> reconciledAmount = new AtomicReference<>(Money.zero(request.getCurrencyId()));
		final AtomicReference<ModularContractLogEntryId> initialInterimContractId = new AtomicReference<>();
		final Money amount = streamInterestRecords(request)
				.peek(interestLog -> {
					logEntryIdsCollector.add(interestLog.getShippingNotificationLogId());
					final ModularContractLogEntryId interimContractLogId = interestLog.getInterimContractLogId();
					if (interimContractLogId != null)
					{
						logEntryIdsCollector.add(interimContractLogId);
						initialInterimContractId.set(interimContractLogId);
						reconciledAmount.set(reconciledAmount.get().add(interestLog.getAllocatedAmt()));
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
		final ImmutableSet<ModularContractLogEntryId> logEntryIds = logEntryIdsCollector.build();

		final ModularContractLogEntriesList logs = logEntryIds.isEmpty() ? ModularContractLogEntriesList.EMPTY : getModularContractLogEntries(request, logEntryIds);

		splitLogsIfNeeded(reconciledAmount.get(), initialInterimContractId.get());
		return ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull())
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(amount.negate())
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}

	protected void splitLogsIfNeeded(final @NonNull Money reconciledAmount, final @Nullable ModularContractLogEntryId initialInterimContractId)
	{

	}

	private @NonNull ModularContractLogEntriesList getModularContractLogEntries(final @NonNull ComputingRequest request, final ImmutableSet<ModularContractLogEntryId> logEntryIds)
	{
		return modularContractLogService.getModularContractLogEntries(ModularContractLogQuery.builder()
																			  .entryIds(logEntryIds)
																			  .flatrateTermId(request.getFlatrateTermId())
																			  .computingMethodType(getComputingMethodType())
																			  .billable(true)
																			  .processed(false)
																			  .build());
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
