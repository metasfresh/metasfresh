package de.metas.rest_api.bpartner.impl;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.JsonPagingDescriptor;
import de.metas.rest_api.JsonPagingDescriptor.JsonPagingDescriptorBuilder;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.BPartnerRestEndpoint;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartner.JsonBPartnerBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerComposite.JsonBPartnerCompositeBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList.JsonBPartnerCompositeListBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerContact;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequest;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequestItem;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertResponse;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertResponse.JsonBPartnerUpsertResponseBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertResponseItem;
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

@RestController
@Profile(Profiles.PROFILE_App)
// the spelling "Bpartner" is to avoid swagger spelling it "b-partner-rest.."
public class BpartnerRestController implements BPartnerRestEndpoint
{
	private static long metasfreshIdCounter = 0;

	@Override
	public ResponseEntity<JsonBPartnerComposite> retrieveBPartner(@NonNull final String bpartnerIdentifier)
	{
		final JsonBPartnerComposite result = createMockBPartnerComposite(bpartnerIdentifier);
		return ResponseEntity.ok(result);
	}

	private JsonBPartnerComposite createMockBPartnerComposite(String bpartnerIdentifier)
	{
		final JsonBPartnerCompositeBuilder result = JsonBPartnerComposite.builder();

		final JsonBPartnerBuilder bPartner = JsonBPartner
				.builder()
				.companyName("companyName")
				.name("name")
				.language("de_CH")
				.parentId(MetasfreshId.of(1l))
				.phone("phone")
				.url("url");

		if (bpartnerIdentifier.startsWith("ext"))
		{
			bPartner.code("code");
			bPartner.externalId(JsonExternalId.of(bpartnerIdentifier));
			bPartner.id(MetasfreshId.of(metasfreshIdCounter++));
		}
		else if (bpartnerIdentifier.startsWith("val"))
		{
			bPartner.code(bpartnerIdentifier);
			bPartner.externalId(JsonExternalId.of("externalId"));
			bPartner.id(MetasfreshId.of(metasfreshIdCounter++));
		}
		else
		{
			final int repoId = Integer.parseInt(bpartnerIdentifier);
			bPartner.id(MetasfreshId.of(repoId));
			bPartner.code("code");
			bPartner.externalId(JsonExternalId.of("externalId"));
		}

		result.bpartner(bPartner.build());

		final JsonBPartnerLocation location1 = createMockLocation("l1", "CH");
		result.location(location1);

		final JsonBPartnerLocation location2 = createMockLocation("l2", "DE");
		result.location(location2);

		final JsonBPartnerContact contact1 = createMockContact("c1");
		result.contact(contact1);

		final JsonBPartnerContact contact2 = createMockContact("c2");
		result.contact(contact2);

		return result.build();
	}

	private JsonBPartnerContact createMockContact(final String prefix)
	{
		final JsonBPartnerContact contact = JsonBPartnerContact.builder()
				.id(MetasfreshId.of(metasfreshIdCounter++))
				.email(prefix + "_email@email.net")
				.externalId(JsonExternalId.of(prefix + "_externalId"))
				.name(prefix + "_name")
				.phone(prefix + "_phone")
				.build();
		return contact;
	}

	private JsonBPartnerLocation createMockLocation(
			@NonNull final String prefix,
			@NonNull final String countryCode)
	{
		return JsonBPartnerLocation.builder()
				.id(MetasfreshId.of(metasfreshIdCounter++))
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
				.state(prefix + "_state")
				.build();
	}

	@Override
	public ResponseEntity<JsonBPartnerLocation> retrieveBPartnerLocation(
			String bpartnerIdentifier,
			String locationIdentifier)
	{
		final JsonBPartnerLocation location = createMockLocation("l1", "CH");
		return ResponseEntity.ok(location);
	}

	@Override
	public ResponseEntity<JsonBPartnerContact> retrieveBPartnerContact(String bpartnerIdentifier, String contactIdentifier)
	{
		final JsonBPartnerContact contact = createMockContact("c1");
		return ResponseEntity.ok(contact);
	}

	public static final String MOCKED_NEXT = UUID.randomUUID().toString();

	@Override
	public ResponseEntity<JsonBPartnerCompositeList> retrieveBPartnersSince(
			final long epochTimestampMillis,
			final String next)
	{
		final JsonPagingDescriptorBuilder pagingDescriptor = JsonPagingDescriptor.builder()
				.pageSize(1)
				.totalSize(2)
				.resultTimestamp(SystemTime.millis());

		final JsonBPartnerCompositeListBuilder compositeList = JsonBPartnerCompositeList				.builder();

		if (Check.isEmpty(next))
		{
			pagingDescriptor.nextPage(MOCKED_NEXT); // will return the first page with the 2nd page's identifier
			compositeList.item(createMockBPartnerComposite("1234"));
		}
		else
		{
			if (MOCKED_NEXT.equals(next))
			{
				pagingDescriptor.nextPage(null); // will return the 2nd and last page
				compositeList.item(createMockBPartnerComposite("1235"));
			}
			return new ResponseEntity<JsonBPartnerCompositeList>(
					(JsonBPartnerCompositeList)null,
					HttpStatus.NOT_FOUND);
		}

		compositeList.pagingDescriptor(pagingDescriptor.build());
		return ResponseEntity.ok(compositeList.build());
	}

	@Override
	public ResponseEntity<JsonBPartnerUpsertResponse> createOrUpdateBPartner(JsonBPartnerUpsertRequest bpartners)
	{
		final JsonBPartnerUpsertResponseBuilder response = JsonBPartnerUpsertResponse.builder();

		for (final JsonBPartnerUpsertRequestItem requestItem : bpartners.getRequestItems())
		{
			final JsonBPartnerUpsertResponseItem responseItem = new JsonBPartnerUpsertResponseItem(
					requestItem.getExternalId(),
					MetasfreshId.of(metasfreshIdCounter++));
			response.responseItem(responseItem);
		}
		return ResponseEntity.ok(response.build());
	}

	@Override
	public ResponseEntity<JsonBPartnerUpsertResponseItem> createOrUpdateLocation(JsonBPartnerLocation location)
	{
		final JsonBPartnerUpsertResponseItem resonseItem = new JsonBPartnerUpsertResponseItem(
				location.getExternalId(),
				MetasfreshId.of(metasfreshIdCounter++));

		return ResponseEntity.ok(resonseItem);
	}

	@Override
	public ResponseEntity<JsonBPartnerUpsertResponseItem> createOrUpdateContact(JsonBPartnerContact contact)
	{
		final JsonBPartnerUpsertResponseItem resonseItem = new JsonBPartnerUpsertResponseItem(
				contact.getExternalId(),
				MetasfreshId.of(metasfreshIdCounter++));

		return ResponseEntity.ok(resonseItem);
	}

}
