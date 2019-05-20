package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.vat;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.util.List;

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
@Builder
public class XmlVat
{
	@Nullable
	String vatNumber;

	@NonNull
	BigDecimal vat;

	@Singular
	List<XmlVatRate> vatRates;

	public XmlVat withMod(@Nullable final VatMod vatMod)
	{
		if (vatMod == null)
		{
			return this;
		}

		final XmlVatBuilder builder = XmlVat.builder();
		if (vatMod.getVatNumber() != null)
		{
			builder.vatNumber(vatMod.getVatNumber());
		}

		// When it comes to the vat amount and rates, we don't just "patch", but replace the whole thing.
		return builder
				.vat(vatMod.getVat())
				.clearVatRates()
				.vatRates(vatMod.getVatRates())
				.build();
	}

	@Value
	@Builder
	public static class VatMod
	{
		@NonNull
		BigDecimal vat;

		@Nullable
		String vatNumber;

		@Singular
		List<XmlVatRate> vatRates;
	}
}
