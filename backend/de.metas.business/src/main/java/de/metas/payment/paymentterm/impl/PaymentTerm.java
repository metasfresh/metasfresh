/*
 * #%L
 * de.metas.business
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

package de.metas.payment.paymentterm.impl;

import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Builder
@Value
public class PaymentTerm
{
	@NonNull private final PaymentTermId id;
	@NonNull private final String value;
	@NonNull private final String name;
	@NonNull private final OrgId orgId;
	@NonNull private final ClientId clientId;

	@Nullable private final Percent discount;
	@Nullable private final Percent discount2;
	@Nullable private final String netDay;

	private final int discountDays;
	private final int discountDays2;
	private final int graceDays;
	private final int netDays;
	private final boolean allowOverrideDueDate;
	private final boolean _default;
}

