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

package de.metas.payment.paymentterm;

import de.metas.i18n.AdMessageKey;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

@UtilityClass
public class PaymentTermConstants
{
	public static final AdMessageKey MSG_ComplexTermConflict = AdMessageKey.of("C_PaymentTerm_ComplexTermConflict");
	public static final AdMessageKey C_PAYMENTTERM_BREAK_TotalPercentTooHigh = AdMessageKey.of("C_PaymentTerm_Break_TotalPercentTooHigh");

	public static final Instant PENDING_DATE = LocalDateTime.of(9999, Month.JANUARY, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
}

