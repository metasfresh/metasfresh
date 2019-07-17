package de.metas.bpartner.composite;

import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import org.adempiere.ad.table.RecordChangeLog;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.rest.ExternalId;
import lombok.Builder;
import lombok.Data;

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
public class BPartnerLocation
{
	private BPartnerLocationId id;

	/** Needs to be unique over all business partners (not only the one this location belongs to). */
	private ExternalId externalId;

	private String gln;

	private String name;

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

	/** They are all nullable because we can create a completely empty instance which we then fill. */
	@Builder(toBuilder = true)
	private BPartnerLocation(
			@Nullable final BPartnerLocationId id,
			@Nullable final ExternalId externalId,
			@Nullable final String gln,
			@Nullable final Boolean active,
			@Nullable final String name,
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
			@Nullable final BPartnerLocationType locationType,
			@Nullable final RecordChangeLog changeLog)
	{
		this.id = id;
		this.gln = gln;
		this.externalId = externalId;

		this.active = coalesce(active, true);

		this.name = name;
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
	}

	public BPartnerLocation deepCopy()
	{
		return toBuilder().build();
	}

	/** Empty list means valid */
	public ImmutableList<ITranslatableString> validate()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();
		if (isEmpty(countryCode, true))
		{
			result.add(TranslatableStrings.constant("Missing countryCode"));
		}
		if (!isEmpty(district, true) && isEmpty(postal, true))
		{
			result.add(TranslatableStrings.constant("Missing post (required if district is set)"));
		}
		return result.build();
	}
}
