package de.metas.rest_api.invoicecandidates.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.BPARTER_IDENTIFIER_DOC;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.*;
import static de.metas.rest_api.bpartner.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	@ApiModelProperty(position = 10, dataType = "java.lang.String", required = false,//
			value = "Needs to be set if the invoice candidate shall be created.")
	JsonExternalId exterhalHeaderId;

	@ApiModelProperty(position = 20, dataType = "java.lang.String", required = false,//
			value = "Needs to be set if the invoice candidate shall be created.")
	JsonExternalId exterhalLineId;

	@ApiModelProperty(position = 30, dataType = "java.lang.Long", required = false,//
			value = "Needs to be empty if the invoice candidate shall be created.\n"
					+ "If specified, then\n"
					+ "- the respective `C_Invoice_candidate` record has to exist\n"
					+ "- all other properties including the externalIds may be updated, depending on the request's `syncAdvise`.")
	MetasfreshId metasfreshId;

	@ApiModelProperty(position = 40, required = false,//
			value = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter recrods using it")
	String poReference;

	@ApiModelProperty(position = 50, required = false, value = BPARTER_IDENTIFIER_DOC + "\n Needs to be set if the invoice candidate shall be created")
	String billPartnerIdentifier;

	@ApiModelProperty(position = 60, required = false, value = LOCATION_IDENTIFIER_DOC + "\n Needs to be set if the invoice candidate shall be created")
	String billLocationIdentifier;

	@ApiModelProperty(position = 70, required = false, value = CONTACT_IDENTIFIER_DOC)
	String billContactIdentifier;

	@ApiModelProperty(position = 80, required = false, value = PRODUCTIDENTIFIER_DOC + "\n Needs to be set if the invoice candidate shall be created")
	String productIdentifier;

	@ApiModelProperty(position = 90, required = false, value = "This translates to `C_UOM.X12DE355`.\n"
			+ "The respective UOM needs to exist in metasfresh and its ID is set as <code>C_OLCand.C_UOM_ID</code>.\n"
			+ "If not set, then the respective product's UOM is used.\n"
			+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the <code>product</code>'s UOM")
	String uomCode;

	@ApiModelProperty(position = 100, required = true, //
			value = "Specifies if this invoice candidate is about a sales- or purchase-transaction."
					+ "\n Needs to be set if the invoice candidate shall be created")
	JsonSOTrx soTrx;

	@ApiModelProperty(position = 110, required = false,//
			value = "Can be set if the invoice's target document type is already known from the external system.\n"
					+ "If specified, the respective doctype needs to be consistent with this instance's `soTrx` value.")
	JsonDocTypeInfo invoiceDocType;

	@ApiModelProperty(position = 120, required = false)
	JsonInvoiceRule invoiceRule;

	@ApiModelProperty(position = 130, required = false, //
			value = "Needs to be set if the invoice candidate shall be created")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateOrdered;

	@ApiModelProperty(position = 140, required = false, //
			value = "Needs to be set if the invoice candidate shall be created")
	BigDecimal qtyOrdered;

	@ApiModelProperty(position = 150, required = false)
	BigDecimal qtyDelivered;

	@ApiModelProperty(position = 150, required = false, //
			value = "Can be set if the invoice's document date is already known from the external system.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate presetDateInvoiced;

	@ApiModelProperty(position = 160, required = false, //
			value = "Optional value to override the price as computed by metasfresh's own pricing engine for the respective invoice candidate.")
	BigDecimal priceEnteredOverride;

	@ApiModelProperty(position = 170, required = false, //
			value = "Optional value to override the discount as computed by metasfresh's own pricing engine for the respective invoice candidate.")
	BigDecimal priceDiscountOverride;

	@ApiModelProperty(position = 180, required = false, value = PARENT_SYNC_ADVISE_DOC)
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
			@JsonProperty("synchAdvise") @Nullable final SyncAdvise synchAdvise)
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
