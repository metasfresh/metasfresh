package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.*;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.common.SyncAdvise;

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
public class JsonRequestContact
{
	@ApiModelProperty(dataType = "java.lang.String")
	private JsonExternalId externalId;
	private boolean externalIdSet;

	@ApiModelProperty(dataType = "java.lang.Integer")
	private MetasfreshId metasfreshBPartnerId;
	private boolean metasfreshBPartnerIdSet;

	@ApiModelProperty("Translated to `AD_User.Value`")
	private String code;
	private boolean codeSet;

	@ApiModelProperty("If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	private Boolean active;
	private boolean activeSet;

	private String name;
	private boolean nameSet;

	private String firstName;
	private boolean firstNameSet;

	private String lastName;
	private boolean lastNameSet;

	private String email;
	private boolean emailSet;

	private String phone;
	private boolean phoneSet;

	private String fax;
	private boolean faxSet;

	private String mobilePhone;
	private boolean mobilePhoneSet;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean defaultContact;
	private boolean defaultContactSet;

	@ApiModelProperty(required = false, value = "Only one location per request may have `shipToDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* another possibly exiting metasfresh contact might be set to `shipToDefault = false`, even if it is not specified in this request.")
	private Boolean shipToDefault;
	private boolean shipToDefaultSet;

	@ApiModelProperty(required = false, value = "Only one location per request may have `billToDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* another possibly exiting metasfresh contact might be set to `billToDefault = false`, even if it is not specified in this request.")
	private Boolean billToDefault;
	private boolean billToDefaultSet;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean newsletter;
	private boolean newsletterSet;

	private String description;
	private boolean descriptionSet;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean sales;
	private boolean salesSet;

	@ApiModelProperty(required = false, value = "Only one location per request may have `salesDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* `sales` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh contact might be set to `salesDefault = false`, even if it is not specified in this request.")
	private Boolean salesDefault;
	private boolean salesDefaultSet;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean purchase;
	private boolean purchaseSet;

	@ApiModelProperty(required = false, value = "Only one location per request may have `purchaseDefault == true`.\n"
			+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
			+ "If `true`, then " //
			+ "* `purchase` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh contact might be set to `purchaseDefault = false`, even if it is not specified in this request.")
	private Boolean purchaseDefault;
	private boolean purchaseDefaultSet;

	@ApiModelProperty(required = false, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean subjectMatter;
	private boolean subjectMatterSet;

	@ApiModelProperty(position = 20, // shall be last
			required = false, value = "Sync advise about this contact's individual properties.\n"
			+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;
	private boolean syncAdviseSet;

	public void setExternalId(JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	public void setMetasfreshBPartnerId(MetasfreshId metasfreshBPartnerId)
	{
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.metasfreshBPartnerIdSet = true;
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

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
		this.firstNameSet = true;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
		this.lastNameSet = true;
	}

	public void setEmail(String email)
	{
		this.email = email;
		this.emailSet = true;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
		this.phoneSet = true;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
		this.faxSet = true;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
		this.mobilePhoneSet = true;
	}

	public void setDefaultContact(Boolean defaultContact)
	{
		this.defaultContact = defaultContact;
		this.defaultContactSet = true;
	}

	public void setShipToDefault(Boolean shipToDefault)
	{
		this.shipToDefault = shipToDefault;
		this.shipToDefaultSet = true;
	}

	public void setBillToDefault(Boolean billToDefault)
	{
		this.billToDefault = billToDefault;
		this.billToDefaultSet = true;
	}

	public void setNewsletter(Boolean newsletter)
	{
		this.newsletter = newsletter;
		this.newsletterSet = true;
	}

	public void setDescription(String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setSales(Boolean sales)
	{
		this.sales = sales;
		this.salesSet = true;
	}

	public void setSalesDefault(Boolean salesDefault)
	{
		this.salesDefault = salesDefault;
		this.salesDefaultSet = true;
	}

	public void setPurchase(Boolean purchase)
	{
		this.purchase = purchase;
		this.purchaseSet = true;
	}

	public void setPurchaseDefault(Boolean purchaseDefault)
	{
		this.purchaseDefault = purchaseDefault;
		this.purchaseDefaultSet = true;
	}

	public void setSubjectMatter(Boolean subjectMatter)
	{
		this.subjectMatter = subjectMatter;
		this.subjectMatterSet = true;
	}

	public void setSyncAdvise(SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}
}
