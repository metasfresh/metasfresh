package de.metas.rest_api.invoicecandidates.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v1.JsonDocTypeInfo;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.JsonInvoiceRule;
import de.metas.common.rest_api.v1.JsonPrice;
import de.metas.common.rest_api.v1.JsonSOTrx;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static de.metas.common.rest_api.v1.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v1.SwaggerDocConstants.CONTACT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v1.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v1.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class JsonCreateInvoiceCandidatesRequestItem
{
	@ApiModelProperty(position = 10, required = false, //
			value = "Optional, to specify the `AD_Org_ID` for a new invoice candidate.\n"
					+ "This property needs to be set to the `AD_Org.Value` of an organisation that the invoking user is allowed to access\n"
					+ "or the invoking user needs to belong to an organisation, which is then used.")
	String orgCode;

	@ApiModelProperty(position = 20, dataType = "java.lang.String", required = true, //
			value = "Needs to be set if the invoice candidate shall be created.")
	JsonExternalId externalHeaderId;

	@ApiModelProperty(position = 30, dataType = "java.lang.String", required = true, //
			value = "Needs to be set if the invoice candidate shall be created.")
	JsonExternalId externalLineId;

	@ApiModelProperty(position = 40, required = false, //
			value = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter recrods using it")
	String poReference;

	@ApiModelProperty(position = 50, required = true, //
			value = BPARTNER_IDENTIFIER_DOC)
	String billPartnerIdentifier;

	@ApiModelProperty(position = 60, required = true, //
			value = LOCATION_IDENTIFIER_DOC)
	String billLocationIdentifier;

	@ApiModelProperty(position = 70, required = false, //
			value = CONTACT_IDENTIFIER_DOC)
	String billContactIdentifier;

	@ApiModelProperty(position = 80, required = true, //
			value = PRODUCT_IDENTIFIER_DOC)
	String productIdentifier;

	@ApiModelProperty(position = 130, required = false, //
			value = "Optional, if not specified, then the respective current date is used", example = "2019-11-08")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateOrdered;

	@ApiModelProperty(position = 140, required = true)
	BigDecimal qtyOrdered;

	@ApiModelProperty(position = 150, required = false, //
			value = "Optional, if not specified, zero is assumed")
	BigDecimal qtyDelivered;

	@ApiModelProperty(position = 90, required = false, //
			value = "Unit of measurement for the ordered, delivered and invoiced quantites\n"
					+ "This translates to `C_UOM.X12DE355`.\n"
					+ "The respective UOM needs to exist in metasfresh and its ID is set as `C_Invoice_candidate.C_UOM_ID`.\n"
					+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the `product`'s UOM")
	String uomCode;

	@ApiModelProperty(position = 100, required = true, //
			value = "Specifies if this invoice candidate is about a sales- or purchase-transaction.")
	JsonSOTrx soTrx;

	@ApiModelProperty(position = 110, required = false,//
			value = "Can be set if the invoice's target document type is already known from the external system.\n"
					+ "If specified, the respective doctype needs to be consistent with this instance's `soTrx` value.")
	JsonDocTypeInfo invoiceDocType;

	@ApiModelProperty(position = 120, required = false)
	JsonInvoiceRule invoiceRuleOverride;

	@ApiModelProperty(position = 150, required = false, //
			value = "Can be set if the invoice's document date is already known from the external system.", example = "2019-11-08")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate presetDateInvoiced;

	@ApiModelProperty(position = 160, required = false, //
			value = "Optional, to override the price-entered (price per unit before discount) as computed by metasfresh's own pricing engine for the respective invoice candidate.\n"
					+ "Note that if this is set, then the currency needs to match currency derived by metasfresh's pricing engine.")
	JsonPrice priceEnteredOverride;

	@ApiModelProperty(position = 170, required = false, //
			value = "Optional, to override the discount as computed by metasfresh's own pricing engine for the respective invoice candidate")
	BigDecimal discountOverride;

	@ApiModelProperty(position = 180, required = false, //
			value = "optional invoice line description")
	String lineDescription;

	@ApiModelProperty(position = 190, required = false, //
			value = "Optional invoice detail items. Will be persisted as `C_Invoice_Detail` records together with the new invoice candidate.")
	List<JSONInvoiceDetailItem> invoiceDetailItems;

	@JsonCreator
	@Builder
	private JsonCreateInvoiceCandidatesRequestItem(
			@JsonProperty("orgCode") @Nullable final String orgCode,
			@JsonProperty("externalHeaderId") @NonNull final JsonExternalId externalHeaderId,
			@JsonProperty("externalLineId") @NonNull final JsonExternalId externalLineId,
			@JsonProperty("poReference") @Nullable final String poReference,
			@JsonProperty("billPartnerIdentifier") @NonNull final String billPartnerIdentifier,
			@JsonProperty("billLocationIdentifier") @Nullable final String billLocationIdentifier,
			@JsonProperty("billContactIdentifier") @Nullable final String billContactIdentifier,
			@JsonProperty("productIdentifier") @NonNull final String productIdentifier,
			@JsonProperty("soTrx") @NonNull final JsonSOTrx soTrx,
			@JsonProperty("invoiceDocType") @Nullable final JsonDocTypeInfo invoiceDocType,
			@JsonProperty("invoiceRuleOverride") @Nullable final JsonInvoiceRule invoiceRuleOverride,
			@JsonProperty("presetDateInvoiced") @Nullable final LocalDate presetDateInvoiced,
			@JsonProperty("dateOrdered") @Nullable final LocalDate dateOrdered,
			@JsonProperty("qtyOrdered") @NonNull final BigDecimal qtyOrdered,
			@JsonProperty("qtyDelivered") @Nullable final BigDecimal qtyDelivered,
			@JsonProperty("uomCode") @Nullable final String uomCode,
			@JsonProperty("priceEnteredOverride") @Nullable final JsonPrice priceEnteredOverride,
			@JsonProperty("discountOverride") @Nullable final BigDecimal discountOverride,
			@JsonProperty("lineDescription") @Nullable final String lineDescription,
			@JsonProperty("invoiceDetailItems") @Nullable @Singular final List<JSONInvoiceDetailItem> invoiceDetailItems)
	{
		this.orgCode = orgCode;
		this.externalHeaderId = externalHeaderId;
		this.externalLineId = externalLineId;
		this.poReference = poReference;
		this.billPartnerIdentifier = billPartnerIdentifier;
		this.billLocationIdentifier = billLocationIdentifier;
		this.billContactIdentifier = billContactIdentifier;
		this.productIdentifier = productIdentifier;
		this.soTrx = soTrx;
		this.invoiceDocType = invoiceDocType;
		this.invoiceRuleOverride = invoiceRuleOverride;
		this.presetDateInvoiced = presetDateInvoiced;
		this.dateOrdered = dateOrdered;
		this.qtyOrdered = qtyOrdered;
		this.qtyDelivered = qtyDelivered;
		this.uomCode = uomCode;
		this.priceEnteredOverride = priceEnteredOverride;
		this.discountOverride = discountOverride;
		this.lineDescription = lineDescription;
		this.invoiceDetailItems = invoiceDetailItems;
	}
}
