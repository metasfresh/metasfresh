package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.OrgMappingId;
import de.metas.greeting.GreetingId;
import de.metas.util.lang.ExternalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.table.RecordChangeLog;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

@Data
@JsonPropertyOrder(alphabetic = true/* we want the serialized json to be less flaky in our snapshot files */)
public class BPartnerContact
{
	public static final String ID = "id";
	public static final String VALUE = "value";
	public static final String BPARTNER_ID = "bpartnerId";
	public static final String EXTERNAL_ID = "externalId";
	public static final String ACTIVE = "active";
	public static final String NAME = "name";
	public static final String LAST_NAME = "lastName";
	public static final String FIRST_NAME = "firstName";
	public static final String EMAIL = "email";
	public static final String PHONE = "phone";
	public static final String FAX = "fax";
	public static final String MOBILE_PHONE = "mobilePhone";
	public static final String DESCRIPTION = "description";
	public static final String GREETING_ID = "greetingId";

	public static final String SUBJECT_MATTER = "subjectMatter";
	public static final String NEWSLETTER = "newsletter";
	public static final String MEMBERSHIP = "membership";

	@Nullable
	private BPartnerContactId id;

	/**
	 * A bit redundant because it's already part of the {@link BPartnerContactId}, but we use if for mapping purposes.
	 */
	@Setter(lombok.AccessLevel.NONE)
	@JsonIgnore
	@Nullable
	private BPartnerId bpartnerId;

	@Nullable
	private ExternalId externalId;

	/**
	 * An ID which is used only by caller API.
	 * It's not persisted to database.
	 * It's not loaded from database.
	 */
	@Nullable
	@JsonInclude(Include.NON_NULL)
	private final transient String transientId;

	@Nullable
	private String value;

	private boolean active;

	@Nullable
	private String name;

	@Nullable
	private String lastName;

	@Nullable
	private String firstName;

	@JsonInclude(Include.NON_NULL)
	@Nullable
	private String email;

	@JsonInclude(Include.NON_NULL)
	@Nullable
	private String phone;

	private boolean newsletter;
	private boolean membershipContact;
	private boolean subjectMatterContact;

	@Nullable
	private Boolean invoiceEmailEnabled;

	@Nullable
	private String fax;

	@Nullable
	private String mobilePhone;

	@Nullable
	private String description;

	@Nullable
	private GreetingId greetingId;

	@Nullable
	private BPartnerContactType contactType;

	private final RecordChangeLog changeLog;

	@Nullable
	private OrgMappingId orgMappingId;

	@Nullable
	private LocalDate birthday;

	@Nullable
	private BPartnerLocationId bPartnerLocationId;

	/**
	 * Can be set in order to identify this label independently of its "real" properties. Won't be saved by the repo.
	 */
	@Setter(AccessLevel.NONE)
	private final Set<String> handles = new HashSet<>();

	/**
	 * They are all nullable because we can create a completely empty instance which we then fill.
	 * <p>
	 * We need no bpartner id property
	 */
	@Builder(toBuilder = true)
	private BPartnerContact(
			@Nullable final BPartnerContactId id,
			@Nullable final ExternalId externalId,
			@Nullable final String transientId,
			@Nullable final String value,
			@Nullable final Boolean active,
			@Nullable final String name,
			@Nullable final String firstName,
			@Nullable final String lastName,
			@Nullable final String email,
			@Nullable final Boolean newsletter,
			@Nullable final Boolean membershipContact,
			@Nullable final Boolean subjectMatterContact,
			@Nullable final Boolean invoiceEmailEnabled,
			@Nullable final String fax,
			@Nullable final String mobilePhone,
			@Nullable final String description,
			@Nullable final GreetingId greetingId,
			@Nullable final String phone,
			@Nullable final BPartnerContactType contactType,
			@Nullable final RecordChangeLog changeLog,
			@Nullable final OrgMappingId orgMappingId,
			@Nullable final LocalDate birthday,
			@Nullable final BPartnerLocationId bPartnerLocationId)
	{
		setId(id);

		this.externalId = externalId;
		this.transientId = transientId;
		this.value = value;

		this.newsletter = newsletter != null ? newsletter : false;
		this.membershipContact = membershipContact != null ? membershipContact : false;
		this.subjectMatterContact = subjectMatterContact != null ? subjectMatterContact : false;
		this.invoiceEmailEnabled = invoiceEmailEnabled;
		this.fax = fax;
		this.mobilePhone = mobilePhone;
		this.description = description;
		this.greetingId = greetingId;

		this.contactType = contactType;
		this.active = active != null ? active : true;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;

		this.changeLog = changeLog;

		this.orgMappingId = orgMappingId;
		this.bPartnerLocationId = bPartnerLocationId;
		this.birthday = birthday;
	}

	public BPartnerContact deepCopy()
	{
		final BPartnerContactBuilder builder = toBuilder();
		if (contactType != null)
		{
			builder.contactType(contactType.deepCopy());
		}
		return builder.build();
	}

	public final void setId(@Nullable final BPartnerContactId id)
	{
		this.id = id;
		this.bpartnerId = id != null ? id.getBpartnerId() : null;
	}

	public void addHandle(@NonNull final String handle)
	{
		handles.add(handle);
	}
}
