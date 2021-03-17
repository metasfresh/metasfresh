/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.pricelist.request.v2;

import de.metas.common.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestPriceListVersion
{
	@ApiModelProperty(required = true)
	private String priceListIdentifier;

	@ApiModelProperty(required = true)
	private String orgCode;

	@ApiModelProperty(required = true)
	private Instant validFrom;

	@ApiModelProperty(required = true)
	private Boolean active;

	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	private SyncAdvise syncAdvise;

	@ApiModelProperty(hidden = true)
	private boolean syncAdviseSet;

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	public void setPriceListIdentifier(final String priceListIdentifier)
	{
		this.priceListIdentifier = priceListIdentifier;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setValidFrom(final Instant validFrom)
	{
		this.validFrom = validFrom;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}
}
