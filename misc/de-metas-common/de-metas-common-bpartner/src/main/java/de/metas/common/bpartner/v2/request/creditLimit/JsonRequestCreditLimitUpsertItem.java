/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request.creditLimit;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Note that given the respective use-case, either one of both properties might be `null`, but not both at once.")
public class JsonRequestCreditLimitUpsertItem
{
	@ApiModelProperty(position = 10)
	private JsonMetasfreshId creditLimitMetasfreshId;

	@ApiModelProperty(hidden = true)
	private boolean creditLimitMetasfreshIdSet;

	@ApiModelProperty(position = 20)
	private BigDecimal amount;

	@ApiModelProperty(hidden = true)
	private boolean amountSet;

	@ApiModelProperty(position = 30)
	private String type;

	@ApiModelProperty(hidden = true)
	private boolean typeSet;

	@ApiModelProperty(position = 40)
	private String orgCode;

	@ApiModelProperty(hidden = true)
	private boolean orgCodeSet;

	@ApiModelProperty(position = 50)
	private LocalDate dateFrom;

	@ApiModelProperty(hidden = true)
	private boolean dateFromSet;

	@ApiModelProperty(position = 60)
	private String currencyCode;

	@ApiModelProperty(hidden = true)
	private boolean currencyCodeSet;

	@ApiModelProperty(position = 70)
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 80)
	private Boolean processed;

	@ApiModelProperty(hidden = true)
	private boolean processedSet;

	public void setAmount(final BigDecimal amount)
	{
		this.amount = amount;
		this.amountSet = true;
	}

	public void setType(final String type)
	{
		this.type = type;
		this.typeSet = true;
	}

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
		this.orgCodeSet = true;
	}

	public void setCreditLimitMetasfreshId(final JsonMetasfreshId creditLimitMetasfreshId)
	{
		this.creditLimitMetasfreshId = creditLimitMetasfreshId;
		this.creditLimitMetasfreshIdSet = true;
	}

	public void setDateFrom(final LocalDate dateFrom)
	{
		this.dateFrom = dateFrom;
		this.dateFromSet = true;
	}

	public void setCurrencyCode(final String currencyCode)
	{
		this.currencyCode = currencyCode;
		this.currencyCodeSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setProcessed(final Boolean processed)
	{
		this.processed = processed;
		this.processedSet = true;
	}
}
