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

package de.metas.common.pricing.v2.pricelist.request;

import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

import static de.metas.common.pricing.v2.constants.SwaggerDocConstants.PRICE_LIST_IDENTIFIER;

@ToString
@EqualsAndHashCode
public class JsonRequestPriceListVersion
{
	@Schema(required = true, description = PRICE_LIST_IDENTIFIER)
	private String priceListIdentifier;

	@Schema(required = true)
	private String orgCode;

	@Schema(required = true)
	private Instant validFrom;

	@Getter
	private Boolean active;

	@Schema(hidden = true)
	@Getter
	private boolean activeSet;

	@Getter
	private String description;

	@Schema(hidden = true)
	@Getter
	private boolean descriptionSet;

	@Getter
	private SyncAdvise syncAdvise;

	@Schema(hidden = true)
	@Getter
	private boolean syncAdviseSet;

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	@NonNull
	public String getOrgCode()
	{
		return orgCode;
	}

	public void setPriceListIdentifier(final String priceListIdentifier)
	{
		this.priceListIdentifier = priceListIdentifier;
	}

	@NonNull
	public String getPriceListIdentifier()
	{
		return priceListIdentifier;
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

	@NonNull
	public Instant getValidFrom()
	{
		return validFrom;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}
}
