/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.invoice.paymentschedule;

import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderAndPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.payment.paymentterm.PayScheduleId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@Getter
@Builder
public class InvoicePayScheduleLine
{
	@NonNull private final InvoicePayScheduleLineId id;
	@NonNull private final InvoiceId invoiceId;

	@Setter private boolean isValid;

	@NonNull @Setter private LocalDate dueDate;
	@NonNull private final Money dueAmount;

	@Nullable private final LocalDate discountDate;
	@Nullable private final Money discountAmount;

	@Nullable private final OrderAndPayScheduleId orderAndPayScheduleId;
	@Nullable private final PayScheduleId paymentTermScheduleId;

	public CurrencyId getCurrencyId() {return Money.getCommonCurrencyIdOfAll(dueAmount, discountAmount);}

	public OrderId getOrderId() {return orderAndPayScheduleId != null ? orderAndPayScheduleId.getOrderId() : null;}

	public OrderPayScheduleId getOrderPayScheduleId() {return orderAndPayScheduleId != null ? orderAndPayScheduleId.getOrderPayScheduleId() : null;}
}
