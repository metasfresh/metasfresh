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

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestContact
{
	@Schema
	private JsonMetasfreshId metasfreshBPartnerId;

	@Schema(hidden = true)
	private boolean metasfreshBPartnerIdSet;

	@Schema(description = "Translated to `AD_User.description`")
	private String code;

	@Schema(hidden = true)
	private boolean codeSet;

	@Schema(description = "If not specified but required (e.g. because a new contact is created), then `true` is assumed")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema
	private String name;

	@Schema(hidden = true)
	private boolean nameSet;

	@Schema
	private String firstName;

	@Schema(hidden = true)
	private boolean firstNameSet;

	@Schema
	private String lastName;

	@Schema(hidden = true)
	private boolean lastNameSet;

	@Schema
	private String email;

	@Schema(hidden = true)
	private boolean emailSet;

	@Schema
	private String phone;

	@Schema(hidden = true)
	private boolean phoneSet;

	@Schema
	private String fax;

	@Schema(hidden = true)
	private boolean faxSet;

	@Schema
	private String mobilePhone;

	@Schema(hidden = true)
	private boolean mobilePhoneSet;

	@Schema(description = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean defaultContact;

	@Schema(hidden = true)
	private boolean defaultContactSet;

	@Schema(description = "Only one location per request may have `shipToDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* another possibly exiting metasfresh contact might be set to `shipToDefault = false`, even if it is not specified in this request.")
	private Boolean shipToDefault;

	@Schema(hidden = true)
	private boolean shipToDefaultSet;

	@Schema(description = "Only one location per request may have `billToDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* another possibly exiting metasfresh contact might be set to `billToDefault = false`, even if it is not specified in this request.")
	private Boolean billToDefault;

	@Schema(hidden = true)
	private boolean billToDefaultSet;

	@Schema(description = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean newsletter;

	@Schema(hidden = true)
	private boolean newsletterSet;

	@Schema
	private String description;

	@Schema(hidden = true)
	private boolean descriptionSet;

	@Schema(description = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean sales;

	@Schema(hidden = true)
	private boolean salesSet;

	@Schema(description = "Only one location per request may have `salesDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* `sales` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh contact might be set to `salesDefault = false`, even if it is not specified in this request.")
	private Boolean salesDefault;

	@Schema(hidden = true)
	private boolean salesDefaultSet;

	@Schema(description = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean purchase;

	@Schema(hidden = true)
	private boolean purchaseSet;

	@Schema(description = "Only one location per request may have `purchaseDefault == true`.\n"
					+ "If not specified but required (e.g. because a new contact is created), then `false` is assumed.\n"
					+ "If `true`, then " //
					+ "* `purchase` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh contact might be set to `purchaseDefault = false`, even if it is not specified in this request.")
	private Boolean purchaseDefault;

	@Schema(hidden = true)
	private boolean purchaseDefaultSet;

	@Schema(description = "If not specified but required (e.g. because a new contact is created), then `false` is assumed")
	private Boolean subjectMatter;

	@Schema(hidden = true)
	private boolean subjectMatterSet;

	@Schema
	private Boolean invoiceEmailEnabled;

	@Schema(hidden = true)
	private boolean invoiceEmailEnabledSet;

	@Schema(description = "This translates to `AD_User.Birthday`.")
	private LocalDate birthday;

	@Schema(hidden = true)
	private boolean birthdaySet;

	private JsonRequestGreetingUpsertItem greeting;

	private boolean greetingSet;

	private SyncAdvise syncAdvise;

	@Schema(hidden = true)
	private boolean syncAdviseSet;

	@Schema
	private String title;

	@Schema(hidden = true)
	private boolean titleSet;

	@Schema
	private String phone2;

	@Schema(hidden = true)
	private boolean phone2Set;

	@Schema(description = "Sync advise about this contact's individual properties.\n"
					+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)// shall be last

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

	public void setMetasfreshBPartnerId(final JsonMetasfreshId metasfreshBPartnerId)
	{
		this.metasfreshBPartnerId = metasfreshBPartnerId;
		this.metasfreshBPartnerIdSet = true;
	}

	public void setBirthday(final LocalDate birthday)
	{
		this.birthday = birthday;
		this.birthdaySet = true;
	}

	public void setInvoiceEmailEnabled(@Nullable final Boolean invoiceEmailEnabled)
	{
		this.invoiceEmailEnabled = invoiceEmailEnabled;
		invoiceEmailEnabledSet = true;
	}

	public void setTitle(final String title)
	{
		this.title = title;
		this.titleSet = true;
	}

	public void setPhone2(final String phone2)
	{
		this.phone2 = phone2;
		this.phone2Set = true;
	}

	public void setGreeting(@Nullable final JsonRequestGreetingUpsertItem greeting)
	{
		this.greeting = greeting;
		greetingSet = true;
	}
}
