/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.blockstatus;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_BlockStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
class BPartnerBlockStatusRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public BPartnerBlockStatus create(@NonNull final CreateBPartnerBlockStatusRequest request)
	{
		retrieveCurrentStatusForBPartnerId(request.getBPartnerId()).ifPresent(oldCurrent -> {
			oldCurrent.setIsCurrent(false);
			InterfaceWrapperHelper.save(oldCurrent);
		});
		
		final I_C_BPartner_BlockStatus blockStatusRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_BlockStatus.class);
		blockStatusRecord.setBlockStatus(request.getBlockStatus().getCode());
		blockStatusRecord.setC_BPartner_ID(request.getBPartnerId().getRepoId());
		blockStatusRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		blockStatusRecord.setReason(request.getReason());
		blockStatusRecord.setIsCurrent(true);

		saveRecord(blockStatusRecord);

		return ofRecord(blockStatusRecord);
	}

	@NonNull
	public Optional<BPartnerBlockStatus> retrieveBlockedByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return retrieveCurrentStatusForBPartnerId(bPartnerId)
				.map(BPartnerBlockStatusRepository::ofRecord);
	}

	@NonNull
	public IQuery<I_C_BPartner_BlockStatus> getBlockedBPartnerQuery()
	{
		return queryBL.createQueryBuilder(I_C_BPartner_BlockStatus.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_BlockStatus.COLUMNNAME_BlockStatus, BlockStatus.Blocked.getCode())
				.addEqualsFilter(I_C_BPartner_BlockStatus.COLUMNNAME_IsCurrent, true)
				.create();
	}

	@NonNull
	private Optional<I_C_BPartner_BlockStatus> retrieveCurrentStatusForBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_BlockStatus.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_C_BPartner_BlockStatus.COLUMNNAME_IsCurrent, true)
				.create()
				.firstOnlyOptional();
	}

	@NonNull
	private static BPartnerBlockStatus ofRecord(@NonNull final I_C_BPartner_BlockStatus blockStatusRecord)
	{
		return BPartnerBlockStatus.builder()
				.id(BPartnerBlockStatusId.ofRepoId(blockStatusRecord.getC_BPartner_BlockStatus_ID()))
				.status(BlockStatus.ofCode(blockStatusRecord.getBlockStatus()))
				.bPartnerId(BPartnerId.ofRepoId(blockStatusRecord.getC_BPartner_ID()))
				.orgId(OrgId.ofRepoId(blockStatusRecord.getAD_Org_ID()))
				.reason(blockStatusRecord.getReason())
				.isCurrent(blockStatusRecord.isCurrent())
				.build();
	}
}
