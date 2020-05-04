/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.milestone;

import de.metas.serviceprovider.model.I_S_Milestone;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class MilestoneRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final Milestone milestone)
	{
		final I_S_Milestone record = InterfaceWrapperHelper.loadOrNew(milestone.getMilestoneId(), I_S_Milestone.class);

		if (milestone.getDueDate() != null)
		{
			record.setMilestone_DueDate(Timestamp.from(milestone.getDueDate()));
		}

		record.setAD_Org_ID(milestone.getOrgId().getRepoId());
		record.setName(milestone.getName());
		record.setValue(milestone.getValue());
		record.setDescription(milestone.getDescription());
		record.setProcessed(milestone.isProcessed());

		record.setExternalUrl(milestone.getExternalURL());

		InterfaceWrapperHelper.saveRecord(record);

		milestone.setMilestoneId(MilestoneId.ofRepoId(record.getS_Milestone_ID()));
	}

	public boolean exists(@NonNull final MilestoneId milestoneId)
	{
		return queryBL
				.createQueryBuilder(I_S_Milestone.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Milestone.COLUMNNAME_S_Milestone_ID, milestoneId.getRepoId())
				.create()
				.anyMatch();
	}
}
