/*
 * #%L
 * de-metas-common-ordercandidates
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

package de.metas.common.ordercandidates.v2.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.ordercandidates.v2.request.alberta.JsonAlbertaOrderInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JSONPaymentRule;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import de.metas.common.rest_api.v2.SwaggerDocConstants;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PRODUCT_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.SHIPPER_IDENTIFIER_DOC;

/**
 * Specifies a single order line candidate for be created by the system
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class JsonOLCandCreateRequest
{
	// note that "position" is currently not supported; see https://github.com/springfox/springfox/issues/3391#issuecomment-700009836
	@ApiModelProperty(position = 10)
	String orgCode;

	@ApiModelProperty(position = 20, required = true, //
			value = "This translates to 'C_OLCand.externalLineId'.\n"
					+ "'externalLineId' and 'dataSource' together need to be unique.")
	String externalLineId;

	@ApiModelProperty(position = 30, required = true, //
			value = "This translates to 'C_OLCand.externalHeaderId'.\n"
					+ " 'externalHeaderId'  and 'dataSource' together denote the unique group of olCands that were added in one bulk.")
	String externalHeaderId;

	@ApiModelProperty(position = 40, required = true, //
			value = "Identifier of the `AD_InputDataSource` record that tells where this OLCand came from.\n" + SwaggerDocConstants.DATASOURCE_IDENTIFIER_DOC)
	String dataSource;

	@ApiModelProperty(position = 50, required = true, //
			value = "Identifier of the `AD_InputDataSource` record that tells what shall be happen with this OLCand.\n" + SwaggerDocConstants.DATASOURCE_IDENTIFIER_DOC)
	String dataDest;

	@ApiModelProperty(position = 60, //
			value = " This translates to `C_OLCand.C_BPartner_ID`, `C_OLCand.C_BPartner_Location_ID` and `C_OLCand.AD_User_ID`.\n"
					+ "It's the business partner that places/placed the order which this candidate is about.\n"
					+ "\n"
					+ "Note that the given partner's *location* can also be left empty, if the partner can be found in metasfresh and has an address there.\n"
					+ "If there are multiple adresses, the default shipTo address is preferred.")
	JsonRequestBPartnerLocationAndContact bpartner;

	@ApiModelProperty(position = 70, // //
			value = " This translates to `C_OLCand.Bill_BPartner_ID`.\n"
					+ "It's the business partner that shall receive the invoice.\n"
					+ "Optional; if empty, then `bpartner` will receive the invoice.")
	@JsonInclude(Include.NON_NULL)
	JsonRequestBPartnerLocationAndContact billBPartner;

	@ApiModelProperty(position = 80, //
			value = " This translates to `C_OLCand.Dropship_BPartner_ID`.\n"
					+ "It's the business partner that shall receive the shipment.\n"
					+ "Optional; if empty, then `bpartner` will receive the shipment.")
	@JsonInclude(Include.NON_NULL)
	JsonRequestBPartnerLocationAndContact dropShipBPartner;

	@ApiModelProperty(position = 90, //
			value = " This translates to `C_OLCand.HandOver_BPartner_ID`.\n"
					+ "It's an intermediate partner that shall receive the shipment and forward it to the eventual recipient.\n"
					+ "Optional; if empty, then `dropShipBPartner` or `bpartner` will directly receive the shipment.")
	@JsonInclude(Include.NON_NULL)
	JsonRequestBPartnerLocationAndContact handOverBPartner;

	@ApiModelProperty(position = 100, //
			value = "This translates to `C_OLCand.DateOrdered`.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate dateOrdered;

	@ApiModelProperty(position = 110, //
			value = "This translates to `C_OLCand.datePromised`.\n"
					+ "It's the date that the external system's user would like the metasfresh user to promise for delivery.\n"
					+ "Note: may be empty, if `dataDestInternalName='DEST.de.metas.invoicecandidate'`")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateRequired;

	@ApiModelProperty(position = 120, //
			value = "This translates to `C_OLCand.dateCandidate`.\n")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dateCandidate;

	@ApiModelProperty(position = 130)
	int flatrateConditionsId;

	@ApiModelProperty(position = 140, required = true, value = PRODUCT_IDENTIFIER_DOC)
	String productIdentifier;

	@ApiModelProperty(position = 150)
	@JsonInclude(Include.NON_NULL)
	String productDescription;

	@ApiModelProperty(position = 160, required = true)
	BigDecimal qty;

	@ApiModelProperty(position = 170, //
			value = "This translates to `C_UOM.X12DE355`.\n"
					+ "The respective UOM needs to exist in metasfresh and its ID is set as `C_OLCand.C_UOM_ID`.\n"
					+ "If not provided here, then the respective product's UOM is used instead.\n"
					+ "Note that if this is set, then there also needs to exist a UOM-conversion rule between this UOM and the `product`'s UOM")
	@JsonInclude(Include.NON_NULL)
	String uomCode;

	@ApiModelProperty(position = 180, //
			value = "This translates to `C_OLCand.M_HU_PI_Item_Product_ID`.")
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId packingMaterialId;

	@ApiModelProperty(position = 190, //
			value = "If a new product price needs to be created on the fly and the system can't deduce the respective pricing system from given business partner,\n"
			+ "then we need this property to specify the `M_PricingSystem.Value` of the pricing system to work with.\n\n"
			+ "Also note that:\n"
			+ "- you should avoid white-spaces in the value string"
			+ "- the pricing system needs to have a price list that matches the BPartner's country and that has a default tax category to be used the creating the new price."
			+ "- if a new business partner is created on the fly, the default business partner group (to which the new BPartner is added) needs to have this pricing system set; otherwise the order line candidate will be created, but can't be processed.")
	@JsonInclude(Include.NON_NULL)
	String pricingSystemCode;

	@ApiModelProperty(position = 200, //
			value = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) price.\n"
					+ "If the price has too many digits, it is rounded according to the price list's price precision.")
	@JsonInclude(Include.NON_NULL)
	BigDecimal price;

	@ApiModelProperty(position = 210, //
			value = "If a (manual) `price` is provided, then also a currencyCode needs be given.")
	@JsonInclude(Include.NON_NULL)
	String currencyCode; // shall come from pricingSystem/priceList

	@ApiModelProperty(position = 220, //
			value = "If set, then the order line candidate will be created with a manual (i.e. not coming from metasfresh) discount.")
	@JsonInclude(Include.NON_NULL)
	BigDecimal discount;

	@ApiModelProperty(position = 230, required = true, //
			value = "External reference (document number) on a remote system. Not neccesarily unique, but but the external user will want to filter recrods using it")
	String poReference;

	@ApiModelProperty(position = 240,  //
			value = "Translates to `M_Warehouse.Value`. The looked up warehouse's ID is then set to `C_OLCand.M_Warehouse_ID`.")
	String warehouseCode;

	@ApiModelProperty(position = 250,  //
			value = "Translates to `C_OLCand.M_Warehouse_Dest_ID`.")
	String warehouseDestCode;

	@ApiModelProperty(position = 260,  //
			value = "Can be set if the invoice's document type is already known from the external system and shall be forwarded to the invoice candidate.\\n\""
					+ "This works only if not an order line but an invoice candidate is directly created for the respective order line candidate.\n"
					+ "Therefore, please make sure to have `dataDestInternalName='DEST.de.metas.invoicecandidate'`.\n"
					+ "Otherwise, this property will be ignored\n"
					+ "\n"
					+ "Note for healthcare-ch users: set `docBaseType` to `ARI` (sales invoice) "
					+ "and `docSubType` to one of `EA` (\"Patient\"), `GM` (\"Gemeinde\" or `KV` (\"Krankenversicherung\"")
	@JsonInclude(Include.NON_NULL)
	JsonDocTypeInfo invoiceDocType;

	@ApiModelProperty(position = 270, //
			value = "Can be set if the invoice's document date is already known from the external system. ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate presetDateInvoiced;

	@ApiModelProperty(position = 280, //
			value = "Can be set if the shipment's document date is already known from the external system. ")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	LocalDate presetDateShipped;

	@ApiModelProperty(position = 290, value = "Specifies if the created order will be a normal Sales Order or a Prepaid Sales Order")
	@JsonInclude(Include.NON_NULL)
	JsonOrderDocType orderDocType;

	@ApiModelProperty(position = 300, value = "Specifies the payment rule that will propagate to the created order")
	@JsonInclude(Include.NON_NULL)
	JSONPaymentRule paymentRule;

	@ApiModelProperty(position = 310, value = "Specifies the SalesPartnerCode for the partner that will propagate as sales rep to the created order")
	@JsonInclude(Include.NON_NULL)
	JsonSalesPartner salesPartner;

	@ApiModelProperty(position = 320, value = "Specifies the value for the shipper that will propagate to the created order. \n " + SHIPPER_IDENTIFIER_DOC)
	@JsonInclude(Include.NON_NULL)
	String shipper;

	@ApiModelProperty(position = 330, value = "Specifies the value or the externalId of the payment term that will propagate to the created order")
	@JsonInclude(Include.NON_NULL)
	String paymentTerm;

	@ApiModelProperty(position = 340)
	@JsonInclude(Include.NON_NULL)
	Integer line;

	@ApiModelProperty(position = 350)
	@JsonInclude(Include.NON_NULL)
	String description;

	@ApiModelProperty(position = 360)
	@JsonInclude(Include.NON_NULL)
	JsonOrderLineGroup orderLineGroup;

	@ApiModelProperty(position = 370, value = "Translates to C_OLCand.isManualPrice ")
	@JsonInclude(Include.NON_NULL)
	Boolean isManualPrice;

	@ApiModelProperty(position = 380, value = "Translates to C_OLCand.isImportedWithIssues")
	@JsonInclude(Include.NON_NULL)
	Boolean isImportedWithIssues;

	@ApiModelProperty(position = 390, value = "Translates to C_OLCand.DeliveryViaRule")
	@JsonInclude(Include.NON_NULL)
	String deliveryViaRule;

	@ApiModelProperty(position = 400, value = "Translates to C_OLCand.DeliveryViaRule")
	@JsonInclude(Include.NON_NULL)
	String deliveryRule;

	@ApiModelProperty(position = 410, value = "Translates to C_OLCand.importWarningMessage")
	@JsonInclude(Include.NON_NULL)
	String importWarningMessage;

	@ApiModelProperty(position = 420, value = "Translates to C_OLCand.qtyShipped")
	@JsonInclude(Include.NON_NULL)
	BigDecimal qtyShipped;

	@ApiModelProperty(position = 430, //
			value = "Translates to C_OLCand.ApplySalesRepFrom. If not specified default value is `CandidateFirst`")
	@JsonInclude(Include.NON_NULL)
	JsonApplySalesRepFrom applySalesRepFrom;

	@ApiModelProperty(position = 440)
	@JsonInclude(Include.NON_NULL)
	JsonAlbertaOrderInfo albertaOrderInfo;
	
	@JsonCreator
	@Builder(toBuilder = true)
	private JsonOLCandCreateRequest(
			@JsonProperty("orgCode") final String orgCode,
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
			@JsonProperty("productIdentifier") final String productIdentifier,
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
			@JsonProperty("orderDocType") final @Nullable JsonOrderDocType orderDocType,
			@JsonProperty("paymentRule") final @Nullable JSONPaymentRule paymentRule,
			@JsonProperty("salesPartner") final @Nullable JsonSalesPartner salesPartner,
			@JsonProperty("shipper") final @Nullable String shipper,
			@JsonProperty("paymentTerm") final @Nullable String paymentTerm,
			@JsonProperty("albertaOrderInfo") final @Nullable JsonAlbertaOrderInfo albertaOrderInfo,
			@JsonProperty("orderLineGroup") final @Nullable JsonOrderLineGroup orderLineGroup,
			@JsonProperty("dateCandidate") final @Nullable LocalDate dateCandidate,
			@JsonProperty("line") final @Nullable Integer line,
			@JsonProperty("description") final @Nullable String description,
			@JsonProperty("isManualPrice") final @Nullable Boolean isManualPrice,
			@JsonProperty("isImportedWithIssues") final @Nullable Boolean isImportedWithIssues,
			@JsonProperty("deliveryViaRule") final @Nullable String deliveryViaRule,
			@JsonProperty("deliveryRule") final @Nullable String deliveryRule,
			@JsonProperty("importWarningMessage") final @Nullable String importWarningMessage,
			@JsonProperty("qtyShipped") final @Nullable BigDecimal qtyShipped,
			@JsonProperty("applySalesRepFrom") final @Nullable JsonApplySalesRepFrom applySalesRepFrom)
	{
		this.orgCode = orgCode;
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
		this.dateCandidate = dateCandidate;
		this.flatrateConditionsId = flatrateConditionsId;
		this.productIdentifier = productIdentifier;
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
		this.salesPartner = salesPartner;
		this.shipper = shipper;

		this.paymentTerm = paymentTerm;
		this.albertaOrderInfo = albertaOrderInfo;
		this.orderLineGroup = orderLineGroup;
		this.line = line;
		this.description = description;
		this.isManualPrice = isManualPrice;
		this.isImportedWithIssues = isImportedWithIssues;
		this.deliveryViaRule = deliveryViaRule;
		this.deliveryRule = deliveryRule;
		this.importWarningMessage = importWarningMessage;
		this.qtyShipped = qtyShipped;
		this.applySalesRepFrom = CoalesceUtil.coalesceNotNull(applySalesRepFrom, JsonApplySalesRepFrom.CandidateFirst);
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
		Check.assumeNotNull(productIdentifier, "productIdentifier may not be null; this={}", this);
		Check.assumeNotNull(bpartner, "bpartner may not be null; this={}", this);

		if (price != null)
		{
			Check.assumeNotNull(currencyCode,
								"currencyCode may not be null, if price is set; this={}",
								this);
		}
		return this;
	}
}
