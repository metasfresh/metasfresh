package de.metas.rest_api.bpartner.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.JsonPagingDescriptor;
import de.metas.rest_api.JsonPagingDescriptor.JsonPagingDescriptorBuilder;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartner.JsonBPartnerBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerComposite.JsonBPartnerCompositeBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList.JsonBPartnerCompositeListBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;
import de.metas.util.Check;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class MockedBPartnerEndpointService implements IBPartnerEndpointService
{

	public static final String MOCKED_NEXT = UUID.randomUUID().toString();

	private static int metasfreshIdCounter = 1;

	public MetasfreshId nextMetasFreshId()
	{
		return MetasfreshId.of(metasfreshIdCounter++);
	}

	@Override
	public Optional<JsonBPartnerComposite> retrieveBPartner(@NonNull final String bpartnerIdentifier)
	{
		final JsonBPartnerCompositeBuilder result = JsonBPartnerComposite.builder();

		final JsonBPartnerBuilder bPartner = JsonBPartner
				.builder()
				.companyName("companyName")
				.name("name")
				.language("de_CH")
				.parentId(MetasfreshId.of(1))
				.phone("phone")
				.url("url");

		if (bpartnerIdentifier.startsWith("ext"))
		{
			bPartner.code("code");
			bPartner.externalId(JsonExternalId.of(bpartnerIdentifier));
			bPartner.metasfreshId(nextMetasFreshId());
		}
		else if (bpartnerIdentifier.startsWith("val"))
		{
			bPartner.code(bpartnerIdentifier);
			bPartner.externalId(JsonExternalId.of("externalId"));
			bPartner.metasfreshId(nextMetasFreshId());
		}
		else
		{
			final int repoId = Integer.parseInt(bpartnerIdentifier);
			bPartner.metasfreshId(MetasfreshId.of(repoId));
			bPartner.code("code");
			bPartner.externalId(JsonExternalId.of("externalId"));
		}

		result.bpartner(bPartner.build());

		final JsonBPartnerLocation location1 = createMockLocation("l1", "CH");
		result.location(location1);

		final JsonBPartnerLocation location2 = createMockLocation("l2", "DE");
		result.location(location2);

		final JsonContact contact1 = createMockContact("c1");
		result.contact(contact1);

		final JsonContact contact2 = createMockContact("c2");
		result.contact(contact2);

		return Optional.of(result.build());
	}

	@Override
	public Optional<JsonBPartnerLocation> retrieveBPartnerLocation(String bpartnerIdentifier, String locationIdentifier)
	{
		return Optional.of(createMockLocation("l" + Long.toString(nextMetasFreshId().getValue()), "DE"));
	}

	private JsonBPartnerLocation createMockLocation(
			@NonNull final String prefix,
			@NonNull final String countryCode)
	{
		return JsonBPartnerLocation.builder()
				.metasfreshId(nextMetasFreshId())
				.address1(prefix + "_address1")
				.address2(prefix + "_address2")
				.poBox(prefix + "_poBox")
				.district(prefix + "_district")
				.region(prefix + "_region")
				.city(prefix + "_city")
				.countryCode(countryCode)
				.externalId(JsonExternalId.of(prefix + "_externalId"))
				.gln(prefix + "_gln")
				.postal(prefix + "_postal")
				.build();
	}

	@Override
	public Optional<JsonContact> retrieveBPartnerContact(String bpartnerIdentifier, String contactIdentifier)
	{
		return Optional.of(createMockContact("c" + Long.toString(nextMetasFreshId().getValue())));
	}

	private JsonContact createMockContact(@NonNull final String prefix)
	{
		final JsonContact contact = JsonContact.builder()
				.metasfreshId(nextMetasFreshId())
				.email(prefix + "_email@email.net")
				.externalId(JsonExternalId.of(prefix + "_externalId"))
				.name(prefix + "_name")
				.phone(prefix + "_phone")
				.build();
		return contact;
	}

	@Override
	public Optional<JsonBPartnerCompositeList> retrieveBPartnersSince(Long epochTimestampMillis, String next)
	{
		final JsonPagingDescriptorBuilder pagingDescriptor = JsonPagingDescriptor.builder()
				.pageSize(1)
				.totalSize(2)
				.resultTimestamp(SystemTime.millis());

		final JsonBPartnerCompositeListBuilder compositeList = JsonBPartnerCompositeList.builder();

		if (Check.isEmpty(next))
		{
			pagingDescriptor.nextPage(MOCKED_NEXT); // will return the first page with the 2nd page's identifier
			compositeList.item(retrieveBPartner("1234").get());
		}
		else
		{
			if (MOCKED_NEXT.equals(next))
			{
				pagingDescriptor.nextPage(null); // will return the 2nd and last page
				compositeList.item(retrieveBPartner("1235").get());
			}
			else
			{
				return Optional.ofNullable(null);
			}
		}

		compositeList.pagingDescriptor(pagingDescriptor.build());
		return Optional.of(compositeList.build());
	}

	@Override
	public Optional<JsonContact> retrieveContact(String contactIdentifier)
	{
		return Optional.of(createMockContact("c1"));
	}

	@Override
	public Optional<JsonContactList> retrieveContactsSince(Long epochTimestampMillis, String next)
	{
		JsonContactList list = JsonContactList
				.builder()
				.contact(createMockContact("c1"))
				.pagingDescriptor(JsonPagingDescriptor.builder()
						.pageSize(40)
						.totalSize(1)
						.resultTimestamp(SystemTime.millis())
						.build())
				.build();
		return Optional.of(list);
	}
}
