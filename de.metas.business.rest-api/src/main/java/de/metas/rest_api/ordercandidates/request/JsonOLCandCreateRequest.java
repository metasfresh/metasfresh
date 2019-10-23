package de.metas.rest_api.ordercandidates.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.rest_api.SyncAdvise;
import de.metas.util.Check;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
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

/**
 * Specifies a single order line candidate for be created by the system
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public final class JsonOLCandCreateRequest
{
	JsonOrganization org;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "This translates to <code>C_OLCand.externalLineId</code>.\n"
					+ "<code>externalLineId</code> and <code>dataSourceInternalName</code> together need to be unique.")
	String externalLineId;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "This translates to <code>C_OLCand.externalHeaderId</code>.\n"
					+ "<code>externalHeaderId</code> and <code>dataSourceInternalName</code> together denote the unique group of olCands that were added in one bulk.")
	String externalHeaderId;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "Internal name of the <code>AD_InputDataSource</code> record that tells where this OLCand came from.")
	String dataSourceInternalName;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "Internal name of the <code>AD_InputDataSource</code> record that tells what shall be happen with this OLCand.")
	private String dataDestInternalName;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = " This translates to `C_OLCand.C_BPartner_ID`, `C_OLCand.C_BPartner_Location_ID` and `C_OLCand.AD_User_ID`.\n"
					+ "It's the business partner that places/placed the order which this candidate is about.\n"
					+ "\n"
					+ "Note that the given partner's *location* can also be left empty, if the partner can be found in metasfresh and has an address there.\n"
					+ "If there are multiple adresses, the default shipTo address is preferred.")
	private JsonRequestBPartnerLocationAndContact bpartner;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = " This translates to <code>C_OLCand.Bill_BPartner_ID</code>.\n"
					+ "It's the business partner that shall receive the invoice.\n"
					+ "Optional; if empty, then <code>bpartner</code> will receive the invoice.")
	@JsonInclude(Include.NON_NULL)
	private JsonRequestBPartnerLocationAndContact billBPartner;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = " This translates to <code>C_OLCand.Dropship_BPartner_ID</code>.\n"
					+ "It's the business partner that shall receive the shipment.\n"
					+ "Optional; if empty, then <code>bpartner</code> will receive the shipment.")
	@JsonInclude(Include.NON_NULL)
	private JsonRequestBPartnerLocationAndContact dropShipBPartner;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = " This translates to <code>C_OLCand.HandOver_BPartner_ID</code>.\n"
					+ "It's an intermediate partner that shall receive the shipment and forward it to the eventual recipient.\n"
					+ "Optional; if empty, then <code>dropShipBPartner</code> or <code>bpartner</code> will directly receive the shipment.")
	@JsonInclude(Include.NON_NULL)
	private JsonRequestBPartnerLocationAndContact handOverBPartner;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_OLCand.DateOrdered</code>.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dateOrdered;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "This translates to <code>C_OLCand.datePromised</code>.\n"
					+ "It's the date that the external system's user would like the metasfresh user to promise for delivery.\n"
					+ "Note: may be empty, if is <code>dataDestInternalName='DEST.de.metas.invoicecandidate'</code>")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dateRequired;

	private int flatrateConditionsId;

	@ApiModelProperty(value = "This translates to <code>C_OLCand.M_Product_ID</code>.")
	private JsonProductInfo product;

	@JsonInclude(Include.NON_NULL)
	private String productDescription;

	private BigDecimal qty;

	@ApiModelProperty(value = "This translates to <code>C_UOM.X12DE355</code>.\n"
			+ "The respective UOM needs to exist in metasfresh and its ID is set as <code>C_OLCand.C_UOM_ID</code>.\n"
			+ "If not set, then the respective product's UOM is used.\n"
			+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the <code>product</code>'s UOM")
	@JsonInclude(Include.NON_NULL)
	private String uomCode;

	private int packingMaterialId;

	@ApiModelProperty(value = "If a new product price needs to be created on the fly and the system can't deduce the respective pricing system from given business partner,\n"
			+ "then we need this property to specify the `M_PricingSystem.Value` of the pricing system to work with.\n\n"
			+ "Also note that:n"
			+ "- the pricing system also needs to have a price list that matches the BPartner's country and that has a default tax category to be used the creating the new price."
			+ "- if a new business partner is created on the fly, the detault business partner group (to which the new BPartner is added) needs to have this pricing system set; otherwise the order line candidate will be created, but can't be processed.")
	@JsonInclude(Include.NON_NULL)
	private String pricingSystemCode;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) price.")
	@JsonInclude(Include.NON_NULL)
	private BigDecimal price;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "If a (manual) <code>price</code> is provided, then also a currencyCode needs be given.")
	@JsonInclude(Include.NON_NULL)
	private String currencyCode; // shall come from pricingSystem/priceList

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) discount.")
	@JsonInclude(Include.NON_NULL)
	private BigDecimal discount;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			value = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter recrods using it")
	private String poReference;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Translates to C_OLCand.M_Warehouse_Dest_ID.")
	private String warehouseDestCode;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Can be set if the invoice's document type is already known from the external system and shall be forwarded to the invoice candidate.\\n\""
					+ "This works only if not an order line but an invoice candidate is directly created for the respective order line candidate.\n"
					+ "Therefore, please make sure to have <code>dataDestInternalName='DEST.de.metas.invoicecandidate'</code>.\n"
					+ "Otherwise, this property will be ignored\n"
					+ "\n"
					+ "Note for healthcare-ch users: set <code>docBaseType</code> to <code>ARI</code> (sale sinvoice) "
					+ "and <code>docSubType</code> to one of <code>EA</code> (\"Patient\"), <code>GM</code> (\"Gemeinde\" or <code>KV</code> (\"Krankenversicherung\"")
	@JsonInclude(Include.NON_NULL)
	private JsonDocTypeInfo invoiceDocType;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Can be set if the invoice's document date is already known from the external system. ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate presetDateInvoiced;

	@ApiModelProperty( //
			allowEmptyValue = true, //
			value = "Can be set if the shipment's document date is already known from the external system. ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	private LocalDate presetDateShipped;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonOLCandCreateRequest(
			@JsonProperty("org") final JsonOrganization org,
			@JsonProperty("externalLineId") final String externalLineId,
			@JsonProperty("externalHeaderId") final String externalHeaderId,
			@JsonProperty("dataSourceInternalName") final @NonNull String dataSourceInternalName,
			@JsonProperty("dataDestInternalName") final @Nullable String dataDestInternalName,
			@JsonProperty("bpartner") final JsonRequestBPartnerLocationAndContact bpartner,
			@JsonProperty("billBPartner") final JsonRequestBPartnerLocationAndContact billBPartner,
			@JsonProperty("dropShipBPartner") final JsonRequestBPartnerLocationAndContact dropShipBPartner,
			@JsonProperty("handOverBPartner") final JsonRequestBPartnerLocationAndContact handOverBPartner,
			@JsonProperty("dateOrdered") final @Nullable LocalDate dateOrdered,
			@JsonProperty("dateRequired") final LocalDate dateRequired,
			@JsonProperty("flatrateConditionsId") final int flatrateConditionsId,
			@JsonProperty("product") final JsonProductInfo product,
			@JsonProperty("productDescription") final @Nullable String productDescription,
			@JsonProperty("qty") final BigDecimal qty,
			@JsonProperty("uomCode") final @Nullable String uomCode,
			@JsonProperty("packingMaterialId") final int packingMaterialId,
			@JsonProperty("pricingSystemCode") final String pricingSystemCode,
			@JsonProperty("price") final @Nullable BigDecimal price,
			@JsonProperty("currencyCode") final @Nullable String currencyCode,
			@JsonProperty("discount") final @Nullable BigDecimal discount,
			@JsonProperty("poReference") final @NonNull String poReference,
			@JsonProperty("warehouseDestCode") final @Nullable String warehouseDestCode,
			@JsonProperty("invoiceDocType") final @Nullable JsonDocTypeInfo invoiceDocType,
			@JsonProperty("presetDateInvoiced") final @Nullable LocalDate presetDateInvoiced,
			@JsonProperty("presetDateShipped") final @Nullable LocalDate presetDateShipped)
	{
		this.org = org;
		this.externalLineId = externalLineId;
		this.externalHeaderId = externalHeaderId;
		this.dataSourceInternalName = dataSourceInternalName;
		this.dataDestInternalName = dataDestInternalName;
		this.bpartner = bpartner;
		this.billBPartner = billBPartner;
		this.dropShipBPartner = dropShipBPartner;
		this.handOverBPartner = handOverBPartner;
		this.dateOrdered = dateOrdered;
		this.dateRequired = dateRequired;
		this.flatrateConditionsId = flatrateConditionsId;
		this.product = product;
		this.productDescription = productDescription;
		this.qty = qty;
		this.uomCode = uomCode;
		this.packingMaterialId = packingMaterialId;
		this.pricingSystemCode = pricingSystemCode;
		this.price = price;
		this.currencyCode = currencyCode;
		this.discount = discount;
		this.poReference = poReference;
		this.warehouseDestCode = warehouseDestCode;
		this.invoiceDocType = invoiceDocType;
		this.presetDateInvoiced = presetDateInvoiced;
		this.presetDateShipped = presetDateShipped;
	}

	/**
	 * Since we want to use {@code ..build().toBuilder()} to get copies if the builder,
	 * we have a number of mandatory fields which are not annotated with {@link NonNull}.
	 * Therefore we call this method to check each instance before it is actually used.
	 */
	public JsonOLCandCreateRequest validate()
	{
		Check.assumeNotNull(externalLineId, "externalLineId may not be null; this={}", this);
		Check.assumeNotNull(externalHeaderId, "externalHeaderId may not be null; this={}", this);
		Check.assumeNotNull(product, "product may not be null; this={}", this);
		Check.assumeNotNull(bpartner, "bpartner may not be null; this={}", this);

		if (!"DEST.de.metas.invoicecandidate".equals(dataDestInternalName)) // TODO extract constant
		{
			Check.assumeNotNull(dateRequired,
					"dateRequired may not be null, unless dataDestInternalName={}; this={}",
					"DEST.de.metas.invoicecandidate", this);
		}
		if (price != null)
		{
			Check.assumeNotNull(currencyCode,
					"currencyCode may not be null, if price is set; this={}",
					this);
		}
		return this;
	}

	public JsonOLCandCreateRequest withOrgSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		if (syncAdvise == null)
		{
			return this;
		}
		final JsonOLCandCreateRequestBuilder builder = toBuilder();
		if (org != null)
		{
			builder.org(org.toBuilder().syncAdvise(syncAdvise).build());
		}
		return builder.build();
	}

	/** Creates an instance with the given {@code syncAdvise} in all bPartners. */
	public JsonOLCandCreateRequest withBPartnersSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		if (syncAdvise == null)
		{
			return this;
		}

		final JsonOLCandCreateRequestBuilder builder = toBuilder();
		if (org != null && org.getBpartner() != null)
		{
			builder.org(org.toBuilder()
					.bpartner(org.getBpartner().toBuilder().syncAdvise(syncAdvise).build())
					.build());
		}
		if (billBPartner != null)
		{
			builder.billBPartner(billBPartner.toBuilder().syncAdvise(syncAdvise).build());
		}
		if (bpartner != null)
		{
			builder.bpartner(bpartner.toBuilder().syncAdvise(syncAdvise).build());
		}
		if (dropShipBPartner != null)
		{
			builder.dropShipBPartner(dropShipBPartner.toBuilder().syncAdvise(syncAdvise).build());
		}
		if (handOverBPartner != null)
		{
			builder.handOverBPartner(handOverBPartner.toBuilder().syncAdvise(syncAdvise).build());
		}
		return builder.build();
	}

	public JsonOLCandCreateRequest withProductsSyncAdvise(@Nullable final SyncAdvise syncAdvise)
	{
		if (syncAdvise == null)
		{
			return this;
		}

		return toBuilder()
				.product(getProduct().toBuilder().syncAdvise(syncAdvise).build())
				.build();
	}
}
