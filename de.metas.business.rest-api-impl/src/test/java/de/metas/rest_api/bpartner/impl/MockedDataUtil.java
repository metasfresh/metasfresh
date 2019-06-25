package de.metas.rest_api.bpartner.impl;

import java.util.UUID;

import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner.JsonRequestBPartnerBuilder;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestComposite.JsonRequestCompositeBuilder;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class MockedDataUtil
{
	public static final String MOCKED_NEXT = UUID.randomUUID().toString();

	private static int metasfreshIdCounter = 1;

	public MetasfreshId nextMetasFreshId()
	{
		return MetasfreshId.of(metasfreshIdCounter++);
	}

	public JsonRequestComposite createMockBPartner(@NonNull final String bpartnerIdentifier)
	{
		final JsonRequestCompositeBuilder result = JsonRequestComposite.builder();

		final JsonRequestBPartnerBuilder bPartner = JsonRequestBPartner
				.builder()
				.companyName("bPartner.companyName")
				.name("bPartner.name")
				.group("bPartner.group")
				.language("bPartner.de_CH")
				.parentId(MetasfreshId.of(1))
				.phone("bPartner.phone")
				.url("bPartner.url");

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

		final JsonRequestLocation location1 = createMockLocation("l1", "CH");
		result.location(location1);

		final JsonRequestLocation location2 = createMockLocation("l2", "DE");
		result.location(location2);

		final JsonRequestContact contact1 = createMockContact("c1");
		result.contact(contact1);

		final JsonRequestContact contact2 = createMockContact("c2");
		result.contact(contact2);

		return result.build();
	}

	public JsonRequestLocation createMockLocation(
			@NonNull final String prefix,
			@NonNull final String countryCode)
	{
		return JsonRequestLocation.builder()
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

	public JsonRequestContact createMockContact(@NonNull final String prefix)
	{
		final JsonRequestContact contact = JsonRequestContact.builder()
				.metasfreshId(nextMetasFreshId())
				.email(prefix + "_email@email.net")
				.externalId(JsonExternalId.of(prefix + "_externalId"))
				.name(prefix + "_name")
				.phone(prefix + "_phone")
				.build();
		return contact;
	}
}
