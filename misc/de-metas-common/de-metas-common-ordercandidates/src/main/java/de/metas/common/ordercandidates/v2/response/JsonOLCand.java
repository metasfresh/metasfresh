package de.metas.common.ordercandidates.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.ordercandidates.v2.request.JsonOrderLineGroup;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

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
	int id;
	String externalLineId;
	String externalHeaderId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId adIssueId;

	String poReference;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String orgCode;

	JsonResponseBPartnerLocationAndContact bpartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonResponseBPartnerLocationAndContact billBPartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonResponseBPartnerLocationAndContact dropShipBPartner;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonResponseBPartnerLocationAndContact handOverBPartner;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateOrdered;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate datePromised;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateCandidate;

	int flatrateConditionsId;

	int productId;

	String productDescription;

	BigDecimal qty;
	int uomId;

	@ApiModelProperty(value = "Effective number of items - in the product's stock UOM - the order line candidate was created with.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	BigDecimal qtyItemCapacity;

	int huPIItemProductId;

	int pricingSystemId;

	// TODO document: per product, discount not yet applied
	// TODO document: might be put irrelevant by jsonOrderLineGroup with discount
	BigDecimal price;

	// TODO document: percent
	// TODO document: might be put irrelevant by jsonOrderLineGroup with discount 
	BigDecimal discount;

	int warehouseDestId;

	JsonOrderLineGroup jsonOrderLineGroup;
	String description;
	Integer line;

	@ApiModelProperty(value = "Custom REST-API columns on C_OLCand (AD_Column.IsRestAPICustomColumn='Y'). "
			+ "Keys are column names; values are the column values.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Map<String, Object> extendedProps;

	@ApiModelProperty(value = "Value of the promotion code linked to this order line candidate (`C_PromotionCode.Value`).")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String promotionCode;

	@ApiModelProperty(value = "Value of the second promotion code linked to this order line candidate (`C_PromotionCode.Value`).")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String promotionCode2;

	@ApiModelProperty(value = "If true, the order line candidate was created without charge.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isWithoutCharge;

	@ApiModelProperty(value = "Free-text reason associated with the order line candidate.")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String reason;

	@JsonCreator
	@Builder
	private JsonOLCand(
			@JsonProperty("id") final int id,
			@JsonProperty("externalLineId") final String externalLineId,
			@JsonProperty("externalHeaderId") final String externalHeaderId,
			@JsonProperty("adIssueId") final JsonMetasfreshId adIssueId,
			@JsonProperty("poReference") final String poReference,
			@JsonProperty("org") final String orgCode,
			@JsonProperty("bpartner") final JsonResponseBPartnerLocationAndContact bpartner,
			@JsonProperty("billBPartner") final JsonResponseBPartnerLocationAndContact billBPartner,
			@JsonProperty("dropShipBPartner") final JsonResponseBPartnerLocationAndContact dropShipBPartner,
			@JsonProperty("handOverBPartner") final JsonResponseBPartnerLocationAndContact handOverBPartner,
			@JsonProperty("dateOrdered") final LocalDate dateOrdered,
			@JsonProperty("datePromised") final LocalDate datePromised,
			@JsonProperty("dateCandidate") final LocalDate dateCandidate,
			@JsonProperty("flatrateConditionsId") final int flatrateConditionsId,
			@JsonProperty("productId") final int productId,
			@JsonProperty("productDescription") final String productDescription,
			@JsonProperty("qty") final BigDecimal qty,
			@JsonProperty("uomId") final int uomId,
			@JsonProperty("qtyItemCapacity") final BigDecimal qtyItemCapacity,
			@JsonProperty("huPIItemProductId") final int huPIItemProductId,
			@JsonProperty("pricingSystemId") final int pricingSystemId,
			@JsonProperty("price") final BigDecimal price,
			@JsonProperty("discount") final BigDecimal discount,
			@JsonProperty("warehouseDestId") final int warehouseDestId,
			@JsonProperty("jsonOrderLineGroup") final JsonOrderLineGroup jsonOrderLineGroup,
			@JsonProperty("description") final String description,
			@JsonProperty("line") final Integer line,
			@JsonProperty("extendedProps") final @Nullable Map<String, Object> extendedProps,
			@JsonProperty("promotionCode") final @Nullable String promotionCode,
			@JsonProperty("promotionCode2") final @Nullable String promotionCode2,
			@JsonProperty("isWithoutCharge") final @Nullable Boolean isWithoutCharge,
			@JsonProperty("reason") final @Nullable String reason)
	{
		this.id = id;
		this.externalLineId = externalLineId;
		this.externalHeaderId = externalHeaderId;
		this.adIssueId = adIssueId;
		this.poReference = poReference;
		this.orgCode = orgCode;
		this.bpartner = bpartner;
		this.billBPartner = billBPartner;
		this.dropShipBPartner = dropShipBPartner;
		this.handOverBPartner = handOverBPartner;
		this.dateOrdered = dateOrdered;
		this.datePromised = datePromised;
		this.dateCandidate = dateCandidate;
		this.flatrateConditionsId = flatrateConditionsId;
		this.productId = productId;
		this.productDescription = productDescription;
		this.qty = qty;
		this.uomId = uomId;
		this.qtyItemCapacity = qtyItemCapacity;
		this.huPIItemProductId = huPIItemProductId;
		this.pricingSystemId = pricingSystemId;
		this.price = price;
		this.discount = discount;
		this.warehouseDestId = warehouseDestId;
		this.jsonOrderLineGroup = jsonOrderLineGroup;
		this.description = description;
		this.line = line;
		this.extendedProps = extendedProps;
		this.promotionCode = promotionCode;
		this.promotionCode2 = promotionCode2;
		this.isWithoutCharge = isWithoutCharge;
		this.reason = reason;
	}

}
