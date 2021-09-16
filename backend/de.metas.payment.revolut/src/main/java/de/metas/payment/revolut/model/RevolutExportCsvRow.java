/*
 * #%L
 * de.metas.payment.revolut
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.payment.revolut.model;

import de.metas.location.ICountryDAO;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class RevolutExportCsvRow
{
	@NonNull
	RevolutPaymentExport row;

	@NonNull
	public static String getCSVHeader()
	{
		return Stream.of(RevolutExportCSVColumn.Name,
						 RevolutExportCSVColumn.RecipientType,
						 RevolutExportCSVColumn.AccountNo,
						 RevolutExportCSVColumn.RoutingNo,
						 RevolutExportCSVColumn.IBAN,
						 RevolutExportCSVColumn.BIC,
						 RevolutExportCSVColumn.RecipientBankCountryName,
						 RevolutExportCSVColumn.Currency,
						 RevolutExportCSVColumn.Amount,
						 RevolutExportCSVColumn.PaymentReference,
						 RevolutExportCSVColumn.RecipientCountryName,
						 RevolutExportCSVColumn.RegionName,
						 RevolutExportCSVColumn.AddressLine1,
						 RevolutExportCSVColumn.AddressLine2,
						 RevolutExportCSVColumn.City,
						 RevolutExportCSVColumn.PostalCode)
				.map(RevolutExportCSVColumn::getValue)
				.collect(Collectors.joining(","));
	}

	@NonNull
	public String toCSVRow(@NonNull final ICountryDAO countryDAO)
	{
		final String recipientBankCountryName = row.getRecipientBankCountryId() != null
				? countryDAO.getById(row.getRecipientBankCountryId()).getName()
				: null;

		final String recipientCountryName = row.getRecipientCountryId() != null
				? countryDAO.getById(row.getRecipientCountryId()).getName()
				: null;

		return Stream.of(row.getName(),
						 row.getRecipientType().getCode(),
						 row.getAccountNo(),
						 row.getRoutingNo(),
						 row.getIBAN(),
						 row.getBIC(),
						 recipientBankCountryName,
						 row.getAmount().getCurrencyCode().toThreeLetterCode(),
						 String.valueOf(row.getAmount().getAsBigDecimal()),
						 row.getPaymentReference(),
						 recipientCountryName,
						 row.getRegionName(),
						 row.getAddressLine1(),
						 row.getAddressLine2(),
						 row.getCity(),
						 row.getPostalCode())
				.map(this::escapeCSV)
				.collect(Collectors.joining(","));
	}

	@NonNull
	private String escapeCSV(@Nullable final String valueToEscape)
	{
		final String escapedQuote = "\"";
		return escapedQuote
				+ StringUtils.nullToEmpty(valueToEscape).replace(escapedQuote, escapedQuote + escapedQuote)
				+ escapedQuote;
	}

}
