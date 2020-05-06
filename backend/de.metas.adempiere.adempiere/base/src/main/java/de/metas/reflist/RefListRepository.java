/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.reflist;

import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Ref_List;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class RefListRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public RefListId save(@NonNull final RefList refList)
	{
		final I_AD_Ref_List record = loadOrNew(refList.getRefListId(), I_AD_Ref_List.class);

		record.setAD_Org_ID(refList.getOrgId().getRepoId());
		record.setAD_Reference_ID(refList.getReferenceId().getRepoId());

		record.setName(refList.getName());
		record.setValue(refList.getValue());

		saveRecord(record);

		return RefListId.ofRepoId(record.getAD_Ref_List_ID());
	}

	public Optional<RefList> getByRequest(@NonNull final GetRefListRequest getRefListRequest)
	{
		return queryBL.createQueryBuilder(I_AD_Ref_List.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_AD_Reference_ID, getRefListRequest.getReferenceId().getRepoId())
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_Value, getRefListRequest.getValue())
				.create()
				.firstOnlyOptional(I_AD_Ref_List.class)
				.map(this::ofRecord);
	}

	private RefList ofRecord(@NonNull final I_AD_Ref_List record)
	{
		return RefList
				.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.referenceId(ReferenceId.ofRepoId(record.getAD_Reference_ID()))
				.refListId(RefListId.ofRepoId(record.getAD_Ref_List_ID()))
				.name(record.getName())
				.value(record.getValue())
				.build();
	}
}
