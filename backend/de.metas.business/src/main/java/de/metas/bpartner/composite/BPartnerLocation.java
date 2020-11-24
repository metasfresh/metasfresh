package de.metas.bpartner.composite;

import static de.metas.util.Check.isEmpty;
import static de.metas.common.util.CoalesceUtil.coalesce;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.table.RecordChangeLog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.lang.ExternalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
@EqualsAndHashCode(exclude = "original")
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

	private BPartnerLocationId id;

	/** Needs to be unique over all business partners (not only the one this location belongs to). */
	private ExternalId externalId;

	private GLN gln;

	private String name;

	private String bpartnerName;

	private boolean active;

	private String address1;

	private String address2;

	private String address3;

	private String address4;

	private String poBox;

	private String postal;

	private String city;

	private String district;

	private String region;

	private String countryCode;

	private BPartnerLocationType locationType;

	private final RecordChangeLog changeLog;

	/**
	 * Can be set in order to identify this label independently of its "real" properties. Won't be saved by the repo.
	 */
	@Setter(AccessLevel.NONE)
	private final Set<String> handles = new HashSet<>();

	/**
	 * Used to track changes that were made since the instance's instantiation.
	 * Goal: allow {@link de.metas.bpartner.composite.repository.BPartnerCompositeRepository} to avoid creating a new {@code C_Location} if nothing changed in there.
	 */
	@JsonIgnore
	@Getter(AccessLevel.NONE)
	private BPartnerLocation original;

	/** They are all nullable because we can create a completely empty instance which we then fill. */
	@JsonCreator
	@Builder(toBuilder = true)
	private BPartnerLocation(
			@Nullable final BPartnerLocationId id,
			@Nullable final ExternalId externalId,
			@Nullable final GLN gln,
			@Nullable final Boolean active,
			@Nullable final String name,
			@Nullable final String bpartnerName,
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
			@Nullable final BPartnerLocation original,
			@Nullable final BPartnerLocationType locationType,
			@Nullable final RecordChangeLog changeLog)
	{
		this.id = id;
		this.gln = gln;
		this.externalId = externalId;

		this.active = coalesce(active, true);

		this.name = name;

		this.bpartnerName = bpartnerName;
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

		this.original = original;
	}

	public BPartnerLocation deepCopy()
	{
		final BPartnerLocationBuilder builder = toBuilder();

		if (locationType != null)
		{
			builder.locationType(locationType.deepCopy());
		}
		if (original != null)
		{
			builder.original(original.deepCopy());
		}
		return builder.build();
	}

	/** Only active instances are actually validated. Empty list means "valid" */
	public ImmutableList<ITranslatableString> validate()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();
		if (!isActive())
		{
			return result.build();
		}

		if (isEmpty(countryCode, true))
		{
			result.add(TranslatableStrings.constant("Missing location.countryCode"));
		}
		if (!isEmpty(district, true) && isEmpty(postal, true))
		{
			result.add(TranslatableStrings.constant("Missing location.postal (required if location.district is set)"));
		}
		return result.build();
	}

	public void setId(@Nullable final BPartnerLocationId id)
	{
		if (!Objects.equals(this.id, id))
		{
			createOriginalIfNotExists();
		}
		this.id = id;
	}

	public void setExternalId(@Nullable final ExternalId externalId)
	{
		if (!Objects.equals(this.externalId, externalId))
		{
			createOriginalIfNotExists();
		}
		this.externalId = externalId;
	}

	public void setGln(@Nullable final GLN gln)
	{
		if (!Objects.equals(this.gln, gln))
		{
			createOriginalIfNotExists();
		}
		this.gln = gln;
	}

	public void setName(@Nullable final String name)
	{
		if (!Objects.equals(this.name, name))
		{
			createOriginalIfNotExists();
		}
		this.name = name;
	}

	public void setBpartnerName(@Nullable final String bpartnerName)
	{
		if (!Objects.equals(this.bpartnerName, bpartnerName))
		{
			createOriginalIfNotExists();
		}
		this.bpartnerName = bpartnerName;
	}

	public void setActive(@Nullable final boolean active)
	{
		if (!Objects.equals(this.active, active))
		{
			createOriginalIfNotExists();
		}
		this.active = active;
	}

	@JsonIgnore
	public boolean isActiveChanged()
	{
		return !Objects.equals(this.active, getOriginalOrSelf().isActive());
	}

	public void setAddress1(@Nullable final String address1)
	{
		if (!Objects.equals(this.address1, address1))
		{
			createOriginalIfNotExists();
		}
		this.address1 = address1;
	}

	@JsonIgnore
	public boolean isAddress1Changed()
	{
		return !Objects.equals(this.address1, getOriginalOrSelf().getAddress1());
	}

	public void setAddress2(@Nullable final String address2)
	{
		if (!Objects.equals(this.address2, address2))
		{
			createOriginalIfNotExists();
		}
		this.address2 = address2;
	}

	@JsonIgnore
	public boolean isAddress2Changed()
	{
		return !Objects.equals(this.address2, getOriginalOrSelf().getAddress2());
	}

	public void setAddress3(@Nullable final String address3)
	{
		if (!Objects.equals(this.address3, address3))
		{
			createOriginalIfNotExists();
		}
		this.address3 = address3;
	}

	@JsonIgnore
	public boolean isAddress3Changed()
	{
		return !Objects.equals(this.address3, getOriginalOrSelf().getAddress3());
	}

	public void setAddress4(@Nullable final String address4)
	{
		if (!Objects.equals(this.address4, address4))
		{
			createOriginalIfNotExists();
		}
		this.address4 = address4;
	}

	@JsonIgnore
	public boolean isAddress4Changed()
	{
		return !Objects.equals(this.address4, getOriginalOrSelf().getAddress4());
	}

	public void setPoBox(@Nullable final String poBox)
	{
		if (!Objects.equals(this.poBox, poBox))
		{
			createOriginalIfNotExists();
		}
		this.poBox = poBox;
	}

	@JsonIgnore
	public boolean isPoBoxChanged()
	{
		return !Objects.equals(this.poBox, getOriginalOrSelf().getPoBox());
	}

	public void setPostal(@Nullable final String postal)
	{
		if (!Objects.equals(this.city, city))
		{
			createOriginalIfNotExists();
		}
		this.postal = postal;
	}

	@JsonIgnore
	public boolean isPostalChanged()
	{
		return !Objects.equals(this.postal, getOriginalOrSelf().getPostal());
	}

	public void setCity(@Nullable final String city)
	{
		if (!Objects.equals(this.city, city))
		{
			createOriginalIfNotExists();
		}
		this.city = city;
	}

	public void setDistrict(@Nullable final String district)
	{
		if (!Objects.equals(this.district, district))
		{
			createOriginalIfNotExists();
		}
		this.district = district;
	}

	public void setRegion(@Nullable final String region)
	{
		if (!Objects.equals(this.region, region))
		{
			createOriginalIfNotExists();
		}
		this.region = region;
	}

	public void setCountryCode(@Nullable final String countryCode)
	{
		if (!Objects.equals(this.countryCode, countryCode))
		{
			createOriginalIfNotExists();
		}
		this.countryCode = countryCode;
	}

	@JsonIgnore
	public boolean isCountryCodeChanged()
	{
		return !Objects.equals(this.countryCode, getOriginalOrSelf().getCountryCode());
	}

	public void setLocationType(@Nullable final BPartnerLocationType locationType)
	{
		if (!Objects.equals(this.locationType, locationType))
		{
			createOriginalIfNotExists();
		}
		this.locationType = locationType;
	}

	@JsonIgnore
	public BPartnerLocation getOriginalOrSelf()
	{
		return coalesce(original, this);
	}

	private void createOriginalIfNotExists()
	{
		if (original != null)
		{
			return;
		}
		original = deepCopy();
	}

	public void addHandle(@NonNull final String handle)
	{
		handles.add(handle);
	}
}
