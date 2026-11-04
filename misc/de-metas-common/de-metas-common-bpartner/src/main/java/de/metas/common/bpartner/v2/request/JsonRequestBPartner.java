/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonInvoiceRule;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Map;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.BPARTNER_VALUE_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

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

	@Schema(description = "This translates to `C_BPartner.Lookup_Label`. Prefer `glnLookupLabel`.")
	@Deprecated
	private @org.jetbrains.annotations.Nullable String lookupLabel;

	@Schema(hidden = true)
	@Deprecated
	private boolean lookupLabelSet;

	@Schema(description = "This translates to `C_BPartner.Lookup_Label`. Canonical name — use this instead of the deprecated `lookupLabel`.")
	private @org.jetbrains.annotations.Nullable String glnLookupLabel;

	@Schema(hidden = true)
	private boolean glnLookupLabelSet;
	
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

	@Schema(description = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company", implementation = Integer.class)
	private JsonMetasfreshId parentId;

	@Schema(hidden = true)
	private boolean parentIdSet;

	@Schema(description = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	private String phone;

	@Schema(hidden = true)
	private boolean phoneSet;

	@Schema(description = "Optional; the partner's language, like `de_DE` or `en_GB`")
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

	@Schema(description = "Translates to `C_BPartner.IsDiscountPrinted`")
	private Boolean discountPrinted;

	@Schema(hidden = true)
	private boolean discountPrintedSet;

	@Schema(description = "Custom REST-API columns on C_BPartner (AD_Column.IsRestAPICustomColumn='Y'). "
					+ "Keys are column names; values are the column values. Unknown columns cause a user-validation error. "
					+ "A null or empty value is treated as a no-op; clearing all custom columns is not supported via this field.")
	private Map<String, Object> extendedProps;

	@Schema(hidden = true)
	private boolean extendedPropsSet;

	@Schema(description = "Optional; the explicit debtor number to assign to this business partner (C_BPartner.DebtorID). Used during data migration from a legacy system.")
	private @Nullable Integer debtorId;

	@Schema(hidden = true)
	private boolean debtorIdSet;

	@Schema(description = "Optional; the explicit creditor number to assign to this business partner (C_BPartner.CreditorID). Used during data migration from a legacy system.")
	private @Nullable Integer creditorId;

	@Schema(hidden = true)
	private boolean creditorIdSet;

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

	@Deprecated
	public void setLookupLabel(@Nullable final String lookupLabel)
	{
		this.lookupLabel = lookupLabel;
		this.lookupLabelSet = true;
	}

	public void setGlnLookupLabel(@Nullable final String glnLookupLabel)
	{
		this.glnLookupLabel = glnLookupLabel;
		this.glnLookupLabelSet = true;
	}

	public boolean isGlnLookupLabelSet()
	{
		return glnLookupLabelSet;
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
	public void setDiscountPrinted(final Boolean discountPrinted)
	{
		this.discountPrinted = discountPrinted;
		this.discountPrintedSet = true;
	}

	public void setExtendedProps(@Nullable final Map<String, Object> extendedProps)
	{
		this.extendedProps = extendedProps;
		this.extendedPropsSet = true;
	}

	public void setDebtorId(@Nullable final Integer debtorId)
	{
		this.debtorId = debtorId;
		this.debtorIdSet = true;
	}

	public void setCreditorId(@Nullable final Integer creditorId)
	{
		this.creditorId = creditorId;
		this.creditorIdSet = true;
	}
}
