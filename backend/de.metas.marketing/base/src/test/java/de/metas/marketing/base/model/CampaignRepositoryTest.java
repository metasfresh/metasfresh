/*
 * #%L
 * marketing-base
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

package de.metas.marketing.base.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CampaignRepositoryTest
{
	private CampaignRepository campaignRepository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		campaignRepository = new CampaignRepository();
	}

	private List<ContactPersonId> getContactPersonIds(final CampaignId campaignId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_Campaign_ID, campaignId)
				.create()
				.stream()
				.map(record -> ContactPersonId.ofRepoId(record.getMKTG_ContactPerson_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	@Test
	void addContactPersonsToCampaign()
	{
		final I_MKTG_Campaign campaignRecord = InterfaceWrapperHelper.newInstance(I_MKTG_Campaign.class);
		campaignRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
		campaignRecord.setName("name");
		campaignRecord.setMKTG_Platform_ID(123);
		InterfaceWrapperHelper.save(campaignRecord);

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecord.getMKTG_Campaign_ID());

		campaignRepository.addContactPersonsToCampaign(
				ImmutableSet.of(
						ContactPersonId.ofRepoId(1),
						ContactPersonId.ofRepoId(2)
				),
				campaignId);

		Assertions.assertThat(getContactPersonIds(campaignId)).containsExactlyInAnyOrder(
				ContactPersonId.ofRepoId(1),
				ContactPersonId.ofRepoId(2)
		);

		campaignRepository.addContactPersonsToCampaign(
				ImmutableSet.of(
						ContactPersonId.ofRepoId(2),
						ContactPersonId.ofRepoId(3),
						ContactPersonId.ofRepoId(4)
				),
				campaignId);

		Assertions.assertThat(getContactPersonIds(campaignId)).containsExactlyInAnyOrder(
				ContactPersonId.ofRepoId(1),
				ContactPersonId.ofRepoId(2),
				ContactPersonId.ofRepoId(3),
				ContactPersonId.ofRepoId(4)
		);
	}
}