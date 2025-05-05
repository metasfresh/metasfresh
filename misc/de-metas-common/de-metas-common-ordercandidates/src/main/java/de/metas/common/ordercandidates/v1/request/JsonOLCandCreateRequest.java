package de.metas.common.ordercandidates.v1.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.bpartner.v1.request.JsonRequestBPartner;
import de.metas.common.bpartner.v1.request.JsonRequestContact;
import de.metas.common.bpartner.v1.request.JsonRequestLocation;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonDocTypeInfo;
import de.metas.common.rest_api.v1.SwaggerDocConstants;
import de.metas.common.rest_api.v1.SyncAdvise;
import de.metas.common.util.Check;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
 */
@Value
public class JsonOLCandCreateRequest
{
	JsonOrganization org;

	@Schema(minLength = 1,
			description = "This translates to 'C_OLCand.externalLineId'.\n"
					+ "'externalLineId' and 'dataSource' together need to be unique.")
	String externalLineId;

	@Schema(minLength = 1,
			description = "This translates to 'C_OLCand.externalHeaderId'.\n"
					+ " 'externalHeaderId'  and 'dataSource' together denote the unique group of olCands that were added in one bulk.")
	String externalHeaderId;

	@Schema(minLength = 1, //
			description = "Identifier of the `AD_InputDataSource` record that tells where this OLCand came from.\n" + SwaggerDocConstants.DATASOURCE_IDENTIFIER_DOC)
	String dataSource;

	@Schema(minLength = 1, //
			description = "Identifier of the `AD_InputDataSource` record that tells what shall be happen with this OLCand.\n" + SwaggerDocConstants.DATASOURCE_IDENTIFIER_DOC)
	String dataDest;

	@Schema(description = " This translates to `C_OLCand.C_BPartner_ID`, `C_OLCand.C_BPartner_Location_ID` and `C_OLCand.AD_User_ID`.\n"
					+ "It's the business partner that places/placed the order which this candidate is about.\n"
					+ "\n"
					+ "Note that the given partner's *location* can also be left empty, if the partner can be found in metasfresh and has an address there.\n"
					+ "If there are multiple adresses, the default shipTo address is preferred.")
	JsonRequestBPartnerLocationAndContact bpartner;

	@Schema(description = " This translates to `C_OLCand.Bill_BPartner_ID`.\n"
					+ "It's the business partner that shall receive the invoice.\n"
					+ "Optional; if empty, then `bpartner` will receive the invoice.")
	@JsonInclude(Include.NON_NULL)
	JsonRequestBPartnerLocationAndContact billBPartner;

	@Schema(description = " This translates to `C_OLCand.Dropship_BPartner_ID`.\n"
					+ "It's the business partner that shall receive the shipment.\n"
					+ "Optional; if empty, then `bpartner` will receive the shipment.")
	@JsonInclude(Include.NON_NULL)
	JsonRequestBPartnerLocationAndContact dropShipBPartner;

	@Schema(description = " This translates to `C_OLCand.HandOver_BPartner_ID`.\n"
					+ "It's an intermediate partner that shall receive the shipment and forward it to the eventual recipient.\n"
					+ "Optional; if empty, then `dropShipBPartner` or `bpartner` will directly receive the shipment.")
	@JsonInclude(Include.NON_NULL)
	JsonRequestBPartnerLocationAndContact handOverBPartner;

