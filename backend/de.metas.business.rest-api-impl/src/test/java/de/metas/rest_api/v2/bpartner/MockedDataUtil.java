/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner;

import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestComposite.JsonRequestCompositeBuilder;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert.JsonRequestContactUpsertBuilder;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.rest_api.utils.MetasfreshId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.EXTERNAL_SYSTEM_NAME;
import static org.assertj.core.api.Assertions.fail;

@UtilityClass
public class MockedDataUtil
{
	public static final String MOCKED_NEXT = UUID.randomUUID().toString();

	private static int metasfreshIdCounter = 1;

	public MetasfreshId nextMetasFreshId()
	{
		return MetasfreshId.of(metasfreshIdCounter++);
	}

	public JsonRequestComposite createMockBPartner(@NonNull final String bpartnerIdentifierStr)
	{
		final JsonRequestCompositeBuilder result = JsonRequestComposite.builder();

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();

		bPartner.setCompanyName("bPartner.companyName");
		bPartner.setName("bPartner.name");
		bPartner.setGroup("bPartner.group");
		bPartner.setLanguage("bPartner.de_CH");
		bPartner.setParentId(JsonMetasfreshId.of(1));
		bPartner.setPhone("bPartner.phone");
		bPartner.setUrl("bPartner.url");
		bPartner.setVatId("bPartner.vatId");

		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(bpartnerIdentifierStr);

		switch (bpartnerIdentifier.getType())
		{
			case EXTERNAL_REFERENCE:
				break;
			case METASFRESH_ID:
				break;
			case GLN:
				break; //todo implement
			default:
				fail("bpartnerIdentifier is not supported by this mockup method; bpartnerIdentifier={}", bpartnerIdentifier);
				break;
		}

		result.bpartner(bPartner);

		final JsonRequestLocationUpsertBuilder locationUpsert = JsonRequestLocationUpsert.builder()
				.requestItem(createMockLocation("l1", "CH"))
				.requestItem(createMockLocation("l2", "DE"));

		result.locations(locationUpsert.build());

		final JsonRequestContactUpsertBuilder contactUpsert = JsonRequestContactUpsert.builder()
				.requestItem(createMockContact("c1"))
				.requestItem(createMockContact("c2"));

		result.contacts(contactUpsert.build());

		return result.build();
	}

	public JsonRequestLocationUpsertItem createMockLocation(
			@NonNull final String prefix,
			@NonNull final String countryCode)
	{
		final String externalId = prefix + "_externalId";

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(prefix + "_address1");
		location.setAddress2(prefix + "_address2");
		location.setPoBox(prefix + "_poBox");
		location.setDistrict(prefix + "_district");
		location.setRegion(prefix + "_region");
		location.setCity(prefix + "_city");
		location.setCountryCode(countryCode);
		location.setGln(prefix + "_gln");
		location.setPostal(prefix + "_postal");

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier("ext-" + EXTERNAL_SYSTEM_NAME + "-" + externalId)
				.externalVersion(prefix + "_version")
				// .locationIdentifier("gln-" + prefix + "_gln")
				.location(location)
				.build();
	}

	public JsonRequestContactUpsertItem createMockContact(@NonNull final String prefix)
	{
		final String externalId = prefix + "_externalId";

		final JsonRequestContact jsonRequestContact = new JsonRequestContact();
		jsonRequestContact.setEmail(prefix + "_email@email.net");
		jsonRequestContact.setName(prefix + "_name");
		jsonRequestContact.setPhone(prefix + "_phone");
		jsonRequestContact.setInvoiceEmailEnabled(true);

		return JsonRequestContactUpsertItem.builder()
				.contactIdentifier("ext-" + EXTERNAL_SYSTEM_NAME + "-" + externalId)
				.contact(jsonRequestContact)
				.build();
	}
}
