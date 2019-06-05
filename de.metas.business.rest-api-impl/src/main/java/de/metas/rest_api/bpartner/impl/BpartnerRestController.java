package de.metas.rest_api.bpartner.impl;

import java.util.UUID;

import javax.annotation.Nullable;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.rest_api.JsonPagingDescriptor;
import de.metas.rest_api.JsonPagingDescriptor.JsonPagingDescriptorBuilder;
import de.metas.rest_api.bpartner.BPartnerRestEndpoint;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList.JsonBPartnerCompositeListBuilder;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequest;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequestItem;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.JsonUpsertResponse.JsonUpsertResponseBuilder;
import de.metas.rest_api.bpartner.JsonUpsertResponseItem;
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

	@Override
	public ResponseEntity<JsonBPartnerComposite> retrieveBPartner(@NonNull final String bpartnerIdentifier)
	{
		final JsonBPartnerComposite result = MockDataUtil.createMockBPartnerComposite(bpartnerIdentifier);
		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<JsonBPartnerLocation> retrieveBPartnerLocation(
			String bpartnerIdentifier,
			String locationIdentifier)
	{
		final JsonBPartnerLocation location = MockDataUtil.createMockLocation("l1", "CH");
		return ResponseEntity.ok(location);
	}

	@Override
	public ResponseEntity<JsonContact> retrieveBPartnerContact(String bpartnerIdentifier, String contactIdentifier)
	{
		final JsonContact contact = MockDataUtil.createMockContact("c1");
		return ResponseEntity.ok(contact);
	}

	public static final String MOCKED_NEXT = UUID.randomUUID().toString();

	@Override
	public ResponseEntity<JsonBPartnerCompositeList> retrieveBPartnersSince(
			@Nullable final Long epochTimestampMillis,
			@Nullable final String next)
	{
		final JsonPagingDescriptorBuilder pagingDescriptor = JsonPagingDescriptor.builder()
				.pageSize(1)
				.totalSize(2)
				.resultTimestamp(SystemTime.millis());

		final JsonBPartnerCompositeListBuilder compositeList = JsonBPartnerCompositeList.builder();

		if (Check.isEmpty(next))
		{
			pagingDescriptor.nextPage(MOCKED_NEXT); // will return the first page with the 2nd page's identifier
			compositeList.item(MockDataUtil.createMockBPartnerComposite("1234"));
		}
		else
		{
			if (MOCKED_NEXT.equals(next))
			{
				pagingDescriptor.nextPage(null); // will return the 2nd and last page
				compositeList.item(MockDataUtil.createMockBPartnerComposite("1235"));
			}
			else
			{
				return new ResponseEntity<JsonBPartnerCompositeList>(
						(JsonBPartnerCompositeList)null,
						HttpStatus.NOT_FOUND);
			}
		}

		compositeList.pagingDescriptor(pagingDescriptor.build());
		return ResponseEntity.ok(compositeList.build());
	}

	@Override
	public ResponseEntity<JsonUpsertResponse> createOrUpdateBPartner(
			// the requestBody annotation needs to be present it here; otherwise, at least swagger doesn't get it
			@RequestBody @NonNull final JsonBPartnerUpsertRequest bpartners)
	{
		final JsonUpsertResponseBuilder response = JsonUpsertResponse.builder();

		for (final JsonBPartnerUpsertRequestItem requestItem : bpartners.getRequestItems())
		{
			final JsonUpsertResponseItem responseItem = JsonUpsertResponseItem.builder()
					.externalId(requestItem.getExternalId())
					.metasfreshId(MockDataUtil.nextMetasFreshId())
					.build();
			response.responseItem(responseItem);
		}
		return new ResponseEntity<>(response.build(), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<JsonUpsertResponseItem> createOrUpdateLocation(
			// the requestBody annotation needs to be present it here; otherwise, at least swagger doesn't get it
			@RequestBody @NonNull final JsonBPartnerLocation location)
	{
		final JsonUpsertResponseItem resonseItem = JsonUpsertResponseItem.builder()
				.externalId(location.getExternalId())
				.metasfreshId(MockDataUtil.nextMetasFreshId())
				.build();
		return new ResponseEntity<>(resonseItem, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<JsonUpsertResponseItem> createOrUpdateContact(
			// the requestBody annotation needs to be present it here; otherwise, at least swagger doesn't get it
			@RequestBody @NonNull final JsonContact contact)
	{
		final JsonUpsertResponseItem resonseItem = JsonUpsertResponseItem.builder()
				.externalId(contact.getExternalId())
				.metasfreshId(MockDataUtil.nextMetasFreshId())
				.build();
		return new ResponseEntity<>(resonseItem, HttpStatus.CREATED);
	}

}
