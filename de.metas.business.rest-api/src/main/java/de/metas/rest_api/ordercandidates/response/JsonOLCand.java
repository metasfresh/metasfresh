package de.metas.rest_api.ordercandidates.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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
public class JsonOLCand
{
	private int id;
	private String externalLineId;
	private String externalHeaderId;

	private String poReference;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonOrganization org;

	private JsonResponseBPartnerLocationAndContact bpartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonResponseBPartnerLocationAndContact billBPartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonResponseBPartnerLocationAndContact dropShipBPartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JsonResponseBPartnerLocationAndContact handOverBPartner;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateOrdered;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate datePromised;

	private int flatrateConditionsId;

	private int productId;

	private String productDescription;

	private BigDecimal qty;
	private int uomId;
	private int huPIItemProductId;

	private int pricingSystemId;

	// TODO document: per product, discount not yet applied
	private BigDecimal price;

	// TODO document: percent
	private BigDecimal discount;

	private int warehouseDestId;

	@JsonCreator
	@Builder
	private JsonOLCand(
			@JsonProperty("id") final int id,
			@JsonProperty("externalLineId") final String externalLineId,
			@JsonProperty("externalHeaderId") final String externalHeaderId,
			@JsonProperty("poReference") final String poReference,
			@JsonProperty("org") final JsonOrganization org,
			@JsonProperty("bpartner") final JsonResponseBPartnerLocationAndContact bpartner,
			@JsonProperty("billBPartner") final JsonResponseBPartnerLocationAndContact billBPartner,
			@JsonProperty("dropShipBPartner") final JsonResponseBPartnerLocationAndContact dropShipBPartner,
			@JsonProperty("handOverBPartner") final JsonResponseBPartnerLocationAndContact handOverBPartner,
			@JsonProperty("dateOrdered") final LocalDate dateOrdered,
			@JsonProperty("datePromised") final LocalDate datePromised,
			@JsonProperty("flatrateConditionsId") final int flatrateConditionsId,
			@JsonProperty("productId") final int productId,
			@JsonProperty("productDescription") final String productDescription,
			@JsonProperty("qty") final BigDecimal qty,
			@JsonProperty("uomId") final int uomId,
			@JsonProperty("huPIItemProductId") final int huPIItemProductId,
			@JsonProperty("pricingSystemId") final int pricingSystemId,
			@JsonProperty("price") final BigDecimal price,
			@JsonProperty("discount") final BigDecimal discount,
			@JsonProperty("warehouseDestId") final int warehouseDestId)
	{
		this.id = id;
		this.externalLineId = externalLineId;
		this.externalHeaderId = externalHeaderId;
		this.poReference = poReference;
		this.org = org;
		this.bpartner = bpartner;
		this.billBPartner = billBPartner;
		this.dropShipBPartner = dropShipBPartner;
		this.handOverBPartner = handOverBPartner;
		this.dateOrdered = dateOrdered;
		this.datePromised = datePromised;
		this.flatrateConditionsId = flatrateConditionsId;
		this.productId = productId;
		this.productDescription = productDescription;
		this.qty = qty;
		this.uomId = uomId;
		this.huPIItemProductId = huPIItemProductId;
		this.pricingSystemId = pricingSystemId;
		this.price = price;
		this.discount = discount;
		this.warehouseDestId = warehouseDestId;
	}

}
