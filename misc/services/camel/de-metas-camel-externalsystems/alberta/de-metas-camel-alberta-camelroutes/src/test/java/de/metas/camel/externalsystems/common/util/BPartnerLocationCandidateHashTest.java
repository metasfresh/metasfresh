/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common.util;

import de.metas.camel.externalsystems.alberta.common.util.BPartnerLocationCandidate;
import de.metas.camel.externalsystems.alberta.ordercandidate.processor.DeliveryAddressUpsertProcessor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BPartnerLocationCandidateHashTest
{
	@Test
	public void givenBPartnerLocationCandidate_whenComputeHashKey_thenReturnHashValue()
	{
		//given
		final BPartnerLocationCandidate bPartnerLocationCandidate = BPartnerLocationCandidate.builder()
				.bpartnerName("bpartnerName")
				.name("name")
				.address1("address1")
				.address2("address2")
				.address3("address3")
				.postal("postal")
				.city("city")
				.countryCode("countryCode")
				.billTo(false)
				.shipTo(true)
				.ephemeral(true)
				.build();

		//when
		final String actualHashValue = DeliveryAddressUpsertProcessor.computeHashKey(bPartnerLocationCandidate);

		//then
		assertThat(actualHashValue).isEqualTo("2b2f13e142ffc23280cd644d549228a80bdf6bffe0f7d0652d596ce95bb306f599d9585ebe2b6c2b2cf2b58e5c483f1e3deb8b96a8e60f18f1d27511cfcce1e6");
	}

	@Test
	public void givenBPartnerLocationCandidateWithExtraWhitespaces_whenComputeHashKey_thenReturnSameHashValue()
	{
		//given
		final BPartnerLocationCandidate bPartnerLocationCandidate = BPartnerLocationCandidate.builder()
				.bpartnerName("bpartnerName")
				.name("name")
				.address1("address1")
				.address2("address2")
				.address3("address3")
				.postal("postal")
				.city("city")
				.countryCode("countryCode")
				.billTo(false)
				.shipTo(true)
				.ephemeral(true)
				.build();

		final String expectedHashValue = DeliveryAddressUpsertProcessor.computeHashKey(bPartnerLocationCandidate);

		//when
		final BPartnerLocationCandidate bPartnerLocationCandidateWithWhitespaces = bPartnerLocationCandidate.toBuilder()
				.address1(bPartnerLocationCandidate.getAddress1() + "     ")
				.build();

		final String hashValue = DeliveryAddressUpsertProcessor.computeHashKey(bPartnerLocationCandidateWithWhitespaces);

		//then
		assertThat(hashValue).isEqualTo(expectedHashValue);
	}

	@Test
	public void givenBPartnerLocationCandidateWithChangedLetter_whenComputeHashKey_thenReturnDiffHashValue()
	{
		//given
		final BPartnerLocationCandidate bPartnerLocationCandidate = BPartnerLocationCandidate.builder()
				.bpartnerName("bpartnerName")
				.name("name")
				.address1("address1")
				.address2("address2")
				.address3("address3")
				.postal("postal")
				.city("city")
				.countryCode("countryCode")
				.billTo(false)
				.shipTo(true)
				.ephemeral(true)
				.build();

		final String hashValue = DeliveryAddressUpsertProcessor.computeHashKey(bPartnerLocationCandidate);

		//when
		final BPartnerLocationCandidate bPartnerLocationCandidateLetterChange = bPartnerLocationCandidate.toBuilder()
				.bpartnerName("bpartnerNami")
				.build();

		final String letterChangeHashValue = DeliveryAddressUpsertProcessor.computeHashKey(bPartnerLocationCandidateLetterChange);

		//then
		assertThat(hashValue).isNotEqualTo(letterChangeHashValue);
	}
}
