package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.OrgMappingId;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.LocationId;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.RecordChangeLog;

import javax.annotation.Nullable;
import java.util.HashSet;

import static de.metas.util.Check.isBlank;

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
public class BPartnerLocation
{
	public static final String ID = "id";
	public static final String EXTERNAL_ID = "externalId";
	public static final String GLN = "gln";
	public static final String NAME = "name";
	public static final String BPARTNERNAME = " bpartnerName";
	public static final String ACTIVE = "active";
	public static final String ADDRESS_1 = "address1";
	public static final String ADDRESS_2 = "address2";
	public static final String ADDRESS_3 = "address3";
	public static final String ADDRESS_4 = "address4";
	public static final String PO_BOX = "poBox";
	public static final String POSTAL = "postal";
	public static final String CITY = "city";
	public static final String DISTRICT = "district";
	public static final String REGION = "region";
	public static final String COUNTRYCODE = "countryCode";
	public static final String EPHEMERAL = "ephemeral";
	public static final String PHONE = "phone";
	public static final String EMAIL = "email";
	public static final String VISITORS_ADDRESS = "visitorsAddress";
	public static final String HANDOVER_LOCATION = "handoverLocation";
	public static final String REMIT_TO = "remitTo";
	public static final String REPLICATION_LOOKUP_DEFAULT = "replicationLookupDefault";
	public static final String VAT_TAX_ID = "vatTaxId";
	public static final String SAP_PAYMENT_METHOD = "sapPaymentMethod";
	public static final String SAP_BPARTNER_CODE = "sapBPartnerCode";
	public static final String COUNTRY_NAME = "countryName";

	@Nullable
	private BPartnerLocationId id;

	/**
	 * Needs to be unique over all business partners (not only the one this location belongs to).
	 */
	@Nullable
	private ExternalId externalId;

	@Nullable
	private GLN gln;

	@Nullable
	private String name;

	@Nullable
	private String bpartnerName;

	private boolean active;

	/**
	 * Use case: When you are creating a new bpartner location and you already have the C_Location_ID
	 * then you can set it to `existingLocationId` and then skip ALL the other address fields (from address1 to countryCode).
	 */
	@Nullable
	private LocationId existingLocationId;
	@Nullable
	private String address1;
	@Nullable
	private String address2;
	@Nullable
	private String address3;
	@Nullable
	private String address4;
	@Nullable
	private String poBox;
	@Nullable
	private String postal;
	@Nullable
	private String city;
	@Nullable
	private String region;
	@Nullable
	private String district;
	@Nullable
	private String countryCode;
	@Nullable
	private String phone;
	@Nullable
	private String email;

	@Nullable
	private BPartnerLocationType locationType;

	@Nullable
	private final RecordChangeLog changeLog;

	@Nullable
	private OrgMappingId orgMappingId;

	private boolean ephemeral;

	@Nullable
	private String mobile;

	@Nullable
	private String fax;

	@Nullable final String setupPlaceNo;

	@Nullable
	private String vatTaxId;

	private boolean remitTo;
	private boolean handOverLocation;
	private boolean replicationLookupDefault;
	private boolean visitorsAddress;

	@Nullable
	private String sapPaymentMethod;

	@Nullable
	private String sapBPartnerCode;

	@Nullable
	private String countryName;

	/**
	 * Can be set in order to identify this label independently of its "real" properties. Won't be saved by the repo.
	 */
	@Getter(AccessLevel.NONE)
	private final HashSet<String> handles = new HashSet<>();