	@Schema(description = "This translates to `C_OLCand.DateOrdered`.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate dateOrdered;

	@Schema(description = "This translates to `C_OLCand.datePromised`.\n"
					+ "It's the date that the external system's user would like the metasfresh user to promise for delivery.\n"
					+ "Note: may be empty, if `dataDestInternalName='DEST.de.metas.invoicecandidate'`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateRequired;

	int flatrateConditionsId;

	@Schema(description = "This translates to `C_OLCand.M_Product_ID`.")
	JsonProductInfo product;

	@JsonInclude(Include.NON_NULL)
	String productDescription;

	BigDecimal qty;

	@Schema(description = "This translates to `C_UOM.X12DE355`.\n"
					+ "The respective UOM needs to exist in metasfresh and its ID is set as `C_OLCand.C_UOM_ID`.\n"
					+ "If not provided here, then the respective product's UOM is used instead.\n"
					+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the `product`'s UOM")
	@JsonInclude(Include.NON_NULL)
	String uomCode;

	@Schema(description = "This translates to `C_OLCand.M_HU_PI_Item_Product_ID`.")
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId packingMaterialId;

	@Schema(description = "If a new product price needs to be created on the fly and the system can't deduce the respective pricing system from given business partner,\n"
			+ "then we need this property to specify the `M_PricingSystem.Value` of the pricing system to work with.\n\n"
			+ "Also note that:\n"
			+ "- you should avoid white-spaces in the value string"
			+ "- the pricing system needs to have a price list that matches the BPartner's country and that has a default tax category to be used the creating the new price."
			+ "- if a new business partner is created on the fly, the default business partner group (to which the new BPartner is added) needs to have this pricing system set; otherwise the order line candidate will be created, but can't be processed.")
	@JsonInclude(Include.NON_NULL)
	String pricingSystemCode;

	@Schema(description = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) price.\n"
					+ "If the price has too many digits, it is rounded according to the price list's price precision.")
	@JsonInclude(Include.NON_NULL)
	BigDecimal price;

	@Schema(description = "If a (manual) `price` is provided, then also a currencyCode needs be given.")
	@JsonInclude(Include.NON_NULL)
	String currencyCode; // shall come from pricingSystem/priceList

	@Schema(description = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) discount.")
	@JsonInclude(Include.NON_NULL)
	BigDecimal discount;

	@Schema(minLength = 1, description = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter records using it")
	String poReference;

	@Schema(description = "Translates to `M_Warehouse.Value`. The looked up warehouse's ID is then set to `C_OLCand.M_Warehouse_ID`.")
	String warehouseCode;

	@Schema(description = "Translates to `C_OLCand.M_Warehouse_Dest_ID`.")
	String warehouseDestCode;

	@Schema(description = "Can be set if the invoice's document type is already known from the external system and shall be forwarded to the invoice candidate.\\n\""
					+ "This works only if not an order line but an invoice candidate is directly created for the respective order line candidate.\n"
					+ "Therefore, please make sure to have `dataDestInternalName='DEST.de.metas.invoicecandidate'`.\n"
					+ "Otherwise, this property will be ignored\n"
					+ "\n"
					+ "Note for healthcare-ch users: set `docBaseType` to `ARI` (sales invoice) "
					+ "and `docSubType` to one of `EA` (\"Patient\"), `GM` (\"Gemeinde\" or `KV` (\"Krankenversicherung\"")
	@JsonInclude(Include.NON_NULL)
	JsonDocTypeInfo invoiceDocType;

	@Schema(description = "Can be set if the invoice's document date is already known from the external system. ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate presetDateInvoiced;

	@Schema(description = "Can be set if the shipment's document date is already known from the external system. ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate presetDateShipped;

	@Schema(description = "Specifies if the created order will be a normal Sales Order or a Prepaid Sales Order")
	@JsonInclude(Include.NON_NULL)
	OrderDocType orderDocType;

	@Schema(description = "Specifies the payment rule that will propagate to the created order")
	@JsonInclude(Include.NON_NULL)
	JSONPaymentRule paymentRule;

	@Schema(description = "Specifies the SalesPartnerCode for the partner that will propagate as sales rep to the created order")
	@JsonInclude(Include.NON_NULL)
	String salesPartnerCode;

	@Schema(description = "Specifies the value for the shipper that will propagate to the created order")
	@JsonInclude(Include.NON_NULL)
	String shipper;

	@Schema(description = "Specifies the value or the externalId of the payment term that will propagate to the created order")
	@JsonInclude(Include.NON_NULL)
	String paymentTerm;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonOLCandCreateRequest(
			@JsonProperty("org") final JsonOrganization org,
			@JsonProperty("externalLineId") final String externalLineId,
			@JsonProperty("externalHeaderId") final String externalHeaderId,
			@JsonProperty("dataSource") final @NonNull String dataSource,
			@JsonProperty("dataDest") final @Nullable String dataDest,
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
			@JsonProperty("packingMaterialId") final JsonMetasfreshId packingMaterialId,
			@JsonProperty("pricingSystemCode") final String pricingSystemCode,
			@JsonProperty("price") final @Nullable BigDecimal price,
			@JsonProperty("currencyCode") final @Nullable String currencyCode,
			@JsonProperty("discount") final @Nullable BigDecimal discount,
			@JsonProperty("poReference") final @NonNull String poReference,
			@JsonProperty("warehouseCode") final @Nullable String warehouseCode,
			@JsonProperty("warehouseDestCode") final @Nullable String warehouseDestCode,
			@JsonProperty("invoiceDocType") final @Nullable JsonDocTypeInfo invoiceDocType,
			@JsonProperty("presetDateInvoiced") final @Nullable LocalDate presetDateInvoiced,
			@JsonProperty("presetDateShipped") final @Nullable LocalDate presetDateShipped,
			@JsonProperty("orderDocType") final @Nullable OrderDocType orderDocType,
			@JsonProperty("paymentRule") final @Nullable JSONPaymentRule paymentRule,
			@JsonProperty("salesPartnerCode") final @Nullable String salesPartnerCode,
			@JsonProperty("shipper") final @Nullable String shipper,
			@JsonProperty("paymentTerm") final @Nullable String paymentTerm)
	{
		this.org = org;
		this.externalLineId = externalLineId;
		this.externalHeaderId = externalHeaderId;
		this.dataSource = dataSource;
		this.dataDest = dataDest;
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
		this.warehouseCode = warehouseCode;
		this.invoiceDocType = invoiceDocType;
		this.presetDateInvoiced = presetDateInvoiced;
		this.presetDateShipped = presetDateShipped;

		this.orderDocType = orderDocType;
		this.paymentRule = paymentRule;
		this.salesPartnerCode = salesPartnerCode;
		this.shipper = shipper;

		this.paymentTerm = paymentTerm;
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
		validateBPartner("bpartner", bpartner);
		validateBPartner("billBPartner", billBPartner);
		validateBPartner("handOverBPartner", handOverBPartner);
		validateBPartner("dropShipBPartner", dropShipBPartner);

		if (price != null)
		{
			Check.assumeNotNull(currencyCode,
								"currencyCode may not be null, if price is set; this={}",
								this);
		}
		return this;
	}

	private void validateBPartner(
			@NonNull final String propertyName,
			@Nullable final JsonRequestBPartnerLocationAndContact bpartnerLocationAndContact)
	{
		if (bpartnerLocationAndContact == null)
		{
			return;
		}

		final JsonRequestBPartner bpartner = bpartnerLocationAndContact.getBpartner();
		if (bpartner != null && bpartner.getSyncAdvise() != null)
		{ // note: bpartner==null can be OK to; lookup can be done via location
			Check.assume(!bpartner.getSyncAdvise().getIfExists().isUpdateRemove(),
						 "{} has bpartner/syncAdvise/ifExists/UPDATE_REMOVE which is not allowed in OLCandCeateRequests", propertyName);
		}
		final JsonRequestLocation location = bpartnerLocationAndContact.getLocation();
		if (location != null && location.getSyncAdvise() != null)
		{
			Check.assume(!location.getSyncAdvise().getIfExists().isUpdateRemove(),
						 "{} has location/syncAdvise/ifExists/UPDATE_REMOVE which is not allowed in OLCandCeateRequests", propertyName);
		}
		final JsonRequestContact contact = bpartnerLocationAndContact.getContact();
		if (contact != null && contact.getSyncAdvise() != null)
		{
			Check.assume(!location.getSyncAdvise().getIfExists().isUpdateRemove(),
						 "{} has contact/syncAdvise/ifExists/UPDATE_REMOVE which is not allowed in OLCandCeateRequests", propertyName);
		}
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

	/**
	 * Creates an instance with the given {@code syncAdvise} in all bPartners.
	 */
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

	@Schema(enumAsRef = true, description = "OrderDocType: \n" +
			"* `SalesOrder` - Specifies if the order will be a standard one. A standard order will be created if no DocTYpe is specified.\n" +
			"* `PrepayOrder` - Specifies if the order will be prepaid\n" +
			"")
	public enum OrderDocType
	{
		SalesOrder("SalesOrder"),

		PrepayOrder("PrepayOrder");

		@Getter
		private final String code;

		OrderDocType(final String code)
		{
			this.code = code;
		}
	}
}
