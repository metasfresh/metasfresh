/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.bpartner.v2.common.JsonDeliveryRule;
import de.metas.common.bpartner.v2.common.JsonDeliveryViaRule;
import de.metas.common.bpartner.v2.common.JsonPaymentRule;
import de.metas.common.changelog.JsonChangeInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Schema(description = "Note that given the respective use-case, either one of both properties migh be `null`, but not both at once.")
public class JsonResponseBPartner
{
	public static final String GROUP_NAME = "group";
	public static final String URL = "url";
	public static final String URL_2 = "url2";
	public static final String URL_3 = "url3";
	public static final String LANGUAGE = "language";
	public static final String PHONE = "phone";
	public static final String PARENT_ID = "parentId";
	public static final String COMPANY_NAME = "companyName";
	public static final String NAME = "name";
	public static final String NAME_2 = "name2";
	public static final String NAME_3 = "name3";
	public static final String EXTERNAL_ID = "externalId";
	public static final String METASFRESH_ID = "metasfreshId";
	public static final String CODE = "code";
	public static final String GLOBAL_ID = "globalId";
	public static final String ACTIVE = "active";
	public static final String VENDOR = "vendor";
	public static final String CUSTOMER = "customer";
	public static final String PRICING_SYSTEM_ID = "pricingSystemId";
	public static final String SALES_PARTNER_CODE = "salesPartnerCode";
	public static final String SALES_PARTNER = "salesPartner";
	public static final String PAYMENT_RULE = "paymentRule";
	public static final String PAYMENT_RULE_PO = "paymentRulePO";
	public static final String INTERNAL_NAME = "internalName";
	public static final String COMPANY = "company";
	public static final String VAT_ID = "vatId";
	public static final String METASFRESH_URL = "metasfreshUrl";
	public static final String CREDITOR_ID = "creditorId";
	public static final String DEBTOR_ID = "debtorId";
	public static final String SECTION_CODE_ID = "sectionCodeId";
	public static final String DESCRIPTION = "description";
	public static final String DELIVERY_RULE = "deliveryRule";
	public static final String DELIVERY_VIA_RULE = "deliveryViaRule";
	public static final String STORAGE_WAREHOUSE = "storageWarehouse";
	public static final String INCOTERMS_CUSTOMER_ID = "incotermsCustomerId";
	public static final String INCOTERMS_VENDOR_ID = "incotermsVendorId";
	public static final String CUSTOMER_PAYMENTTERM_ID = "customerPaymentTermId";
	public static final String VENDOR_PAYMENTTERM_ID = "vendorPaymentTermId";
	public static final String SECTION_GROUP_PARTNER_ID = "sectionGroupPartnerId";
	public static final String PROSPECT = "isProspect";
	public static final String SAP_BPARTNER_CODE = "sapBPartnerCode";
	public static final String SECTION_GROUP_PARTNER = "sectionGroupPartner";
	public static final String SECTION_PARTNER = "sectionPartner";

	private static final String CHANGE_INFO = "changeInfo";

