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

package de.metas.common.bpartner.v2.request;

import de.metas.common.bpartner.v2.common.JsonDeliveryRule;
import de.metas.common.bpartner.v2.common.JsonDeliveryViaRule;
import de.metas.common.bpartner.v2.common.JsonPaymentRule;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonInvoiceRule;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_VALUE_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.PAYMENT_TERM_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Note that given the respective use-case, either one of both properties might be `null`, but not both at once.")
public class JsonRequestBPartner
{
	@Schema(description = BPARTNER_VALUE_DOC)
	private String code;

	@Schema(hidden = true)
	private boolean codeSet;

	@Schema(description = "If not specified but required (e.g. because a new partner is created), then `true` is assumed.")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(description = "This translates to `C_BPartner.Name`.\n"
					+ "If this is empty, and a BPartner with the given `name` does not yet exist, then the request will fail.")
	private String name;

	@Schema(hidden = true)
	private boolean nameSet;

	@Schema(description = "This translates to `C_BPartner.Name2`.")
	private String name2;

	@Schema(hidden = true)
	private boolean name2Set;

	@Schema(description = "This translates to `C_BPartner.Name3`.")
	private String name3;

	@Schema(hidden = true)
	private boolean name3Set;

	@Schema(description = "This translates to `C_BPartner.CompanyName`.\n"
					+ "If set, the the respective `C_BPartner` record will also have `IsCompany='Y'`")
	private String companyName;

	@Schema(hidden = true)
	private boolean companyNameSet;

	private Boolean vendor;

	@Schema(hidden = true)
	private boolean vendorSet;

	private Boolean customer;

	@Schema(hidden = true)
	private boolean customerSet;

