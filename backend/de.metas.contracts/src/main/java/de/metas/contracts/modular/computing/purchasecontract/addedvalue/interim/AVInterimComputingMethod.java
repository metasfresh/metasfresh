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

package de.metas.contracts.modular.computing.purchasecontract.addedvalue.interim;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.purchasecontract.AbstractInterestComputingMethod;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogEntryId;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.currency.CurrencyConversionContext;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationRepository;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class AVInterimComputingMethod extends AbstractInterestComputingMethod
{

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private final ShippingNotificationRepository shippingNotificationRepository;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final MoneyService moneyService;

	public AVInterimComputingMethod(
			@NonNull final ShippingNotificationRepository shippingNotificationRepository,
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ModCntrInvoicingGroupRepository invoicingGroupRepository,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModularLogInterestRepository modularLogInterestRepository,
			@NonNull final MoneyService moneyService)
	{
		super(shippingNotificationRepository, contractProvider, invoicingGroupRepository, modularContractLogService, modularLogInterestRepository);
		this.shippingNotificationRepository = shippingNotificationRepository;
		this.modularContractLogService = modularContractLogService;
		this.moneyService = moneyService;

	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.AddValueOnInterim;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		if (recordRef.tableNameEqualsTo(I_C_Flatrate_Term.Table_Name))
		{
			final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
			if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isInterimContractType())
			{
				return false;
			}

			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(flatrateTermRecord.getC_OrderLine_Term_ID());
			if (orderLineId == null)
			{
				return false;
			}

			final OrderId orderId = orderLineBL.getOrderIdByOrderLineId(orderLineId);
			return SOTrx.ofBoolean(orderBL.getById(orderId).isSOTrx()).isPurchase();
		}
		if (recordRef.tableNameEqualsTo(I_M_Shipping_NotificationLine.Table_Name))
		{
			final I_M_Shipping_NotificationLine line = shippingNotificationRepository.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecord_ID()));

			final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(line.getC_Order_ID()));
			final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoIdOrNull(salesOrder.getHarvesting_Year_ID(), salesOrder.getC_Harvesting_Calendar_ID());

			return yearAndCalendarId != null;
		}

		return false;
	}

	@Override
	protected void splitLogsIfNeeded(final Money reconciledAmount, final AtomicReference<ModularContractLogEntryId> initialInterimContractId)
	{
		final ModularContractLogEntry interimContractEntry = modularContractLogService.getById(initialInterimContractId.get());

		final Money interimLogAmount = interimContractEntry.getAmount();
		if(interimLogAmount == null  || interimLogAmount.isZero())
		{
			// no log split is needed
			return;
		}

		final CurrencyConversionContext context = modularContractLogService.getCurrencyConversionContext(interimContractEntry);
		final Money reconciledInInterimContractCurrency = moneyService.convertMoneyToCurrency(reconciledAmount,
																							  interimLogAmount.getCurrencyId(),
																							  context);

		if (interimLogAmount.isLessThanOrEqualTo(reconciledInInterimContractCurrency))
		{
			// no log split is needed
			return;
		}

		final Percent reconciledAmountPercent = reconciledInInterimContractCurrency.percentageOf(interimLogAmount.abs());
		final Quantity reconciledAmountQty = Optional.ofNullable(interimContractEntry.getQuantity())
				.map(qty -> {
					final BigDecimal openQty = reconciledAmountPercent.computePercentageOf(qty.toBigDecimal(), qty.getUOM().getStdPrecision());
					return Quantity.of(openQty, qty.getUOM());
				}).orElse(null);

		final Money reconciledAmountInInterimContractCurrencyWithSignApplied = reconciledInInterimContractCurrency.negateIf(interimLogAmount.isNegative());

		final LogEntryCreateRequest createInterimLogForOpenAmt = LogEntryCreateRequest.ofEntry(interimContractEntry)
				.toBuilder()
				.amount(interimLogAmount.subtract(reconciledAmountInInterimContractCurrencyWithSignApplied))
				.quantity(Optional.ofNullable(interimContractEntry.getQuantity()).map(qty -> qty.subtract(reconciledAmountQty)).orElse(null))
				.build();
		modularContractLogService.create(createInterimLogForOpenAmt);

		final ModularContractLogEntry interimLogEntryToUpdate = interimContractEntry.toBuilder()
				.amount(reconciledAmountInInterimContractCurrencyWithSignApplied)
				.quantity(reconciledAmountQty)
				.build();

		modularContractLogService.updateModularLog(interimLogEntryToUpdate);
	}
}
