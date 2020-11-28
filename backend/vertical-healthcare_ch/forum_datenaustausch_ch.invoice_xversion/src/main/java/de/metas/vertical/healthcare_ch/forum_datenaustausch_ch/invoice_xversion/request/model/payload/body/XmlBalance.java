package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat.XmlVat.VatMod;

import java.math.BigDecimal;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class XmlBalance
{
	/** expecting default = CHF */
	@NonNull
	String currency;

	@NonNull
	BigDecimal amount;

	/** expecting default = 0 */
	@NonNull
	BigDecimal amountReminder;

	/** expecting default = 0 */
	@NonNull
	BigDecimal amountPrepaid;

	@NonNull
	BigDecimal amountDue;

	/** expecting default = 0 */
	@NonNull
	BigDecimal amountObligations;

	@NonNull
	XmlVat vat;

	public XmlBalance withMod(@Nullable final BalanceMod balanceMod)
	{
		if (balanceMod == null)
		{
			return this;
		}

		final XmlBalanceBuilder builder = toBuilder();
		if (balanceMod.getCurrency() != null)
		{
			builder.currency(balanceMod.getCurrency());
		}
		if (balanceMod.getAmount() != null)
		{
			builder.amount(balanceMod.getAmount());
		}
		if (balanceMod.getAmountDue() != null)
		{
			builder.amountDue(balanceMod.getAmountDue());
		}

		return builder
				.vat(vat.withMod(balanceMod.getVatMod()))
				.build();
	}

	@Value
	@Builder
	public static class BalanceMod
	{
		@Nullable
		String currency;

		@Nullable
		BigDecimal amount;

		@Nullable
		BigDecimal amountDue;

		@Nullable
		VatMod vatMod;
	}
}