	@Schema(description = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company")
	private JsonMetasfreshId parentId;

	@Schema(hidden = true)
	private boolean parentIdSet;

	@Schema(description = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	private String phone;

	@Schema(hidden = true)
	private boolean phoneSet;

	@Schema
	private String language;

	@Schema(hidden = true)
	private boolean languageSet;

	@Schema(description = "Optional; if specified, it will be used, e.g. when an order is created for this business partner.")
	private JsonInvoiceRule invoiceRule;

	@Schema(hidden = true)
	private boolean invoiceRuleSet;

	@Schema(description = "Optional; if specified, it will be used, e.g. when a purchase order is created for this business partner.")
	private JsonInvoiceRule poInvoiceRule;

	@Schema(hidden = true)
	private boolean poInvoiceRuleSet;

	@Schema
	private String url;

	@Schema(hidden = true)
	private boolean urlSet;

	@Schema
	private String url2;

	@Schema(hidden = true)
	private boolean url2Set;

	@Schema
	private String url3;

	@Schema(hidden = true)
	private boolean url3Set;

	@Schema(description = "Name of the business partner's group")
	private String group;

	@Schema(hidden = true)
	private boolean groupSet;

	@Schema(description = "Translates to `C_BPartner.GlobalId`")
	private String globalId;

	@Schema(hidden = true)
	private boolean globalIdset;

	@Schema(description = "The ID of the price list that the business partner is to use as a customer. If provided, this will be used to lookup `C_BPartner.M_PricingSystem_ID`")
	private JsonMetasfreshId priceListId;

	private boolean priceListIdSet;

	@Schema(description = "Translates to `C_BPartner.VATaxId`")
	private String vatId;

	@Schema(hidden = true)
	private boolean vatIdSet;

	@Schema(description = "This translates to `C_BPartner.memo`")
	private String memo;

	private boolean memoIsSet;

	@Schema(description = "Translates to `M_SectionCode.Value` and it's mapped to `C_BPartner.M_SectionCode_ID`")
	private String sectionCodeValue;

	private boolean sectionCodeValueSet;

	@Schema(description = "Translates to `C_BPartner.Description`")
	private String description;

	private boolean descriptionSet;

	@Schema(description = "Translates to `C_BPartner.DeliveryRule`")
	private JsonDeliveryRule deliveryRule;

	private boolean deliveryRuleSet;

	@Schema(description = "Translates to `C_BPartner.DeliveryViaRule`")
	private JsonDeliveryViaRule deliveryViaRule;

	private boolean deliveryViaRuleSet;

	@Schema(description = "Translates to `C_BPartner.IsStorageWarehouse`")
	private Boolean storageWarehouse;

	private boolean storageWarehouseSet;

	@Schema(description = "Translates to `C_Incoterms.Value` and it's mapped to `C_BPartner.C_Incoterms_Customer_ID`")
	private String incotermsCustomerValue;

	private boolean incotermsCustomerValueSet;

	@Schema(description = "Translates to `C_Incoterms.Value` and it's mapped to `C_BPartner.C_Incoterms_Vendor_ID`")
	private String incotermsVendorValue;

	private boolean incotermsVendorValueSet;

	@Schema(description = "Mapped to `C_BPartner.C_PaymentTerm_ID`. " + PAYMENT_TERM_IDENTIFIER_DOC)
	private String customerPaymentTermIdentifier;

	private boolean customerPaymentTermIdentifierSet;

	@Schema(description = "Mapped to `C_BPartner.PO_PaymentTerm_ID`. " + PAYMENT_TERM_IDENTIFIER_DOC)
	private String vendorPaymentTermIdentifier;

	private boolean vendorPaymentTermIdentifierSet;

	@Schema(description = "Mapped to `C_BPartner.BPartner_Parent_ID`. " + BPARTNER_IDENTIFIER_DOC)
	private String parentIdentifier;

	private boolean parentIdentifierSet;

	@Schema(description = "Translates to `C_BPartner.PaymentRule`")
	private JsonPaymentRule paymentRule;

	private boolean paymentRuleSet;

	@Schema(description = "Translates to `C_BPartner.PaymentRulePO`")
	private JsonPaymentRule paymentRulePO;

	private boolean paymentRulePOSet;

	@Schema(description = "Mapped to `C_BPartner.Section_Group_Partner_ID`. " + BPARTNER_IDENTIFIER_DOC)
	private String sectionGroupPartnerIdentifier;

	@Schema(hidden = true)
	private boolean sectionGroupPartnerIdentifierSet;

	@Schema(description = "Translates to `C_BPartner.IsProspect`")
	private Boolean prospect;

	private boolean prospectSet;

	@Schema(description = "This translates to `C_BPartner.SAP_BPartnerCode`.")
	private String sapBPartnerCode;

	@Schema(hidden = true)
	private boolean sapBPartnerCodeSet;

	@Schema(description = "This translates to `C_BPartner.IsSectionGroupPartner`.")
	private boolean sectionGroupPartner;

	@Schema(hidden = true)
	private boolean sectionGroupPartnerSet;

	@Schema(description = "This translates to `C_BPartner.IsSectionPartner`.")
	private boolean sectionPartner;

	@Schema(hidden = true)
	private boolean sectionPartnerSet;

	@Schema(description = "Sync advise about this bPartner's individual properties.\n"
					+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;

	@Schema(hidden = true)
	private boolean syncAdviseSet;

	public void setCode(final String code)
	{
		this.code = code;
		this.codeSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setName(final String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setName2(final String name2)
	{
		this.name2 = name2;
		this.name2Set = true;
	}

	public void setName3(final String name3)
	{
		this.name3 = name3;
		this.name3Set = true;
	}

	public void setCompanyName(final String companyName)
	{
		this.companyName = companyName;
		this.companyNameSet = true;
	}

	public void setVendor(final Boolean vendor)
	{
		this.vendor = vendor;
		this.vendorSet = true;
	}

	public void setCustomer(final Boolean customer)
	{
		this.customer = customer;
		this.customerSet = true;
	}

	public void setParentId(final JsonMetasfreshId parentId)
	{
		this.parentId = parentId;
		this.parentIdSet = true;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
		this.phoneSet = true;
	}

	public void setLanguage(final String language)
	{
		this.language = language;
		this.languageSet = true;
	}

	public void setInvoiceRule(final JsonInvoiceRule invoiceRule)
	{
		this.invoiceRule = invoiceRule;
		this.invoiceRuleSet = true;
	}

	public void setPOInvoiceRule(final JsonInvoiceRule invoiceRule)
	{
		this.poInvoiceRule = invoiceRule;
		this.poInvoiceRuleSet = true;
	}

	public void setUrl(final String url)
	{
		this.url = url;
		this.urlSet = true;
	}

	public void setUrl2(final String url2)
	{
		this.url2 = url2;
		this.url2Set = true;
	}

	public void setUrl3(final String url3)
	{
		this.url3 = url3;
		this.url3Set = true;
	}

	public void setGroup(final String group)
	{
		this.group = group;
		this.groupSet = true;
	}

	public void setGlobalId(final String globalId)
	{
		this.globalId = globalId;
		this.globalIdset = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}

	public void setVatId(final String vatId)
	{
		this.vatId = vatId;
		this.vatIdSet = true;
	}

	public void setMemo(final String memo)
	{
		this.memo = memo;
		this.memoIsSet = true;
	}

	public void setPriceListId(@Nullable final JsonMetasfreshId priceListId)
	{
		if (JsonMetasfreshId.toValue(priceListId) != null)
		{
			this.priceListId = priceListId;
			this.priceListIdSet = true;
		}
	}

	public void setSectionCodeValue(final String sectionCodeValue)
	{
		this.sectionCodeValue = sectionCodeValue;
		this.sectionCodeValueSet = true;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setDeliveryRule(final JsonDeliveryRule deliveryRule)
	{
		this.deliveryRule = deliveryRule;
		this.deliveryRuleSet = true;
	}

	public void setDeliveryViaRule(final JsonDeliveryViaRule deliveryViaRule)
	{
		this.deliveryViaRule = deliveryViaRule;
		this.deliveryViaRuleSet = true;
	}

	public void setStorageWarehouse(final Boolean storageWarehouse)
	{
		this.storageWarehouse = storageWarehouse;
		this.storageWarehouseSet = true;
	}

	public void setIncotermsCustomerValue(final String incotermsCustomerValue)
	{
		this.incotermsCustomerValue = incotermsCustomerValue;
		this.incotermsCustomerValueSet = true;
	}

	public void setIncotermsVendorValue(final String incotermsVendorValue)
	{
		this.incotermsVendorValue = incotermsVendorValue;
		this.incotermsVendorValueSet = true;
	}

	public void setCustomerPaymentTermIdentifier(final String customerPaymentTermIdentifier)
	{
		this.customerPaymentTermIdentifier = customerPaymentTermIdentifier;
		this.customerPaymentTermIdentifierSet = true;
	}

	public void setVendorPaymentTermIdentifier(final String vendorPaymentTermIdentifier)
	{
		this.vendorPaymentTermIdentifier = vendorPaymentTermIdentifier;
		this.vendorPaymentTermIdentifierSet = true;
	}

	public void setParentIdentifier(final String parentIdentifier)
	{
		this.parentIdentifier = parentIdentifier;
		this.parentIdentifierSet = true;
	}

	public void setPaymentRule(final JsonPaymentRule paymentRule)
	{
		this.paymentRule = paymentRule;
		this.paymentRuleSet = true;
	}

	public void setPaymentRulePO(final JsonPaymentRule paymentRulePO)
	{
		this.paymentRulePO = paymentRulePO;
		this.paymentRulePOSet = true;
	}

	public void setSectionGroupPartnerIdentifier(final String sectionGroupPartnerIdentifier)
	{
		this.sectionGroupPartnerIdentifier = sectionGroupPartnerIdentifier;
		this.sectionGroupPartnerIdentifierSet = true;
	}

	public void setProspect(final Boolean prospect)
	{
		this.prospect = prospect;
		this.prospectSet = true;
	}

	public void setSapBPartnerCode(final String sapBPartnerCode)
	{
		this.sapBPartnerCode = sapBPartnerCode;
		this.sapBPartnerCodeSet = true;
	}

	public void setSectionGroupPartner(final boolean sectionGroupPartner)
	{
		this.sectionGroupPartner = sectionGroupPartner;
		this.sectionGroupPartnerSet = true;
	}

	public void setSectionPartner(final boolean sectionPartner)
	{
		this.sectionPartner = sectionPartner;
		this.sectionPartnerSet = true;
	}
}
