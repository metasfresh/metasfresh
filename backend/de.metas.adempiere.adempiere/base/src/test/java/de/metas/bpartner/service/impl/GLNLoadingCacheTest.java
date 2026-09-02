package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
@ExtendWith(AdempiereTestWatcher.class)
public class GLNLoadingCacheTest
{
	private final BPartnerId bpartnerId1 = BPartnerId.ofRepoId(1);
	private final BPartnerId bpartnerId2 = BPartnerId.ofRepoId(2);

	private final OrgId orgId1 = OrgId.ofRepoId(1);
	private final OrgId orgId2 = OrgId.ofRepoId(2);

	private Map<BPartnerId, I_C_BPartner> bpartnerId2BPartnerMap;

	private GLNLoadingCache glnLoadingCache;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		bpartnerId2BPartnerMap = new HashMap<>();

		glnLoadingCache = new GLNLoadingCache();
	}

	@Test
	public void twoLocations_sameGLN()
	{
		createBPartnerLocationRecord(orgId1, bpartnerId1, GLN.ofString("gln1"), null);
		createBPartnerLocationRecord(orgId2, bpartnerId2, GLN.ofString("gln1"), null);

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

	@Test
	public void find_partner_that_has_label_if_no_label_in_query()
	{
		createBPartnerLocationRecord(orgId1, bpartnerId1, GLN.ofString("gln1"), "label123");
		final Optional<BPartnerId> result = glnLoadingCache.getSingleBPartnerId(GLNQuery.builder()
				.gln(GLN.ofString("gln1"))
				.onlyOrgId(orgId1)
				.build());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(bpartnerId1);
	}

	@Test
	public void twoLocations_sameGLN_()
	{
		createBPartnerLocationRecord(orgId1, bpartnerId1, GLN.ofString("gln1"), null);
		createBPartnerLocationRecord(orgId1, bpartnerId2, GLN.ofString("gln1"), "label123");
		
		final Optional<BPartnerId> result = glnLoadingCache.getSingleBPartnerId(GLNQuery.builder()
				.gln(GLN.ofString("gln1"))
				.onlyOrgId(orgId1)
				.glnLookupLabel("label123")
				.build());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(bpartnerId2);
	}

	@Test
	public void fail_if_same_gln_and_query_without_glnNookupLabel()
	{
		createBPartnerLocationRecord(orgId1, bpartnerId1, GLN.ofString("gln1"), null);
		createBPartnerLocationRecord(orgId1, bpartnerId2, GLN.ofString("gln1"), "label123");

		final GLNQuery query = GLNQuery.builder()
				.gln(GLN.ofString("gln1"))
				.onlyOrgId(orgId1)
				.build();
		assertThatThrownBy(() -> glnLoadingCache.getSingleBPartnerId(query)).isInstanceOf(AdempiereException.class);
	}

	private void createBPartnerLocationRecord(@NonNull final OrgId orgId, 
											  @NonNull final BPartnerId bpartnerId,
											  @Nullable final GLN gln, 
											  @Nullable final String glnLookupLabel)
	{
		bpartnerId2BPartnerMap.computeIfAbsent(bpartnerId, id -> {
			final I_C_BPartner newBPartner = newInstance(I_C_BPartner.class);
			newBPartner.setC_BPartner_ID(id.getRepoId());
			newBPartner.setAD_Org_ID(orgId.getRepoId());
			newBPartner.setLookup_Label(glnLookupLabel);
			saveRecord(newBPartner);
			return newBPartner;
		});

		final I_C_BPartner_Location record = newInstance(I_C_BPartner_Location.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setGLN(GLN.toCode(gln));
		saveRecord(record);
	}
}
