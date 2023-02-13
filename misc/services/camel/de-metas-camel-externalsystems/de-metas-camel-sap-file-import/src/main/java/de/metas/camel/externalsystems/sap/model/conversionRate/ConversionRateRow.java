
/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.model.conversionRate;

import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static de.metas.camel.externalsystems.sap.SAPConstants.DEFAULT_DATE_FORMAT;

@CsvRecord(separator = "	", skipField = true)
@Getter
public class ConversionRateRow
{
	@DataField(pos = 1)
	private String exrt;

	@DataField(pos = 2)
	private String from;

	@DataField(pos = 3)
	private String to;

	@DataField(pos = 4)
	private String validFrom;

	@DataField(pos = 5)
	private String indirQuot;

	@NonNull
	public LocalDate getValidFrom()
	{
		return LocalDate.parse(validFrom, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
	}

	@NonNull
	public BigDecimal getDivideRate()
	{
		return new BigDecimal(indirQuot.trim()).abs();
	}
}
