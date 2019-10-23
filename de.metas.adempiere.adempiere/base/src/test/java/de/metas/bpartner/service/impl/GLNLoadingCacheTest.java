package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.organization.OrgId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class GLNLoadingCacheTest
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private final BPartnerId bpartnerId1 = BPartnerId.ofRepoId(1);
	private final BPartnerId bpartnerId2 = BPartnerId.ofRepoId(2);

	private final OrgId orgId1 = OrgId.ofRepoId(1);
	private final OrgId orgId2 = OrgId.ofRepoId(2);

	private GLNLoadingCache glnLoadingCache;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		glnLoadingCache = new GLNLoadingCache();
	}

	@Test
	public void twoLocations_sameGLN()
	{
		createBPartnerLocationRecord(bpartnerId1, orgId1, GLN.ofString("gln1"));
		createBPartnerLocationRecord(bpartnerId2, orgId2, GLN.ofString("gln1"));

		{
			final Optional<BPartnerId> result = glnLoadingCache.getSingleBPartnerId(GLNQuery.builder()
					.gln(GLN.ofString("gln1"))
					.onlyOrgId(orgId1)
					.build());

			assertThat(result).isPresent();
			assertThat(result.get()).isEqualTo(bpartnerId1);
		}

		{
			final Optional<BPartnerId> result = glnLoadingCache.getSingleBPartnerId(GLNQuery.builder()
					.gln(GLN.ofString("gln1"))
					.onlyOrgId(orgId2)
					.build());

			assertThat(result).isPresent();
			assertThat(result.get()).isEqualTo(bpartnerId2);
		}
	}

	private void createBPartnerLocationRecord(final BPartnerId bpartnerId, final OrgId orgId, final GLN gln)
	{
		I_C_BPartner_Location record = newInstance(I_C_BPartner_Location.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setGLN(gln.getCode());
		saveRecord(record);
	}
}
