/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.conversionRate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
public class JsonCurrencyRateCreateRequestItem
{
	@ApiModelProperty(required = true, position = 10, value = "Translated to `C_ConversionRate.C_Currency_To.ISO_Code")
	private String currencyCodeTo;

	@ApiModelProperty(hidden = true)
	private boolean currencyCodeToSet;

	@ApiModelProperty(position = 20, value = "Translated to `C_ConversionRate.C_ConversionType.Name`")
	private String conversionType;

	@ApiModelProperty(hidden = true)
	private boolean conversionTypeSet;

	@ApiModelProperty(required = true, position = 30, value = "Translated to `C_ConversionRate.ValidFrom`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate validFrom;

	@ApiModelProperty(hidden = true)
	private boolean validFromSet;

	@ApiModelProperty(position = 40, value = "Translated to `C_ConversionRate.ValidTo`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate validTo;

	@ApiModelProperty(hidden = true)
	private boolean validToSet;

	@ApiModelProperty(required = true, position = 50, value = "Translated to `C_ConversionRate.DivideRate`")
	private BigDecimal divideRate;

	@ApiModelProperty(hidden = true)
	private boolean divideRateSet;

	public void setCurrencyCodeTo(final String currencyCodeTo)
	{
		this.currencyCodeTo = currencyCodeTo;
		this.currencyCodeToSet = true;
	}

	public void setConversionType(final String conversionType)
	{
		this.conversionType = conversionType;
		this.conversionTypeSet = true;
	}

	public void setValidFrom(final LocalDate validFrom)
	{
		this.validFrom = validFrom;
		this.validFromSet = true;
	}

	public void setValidTo(final LocalDate validTo)
	{
		this.validTo = validTo;
		this.validToSet = true;
	}

	public void setDivideRate(final BigDecimal divideRate)
	{
		this.divideRate = divideRate;
		this.divideRateSet = true;
	}
}