	/**
	 * They are all nullable because we can create a completely empty instance which we then fill.
	 */
	@JsonCreator
	@Builder(toBuilder = true)
	private BPartnerLocation(
			@Nullable final BPartnerLocationId id,
			@Nullable final ExternalId externalId,
			@Nullable final GLN gln,
			@Nullable final Boolean active,
			@Nullable final String name,
			@Nullable final String bpartnerName,
			@Nullable final LocationId existingLocationId,
			@Nullable final String address1,
			@Nullable final String address2,
			@Nullable final String address3,
			@Nullable final String address4,
			@Nullable final String postal,
			@Nullable final String poBox,
			@Nullable final String district,
			@Nullable final String region,
			@Nullable final String city,
			@Nullable final String countryCode,
			@Nullable final String phone,
			@Nullable final String email,
			@Nullable final BPartnerLocationType locationType,
			@Nullable final RecordChangeLog changeLog,
			@Nullable final OrgMappingId orgMappingId,
			@Nullable final Boolean ephemeral,
			@Nullable final String mobile,
			@Nullable final String fax,
			@Nullable final String setupPlaceNo,
			@Nullable final String vatTaxId,
			@Nullable final String countryName,
			@Nullable final Boolean remitTo,
			@Nullable final Boolean handOverLocation,
			@Nullable final Boolean replicationLookupDefault,
			@Nullable final Boolean visitorsAddress,
			@Nullable final String sapPaymentMethod,
			@Nullable final String sapBPartnerCode)
	{
		this.id = id;
		this.gln = gln;
		this.externalId = externalId;

		this.active = active != null ? active : true;

		this.name = name;

		this.bpartnerName = bpartnerName;
		this.existingLocationId = existingLocationId;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.address4 = address4;
		this.postal = postal;
		this.poBox = poBox;
		this.district = district;
		this.region = region;
		this.city = city;
		this.countryCode = countryCode; // mandatory only if we want to insert/update a new location

		this.locationType = locationType;

		this.changeLog = changeLog;

		this.orgMappingId = orgMappingId;

		this.ephemeral = CoalesceUtil.coalesceNotNull(ephemeral, false);

		this.phone = phone;

		this.mobile = mobile;

		this.fax = fax;

		this.email = email;

		this.setupPlaceNo = setupPlaceNo;
		this.vatTaxId = vatTaxId;

		this.countryName = countryName;

		this.handOverLocation = handOverLocation != null ? handOverLocation : false;

		this.replicationLookupDefault = replicationLookupDefault != null ? replicationLookupDefault : false;

		this.remitTo = remitTo != null ? remitTo : false;

		this.visitorsAddress = visitorsAddress != null ? visitorsAddress : false;

		this.sapPaymentMethod = sapPaymentMethod;
		this.sapBPartnerCode = sapBPartnerCode;
	}

	public BPartnerLocation deepCopy()
	{
		final BPartnerLocationBuilder builder = toBuilder();

		if (locationType != null)
		{
			builder.locationType(locationType.deepCopy());
		}

		return builder.build();
	}

	/**
	 * Only active instances are actually validated. Empty list means "valid"
	 */
	public ImmutableList<ITranslatableString> validate()
	{
		if (!isActive())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();

		if (!isAddressSpecifiedByExistingLocationIdOnly())
		{
			if (isBlank(countryCode))
			{
				result.add(TranslatableStrings.constant("Missing location.countryCode"));
			}

			if (!isBlank(district) && isBlank(postal))
			{
				result.add(TranslatableStrings.constant("Missing location.postal (required if location.district is set)"));
			}
		}

		return result.build();
	}

	@JsonIgnore
	public boolean isAddressSpecifiedByExistingLocationIdOnly()
	{
		return toAddress().isOnlyExistingLocationIdSet();
	}

	public void addHandle(@NonNull final String handle)
	{
		handles.add(handle);
	}

	public boolean containsHandle(@NonNull final String handle)
	{
		return handles.contains(handle);
	}

	public BPartnerLocationAddressPart toAddress()
	{
		return BPartnerLocationAddressPart.builder()
				.existingLocationId(getExistingLocationId())
				.address1(getAddress1())
				.address2(getAddress2())
				.address3(getAddress3())
				.address4(getAddress4())
				.poBox(getPoBox())
				.postal(getPostal())
				.city(getCity())
				.region(getRegion())
				.district(getDistrict())
				.countryCode(getCountryCode())
				.countryName(getCountryName())
				.build();
	}

	public void setFromAddress(@NonNull final BPartnerLocationAddressPart address)
	{
		setExistingLocationId(address.getExistingLocationId());
		setAddress1(address.getAddress1());
		setAddress2(address.getAddress2());
		setAddress3(address.getAddress3());
		setAddress4(address.getAddress4());
		setCity(address.getCity());
		setCountryCode(address.getCountryCode());
		setPoBox(address.getPoBox());
		setPostal(address.getPostal());
		setRegion(address.getRegion());
		setDistrict(address.getDistrict());
		setCountryName(address.getCountryName());
	}

	@Nullable
	public LocationId getExistingLocationIdNotNull()
	{
		return Check.assumeNotNull(getExistingLocationId(), "existingLocationId not null: {}", this);
	}

	/**
	 * Can be used if this instance's ID is known to be not null.
	 */
	@NonNull
	public BPartnerLocationId getIdNotNull()
	{
		return Check.assumeNotNull(id, "id may not be null at this point");
	}
}
