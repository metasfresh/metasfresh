/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.attributes.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.attributes.BPartnerAttribute;
import de.metas.bpartner.attributes.BPartnerAttributes;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BPartnerAttributesRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void testSaveAndLoad()
	{
		final BPartnerAttributesRepository repo = new BPartnerAttributesRepository();

		final BPartnerAttributes bpAttributes = BPartnerAttributes.builder()
				.attributesSet1(ImmutableSet.of(
						BPartnerAttribute.ofCode("S1A1"),
						BPartnerAttribute.ofCode("S1A2"),
						BPartnerAttribute.ofCode("S1A3")))
				.attributesSet2(ImmutableSet.of(
						BPartnerAttribute.ofCode("S2A1"),
						BPartnerAttribute.ofCode("S2A2"),
						BPartnerAttribute.ofCode("S2A3")))
				.attributesSet3(ImmutableSet.of(
						BPartnerAttribute.ofCode("S3A1"),
						BPartnerAttribute.ofCode("S3A2"),
						BPartnerAttribute.ofCode("S3A3")))
				.attributesSet4(ImmutableSet.of(
						BPartnerAttribute.ofCode("S4A1"),
						BPartnerAttribute.ofCode("S4A2"),
						BPartnerAttribute.ofCode("S4A3")))
				.attributesSet5(ImmutableSet.of(
						BPartnerAttribute.ofCode("S5A1"),
						BPartnerAttribute.ofCode("S5A2"),
						BPartnerAttribute.ofCode("S5A3")))
				.build();

		repo.saveAttributes(bpAttributes, BPartnerId.ofRepoId(1));

		final BPartnerAttributes bpAttributesLoaded = repo.getByBPartnerId(BPartnerId.ofRepoId(1));
		Assertions.assertThat(bpAttributesLoaded).isEqualTo(bpAttributes);
	}

	@Test
	void testSaveChanges()
	{
		final BPartnerAttributesRepository repo = new BPartnerAttributesRepository();

		repo.saveAttributes(
				BPartnerAttributes.builder()
						.attributesSet1(ImmutableSet.of(
								BPartnerAttribute.ofCode("A1"),
								BPartnerAttribute.ofCode("A2"),
								BPartnerAttribute.ofCode("A3")))
						.build(),
				BPartnerId.ofRepoId(1));

		repo.saveAttributes(
				BPartnerAttributes.builder()
						.attributesSet1(ImmutableSet.of(
								BPartnerAttribute.ofCode("A2"),
								BPartnerAttribute.ofCode("A4")))
						.build(),
				BPartnerId.ofRepoId(1));

		Assertions.assertThat(repo.getByBPartnerId(BPartnerId.ofRepoId(1)))
				.isEqualTo(BPartnerAttributes.builder()
						.attributesSet1(ImmutableSet.of(
								BPartnerAttribute.ofCode("A2"),
								BPartnerAttribute.ofCode("A4")))
						.build());
	}

	@Test
	void testRemoteAllAttributes()
	{
		final BPartnerAttributesRepository repo = new BPartnerAttributesRepository();

		repo.saveAttributes(
				BPartnerAttributes.builder()
						.attributesSet1(ImmutableSet.of(
								BPartnerAttribute.ofCode("A1"),
								BPartnerAttribute.ofCode("A2"),
								BPartnerAttribute.ofCode("A3")))
						.build(),
				BPartnerId.ofRepoId(1));

		repo.saveAttributes(
				BPartnerAttributes.builder().build(),
				BPartnerId.ofRepoId(1));

		Assertions.assertThat(repo.getByBPartnerId(BPartnerId.ofRepoId(1)))
				.isEqualTo(BPartnerAttributes.builder().build());
	}
}