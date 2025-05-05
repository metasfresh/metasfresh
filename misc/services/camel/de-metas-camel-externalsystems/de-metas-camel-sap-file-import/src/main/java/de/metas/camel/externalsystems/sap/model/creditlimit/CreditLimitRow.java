/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.sap.model.creditlimit;

import de.metas.camel.externalsystems.sap.model.bpartner.PartnerCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@CsvRecord(separator = "	", skipField = true)
@Getter
public class CreditLimitRow
{
	@DataField(pos = 2)
	private String creditControlArea;

	@DataField(pos = 3)
	@Getter(AccessLevel.NONE)
	private String creditAccount;

	@DataField(pos = 4)
	private String creditType;

	@DataField(pos = 6)
	private String effectiveDateFrom;

	@DataField(pos = 7)
	private String effectiveDateTo;

	@DataField(pos = 8)
	private String currencyCode;

	@DataField(pos = 9)
	private String creditLine;

	@DataField(pos = 11)
	private String deleteFlag;

	@NonNull
	public PartnerCode getCreditAccount()
	{
		return PartnerCode.of(creditAccount);
	}

	@NonNull
	public Optional<LocalDate> getEffectiveDateFrom(@NonNull final String datePattern)
	{
		return Optional.ofNullable(effectiveDateFrom)
				.map(dateFrom -> LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern(datePattern)));
	}

	@NonNull
	public Optional<LocalDate> getEffectiveDateTo(@NonNull final String datePattern)
	{
		return Optional.ofNullable(effectiveDateTo)
				.map(dateTo -> LocalDate.parse(dateTo, DateTimeFormatter.ofPattern(datePattern)));
	}
}
