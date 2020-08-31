package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.JsonInvoiceRule;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.common.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Note that given the respective use-case, either one of both properties migh be `null`, but not both at once.")
public class JsonRequestBPartner
{
	@ApiModelProperty(position = 10, required = false, //
			dataType = "java.lang.String", //
			value = "This translates to `C_BPartner.ExternalId`.")
	private JsonExternalId externalId;
	private boolean externalIdSet;

	@ApiModelProperty(position = 20, required = false, //
			value = "This translates to `C_BPartner.Value`.")
	private String code;
	private boolean codeSet;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new partner is created), then `true` is assumed.")
	private Boolean active;
	private boolean activeSet;

	@ApiModelProperty(position = 30, required = false, //
			value = "This translates to `C_BPartner.Name`.\n"
					+ "If this is empty, and a BPartner with the given `name` does not yet exist, then the request will fail.")
	private String name;
	private boolean nameSet;

	@ApiModelProperty(position = 40, required = false, //
			value = "This translates to `C_BPartner.Name2`.")
	private String name2;
	private boolean name2Set;

	@ApiModelProperty(position = 50, required = false, //
			value = "This translates to `C_BPartner.Name3`.")
	private String name3;
	private boolean name3Set;

	@ApiModelProperty(position = 60, required = false, //
			value = "This translates to `C_BPartner.CompanyName`.\n"
					+ "If set, the the respective `C_BPartner` record will also have `IsCompany='Y'`")
	private String companyName;
	private boolean companyNameSet;

	private Boolean vendor;
	private boolean vendorSet;

	private Boolean customer;
	private boolean customerSet;

	@ApiModelProperty(position = 70, required = false, //
			value = "This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company",//
			dataType = "java.lang.Integer")
	private MetasfreshId parentId;
	private boolean parentIdSet;

	@ApiModelProperty(position = 80, required = false, //
			value = "This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number")
	private String phone;
	private boolean phoneSet;

	@ApiModelProperty(position = 90, required = false)
	private String language;
	private boolean languageSet;

	@ApiModelProperty(position = 100, required = false, //
			value = "Optional; if specified, it will be used, e.g. when an order is created for this business partner.")
	private JsonInvoiceRule invoiceRule;
	private boolean invoiceRuleSet;

	@ApiModelProperty(position = 110, required = false)
	private String url;
	private boolean urlSet;

	@ApiModelProperty(position = 120, required = false)
	private String url2;
	private boolean url2Set;

	@ApiModelProperty(position = 130, required = false)
	private String url3;
	private boolean url3Set;

	@ApiModelProperty(position = 140, required = false, //
			value = "Name of the business partner's group")
	private String group;
	private boolean groupSet;

	@ApiModelProperty(position = 150, required = false, //
			value = "Translates to `C_BPartner.GlobalId`")
	private String globalId;
	private boolean globalIdset;

	@ApiModelProperty(position = 20, // shall be last
			required = false, value = "Sync advise about this bPartner's individual properties.\n"
					+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;
	private boolean syncAdviseSet;

	@ApiModelProperty(position = 160, required = false, //
			value = "Translates to `C_BPartner.VATaxId`")
	private String vatId;
	private boolean vatIdSet;

	public void setExternalId(JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	public void setCode(String code)
	{
		this.code = code;
		this.codeSet = true;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setName(String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setName2(String name2)
	{
		this.name2 = name2;
		this.name2Set = true;
	}

	public void setName3(String name3)
	{
		this.name3 = name3;
		this.name3Set = true;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
		this.companyNameSet = true;
	}

	public void setVendor(Boolean vendor)
	{
		this.vendor = vendor;
		this.vendorSet = true;
	}

	public void setCustomer(Boolean customer)
	{
		this.customer = customer;
		this.customerSet = true;
	}

	public void setParentId(MetasfreshId parentId)
	{
		this.parentId = parentId;
		this.parentIdSet = true;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
		this.phoneSet = true;
	}

	public void setLanguage(String language)
	{
		this.language = language;
		this.languageSet = true;
	}

	public void setInvoiceRule(JsonInvoiceRule invoiceRule)
	{
		this.invoiceRule = invoiceRule;
		this.invoiceRuleSet = true;
	}

	public void setUrl(String url)
	{
		this.url = url;
		this.urlSet = true;
	}

	public void setUrl2(String url2)
	{
		this.url2 = url2;
		this.url2Set = true;
	}

	public void setUrl3(String url3)
	{
		this.url3 = url3;
		this.url3Set = true;
	}

	public void setGroup(String group)
	{
		this.group = group;
		this.groupSet = true;
	}

	public void setGlobalId(String globalId)
	{
		this.globalId = globalId;
		this.globalIdset = true;
	}

	public void setSyncAdvise(SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}

	public void setVatId(String vatId)
	{
		this.vatId = vatId;
		this.vatIdSet = true;
	}
}
