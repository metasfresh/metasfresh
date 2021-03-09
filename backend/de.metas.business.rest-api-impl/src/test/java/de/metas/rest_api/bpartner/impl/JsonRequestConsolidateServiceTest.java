package de.metas.rest_api.bpartner.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.metas.common.bpartner.request.JsonRequestBPartner;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.request.JsonRequestComposite;
import de.metas.common.rest_api.JsonExternalId;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

class JsonRequestConsolidateServiceTest
{

	@ParameterizedTest
	@CsvSource({
			"ext-123, 123, 123",
			"ext-123, DONT_SET, 123",
			"ext-123, , ",
			"ext-123, abc, abc"
	})
	void consolidateBPartnerExternalId(
			String bpartnerIdentifier,
			String initialBPartnerExternalId,
			String expectedBPartnerExternalId)
	{
		// given
		final JsonRequestConsolidateService jsonRequestConsolidateService = new JsonRequestConsolidateService();

		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();
		if (!"DONT_SET".equals(initialBPartnerExternalId))
		{
			jsonRequestBPartner.setExternalId(JsonExternalId.ofOrNull(initialBPartnerExternalId));
		}
		final JsonRequestBPartnerUpsertItem requestItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bpartnerIdentifier)
				.bpartnerComposite(JsonRequestComposite.builder().bpartner(jsonRequestBPartner).build())
				.build();

		// when
		jsonRequestConsolidateService.consolidateWithIdentifier(requestItem);

		// then
		assertThat(JsonExternalId.toValue(jsonRequestBPartner.getExternalId())).isEqualTo(expectedBPartnerExternalId);
	}
}
