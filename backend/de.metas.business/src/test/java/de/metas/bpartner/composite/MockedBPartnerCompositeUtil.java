/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.composite;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.i18n.Language;
import de.metas.organization.OrgId;
import de.metas.util.lang.ExternalId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MockedBPartnerCompositeUtil
{
	public static final int ORG_ID = 10;
	public static final int GROUP_ID = 11;

	public static BPartnerComposite createMockedBPartnerComposite(final ExternalId externalId)
	{
		final BPartner bpartner = BPartner.builder()
				.companyName("bPartner.companyName")
				.name("bPartner.name")
				.groupId(BPGroupId.ofRepoId(GROUP_ID))
				.language(Language.asLanguage("de_DE"))
				.externalId(externalId)
				.build();

		final BPartnerComposite.BPartnerCompositeBuilder builder = BPartnerComposite.builder()
				.orgId(OrgId.ofRepoId(ORG_ID))
				.bpartner(bpartner);

		return builder.build();
	}

	public static BPartnerLocation addLocation(final ExternalId externalId, final String city, final String poCode, final String countryCode)
	{
		return BPartnerLocation.builder()
				.active(true)
				.city(city)
				.postal(poCode)
				.countryCode(countryCode)
				.name("testLocation")
				.externalId(externalId)
				.build();
	}
}
