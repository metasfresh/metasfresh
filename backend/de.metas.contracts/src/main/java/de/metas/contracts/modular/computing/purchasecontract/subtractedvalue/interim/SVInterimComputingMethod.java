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

package de.metas.contracts.modular.computing.purchasecontract.subtractedvalue.interim;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.purchasecontract.AbstractInterestComputingMethod;
import de.metas.contracts.modular.interest.log.ModularLogInterestRepository;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationRepository;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SVInterimComputingMethod extends AbstractInterestComputingMethod
{
	@NonNull private final ShippingNotificationRepository shippingNotificationRepository;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	public SVInterimComputingMethod(
			@NonNull final ShippingNotificationRepository shippingNotificationRepository,
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ModCntrInvoicingGroupRepository invoicingGroupRepository,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModularLogInterestRepository modularLogInterestRepository)
	{
		super(shippingNotificationRepository, contractProvider, invoicingGroupRepository, modularContractLogService, modularLogInterestRepository);
		this.shippingNotificationRepository = shippingNotificationRepository;
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.SubtractValueOnInterim;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}
		else if (recordRef.tableNameEqualsTo(I_M_Shipping_NotificationLine.Table_Name))
		{
			final I_M_Shipping_NotificationLine line = shippingNotificationRepository.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecord_ID()));

			final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(line.getC_Order_ID()));
			final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoIdOrNull(salesOrder.getHarvesting_Year_ID(), salesOrder.getC_Harvesting_Calendar_ID());

			return yearAndCalendarId != null;
		}

		else if (recordRef.tableNameEqualsTo(I_C_InvoiceLine.Table_Name))
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
}
