/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.log.status;

import de.metas.async.QueueWorkPackageId;
import de.metas.contracts.model.I_ModCntr_Log_Status;
import de.metas.error.AdIssueId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class ModularLogCreateStatusRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final ModularLogCreateStatus createStatus)
	{
		final I_ModCntr_Log_Status logStatus = getByRefRecordAndWorkPackageId(createStatus.workPackageId(), createStatus.recordReference())
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_ModCntr_Log_Status.class));

		logStatus.setProcessingStatus(createStatus.status().getCode());
		logStatus.setC_Queue_WorkPackage_ID(createStatus.workPackageId().getRepoId());
		logStatus.setRecord_ID(createStatus.recordReference().getRecord_ID());
		logStatus.setAD_Table_ID(createStatus.recordReference().getAD_Table_ID());

		logStatus.setAD_Issue_ID(AdIssueId.toRepoId(createStatus.issueId()));

		InterfaceWrapperHelper.save(logStatus);
	}

	@NonNull
	private Optional<I_ModCntr_Log_Status> getByRefRecordAndWorkPackageId(
			@NonNull final QueueWorkPackageId workPackageId,
			@NonNull final TableRecordReference recordReference)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Log_Status.class)
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_C_Queue_WorkPackage_ID, workPackageId)
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.addEqualsFilter(I_ModCntr_Log_Status.COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.create()
				.firstOnlyOptional(I_ModCntr_Log_Status.class);
	}
}
