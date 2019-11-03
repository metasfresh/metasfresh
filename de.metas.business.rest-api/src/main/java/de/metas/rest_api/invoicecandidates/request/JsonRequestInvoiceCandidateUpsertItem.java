package de.metas.rest_api.invoicecandidates.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTER_IDENTIFIER_DOC;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.*;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.JsonDocTypeInfo;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.JsonInvoiceRule;
import de.metas.rest_api.JsonSOTrx;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

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
public class JsonRequestInvoiceCandidateUpsertItem
{
	JsonExternalId exterhalHeaderId;

	JsonExternalId exterhalLineId;

	MetasfreshId metasfreshId;

	@ApiModelProperty(value = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter recrods using it")
	String poReference;

	@ApiModelProperty(required = false, value = BPARTER_IDENTIFIER_DOC + "\n needs to be set if the invoice candidate shall be created")
	String billPartnerIdentifier;

	@ApiModelProperty(required = false, value = LOCATION_IDENTIFIER_DOC + "\n needs to be set if the invoice candidate shall be created")
	String billLocationIdentifier;

	@ApiModelProperty(required = false, value = CONTACT_IDENTIFIER_DOC)
	String billContactIdentifier;

	@ApiModelProperty(required = false, value = PRODUCTIDENTIFIER_DOC)
	String productIdentifier;

	@ApiModelProperty(required = true, value = "Specified if this invoice candidate is about a sales- or purchase-transactions")
	JsonSOTrx soTrx;

	@ApiModelProperty(value = "Can be set if the invoice's document type is already known from the external system.\n"
			+ "If specified, the respective doctype needs to be consistent with this instance's `soTrx` value.")
	@JsonInclude(Include.NON_NULL)
	JsonDocTypeInfo invoiceDocType;

	JsonInvoiceRule invoiceRule;

	@ApiModelProperty(value = "Can be set if the invoice's document date is already known from the external system.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate presetDateInvoiced;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(required = true)
	LocalDate dateOrdered;

	BigDecimal qtyOrdered;

	BigDecimal qtyDelivered;

	@ApiModelProperty(value = "This translates to `C_UOM.X12DE355`.\n"
			+ "The respective UOM needs to exist in metasfresh and its ID is set as <code>C_OLCand.C_UOM_ID</code>.\n"
			+ "If not set, then the respective product's UOM is used.\n"
			+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the <code>product</code>'s UOM")
	@JsonInclude(Include.NON_NULL)
	String uomCode;

	@JsonInclude(Include.NON_NULL)
	BigDecimal priceEnteredOverride;

	@JsonInclude(Include.NON_NULL)
	BigDecimal priceDiscountOverride;

	@ApiModelProperty(PARENT_SYNC_ADVISE_DOC)
	@JsonInclude(Include.NON_NULL)
	SyncAdvise synchAdvise;

	@JsonCreator
	private JsonRequestInvoiceCandidateUpsertItem(
			@JsonProperty("exterhalHeaderId") final JsonExternalId exterhalHeaderId,
			@JsonProperty("exterhalLineId") final JsonExternalId exterhalLineId,
			@JsonProperty("metasfreshId") final MetasfreshId metasfreshId,
			@JsonProperty("poReference") final String poReference,
			@JsonProperty("billPartnerIdentifier") final String billPartnerIdentifier,
			@JsonProperty("billLocationIdentifier") final String billLocationIdentifier,
			@JsonProperty("billContactIdentifier") final String billContactIdentifier,
			@JsonProperty("productIdentifier") final String productIdentifier,
			@JsonProperty("soTrx") final JsonSOTrx soTrx,
			@JsonProperty("invoiceDocType") final JsonDocTypeInfo invoiceDocType,
			@JsonProperty("invoiceRule") final JsonInvoiceRule invoiceRule,
			@JsonProperty("presetDateInvoiced") final LocalDate presetDateInvoiced,
			@JsonProperty("dateOrdered") final LocalDate dateOrdered,
			@JsonProperty("qtyOrdered") final BigDecimal qtyOrdered,
			@JsonProperty("qtyDelivered") final BigDecimal qtyDelivered,
			@JsonProperty("uomCode") final String uomCode,
			@JsonProperty("priceEnteredOverride") final BigDecimal priceEnteredOverride,
			@JsonProperty("priceDiscountOverride") final BigDecimal priceDiscountOverride,
			@JsonProperty("synchadvise") @Nullable final SyncAdvise synchAdvise)
	{
		this.exterhalHeaderId = exterhalHeaderId;
		this.exterhalLineId = exterhalLineId;
		this.metasfreshId = metasfreshId;
		this.poReference = poReference;
		this.billPartnerIdentifier = billPartnerIdentifier;
		this.billLocationIdentifier = billLocationIdentifier;
		this.billContactIdentifier = billContactIdentifier;
		this.productIdentifier = productIdentifier;
		this.soTrx = soTrx;
		this.invoiceDocType = invoiceDocType;
		this.invoiceRule = invoiceRule;
		this.presetDateInvoiced = presetDateInvoiced;
		this.dateOrdered = dateOrdered;
		this.qtyOrdered = qtyOrdered;
		this.qtyDelivered = qtyDelivered;
		this.uomCode = uomCode;
		this.priceEnteredOverride = priceEnteredOverride;
		this.priceDiscountOverride = priceDiscountOverride;
		this.synchAdvise = synchAdvise;
	}
}