	@Schema(description = "This translates to `C_BPartner.C_BPartner_ID`.")
	@JsonProperty(METASFRESH_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId metasfreshId;

	@Schema(description = "This translates to `C_BPartner.Value`.")
	@JsonProperty(CODE)
	String code;

	@Schema(description = "This translates to `C_BPartner.GlobalId`.")
	@JsonProperty(GLOBAL_ID)
	String globalId;

	@Schema(description = "This translates to `C_BPartner.IsActive`.")
	@JsonProperty(ACTIVE)
	boolean active;

	@Schema(description = "This translates to `C_BPartner.Name`.")
	@JsonProperty(NAME)
	String name;

	@Schema(description = "This translates to `C_BPartner.Name2`.")
	@JsonProperty(NAME_2)
	@JsonInclude(Include.NON_NULL)
	String name2;

	@Schema(description = "This translates to `C_BPartner.Name3`.")
	@JsonProperty(NAME_3)
	@JsonInclude(Include.NON_NULL)
	String name3;

	@Schema(description = "This translates to `C_BPartner.CompanyName`.\n"
					+ "If set, the the respective `C_BPartner` record will also have `IsCompany='Y'`")
	@JsonProperty(COMPANY_NAME)
	@JsonInclude(Include.NON_NULL)
	String companyName;

	@Schema(description = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company")
	@JsonProperty(PARENT_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId parentId;

	@Schema(description = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	@JsonProperty(PHONE)
	@JsonInclude(Include.NON_NULL)
	String phone;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(LANGUAGE)
	String language;

	@Schema(description = "This translates to `C_BPartner.URL`.")
	@JsonProperty(URL)
	@JsonInclude(Include.NON_NULL)
	String url;

	@Schema(description = "This translates to `C_BPartner.URL2`.")
	@JsonProperty(URL_2)
	@JsonInclude(Include.NON_NULL)
	String url2;

	@Schema(description = "This translates to `C_BPartner.URL3`.")
	@JsonProperty(URL_3)
	@JsonInclude(Include.NON_NULL)
	String url3;

	@Schema( description = "Name of the business partner's group")
	@JsonProperty(GROUP_NAME)
	@JsonInclude(Include.NON_NULL)
	String group;

	@Schema(description = "This translates to `C_BPartner.IsVendor`.")
	@JsonProperty(VENDOR)
	boolean vendor;

	@Schema(description = "This translates to `C_BPartner.IsCustomer`.")
	@JsonProperty(CUSTOMER)
	boolean customer;

	@Schema(description = "This translates to `C_BPartner.SalesPartnerCode`")
	@JsonProperty(SALES_PARTNER_CODE)
	@JsonInclude(Include.NON_NULL)
	String salesPartnerCode;

	@Schema(description = "This translates to `C_BPartner.M_PricingSystem_ID`")
	@JsonProperty(PRICING_SYSTEM_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId pricingSystemId;

	@Schema(description = "This contains information about the superior sales rep of the respective `C_BPartner` record")
	@JsonProperty(SALES_PARTNER)
	@JsonInclude(Include.NON_NULL)
	JsonResponseSalesRep responseSalesRep;

	@JsonProperty(PAYMENT_RULE)
	@JsonInclude(Include.NON_NULL)
	JsonPaymentRule paymentRule;

	@JsonProperty(PAYMENT_RULE_PO)
	@JsonInclude(Include.NON_NULL)
	JsonPaymentRule paymentRulePO;

	@JsonProperty(INTERNAL_NAME)
	@JsonInclude(Include.NON_NULL)
	String internalName;

	@Schema(description = "This translates to `C_BPartner.IsCompany`.")
	@JsonProperty(COMPANY)
	boolean company;

	@Schema(description = "This translates to `C_BPartner.VATaxID`.")
	@JsonProperty(VAT_ID)
	String vatId;

	@Schema(description = "This translates to `baseUrl/window/{specificBPartnerWindowId}/{C_BPartner_ID}`")
	@JsonProperty(METASFRESH_URL)
	@JsonInclude(Include.NON_NULL)
	String metasfreshUrl;

	@Schema(description = "This translates to `C_BPartner.CreditorId` ")
	@JsonProperty(CREDITOR_ID)
	@JsonInclude(Include.NON_NULL)
	Integer creditorId;

	@Schema(description = "This translates to `C_BPartner.DebtorId` ")
	@JsonProperty(DEBTOR_ID)
	@JsonInclude(Include.NON_NULL)
	Integer debtorId;

	@Schema(description = "Translates to `C_BPartner.M_SectionCode_ID`")
	@JsonProperty(SECTION_CODE_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId sectionCodeId;

	@Schema(description = "Translates to `C_BPartner.Description`")
	@JsonProperty(DESCRIPTION)
	@JsonInclude(Include.NON_NULL)
	String description;

	@Schema(description = "Translates to `C_BPartner.DeliveryRule`")
	@JsonProperty(DELIVERY_RULE)
	@JsonInclude(Include.NON_NULL)
	JsonDeliveryRule deliveryRule;

	@Schema(description = "Translates to `C_BPartner.DeliveryViaRule`")
	@JsonProperty(DELIVERY_VIA_RULE)
	@JsonInclude(Include.NON_NULL)
	JsonDeliveryViaRule deliveryViaRule;

	@Schema(description = "Translates to `C_BPartner.IsStorageWarehouse`")
	@JsonProperty(STORAGE_WAREHOUSE)
	@JsonInclude(Include.NON_NULL)
	Boolean storageWarehouse;

	@Schema(description = "Translates to `C_BPartner.C_Incoterms_Customer_ID`")
	@JsonProperty(INCOTERMS_CUSTOMER_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId incotermsCustomerId;

	@Schema(description = "Translates to `C_BPartner.C_Incoterms_Vendor_ID`")
	@JsonProperty(INCOTERMS_VENDOR_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId incotermsVendorId;

	@Schema(description = "Translates to `C_BPartner.C_PaymentTerm_ID`")
	@JsonProperty(CUSTOMER_PAYMENTTERM_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId customerPaymentTermId;

	@Schema(description = "Translates to `C_BPartner.PO_PaymentTerm_ID`")
	@JsonProperty(VENDOR_PAYMENTTERM_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId vendorPaymentTermId;

	@Schema(description = "This translates to `C_BPartner.Section_Group_Partner_ID`")
	@JsonProperty(SECTION_GROUP_PARTNER_ID)
	@JsonInclude(Include.NON_NULL)
	JsonMetasfreshId sectionGroupPartnerId;

	@Schema(description = "This translates to `C_BPartner.IsProspect`")
	@JsonProperty(PROSPECT)
	@JsonInclude(Include.NON_NULL)
	Boolean prospect;

	@Schema(description = "This translates to `C_BPartner.SAP_BPartnerCode`")
	@JsonProperty(SAP_BPARTNER_CODE)
	@JsonInclude(Include.NON_NULL)
	String sapBPartnerCode;

	@Schema(description = "This translates to `C_BPartner.IsSectionGroupPartner`")
	@JsonProperty(SECTION_GROUP_PARTNER)
	@JsonInclude(Include.NON_NULL)
	boolean sectionGroupPartner;

	@Schema(description = "This translates to `C_BPartner.IsSectionPartner`")
	@JsonProperty(SECTION_PARTNER)
	@JsonInclude(Include.NON_NULL)
	boolean sectionPartner;

	@Schema // shall be last
	@JsonProperty(CHANGE_INFO)
	@JsonInclude(Include.NON_NULL)
	JsonChangeInfo changeInfo;

	@JsonCreator
	@Builder(toBuilder = true)
	private JsonResponseBPartner(
			@JsonProperty(METASFRESH_ID) @NonNull final JsonMetasfreshId metasfreshId,
			@JsonProperty(CODE) @Nullable final String code, // usually metasfresh makes sure it's not null; but in unit-tests it might be; also, let's just return the result here, and avoid throwing an NPE
			@JsonProperty(GLOBAL_ID) @Nullable final String globalId,
			@JsonProperty(ACTIVE) @NonNull final Boolean active,
			@JsonProperty(NAME) @NonNull final String name,
			@JsonProperty(NAME_2) @Nullable final String name2,
			@JsonProperty(NAME_3) @Nullable final String name3,
			@JsonProperty(COMPANY_NAME) @Nullable final String companyName,
			@JsonProperty(PARENT_ID) @Nullable final JsonMetasfreshId parentId,
			@JsonProperty(PHONE) @Nullable final String phone,
			@JsonProperty(LANGUAGE) @Nullable final String language,
			@JsonProperty(URL) @Nullable final String url,
			@JsonProperty(URL_2) @Nullable final String url2,
			@JsonProperty(URL_3) @Nullable final String url3,
			@JsonProperty(GROUP_NAME) @Nullable final String group,
			@JsonProperty(VENDOR) @NonNull final Boolean vendor,
			@JsonProperty(CUSTOMER) @NonNull final Boolean customer,
			@JsonProperty(SALES_PARTNER_CODE) @Nullable final String salesPartnerCode,
			@JsonProperty(PRICING_SYSTEM_ID) @Nullable final JsonMetasfreshId pricingSystemId,
			@JsonProperty(SALES_PARTNER) @Nullable final JsonResponseSalesRep responseSalesRep,
			@JsonProperty(PAYMENT_RULE) @Nullable final JsonPaymentRule paymentRule,
			@JsonProperty(PAYMENT_RULE_PO) @Nullable final JsonPaymentRule paymentRulePO,
			@JsonProperty(INTERNAL_NAME) @Nullable final String internalName,
			@JsonProperty(COMPANY) @NonNull final Boolean company,
			@JsonProperty(VAT_ID) @Nullable final String vatId,
			@JsonProperty(METASFRESH_URL) @Nullable final String metasfreshUrl,
			@JsonProperty(CREDITOR_ID) @Nullable final Integer creditorId,
			@JsonProperty(DEBTOR_ID) @Nullable final Integer debtorId,
			@JsonProperty(SECTION_CODE_ID) @Nullable final JsonMetasfreshId sectionCodeId,
			@JsonProperty(DESCRIPTION) @Nullable final String description,
			@JsonProperty(DELIVERY_RULE) @Nullable final JsonDeliveryRule deliveryRule,
			@JsonProperty(DELIVERY_VIA_RULE) @Nullable final JsonDeliveryViaRule deliveryViaRule,
			@JsonProperty(STORAGE_WAREHOUSE) @Nullable final Boolean storageWarehouse,
			@JsonProperty(INCOTERMS_CUSTOMER_ID) @Nullable final JsonMetasfreshId incotermsCustomerId,
			@JsonProperty(INCOTERMS_VENDOR_ID) @Nullable final JsonMetasfreshId incotermsVendorId,
			@JsonProperty(CUSTOMER_PAYMENTTERM_ID) @Nullable final JsonMetasfreshId customerPaymentTermId,
			@JsonProperty(VENDOR_PAYMENTTERM_ID) @Nullable final JsonMetasfreshId vendorPaymentTermId,
			@JsonProperty(SECTION_GROUP_PARTNER_ID) @Nullable final JsonMetasfreshId sectionGroupPartnerId,
			@JsonProperty(PROSPECT) final Boolean prospect,
			@JsonProperty(SAP_BPARTNER_CODE) @Nullable final String sapBPartnerCode,
			@JsonProperty(SECTION_GROUP_PARTNER) final boolean sectionGroupPartner,
			@JsonProperty(SECTION_PARTNER) final boolean sectionPartner,
			@JsonProperty(CHANGE_INFO) @Nullable JsonChangeInfo changeInfo)
			//
	{
		this.metasfreshId = metasfreshId;
		this.code = code;
		this.globalId = globalId;
		this.active = active;

		this.name = name;
		this.name2 = name2;
		this.name3 = name3;

		this.companyName = companyName;

		this.parentId = parentId;

		this.phone = phone;
		this.language = language;

		this.url = url;
		this.url2 = url2;
		this.url3 = url3;

		this.group = group;

		this.vendor = vendor;
		this.customer = customer;
		this.salesPartnerCode = salesPartnerCode;
		this.pricingSystemId = pricingSystemId;
		this.responseSalesRep = responseSalesRep;
		this.paymentRule = paymentRule;
		this.paymentRulePO = paymentRulePO;
		this.internalName = internalName;
		this.company = company;

		this.vatId = vatId;

		this.metasfreshUrl = metasfreshUrl;

		this.creditorId = creditorId;
		this.debtorId = debtorId;
		this.sectionCodeId = sectionCodeId;
		this.description = description;
		this.deliveryRule = deliveryRule;
		this.deliveryViaRule = deliveryViaRule;
		this.storageWarehouse = storageWarehouse;
		this.incotermsCustomerId = incotermsCustomerId;
		this.incotermsVendorId = incotermsVendorId;
		this.customerPaymentTermId = customerPaymentTermId;
		this.vendorPaymentTermId = vendorPaymentTermId;
		this.sectionGroupPartnerId = sectionGroupPartnerId;
		this.prospect = prospect;
		this.sapBPartnerCode = sapBPartnerCode;
		this.sectionGroupPartner = sectionGroupPartner;
		this.sectionPartner = sectionPartner;

		this.changeInfo = changeInfo;
	}
}
