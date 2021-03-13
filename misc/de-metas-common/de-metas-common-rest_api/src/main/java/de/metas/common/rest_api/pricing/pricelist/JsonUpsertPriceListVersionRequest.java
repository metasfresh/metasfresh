/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.pricing.pricelist;

import de.metas.common.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Getter
@ToString
@EqualsAndHashCode
public class JsonUpsertPriceListVersionRequest
{
	@ApiModelProperty(required = true)
	private String priceListIdentifier;

	@ApiModelProperty(required = true)
	private String orgCode;

	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	@ApiModelProperty(required = true)
	private String validFrom;

	@ApiModelProperty(hidden = true)
	private boolean validFromSet;

	@ApiModelProperty(required = true)
	private String active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

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

	public void setValidFrom(final String validFrom)
	{
		this.validFrom = validFrom;
		this.validFromSet = true;
	}

	public void setActive(final String active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = coalesce(syncAdvise, SyncAdvise.READ_ONLY);
		this.syncAdviseSet = true;
	}
}
