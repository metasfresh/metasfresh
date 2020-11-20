package de.metas.rest_api.bpartner.request;

import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.common.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

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
	@ApiModelProperty(position = 10, dataType = "java.lang.String")
	private JsonExternalId externalId;

	@ApiModelProperty(hidden = true)
	private boolean externalIdSet;

	@ApiModelProperty(position = 20, dataType = "java.lang.Integer")
	private MetasfreshId metasfreshBPartnerId;

	@ApiModelProperty(hidden = true)
	private boolean metasfreshBPartnerIdSet;

	@ApiModelProperty(position = 30, value = "Translated to `AD_User.Value`")
	private String code;

	@ApiModelProperty(hidden = true)
	private boolean codeSet;

	@ApiModelProperty(position = 40, value = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 50)
	private String name;

	@ApiModelProperty(hidden = true)
	private boolean nameSet;

	@ApiModelProperty(position = 60)
	private String firstName;

	@ApiModelProperty(hidden = true)
	private boolean firstNameSet;

	@ApiModelProperty(position = 70)
	private String lastName;

	@ApiModelProperty(hidden = true)
	private boolean lastNameSet;

	@ApiModelProperty(position = 80)
	private String email;

	@ApiModelProperty(hidden = true)
	private boolean emailSet;

	@ApiModelProperty(position = 90)
	private String phone;

	@ApiModelProperty(hidden = true)
	private boolean phoneSet;

	@ApiModelProperty(position = 100)
	private String fax;

	@ApiModelProperty(hidden = true)
	private boolean faxSet;

	@ApiModelProperty(position = 110)
	private String mobilePhone;

	@ApiModelProperty(hidden = true)
	private boolean mobilePhoneSet;

	@ApiModelProperty(position = 120, value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean defaultContact;

	@ApiModelProperty(hidden = true)
	private boolean defaultContactSet;

	@ApiModelProperty(position = 130, //
			value = "Only one location per request may have `shipToDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* another possibly exiting metasfresh contact might be set to `shipToDefault = false`, even if it is not specified in this request.")
	private Boolean shipToDefault;

	@ApiModelProperty(hidden = true)
	private boolean shipToDefaultSet;

	@ApiModelProperty(position = 140, //
			value = "Only one location per request may have `billToDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* another possibly exiting metasfresh contact might be set to `billToDefault = false`, even if it is not specified in this request.")
	private Boolean billToDefault;

	@ApiModelProperty(hidden = true)
	private boolean billToDefaultSet;

	@ApiModelProperty(position = 150, //
			value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean newsletter;

	@ApiModelProperty(hidden = true)
	private boolean newsletterSet;

	@ApiModelProperty(position = 160)
	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	@ApiModelProperty(position = 170, //
			value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean sales;

	@ApiModelProperty(hidden = true)
	private boolean salesSet;

	@ApiModelProperty(position = 180, //
			value = "Only one location per request may have `salesDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* `sales` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh contact might be set to `salesDefault = false`, even if it is not specified in this request.")
	private Boolean salesDefault;

	@ApiModelProperty(hidden = true)
	private boolean salesDefaultSet;

	@ApiModelProperty(position = 190, //
			value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean purchase;

	@ApiModelProperty(hidden = true)
	private boolean purchaseSet;

	@ApiModelProperty(position = 200, //
			value = "Only one location per request may have `purchaseDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* `purchase` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh contact might be set to `purchaseDefault = false`, even if it is not specified in this request.")
	private Boolean purchaseDefault;

	@ApiModelProperty(hidden = true)
	private boolean purchaseDefaultSet;

	@ApiModelProperty(position = 210, //
			value = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean subjectMatter;

	@ApiModelProperty(hidden = true)
	private boolean subjectMatterSet;

	@ApiModelProperty(position = 220, // shall be last
			value = "Sync advise about this contact's individual properties.\n"
					+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;

	@ApiModelProperty(hidden = true)
	private boolean syncAdviseSet;

	public void setExternalId(final JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	public void setMetasfreshBPartnerId(final MetasfreshId metasfreshBPartnerId)
	{
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.metasfreshBPartnerIdSet = true;
	}

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

	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
		this.firstNameSet = true;
	}

	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
		this.lastNameSet = true;
	}

	public void setEmail(final String email)
	{
		this.email = email;
		this.emailSet = true;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
		this.phoneSet = true;
	}

	public void setFax(final String fax)
	{
		this.fax = fax;
		this.faxSet = true;
	}

	public void setMobilePhone(final String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
		this.mobilePhoneSet = true;
	}

	public void setDefaultContact(final Boolean defaultContact)
	{
		this.defaultContact = defaultContact;
		this.defaultContactSet = true;
	}

	public void setShipToDefault(final Boolean shipToDefault)
	{
		this.shipToDefault = shipToDefault;
		this.shipToDefaultSet = true;
	}

	public void setBillToDefault(final Boolean billToDefault)
	{
		this.billToDefault = billToDefault;
		this.billToDefaultSet = true;
	}

	public void setNewsletter(final Boolean newsletter)
	{
		this.newsletter = newsletter;
		this.newsletterSet = true;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setSales(final Boolean sales)
	{
		this.sales = sales;
		this.salesSet = true;
	}

	public void setSalesDefault(final Boolean salesDefault)
	{
		this.salesDefault = salesDefault;
		this.salesDefaultSet = true;
	}

	public void setPurchase(final Boolean purchase)
	{
		this.purchase = purchase;
		this.purchaseSet = true;
	}

	public void setPurchaseDefault(final Boolean purchaseDefault)
	{
		this.purchaseDefault = purchaseDefault;
		this.purchaseDefaultSet = true;
	}

	public void setSubjectMatter(final Boolean subjectMatter)
	{
		this.subjectMatter = subjectMatter;
		this.subjectMatterSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}
}
