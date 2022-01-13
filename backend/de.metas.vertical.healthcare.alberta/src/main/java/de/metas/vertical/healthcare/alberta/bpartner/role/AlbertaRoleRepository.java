/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.bpartner.role;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_AlbertaRole;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbertaRoleRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AlbertaRole save(final @NonNull AlbertaRole role)
	{
		final I_C_BPartner_AlbertaRole record = InterfaceWrapperHelper.loadOrNew(role.getBPartnerAlbertaRoleId(), I_C_BPartner_AlbertaRole.class);

		record.setC_BPartner_ID(role.getBPartnerId().getRepoId());
		record.setAlbertaRole(role.getRole().getCode());

		InterfaceWrapperHelper.save(record);

		return toAlbertaRole(record);
	}

	@NonNull
	public List<AlbertaRole> getByPartnerId(final @NonNull BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_AlbertaRole.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_AlbertaRole.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.create()
				.list(I_C_BPartner_AlbertaRole.class)
				.stream()
				.map(this::toAlbertaRole)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public AlbertaRole toAlbertaRole(final @NonNull I_C_BPartner_AlbertaRole record)
	{
		final BPartnerAlbertaRoleId bPartnerAlbertaRoleId = BPartnerAlbertaRoleId.ofRepoId(record.getC_BPartner_AlbertaRole_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(record.getC_BPartner_ID());

		return AlbertaRole.builder()
				.bPartnerAlbertaRoleId(bPartnerAlbertaRoleId)
				.bPartnerId(bPartnerId)
				.role(AlbertaRoleType.ofCode(record.getAlbertaRole()))
				.build();
	}
}
